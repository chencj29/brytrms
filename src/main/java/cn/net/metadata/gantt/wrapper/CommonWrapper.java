package cn.net.metadata.gantt.wrapper;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用包装模型
 * Created by xiaowu on 3/31/16.
 */
public class CommonWrapper {
    String id;                                      // ID
    String flightDynamicId;                         // 航班状态ID
    @Deprecated
    String flightNum;                               // 航班号
    String flightCompanyCode;                       // 航空公司号
    String flightDynamicCode;                       // 航班号
    String text;                                    // Gantt图显示的标题
    String startDate;                               // 起始日期
    String endDate;                                 // 结束日期
    String startTime;                               // 起始时间
    String endTime;                                 // 结束时间
    Integer expectedGate = 1;                       // 预算占用yAxis个数
    List<String> gate;                              // 实际占用yAxis个数
    @JsonProperty("transient")
    boolean moment = false;                         // 是否瞬间动作，如果为是，在Gantt上显示一个点
    String agentCode;                               //服务提供者

    Map<String, Object> extra = new HashMap<>();    // 额外的属性

    public String getFlightDynamicCode() {
        return flightDynamicCode;
    }

    public void setFlightDynamicCode(String flightDynamicCode) {
        this.flightDynamicCode = flightDynamicCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlightDynamicId() {
        return flightDynamicId;
    }

    public void setFlightDynamicId(String flightDynamicId) {
        this.flightDynamicId = flightDynamicId;
    }

    @Deprecated
    public String getFlightNum() {
        return flightNum;
    }

    @Deprecated
    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getExpectedGate() {
        return expectedGate;
    }

    public void setExpectedGate(Integer expectedGate) {
        this.expectedGate = expectedGate;
    }

    public List<String> getGate() {
        return gate;
    }

    public void setGate(List<String> gate) {
        this.gate = gate;
    }

    public boolean isMoment() {
        return moment;
    }

    public void setMoment(boolean moment) {
        this.moment = moment;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }

    public String getFlightCompanyCode() {
        return flightCompanyCode;
    }

    public void setFlightCompanyCode(String flightCompanyCode) {
        this.flightCompanyCode = flightCompanyCode;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }
}
