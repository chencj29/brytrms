/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.ams.service.FlightPropertiesService;
import org.hibernate.validator.constraints.Length;

/**
 * 航班属性管理Entity
 *
 * @author xiaopo
 * @version 2015-12-14
 */
@Monitor(desc = "航班属性信息", tableName = "AMS_FLIGHT_PROPERTIES", service = FlightPropertiesService.class, socketNS = "/rms/flightProperties")
public class FlightProperties extends DataEntity<FlightProperties> {

    private static final long serialVersionUID = 1L;
    @MonitorField(desc = "属性编码")
    @Label("属性编码")
    private String propertiesNo;        // 属性编码
    @TipMainField
    @MonitorField(desc = "属性名称")
    @Label("属性名称")
    private String propertiesName;        // 属性名称


    public FlightProperties() {
        super();
    }

    public FlightProperties(String id) {
        super(id);
    }

    @Length(min = 0, max = 100, message = "属性编码长度必须介于 0 和 100 之间")
    public String getPropertiesNo() {
        return propertiesNo;
    }

    public void setPropertiesNo(String propertiesNo) {
        this.propertiesNo = propertiesNo;
    }

    @Length(min = 0, max = 300, message = "属性名称长度必须介于 0 和 300 之间")
    public String getPropertiesName() {
        return propertiesName;
    }

    public void setPropertiesName(String propertiesName) {
        this.propertiesName = propertiesName;
    }

}