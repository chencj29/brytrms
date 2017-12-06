package cn.net.metadata.rule.action.dist.checkingCounter;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.thinkgem.jeesite.modules.rms.entity.CheckingCounterOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.entity.RmsCheckinCounter;
import com.thinkgem.jeesite.modules.rms.service.RmsCheckinCounterService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * 值机柜台状态检测
 * Created by xiaowu on 3/28/16.
 */
@Component
public class CheckingCounterStatusDetectingAction implements FlowAction {

    static final Logger logger = LoggerFactory.getLogger(CheckingCounterStatusDetectingAction.class);

    @Autowired
    RmsCheckinCounterService rmsCheckinCounterServices;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstances) {
        flowContext.getWorkingMemory().getAllFacts().stream().filter(obj -> obj instanceof CheckingCounterOccupyingInfo).findFirst().ifPresent(obj -> {
            CheckingCounterOccupyingInfo info = (CheckingCounterOccupyingInfo) obj;
            logger.info(" *** 开始值机柜台状态检测 *** ");
            if (StringUtils.isEmpty(info.getInteCheckingCounterCode()) && StringUtils.isEmpty(info.getIntlCheckingCounterCode())) {
                flowContext.addVariable("success", false);
                flowContext.addVariable("message", "没有找到值机柜台编号");
                logger.info("没有找到值机柜台编号！");
            } else {
                if (StringUtils.isNotEmpty(info.getInteCheckingCounterCode()))
                    info.setInteCheckingCounterCode(Joiner.on(",").join(rmsCheckinCounterServices.checkStatusByCheckinCounterNum(Splitter.on(",").splitToList(info.getInteCheckingCounterCode())).stream().map(RmsCheckinCounter::getCheckinCounterNum).collect(Collectors.toList())));

                if (StringUtils.isNotEmpty(info.getIntlCheckingCounterCode()))
                    info.setIntlCheckingCounterCode(Joiner.on(",").join(rmsCheckinCounterServices.checkStatusByCheckinCounterNum(Splitter.on(",").splitToList(info.getIntlCheckingCounterCode())).stream().map(RmsCheckinCounter::getCheckinCounterNum).collect(Collectors.toList())));

                if (StringUtils.isEmpty(info.getInteCheckingCounterCode()) && StringUtils.isEmpty(info.getIntlCheckingCounterCode())) {
                    flowContext.addVariable("success", false);
                    flowContext.addVariable("message", "未通过值机柜台状态检测：未找到可用的值机柜台");
                    logger.info("未通过值机柜台状态检测：未找到可用的值机柜台");
                }
            }

            logger.info(" *** 值机柜台状态检测完毕 *** ");
        });
    }
}
