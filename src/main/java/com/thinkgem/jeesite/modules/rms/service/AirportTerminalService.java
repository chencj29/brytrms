/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.AirportTerminal;
import com.thinkgem.jeesite.modules.ams.dao.AirportTerminalDao;

/**
 * 航站楼信息Service
 * @author hxx
 * @version 2016-01-26
 */
@Service
@Transactional(readOnly = true)
public class AirportTerminalService extends CrudService<AirportTerminalDao, AirportTerminal> {

	public AirportTerminal get(String id) {
		return super.get(id);
	}
	
	public List<AirportTerminal> findList(AirportTerminal airportTerminal) {
		return super.findList(airportTerminal);
	}
	
	public Page<AirportTerminal> findPage(Page<AirportTerminal> page, AirportTerminal airportTerminal) {
		return super.findPage(page, airportTerminal);
	}
	
	@Transactional(readOnly = false)
	public void save(AirportTerminal airportTerminal) {
		super.save(airportTerminal);
	}
	
	@Transactional(readOnly = false)
	public void delete(AirportTerminal airportTerminal) {
		super.delete(airportTerminal);
	}
	
}