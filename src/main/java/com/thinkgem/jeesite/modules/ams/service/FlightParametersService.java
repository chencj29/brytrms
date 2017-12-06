/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ams.dao.FlightParametersDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 航班状态管理Service
 *
 * @author xiaopo
 * @version 2015-12-14
 */
@Service
@Transactional(readOnly = true)
public class FlightParametersService extends CrudService<FlightParametersDao, FlightParameters> {

	public FlightParameters get(String id) {
		return super.get(id);
	}

	public List<FlightParameters> findList(FlightParameters flightParameters) {
		return super.findList(flightParameters);
	}

	public Page<FlightParameters> findPage(Page<FlightParameters> page, FlightParameters flightParameters) {
		return super.findPage(page, flightParameters);
	}

	@Transactional(readOnly = false)
	public void save(FlightParameters flightParameters) {
		super.save(flightParameters);
	}

	@Transactional(readOnly = false)
	public void delete(FlightParameters flightParameters) {
		super.delete(flightParameters);
	}


	public List<FlightParameters> getFlightParametersByRegex(String regex) {
		ImmutableMap map = ImmutableMap.of(
				"DEL_FLAG_NORMAL", FlightParameters.DEL_FLAG_NORMAL,
				"statusNum", regex,
				"full", regex + "00");
		return super.dao.getFlightParametersByRegex(map);
	}

	public List<FlightParameters> listJsons4Type(String type) {
		return super.dao.listJsons4Type(type);
	}
}