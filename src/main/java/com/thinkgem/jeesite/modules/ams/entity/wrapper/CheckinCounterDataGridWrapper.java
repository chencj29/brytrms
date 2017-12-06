package com.thinkgem.jeesite.modules.ams.entity.wrapper;

import com.thinkgem.jeesite.modules.rms.entity.CheckingCounterOccupyingInfo;
import org.apache.ibatis.type.Alias;

/**
 * Created by xiaowu on 4/16/16.
 */
@Alias("CheckinCounterDataGridWrapper")
public class CheckinCounterDataGridWrapper extends CheckingCounterOccupyingInfo {
    String id;
    String flightNum;
    String aircraftNum;
    String flightDynamicId;

    @Override
    public String getFlightDynamicId() {
        return flightDynamicId;
    }

    @Override
    public void setFlightDynamicId(String flightDynamicId) {
        this.flightDynamicId = flightDynamicId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public String getAircraftNum() {
        return aircraftNum;
    }

    public void setAircraftNum(String aircraftNum) {
        this.aircraftNum = aircraftNum;
    }
}
