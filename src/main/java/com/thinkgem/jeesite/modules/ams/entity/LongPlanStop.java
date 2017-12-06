/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 长期计划管理Entity
 *
 * @author xiaopo
 * @version 2015-12-22
 */
public class LongPlanStop extends DataEntity<LongPlanStop> {

    private static final long serialVersionUID = 1L;
    private LongPlan LongPlan; // 长期计划ID 父类
    //    @TipMainField
    //    @MonitorField(desc = "航站ID")
    private String stopPointId;   // 航站ID
    //    @MonitorField(desc = "经停站编号")
    private String stopPointCode; // 经停站编号
    //    @MonitorField(desc = "经停站名称")
    private String stopPointName; // 经停站名称
    //    @MonitorField(desc = "离站时间")
    private String stopPointSTime; // 离站时间
    //    @MonitorField(desc = "到站时间")
    private String stopPointETime; // 到站时间到站时间

    public LongPlanStop() {
        super();
    }

    public LongPlanStop(String id) {
        super(id);
    }

    public LongPlanStop(LongPlan LongPlan) {
        this.LongPlan = LongPlan;
    }

    @Length(min = 0, max = 36, message = "长期计划ID长度必须介于 0 和 36 之间")
    public LongPlan getLongPlan() {
        return LongPlan;
    }

    public void setLongPlan(LongPlan LongPlan) {
        this.LongPlan = LongPlan;
    }

    public String getStopPointId() {
        return stopPointId;
    }

    public void setStopPointId(String stopPointId) {
        this.stopPointId = stopPointId;
    }

    @Length(min = 0, max = 100, message = "经停站编号长度必须介于 0 和 100 之间")
    public String getStopPointCode() {
        return stopPointCode;
    }

    public void setStopPointCode(String stopPointCode) {
        this.stopPointCode = stopPointCode;
    }

    @Length(min = 0, max = 200, message = "经停站名称长度必须介于 0 和 200 之间")
    public String getStopPointName() {
        return stopPointName;
    }

    public void setStopPointName(String stopPointName) {
        this.stopPointName = stopPointName;
    }

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8:00")
    public String getStopPointSTime() {
        return stopPointSTime;
    }

    public void setStopPointSTime(String stopPointSTime) {
        this.stopPointSTime = stopPointSTime;
    }

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8:00")
    public String getStopPointETime() {
        return stopPointETime;
    }

    public void setStopPointETime(String stopPointETime) {
        this.stopPointETime = stopPointETime;
    }
}