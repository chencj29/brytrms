/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.CheckinCounterBasic;
import com.thinkgem.jeesite.modules.rms.dao.CheckinCounterBasicDao;

/**
 * 值机柜台基础信息表(基础专用)Service
 * @author wjp
 * @version 2017-09-22
 */
@Service
@Transactional(readOnly = true)
public class CheckinCounterBasicService extends CrudService<CheckinCounterBasicDao, CheckinCounterBasic> {

	public CheckinCounterBasic get(String id) {
		return super.get(id);
	}
	
	public List<CheckinCounterBasic> findList(CheckinCounterBasic checkinCounterBasic) {
		return super.findList(checkinCounterBasic);
	}
	
	public Page<CheckinCounterBasic> findPage(Page<CheckinCounterBasic> page, CheckinCounterBasic checkinCounterBasic) {
		return super.findPage(page, checkinCounterBasic);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckinCounterBasic checkinCounterBasic) {
		super.save(checkinCounterBasic);
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckinCounterBasic checkinCounterBasic) {
		super.delete(checkinCounterBasic);
	}
	
}