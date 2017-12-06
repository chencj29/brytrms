/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import org.hibernate.validator.constraints.Length;

import java.beans.Transient;
import java.util.Date;

/**
 * 航班动态管理Entity
 *
 * @author xiaopo
 * @version 2016-01-10
 */
@Monitor(desc = "航班动态信息", tableName = "AMS_FLIGHT_DYNAMIC", service = FlightDynamicService.class, socketNS = "/global/dynamics")
public class FlightDynamic extends DataEntity<FlightDynamic> {

    private static final long serialVersionUID = 1L;
    @MonitorField(desc = "计划日期")
    @Label("计划日期")
    private Date planDate;              // 计划日期
    @MonitorField(desc = "代理人编号")
    @Label("代理人编号")
    private String agentCode;           // 代理人编号
    @MonitorField(desc = "代理人名称")
    @Label("代理人名称")
    private String agentName;           // 代理人名称
    @MonitorField(desc = "飞机号")
    @Label("飞机号")
    private String aircraftNum;         // 飞机号
    @MonitorField(desc = "机型编号")
    @Label("机型编号")
    private String aircraftTypeCode;    // 机型编号
    @MonitorField(desc = "机型名称")
    @Label("机型名称")
    private String aircraftTypeName;        // 机型名称
    @MonitorField(desc = "机位号")
    @Label("机位号")
    private String placeNum;        // 机位号
    @TipMainField
    @MonitorField(desc = "航班号")
    @Label("航班号")
    private String flightNum;       //航班号
    @MonitorField(desc = "共享航班号")
    @Label("共享航班号")
    private String shareFlightNum;        // 共享航班号
    @MonitorField(desc = "航班性质编号")
    @Label("航班性质编号")
    private String flightNatureCode;        // 航班性质编号
    @MonitorField(desc = "航班性质名称")
    @Label("航班性质名称")
    private String flightNatureName;        // 航班性质名称
    @MonitorField(desc = "进出港类型编码")
    @Label("进出港类型编码")
    private String inoutTypeCode;        // 进出港类型编码
    @MonitorField(desc = "进出港类型名称")
    @Label("进出港类型名称")
    private String inoutTypeName;        // 进出港类型名称
    @MonitorField(desc = "进出港状态编码")
    @Label("进出港状态编码")
    private String inoutStatusCode;        // 进出港状态编号
    @MonitorField(desc = "进出港状态名称")
    @Label("进出港状态名称")
    private String inoutStatusName;        // 进出港状态名称
    @MonitorField(desc = "起飞地编码")
    @Label("起飞地编码")
    private String departureAirportCode;        // 起飞地编码
    @MonitorField(desc = "起飞地三字码")
    @Label("起飞地三字码")
    private String departureAirportThree;        // 起飞地三字码
    @MonitorField(desc = "起飞地名称")
    @Label("起飞地名称")
    private String departureAirportName;        // 起飞地名称
    private AmsAddress passAirport1;        // 经停地1ID
    @MonitorField(desc = "经停地1编号")
    @Label("经停地1编号")
    private String passAirport1Code;        // 经停地1编号
    @MonitorField(desc = "经停地1名称")
    @Label("经停地1名称")
    private String passAirport1Name;        // 经停地1名称
    private AmsAddress passAirport2;        // 经停地2ID
    @MonitorField(desc = "经停地2编号")
    @Label("经停地2编号")
    private String passAirport2Code;        // 经停地2编号
    @MonitorField(desc = "经停地2名称")
    @Label("经停地2名称")
    private String passAirport2Name;        // 经停地2名称
    @MonitorField(desc = "目的地编号")
    @Label("目的地编号")
    private String arrivalAirportCode;        // 目的地编号
    @MonitorField(desc = "目的地三字码")
    @Label("目的地三字码")
    private String arrivalAirportThree;        // 目的地三字码
    @MonitorField(desc = "目的地名称")
    @Label("目的地名称")
    private String arrivalAirportName;        // 目的地名称
    @MonitorField(desc = "计划起飞时间")
    @Label("计划起飞时间")
    private Date departurePlanTime;        // 计划起飞时间
    @MonitorField(desc = "计划到达时间")
    @Label("计划到达时间")
    private Date arrivalPlanTime;        // 计划到达时间
    @MonitorField(desc = "预计起飞时间")
    @Label("预计起飞时间")
    private Date etd;        // 预计起飞时间
    @MonitorField(desc = "预计到达时间")
    @Label("预计到达时间")
    private Date eta;        // 预计到达时间
    @MonitorField(desc = "实际起飞时间")
    @Label("实际起飞时间")
    private Date atd;        // 实际起飞时间
    @MonitorField(desc = "实际到达时间")
    @Label("实际到达时间")
    private Date ata;        // 实际到达时间
    @MonitorField(desc = "行程时间")
    @Label("行程时间")
    private Long travelTime;        // 行程时间
    @MonitorField(desc = "行李转盘编号")
    @Label("行李转盘编号")
    private String carouselNum;        // 行李转盘
    @MonitorField(desc = "VIP")
    @Label("VIP")
    private String vip;        // VIP
    @MonitorField(desc = "延误原因编号")
    @Label("延误原因编号")
    private String delayResonsCode;        // 延误原因编号
    @MonitorField(desc = "延误原因名称")
    @Label("延误原因名称")
    private String delayResonsName;        // 延误原因名称
    @MonitorField(desc = "延误备注")
    @Label("延误备注")
    private String delayResonsRemark;        // 延误备注
    @MonitorField(desc = "登机口编号")
    @Label("登机口编号")
    private String gateCode;        // 登机口编号
    @MonitorField(desc = "登机口名称")
    @Label("登机口名称")
    private String gateName;        // 登机口名称
    @MonitorField(desc = "登机开始时间")
    @Label("登机开始时间")
    private Date boardingStartTime;        // 登机开始时间
    @MonitorField(desc = "登机结束时间")
    @Label("登机结束时间")
    private Date boardingEndTime;        // 登机结束时间
    @MonitorField(desc = "值机岛编号")
    @Label("值机岛编号")
    private String checkinIslandCode;        // 值机岛编号
    @MonitorField(desc = "值机岛名称")
    @Label("值机岛名称")
    private String checkinIslandName;        // 值机岛名称
    @MonitorField(desc = "值机站台编号")
    @Label("值机站台编号")
    private String checkinCounterCode;        // 值机站台编号
    @MonitorField(desc = "值机站台名称")
    @Label("值机站台名称")
    private String checkinCounterName;        // 值机站台名称
    @MonitorField(desc = "值机开始时间")
    @Label("值机开始时间")
    private Date checkinStartTime;        // 值机开始时间
    @MonitorField(desc = "值机结束时间")
    @Label("值机结束时间")
    private Date checkinEndTime;        // 值机结束时间
    private FlightPlan flightPlan; //航班计划
    private FlightCompanyInfo flightCompanyInfo;
    @MonitorField(desc = "航空公司代码")
    @Label("航空公司代码")
    private String flightCompanyCode;
    @MonitorField(desc = "航空公司名称")
    @Label("航空公司名称")
    private String flightCompanyName;
    @MonitorField(desc = "航班延误时间")
    @Label("航班延误时间")
    private Integer delayTimePend; //航班延误时间是否待定 DELAY_TIME_PEND
    @MonitorField(desc = "航班状态")
    @Label("航班状态")
    private String status;
    @MonitorField(desc = "航班状态名称")
    @Label("航班状态名称")
    private String statusName;
    @MonitorField(desc = "代理人备注")
    @Label("代理人备注")
    private String alterNateRemarks;

    private FlightProperties flightProperties;        // 航班属性ID
    @MonitorField(desc = "航班属性编号")
    @Label("航班属性编号")
    private String flightAttrCode;        // 航班属性编号
    @MonitorField(desc = "航班属性名称")
    @Label("航班属性名称")
    private String flightAttrName;        // 航班属性名称
    @MonitorField(desc = "航班延误地点三字码")
    @Label("航班延误地点三字码")
    private String delayResonsAreaCode;        // 航班延误地点 三字码
    @MonitorField(desc = "航班延误地点名称")
    @Label("航班延误地点名称")
    private String delayResonsAreaName;     // 航班延误地点名称
    @MonitorField(desc = "航班备降机场三字码")
    @Label("航班备降机场三字码")
    private String alterNateAreaCode;  //航班备降机场code
    @MonitorField(desc = "航班备降机场名称")
    @Label("航班备降机场名称")
    private String alterNateAreaName;  //航班备降机场name
    @MonitorField(desc = "补班时间是否待定")
    @Label("补班时间是否待定")
    private String extraFlightTimePend;  // 补班是时间待定
    @MonitorField(desc = "航班取消原因")
    @Label("航班取消原因")
    private String cancelFlightResons;  //取消原因
    @MonitorField(desc = "是否补班")
    @Label("是否补班")
    private Integer isExtraFlight;      //是否补班
    @MonitorField(desc = "催促登机时间")
    @Label("催促登机时间")
    private Date urgeBoardTime;         //催促登机时间
    @MonitorField(desc = "计划登机时间")
    @Label("计划登机时间")
    private Date planBoardingStartTime;            //计划登机时间
    @MonitorField(desc = "计划登机结束时间")
    @Label("计划登机结束时间")
    private Date planBoardingEndTime;               // 计划登机结束时间
    @MonitorField(desc = "客齐时间")
    @Label("客齐时间")
    private Date guestTime;                 // 客齐时间
    @MonitorField(desc = "计划日期的星期号")
    @Label("计划日期的星期号")
    private String planDayOfWeek;         // 计划日期的星期号

    private Date occupiedStart;         //机位 - 开始占用时间

    private Date occupiedEnd;           //机位 - 结束占用时间

    private String leave = "0";         //机位 - 中途是否离开

    private Date leaveDate;             //机位 - 中途离开时间

    private Integer expectedGateNum; // 机位 - 预计占用机位
    @Label("返航时间")
    @MonitorField(desc = "返航时间")
    private Date turnBackTime;

    /******************
     * 资源分配获取字段 start 苍天终有报
     ********************/

    private String inteCarouselCode;    //虚 行李转盘(国内)

    private String intlCarouselCode;    //虚 行李转盘（国际）

    private String inteBoardingGateCode;       // 虚 国内登机口

    private String intlBoardingGateCode;       // 虚拟字段 国际登机口

    private String inteArrivalGateCode;       // 虚 国内到港门

    private String intlArrivalGateCode;       // 虚 国际到港门

    private String inteCheckingCounterCode; // 值机柜台编号(国内)

    private String intlCheckingCounterCode; // 值机柜台编号(国际)

    private Date inteActualStartTime;  // 值机实际开始时间(默认使用国内值机开始时间)

    private Date inteActualEndTime; // 值机实际结束时间(默认使用国内值机结束时间)


    private String inteSecurityCheckinCode; // 安检口(国内) INTE_SECURITY_CHECKIN_CODE

    private String intlSecurityCheckinCode;  // 安检口(国际)

    private String inteSlideCoastCode; // 滑槽(国内) INTE_SLIDE_COAST_CODE

    private String intlSlideCoastCode;  // 滑槽(国际)


    private String inteDepartureHallCode;  // 候机厅(国内) INTE_DEPARTURE_HALL_CODE

    private String intlDepartureHallCode;  // 候机厅(国际)

    private String safeguardTypeCode;     // 保障过程编号

    private String safeguardTypeName;    // 保障过程名称

    private Integer num;  //序号

    /*
     * 增加航站楼信息数据
	 * author dingshuang
	 * date 2016-04-01
	 */
    @Label("航站楼ID")
    @MonitorField(desc = "航站楼ID")
    private String terminalId;//航站楼id

    @Label("航站楼")
    @MonitorField(desc = "航站楼")
    private String terminal; //航站楼


    @MonitorField(desc = "航班延误状态")
    @Label("航班延误状态")
    private String delayStatus; //航班延误状态
    @MonitorField(desc = "航班取消状态")
    @Label("航班取消状态")
    private String cancelFlightStatus; //航班取消状态
    @MonitorField(desc = "补班时间")
    @Label("补班时间")
    private String ext1; // 航班动态发布取消的补班时间
    @MonitorField(desc = "延误状态")
    @Label("延误状态")
    private String ext2; // 延误状态
    @MonitorField(desc = "扩展3")
    @Label("扩展3")
    private String ext3; //扩展3
    @MonitorField(desc = "扩展4")
    @Label("扩展4")
    private String ext4; //扩展4
    @MonitorField(desc = "扩展5")
    @Label("扩展5")
    private String ext5; //扩展5
    @MonitorField(desc = "扩展6")
    @Label("扩展6")
    private String ext6; //扩展6
    @MonitorField(desc = "扩展7")
    @Label("扩展7")
    private String ext7; //扩展7 ams记录同意登机时间 wjp_2017年8月13日16时20分 新加
    @MonitorField(desc = "扩展8")
    @Label("扩展8")
    private String ext8; //扩展8
    @MonitorField(desc = "扩展9")
    @Label("扩展9")
    private String ext9; //扩展9 //配对为ext6 VIP行李总数、中转行李总数显示 wjp_2017年9月7日19时39分 新加
    @MonitorField(desc = "扩展10")
    @Label("扩展10")
    private String ext10; //扩展10

    @MonitorField(desc = "经停地3三字码")
    @Label("经停地3三字码")
    private String ext11; // 经停地3三字码
    @MonitorField(desc = "经停地3名称")
    @Label("经停地3名称")
    private String ext12; // 经停地3名称
    @MonitorField(desc = "经停地4三字码")
    @Label("经停地4三字码")
    private String ext13; // 经停地4三字码
    @MonitorField(desc = "经停地4名称")
    @Label("经停地4名称")
    private String ext14; // 经停地4名称
    @MonitorField(desc = "经停地5三字码")
    @Label("经停地5三字码")
    private String ext15; // 经停地5三字码
    @MonitorField(desc = "经停地5名称")
    @Label("经停地5名称")
    private String ext16; // 经停地5名称
    @MonitorField(desc = "经停地6三字码")
    @Label("经停地6三字码")
    private String ext17; // 经停地6三字码
    @MonitorField(desc = "经停地6名称")
    @Label("经停地6名称")
    private String ext18; // 经停地6名称
    @MonitorField(desc = "扩展19")
    @Label("扩展19")
    private String ext19;
    @MonitorField(desc = "扩展20")
    @Label("扩展20")
    private String ext20;
    @MonitorField(desc = "扩展21")
    @Label("扩展21")
    private String ext21;
    @MonitorField(desc = "扩展22")
    @Label("扩展22")
    private String ext22;
    @MonitorField(desc = "扩展23")
    @Label("扩展23")
    private String ext23;
    @MonitorField(desc = "扩展24")
    @Label("扩展24")
    private String ext24;
    @MonitorField(desc = "扩展25")
    @Label("扩展25")
    private String ext25;


    private String delayResonsRemarkStr;

    public String getDelayResonsRemarkStr() {
        return delayResonsRemarkStr;
    }

    public void setDelayResonsRemarkStr(String delayResonsRemarkStr) {
        this.delayResonsRemarkStr = delayResonsRemarkStr;
    }

    public FlightDynamic() {
        super();
    }

    public FlightDynamic(String id) {
        super(id);
    }

    @Transient
    public Integer getExpectedGateNum() {
        return expectedGateNum;
    }

    public void setExpectedGateNum(Integer expectedGateNum) {
        this.expectedGateNum = expectedGateNum;
    }

    @Transient
    public Date getOccupiedStart() {
        return occupiedStart;
    }

    public void setOccupiedStart(Date occupiedStart) {
        this.occupiedStart = occupiedStart;
    }

    @Transient
    public Date getOccupiedEnd() {
        return occupiedEnd;
    }

    public void setOccupiedEnd(Date occupiedEnd) {
        this.occupiedEnd = occupiedEnd;
    }

    @Transient
    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }

    @Transient
    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getPlanDayOfWeek() {
        return planDayOfWeek;
    }

    public void setPlanDayOfWeek(String planDayOfWeek) {
        this.planDayOfWeek = planDayOfWeek;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    @Length(min = 0, max = 100, message = "代理人编号长度必须介于 0 和 100 之间")
    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    @Length(min = 0, max = 200, message = "代理人名称长度必须介于 0 和 200 之间")
    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    @Length(min = 0, max = 100, message = "飞机号长度必须介于 0 和 100 之间")
    public String getAircraftNum() {
        return aircraftNum;
    }

    public void setAircraftNum(String aircraftNum) {
        this.aircraftNum = aircraftNum;
    }

    @Length(min = 0, max = 100, message = "机型编号长度必须介于 0 和 100 之间")
    public String getAircraftTypeCode() {
        return aircraftTypeCode;
    }

    public void setAircraftTypeCode(String aircraftTypeCode) {
        this.aircraftTypeCode = aircraftTypeCode;
    }

    @Length(min = 0, max = 200, message = "机型名称长度必须介于 0 和 200 之间")
    public String getAircraftTypeName() {
        return aircraftTypeName;
    }

    public void setAircraftTypeName(String aircraftTypeName) {
        this.aircraftTypeName = aircraftTypeName;
    }

    @Length(min = 0, max = 100, message = "机位号长度必须介于 0 和 100 之间")
    public String getPlaceNum() {
        return placeNum;
    }

    public void setPlaceNum(String placeNum) {
        this.placeNum = placeNum;
    }

    @Length(min = 0, max = 100, message = "共享航班号长度必须介于 0 和 100 之间")
    public String getShareFlightNum() {
        return shareFlightNum;
    }

    public void setShareFlightNum(String shareFlightNum) {
        this.shareFlightNum = shareFlightNum;
    }

    @Length(min = 0, max = 100, message = "航班性质编号长度必须介于 0 和 100 之间")
    public String getFlightNatureCode() {
        return flightNatureCode;
    }

    public void setFlightNatureCode(String flightNatureCode) {
        this.flightNatureCode = flightNatureCode;
    }

    @Length(min = 0, max = 200, message = "航班性质名称长度必须介于 0 和 200 之间")
    public String getFlightNatureName() {
        return flightNatureName;
    }

    public void setFlightNatureName(String flightNatureName) {
        this.flightNatureName = flightNatureName;
    }

    @Length(min = 0, max = 100, message = "J进出港类型编码长度必须介于 0 和 100 之间")
    public String getInoutTypeCode() {
        return inoutTypeCode;
    }

    public void setInoutTypeCode(String inoutTypeCode) {
        this.inoutTypeCode = inoutTypeCode;
    }

    @Length(min = 0, max = 200, message = "进出港类型名称长度必须介于 0 和 200 之间")
    public String getInoutTypeName() {
        return inoutTypeName;
    }

    public void setInoutTypeName(String inoutTypeName) {
        this.inoutTypeName = inoutTypeName;
    }

    @Length(min = 0, max = 100, message = "进出港状态编号长度必须介于 0 和 100 之间")
    public String getInoutStatusCode() {
        return inoutStatusCode;
    }

    public void setInoutStatusCode(String inoutStatusCode) {
        this.inoutStatusCode = inoutStatusCode;
    }

    @Length(min = 0, max = 200, message = "进出港状态名称长度必须介于 0 和 200 之间")
    public String getInoutStatusName() {
        return inoutStatusName;
    }

    public void setInoutStatusName(String inoutStatusName) {
        this.inoutStatusName = inoutStatusName;
    }

    @Length(min = 0, max = 100, message = "起飞地编码长度必须介于 0 和 100 之间")
    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    @Length(min = 0, max = 3, message = "起飞地三字码长度必须介于 0 和 3 之间")
    public String getDepartureAirportThree() {
        return departureAirportThree;
    }

    public void setDepartureAirportThree(String departureAirportThree) {
        this.departureAirportThree = departureAirportThree;
    }

    @Length(min = 0, max = 200, message = "起飞地名称长度必须介于 0 和 200 之间")
    public String getDepartureAirportName() {
        return departureAirportName;
    }

    public void setDepartureAirportName(String departureAirportName) {
        this.departureAirportName = departureAirportName;
    }

    @Length(min = 0, max = 100, message = "目的地编号长度必须介于 0 和 100 之间")
    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    @Length(min = 0, max = 3, message = "目的地三字码长度必须介于 0 和 3 之间")
    public String getArrivalAirportThree() {
        return arrivalAirportThree;
    }

    public void setArrivalAirportThree(String arrivalAirportThree) {
        this.arrivalAirportThree = arrivalAirportThree;
    }

    @Length(min = 0, max = 200, message = "目的地名称长度必须介于 0 和 200 之间")
    public String getArrivalAirportName() {
        return arrivalAirportName;
    }

    public void setArrivalAirportName(String arrivalAirportName) {
        this.arrivalAirportName = arrivalAirportName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getDeparturePlanTime() {
        return departurePlanTime;
    }

    public void setDeparturePlanTime(Date departurePlanTime) {
        this.departurePlanTime = departurePlanTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getArrivalPlanTime() {
        return arrivalPlanTime;
    }

    public void setArrivalPlanTime(Date arrivalPlanTime) {
        this.arrivalPlanTime = arrivalPlanTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getEtd() {
        return etd;
    }

    public void setEtd(Date etd) {
        this.etd = etd;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getEta() {
        return eta;
    }

    public void setEta(Date eta) {
        this.eta = eta;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getAtd() {
        return atd;
    }

    public void setAtd(Date atd) {
        this.atd = atd;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getAta() {
        return ata;
    }

    public void setAta(Date ata) {
        this.ata = ata;
    }

    public Long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Long travelTime) {
        this.travelTime = travelTime;
    }

    @Length(min = 0, max = 100, message = "行李转盘长度必须介于 0 和 100 之间")
    public String getCarouselNum() {
        return carouselNum;
    }

    public void setCarouselNum(String carouselNum) {
        this.carouselNum = carouselNum;
    }

    @Length(min = 0, max = 100, message = "VIP长度必须介于 0 和 100 之间")
    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    @Length(min = 0, max = 100, message = "延误原因编号长度必须介于 0 和 100 之间")
    public String getDelayResonsCode() {
        return delayResonsCode;
    }

    public void setDelayResonsCode(String delayResonsCode) {
        this.delayResonsCode = delayResonsCode;
    }

    @Length(min = 0, max = 200, message = "延误原因名称长度必须介于 0 和 200 之间")
    public String getDelayResonsName() {
        return delayResonsName;
    }

    public void setDelayResonsName(String delayResonsName) {
        this.delayResonsName = delayResonsName;
    }

    @Length(min = 0, max = 500, message = "延误备注长度必须介于 0 和 500 之间")
    public String getDelayResonsRemark() {
        return delayResonsRemark;
    }

    public void setDelayResonsRemark(String delayResonsRemark) {
        this.delayResonsRemark = delayResonsRemark;
    }

    @Length(min = 0, max = 100, message = "登机口编号长度必须介于 0 和 100 之间")
    public String getGateCode() {
        return gateCode;
    }

    public void setGateCode(String gateCode) {
        this.gateCode = gateCode;
    }

    @Length(min = 0, max = 200, message = "登机口名称长度必须介于 0 和 200 之间")
    public String getGateName() {
        return gateName;
    }

    public void setGateName(String gateName) {
        this.gateName = gateName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getBoardingStartTime() {
        return boardingStartTime;
    }

    public void setBoardingStartTime(Date boardingStartTime) {
        this.boardingStartTime = boardingStartTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getBoardingEndTime() {
        return boardingEndTime;
    }

    public void setBoardingEndTime(Date boardingEndTime) {
        this.boardingEndTime = boardingEndTime;
    }

    @Length(min = 0, max = 100, message = "值机岛编号长度必须介于 0 和 100 之间")
    public String getCheckinIslandCode() {
        return checkinIslandCode;
    }

    public void setCheckinIslandCode(String checkinIslandCode) {
        this.checkinIslandCode = checkinIslandCode;
    }

    @Length(min = 0, max = 200, message = "值机岛名称长度必须介于 0 和 200 之间")
    public String getCheckinIslandName() {
        return checkinIslandName;
    }

    public void setCheckinIslandName(String checkinIslandName) {
        this.checkinIslandName = checkinIslandName;
    }

    @Length(min = 0, max = 100, message = "值机站台编号长度必须介于 0 和 100 之间")
    public String getCheckinCounterCode() {
        return checkinCounterCode;
    }

    public void setCheckinCounterCode(String checkinCounterCode) {
        this.checkinCounterCode = checkinCounterCode;
    }

    @Length(min = 0, max = 200, message = "值机站台名称长度必须介于 0 和 200 之间")
    public String getCheckinCounterName() {
        return checkinCounterName;
    }

    public void setCheckinCounterName(String checkinCounterName) {
        this.checkinCounterName = checkinCounterName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getCheckinStartTime() {
        return checkinStartTime;
    }

    public void setCheckinStartTime(Date checkinStartTime) {
        this.checkinStartTime = checkinStartTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getCheckinEndTime() {
        return checkinEndTime;
    }

    public void setCheckinEndTime(Date checkinEndTime) {
        this.checkinEndTime = checkinEndTime;
    }

    public FlightPlan getFlightPlan() {
        return flightPlan;
    }

    public void setFlightPlan(FlightPlan flightPlan) {
        this.flightPlan = flightPlan;
    }

    @Length(min = 0, max = 100, message = "航班号长度必须介于 0 和 100 之间")
    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public AmsAddress getPassAirport1() {
        return passAirport1;
    }

    public void setPassAirport1(AmsAddress passAirport1) {
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

    //@Length(min=0, max=100, message="经停地2ID长度必须介于 0 和 100 之间")
    public AmsAddress getPassAirport2() {
        return passAirport2;
    }

    public void setPassAirport2(AmsAddress passAirport2) {
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

    public Integer getDelayTimePend() {
        return delayTimePend;
    }

    public void setDelayTimePend(Integer delayTimePend) {
        this.delayTimePend = delayTimePend;
    }

    public FlightCompanyInfo getFlightCompanyInfo() {
        return flightCompanyInfo;
    }

    public void setFlightCompanyInfo(FlightCompanyInfo flightCompanyInfo) {
        this.flightCompanyInfo = flightCompanyInfo;
    }

    public String getFlightCompanyCode() {
        return flightCompanyCode;
    }

    public void setFlightCompanyCode(String flightCompanyCode) {
        this.flightCompanyCode = flightCompanyCode;
    }

    public String getFlightCompanyName() {
        return flightCompanyName;
    }

    public void setFlightCompanyName(String flightCompanyName) {
        this.flightCompanyName = flightCompanyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getAlterNateRemarks() {
        return alterNateRemarks;
    }

    public void setAlterNateRemarks(String alterNateRemarks) {
        this.alterNateRemarks = alterNateRemarks;
    }

    public FlightProperties getFlightProperties() {
        return flightProperties;
    }

    public void setFlightProperties(FlightProperties flightProperties) {
        this.flightProperties = flightProperties;
    }

    public String getFlightAttrCode() {
        return flightAttrCode;
    }

    public void setFlightAttrCode(String flightAttrCode) {
        this.flightAttrCode = flightAttrCode;
    }

    public String getFlightAttrName() {
        return flightAttrName;
    }

    public void setFlightAttrName(String flightAttrName) {
        this.flightAttrName = flightAttrName;
    }

    public String getDelayResonsAreaCode() {
        return delayResonsAreaCode;
    }

    public void setDelayResonsAreaCode(String delayResonsAreaCode) {
        this.delayResonsAreaCode = delayResonsAreaCode;
    }

    public String getDelayResonsAreaName() {
        return delayResonsAreaName;
    }

    public void setDelayResonsAreaName(String delayResonsAreaName) {
        this.delayResonsAreaName = delayResonsAreaName;
    }

    public String getAlterNateAreaCode() {
        return alterNateAreaCode;
    }

    public void setAlterNateAreaCode(String alterNateAreaCode) {
        this.alterNateAreaCode = alterNateAreaCode;
    }

    public String getAlterNateAreaName() {
        return alterNateAreaName;
    }

    public void setAlterNateAreaName(String alterNateAreaName) {
        this.alterNateAreaName = alterNateAreaName;
    }

    public String getCancelFlightResons() {
        return cancelFlightResons;
    }

    public void setCancelFlightResons(String cancelFlightResons) {
        this.cancelFlightResons = cancelFlightResons;
    }

    public Integer getIsExtraFlight() {
        return isExtraFlight;
    }

    public void setIsExtraFlight(Integer isExtraFlight) {
        this.isExtraFlight = isExtraFlight;
    }

    public String getExtraFlightTimePend() {
        return extraFlightTimePend;
    }

    public void setExtraFlightTimePend(String extraFlightTimePend) {
        this.extraFlightTimePend = extraFlightTimePend;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getUrgeBoardTime() {
        return urgeBoardTime;
    }

    public void setUrgeBoardTime(Date urgeBoardTime) {
        this.urgeBoardTime = urgeBoardTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getPlanBoardingStartTime() {
        return planBoardingStartTime;
    }

    public void setPlanBoardingStartTime(Date planBoardingStartTime) {
        this.planBoardingStartTime = planBoardingStartTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getPlanBoardingEndTime() {
        return planBoardingEndTime;
    }

    public void setPlanBoardingEndTime(Date planBoardingEndTime) {
        this.planBoardingEndTime = planBoardingEndTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getGuestTime() {
        return guestTime;
    }

    public void setGuestTime(Date guestTime) {
        this.guestTime = guestTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getTurnBackTime() {
        return turnBackTime;
    }

    public void setTurnBackTime(Date turnBackTime) {
        this.turnBackTime = turnBackTime;
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

    public String getDelayStatus() {
        return delayStatus;
    }

    public void setDelayStatus(String delayStatus) {
        this.delayStatus = delayStatus;
    }

    public String getCancelFlightStatus() {
        return cancelFlightStatus;
    }

    public void setCancelFlightStatus(String cancelFlightStatus) {
        this.cancelFlightStatus = cancelFlightStatus;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    public String getExt4() {
        return ext4;
    }

    public void setExt4(String ext4) {
        this.ext4 = ext4;
    }

    public String getExt5() {
        return ext5;
    }

    public void setExt5(String ext5) {
        this.ext5 = ext5;
    }

    public String getExt6() {
        return ext6;
    }

    public void setExt6(String ext6) {
        this.ext6 = ext6;
    }

    public String getExt7() {
        return ext7;
    }

    public void setExt7(String ext7) {
        this.ext7 = ext7;
    }

    public String getExt8() {
        return ext8;
    }

    public void setExt8(String ext8) {
        this.ext8 = ext8;
    }

    public String getExt9() {
        return ext9;
    }

    public void setExt9(String ext9) {
        this.ext9 = ext9;
    }

    public String getExt10() {
        return ext10;
    }

    public String getExt11() {
        return ext11;
    }

    public void setExt11(String ext11) {
        this.ext11 = ext11;
    }

    public String getExt12() {
        return ext12;
    }

    public void setExt12(String ext12) {
        this.ext12 = ext12;
    }

    public String getExt13() {
        return ext13;
    }

    public void setExt13(String ext13) {
        this.ext13 = ext13;
    }

    public String getExt14() {
        return ext14;
    }

    public void setExt14(String ext14) {
        this.ext14 = ext14;
    }

    public String getExt15() {
        return ext15;
    }

    public void setExt15(String ext15) {
        this.ext15 = ext15;
    }

    public String getExt16() {
        return ext16;
    }

    public void setExt16(String ext16) {
        this.ext16 = ext16;
    }

    public String getExt17() {
        return ext17;
    }

    public void setExt17(String ext17) {
        this.ext17 = ext17;
    }

    public String getExt18() {
        return ext18;
    }

    public void setExt18(String ext18) {
        this.ext18 = ext18;
    }

    public String getExt19() {
        return ext19;
    }

    public void setExt19(String ext19) {
        this.ext19 = ext19;
    }

    public String getExt20() {
        return ext20;
    }

    public void setExt20(String ext20) {
        this.ext20 = ext20;
    }

    public String getExt21() {
        return ext21;
    }

    public void setExt21(String ext21) {
        this.ext21 = ext21;
    }

    public String getExt22() {
        return ext22;
    }

    public void setExt22(String ext22) {
        this.ext22 = ext22;
    }

    public String getExt23() {
        return ext23;
    }

    public void setExt23(String ext23) {
        this.ext23 = ext23;
    }

    public String getExt24() {
        return ext24;
    }

    public void setExt24(String ext24) {
        this.ext24 = ext24;
    }

    public String getExt25() {
        return ext25;
    }

    public void setExt25(String ext25) {
        this.ext25 = ext25;
    }

    public void setExt10(String ext10) {
        this.ext10 = ext10;
    }

    public String getInteCarouselCode() {
        return inteCarouselCode;
    }

    public void setInteCarouselCode(String inteCarouselCode) {
        this.inteCarouselCode = inteCarouselCode;
    }

    public String getIntlCarouselCode() {
        return intlCarouselCode;
    }

    public void setIntlCarouselCode(String intlCarouselCode) {
        this.intlCarouselCode = intlCarouselCode;
    }

    public String getInteBoardingGateCode() {
        return inteBoardingGateCode;
    }

    public void setInteBoardingGateCode(String inteBoardingGateCode) {
        this.inteBoardingGateCode = inteBoardingGateCode;
    }

    public String getIntlBoardingGateCode() {
        return intlBoardingGateCode;
    }

    public void setIntlBoardingGateCode(String intlBoardingGateCode) {
        this.intlBoardingGateCode = intlBoardingGateCode;
    }

    public String getInteArrivalGateCode() {
        return inteArrivalGateCode;
    }

    public void setInteArrivalGateCode(String inteArrivalGateCode) {
        this.inteArrivalGateCode = inteArrivalGateCode;
    }

    public String getIntlArrivalGateCode() {
        return intlArrivalGateCode;
    }

    public void setIntlArrivalGateCode(String intlArrivalGateCode) {
        this.intlArrivalGateCode = intlArrivalGateCode;
    }

    public String getInteCheckingCounterCode() {
        return inteCheckingCounterCode;
    }

    public void setInteCheckingCounterCode(String inteCheckingCounterCode) {
        this.inteCheckingCounterCode = inteCheckingCounterCode;
    }

    public String getIntlCheckingCounterCode() {
        return intlCheckingCounterCode;
    }

    public void setIntlCheckingCounterCode(String intlCheckingCounterCode) {
        this.intlCheckingCounterCode = intlCheckingCounterCode;
    }

    public Date getInteActualStartTime() {
        return inteActualStartTime;
    }

    public void setInteActualStartTime(Date inteActualStartTime) {
        this.inteActualStartTime = inteActualStartTime;
    }

    public Date getInteActualEndTime() {
        return inteActualEndTime;
    }

    public void setInteActualEndTime(Date inteActualEndTime) {
        this.inteActualEndTime = inteActualEndTime;
    }

    public String getInteSecurityCheckinCode() {
        return inteSecurityCheckinCode;
    }

    public void setInteSecurityCheckinCode(String inteSecurityCheckinCode) {
        this.inteSecurityCheckinCode = inteSecurityCheckinCode;
    }

    public String getIntlSecurityCheckinCode() {
        return intlSecurityCheckinCode;
    }

    public void setIntlSecurityCheckinCode(String intlSecurityCheckinCode) {
        this.intlSecurityCheckinCode = intlSecurityCheckinCode;
    }

    public String getInteSlideCoastCode() {
        return inteSlideCoastCode;
    }

    public void setInteSlideCoastCode(String inteSlideCoastCode) {
        this.inteSlideCoastCode = inteSlideCoastCode;
    }

    public String getIntlSlideCoastCode() {
        return intlSlideCoastCode;
    }

    public void setIntlSlideCoastCode(String intlSlideCoastCode) {
        this.intlSlideCoastCode = intlSlideCoastCode;
    }

    public String getInteDepartureHallCode() {
        return inteDepartureHallCode;
    }

    public void setInteDepartureHallCode(String inteDepartureHallCode) {
        this.inteDepartureHallCode = inteDepartureHallCode;
    }

    public String getIntlDepartureHallCode() {
        return intlDepartureHallCode;
    }

    public void setIntlDepartureHallCode(String intlDepartureHallCode) {
        this.intlDepartureHallCode = intlDepartureHallCode;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }
}