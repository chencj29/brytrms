/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import java.util.Date;

import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.thinkgem.jeesite.modules.rms.service.CheckinCounterBasicService;

/**
 * 值机柜台基础信息表(基础专用)Entity
 * @author wjp
 * @version 2017-09-22
 */

@Monitor(desc = "值机柜台基础信息表(基础专用)信息", tableName = "rms_checkin_counter_basic", service = CheckinCounterBasicService.class , socketNS = "/rms/checkinCounterBasic")
public class CheckinCounterBasic extends DataEntity<CheckinCounterBasic> {
	
	private static final long serialVersionUID = 1L;
	@Label("创建时间")
	@MonitorField(desc = "创建时间")
	private Date createTime;		// 创建时间
	@Label("更新时间")
	@MonitorField(desc = "更新时间")
	private Date updateTime;		// 更新时间
	@Label("航站楼编号")
	@MonitorField(desc = "航站楼编号")
	private String airportTerminalCode;		// 航站楼编号
	@Label("航站楼名称")
	@MonitorField(desc = "航站楼名称")
	private String airportTerminalName;		// 航站楼名称
	@Label("值机柜台编号")
	@MonitorField(desc = "值机柜台编号")
	private String checkinCounterNum;		// 值机柜台编号
	@Label("值机柜台性质")
	@MonitorField(desc = "值机柜台性质")
	private String checkinCounterNature;		// 值机柜台性质
	@Label("值机柜台状态")
	@MonitorField(desc = "值机柜台状态")
	private String checkinCounterTypeCode;		// 值机柜台状态
	@Label("值机柜台状态名称")
	@MonitorField(desc = "值机柜台状态名称")
	private String checkinCounterTypeName;		// 值机柜台状态名称
	@Label("排序字段")
	@MonitorField(desc = "排序字段")
	private Long orderIndex;		// 排序字段
	
	public CheckinCounterBasic() {
		super();
	}

	public CheckinCounterBasic(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8:00")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8:00")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getAirportTerminalCode() {
		return airportTerminalCode;
	}

	public void setAirportTerminalCode(String airportTerminalCode) {
		this.airportTerminalCode = airportTerminalCode;
	}
	
	public String getAirportTerminalName() {
		return airportTerminalName;
	}

	public void setAirportTerminalName(String airportTerminalName) {
		this.airportTerminalName = airportTerminalName;
	}
	
	public String getCheckinCounterNum() {
		return checkinCounterNum;
	}

	public void setCheckinCounterNum(String checkinCounterNum) {
		this.checkinCounterNum = checkinCounterNum;
	}
	
	public String getCheckinCounterNature() {
		return checkinCounterNature;
	}

	public void setCheckinCounterNature(String checkinCounterNature) {
		this.checkinCounterNature = checkinCounterNature;
	}
	
	public String getCheckinCounterTypeCode() {
		return checkinCounterTypeCode;
	}

	public void setCheckinCounterTypeCode(String checkinCounterTypeCode) {
		this.checkinCounterTypeCode = checkinCounterTypeCode;
	}
	
	public String getCheckinCounterTypeName() {
		return checkinCounterTypeName;
	}

	public void setCheckinCounterTypeName(String checkinCounterTypeName) {
		this.checkinCounterTypeName = checkinCounterTypeName;
	}
	
	public Long getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Long orderIndex) {
		this.orderIndex = orderIndex;
	}
	
}