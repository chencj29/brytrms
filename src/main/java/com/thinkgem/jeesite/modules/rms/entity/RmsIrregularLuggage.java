/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 不正常行李表Entity
 * @author wjp
 * @version 2016-02-03
 */
public class RmsIrregularLuggage extends DataEntity<RmsIrregularLuggage> {
	
	private static final long serialVersionUID = 1L;
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String flightNum;		// 航班号
	private String inoutTypeCode;		// 进出港类型编码
	private String inoutTypeName;		// 进出港类型名称
	private String irregularLuggageTypeCode;		// 不正常行李类别编号
	private String irregularLuggageTypeName;		// 不正常行李类别名称
	private String irregularCause;		// 不正常原因描述
	private String processingResult;		// 处理结果
	private Date processingTime;		// 处理日期
	private String redundance1;		// 冗余1
	private String redundance2;		// 冗余2
	private String redundance3;		// 冗余3
	private String redundance4;		// 冗余4
	private String redundance5;		// 冗余5
	
	public RmsIrregularLuggage() {
		super();
	}

	public RmsIrregularLuggage(String id){
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
	
	@Length(min=0, max=36, message="航班号长度必须介于 0 和 36 之间")
	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}
	
	@Length(min=0, max=36, message="进出港类型编码长度必须介于 0 和 36 之间")
	public String getInoutTypeCode() {
		return inoutTypeCode;
	}

	public void setInoutTypeCode(String inoutTypeCode) {
		this.inoutTypeCode = inoutTypeCode;
	}
	
	@Length(min=0, max=100, message="进出港类型名称长度必须介于 0 和 100 之间")
	public String getInoutTypeName() {
		return inoutTypeName;
	}

	public void setInoutTypeName(String inoutTypeName) {
		this.inoutTypeName = inoutTypeName;
	}
	
	@Length(min=0, max=36, message="不正常行李类别编号长度必须介于 0 和 36 之间")
	public String getIrregularLuggageTypeCode() {
		return irregularLuggageTypeCode;
	}

	public void setIrregularLuggageTypeCode(String irregularLuggageTypeCode) {
		this.irregularLuggageTypeCode = irregularLuggageTypeCode;
	}
	
	@Length(min=0, max=300, message="不正常行李类别名称长度必须介于 0 和 300 之间")
	public String getIrregularLuggageTypeName() {
		return irregularLuggageTypeName;
	}

	public void setIrregularLuggageTypeName(String irregularLuggageTypeName) {
		this.irregularLuggageTypeName = irregularLuggageTypeName;
	}
	
	@Length(min=0, max=500, message="不正常原因描述长度必须介于 0 和 500 之间")
	public String getIrregularCause() {
		return irregularCause;
	}

	public void setIrregularCause(String irregularCause) {
		this.irregularCause = irregularCause;
	}
	
	@Length(min=0, max=2000, message="处理结果长度必须介于 0 和 2000 之间")
	public String getProcessingResult() {
		return processingResult;
	}

	public void setProcessingResult(String processingResult) {
		this.processingResult = processingResult;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8:00")
	public Date getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTime(Date processingTime) {
		this.processingTime = processingTime;
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