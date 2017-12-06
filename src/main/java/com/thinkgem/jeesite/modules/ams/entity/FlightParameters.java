/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.ams.service.FlightParametersService;
import org.hibernate.validator.constraints.Length;

/**
 * 航班状态管理Entity
 *
 * @author xiaopo
 * @version 2015-12-14
 */
@Monitor(desc = "航班状态信息", tableName = "AMS_FLIGHT_PARAMETERS", service = FlightParametersService.class, socketNS = "/rms/flightParameters")
public class FlightParameters extends DataEntity<FlightParameters> {

    private static final long serialVersionUID = 1L;
    @MonitorField(desc = "状态编号")
    @Label("状态编号")
    private String statusNum;        // 状态编号
    @MonitorField(desc = "类型编号")
    @Label("类型编号")
    private String statusCode;      // 类型编号
    @MonitorField(desc = "状态类型")
    @Label("状态类型")
    private String statusName;        // 状态类型
    @MonitorField(desc = "性质描述")
    @Label("性质描述")
    private String propertyDescription;        // 性质描述
    @MonitorField(desc = "状态简称")
    @Label("状态简称")
    private String statusShort;        // 状态简称


    public FlightParameters() {
        super();
    }

    public FlightParameters(String id) {
        super(id);
    }

    @Length(min = 0, max = 100, message = "状态编号长度必须介于 0 和 100 之间")
    public String getStatusNum() {
        return statusNum;
    }

    public void setStatusNum(String statusNum) {
        this.statusNum = statusNum;
    }

    @Length(min = 0, max = 300, message = "状态类型长度必须介于 0 和 300 之间")
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Length(min = 0, max = 500, message = "性质描述长度必须介于 0 和 500 之间")
    public String getPropertyDescription() {
        return propertyDescription;
    }

    public void setPropertyDescription(String propertyDescription) {
        this.propertyDescription = propertyDescription;
    }

    @Length(min = 0, max = 100, message = "状态简称长度必须介于 0 和 100 之间")
    public String getStatusShort() {
        return statusShort;
    }

    public void setStatusShort(String statusShort) {
        this.statusShort = statusShort;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}