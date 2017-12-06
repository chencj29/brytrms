package com.thinkgem.jeesite.modules.ams.event.listener;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.event.FlightDynamicEvent;
import com.thinkgem.jeesite.modules.ams.event.SyncTypeEnum;
import com.thinkgem.jeesite.modules.ams.utils.MsgWebServiceUtils;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by wjp on 2017/4/14.
 */
@Component
public class FlightDynamicEventListener implements ApplicationListener<FlightDynamicEvent> {

    @Async
    public void onApplicationEvent(FlightDynamicEvent event) {
        Map<String, Object> map = (Map) event.getSource();
        ArrayList<FlightDynamic> flightDynamics = (ArrayList<FlightDynamic>) map.get("dynamics");
        ArrayList<FlightPlanPair> pairs = (ArrayList<FlightPlanPair>) map.get("pairWrappers");
        String syncType = (String) map.get("type");

        if(StringUtils.isBlank(syncType)) syncType = SyncTypeEnum.OTHER.name();
        // webservice发送航班动态给消息中间件

        if (Boolean.parseBoolean(Global.getConfig("syncMiddle")))
            MsgWebServiceUtils.publishFlightDynamic("sync_ams_"+syncType, flightDynamics, pairs);
    }
}
