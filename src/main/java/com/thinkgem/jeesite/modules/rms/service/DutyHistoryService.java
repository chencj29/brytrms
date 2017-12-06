/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.DutyHistory;
import com.thinkgem.jeesite.modules.rms.dao.DutyHistoryDao;

/**
 * 配载工作人员值班记录Service
 * @author wjp
 * @version 2016-03-21
 */
@Service
@Transactional(readOnly = true)
public class DutyHistoryService extends CrudService<DutyHistoryDao, DutyHistory> {

	public DutyHistory get(String id) {
		return super.get(id);
	}
	
	public List<DutyHistory> findList(DutyHistory dutyHistory) {
		return super.findList(dutyHistory);
	}
	
	public Page<DutyHistory> findPage(Page<DutyHistory> page, DutyHistory dutyHistory) {
		return super.findPage(page, dutyHistory);
	}
	
	@Transactional(readOnly = false)
	public void save(DutyHistory dutyHistory) {
		super.save(dutyHistory);
	}
	
	@Transactional(readOnly = false)
	public void delete(DutyHistory dutyHistory) {
		super.delete(dutyHistory);
	}

	public List<DutyHistory> findListStatistics(DutyHistory dutyHistory) {
		return dao.findListStatistics(dutyHistory);
	}
	
}