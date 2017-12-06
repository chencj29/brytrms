/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 预警提醒Entity
 * @author xiaowu
 * @version 2016-01-19
 */
public class WarnRemindConfig extends DataEntity<WarnRemindConfig> {
	
	private static final long serialVersionUID = 1L;
	private String configName;		// 名称
	private String configType;		// 类型
	private String actionService;		// 执行动作
	private Long configAvailable;		// 是否启用
	private String monitorClass;		// 监控实体
	private String messageTpl;		// 消息模板
	private List<WarnRemindConfigItem> warnRemindConfigItemList = Lists.newArrayList();		// 子表列表
	
	public WarnRemindConfig() {
		super();
	}

	public WarnRemindConfig(String id){
		super(id);
	}

	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}
	
	@NotNull(message="类型不能为空")
	public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}
	
	@Length(min=1, max=50, message="执行动作长度必须介于 1 和 50 之间")
	public String getActionService() {
		return actionService;
	}

	public void setActionService(String actionService) {
		this.actionService = actionService;
	}
	
	@NotNull(message="是否启用不能为空")
	public Long getConfigAvailable() {
		return configAvailable;
	}

	public void setConfigAvailable(Long configAvailable) {
		this.configAvailable = configAvailable;
	}
	
	@Length(min=1, max=100, message="监控实体长度必须介于 1 和 100 之间")
	public String getMonitorClass() {
		return monitorClass;
	}

	public void setMonitorClass(String monitorClass) {
		this.monitorClass = monitorClass;
	}
	
	@Length(min=0, max=1000, message="消息模板长度必须介于 0 和 1000 之间")
	public String getMessageTpl() {
		return messageTpl;
	}

	public void setMessageTpl(String messageTpl) {
		this.messageTpl = messageTpl;
	}
	
	public List<WarnRemindConfigItem> getWarnRemindConfigItemList() {
		return warnRemindConfigItemList;
	}

	public void setWarnRemindConfigItemList(List<WarnRemindConfigItem> warnRemindConfigItemList) {
		this.warnRemindConfigItemList = warnRemindConfigItemList;
	}
}