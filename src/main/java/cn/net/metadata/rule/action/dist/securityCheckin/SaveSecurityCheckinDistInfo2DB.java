package cn.net.metadata.rule.action.dist.securityCheckin;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.rms.entity.SecurityCheckinOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.SecurityCheckinOccupyingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xiaowu on 2017/3/23.
 */
@Component
public class SaveSecurityCheckinDistInfo2DB implements FlowAction {
    @Autowired
    SecurityCheckinOccupyingInfoService occupyingInfoService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {
        FlightDynamic flightDynamic = (FlightDynamic) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof FlightDynamic).findFirst().get();
        SecurityCheckinOccupyingInfo securityCheckinOccupyingInfo = (SecurityCheckinOccupyingInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof SecurityCheckinOccupyingInfo).findFirst().get();

        if (null != flightDynamic && null != securityCheckinOccupyingInfo
                && (StringUtils.isNotBlank(securityCheckinOccupyingInfo.getInteSecurityCheckinCode())
                || StringUtils.isNoneBlank(securityCheckinOccupyingInfo.getIntlSecurityCheckinCode()))) {
            occupyingInfoService.save(securityCheckinOccupyingInfo);

            flowContext.addVariable("success", true);
            flowContext.addVariable("message", "安检口分配成功");
            flowContext.addVariable("result", securityCheckinOccupyingInfo);
        }
    }

}
