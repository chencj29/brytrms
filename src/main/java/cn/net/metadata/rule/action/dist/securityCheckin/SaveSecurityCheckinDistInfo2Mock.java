package cn.net.metadata.rule.action.dist.securityCheckin;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistDetail;
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistInfo;
import com.thinkgem.jeesite.modules.rms.entity.SecurityCheckinOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.ResourceMockDistDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xiaowu on 2017/3/23.
 */
@Component
public class SaveSecurityCheckinDistInfo2Mock implements FlowAction {
    @Autowired
    ResourceMockDistDetailService detailService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {
        FlightDynamic flightDynamic = (FlightDynamic) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof FlightDynamic).findFirst().get();
        SecurityCheckinOccupyingInfo securityCheckinOccupyingInfo = (SecurityCheckinOccupyingInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof SecurityCheckinOccupyingInfo).findFirst().get();

        ResourceMockDistInfo resourceMockDistInfo = (ResourceMockDistInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof ResourceMockDistInfo).findFirst().get();
        if (null != flightDynamic && securityCheckinOccupyingInfo != null) {
            ResourceMockDistDetail detail = new ResourceMockDistDetail();
            detail.setDataId(flightDynamic.getId());
            detail.setInfoId(resourceMockDistInfo.getId());
            detail.setInte(securityCheckinOccupyingInfo.getInteSecurityCheckinCode());
            detail.setIntl(securityCheckinOccupyingInfo.getIntlSecurityCheckinCode());
            if (StringUtils.isBlank(detail.getInte()) && StringUtils.isBlank(detail.getIntl())) detail.setInfo(String.valueOf(flowContext.getVariable("message")));
            detailService.save(detail);
        }
    }
}
