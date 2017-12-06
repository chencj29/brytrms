package cn.net.metadata.wrapper;

import java.util.Date;

public class ConflictPlaceNumWrapper {
    private Date planDate;
    private String placeNum;
    private String flightNum;
    private String flightNum2;
    private Date startTime;
    private Date overTime;
    private Date atd;
    private String fd;
    private String fd2;
    private String status;
    private String status2;

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getPlaceNum() {
        return placeNum;
    }

    public void setPlaceNum(String placeNum) {
        this.placeNum = placeNum;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public String getFlightNum2() {
        return flightNum2;
    }

    public void setFlightNum2(String flightNum2) {
        this.flightNum2 = flightNum2;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    public Date getAtd() {
        return atd;
    }

    public void setAtd(Date atd) {
        this.atd = atd;
    }

    public String getFd() {
        return fd;
    }

    public void setFd(String fd) {
        this.fd = fd;
    }

    public String getFd2() {
        return fd2;
    }

    public void setFd2(String fd2) {
        this.fd2 = fd2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }
}
