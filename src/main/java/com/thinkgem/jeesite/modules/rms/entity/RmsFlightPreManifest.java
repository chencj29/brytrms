/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;


import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.ObjectUtils;
import com.thinkgem.jeesite.modules.rms.service.RmsFlightPreManifestService;

import java.util.Date;

/**
 * 上站舱单数据管理Entity
 *
 * @author dingshuang
 * @version 2016-05-21
 */

@Monitor(desc = "上站舱单数据管理信息", tableName = "rms_flight_pre_manifest", service = RmsFlightPreManifestService.class, socketNS = "/rms/rmsFlightPreManifest")
public class RmsFlightPreManifest extends DataEntity<RmsFlightPreManifest> {

    private static final Long serialVersionUID = 1L;
    @Label("航班动态ID")
    @MonitorField(desc = "航班动态ID")
    private String flightDynamicId;        // 航班动态ID
    @Label("地点")
    @MonitorField(desc = "地点")
    private String addr;        // 地点
    @Label("地点二字码")
    @MonitorField(desc = "地点二字码")
    private String addrCode;        // 地点二字码
    @Label("地点性质")
    @MonitorField(desc = "地点性质")
    private String addrNature;        // 地点性质
    @Label("进港成人数")
    @MonitorField(desc = "进港成人数")
    private Integer personCountJ = 0;        // 进港成人数
    @Label("进港儿童数")
    @MonitorField(desc = "进港儿童数")
    private Integer childCountJ = 0;        // 进港儿童数
    @Label("进港婴儿数")
    @MonitorField(desc = "进港婴儿数")
    private Integer babyCountJ = 0;        // 进港婴儿数


    private Integer touristCountJ = 0; // 进港总数

    private Integer touristCountP = 0; // 过站总数


    @Label("过站成人数")
    @MonitorField(desc = "过站成人数")
    private Integer personCountP = 0;        // 过站成人数
    @Label("过站儿童数")
    @MonitorField(desc = "过站儿童数")
    private Integer childCountP = 0;        // 过站儿童数
    @Label("过站婴儿数")
    @MonitorField(desc = "过站婴儿数")
    private Integer babyCountP = 0;        // 过站婴儿数
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

    //起飞地编码
    private String departureAirportCode;

    //起飞地名称
    private String departureAirportName;
    //经停地1编码
    private String passAirport1Code;
    //经停地1名称
    private String passAirport1Name;
    //经停地2编码
    private String passAirport2Code;
    //经停地2名称
    private String passAirport2Name;

    //机型编号 导出用
    private String aircraftTypeCode;
    //实际到达 导出用
    private Date ata;
    //航班性质 导出用
    private String flightNatureCode;
    //航班性质名称 导出用
    private String flightNatureName;
    //航线  导出用
    private String addressCode;

    public RmsFlightPreManifest() {
        super();
    }

    public RmsFlightPreManifest(String id) {
        super(id);
    }

    public String getFlightDynamicId() {
        return flightDynamicId;
    }

    public void setFlightDynamicId(String flightDynamicId) {
        this.flightDynamicId = flightDynamicId;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddrCode() {
        return addrCode;
    }

    public void setAddrCode(String addrCode) {
        this.addrCode = addrCode;
    }

    public String getAddrNature() {
        return addrNature;
    }

    public void setAddrNature(String addrNature) {
        this.addrNature = addrNature;
    }

    public Integer getPersonCountJ() {
        return personCountJ;
    }

    public void setPersonCountJ(Integer personCountJ) {
        this.personCountJ = personCountJ;
    }

    public Integer getChildCountJ() {
        return childCountJ;
    }

    public void setChildCountJ(Integer childCountJ) {
        this.childCountJ = childCountJ;
    }

    public Integer getBabyCountJ() {
        return babyCountJ;
    }

    public void setBabyCountJ(Integer babyCountJ) {
        this.babyCountJ = babyCountJ;
    }

    public Integer getTouristCountJ() {
        return touristCountJ;
    }

    public void setTouristCountJ(Integer touristCountJ) {
        this.touristCountJ = touristCountJ;
    }

    public Integer getTouristCountP() {
        return touristCountP;
    }

    public void setTouristCountP(Integer touristCountP) {
        this.touristCountP = touristCountP;
    }

    public Integer getPersonCountP() {
        return personCountP;
    }

    public void setPersonCountP(Integer personCountP) {
        this.personCountP = personCountP;
    }

    public Integer getChildCountP() {
        return childCountP;
    }

    public void setChildCountP(Integer childCountP) {
        this.childCountP = childCountP;
    }

    public Integer getBabyCountP() {
        return babyCountP;
    }

    public void setBabyCountP(Integer babyCountP) {
        this.babyCountP = babyCountP;
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

    public Date getAta() {
        return ata;
    }

    public void setAta(Date ata) {
        this.ata = ata;
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

    public String getAircraftTypeCode() {
        return aircraftTypeCode;
    }

    public void setAircraftTypeCode(String aircraftTypeCode) {
        this.aircraftTypeCode = aircraftTypeCode;
    }

    @Override
    public String toString() {
        return ObjectUtils.toEnitiyLog(this, new String[]{"flightDynamicId","id"});
    }
}