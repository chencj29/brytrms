package cn.net.metadata.rule.action.dist.checkingCounter;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.entity.CheckingCounterOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistInfo;
import com.thinkgem.jeesite.modules.rms.service.CheckingCounterOccupyingInfoService;
import com.thinkgem.jeesite.modules.rms.service.ResourceSharingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 值机柜台占用检测
 * Created by xiaowu on 3/28/16.
 */
@Component
public class CheckingCounterOccupyingDetectingAction implements FlowAction {
    static final Logger logger = LoggerFactory.getLogger(CheckingCounterOccupyingDetectingAction.class);
    @Autowired
    FlightDynamicService flightDynamicService;
    @Autowired
    CheckingCounterOccupyingInfoService checkingCounterOccupyingInfoService;
    @Autowired
    ResourceSharingService resourceSharingService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {
        flowContext.getWorkingMemory().getAllFacts().stream().filter(obj -> obj instanceof CheckingCounterOccupyingInfo).findFirst().ifPresent(obj -> {
            CheckingCounterOccupyingInfo info = (CheckingCounterOccupyingInfo) obj;
            if (StringUtils.isNotEmpty(info.getInteCheckingCounterCode()) || StringUtils.isNotEmpty(info.getIntlCheckingCounterCode())) {
                logger.info(" *** 开始值机柜台占用检测 *** ");
                logger.info("航班属性：{}, 国内：{}， 国际：{}", info.getFlightDynamicNature(), info.getInteCheckingCounterCode(), info.getIntlCheckingCounterCode());
                Object flightDynamicObj = flowContext.getWorkingMemory().getAllFacts().stream().filter(dynamic -> dynamic instanceof FlightDynamic).findFirst().get();
                FlightDynamic flightDynamic;

                if (flightDynamicObj != null) {
                    flightDynamic = (FlightDynamic) flightDynamicObj;

                    // 计算预计开始及结束时间
                    Date startTime = info.getExpectedStartTime();
                    List<String> availableInteCodeList = Lists.newArrayList(), availableIntlCodeList = Lists.newArrayList();
                    if (StringUtils.isNotEmpty(info.getInteCheckingCounterCode())) availableInteCodeList = Splitter.on(",").splitToList(info.getInteCheckingCounterCode());
                    if (StringUtils.isNotEmpty(info.getIntlCheckingCounterCode())) availableIntlCodeList = Splitter.on(",").splitToList(info.getIntlCheckingCounterCode());

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
                    usageVariable.put("expectedNum", info.getExpectedCheckingCounterNum());

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

                    //屏蔽值机柜台占用检测
                    if (availableInteCodeList.isEmpty() && availableIntlCodeList.isEmpty()) {
                        logger.error("未通过值机柜台占用检测：无空闲的值机柜台");
                        flowContext.addVariable("success", false);
                        flowContext.addVariable("message", "未通过值机柜台占用检测：无空闲的值机柜台");
                        info.setInteCheckingCounterCode("");
                        info.setIntlCheckingCounterCode("");
                    } else {
                        if (StringUtils.equals(info.getFlightDynamicNature(), "1")) { //国内进港
                            if (availableInteCodeList.isEmpty()) {
                                logger.error("未通过值机柜台占用检测：当前无可用的国内航班值机柜台");
                                flowContext.addVariable("success", false);
                                flowContext.addVariable("message", "未通过值机柜台占用检测：当前无可用的国内航班值机柜台");
                                info.setInteCheckingCounterCode("");
                                info.setIntlCheckingCounterCode("");
                            } else if (availableInteCodeList.size() < info.getExpectedCheckingCounterNum()) {
                                logger.error("未通过值机柜台占用检测：当前无足够的国内航班值机柜台");
                                flowContext.addVariable("success", false);
                                flowContext.addVariable("message", "未通过值机柜台占用检测：当前无足够可用的国内航班值机柜台");
                                info.setInteCheckingCounterCode("");
                                info.setIntlCheckingCounterCode("");
                            } else {
                                info.setInteCheckingCounterCode(findCarouselByAverageUsing(availableInteCodeList, usageVariable));
                                info.setActualCheckingCounterNum(info.getExpectedCheckingCounterNum());
                            }
                        } else if (StringUtils.equals(info.getFlightDynamicNature(), "2")) { // 国际进港
                            if (availableIntlCodeList.isEmpty()) {
                                logger.error("未通过值机柜台占用检测：当前无可用的国际航班值机柜台");
                                flowContext.addVariable("success", false);
                                flowContext.addVariable("message", "未通过值机柜台占用检测：当前无可用的国际航班值机柜台");
                                info.setInteCheckingCounterCode("");
                                info.setIntlCheckingCounterCode("");
                            } else if (availableIntlCodeList.size() < info.getExpectedCheckingCounterNum()) {
                                logger.error("未通过值机柜台占用检测：当前无足够的国际航班值机柜台");
                                flowContext.addVariable("success", false);
                                flowContext.addVariable("message", "未通过值机柜台占用检测：当前无足够的国际航班值机柜台");
                                info.setInteCheckingCounterCode("");
                                info.setIntlCheckingCounterCode("");
                            } else {
                                info.setIntlCheckingCounterCode(findCarouselByAverageUsing(availableIntlCodeList, usageVariable));
                                info.setActualCheckingCounterNum(info.getExpectedCheckingCounterNum());
                            }
                        } else if (StringUtils.equals(info.getFlightDynamicNature(), "3")) { // 混合航班
                            if (availableInteCodeList.isEmpty() || availableIntlCodeList.isEmpty()) {
                                logger.error("未通过值机柜台占用检测：当前无可用的值机柜台");
                                flowContext.addVariable("success", false);
                                flowContext.addVariable("message", "未通过值机柜台占用检测：当前无可用的值机柜台");
                                info.setInteCheckingCounterCode("");
                                info.setIntlCheckingCounterCode("");
                            } else if (availableIntlCodeList.size() < info.getExpectedCheckingCounterNum() || availableInteCodeList.size() < info.getExpectedCheckingCounterNum()) {
                                logger.error("未通过值机柜台占用检测：当前无足够的值机柜台");
                                flowContext.addVariable("success", false);
                                flowContext.addVariable("message", "未通过值机柜台占用检测：当前无足够的值机柜台");
                                info.setInteCheckingCounterCode("");
                                info.setIntlCheckingCounterCode("");
                            } else {
                                info.setInteCheckingCounterCode(findCarouselByAverageUsing(availableInteCodeList, usageVariable));
                                info.setIntlCheckingCounterCode(findCarouselByAverageUsing(availableIntlCodeList, usageVariable));
                                info.setActualCheckingCounterNum(info.getExpectedCheckingCounterNum() * 2);
                            }
                        } else { // 拋出异常，无法认识的航班属性
                            logger.error("未通过值机柜台占用检测：无法识别的航班属性 {}", info.getFlightDynamicNature());
                            flowContext.addVariable("success", false);
                            flowContext.addVariable("message", "未通过值机柜台占用检测：无法识别的航班属性 => " + info.getFlightDynamicNature());
                            info.setInteCheckingCounterCode("");
                            info.setIntlCheckingCounterCode("");
                        }

//                        checkingCounterOccupyingInfoService.save(info);
                    }
                } else {
                    logger.error("未通过值机柜台占用检测：无法获取航班动态对象");
                    flowContext.addVariable("success", false);
                    flowContext.addVariable("message", "未通过值机柜台占用检测：无法获取航班动态对象");
                }
                logger.info(" *** 值机柜台占用检测完毕 *** ");
            }
        });
    }

    /**
     * 根据平均使用率进行分配
     *
     * @return
     */
    private String findCarouselByAverageUsing(List<String> arrivalGates, Map<String, Object> usageVariable) {
        usageVariable.put("numbers", Joiner.on(",").join(arrivalGates));
        return Joiner.on(",").join(checkingCounterOccupyingInfoService.findCompatibleCodes(usageVariable));
    }
}
