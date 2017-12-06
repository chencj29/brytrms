/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.FlightDynamicCheckin;
import com.thinkgem.jeesite.modules.rms.dao.FlightDynamicCheckinDao;

/**
 * 值机柜台员工分配Service
 * @author xiaopo
 * @version 2016-05-24
 */
@Service
@Transactional(readOnly = true)
public class FlightDynamicCheckinService extends CrudService<FlightDynamicCheckinDao, FlightDynamicCheckin> {

	public FlightDynamicCheckin get(String id) {
		return super.get(id);
	}
	
	public List<FlightDynamicCheckin> findList(FlightDynamicCheckin flightDynamicCheckin) {
		return super.findList(flightDynamicCheckin);
	}
	
	public Page<FlightDynamicCheckin> findPage(Page<FlightDynamicCheckin> page, FlightDynamicCheckin flightDynamicCheckin) {
		return super.findPage(page, flightDynamicCheckin);
	}
	
	@Transactional(readOnly = false)
	public void save(FlightDynamicCheckin flightDynamicCheckin) {
		super.save(flightDynamicCheckin);
	}
	
	@Transactional(readOnly = false)
	public void delete(FlightDynamicCheckin flightDynamicCheckin) {
		super.delete(flightDynamicCheckin);
	}

	public FlightDynamicCheckin get(FlightDynamicCheckin dynamicCheckin) {
		return super.get(dynamicCheckin);
	}
}