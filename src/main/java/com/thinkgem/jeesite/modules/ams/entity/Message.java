package com.thinkgem.jeesite.modules.ams.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * @author xiaopo
 * @date 16/5/12
 */
public class Message extends DataEntity<Message> {


	private String send_id;
	private String send_name;
	private String message;
	private Integer message_type;
	private String message_group;
	private Date post_date;


	public String getSend_id() {
		return send_id;
	}

	public void setSend_id(String send_id) {
		this.send_id = send_id;
	}

	public String getSend_name() {
		return send_name;
	}

	public void setSend_name(String send_name) {
		this.send_name = send_name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getMessage_type() {
		return message_type;
	}

	public void setMessage_type(Integer message_type) {
		this.message_type = message_type;
	}

	public String getMessage_group() {
		return message_group;
	}

	public void setMessage_group(String message_group) {
		this.message_group = message_group;
	}

	public Date getPost_date() {
		return post_date;
	}

	public void setPost_date(Date post_date) {
		this.post_date = post_date;
	}
}

