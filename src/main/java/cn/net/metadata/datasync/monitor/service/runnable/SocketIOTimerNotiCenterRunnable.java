package cn.net.metadata.datasync.monitor.service.runnable;

import cn.net.metadata.datasync.monitor.service.Notification;
import cn.net.metadata.datasync.monitor.service.TimerNotification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.rms.entity.WarnExecutionLog;
import com.thinkgem.jeesite.modules.rms.entity.WarnRemindConfig;
import com.thinkgem.jeesite.modules.rms.service.WarnExecutionLogService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by xiaowu on 16/1/19.
 */
public class SocketIOTimerNotiCenterRunnable implements Runnable {

    @Autowired
    WarnExecutionLogService service = SpringContextHolder.getBean(WarnExecutionLogService.class);
    private WarnRemindConfig config;
    private Notification notification;
    private List data;
    private Class<?> clazz;

    public SocketIOTimerNotiCenterRunnable(WarnRemindConfig config, Notification notification, List data, Class<?> clazz) {
        this.config = config;
        this.notification = notification;
        this.clazz = clazz;
        this.data = data;
    }

    @Override
    public void run() {
        try {
            ((TimerNotification) notification).process(clazz, data, config);
            //保存预警历史，防止重复提醒

            WarnExecutionLog log = new WarnExecutionLog();
            log.setConfigId(config.getId());
            log.setExecutionTime(new Timestamp(System.currentTimeMillis()));
            for (Object object : data) {
                try {
                    log.setBusinessId(String.valueOf(Reflections.getFieldValue(object, "id")));
                    service.save(log);
                } catch (NullPointerException e) {
                    LoggerFactory.getLogger(getClass()).error("保存预警运行记录时出现错误：{}", e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (URISyntaxException | JsonProcessingException e) {
            LoggerFactory.getLogger(getClass()).error("执行SocketIOTimerNotiCenter任务调试时失败, 信息:{}", e.getMessage());
            e.printStackTrace();
        }
    }
}
