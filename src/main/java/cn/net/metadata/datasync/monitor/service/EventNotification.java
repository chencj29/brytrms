package cn.net.metadata.datasync.monitor.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by xiaowu on 16/1/21.
 */
public interface EventNotification extends Notification {
    /**
     * 逻辑处理方法, 可以选择使用SocketIONotiCenter向Browsers发送消息
     */
    void process(Map<String, Object> processVariables) throws URISyntaxException, JsonProcessingException;
}
