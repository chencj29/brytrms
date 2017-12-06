package cn.net.metadata.rule.action.dist.boardingGate;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.entity.BoardingGateOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistInfo;
import com.thinkgem.jeesite.modules.rms.service.BoardingGateOccupyingInfoService;
import com.thinkgem.jeesite.modules.rms.service.ResourceSharingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 登机口占用检测
 * Created by xiaowu on 3/28/16.
 */
@Component
public class BoardingGateOccupyingDetectingAction implements FlowAction {
    static final Logger logger = LoggerFactory.getLogger(BoardingGateOccupyingDetectingAction.class);
    @Autowired
    FlightDynamicService flightDynamicService;
    @Autowired
    BoardingGateOccupyingInfoService boardingGateOccupyingInfoService;
    @Autowired
    ResourceSharingService resourceSharingService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {
        flowContext.getWorkingMemory().getAllFacts().stream().filter(obj -> obj instanceof BoardingGateOccupyingInfo).findFirst().ifPresent(obj -> {
            BoardingGateOccupyingInfo info = (BoardingGateOccupyingInfo) obj;
            if (StringUtils.isNotEmpty(info.getInteBoardingGateCode()) || StringUtils.isNotEmpty(info.getIntlBoardingGateCode())) {
                logger.info(" *** 开始登机口占用检测 *** ");
                logger.info("航班属性：{}, 国内：{}， 国际：{}", info.getFlightDynamicNature(), info.getInteBoardingGateCode(), info.getIntlBoardingGateCode());
                Object flightDynamicObj = flowContext.getWorkingMemory().getAllFacts().stream().filter(dynamic -> dynamic instanceof FlightDynamic).findFirst().get();
                FlightDynamic flightDynamic = null;

                if (flightDynamicObj != null) {
                    flightDynamic = (FlightDynamic) flightDynamicObj;

                    Date overTime = info.getExpectedOverTime();
                    if (overTime == null) {
                        logger.error("未通过登机口占用检测：无法得到航班的起飞时间");
                        flowContext.addVariable("success", false);
                        flowContext.addVariable("message", "未通过登机口占用检测：无法得到航班的起飞时间");
                        info.setInteBoardingGateCode("");
                        info.setIntlBoardingGateCode("");
                    } else {
                        // 模拟分配及忽略占用（此处变量用于模拟分配的功能实现，与自动分配无关）
                        boolean mock_dist = false, omit_oci = false;

                        if (null != flowContext.getVariable("mock_dist") && StringUtils.isNotBlank(String.valueOf(flowContext.getVariable("mock_dist"))))
                            mock_dist = (boolean) flowContext.getVariable("mock_dist");
                        if (null != flowContext.getVariable("omit_oci") && StringUtils.isNotBlank(String.valueOf(flowContext.getVariable("omit_oci"))))
                            omit_oci = (boolean) flowContext.getVariable("omit_oci");


                        Integer thresholdValue;
                        try {
                            thresholdValue = Integer.parseInt(String.valueOf(flowContext.getVariable("threshold_value")));
                        } catch (Exception e) {
                            thresholdValue = 15;
                        }

                        Date startTime = info.getExpectedStartTime();
                        // 构造条件
                        Map<String, Object> criteriaMap = new HashMap<>();
                        criteriaMap.put("start", "'" + DateUtils.formatDateTime(DateUtils.addMinutes(startTime, -thresholdValue)) + "'");
                        criteriaMap.put("over", "'" + DateUtils.formatDateTime(DateUtils.addMinutes(overTime, thresholdValue)) + "'");
                        criteriaMap.put("flightDynamic", flightDynamic.getId());


                        List<String> occupiedBoardingGates, availableInteCodeList = Lists.newArrayList(), availableIntlCodeList = Lists.newArrayList();

                        if (!mock_dist) occupiedBoardingGates = flightDynamicService.findOccupiedBoardingGateCodes(criteriaMap);
                        else {
                            ResourceMockDistInfo resourceMockDistInfo = (ResourceMockDistInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(distInfo -> distInfo instanceof ResourceMockDistInfo).findFirst().get();
                            criteriaMap.put("mockInfoId", resourceMockDistInfo.getId());
                            criteriaMap.put("omitOci", omit_oci);
                            // 根据startMockDate与overMockDate计算出planDate
                            String startMockDate = DateUtils.formatDate(resourceMockDistInfo.getMockStartDate()), overMockDate = DateUtils.formatDate(resourceMockDistInfo.getMockOverDate());
                            if (startMockDate.equals(overMockDate)) criteriaMap.put("samePlanDate", true);

                            criteriaMap.put("startMockPlanDate", DateUtils.parseDate(startMockDate));
                            criteriaMap.put("overMockPlanDate", DateUtils.parseDate(overMockDate));

                            occupiedBoardingGates = flightDynamicService.findOccupiedBoardingGateCodes4Mock(criteriaMap);
                        }

                        // 可用的国内航班登机口
                        if (StringUtils.isNotEmpty(info.getInteBoardingGateCode()))
                            availableInteCodeList = Splitter.on(",").splitToList(info.getInteBoardingGateCode()).stream().filter(str -> !occupiedBoardingGates.contains(str)).collect(Collectors.toList());
                        // 可用的国际航班登机口
                        if (StringUtils.isNotEmpty(info.getIntlBoardingGateCode()))
                            availableIntlCodeList = Splitter.on(",").splitToList(info.getIntlBoardingGateCode()).stream().filter(str -> !occupiedBoardingGates.contains(str)).collect(Collectors.toList());

                        Calendar arrivalDate = Calendar.getInstance();
                        arrivalDate.setTime(startTime);
                        Calendar startCalendar = Calendar.getInstance();
                        startCalendar.set(arrivalDate.get(Calendar.YEAR), arrivalDate.get(Calendar.MONTH), arrivalDate.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
                        Date usageStartDate = startCalendar.getTime();
                        startCalendar.add(Calendar.DAY_OF_MONTH, 1);
                        Date usageOverDate = startCalendar.getTime();

                        Map<String, Object> usageVariable = new HashMap<>();
                        usageVariable.put("start", usageStartDate);
                        usageVariable.put("over", usageOverDate);


                        if (availableInteCodeList.isEmpty() && availableIntlCodeList.isEmpty()) {
                            logger.error("未通过登机口占用检测：无空闲的登机口");
                            flowContext.addVariable("success", false);
                            flowContext.addVariable("message", "未通过登机口占用检测：无空闲的登机口");
                            info.setInteBoardingGateCode("");
                            info.setIntlBoardingGateCode("");
                        } else {
                            if (StringUtils.equals(info.getFlightDynamicNature(), "1")) { //国内进港
                                if (availableInteCodeList.isEmpty()) {
                                    logger.error("未通过登机口占用检测：当前无可用的国内航班登机口");
                                    flowContext.addVariable("success", false);
                                    flowContext.addVariable("message", "未通过登机口占用检测：当前无可用的国内航班登机口");
                                    info.setInteBoardingGateCode("");
                                    info.setIntlBoardingGateCode("");
                                } else {
                                    // 设置， 如果多个值，则根据平均使用率进行分配
                                    info.setInteBoardingGateCode(findCarouselByAverageUsing(availableInteCodeList, usageVariable));
                                    info.setActualBoardingGateNum(1);
                                }
                            } else if (StringUtils.equals(info.getFlightDynamicNature(), "2")) { // 国际进港
                                if (availableIntlCodeList.isEmpty()) {
                                    logger.error("未通过登机口占用检测：当前无可用的国际航班登机口");
                                    flowContext.addVariable("success", false);
                                    flowContext.addVariable("message", "未通过登机口占用检测：当前无可用的国际航班登机口");
                                    info.setInteBoardingGateCode("");
                                    info.setIntlBoardingGateCode("");
                                } else {
                                    info.setIntlBoardingGateCode(findCarouselByAverageUsing(availableIntlCodeList, usageVariable));
                                    info.setActualBoardingGateNum(1);
                                }
                            } else if (StringUtils.equals(info.getFlightDynamicNature(), "3")) { // 混合航班
//                                if (availableInteCodeList.isEmpty() && availableIntlCodeList.isEmpty()) {
//                                    logger.error("未通过登机口占用检测：当前无可用的登机口");
//                                    flowContext.addVariable("success", false);
//                                    flowContext.addVariable("message", "未通过登机口占用检测：当前无可用的登机口");
//                                    info.setInteBoardingGateCode("");
//                                    info.setIntlBoardingGateCode("");
//                                } else {
                                info.setInteBoardingGateCode(findCarouselByAverageUsing(availableInteCodeList, usageVariable));
                                info.setIntlBoardingGateCode(findCarouselByAverageUsing(availableIntlCodeList, usageVariable));
                                info.setActualBoardingGateNum(2);
//                                }
                            } else { // 拋出异常，无法认识的航班属性
                                logger.error("未通过登机口占用检测：无法识别的航班属性 {}", info.getFlightDynamicNature());
                                flowContext.addVariable("success", false);
                                flowContext.addVariable("message", "未通过登机口占用检测：无法识别的航班属性 => " + info.getFlightDynamicNature());
                                info.setInteBoardingGateCode("");
                                info.setIntlBoardingGateCode("");
                            }

//                            boardingGateOccupyingInfoService.save(info);
                        }
                    }
                } else {
                    logger.error("未通过登机口占用检测：无法获取航班动态对象");
                    flowContext.addVariable("success", false);
                    flowContext.addVariable("message", "未通过登机口占用检测：无法获取航班动态对象");

                }
                logger.info(" *** 登机口占用检测完毕 *** ");
            }
        });
    }

    /**
     * 根据平均使用率进行分配
     *
     * @return
     */
    private String findCarouselByAverageUsing(List<String> arrivalGates, Map<String, Object> usageVariable) {
        if (arrivalGates.size() == 1) return arrivalGates.get(0);
        usageVariable.put("numbers", Joiner.on(",").join(arrivalGates));
        List<Map<String, String>> results = boardingGateOccupyingInfoService.findArrivalGateUsage(usageVariable);
        return null == results || results.isEmpty() ? arrivalGates.get(0) : results.get(0).get("CAROUSEL_CODE");
    }
}
