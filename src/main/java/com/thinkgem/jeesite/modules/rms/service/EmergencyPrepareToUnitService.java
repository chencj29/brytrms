/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.EmergencyPrepareToUnit;
import com.thinkgem.jeesite.modules.rms.dao.EmergencyPrepareToUnitDao;

/**
 * 应急救援预案救援单位关联表Service
 * @author wjp
 * @version 2016-04-08
 */
@Service
@Transactional(readOnly = true)
public class EmergencyPrepareToUnitService extends CrudService<EmergencyPrepareToUnitDao, EmergencyPrepareToUnit> {

	public EmergencyPrepareToUnit get(String id) {
		return super.get(id);
	}
	
	public List<EmergencyPrepareToUnit> findList(EmergencyPrepareToUnit emergencyPrepareToUnit) {
		return super.findList(emergencyPrepareToUnit);
	}

	/**
	 * 获取应急救援预案下的所有救援单位id
	 * @param epid rmsEmergencyPrepareId 应急救援预案ID
	 * @return
     */
	public List<Map> findByEUIds(String epid) {
		return dao.findByEUNames(epid);
	}
	
	public Page<EmergencyPrepareToUnit> findPage(Page<EmergencyPrepareToUnit> page, EmergencyPrepareToUnit emergencyPrepareToUnit) {
		return super.findPage(page, emergencyPrepareToUnit);
	}
	
	@Transactional(readOnly = false)
	public void save(EmergencyPrepareToUnit emergencyPrepareToUnit) {
		super.save(emergencyPrepareToUnit);
	}

	/**
	 * 批量保存应急救援预案与救援单位关联
	 * @param epid  rmsEmergencyPrepareId 应急救援预案ID
	 * @param euids 多个用逗号分割的rmsEmergencyUnitId 救援单位ID
     */
	@Transactional(readOnly = false)
	public void batchSave(String epid, String euids) {
		String[] euid =StringUtils.split(euids);
		dao.EPDelete(epid);
		for(String uid:euid){
			EmergencyPrepareToUnit eptu = new EmergencyPrepareToUnit(epid,uid);
			super.save(eptu);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(EmergencyPrepareToUnit emergencyPrepareToUnit) {
		super.delete(emergencyPrepareToUnit);
	}
	
}