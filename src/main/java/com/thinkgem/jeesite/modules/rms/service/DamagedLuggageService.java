/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.DamagedLuggage;
import com.thinkgem.jeesite.modules.rms.dao.DamagedLuggageDao;

/**
 * 损坏行李表Service
 * @author wjp
 * @version 2016-03-14
 */
@Service
@Transactional(readOnly = true)
public class DamagedLuggageService extends CrudService<DamagedLuggageDao, DamagedLuggage> {

	public DamagedLuggage get(String id) {
		return super.get(id);
	}
	
	public List<DamagedLuggage> findList(DamagedLuggage damagedLuggage) {
		return super.findList(damagedLuggage);
	}
	
	public Page<DamagedLuggage> findPage(Page<DamagedLuggage> page, DamagedLuggage damagedLuggage) {
		return super.findPage(page, damagedLuggage);
	}
	
	@Transactional(readOnly = false)
	public void save(DamagedLuggage damagedLuggage) {
		super.save(damagedLuggage);
	}
	
	@Transactional(readOnly = false)
	public void delete(DamagedLuggage damagedLuggage) {
		super.delete(damagedLuggage);
	}
	
}