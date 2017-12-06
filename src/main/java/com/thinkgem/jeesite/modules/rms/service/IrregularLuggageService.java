/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.IrregularLuggage;
import com.thinkgem.jeesite.modules.rms.dao.IrregularLuggageDao;

/**
 * 不正常行李表Service
 * @author wjp
 * @version 2016-04-11
 */
@Service
@Transactional(readOnly = true)
public class IrregularLuggageService extends CrudService<IrregularLuggageDao, IrregularLuggage> {

	public IrregularLuggage get(String id) {
		return super.get(id);
	}
	
	public List<IrregularLuggage> findList(IrregularLuggage irregularLuggage) {
		return super.findList(irregularLuggage);
	}
	
	public Page<IrregularLuggage> findPage(Page<IrregularLuggage> page, IrregularLuggage irregularLuggage) {
		return super.findPage(page, irregularLuggage);
	}
	
	@Transactional(readOnly = false)
	public void save(IrregularLuggage irregularLuggage) {
		super.save(irregularLuggage);
	}
	
	@Transactional(readOnly = false)
	public void delete(IrregularLuggage irregularLuggage) {
		super.delete(irregularLuggage);
	}
	
}