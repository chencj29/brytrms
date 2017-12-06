/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.RmsEmpVacationService;

import java.util.Date;

/**
 * 工作人员请假管理Entity
 *
 * @author xiaopo
 * @version 2016-05-18
 */

@Monitor(desc = "工作人员请假管理信息", tableName = "rms_emp_vacation", service = RmsEmpVacationService.class, socketNS = "/rms/rmsEmpVacation")
public class RmsEmpVacation extends DataEntity<RmsEmpVacation> {

    private static final long serialVersionUID = 1L;
    @Label("部门ID")
    @MonitorField(desc = "部门ID")
    private String deptId;        // 部门ID
    @Label("部门名称")
    @MonitorField(desc = "部门名称")
    private String deptName;        // 部门名称
    @Label("岗位ID")
    @MonitorField(desc = "岗位ID")
    private String postId;        // 岗位ID
    @Label("岗位名称")
    @MonitorField(desc = "岗位名称")
    private String postName;        // 岗位名称
    @Label("工作人员姓名")
    @MonitorField(desc = "工作人员姓名")
    private String empName;        // 工作人员姓名
    @Label("工号")
    @MonitorField(desc = "工号")
    private String jobNum;        // 工号
    @Label("请假开始时间")
    @MonitorField(desc = "请假开始时间")
    private Date startTime;        // 请假开始时间
    @Label("请假结束时间")
    @MonitorField(desc = "请假结束时间")
    private Date endTime;        // 请假开始时间
    @Label("请假类型")
    @MonitorField(desc = "请假类型")
    private String vacationTpye;        // 请假类型
    @Label("请假事由")
    @MonitorField(desc = "请假事由")
    private String vacationReason;        // 请假事由
    @Label("备注")
    @MonitorField(desc = "备注")
    private String comments;        // 备注

    public RmsEmpVacation() {
        super();
    }

    public RmsEmpVacation(String id) {
        super(id);
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getVacationTpye() {
        return vacationTpye;
    }

    public void setVacationTpye(String vacationTpye) {
        this.vacationTpye = vacationTpye;
    }

    public String getVacationReason() {
        return vacationReason;
    }

    public void setVacationReason(String vacationReason) {
        this.vacationReason = vacationReason;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}