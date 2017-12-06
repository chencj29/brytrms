package com.thinkgem.jeesite.common.easyui.wrapper;

import java.util.List;
import java.util.Map;

/**
 * EasyUI Tree 数据包装器
 * 
 */
public class TreeNode {
	private String id;
	private String pid ;
	private String text;
	private Map<String, Object> attributes;
	private List children;
	private String iconCls;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}


	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
}
