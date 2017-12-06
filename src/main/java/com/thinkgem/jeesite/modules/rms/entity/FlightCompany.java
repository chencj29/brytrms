/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.FlightCompanyService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 航空公司联系信息Entity
 *
 * @author wjp
 * @version 2016-03-21
 */

@Monitor(desc = "航空公司联系信息", tableName = "rms_flight_company", service = FlightCompanyService.class, socketNS = "/rms/flightCompany")
public class FlightCompany extends DataEntity<FlightCompany> {

    private static final long serialVersionUID = 1L;
    @Label("创建时间")
    @MonitorField(desc = "创建时间")
    private Date createTime;        // 创建时间
    @Label("更新时间")
    @MonitorField(desc = "更新时间")
    private Date updateTime;        // 更新时间
    @Label("航空公司")
    @MonitorField(desc = "航空公司")
    private String flightCompanyName;        // 航空公司
    @Label("联系部门")
    @MonitorField(desc = "联系部门")
    private String contactDept;        // 联系部门
    @Label("联系人")
    @MonitorField(desc = "联系人")
    private String contactPersonal;        // 联系人
    @Label("职务")
    @MonitorField(desc = "职务")
    private String duty;        // 职务
    @Label("性别")
    @MonitorField(desc = "性别")
    private String sex;        // 性别
    @Label("联系电话")
    @MonitorField(desc = "联系电话")
    private String tel;        // 联系电话

    public FlightCompany() {
        super();
    }

    public FlightCompany(String id) {
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

    @Length(min = 1, max = 40, message = "航空公司长度必须介于 1 和 40 之间")
    public String getFlightCompanyName() {
        return flightCompanyName;
    }

    public void setFlightCompanyName(String flightCompanyName) {
        this.flightCompanyName = flightCompanyName;
    }

    @Length(min = 0, max = 40, message = "联系部门长度必须介于 0 和 40 之间")
    public String getContactDept() {
        return contactDept;
    }

    public void setContactDept(String contactDept) {
        this.contactDept = contactDept;
    }

    @Length(min = 0, max = 20, message = "联系人长度必须介于 0 和 20 之间")
    public String getContactPersonal() {
        return contactPersonal;
    }

    public void setContactPersonal(String contactPersonal) {
        this.contactPersonal = contactPersonal;
    }

    @Length(min = 0, max = 20, message = "职务长度必须介于 0 和 20 之间")
    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    @Length(min = 0, max = 4, message = "性别长度必须介于 0 和 4 之间")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Length(min = 0, max = 20, message = "联系电话长度必须介于 0 和 20 之间")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}