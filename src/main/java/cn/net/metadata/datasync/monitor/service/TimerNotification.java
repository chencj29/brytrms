package cn.net.metadata.datasync.monitor.service;

import cn.net.metadata.datasync.monitor.annotation.ActionService;
import cn.net.metadata.datasync.monitor.annotation.ActionServiceType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.thinkgem.jeesite.modules.rms.entity.WarnRemindConfig;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by xiaowu on 16/1/21.
 */
public interface TimerNotification extends Notification {
    /**
     * Timer类型预警的处理方法
     *
     * @param clazz  数据类型
     * @param data   命中规则的数据集合
     * @param config 预警实体
     */
    void process(Class clazz, List data, WarnRemindConfig config) throws URISyntaxException, JsonProcessingException;
}
