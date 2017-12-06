/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.ResourceCategory;
import com.thinkgem.jeesite.modules.rms.dao.ResourceCategoryDao;

/**
 * 资源类别信息Service
 * @author wjp
 * @version 2016-03-24
 */
@Service
@Transactional(readOnly = true)
public class ResourceCategoryService extends CrudService<ResourceCategoryDao, ResourceCategory> {

	public ResourceCategory get(String id) {
		return super.get(id);
	}
	
	public List<ResourceCategory> findList(ResourceCategory resourceCategory) {
		return super.findList(resourceCategory);
	}
	
	public Page<ResourceCategory> findPage(Page<ResourceCategory> page, ResourceCategory resourceCategory) {
		return super.findPage(page, resourceCategory);
	}
	
	@Transactional(readOnly = false)
	public void save(ResourceCategory resourceCategory) {
		super.save(resourceCategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(ResourceCategory resourceCategory) {
		super.delete(resourceCategory);
	}
	
}