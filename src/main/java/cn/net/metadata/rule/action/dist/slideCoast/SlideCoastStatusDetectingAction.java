package cn.net.metadata.rule.action.dist.slideCoast;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.thinkgem.jeesite.modules.rms.entity.SlideCoast;
import com.thinkgem.jeesite.modules.rms.entity.SlideCoastOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.SlideCoastService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * 滑槽状态检测
 * Created by xiaowu on 3/28/16.
 */
@Component
public class SlideCoastStatusDetectingAction implements FlowAction {

    static final Logger logger = LoggerFactory.getLogger(SlideCoastStatusDetectingAction.class);

    @Autowired
    SlideCoastService slideCoastService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstances) {
        flowContext.getWorkingMemory().getAllFacts().stream().filter(obj -> obj instanceof SlideCoastOccupyingInfo).findFirst().ifPresent(obj -> {
            SlideCoastOccupyingInfo info = (SlideCoastOccupyingInfo) obj;
            logger.info(" *** 开始滑槽状态检测 *** ");
            if (StringUtils.isEmpty(info.getInteSlideCoastCode()) && StringUtils.isEmpty(info.getIntlSlideCoastCode())) {
                flowContext.addVariable("success", false);
                flowContext.addVariable("message", "没有找到滑槽编号");
                logger.info("没有找到滑槽编号！");
            } else {
                if (StringUtils.isNotEmpty(info.getInteSlideCoastCode())) info.setInteSlideCoastCode(Joiner.on(",").join(slideCoastService.checkStatusBySlideCoastNum(Splitter.on(",").splitToList(info.getInteSlideCoastCode())).stream().map(SlideCoast::getSlideCoastNum).collect(Collectors.toList())));

                if (StringUtils.isNotEmpty(info.getIntlSlideCoastCode())) info.setIntlSlideCoastCode(Joiner.on(",").join(slideCoastService.checkStatusBySlideCoastNum(Splitter.on(",").splitToList(info.getIntlSlideCoastCode())).stream().map(SlideCoast::getSlideCoastNum).collect(Collectors.toList())));

                if (StringUtils.isEmpty(info.getInteSlideCoastCode()) && StringUtils.isEmpty(info.getIntlSlideCoastCode())) {
                    flowContext.addVariable("success", false);
                    flowContext.addVariable("message", "未通过滑槽状态检测：未找到可用的滑槽");
                    logger.info("未通过滑槽状态检测：未找到可用的滑槽");
                }
            }

            logger.info(" *** 滑槽状态检测完毕 *** ");
        });
    }
}
