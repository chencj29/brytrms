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
public class FlightDynamicStatusCancel implements IFlightDynamicStatus {

	private final static String SQL = COMMON_SQL + " where id = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public String updateStatusData(FlightDynamic dynamic) {

		jdbcTemplate.update(SQL, dynamic.getStatus(), dynamic.getStatusName(), dynamic.getId());

		return "success";
	}
}
