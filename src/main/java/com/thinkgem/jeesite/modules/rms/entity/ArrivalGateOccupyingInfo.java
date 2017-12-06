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
import com.thinkgem.jeesite.modules.rms.service.ArrivalGateOccupyingInfoService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 到港门占用信息Entity
 *
 * @author bb5
 * @version 2016-04-08
 */
@Oci
@Monitor(desc = "到港门占用信息", tableName = "rms_arrival_gate_oci", service = ArrivalGateOccupyingInfoService.class, socketNS = "/global/dynamics")
public class ArrivalGateOccupyingInfo extends DataEntity<ArrivalGateOccupyingInfo> {

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
    @Label("预计占用到港门个数")
    @MonitorField(desc = "预计占用到港门个数")
    private Integer expectedArrivalGateNum;        // 预计占用到港门个数
    @Label("实际占用到港门个数")
    @MonitorField(desc = "实际占用到港门个数")
    private Integer actualArrivalGateNum;        // 实际占用到港门个数
    @Label("到港门编号(国内)")
    @MonitorField(desc = "到港门编号(国内)")
    private String inteArrivalGateCode;        // 到港门编号(国内)
    @Label("到港门编号(国际)")
    @MonitorField(desc = "到港门编号(国际)")
    private String intlArrivalGateCode;        // 到港门编号(国际)
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

    public ArrivalGateOccupyingInfo() {
        super();
    }

    public ArrivalGateOccupyingInfo(String id) {
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

    public Integer getExpectedArrivalGateNum() {
        return expectedArrivalGateNum;
    }

    public void setExpectedArrivalGateNum(Integer expectedArrivalGateNum) {
        this.expectedArrivalGateNum = expectedArrivalGateNum;
    }

    public Integer getActualArrivalGateNum() {
        return actualArrivalGateNum;
    }

    public void setActualArrivalGateNum(Integer actualArrivalGateNum) {
        this.actualArrivalGateNum = actualArrivalGateNum;
    }

    @Length(min = 0, max = 100, message = "到港门编号(国内)长度必须介于 0 和 100 之间")
    public String getInteArrivalGateCode() {
        return inteArrivalGateCode;
    }

    public void setInteArrivalGateCode(String inteArrivalGateCode) {
        this.inteArrivalGateCode = inteArrivalGateCode;
    }

    @Length(min = 0, max = 100, message = "到港门编号(国际)长度必须介于 0 和 100 之间")
    public String getIntlArrivalGateCode() {
        return intlArrivalGateCode;
    }

    public void setIntlArrivalGateCode(String intlArrivalGateCode) {
        this.intlArrivalGateCode = intlArrivalGateCode;
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
        if (expectedArrivalGateNum != null) msg.append("预计占用到港门个数:").append(expectedArrivalGateNum).append(",");
        if (actualArrivalGateNum != null) msg.append("实际占用到港门个数:").append(actualArrivalGateNum).append(",");
        if (inteArrivalGateCode != null) msg.append("到港门编号(国内):").append(inteArrivalGateCode).append(",");
        if (intlArrivalGateCode != null) msg.append("到港门编号(国际):").append(intlArrivalGateCode).append(",");
        if (expectedStartTime != null) msg.append("预计开始占用时间:").append(DateUtils.formatDate(expectedStartTime, "yyyy-MM-dd HH:mm")).append(",");
        if (expectedOverTime != null) msg.append("预计结束占用时间:").append(DateUtils.formatDate(expectedOverTime, "yyyy-MM-dd HH:mm")).append(",");
        if (inteActualStartTime != null) msg.append("实际开始占用时间(国内):").append(DateUtils.formatDate(inteActualStartTime, "yyyy-MM-dd HH:mm")).append(",");
        if (inteActualOverTime != null) msg.append("实际结束占用时间(国内):").append(DateUtils.formatDate(inteActualOverTime, "yyyy-MM-dd HH:mm")).append(",");
        if (intlActualStartTime != null) msg.append("实际开始占用时间(国际):").append(DateUtils.formatDate(intlActualStartTime, "yyyy-MM-dd HH:mm")).append(",");
        if (intlActualOverTime != null) msg.append("实际结束占用时间(国际):").append(DateUtils.formatDate(intlActualOverTime, "yyyy-MM-dd HH:mm")).append(",");
        return msg.toString();
    }
}