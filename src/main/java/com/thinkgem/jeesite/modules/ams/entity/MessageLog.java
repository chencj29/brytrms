package com.thinkgem.jeesite.modules.ams.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * @author xiaopo
 * @date 16/5/12
 */
public class MessageLog extends DataEntity<MessageLog> {
	private String rec_id;
	private String rec_name;
	private String message_id;
	private Integer status;

	public String getRec_id() {
		return rec_id;
	}

	public void setRec_id(String rec_id) {
		this.rec_id = rec_id;
	}

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRec_name() {
		return rec_name;
	}

	public void setRec_name(String rec_name) {
		this.rec_name = rec_name;
	}
}
