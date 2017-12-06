/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.FlightCompany;
import com.thinkgem.jeesite.modules.rms.dao.FlightCompanyDao;

/**
 * 航空公司联系信息Service
 * @author wjp
 * @version 2016-03-21
 */
@Service
@Transactional(readOnly = true)
public class FlightCompanyService extends CrudService<FlightCompanyDao, FlightCompany> {

	public FlightCompany get(String id) {
		return super.get(id);
	}
	
	public List<FlightCompany> findList(FlightCompany flightCompany) {
		return super.findList(flightCompany);
	}
	
	public Page<FlightCompany> findPage(Page<FlightCompany> page, FlightCompany flightCompany) {
		return super.findPage(page, flightCompany);
	}
	
	@Transactional(readOnly = false)
	public void save(FlightCompany flightCompany) {
		super.save(flightCompany);
	}
	
	@Transactional(readOnly = false)
	public void delete(FlightCompany flightCompany) {
		super.delete(flightCompany);
	}
	
}