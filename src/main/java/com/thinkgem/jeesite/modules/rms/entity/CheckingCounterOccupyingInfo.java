/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.annotation.Oci;
import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.rms.service.CheckingCounterOccupyingInfoService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 值机柜台占用信息Entity
 *
 * @author xiaowu
 * @version 2016-04-13
 */
@Oci
@Monitor(desc = "值机柜台占用信息", tableName = "rms_checking_counter_oci", service = CheckingCounterOccupyingInfoService.class, socketNS = "/global/dynamics")
public class CheckingCounterOccupyingInfo extends DataEntity<CheckingCounterOccupyingInfo> {

    private static final long serialVersionUID = 1L;
    @Label("航班动态ID")
    @MonitorField(desc = "航班动态ID")
    private String flightDynamicId;        // 航班动态ID
    @Label("航班号")
    @MonitorField(desc = "航班号")
    @TipMainField
    private String flightDynamicCode;        // 航班号
    @Label("航班属性(1 - 国内, 2 - 国际, 3 - 混合)")
    @MonitorField(desc = "航班属性(1 - 国内, 2 - 国际, 3 - 混合)")
    private String flightDynamicNature;        // 航班属性(1 - 国内, 2 - 国际, 3 - 混合)
    @Label("预计占用值机柜台个数")
    @MonitorField(desc = "预计占用值机柜台个数")
    private Integer expectedCheckingCounterNum;        // 预计占用值机柜台个数
    @Label("实际占用值机柜台个数")
    @MonitorField(desc = "实际占用值机柜台个数")
    private Integer actualCheckingCounterNum;        // 实际占用值机柜台个数
    @Label("值机柜台编号(国内)")
    @MonitorField(desc = "值机柜台编号(国内)")
    private String inteCheckingCounterCode;        // 值机柜台编号(国内)
    @Label("值机柜台编号(国际)")
    @MonitorField(desc = "值机柜台编号(国际)")
    private String intlCheckingCounterCode;        // 值机柜台编号(国际)
    @Label("预计开始占用时间")
    @MonitorField(desc = "预计开始占用时间")
    private Date expectedStartTime;        // 预计开始占用时间
    @Label("预计结束占用时间")
    @MonitorField(desc = "预计结束占用时间")
    private Date expectedOverTime;        // 预计结束占用时间
    @Label("实际开始占用时间(国内)")
    @MonitorField(desc = "实际开始占用时间(国内)")
    private Date inteActualStartTime;        // 实际开始占用时间(国内)
    @Label("实际结束占用时间(国内)")
    @MonitorField(desc = "实际结束占用时间(国内)")
    private Date inteActualOverTime;        // 实际结束占用时间(国内)
    @Label("实际开始占用时间(国际)")
    @MonitorField(desc = "实际开始占用时间(国际)")
    private Date intlActualStartTime;        // 实际开始占用时间(国际)
    @Label("实际结束占用时间(国际)")
    @MonitorField(desc = "实际结束占用时间(国际)")
    private Date intlActualOverTime;        // 实际结束占用时间(国际)

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

    public CheckingCounterOccupyingInfo() {
        super();
    }

    public CheckingCounterOccupyingInfo(String id) {
        super(id);
    }

    @Length(min = 1, max = 36, message = "航班动态ID长度必须介于 1 和 36 之间")
    public String getFlightDynamicId() {
        return flightDynamicId;
    }

    public void setFlightDynamicId(String flightDynamicId) {
        this.flightDynamicId = flightDynamicId;
    }

    @Length(min = 1, max = 100, message = "航班号长度必须介于 1 和 100 之间")
    public String getFlightDynamicCode() {
        return flightDynamicCode;
    }

    public void setFlightDynamicCode(String flightDynamicCode) {
        this.flightDynamicCode = flightDynamicCode;
    }

    @Length(min = 1, max = 1, message = "航班属性(1 - 国内, 2 - 国际, 3 - 混合)长度必须介于 1 和 1 之间")
    public String getFlightDynamicNature() {
        return flightDynamicNature;
    }

    public void setFlightDynamicNature(String flightDynamicNature) {
        this.flightDynamicNature = flightDynamicNature;
    }

    public Integer getExpectedCheckingCounterNum() {
        return expectedCheckingCounterNum;
    }

    public void setExpectedCheckingCounterNum(Integer expectedCheckingCounterNum) {
        this.expectedCheckingCounterNum = expectedCheckingCounterNum;
    }

    public Integer getActualCheckingCounterNum() {
        return actualCheckingCounterNum;
    }

    public void setActualCheckingCounterNum(Integer actualCheckingCounterNum) {
        this.actualCheckingCounterNum = actualCheckingCounterNum;
    }

    @Length(min = 0, max = 100, message = "值机柜台编号(国内)长度必须介于 0 和 100 之间")
    public String getInteCheckingCounterCode() {
        return inteCheckingCounterCode;
    }

    public void setInteCheckingCounterCode(String inteCheckingCounterCode) {
        this.inteCheckingCounterCode = inteCheckingCounterCode;
    }

    @Length(min = 0, max = 100, message = "值机柜台编号(国际)长度必须介于 0 和 100 之间")
    public String getIntlCheckingCounterCode() {
        return intlCheckingCounterCode;
    }

    public void setIntlCheckingCounterCode(String intlCheckingCounterCode) {
        this.intlCheckingCounterCode = intlCheckingCounterCode;
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

    @Override
    public String toString() {
        StringBuffer msg = new StringBuffer();
        if (expectedCheckingCounterNum != null) msg.append("预计占用值机柜台个数:").append(expectedCheckingCounterNum).append(",");
        if (actualCheckingCounterNum != null) msg.append("实际占用值机柜台个数:").append(actualCheckingCounterNum).append(",");
        if (inteCheckingCounterCode != null) msg.append("值机柜台编号(国内):").append(inteCheckingCounterCode).append(",");
        if (intlCheckingCounterCode != null) msg.append("值机柜台编号(国际):").append(intlCheckingCounterCode).append(",");
        if (expectedStartTime != null) msg.append("预计开始占用时间:").append(DateUtils.formatDate(expectedStartTime, "yyyy-MM-dd HH:mm")).append(",");
        if (expectedOverTime != null) msg.append("预计结束占用时间:").append(DateUtils.formatDate(expectedOverTime, "yyyy-MM-dd HH:mm")).append(",");
        if (inteActualStartTime != null) msg.append("实际开始占用时间(国内):").append(DateUtils.formatDate(inteActualStartTime, "yyyy-MM-dd HH:mm")).append(",");
        if (inteActualOverTime != null) msg.append("实际结束占用时间(国内):").append(DateUtils.formatDate(inteActualOverTime, "yyyy-MM-dd HH:mm")).append(",");
        if (intlActualStartTime != null) msg.append("实际开始占用时间(国际):").append(DateUtils.formatDate(intlActualStartTime, "yyyy-MM-dd HH:mm")).append(",");
        if (intlActualOverTime != null) msg.append("实际结束占用时间(国际):").append(DateUtils.formatDate(intlActualOverTime, "yyyy-MM-dd HH:mm")).append(",");

        return msg.toString();
    }
}