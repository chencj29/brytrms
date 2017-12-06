package cn.net.metadata.rule.action.dist.gate;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.AircraftParameters;
import com.thinkgem.jeesite.modules.ams.service.AircraftParametersService;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.entity.AircraftStand;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;
import com.thinkgem.jeesite.modules.rms.entity.GateOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistInfo;
import com.thinkgem.jeesite.modules.rms.service.AircraftStandService;
import com.thinkgem.jeesite.modules.rms.service.FlightPlanPairService;
import com.thinkgem.jeesite.modules.rms.service.GateOccupyingInfoService;
import com.thinkgem.jeesite.modules.rms.service.ResourceSharingService;
import org.apache.commons.collections.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 多机位的翼展及占用检测
 * Created by xiaowu on 3/29/16.
 */
@Component
public class MultiAircraftsDetectingAction implements FlowAction {

    static final Logger logger = LoggerFactory.getLogger(MultiAircraftsDetectingAction.class);
    @Autowired
    AircraftStandService aircraftStandService;
    @Autowired
    AircraftParametersService aircraftParametersService;

    @Autowired
    FlightDynamicService flightDynamicService;
    @Autowired
    FlightPlanPairService flightPlanPairService;


    @Autowired
    GateOccupyingInfoService gateOccupyingInfoService;

    @Autowired
    ResourceSharingService resourceSharingService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {
        // 根据机位基本信息中的{左机位号}与{右机位号}组装配对机位，例如：
        // 两个机位时,  {1-2} 、 {2-3}
        // 三个机位时， {1-2-3}、 {2-3-4}

        flowContext.getWorkingMemory().getAllFacts().stream().filter(obj -> obj instanceof FlightPlanPair).findFirst().ifPresent(obj -> {
            FlightPlanPair flightPlanPair = (FlightPlanPair) obj;
            GateOccupyingInfo gateOccupyingInfo = (GateOccupyingInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof GateOccupyingInfo).findFirst().get();

            if (null == flightPlanPair || gateOccupyingInfo == null) {
                flowContext.addVariable("success", false);
                flowContext.addVariable("message", "未通过多机位的翼展及占用检测：不合法航班动态或机位占用实体");
                logger.error("未通过多机位的翼展及占用检测：不合法航班动态或机位占用实体");
            } else {
                if (StringUtils.isEmpty(flightPlanPair.getPlaceNum())) {
                    flowContext.addVariable("success", false);
                    flowContext.addVariable("message", "未通过多机位的翼展及占用检测：没有找到可用的机位号");
                    logger.error("未通过多机位的翼展及占用检测：没有找到可用的机位号");
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

                    List<AircraftStand> aircraftStandList = aircraftStandService.findByIds(Splitter.on(",").splitToList(flightPlanPair.getPlaceNum()));
                    // 得出当前航班的翼展
                    AircraftParameters aircraftParameters = aircraftParametersService.findByAircraftModelCode(flightPlanPair.getAircraftTypeCode());

                    Map<String, Object> occupiedVarirable = new HashMap<>();
                    Integer thresholdValue = Integer.parseInt(String.valueOf(flowContext.getVariable("threshold_value")));
                    Date _startDate = gateOccupyingInfo.getStartTime(), _overDate = gateOccupyingInfo.getOverTime();
                    occupiedVarirable.put("start", "'" + DateUtils.formatDateTime(DateUtils.addMinutes(_startDate, -thresholdValue)) + "'");
                    occupiedVarirable.put("over", "'" + DateUtils.formatDateTime(DateUtils.addMinutes(_overDate, thresholdValue)) + "'");
                    occupiedVarirable.put("flightDynamic", flightPlanPair.getId());

                    if (aircraftParameters == null) {
                        flowContext.addVariable("success", false);
                        flowContext.addVariable("message", "未通过多机位的翼展及占用检测：没有找到匹配的机型参数");
                        logger.error("未通过多机位的翼展及占用检测：没有找到匹配的机型参数");
                    } else {
                        List<String> occpiedList;
                        if (!mock_dist) occpiedList = flightDynamicService.findOccupiedGates(occupiedVarirable);
                        else {
                            ResourceMockDistInfo resourceMockDistInfo = (ResourceMockDistInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof ResourceMockDistInfo).findFirst().get();
                            occupiedVarirable.put("mockInfoId", resourceMockDistInfo.getId());
                            occupiedVarirable.put("omitOci", omit_oci);
                            // 根据startMockDate与overMockDate计算出planDate
                            String startMockDate = DateUtils.formatDate(resourceMockDistInfo.getMockStartDate()), overMockDate = DateUtils.formatDate(resourceMockDistInfo.getMockOverDate());
                            if (startMockDate.equals(overMockDate)) occupiedVarirable.put("samePlanDate", true);

                            occupiedVarirable.put("startMockPlanDate", DateUtils.parseDate(startMockDate));
                            occupiedVarirable.put("overMockPlanDate", DateUtils.parseDate(overMockDate));

                            occpiedList = flightDynamicService.findoccupiedGates4Mock(occupiedVarirable);
                        }


                        occpiedList = flightDynamicService.findOccupiedGates(occupiedVarirable);
                        // 根据列表找到其「左机位」及「右机位」配对进行对比
                        String diretional = String.valueOf(flowContext.getVariable("dist_type")).equals("手动分配") ? "right" : "bi";
                        List<AircraftStand> calcList = findCapableAircraftStand(aircraftStandList, aircraftParameters, occpiedList, diretional);
                        if (calcList.isEmpty()) {
                            flowContext.addVariable("success", false);
                            flowContext.addVariable("message", "未通过翼展及占用检测：没有找到可用的机位号");
                            logger.error("未通过多机位的翼展及占用检测：没有找到可用的机位号");
                            flightPlanPair.setPlaceNum("");
                            gateOccupyingInfo.setActualGateNum("");
                        } else {
                            String aircraftNums = Joiner.on(",").join(calcList.stream().map(AircraftStand::getAircraftStandNum).collect(Collectors.toList()));
                            gateOccupyingInfo.setActualGateNum(aircraftNums);
                            flightPlanPair.setPlaceNum(aircraftNums);
                            flowContext.addVariable("success", true);
                            flowContext.addVariable("message", "分配成功");
                            flowContext.addVariable("result", aircraftNums);
                        }

                        gateOccupyingInfo.setStartTime(_startDate);
                        gateOccupyingInfo.setOverTime(_overDate);
                        gateOccupyingInfo.setFlightDynamicId(flightPlanPair.getId());
                        gateOccupyingInfo.setLeave("0");
                    }
                }
            }
        });
    }

    private List<AircraftStand> findCapableAircraftStand(List<AircraftStand> aircraftStandList, AircraftParameters aircraftParameters, List<String> occupiedList, String diretional) {
        List<AircraftStand> returnList = Lists.newLinkedList(); // 需要计算的List
        for (AircraftStand aircraftStand : aircraftStandList) {
            // 如果当前机位即不存在「左」又不存在「右」，忽略
            if (StringUtils.isEmpty(aircraftStand.getLeftAsNum()) && StringUtils.isEmpty(aircraftStand.getRightAsNum())) continue;
            List<AircraftStand> stands = null;
            if (StringUtils.equals(diretional, "bi")) stands = ListUtils.union(aircraftStandService.findByAircraftNum(aircraftStand.getLeftAsNum()), aircraftStandService.findByAircraftNum(aircraftStand.getRightAsNum()));
            else if (StringUtils.equals(diretional, "left")) stands = aircraftStandService.findByAircraftNum(aircraftStand.getLeftAsNum());
            else if (StringUtils.equals(diretional, "right")) stands = aircraftStandService.findByAircraftNum(aircraftStand.getRightAsNum());

            for (AircraftStand stand : stands)
                if ((stand.getWingLength() + aircraftStand.getWingLength() >= aircraftParameters.getWingspan()) && (!occupiedList.contains(stand.getAircraftStandNum()) && !occupiedList.contains(aircraftStand.getAircraftStandNum())))
                    return Lists.newArrayList(aircraftStand, stand);
        }
        return returnList;
    }
}
