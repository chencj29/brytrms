package cn.net.metadata.wrapper;

import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;

import java.util.List;

/**
 * Created by Administrator on 2016/10/21.
 */
public class PairAndDynamicCollections {
    List<FlightDynamic> flightDynamics;
    List<FlightPlanPair> flightPairWrappers;

    public PairAndDynamicCollections() {
    }

    public PairAndDynamicCollections(List<FlightDynamic> flightDynamics, List<FlightPlanPair> flightPairWrappers) {
        this.flightDynamics = flightDynamics;
        this.flightPairWrappers = flightPairWrappers;
    }

    public List<FlightDynamic> getFlightDynamics() {
        return flightDynamics;
    }

    public void setFlightDynamics(List<FlightDynamic> flightDynamics) {
        this.flightDynamics = flightDynamics;
    }

    public List<FlightPlanPair> getFlightPairWrappers() {
        return flightPairWrappers;
    }

    public void setFlightPairWrappers(List<FlightPlanPair> flightPairWrappers) {
        this.flightPairWrappers = flightPairWrappers;
    }
}
