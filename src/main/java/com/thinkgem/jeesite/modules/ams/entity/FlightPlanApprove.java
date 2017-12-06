/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 航班计划审批Entity
 *
 * @author chrischen
 * @version 2016-03-10
 */
public class FlightPlanApprove extends DataEntity<FlightPlanApprove> {

    private static final long serialVersionUID = 1L;

    @MonitorField(desc = "审核人ID")
    private String approveUserId;        // 审核人ID

    @MonitorField(desc = "航班计划ID")
    private String flightPlanId; // 航班计划ID

    @TipMainField
    @MonitorField(desc = "审核人名称")
    private String approveUserName;        // 审核人名称
    @MonitorField(desc = "审核时间")
    private Date approveTime;        // 审核时间
    @MonitorField(desc = "审核意见")
    private String approveDesc;        // 审核意见

    public FlightPlanApprove() {
        super();
    }

    public FlightPlanApprove(String id) {
        super(id);
    }

    @Length(min = 0, max = 36, message = "审核人ID长度必须介于 0 和 36 之间")
    public String getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(String approveUserId) {
        this.approveUserId = approveUserId;
    }

    @Length(min = 0, max = 50, message = "审核人名称长度必须介于 0 和 50 之间")
    public String getApproveUserName() {
        return approveUserName;
    }

    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    @Length(min = 0, max = 250, message = "审核意见长度必须介于 0 和 250 之间")
    public String getApproveDesc() {
        return approveDesc;
    }

    public void setApproveDesc(String approveDesc) {
        this.approveDesc = approveDesc;
    }

    public String getFlightPlanId() {
        return flightPlanId;
    }

    public void setFlightPlanId(String flightPlanId) {
        this.flightPlanId = flightPlanId;
    }
}