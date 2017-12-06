package cn.net.metadata.schedule;

import cn.net.metadata.datasync.monitor.factory.MonitorFactory;
import cn.net.metadata.datasync.monitor.service.runnable.SocketIOTimerNotiCenterRunnable;
import cn.net.metadata.datasync.monitor.wrapper.MonitorEntityWrapper;
import com.google.common.base.Joiner;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.rms.entity.WarnRemindConfig;
import com.thinkgem.jeesite.modules.rms.entity.WarnRemindConfigItem;
import com.thinkgem.jeesite.modules.rms.service.WarnRemindConfigItemService;
import com.thinkgem.jeesite.modules.rms.service.WarnRemindConfigService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaowu on 16/1/20.
 */
@Component
@Lazy(false)
public class BatchJobScheduler {

    String SCRIPT_TPL = " SELECT o.* FROM {{table_name}} o  LEFT JOIN ( SELECT ID, {{inner_field_list}} FROM {{table_name}} ) t " + " ON t.ID= o.ID WHERE {{condition_list}} ";
    String PREVENT_REPEAT_CONDITION = " ( SELECT COUNT(1) FROM WARN_EXECUTION_LOG log WHERE log.BUSINESS_ID = o.ID AND log.CONFIG_ID = '{{config_id}}' ) = 0";
    @Autowired
    WarnRemindConfigItemService itemService;
    @Autowired
    WarnRemindConfigService configService;
    @Autowired
    JdbcTemplate jdbcTemplate;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String getColumnName(String fieldCode) {
        return Joiner.on("_").join(StringUtils.splitByCharacterTypeCamelCase(fieldCode)).toLowerCase();
    }


    @Scheduled(fixedDelay = 30000, initialDelay = 30000)
    public void execute() throws Exception {
        logger.debug("预警调度任务启动");

        UserUtils.getUser();

        //查出所有timer的config
        List<WarnRemindConfig> timerConfigs = configService.getWarnConfigs();

        if (timerConfigs == null || timerConfigs.size() == 0) {
            logger.debug("没有发现可用的预警配置信息, 调试任务结束!");
            return;
        }

        for (WarnRemindConfig config : timerConfigs) {
            List<WarnRemindConfigItem> items = itemService.findList(new WarnRemindConfigItem(config));
            if (items == null || items.size() == 0) {
                logger.debug("编号:{},名称:{}没有找到可用的预警规则, 舍弃!", config.getId(), config.getConfigName());
                continue;
            }

            if (!MonitorFactory.getDatas().containsKey(config.getMonitorClass())) {
                logger.error("{}没有注册, 已忽略", config.getMonitorClass());
                continue;
            }

            MonitorEntityWrapper wrapper = MonitorFactory.getDatas().get(config.getMonitorClass());

            Map<String, String> replaceMap = new HashMap<>();
            replaceMap.put("{{table_name}}", wrapper.getTableName());


            logger.debug("编号:{},名称:{}找到{}条预警规则,正在进行分析转换", config.getId(), config.getConfigName(), items.size());

            List<String> innerFieldList = new ArrayList<>();
            List<String> conditionList = new ArrayList<>();
            for (WarnRemindConfigItem item : items) {
                if (StringUtils.isEmpty(item.getFieldCode())) continue;

                // 将其设置的规则以 AND 的关系转换为可执行的SQL语句
                String conditionCode = item.getConditionCode();

                if (StringUtils.isEmpty(conditionCode)) continue;

                if ((!StringUtils.equalsIgnoreCase("@=", conditionCode) && !StringUtils.equalsIgnoreCase("=@", conditionCode)) && StringUtils.isEmpty(item.getThresholdValue()))
                    continue;


                String columnName = getColumnName(item.getFieldCode());
                logger.debug("fieldName:{}, fieldCode:{}, columnName:{}, condition:{}", item.getFieldName(), item.getFieldCode(), columnName, item.getConditionCode());

                boolean illegal = false;

                switch (conditionCode) {
                    // 提前预警 @fieldCode - systimestamp = threshold_value
                    case "@=":
                        innerFieldList.add(" ABS(GETDIFFMINUTES(" + columnName + ", systimestamp)) AS inner_" + columnName + " ");
                        conditionList.add(" t.inner_" + columnName + " <= " + item.getThresholdValue() + " AND t.inner_" + columnName + " >= 0");
                        break;
                    // 事后提醒 systimestamp - @fieldCode = threshold_value
                    case "=@":
                        innerFieldList.add(" ABS(GETDIFFMINUTES(systimestamp, " + columnName + ")) AS inner_" + columnName + " ");
                        conditionList.add(" t.inner_" + columnName + " <= " + Integer.parseInt(item.getThresholdValue()) * 1.5 + " AND t.inner_" + columnName + " >= " + item.getThresholdValue());
                        break;

                    // 等于 fieldCode = threshold_value
                    case "=":
                        conditionList.add(" o." + columnName + " = '" + item.getThresholdValue() + "' ");
                        break;

                    // 不等于 fieldCode != threshold_value
                    case "!=":
                        conditionList.add(" o." + columnName + " = '" + item.getThresholdValue() + "' ");
                        break;

                    // 大于等于 fieldCode >= threshold_value
                    case ">=":
                        conditionList.add(" o." + columnName + " >= '" + item.getThresholdValue() + "' ");
                        break;

                    // 小于等于 fieldCode <= threshold_value
                    case "<=":
                        conditionList.add(" o." + columnName + " <= '" + item.getThresholdValue() + "' ");
                        break;

                    default:
                        illegal = true;
                        break;
                }


                if (illegal) {
                    logger.warn("组装条件语句时出现异常情况, 忽略");
                    continue;
                }
            }


            //添加防重提醒条件, 与ams_warn_execution_log双比
            conditionList.add(PREVENT_REPEAT_CONDITION);


            replaceMap.put("{{inner_field_list}}", Joiner.on(" , ").join(innerFieldList));
            replaceMap.put("{{condition_list}}", Joiner.on(" AND ").join(conditionList));
            replaceMap.put("{{config_id}}", config.getId());

            String finalScripts = SCRIPT_TPL;
            for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
                finalScripts = StringUtils.replace(finalScripts, entry.getKey(), entry.getValue());
            }

            logger.debug("分析成功, 生成脚本:{}", finalScripts);

            logger.debug("正在执行生成脚本");

            List datas = jdbcTemplate.query(finalScripts, BeanPropertyRowMapper.newInstance(Class.forName(config.getMonitorClass())));


            logger.debug("执行成功, 命中{}条数据", datas.size());

            if (datas.size() == 0) {
                logger.debug("编号:{},名称:{}并没有命中业务数据, 忽略", config.getId(), config.getConfigName());
                continue;
            }

            new Thread(new SocketIOTimerNotiCenterRunnable(config, SpringContextHolder.getBean(config.getActionService()), datas, Class.forName(config.getMonitorClass()))).start();

            innerFieldList.clear();
            conditionList.clear();
        }

        logger.debug("预警调度任务结束");
    }
}
