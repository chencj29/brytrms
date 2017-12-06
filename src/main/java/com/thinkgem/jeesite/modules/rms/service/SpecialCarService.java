/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.SpecialCar;
import com.thinkgem.jeesite.modules.rms.dao.SpecialCarDao;

/**
 * 特殊车辆基础信息Service
 * @author wjp
 * @version 2016-03-09
 */
@Service
@Transactional(readOnly = true)
public class SpecialCarService extends CrudService<SpecialCarDao, SpecialCar> {

	public SpecialCar get(String id) {
		return super.get(id);
	}
	
	public List<SpecialCar> findList(SpecialCar specialCar) {
		return super.findList(specialCar);
	}
	
	public Page<SpecialCar> findPage(Page<SpecialCar> page, SpecialCar specialCar) {
		return super.findPage(page, specialCar);
	}
	
	@Transactional(readOnly = false)
	public void save(SpecialCar specialCar) {
		super.save(specialCar);
	}
	
	@Transactional(readOnly = false)
	public void delete(SpecialCar specialCar) {
		super.delete(specialCar);
	}
	
}