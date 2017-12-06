package cn.net.metadata.gantt.wrapper;

import java.util.Date;

/**
 * 机位Gantt图包装类
 * Created by xiaowu on 3/18/16.
 */
public class GateGanttWrapper extends CommonWrapper {
    String status;              // 航班状态
    String leave = "0";         // 中途是否离开
    Date leaveTime;             // 中途离开时间
    String inCode;              // 进港航班号
    String outCode;             // 出港航班号
    String typeCode;            // 机型号
    String leavePlace;          // 起飞起三字码
    String arrivePlace;         // 到达地三字码
    String aircraftStartDate;   // 航班到达时间
    String aircraftOverDate;    // 航班起飞时间
    String planDate;            // 计划日期

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getInCode() {
        return inCode;
    }

    public void setInCode(String inCode) {
        this.inCode = inCode;
    }

    public String getOutCode() {
        return outCode;
    }

    public void setOutCode(String outCode) {
        this.outCode = outCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getLeavePlace() {
        return leavePlace;
    }

    public void setLeavePlace(String leavePlace) {
        this.leavePlace = leavePlace;
    }

    public String getArrivePlace() {
        return arrivePlace;
    }

    public void setArrivePlace(String arrivePlace) {
        this.arrivePlace = arrivePlace;
    }

    public String getAircraftStartDate() {
        return aircraftStartDate;
    }

    public void setAircraftStartDate(String aircraftStartDate) {
        this.aircraftStartDate = aircraftStartDate;
    }

    public String getAircraftOverDate() {
        return aircraftOverDate;
    }

    public void setAircraftOverDate(String aircraftOverDate) {
        this.aircraftOverDate = aircraftOverDate;
    }
}
