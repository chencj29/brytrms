package cn.net.metadata.rule.action.dist.boardingGate;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.rms.entity.BoardingGate;
import com.thinkgem.jeesite.modules.rms.entity.BoardingGateOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.BoardingGateService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * 登机口状态检测
 * Created by xiaowu on 3/28/16.
 */
@Component
public class BoardingGateStatusDetectingAction implements FlowAction {

    static final Logger logger = LoggerFactory.getLogger(BoardingGateStatusDetectingAction.class);

    @Autowired
    BoardingGateService boardingGateService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstances) {
        flowContext.getWorkingMemory().getAllFacts().stream().filter(obj -> obj instanceof BoardingGateOccupyingInfo).findFirst().ifPresent(obj -> {
            BoardingGateOccupyingInfo info = (BoardingGateOccupyingInfo) obj;
            logger.info(" *** 开始登机口状态检测 *** ");

            if (StringUtils.isEmpty(info.getInteBoardingGateCode()) && StringUtils.isEmpty(info.getIntlBoardingGateCode())) {
                FlightDynamic flightDynamic = (FlightDynamic) flowContext.getWorkingMemory().getAllFacts().stream().filter(dynamic -> dynamic instanceof FlightDynamic).findFirst().get();
                flowContext.addVariable("success", false);
                String failReason = "经静态规则过滤，无适用的登机口！";
                if (null != flightDynamic && StringUtils.isBlank(flightDynamic.getPlaceNum())) failReason = "该航班的机位号为空！";

                flowContext.addVariable("message", failReason);
                logger.info(failReason);
            } else {
                if (StringUtils.isNotEmpty(info.getInteBoardingGateCode()))
                    info.setInteBoardingGateCode(Joiner.on(",").join(boardingGateService.checkStatusByArrivalGateNum(Splitter.on(",").splitToList(info.getInteBoardingGateCode())).stream().map(BoardingGate::getBoardingGateNum).collect(Collectors.toList())));

                if (StringUtils.isNotEmpty(info.getIntlBoardingGateCode()))
                    info.setIntlBoardingGateCode(Joiner.on(",").join(boardingGateService.checkStatusByArrivalGateNum(Splitter.on(",").splitToList(info.getIntlBoardingGateCode())).stream().map(BoardingGate::getBoardingGateNum).collect(Collectors.toList())));

                if (StringUtils.isEmpty(info.getInteBoardingGateCode()) && StringUtils.isEmpty(info.getIntlBoardingGateCode())) {
                    flowContext.addVariable("success", false);
                    flowContext.addVariable("message", "未通过登机口状态检测：未找到可用的登机口");
                    logger.info("未通过登机口状态检测：未找到可用的登机口");
                }
            }

            logger.info(" *** 登机口状态检测完毕 *** ");
        });
    }
}
