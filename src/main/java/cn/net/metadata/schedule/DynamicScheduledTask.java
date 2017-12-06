package cn.net.metadata.schedule;

import cn.net.metadata.datasync.socketio.ConcurrentClientsHolder;
import cn.net.metadata.wrapper.ConflictPlaceNumWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.rms.dao.FlightPlanPairDao;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DynamicScheduledTask implements SchedulingConfigurer {
    @Autowired
    FlightPlanPairDao flightPlanPairDao;

    final ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @Lazy(false)
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(() -> doConflictPlaceNum(), new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                //定时获取配置 机位冲突提醒预警 时间
                String cronStr = "0/50 * * * * ?";
                try {
                    int defTimeInterval = Integer.parseInt(DictUtils.getDictValueConstant("conflict_warn", "conflict_time"));
                    if (defTimeInterval > 0 && defTimeInterval < 60) cronStr = "0 0/" + defTimeInterval + " * * * ?";
                    else {
                        logger.error("配置的冲突提醒预警分钟数有误，只能输入1到59分钟！恢复成每50秒一次!!");
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                    logger.error("获取冲突提醒预警配置出错！");
                }
                //定时任务的业务逻辑
                //logger.info("动态修改定时任务cron参数:" + cronStr);

                // 定时任务触发，可修改定时任务的执行周期
                CronTrigger trigger = new CronTrigger(cronStr);
                Date nextExecDate = trigger.nextExecutionTime(triggerContext);
                return nextExecDate;
            }
        });
    }

    /**
     * wjp_2017年11月16日16时39分
     * 新加机位冲突提醒预警 每隔5分钟提醒，延迟50秒开启动
     */
    public void doConflictPlaceNum() {
        logger.debug("机位冲突提醒预警任务启动");
        //间隔分钟
        //Long defTimeInterval = Long.parseLong(DictUtils.getDictValue("机位冲突提醒", "conflict_place_num", "30"));
        Long defTimeInterval = Long.parseLong(DictUtils.getDictValueConstant("conflict_warn", "conflict_place_num"));
        List<ConflictPlaceNumWrapper> placeNumWrappers = flightPlanPairDao.findListByConflictPlaceNum();

        List<String> result = new ArrayList<>();
        if (!Collections3.isEmpty(placeNumWrappers)) {
            //处理航班取消
            placeNumWrappers = placeNumWrappers.stream().filter(a -> !(StringUtils.isBlank(a.getFd2()) && "CNL".equals(a.getStatus())))
                    .filter(a -> !(StringUtils.isBlank(a.getFd()) && "CNL".equals(a.getStatus2())))
                    .filter(a -> !("CNL".equals(a.getStatus()) && "CNL".equals(a.getStatus2())))
                    .collect(Collectors.toList());
            if (Collections3.isEmpty(placeNumWrappers)) return;

            for (int i = 1; i < placeNumWrappers.size(); i++) {
                ConflictPlaceNumWrapper beforeWrapper = placeNumWrappers.get(i - 1);
                ConflictPlaceNumWrapper wrapper = placeNumWrappers.get(i);
                //判断同一机位 且前方起飞后
                if (StringUtils.equals(beforeWrapper.getPlaceNum(), wrapper.getPlaceNum()) && beforeWrapper.getAtd() != null) {
                    Long timeInterval = DateUtils.pastMinutes(beforeWrapper.getOverTime(), wrapper.getStartTime());
                    //todo 处理航班取消  取消进港的单出，开始时间按当天0点，取消出港的单进，结束时间按第二天6点
                    //前一航班只处理占用结束时间
                    if ("CNL".equals(beforeWrapper.getStatus2())) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(beforeWrapper.getPlanDate());  // 按照计划日期的第二天6点
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                        calendar.set(Calendar.HOUR_OF_DAY, 6);
                        calendar.set(Calendar.MINUTE, 0);
                        timeInterval = DateUtils.pastMinutes(calendar.getTime(), wrapper.getStartTime());
                    }

                    //当前航班只处理占用开始时间
                    if ("CNL".equals(wrapper.getStatus())) {
                        Date startDate = null;
                        try {
                            startDate = DateUtils.parseDate(DateUtils.formatDate(wrapper.getStartTime()) + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
                        } catch (ParseException e) {
                            //e.printStackTrace();
                            logger.error("机位冲突处理中，处理取消单进航班时，开始占用时间计算出错");
                        }
                        timeInterval = DateUtils.pastMinutes(beforeWrapper.getOverTime(), startDate);
                    }

                    if (timeInterval <= defTimeInterval) {
                        //【进港航HU1234，机位：101】，【进港航CZ2345，机位：102】机位冲突提醒
                        result.add(StringUtils.tpl("【{} 与 {}，机位冲突,机位号：{}】", getflightNumMsg(beforeWrapper), getflightNumMsg(wrapper), beforeWrapper.getPlaceNum()));
                    }
                }
            }
        }

        if (!Collections3.isEmpty(result)) {
            try {
                ConcurrentClientsHolder.getSocket("/dynamic_realtime_message").emit("dynamic_message_dispatcher",
                        objectMapper.writeValueAsString(ImmutableMap.of("occur", System.currentTimeMillis(), "message",
                                StringEscapeUtils.unescapeHtml4(Joiner.on("<br>").join(result)))));
            } catch (JsonProcessingException e) {
                //e.printStackTrace();
                logger.error("机位冲突提醒消息发送异常！");
            }
            logger.info("机位冲突提醒:" + Joiner.on(",").join(result));
        }

        logger.debug("机位冲突提醒预警任务结束");
    }

    //获取航班类型及航班号
    private String getflightNumMsg(ConflictPlaceNumWrapper wrapper) {
        String result = "";
        if (wrapper != null) {
            if (StringUtils.isNotBlank(wrapper.getFlightNum()) && StringUtils.isNotBlank(wrapper.getFlightNum2())) {
                return wrapper.getFlightNum() + "/" + wrapper.getFlightNum2().substring(2, wrapper.getFlightNum2().length());
            } else if (StringUtils.isNotBlank(wrapper.getFlightNum())) {
                return wrapper.getFlightNum();
            } else if (StringUtils.isNotBlank(wrapper.getFlightNum2())) {
                return wrapper.getFlightNum2();
            }
        }
        return result;
    }
}
