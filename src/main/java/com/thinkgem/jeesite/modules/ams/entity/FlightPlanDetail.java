/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.ams.service.FlightPlanDetailService;
import com.thinkgem.jeesite.modules.rms.entity.AirportTerminal;
import com.thinkgem.jeesite.modules.rms.entity.ServiceProvider;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 航班计划管理Entity
 *
 * @author xiaopo
 * @version 2015-12-29
 */
@Monitor(desc = "航班计划详情", tableName = "AMS_FLIGHT_PLAN_DETAIL", service = FlightPlanDetailService.class, socketNS = "/rms/flightPlanDetail")
public class FlightPlanDetail extends DataEntity<FlightPlanDetail> {

    private static final long serialVersionUID = 1L;


    private FlightPlan flightPlan;        // 航班计划ID 父类

    private FlightCompanyInfo flightCompanyInfo;

    @MonitorField(desc = "航班公司代码")
    @Label("航班公司代码")
    private String flightCompanyCode;   // 航班公司代码
    @MonitorField(desc = "航班公司名称")
    @Label("航班公司名称")
    private String flightCompanyName;        // 航班公司名称

    @TipMainField
    @MonitorField(desc = "航班号")
    @Label("航班号")
    private String flightNum;        // 航班号
    @MonitorField(desc = "飞机号")
    @Label("飞机号")
    private String aircraftNum;        // 飞机号

    @Label("机型参数")
    private AircraftParameters aircraftParameters;        // 机型ID
    @MonitorField(desc = "机型编号")
    @Label("机型编号")
    private String aircraftTypeCode;        // 机型编号
    @MonitorField(desc = "机型名称")
    @Label("机型名称")
    private String aircraftTypeName;        // 机型名称

    @Label("航班性质")
    private FlightNature flightNature;        // 航班性质ID
    @MonitorField(desc = "航班性质编号")
    @Label("航班性质编号")
    private String flightNatureCode;        // 航班性质编号
    @MonitorField(desc = "航班性质名称")
    @Label("航班性质名称")
    private String flightNatureName;        // 航班性质名称

    @Label("航班属性")
    private FlightProperties flightProperties;        // 航班属性ID
    @MonitorField(desc = "航班属性编号")
    @Label("航班属性编号")
    private String flightAttrCode;        // 航班属性编号
    @MonitorField(desc = "航班属性名称")
    @Label("航班属性名称")
    private String flightAttrName;        // 航班属性名称

    @Label("起飞地")
    private AmsAddress departureAirport;        // 起飞地ID
    @MonitorField(desc = "起飞地编号")
    @Label("起飞地编号")
    private String departureAirportCode;        // 起飞地编号
    @MonitorField(desc = "起飞地名称")
    @Label("起飞地名称")
    private String departureAirportName;        // 起飞地名称

    @Label("经停地1")
    private AmsAddress passAirport1;        // 经停地1ID
    @MonitorField(desc = "经停地1编号")
    @Label("经停地1编号")
    private String passAirport1Code;        // 经停地1编号
    @MonitorField(desc = "经停地1名称")
    @Label("经停地1名称")
    private String passAirport1Name;        // 经停地1名称

    @Label("经停地2")
    private AmsAddress passAirport2;        // 经停地2ID
    @MonitorField(desc = "经停地2编号")
    @Label("经停地2编号")
    private String passAirport2Code;        // 经停地2编号
    @MonitorField(desc = "经停地2名称")
    @Label("经停地2名称")
    private String passAirport2Name;        // 经停地2名称

    @Label("到达地")
    private AmsAddress arrivalAirport;        // 到达地ID
    @MonitorField(desc = "到达地编号")
    @Label("到达地编号")
    private String arrivalAirportCode;        // 到达地编号
    @MonitorField(desc = "到达地名称")
    @Label("到达地名称")
    private String arrivalAirportName;        // 到达地名称

    @MonitorField(desc = "计划起飞时间")
    @Label("计划起飞时间")
    private Date departurePlanTime;        // 计划起飞时间
    @MonitorField(desc = "计划到达时间")
    @Label("计划到达时间")
    private Date arrivalPlanTime;        // 计划到达时间

    @MonitorField(desc = "进出港类型编码")
    @Label("进出港类型编码")
    private String inoutTypeCode;        // 进出港类型编码
    @MonitorField(desc = "进出港类型名称")
    @Label("进出港类型名称")
    private String inoutTypeName;        // 进出港类型名称

    @Label("代理人")
    private ServiceProvider serviceProvider;        // 代理人ID
    @MonitorField(desc = "代理人编码")
    @Label("代理人编码")
    private String agentCode;        // 代理人编码
    @MonitorField(desc = "代理人名称")
    @Label("代理人名称")
    private String agentName;        // 代理人名称

    @Label("航站楼")
    private AirportTerminal airportTerminal;        // 航站楼ID
    @MonitorField(desc = "航站楼编号")
    @Label("航站楼编号")
    private String terminal;        // 航站楼
    @MonitorField(desc = "状态")
    @Label("状态")
    private Integer status = 0; // 状态

    @Label("变更类型")
    @MonitorField(desc = "变更类型")
    private String changeType; // 变更类型 1：修改数据需要审核；2：新增了数据需要审核；3：删除了数据需要审核

    @MonitorField(desc = "计划日期")
    @Label("计划日期")
    private Date planDate;

    public FlightPlanDetail() {
        super();
    }

    public FlightPlanDetail(String id) {
        super(id);
    }

    public FlightPlanDetail(FlightPlan flightPlan) {
        this.flightPlan = flightPlan;
    }

    //@Length(min = 1, max = 36, message = "航班计划ID长度必须介于 1 和 36 之间")
    public FlightPlan getFlightPlan() {
        return flightPlan;
    }

    public void setFlightPlan(FlightPlan flightPlan) {
        this.flightPlan = flightPlan;
    }

    @Length(min = 1, max = 100, message = "航班公司代码长度必须介于 1 和 100 之间")
    public String getFlightCompanyCode() {
        return flightCompanyCode;
    }

    public void setFlightCompanyCode(String flightCompanyCode) {
        this.flightCompanyCode = flightCompanyCode;
    }

    @Length(min = 0, max = 200, message = "航班公司名称长度必须介于 0 和 200 之间")
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

    @Length(min = 0, max = 100, message = "飞机号长度必须介于 0 和 100 之间")
    public String getAircraftNum() {
        return aircraftNum;
    }

    public void setAircraftNum(String aircraftNum) {
        this.aircraftNum = aircraftNum;
    }

    //@Length(min = 0, max = 100, message = "机型ID长度必须介于 0 和 100 之间")
    public AircraftParameters getAircraftParameters() {
        return aircraftParameters;
    }

    public void setAircraftParameters(AircraftParameters aircraftParameters) {
        this.aircraftParameters = aircraftParameters;

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

    //@Length(min=0, max=100, message="航班性质ID长度必须介于 0 和 100 之间")
    public FlightNature getFlightNature() {
        return flightNature;
    }

    public void setFlightNature(FlightNature flightNature) {
        this.flightNature = flightNature;
    }


    @Length(min = 0, max = 100, message = "航班性质编号长度必须介于 0 和 100 之间")
    public String getFlightNatureCode() {
        return flightNatureCode;
    }

    public void setFlightNatureCode(String flightNatureCode) {
        this.flightNatureCode = flightNatureCode;
    }

    @Length(min = 0, max = 200, message = "航班性质名称长度必须介于 0 和 200 之间")
    public String getFlightNatureName() {
        return flightNatureName;
    }

    public void setFlightNatureName(String flightNatureName) {
        this.flightNatureName = flightNatureName;
    }

    //@Length(min=0, max=100, message="航班属性ID长度必须介于 0 和 100 之间")
    public FlightProperties getFlightProperties() {
        return flightProperties;
    }

    public void setFlightProperties(FlightProperties flightProperties) {
        this.flightProperties = flightProperties;
    }


    @Length(min = 0, max = 100, message = "航班属性编号长度必须介于 0 和 100 之间")
    public String getFlightAttrCode() {
        return flightAttrCode;
    }

    public void setFlightAttrCode(String flightAttrCode) {
        this.flightAttrCode = flightAttrCode;
    }

    @Length(min = 0, max = 200, message = "航班属性名称长度必须介于 0 和 200 之间")
    public String getFlightAttrName() {
        return flightAttrName;
    }

    public void setFlightAttrName(String flightAttrName) {
        this.flightAttrName = flightAttrName;
    }

    //@Length(min=0, max=100, message="起飞地ID长度必须介于 0 和 100 之间")
    public AmsAddress getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(AmsAddress departureAirport) {
        this.departureAirport = departureAirport;
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

    //@Length(min = 0, max = 100, message = "经停地1ID长度必须介于 0 和 100 之间")
    public AmsAddress getPassAirport1() {
        return passAirport1;
    }

    public void setPassAirport1(AmsAddress passAirport1) {
        this.passAirport1 = passAirport1;
    }


    @Length(min = 0, max = 100, message = "经停地1编号长度必须介于 0 和 100 之间")
    public String getPassAirport1Code() {
        return passAirport1Code;
    }

    public void setPassAirport1Code(String passAirport1Code) {
        this.passAirport1Code = passAirport1Code;
    }

    @Length(min = 0, max = 200, message = "经停地1名称长度必须介于 0 和 200 之间")
    public String getPassAirport1Name() {
        return passAirport1Name;
    }

    public void setPassAirport1Name(String passAirport1Name) {
        this.passAirport1Name = passAirport1Name;
    }

    //@Length(min=0, max=100, message="经停地2ID长度必须介于 0 和 100 之间")
    public AmsAddress getPassAirport2() {
        return passAirport2;
    }

    public void setPassAirport2(AmsAddress passAirport2) {
        this.passAirport2 = passAirport2;
    }


    @Length(min = 0, max = 100, message = "经停地2编号长度必须介于 0 和 100 之间")
    public String getPassAirport2Code() {
        return passAirport2Code;
    }

    public void setPassAirport2Code(String passAirport2Code) {
        this.passAirport2Code = passAirport2Code;
    }

    @Length(min = 0, max = 200, message = "经停地2名称长度必须介于 0 和 200 之间")
    public String getPassAirport2Name() {
        return passAirport2Name;
    }

    public void setPassAirport2Name(String passAirport2Name) {
        this.passAirport2Name = passAirport2Name;
    }

    //@Length(min=0, max=100, message="到达地ID长度必须介于 0 和 100 之间")
    public AmsAddress getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(AmsAddress arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
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

    //@Length(min=0, max=10, message="代理人ID长度必须介于 0 和 10 之间")
    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }


    @Length(min = 0, max = 100, message = "代理人编码长度必须介于 0 和 100 之间")
    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    @Length(min = 0, max = 200, message = "代理人名称长度必须介于 0 和 200 之间")
    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    //@Length(min = 0, max = 100, message = "航站楼ID长度必须介于 0 和 100 之间")
    public AirportTerminal getAirportTerminal() {
        return airportTerminal;
    }

    public void setAirportTerminal(AirportTerminal airportTerminal) {
        this.airportTerminal = airportTerminal;
    }

    @Length(min = 0, max = 200, message = "航站楼长度必须介于 0 和 200 之间")
    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public FlightCompanyInfo getFlightCompanyInfo() {
        return flightCompanyInfo;
    }

    public void setFlightCompanyInfo(FlightCompanyInfo flightCompanyInfo) {
        this.flightCompanyInfo = flightCompanyInfo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }
}