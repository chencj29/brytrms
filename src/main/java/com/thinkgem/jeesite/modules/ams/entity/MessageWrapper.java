package com.thinkgem.jeesite.modules.ams.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaopo
 * @date 16/5/12
 */
public class MessageWrapper implements Serializable {


	private static final long serialVersionUID = -8016302549932959164L;

	private String id;
	private String logId;
	private String send_id;
	private String send_name;
	private String message;
	private Integer message_type;
	private String message_group;
	private Date post_date;
	private String rec_id;
	private String rec_name;
	private Integer status;


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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone="GMT+8:00")
	public Date getPost_date() {
		return post_date;
	}

	public void setPost_date(Date post_date) {
		this.post_date = post_date;
	}

	public String getRec_id() {
		return rec_id;
	}

	public void setRec_id(String rec_id) {
		this.rec_id = rec_id;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}
}

