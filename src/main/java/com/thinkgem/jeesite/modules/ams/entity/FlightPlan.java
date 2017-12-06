/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.ams.service.FlightPlanService;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 航班计划管理Entity
 *
 * @author xiaopo
 * @version 2015-12-24
 */
@Monitor(desc = "航班计划信息", tableName = "AMS_FLIGHT_PLAN", service = FlightPlanService.class, socketNS = "/rms/flightPlan")
public class FlightPlan extends DataEntity<FlightPlan> {

    private static final long serialVersionUID = 1L;
    @MonitorField(desc = "计划日期")
    @Label("计划日期")
    private Date planTime;        // 计划日期
    /**
     * 3：已动态化
     * 2：已发布
     * 1：已审核
     * 0：初始化
     */
    @TipMainField
    @MonitorField(desc = "状态")
    @Label("状态")
    private Integer status;        // 状态
    @MonitorField(desc = "初始化时间")
    @Label("初始化时间")
    private Date initTime;        // 初始化时间
    @MonitorField(desc = "发布时间")
    @Label("发布时间")
    private Date publishTime;        // 发布时间
    @MonitorField(desc = "是否变更")
    @Label("是否变更")
    private Integer ischanged;        // 是否变更
    @MonitorField(desc = "审核人意见")
    @Label("审核人意见")
    private String auditOpinion;
    @MonitorField(desc = "审核时间")
    @Label("审核时间")
    private Date auditTime;

    @Label("航班明细")
    private List<FlightPlanDetail> flightPlanDetailList = Lists.newArrayList();        // 子表列表


    public FlightPlan() {
        super();
    }

    public FlightPlan(String id) {
        super(id);
    }

    private Integer number;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    @NotNull(message = "计划日期不能为空")
    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    @NotNull(message = "状态不能为空")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    @NotNull(message = "初始化时间不能为空")
    public Date getInitTime() {
        return initTime;
    }

    public void setInitTime(Date initTime) {
        this.initTime = initTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    @NotNull(message = "是否变更不能为空")
    public Integer getIschanged() {
        return ischanged;
    }

    public void setIschanged(Integer ischanged) {
        this.ischanged = ischanged;
    }

    public List<FlightPlanDetail> getFlightPlanDetailList() {
        return flightPlanDetailList;
    }

    public void setFlightPlanDetailList(List<FlightPlanDetail> flightPlanDetailList) {
        this.flightPlanDetailList = flightPlanDetailList;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}