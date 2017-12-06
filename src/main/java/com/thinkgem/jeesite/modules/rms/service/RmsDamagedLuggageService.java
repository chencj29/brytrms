/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.RmsDamagedLuggage;
import com.thinkgem.jeesite.modules.rms.dao.RmsDamagedLuggageDao;

/**
 * 损坏行李表Service
 * @author wjp
 * @version 2016-02-03
 */
@Service
@Transactional(readOnly = true)
public class RmsDamagedLuggageService extends CrudService<RmsDamagedLuggageDao, RmsDamagedLuggage> {

	public RmsDamagedLuggage get(String id) {
		return super.get(id);
	}
	
	public List<RmsDamagedLuggage> findList(RmsDamagedLuggage rmsDamagedLuggage) {
		return super.findList(rmsDamagedLuggage);
	}
	
	public Page<RmsDamagedLuggage> findPage(Page<RmsDamagedLuggage> page, RmsDamagedLuggage rmsDamagedLuggage) {
		return super.findPage(page, rmsDamagedLuggage);
	}
	
	@Transactional(readOnly = false)
	public void save(RmsDamagedLuggage rmsDamagedLuggage) {
		super.save(rmsDamagedLuggage);
	}
	
	@Transactional(readOnly = false)
	public void delete(RmsDamagedLuggage rmsDamagedLuggage) {
		super.delete(rmsDamagedLuggage);
	}
	
}