/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.DutyHistoryService;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 配载工作人员值班记录Entity
 *
 * @author wjp
 * @version 2016-03-21
 */

@Monitor(desc = "配载工作人员值班记录信息", tableName = "rms_duty_history", service = DutyHistoryService.class, socketNS = "/rms/dutyHistory")
public class DutyHistory extends DataEntity<DutyHistory> {

    private static final long serialVersionUID = 1L;
    @Label("创建时间")
    @MonitorField(desc = "创建时间")
    private Date createTime;        // 创建时间
    @Label("更新时间")
    @MonitorField(desc = "更新时间")
    private Date updateTime;        // 更新时间
    @Label("值班日期")
    @MonitorField(desc = "值班日期")
    private Date dutyDate;        // 值班日期
    @Label("所在部门")
    @MonitorField(desc = "所在部门")
    private String dutyDept;        // 所在部门
    @Label("岗位")
    @MonitorField(desc = "岗位")
    private String postName;        // 岗位
    @Label("岗位ID")
    @MonitorField(desc = "岗位ID")
    private String postId;        // 岗位ID
    @Label("值班人员")
    @MonitorField(desc = "值班人员")
    private String dutyPersonal;        // 值班人员
    @Label("值班开始时间")
    @MonitorField(desc = "值班开始时间")
    private Date dutyStartTime;        // 值班开始时间
    @Label("值班结束时间")
    @MonitorField(desc = "值班结束时间")
    private Date dutyEndTime;        // 值班结束时间

    public DutyHistory() {
        super();
    }

    public DutyHistory(String id) {
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

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8:00")
    @NotNull(message = "值班日期不能为空")
    public Date getDutyDate() {
        return dutyDate;
    }

    public void setDutyDate(Date dutyDate) {
        this.dutyDate = dutyDate;
    }

    @Length(min = 0, max = 36, message = "所在部门长度必须介于 0 和 36 之间")
    public String getDutyDept() {
        return dutyDept;
    }

    public void setDutyDept(String dutyDept) {
        this.dutyDept = dutyDept;
    }

    @Length(min = 0, max = 50, message = "岗位长度必须介于 0 和 50 之间")
    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    @Length(min = 0, max = 36, message = "岗位ID长度必须介于 0 和 36 之间")
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    @Length(min = 0, max = 36, message = "值班人员长度必须介于 0 和 36 之间")
    public String getDutyPersonal() {
        return dutyPersonal;
    }

    public void setDutyPersonal(String dutyPersonal) {
        this.dutyPersonal = dutyPersonal;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getDutyStartTime() {
        return dutyStartTime;
    }

    public void setDutyStartTime(Date dutyStartTime) {
        this.dutyStartTime = dutyStartTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getDutyEndTime() {
        return dutyEndTime;
    }

    public void setDutyEndTime(Date dutyEndTime) {
        this.dutyEndTime = dutyEndTime;
    }

}