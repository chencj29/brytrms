/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;


import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.ObjectUtils;
import com.thinkgem.jeesite.modules.rms.service.RmsFlightOverManifestService;

import java.util.Date;

/**
 * 过站仓单数据管理Entity
 *
 * @author dingshuang
 * @version 2016-05-20
 */

@Monitor(desc = "过站仓单数据管理信息", tableName = "rms_flight_over_manifest", service = RmsFlightOverManifestService.class, socketNS = "/rms/rmsFlightOverManifest")
public class RmsFlightOverManifest extends DataEntity<RmsFlightOverManifest> {

    private static final Long serialVersionUID = 1L;
    @Label("航班动态ID")
    @MonitorField(desc = "航班动态ID")
    private String flightDynamicId;        // 航班动态ID
    @Label("行李数量")
    @MonitorField(desc = "行李数量")
    private Integer luggageCount = 0;        // 行李数量
    @Label("行李重量")
    @MonitorField(desc = "行李重量")
    private Float luggageWeight = new Float(0);        // 行李重量
    @Label("货物数量")
    @MonitorField(desc = "货物数量")
    private Integer goodsCount = 0;        // 货物数量
    @Label("货物重量")
    @MonitorField(desc = "货物重量")
    private Float goodsWeight = new Float(0);        // 货物重量
    @Label("邮件数量")
    @MonitorField(desc = "邮件数量")
    private Integer mailCount = 0;        // 邮件数量
    @Label("邮件重量")
    @MonitorField(desc = "邮件重量")
    private Float mailWeight = new Float(0);        // 邮件重量
    @Label("VIP人数")
    @MonitorField(desc = "VIP人数")
    private Integer vipCount = 0;        // VIP人数
    @Label("成人数")
    @MonitorField(desc = "成人数")
    private Integer personCount = 0;        // 成人数
    @Label("儿童数")
    @MonitorField(desc = "儿童数")
    private Integer childCount = 0;        // 儿童数
    @Label("婴儿数")
    @MonitorField(desc = "婴儿数")
    private Integer babyCount = 0;        // 婴儿数
    @Label("旅客计划总人数")
    @MonitorField(desc = "旅客计划总人数")
    private Integer personPlanCount = 0;        // 旅客计划总人数
    @Label("旅客实际总人数")
    @MonitorField(desc = "旅客实际总人数")
    private Integer personTrueCount = 0;        // 旅客实际总人数

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
//    private Integer firstCabinSeat = 0;
//    //结载总行李 导出用
//    private Integer allLuggageCount = 0;
//    //头等舱人数  导出用
//    private Integer firstCabinPersonCount = 0;
//    //vip行李件数  导出用
//    private Integer vipLuggageCount = 0;
//    private Integer transferLuggageCount = 0;

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
    //-----------------------------导出用属性  end


    public RmsFlightOverManifest() {
        super();
    }

    public RmsFlightOverManifest(String id) {
        super(id);
    }

    public String getFlightDynamicId() {
        return flightDynamicId;
    }

    public void setFlightDynamicId(String flightDynamicId) {
        this.flightDynamicId = flightDynamicId;
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

    public Integer getVipCount() {
        return vipCount;
    }

    public void setVipCount(Integer vipCount) {
        this.vipCount = vipCount;
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

    public Integer getPersonPlanCount() {
        return personPlanCount;
    }

    public void setPersonPlanCount(Integer personPlanCount) {
        this.personPlanCount = personPlanCount;
    }

    public Integer getPersonTrueCount() {
        return personTrueCount;
    }

    public void setPersonTrueCount(Integer personTrueCount) {
        this.personTrueCount = personTrueCount;
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

    @Override
    public String toString() {
        return ObjectUtils.toEnitiyLog(this, new String[]{"flightDynamicId","id"});
    }
}