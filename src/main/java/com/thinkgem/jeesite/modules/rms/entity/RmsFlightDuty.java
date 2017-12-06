/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;


import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.ObjectUtils;
import com.thinkgem.jeesite.modules.rms.service.RmsFlightDutyService;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 值机数据管理Entity
 *
 * @author dingshuang
 * @version 2016-05-18
 */

@Monitor(desc = "值机数据管理信息", tableName = "rms_flight_duty", service = RmsFlightDutyService.class, socketNS = "/rms/rmsFlightDuty")
public class RmsFlightDuty extends DataEntity<RmsFlightDuty> {

    private static final Long serialVersionUID = 1L;


    @Label("ID")
    @MonitorField(desc = "ID")
    private String id;        // 航班动态ID

    @Label("航班动态ID")
    @MonitorField(desc = "航班动态ID")
    private String flightDynamicId;        // 航班动态ID
    @Label("航班号")
    @MonitorField(desc = "航班号")
    private String flightNum;        // 航班号
    @Label("地点")
    @MonitorField(desc = "地点")
    private String addr;        // 地点
    @Label("地点二字码")
    @MonitorField(desc = "地点二字码")
    private String addrTwoCode;        // 地点二字码
    @Label("地点性质")
    @MonitorField(desc = "地点性质")
    private String addrTwoNature;        // 地点性质
    @Label("成人数")
    @MonitorField(desc = "成人数")
    private Integer personCount = 0;        // 成人数
    @Label("儿童数")
    @MonitorField(desc = "儿童数")
    private Integer childCount = 0;        // 儿童数
    @Label("婴儿数")
    @MonitorField(desc = "婴儿数")
    private Integer babyCount = 0;        // 婴儿数
    @Label("无陪伴儿童人数")
    @MonitorField(desc = "无陪伴儿童人数")
    private Integer babyAloneCount = 0;    // 无陪伴儿童人数
    @Label("直达行李数量")
    @MonitorField(desc = "直达行李数量")
    private Integer nonstopLuggageCount = 0;            // 直达行李数量
    @Label("直达行李重量")
    @MonitorField(desc = "直达行李重量")
    private Float nonstopLuggageWeight = new Float(0);        // 直达行李重量
    @Label("转运货物数量")
    @MonitorField(desc = "转运货物数量")
    private Integer transferLuggageCount = 0;            // 转运货物数量
    @Label("转运货物重量")
    @MonitorField(desc = "转运货物重量")
    private Float transferLuggageWeight = new Float(0);    // 转运货物重量
    @Label("vip人数")
    @MonitorField(desc = "vip人数")
    private Integer vipPersonCount = 0;            // vip人数
    @Label("vip座位")
    @MonitorField(desc = "vip座位")
    private String vipSeat;        // vip座位
    @Label("vip行李件数")
    @MonitorField(desc = "vip行李件数")
    private Integer vipLuggageCount = 0;            // vip行李件数
    @Label("vip行李重量")
    @MonitorField(desc = "vip行李重量")
    private Float vipLuggageWeight = new Float(0);    // vip行李重量
    @Label("头等舱人数")
    @MonitorField(desc = "头等舱人数")
    private Integer firstCabinPersonCount = 0;            // 头等舱人数
    @Label("头等舱座位")
    @MonitorField(desc = "头等舱座位")
    private String firstCabinSeat;        // 头等舱座位
    @Label("公务舱人数")
    @MonitorField(desc = "公务舱人数")
    private Integer businessCabinPersonCount = 0;            // 公务舱人数
    @Label("公务舱座位")
    @MonitorField(desc = "公务舱座位")
    private String businessCabinSeat;        // 公务舱座位

    public RmsFlightDuty() {
        super();
    }

    public RmsFlightDuty(String id) {
        super(id);
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public static Long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFlightDynamicId() {
        return flightDynamicId;
    }

    public void setFlightDynamicId(String flightDynamicId) {
        this.flightDynamicId = flightDynamicId;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddrTwoCode() {
        return addrTwoCode;
    }

    public void setAddrTwoCode(String addrTwoCode) {
        this.addrTwoCode = addrTwoCode;
    }

    public String getAddrTwoNature() {
        return addrTwoNature;
    }

    public void setAddrTwoNature(String addrTwoNature) {
        this.addrTwoNature = addrTwoNature;
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

    public Float getNonstopLuggageWeight() {
        return nonstopLuggageWeight;
    }

    public void setNonstopLuggageWeight(Float nonstopLuggageWeight) {
        this.nonstopLuggageWeight = nonstopLuggageWeight;
    }

    public Integer getTransferLuggageCount() {
        return transferLuggageCount;
    }

    public void setTransferLuggageCount(Integer transferLuggageCount) {
        this.transferLuggageCount = transferLuggageCount;
    }

    public Float getTransferLuggageWeight() {
        return transferLuggageWeight;
    }

    public void setTransferLuggageWeight(Float transferLuggageWeight) {
        this.transferLuggageWeight = transferLuggageWeight;
    }

    public Integer getVipPersonCount() {
        return vipPersonCount;
    }

    public void setVipPersonCount(Integer vipPersonCount) {
        this.vipPersonCount = vipPersonCount;
    }

    public String getVipSeat() {
        return vipSeat;
    }

    public void setVipSeat(String vipSeat) {
        this.vipSeat = vipSeat;
    }

    public Integer getVipLuggageCount() {
        return vipLuggageCount;
    }

    public void setVipLuggageCount(Integer vipLuggageCount) {
        this.vipLuggageCount = vipLuggageCount;
    }

    public Float getVipLuggageWeight() {
        return vipLuggageWeight;
    }

    public void setVipLuggageWeight(Float vipLuggageWeight) {
        this.vipLuggageWeight = vipLuggageWeight;
    }

    public Integer getFirstCabinPersonCount() {
        return firstCabinPersonCount;
    }

    public void setFirstCabinPersonCount(Integer firstCabinPersonCount) {
        this.firstCabinPersonCount = firstCabinPersonCount;
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

    @Override
    public String toString() {
        return ObjectUtils.toEnitiyLog(this, new String[]{"flightDynamicId", "id"});
    }
}