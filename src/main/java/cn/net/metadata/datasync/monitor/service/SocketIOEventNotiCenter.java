package cn.net.metadata.datasync.monitor.service;

import cn.net.metadata.datasync.monitor.annotation.*;
import cn.net.metadata.datasync.monitor.wrapper.MonitorTipWrapper;
import cn.net.metadata.datasync.socketio.ConcurrentClientsHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.modules.ams.utils.MsgWebServiceUtils;
import com.thinkgem.jeesite.modules.rms.entity.WarnRemindConfig;
import org.apache.commons.lang3.StringUtils;
import org.reflections.ReflectionUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xiaowu on 16/1/12.
 */
@ActionService(type = ActionServiceType.EVENT, desc = "提醒消息处理")
@Component
public class SocketIOEventNotiCenter implements EventNotification {
    public static final ObjectMapper mapper = new ObjectMapper();
//    private static final Logger logger = LoggerFactory.getLogger(SocketIOEventNotiCenter.class);

    @Override
    public void process(Map<String, Object> processVariables) throws URISyntaxException, JsonProcessingException {
        Object data = processVariables.get("data");
        Class clazz = (Class) processVariables.get("clazz");
        List<MonitorTipWrapper> changeList = (List<MonitorTipWrapper>) processVariables.get("changeList");
        WarnRemindConfig config = (WarnRemindConfig) processVariables.get("config");

        Monitor monitor = (Monitor) clazz.getAnnotation(Monitor.class);
        String ns = monitor.socketNS();  //命名空间
        String moduleDesc = monitor.desc(); //模块描述
        //        String id = String.valueOf(Reflections.getFieldValue(data, "id")); // 数据ID
        String random = String.valueOf(Reflections.getFieldValue(data, "random")); //前台传递过来的随机数

        //拿到主提醒field
        Field mainTipField = ReflectionUtils.getFields(clazz, field -> field.isAnnotationPresent(TipMainField.class) && field.isAnnotationPresent(MonitorField.class)).stream().findFirst().get();
        MonitorField monitorFieldAnno = mainTipField.getAnnotation(MonitorField.class);
        String mainFieldDesc = monitorFieldAnno.desc(), mainFieldValue = String.valueOf(Reflections.invokeGetter(data, mainTipField.getName()));

        //遍历拿到ChangeList
        StringBuffer sb = new StringBuffer();
        changeList.forEach(item -> sb.append(item.toString()).append("<br/>"));
        String messageTpl = StringUtils.trimToEmpty(config.getMessageTpl());

        Map<String, Object> map = ImmutableMap.of("{{clazzDesc}}", moduleDesc, "{{tipFieldDesc}}", mainFieldDesc, "{{tipFieldValue}}", mainFieldValue, "{{changeList}}", sb.toString());

        if (StringUtils.isNotEmpty(messageTpl)) {
            messageTpl = org.apache.commons.lang3.StringUtils.replaceEachRepeatedly(messageTpl, map.keySet().toArray(new String[]{}), map.values().toArray(new String[]{}));
        }

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("data", mapper.writeValueAsString(data)); // 完整的Data信息
        parameters.put("message", messageTpl);
        parameters.put("ns", ns);
        parameters.put("uuid", random);
        parameters.put("identify", clazz.getSimpleName());
        parameters.put("changeList", mapper.writeValueAsString(changeList));
        parameters.put("monitorType", config.getConfigType());
        parameters.put("oldData", mapper.writeValueAsString(processVariables.get("oldData")));
        if (Boolean.parseBoolean(Global.getConfig("syncMiddle"))) {
            Map<String, Class> ociClass = (Map<String, Class>) CacheUtils.get("ociClass");
            if (ociClass.containsKey(data.getClass().getSimpleName())) {
                String no = UUID.randomUUID().toString();
                Map<String, Object> noMap = new HashMap();
                noMap.put("namespace", monitor.socketNS());
                noMap.put("event", "modification");
                noMap.put("args", parameters);
                //缓存同步id（ArrivalGateOccupyingInfo_ae91c01d-27a1-4a53-80b6-286813ff1733_UPDATE）用于等待同步消息 wjp_2017年4月12日16时29分
                String syncNo = data.getClass().getSimpleName() + "_" + no + "_" + parameters.get("monitorType");
                CacheUtils.put(syncNo, noMap);
                //发送同步实体数据
                MsgWebServiceUtils.publishOciUniq(syncNo, mapper.writeValueAsString(data));
            } else {
                ConcurrentClientsHolder.getSocket(monitor.socketNS()).emit("modification", parameters);
            }
        } else {
            ConcurrentClientsHolder.getSocket(monitor.socketNS()).emit("modification", parameters);
        }
    }
}
