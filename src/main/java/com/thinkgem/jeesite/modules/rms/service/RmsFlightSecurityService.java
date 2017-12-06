/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightSecurity;
import com.thinkgem.jeesite.modules.rms.dao.RmsFlightSecurityDao;

/**
 * 安检旅客信息Service
 * @author dingshuang
 * @version 2016-05-21
 */
@Service
@Transactional(readOnly = true)
public class RmsFlightSecurityService extends CrudService<RmsFlightSecurityDao, RmsFlightSecurity> {

	public RmsFlightSecurity get(String id) {
		return super.get(id);
	}
	
	public List<RmsFlightSecurity> findList(RmsFlightSecurity rmsFlightSecurity) {
		return super.findList(rmsFlightSecurity);
	}
	
	public Page<RmsFlightSecurity> findPage(Page<RmsFlightSecurity> page, RmsFlightSecurity rmsFlightSecurity) {
		return super.findPage(page, rmsFlightSecurity);
	}
	
	@Transactional(readOnly = false)
	public void save(RmsFlightSecurity rmsFlightSecurity) {
		super.save(rmsFlightSecurity);
	}
	
	@Transactional(readOnly = false)
	public void delete(RmsFlightSecurity rmsFlightSecurity) {
		super.delete(rmsFlightSecurity);
	}
	
}