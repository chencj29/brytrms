package com.thinkgem.jeesite.modules.ams.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * Created by wjp on 2017/4/14.
 */
public class FlightDynamicEvent extends ApplicationEvent {
    public FlightDynamicEvent(Map<String,Object> map) {
        super(map);
    }
}
