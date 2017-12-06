package cn.net.metadata.rule.action.dist.gate;

import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.entity.AirstandAirparameters;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;
import com.thinkgem.jeesite.modules.rms.service.AircraftStandService;
import com.thinkgem.jeesite.modules.rms.service.AirstandAirparametersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 机位与机型关系匹配
 * Created by xiaowu on 3/14/16.
 */
@Component
public class GateWithAircraftTypeMatchAction implements FlowAction {

    static final Logger logger = LoggerFactory.getLogger(GateWithAircraftTypeMatchAction.class);

    @Autowired
    AircraftStandService aircraftStandService;
    @Autowired
    FlightDynamicService flightDynamicService;
    @Autowired
    AirstandAirparametersService airstandAirparametersService;


    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {
        logger.info(" *** 开始机位与机型关系匹配检测 *** ");
        flowContext.getWorkingMemory().getAllFacts().stream().filter(obj -> obj instanceof FlightPlanPair).findFirst().ifPresent(obj -> {
            FlightPlanPair flightPlanPair = (FlightPlanPair) obj;
            if (StringUtils.isNotBlank(flightPlanPair.getPlaceNum()) && StringUtils.isNotBlank(flightPlanPair.getAircraftTypeCode())) {
                AirstandAirparameters modelList = airstandAirparametersService.findByAircraftModel(flightPlanPair.getAircraftTypeCode());
                if (null == modelList || org.apache.commons.lang3.StringUtils.isBlank(modelList.getAircraftStandNum())) {
                    logger.info("当前机型{}没有找到与之对应的机位列表，舍弃", flightPlanPair.getAircraftTypeCode());
                    flightPlanPair.setPlaceNum("");
                    flowContext.addVariable("success", false);
                    flowContext.addVariable("message", "未通过机位与机型关系匹配检测：当前航班的机型没有找到与之对应的机位列表！");
                } else {
                    String aircraftStandNums = modelList.getAircraftStandNum();
                    logger.info("当前机型{}对应的机位列表为{}", flightPlanPair.getAircraftTypeCode(), aircraftStandNums);
                    List<String> gateList4Type = Lists.newArrayList(Splitter.on(",").split(aircraftStandNums));
                    String placeNum = Joiner.on(",").join(Splitter.on(",").splitToList(flightPlanPair.getPlaceNum()).stream().filter(gateList4Type::contains).collect(Collectors.toList()));
                    if (StringUtils.isEmpty(placeNum)) {
                        flightPlanPair.setPlaceNum("");
                        flowContext.addVariable("success", false);
                        flowContext.addVariable("message", "未通过机位与机型关系匹配检测：当前航班的机型不适用此机位！");
                    } else flightPlanPair.setPlaceNum(placeNum);
                }
            } else {
                logger.info("机位号或机型编号不存在，舍弃！{} - {}", flightPlanPair.getPlaceNum(), flightPlanPair.getAircraftTypeCode());
                flightPlanPair.setPlaceNum("");
                flowContext.addVariable("success", false);
                flowContext.addVariable("message", "未通过机位与机型关系匹配检测：机位号或机型编号不存在");
            }
        });
    }
}
