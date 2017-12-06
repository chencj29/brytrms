/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.ServiceProvider;
import com.thinkgem.jeesite.modules.rms.dao.ServiceProviderDao;

/**
 * 服务提供者表Service
 * @author wjp
 * @version 2016-03-09
 */
@Service
@Transactional(readOnly = true)
public class ServiceProviderService extends CrudService<ServiceProviderDao, ServiceProvider> {

	public ServiceProvider get(String id) {
		return super.get(id);
	}
	
	public List<ServiceProvider> findList(ServiceProvider serviceProvider) {
		return super.findList(serviceProvider);
	}
	
	public Page<ServiceProvider> findPage(Page<ServiceProvider> page, ServiceProvider serviceProvider) {
		return super.findPage(page, serviceProvider);
	}
	
	@Transactional(readOnly = false)
	public void save(ServiceProvider serviceProvider) {
		super.save(serviceProvider);
	}
	
	@Transactional(readOnly = false)
	public void delete(ServiceProvider serviceProvider) {
		super.delete(serviceProvider);
	}
	
}