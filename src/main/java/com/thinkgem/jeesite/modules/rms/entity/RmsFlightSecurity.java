/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.RmsFlightSecurityService;

import java.util.Date;

/**
 * 安检旅客信息Entity
 *
 * @author dingshuang
 * @version 2016-05-21
 */

@Monitor(desc = "安检旅客信息信息", tableName = "rms_flight_security", service = RmsFlightSecurityService.class, socketNS = "/rms/rmsFlightSecurity")
public class RmsFlightSecurity extends DataEntity<RmsFlightSecurity> {

    private static final long serialVersionUID = 1L;
    @Label("安检时间")
    @MonitorField(desc = "安检时间")
    private Date checkDate;        // 安检时间
    @Label("航空公司代码")
    @MonitorField(desc = "航空公司代码")
    private String flightCompanyCode;        // 航空公司代码
    @Label("flight_company_name")
    @MonitorField(desc = "flight_company_name")
    private String flightCompanyName;        // flight_company_name
    @Label("航班号")
    @MonitorField(desc = "航班号")
    private String flightNum;        // 航班号
    @Label("到达地代码")
    @MonitorField(desc = "到达地代码")
    private String arrivalAirportCode;        // 到达地代码
    @Label("到达地名称")
    @MonitorField(desc = "到达地名称")
    private String arrivalAirportName;        // 到达地名称
    @Label("旅客姓名")
    @MonitorField(desc = "旅客姓名")
    private String name;        // 旅客姓名
    @Label("旅客性别")
    @MonitorField(desc = "旅客性别")
    private String sex;        // 旅客性别
    @Label("旅客国籍")
    @MonitorField(desc = "旅客国籍")
    private String nationality;        // 旅客国籍
    @Label("旅客身份证号")
    @MonitorField(desc = "旅客身份证号")
    private String idCode;        // 旅客身份证号
    @Label("未通过原因")
    @MonitorField(desc = "未通过原因")
    private String noPassReason;        // 未通过原因
    @Label("安检员")
    @MonitorField(desc = "安检员")
    private String screeners;        // 安检员
    @Label("不能登机原因")
    @MonitorField(desc = "不能登机原因")
    private String noBoardReason;        // 不能登机原因
    @Label("公安人员")
    @MonitorField(desc = "公安人员")
    private String publicSecurityOfficers;        // 公安人员

    public RmsFlightSecurity() {
        super();
    }

    public RmsFlightSecurity(String id) {
        super(id);
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getFlightCompanyCode() {
        return flightCompanyCode;
    }

    public void setFlightCompanyCode(String flightCompanyCode) {
        this.flightCompanyCode = flightCompanyCode;
    }

    public String getFlightCompanyName() {
        return flightCompanyName;
    }

    public void setFlightCompanyName(String flightCompanyName) {
        this.flightCompanyName = flightCompanyName;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public String getArrivalAirportName() {
        return arrivalAirportName;
    }

    public void setArrivalAirportName(String arrivalAirportName) {
        this.arrivalAirportName = arrivalAirportName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getNoPassReason() {
        return noPassReason;
    }

    public void setNoPassReason(String noPassReason) {
        this.noPassReason = noPassReason;
    }

    public String getScreeners() {
        return screeners;
    }

    public void setScreeners(String screeners) {
        this.screeners = screeners;
    }

    public String getNoBoardReason() {
        return noBoardReason;
    }

    public void setNoBoardReason(String noBoardReason) {
        this.noBoardReason = noBoardReason;
    }

    public String getPublicSecurityOfficers() {
        return publicSecurityOfficers;
    }

    public void setPublicSecurityOfficers(String publicSecurityOfficers) {
        this.publicSecurityOfficers = publicSecurityOfficers;
    }

}