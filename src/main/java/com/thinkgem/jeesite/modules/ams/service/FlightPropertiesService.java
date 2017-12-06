/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ams.dao.FlightPropertiesDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 航班属性管理Service
 * @author xiaopo
 * @version 2015-12-14
 */
@Service
@Transactional(readOnly = true)
public class FlightPropertiesService extends CrudService<FlightPropertiesDao, FlightProperties> {

	public FlightProperties get(String id) {
		return super.get(id);
	}
	
	public List<FlightProperties> findList(FlightProperties flightProperties) {
		return super.findList(flightProperties);
	}
	
	public Page<FlightProperties> findPage(Page<FlightProperties> page, FlightProperties flightProperties) {
		return super.findPage(page, flightProperties);
	}
	
	@Transactional(readOnly = false)
	public void save(FlightProperties flightProperties) {
		super.save(flightProperties);
	}
	
	@Transactional(readOnly = false)
	public void delete(FlightProperties flightProperties) {
		super.delete(flightProperties);
	}
}