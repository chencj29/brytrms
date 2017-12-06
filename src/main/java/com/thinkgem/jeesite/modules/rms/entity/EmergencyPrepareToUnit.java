/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.EmergencyPrepareToUnitService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 应急救援预案救援单位关联表Entity
 *
 * @author wjp
 * @version 2016-04-08
 */

@Monitor(desc = "应急救援预案救援单位关联表信息", tableName = "rms_emergency_prepare_to_unit", service = EmergencyPrepareToUnitService.class, socketNS = "/rms/emergencyPrepareToUnit")
public class EmergencyPrepareToUnit extends DataEntity<EmergencyPrepareToUnit> {

    private static final long serialVersionUID = 1L;
    @Label("创建时间")
    @MonitorField(desc = "创建时间")
    private Date createTime;        // 创建时间
    @Label("更新时间")
    @MonitorField(desc = "更新时间")
    private Date updateTime;        // 更新时间
    @Label("应急救援预案ID")
    @MonitorField(desc = "应急救援预案ID")
    private String rmsEmergencyPrepareId;        // 应急救援预案ID
    @Label("救援单位ID")
    @MonitorField(desc = "救援单位ID")
    private String rmsEmergencyUnitId;        // 救援单位ID

    public EmergencyPrepareToUnit() {
        super();
    }

    public EmergencyPrepareToUnit(String id) {
        super(id);
    }

    public EmergencyPrepareToUnit(String pid, String uid) {
        this.rmsEmergencyPrepareId = pid;
        this.rmsEmergencyUnitId = uid;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Length(min = 1, max = 36, message = "应急救援预案ID长度必须介于 1 和 36 之间")
    public String getRmsEmergencyPrepareId() {
        return rmsEmergencyPrepareId;
    }

    public void setRmsEmergencyPrepareId(String rmsEmergencyPrepareId) {
        this.rmsEmergencyPrepareId = rmsEmergencyPrepareId;
    }

    @Length(min = 1, max = 36, message = "救援单位ID长度必须介于 1 和 36 之间")
    public String getRmsEmergencyUnitId() {
        return rmsEmergencyUnitId;
    }

    public void setRmsEmergencyUnitId(String rmsEmergencyUnitId) {
        this.rmsEmergencyUnitId = rmsEmergencyUnitId;
    }

}