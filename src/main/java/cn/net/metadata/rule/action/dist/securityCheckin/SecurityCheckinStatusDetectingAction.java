package cn.net.metadata.rule.action.dist.securityCheckin;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.thinkgem.jeesite.modules.rms.entity.SecurityCheckin;
import com.thinkgem.jeesite.modules.rms.entity.SecurityCheckinOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.SecurityCheckinService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * 安检口状态检测
 * Created by xiaowu on 3/28/16.
 */
@Component
public class SecurityCheckinStatusDetectingAction implements FlowAction {

    static final Logger logger = LoggerFactory.getLogger(SecurityCheckinStatusDetectingAction.class);

    @Autowired
    SecurityCheckinService securityCheckinService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstances) {
        flowContext.getWorkingMemory().getAllFacts().stream().filter(obj -> obj instanceof SecurityCheckinOccupyingInfo).findFirst().ifPresent(obj -> {
            SecurityCheckinOccupyingInfo info = (SecurityCheckinOccupyingInfo) obj;
            logger.info(" *** 开始安检口状态检测 *** ");
            if (StringUtils.isEmpty(info.getInteSecurityCheckinCode()) && StringUtils.isEmpty(info.getIntlSecurityCheckinCode())) {
                flowContext.addVariable("success", false);
                flowContext.addVariable("message", "没有找到安检口编号");
                logger.info("没有找到安检口编号！");
            } else {
                if (StringUtils.isNotEmpty(info.getInteSecurityCheckinCode()))
                    info.setInteSecurityCheckinCode(Joiner.on(",").join(securityCheckinService.checkStatusBySecurityCheckinNum(Splitter.on(",").splitToList(info.getInteSecurityCheckinCode())).stream().map(SecurityCheckin::getScecurityCheckinNum).collect(Collectors.toList())));

                if (StringUtils.isNotEmpty(info.getIntlSecurityCheckinCode()))
                    info.setIntlSecurityCheckinCode(Joiner.on(",").join(securityCheckinService.checkStatusBySecurityCheckinNum(Splitter.on(",").splitToList(info.getIntlSecurityCheckinCode())).stream().map(SecurityCheckin::getScecurityCheckinNum).collect(Collectors.toList())));

                if (StringUtils.isEmpty(info.getInteSecurityCheckinCode()) && StringUtils.isEmpty(info.getIntlSecurityCheckinCode())) {
                    flowContext.addVariable("success", false);
                    flowContext.addVariable("message", "未通过安检口状态检测：未找到可用的安检口");
                    logger.info("未通过安检口状态检测：未找到可用的安检口");
                }
            }

            logger.info(" *** 安检口状态检测完毕 *** ");
        });
    }
}
