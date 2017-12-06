package com.thinkgem.jeesite.modules.ams.entity;

import java.util.Date;

/**
 * @author chencheng
 * @ClassName: LongPlanVO
 * @Description: 长期计划VO
 * @date 2016年1月4日 下午1:15:06
 */
public class LongPlanVO extends LongPlan {

    /**
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 4071995644115559654L;

    /**
     * 经停地
     */
    private String longPlanStopStr;

    private Date applyDate; // 计划应用日期，用于查询传值

    public String getLongPlanStopStr() {
        return longPlanStopStr;
    }

    public void setLongPlanStopStr(String longPlanStopStr) {
        this.longPlanStopStr = longPlanStopStr;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

}
