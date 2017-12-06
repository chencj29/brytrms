package cn.net.metadata.webservice.dynamic.impl;

import cn.net.metadata.webservice.dynamic.IFlightDynamicStatus;
import com.thinkgem.jeesite.modules.ams.dao.FlightDynamicDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author xiaopo
 * @date 16/4/21
 */
@Service
public class FlightDynamicStatusModify implements IFlightDynamicStatus {

	@Autowired
	private FlightDynamicDao dynamicDao;

	@Override
	public String updateStatusData(FlightDynamic dynamic) {

		com.thinkgem.jeesite.modules.ams.entity.FlightDynamic dynamictemp = new com.thinkgem.jeesite.modules.ams.entity.FlightDynamic();
		BeanUtils.copyProperties(dynamic, dynamictemp);
		dynamicDao.update(dynamictemp);

		return "success";
	}
}
