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
import com.thinkgem.jeesite.modules.rms.service.SlideCoastOccupyingInfoService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 滑槽占用信息Entity
 *
 * @author xiaowu
 * @version 2016-04-12
 */
@Oci
@Monitor(desc = "滑槽占用信息", tableName = "rms_slide_coast_oci", service = SlideCoastOccupyingInfoService.class, socketNS = "/global/dynamics")
public class SlideCoastOccupyingInfo extends DataEntity<SlideCoastOccupyingInfo> {

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
    @Label("预计占用滑槽个数")
    @MonitorField(desc = "预计占用滑槽个数")
    private Integer expectedSlideCoastNum;        // 预计占用滑槽个数
    @Label("实际占用滑槽个数")
    @MonitorField(desc = "实际占用滑槽个数")
    private Integer actualSlideCoastNum;        // 实际占用滑槽个数
    @Label("滑槽编号(国内)")
    @MonitorField(desc = "滑槽编号(国内)")
    private String inteSlideCoastCode;        // 滑槽编号(国内)
    @Label("滑槽编号(国际)")
    @MonitorField(desc = "滑槽编号(国际)")
    private String intlSlideCoastCode;        // 滑槽编号(国际)
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

    public SlideCoastOccupyingInfo() {
        super();
    }

    public SlideCoastOccupyingInfo(String id) {
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

    public Integer getExpectedSlideCoastNum() {
        return expectedSlideCoastNum;
    }

    public void setExpectedSlideCoastNum(Integer expectedSlideCoastNum) {
        this.expectedSlideCoastNum = expectedSlideCoastNum;
    }

    public Integer getActualSlideCoastNum() {
        return actualSlideCoastNum;
    }

    public void setActualSlideCoastNum(Integer actualSlideCoastNum) {
        this.actualSlideCoastNum = actualSlideCoastNum;
    }

    @Length(min = 0, max = 100, message = "滑槽编号(国内)长度必须介于 0 和 100 之间")
    public String getInteSlideCoastCode() {
        return inteSlideCoastCode;
    }

    public void setInteSlideCoastCode(String inteSlideCoastCode) {
        this.inteSlideCoastCode = inteSlideCoastCode;
    }

    @Length(min = 0, max = 100, message = "滑槽编号(国际)长度必须介于 0 和 100 之间")
    public String getIntlSlideCoastCode() {
        return intlSlideCoastCode;
    }

    public void setIntlSlideCoastCode(String intlSlideCoastCode) {
        this.intlSlideCoastCode = intlSlideCoastCode;
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
        if (expectedSlideCoastNum != null) msg.append("预计占用滑槽个数:").append(expectedSlideCoastNum).append(",");
        if (actualSlideCoastNum != null) msg.append("实际占用滑槽个数:").append(actualSlideCoastNum).append(",");
        if (inteSlideCoastCode != null) msg.append("滑槽编号(国内):").append(inteSlideCoastCode).append(",");
        if (intlSlideCoastCode != null) msg.append("滑槽编号(国际):").append(intlSlideCoastCode).append(",");
        if (expectedStartTime != null) msg.append("预计开始占用时间:").append(DateUtils.formatDate(expectedStartTime, "yyyy-MM-dd HH:mm")).append(",");
        if (expectedOverTime != null) msg.append("预计结束占用时间:").append(DateUtils.formatDate(expectedOverTime, "yyyy-MM-dd HH:mm")).append(",");
        if (inteActualStartTime != null) msg.append("实际开始占用时间(国内):").append(DateUtils.formatDate(inteActualStartTime, "yyyy-MM-dd HH:mm")).append(",");
        if (inteActualOverTime != null) msg.append("实际结束占用时间(国内):").append(DateUtils.formatDate(inteActualOverTime, "yyyy-MM-dd HH:mm")).append(",");
        if (intlActualStartTime != null) msg.append("实际开始占用时间(国际):").append(DateUtils.formatDate(intlActualStartTime, "yyyy-MM-dd HH:mm")).append(",");
        if (intlActualOverTime != null) msg.append("实际结束占用时间(国际):").append(DateUtils.formatDate(intlActualOverTime, "yyyy-MM-dd HH:mm")).append(",");
        return msg.toString();
    }
}