/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.SpecialCarService;
import org.hibernate.validator.constraints.Length;

/**
 * 特殊车辆基础信息Entity
 *
 * @author wjp
 * @version 2016-03-09
 */

@Monitor(desc = "特殊车辆基础信息", tableName = "rms_special_car", service = SpecialCarService.class, socketNS = "/rms/specialCar")
public class SpecialCar extends DataEntity<SpecialCar> {

    private static final long serialVersionUID = 1L;
    @Label("资源名称")
    @MonitorField(desc = "资源名称")
    private String resourceName;        // 资源名称
    @Label("资源型号")
    @MonitorField(desc = "资源型号")
    private String resourceType;        // 资源型号
    @Label("计费方式")
    @MonitorField(desc = "计费方式")
    private String chargeMethod;        // 计费方式
    @Label("资源数量")
    @MonitorField(desc = "资源数量")
    private Long resourceCount;        // 资源数量
    @Label("维护岗位")
    @MonitorField(desc = "维护岗位")
    private String maintanceDuty;        // 维护岗位
    @Label("使用岗位")
    @MonitorField(desc = "使用岗位")
    private String uesDuty;        // 使用岗位
    @Label("航站楼")
    @MonitorField(desc = "航站楼")
    private String aircraftTerminalCode;        // 航站楼
    @Label("服务提供者")
    @MonitorField(desc = "服务提供者")
    private String serviceProvider;        // 服务提供者

    public SpecialCar() {
        super();
    }

    public SpecialCar(String id) {
        super(id);
    }

    @Length(min = 0, max = 36, message = "资源名称长度必须介于 0 和 36 之间")
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Length(min = 0, max = 10, message = "资源型号长度必须介于 0 和 10 之间")
    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    @Length(min = 0, max = 1, message = "计费方式长度必须介于 0 和 1 之间")
    public String getChargeMethod() {
        return chargeMethod;
    }

    public void setChargeMethod(String chargeMethod) {
        this.chargeMethod = chargeMethod;
    }

    public Long getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(Long resourceCount) {
        this.resourceCount = resourceCount;
    }

    @Length(min = 0, max = 20, message = "维护岗位长度必须介于 0 和 20 之间")
    public String getMaintanceDuty() {
        return maintanceDuty;
    }

    public void setMaintanceDuty(String maintanceDuty) {
        this.maintanceDuty = maintanceDuty;
    }

    @Length(min = 0, max = 20, message = "使用岗位长度必须介于 0 和 20 之间")
    public String getUesDuty() {
        return uesDuty;
    }

    public void setUesDuty(String uesDuty) {
        this.uesDuty = uesDuty;
    }

    @Length(min = 0, max = 10, message = "航站楼长度必须介于 0 和 10 之间")
    public String getAircraftTerminalCode() {
        return aircraftTerminalCode;
    }

    public void setAircraftTerminalCode(String aircraftTerminalCode) {
        this.aircraftTerminalCode = aircraftTerminalCode;
    }

    @Length(min = 0, max = 20, message = "服务提供者长度必须介于 0 和 20 之间")
    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

}