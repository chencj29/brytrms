package cn.net.metadata.datasync.mybatis.plugins;

import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.factory.MonitorFactory;
import cn.net.metadata.datasync.monitor.queue.MessageBlockingQueue;
import cn.net.metadata.datasync.monitor.service.runnable.SocketIOEventNotiCenterRunnable;
import cn.net.metadata.datasync.monitor.wrapper.MonitorEntityWrapper;
import cn.net.metadata.datasync.monitor.wrapper.MonitorTipWrapper;
import com.google.common.base.Joiner;
import com.google.common.collect.Ordering;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.rms.entity.WarnRemindConfig;
import com.thinkgem.jeesite.modules.rms.service.WarnRemindConfigItemService;
import com.thinkgem.jeesite.modules.rms.service.WarnRemindConfigService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 事件类型的预警提醒监听, 基于持久层框架MyBatis的Interceptor机制, 监听所有对于对象的create,modify及delete动作, 再根据entityClass查询匹配的
 * Configuration进行转换分析
 * <p>
 * 转换思路: 遍历匹配到的configuration对象, 将其items根据condition和priority进行排序, 如果到匹配到的create或者delete, 则忽略其他items, 转换成功
 * 如果匹配到的condition为update, 则根据updateObject的ID到数据库中拿到旧的数据originalObject, 利用Reflection进行反射分析, 配合updateItems进行
 * 逐条配对, 如果全部匹配中, 则转换成功
 * <p>
 * 转换成功后再调用configuration entity中配置的actionServiceId进行下一步操作
 * <p>
 * Created by xiaowu on 16/1/12.
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class ExecutorPlugins implements Interceptor {

    private WarnRemindConfigService configService;
    private WarnRemindConfigItemService itemService;
    private String common_exp_prefix = "预警提示监测中心(ExecutorPlugin)在执行时";
    private Logger logger = LoggerFactory.getLogger(getClass());

    static JdbcTemplate jdbcTemplate;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        if (jdbcTemplate == null) {
            jdbcTemplate = SpringContextHolder.getBean(JdbcTemplate.class);
        }

        if (invocation.getArgs()[1] == null || !MonitorFactory.getDatas().containsKey(invocation.getArgs()[1].getClass().getName()))
            return invocation.proceed();

        try {
            Class clazz = invocation.getArgs()[1].getClass();
            Object newData = invocation.getArgs()[1];

            Object randomObj = null;
            try {
                randomObj = Reflections.getFieldValue(newData, "random");
            } catch (IllegalArgumentException e) {
                return invocation.proceed();
            }
            if (randomObj == null) return invocation.proceed();
            if (StringUtils.trimToEmpty(String.valueOf(randomObj)).isEmpty()) return invocation.proceed();

            Object oldData = null;
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            String commandType = mappedStatement.getSqlCommandType().name();
            String statementId = mappedStatement.getId().substring(mappedStatement.getId().lastIndexOf(".") + 1);
            if (StringUtils.containsIgnoreCase(statementId, "delete")) commandType = SqlCommandType.DELETE.name();
            boolean isSuccess = false;
            WarnRemindConfig config;
            List<MonitorTipWrapper> changeList = new ArrayList<>();
            CrudService service = SpringContextHolder.getBean(MonitorFactory.getDatas().get(clazz.getName()).getService());

            if (service == null) {
                logger.error("{}找不到对应的Service业务类,请检查相关实体对象是否设置@monitor#service注解. class:{}", common_exp_prefix, clazz);
                return invocation.proceed();
            }

            Map<String, Object> variables = new HashMap<>();
            variables.put("entityClass", clazz.getName());
            variables.put("configType", commandType);


            if (configService == null) configService = SpringContextHolder.getBean(WarnRemindConfigService.class);
            if (itemService == null) itemService = SpringContextHolder.getBean(WarnRemindConfigItemService.class);

            //判断是否为update
            if (StringUtils.equalsIgnoreCase(commandType, "update")) {

                String oldDataQuerySQL = "select * from " + MonitorFactory.getDatas().get(clazz.getName()).getTableName() + " where id = ?";
                oldData = jdbcTemplate.queryForObject(oldDataQuerySQL, new Object[]{String.valueOf(Reflections.getFieldValue(newData, "id"))}, new BeanPropertyRowMapper(clazz));

                //oldData = service.get(String.valueOf(Reflections.getFieldValue(newData, "id")));

                if (oldData == null) {
                    logger.error("{}在数据库中找不到对应的数据,请查检数据是否存在, id:{}", common_exp_prefix, clazz.getName());
                    return invocation.proceed();
                }

                // 此处查询，按照了update 的 items项匹配， 要修改， 不按照子项匹配，只接查询config中是否有该entity的监控
                List<WarnRemindConfig> configList = configService.getConfigsByVairables(variables);
                if (configList == null || configList.size() != 1) return invocation.proceed();
                config = configList.get(0);

                for (Field field : ReflectionUtils.getFields(clazz)) {
                    if (field.getAnnotation(MonitorField.class) == null) continue;
                    String desc = field.getAnnotation(MonitorField.class).desc();
                    String oldValue = String.valueOf(Reflections.getFieldValue(oldData, field.getName()));
                    String newValue = String.valueOf(Reflections.getFieldValue(newData, field.getName()));

                    if (StringUtils.equalsIgnoreCase("null", oldValue) && StringUtils.isEmpty(newValue)) continue;
                    if (StringUtils.equals(oldValue, newValue)) continue;

                    if (field.getType().getName().equals("java.util.Date")) {  // 日期字段特殊处理
                        Date oDate = (Date) Reflections.getFieldValue(oldData, field.getName());
                        Date nDate = (Date) Reflections.getFieldValue(newData, field.getName());
                        if (!DateUtils.diffDate(nDate, oDate)) continue;
                        if (StringUtils.isNoneEmpty(oldValue) && !StringUtils.equals(oldValue, "null"))
                            oldValue = DateUtils.formatDate(oDate, "yyyy-MM-dd HH:mm:ss");
                        if (StringUtils.isNoneEmpty(newValue) && !StringUtils.equals(newValue, "null"))
                            newValue = DateUtils.formatDate(nDate, "yyyy-MM-dd HH:mm:ss");
                    }
                    changeList.add(new MonitorTipWrapper(field.getName(), desc, oldValue, newValue));
                    isSuccess = true;
                }
            } else {
                //反之组合消息直接发送
                List<WarnRemindConfig> configList = configService.getConfigsByVairables(variables);
                if (configList == null || configList.size() != 1) return invocation.proceed();
                config = configList.get(0);
                isSuccess = true;
            }

            if (isSuccess) {
                Map<String, Object> params = new HashMap<>();
                params.put("config", config);
                params.put("data", newData);
                params.put("oldData", oldData);
                params.put("changeList", changeList);
                params.put("clazz", clazz);

                MessageBlockingQueue.MESSAGE_QUEUE.put(new SocketIOEventNotiCenterRunnable(params));
            }
        } catch (Exception e) {
            logger.error("{}发生错误 - {}", common_exp_prefix, e.getMessage());
            e.printStackTrace();
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    public boolean isChanged(Object newData, Object oldData, String fieldName) {
        return !String.valueOf(Reflections.getFieldValue(newData, fieldName)).equals(String.valueOf(Reflections.getFieldValue(oldData, fieldName)));
    }

    private String getDifferenceFields(Object newData, Object oldData, String clazz) {
        MonitorEntityWrapper wrapper = MonitorFactory.getDatas().get(clazz);
        List<String> diffFieldList = new ArrayList<>();

        wrapper.getFields().forEach((key, value) -> {
            if (isChanged(newData, oldData, key))
                diffFieldList.add(key);
        });

        Collections.sort(diffFieldList, Ordering.natural());

        return Joiner.on(",").join(diffFieldList);
    }
}
