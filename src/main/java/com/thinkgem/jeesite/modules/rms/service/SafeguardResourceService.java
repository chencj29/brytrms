/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.SafeguardResource;
import com.thinkgem.jeesite.modules.rms.dao.SafeguardResourceDao;

/**
 * 保障资源表Service
 * @author wjp
 * @version 2016-04-15
 */
@Service
@Transactional(readOnly = true)
public class SafeguardResourceService extends CrudService<SafeguardResourceDao, SafeguardResource> {

	public SafeguardResource get(String id) {
		return super.get(id);
	}
	
	public List<SafeguardResource> findList(SafeguardResource safeguardResource) {
		return super.findList(safeguardResource);
	}
	
	public Page<SafeguardResource> findPage(Page<SafeguardResource> page, SafeguardResource safeguardResource) {
		return super.findPage(page, safeguardResource);
	}
	
	@Transactional(readOnly = false)
	public void save(SafeguardResource safeguardResource) {
		super.save(safeguardResource);
	}
	
	@Transactional(readOnly = false)
	public void delete(SafeguardResource safeguardResource) {
		super.delete(safeguardResource);
	}
	
}