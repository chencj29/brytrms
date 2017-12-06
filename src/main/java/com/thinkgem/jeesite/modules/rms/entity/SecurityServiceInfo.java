/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.thinkgem.jeesite.modules.rms.service.SecurityServiceInfoService;
import com.bstek.urule.model.Label;

/**
 * 安检航班信息Entity
 *
 * @author wjp
 * @version 2017-11-03
 */

@Monitor(desc = "安检航班信息信息", tableName = "rms_security_service_info", service = SecurityServiceInfoService.class, socketNS = "/rms/securityServiceInfo")
public class SecurityServiceInfo extends DataEntity<SecurityServiceInfo> {

    private static final long serialVersionUID = 1L;
    @Label("动态id")
    @MonitorField(desc = "动态id")
    private String flightDynamicIc;        // 动态id
    @Label("安检和勤务信息")
    @MonitorField(desc = "安检和勤务信息")
    @TipMainField
    private String serviceInfo;        // 安检和勤务信息

    public SecurityServiceInfo() {
        super();
    }

    public SecurityServiceInfo(String id) {
        super(id);
    }

    public String getFlightDynamicIc() {
        return flightDynamicIc;
    }

    public void setFlightDynamicIc(String flightDynamicIc) {
        this.flightDynamicIc = flightDynamicIc;
    }

    public String getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(String serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

}