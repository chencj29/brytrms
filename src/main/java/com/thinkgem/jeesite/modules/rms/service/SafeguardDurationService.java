/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.SafeguardDuration;
import com.thinkgem.jeesite.modules.rms.dao.SafeguardDurationDao;

/**
 * 保障时长管理Service
 * @author chrischen
 * @version 2016-03-16
 */
@Service
@Transactional(readOnly = true)
public class SafeguardDurationService extends CrudService<SafeguardDurationDao, SafeguardDuration> {

	public SafeguardDuration get(String id) {
		return super.get(id);
	}
	
	public List<SafeguardDuration> findList(SafeguardDuration safeguardDuration) {
		return super.findList(safeguardDuration);
	}
	
	public Page<SafeguardDuration> findPage(Page<SafeguardDuration> page, SafeguardDuration safeguardDuration) {
		return super.findPage(page, safeguardDuration);
	}
	
	@Transactional(readOnly = false)
	public void save(SafeguardDuration safeguardDuration) {
		super.save(safeguardDuration);
	}
	
	@Transactional(readOnly = false)
	public void delete(SafeguardDuration safeguardDuration) {
		super.delete(safeguardDuration);
	}

	//获取保障总时长
	public Long getLongTime(SafeguardDuration safeguardDuration){
		return dao.getLongTime(safeguardDuration);
	}
}