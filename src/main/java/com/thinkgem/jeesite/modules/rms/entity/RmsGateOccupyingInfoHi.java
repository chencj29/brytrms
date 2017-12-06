/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.RmsGateOccupyingInfoHiService;

import java.util.Date;

/**
 * 机位占用信息历史Entity
 *
 * @author wjp
 * @version 2016-05-16
 */

@Monitor(desc = "机位占用信息历史信息", tableName = "rms_gate_occupying_info_hi", service = RmsGateOccupyingInfoHiService.class, socketNS = "/rms/rmsGateOccupyingInfoHi")
public class RmsGateOccupyingInfoHi extends DataEntity<RmsGateOccupyingInfoHi> {

    private static final long serialVersionUID = 1L;
    @Label("航班ID")
    @MonitorField(desc = "航班ID")
    private String flightDynamicId;        // 航班ID
    @Label("航班号")
    @MonitorField(desc = "航班号")
    private String flightDynamicCode;        // 航班号
    @Label("预计使用机位个数")
    @MonitorField(desc = "预计使用机位个数")
    private Long expectedGateNum;        // 预计使用机位个数
    @Label("实际占用使用编号")
    @MonitorField(desc = "实际占用使用编号")
    private String actualGateNum;        // 实际占用使用编号
    @Label("开始占用时间")
    @MonitorField(desc = "开始占用时间")
    private Date startTime;        // 开始占用时间
    @Label("结束占用时间")
    @MonitorField(desc = "结束占用时间")
    private Date overTime;        // 结束占用时间
    @Label("中途是否离开")
    @MonitorField(desc = "中途是否离开")
    private String leave;        // 中途是否离开
    @Label("中途离开开始时间")
    @MonitorField(desc = "中途离开开始时间")
    private Date leaveTime;        // 中途离开开始时间

    public RmsGateOccupyingInfoHi() {
        super();
    }

    public RmsGateOccupyingInfoHi(String id) {
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

    public Long getExpectedGateNum() {
        return expectedGateNum;
    }

    public void setExpectedGateNum(Long expectedGateNum) {
        this.expectedGateNum = expectedGateNum;
    }

    public String getActualGateNum() {
        return actualGateNum;
    }

    public void setActualGateNum(String actualGateNum) {
        this.actualGateNum = actualGateNum;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

}