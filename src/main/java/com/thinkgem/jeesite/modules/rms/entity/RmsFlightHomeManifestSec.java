/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;


import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.ObjectUtils;

/**
 * 本站仓单航段数据Entity
 *
 * @author dingshuang
 * @version 2016-05-20
 */

public class RmsFlightHomeManifestSec extends DataEntity<RmsFlightHomeManifestSec> {

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
    @Label("成人数")
    @MonitorField(desc = "成人数")
    private Float personCount = new Float(0);        // 成人数
    @Label("儿童数")
    @MonitorField(desc = "儿童数")
    private Float childCount = new Float(0);        // 儿童数
    @Label("婴儿数")
    @MonitorField(desc = "婴儿数")
    private Float babyCount = new Float(0);        // 婴儿数
    @Label("无陪伴儿童人数")
    @MonitorField(desc = "无陪伴儿童人数")
    private Float babyAloneCount = new Float(0);        // 无陪伴儿童人数
    @Label("公务舱人数")
    @MonitorField(desc = "公务舱人数")
    private Float businessCabinPersonCount = new Float(0);        // 公务舱人数
    @Label("经济舱人数")
    @MonitorField(desc = "经济舱人数")
    private Float touristCabinPersonCount = new Float(0);        // 经济舱人数
    @Label("头等舱人数")
    @MonitorField(desc = "头等舱人数")
    private Float firstCabinPersonCount = new Float(0);        // 头等舱人数
    @Label("VIP人数")
    @MonitorField(desc = "VIP人数")
    private Float vipCount = new Float(0);        // VIP人数
    @Label("行李数量")
    @MonitorField(desc = "行李数量")
    private Float luggageCount = new Float(0);        // 行李数量
    @Label("行李重量")
    @MonitorField(desc = "行李重量")
    private Float luggageWeight = new Float(0);       // 行李重量
    @Label("货物数量")
    @MonitorField(desc = "货物数量")
    private Float goodsCount = new Float(0);        // 货物数量
    @Label("货物重量")
    @MonitorField(desc = "货物重量")
    private Float goodsWeight = new Float(0);       // 货物重量
    @Label("邮件数量")
    @MonitorField(desc = "邮件数量")
    private Float mailCount = new Float(0);        // 邮件数量
    @Label("邮件重量")
    @MonitorField(desc = "邮件重量")
    private Float mailWeight = new Float(0);       // 邮件重量

    public RmsFlightHomeManifestSec() {
        super();
    }

    public RmsFlightHomeManifestSec(String id) {
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

    public Float getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Float personCount) {
        this.personCount = personCount;
    }

    public Float getChildCount() {
        return childCount;
    }

    public void setChildCount(Float childCount) {
        this.childCount = childCount;
    }

    public Float getBabyCount() {
        return babyCount;
    }

    public void setBabyCount(Float babyCount) {
        this.babyCount = babyCount;
    }

    public Float getBabyAloneCount() {
        return babyAloneCount;
    }

    public void setBabyAloneCount(Float babyAloneCount) {
        this.babyAloneCount = babyAloneCount;
    }

    public Float getBusinessCabinPersonCount() {
        return businessCabinPersonCount;
    }

    public void setBusinessCabinPersonCount(Float businessCabinPersonCount) {
        this.businessCabinPersonCount = businessCabinPersonCount;
    }

    public Float getTouristCabinPersonCount() {
        return touristCabinPersonCount;
    }

    public void setTouristCabinPersonCount(Float touristCabinPersonCount) {
        this.touristCabinPersonCount = touristCabinPersonCount;
    }

    public Float getFirstCabinPersonCount() {
        return firstCabinPersonCount;
    }

    public void setFirstCabinPersonCount(Float firstCabinPersonCount) {
        this.firstCabinPersonCount = firstCabinPersonCount;
    }

    public Float getVipCount() {
        return vipCount;
    }

    public void setVipCount(Float vipCount) {
        this.vipCount = vipCount;
    }

    public Float getLuggageCount() {
        return luggageCount;
    }

    public void setLuggageCount(Float luggageCount) {
        this.luggageCount = luggageCount;
    }

    public Float getLuggageWeight() {
        return luggageWeight;
    }

    public void setLuggageWeight(Float luggageWeight) {
        this.luggageWeight = luggageWeight;
    }

    public Float getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Float goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Float getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(Float goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public Float getMailCount() {
        return mailCount;
    }

    public void setMailCount(Float mailCount) {
        this.mailCount = mailCount;
    }

    public Float getMailWeight() {
        return mailWeight;
    }

    public void setMailWeight(Float mailWeight) {
        this.mailWeight = mailWeight;
    }

    @Override
    public String toString() {
        return ObjectUtils.toEnitiyLog(this, new String[]{"flightDynamicId","id"});
    }
}