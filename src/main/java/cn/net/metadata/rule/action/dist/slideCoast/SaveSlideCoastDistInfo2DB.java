package cn.net.metadata.rule.action.dist.slideCoast;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.rms.entity.SlideCoastOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.SlideCoastOccupyingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xiaowu on 2017/3/22.
 */
@Component
public class SaveSlideCoastDistInfo2DB implements FlowAction {

    @Autowired
    SlideCoastOccupyingInfoService occupyingInfoService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {
        FlightDynamic flightDynamic = (FlightDynamic) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof FlightDynamic).findFirst().get();
        SlideCoastOccupyingInfo slideCoastOccupyingInfo = (SlideCoastOccupyingInfo) flowContext.getWorkingMemory().getAllFacts().stream().filter(info -> info instanceof SlideCoastOccupyingInfo).findFirst().get();


        if (null != flightDynamic && null != slideCoastOccupyingInfo
                && (StringUtils.isNotBlank(slideCoastOccupyingInfo.getInteSlideCoastCode())
                || StringUtils.isNoneBlank(slideCoastOccupyingInfo.getIntlSlideCoastCode()))) {
            occupyingInfoService.save(slideCoastOccupyingInfo);

            flowContext.addVariable("success", true);
            flowContext.addVariable("message", "滑槽分配成功");
            flowContext.addVariable("result", slideCoastOccupyingInfo);
        }
    }
}
