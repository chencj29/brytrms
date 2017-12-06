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
import com.thinkgem.jeesite.modules.ams.service.LongPlanService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 * 长期计划管理Entity
 *
 * @author xiaopo
 * @version 2015-12-22
 */
@Monitor(desc = "航班计划详情", tableName = "AMS_FLIGHT_PLAN_DETAIL", service = LongPlanService.class, socketNS = "/rms/flightPlanDetail")
public class LongPlan extends DataEntity<LongPlan> {

    private static final long serialVersionUID = 1L;
    @TipMainField
    @MonitorField(desc = "年度")
    @Label("年度")
    private Integer year; // 年度 *
    @MonitorField(desc = "计划类型")
    @Label("计划类型")
    private String type; // 计划类型 *
    @MonitorField(desc = "开始时间")
    @Label("开始时间")
    private Date startDate; // 开始时间 *
    @MonitorField(desc = "结束时间")
    @Label("结束时间")
    private Date endDate; // 结束时间 *
    @MonitorField(desc = "进出港类型编码")
    @Label("进出港类型编码")
    private String inoutTypeCode; // 进出港类型编码 *
    @MonitorField(desc = "进出港类型名称")
    @Label("进出港类型名称")
    private String inoutTypeName; // 进出港类型名称 *
    @MonitorField(desc = "航空公司ID")
    @Label("航空公司ID")
    private String flightCompanyId; // 航空公司ID
    @MonitorField(desc = "航空公司代码")
    @Label("航空公司代码")
    private String flightCompanyCode; // 航空公司代码 *
    @MonitorField(desc = "航空公司名称")
    @Label("航空公司名称")
    private String flightCompanyName; // 航空公司名称 *
    @MonitorField(desc = "航班号")
    @Label("航班号")
    private String flightNum; // 航班号 *
    @MonitorField(desc = "机型ID")
    @Label("机型ID")
    private String aircraftTypeId;   // 机型ID
    @MonitorField(desc = "机型编号")
    @Label("机型编号")
    private String aircraftTypeCode; // 机型编号 *
    @MonitorField(desc = "机型名称")
    @Label("机型名称")
    private String aircraftTypeName; // 机型名称 *

    /**
     * 下面这些独立出来在经停地表中
     */
    @MonitorField(desc = "起飞地编号")
    @Label("起飞地编号")
    private String departureAirportCode; // 起飞地编号
    @MonitorField(desc = "起飞地名称")
    @Label("起飞地名称")
    private String departureAirportName; // 起飞地名称
    @MonitorField(desc = "到达地编号")
    @Label("到达地编号")
    private String arrivalAirportCode; // 到达地编号
    @MonitorField(desc = "到达地名称")
    @Label("到达地名称")
    private String arrivalAirportName; // 到达地名称
    @MonitorField(desc = "计划起飞时间")
    @Label("计划起飞时间")
    private Date departurePlanTime; // 计划起飞时间
    @MonitorField(desc = "计划到达时间")
    @Label("计划到达时间")
    private Date arrivalPlanTime; // 计划到达时间
    /******************/
    @MonitorField(desc = "班期")
    @Label("班期")
    private String period; // 班期 *
    @MonitorField(desc = "备注")
    @Label("备注")
    private String remark; // 备注
    @MonitorField(desc = "是否合法")
    @Label("是否合法")
    private String legal = "1"; // 是否合法 1:合法（默认）;0：不合法（不合法的记录会标黄提示）

    private List<LongPlanStop> longPlanStopList = Lists.newArrayList(); // 子表列表

    public LongPlan() {
        super();
    }

    public LongPlan(String id) {
        super(id);
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8:00")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8:00")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Length(min = 0, max = 100, message = "进出港类型编码长度必须介于 0 和 100 之间")
    public String getInoutTypeCode() {
        return inoutTypeCode;
    }

    public void setInoutTypeCode(String inoutTypeCode) {
        this.inoutTypeCode = inoutTypeCode;
    }

    @Length(min = 0, max = 200, message = "进出港类型名称长度必须介于 0 和 200 之间")
    public String getInoutTypeName() {
        return inoutTypeName;
    }

    public void setInoutTypeName(String inoutTypeName) {
        this.inoutTypeName = inoutTypeName;
    }

    @Length(min = 0, max = 100, message = "航空公司代码长度必须介于 0 和 100 之间")
    public String getFlightCompanyCode() {
        return flightCompanyCode;
    }

    public void setFlightCompanyCode(String flightCompanyCode) {
        this.flightCompanyCode = flightCompanyCode;
    }

    @Length(min = 0, max = 200, message = "航空公司名称长度必须介于 0 和 200 之间")
    public String getFlightCompanyName() {
        return flightCompanyName;
    }

    public void setFlightCompanyName(String flightCompanyName) {
        this.flightCompanyName = flightCompanyName;
    }

    @Length(min = 0, max = 100, message = "航班号长度必须介于 0 和 100 之间")
    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    @Length(min = 0, max = 100, message = "机型编号长度必须介于 0 和 100 之间")
    public String getAircraftTypeCode() {
        return aircraftTypeCode;
    }

    public void setAircraftTypeCode(String aircraftTypeCode) {
        this.aircraftTypeCode = aircraftTypeCode;
    }

    @Length(min = 0, max = 200, message = "机型名称长度必须介于 0 和 200 之间")
    public String getAircraftTypeName() {
        return aircraftTypeName;
    }

    public void setAircraftTypeName(String aircraftTypeName) {
        this.aircraftTypeName = aircraftTypeName;
    }

    @Length(min = 0, max = 100, message = "起飞地编号长度必须介于 0 和 100 之间")
    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    @Length(min = 0, max = 200, message = "起飞地名称长度必须介于 0 和 200 之间")
    public String getDepartureAirportName() {
        return departureAirportName;
    }

    public void setDepartureAirportName(String departureAirportName) {
        this.departureAirportName = departureAirportName;
    }

    @Length(min = 0, max = 100, message = "到达地编号长度必须介于 0 和 100 之间")
    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    @Length(min = 0, max = 200, message = "到达地名称长度必须介于 0 和 200 之间")
    public String getArrivalAirportName() {
        return arrivalAirportName;
    }

    public void setArrivalAirportName(String arrivalAirportName) {
        this.arrivalAirportName = arrivalAirportName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getDeparturePlanTime() {
        return departurePlanTime;
    }

    public void setDeparturePlanTime(Date departurePlanTime) {
        this.departurePlanTime = departurePlanTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getArrivalPlanTime() {
        return arrivalPlanTime;
    }

    public void setArrivalPlanTime(Date arrivalPlanTime) {
        this.arrivalPlanTime = arrivalPlanTime;
    }

    public List<LongPlanStop> getLongPlanStopList() {
        return longPlanStopList;
    }

    public void setLongPlanStopList(List<LongPlanStop> longPlanStopList) {
        this.longPlanStopList = longPlanStopList;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getFlightCompanyId() {
        return flightCompanyId;
    }

    public void setFlightCompanyId(String flightCompanyId) {
        this.flightCompanyId = flightCompanyId;
    }

    public String getAircraftTypeId() {
        return aircraftTypeId;
    }

    public void setAircraftTypeId(String aircraftTypeId) {
        this.aircraftTypeId = aircraftTypeId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal;
    }

}