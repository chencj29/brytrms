/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.ServiceProviderService;
import org.hibernate.validator.constraints.Length;

/**
 * 服务提供者表Entity
 *
 * @author wjp
 * @version 2016-03-09
 */

@Monitor(desc = "服务提供者表信息", tableName = "ams_service_provider", service = ServiceProviderService.class, socketNS = "/rms/serviceProvider")
public class ServiceProvider extends DataEntity<ServiceProvider> {

    private static final long serialVersionUID = 1L;
    @Label("服务提供者编号")
    @MonitorField(desc = "服务提供者编号")
    private String serviceProviderNo;        // 服务提供者编号
    @Label("服务提供者名称")
    @MonitorField(desc = "服务提供者名称")
    private String serviceProviderName;        // 服务提供者名称
    @Label("服务提供者简称")
    @MonitorField(desc = "服务提供者简称")
    private String serviceProviderShort;        // 服务提供者简称
    @Label("是否默认")
    @MonitorField(desc = "是否默认")
    private Long isdefault;        // 是否默认

    public ServiceProvider() {
        super();
    }

    public ServiceProvider(String id) {
        super(id);
    }

    @Length(min = 0, max = 100, message = "服务提供者编号长度必须介于 0 和 100 之间")
    public String getServiceProviderNo() {
        return serviceProviderNo;
    }

    public void setServiceProviderNo(String serviceProviderNo) {
        this.serviceProviderNo = serviceProviderNo;
    }

    @Length(min = 0, max = 200, message = "服务提供者名称长度必须介于 0 和 200 之间")
    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    @Length(min = 0, max = 100, message = "服务提供者简称长度必须介于 0 和 100 之间")
    public String getServiceProviderShort() {
        return serviceProviderShort;
    }

    public void setServiceProviderShort(String serviceProviderShort) {
        this.serviceProviderShort = serviceProviderShort;
    }

    public Long getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Long isdefault) {
        this.isdefault = isdefault;
    }

}