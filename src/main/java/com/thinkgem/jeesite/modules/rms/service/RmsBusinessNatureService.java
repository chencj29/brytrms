/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.RmsBusinessNature;
import com.thinkgem.jeesite.modules.rms.dao.RmsBusinessNatureDao;

/**
 * 岗位业务性质管理Service
 * @author xiaopo
 * @version 2016-05-18
 */
@Service
@Transactional(readOnly = true)
public class RmsBusinessNatureService extends CrudService<RmsBusinessNatureDao, RmsBusinessNature> {

	public RmsBusinessNature get(String id) {
		return super.get(id);
	}
	
	public List<RmsBusinessNature> findList(RmsBusinessNature rmsBusinessNature) {
		return super.findList(rmsBusinessNature);
	}
	
	public Page<RmsBusinessNature> findPage(Page<RmsBusinessNature> page, RmsBusinessNature rmsBusinessNature) {
		return super.findPage(page, rmsBusinessNature);
	}
	
	@Transactional(readOnly = false)
	public void save(RmsBusinessNature rmsBusinessNature) {
		super.save(rmsBusinessNature);
	}
	
	@Transactional(readOnly = false)
	public void delete(RmsBusinessNature rmsBusinessNature) {
		super.delete(rmsBusinessNature);
	}
	
}