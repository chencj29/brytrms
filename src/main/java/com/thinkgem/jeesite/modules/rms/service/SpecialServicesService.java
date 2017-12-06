/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.SpecialServices;
import com.thinkgem.jeesite.modules.rms.dao.SpecialServicesDao;

/**
 * 特殊服务记录表Service
 * @author wjp
 * @version 2016-03-14
 */
@Service
@Transactional(readOnly = true)
public class SpecialServicesService extends CrudService<SpecialServicesDao, SpecialServices> {

	public SpecialServices get(String id) {
		return super.get(id);
	}
	
	public List<SpecialServices> findList(SpecialServices specialServices) {
		specialServices.getSqlMap().put("dsfn",dataScopeFilterSpecial(specialServices.getCurrentUser()));
		return super.findList(specialServices);
	}
	
	public Page<SpecialServices> findPage(Page<SpecialServices> page, SpecialServices specialServices) {
		return super.findPage(page, specialServices);
	}
	
	@Transactional(readOnly = false)
	public void save(SpecialServices specialServices) {
		super.save(specialServices);
	}
	
	@Transactional(readOnly = false)
	public void delete(SpecialServices specialServices) {
		super.delete(specialServices);
	}

	public List<SpecialServices> findSpecialServicesList(SpecialServices specialServices) {
		return dao.getSpecialServicesList(specialServices);
	}
}