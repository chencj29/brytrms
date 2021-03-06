package cn.net.metadata.rule.action.dist.departureHall;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.entity.DepartureHallOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistInfo;
import com.thinkgem.jeesite.modules.rms.service.DepartureHallOccupyingInfoService;
import com.thinkgem.jeesite.modules.rms.service.ResourceSharingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 候机厅占用检测
 * Created by xiaowu on 3/28/16.
 */
@Component
public class DepartureHallOccupyingDetectingAction implements FlowAction {
    static final Logger logger = LoggerFactory.getLogger(DepartureHallOccupyingDetectingAction.class);
    @Autowired
    FlightDynamicService flightDynamicService;
    @Autowired
    DepartureHallOccupyingInfoService departureHallOccupyingInfoService;
    @Autowired
    ResourceSharingService resourceSharingService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {
        flowContext.getWorkingMemory().getAllFacts().stream().filter(obj -> obj instanceof DepartureHallOccupyingInfo).findFirst().ifPresent(obj -> {
            DepartureHallOccupyingInfo info = (DepartureHallOccupyingInfo) obj;
            if (StringUtils.isNotEmpty(info.getInteDepartureHallCode()) || StringUtils.isNotEmpty(info.getIntlDepartureHallCode())) {
                logger.info(" *** 开始候机厅占用检测 *** ");
                logger.info("航班属性：{}, 国内：{}， 国际：{}", info.getFlightDynamicNature(), info.getInteDepartureHallCode(), info.getIntlDepartureHallCode());
                Object flightDynamicObj = flowContext.getWorkingMemory().getAllFacts().stream().filter(dynamic -> dynamic instanceof FlightDynamic).findFirst().get();
                FlightDynamic flightDynamic;

                if (flightDynamicObj != null) {
                    flightDynamic = (FlightDynamic) flightDynamicObj;

                    // 计算预计开始及结束时间
                    Date startTime = info.getExpectedStartTime();
                    Date overTime = info.getExpectedOverTime();
                    List<String> availableInteCodeList = Lists.newArrayList(), availableIntlCodeList = Lists.newArrayList();
                    if (StringUtils.isNotEmpty(info.getInteDepartureHallCode())) availableInteCodeList = Splitter.on(",").splitToList(info.getInteDepartureHallCode());
                    if (StringUtils.isNotEmpty(info.getIntlDepartureHallCode())) availableIntlCodeList = Splitter.on(",").splitToList(info.getIntlDepartureHallCode());

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
                    usageVariable.put("expectedNum", info.getExpectedDepartureHallNum());

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
                        logger.error("未通过候机厅占用检测：无空闲的候机厅");
                        flowContext.addVariable("success", false);
                        flowContext.addVariable("message", "未通过候机厅占用检测：无空闲的候机厅");
                        info.setInteDepartureHallCode("");
                        info.setIntlDepartureHallCode("");
                    } else {
                        if (StringUtils.equals(info.getFlightDynamicNature(), "1")) { //国内进港
                            if (availableInteCodeList.isEmpty()) {
                                logger.error("未通过候机厅占用检测：当前无可用的国内航班候机厅");
                                flowContext.addVariable("success", false);
                                flowContext.addVariable("message", "未通过候机厅占用检测：当前无可用的国内航班候机厅");
                                info.setInteDepartureHallCode("");
                                info.setIntlDepartureHallCode("");
                            } else if (availableInteCodeList.size() < info.getExpectedDepartureHallNum()) {
                                logger.error("未通过候机厅占用检测：当前无足够的国内航班候机厅");
                                flowContext.addVariable("success", false);
                                flowContext.addVariable("message", "未通过候机厅占用检测：当前无足够可用的国内航班候机厅");
                                info.setInteDepartureHallCode("");
                                info.setIntlDepartureHallCode("");
                            } else {
                                info.setInteDepartureHallCode(findCarouselByAverageUsing(availableInteCodeList, usageVariable));
                                info.setActualDepartureHallNum(info.getExpectedDepartureHallNum());
                            }
                        } else if (StringUtils.equals(info.getFlightDynamicNature(), "2")) { // 国际进港
                            if (availableIntlCodeList.isEmpty()) {
                                logger.error("未通过候机厅占用检测：当前无可用的国际航班候机厅");
                                flowContext.addVariable("success", false);
                                flowContext.addVariable("message", "未通过候机厅占用检测：当前无可用的国际航班候机厅");
                                info.setInteDepartureHallCode("");
                                info.setIntlDepartureHallCode("");
                            } else if (availableIntlCodeList.size() < info.getExpectedDepartureHallNum()) {
                                logger.error("未通过候机厅占用检测：当前无足够的国际航班候机厅");
                                flowContext.addVariable("success", false);
                                flowContext.addVariable("message", "未通过候机厅占用检测：当前无足够的国际航班候机厅");
                                info.setInteDepartureHallCode("");
                                info.setIntlDepartureHallCode("");
                            } else {
                                info.setIntlDepartureHallCode(findCarouselByAverageUsing(availableIntlCodeList, usageVariable));
                                info.setActualDepartureHallNum(info.getExpectedDepartureHallNum());
                            }
                        } else if (StringUtils.equals(info.getFlightDynamicNature(), "3")) { // 混合航班
                            if (availableInteCodeList.isEmpty() || availableIntlCodeList.isEmpty()) {
                                logger.error("未通过候机厅占用检测：当前无可用的候机厅");
                                flowContext.addVariable("success", false);
                                flowContext.addVariable("message", "未通过候机厅占用检测：当前无可用的候机厅");
                                info.setInteDepartureHallCode("");
                                info.setIntlDepartureHallCode("");
                            } else if (availableIntlCodeList.size() < info.getExpectedDepartureHallNum() || availableInteCodeList.size() < info.getExpectedDepartureHallNum()) {
                                logger.error("未通过候机厅占用检测：当前无足够的候机厅");
                                flowContext.addVariable("success", false);
                                flowContext.addVariable("message", "未通过候机厅占用检测：当前无足够的候机厅");
                                info.setInteDepartureHallCode("");
                                info.setIntlDepartureHallCode("");
                            } else {
                                info.setInteDepartureHallCode(findCarouselByAverageUsing(availableInteCodeList, usageVariable));
                                info.setIntlDepartureHallCode(findCarouselByAverageUsing(availableIntlCodeList, usageVariable));
                                info.setActualDepartureHallNum(info.getExpectedDepartureHallNum() * 2);
                            }
                        } else { // 拋出异常，无法认识的航班属性
                            logger.error("未通过候机厅占用检测：无法识别的航班属性 {}", info.getFlightDynamicNature());
                            flowContext.addVariable("success", false);
                            flowContext.addVariable("message", "未通过候机厅占用检测：无法识别的航班属性 => " + info.getFlightDynamicNature());
                            info.setInteDepartureHallCode("");
                            info.setIntlDepartureHallCode("");
                        }

//                        departureHallOccupyingInfoService.save(info);
                    }

                } else {
                    logger.error("未通过候机厅占用检测：无法获取航班动态对象");
                    flowContext.addVariable("success", false);
                    flowContext.addVariable("message", "未通过候机厅占用检测：无法获取航班动态对象");
                }
                logger.info(" *** 候机厅占用检测完毕 *** ");
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
        return Joiner.on(",").join(departureHallOccupyingInfoService.findCompatibleCodes(usageVariable));
    }
}
