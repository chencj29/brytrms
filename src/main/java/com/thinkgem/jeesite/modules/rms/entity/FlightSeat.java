/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.FlightSeatService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 座位交接单Entity
 *
 * @author wjp
 * @version 2016-03-21
 */

@Monitor(desc = "座位交接单信息", tableName = "rms_flight_seat", service = FlightSeatService.class, socketNS = "/rms/flightSeat")
public class FlightSeat extends DataEntity<FlightSeat> {

    private static final long serialVersionUID = 1L;
    @Label("创建时间")
    @MonitorField(desc = "创建时间")
    private Date createTime;        // 创建时间
    @Label("更新时间")
    @MonitorField(desc = "更新时间")
    private Date updateTime;        // 更新时间
    @Label("航班号")
    @MonitorField(desc = "航班号")
    private String flightNum;        // 航班号
    @Label("航班日期")
    @MonitorField(desc = "航班日期")
    private Date flightDate;        // 航班日期
    @Label("成人数")
    @MonitorField(desc = "成人数")
    private Long adultCount;        // 成人数
    @Label("儿童数")
    @MonitorField(desc = "儿童数")
    private Long childrenCount;        // 儿童数
    @Label("婴儿数")
    @MonitorField(desc = "婴儿数")
    private Long babyCount;        // 婴儿数
    @Label("头等舱人数")
    @MonitorField(desc = "头等舱人数")
    private Long firstClassCount;        // 头等舱人数
    @Label("公务舱人数")
    @MonitorField(desc = "公务舱人数")
    private Long businessClassCount;        // 公务舱人数
    @Label("经济舱人数")
    @MonitorField(desc = "经济舱人数")
    private Long touristClassCount;        // 经济舱人数

    public FlightSeat() {
        super();
    }

    public FlightSeat(String id) {
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

    @Length(min = 1, max = 10, message = "航班号长度必须介于 1 和 10 之间")
    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8:00")
    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public Long getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(Long adultCount) {
        this.adultCount = adultCount;
    }

    public Long getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(Long childrenCount) {
        this.childrenCount = childrenCount;
    }

    public Long getBabyCount() {
        return babyCount;
    }

    public void setBabyCount(Long babyCount) {
        this.babyCount = babyCount;
    }

    public Long getFirstClassCount() {
        return firstClassCount;
    }

    public void setFirstClassCount(Long firstClassCount) {
        this.firstClassCount = firstClassCount;
    }

    public Long getBusinessClassCount() {
        return businessClassCount;
    }

    public void setBusinessClassCount(Long businessClassCount) {
        this.businessClassCount = businessClassCount;
    }

    public Long getTouristClassCount() {
        return touristClassCount;
    }

    public void setTouristClassCount(Long touristClassCount) {
        this.touristClassCount = touristClassCount;
    }

}