/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.RmsCheckinCounterService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 值机柜台基础信息表Entity
 *
 * @author wjp
 * @version 2016-03-07
 */

@Monitor(desc = "值机柜台基础信息表信息", tableName = "rms_checkin_counter", service = RmsCheckinCounterService.class, socketNS = "/rms/rmsCheckinCounter")
public class RmsCheckinCounter extends DataEntity<RmsCheckinCounter> {

    private static final long serialVersionUID = 1L;
    @MonitorField(desc = "创建时间")
    private Date createTime;        // 创建时间
    @MonitorField(desc = "更新时间")
    private Date updateTime;        // 更新时间
    @MonitorField(desc = "航站楼编号")
    private String airportTerminalCode;        // 航站楼编号
    @MonitorField(desc = "航站楼名称")
    private String airportTerminalName;        // 航站楼名称
    @MonitorField(desc = "值机柜台编号")
    private String checkinCounterNum;        // 值机柜台编号
    @MonitorField(desc = "值机柜台性质")
    private String checkinCounterNature;        // 值机柜台性质
    @MonitorField(desc = "值机柜台状态")
    private String checkinCounterTypeCode;        // 值机柜台状态
    @MonitorField(desc = "值机柜台状态名称")
    private String checkinCounterTypeName;        // 值机柜台状态名称

    @Label("排序字段")
    @MonitorField(desc = "排序字段")
    private Integer orderIndex;

    public RmsCheckinCounter() {
        super();
    }

    public RmsCheckinCounter(String id) {
        super(id);
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Length(min = 0, max = 36, message = "航站楼编号长度必须介于 0 和 36 之间")
    public String getAirportTerminalCode() {
        return airportTerminalCode;
    }

    public void setAirportTerminalCode(String airportTerminalCode) {
        this.airportTerminalCode = airportTerminalCode;
    }

    @Length(min = 0, max = 200, message = "航站楼名称长度必须介于 0 和 200 之间")
    public String getAirportTerminalName() {
        return airportTerminalName;
    }

    public void setAirportTerminalName(String airportTerminalName) {
        this.airportTerminalName = airportTerminalName;
    }

    @Length(min = 0, max = 36, message = "值机柜台编号长度必须介于 0 和 36 之间")
    public String getCheckinCounterNum() {
        return checkinCounterNum;
    }

    public void setCheckinCounterNum(String checkinCounterNum) {
        this.checkinCounterNum = checkinCounterNum;
    }

    @Length(min = 0, max = 200, message = "值机柜台性质长度必须介于 0 和 200 之间")
    public String getCheckinCounterNature() {
        return checkinCounterNature;
    }

    public void setCheckinCounterNature(String checkinCounterNature) {
        this.checkinCounterNature = checkinCounterNature;
    }

    @Length(min = 0, max = 36, message = "值机柜台状态长度必须介于 0 和 36 之间")
    public String getCheckinCounterTypeCode() {
        return checkinCounterTypeCode;
    }

    public void setCheckinCounterTypeCode(String checkinCounterTypeCode) {
        this.checkinCounterTypeCode = checkinCounterTypeCode;
    }

    @Length(min = 0, max = 200, message = "值机柜台状态名称长度必须介于 0 和 200 之间")
    public String getCheckinCounterTypeName() {
        return checkinCounterTypeName;
    }

    public void setCheckinCounterTypeName(String checkinCounterTypeName) {
        this.checkinCounterTypeName = checkinCounterTypeName;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

}