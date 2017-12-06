package com.thinkgem.jeesite.modules.ams.utils;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.ams.dao.FlightParametersDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightParameters;

import java.util.List;

/**
 * Created by xiaopo on 16/1/27.
 */
public class FlightParametersUtils {

	public static final String CACHE_FLIGHT_PARAMETERS = "flightParameters";
	public static final String CACHE_FLIGHT_PARAMETERS_LIST = "flightParametersList";
	private static FlightParametersDao flightParametersDao = SpringContextHolder.getBean(FlightParametersDao.class);


	//
	public static List<FlightParameters> getFlightParametersByType(String type) {
		List<FlightParameters> flightParameterses = null;
		flightParameterses = (List<FlightParameters>) CacheUtils.get(CACHE_FLIGHT_PARAMETERS, CACHE_FLIGHT_PARAMETERS_LIST);
		if (flightParameterses == null) {
			FlightParameters parameters = new FlightParameters();
			parameters.setStatusCode(type);
			flightParameterses = flightParametersDao.findList(parameters);
			CacheUtils.put(CACHE_FLIGHT_PARAMETERS, CACHE_FLIGHT_PARAMETERS_LIST, flightParameterses);
		}
		return flightParameterses;
	}


}
