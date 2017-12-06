/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.RmsEmpService;
import org.hibernate.validator.constraints.Length;

/**
 * 工作人员基础信息Entity
 *
 * @author wjp
 * @version 2016-03-09
 */

@Monitor(desc = "工作人员基础信息", tableName = "rms_emp", service = RmsEmpService.class, socketNS = "/rms/rmsEmp")
public class RmsEmp extends DataEntity<RmsEmp> {

    private static final long serialVersionUID = 1L;
    @Label("部门")
    @MonitorField(desc = "部门")
    private String dept;        // 部门
    @Label("岗位")
    @MonitorField(desc = "岗位")
    private String post;        // 岗位
    @Label("姓名")
    @MonitorField(desc = "姓名")
    private String empName;        // 姓名
    @Label("性别")
    @MonitorField(desc = "性别")
    private String sex;        // 性别
    @Label("职务")
    @MonitorField(desc = "职务")
    private String duty;        // 职务
    @Label("业务技能")
    @MonitorField(desc = "业务技能")
    private String skill;        // 业务技能
    @Label("航站楼")
    @MonitorField(desc = "航站楼")
    private String aircraftTerminalCode;        // 航站楼
    @Label("部门编码")
    @MonitorField(desc = "部门编码")
    private String deptId;        // 部门编码
    @Label("岗位编码")
    @MonitorField(desc = "岗位编码")
    private String postId;        // 岗位编码
    @Label("班组")
    @MonitorField(desc = "班组")
    private String dutyGroup;  // 班组

    @Label("工号")
    @MonitorField(desc = "工号")
    private String jobNum;  // 工号

    private Boolean _isAllocation; //是否分配班组

    public RmsEmp() {
        super();
    }

    public RmsEmp(String id) {
        super(id);
    }

    @Length(min = 0, max = 36, message = "部门长度必须介于 0 和 20 之间")
    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Length(min = 0, max = 36, message = "岗位长度必须介于 0 和 20 之间")
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Length(min = 0, max = 20, message = "姓名长度必须介于 0 和 20 之间")
    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    @Length(min = 0, max = 2, message = "性别长度必须介于 0 和 2 之间")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Length(min = 0, max = 20, message = "职务长度必须介于 0 和 20 之间")
    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    @Length(min = 0, max = 20, message = "业务技能长度必须介于 0 和 20 之间")
    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Length(min = 0, max = 10, message = "航站楼长度必须介于 0 和 10 之间")
    public String getAircraftTerminalCode() {
        return aircraftTerminalCode;
    }

    public void setAircraftTerminalCode(String aircraftTerminalCode) {
        this.aircraftTerminalCode = aircraftTerminalCode;
    }

    @Length(min = 0, max = 36, message = "部门编码长度必须介于 0 和 36 之间")
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Length(min = 0, max = 36, message = "岗位编码长度必须介于 0 和 36 之间")
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getDutyGroup() {
        return dutyGroup;
    }

    public void setDutyGroup(String dutyGroup) {
        this.dutyGroup = dutyGroup;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public Boolean get_isAllocation() {
        return _isAllocation;
    }

    public void set_isAllocation(Boolean _isAllocation) {
        this._isAllocation = _isAllocation;
    }
}