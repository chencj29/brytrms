/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.CheckinCounter;
import com.thinkgem.jeesite.modules.rms.dao.CheckinCounterDao;

/**
 * 值机柜台基础信息表Service
 * @author wjp
 * @version 2016-03-09
 */
@Service
@Transactional(readOnly = true)
public class CheckinCounterService extends CrudService<CheckinCounterDao, CheckinCounter> {

	public CheckinCounter get(String id) {
		return super.get(id);
	}
	
	public List<CheckinCounter> findList(CheckinCounter checkinCounter) {
		return super.findList(checkinCounter);
	}
	
	public Page<CheckinCounter> findPage(Page<CheckinCounter> page, CheckinCounter checkinCounter) {
		return super.findPage(page, checkinCounter);
	}
	
	@Transactional(readOnly = false)
	public void save(CheckinCounter checkinCounter) {
		super.save(checkinCounter);
	}
	
	@Transactional(readOnly = false)
	public void delete(CheckinCounter checkinCounter) {
		super.delete(checkinCounter);
	}
	
}