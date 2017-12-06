package com.thinkgem.jeesite.modules.ams.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiaopo on 16/1/27.
 */
public class FlightPlanWrapper implements Serializable {

	private static final long serialVersionUID = 7460732421466499408L;

	private List<FlightPlan> flightPlanList;


	public List<FlightPlan> getFlightPlanList() {
		return flightPlanList;
	}

	public void setFlightPlanList(List<FlightPlan> flightPlanList) {
		this.flightPlanList = flightPlanList;
	}
}
