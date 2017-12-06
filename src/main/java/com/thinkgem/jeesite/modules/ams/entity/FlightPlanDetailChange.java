/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 航班计划变更Entity
 * @author chrischen
 * @version 2016-01-21
 */
public class FlightPlanDetailChange extends DataEntity<FlightPlanDetailChange> {
	
	private static final long serialVersionUID = 1L;
	private String baseId;   // 原航班计划ID
	private String flightPlanId;		// 航班计划ID
	private String flightCompanyCode;		// 航班公司代码
	private String flightCompanyName;		// 航班公司名称
	private String flightNum;		// 航班号
	private String aircraftNum;		// 飞机号
	private String aircraftParametersId;		// 机型ID
	private String aircraftTypeCode;		// 机型编号
	private String aircraftTypeName;		// 机型名称
	private String flightNatureId;		// 航班性质ID
	private String flightNatureCode;		// 航班性质编号
	private String flightNatureName;		// 航班性质名称
	private String flightPropertiesId;		// 航班属性ID
	private String flightAttrCode;		// 航班属性编号
	private String flightAttrName;		// 航班属性名称
	private String departureAirportId;		// 起飞地ID
	private String departureAirportCode;		// 起飞地编号
	private String departureAirportName;		// 起飞地名称
	private String passAirport1Id;		// 经停地1ID
	private String passAirport1Code;		// 经停地1编号
	private String passAirport1Name;		// 经停地1名称
	private String passAirport2Id;		// 经停地2ID
	private String passAirport2Code;		// 经停地2编号
	private String passAirport2Name;		// 经停地2名称
	private String arrivalAirportId;		// 到达地ID
	private String arrivalAirportCode;		// 到达地编号
	private String arrivalAirportName;		// 到达地名称
	private Date departurePlanTime;		// 计划起飞时间
	private Date arrivalPlanTime;		// 计划到达时间
	private String inoutTypeCode;		// 进出港类型编码
	private String inoutTypeName;		// 进出港类型名称
	private String agentId;		// 代理人ID
	private String agentCode;		// 代理人编码
	private String agentName;		// 代理人名称
	private String terminalId;		// 航站楼ID
	private String terminal;		// 航站楼
	private String flightCompanyId;		// flight_company_id
	private Integer status;		// 0 待审核 1 已审核 2 已发布 3已初始化
	private String changeType;		// 修改类型

	private String applyUserId; // 变更申请人ID
	private String applyUserName;  // 变更人名称
	private Date applyTime; // 变更申请时间

	private String approveUserId; // 审核人ID
	private String approveUserName; // 审核人名称
	private Date approveTime; // 审核时间

	private String approveStatus; // 审核状态 0：拒绝；1：通过

	private Date planDate;
	
	public FlightPlanDetailChange() {
		super();
	}

	public FlightPlanDetailChange(String id){
		super(id);
	}

	@Length(min=0, max=36, message="航班计划ID长度必须介于 0 和 36 之间")
	public String getFlightPlanId() {
		return flightPlanId;
	}

	public void setFlightPlanId(String flightPlanId) {
		this.flightPlanId = flightPlanId;
	}
	
	@Length(min=0, max=100, message="航班公司代码长度必须介于 0 和 100 之间")
	public String getFlightCompanyCode() {
		return flightCompanyCode;
	}

	public void setFlightCompanyCode(String flightCompanyCode) {
		this.flightCompanyCode = flightCompanyCode;
	}
	
	@Length(min=0, max=200, message="航班公司名称长度必须介于 0 和 200 之间")
	public String getFlightCompanyName() {
		return flightCompanyName;
	}

	public void setFlightCompanyName(String flightCompanyName) {
		this.flightCompanyName = flightCompanyName;
	}
	
	@Length(min=0, max=100, message="航班号长度必须介于 0 和 100 之间")
	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}
	
	@Length(min=0, max=100, message="飞机号长度必须介于 0 和 100 之间")
	public String getAircraftNum() {
		return aircraftNum;
	}

	public void setAircraftNum(String aircraftNum) {
		this.aircraftNum = aircraftNum;
	}
	
	@Length(min=0, max=100, message="机型ID长度必须介于 0 和 100 之间")
	public String getAircraftParametersId() {
		return aircraftParametersId;
	}

	public void setAircraftParametersId(String aircraftParametersId) {
		this.aircraftParametersId = aircraftParametersId;
	}
	
	@Length(min=0, max=100, message="机型编号长度必须介于 0 和 100 之间")
	public String getAircraftTypeCode() {
		return aircraftTypeCode;
	}

	public void setAircraftTypeCode(String aircraftTypeCode) {
		this.aircraftTypeCode = aircraftTypeCode;
	}
	
	@Length(min=0, max=200, message="机型名称长度必须介于 0 和 200 之间")
	public String getAircraftTypeName() {
		return aircraftTypeName;
	}

	public void setAircraftTypeName(String aircraftTypeName) {
		this.aircraftTypeName = aircraftTypeName;
	}
	
	@Length(min=0, max=100, message="航班性质ID长度必须介于 0 和 100 之间")
	public String getFlightNatureId() {
		return flightNatureId;
	}

	public void setFlightNatureId(String flightNatureId) {
		this.flightNatureId = flightNatureId;
	}
	
	@Length(min=0, max=100, message="航班性质编号长度必须介于 0 和 100 之间")
	public String getFlightNatureCode() {
		return flightNatureCode;
	}

	public void setFlightNatureCode(String flightNatureCode) {
		this.flightNatureCode = flightNatureCode;
	}
	
	@Length(min=0, max=200, message="航班性质名称长度必须介于 0 和 200 之间")
	public String getFlightNatureName() {
		return flightNatureName;
	}

	public void setFlightNatureName(String flightNatureName) {
		this.flightNatureName = flightNatureName;
	}
	
	@Length(min=0, max=100, message="航班属性ID长度必须介于 0 和 100 之间")
	public String getFlightPropertiesId() {
		return flightPropertiesId;
	}

	public void setFlightPropertiesId(String flightPropertiesId) {
		this.flightPropertiesId = flightPropertiesId;
	}
	
	@Length(min=0, max=100, message="航班属性编号长度必须介于 0 和 100 之间")
	public String getFlightAttrCode() {
		return flightAttrCode;
	}

	public void setFlightAttrCode(String flightAttrCode) {
		this.flightAttrCode = flightAttrCode;
	}
	
	@Length(min=0, max=200, message="航班属性名称长度必须介于 0 和 200 之间")
	public String getFlightAttrName() {
		return flightAttrName;
	}

	public void setFlightAttrName(String flightAttrName) {
		this.flightAttrName = flightAttrName;
	}
	
	@Length(min=0, max=100, message="起飞地ID长度必须介于 0 和 100 之间")
	public String getDepartureAirportId() {
		return departureAirportId;
	}

	public void setDepartureAirportId(String departureAirportId) {
		this.departureAirportId = departureAirportId;
	}
	
	@Length(min=0, max=100, message="起飞地编号长度必须介于 0 和 100 之间")
	public String getDepartureAirportCode() {
		return departureAirportCode;
	}

	public void setDepartureAirportCode(String departureAirportCode) {
		this.departureAirportCode = departureAirportCode;
	}
	
	@Length(min=0, max=200, message="起飞地名称长度必须介于 0 和 200 之间")
	public String getDepartureAirportName() {
		return departureAirportName;
	}

	public void setDepartureAirportName(String departureAirportName) {
		this.departureAirportName = departureAirportName;
	}
	
	@Length(min=0, max=100, message="经停地1ID长度必须介于 0 和 100 之间")
	public String getPassAirport1Id() {
		return passAirport1Id;
	}

	public void setPassAirport1Id(String passAirport1Id) {
		this.passAirport1Id = passAirport1Id;
	}
	
	@Length(min=0, max=100, message="经停地1编号长度必须介于 0 和 100 之间")
	public String getPassAirport1Code() {
		return passAirport1Code;
	}

	public void setPassAirport1Code(String passAirport1Code) {
		this.passAirport1Code = passAirport1Code;
	}
	
	@Length(min=0, max=200, message="经停地1名称长度必须介于 0 和 200 之间")
	public String getPassAirport1Name() {
		return passAirport1Name;
	}

	public void setPassAirport1Name(String passAirport1Name) {
		this.passAirport1Name = passAirport1Name;
	}
	
	@Length(min=0, max=100, message="经停地2ID长度必须介于 0 和 100 之间")
	public String getPassAirport2Id() {
		return passAirport2Id;
	}

	public void setPassAirport2Id(String passAirport2Id) {
		this.passAirport2Id = passAirport2Id;
	}
	
	@Length(min=0, max=100, message="经停地2编号长度必须介于 0 和 100 之间")
	public String getPassAirport2Code() {
		return passAirport2Code;
	}

	public void setPassAirport2Code(String passAirport2Code) {
		this.passAirport2Code = passAirport2Code;
	}
	
	@Length(min=0, max=200, message="经停地2名称长度必须介于 0 和 200 之间")
	public String getPassAirport2Name() {
		return passAirport2Name;
	}

	public void setPassAirport2Name(String passAirport2Name) {
		this.passAirport2Name = passAirport2Name;
	}
	
	@Length(min=0, max=100, message="到达地ID长度必须介于 0 和 100 之间")
	public String getArrivalAirportId() {
		return arrivalAirportId;
	}

	public void setArrivalAirportId(String arrivalAirportId) {
		this.arrivalAirportId = arrivalAirportId;
	}
	
	@Length(min=0, max=100, message="到达地编号长度必须介于 0 和 100 之间")
	public String getArrivalAirportCode() {
		return arrivalAirportCode;
	}

	public void setArrivalAirportCode(String arrivalAirportCode) {
		this.arrivalAirportCode = arrivalAirportCode;
	}
	
	@Length(min=0, max=200, message="到达地名称长度必须介于 0 和 200 之间")
	public String getArrivalAirportName() {
		return arrivalAirportName;
	}

	public void setArrivalAirportName(String arrivalAirportName) {
		this.arrivalAirportName = arrivalAirportName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDeparturePlanTime() {
		return departurePlanTime;
	}

	public void setDeparturePlanTime(Date departurePlanTime) {
		this.departurePlanTime = departurePlanTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getArrivalPlanTime() {
		return arrivalPlanTime;
	}

	public void setArrivalPlanTime(Date arrivalPlanTime) {
		this.arrivalPlanTime = arrivalPlanTime;
	}
	
	@Length(min=0, max=100, message="进出港类型编码长度必须介于 0 和 100 之间")
	public String getInoutTypeCode() {
		return inoutTypeCode;
	}

	public void setInoutTypeCode(String inoutTypeCode) {
		this.inoutTypeCode = inoutTypeCode;
	}
	
	@Length(min=0, max=200, message="进出港类型名称长度必须介于 0 和 200 之间")
	public String getInoutTypeName() {
		return inoutTypeName;
	}

	public void setInoutTypeName(String inoutTypeName) {
		this.inoutTypeName = inoutTypeName;
	}
	
	@Length(min=0, max=100, message="代理人ID长度必须介于 0 和 100 之间")
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	@Length(min=0, max=100, message="代理人编码长度必须介于 0 和 100 之间")
	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	
	@Length(min=0, max=200, message="代理人名称长度必须介于 0 和 200 之间")
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	@Length(min=0, max=100, message="航站楼ID长度必须介于 0 和 100 之间")
	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	
	@Length(min=0, max=200, message="航站楼长度必须介于 0 和 200 之间")
	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	
	@Length(min=0, max=100, message="flight_company_id长度必须介于 0 和 100 之间")
	public String getFlightCompanyId() {
		return flightCompanyId;
	}

	public void setFlightCompanyId(String flightCompanyId) {
		this.flightCompanyId = flightCompanyId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Length(min=0, max=1, message="修改类型长度必须介于 0 和 1 之间")
	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getApproveUserId() {
		return approveUserId;
	}

	public void setApproveUserId(String approveUserId) {
		this.approveUserId = approveUserId;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getApproveUserName() {
		return approveUserName;
	}

	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
}