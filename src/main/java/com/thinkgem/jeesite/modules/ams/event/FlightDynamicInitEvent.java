package com.thinkgem.jeesite.modules.ams.event;

import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import org.springframework.context.ApplicationEvent;

/**
 * 航班初始化事件,监听该事件用于调用[资源管理系统]对该航班进行初始化,登机口设置等.
 * Created by xiaopo on 16/1/13.
 */
public class FlightDynamicInitEvent extends ApplicationEvent {

    public FlightDynamicInitEvent(FlightDynamic flightDynamic) {
        super(flightDynamic);
    }

}
