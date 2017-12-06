package cn.net.metadata.datasync.monitor.wrapper;

/**
 * Created by xiaowu on 16/1/12.
 */
public class MonitorLogEntity {
    private long start;
    private long stop;
    private Class clazz;
    private Object newData;
    private Object oldData;
    private String commandType;
    private String configId;
    private String configName;
    private String actionService;
    private Integer itemCount;
    private boolean success;
    private String exception_str;


    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Object getNewData() {
        return newData;
    }

    public void setNewData(Object newData) {
        this.newData = newData;
    }

    public Object getOldData() {
        return oldData;
    }

    public void setOldData(Object oldData) {
        this.oldData = oldData;
    }

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getActionService() {
        return actionService;
    }

    public void setActionService(String actionService) {
        this.actionService = actionService;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getStop() {
        return stop;
    }

    public void setStop(long stop) {
        this.stop = stop;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getException_str() {
        return exception_str;
    }

    public void setException_str(String exception_str) {
        this.exception_str = exception_str;
    }

    @Override
    public String toString() {
        return "MonitorLogEntity{" +
                "start=" + start +
                ", stop=" + stop +
                ", clazz=" + clazz +
                ", newData=" + newData +
                ", oldData=" + oldData +
                ", commandType='" + commandType + '\'' +
                ", configId='" + configId + '\'' +
                ", configName='" + configName + '\'' +
                ", actionService='" + actionService + '\'' +
                ", itemCount=" + itemCount +
                ", success=" + success +
                ", exception_str='" + exception_str + '\'' +
                '}';
    }
}
