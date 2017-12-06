package cn.net.metadata.rule.action.dist.carousel;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.rms.entity.CarouselOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistDetail;
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistInfo;
import com.thinkgem.jeesite.modules.rms.service.ResourceMockDistDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xiaowu on 2017/3/21.
 */
@Component
public class SaveCarouselDistInfo2Mock implements FlowAction {
    @Autowired
    ResourceMockDistDetailService detailService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {
        FlightDynamic flightDynamic = (FlightDynamic) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof FlightDynamic).findFirst().get();
        CarouselOccupyingInfo carouselOccupyingInfo = (CarouselOccupyingInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof CarouselOccupyingInfo).findFirst().get();
        ResourceMockDistInfo resourceMockDistInfo = (ResourceMockDistInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof ResourceMockDistInfo).findFirst().get();
        if (null != flightDynamic && carouselOccupyingInfo != null) {
            ResourceMockDistDetail detail = new ResourceMockDistDetail();
            detail.setDataId(flightDynamic.getId());
            detail.setInfoId(resourceMockDistInfo.getId());
            detail.setInte(carouselOccupyingInfo.getInteCarouselCode());
            detail.setIntl(carouselOccupyingInfo.getIntlCarouselCode());
            if (StringUtils.isBlank(detail.getInte()) && StringUtils.isBlank(detail.getIntl()))
                detail.setInfo(String.valueOf(flowContext.getVariable("message")));
            detailService.save(detail);
        }
    }
}
