/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ams.entity.FlyTime;
import com.thinkgem.jeesite.modules.ams.dao.FlyTimeDao;

/**
 * 飞越时间管理Service
 * @author chrischen
 * @version 2016-02-04
 */
@Service
@Transactional(readOnly = true)
public class FlyTimeService extends CrudService<FlyTimeDao, FlyTime> {

	public FlyTime get(String id) {
		return super.get(id);
	}
	
	public List<FlyTime> findList(FlyTime flyTime) {
		return super.findList(flyTime);
	}
	
	public Page<FlyTime> findPage(Page<FlyTime> page, FlyTime flyTime) {
		return super.findPage(page, flyTime);
	}
	
	@Transactional(readOnly = false)
	public void save(FlyTime flyTime) {
		super.save(flyTime);
	}
	
	@Transactional(readOnly = false)
	public void delete(FlyTime flyTime) {
		super.delete(flyTime);
	}
	
}