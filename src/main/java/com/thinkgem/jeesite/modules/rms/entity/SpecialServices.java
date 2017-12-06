/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.SpecialServicesService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 特殊服务记录表Entity
 *
 * @author wjp
 * @version 2016-03-14
 */

@Monitor(desc = "特殊服务记录表信息", tableName = "rms_special_services", service = SpecialServicesService.class, socketNS = "/rms/specialServices")
public class SpecialServices extends DataEntity<SpecialServices> {

    private static final long serialVersionUID = 1L;
    @Label("创建时间")
    @MonitorField(desc = "创建时间")
    private Date createTime;        // 创建时间
    @Label("更新时间")
    @MonitorField(desc = "更新时间")
    private Date updateTime;        // 更新时间
    @Label("日期")
    @MonitorField(desc = "日期")
    private Date passengerDate;    // 日期
    @Label("航空公司代码")
    @MonitorField(desc = "航空公司代码")
    private String flightCompanyCode;        // 航空公司代码
    @Label("航空公司名称")
    @MonitorField(desc = "航空公司名称")
    private String flightCompanyName;        // 航空公司名称
    @Label("航班号")
    @MonitorField(desc = "航班号")
    private String flightNum;        // 航班号
    @Label("地点")
    @MonitorField(desc = "地点")
    private String place;        // 地点
    @Label("特服旅客姓名")
    @MonitorField(desc = "特服旅客姓名")
    private String passengerName;        // 特服旅客姓名
    @Label("特服旅客年龄")
    @MonitorField(desc = "特服旅客年龄")
    private String passengerAge;        // 特服旅客年龄
    @Label("接站人姓名")
    @MonitorField(desc = "接站人姓名")
    private String pickUpName;        // 接站人姓名
    @Label("接站人电话")
    @MonitorField(desc = "接站人电话")
    private String pickUpPhone;        // 接站人电话
    @Label("接站人地址")
    @MonitorField(desc = "接站人地址")
    private String pickUpAddress;        // 接站人地址
    @Label("服务员编号")
    @MonitorField(desc = "服务员编号")
    private String waiterCode;        // 服务员编号
    @Label("服务员姓名")
    @MonitorField(desc = "服务员姓名")
    private String waiterName;        // 服务员姓名
    @Label("值机员编号")
    @MonitorField(desc = "值机员编号")
    private String operatorCode;        // 值机员编号
    @Label("值机员名称")
    @MonitorField(desc = "值机员名称")
    private String operatorName;        // 值机员名称
    @Label("进出港类型")
    @MonitorField(desc = "进出港类型")
    private String redundance1;        // 进出港类型
    @Label("特服旅客类型")
    @MonitorField(desc = "特服旅客类型")
    private String redundance2;        // 特服旅客类型
    @Label("冗余3")
    @MonitorField(desc = "冗余3")
    private String redundance3;        // 冗余3
    @Label("冗余4")
    @MonitorField(desc = "冗余4")
    private String redundance4;        // 冗余4
    @Label("冗余5")
    @MonitorField(desc = "冗余5")
    private String redundance5;        // 冗余5

    public SpecialServices() {
        super();
    }

    public SpecialServices(String id) {
        super(id);
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8:00")
    public Date getPassengerDate() {
        return passengerDate;
    }

    public void setPassengerDate(Date passengerDate) {
        this.passengerDate = passengerDate;
    }

    @Length(min = 0, max = 20, message = "航空公司代码长度必须介于 0 和 20 之间")
    public String getFlightCompanyCode() {
        return flightCompanyCode;
    }

    public void setFlightCompanyCode(String flightCompanyCode) {
        this.flightCompanyCode = flightCompanyCode;
    }

    @Length(min = 0, max = 500, message = "航空公司名称长度必须介于 0 和 500 之间")
    public String getFlightCompanyName() {
        return flightCompanyName;
    }

    public void setFlightCompanyName(String flightCompanyName) {
        this.flightCompanyName = flightCompanyName;
    }

    @Length(min = 0, max = 36, message = "航班号长度必须介于 0 和 36 之间")
    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    @Length(min = 0, max = 100, message = "地点长度必须介于 0 和 100 之间")
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Length(min = 0, max = 100, message = "特服旅客姓名长度必须介于 0 和 100 之间")
    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    @Length(min = 0, max = 10, message = "特服旅客年龄长度必须介于 0 和 10 之间")
    public String getPassengerAge() {
        return passengerAge;
    }

    public void setPassengerAge(String passengerAge) {
        this.passengerAge = passengerAge;
    }

    @Length(min = 0, max = 100, message = "接站人姓名长度必须介于 0 和 100 之间")
    public String getPickUpName() {
        return pickUpName;
    }

    public void setPickUpName(String pickUpName) {
        this.pickUpName = pickUpName;
    }

    @Length(min = 0, max = 20, message = "接站人电话长度必须介于 0 和 20 之间")
    public String getPickUpPhone() {
        return pickUpPhone;
    }

    public void setPickUpPhone(String pickUpPhone) {
        this.pickUpPhone = pickUpPhone;
    }

    @Length(min = 0, max = 100, message = "接站人地址长度必须介于 0 和 100 之间")
    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    @Length(min = 0, max = 36, message = "服务员编号长度必须介于 0 和 36 之间")
    public String getWaiterCode() {
        return waiterCode;
    }

    public void setWaiterCode(String waiterCode) {
        this.waiterCode = waiterCode;
    }

    @Length(min = 0, max = 100, message = "服务员姓名长度必须介于 0 和 100 之间")
    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    @Length(min = 0, max = 36, message = "值机员编号长度必须介于 0 和 36 之间")
    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    @Length(min = 0, max = 100, message = "值机员名称长度必须介于 0 和 100 之间")
    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    @Length(min = 0, max = 10, message = "冗余1长度必须介于 0 和 10 之间")
    public String getRedundance1() {
        return redundance1;
    }

    public void setRedundance1(String redundance1) {
        this.redundance1 = redundance1;
    }

    @Length(min = 0, max = 10, message = "冗余2长度必须介于 0 和 10 之间")
    public String getRedundance2() {
        return redundance2;
    }

    public void setRedundance2(String redundance2) {
        this.redundance2 = redundance2;
    }

    @Length(min = 0, max = 10, message = "冗余3长度必须介于 0 和 10 之间")
    public String getRedundance3() {
        return redundance3;
    }

    public void setRedundance3(String redundance3) {
        this.redundance3 = redundance3;
    }

    @Length(min = 0, max = 10, message = "冗余4长度必须介于 0 和 10 之间")
    public String getRedundance4() {
        return redundance4;
    }

    public void setRedundance4(String redundance4) {
        this.redundance4 = redundance4;
    }

    @Length(min = 0, max = 10, message = "冗余5长度必须介于 0 和 10 之间")
    public String getRedundance5() {
        return redundance5;
    }

    public void setRedundance5(String redundance5) {
        this.redundance5 = redundance5;
    }

}