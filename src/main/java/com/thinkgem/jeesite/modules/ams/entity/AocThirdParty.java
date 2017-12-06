/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.ams.service.AocThirdPartyService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 空管动态变更表Entity
 * @author wjp
 * @version 2017-05-22
 */

@Monitor(desc = "空管动态变更表信息", tableName = "aoc_third_party", service = AocThirdPartyService.class , socketNS = "/rms/aocThirdParty")
public class AocThirdParty extends DataEntity<AocThirdParty> {
	
	private static final long serialVersionUID = 1L;
	@MonitorField(desc = "航空公司")
	private String flightCompanyCode;		// 航空公司
	@MonitorField(desc = "航班号")
	private String flightNum;		// 航班号
	@MonitorField(desc = "变更时间")
	private Date updateTime;		// 变更时间
	@MonitorField(desc = "消息发送时间")
	private Date sendTime;		// 消息发送时间
	@MonitorField(desc = "更新时间类型")
	private String updateType;		//更新时间类型
	@MonitorField(desc = "调用方法")
	private String method;		// 调用方法
	@MonitorField(desc = "动作描述")
	private String description;		// 动作描述
	@MonitorField(desc = "审核意见")
	private String adviceFlag;		// 审核意见
	@MonitorField(desc = "审核人")
	private String daviceBy;		// 审核人
	@MonitorField(desc = "匹配的航班动态")
	private String ext1;		// 匹配的航班动态json
	@MonitorField(desc = "选择的航班动态")
	private String ext2;		// 选择的航班动态
	@MonitorField(desc = "保障进程的json")
	private String ext3;		// 扩展3  保障进程的json
	
	public AocThirdParty() {
		super();
	}

	public AocThirdParty(String id){
		super(id);
	}

	@Length(min=0, max=4000, message="航空公司长度必须介于 0 和 4000 之间")
	public String getFlightCompanyCode() {
		return flightCompanyCode;
	}

	public void setFlightCompanyCode(String flightCompanyCode) {
		this.flightCompanyCode = flightCompanyCode;
	}
	
	@Length(min=0, max=20, message="航班号长度必须介于 0 和 20 之间")
	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	@Length(min=0, max=50, message="调用方法长度必须介于 0 和 50 之间")
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	@Length(min=0, max=2000, message="动作描述长度必须介于 0 和 2000 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=10, message="审核意见长度必须介于 0 和 10 之间")
	public String getAdviceFlag() {
		return adviceFlag;
	}

	public void setAdviceFlag(String adviceFlag) {
		this.adviceFlag = adviceFlag;
	}
	
	@Length(min=0, max=36, message="审核人长度必须介于 0 和 36 之间")
	public String getDaviceBy() {
		return daviceBy;
	}

	public void setDaviceBy(String daviceBy) {
		this.daviceBy = daviceBy;
	}
	
	@Length(min=0, max=4000, message="扩展1长度必须介于 0 和 4000 之间")
	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	
	@Length(min=0, max=2000, message="扩展2长度必须介于 0 和 2000 之间")
	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	
	@Length(min=0, max=2000, message="扩展3长度必须介于 0 和 2000 之间")
	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	
}