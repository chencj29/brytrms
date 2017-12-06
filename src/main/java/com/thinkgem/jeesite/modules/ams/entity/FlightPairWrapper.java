package com.thinkgem.jeesite.modules.ams.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xiaopo on 16/1/26.
 */
public class FlightPairWrapper extends DataEntity<FlightPairWrapper> implements Serializable {
	private static final long serialVersionUID = -1467040049431647662L;
	private String id;
	private String remark;
	private Date planDate;
	private String agentCode;
	private String agentName;
	private String aircraftNum;
	private String aircraftTypeCode;
	private String placeNum;
	private String inOutTypeName;
	private String flightNum;
	private String shareFlightNum;
	private String flightNatureName;
	private String planDetailId;
	private String departureAirportCode;
	private String departureAirportName;
	private String passAirport1Code;
	private String passAirport1Name;
	private String passAirport2Code;
	private String passAirport2Name;
	private Date departurePlanTime;
	private Date arrivalPlanTime;
	private Date etd;
	private Date eta;
	private Date atd;
	private Date ata;
	private String carouselNum;
	private String VIP;
	private String delayResonsName;
	private String delayResonsRemark;
	private String status;
	private String statusName;

	private String alterNateAreaCode;  // 备降地三字码
	private String alterNateAreaName;  // 备降地名称
	private String alternateRemarks; // 备降原因

	private String cancelFlightResons; // 航班取消原因

	private String safeguardProcessCode;     // 保障过程编号

	private String safeguardProcessName;    // 保障过程名称

	private String planDetailId2;
	private String flightNatureName2;
	private String inOutTypeName2;
	private String flightNum2;
	private String shareFlightNum2;
	private String arrivalAirportCode;
	private String arrivalAirportName;
	private String passAirport1Code2;
	private String passAirport1Name2;
	private String passAirport2Code2;
	private String passAirport2Name2;
	private String VIP2;
	private String gateName;
	private Date boardingStartTime;
	private Date boardingEndTime;
	private Date departurePlanTime2;
	private Date etd2;
	private Date eta2;
	private Date atd2;
	private Date ata2;
	private String checkinIslandCode;
	private String checkinIslandName;
	private String checkinCounterCode;
	private String checkinCounterName;
	private Date checkinStartTime;
	private Date checkinEndTime;
	private String delayResonsName2;
	private String delayResonsRemark2;
	private String status2;
	private String statusName2;

	private String safecurdYypeName;

	private String safecurdTypeName;

	private String cancelFlightResons2; // 航班取消原因

	private String safeguardProcessCode2;     // 保障过程编号

	private String safeguardProcessName2;    // 保障过程名称

	private String delayStatus; //航班延误状态
	private String cancelFlightStatus; //航班取消状态
	private String ext1; //扩展1
	private String ext2; //扩展2
	private String ext3; //扩展3
	private String ext4; //扩展4
	private String ext5; //扩展5
	private String ext6; //扩展6
	private String ext7; //扩展7
	private String ext8; //扩展8
	private String ext9; //扩展9
	private String ext10; //扩展10
	private String delayStatus2; //航班延误状态
	private String cancelFlightStatus2; //航班取消状态


	public FlightPairWrapper(){
	}


	public FlightPairWrapper(String id){
		this.id = id;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String setRemark() {
		return remark;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public String getAircraftNum() {
		return aircraftNum;
	}

	public void setAircraftNum(String aircraftNum) {
		this.aircraftNum = aircraftNum;
	}

	public String getAircraftTypeCode() {
		return aircraftTypeCode;
	}

	public void setAircraftTypeCode(String aircraftTypeCode) {
		this.aircraftTypeCode = aircraftTypeCode;
	}

	public String getPlaceNum() {
		return placeNum;
	}

	public void setPlaceNum(String placeNum) {
		this.placeNum = placeNum;
	}

	public String getInOutTypeName() {
		return inOutTypeName;
	}

	public void setInOutTypeName(String inOutTypeName) {
		this.inOutTypeName = inOutTypeName;
	}

	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	public String getFlightNatureName() {
		return flightNatureName;
	}

	public void setFlightNatureName(String flightNatureName) {
		this.flightNatureName = flightNatureName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
	public Date getArrivalPlanTime() {
		return arrivalPlanTime;
	}

	public void setArrivalPlanTime(Date arrivalPlanTime) {
		this.arrivalPlanTime = arrivalPlanTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
	public Date getEta() {
		return eta;
	}


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
	public Date getEta2() {
		return eta2;
	}

	public void setEta(Date eta) {
		this.eta = eta;
	}
	public void setEta2(Date eta2) {
		this.eta2 = eta2;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
	public Date getAta() {
		return ata;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
	public Date getAta2() {
		return ata2;
	}

	public void setAta(Date ata) {
		this.ata = ata;
	}
	public void setAta2(Date ata2) {
		this.ata2 = ata2;
	}

	public String getInOutTypeName2() {
		return inOutTypeName2;
	}

	public void setInOutTypeName2(String inOutTypeName2) {
		this.inOutTypeName2 = inOutTypeName2;
	}

	public String getFlightNum2() {
		return flightNum2;
	}

	public void setFlightNum2(String flightNum2) {
		this.flightNum2 = flightNum2;
	}

	public String getFlightNatureName2() {
		return flightNatureName2;
	}

	public void setFlightNatureName2(String flightNatureName2) {
		this.flightNatureName2 = flightNatureName2;
	}


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
	public Date getEtd() {
		return etd;
	}


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
	public Date getEtd2() {
		return etd2;
	}


	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getShareFlightNum() {
		return shareFlightNum;
	}

	public void setShareFlightNum(String shareFlightNum) {
		this.shareFlightNum = shareFlightNum;
	}

	public String getDepartureAirportCode() {
		return departureAirportCode;
	}

	public void setDepartureAirportCode(String departureAirportCode) {
		this.departureAirportCode = departureAirportCode;
	}

	public String getDepartureAirportName() {
		return departureAirportName;
	}

	public void setDepartureAirportName(String departureAirportName) {
		this.departureAirportName = departureAirportName;
	}

	public String getPassAirport1Code() {
		return passAirport1Code;
	}

	public void setPassAirport1Code(String passAirport1Code) {
		this.passAirport1Code = passAirport1Code;
	}

	public String getPassAirport1Name() {
		return passAirport1Name;
	}

	public void setPassAirport1Name(String passAirport1Name) {
		this.passAirport1Name = passAirport1Name;
	}

	public String getPassAirport2Code() {
		return passAirport2Code;
	}

	public void setPassAirport2Code(String passAirport2Code) {
		this.passAirport2Code = passAirport2Code;
	}

	public String getPassAirport2Name() {
		return passAirport2Name;
	}

	public void setPassAirport2Name(String passAirport2Name) {
		this.passAirport2Name = passAirport2Name;
	}

	public Date getDeparturePlanTime() {
		return departurePlanTime;
	}

	public void setDeparturePlanTime(Date departurePlanTime) {
		this.departurePlanTime = departurePlanTime;
	}

	public String getCarouselNum() {
		return carouselNum;
	}

	public void setCarouselNum(String carouselNum) {
		this.carouselNum = carouselNum;
	}

	public String getDelayResonsName() {
		return delayResonsName;
	}

	public void setDelayResonsName(String delayResonsName) {
		this.delayResonsName = delayResonsName;
	}

	public String getDelayResonsRemark() {
		return delayResonsRemark;
	}

	public void setDelayResonsRemark(String delayResonsRemark) {
		this.delayResonsRemark = delayResonsRemark;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public Date getAtd() {
		return atd;
	}

	public void setAtd(Date atd) {
		this.atd = atd;
	}

	public String getVIP() {
		return VIP;
	}

	public void setVIP(String VIP) {
		this.VIP = VIP;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}


	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusName() {
		return statusName;
	}


	public String getShareFlightNum2() {
		return shareFlightNum2;
	}

	public void setShareFlightNum2(String shareFlightNum2) {
		this.shareFlightNum2 = shareFlightNum2;
	}

	public String getArrivalAirportCode() {
		return arrivalAirportCode;
	}

	public void setArrivalAirportCode(String arrivalAirportCode) {
		this.arrivalAirportCode = arrivalAirportCode;
	}

	public String getArrivalAirportName() {
		return arrivalAirportName;
	}

	public void setArrivalAirportName(String arrivalAirportName) {
		this.arrivalAirportName = arrivalAirportName;
	}

	public String getPassAirport1Code2() {
		return passAirport1Code2;
	}

	public void setPassAirport1Code2(String passAirport1Code2) {
		this.passAirport1Code2 = passAirport1Code2;
	}

	public String getPassAirport1Name2() {
		return passAirport1Name2;
	}

	public void setPassAirport1Name2(String passAirport1Name2) {
		this.passAirport1Name2 = passAirport1Name2;
	}

	public String getPassAirport2Code2() {
		return passAirport2Code2;
	}

	public void setPassAirport2Code2(String passAirport2Code2) {
		this.passAirport2Code2 = passAirport2Code2;
	}

	public String getPassAirport2Name2() {
		return passAirport2Name2;
	}

	public void setPassAirport2Name2(String passAirport2Name2) {
		this.passAirport2Name2 = passAirport2Name2;
	}

	public String getVIP2() {
		return VIP2;
	}

	public void setVIP2(String VIP2) {
		this.VIP2 = VIP2;
	}

	public String getGateName() {
		return gateName;
	}

	public void setGateName(String gateName) {
		this.gateName = gateName;
	}

	public Date getBoardingStartTime() {
		return boardingStartTime;
	}

	public void setBoardingStartTime(Date boardingStartTime) {
		this.boardingStartTime = boardingStartTime;
	}

	public Date getBoardingEndTime() {
		return boardingEndTime;
	}

	public void setBoardingEndTime(Date boardingEndTime) {
		this.boardingEndTime = boardingEndTime;
	}

	public Date getDeparturePlanTime2() {
		return departurePlanTime2;
	}

	public void setDeparturePlanTime2(Date departurePlanTime2) {
		this.departurePlanTime2 = departurePlanTime2;
	}

	public void setEtd(Date etd) {
		this.etd = etd;
	}

	public void setEtd2(Date etd2) {
		this.etd2 = etd2;
	}

	public Date getAtd2() {
		return atd2;
	}

	public void setAtd2(Date atd2) {
		this.atd2 = atd2;
	}

	public String getCheckinIslandCode() {
		return checkinIslandCode;
	}

	public void setCheckinIslandCode(String checkinIslandCode) {
		this.checkinIslandCode = checkinIslandCode;
	}

	public String getCheckinIslandName() {
		return checkinIslandName;
	}

	public void setCheckinIslandName(String checkinIslandName) {
		this.checkinIslandName = checkinIslandName;
	}

	public String getCheckinCounterCode() {
		return checkinCounterCode;
	}

	public void setCheckinCounterCode(String checkinCounterCode) {
		this.checkinCounterCode = checkinCounterCode;
	}

	public String getCheckinCounterName() {
		return checkinCounterName;
	}

	public void setCheckinCounterName(String checkinCounterName) {
		this.checkinCounterName = checkinCounterName;
	}

	public Date getCheckinStartTime() {
		return checkinStartTime;
	}

	public void setCheckinStartTime(Date checkinStartTime) {
		this.checkinStartTime = checkinStartTime;
	}

	public Date getCheckinEndTime() {
		return checkinEndTime;
	}

	public void setCheckinEndTime(Date checkinEndTime) {
		this.checkinEndTime = checkinEndTime;
	}

	public String getDelayResonsName2() {
		return delayResonsName2;
	}

	public void setDelayResonsName2(String delayResonsName2) {
		this.delayResonsName2 = delayResonsName2;
	}

	public String getDelayResonsRemark2() {
		return delayResonsRemark2;
	}

	public void setDelayResonsRemark2(String delayResonsRemark2) {
		this.delayResonsRemark2 = delayResonsRemark2;
	}


	public void setStatus2(String status2) {
		this.status2 = status2;
	}

	public String getStatus2() {
		return status2;
	}


	public void setStatusName2(String statusName2) {
		this.statusName2 = statusName2;
	}

	public String getStatusName2() {
		return statusName2;
	}


	public String getPlanDetailId() {
		return planDetailId;
	}

	public void setPlanDetailId(String planDetailId) {
		this.planDetailId = planDetailId;
	}


	public String getPlanDetailId2() {
		return planDetailId2;
	}

	public void setPlanDetailId2(String planDetailId2) {
		this.planDetailId2 = planDetailId2;
	}


	public String getSafecurdYypeName(){
		return safecurdYypeName;
	}

	public void setSafecurdYypeName(String safecurdYypeName){
		this.safecurdYypeName=safecurdYypeName;
	}



	private void setSafecurdTypeName(){
		this.safecurdTypeName = safecurdTypeName;
	}

	public String getRemark() {
		return remark;
	}

	public String getAlterNateAreaCode() {
		return alterNateAreaCode;
	}

	public void setAlterNateAreaCode(String alterNateAreaCode) {
		this.alterNateAreaCode = alterNateAreaCode;
	}

	public String getAlterNateAreaName() {
		return alterNateAreaName;
	}

	public void setAlterNateAreaName(String alterNateAreaName) {
		this.alterNateAreaName = alterNateAreaName;
	}


	public String getSafecurdTypeName() {
		return safecurdTypeName;
	}

	public void setSafecurdTypeName(String safecurdTypeName) {
		this.safecurdTypeName = safecurdTypeName;
	}


	public String getAlternateRemarks() {
		return alternateRemarks;
	}

	public void setAlternateRemarks(String alternateRemarks) {
		this.alternateRemarks = alternateRemarks;
	}

	public String getCancelFlightResons() {
		return cancelFlightResons;
	}

	public void setCancelFlightResons(String cancelFlightResons) {
		this.cancelFlightResons = cancelFlightResons;
	}

	public String getCancelFlightResons2() {
		return cancelFlightResons2;
	}

	public void setCancelFlightResons2(String cancelFlightResons2) {
		this.cancelFlightResons2 = cancelFlightResons2;
	}

	public String getSafeguardProcessCode() {
		return safeguardProcessCode;
	}

	public void setSafeguardProcessCode(String safeguardProcessCode) {
		this.safeguardProcessCode = safeguardProcessCode;
	}

	public String getSafeguardProcessName() {
		return safeguardProcessName;
	}

	public void setSafeguardProcessName(String safeguardProcessName) {
		this.safeguardProcessName = safeguardProcessName;
	}

	public String getSafeguardProcessCode2() {
		return safeguardProcessCode2;
	}

	public void setSafeguardProcessCode2(String safeguardProcessCode2) {
		this.safeguardProcessCode2 = safeguardProcessCode2;
	}

	public String getSafeguardProcessName2() {
		return safeguardProcessName2;
	}

	public void setSafeguardProcessName2(String safeguardProcessName2) {
		this.safeguardProcessName2 = safeguardProcessName2;
	}

	public String getDelayStatus() {
		return delayStatus;
	}

	public void setDelayStatus(String delayStatus) {
		this.delayStatus = delayStatus;
	}

	public String getCancelFlightStatus() {
		return cancelFlightStatus;
	}

	public void setCancelFlightStatus(String cancelFlightStatus) {
		this.cancelFlightStatus = cancelFlightStatus;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public String getExt4() {
		return ext4;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	public String getExt5() {
		return ext5;
	}

	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}

	public String getExt6() {
		return ext6;
	}

	public void setExt6(String ext6) {
		this.ext6 = ext6;
	}

	public String getExt7() {
		return ext7;
	}

	public void setExt7(String ext7) {
		this.ext7 = ext7;
	}

	public String getExt8() {
		return ext8;
	}

	public void setExt8(String ext8) {
		this.ext8 = ext8;
	}

	public String getExt9() {
		return ext9;
	}

	public void setExt9(String ext9) {
		this.ext9 = ext9;
	}

	public String getExt10() {
		return ext10;
	}

	public void setExt10(String ext10) {
		this.ext10 = ext10;
	}

	public String getDelayStatus2() {
		return delayStatus2;
	}

	public void setDelayStatus2(String delayStatus2) {
		this.delayStatus2 = delayStatus2;
	}

	public String getCancelFlightStatus2() {
		return cancelFlightStatus2;
	}

	public void setCancelFlightStatus2(String cancelFlightStatus2) {
		this.cancelFlightStatus2 = cancelFlightStatus2;
	}
}
