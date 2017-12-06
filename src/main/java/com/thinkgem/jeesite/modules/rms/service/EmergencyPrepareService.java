/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.EmergencyPrepare;
import com.thinkgem.jeesite.modules.rms.dao.EmergencyPrepareDao;

/**
 * 应急救援预案Service
 * @author wjp
 * @version 2016-03-21
 */
@Service
@Transactional(readOnly = true)
public class EmergencyPrepareService extends CrudService<EmergencyPrepareDao, EmergencyPrepare> {

	public EmergencyPrepare get(String id) {
		return super.get(id);
	}
	
	public List<EmergencyPrepare> findList(EmergencyPrepare emergencyPrepare) {
		return super.findList(emergencyPrepare);
	}
	
	public Page<EmergencyPrepare> findPage(Page<EmergencyPrepare> page, EmergencyPrepare emergencyPrepare) {
		return super.findPage(page, emergencyPrepare);
	}
	
	@Transactional(readOnly = false)
	public void save(EmergencyPrepare emergencyPrepare) {
		super.save(emergencyPrepare);
	}
	
	@Transactional(readOnly = false)
	public void delete(EmergencyPrepare emergencyPrepare) {
		super.delete(emergencyPrepare);
	}
	
}