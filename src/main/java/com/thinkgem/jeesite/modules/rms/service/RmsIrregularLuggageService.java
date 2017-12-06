/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.RmsIrregularLuggage;
import com.thinkgem.jeesite.modules.rms.dao.RmsIrregularLuggageDao;

/**
 * 不正常行李表Service
 * @author wjp
 * @version 2016-02-03
 */
@Service
@Transactional(readOnly = true)
public class RmsIrregularLuggageService extends CrudService<RmsIrregularLuggageDao, RmsIrregularLuggage> {

	public RmsIrregularLuggage get(String id) {
		return super.get(id);
	}
	
	public List<RmsIrregularLuggage> findList(RmsIrregularLuggage rmsIrregularLuggage) {
		return super.findList(rmsIrregularLuggage);
	}
	
	public Page<RmsIrregularLuggage> findPage(Page<RmsIrregularLuggage> page, RmsIrregularLuggage rmsIrregularLuggage) {
		return super.findPage(page, rmsIrregularLuggage);
	}
	
	@Transactional(readOnly = false)
	public void save(RmsIrregularLuggage rmsIrregularLuggage) {
		super.save(rmsIrregularLuggage);
	}
	
	@Transactional(readOnly = false)
	public void delete(RmsIrregularLuggage rmsIrregularLuggage) {
		super.delete(rmsIrregularLuggage);
	}
	
}