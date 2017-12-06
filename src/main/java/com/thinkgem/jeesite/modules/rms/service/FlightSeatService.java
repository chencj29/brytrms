/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.FlightSeat;
import com.thinkgem.jeesite.modules.rms.dao.FlightSeatDao;

/**
 * 座位交接单Service
 * @author wjp
 * @version 2016-03-21
 */
@Service
@Transactional(readOnly = true)
public class FlightSeatService extends CrudService<FlightSeatDao, FlightSeat> {

	public FlightSeat get(String id) {
		return super.get(id);
	}
	
	public List<FlightSeat> findList(FlightSeat flightSeat) {
		return super.findList(flightSeat);
	}
	
	public Page<FlightSeat> findPage(Page<FlightSeat> page, FlightSeat flightSeat) {
		return super.findPage(page, flightSeat);
	}
	
	@Transactional(readOnly = false)
	public void save(FlightSeat flightSeat) {
		super.save(flightSeat);
	}
	
	@Transactional(readOnly = false)
	public void delete(FlightSeat flightSeat) {
		super.delete(flightSeat);
	}
	
}