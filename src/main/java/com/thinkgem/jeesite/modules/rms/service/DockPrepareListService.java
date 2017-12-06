/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.DockPrepareList;
import com.thinkgem.jeesite.modules.rms.dao.DockPrepareListDao;

/**
 * 航班预配单信息Service
 * @author wjp
 * @version 2016-03-21
 */
@Service
@Transactional(readOnly = true)
public class DockPrepareListService extends CrudService<DockPrepareListDao, DockPrepareList> {

	public DockPrepareList get(String id) {
		return super.get(id);
	}
	
	public List<DockPrepareList> findList(DockPrepareList dockPrepareList) {
		return super.findList(dockPrepareList);
	}
	
	public Page<DockPrepareList> findPage(Page<DockPrepareList> page, DockPrepareList dockPrepareList) {
		return super.findPage(page, dockPrepareList);
	}
	
	@Transactional(readOnly = false)
	public void save(DockPrepareList dockPrepareList) {
		super.save(dockPrepareList);
	}
	
	@Transactional(readOnly = false)
	public void delete(DockPrepareList dockPrepareList) {
		super.delete(dockPrepareList);
	}
	
}