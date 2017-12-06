/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.ams.service.CompanyAircraftNumService;
import org.hibernate.validator.constraints.Length;

/**
 * 公司机号管理Entity
 *
 * @author xiaopo
 * @version 2015-12-15
 */
@Monitor(desc = "公司机号信息", tableName = "AMS_COMPANY_AIRCRAFT_NUM", service = CompanyAircraftNumService.class, socketNS = "/rms/companyAircraftNum")
public class CompanyAircraftNum extends DataEntity<CompanyAircraftNum> {

    private static final long serialVersionUID = 1L;
    @MonitorField(desc = "航空公司编码")
    @Label("航空公司编码")
    private String flightCompanyCode;        // 航空公司编码
    @TipMainField
    @MonitorField(desc = "航空公司名称")
    @Label("航空公司名称")
    private String flightCompanyName;        // 航空公司名称
    @MonitorField(desc = "子航空公司编码")
    @Label("子航空公司编码")
    private String flightCompanySubCode;        // 子航空公司编码
    @MonitorField(desc = "子公司名称")
    @Label("子公司名称")
    private String flightCompanySubName;        // 子公司名称
    @MonitorField(desc = "飞机参数表ID")
    @Label("飞机参数表ID")
    private String aircraftParametersId;        // 飞机参数表ID
    @MonitorField(desc = "飞机型号")
    @Label("飞机型号")
    private String aircraftModel;        // 飞机型号
    @MonitorField(desc = "飞机主型号")
    @Label("飞机主型号")
    private String aircraftMainModel;        // 飞机主型号
    @MonitorField(desc = "飞机子型号")
    @Label("飞机子型号")
    private String aircraftSubModel;        // 飞机子型号
    @MonitorField(desc = "起飞重量")
    @Label("起飞重量")
    private Double maxnumTakeoffWeight;        // 起飞重量
    @MonitorField(desc = "业务载重")
    @Label("业务载重")
    private Double maxnumPlayload;        // 业务载重
    @MonitorField(desc = "客座数")
    @Label("客座数")
    private Double maxnumSeat;        // 客座数
    @MonitorField(desc = "飞机类型编号")
    @Label("飞机类型编号")
    private String aircraftTypeCode;        // 飞机类型编号
    @MonitorField(desc = "飞机类型名称")
    @Label("飞机类型名称")
    private String aircraftTypeName;        // 飞机类型名称
    @MonitorField(desc = "飞机号码")
    @Label("飞机号码")
    private String aircraftNum;        // 飞机号码

    public CompanyAircraftNum() {
        super();
    }

    public CompanyAircraftNum(String id) {
        super(id);
    }

    @Length(min = 0, max = 36, message = "航空公司编码长度必须介于 0 和 36 之间")
    public String getFlightCompanyCode() {
        return flightCompanyCode;
    }

    public void setFlightCompanyCode(String flightCompanyCode) {
        this.flightCompanyCode = flightCompanyCode;
    }

    @Length(min = 0, max = 200, message = "航空公司名称长度必须介于 0 和 200 之间")
    public String getFlightCompanyName() {
        return flightCompanyName;
    }

    public void setFlightCompanyName(String flightCompanyName) {
        this.flightCompanyName = flightCompanyName;
    }

    @Length(min = 0, max = 100, message = "子航空公司编码长度必须介于 0 和 100 之间")
    public String getFlightCompanySubCode() {
        return flightCompanySubCode;
    }

    public void setFlightCompanySubCode(String flightCompanySubCode) {
        this.flightCompanySubCode = flightCompanySubCode;
    }

    @Length(min = 0, max = 200, message = "子公司名称长度必须介于 0 和 200 之间")
    public String getFlightCompanySubName() {
        return flightCompanySubName;
    }

    public void setFlightCompanySubName(String flightCompanySubName) {
        this.flightCompanySubName = flightCompanySubName;
    }

    @Length(min = 0, max = 36, message = "飞机参数表ID长度必须介于 0 和 36 之间")
    public String getAircraftParametersId() {
        return aircraftParametersId;
    }

    public void setAircraftParametersId(String aircraftParametersId) {
        this.aircraftParametersId = aircraftParametersId;
    }

    @Length(min = 0, max = 200, message = "飞机型号长度必须介于 0 和 200 之间")
    public String getAircraftModel() {
        return aircraftModel;
    }

    public void setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
    }

    @Length(min = 0, max = 200, message = "飞机主型号长度必须介于 0 和 200 之间")
    public String getAircraftMainModel() {
        return aircraftMainModel;
    }

    public void setAircraftMainModel(String aircraftMainModel) {
        this.aircraftMainModel = aircraftMainModel;
    }

    @Length(min = 0, max = 200, message = "飞机子型号长度必须介于 0 和 200 之间")
    public String getAircraftSubModel() {
        return aircraftSubModel;
    }

    public void setAircraftSubModel(String aircraftSubModel) {
        this.aircraftSubModel = aircraftSubModel;
    }

    public Double getMaxnumTakeoffWeight() {
        return maxnumTakeoffWeight;
    }

    public void setMaxnumTakeoffWeight(Double maxnumTakeoffWeight) {
        this.maxnumTakeoffWeight = maxnumTakeoffWeight;
    }

    public Double getMaxnumPlayload() {
        return maxnumPlayload;
    }

    public void setMaxnumPlayload(Double maxnumPlayload) {
        this.maxnumPlayload = maxnumPlayload;
    }

    public Double getMaxnumSeat() {
        return maxnumSeat;
    }

    public void setMaxnumSeat(Double maxnumSeat) {
        this.maxnumSeat = maxnumSeat;
    }

    @Length(min = 0, max = 36, message = "飞机类型编号长度必须介于 0 和 36 之间")
    public String getAircraftTypeCode() {
        return aircraftTypeCode;
    }

    public void setAircraftTypeCode(String aircraftTypeCode) {
        this.aircraftTypeCode = aircraftTypeCode;
    }

    @Length(min = 0, max = 200, message = "飞机类型名称长度必须介于 0 和 200 之间")
    public String getAircraftTypeName() {
        return aircraftTypeName;
    }

    public void setAircraftTypeName(String aircraftTypeName) {
        this.aircraftTypeName = aircraftTypeName;
    }

    @Length(min = 0, max = 100, message = "飞机号码长度必须介于 0 和 100 之间")
    public String getAircraftNum() {
        return aircraftNum;
    }

    public void setAircraftNum(String aircraftNum) {
        this.aircraftNum = aircraftNum;
    }

}