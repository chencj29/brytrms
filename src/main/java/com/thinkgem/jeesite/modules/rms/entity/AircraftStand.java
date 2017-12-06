/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.AircraftStandService;
import org.hibernate.validator.constraints.Length;

/**
 * 机位基础信息Entity
 *
 * @author wjp
 * @version 2016-03-09
 */

@Monitor(desc = "机位基础信息", tableName = "rms_aircraft_stand", service = AircraftStandService.class, socketNS = "/rms/aircraftStand")
public class AircraftStand extends DataEntity<AircraftStand> {

    private static final long serialVersionUID = 1L;
    @Label("机位号")
    @MonitorField(desc = "机位号")
    private String aircraftStandNum;        // 机位号
    @Label("机坪号")
    @MonitorField(desc = "机坪号")
    private String aircraftParkNum;        // 机坪号
    @Label("机坪调度者")
    @MonitorField(desc = "机坪调度者")
    private String aircraftParkDispatcher;        // 机坪调度者
    @Label("是否有廊桥")
    @MonitorField(desc = "是否有廊桥")
    private String hasSalon;        // 是否有廊桥
    @Label("廊桥状态")
    @MonitorField(desc = "廊桥状态")
    private String salonStatus;        // 廊桥状态
    @Label("是否有油管")
    @MonitorField(desc = "是否有油管")
    private String hasOilpipeline;        // 是否有油管
    @Label("油管状态")
    @MonitorField(desc = "油管状态")
    private String oilpipelineStatus;        // 油管状态
    @Label("左机位号")
    @MonitorField(desc = "左机位号")
    private String leftAsNum;        // 左机位号
    @Label("右机位号")
    @MonitorField(desc = "右机位号")
    private String rightAsNum;        // 右机位号
    @Label("前滑行道")
    @MonitorField(desc = "前滑行道")
    private String frontCoast;        // 前滑行道
    @Label("后滑行道")
    @MonitorField(desc = "后滑行道")
    private String backCoast;        // 后滑行道
    @Label("左鼻轮线距")
    @MonitorField(desc = "左鼻轮线距")
    private Double leftWheelLine;        // 左鼻轮线距
    @Label("右鼻轮线距")
    @MonitorField(desc = "右鼻轮线距")
    private Double rightWheelLine;        // 右鼻轮线距
    @Label("翼展")
    @MonitorField(desc = "翼展")
    private Double wingLength;        // 翼展
    @Label("是否可用")
    @MonitorField(desc = "是否可用")
    private String available;        // 是否可用
    @Label("所属航站楼")
    @MonitorField(desc = "所属航站楼")
    private String airportTerminalCode;        // 所属航站楼

    @Label("排序字段")
    @MonitorField(desc = "排序字段")
    private Integer orderIndex;

    public AircraftStand() {
        super();
    }

    public AircraftStand(String id) {
        super(id);
    }

    @Length(min = 0, max = 36, message = "机位号长度必须介于 0 和 36 之间")
    public String getAircraftStandNum() {
        return aircraftStandNum;
    }

    public void setAircraftStandNum(String aircraftStandNum) {
        this.aircraftStandNum = aircraftStandNum;
    }

    @Length(min = 0, max = 10, message = "机坪号长度必须介于 0 和 10 之间")
    public String getAircraftParkNum() {
        return aircraftParkNum;
    }

    public void setAircraftParkNum(String aircraftParkNum) {
        this.aircraftParkNum = aircraftParkNum;
    }

    @Length(min = 0, max = 50, message = "机坪调度者长度必须介于 0 和 50 之间")
    public String getAircraftParkDispatcher() {
        return aircraftParkDispatcher;
    }

    public void setAircraftParkDispatcher(String aircraftParkDispatcher) {
        this.aircraftParkDispatcher = aircraftParkDispatcher;
    }

    @Length(min = 0, max = 1, message = "是否有廊桥长度必须介于 0 和 1 之间")
    public String getHasSalon() {
        return hasSalon;
    }

    public void setHasSalon(String hasSalon) {
        this.hasSalon = hasSalon;
    }

    @Length(min = 0, max = 1, message = "廊桥状态长度必须介于 0 和 1 之间")
    public String getSalonStatus() {
        return salonStatus;
    }

    public void setSalonStatus(String salonStatus) {
        this.salonStatus = salonStatus;
    }

    @Length(min = 0, max = 1, message = "是否有油管长度必须介于 0 和 1 之间")
    public String getHasOilpipeline() {
        return hasOilpipeline;
    }

    public void setHasOilpipeline(String hasOilpipeline) {
        this.hasOilpipeline = hasOilpipeline;
    }

    @Length(min = 0, max = 1, message = "油管状态长度必须介于 0 和 1 之间")
    public String getOilpipelineStatus() {
        return oilpipelineStatus;
    }

    public void setOilpipelineStatus(String oilpipelineStatus) {
        this.oilpipelineStatus = oilpipelineStatus;
    }

    @Length(min = 0, max = 5, message = "左机位号长度必须介于 0 和 5 之间")
    public String getLeftAsNum() {
        return leftAsNum;
    }

    public void setLeftAsNum(String leftAsNum) {
        this.leftAsNum = leftAsNum;
    }

    @Length(min = 0, max = 5, message = "右机位号长度必须介于 0 和 5 之间")
    public String getRightAsNum() {
        return rightAsNum;
    }

    public void setRightAsNum(String rightAsNum) {
        this.rightAsNum = rightAsNum;
    }

    @Length(min = 0, max = 5, message = "前滑行道长度必须介于 0 和 5 之间")
    public String getFrontCoast() {
        return frontCoast;
    }

    public void setFrontCoast(String frontCoast) {
        this.frontCoast = frontCoast;
    }

    @Length(min = 0, max = 5, message = "后滑行道长度必须介于 0 和 5 之间")
    public String getBackCoast() {
        return backCoast;
    }

    public void setBackCoast(String backCoast) {
        this.backCoast = backCoast;
    }

    public Double getLeftWheelLine() {
        return leftWheelLine;
    }

    public void setLeftWheelLine(Double leftWheelLine) {
        this.leftWheelLine = leftWheelLine;
    }

    public Double getRightWheelLine() {
        return rightWheelLine;
    }

    public void setRightWheelLine(Double rightWheelLine) {
        this.rightWheelLine = rightWheelLine;
    }

    public Double getWingLength() {
        return wingLength;
    }

    public void setWingLength(Double wingLength) {
        this.wingLength = wingLength;
    }

    @Length(min = 0, max = 1, message = "是否可用长度必须介于 0 和 1 之间")
    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    @Length(min = 0, max = 5, message = "所属航站楼长度必须介于 0 和 5 之间")
    public String getAirportTerminalCode() {
        return airportTerminalCode;
    }

    public void setAirportTerminalCode(String airportTerminalCode) {
        this.airportTerminalCode = airportTerminalCode;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
}