/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.DockOutList;
import com.thinkgem.jeesite.modules.rms.dao.DockOutListDao;

/**
 * 航班出仓机单信息Service
 * @author wjp
 * @version 2016-03-21
 */
@Service
@Transactional(readOnly = true)
public class DockOutListService extends CrudService<DockOutListDao, DockOutList> {

	public DockOutList get(String id) {
		return super.get(id);
	}
	
	public List<DockOutList> findList(DockOutList dockOutList) {
		return super.findList(dockOutList);
	}
	
	public Page<DockOutList> findPage(Page<DockOutList> page, DockOutList dockOutList) {
		return super.findPage(page, dockOutList);
	}
	
	@Transactional(readOnly = false)
	public void save(DockOutList dockOutList) {
		super.save(dockOutList);
	}
	
	@Transactional(readOnly = false)
	public void delete(DockOutList dockOutList) {
		super.delete(dockOutList);
	}
	
}