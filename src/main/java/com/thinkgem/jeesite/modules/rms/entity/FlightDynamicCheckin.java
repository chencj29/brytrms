/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.rms.service.FlightDynamicCheckinService;

import java.util.Date;

/**
 * 值机柜台员工分配Entity
 *
 * @author xiaopo
 * @version 2016-05-24
 */

@Monitor(desc = "值机柜台员工分配信息", tableName = "ams_flight_dynamic_checkin", service = FlightDynamicCheckinService.class, socketNS = "/rms/flightDynamicCheckin")
public class FlightDynamicCheckin extends DataEntity<FlightDynamicCheckin> {

    private static final long serialVersionUID = 1L;
    @Label("航班动态ID")
    @MonitorField(desc = "航班动态ID")
    private FlightDynamic flightDynamic;        // dynamic_id
    @Label("值机开始时间")
    @MonitorField(desc = "值机开始时间")
    private Date checkinStartTime;        // checkin_start_time
    @Label("值机结束时间")
    @MonitorField(desc = "值机结束时间")
    private Date checkinEndTime;        // checkin_end_time
    @Label("工作人员ID")
    @MonitorField(desc = "工作人员ID")
    private RmsEmp rmsEmp;        // emp_id
    @Label("工作人员名称")
    @MonitorField(desc = "工作人员名称")
    private String empName;        // emp_name

    private String _empId;

    public FlightDynamicCheckin() {
        super();
    }

    public FlightDynamicCheckin(String id) {
        super(id);
    }

    public FlightDynamic getFlightDynamic() {
        return flightDynamic;
    }

    public void setFlightDynamic(FlightDynamic flightDynamic) {
        this.flightDynamic = flightDynamic;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getCheckinStartTime() {
        return checkinStartTime;
    }

    public void setCheckinStartTime(Date checkinStartTime) {
        this.checkinStartTime = checkinStartTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getCheckinEndTime() {
        return checkinEndTime;
    }

    public void setCheckinEndTime(Date checkinEndTime) {
        this.checkinEndTime = checkinEndTime;
    }

    public RmsEmp getRmsEmp() {
        return rmsEmp;
    }

    public void setRmsEmp(RmsEmp rmsEmp) {
        this.rmsEmp = rmsEmp;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String get_empId() {
        if (this.getRmsEmp() != null)
            this._empId = this.getRmsEmp().getId();

        return _empId;
    }

    public void set_empId(String _empId) {
        this._empId = _empId;
    }
}