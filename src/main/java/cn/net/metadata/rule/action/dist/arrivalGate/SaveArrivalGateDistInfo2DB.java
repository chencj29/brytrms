package cn.net.metadata.rule.action.dist.arrivalGate;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.rms.entity.ArrivalGateOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.ArrivalGateOccupyingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xiaowu on 2017/3/21.
 */
@Component
public class SaveArrivalGateDistInfo2DB implements FlowAction {

    @Autowired
    ArrivalGateOccupyingInfoService occupyingInfoService;


    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {
        FlightDynamic flightDynamic = (FlightDynamic) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof FlightDynamic).findFirst().get();
        ArrivalGateOccupyingInfo arrivalGateOccupyingInfo = (ArrivalGateOccupyingInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof ArrivalGateOccupyingInfo).findFirst().get();


        if (null != flightDynamic && null != arrivalGateOccupyingInfo
                && (StringUtils.isNotBlank(arrivalGateOccupyingInfo.getInteArrivalGateCode())
                || StringUtils.isNoneBlank(arrivalGateOccupyingInfo.getIntlArrivalGateCode()))) {
            occupyingInfoService.save(arrivalGateOccupyingInfo);

            flowContext.addVariable("success", true);
            flowContext.addVariable("message", "到港门分配成功");
            flowContext.addVariable("result", arrivalGateOccupyingInfo);
        }

    }
}
