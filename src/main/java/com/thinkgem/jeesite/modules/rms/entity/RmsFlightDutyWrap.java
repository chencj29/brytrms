/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 值机数据管理通用类Entity
 *
 * @author dingshuang
 * @version 2016-05-18
 */
public class RmsFlightDutyWrap extends DataEntity<RmsFlightDutyWrap> {
    private static final long serialVersionUID = 1L;
    //计划日期
    private Date planDate;
    //航空公司号码
    private String flightCompanyCode;
    //航班号
    private String flightNum;
    //飞机号码
    private String aircraftNum;
    //旅客总数
    private Integer travellersAllCount = 0;
    //子公司名称
    private String flightCompanySubInfoCode;
    //子公司代码
    private String flightCompanySubInfoName;
    //成人数
    private Integer personCount = 0;
    //vip人数
    private Integer vipPersonCount = 0;
    //儿童数
    private Integer childCount = 0;
    //婴儿数
    private Integer babyCount = 0;
    //无陪伴儿童人数
    private Integer babyAloneCount = 0;
    //直达行李数量
    private Integer nonstopLuggageCount = 0;
    //直达行李重量
    private Double nonstopLuggageWeight = 0.0;
    //转运货物数量
    private Integer transferLuggageCount = 0;
    //转运货物重量
    private Double transferLuggageWeight = 0.0;

    //-----------------------------导出用属性  begin
    // 头等舱座位 导出用
    private String firstCabinSeat;
    //结载总行李 导出用
    private Integer allLuggageCount = 0;
    //头等舱人数  导出用
    private Integer firstCabinPersonCount = 0;
    //vip行李件数  导出用
    private Integer vipLuggageCount = 0;
    //公务舱人数   导出用
    private Integer businessCabinPersonCount = 0;
    //公务舱座位 导出用
    private String businessCabinSeat;

    //航空公司 导出用
    private String flightCompanyName;
    //经停地1编号 导出用
    private String passAirport1Code;
    //经停地1名称 导出用
    private String passAirport1Name;
    //经停地2编号 导出用
    private String passAirport2Code;
    //经停地2名称 导出用
    private String passAirport2Name;
    //经停地3编号 导出用
    private String ext11;
    //经停地3名称 导出用
    private String ext12;
    //机型编号 导出用
    private String aircraftTypeCode;
    //目的地编号 导出用
    private String arrivalAirportCode;
    //计划起飞 导出用
    private Date departurePlanTime;
    //-----------------------------导出用属性  end

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getFlightCompanyCode() {
        return flightCompanyCode;
    }

    public void setFlightCompanyCode(String flightCompanyCode) {
        this.flightCompanyCode = flightCompanyCode;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public String getAircraftNum() {
        return aircraftNum;
    }

    public void setAircraftNum(String aircraftNum) {
        this.aircraftNum = aircraftNum;
    }

    public Integer getTravellersAllCount() {
        return travellersAllCount;
    }

    public void setTravellersAllCount(Integer travellersAllCount) {
        this.travellersAllCount = travellersAllCount;
    }

    public String getFlightCompanySubInfoCode() {
        return flightCompanySubInfoCode;
    }

    public void setFlightCompanySubInfoCode(String flightCompanySubInfoCode) {
        this.flightCompanySubInfoCode = flightCompanySubInfoCode;
    }

    public String getFlightCompanySubInfoName() {
        return flightCompanySubInfoName;
    }

    public void setFlightCompanySubInfoName(String flightCompanySubInfoName) {
        this.flightCompanySubInfoName = flightCompanySubInfoName;
    }

    public Integer getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Integer personCount) {
        this.personCount = personCount;
    }

    public Integer getVipPersonCount() {
        return vipPersonCount;
    }

    public void setVipPersonCount(Integer vipPersonCount) {
        this.vipPersonCount = vipPersonCount;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    public Integer getBabyCount() {
        return babyCount;
    }

    public void setBabyCount(Integer babyCount) {
        this.babyCount = babyCount;
    }

    public Integer getBabyAloneCount() {
        return babyAloneCount;
    }

    public void setBabyAloneCount(Integer babyAloneCount) {
        this.babyAloneCount = babyAloneCount;
    }

    public Integer getNonstopLuggageCount() {
        return nonstopLuggageCount;
    }

    public void setNonstopLuggageCount(Integer nonstopLuggageCount) {
        this.nonstopLuggageCount = nonstopLuggageCount;
    }

    public Double getNonstopLuggageWeight() {
        return nonstopLuggageWeight;
    }

    public void setNonstopLuggageWeight(Double nonstopLuggageWeight) {
        this.nonstopLuggageWeight = nonstopLuggageWeight;
    }

    public Integer getTransferLuggageCount() {
        return transferLuggageCount;
    }

    public void setTransferLuggageCount(Integer transferLuggageCount) {
        this.transferLuggageCount = transferLuggageCount;
    }

    public Double getTransferLuggageWeight() {
        return transferLuggageWeight;
    }

    public void setTransferLuggageWeight(Double transferLuggageWeight) {
        this.transferLuggageWeight = transferLuggageWeight;
    }

    public Integer getAllLuggageCount() {
        return allLuggageCount;
    }

    public void setAllLuggageCount(Integer allLuggageCount) {
        this.allLuggageCount = allLuggageCount;
    }

    public String getFlightCompanyName() {
        return flightCompanyName;
    }

    public void setFlightCompanyName(String flightCompanyName) {
        this.flightCompanyName = flightCompanyName;
    }

    public String getPassAirport1Code() {
        return passAirport1Code;
    }

    public void setPassAirport1Code(String passAirport1Code) {
        this.passAirport1Code = passAirport1Code;
    }

    public String getPassAirport2Code() {
        return passAirport2Code;
    }

    public void setPassAirport2Code(String passAirport2Code) {
        this.passAirport2Code = passAirport2Code;
    }

    public String getAircraftTypeCode() {
        return aircraftTypeCode;
    }

    public void setAircraftTypeCode(String aircraftTypeCode) {
        this.aircraftTypeCode = aircraftTypeCode;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public Integer getFirstCabinPersonCount() {
        return firstCabinPersonCount;
    }

    public void setFirstCabinPersonCount(Integer firstCabinPersonCount) {
        this.firstCabinPersonCount = firstCabinPersonCount;
    }

    public Date getDeparturePlanTime() {
        return departurePlanTime;
    }

    public void setDeparturePlanTime(Date departurePlanTime) {
        this.departurePlanTime = departurePlanTime;
    }

    public Integer getVipLuggageCount() {
        return vipLuggageCount;
    }

    public void setVipLuggageCount(Integer vipLuggageCount) {
        this.vipLuggageCount = vipLuggageCount;
    }

    public String getPassAirport1Name() {
        return passAirport1Name;
    }

    public void setPassAirport1Name(String passAirport1Name) {
        this.passAirport1Name = passAirport1Name;
    }

    public String getPassAirport2Name() {
        return passAirport2Name;
    }

    public void setPassAirport2Name(String passAirport2Name) {
        this.passAirport2Name = passAirport2Name;
    }

    public String getExt11() {
        return ext11;
    }

    public void setExt11(String ext11) {
        this.ext11 = ext11;
    }

    public String getExt12() {
        return ext12;
    }

    public void setExt12(String ext12) {
        this.ext12 = ext12;
    }

    public String getFirstCabinSeat() {
        return firstCabinSeat;
    }

    public void setFirstCabinSeat(String firstCabinSeat) {
        this.firstCabinSeat = firstCabinSeat;
    }

    public Integer getBusinessCabinPersonCount() {
        return businessCabinPersonCount;
    }

    public void setBusinessCabinPersonCount(Integer businessCabinPersonCount) {
        this.businessCabinPersonCount = businessCabinPersonCount;
    }

    public String getBusinessCabinSeat() {
        return businessCabinSeat;
    }

    public void setBusinessCabinSeat(String businessCabinSeat) {
        this.businessCabinSeat = businessCabinSeat;
    }
}