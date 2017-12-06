/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.EmpSchedulingService;

import java.util.Date;

/**
 * 工作人员排班管理Entity
 *
 * @author xiaopo
 * @version 2016-05-19
 */

@Monitor(desc = "工作人员排班管理信息", tableName = "rms_emp_scheduling", service = EmpSchedulingService.class, socketNS = "/rms/empScheduling")
public class EmpScheduling extends DataEntity<EmpScheduling> {

    private static final long serialVersionUID = 1L;
    @Label("update_create")
    @MonitorField(desc = "update_create")
    private Date updateCreate;        // update_create
    @Label("部门ID")
    @MonitorField(desc = "部门ID")
    private String deptId;        // 部门ID
    @Label("部门名字")
    @MonitorField(desc = "部门名字")
    private String deptName;        // 部门名字
    @Label("岗位ID")
    @MonitorField(desc = "岗位ID")
    private String postId;        // 岗位ID
    @Label("岗位名称")
    @MonitorField(desc = "岗位名称")
    private String postName;        // 岗位名称
    @Label("班组ID")
    @MonitorField(desc = "班组ID")
    private String groupId;        // 班组ID
    @Label("班组名称")
    @MonitorField(desc = "班组名称")
    private String groupName;        // 班组名称
    @Label("工号")
    @MonitorField(desc = "工号")
    private String jobNum;        // 工号
    @Label("工作人员名称")
    @MonitorField(desc = "工作人员名称")
    private String empName;        // 工作人员名称
    @Label("业务性质名称")
    @MonitorField(desc = "业务性质名称")
    private String natureName;        // 业务性质名称
    @Label("排班开始时间")
    @MonitorField(desc = "排班开始时间")
    private Date dutyStartTime;        // 排班开始时间
    @Label("排班结束时间")
    @MonitorField(desc = "排班结束时间")
    private Date dutyEndTime;        // 排班结束时间
    @Label("工作类别")
    @MonitorField(desc = "工作类别")
    private String workingType;     // 工作类别
    @Label("工作地点")
    @MonitorField(desc = "工作地点")
    private String workingArea;     // 工作地点
    @Label("其他工作地点")
    @MonitorField(desc = "其他工作地点")
    private String otherWorkingArea;    // 其他工作地点
    @Label("实际开始时间")
    @MonitorField(desc = "实际开始时间")
    private Date actualStartTime;       //实际开始时间
    @Label("实际结束时间")
    @MonitorField(desc = "实际结束时间")
    private Date actualEndTime;         //实际结束时间
    @Label("工作类型ID")
    @MonitorField(desc = "工作类型ID")
    private String workingTypeId;       //工作类型ID
    @Label("工作地点ID")
    @MonitorField(desc = "工作地点ID")
    private String workingAreaId;       //工作地点ID

    public EmpScheduling() {
        super();
    }

    public EmpScheduling(String id) {
        super(id);
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getUpdateCreate() {
        return updateCreate;
    }

    public void setUpdateCreate(Date updateCreate) {
        this.updateCreate = updateCreate;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getNatureName() {
        return natureName;
    }

    public void setNatureName(String natureName) {
        this.natureName = natureName;
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

    public String getWorkingArea() {
        return workingArea;
    }

    public void setWorkingArea(String workingArea) {
        this.workingArea = workingArea;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getWorkingType() {
        return workingType;
    }

    public void setWorkingType(String workingType) {
        this.workingType = workingType;
    }

    public String getOtherWorkingArea() {
        return otherWorkingArea;
    }

    public void setOtherWorkingArea(String otherWorkingArea) {
        this.otherWorkingArea = otherWorkingArea;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public String getWorkingTypeId() {
        return workingTypeId;
    }

    public void setWorkingTypeId(String workingTypeId) {
        this.workingTypeId = workingTypeId;
    }

    public String getWorkingAreaId() {
        return workingAreaId;
    }

    public void setWorkingAreaId(String workingAreaId) {
        this.workingAreaId = workingAreaId;
    }
}