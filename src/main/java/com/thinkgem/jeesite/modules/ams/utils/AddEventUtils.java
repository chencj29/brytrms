package com.thinkgem.jeesite.modules.ams.utils;

import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.event.FlightDynamicEvent;
import com.thinkgem.jeesite.modules.ams.event.SyncTypeEnum;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wjp on 2017/4/14.
 */
public class AddEventUtils {
    public static void addEvent(ArrayList<FlightDynamic> flightDynamics, ArrayList<FlightPlanPair> pairs, SyncTypeEnum syncType, ApplicationContext app){

        Map<String,Object> map = new HashMap<>();
        map.put("dynamics",flightDynamics);
        map.put("pairWrappers",pairs);
        map.put("type", syncType.name());
        FlightDynamicEvent flightDynamicEvent = new FlightDynamicEvent(map);
        app.publishEvent(flightDynamicEvent);
    }
}
