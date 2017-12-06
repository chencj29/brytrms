package cn.net.metadata.rule.action.dist.slideCoast;

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
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistInfo;
import com.thinkgem.jeesite.modules.rms.entity.SlideCoastOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.ResourceSharingService;
import com.thinkgem.jeesite.modules.rms.service.SlideCoastOccupyingInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 滑槽占用检测
 * Created by xiaowu on 3/28/16.
 */
@Component
public class SlideCoastOccupyingDetectingAction implements FlowAction {
    static final Logger logger = LoggerFactory.getLogger(SlideCoastOccupyingDetectingAction.class);
    @Autowired
    FlightDynamicService flightDynamicService;
    @Autowired
    SlideCoastOccupyingInfoService slideCoastOccupyingInfoService;
    @Autowired
    ResourceSharingService resourceSharingService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {
        flowContext.getWorkingMemory().getAllFacts().stream().filter(obj -> obj instanceof SlideCoastOccupyingInfo).findFirst().ifPresent(obj -> {
            SlideCoastOccupyingInfo info = (SlideCoastOccupyingInfo) obj;
            if (StringUtils.isNotEmpty(info.getInteSlideCoastCode()) || StringUtils.isNotEmpty(info.getIntlSlideCoastCode())) {
                logger.info(" *** 开始滑槽占用检测 *** ");
                logger.info("航班属性：{}, 国内：{}， 国际：{}", info.getFlightDynamicNature(), info.getInteSlideCoastCode(), info.getIntlSlideCoastCode());
                Object flightDynamicObj = flowContext.getWorkingMemory().getAllFacts().stream().filter(dynamic -> dynamic instanceof FlightDynamic).findFirst().get();
                FlightDynamic flightDynamic = null;

                if (flightDynamicObj != null) {
                    flightDynamic = (FlightDynamic) flightDynamicObj;

                    // 计算开始占用及结束占用时间
                    Date overTime = info.getExpectedOverTime();
                    if (overTime == null) {
                        logger.error("未通过滑槽占用检测：无法得到航班的起飞时间");
                        flowContext.addVariable("success", false);
                        flowContext.addVariable("message", "未通过滑槽占用检测：无法得到航班的起飞时间");
                        info.setInteSlideCoastCode("");
                        info.setIntlSlideCoastCode("");
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

                        List<String> occupiedSlideCoastCodes, availableInteCodeList = Lists.newArrayList(), availableIntlCodeList = Lists.newArrayList();

                        if (!mock_dist) occupiedSlideCoastCodes = flightDynamicService.findOccupiedSlideCoastCodes(criteriaMap);
                        else {
                            ResourceMockDistInfo resourceMockDistInfo = (ResourceMockDistInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(distInfo -> distInfo instanceof ResourceMockDistInfo).findFirst().get();
                            criteriaMap.put("mockInfoId", resourceMockDistInfo.getId());
                            criteriaMap.put("omitOci", omit_oci);
                            // 根据startMockDate与overMockDate计算出planDate
                            String startMockDate = DateUtils.formatDate(resourceMockDistInfo.getMockStartDate()), overMockDate = DateUtils.formatDate(resourceMockDistInfo.getMockOverDate());
                            if (startMockDate.equals(overMockDate)) criteriaMap.put("samePlanDate", true);

                            criteriaMap.put("startMockPlanDate", DateUtils.parseDate(startMockDate));
                            criteriaMap.put("overMockPlanDate", DateUtils.parseDate(overMockDate));

                            occupiedSlideCoastCodes = flightDynamicService.findOccupiedSlideCoastCodes4Mock(criteriaMap);
                        }

                        // 可用的国内航班滑槽
                        if (StringUtils.isNotEmpty(info.getInteSlideCoastCode()))
                            availableInteCodeList = Splitter.on(",").splitToList(info.getInteSlideCoastCode()).stream().filter(str -> !occupiedSlideCoastCodes.contains(str)).collect(Collectors.toList());
                        // 可用的国际航班滑槽
                        if (StringUtils.isNotEmpty(info.getIntlSlideCoastCode()))
                            availableIntlCodeList = Splitter.on(",").splitToList(info.getIntlSlideCoastCode()).stream().filter(str -> !occupiedSlideCoastCodes.contains(str)).collect(Collectors.toList());

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
                            logger.error("未通过滑槽占用检测：无空闲的滑槽");
                            flowContext.addVariable("success", false);
                            flowContext.addVariable("message", "未通过滑槽占用检测：无空闲的滑槽");
                            info.setInteSlideCoastCode("");
                            info.setIntlSlideCoastCode("");
                        } else {
                            if (StringUtils.equals(info.getFlightDynamicNature(), "1")) { //国内进港
                                if (availableInteCodeList.isEmpty()) {
                                    logger.error("未通过滑槽占用检测：当前无可用的国内航班滑槽");
                                    flowContext.addVariable("success", false);
                                    flowContext.addVariable("message", "未通过滑槽占用检测：当前无可用的国内航班滑槽");
                                    info.setInteSlideCoastCode("");
                                    info.setIntlSlideCoastCode("");
                                } else {
                                    // 设置， 如果多个值，则根据平均使用率进行分配
                                    info.setInteSlideCoastCode(findCarouselByAverageUsing(availableInteCodeList, usageVariable));
                                    info.setActualSlideCoastNum(1);
                                }
                            } else if (StringUtils.equals(info.getFlightDynamicNature(), "2")) { // 国际进港
                                if (availableIntlCodeList.isEmpty()) {
                                    logger.error("未通过滑槽占用检测：当前无可用的国际航班滑槽");
                                    flowContext.addVariable("success", false);
                                    flowContext.addVariable("message", "未通过滑槽占用检测：当前无可用的国际航班滑槽");
                                    info.setInteSlideCoastCode("");
                                    info.setIntlSlideCoastCode("");
                                } else {
                                    info.setIntlSlideCoastCode(findCarouselByAverageUsing(availableIntlCodeList, usageVariable));
                                    info.setActualSlideCoastNum(1);
                                }
                            } else if (StringUtils.equals(info.getFlightDynamicNature(), "3")) { // 混合航班
                                if (availableInteCodeList.isEmpty() || availableIntlCodeList.isEmpty()) {
                                    logger.error("未通过滑槽占用检测：当前无可用的滑槽");
                                    flowContext.addVariable("success", false);
                                    flowContext.addVariable("message", "未通过滑槽占用检测：当前无可用的滑槽");
                                    info.setInteSlideCoastCode("");
                                    info.setIntlSlideCoastCode("");
                                } else {
                                    info.setInteSlideCoastCode(findCarouselByAverageUsing(availableInteCodeList, usageVariable));
                                    info.setIntlSlideCoastCode(findCarouselByAverageUsing(availableIntlCodeList, usageVariable));
                                    info.setActualSlideCoastNum(2);
                                }
                            } else { // 拋出异常，无法认识的航班属性
                                logger.error("未通过滑槽占用检测：无法识别的航班属性 {}", info.getFlightDynamicNature());
                                flowContext.addVariable("success", false);
                                flowContext.addVariable("message", "未通过滑槽占用检测：无法识别的航班属性 => " + info.getFlightDynamicNature());
                                info.setInteSlideCoastCode("");
                                info.setIntlSlideCoastCode("");
                            }

//                            slideCoastOccupyingInfoService.save(info);
                        }
                    }
                } else {
                    logger.error("未通过滑槽占用检测：无法获取航班动态对象");
                    flowContext.addVariable("success", false);
                    flowContext.addVariable("message", "未通过滑槽占用检测：无法获取航班动态对象");
                }
                logger.info(" *** 滑槽占用检测完毕 *** ");
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
        List<Map<String, String>> results = slideCoastOccupyingInfoService.findSlideCoastUsage(usageVariable);
        return null == results || results.isEmpty() ? arrivalGates.get(0) : results.get(0).get("CAROUSEL_CODE");
    }
}
