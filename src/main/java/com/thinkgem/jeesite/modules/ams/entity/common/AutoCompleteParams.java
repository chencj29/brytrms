package com.thinkgem.jeesite.modules.ams.entity.common;

import java.io.Serializable;

/**
 * Created by chencheng on 15/12/24.
 * 自动完成检索条件
 */
public class AutoCompleteParams implements Serializable {
	
    /** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */ 
	private static final long serialVersionUID = 207558699005298743L;
	/** 一次显示检索结果个数 */
    private Integer limit;
    /** 检索条件 */
    private String q;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}

