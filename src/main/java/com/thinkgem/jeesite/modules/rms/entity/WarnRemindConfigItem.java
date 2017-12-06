/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 预警提醒Entity
 * @author xiaowu
 * @version 2016-01-19
 */
public class WarnRemindConfigItem extends DataEntity<WarnRemindConfigItem> {
	
	private static final long serialVersionUID = 1L;
	private WarnRemindConfig config;		// 主表主键 父类
	private String fieldName;		// 字段描述
	private String fieldCode;		// 字段代码
	private String conditionName;		// 条件描述
	private String conditionCode;		// 条件代码
	private String thresholdValue;		// 条件阀值
	private Long itemPriority;		// 优先级
	private Long itemAvailable;		// 是否可用
	
	public WarnRemindConfigItem() {
		super();
	}

	public WarnRemindConfigItem(String id){
		super(id);
	}

	public WarnRemindConfigItem(WarnRemindConfig config){
		this.config = config;
	}

	@NotNull(message="主表主键不能为空")
	public WarnRemindConfig getConfig() {
		return config;
	}

	public void setConfig(WarnRemindConfig config) {
		this.config = config;
	}
	
	@Length(min=1, max=100, message="字段描述长度必须介于 1 和 100 之间")
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	@Length(min=1, max=88, message="字段代码长度必须介于 1 和 88 之间")
	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}
	
	@Length(min=0, max=100, message="条件描述长度必须介于 0 和 100 之间")
	public String getConditionName() {
		return conditionName;
	}

	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}
	
	@Length(min=0, max=100, message="条件代码长度必须介于 0 和 100 之间")
	public String getConditionCode() {
		return conditionCode;
	}

	public void setConditionCode(String conditionCode) {
		this.conditionCode = conditionCode;
	}
	
	@Length(min=0, max=200, message="条件阀值长度必须介于 0 和 200 之间")
	public String getThresholdValue() {
		return thresholdValue;
	}

	public void setThresholdValue(String thresholdValue) {
		this.thresholdValue = thresholdValue;
	}
	
	@NotNull(message="优先级不能为空")
	public Long getItemPriority() {
		return itemPriority;
	}

	public void setItemPriority(Long itemPriority) {
		this.itemPriority = itemPriority;
	}
	
	@NotNull(message="是否可用不能为空")
	public Long getItemAvailable() {
		return itemAvailable;
	}

	public void setItemAvailable(Long itemAvailable) {
		this.itemAvailable = itemAvailable;
	}
	
}