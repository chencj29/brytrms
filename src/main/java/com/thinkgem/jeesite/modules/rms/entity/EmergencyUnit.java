/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.EmergencyUnitService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 应急救援单位信息Entity
 *
 * @author wjp
 * @version 2016-04-06
 */

@Monitor(desc = "应急救援单位信息", tableName = "rms_emergency_unit", service = EmergencyUnitService.class, socketNS = "/rms/emergencyUnit")
public class EmergencyUnit extends DataEntity<EmergencyUnit> {

    private static final long serialVersionUID = 1L;
    @Label("创建时间")
    @MonitorField(desc = "创建时间")
    private Date createTime;        // 创建时间
    @Label("更新时间")
    @MonitorField(desc = "更新时间")
    private Date updateTime;        // 更新时间
    @Label("父机构ID")
    @MonitorField(desc = "父机构ID")
    private String pid;        // 父机构ID
    @Label("单位名称")
    @MonitorField(desc = "单位名称")
    private String unitName;        // 单位名称
    @Label("联系人姓名")
    @MonitorField(desc = "联系人姓名")
    private String contactName;        // 联系人姓名
    @Label("联系人电话")
    @MonitorField(desc = "联系人电话")
    private String contactTel;        // 联系人电话
    @Label("类型")
    @MonitorField(desc = "类型")
    private String ntype;        // 类型

    public EmergencyUnit() {
        super();
    }

    public EmergencyUnit(String id) {
        super(id);
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Length(min = 1, max = 36, message = "父机构ID长度必须介于 1 和 36 之间")
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Length(min = 1, max = 200, message = "单位名称长度必须介于 1 和 200 之间")
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Length(min = 0, max = 50, message = "联系人姓名长度必须介于 0 和 50 之间")
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @Length(min = 0, max = 20, message = "联系人电话长度必须介于 0 和 20 之间")
    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    @Length(min = 1, max = 36, message = "类型长度必须介于 1 和 36 之间")
    public String getNtype() {
        return ntype;
    }

    public void setNtype(String ntype) {
        this.ntype = ntype;
    }

}