/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.EmergencyPrepareService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 应急救援预案Entity
 *
 * @author wjp
 * @version 2016-03-21
 */

@Monitor(desc = "应急救援预案信息", tableName = "rms_emergency_prepare", service = EmergencyPrepareService.class, socketNS = "/rms/emergencyPrepare")
public class EmergencyPrepare extends DataEntity<EmergencyPrepare> {

    private static final long serialVersionUID = 1L;
    @Label("创建时间")
    @MonitorField(desc = "创建时间")
    private Date createTime;        // 创建时间
    @Label("更新时间")
    @MonitorField(desc = "更新时间")
    private Date updateTime;        // 更新时间
    @Label("应急事件名称")
    @MonitorField(desc = "应急事件名称")
    private String emEventName;        // 应急事件名称
    @Label("应急救援方案ID")
    @MonitorField(desc = "应急救援方案ID")
    private String emPCode;        // 应急救援方案ID
    @Label("应急救援方案名称")
    @MonitorField(desc = "应急救援方案名称")
    private String emPName;        // 应急救援方案名称
    @Label("救援等级")
    @MonitorField(desc = "救援等级")
    private String emLevel;        // 救援等级

    public EmergencyPrepare() {
        super();
    }

    public EmergencyPrepare(String id) {
        super(id);
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

    @Length(min = 1, max = 50, message = "应急事件名称长度必须介于 1 和 50 之间")
    public String getEmEventName() {
        return emEventName;
    }

    public void setEmEventName(String emEventName) {
        this.emEventName = emEventName;
    }

    @Length(min = 1, max = 36, message = "应急救援方案ID长度必须介于 1 和 36 之间")
    public String getEmPCode() {
        return emPCode;
    }

    public void setEmPCode(String emPCode) {
        this.emPCode = emPCode;
    }

    @Length(min = 1, max = 200, message = "应急救援方案名称长度必须介于 1 和 200 之间")
    public String getEmPName() {
        return emPName;
    }

    public void setEmPName(String emPName) {
        this.emPName = emPName;
    }

    @Length(min = 0, max = 50, message = "救援等级长度必须介于 0 和 50 之间")
    public String getEmLevel() {
        return emLevel;
    }

    public void setEmLevel(String emLevel) {
        this.emLevel = emLevel;
    }

}