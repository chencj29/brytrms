/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.EmergencyPlan;
import com.thinkgem.jeesite.modules.rms.dao.EmergencyPlanDao;

/**
 * 应急救援方案Service
 * @author wjp
 * @version 2016-08-25
 */
@Service
@Transactional(readOnly = true)
public class EmergencyPlanService extends CrudService<EmergencyPlanDao, EmergencyPlan> {

	public EmergencyPlan get(String id) {
		return super.get(id);
	}
	
	public List<EmergencyPlan> findList(EmergencyPlan emergencyPlan) {
		return super.findList(emergencyPlan);
	}
	
	public Page<EmergencyPlan> findPage(Page<EmergencyPlan> page, EmergencyPlan emergencyPlan) {
		return super.findPage(page, emergencyPlan);
	}
	
	@Transactional(readOnly = false)
	public void save(EmergencyPlan emergencyPlan) {
		super.save(emergencyPlan);
	}
	
	@Transactional(readOnly = false)
	public void delete(EmergencyPlan emergencyPlan) {
		super.delete(emergencyPlan);
	}
	
}