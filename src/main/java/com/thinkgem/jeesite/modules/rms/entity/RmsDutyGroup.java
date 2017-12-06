/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.RmsDutyGroupService;

import java.util.List;

/**
 * 岗位班组管理Entity
 *
 * @author xiaopo
 * @version 2016-05-19
 */

@Monitor(desc = "岗位班组管理信息", tableName = "rms_duty_group", service = RmsDutyGroupService.class, socketNS = "/rms/rmsDutyGroup")
public class RmsDutyGroup extends DataEntity<RmsDutyGroup> {

	private static final long serialVersionUID = 1L;
	@Label("岗位ID")
	@MonitorField(desc = "岗位ID")
	private String postId;        // 岗位ID
	@Label("岗位名称")
	@MonitorField(desc = "岗位名称")
	private String postName;        // 岗位名称
	@Label("班组名称")
	@MonitorField(desc = "班组名称")
	private String groupName;        // 班组名称
	@Label("开始时间")
	@MonitorField(desc = "开始时间")
	private String startTime;        // 开始时间
	@Label("结束时间")
	@MonitorField(desc = "结束时间")
	private String endTime;        // 结束时间
	@Label("持续天数")
	@MonitorField(desc = "持续天数")
	private Long period;        // 持续天数
	@Label("班组人数")
	@MonitorField(desc = "班组人数")
	private Long workerCount;        // 班组人数

	private String _groupName; //

	private List<EmpWrapper> _allocationEmpList; // 该班组分配的员工

	public RmsDutyGroup() {
		super();
	}

	public RmsDutyGroup(String id) {
		super(id);
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long getPeriod() {
		return period;
	}

	public void setPeriod(Long period) {
		this.period = period;
	}

	public Long getWorkerCount() {
		return workerCount;
	}

	public void setWorkerCount(Long workerCount) {
		this.workerCount = workerCount;
	}

	public List<EmpWrapper> get_allocationEmpList() {
		return _allocationEmpList;
	}

	public void set_allocationEmpList(List<EmpWrapper> _allocationEmpList) {
		this._allocationEmpList = _allocationEmpList;
	}

	public String get_groupName() {
		this._groupName = this.groupName + "(" + this.getPostName() + ")";
		return _groupName;
	}

	public void set_groupName(String _groupName) {
		this._groupName = _groupName;
	}
}