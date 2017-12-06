package com.thinkgem.jeesite.modules.rms.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chencheng on 16/3/15.
 */

/**
 * "id":1,
 * "text":"Folder1",
 * "children":[{
 * "text":"File1",
 * "checked":true
 * },{
 */
public class TreeVO implements Serializable {

    private static final long serialVersionUID = 8937140243010106061L;

    private String id;

    private String text;

    private String state = "open";

    private Map<String, Object> params = new HashMap<String, Object>();

    private List<TreeVO> children = new ArrayList<TreeVO>();

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

    public List<TreeVO> getChildren() {
        return children;
    }

    public void setChildren(List<TreeVO> children) {
        this.children = children;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
