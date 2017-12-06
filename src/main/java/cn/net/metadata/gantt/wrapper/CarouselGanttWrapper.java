package cn.net.metadata.gantt.wrapper;

import java.util.List;

/**
 * 行李转盘Gantt包装类
 * Created by xiaowu on 3/31/16.
 */
public class CarouselGanttWrapper extends CommonWrapper {
    List<String> inteCode;      // 国内航班 - 行李转盘编号
    List<String> intlCode;      // 国际航班 - 行李转盘编号
    String inteActualStartTime; // 国内航班 - 行李转盘实际开始使用时间
    String inteActualOverTime;  // 国内航班 - 行李转盘实际结束使用时间
    String intlActualStartTime; // 国际航班 - 行李转盘实际开始使用时间
    String intlActualOverTime;  // 国际航班 - 行李转盘实际结束使用时间
    String flightDynamicNature; // 航班性质 - 1 ~ 国内航班 | 2 ~ 国际航班 | 3 ~ 混合航班

    String status;              // 航班状态
    String flightCompanyCode;              // 航空公司
    String planDate;

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getPlanDate() {
        return planDate;
    }

    public String getFlightCompanyCode() {
        return flightCompanyCode;
    }

    public void setFlightCompanyCode(String flightCompanyCode) {
        this.flightCompanyCode = flightCompanyCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getInteCode() {
        return inteCode;
    }

    public void setInteCode(List<String> inteCode) {
        this.inteCode = inteCode;
    }

    public List<String> getIntlCode() {
        return intlCode;
    }

    public void setIntlCode(List<String> intlCode) {
        this.intlCode = intlCode;
    }

    public String getInteActualStartTime() {
        return inteActualStartTime;
    }

    public void setInteActualStartTime(String inteActualStartTime) {
        this.inteActualStartTime = inteActualStartTime;
    }

    public String getInteActualOverTime() {
        return inteActualOverTime;
    }

    public void setInteActualOverTime(String inteActualOverTime) {
        this.inteActualOverTime = inteActualOverTime;
    }

    public String getIntlActualStartTime() {
        return intlActualStartTime;
    }

    public void setIntlActualStartTime(String intlActualStartTime) {
        this.intlActualStartTime = intlActualStartTime;
    }

    public String getIntlActualOverTime() {
        return intlActualOverTime;
    }

    public void setIntlActualOverTime(String intlActualOverTime) {
        this.intlActualOverTime = intlActualOverTime;
    }

    public String getFlightDynamicNature() {
        return flightDynamicNature;
    }

    public void setFlightDynamicNature(String flightDynamicNature) {
        this.flightDynamicNature = flightDynamicNature;
    }
}
