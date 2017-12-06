package com.thinkgem.jeesite.modules.ams.event.listener;

import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.event.FlightDynamicInitEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by xiaopo on 16/1/13.
 */
@Deprecated
@Component
public class FlightDynamicInitEventListener implements ApplicationListener<FlightDynamicInitEvent> {

    @Async
    @Override
    public void onApplicationEvent(FlightDynamicInitEvent flightDynamicInitEvent) {
        FlightDynamic dynamic = (FlightDynamic) flightDynamicInitEvent.getSource();

    }
}
