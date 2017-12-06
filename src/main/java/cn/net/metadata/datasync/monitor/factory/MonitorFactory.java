package cn.net.metadata.datasync.monitor.factory;

import cn.net.metadata.datasync.monitor.wrapper.MonitorEntityWrapper;
import com.alibaba.druid.support.json.JSONUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaowu on 16/1/7.
 */
public class MonitorFactory {
    public static final Map<String, MonitorEntityWrapper> datas = new HashMap<>();

    public static final Map<String, List<String>> actionServices = new HashMap<>();

    private MonitorFactory() {
    }

    public static final String getJson() {
        return JSONUtils.toJSONString(datas);
    }

    public static Map<String, MonitorEntityWrapper> getDatas() {
        return datas;
    }

    public static Map<String, List<String>> getActionServices() {
        return actionServices;
    }
}
