/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.IrregularServices;
import com.thinkgem.jeesite.modules.rms.dao.IrregularServicesDao;

/**
 * 不正常服务记录Service
 * @author wjp
 * @version 2016-03-19
 */
@Service
@Transactional(readOnly = true)
public class IrregularServicesService extends CrudService<IrregularServicesDao, IrregularServices> {

	public IrregularServices get(String id) {
		return super.get(id);
	}
	
	public List<IrregularServices> findList(IrregularServices irregularServices) {
		irregularServices.getSqlMap().put("dsfn",dataScopeFilterSpecial(irregularServices.getCurrentUser()));
		return super.findList(irregularServices);
	}
	
	public Page<IrregularServices> findPage(Page<IrregularServices> page, IrregularServices irregularServices) {
		return super.findPage(page, irregularServices);
	}
	
	@Transactional(readOnly = false)
	public void save(IrregularServices irregularServices) {
		super.save(irregularServices);
	}
	
	@Transactional(readOnly = false)
	public void delete(IrregularServices irregularServices) {
		super.delete(irregularServices);
	}
	
}