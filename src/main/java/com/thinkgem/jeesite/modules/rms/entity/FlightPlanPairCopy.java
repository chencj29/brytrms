/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.FlightPlanPairService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 运输调度计划管理Entity
 *
 * @author xiaopo
 * @version 2016-03-30
 */

@Monitor(desc = "运输调度计划管理信息", tableName = "ams_flight_plan_pair", service = FlightPlanPairService.class, socketNS = "/rms/flightPlanPair")
public class FlightPlanPairCopy extends DataEntity<FlightPlanPairCopy> {

    private static final long serialVersionUID = 1L;
    @Label("计划时间")
    @MonitorField(desc = "计划时间")
    private Date plandate;        // 计划时间
    @Label("代理人ID")
    @MonitorField(desc = "代理人ID")
    private String agentcode;        // 代理人ID
    @Label("ID代理人姓名")
    @MonitorField(desc = "ID代理人姓名")
    private String agentname;        // ID代理人姓名
    @Label("机号")
    @MonitorField(desc = "机号")
    private String aircraftnum;        // 机号
    @Label("飞机型号")
    @MonitorField(desc = "飞机型号")
    private String aircrafttypecode;        // 飞机类型
    @Label("机位号")
    @MonitorField(desc = "机位号")
    private String placenum;        // 机位号
    @Label("进出港(进)")
    @MonitorField(desc = "进出港(进)")
    private String inouttypename;        // 进出港
    @Label("进港航班号")
    @MonitorField(desc = "进港航班号")
    private String flightnum;        // 航班号
    @Label("进港共享航班")
    @MonitorField(desc = "进港共享航班")
    private String shareflightnum;        // 共享航班

    @Label("进港航班性质编号")
    @MonitorField(desc = "进港航班性质编号")
    private String flightNatureCode;

    @Label("进港航班性质")
    @MonitorField(desc = "进港航班性质")
    private String flightnaturename;        // 航班性质

    @Label("(进港)进出港类型")
    @MonitorField(desc = "(进港)进出港类型")
    private String inoutTypeCode;

    @Label("(进港)航班状态编码")
    @MonitorField(desc = "(进港)航班状态编码")
    private String inoutStatusCode;

    @Label("(进港)航班状态")
    @MonitorField(desc = "(进港)航班状态")
    private String inoutStatusName;


    @Label("进港航班计划ID")
    @MonitorField(desc = "进港航班计划ID")
    private String planDetailId;        // 航班计划ID
    @Label("进港起飞地编码")
    @MonitorField(desc = "进港起飞地编码")
    private String departureairportcode;        // 起飞地编码
    @Label("进港起飞地名称")
    @MonitorField(desc = "进港起飞地名称")
    private String departureairportname;        // 起飞地名称

    @Label("进港经停地1代码")
    @MonitorField(desc = "进港经停地1代码")
    private String passairport1code;        // 经停地1代码
    @Label("进港经停地1名称")
    @MonitorField(desc = "进港经停地1名称")
    private String passairport1name;        // 经停地1名称

    @Label("进港经停地2代码")
    @MonitorField(desc = "进港经停地2代码")
    private String passairport2code;        // 经停地2代码
    @Label("进港经停地2名称")
    @MonitorField(desc = "进港经停地2名称")
    private String passairport2name;        // 经停地2名称
    @Label("进港计起时间")
    @MonitorField(desc = "进港计起时间")
    private Date departureplantime;        // 计起时间
    @Label("进港计达时间")
    @MonitorField(desc = "进港计达时间")
    private Date arrivalplantime;        // 计达时间
    @Label("进港预起时间")
    @MonitorField(desc = "进港预起时间")
    private Date etd;   //预起
    @Label("进港预达时间")
    @MonitorField(desc = "进港预达时间")
    private Date eta;        // 预达时间
    @Label("进港实起时间")
    @MonitorField(desc = "进港实起时间")
    private Date atd;        // 实起时间
    @Label("进港实达时间")
    @MonitorField(desc = "进港实达时间")
    private Date ata;        // 实达时间

    @Label("行李转盘")
    @MonitorField(desc = "行李转盘")
    private String carouselnum;        // 行李转盘

    @Label("进港VIP")
    @MonitorField(desc = "进港VIP")
    private String vip;        // VIP

    @Label("(进港)延误原因编号")
    @MonitorField(desc = "(进港)延误原因编号")
    private String delayResonsCode;

    @MonitorField(desc = "登机口编号")
    @Label("登机口编号")
    private String gateCode;        // 登机口编号


    @Label("进港延误原因")
    @MonitorField(desc = "进港延误原因")
    private String delayresonsname;        // 延误原因
    @Label("进港延误备注")
    @MonitorField(desc = "进港延误备注")
    private String delayresonsremark;        // 延误备注
    @Label("进港状态")
    @MonitorField(desc = "进港状态")
    private String status;        // 状态
    @Label("进港状态名称")
    @MonitorField(desc = "进港状态名称")
    private String statusName;        // 状态名称
    @Label("出港航班计划ID")
    @MonitorField(desc = "出港航班计划ID")
    private String planDetailId2;        // 航班计划ID

    @Label("出港航班性质编号")
    @MonitorField(desc = "出港航班性质编号")
    private String flightNatureCode2;


    @Label("出港航班性质名称")
    @MonitorField(desc = "出港航班性质名称")
    private String flightnaturename2;        // 航班性质名称

    @Label("(出港)进出港类型")
    @MonitorField(desc = "(出港)进出港类型")
    private String inoutTypeCode2;

    @Label("(出港)航班状态编码")
    @MonitorField(desc = "(出港)航班状态编码")
    private String inoutStatusCode2;

    @Label("(出港)航班状态")
    @MonitorField(desc = "(出港)航班状态")
    private String inoutStatusName2;

    @Label("进出港(出)")
    @MonitorField(desc = "出港进(出)")
    private String inouttypename2;        // 进出港
    @Label("出港航班号")
    @MonitorField(desc = "出港航班号")
    private String flightnum2;        // 航班号
    @Label("出港共享航班号")
    @MonitorField(desc = "出港共享航班号")
    private String shareflightnum2;        // 共享航班号
    @Label("出港到达地编号")
    @MonitorField(desc = "出港到达地编号")
    private String arrivalairportcode;        // 到达地编号
    @Label("出港到达地名称")
    @MonitorField(desc = "出港到达地名称")
    private String arrivalairportname;        // 到达地名称
    @Label("出港经停地1编码")
    @MonitorField(desc = "出港经停地1编码")
    private String passairport1code2;        // 经停地1编码
    @Label("出港经停地1名称")
    @MonitorField(desc = "出港经停地1名称")
    private String passairport1name2;        // 经停地1名称
    @Label("出港经停地2编码")
    @MonitorField(desc = "出港经停地2编码")
    private String passairport2code2;        // 经停地2编码
    @Label("出港经停地2名称")
    @MonitorField(desc = "出港经停地2名称")
    private String passairport2name2;        // 经停地2名称
    @Label("出港VIP")
    @MonitorField(desc = "出港VIP")
    private String vip2;        // VIP
    @Label("登机口")
    @MonitorField(desc = "登机口")
    private String gatename;        // 登机口

    @Label("登机开始时间")
    @MonitorField(desc = "登机开始时间")
    private Date boardingstarttime;        // 登机开始时间
    @Label("登机结束时间")
    @MonitorField(desc = "登机结束时间")
    private Date boardingendtime;        // 登机结束时间

    @Label("出港计起时间")
    @MonitorField(desc = "出港计起时间")
    private Date departureplantime2;        // 计起时间
    @Label("出港计达时间")
    @MonitorField(desc = "出港计达时间")
    private Date arrivalplantime2;      //计达时间

    @Label("出港预起时间")
    @MonitorField(desc = "出港预起时间")
    private Date etd2;        // 预飞时间
    @Label("出港预达时间")
    @MonitorField(desc = "出港预达时间")
    private Date eta2;        // 预飞时间
    @Label("出港实起时间")
    @MonitorField(desc = "出港实起时间")
    private Date atd2;        // 实飞时间
    @Label("出港实达时间")
    @MonitorField(desc = "出港实达时间")
    private Date ata2;        // 实飞时间


    @Label("出港行李转盘")
    @MonitorField(desc = "出港行李转盘")
    private String carouselnum2;        // 出港行李转盘

    @Label("(出港)延误原因编号")
    @MonitorField(desc = "(出港)延误原因编号")
    private String delayResonsCode2;

    @MonitorField(desc = "(出港)登机口编号")
    @Label("(出港)登机口编号")
    private String gateCode2;        // 登机口编号


    @Label("值机岛代码")
    @MonitorField(desc = "值机岛代码")
    private String checkinislandcode;        // 值机岛代码
    @Label("值机岛名称")
    @MonitorField(desc = "值机岛名称")
    private String checkinislandname;        // 值机岛名称
    @Label("值机柜台代码")
    @MonitorField(desc = "值机柜台代码")
    private String checkincountercode;        // 值机柜台代码
    @Label("值机柜台名称")
    @MonitorField(desc = "值机柜台名称")
    private String checkincountername;        // 值机柜台名称
    @Label("值机开始时间")
    @MonitorField(desc = "值机开始时间")
    private Date checkinstarttime;        // 值机开始时间
    @Label("值机结束时间")
    @MonitorField(desc = "值机结束时间")
    private Date checkinendtime;        // 值机结束时间
    @Label("出港延误原因")
    @MonitorField(desc = "出港延误原因")
    private String delayresonsname2;        // 延误原因
    @Label("出港延误原因备注")
    @MonitorField(desc = "出港延误原因备注")
    private String delayresonsremark2;        // 延误原因备注
    @Label("出港状态")
    @MonitorField(desc = "出港状态")
    private String status2;        // 状态2
    @Label("出港状态名称")
    @MonitorField(desc = "出港状态名称")
    private String statusName2;        // 状态名称2

    @Label("(出港)登机开始时间")
    @MonitorField(desc = "(出港)登机开始时间")
    private Date boardingstarttime2;        // 登机开始时间
    @Label("(出港)登机结束时间")
    @MonitorField(desc = "(出港)登机结束时间")
    private Date boardingendtime2;        // 登机结束时间

    @MonitorField(desc = "(出港)值机岛编号")
    @Label("(出港)值机岛编号")
    private String checkinIslandCode2;        // 值机岛编号
    @MonitorField(desc = "(出港)值机岛名称")
    @Label("(出港)值机岛名称")
    private String checkinIslandName2;        // 值机岛名称

    @MonitorField(desc = "(出港)值机站台编号")
    @Label("(出港)值机站台编号")
    private String checkinCounterCode2;        // 值机站台编号
    @MonitorField(desc = "(出港)值机站台名称")
    @Label("(出港)值机站台名称")
    private String checkinCounterName2;        // 值机站台名称

    @Label("航班保障编号")
    @MonitorField(desc = "航班保障编号")
    private String safeguardTypeCode;
    @Label("航班保障名称")
    @MonitorField(desc = "航班保障编号")
    private String safeguardTypeName;

    public FlightPlanPairCopy() {
        super();
    }

    public FlightPlanPairCopy(String id) {
        super(id);
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getPlandate() {
        return plandate;
    }

    public void setPlandate(Date plandate) {
        this.plandate = plandate;
    }

    @Length(min = 0, max = 100, message = "代理人ID长度必须介于 0 和 100 之间")
    public String getAgentcode() {
        return agentcode;
    }

    public void setAgentcode(String agentcode) {
        this.agentcode = agentcode;
    }

    @Length(min = 0, max = 200, message = "ID代理人姓名长度必须介于 0 和 200 之间")
    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    @Length(min = 0, max = 100, message = "机号长度必须介于 0 和 100 之间")
    public String getAircraftnum() {
        return aircraftnum;
    }

    public void setAircraftnum(String aircraftnum) {
        this.aircraftnum = aircraftnum;
    }

    @Length(min = 0, max = 100, message = "飞机类型长度必须介于 0 和 100 之间")
    public String getAircrafttypecode() {
        return aircrafttypecode;
    }

    public void setAircrafttypecode(String aircrafttypecode) {
        this.aircrafttypecode = aircrafttypecode;
    }

    @Length(min = 0, max = 100, message = "机位号长度必须介于 0 和 100 之间")
    public String getPlacenum() {
        return placenum;
    }

    public void setPlacenum(String placenum) {
        this.placenum = placenum;
    }

    @Length(min = 0, max = 4, message = "进出港长度必须介于 0 和 4 之间")
    public String getInouttypename() {
        return inouttypename;
    }

    public void setInouttypename(String inouttypename) {
        this.inouttypename = inouttypename;
    }

    @Length(min = 0, max = 100, message = "航班号长度必须介于 0 和 100 之间")
    public String getFlightnum() {
        return flightnum;
    }

    public void setFlightnum(String flightnum) {
        this.flightnum = flightnum;
    }

    @Length(min = 0, max = 100, message = "共享航班长度必须介于 0 和 100 之间")
    public String getShareflightnum() {
        return shareflightnum;
    }

    public void setShareflightnum(String shareflightnum) {
        this.shareflightnum = shareflightnum;
    }

    @Length(min = 0, max = 200, message = "航班性质长度必须介于 0 和 200 之间")
    public String getFlightnaturename() {
        return flightnaturename;
    }

    public void setFlightnaturename(String flightnaturename) {
        this.flightnaturename = flightnaturename;
    }

    @Length(min = 0, max = 36, message = "航班计划ID长度必须介于 0 和 36 之间")
    public String getPlanDetailId() {
        return planDetailId;
    }

    public void setPlanDetailId(String planDetailId) {
        this.planDetailId = planDetailId;
    }

    @Length(min = 0, max = 100, message = "起飞地编码长度必须介于 0 和 100 之间")
    public String getDepartureairportcode() {
        return departureairportcode;
    }

    public void setDepartureairportcode(String departureairportcode) {
        this.departureairportcode = departureairportcode;
    }

    @Length(min = 0, max = 200, message = "起飞地名称长度必须介于 0 和 200 之间")
    public String getDepartureairportname() {
        return departureairportname;
    }

    public void setDepartureairportname(String departureairportname) {
        this.departureairportname = departureairportname;
    }

    @Length(min = 0, max = 100, message = "经停地1代码长度必须介于 0 和 100 之间")
    public String getPassairport1code() {
        return passairport1code;
    }

    public void setPassairport1code(String passairport1code) {
        this.passairport1code = passairport1code;
    }

    @Length(min = 0, max = 200, message = "经停地1名称长度必须介于 0 和 200 之间")
    public String getPassairport1name() {
        return passairport1name;
    }

    public void setPassairport1name(String passairport1name) {
        this.passairport1name = passairport1name;
    }

    @Length(min = 0, max = 100, message = "经停地2代码长度必须介于 0 和 100 之间")
    public String getPassairport2code() {
        return passairport2code;
    }

    public void setPassairport2code(String passairport2code) {
        this.passairport2code = passairport2code;
    }

    @Length(min = 0, max = 200, message = "经停地2名称长度必须介于 0 和 200 之间")
    public String getPassairport2name() {
        return passairport2name;
    }

    public void setPassairport2name(String passairport2name) {
        this.passairport2name = passairport2name;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getDepartureplantime() {
        return departureplantime;
    }

    public void setDepartureplantime(Date departureplantime) {
        this.departureplantime = departureplantime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getArrivalplantime() {
        return arrivalplantime;
    }

    public void setArrivalplantime(Date arrivalplantime) {
        this.arrivalplantime = arrivalplantime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getAtd() {
        return atd;
    }

    public void setAtd(Date atd) {
        this.atd = atd;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getEta() {
        return eta;
    }

    public void setEta(Date eta) {
        this.eta = eta;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getAta() {
        return ata;
    }

    public void setAta(Date ata) {
        this.ata = ata;
    }

    @Length(min = 0, max = 100, message = "行李转盘长度必须介于 0 和 100 之间")
    public String getCarouselnum() {
        return carouselnum;
    }

    public void setCarouselnum(String carouselnum) {
        this.carouselnum = carouselnum;
    }

    @Length(min = 0, max = 100, message = "VIP长度必须介于 0 和 100 之间")
    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    @Length(min = 0, max = 200, message = "延误原因长度必须介于 0 和 200 之间")
    public String getDelayresonsname() {
        return delayresonsname;
    }

    public void setDelayresonsname(String delayresonsname) {
        this.delayresonsname = delayresonsname;
    }

    @Length(min = 0, max = 500, message = "延误备注长度必须介于 0 和 500 之间")
    public String getDelayresonsremark() {
        return delayresonsremark;
    }

    public void setDelayresonsremark(String delayresonsremark) {
        this.delayresonsremark = delayresonsremark;
    }

    @Length(min = 0, max = 100, message = "状态长度必须介于 0 和 100 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Length(min = 0, max = 200, message = "状态名称长度必须介于 0 和 200 之间")
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Length(min = 0, max = 36, message = "航班计划ID长度必须介于 0 和 36 之间")
    public String getPlanDetailId2() {
        return planDetailId2;
    }

    public void setPlanDetailId2(String planDetailId2) {
        this.planDetailId2 = planDetailId2;
    }

    @Length(min = 0, max = 200, message = "航班性质名称长度必须介于 0 和 200 之间")
    public String getFlightnaturename2() {
        return flightnaturename2;
    }

    public void setFlightnaturename2(String flightnaturename2) {
        this.flightnaturename2 = flightnaturename2;
    }

    @Length(min = 0, max = 4, message = "进出港长度必须介于 0 和 4 之间")
    public String getInouttypename2() {
        return inouttypename2;
    }

    public void setInouttypename2(String inouttypename2) {
        this.inouttypename2 = inouttypename2;
    }

    @Length(min = 0, max = 100, message = "航班号长度必须介于 0 和 100 之间")
    public String getFlightnum2() {
        return flightnum2;
    }

    public void setFlightnum2(String flightnum2) {
        this.flightnum2 = flightnum2;
    }

    @Length(min = 0, max = 100, message = "共享航班号长度必须介于 0 和 100 之间")
    public String getShareflightnum2() {
        return shareflightnum2;
    }

    public void setShareflightnum2(String shareflightnum2) {
        this.shareflightnum2 = shareflightnum2;
    }

    @Length(min = 0, max = 100, message = "到达地编号长度必须介于 0 和 100 之间")
    public String getArrivalairportcode() {
        return arrivalairportcode;
    }

    public void setArrivalairportcode(String arrivalairportcode) {
        this.arrivalairportcode = arrivalairportcode;
    }

    @Length(min = 0, max = 200, message = "到达地名称长度必须介于 0 和 200 之间")
    public String getArrivalairportname() {
        return arrivalairportname;
    }

    public void setArrivalairportname(String arrivalairportname) {
        this.arrivalairportname = arrivalairportname;
    }

    @Length(min = 0, max = 100, message = "经停地1编码长度必须介于 0 和 100 之间")
    public String getPassairport1code2() {
        return passairport1code2;
    }

    public void setPassairport1code2(String passairport1code2) {
        this.passairport1code2 = passairport1code2;
    }

    @Length(min = 0, max = 200, message = "经停地1名称长度必须介于 0 和 200 之间")
    public String getPassairport1name2() {
        return passairport1name2;
    }

    public void setPassairport1name2(String passairport1name2) {
        this.passairport1name2 = passairport1name2;
    }

    @Length(min = 0, max = 100, message = "经停地2编码长度必须介于 0 和 100 之间")
    public String getPassairport2code2() {
        return passairport2code2;
    }

    public void setPassairport2code2(String passairport2code2) {
        this.passairport2code2 = passairport2code2;
    }

    @Length(min = 0, max = 200, message = "经停地2名称长度必须介于 0 和 200 之间")
    public String getPassairport2name2() {
        return passairport2name2;
    }

    public void setPassairport2name2(String passairport2name2) {
        this.passairport2name2 = passairport2name2;
    }

    @Length(min = 0, max = 100, message = "VIP长度必须介于 0 和 100 之间")
    public String getVip2() {
        return vip2;
    }

    public void setVip2(String vip2) {
        this.vip2 = vip2;
    }

    @Length(min = 0, max = 200, message = "登机口长度必须介于 0 和 200 之间")
    public String getGatename() {
        return gatename;
    }

    public void setGatename(String gatename) {
        this.gatename = gatename;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getBoardingstarttime() {
        return boardingstarttime;
    }

    public void setBoardingstarttime(Date boardingstarttime) {
        this.boardingstarttime = boardingstarttime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getBoardingendtime() {
        return boardingendtime;
    }

    public void setBoardingendtime(Date boardingendtime) {
        this.boardingendtime = boardingendtime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getDepartureplantime2() {
        return departureplantime2;
    }

    public void setDepartureplantime2(Date departureplantime2) {
        this.departureplantime2 = departureplantime2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getEtd() {
        return etd;
    }

    public void setEtd(Date etd) {
        this.etd = etd;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getAtd2() {
        return atd2;
    }

    public void setAtd2(Date atd2) {
        this.atd2 = atd2;
    }

    @Length(min = 0, max = 100, message = "值机岛代码长度必须介于 0 和 100 之间")
    public String getCheckinislandcode() {
        return checkinislandcode;
    }

    public void setCheckinislandcode(String checkinislandcode) {
        this.checkinislandcode = checkinislandcode;
    }

    @Length(min = 0, max = 200, message = "值机岛名称长度必须介于 0 和 200 之间")
    public String getCheckinislandname() {
        return checkinislandname;
    }

    public void setCheckinislandname(String checkinislandname) {
        this.checkinislandname = checkinislandname;
    }

    @Length(min = 0, max = 100, message = "值机柜台代码长度必须介于 0 和 100 之间")
    public String getCheckincountercode() {
        return checkincountercode;
    }

    public void setCheckincountercode(String checkincountercode) {
        this.checkincountercode = checkincountercode;
    }

    @Length(min = 0, max = 200, message = "值机柜台名称长度必须介于 0 和 200 之间")
    public String getCheckincountername() {
        return checkincountername;
    }

    public void setCheckincountername(String checkincountername) {
        this.checkincountername = checkincountername;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCheckinstarttime() {
        return checkinstarttime;
    }

    public void setCheckinstarttime(Date checkinstarttime) {
        this.checkinstarttime = checkinstarttime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCheckinendtime() {
        return checkinendtime;
    }

    public void setCheckinendtime(Date checkinendtime) {
        this.checkinendtime = checkinendtime;
    }

    @Length(min = 0, max = 200, message = "延误原因长度必须介于 0 和 200 之间")
    public String getDelayresonsname2() {
        return delayresonsname2;
    }

    public void setDelayresonsname2(String delayresonsname2) {
        this.delayresonsname2 = delayresonsname2;
    }

    @Length(min = 0, max = 500, message = "延误原因备注长度必须介于 0 和 500 之间")
    public String getDelayresonsremark2() {
        return delayresonsremark2;
    }

    public void setDelayresonsremark2(String delayresonsremark2) {
        this.delayresonsremark2 = delayresonsremark2;
    }

    @Length(min = 0, max = 100, message = "状态2长度必须介于 0 和 100 之间")
    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    @Length(min = 0, max = 200, message = "状态名称2长度必须介于 0 和 200 之间")
    public String getStatusName2() {
        return statusName2;
    }

    public void setStatusName2(String statusName2) {
        this.statusName2 = statusName2;
    }

    public String getSafeguardTypeCode() {
        return safeguardTypeCode;
    }

    public void setSafeguardTypeCode(String safeguardTypeCode) {
        this.safeguardTypeCode = safeguardTypeCode;
    }

    public String getSafeguardTypeName() {
        return safeguardTypeName;
    }

    public void setSafeguardTypeName(String safeguardTypeName) {
        this.safeguardTypeName = safeguardTypeName;
    }

    public Date getArrivalplantime2() {
        return arrivalplantime2;
    }

    public void setArrivalplantime2(Date arrivalplantime2) {
        this.arrivalplantime2 = arrivalplantime2;
    }

    public Date getEtd2() {
        return etd2;
    }

    public void setEtd2(Date etd2) {
        this.etd2 = etd2;
    }

    public Date getEta2() {
        return eta2;
    }

    public void setEta2(Date eta2) {
        this.eta2 = eta2;
    }

    public Date getAta2() {
        return ata2;
    }

    public void setAta2(Date ata2) {
        this.ata2 = ata2;
    }

    public String getFlightNatureCode() {
        return flightNatureCode;
    }

    public void setFlightNatureCode(String flightNatureCode) {
        this.flightNatureCode = flightNatureCode;
    }

    public String getFlightNatureCode2() {
        return flightNatureCode2;
    }

    public void setFlightNatureCode2(String flightNatureCode2) {
        this.flightNatureCode2 = flightNatureCode2;
    }

    public String getInoutTypeCode() {
        return inoutTypeCode;
    }

    public void setInoutTypeCode(String inoutTypeCode) {
        this.inoutTypeCode = inoutTypeCode;
    }

    public String getInoutTypeCode2() {
        return inoutTypeCode2;
    }

    public void setInoutTypeCode2(String inoutTypeCode2) {
        this.inoutTypeCode2 = inoutTypeCode2;
    }

    public String getInoutStatusCode() {
        return inoutStatusCode;
    }

    public void setInoutStatusCode(String inoutStatusCode) {
        this.inoutStatusCode = inoutStatusCode;
    }

    public String getInoutStatusName() {
        return inoutStatusName;
    }

    public void setInoutStatusName(String inoutStatusName) {
        this.inoutStatusName = inoutStatusName;
    }

    public String getInoutStatusCode2() {
        return inoutStatusCode2;
    }

    public void setInoutStatusCode2(String inoutStatusCode2) {
        this.inoutStatusCode2 = inoutStatusCode2;
    }

    public String getInoutStatusName2() {
        return inoutStatusName2;
    }

    public void setInoutStatusName2(String inoutStatusName2) {
        this.inoutStatusName2 = inoutStatusName2;
    }

    public String getCarouselnum2() {
        return carouselnum2;
    }

    public void setCarouselnum2(String carouselnum2) {
        this.carouselnum2 = carouselnum2;
    }

    public String getDelayResonsCode() {
        return delayResonsCode;
    }

    public void setDelayResonsCode(String delayResonsCode) {
        this.delayResonsCode = delayResonsCode;
    }

    public String getDelayResonsCode2() {
        return delayResonsCode2;
    }

    public void setDelayResonsCode2(String delayResonsCode2) {
        this.delayResonsCode2 = delayResonsCode2;
    }

    public String getGateCode() {
        return gateCode;
    }

    public void setGateCode(String gateCode) {
        this.gateCode = gateCode;
    }

    public String getGateCode2() {
        return gateCode2;
    }

    public void setGateCode2(String gateCode2) {
        this.gateCode2 = gateCode2;
    }

    public Date getBoardingstarttime2() {
        return boardingstarttime2;
    }

    public void setBoardingstarttime2(Date boardingstarttime2) {
        this.boardingstarttime2 = boardingstarttime2;
    }

    public Date getBoardingendtime2() {
        return boardingendtime2;
    }

    public void setBoardingendtime2(Date boardingendtime2) {
        this.boardingendtime2 = boardingendtime2;
    }

    public String getCheckinIslandCode2() {
        return checkinIslandCode2;
    }

    public void setCheckinIslandCode2(String checkinIslandCode2) {
        this.checkinIslandCode2 = checkinIslandCode2;
    }

    public String getCheckinIslandName2() {
        return checkinIslandName2;
    }

    public void setCheckinIslandName2(String checkinIslandName2) {
        this.checkinIslandName2 = checkinIslandName2;
    }

    public String getCheckinCounterCode2() {
        return checkinCounterCode2;
    }

    public void setCheckinCounterCode2(String checkinCounterCode2) {
        this.checkinCounterCode2 = checkinCounterCode2;
    }

    public String getCheckinCounterName2() {
        return checkinCounterName2;
    }

    public void setCheckinCounterName2(String checkinCounterName2) {
        this.checkinCounterName2 = checkinCounterName2;
    }
}