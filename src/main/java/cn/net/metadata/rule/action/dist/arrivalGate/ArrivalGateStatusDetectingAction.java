package cn.net.metadata.rule.action.dist.arrivalGate;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.thinkgem.jeesite.modules.rms.entity.ArrivalGate;
import com.thinkgem.jeesite.modules.rms.entity.ArrivalGateOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.ArrivalGateService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * 到港门状态检测
 * Created by xiaowu on 3/28/16.
 */
@Component
public class ArrivalGateStatusDetectingAction implements FlowAction {

    static final Logger logger = LoggerFactory.getLogger(ArrivalGateStatusDetectingAction.class);

    @Autowired
    ArrivalGateService arrivalGateService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstances) {
        flowContext.getWorkingMemory().getAllFacts().stream().filter(obj -> obj instanceof ArrivalGateOccupyingInfo).findFirst().ifPresent(obj -> {
            ArrivalGateOccupyingInfo info = (ArrivalGateOccupyingInfo) obj;
            logger.info(" *** 开始到港门状态检测 *** ");
            if (StringUtils.isEmpty(info.getInteArrivalGateCode()) && StringUtils.isEmpty(info.getIntlArrivalGateCode())) {
                flowContext.addVariable("success", false);
                flowContext.addVariable("message", "没有找到到港门编号");
                logger.info("没有找到到港门编号！");
            } else {
                if (StringUtils.isNotEmpty(info.getInteArrivalGateCode()))
                    info.setInteArrivalGateCode(Joiner.on(",").join(arrivalGateService.checkStatusByArrivalGateNum(Splitter.on(",").splitToList(info.getInteArrivalGateCode())).stream().map(ArrivalGate::getArrivalGateNum).collect(Collectors.toList())));

                if (StringUtils.isNotEmpty(info.getIntlArrivalGateCode()))
                    info.setIntlArrivalGateCode(Joiner.on(",").join(arrivalGateService.checkStatusByArrivalGateNum(Splitter.on(",").splitToList(info.getIntlArrivalGateCode())).stream().map(ArrivalGate::getArrivalGateNum).collect(Collectors.toList())));

                if (StringUtils.isEmpty(info.getInteArrivalGateCode()) && StringUtils.isEmpty(info.getIntlArrivalGateCode())) {
                    flowContext.addVariable("success", false);
                    flowContext.addVariable("message", "未通过到港门状态检测：未找到可用的到港门");
                    logger.info("未通过到港门状态检测：未找到可用的到港门");
                }
            }

            logger.info(" *** 到港门状态检测完毕 *** ");
        });
    }
}
