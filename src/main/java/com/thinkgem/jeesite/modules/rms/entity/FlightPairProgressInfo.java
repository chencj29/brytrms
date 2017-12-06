/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.rms.service.FlightPairProgressInfoService;

import java.util.Date;

/**
 * 航班运输保障信息Entity
 *
 * @author xiaowu
 * @version 2016-06-13
 */

@Monitor(desc = "航班运输保障信息", tableName = "rms_pair_prog_info", service = FlightPairProgressInfoService.class, socketNS = "/global/dynamics")
public class FlightPairProgressInfo extends DataEntity<FlightPairProgressInfo> {

    private static final long serialVersionUID = 1L;
    @Label("配对ID")
    @MonitorField(desc = "配对ID")
    private String pairId;        // pair_id
    @Label("主类型编码")
    @MonitorField(desc = "主类型编码")
    private String mainTypeCode;        // main_type_code
    @TipMainField
    @Label("主类型名称")
    @MonitorField(desc = "主类型名称")
    private String mainTypeName;        // main_type_name
    @Label("子类型编码")
    @MonitorField(desc = "子类型编码")
    private String subTypeCode;        // sub_type_code
    @Label("子类型名称")
    @MonitorField(desc = "子类型名称")
    private String subTypeName;        // sub_type_name
    @Label("过程名称")
    @MonitorField(desc = "过程名称")
    private String progressName;        // progress_name
    @Label("过程编码")
    @MonitorField(desc = "过程编码")
    private String progressCode;        // progress_code
    @Label("计划开始")
    @MonitorField(desc = "计划开始")
    private Date planStartTime;        // plan_start_time
    @Label("计划结束")
    @MonitorField(desc = "计划结束")
    private Date planOverTime;        // plan_over_time
    @Label("实际开始")
    @MonitorField(desc = "实际开始")
    private Date actualStartTime;        // actual_start_time
    @Label("实际结束")
    @MonitorField(desc = "实际结束")
    private Date actualOverTime;        // actual_over_time
    @Label("过程ID")
    @MonitorField(desc = "过程ID")
    private String progressRefId;        // progress_ref_id

    @Label("完成人")
    @MonitorField(desc = "完成人")
    private String opName;
    @Label("特种车辆类型")
    @MonitorField(desc = "特种车辆类型")
    private String specialCarType;
    @Label("特种车牌号")
    @MonitorField(desc = "特种车牌号")
    private String specialCarCode;

    public FlightPairProgressInfo() {
        super();
    }

    public FlightPairProgressInfo(String id) {
        super(id);
    }

    public String getPairId() {
        return pairId;
    }

    public void setPairId(String pairId) {
        this.pairId = pairId;
    }

    public String getMainTypeCode() {
        return mainTypeCode;
    }

    public void setMainTypeCode(String mainTypeCode) {
        this.mainTypeCode = mainTypeCode;
    }

    public String getMainTypeName() {
        return mainTypeName;
    }

    public void setMainTypeName(String mainTypeName) {
        this.mainTypeName = mainTypeName;
    }

    public String getSubTypeCode() {
        return subTypeCode;
    }

    public void setSubTypeCode(String subTypeCode) {
        this.subTypeCode = subTypeCode;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }

    public String getProgressName() {
        return progressName;
    }

    public void setProgressName(String progressName) {
        this.progressName = progressName;
    }

    public String getProgressCode() {
        return progressCode;
    }

    public void setProgressCode(String progressCode) {
        this.progressCode = progressCode;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getPlanOverTime() {
        return planOverTime;
    }

    public void setPlanOverTime(Date planOverTime) {
        this.planOverTime = planOverTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getActualOverTime() {
        return actualOverTime;
    }

    public void setActualOverTime(Date actualOverTime) {
        this.actualOverTime = actualOverTime;
    }

    public String getProgressRefId() {
        return progressRefId;
    }

    public void setProgressRefId(String progressRefId) {
        this.progressRefId = progressRefId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public String getSpecialCarType() {
        return specialCarType;
    }

    public void setSpecialCarType(String specialCarType) {
        this.specialCarType = specialCarType;
    }

    public String getSpecialCarCode() {
        return specialCarCode;
    }

    public void setSpecialCarCode(String specialCarCode) {
        this.specialCarCode = specialCarCode;
    }

    @Override
    public String toString() {
        StringBuffer msg = new StringBuffer();
        if (progressCode != null) msg.append("过程编号:").append(progressCode).append(",");
        if (progressName != null) msg.append("过程名称:").append(progressName).append(",");
        if (planStartTime != null) msg.append("计划开始占用时间:").append(DateUtils.formatDate(planStartTime, "yyyy-MM-dd HH:mm")).append(",");
        if (planOverTime != null) msg.append("计划结束占用时间:").append(DateUtils.formatDate(planOverTime, "yyyy-MM-dd HH:mm")).append(",");
        if (actualStartTime != null) msg.append("实际开始占用时间:").append(DateUtils.formatDate(actualStartTime, "yyyy-MM-dd HH:mm")).append(",");
        if (actualOverTime != null) msg.append("实际结束占用时间:").append(DateUtils.formatDate(actualOverTime, "yyyy-MM-dd HH:mm")).append(",");
        if (StringUtils.isNotBlank(opName)) msg.append("完成人:").append(opName).append(",");
        if (StringUtils.isNotBlank(specialCarType)) msg.append("特种车辆类型:").append(specialCarType).append(",");
        if (StringUtils.isNotBlank(specialCarCode)) msg.append("特种车牌号:").append(specialCarCode);
        return msg.toString();
    }
}