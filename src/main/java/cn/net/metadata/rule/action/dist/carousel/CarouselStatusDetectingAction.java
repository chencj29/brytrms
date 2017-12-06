package cn.net.metadata.rule.action.dist.carousel;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.thinkgem.jeesite.modules.rms.entity.CarouselOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.entity.RmsCarousel;
import com.thinkgem.jeesite.modules.rms.service.RmsCarouselService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * 行李转盘状态检测
 * Created by xiaowu on 3/28/16.
 */
@Component
public class CarouselStatusDetectingAction implements FlowAction {

    static final Logger logger = LoggerFactory.getLogger(CarouselStatusDetectingAction.class);

    @Autowired
    RmsCarouselService carouselService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstances) {
        flowContext.getWorkingMemory().getAllFacts().stream().filter(obj -> obj instanceof CarouselOccupyingInfo).findFirst().ifPresent(obj -> {
            CarouselOccupyingInfo info = (CarouselOccupyingInfo) obj;
            logger.info(" *** 开始行李转盘状态检测 *** ");
            if (StringUtils.isEmpty(info.getInteCarouselCode()) && StringUtils.isEmpty(info.getIntlCarouselCode())) {
                flowContext.addVariable("success", false);
                flowContext.addVariable("message", "没有找到行李转盘编号");
                logger.info("没有找到行李转盘编号！");
            } else {
                if (StringUtils.isNotEmpty(info.getInteCarouselCode())) info.setInteCarouselCode(Joiner.on(",").join(carouselService.checkStatusByCarouselNum(Splitter.on(",").splitToList(info.getInteCarouselCode())).stream().map(RmsCarousel::getCarouselNum).collect(Collectors.toList())));

                if (StringUtils.isNotEmpty(info.getIntlCarouselCode())) info.setIntlCarouselCode(Joiner.on(",").join(carouselService.checkStatusByCarouselNum(Splitter.on(",").splitToList(info.getIntlCarouselCode())).stream().map(RmsCarousel::getCarouselNum).collect(Collectors.toList())));

                if (StringUtils.isEmpty(info.getInteCarouselCode()) && StringUtils.isEmpty(info.getIntlCarouselCode())) {
                    flowContext.addVariable("success", false);
                    flowContext.addVariable("message", "未通过行李转盘状态检测：未找到可用的行李转盘");
                    logger.info("未通过行李转盘状态检测：未找到可用的行李转盘");
                }
            }

            logger.info(" *** 行李转盘状态检测完毕 *** ");
        });
    }
}
