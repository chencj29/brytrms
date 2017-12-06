package cn.net.metadata.rule.action.dist.carousel;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.rms.entity.CarouselOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.CarouselOccupyingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xiaowu on 2017/3/21.
 */
@Component
public class SaveCarouselDistInfo2DB implements FlowAction {

    @Autowired
    CarouselOccupyingInfoService occupyingInfoService;


    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {
        FlightDynamic flightDynamic = (FlightDynamic) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof FlightDynamic).findFirst().get();
        CarouselOccupyingInfo carouselOccupyingInfo = (CarouselOccupyingInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof CarouselOccupyingInfo).findFirst().get();


        if (null != flightDynamic && null != carouselOccupyingInfo
                && (StringUtils.isNotBlank(carouselOccupyingInfo.getInteCarouselCode())
                || StringUtils.isNoneBlank(carouselOccupyingInfo.getIntlCarouselCode()))) {
            occupyingInfoService.save(carouselOccupyingInfo);

            flowContext.addVariable("success", true);
            flowContext.addVariable("message", "行李转盘分配成功");
            flowContext.addVariable("result", carouselOccupyingInfo);
        }

    }
}
