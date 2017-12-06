package cn.net.metadata.datasync.monitor.service;

import cn.net.metadata.datasync.monitor.annotation.*;
import cn.net.metadata.datasync.socketio.ConcurrentClientsHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.modules.rms.entity.WarnRemindConfig;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaowu on 16/1/21.
 */
@ActionService(type = ActionServiceType.TIMER, desc = "预警消息处理")
@Component
public class SocketIOTimerNotiCenter implements TimerNotification {
    static final Logger logger = LoggerFactory.getLogger(SocketIOTimerNotiCenter.class);
    final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Timer类型预警的处理方法
     *
     * @param clazz  数据类型
     * @param data   命中规则的数据集合
     * @param config 预警实体
     */
    @Override
    public void process(Class clazz, List data, WarnRemindConfig config) throws URISyntaxException, JsonProcessingException {
        Monitor monitor = (Monitor) clazz.getAnnotation(Monitor.class);
        String ns = monitor.socketNS();  //命名空间
        String moduleDesc = monitor.desc(); //模块描述
        String messageTpl = config.getMessageTpl();
        List<String> values = new ArrayList<>();

        Field mainTipField = ReflectionUtils.getFields(clazz, field -> field.isAnnotationPresent(TipMainField.class) && field.isAnnotationPresent(MonitorField.class)).stream().findFirst().get();
        MonitorField monitorFieldAnno = mainTipField.getAnnotation(MonitorField.class);
        String mainFieldDesc = monitorFieldAnno.desc();
        for (Object object : data) {
            values.add(String.valueOf(Reflections.getFieldValue(object, mainTipField.getName())));

            Map<String, Object> map = ImmutableMap.of("{{clazzDesc}}", moduleDesc, "{{tipFieldDesc}}", mainFieldDesc, "{{tipFieldValue}}", Joiner.on("、").join(values));

            if (StringUtils.isNotEmpty(messageTpl)) {
                messageTpl = StringUtils.replaceEachRepeatedly(messageTpl, map.keySet().toArray(new String[]{}), map.values().toArray(new String[]{}));
            }
//            IO.Options opts = new IO.Options();
//            opts.query = "ns=" + monitor.socketNS();
//            Socket socket = IO.socket(DictUtils.getDictValueConstant("socket_io_setting", "url"), opts).connect();
//            ImmutableMap<String, Object> parameters = ImmutableMap.of("data", objectMapper.writeValueAsString(data), "message", StringEscapeUtils.unescapeHtml4(messageTpl), "ns", ns);
//            socket.emit("tips", parameters);
            ConcurrentClientsHolder.getSocket("/dynamic_realtime_message").emit("dynamic_message_dispatcher",
                    objectMapper.writeValueAsString(ImmutableMap.of("occur", System.currentTimeMillis(), "message",
                            StringEscapeUtils.unescapeHtml4(messageTpl))));
        }
    }
}
