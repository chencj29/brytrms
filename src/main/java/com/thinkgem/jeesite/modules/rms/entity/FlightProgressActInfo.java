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
import com.thinkgem.jeesite.modules.rms.service.FlightProgressActInfoService;

import java.util.Date;

/**
 * 保障过程时间Entity
 *
 * @author xiaowu
 * @version 2016-04-25
 */

@Monitor(desc = "保障过程时间信息", tableName = "rms_flight_progress_act_info", service = FlightProgressActInfoService.class, socketNS = "/global/dynamics")
public class FlightProgressActInfo extends DataEntity<FlightProgressActInfo> {

    private static final long serialVersionUID = 1L;
    @Label("配对航班ID")
    @MonitorField(desc = "配对航班ID")
    @TipMainField
    private String pairId;        // 配对航班ID
    @Label("保障过程ID")
    @MonitorField(desc = "保障过程ID")
    private String progressId;        // 保障过程ID
    @Label("计划开始时间")
    @MonitorField(desc = "计划开始时间")
    private Date planStartTime;        // 计划开始时间
    @Label("计划结束时间")
    @MonitorField(desc = "计划结束时间")
    private Date planOverTime;        // 计划结束时间
    @Label("实际开始时间")
    @MonitorField(desc = "实际开始时间")
    private Date actualStartTime;        // 实际开始时间
    @Label("实际结束时间")
    @MonitorField(desc = "实际结束时间")
    private Date actualOverTime;        // 实际结束时间

    private String _progressName;

    public FlightProgressActInfo() {
        super();
    }

    public FlightProgressActInfo(String id) {
        super(id);
    }

    public String getPairId() {
        return pairId;
    }

    public void setPairId(String pairId) {
        this.pairId = pairId;
    }

    public String getProgressId() {
        return progressId;
    }

    public void setProgressId(String progressId) {
        this.progressId = progressId;
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

    public String get_progressName() {
        return _progressName;
    }

    public void set_progressName(String _progressName) {
        this._progressName = _progressName;
    }
}