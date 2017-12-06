package cn.net.metadata.rule.action.dist.checkingCounter;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.rms.entity.CheckingCounterOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.CheckingCounterOccupyingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xiaowu on 2017/3/22.
 */
@Component
public class SaveCheckingCounterDistInfo2DB implements FlowAction {

    @Autowired
    CheckingCounterOccupyingInfoService occupyingInfoService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {
        FlightDynamic flightDynamic = (FlightDynamic) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof FlightDynamic).findFirst().get();
        CheckingCounterOccupyingInfo checkingCounterOccupyingInfo = (CheckingCounterOccupyingInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof CheckingCounterOccupyingInfo).findFirst().get();

        if (null != flightDynamic && null != checkingCounterOccupyingInfo
                && (StringUtils.isNotBlank(checkingCounterOccupyingInfo.getInteCheckingCounterCode())
                || StringUtils.isNoneBlank(checkingCounterOccupyingInfo.getIntlCheckingCounterCode()))) {
            occupyingInfoService.save(checkingCounterOccupyingInfo);

            flowContext.addVariable("success", true);
            flowContext.addVariable("message", "值机柜台分配成功");
            flowContext.addVariable("result", checkingCounterOccupyingInfo);
        }
    }
}
