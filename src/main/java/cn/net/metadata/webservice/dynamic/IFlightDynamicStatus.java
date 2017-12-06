package cn.net.metadata.webservice.dynamic;

import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;

/**
 * @author xiaopo
 * @date 16/4/21
 */
public interface IFlightDynamicStatus {

	final static String COMMON_SQL = "update ams_flight_dynamic set status = ? ,status_name = ? ";

	String updateStatusData(FlightDynamic dynamic);

}
