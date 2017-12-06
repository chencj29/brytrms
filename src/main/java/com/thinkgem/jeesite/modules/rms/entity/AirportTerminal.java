/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.AirportTerminalService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 航站楼信息Entity
 * @author hxx
 * @version 2016-01-26
 */
@Monitor(desc = " 航站楼信息", tableName = "ams_airport_terminal", service = AirportTerminalService.class, socketNS = "/ams/airportTerminal")
public class AirportTerminal extends DataEntity<AirportTerminal> {
	
	private static final long serialVersionUID = 1L;
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	@Label("航站楼名称")
	@MonitorField(desc = "航站楼名称")
	private String terminalName;		// 航站楼名称
	@Label("航站楼编码")
	@MonitorField(desc = "航站楼编码")
	private String terminalCode;		// 航站楼编码
	
	public AirportTerminal() {
		super();
	}

	public AirportTerminal(String id){
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
	
	@Length(min=0, max=100, message="航站楼名称长度必须介于 0 和 100 之间")
	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}
	
	@Length(min=0, max=200, message="航站楼编码长度必须介于 0 和 200 之间")
	public String getTerminalCode() {
		return terminalCode;
	}

	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}
	
}