/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.ams.service.AircraftParametersService;
import org.hibernate.validator.constraints.Length;

/**
 * 机型参数管理Entity
 *
 * @author xiaopo
 * @version 2015-12-14
 */
@Monitor(desc = "机型参数信息", tableName = "AMS_AIRCRAFT_PARAMETERS", service = AircraftParametersService.class, socketNS = "/rms/aircraftParameters")
public class AircraftParameters extends DataEntity<AircraftParameters> {

    private static final long serialVersionUID = 1L;
    @TipMainField
    @MonitorField(desc = "机型")
    @Label("机型")
    private String aircraftModel;        // 机型
    @MonitorField(desc = "主机型")
    @Label("主机型")
    private String aircraftMainModel;        // 主机型
    @MonitorField(desc = "子机型")
    @Label("子机型")
    private String aircraftSubModel;        // 子机型
    @MonitorField(desc = "是否备用梯")
    @Label("是否备用梯")
    private Long useLift;        // 是否备用梯
    @MonitorField(desc = "是否用桥")
    @Label("是否用桥")
    private Long useBridge;        // 是否用桥
    @MonitorField(desc = "电源供应方式编号")
    @Label("电源供应方式编号")
    private String powerTypeCode;        // 电源供应方式编号
    @MonitorField(desc = "电源供应方式名称")
    @Label("电源供应方式名称")
    private String powerTypeName;        // 电源供应方式名称
    @MonitorField(desc = "燃料补充方式编号")
    @Label("燃料补充方式编号")
    private String fuelTypeCode;        // 燃料补充方式编号
    @MonitorField(desc = "燃料补充方式名称")
    @Label("燃料补充方式名称")
    private String fuelTypeName;        // 燃料补充方式名称
    @MonitorField(desc = "最大座位数")
    @Label("最大座位数")
    private Long maxnumSeat;        // 最大座位数
    @MonitorField(desc = "最大载货量")
    @Label("最大载货量")
    private Double maxnumCargoes;        // 最大载货量
    @MonitorField(desc = "最大加油量")
    @Label("最大加油量")
    private Double maxnumAmount;        // 最大加油量
    @MonitorField(desc = "翼展")
    @Label("翼展")
    private Double wingspan;        // 翼展
    @MonitorField(desc = "机身长")
    @Label("机身长")
    private Double fuselageLength;        // 机身长
    @MonitorField(desc = "鼻轮前长")
    @Label("鼻轮前长")
    private Double frontWheelLength;        // 鼻轮前长
    @MonitorField(desc = "高度")
    @Label("高度")
    private Double fuselageHeight;        // 高度
    @MonitorField(desc = "空重")
    @Label("空重")
    private Double aircraftWeight;        // 空重
    @MonitorField(desc = "无油重量")
    @Label("无油重量")
    private Double zeroFuelWeight;        // 无油重量
    @MonitorField(desc = "着陆重量")
    @Label("着陆重量")
    private Double wheelsWeight;        // 着陆重量
    @MonitorField(desc = "最大业栽")
    @Label("最大业栽")
    private Double maxnumPlayload;        // 最大业栽
    @MonitorField(desc = "最大起飞重量")
    @Label("最大起飞重量")
    private Double maxnumTakeoffWeight;        // 最大起飞重量
    @MonitorField(desc = "巡航高度")
    @Label("巡航高度")
    private Double cruisingAltitude;        // 巡航高度
    @MonitorField(desc = "最大航程")
    @Label("最大航程")
    private Double maxnumRange;        // 最大航程
    @MonitorField(desc = "跑道长度")
    @Label("跑道长度")
    private Double runwayLength;        // 跑道长度
    @MonitorField(desc = "ACN刚度")
    @Label("ACN刚度")
    private Double acnRigid;        // ACN刚度
    @MonitorField(desc = "ACN柔性")
    @Label("ACN柔性")
    private Double acnFlexible;        // ACN柔性
    @MonitorField(desc = "发动机台数")
    @Label("发动机台数")
    private Integer engineNum;        // 发动机台数
    @MonitorField(desc = "发动机型号")
    @Label("发动机型号")
    private Double engineType;        // 发动机型号
    @MonitorField(desc = "生产商")
    @Label("生产商")
    private String engineManufacturer;        // 生产商
    @MonitorField(desc = "最少停留时间")
    @Label("最少停留时间")
    private Integer lostofStopTime;        // 最少停留时间
    @MonitorField(desc = "机型分类编号")
    @Label("机型分类编号")
    private String articraftTypeCode;        // 机型分类编号
    @MonitorField(desc = "机型分类名称")
    @Label("机型分类名称")
    private String articraftTypeName;        // 机型分类名称
    @MonitorField(desc = "是否最大机型")
    @Label("是否最大机型")
    private Long isbigtype;        // 是否最大机型
    @MonitorField(desc = "供气方式")
    @Label("供气方式")
    private String arrationType;        // 供气方式

    public AircraftParameters() {
        super();
    }

    public AircraftParameters(String id) {
        super(id);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Length(min = 0, max = 200, message = "机型长度必须介于 0 和 200 之间")
    public String getAircraftModel() {
        return aircraftModel;
    }

    public void setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
    }

    @Length(min = 0, max = 200, message = "主机型长度必须介于 0 和 200 之间")
    public String getAircraftMainModel() {
        return aircraftMainModel;
    }

    public void setAircraftMainModel(String aircraftMainModel) {
        this.aircraftMainModel = aircraftMainModel;
    }

    @Length(min = 0, max = 200, message = "子机型长度必须介于 0 和 200 之间")
    public String getAircraftSubModel() {
        return aircraftSubModel;
    }

    public void setAircraftSubModel(String aircraftSubModel) {
        this.aircraftSubModel = aircraftSubModel;
    }

    public Long getUseLift() {
        return useLift;
    }

    public void setUseLift(Long useLift) {
        this.useLift = useLift;
    }

    public Long getUseBridge() {
        return useBridge;
    }

    public void setUseBridge(Long useBridge) {
        this.useBridge = useBridge;
    }

    @Length(min = 0, max = 100, message = "电源供应方式编号长度必须介于 0 和 100 之间")
    public String getPowerTypeCode() {
        return powerTypeCode;
    }

    public void setPowerTypeCode(String powerTypeCode) {
        this.powerTypeCode = powerTypeCode;
    }

    @Length(min = 0, max = 200, message = "电源供应方式名称长度必须介于 0 和 200 之间")
    public String getPowerTypeName() {
        return powerTypeName;
    }

    public void setPowerTypeName(String powerTypeName) {
        this.powerTypeName = powerTypeName;
    }

    @Length(min = 0, max = 100, message = "燃料补充方式编号长度必须介于 0 和 100 之间")
    public String getFuelTypeCode() {
        return fuelTypeCode;
    }

    public void setFuelTypeCode(String fuelTypeCode) {
        this.fuelTypeCode = fuelTypeCode;
    }

    @Length(min = 0, max = 200, message = "燃料补充方式名称长度必须介于 0 和 200 之间")
    public String getFuelTypeName() {
        return fuelTypeName;
    }

    public void setFuelTypeName(String fuelTypeName) {
        this.fuelTypeName = fuelTypeName;
    }

    public Long getMaxnumSeat() {
        return maxnumSeat;
    }

    public void setMaxnumSeat(Long maxnumSeat) {
        this.maxnumSeat = maxnumSeat;
    }

    public Double getMaxnumCargoes() {
        return maxnumCargoes;
    }

    public void setMaxnumCargoes(Double maxnumCargoes) {
        this.maxnumCargoes = maxnumCargoes;
    }

    public Double getMaxnumAmount() {
        return maxnumAmount;
    }

    public void setMaxnumAmount(Double maxnumAmount) {
        this.maxnumAmount = maxnumAmount;
    }

    public Double getWingspan() {
        return wingspan;
    }

    public void setWingspan(Double wingspan) {
        this.wingspan = wingspan;
    }

    public Double getFuselageLength() {
        return fuselageLength;
    }

    public void setFuselageLength(Double fuselageLength) {
        this.fuselageLength = fuselageLength;
    }

    public Double getFrontWheelLength() {
        return frontWheelLength;
    }

    public void setFrontWheelLength(Double frontWheelLength) {
        this.frontWheelLength = frontWheelLength;
    }

    public Double getFuselageHeight() {
        return fuselageHeight;
    }

    public void setFuselageHeight(Double fuselageHeight) {
        this.fuselageHeight = fuselageHeight;
    }

    public Double getAircraftWeight() {
        return aircraftWeight;
    }

    public void setAircraftWeight(Double aircraftWeight) {
        this.aircraftWeight = aircraftWeight;
    }

    public Double getZeroFuelWeight() {
        return zeroFuelWeight;
    }

    public void setZeroFuelWeight(Double zeroFuelWeight) {
        this.zeroFuelWeight = zeroFuelWeight;
    }

    public Double getWheelsWeight() {
        return wheelsWeight;
    }

    public void setWheelsWeight(Double wheelsWeight) {
        this.wheelsWeight = wheelsWeight;
    }

    public Double getMaxnumPlayload() {
        return maxnumPlayload;
    }

    public void setMaxnumPlayload(Double maxnumPlayload) {
        this.maxnumPlayload = maxnumPlayload;
    }

    public Double getMaxnumTakeoffWeight() {
        return maxnumTakeoffWeight;
    }

    public void setMaxnumTakeoffWeight(Double maxnumTakeoffWeight) {
        this.maxnumTakeoffWeight = maxnumTakeoffWeight;
    }

    public Double getCruisingAltitude() {
        return cruisingAltitude;
    }

    public void setCruisingAltitude(Double cruisingAltitude) {
        this.cruisingAltitude = cruisingAltitude;
    }

    public Double getMaxnumRange() {
        return maxnumRange;
    }

    public void setMaxnumRange(Double maxnumRange) {
        this.maxnumRange = maxnumRange;
    }

    public Double getRunwayLength() {
        return runwayLength;
    }

    public void setRunwayLength(Double runwayLength) {
        this.runwayLength = runwayLength;
    }

    public Double getAcnRigid() {
        return acnRigid;
    }

    public void setAcnRigid(Double acnRigid) {
        this.acnRigid = acnRigid;
    }

    public Double getAcnFlexible() {
        return acnFlexible;
    }

    public void setAcnFlexible(Double acnFlexible) {
        this.acnFlexible = acnFlexible;
    }

    public Integer getEngineNum() {
        return engineNum;
    }

    public void setEngineNum(Integer engineNum) {
        this.engineNum = engineNum;
    }

    public Double getEngineType() {
        return engineType;
    }

    public void setEngineType(Double engineType) {
        this.engineType = engineType;
    }

    @Length(min = 0, max = 200, message = "生产商长度必须介于 0 和 200 之间")
    public String getEngineManufacturer() {
        return engineManufacturer;
    }

    public void setEngineManufacturer(String engineManufacturer) {
        this.engineManufacturer = engineManufacturer;
    }

    public Integer getLostofStopTime() {
        return lostofStopTime;
    }

    public void setLostofStopTime(Integer lostofStopTime) {
        this.lostofStopTime = lostofStopTime;
    }

    @Length(min = 0, max = 100, message = "机型分类编号长度必须介于 0 和 100 之间")
    public String getArticraftTypeCode() {
        return articraftTypeCode;
    }

    public void setArticraftTypeCode(String articraftTypeCode) {
        this.articraftTypeCode = articraftTypeCode;
    }

    @Length(min = 0, max = 200, message = "机型分类名称长度必须介于 0 和 200 之间")
    public String getArticraftTypeName() {
        return articraftTypeName;
    }

    public void setArticraftTypeName(String articraftTypeName) {
        this.articraftTypeName = articraftTypeName;
    }

    public Long getIsbigtype() {
        return isbigtype;
    }

    public void setIsbigtype(Long isbigtype) {
        this.isbigtype = isbigtype;
    }

    @Length(min = 0, max = 100, message = "供气方式长度必须介于 0 和 100 之间")
    public String getArrationType() {
        return arrationType;
    }

    public void setArrationType(String arrationType) {
        this.arrationType = arrationType;
    }

}