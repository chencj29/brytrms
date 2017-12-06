package cn.net.metadata.webservice.dynamic.impl;

import cn.net.metadata.webservice.dynamic.IFlightDynamicStatus;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author xiaopo
 * @date 16/4/21
 */
@Service
public class FlightDynamicStatusDelete implements IFlightDynamicStatus {

	private final static String SQL = "DELETE ams_flight_dynamic WHERE id = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public String updateStatusData(FlightDynamic dynamic) {

		jdbcTemplate.update(SQL, dynamic.getId());
		return "success";
	}
}
