package cn.net.metadata.rule.action.dist.gate;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;
import com.thinkgem.jeesite.modules.rms.entity.GateOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.FlightPlanPairService;
import com.thinkgem.jeesite.modules.rms.service.GateOccupyingInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xiaowu on 2017/3/8.
 */
@Component
public class SaveGateDistInfo2DB implements FlowAction {
    @Autowired
    GateOccupyingInfoService gateOccupyingInfoService;

    @Autowired
    FlightPlanPairService flightPlanPairService;

    @Autowired
    FlightDynamicService flightDynamicService;

    private static final Logger logger = LoggerFactory.getLogger(SaveGateDistInfo2DB.class);


    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {
        FlightPlanPair flightPlanPair = (FlightPlanPair) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof FlightPlanPair).findFirst().get();
        GateOccupyingInfo gateOccupyingInfo = (GateOccupyingInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof GateOccupyingInfo).findFirst().get();
        if (null != flightPlanPair && StringUtils.isNotBlank(flightPlanPair.getPlaceNum()) && null != gateOccupyingInfo) {
            gateOccupyingInfo.setFlightDynamicCode(flightPlanPair.getVirtualTipField());
            gateOccupyingInfo.setActualGateNum(flightPlanPair.getPlaceNum());
            gateOccupyingInfoService.save(gateOccupyingInfo);
            logger.info("信息记录成功，{}", gateOccupyingInfo);

            flowContext.addVariable("success", true);
            flowContext.addVariable("message", "机位分配成功");
            flowContext.addVariable("result", flightPlanPair.getPlaceNum());
            flightPlanPairService.save(flightPlanPair);
            flightDynamicService.updateFlightDynamicInfoAfterAircraftDistributed(flightPlanPair);
        }
    }
}
