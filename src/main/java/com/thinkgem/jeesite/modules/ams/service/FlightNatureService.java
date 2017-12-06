/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ams.dao.FlightNatureDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightNature;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 航班性质管理Service
 * @author xiaopo
 * @version 2015-12-14
 */
@Service
@Transactional(readOnly = true)
public class FlightNatureService extends CrudService<FlightNatureDao, FlightNature> {

	public FlightNature get(String id) {
		return super.get(id);
	}
	
	public List<FlightNature> findList(FlightNature flightNature) {
		return super.findList(flightNature);
	}
	
	public Page<FlightNature> findPage(Page<FlightNature> page, FlightNature flightNature) {
		return super.findPage(page, flightNature);
	}
	
	@Transactional(readOnly = false)
	public void save(FlightNature flightNature) {
		super.save(flightNature);
	}
	
	@Transactional(readOnly = false)
	public void delete(FlightNature flightNature) {
		super.delete(flightNature);
	}
}