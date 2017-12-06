/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;


import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.drew.lang.StringUtil;
import com.sun.org.apache.xerces.internal.xs.StringList;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.ObjectUtils;
import com.thinkgem.jeesite.modules.rms.service.RmsFlightHomeManifestService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 本站仓单数据管理Entity
 *
 * @author dingshuang
 * @version 2016-05-19
 */

@Monitor(desc = "本站仓单数据管理信息", tableName = "rms_flight_home_manifest", service = RmsFlightHomeManifestService.class, socketNS = "/rms/rmsFlightHomeManifest")
public class RmsFlightHomeManifest extends DataEntity<RmsFlightHomeManifest> {

    private static final Long serialVersionUID = 1L;

    @Label("航班动态ID")
    @MonitorField(desc = "航班动态ID")
    private String flightDynamicId;        // 航班动态ID
    @Label("出港成人数量")
    @MonitorField(desc = "出港成人数量")
    private Integer personCount = 0;        // 出港成人数量
    @Label("出港儿童数量")
    @MonitorField(desc = "出港儿童数量")
    private Integer childCount = 0;        // 出港儿童数量
    @Label("出港婴儿数量")
    @MonitorField(desc = "出港婴儿数量")
    private Integer babyCount = 0;        // 出港婴儿数量
    @Label("出港旅客总数")
    @MonitorField(desc = "出港旅客总数")
    private Integer travellerAllCount = 0;        // 出港旅客总数
    @Label("机组人数")
    @MonitorField(desc = "机组人数")
    private Integer flightCrewCount = 0;        // 机组人数
    @Label("油料重量")
    @MonitorField(desc = "油料重量")
    private Float oilWeight = new Float(0);        // 油料重量
    @Label("出港邮件件数")
    @MonitorField(desc = "出港邮件件数")
    private Integer mailCount = 0;        // 出港邮件件数
    @Label("出港邮件重量")
    @MonitorField(desc = "出港邮件重量")
    private Float mailWeight = new Float(0);        // 出港邮件重量
    @Label("出港货物数量")
    @MonitorField(desc = "出港货物数量")
    private Integer goodsCount = 0;        // 出港货物数量
    @Label("出港货物重量")
    @MonitorField(desc = "出港货物重量")
    private Float goodsWeight = new Float(0);        // 出港货物重量
    @Label("出港行李件数")
    @MonitorField(desc = "出港行李件数")
    private Integer luggageCount = 0;        // 出港行李件数
    @Label("出港行李重量")
    @MonitorField(desc = "出港行李重量")
    private Float luggageWeight = new Float(0);        // 出港行李重量
    @Label("出港VIP人数")
    @MonitorField(desc = "出港VIP人数")
    private Integer vipCount = 0;        // 出港VIP人数
    @Label("头等舱旅客人数")
    @MonitorField(desc = "头等舱旅客人数")
    private Integer firstCabinCount = 0;        // 头等舱旅客人数
    @Label("公务舱旅客人数")
    @MonitorField(desc = "公务舱旅客人数")
    private Integer businessCabinCount = 0;        // 公务舱旅客人数
    @Label("经济舱旅客人数")
    @MonitorField(desc = "经济舱旅客人数")
    private Integer touristCabinCount = 0;        // 经济舱旅客人数
    @Label("实际过站旅客总数")
    @MonitorField(desc = "实际过站旅客总数")
    private Integer personActualCountP = 0;         // 实际过站旅客总数
    @Label("实际过站成人数")
    @MonitorField(desc = "实际过站成人数")
    private Integer personCountP = 0;         // 实际过站成人数
    @Label("实际过站儿童数")
    @MonitorField(desc = "实际过站儿童数")
    private Integer childCoutP = 0;       // 实际过站儿童数
    @Label("实际过站婴儿数")
    @MonitorField(desc = "实际过站婴儿数")
    private Integer babyCountP = 0;       // 实际过站婴儿数


    //计划日期
    private Date planDate;
    //航空公司号码
    private String flightCompanyCode;
    //航班号
    private String flightNum;
    //飞机号码
    private String aircraftNum;
    //子公司名称
    private String flightCompanySubInfoCode;
    //子公司代码
    private String flightCompanySubInfoName;
    //结载人数
    private Integer lastAllCount = 0;

    //-----------------------------导出用属性  begin
    // 头等舱座位 导出用
    private Integer firstCabinSeat = 0;
    //结载总行李 导出用
    private Integer allLuggageCount = 0;
    //头等舱人数  导出用
    private Integer firstCabinPersonCount = 0;
    //vip行李件数  导出用
    private Integer vipLuggageCount = 0;
    private Integer transferLuggageCount = 0;

    //航空公司 导出用
    private String flightCompanyName;
    //经停地1编号 导出用
    private String passAirport1Code;
    //经停地2编号 导出用
    private String passAirport2Code;
    //机型编号 导出用
    private String aircraftTypeCode;
    //目的地编号 导出用
    private String arrivalAirportCode;
    //计划起飞 导出用
    private Date departurePlanTime;

    //实际起飞 导出用
    private Date atd;
    //航班性质 导出用
    private String flightNatureCode;
    //航班性质名称 导出用
    private String flightNatureName;
    //航线  导出用
    private String addressCode;
    //-----------------------------导出用属性  end


    public RmsFlightHomeManifest() {
        super();
    }

    public RmsFlightHomeManifest(String id) {
        super(id);
    }

    public String getFlightDynamicId() {
        return flightDynamicId;
    }

    public void setFlightDynamicId(String flightDynamicId) {
        this.flightDynamicId = flightDynamicId;
    }

    public Integer getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Integer personCount) {
        this.personCount = personCount;
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

    public Integer getTravellerAllCount() {
        return travellerAllCount;
    }

    public void setTravellerAllCount(Integer travellerAllCount) {
        this.travellerAllCount = travellerAllCount;
    }

    public Integer getFlightCrewCount() {
        return flightCrewCount;
    }

    public void setFlightCrewCount(Integer flightCrewCount) {
        this.flightCrewCount = flightCrewCount;
    }

    public Float getOilWeight() {
        return oilWeight;
    }

    public void setOilWeight(Float oilWeight) {
        this.oilWeight = oilWeight;
    }

    public Integer getMailCount() {
        return mailCount;
    }

    public void setMailCount(Integer mailCount) {
        this.mailCount = mailCount;
    }

    public Float getMailWeight() {
        return mailWeight;
    }

    public void setMailWeight(Float mailWeight) {
        this.mailWeight = mailWeight;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Float getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(Float goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public Integer getLuggageCount() {
        return luggageCount;
    }

    public void setLuggageCount(Integer luggageCount) {
        this.luggageCount = luggageCount;
    }

    public Float getLuggageWeight() {
        return luggageWeight;
    }

    public void setLuggageWeight(Float luggageWeight) {
        this.luggageWeight = luggageWeight;
    }

    public Integer getVipCount() {
        return vipCount;
    }

    public void setVipCount(Integer vipCount) {
        this.vipCount = vipCount;
    }

    public Integer getFirstCabinCount() {
        return firstCabinCount;
    }

    public void setFirstCabinCount(Integer firstCabinCount) {
        this.firstCabinCount = firstCabinCount;
    }

    public Integer getBusinessCabinCount() {
        return businessCabinCount;
    }

    public void setBusinessCabinCount(Integer businessCabinCount) {
        this.businessCabinCount = businessCabinCount;
    }

    public Integer getTouristCabinCount() {
        return touristCabinCount;
    }

    public void setTouristCabinCount(Integer touristCabinCount) {
        this.touristCabinCount = touristCabinCount;
    }

    public Integer getPersonActualCountP() {
        return personActualCountP;
    }

    public void setPersonActualCountP(Integer personActualCountP) {
        this.personActualCountP = personActualCountP;
    }

    public Integer getPersonCountP() {
        return personCountP;
    }

    public void setPersonCountP(Integer personCountP) {
        this.personCountP = personCountP;
    }

    public Integer getChildCoutP() {
        return childCoutP;
    }

    public void setChildCoutP(Integer childCoutP) {
        this.childCoutP = childCoutP;
    }

    public Integer getBabyCountP() {
        return babyCountP;
    }

    public void setBabyCountP(Integer babyCountP) {
        this.babyCountP = babyCountP;
    }

    public static Long getSerialVersionUID() {
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

    public Integer getLastAllCount() {
        return lastAllCount;
    }

    public void setLastAllCount(Integer lastAllCount) {
        this.lastAllCount = lastAllCount;
    }

    public Integer getFirstCabinSeat() {
        return firstCabinSeat;
    }

    public void setFirstCabinSeat(Integer firstCabinSeat) {
        this.firstCabinSeat = firstCabinSeat;
    }

    public Integer getAllLuggageCount() {
        return allLuggageCount;
    }

    public void setAllLuggageCount(Integer allLuggageCount) {
        this.allLuggageCount = allLuggageCount;
    }

    public Integer getFirstCabinPersonCount() {
        return firstCabinPersonCount;
    }

    public void setFirstCabinPersonCount(Integer firstCabinPersonCount) {
        this.firstCabinPersonCount = firstCabinPersonCount;
    }

    public Integer getVipLuggageCount() {
        return vipLuggageCount;
    }

    public void setVipLuggageCount(Integer vipLuggageCount) {
        this.vipLuggageCount = vipLuggageCount;
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

    public Date getDeparturePlanTime() {
        return departurePlanTime;
    }

    public void setDeparturePlanTime(Date departurePlanTime) {
        this.departurePlanTime = departurePlanTime;
    }

    public Integer getTransferLuggageCount() {
        return transferLuggageCount;
    }

    public void setTransferLuggageCount(Integer transferLuggageCount) {
        this.transferLuggageCount = transferLuggageCount;
    }

    public Date getAtd() {
        return atd;
    }

    public void setAtd(Date atd) {
        this.atd = atd;
    }

    public String getFlightNatureCode() {
        return flightNatureCode;
    }

    public void setFlightNatureCode(String flightNatureCode) {
        this.flightNatureCode = flightNatureCode;
    }

    public String getFlightNatureName() {
        return flightNatureName;
    }

    public void setFlightNatureName(String flightNatureName) {
        this.flightNatureName = flightNatureName;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    @Override
    public String toString() {
        return ObjectUtils.toEnitiyLog(this, new String[]{"flightDynamicId","id"});
    }
}