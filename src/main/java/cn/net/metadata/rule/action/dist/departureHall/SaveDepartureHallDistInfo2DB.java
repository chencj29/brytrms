package cn.net.metadata.rule.action.dist.departureHall;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.rms.entity.DepartureHallOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.DepartureHallOccupyingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xiaowu on 2017/3/23.
 */
@Component
public class SaveDepartureHallDistInfo2DB implements FlowAction {
    @Autowired
    DepartureHallOccupyingInfoService occupyingInfoService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {
        FlightDynamic flightDynamic = (FlightDynamic) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof FlightDynamic).findFirst().get();
        DepartureHallOccupyingInfo departureHallOccupyingInfo = (DepartureHallOccupyingInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof DepartureHallOccupyingInfo).findFirst().get();

        if (null != flightDynamic && null != departureHallOccupyingInfo
                && (StringUtils.isNotBlank(departureHallOccupyingInfo.getInteDepartureHallCode())
                || StringUtils.isNoneBlank(departureHallOccupyingInfo.getIntlDepartureHallCode()))) {
            occupyingInfoService.save(departureHallOccupyingInfo);

            flowContext.addVariable("success", true);
            flowContext.addVariable("message", "候机厅分配成功");
            flowContext.addVariable("result", departureHallOccupyingInfo);
        }
    }
}
