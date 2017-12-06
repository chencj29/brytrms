package cn.net.metadata.datasync.monitor.service.runnable;

import cn.net.metadata.datasync.monitor.service.EventNotification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.rms.entity.WarnRemindConfig;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by xiaowu on 16/1/19.
 */
public class SocketIOEventNotiCenterRunnable implements Runnable {

    private Map<String, Object> processVariables;

    public SocketIOEventNotiCenterRunnable(Map<String, Object> processVariables) {
        this.processVariables = processVariables;
    }

    @Override
    public void run() {
        try {
            ((EventNotification) SpringContextHolder.getBean(((WarnRemindConfig) processVariables.get("config")).getActionService())).process(processVariables);
        } catch (URISyntaxException | JsonProcessingException e) {
            LoggerFactory.getLogger(getClass()).error("执行SocketIOEventNotiCenter任务调试时失败, 信息:{}", e.getMessage());
            e.printStackTrace();
        }
    }
}
