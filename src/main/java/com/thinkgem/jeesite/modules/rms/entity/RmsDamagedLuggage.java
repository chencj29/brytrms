/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 损坏行李表Entity
 * @author wjp
 * @version 2016-02-03
 */
public class RmsDamagedLuggage extends DataEntity<RmsDamagedLuggage> {
	
	private static final long serialVersionUID = 1L;
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String flightNum;		// 航班号
	private String name;		// 姓名
	private String luggageName;		// 行李名称
	private String damagedConditionCode;		// 破损程度编号
	private String damagedConditionName;		// 破损程度名称
	private String damagedPart;		// 破损部位描述
	private Double luggagePrice;		// 购买行李价格
	private String purchasePrice;		// 购买地点
	private String isreceipt;		// 是否有发票
	private String processingMode;		// 处理方式
	private Double indemnify;		// 赔偿金额
	private String redundance1;		// 冗余1
	private String redundance2;		// 冗余2
	private String redundance3;		// 冗余3
	private String redundance4;		// 冗余4
	private String redundance5;		// 冗余5
	
	public RmsDamagedLuggage() {
		super();
	}

	public RmsDamagedLuggage(String id){
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
	
	@Length(min=0, max=36, message="航班号长度必须介于 0 和 36 之间")
	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}
	
	@Length(min=0, max=36, message="姓名长度必须介于 0 和 36 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=36, message="行李名称长度必须介于 0 和 36 之间")
	public String getLuggageName() {
		return luggageName;
	}

	public void setLuggageName(String luggageName) {
		this.luggageName = luggageName;
	}
	
	@Length(min=0, max=36, message="破损程度编号长度必须介于 0 和 36 之间")
	public String getDamagedConditionCode() {
		return damagedConditionCode;
	}

	public void setDamagedConditionCode(String damagedConditionCode) {
		this.damagedConditionCode = damagedConditionCode;
	}
	
	@Length(min=0, max=500, message="破损程度名称长度必须介于 0 和 500 之间")
	public String getDamagedConditionName() {
		return damagedConditionName;
	}

	public void setDamagedConditionName(String damagedConditionName) {
		this.damagedConditionName = damagedConditionName;
	}
	
	@Length(min=0, max=100, message="破损部位描述长度必须介于 0 和 100 之间")
	public String getDamagedPart() {
		return damagedPart;
	}

	public void setDamagedPart(String damagedPart) {
		this.damagedPart = damagedPart;
	}
	
	public Double getLuggagePrice() {
		return luggagePrice;
	}

	public void setLuggagePrice(Double luggagePrice) {
		this.luggagePrice = luggagePrice;
	}
	
	@Length(min=0, max=300, message="购买地点长度必须介于 0 和 300 之间")
	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	@Length(min=0, max=2, message="是否有发票长度必须介于 0 和 2 之间")
	public String getIsreceipt() {
		return isreceipt;
	}

	public void setIsreceipt(String isreceipt) {
		this.isreceipt = isreceipt;
	}
	
	@Length(min=0, max=2000, message="处理方式长度必须介于 0 和 2000 之间")
	public String getProcessingMode() {
		return processingMode;
	}

	public void setProcessingMode(String processingMode) {
		this.processingMode = processingMode;
	}
	
	public Double getIndemnify() {
		return indemnify;
	}

	public void setIndemnify(Double indemnify) {
		this.indemnify = indemnify;
	}
	
	@Length(min=0, max=10, message="冗余1长度必须介于 0 和 10 之间")
	public String getRedundance1() {
		return redundance1;
	}

	public void setRedundance1(String redundance1) {
		this.redundance1 = redundance1;
	}
	
	@Length(min=0, max=10, message="冗余2长度必须介于 0 和 10 之间")
	public String getRedundance2() {
		return redundance2;
	}

	public void setRedundance2(String redundance2) {
		this.redundance2 = redundance2;
	}
	
	@Length(min=0, max=10, message="冗余3长度必须介于 0 和 10 之间")
	public String getRedundance3() {
		return redundance3;
	}

	public void setRedundance3(String redundance3) {
		this.redundance3 = redundance3;
	}
	
	@Length(min=0, max=10, message="冗余4长度必须介于 0 和 10 之间")
	public String getRedundance4() {
		return redundance4;
	}

	public void setRedundance4(String redundance4) {
		this.redundance4 = redundance4;
	}
	
	@Length(min=0, max=10, message="冗余5长度必须介于 0 和 10 之间")
	public String getRedundance5() {
		return redundance5;
	}

	public void setRedundance5(String redundance5) {
		this.redundance5 = redundance5;
	}
	
}