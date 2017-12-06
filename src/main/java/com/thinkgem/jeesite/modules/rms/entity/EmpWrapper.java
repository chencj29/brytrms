/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.io.Serializable;
import java.util.List;

public class EmpWrapper extends DataEntity<EmpWrapper> {

	private static final long serialVersionUID = 1L;
	private String dept;        // 部门
	private String post;        // 岗位
	private String empName;        // 姓名
	private String sex;        // 性别
	private String duty;        // 职务
	private String skill;        // 业务技能
	private String aircraftTerminalCode;        // 航站楼
	private String deptId;        // 部门编码
	private String postId;        // 岗位编码
	private String dutyGroup;  // 班组
	private String jobNum;  // 工号

	private Boolean _isAllocation; //是否分配班组

	private String _skill; //工作内容 业务性质

	private List<RmsEmpVacation> _empVacationList; // 请假次数

	public EmpWrapper() {
		super();
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getAircraftTerminalCode() {
		return aircraftTerminalCode;
	}

	public void setAircraftTerminalCode(String aircraftTerminalCode) {
		this.aircraftTerminalCode = aircraftTerminalCode;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

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

	public String get_skill() {
		return _skill;
	}

	public void set_skill(String _skill) {
		this._skill = _skill;
	}

	public List<RmsEmpVacation> get_empVacationList() {
		return _empVacationList;
	}

	public void set_empVacationList(List<RmsEmpVacation> _empVacationList) {
		this._empVacationList = _empVacationList;
	}
}