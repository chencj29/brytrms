/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;

import java.util.Date;

/**
 * 安检口占用信息Entity
 *
 * @author xiaowu
 * @version 2016-04-18
 */

public class SecurityCheckinOccupyingInfoWrapper extends FlightDynamic {

    private static final long serialVersionUID = 1L;
    private String flightDynamicId;        // 航班动态ID
    private String flightDynamicCode;        // 航班号
    private String flightDynamicNature;        // 航班属性(1 - 国内, 2 - 国际, 3 - 混合)
    private Integer expectedSecurityCheckinNum;        // 预计占用安检口个数
    private Integer actualSecurityCheckinNum;        // 实际占用安检口个数
    private String inteSecurityCheckinCode;        // 安检口编号(国内)
    private String intlSecurityCheckinCode;        // 安检口编号(国际)
    private Date expectedStartTime;        // 预计开始占用时间
    private Date expectedOverTime;        // 预计结束占用时间
    private Date inteActualStartTime;        // 实际开始占用时间(国内)
    private Date inteActualOverTime;        // 实际结束占用时间(国内)
    private Date intlActualStartTime;        // 实际开始占用时间(国际)
    private Date intlActualOverTime;        // 实际结束占用时间(国际)

    private String detailId;

    private String aircraftNum;

    private String flightCompanyCode;

    private Date departurePlanTime;  //计起

    private Date etd; // 预起

    private Date atd; //实起

    private String aircraftTypeCode;

    public Date getDeparturePlanTime() {
        return departurePlanTime;
    }

    public void setDeparturePlanTime(Date departurePlanTime) {
        this.departurePlanTime = departurePlanTime;
    }

    public Date getEtd() {
        return etd;
    }

    public void setEtd(Date etd) {
        this.etd = etd;
    }

    public Date getAtd() {
        return atd;
    }

    public void setAtd(Date atd) {
        this.atd = atd;
    }

    public String getAircraftTypeCode() {
        return aircraftTypeCode;
    }

    public void setAircraftTypeCode(String aircraftTypeCode) {
        this.aircraftTypeCode = aircraftTypeCode;
    }

    public String getFlightCompanyCode() {
        return flightCompanyCode;
    }

    public void setFlightCompanyCode(String flightCompanyCode) {
        this.flightCompanyCode = flightCompanyCode;
    }

    public String getAircraftNum() {
        return aircraftNum;
    }

    public void setAircraftNum(String aircraftNum) {
        this.aircraftNum = aircraftNum;
    }

    public SecurityCheckinOccupyingInfoWrapper() {
        super();
    }

    public SecurityCheckinOccupyingInfoWrapper(String id) {
        super(id);
    }

    public String getFlightDynamicId() {
        return flightDynamicId;
    }

    public void setFlightDynamicId(String flightDynamicId) {
        this.flightDynamicId = flightDynamicId;
    }

    public String getFlightDynamicCode() {
        return flightDynamicCode;
    }

    public void setFlightDynamicCode(String flightDynamicCode) {
        this.flightDynamicCode = flightDynamicCode;
    }

    public String getFlightDynamicNature() {
        return flightDynamicNature;
    }

    public void setFlightDynamicNature(String flightDynamicNature) {
        this.flightDynamicNature = flightDynamicNature;
    }

    public Integer getExpectedSecurityCheckinNum() {
        return expectedSecurityCheckinNum;
    }

    public void setExpectedSecurityCheckinNum(Integer expectedSecurityCheckinNum) {
        this.expectedSecurityCheckinNum = expectedSecurityCheckinNum;
    }

    public Integer getActualSecurityCheckinNum() {
        return actualSecurityCheckinNum;
    }

    public void setActualSecurityCheckinNum(Integer actualSecurityCheckinNum) {
        this.actualSecurityCheckinNum = actualSecurityCheckinNum;
    }

    public String getInteSecurityCheckinCode() {
        return inteSecurityCheckinCode;
    }

    public void setInteSecurityCheckinCode(String inteSecurityCheckinCode) {
        this.inteSecurityCheckinCode = inteSecurityCheckinCode;
    }

    public String getIntlSecurityCheckinCode() {
        return intlSecurityCheckinCode;
    }

    public void setIntlSecurityCheckinCode(String intlSecurityCheckinCode) {
        this.intlSecurityCheckinCode = intlSecurityCheckinCode;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getExpectedStartTime() {
        return expectedStartTime;
    }

    public void setExpectedStartTime(Date expectedStartTime) {
        this.expectedStartTime = expectedStartTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getExpectedOverTime() {
        return expectedOverTime;
    }

    public void setExpectedOverTime(Date expectedOverTime) {
        this.expectedOverTime = expectedOverTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getInteActualStartTime() {
        return inteActualStartTime;
    }

    public void setInteActualStartTime(Date inteActualStartTime) {
        this.inteActualStartTime = inteActualStartTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getInteActualOverTime() {
        return inteActualOverTime;
    }

    public void setInteActualOverTime(Date inteActualOverTime) {
        this.inteActualOverTime = inteActualOverTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getIntlActualStartTime() {
        return intlActualStartTime;
    }

    public void setIntlActualStartTime(Date intlActualStartTime) {
        this.intlActualStartTime = intlActualStartTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getIntlActualOverTime() {
        return intlActualOverTime;
    }

    public void setIntlActualOverTime(Date intlActualOverTime) {
        this.intlActualOverTime = intlActualOverTime;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }
}