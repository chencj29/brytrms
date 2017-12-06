package cn.net.metadata.rule.action.dist.boardingGate;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.rms.entity.BoardingGateOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.BoardingGateOccupyingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xiaowu on 2017/3/21.
 */
@Component
public class SaveBoardingGateDistInfo2DB implements FlowAction {

    @Autowired
    BoardingGateOccupyingInfoService occupyingInfoService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {
        FlightDynamic flightDynamic = (FlightDynamic) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof FlightDynamic).findFirst().get();
        BoardingGateOccupyingInfo boardingGateOccupyingInfo = (BoardingGateOccupyingInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof BoardingGateOccupyingInfo).findFirst().get();


        if (null != flightDynamic && null != boardingGateOccupyingInfo
                && (StringUtils.isNotBlank(boardingGateOccupyingInfo.getInteBoardingGateCode())
                || StringUtils.isNoneBlank(boardingGateOccupyingInfo.getIntlBoardingGateCode()))) {
            occupyingInfoService.save(boardingGateOccupyingInfo);

            flowContext.addVariable("success", true);
            flowContext.addVariable("message", "登机口分配成功");
            flowContext.addVariable("result", boardingGateOccupyingInfo);
        }
    }
}
