/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;


import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.SafeguardResourceService;

/**
 * 保障资源表Entity
 *
 * @author wjp
 * @version 2016-04-15
 */

@Monitor(desc = "保障资源表信息", tableName = "rms_safeguard_resource", service = SafeguardResourceService.class, socketNS = "/rms/safeguardResource")
public class SafeguardResource extends DataEntity<SafeguardResource> {

    private static final long serialVersionUID = 1L;
    @Label("服务提供者编号")
    @MonitorField(desc = "服务提供者编号")
    private String serviceProviderCode;        // 服务提供者编号
    @Label("服务提供者名称")
    @MonitorField(desc = "服务提供者名称")
    private String serviceProviderName;        // 服务提供者名称
    @Label("资源名称")
    @MonitorField(desc = "资源名称")
    private String resourceName;        // 资源名称
    @Label("资源型号编号")
    @MonitorField(desc = "资源型号编号")
    private String resourceTypeCode;        // 资源型号编号
    @Label("资源型号名称")
    @MonitorField(desc = "资源型号名称")
    private String resourceTypeName;        // 资源型号名称
    @Label("计费方式编号")
    @MonitorField(desc = "计费方式编号")
    private String billingModelsCode;        // 计费方式编号
    @Label("计费方式名称")
    @MonitorField(desc = "计费方式名称")
    private String billingModelsName;        // 计费方式名称
    @Label("资源数量")
    @MonitorField(desc = "资源数量")
    private String resourceNum;        // 资源数量
    @Label("维护岗位")
    @MonitorField(desc = "维护岗位")
    private String maintainedStation;        // 维护岗位
    @Label("使用岗位")
    @MonitorField(desc = "使用岗位")
    private String useStation;        // 使用岗位
    @Label("车辆名称")
    @MonitorField(desc = "车辆名称")
    private String carName;        // 车辆名称
    @Label("车辆类型编号")
    @MonitorField(desc = "车辆类型编号")
    private String carModelCode;        // 车辆类型编号
    @Label("车辆类型名称")
    @MonitorField(desc = "车辆类型名称")
    private String carModelName;        // 车辆类型名称
    @Label("车辆状态")
    @MonitorField(desc = "车辆状态")
    private String carStatus;        // 车辆状态
    @Label("冗余1")
    @MonitorField(desc = "冗余1")
    private String redundance1;        // 冗余1
    @Label("冗余2")
    @MonitorField(desc = "冗余2")
    private String redundance2;        // 冗余2
    @Label("冗余3")
    @MonitorField(desc = "冗余3")
    private String redundance3;        // 冗余3
    @Label("冗余4")
    @MonitorField(desc = "冗余4")
    private String redundance4;        // 冗余4
    @Label("冗余5")
    @MonitorField(desc = "冗余5")
    private String redundance5;        // 冗余5

    public SafeguardResource() {
        super();
    }

    public SafeguardResource(String id) {
        super(id);
    }

    public String getServiceProviderCode() {
        return serviceProviderCode;
    }

    public void setServiceProviderCode(String serviceProviderCode) {
        this.serviceProviderCode = serviceProviderCode;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceTypeCode() {
        return resourceTypeCode;
    }

    public void setResourceTypeCode(String resourceTypeCode) {
        this.resourceTypeCode = resourceTypeCode;
    }

    public String getResourceTypeName() {
        return resourceTypeName;
    }

    public void setResourceTypeName(String resourceTypeName) {
        this.resourceTypeName = resourceTypeName;
    }

    public String getBillingModelsCode() {
        return billingModelsCode;
    }

    public void setBillingModelsCode(String billingModelsCode) {
        this.billingModelsCode = billingModelsCode;
    }

    public String getBillingModelsName() {
        return billingModelsName;
    }

    public void setBillingModelsName(String billingModelsName) {
        this.billingModelsName = billingModelsName;
    }

    public String getResourceNum() {
        return resourceNum;
    }

    public void setResourceNum(String resourceNum) {
        this.resourceNum = resourceNum;
    }

    public String getMaintainedStation() {
        return maintainedStation;
    }

    public void setMaintainedStation(String maintainedStation) {
        this.maintainedStation = maintainedStation;
    }

    public String getUseStation() {
        return useStation;
    }

    public void setUseStation(String useStation) {
        this.useStation = useStation;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarModelCode() {
        return carModelCode;
    }

    public void setCarModelCode(String carModelCode) {
        this.carModelCode = carModelCode;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }

    public String getRedundance1() {
        return redundance1;
    }

    public void setRedundance1(String redundance1) {
        this.redundance1 = redundance1;
    }

    public String getRedundance2() {
        return redundance2;
    }

    public void setRedundance2(String redundance2) {
        this.redundance2 = redundance2;
    }

    public String getRedundance3() {
        return redundance3;
    }

    public void setRedundance3(String redundance3) {
        this.redundance3 = redundance3;
    }

    public String getRedundance4() {
        return redundance4;
    }

    public void setRedundance4(String redundance4) {
        this.redundance4 = redundance4;
    }

    public String getRedundance5() {
        return redundance5;
    }

    public void setRedundance5(String redundance5) {
        this.redundance5 = redundance5;
    }

}