package cn.net.metadata.rule.action.dist.departureHall;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.thinkgem.jeesite.modules.rms.entity.DepartureHall;
import com.thinkgem.jeesite.modules.rms.entity.DepartureHallOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.DepartureHallService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * 候机厅状态检测
 * Created by xiaowu on 3/28/16.
 */
@Component
public class DepartureHallStatusDetectingAction implements FlowAction {

    static final Logger logger = LoggerFactory.getLogger(DepartureHallStatusDetectingAction.class);

    @Autowired
    DepartureHallService departureHallService;

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstances) {
        flowContext.getWorkingMemory().getAllFacts().stream().filter(obj -> obj instanceof DepartureHallOccupyingInfo).findFirst().ifPresent(obj -> {
            DepartureHallOccupyingInfo info = (DepartureHallOccupyingInfo) obj;
            logger.info(" *** 开始候机厅状态检测 *** ");
            if (StringUtils.isEmpty(info.getInteDepartureHallCode()) && StringUtils.isEmpty(info.getIntlDepartureHallCode())) {
                flowContext.addVariable("success", false);
                flowContext.addVariable("message", "没有找到候机厅编号");
                logger.info("没有找到候机厅编号！");
            } else {
                if (StringUtils.isNotEmpty(info.getInteDepartureHallCode()))
                    info.setInteDepartureHallCode(Joiner.on(",").join(departureHallService.checkStatusByDepartureHallNum(Splitter.on(",").splitToList(info.getInteDepartureHallCode())).stream().map(DepartureHall::getDepartureHallNum).collect(Collectors.toList())));

                if (StringUtils.isNotEmpty(info.getIntlDepartureHallCode()))
                    info.setIntlDepartureHallCode(Joiner.on(",").join(departureHallService.checkStatusByDepartureHallNum(Splitter.on(",").splitToList(info.getIntlDepartureHallCode())).stream().map(DepartureHall::getDepartureHallNum).collect(Collectors.toList())));

                if (StringUtils.isEmpty(info.getInteDepartureHallCode()) && StringUtils.isEmpty(info.getIntlDepartureHallCode())) {
                    flowContext.addVariable("success", false);
                    flowContext.addVariable("message", "未通过候机厅状态检测：未找到可用的候机厅");
                    logger.info("未通过候机厅状态检测：未找到可用的候机厅");
                }
            }

            logger.info(" *** 候机厅状态检测完毕 *** ");
        });
    }
}
