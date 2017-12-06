/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.SafegusardResource;
import com.thinkgem.jeesite.modules.rms.dao.SafegusardResourceDao;

/**
 * 保障资源表Service
 * @author wjp
 * @version 2016-04-15
 */
@Service
@Transactional(readOnly = true)
public class SafegusardResourceService extends CrudService<SafegusardResourceDao, SafegusardResource> {

	public SafegusardResource get(String id) {
		return super.get(id);
	}
	
	public List<SafegusardResource> findList(SafegusardResource safegusardResource) {
		return super.findList(safegusardResource);
	}
	
	public Page<SafegusardResource> findPage(Page<SafegusardResource> page, SafegusardResource safegusardResource) {
		return super.findPage(page, safegusardResource);
	}
	
	@Transactional(readOnly = false)
	public void save(SafegusardResource safegusardResource) {
		super.save(safegusardResource);
	}
	
	@Transactional(readOnly = false)
	public void delete(SafegusardResource safegusardResource) {
		super.delete(safegusardResource);
	}
	
}