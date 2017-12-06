package cn.net.metadata.datasync.monitor.wrapper;

/**
 * Created by xiaowu on 16/1/7.
 */
public class MonitorFieldWrapper {
    private String fieldName;
    private String fieldDesc;

    public MonitorFieldWrapper(String fieldName, String fieldDesc) {
        this.fieldName = fieldName;
        this.fieldDesc = fieldDesc;
    }

    public MonitorFieldWrapper() {
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MonitorFieldWrapper)) return false;

        MonitorFieldWrapper that = (MonitorFieldWrapper) o;

        if (!getFieldName().equals(that.getFieldName())) return false;
        return getFieldDesc().equals(that.getFieldDesc());

    }

    @Override
    public int hashCode() {
        int result = getFieldName().hashCode();
        result = 31 * result + getFieldDesc().hashCode();
        return result;
    }
}

