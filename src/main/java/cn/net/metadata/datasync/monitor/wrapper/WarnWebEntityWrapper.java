package cn.net.metadata.datasync.monitor.wrapper;

/**
 * Created by xiaowu on 16/1/26.
 */

/**
 * 预警参数转换包装类
 */
public class WarnWebEntityWrapper {
    //字段名称
    private String fieldName;
    //字段代码
    private String fieldCode;
    //条件名称
    private String conditionName;
    //条件代码
    private String conditionCode;
    //阀值
    private String thresholdValue;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public String getConditionCode() {
        return conditionCode;
    }

    public void setConditionCode(String conditionCode) {
        this.conditionCode = conditionCode;
    }

    public String getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(String thresholdValue) {
        this.thresholdValue = thresholdValue;
    }
}
