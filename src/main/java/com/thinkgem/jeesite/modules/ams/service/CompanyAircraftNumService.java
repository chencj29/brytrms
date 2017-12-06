/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ams.entity.CompanyAircraftNum;
import com.thinkgem.jeesite.modules.ams.dao.CompanyAircraftNumDao;

/**
 * 公司机号管理Service
 * @author xiaopo
 * @version 2015-12-15
 */
@Service
@Transactional(readOnly = true)
public class CompanyAircraftNumService extends CrudService<CompanyAircraftNumDao, CompanyAircraftNum> {

	public CompanyAircraftNum get(String id) {
		return super.get(id);
	}
	
	public List<CompanyAircraftNum> findList(CompanyAircraftNum companyAircraftNum) {
		return super.findList(companyAircraftNum);
	}
	
	public Page<CompanyAircraftNum> findPage(Page<CompanyAircraftNum> page, CompanyAircraftNum companyAircraftNum) {
		return super.findPage(page, companyAircraftNum);
	}
	
	@Transactional(readOnly = false)
	public void save(CompanyAircraftNum companyAircraftNum) {
		super.save(companyAircraftNum);
	}
	
	@Transactional(readOnly = false)
	public void delete(CompanyAircraftNum companyAircraftNum) {
		super.delete(companyAircraftNum);
	}
	
}