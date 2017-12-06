package cn.net.metadata.datasync.monitor.wrapper;

import com.thinkgem.jeesite.common.service.CrudService;

import java.util.Map;

/**
 * Created by xiaowu on 16/1/7.
 */
public class MonitorEntityWrapper {
    private String clazz;
    private String desc;
    private String tableName;
    private Class<? extends CrudService> service;
    private String socketNS;
    private Map<String, String> fields;

    public MonitorEntityWrapper() {
    }

    public String getSocketNS() {
        return socketNS;
    }

    public void setSocketNS(String socketNS) {
        this.socketNS = socketNS;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Class<? extends CrudService> getService() {
        return service;
    }

    public void setService(Class<? extends CrudService> service) {
        this.service = service;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MonitorEntityWrapper)) return false;

        MonitorEntityWrapper that = (MonitorEntityWrapper) o;

        if (!getClazz().equals(that.getClazz())) return false;
        if (!getDesc().equals(that.getDesc())) return false;
        if (!getTableName().equals(that.getTableName())) return false;
        return getFields().equals(that.getFields());

    }

    @Override
    public int hashCode() {
        int result = getClazz().hashCode();
        result = 31 * result + getDesc().hashCode();
        result = 31 * result + getTableName().hashCode();
        result = 31 * result + getFields().hashCode();
        return result;
    }
}
