package cn.net.metadata.rule.action.dist.gate;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.dao.YesterAircraftNumPairDao;
import com.thinkgem.jeesite.modules.ams.entity.YesterAircraftNumPair;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.ams.service.SysDictService;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;
import com.thinkgem.jeesite.modules.rms.entity.GateOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistInfo;
import com.thinkgem.jeesite.modules.rms.service.*;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 机位状态、是否被占用检测
 */
@Component
public class GateOccupyingDetectingAction implements FlowAction {

    static final Logger logger = LoggerFactory.getLogger(GateOccupyingDetectingAction.class);
    @Autowired
    FlightDynamicService flightDynamicService;
    @Autowired
    AircraftStandService aircraftStandService;
    @Autowired
    GateOccupyingInfoService gateOccupyingInfoService;
    @Autowired
    SysDictService sysDictService;
    @Autowired
    FlightPlanPairService flightPlanPairService;
    @Autowired
    ResourceSharingService resourceSharingService;

    @Autowired
    ResourceMockDistDetailService mockDistDetailService;

    @Autowired
    YesterAircraftNumPairDao yesterAircraftNumPairDao;

    Boolean placeNumFlag = Boolean.parseBoolean(DictUtils.getDictValueConstant("urule_yester_place_num", "flag"));

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {

        flowContext.getWorkingMemory().getAllFacts().stream().filter(obj -> obj instanceof FlightPlanPair).findFirst().ifPresent(obj -> {
            FlightPlanPair flightPlanPair = (FlightPlanPair) obj;
            GateOccupyingInfo gateOccupyingInfo = gateOccupyingInfoService.getByFlightDynamicId(flightPlanPair.getId());
            GateOccupyingInfo flowGateOccupyingInfo = (GateOccupyingInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof GateOccupyingInfo).findFirst().get();
            if (gateOccupyingInfo == null)
                gateOccupyingInfo = resourceSharingService.saveGateoccupiedEntity(flightPlanPair);
            gateOccupyingInfo.setRandom(flightPlanPair.getRandom());
            gateOccupyingInfo.setExpectedGateNum(flowGateOccupyingInfo.getExpectedGateNum());

            if (StringUtils.isNotEmpty(flightPlanPair.getPlaceNum())) {
                logger.info(" *** 开始机位是否被占用检测 *** ");

                //
                // 判断机位是否被占用，总体思路：
                //      1) 计算出当前航班需要占用的时长，
                //          a. 单进航班的起始时间为（实达 - 预达 - 计达），终止时间为第二天的凌晨6点（自由配置）
                //          b. 单出航班的起始时间为当前时间， 终止时间为（实飞 - 预飞 - 计飞）
                //          c. 联班航班的起始时间为（实达 - 预达 - 计达），终止时间为（实飞 - 预飞 - 计飞）
                //      2) 根据机位号查询出当前动态列表，查询条件为：
                //          a. 机位号
                //          b. 单进、联班航班的落地时间在当前时间之前（计达 - 预达 - 实达）
                //          c. 单出航班的起飞时间在当前时间之后（计飞 - 实飞 - 预飞）
                //      3) 遍历动态列表，计算当前机位是否已经被占用，如果没有合适的机位列表，将当前机位存入至hold区
                //

                if (gateOccupyingInfo.getStartTime() == null || gateOccupyingInfo.getOverTime() == null) {
                    logger.info("无法根据航班的时间计算出机位占用开始及结束时间，舍弃！");
                    flightPlanPair.setPlaceNum("");
                    flowContext.addVariable("success", false);
                    flowContext.addVariable("message", "未通过机位占用检测：无法根据航班的时间计算出机位占用开始及结束时间");
                } else if (gateOccupyingInfo.getStartTime().after(gateOccupyingInfo.getOverTime())) {
                    String reason = String.format("未通过机位占用检测：经过计算，当前航班占用开始时间在结束时间之后，不符合规则。<BR/>开始占用时间：%s<BR>结束占用时间：%s", DateUtils.formatDateTime(gateOccupyingInfo.getStartTime()), DateUtils.formatDateTime(gateOccupyingInfo.getOverTime()));
                    logger.info(reason);
                    flightPlanPair.setPlaceNum("");
                    flowContext.addVariable("success", false);
                    flowContext.addVariable("message", reason);
                } else {
                    // 模拟分配及忽略占用（此处变量用于模拟分配的功能实现，与自动分配无关）
                    boolean mock_dist = false, omit_oci = false;

                    if (null != flowContext.getVariable("mock_dist") && StringUtils.isNotBlank(String.valueOf(flowContext.getVariable("mock_dist"))))
                        mock_dist = (boolean) flowContext.getVariable("mock_dist");
                    if (null != flowContext.getVariable("omit_oci") && StringUtils.isNotBlank(String.valueOf(flowContext.getVariable("omit_oci"))))
                        omit_oci = (boolean) flowContext.getVariable("omit_oci");

                    List<String> filteredGates = Splitter.on(",").splitToList(flightPlanPair.getPlaceNum());

                    Map<String, Object> occupiedVarirable = new HashMap<>();
                    Integer thresholdValue = 15;
                    Object thresholdValueObj = flowContext.getVariable("threshold_value");
                    Date _startDate = gateOccupyingInfo.getStartTime(), _overDate = gateOccupyingInfo.getOverTime();
                    if (thresholdValueObj != null) {
                        try {
                            if (StringUtils.isNotEmpty(String.valueOf(thresholdValueObj)))
                                thresholdValue = Integer.parseInt(String.valueOf(thresholdValueObj));
                        } catch (Exception e) {
                            logger.error("应用「资源分隔间隔值」时失败：" + e.getMessage());
                        }
                    }
                    occupiedVarirable.put("start", "'" + DateUtils.formatDateTime(DateUtils.addMinutes(_startDate, -thresholdValue)) + "'");
                    occupiedVarirable.put("over", "'" + DateUtils.formatDateTime(DateUtils.addMinutes(_overDate, thresholdValue)) + "'");
                    occupiedVarirable.put("flightDynamic", flightPlanPair.getId());

                    logger.info("开始时间：{}，结束时间：{}", DateUtils.formatDateTime(_startDate), DateUtils.formatDateTime(_overDate));
                    //可以使用的机位列表

                    List<String> occupiedGateList;

                    //根据当前航班计算出已经被占用的机位列表(根据当前航班的「开始占用」及「结束占用」时间匹配可用机位)

                    if (!mock_dist) occupiedGateList = flightDynamicService.findOccupiedGates(occupiedVarirable);
                    else {
                        ResourceMockDistInfo resourceMockDistInfo = (ResourceMockDistInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof ResourceMockDistInfo).findFirst().get();
                        occupiedVarirable.put("mockInfoId", resourceMockDistInfo.getId());
                        occupiedVarirable.put("omitOci", omit_oci);
                        // 根据startMockDate与overMockDate计算出planDate
                        String startMockDate = DateUtils.formatDate(resourceMockDistInfo.getMockStartDate()), overMockDate = DateUtils.formatDate(resourceMockDistInfo.getMockOverDate());
                        if (startMockDate.equals(overMockDate)) occupiedVarirable.put("samePlanDate", true);

                        occupiedVarirable.put("startMockPlanDate", DateUtils.parseDate(startMockDate));
                        occupiedVarirable.put("overMockPlanDate", DateUtils.parseDate(overMockDate));

                        occupiedGateList = flightDynamicService.findoccupiedGates4Mock(occupiedVarirable);
                    }


                    filteredGates = filteredGates.stream().filter(str -> !occupiedGateList.contains(str)).collect(Collectors.toList());

                    logger.info("根据机位开始及结束使用时间计算已经被占用的机位号并过滤");
                    //处理次日航班机位占用问题 wjp_2017年11月22日16时44分
                    if (placeNumFlag) {
                        if (!Collections3.isEmpty(filteredGates) && StringUtils.isNotBlank(flightPlanPair.getAircraftNum())) {
                            String ypNum = flightPlanPair.getPlaceNum();
                            List<YesterAircraftNumPair> numPair = yesterAircraftNumPairDao.findListByOci(flightPlanPair.getPlaceNum());
                            if (!Collections3.isEmpty(numPair)) {
                                filteredGates.remove(ypNum);
                                logger.info("过滤次日航班已经被占用的机号："+numPair.get(0).getAircraftNum());
                                if(Collections3.isEmpty(filteredGates)){
                                    logger.info("过滤次日占用机位后无符合条件的机位，舍弃！");
                                    flowContext.addVariable("success", false);
                                    flowContext.addVariable("message", "警告：当前机位["+ypNum+"]已经被【机号"+numPair.get(0).getAircraftNum()+"停场飞机】占用！");
                                    flightPlanPair.setPlaceNum("");
                                    return;
                                }
                            }
                        }
                    }

                    if (null == filteredGates || filteredGates.isEmpty()) {
                        logger.info("过滤后无符合条件的机位，舍弃！");
                        flowContext.addVariable("success", false);
                        flowContext.addVariable("message", "未通过机位占用检测：当前机位已经被占用！");
                        flightPlanPair.setPlaceNum("");
                    } else {
                        // 如果存在多个可分配的机位列表，根据平均使用的原则进行合理分配
                        // 得到要分配机位的开始日期，拼装当天的日期
                        Calendar distDate = Calendar.getInstance();
                        distDate.setTime(gateOccupyingInfo.getStartTime());
                        Calendar startCalendar = Calendar.getInstance();
                        startCalendar.set(distDate.get(Calendar.YEAR), distDate.get(Calendar.MONTH), distDate.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
                        Date startDate = startCalendar.getTime();
                        startCalendar.add(Calendar.DAY_OF_MONTH, 1);
                        Date overDate = startCalendar.getTime();

                        logger.info("发现多个可用机位{}，正在根据平均使用率计算出最适合的机位号", Joiner.on(",").join(filteredGates));

                        //组装查询条件
                        Map<String, Object> params = ImmutableMap.of("numbers", "'" + Joiner.on(",").join(filteredGates) + "'", "start", startDate, "over", overDate);
                        List<Map<String, String>> result = aircraftStandService.findStandUsage(params);

                        if (result.isEmpty()) flightPlanPair.setPlaceNum(filteredGates.get(0));
                        else flightPlanPair.setPlaceNum(result.get(0).get("ACTUAL_GATE_NUM"));

                        logger.info("计算成功，机位号为{}", flightPlanPair.getPlaceNum());
                        flowContext.addVariable("success", true);
                        gateOccupyingInfo.setActualGateNum(flightPlanPair.getPlaceNum());
                        gateOccupyingInfo.setFlightDynamicCode(flightPlanPair.getVirtualTipField());
                        gateOccupyingInfo.setLeave("0");
                    }
                }
            }
        });
    }
}
