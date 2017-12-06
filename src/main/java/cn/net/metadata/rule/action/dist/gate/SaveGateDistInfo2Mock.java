package cn.net.metadata.rule.action.dist.gate;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistDetail;
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistInfo;
import com.thinkgem.jeesite.modules.rms.service.ResourceMockDistDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xiaowu on 2017/3/8.
 */
@Component
public class SaveGateDistInfo2Mock implements FlowAction {
    @Autowired
    ResourceMockDistDetailService detailService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {
        FlightPlanPair flightPlanPair = (FlightPlanPair) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof FlightPlanPair).findFirst().get();
        ResourceMockDistInfo resourceMockDistInfo = (ResourceMockDistInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof ResourceMockDistInfo).findFirst().get();
        if (null != flightPlanPair && null != resourceMockDistInfo) {
            ResourceMockDistDetail detail = new ResourceMockDistDetail();
            detail.setDataId(flightPlanPair.getId());
            detail.setInfoId(resourceMockDistInfo.getId());
            detail.setInte(flightPlanPair.getPlaceNum());
            detail.setIntl(null);
            if (StringUtils.isBlank(flightPlanPair.getPlaceNum())) detail.setInfo(String.valueOf(flowContext.getVariable("message")));
            detailService.save(detail);
        }
    }
}
