package cn.net.metadata.rule.action.dist.carousel;

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
import com.thinkgem.jeesite.modules.rms.entity.CarouselOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistInfo;
import com.thinkgem.jeesite.modules.rms.service.CarouselOccupyingInfoService;
import com.thinkgem.jeesite.modules.rms.service.ResourceSharingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 行李转盘占用检测
 * Created by xiaowu on 3/28/16.
 */
@Component
public class CarouselOccupyingDetectingAction implements FlowAction {
    static final Logger logger = LoggerFactory.getLogger(CarouselOccupyingDetectingAction.class);
    @Autowired
    FlightDynamicService flightDynamicService;
    @Autowired
    CarouselOccupyingInfoService carouselOccupyingInfoService;
    @Autowired
    ResourceSharingService resourceSharingService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {
        flowContext.getWorkingMemory().getAllFacts().stream().filter(obj -> obj instanceof CarouselOccupyingInfo).findFirst().ifPresent(obj -> {
            CarouselOccupyingInfo info = (CarouselOccupyingInfo) obj;
            if (StringUtils.isNotEmpty(info.getInteCarouselCode()) || StringUtils.isNotEmpty(info.getIntlCarouselCode())) {
                logger.info(" *** 开始行李转盘占用检测 *** ");
                logger.info("航班属性：{}, 国内：{}， 国际：{}", info.getFlightDynamicNature(), info.getInteCarouselCode(), info.getIntlCarouselCode());
                Object flightDynamicObj = flowContext.getWorkingMemory().getAllFacts().stream().filter(dynamic -> dynamic instanceof FlightDynamic).findFirst().get();
                FlightDynamic flightDynamic;

                if (flightDynamicObj != null) {
                    flightDynamic = (FlightDynamic) flightDynamicObj;

                    Date startTime = info.getExpectedStartTime();

//                    if (startTime == null) {
//                        logger.error("未通过行李转盘占用检测：无法得到航班的到达时间");
//                        flowContext.addVariable("success", false);
//                        flowContext.addVariable("message", "未通过行李转盘占用检测：无法得到航班的到达时间");
//                        info.setInteCarouselCode("");
//                        info.setIntlCarouselCode("");
//                    } else {
//
//                        // 模拟分配及忽略占用（此处变量用于模拟分配的功能实现，与自动分配无关）
//                        boolean mock_dist = false, omit_oci = false;
//
//                        if (null != flowContext.getVariable("mock_dist") && StringUtils.isNotBlank(String.valueOf(flowContext.getVariable("mock_dist"))))
//                            mock_dist = (boolean) flowContext.getVariable("mock_dist");
//                        if (null != flowContext.getVariable("omit_oci") && StringUtils.isNotBlank(String.valueOf(flowContext.getVariable("omit_oci"))))
//                            omit_oci = (boolean) flowContext.getVariable("omit_oci");
//
//                        Integer thresholdValue;
//                        try {
//                            thresholdValue = Integer.parseInt(String.valueOf(flowContext.getVariable("threshold_value")));
//                        } catch (Exception e) {
//                            thresholdValue = 15;
//                        }
//
//                        Date overTime = info.getExpectedOverTime();
//
//                        //构造条件
//                        Map<String, Object> criteriaMap = new HashMap<>();
//                        criteriaMap.put("start", "'" + DateUtils.formatDateTime(DateUtils.addMinutes(startTime, -thresholdValue)) + "'");
//                        criteriaMap.put("over", "'" + DateUtils.formatDateTime(DateUtils.addMinutes(overTime, thresholdValue)) + "'");
//                        criteriaMap.put("flightDynamic", flightDynamic.getId());
//
//                        List<String> occupiedCarousels, availableInteCodeList = Lists.newArrayList(), availableIntlCodeList = Lists.newArrayList();
//
//                        if (!mock_dist) occupiedCarousels = flightDynamicService.findOccupiedCarouselCodes(criteriaMap);
//                        else {
//                            ResourceMockDistInfo resourceMockDistInfo = (ResourceMockDistInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(distInfo -> distInfo instanceof ResourceMockDistInfo).findFirst().get();
//                            criteriaMap.put("mockInfoId", resourceMockDistInfo.getId());
//                            criteriaMap.put("omitOci", omit_oci);
//                            // 根据startMockDate与overMockDate计算出planDate
//                            String startMockDate = DateUtils.formatDate(resourceMockDistInfo.getMockStartDate()), overMockDate = DateUtils.formatDate(resourceMockDistInfo.getMockOverDate());
//                            if (startMockDate.equals(overMockDate)) criteriaMap.put("samePlanDate", true);
//
//                            criteriaMap.put("startMockPlanDate", resourceMockDistInfo.getMockStartDate());
//                            criteriaMap.put("overMockPlanDate", resourceMockDistInfo.getMockOverDate());
//
//                            occupiedCarousels = flightDynamicService.findOccupiedCarouselCodes4Mock(criteriaMap);
//                        }
//
//                    // 可用的国内航班行李转盘
//                        if (StringUtils.isNotEmpty(info.getInteCarouselCode()))
//                            availableInteCodeList = Splitter.on(",").splitToList(info.getInteCarouselCode()).stream().filter(str -> !occupiedCarousels.contains(str)).collect(Collectors.toList());
//                        // 可用的国际航班行李转盘
//                        if (StringUtils.isNotEmpty(info.getIntlCarouselCode()))
//                            availableIntlCodeList = Splitter.on(",").splitToList(info.getIntlCarouselCode()).stream().filter(str -> !occupiedCarousels.contains(str)).collect(Collectors.toList());
//
//                        Calendar arrivalDate = Calendar.getInstance();
//                        arrivalDate.setTime(startTime);
//                        Calendar startCalendar = Calendar.getInstance();
//                        startCalendar.set(arrivalDate.get(Calendar.YEAR), arrivalDate.get(Calendar.MONTH), arrivalDate.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
//                        Date usageStartDate = startCalendar.getTime();
//                        startCalendar.add(Calendar.DAY_OF_MONTH, 1);
//                        Date usageOverDate = startCalendar.getTime();
//
//                        Map<String, Object> usageVariable = new HashMap<>();
//                        usageVariable.put("start", usageStartDate);
//                        usageVariable.put("over", usageOverDate);

                    List<String> availableInteCodeList = Lists.newArrayList(), availableIntlCodeList = Lists.newArrayList();
                    if (StringUtils.isNotEmpty(info.getInteCarouselCode()))
                        availableInteCodeList = Splitter.on(",").splitToList(info.getInteCarouselCode());
                    if (StringUtils.isNotEmpty(info.getIntlCarouselCode()))
                        availableIntlCodeList = Splitter.on(",").splitToList(info.getIntlCarouselCode());

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
                    usageVariable.put("ociStart", info.getExpectedStartTime());
                    usageVariable.put("ociOver", info.getExpectedOverTime());
                    usageVariable.put("expectedNum", info.getExpectedCarouselNum());

                    // 模拟分配及忽略占用（此处变量用于模拟分配的功能实现，与自动分配无关）
                    boolean mock_dist = false, omit_oci = false;

                    if (null != flowContext.getVariable("mock_dist") && StringUtils.isNotBlank(String.valueOf(flowContext.getVariable("mock_dist"))))
                        mock_dist = (boolean) flowContext.getVariable("mock_dist");
                    if (null != flowContext.getVariable("omit_oci") && StringUtils.isNotBlank(String.valueOf(flowContext.getVariable("omit_oci"))))
                        omit_oci = (boolean) flowContext.getVariable("omit_oci");

                    usageVariable.put("mock_dist", mock_dist);
                    usageVariable.put("omit_oci", omit_oci);

                    if (mock_dist) {
                        ResourceMockDistInfo resourceMockDistInfo = (ResourceMockDistInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(distInfo -> distInfo instanceof ResourceMockDistInfo).findFirst().get();
                        usageVariable.put("mockInfoId", resourceMockDistInfo.getId());
                        usageVariable.put("start", resourceMockDistInfo.getMockStartDate());
                        usageVariable.put("over", resourceMockDistInfo.getMockOverDate());
                    }


                    if (availableInteCodeList.isEmpty() && availableIntlCodeList.isEmpty()) {
                        logger.error("未通过行李转盘占用检测：无空闲的行李转盘");
                        flowContext.addVariable("success", false);
                        flowContext.addVariable("message", "未通过行李转盘占用检测：无空闲的行李转盘");
                        info.setInteCarouselCode("");
                        info.setIntlCarouselCode("");
                    } else {
                        if (StringUtils.equals(info.getFlightDynamicNature(), "1")) { //国内进港
                            if (availableInteCodeList.isEmpty()) {
                                logger.error("未通过行李转盘占用检测：当前无可用的国内航班行李转盘");
                                flowContext.addVariable("success", false);
                                flowContext.addVariable("message", "未通过行李转盘占用检测：无可用的国内航班行李转盘");
                                info.setInteCarouselCode("");
                                info.setIntlCarouselCode("");
                            } else {
                                // 设置， 如果多个值，则根据平均使用率进行分配
                                info.setInteCarouselCode(findCarouselByAverageUsing(availableInteCodeList, usageVariable));
                                info.setActualCarouselNum(1L);
                            }
                        } else if (StringUtils.equals(info.getFlightDynamicNature(), "2")) { // 国际进港
                            if (availableIntlCodeList.isEmpty()) {
                                logger.error("未通过行李转盘占用检测：当前无可用的国际航班行李转盘");
                                flowContext.addVariable("success", false);
                                flowContext.addVariable("message", "未通过行李转盘占用检测：当前无可用的国际航班行李转盘");
                                info.setInteCarouselCode("");
                                info.setIntlCarouselCode("");
                            } else {
                                info.setIntlCarouselCode(findCarouselByAverageUsing(availableIntlCodeList, usageVariable));
                                info.setActualCarouselNum(1L);
                            }
                        } else if (StringUtils.equals(info.getFlightDynamicNature(), "3")) { // 混合航班
                            if (availableInteCodeList.isEmpty() || availableIntlCodeList.isEmpty()) {
                                logger.error("未通过行李转盘占用检测：当前无可用的行李转盘");
                                flowContext.addVariable("success", false);
                                flowContext.addVariable("message", "未通过行李转盘占用检测：当前无可用的行李转盘");
                                info.setInteCarouselCode("");
                                info.setIntlCarouselCode("");
                            } else {
                                info.setInteCarouselCode(findCarouselByAverageUsing(availableInteCodeList, usageVariable));
                                info.setIntlCarouselCode(findCarouselByAverageUsing(availableIntlCodeList, usageVariable));
                                info.setActualCarouselNum(2L);
                            }
                        } else { // 拋出异常，无法认识的航班属性
                            logger.error("未通过行李转盘占用检测：无法识别的航班属性 {}", info.getFlightDynamicNature());
                            flowContext.addVariable("success", false);
                            flowContext.addVariable("message", "未通过行李转盘占用检测：无法识别的航班属性 => " + info.getFlightDynamicNature());
                            info.setInteCarouselCode("");
                            info.setIntlCarouselCode("");
                        }

//                            carouselOccupyingInfoService.save(infoInDB);
                    }
                }
            } else {
                logger.error("未通过行李转盘占用检测：无法获取航班动态对象");
                flowContext.addVariable("success", false);
                flowContext.addVariable("message", "未通过行李转盘占用检测：无法获取航班动态对象");
            }
            logger.info(" *** 行李转盘占用检测完毕 *** ");
//            }
        });
    }

    /**
     * 根据平均使用率进行分配
     *
     * @return
     */
//    private String findCarouselByAverageUsing(List<String> carousels, Map<String, Object> usageVariable) {
//        if (carousels.size() == 1) return carousels.get(0);
//        usageVariable.put("numbers", Joiner.on(",").join(carousels));
//        List<Map<String, String>> results = carouselOccupyingInfoService.findCarouselUsage(usageVariable);
//        return null == results || results.isEmpty() ? carousels.get(0) : results.get(0).get("CAROUSEL_CODE");
//    }
    private String findCarouselByAverageUsing(List<String> arrivalGates, Map<String, Object> usageVariable) {
        usageVariable.put("numbers", Joiner.on(",").join(arrivalGates));
        return Joiner.on(",").join(carouselOccupyingInfoService.findCompatibleCodes(usageVariable));
    }
}
