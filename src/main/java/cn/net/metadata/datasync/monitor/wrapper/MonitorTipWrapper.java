package cn.net.metadata.datasync.monitor.wrapper;

import java.util.Locale;

/**
 * Created by xiaowu on 16/1/12.
 */
public class MonitorTipWrapper {
    private String fieldName; // fieldCode
    private String fieldDesc; // fieldName
    private String oldValue;
    private String newValue;

    public MonitorTipWrapper(String fieldName, String fieldDesc, String oldValue, String newValue) {
        this.fieldName = fieldName;
        this.fieldDesc = fieldDesc;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    @Override
    public String toString() {
        return String.format(Locale.SIMPLIFIED_CHINESE, "%s 变动前: %s, 变动后: %s", fieldDesc, oldValue, newValue);
    }
}
