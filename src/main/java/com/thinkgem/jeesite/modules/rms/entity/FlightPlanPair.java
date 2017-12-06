/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.rms.service.FlightPlanPairService;

import java.util.Date;

/**
 * 航班动态配对Entity
 *
 * @author xiaowu
 * @version 2016-05-04
 */

@Monitor(desc = "航班动态配对信息", tableName = "ams_flight_dynamic_pair", service = FlightPlanPairService.class, socketNS = "/global/dynamics")
public class FlightPlanPair extends DataEntity<FlightPlanPair> {

    private static final long serialVersionUID = 1L;
    @Label("进港航班动态ID")
    @MonitorField(desc = "进港航班动态ID")
    private String flightDynimicId;        // 进港航班动态ID
    @Label("进港航班号")
    @MonitorField(desc = "进港航班号")
    private String flightNum;        // 进港航班号
    @Label("机位号")
    @MonitorField(desc = "机位号")
    private String placeNum;        // 机位号
    @Label("进港航班航空公司ID")
    @MonitorField(desc = "进港航班航空公司ID")
    private String flightCompanyId;        // 进港航班航空公司ID
    @Label("进港航班航班公司代码")
    @MonitorField(desc = "进港航班航班公司代码")
    private String flightCompanyCode;        // 进港航班航班公司代码
    @Label("进港航班航空公司名称")
    @MonitorField(desc = "进港航班航空公司名称")
    private String flightCompanyName;        // 进港航班航空公司名称
    @Label("进港共享航班号")
    @MonitorField(desc = "进港共享航班号")
    private String shareFlightNum;        // 进港共享航班号
    @Label("进港航班性质编号")
    @MonitorField(desc = "进港航班性质编号")
    private String flightNatureCode;        // 进港航班性质编号
    @Label("进港航班性质名称")
    @MonitorField(desc = "进港航班性质名称")
    private String flightNatureName;        // 进港航班性质名称
    @Label("进港航班进出港类型编码")
    @MonitorField(desc = "进港航班进出港类型编码")
    private String inoutTypeCode;        // 进港航班进出港类型编码
    @Label("进港航班进出港类型名称")
    @MonitorField(desc = "进港航班进出港类型名称")
    private String inoutTypeName;        // 进港航班进出港类型名称
    @Label("进港航班进出港状态编号")
    @MonitorField(desc = "进港航班进出港状态编号")
    private String inoutStatusCode;        // 进港航班进出港状态编号
    @Label("进港航班进出港状态名称")
    @MonitorField(desc = "进港航班进出港状态名称")
    private String inoutStatusName;        // 进港航班进出港状态名称
    @Label("进港航班起飞地编码")
    @MonitorField(desc = "进港航班起飞地编码")
    private String departureAirportCode;        // 进港航班起飞地编码
    @Label("进港航班起飞地三字码")
    @MonitorField(desc = "进港航班起飞地三字码")
    private String departureAirportThree;        // 进港航班起飞地三字码
    @Label("进港航班起飞地名称")
    @MonitorField(desc = "进港航班起飞地名称")
    private String departureAirportName;        // 进港航班起飞地名称
    @Label("进港航班经停地1编号")
    @MonitorField(desc = "进港航班经停地1编号")
    private String passAirport1Code;        // 进港航班经停地1编号
    @Label("进港航班经停地1名称")
    @MonitorField(desc = "进港航班经停地1名称")
    private String passAirport1Name;        // 进港航班经停地1名称
    @Label("进港航班经停地2编号")
    @MonitorField(desc = "进港航班经停地2编号")
    private String passAirport2Code;        // 进港航班经停地2编号
    @Label("进港航班经停地2名称")
    @MonitorField(desc = "进港航班经停地2名称")
    private String passAirport2Name;        // 进港航班经停地2名称
    @Label("进港航班目的地编号")
    @MonitorField(desc = "进港航班目的地编号")
    private String arrivalAirportCode;        // 进港航班目的地编号
    @Label("进港航班目的地三字码")
    @MonitorField(desc = "进港航班目的地三字码")
    private String arrivalAirportThree;        // 进港航班目的地三字码
    @Label("进港航班目的地名称")
    @MonitorField(desc = "进港航班目的地名称")
    private String arrivalAirportName;        // 进港航班目的地名称
    @Label("进港航班计划起飞时间")
    @MonitorField(desc = "进港航班计划起飞时间")
    private Date departurePlanTime;        // 进港航班计划起飞时间
    @Label("进港航班计划到达时间")
    @MonitorField(desc = "进港航班计划到达时间")
    private Date arrivalPlanTime;        // 进港航班计划到达时间
    @Label("进港航班预计起飞时间")
    @MonitorField(desc = "进港航班预计起飞时间")
    private Date etd;        // 进港航班预计起飞时间
    @Label("进港航班预计到达时间")
    @MonitorField(desc = "进港航班预计到达时间")
    private Date eta;        // 进港航班预计到达时间机位号
    @Label("进港航班实际起飞时间")
    @MonitorField(desc = "进港航班实际起飞时间")
    private Date atd;        // 进港航班实际起飞时间
    @Label("进港航班实际到达时间")
    @MonitorField(desc = "进港航班实际到达时间")
    private Date ata;        // 进港航班实际到达时间
    @Label("进港航班行程时间")
    @MonitorField(desc = "进港航班行程时间")
    private Long travelTime;        // 进港航班行程时间
    @Label("进港航班行李转盘")
    @MonitorField(desc = "进港航班行李转盘")
    private String carouselNum;        // 进港航班行李转盘
    @Label("进港航班VIP")
    @MonitorField(desc = "进港航班VIP")
    private String vip;        // 进港航班VIP
    @Label("进港航班延误原因编号")
    @MonitorField(desc = "进港航班延误原因编号")
    private String delayResonsCode;        // 进港航班延误原因编号
    @Label("进港航班延误原因名称")
    @MonitorField(desc = "进港航班延误原因名称")
    private String delayResonsName;        // 进港航班延误原因名称
    @Label("进港航班延误备注")
    @MonitorField(desc = "进港航班延误备注")
    private String delayResonsRemark;        // 进港航班延误备注
    @Label("进港航班登机口编号")
    @MonitorField(desc = "进港航班登机口编号")
    private String gateCode;        // 进港航班登机口编号
    @Label("进港航班登机口名称")
    @MonitorField(desc = "进港航班登机口名称")
    private String gateName;        // 进港航班登机口名称
    @Label("进港航班登机开始时间")
    @MonitorField(desc = "进港航班登机开始时间")
    private Date boardingStartTime;        // 进港航班登机开始时间
    @Label("进港航班登机结束时间")
    @MonitorField(desc = "进港航班登机结束时间")
    private Date boardingEndTime;        // 进港航班登机结束时间
    @Label("进港航班值机岛编号")
    @MonitorField(desc = "进港航班值机岛编号")
    private String checkinIslandCode;        // 进港航班值机岛编号
    @Label("进港航班值机岛名称")
    @MonitorField(desc = "进港航班值机岛名称")
    private String checkinIslandName;        // 进港航班值机岛名称
    @Label("进港航班值机站台编号")
    @MonitorField(desc = "进港航班值机站台编号")
    private String checkinCounterCode;        // 进港航班值机站台编号
    @Label("进港航班值机站台名称")
    @MonitorField(desc = "进港航班值机站台名称")
    private String checkinCounterName;        // 进港航班值机站台名称
    @Label("进港航班值机开始时间")
    @MonitorField(desc = "进港航班值机开始时间")
    private Date checkinStartTime;        // 进港航班值机开始时间
    @Label("进港航班值机结束时间")
    @MonitorField(desc = "进港航班值机结束时间")
    private Date checkinEndTime;        // 进港航班值机结束时间
    @Label("进港航班是否延误")
    @MonitorField(desc = "进港航班是否延误")
    private Long delayTimePend;        // 进港航班是否延误
    @Label("进港航班状态编码")
    @MonitorField(desc = "进港航班状态编码")
    private String status;        // 进港航班状态编码
    @Label("进港航班状态名称")
    @MonitorField(desc = "进港航班状态名称")
    private String statusName;        // 进港航班状态名称
    @Label("进港航班机型参数ID")
    @MonitorField(desc = "进港航班机型参数ID")
    private String flightPropertiesId;        // 进港航班机型参数ID
    @Label("进港航班航班属性编码")
    @MonitorField(desc = "进港航班航班属性编码")
    private String flightAttrCode;        // 进港航班航班属性编码
    @Label("进港航班航班属性名称")
    @MonitorField(desc = "进港航班航班属性名称")
    private String flightAttrName;        // 进港航班航班属性名称
    @Label("进港航班计划日期星期数")
    @MonitorField(desc = "进港航班计划日期星期数")
    private String planDayOfWeek;        // 进港航班计划日期星期数
    @Label("进港航班备降备注")
    @MonitorField(desc = "进港航班备降备注")
    private String alterNateRemarks;        // 进港航班备降备注
    @Label("进港航班延误地点编码")
    @MonitorField(desc = "进港航班延误地点编码")
    private String delayResonsAreaCode;        // 进港航班延误地点编码
    @Label("进港航班延误地点名称")
    @MonitorField(desc = "进港航班延误地点名称")
    private String delayResonsAreaName;        // 进港航班延误地点名称
    @Label("进港航班备降地点编码")
    @MonitorField(desc = "进港航班备降地点编码")
    private String alterNateAreaCode;        // 进港航班备降地点编码
    @Label("进港航班备降地点名称")
    @MonitorField(desc = "进港航班备降地点名称")
    private String alterNateAreaName;        // 进港航班备降地点名称
    @Label("进港航班取消原因")
    @MonitorField(desc = "进港航班取消原因")
    private String cancelFlightResons;        // 进港航班取消原因
    @Label("进港航班是否外航")
    @MonitorField(desc = "进港航班是否外航")
    private Integer isExtraFlight;        // 进港航班是否外航
    @Label("进港航班补班时间是否待定")
    @MonitorField(desc = "进港航班补班时间是否待定")
    private Integer extraFlightTimePend;        // 进港航班补班时间是否待定
    @Label("进港航班催促登机时间")
    @MonitorField(desc = "进港航班催促登机时间")
    private Date urgeBoardTime;        // 进港航班催促登机时间
    @Label("进港航班登机开始时间")
    @MonitorField(desc = "进港航班登机开始时间")
    private Date planBoardingStarttime;        // 进港航班登机开始时间
    @Label("进港航班登机结束时间")
    @MonitorField(desc = "进港航班登机结束时间")
    private Date planBoardingEndtime;        // 进港航班登机结束时间
    @Label("进港航班客齐时间")
    @MonitorField(desc = "进港航班客齐时间")
    private Date guestTime;        // 进港航班客齐时间
    @Label("进港航班航站楼ID")
    @MonitorField(desc = "进港航班航站楼ID")
    private String terminalId;        // 进港航班航站楼ID
    @Label("进港航班航站楼")
    @MonitorField(desc = "进港航班航站楼")
    private String terminal;        // 进港航班航站楼
    @Label("进港航班返航时间")
    @MonitorField(desc = "进港航班返航时间")
    private Date turnBackTime;        // 进港航班返航时间
    @Label("出港航班动态ID")
    @MonitorField(desc = "出港航班动态ID")
    private String flightDynimicId2;        // 出港航班动态ID
    @Label("出港航班号")
    @MonitorField(desc = "出港航班号")
    private String flightNum2;        // 出港航班号
    @Label("出港航班机位号")
    @MonitorField(desc = "出港航班机位号")
    private String placeNum2;        // 出港航班机位号
    @Label("出港航班航空公司ID")
    @MonitorField(desc = "出港航班航空公司ID")
    private String flightCompanyId2;        // 出港航班航空公司ID
    @Label("出港航班航空公司代码")
    @MonitorField(desc = "出港航班航空公司代码")
    private String flightCompanyCode2;        // 出港航班航空公司代码
    @Label("出港航班航空公司名称")
    @MonitorField(desc = "出港航班航空公司名称")
    private String flightCompanyName2;        // 出港航班航空公司名称
    @Label("出港航班共享航班号")
    @MonitorField(desc = "出港航班共享航班号")
    private String shareFlightNum2;        // 出港航班共享航班号
    @Label("出港航班性质编号")
    @MonitorField(desc = "出港航班性质编号")
    private String flightNatureCode2;        // 出港航班性质编号
    @Label("出港航班性质名称")
    @MonitorField(desc = "出港航班性质名称")
    private String flightNatureName2;        // 出港航班性质名称
    @Label("出港航班进出港类型编码")
    @MonitorField(desc = "出港航班进出港类型编码")
    private String inoutTypeCode2;        // 出港航班进出港类型编码
    @Label("出港航班进出港类型名称")
    @MonitorField(desc = "出港航班进出港类型名称")
    private String inoutTypeName2;        // 出港航班进出港类型名称
    @Label("出港航班进出港状态编号")
    @MonitorField(desc = "出港航班进出港状态编号")
    private String inoutStatusCode2;        // 出港航班进出港状态编号
    @Label("出港航班进出港状态名称")
    @MonitorField(desc = "出港航班进出港状态名称")
    private String inoutStatusName2;        // 出港航班进出港状态名称
    @Label("出港航班起飞地编码")
    @MonitorField(desc = "出港航班起飞地编码")
    private String departureAirportCode2;        // 出港航班起飞地编码
    @Label("出港航班起飞地三字码")
    @MonitorField(desc = "出港航班起飞地三字码")
    private String departureAirportThree2;        // 出港航班起飞地三字码
    @Label("出港航班起飞地名称")
    @MonitorField(desc = "出港航班起飞地名称")
    private String departureAirportName2;        // 出港航班起飞地名称
    @Label("出港航班经停地1编号")
    @MonitorField(desc = "出港航班经停地1编号")
    private String passAirport1Code2;        // 出港航班经停地1编号
    @Label("出港航班经停地1名称")
    @MonitorField(desc = "出港航班经停地1名称")
    private String passAirport1Name2;        // 出港航班经停地1名称
    @Label("出港航班经停地2编号")
    @MonitorField(desc = "出港航班经停地2编号")
    private String passAirport2Code2;        // 出港航班经停地2编号
    @Label("出港航班经停地2名称")
    @MonitorField(desc = "出港航班经停地2名称")
    private String passAirport2Name2;        // 出港航班经停地2名称
    @Label("出港航班目的地编号")
    @MonitorField(desc = "出港航班目的地编号")
    private String arrivalAirportCode2;        // 出港航班目的地编号
    @Label("出港航班目的地三字码")
    @MonitorField(desc = "出港航班目的地三字码")
    private String arrivalAirportThree2;        // 出港航班目的地三字码
    @Label("出港航班目的地名称")
    @MonitorField(desc = "出港航班目的地名称")
    private String arrivalAirportName2;        // 出港航班目的地名称
    @Label("出港航班计划起飞时间")
    @MonitorField(desc = "出港航班计划起飞时间")
    private Date departurePlanTime2;        // 出港航班计划起飞时间
    @Label("出港航班计划到达时间")
    @MonitorField(desc = "出港航班计划到达时间")
    private Date arrivalPlanTime2;        // 出港航班计划到达时间
    @Label("出港航班预计起飞时间")
    @MonitorField(desc = "出港航班预计起飞时间")
    private Date etd2;        // 出港航班预计起飞时间
    @Label("出港航班预计到达时间")
    @MonitorField(desc = "出港航班预计到达时间")
    private Date eta2;        // 出港航班预计到达时间
    @Label("出港航班实际起飞时间")
    @MonitorField(desc = "出港航班实际起飞时间")
    private Date atd2;        // 出港航班实际起飞时间
    @Label("出港航班实际到达时间")
    @MonitorField(desc = "出港航班实际到达时间")
    private Date ata2;        // 出港航班实际到达时间
    @Label("出港航班行程时间")
    @MonitorField(desc = "出港航班行程时间")
    private Long travelTime2;        // 出港航班行程时间
    @Label("出港航班行李转盘")
    @MonitorField(desc = "出港航班行李转盘")
    private String carouselNum2;        // 出港航班行李转盘
    @Label("出港航班VIP")
    @MonitorField(desc = "出港航班VIP")
    private String vip2;        // 出港航班VIP
    @Label("出港航班延误原因编号")
    @MonitorField(desc = "出港航班延误原因编号")
    private String delayResonsCode2;        // 出港航班延误原因编号
    @Label("出港航班延误原因名称")
    @MonitorField(desc = "出港航班延误原因名称")
    private String delayResonsName2;        // 出港航班延误原因名称
    @Label("出港航班延误备注")
    @MonitorField(desc = "出港航班延误备注")
    private String delayResonsRemark2;        // 出港航班延误备注
    @Label("出港航班登机口编号")
    @MonitorField(desc = "出港航班登机口编号")
    private String gateCode2;        // 出港航班登机口编号
    @Label("出港航班登机口名称")
    @MonitorField(desc = "出港航班登机口名称")
    private String gateName2;        // 出港航班登机口名称
    @Label("出港航班登机开始时间")
    @MonitorField(desc = "出港航班登机开始时间")
    private Date boardingStartTime2;        // 出港航班登机开始时间
    @Label("出港航班登机结束时间")
    @MonitorField(desc = "出港航班登机结束时间")
    private Date boardingEndTime2;        // 出港航班登机结束时间
    @Label("出港航班值机岛编号")
    @MonitorField(desc = "出港航班值机岛编号")
    private String checkinIslandCode2;        // 出港航班值机岛编号
    @Label("出港航班值机岛名称")
    @MonitorField(desc = "出港航班值机岛名称")
    private String checkinIslandName2;        // 出港航班值机岛名称
    @Label("出港航班值机站台编号")
    @MonitorField(desc = "出港航班值机站台编号")
    private String checkinCounterCode2;        // 出港航班值机站台编号
    @Label("出港航班值机站台名称")
    @MonitorField(desc = "出港航班值机站台名称")
    private String checkinCounterName2;        // 出港航班值机站台名称
    @Label("出港航班值机开始时间")
    @MonitorField(desc = "出港航班值机开始时间")
    private Date checkinStartTime2;        // 出港航班值机开始时间
    @Label("出港航班值机结束时间")
    @MonitorField(desc = "出港航班值机结束时间")
    private Date checkinEndTime2;        // 出港航班值机结束时间
    @Label("出港航班是否延误")
    @MonitorField(desc = "出港航班是否延误")
    private Long delayTimePend2;        // 出港航班是否延误
    @Label("出港航班状态编码")
    @MonitorField(desc = "出港航班状态编码")
    private String status2;        // 出港航班状态编码
    @Label("出港航班状态名称")
    @MonitorField(desc = "出港航班状态名称")
    private String statusName2;        // 出港航班状态名称
    @Label("出港航班机型参数ID")
    @MonitorField(desc = "出港航班机型参数ID")
    private String flightPropertiesId2;        // 出港航班机型参数ID
    @Label("出港航班属性编码")
    @MonitorField(desc = "出港航班属性编码")
    private String flightAttrCode2;        // 出港航班属性编码
    @Label("出港航班属性名称")
    @MonitorField(desc = "出港航班属性名称")
    private String flightAttrName2;        // 出港航班属性名称
    @Label("出港航班计划日期星期数")
    @MonitorField(desc = "出港航班计划日期星期数")
    private String planDayOfWeek2;        // 出港航班计划日期星期数
    @Label("出港航班备降备注")
    @MonitorField(desc = "出港航班备降备注")
    private String alterNateRemarks2;        // 出港航班备降备注
    @Label("出港航班延误地名编码")
    @MonitorField(desc = "出港航班延误地名编码")
    private String delayResonsAreaCode2;        // 出港航班延误地名编码
    @Label("出港航班延误地名名称")
    @MonitorField(desc = "出港航班延误地名名称")
    private String delayResonsAreaName2;        // 出港航班延误地名名称
    @Label("出港航班备降地点编码")
    @MonitorField(desc = "出港航班备降地点编码")
    private String alterNateAreaCode2;        // 出港航班备降地点编码
    @Label("出港航班备降地点名称")
    @MonitorField(desc = "出港航班备降地点名称")
    private String alterNateAreaName2;        // 出港航班备降地点名称
    @Label("出港航班取消原因")
    @MonitorField(desc = "出港航班取消原因")
    private String cancelFlightResons2;        // 出港航班取消原因
    @Label("出港航班是否外航")
    @MonitorField(desc = "出港航班是否外航")
    private Integer isExtraFlight2;        // 出港航班是否外航
    @Label("出港航班补班时间是否待定")
    @MonitorField(desc = "出港航班补班时间是否待定")
    private Integer extraFlightTimePend2;        // 出港航班补班时间是否待定
    @Label("出港航班催促登机时间")
    @MonitorField(desc = "出港航班催促登机时间")
    private Date urgeBoardTime2;        // 出港航班催促登机时间
    @Label("出港航班开始登机时间")
    @MonitorField(desc = "出港航班开始登机时间")
    private Date planBoardingStarttime2;        // 出港航班开始登机时间
    @Label("出港航班结束登机时间")
    @MonitorField(desc = "出港航班结束登机时间")
    private Date planBoardingEndtime2;        // 出港航班结束登机时间
    @Label("出港航班客齐时间")
    @MonitorField(desc = "出港航班客齐时间")
    private Date guestTime2;        // 出港航班客齐时间
    @Label("出港航班航站楼ID")
    @MonitorField(desc = "出港航班航站楼ID")
    private String terminalId2;        // 出港航班航站楼ID
    @Label("出港航班航站楼")
    @MonitorField(desc = "出港航班航站楼")
    private String terminal2;        // 出港航班航站楼
    @Label("出港航班返航时间")
    @MonitorField(desc = "出港航班返航时间")
    private Date turnBackTime2;        // 出港航班返航时间
    @Label("计划日期")
    @MonitorField(desc = "计划日期")
    private Date planDate;        // 计划日期
    @Label("代理人编号")
    @MonitorField(desc = "代理人编号")
    private String agentCode;        // 代理人编号
    @Label("代理人名称")
    @MonitorField(desc = "代理人名称")
    private String agentName;        // 代理人名称
    @Label("飞机号")
    @MonitorField(desc = "飞机号")
    private String aircraftNum;        // 飞机号
    @Label("机型编号")
    @MonitorField(desc = "机型编号")
    private String aircraftTypeCode;        // 机型编号
    @Label("机型名称")
    @MonitorField(desc = "机型名称")
    private String aircraftTypeName;        // 机型名称
    @Label("保障类型编码")
    @MonitorField(desc = "保障类型编码")
    private String safecuardTypeCode;        // 保障类型编码
    @Label("保障类型名称")
    @MonitorField(desc = "保障类型名称")
    private String safecuardTypeName;        // 保障类型名称

    @Label("航线")
    @MonitorField(desc = "航线")
    private String travelLine;

    private Date occupiedStart;         //机位 - 开始占用时间

    private Date occupiedEnd;           //机位 - 结束占用时间

    private String leave = "0";         //机位 - 中途是否离开

    private Date leaveDate;             //机位 - 中途离开时间

    private String detailId;

    private Integer expectedGateNum; // 机位 - 预计占用机位

    @MonitorField(desc = "进港航班延误状态")
    @Label("进港航班延误状态")
    private String delayStatus; //航班延误状态

    @MonitorField(desc = "进港航班取消状态")
    @Label("进港航班取消状态")
    private String cancelFlightStatus; //航班取消状态
    @MonitorField(desc = "扩展1")
    @Label("扩展1")
    private String ext1; //扩展1
    @MonitorField(desc = "扩展2")
    @Label("扩展2")
    private String ext2; //扩展2
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
    private String ext6; //扩展6 //动态为ext9 VIP行李总数、中转行李总数显示 wjp_2017年9月7日19时39分 新加
    @MonitorField(desc = "扩展7")
    @Label("扩展7")
    private String ext7; //扩展7
    @MonitorField(desc = "扩展8")
    @Label("扩展8")
    private String ext8; //扩展8
    @MonitorField(desc = "扩展9")
    @Label("扩展9")
    private String ext9; //扩展9
    @MonitorField(desc = "扩展10")
    @Label("扩展10")
    private String ext10; //扩展10

    @MonitorField(desc = "扩展11")
    @Label("扩展11")
    private String ext11;
    @MonitorField(desc = "扩展12")
    @Label("扩展12")
    private String ext12;
    @MonitorField(desc = "扩展13")
    @Label("扩展13")
    private String ext13;
    @MonitorField(desc = "扩展14")
    @Label("扩展14")
    private String ext14;
    @MonitorField(desc = "扩展15")
    @Label("扩展15")
    private String ext15;
    @MonitorField(desc = "扩展16")
    @Label("扩展16")
    private String ext16;
    @MonitorField(desc = "扩展17")
    @Label("扩展17")
    private String ext17;
    @MonitorField(desc = "扩展18")
    @Label("扩展18")
    private String ext18;
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
    @MonitorField(desc = "出港航班延误状态")
    @Label("出港航班延误状态")
    private String delayStatus2; //航班延误状态
    @MonitorField(desc = "出港航班取消状态")
    @Label("出港航班取消状态")
    private String cancelFlightStatus2; //航班取消状态

    @TipMainField
    @MonitorField(desc = "提醒标识")

    private String virtualTipField;

    private Integer num;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    //    private String _safeguardMainTypeName;

    /****************** 资源分配获取字段 start 苍天终有报 ********************/


    private String inteCarouselCode;    //虚 行李转盘(国内)

    private String intlCarouselCode;    //虚 行李转盘（国际）

    private String inteBoardingGateCode;       // 虚 国内登机口

    private String intlBoardingGateCode;       // 虚拟字段 国际登机口

    private Date inteBoardingActualStartTime;  // 值机实际开始时间(默认使用国内值机开始时间)   暂时没有使用

    private Date inteBoardingActualEndTime; // 值机实际结束时间(默认使用国内值机结束时间)      暂时没有使用

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

    /****************** 资源分配获取字段 end 天道好轮回 ********************/


    public String getVirtualTipField() {

        String in = StringUtils.trimToEmpty(flightCompanyCode) + StringUtils.trimToEmpty(flightNum);
        String out = StringUtils.trimToEmpty(flightCompanyCode2) + StringUtils.trimToEmpty(flightNum2);


        String finalValue = "";

        if (StringUtils.isNotEmpty(in) && StringUtils.isNotEmpty(out))
            finalValue = in + "/" + out;
        else if (StringUtils.isNotEmpty(in))
            finalValue = in;
        else
            finalValue = out;

        return finalValue + " " + StringUtils.trimToEmpty(aircraftTypeCode);
    }

    public void setVirtualTipField(String virtualTipField) {
        this.virtualTipField = virtualTipField;
    }

    public String getTravelLine() {
        return travelLine;
    }

    public void setTravelLine(String travelLine) {
        this.travelLine = travelLine;
    }

    public Integer getExpectedGateNum() {
        return expectedGateNum;
    }

    public void setExpectedGateNum(Integer expectedGateNum) {
        this.expectedGateNum = expectedGateNum;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getOccupiedStart() {
        return occupiedStart;
    }

    public void setOccupiedStart(Date occupiedStart) {
        this.occupiedStart = occupiedStart;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getOccupiedEnd() {
        return occupiedEnd;
    }

    public void setOccupiedEnd(Date occupiedEnd) {
        this.occupiedEnd = occupiedEnd;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public FlightPlanPair() {
        super();
    }

    public FlightPlanPair(String id) {
        super(id);
    }

    public String getFlightDynimicId() {
        return flightDynimicId;
    }

    public void setFlightDynimicId(String flightDynimicId) {
        this.flightDynimicId = flightDynimicId;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public String getPlaceNum() {
        return placeNum;
    }

    public void setPlaceNum(String placeNum) {
        this.placeNum = placeNum;
    }

    public String getFlightCompanyId() {
        return flightCompanyId;
    }

    public void setFlightCompanyId(String flightCompanyId) {
        this.flightCompanyId = flightCompanyId;
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

    public String getShareFlightNum() {
        return shareFlightNum;
    }

    public void setShareFlightNum(String shareFlightNum) {
        this.shareFlightNum = shareFlightNum;
    }

    public String getFlightNatureCode() {
        return flightNatureCode;
    }

    public void setFlightNatureCode(String flightNatureCode) {
        this.flightNatureCode = flightNatureCode;
    }

    public String getFlightNatureName() {
        return flightNatureName;
    }

    public void setFlightNatureName(String flightNatureName) {
        this.flightNatureName = flightNatureName;
    }

    public String getInoutTypeCode() {
        return inoutTypeCode;
    }

    public void setInoutTypeCode(String inoutTypeCode) {
        this.inoutTypeCode = inoutTypeCode;
    }

    public String getInoutTypeName() {
        return inoutTypeName;
    }

    public void setInoutTypeName(String inoutTypeName) {
        this.inoutTypeName = inoutTypeName;
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

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    public String getDepartureAirportThree() {
        return departureAirportThree;
    }

    public void setDepartureAirportThree(String departureAirportThree) {
        this.departureAirportThree = departureAirportThree;
    }

    public String getDepartureAirportName() {
        return departureAirportName;
    }

    public void setDepartureAirportName(String departureAirportName) {
        this.departureAirportName = departureAirportName;
    }

    public String getPassAirport1Code() {
        return passAirport1Code;
    }

    public void setPassAirport1Code(String passAirport1Code) {
        this.passAirport1Code = passAirport1Code;
    }

    public String getPassAirport1Name() {
        return passAirport1Name;
    }

    public void setPassAirport1Name(String passAirport1Name) {
        this.passAirport1Name = passAirport1Name;
    }

    public String getPassAirport2Code() {
        return passAirport2Code;
    }

    public void setPassAirport2Code(String passAirport2Code) {
        this.passAirport2Code = passAirport2Code;
    }

    public String getPassAirport2Name() {
        return passAirport2Name;
    }

    public void setPassAirport2Name(String passAirport2Name) {
        this.passAirport2Name = passAirport2Name;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public String getArrivalAirportThree() {
        return arrivalAirportThree;
    }

    public void setArrivalAirportThree(String arrivalAirportThree) {
        this.arrivalAirportThree = arrivalAirportThree;
    }

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

    public String getCarouselNum() {
        return carouselNum;
    }

    public void setCarouselNum(String carouselNum) {
        this.carouselNum = carouselNum;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getDelayResonsCode() {
        return delayResonsCode;
    }

    public void setDelayResonsCode(String delayResonsCode) {
        this.delayResonsCode = delayResonsCode;
    }

    public String getDelayResonsName() {
        return delayResonsName;
    }

    public void setDelayResonsName(String delayResonsName) {
        this.delayResonsName = delayResonsName;
    }

    public String getDelayResonsRemark() {
        return delayResonsRemark;
    }

    public void setDelayResonsRemark(String delayResonsRemark) {
        this.delayResonsRemark = delayResonsRemark;
    }

    public String getGateCode() {
        return gateCode;
    }

    public void setGateCode(String gateCode) {
        this.gateCode = gateCode;
    }

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

    public String getCheckinIslandCode() {
        return checkinIslandCode;
    }

    public void setCheckinIslandCode(String checkinIslandCode) {
        this.checkinIslandCode = checkinIslandCode;
    }

    public String getCheckinIslandName() {
        return checkinIslandName;
    }

    public void setCheckinIslandName(String checkinIslandName) {
        this.checkinIslandName = checkinIslandName;
    }

    public String getCheckinCounterCode() {
        return checkinCounterCode;
    }

    public void setCheckinCounterCode(String checkinCounterCode) {
        this.checkinCounterCode = checkinCounterCode;
    }

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

    public Long getDelayTimePend() {
        return delayTimePend;
    }

    public void setDelayTimePend(Long delayTimePend) {
        this.delayTimePend = delayTimePend;
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

    public String getFlightPropertiesId() {
        return flightPropertiesId;
    }

    public void setFlightPropertiesId(String flightPropertiesId) {
        this.flightPropertiesId = flightPropertiesId;
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

    public String getPlanDayOfWeek() {
        return planDayOfWeek;
    }

    public void setPlanDayOfWeek(String planDayOfWeek) {
        this.planDayOfWeek = planDayOfWeek;
    }

    public String getAlterNateRemarks() {
        return alterNateRemarks;
    }

    public void setAlterNateRemarks(String alterNateRemarks) {
        this.alterNateRemarks = alterNateRemarks;
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

    public Integer getExtraFlightTimePend() {
        return extraFlightTimePend;
    }

    public void setExtraFlightTimePend(Integer extraFlightTimePend) {
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
    public Date getPlanBoardingStarttime() {
        return planBoardingStarttime;
    }

    public void setPlanBoardingStarttime(Date planBoardingStarttime) {
        this.planBoardingStarttime = planBoardingStarttime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getPlanBoardingEndtime() {
        return planBoardingEndtime;
    }

    public void setPlanBoardingEndtime(Date planBoardingEndtime) {
        this.planBoardingEndtime = planBoardingEndtime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getGuestTime() {
        return guestTime;
    }

    public void setGuestTime(Date guestTime) {
        this.guestTime = guestTime;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getTurnBackTime() {
        return turnBackTime;
    }

    public void setTurnBackTime(Date turnBackTime) {
        this.turnBackTime = turnBackTime;
    }

    public String getFlightDynimicId2() {
        return flightDynimicId2;
    }

    public void setFlightDynimicId2(String flightDynimicId2) {
        this.flightDynimicId2 = flightDynimicId2;
    }

    public String getFlightNum2() {
        return flightNum2;
    }

    public void setFlightNum2(String flightNum2) {
        this.flightNum2 = flightNum2;
    }

    public String getPlaceNum2() {
        return placeNum2;
    }

    public void setPlaceNum2(String placeNum2) {
        this.placeNum2 = placeNum2;
    }

    public String getFlightCompanyId2() {
        return flightCompanyId2;
    }

    public void setFlightCompanyId2(String flightCompanyId2) {
        this.flightCompanyId2 = flightCompanyId2;
    }

    public String getFlightCompanyCode2() {
        return flightCompanyCode2;
    }

    public void setFlightCompanyCode2(String flightCompanyCode2) {
        this.flightCompanyCode2 = flightCompanyCode2;
    }

    public String getFlightCompanyName2() {
        return flightCompanyName2;
    }

    public void setFlightCompanyName2(String flightCompanyName2) {
        this.flightCompanyName2 = flightCompanyName2;
    }

    public String getShareFlightNum2() {
        return shareFlightNum2;
    }

    public void setShareFlightNum2(String shareFlightNum2) {
        this.shareFlightNum2 = shareFlightNum2;
    }

    public String getFlightNatureCode2() {
        return flightNatureCode2;
    }

    public void setFlightNatureCode2(String flightNatureCode2) {
        this.flightNatureCode2 = flightNatureCode2;
    }

    public String getFlightNatureName2() {
        return flightNatureName2;
    }

    public void setFlightNatureName2(String flightNatureName2) {
        this.flightNatureName2 = flightNatureName2;
    }

    public String getInoutTypeCode2() {
        return inoutTypeCode2;
    }

    public void setInoutTypeCode2(String inoutTypeCode2) {
        this.inoutTypeCode2 = inoutTypeCode2;
    }

    public String getInoutTypeName2() {
        return inoutTypeName2;
    }

    public void setInoutTypeName2(String inoutTypeName2) {
        this.inoutTypeName2 = inoutTypeName2;
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

    public String getDepartureAirportCode2() {
        return departureAirportCode2;
    }

    public void setDepartureAirportCode2(String departureAirportCode2) {
        this.departureAirportCode2 = departureAirportCode2;
    }

    public String getDepartureAirportThree2() {
        return departureAirportThree2;
    }

    public void setDepartureAirportThree2(String departureAirportThree2) {
        this.departureAirportThree2 = departureAirportThree2;
    }

    public String getDepartureAirportName2() {
        return departureAirportName2;
    }

    public void setDepartureAirportName2(String departureAirportName2) {
        this.departureAirportName2 = departureAirportName2;
    }

    public String getPassAirport1Code2() {
        return passAirport1Code2;
    }

    public void setPassAirport1Code2(String passAirport1Code2) {
        this.passAirport1Code2 = passAirport1Code2;
    }

    public String getPassAirport1Name2() {
        return passAirport1Name2;
    }

    public void setPassAirport1Name2(String passAirport1Name2) {
        this.passAirport1Name2 = passAirport1Name2;
    }

    public String getPassAirport2Code2() {
        return passAirport2Code2;
    }

    public void setPassAirport2Code2(String passAirport2Code2) {
        this.passAirport2Code2 = passAirport2Code2;
    }

    public String getPassAirport2Name2() {
        return passAirport2Name2;
    }

    public void setPassAirport2Name2(String passAirport2Name2) {
        this.passAirport2Name2 = passAirport2Name2;
    }

    public String getArrivalAirportCode2() {
        return arrivalAirportCode2;
    }

    public void setArrivalAirportCode2(String arrivalAirportCode2) {
        this.arrivalAirportCode2 = arrivalAirportCode2;
    }

    public String getArrivalAirportThree2() {
        return arrivalAirportThree2;
    }

    public void setArrivalAirportThree2(String arrivalAirportThree2) {
        this.arrivalAirportThree2 = arrivalAirportThree2;
    }

    public String getArrivalAirportName2() {
        return arrivalAirportName2;
    }

    public void setArrivalAirportName2(String arrivalAirportName2) {
        this.arrivalAirportName2 = arrivalAirportName2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getDeparturePlanTime2() {
        return departurePlanTime2;
    }

    public void setDeparturePlanTime2(Date departurePlanTime2) {
        this.departurePlanTime2 = departurePlanTime2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getArrivalPlanTime2() {
        return arrivalPlanTime2;
    }

    public void setArrivalPlanTime2(Date arrivalPlanTime2) {
        this.arrivalPlanTime2 = arrivalPlanTime2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getEtd2() {
        return etd2;
    }

    public void setEtd2(Date etd2) {
        this.etd2 = etd2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getEta2() {
        return eta2;
    }

    public void setEta2(Date eta2) {
        this.eta2 = eta2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getAtd2() {
        return atd2;
    }

    public void setAtd2(Date atd2) {
        this.atd2 = atd2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getAta2() {
        return ata2;
    }

    public void setAta2(Date ata2) {
        this.ata2 = ata2;
    }

    public Long getTravelTime2() {
        return travelTime2;
    }

    public void setTravelTime2(Long travelTime2) {
        this.travelTime2 = travelTime2;
    }

    public String getCarouselNum2() {
        return carouselNum2;
    }

    public void setCarouselNum2(String carouselNum2) {
        this.carouselNum2 = carouselNum2;
    }

    public String getVip2() {
        return vip2;
    }

    public void setVip2(String vip2) {
        this.vip2 = vip2;
    }

    public String getDelayResonsCode2() {
        return delayResonsCode2;
    }

    public void setDelayResonsCode2(String delayResonsCode2) {
        this.delayResonsCode2 = delayResonsCode2;
    }

    public String getDelayResonsName2() {
        return delayResonsName2;
    }

    public void setDelayResonsName2(String delayResonsName2) {
        this.delayResonsName2 = delayResonsName2;
    }

    public String getDelayResonsRemark2() {
        return delayResonsRemark2;
    }

    public void setDelayResonsRemark2(String delayResonsRemark2) {
        this.delayResonsRemark2 = delayResonsRemark2;
    }

    public String getGateCode2() {
        return gateCode2;
    }

    public void setGateCode2(String gateCode2) {
        this.gateCode2 = gateCode2;
    }

    public String getGateName2() {
        return gateName2;
    }

    public void setGateName2(String gateName2) {
        this.gateName2 = gateName2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getBoardingStartTime2() {
        return boardingStartTime2;
    }

    public void setBoardingStartTime2(Date boardingStartTime2) {
        this.boardingStartTime2 = boardingStartTime2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getBoardingEndTime2() {
        return boardingEndTime2;
    }

    public void setBoardingEndTime2(Date boardingEndTime2) {
        this.boardingEndTime2 = boardingEndTime2;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getCheckinStartTime2() {
        return checkinStartTime2;
    }

    public void setCheckinStartTime2(Date checkinStartTime2) {
        this.checkinStartTime2 = checkinStartTime2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getCheckinEndTime2() {
        return checkinEndTime2;
    }

    public void setCheckinEndTime2(Date checkinEndTime2) {
        this.checkinEndTime2 = checkinEndTime2;
    }

    public Long getDelayTimePend2() {
        return delayTimePend2;
    }

    public void setDelayTimePend2(Long delayTimePend2) {
        this.delayTimePend2 = delayTimePend2;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getStatusName2() {
        return statusName2;
    }

    public void setStatusName2(String statusName2) {
        this.statusName2 = statusName2;
    }

    public String getFlightPropertiesId2() {
        return flightPropertiesId2;
    }

    public void setFlightPropertiesId2(String flightPropertiesId2) {
        this.flightPropertiesId2 = flightPropertiesId2;
    }

    public String getFlightAttrCode2() {
        return flightAttrCode2;
    }

    public void setFlightAttrCode2(String flightAttrCode2) {
        this.flightAttrCode2 = flightAttrCode2;
    }

    public String getFlightAttrName2() {
        return flightAttrName2;
    }

    public void setFlightAttrName2(String flightAttrName2) {
        this.flightAttrName2 = flightAttrName2;
    }

    public String getPlanDayOfWeek2() {
        return planDayOfWeek2;
    }

    public void setPlanDayOfWeek2(String planDayOfWeek2) {
        this.planDayOfWeek2 = planDayOfWeek2;
    }

    public String getAlterNateRemarks2() {
        return alterNateRemarks2;
    }

    public void setAlterNateRemarks2(String alterNateRemarks2) {
        this.alterNateRemarks2 = alterNateRemarks2;
    }

    public String getDelayResonsAreaCode2() {
        return delayResonsAreaCode2;
    }

    public void setDelayResonsAreaCode2(String delayResonsAreaCode2) {
        this.delayResonsAreaCode2 = delayResonsAreaCode2;
    }

    public String getDelayResonsAreaName2() {
        return delayResonsAreaName2;
    }

    public void setDelayResonsAreaName2(String delayResonsAreaName2) {
        this.delayResonsAreaName2 = delayResonsAreaName2;
    }

    public String getAlterNateAreaCode2() {
        return alterNateAreaCode2;
    }

    public void setAlterNateAreaCode2(String alterNateAreaCode2) {
        this.alterNateAreaCode2 = alterNateAreaCode2;
    }

    public String getAlterNateAreaName2() {
        return alterNateAreaName2;
    }

    public void setAlterNateAreaName2(String alterNateAreaName2) {
        this.alterNateAreaName2 = alterNateAreaName2;
    }

    public String getCancelFlightResons2() {
        return cancelFlightResons2;
    }

    public void setCancelFlightResons2(String cancelFlightResons2) {
        this.cancelFlightResons2 = cancelFlightResons2;
    }

    public Integer getIsExtraFlight2() {
        return isExtraFlight2;
    }

    public void setIsExtraFlight2(Integer isExtraFlight2) {
        this.isExtraFlight2 = isExtraFlight2;
    }

    public Integer getExtraFlightTimePend2() {
        return extraFlightTimePend2;
    }

    public void setExtraFlightTimePend2(Integer extraFlightTimePend2) {
        this.extraFlightTimePend2 = extraFlightTimePend2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getUrgeBoardTime2() {
        return urgeBoardTime2;
    }

    public void setUrgeBoardTime2(Date urgeBoardTime2) {
        this.urgeBoardTime2 = urgeBoardTime2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getPlanBoardingStarttime2() {
        return planBoardingStarttime2;
    }

    public void setPlanBoardingStarttime2(Date planBoardingStarttime2) {
        this.planBoardingStarttime2 = planBoardingStarttime2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getPlanBoardingEndtime2() {
        return planBoardingEndtime2;
    }

    public void setPlanBoardingEndtime2(Date planBoardingEndtime2) {
        this.planBoardingEndtime2 = planBoardingEndtime2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getGuestTime2() {
        return guestTime2;
    }

    public void setGuestTime2(Date guestTime2) {
        this.guestTime2 = guestTime2;
    }

    public String getTerminalId2() {
        return terminalId2;
    }

    public void setTerminalId2(String terminalId2) {
        this.terminalId2 = terminalId2;
    }

    public String getTerminal2() {
        return terminal2;
    }

    public void setTerminal2(String terminal2) {
        this.terminal2 = terminal2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getTurnBackTime2() {
        return turnBackTime2;
    }

    public void setTurnBackTime2(Date turnBackTime2) {
        this.turnBackTime2 = turnBackTime2;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAircraftNum() {
        return aircraftNum;
    }

    public void setAircraftNum(String aircraftNum) {
        this.aircraftNum = aircraftNum;
    }

    public String getAircraftTypeCode() {
        return aircraftTypeCode;
    }

    public void setAircraftTypeCode(String aircraftTypeCode) {
        this.aircraftTypeCode = aircraftTypeCode;
    }

    public String getAircraftTypeName() {
        return aircraftTypeName;
    }

    public void setAircraftTypeName(String aircraftTypeName) {
        this.aircraftTypeName = aircraftTypeName;
    }

    public String getSafecuardTypeCode() {
        return safecuardTypeCode;
    }

    public void setSafecuardTypeCode(String safecuardTypeCode) {
        this.safecuardTypeCode = safecuardTypeCode;
    }

    public String getSafecuardTypeName() {
        return safecuardTypeName;
    }

    public void setSafecuardTypeName(String safecuardTypeName) {
        this.safecuardTypeName = safecuardTypeName;
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

    public void setExt10(String ext10) {
        this.ext10 = ext10;
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

    public String getDelayStatus2() {
        return delayStatus2;
    }

    public void setDelayStatus2(String delayStatus2) {
        this.delayStatus2 = delayStatus2;
    }

    public String getCancelFlightStatus2() {
        return cancelFlightStatus2;
    }

    public void setCancelFlightStatus2(String cancelFlightStatus2) {
        this.cancelFlightStatus2 = cancelFlightStatus2;
    }

    public Date getInteBoardingActualStartTime() {
        return inteBoardingActualStartTime;
    }

    public void setInteBoardingActualStartTime(Date inteBoardingActualStartTime) {
        this.inteBoardingActualStartTime = inteBoardingActualStartTime;
    }

    public Date getInteBoardingActualEndTime() {
        return inteBoardingActualEndTime;
    }

    public void setInteBoardingActualEndTime(Date inteBoardingActualEndTime) {
        this.inteBoardingActualEndTime = inteBoardingActualEndTime;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }
}