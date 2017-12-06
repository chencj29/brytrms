/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.ams.service.ShareFlightService;
import org.hibernate.validator.constraints.Length;

/**
 * 共享航班管理Entity
 *
 * @author xiaopo
 * @version 2016-01-19
 */
@Monitor(desc = "共享航班信息", tableName = "AMS_SHARE_FLIGHT", service = ShareFlightService.class, socketNS = "/rms/shareFlight")
public class ShareFlight extends DataEntity<ShareFlight> {

    private static final long serialVersionUID = 1L;
    @MonitorField(desc = "航空公司ID")
    @Label("航空公司ID")
    private String masterAirline;        // 航空公司ID
    @MonitorField(desc = "航空公司ID（被共享）")
    @Label("航空公司ID（被共享）")
    private String masterAirlineCode;        // 航空公司ID（被共享）
    @TipMainField
    @MonitorField(desc = "航班号（被共享）")
    @Label("航班号（被共享）")
    private String masterFlightNo;        // 航班号（被共享）
    @MonitorField(desc = "航空公司（被共享）")
    @Label("航空公司（被共享）")
    private String masterAirlineName;        // 航空公司（被共享）
    @MonitorField(desc = "航空公司ID（共享）")
    @Label("航空公司ID（共享）")
    private String slaveAirline;        // 航空公司ID（共享）
    @MonitorField(desc = "航空公司（共享）")
    @Label("航空公司（共享）")
    private String slaveAirlineCode;        // 航空公司（共享）
    @MonitorField(desc = "航空公司（共享）")
    @Label("航空公司（共享）")
    private String slaveAirlineName;        // 航空公司（共享）
    @TipMainField
    @MonitorField(desc = "航班号（共享）")
    @Label("航班号（共享）")
    private String slaveFlightNo;        // 航班号（共享）
    @MonitorField(desc = "班期")
    @Label("班期")
    private String daysOpt;        // 班期
    @MonitorField(desc = "进出港")
    @Label("进出港")
    private String ioOptCode;        // 进出港
    @MonitorField(desc = "进出港")
    @Label("进出港")
    private String ioOptName;        // 进出港
    @MonitorField(desc = "起飞地ID")
    @Label("起飞地ID")
    private String departureAirport;        // 起飞地ID
    @MonitorField(desc = "起飞地编号")
    @Label("起飞地编号")
    private String departureAirportCode;        // 起飞地编号
    @MonitorField(desc = "起飞地名称")
    @Label("起飞地名称")
    private String departureAirportName;        // 起飞地名称
    @MonitorField(desc = "经停地1ID")
    @Label("经停地1ID")
    private String passAirport1;        // 经停地1ID
    @MonitorField(desc = "经停地1编号")
    @Label("经停地1编号")
    private String passAirport1Code;        // 经停地1编号
    @MonitorField(desc = "经停地1名称")
    @Label("经停地1名称")
    private String passAirport1Name;        // 经停地1名称
    @MonitorField(desc = "经停地2ID")
    @Label("经停地2ID")
    private String passAirport2;        // 经停地2ID
    @MonitorField(desc = "经停地2编号")
    @Label("经停地2编号")
    private String passAirport2Code;        // 经停地2编号
    @MonitorField(desc = "经停地2名称")
    @Label("经停地2名称")
    private String passAirport2Name;        // 经停地2名称
    @MonitorField(desc = "到达地ID")
    @Label("到达地ID")
    private String arrivalAirport;        // 到达地ID
    @MonitorField(desc = "到达地编号")
    @Label("到达地编号")
    private String arrivalAirportCode;        // 到达地编号
    @MonitorField(desc = "到达地名称")
    @Label("到达地名称")
    private String arrivalAirportName;        // 到达地名称

    public ShareFlight() {
        super();
    }

    public ShareFlight(String id) {
        super(id);
    }

    @Length(min = 0, max = 100, message = "航空公司ID长度必须介于 0 和 100 之间")
    public String getMasterAirline() {
        return masterAirline;
    }

    public void setMasterAirline(String masterAirline) {
        this.masterAirline = masterAirline;
    }

    @Length(min = 0, max = 100, message = "航空公司ID（被共享）长度必须介于 0 和 100 之间")
    public String getMasterAirlineCode() {
        return masterAirlineCode;
    }

    public void setMasterAirlineCode(String masterAirlineCode) {
        this.masterAirlineCode = masterAirlineCode;
    }

    @Length(min = 0, max = 200, message = "航班号（被共享）长度必须介于 0 和 200 之间")
    public String getMasterFlightNo() {
        return masterFlightNo;
    }

    public void setMasterFlightNo(String masterFlightNo) {
        this.masterFlightNo = masterFlightNo;
    }

    @Length(min = 0, max = 100, message = "航空公司（被共享）长度必须介于 0 和 100 之间")
    public String getMasterAirlineName() {
        return masterAirlineName;
    }

    public void setMasterAirlineName(String masterAirlineName) {
        this.masterAirlineName = masterAirlineName;
    }

    @Length(min = 0, max = 100, message = "航空公司ID（共享）长度必须介于 0 和 100 之间")
    public String getSlaveAirline() {
        return slaveAirline;
    }

    public void setSlaveAirline(String slaveAirline) {
        this.slaveAirline = slaveAirline;
    }

    @Length(min = 0, max = 100, message = "航空公司（共享）长度必须介于 0 和 100 之间")
    public String getSlaveAirlineCode() {
        return slaveAirlineCode;
    }

    public void setSlaveAirlineCode(String slaveAirlineCode) {
        this.slaveAirlineCode = slaveAirlineCode;
    }

    @Length(min = 0, max = 100, message = "航空公司（共享）长度必须介于 0 和 100 之间")
    public String getSlaveAirlineName() {
        return slaveAirlineName;
    }

    public void setSlaveAirlineName(String slaveAirlineName) {
        this.slaveAirlineName = slaveAirlineName;
    }

    @Length(min = 0, max = 100, message = "航班号（共享）长度必须介于 0 和 100 之间")
    public String getSlaveFlightNo() {
        return slaveFlightNo;
    }

    public void setSlaveFlightNo(String slaveFlightNo) {
        this.slaveFlightNo = slaveFlightNo;
    }

    @Length(min = 0, max = 200, message = "班期长度必须介于 0 和 200 之间")
    public String getDaysOpt() {
        return daysOpt;
    }

    public void setDaysOpt(String daysOpt) {
        this.daysOpt = daysOpt;
    }

    @Length(min = 0, max = 200, message = "进出港长度必须介于 0 和 200 之间")
    public String getIoOptCode() {
        return ioOptCode;
    }

    public void setIoOptCode(String ioOptCode) {
        this.ioOptCode = ioOptCode;
    }

    @Length(min = 0, max = 200, message = "进出港长度必须介于 0 和 200 之间")
    public String getIoOptName() {
        return ioOptName;
    }

    public void setIoOptName(String ioOptName) {
        this.ioOptName = ioOptName;
    }

    @Length(min = 0, max = 100, message = "起飞地ID长度必须介于 0 和 100 之间")
    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    @Length(min = 0, max = 100, message = "起飞地编号长度必须介于 0 和 100 之间")
    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    @Length(min = 0, max = 200, message = "起飞地名称长度必须介于 0 和 200 之间")
    public String getDepartureAirportName() {
        return departureAirportName;
    }

    public void setDepartureAirportName(String departureAirportName) {
        this.departureAirportName = departureAirportName;
    }

    @Length(min = 0, max = 100, message = "经停地1ID长度必须介于 0 和 100 之间")
    public String getPassAirport1() {
        return passAirport1;
    }

    public void setPassAirport1(String passAirport1) {
        this.passAirport1 = passAirport1;
    }

    @Length(min = 0, max = 100, message = "经停地1编号长度必须介于 0 和 100 之间")
    public String getPassAirport1Code() {
        return passAirport1Code;
    }

    public void setPassAirport1Code(String passAirport1Code) {
        this.passAirport1Code = passAirport1Code;
    }

    @Length(min = 0, max = 200, message = "经停地1名称长度必须介于 0 和 200 之间")
    public String getPassAirport1Name() {
        return passAirport1Name;
    }

    public void setPassAirport1Name(String passAirport1Name) {
        this.passAirport1Name = passAirport1Name;
    }

    @Length(min = 0, max = 100, message = "经停地2ID长度必须介于 0 和 100 之间")
    public String getPassAirport2() {
        return passAirport2;
    }

    public void setPassAirport2(String passAirport2) {
        this.passAirport2 = passAirport2;
    }

    @Length(min = 0, max = 100, message = "经停地2编号长度必须介于 0 和 100 之间")
    public String getPassAirport2Code() {
        return passAirport2Code;
    }

    public void setPassAirport2Code(String passAirport2Code) {
        this.passAirport2Code = passAirport2Code;
    }

    @Length(min = 0, max = 200, message = "经停地2名称长度必须介于 0 和 200 之间")
    public String getPassAirport2Name() {
        return passAirport2Name;
    }

    public void setPassAirport2Name(String passAirport2Name) {
        this.passAirport2Name = passAirport2Name;
    }

    @Length(min = 0, max = 100, message = "到达地ID长度必须介于 0 和 100 之间")
    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    @Length(min = 0, max = 100, message = "到达地编号长度必须介于 0 和 100 之间")
    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    @Length(min = 0, max = 200, message = "到达地名称长度必须介于 0 和 200 之间")
    public String getArrivalAirportName() {
        return arrivalAirportName;
    }

    public void setArrivalAirportName(String arrivalAirportName) {
        this.arrivalAirportName = arrivalAirportName;
    }

}