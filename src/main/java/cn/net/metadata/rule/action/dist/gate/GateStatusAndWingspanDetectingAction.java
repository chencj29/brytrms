package cn.net.metadata.rule.action.dist.gate;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.AircraftParameters;
import com.thinkgem.jeesite.modules.ams.service.AircraftParametersService;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.entity.AircraftStand;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;
import com.thinkgem.jeesite.modules.rms.service.AircraftStandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 机位状态（是否可用）及翼展检测
 * Created by xiaowu on 3/16/16.
 */
@Component
public class GateStatusAndWingspanDetectingAction implements FlowAction {

    static final Logger logger = LoggerFactory.getLogger(GateStatusAndWingspanDetectingAction.class);

    @Autowired
    AircraftStandService aircraftStandService;
    @Autowired
    FlightDynamicService flightDynamicService;
    @Autowired
    AircraftParametersService aircraftParametersService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {

        flowContext.getWorkingMemory().getAllFacts().stream().filter(obj -> obj instanceof FlightPlanPair).findFirst().ifPresent(obj -> {
            FlightPlanPair flightPlanPair = (FlightPlanPair) obj;
            if (StringUtils.isNotEmpty(flightPlanPair.getPlaceNum())) {
                logger.info(" *** 开始机位状态及翼展检测 *** ");
                logger.info("当前航班的机型编号为：{}", flightPlanPair.getAircraftTypeCode());
                AircraftParameters aircraftParameters = aircraftParametersService.findByAircraftModelCode(flightPlanPair.getAircraftTypeCode());
                if (null == aircraftParameters) {
                    logger.info("没有找到符合机型编号{}的机型信息，舍弃！", flightPlanPair.getAircraftTypeCode());
                    flightPlanPair.setPlaceNum("");
                    flowContext.addVariable("success", false);
                    flowContext.addVariable("message", "未通过机位状态及翼展检测：没有找到对应的机型！");
                } else {
                    logger.info("目前可用的机位为：{}", flightPlanPair.getPlaceNum());
                    List<AircraftStand> availableAircraftStands = aircraftStandService.checkStatusByIds(Splitter.on(",").splitToList(flightPlanPair.getPlaceNum()));
                    if (null == availableAircraftStands || availableAircraftStands.isEmpty()) {
                        logger.info("机位{}不可用，舍弃！", flightPlanPair.getPlaceNum());
                        flightPlanPair.setPlaceNum("");
                        flowContext.addVariable("success", false);
                        flowContext.addVariable("message", "未通过机位状态及翼展检测：机位不可用！");
                    } else {
                        logger.info("开始翼展检测");
                        List<String> filteredStands = availableAircraftStands.stream().filter(aircraftStand -> aircraftStand.getWingLength() >= aircraftParameters.getWingspan()).map(AircraftStand::getAircraftStandNum).collect(Collectors.toList());

                        if (filteredStands.isEmpty()) {
                            logger.info("翼展检测未通过，舍弃！");
                            flightPlanPair.setPlaceNum("");
                            flowContext.addVariable("success", false);
                            flowContext.addVariable("message", "未通过机位状态及翼展检测：翼展不符合条件！");
                        } else {
                            List<String> filteredStandsResult = Splitter.on(",").splitToList(flightPlanPair.getPlaceNum()).stream().filter(s -> filteredStands.contains(s)).collect(Collectors.toList());
                            flightPlanPair.setPlaceNum(Joiner.on(",").join(filteredStandsResult));
                            logger.info("翼展检测通过：" + Joiner.on(",").join(filteredStandsResult));
                        }
                    }
                }
            }
        });
    }
}
