/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.RmsGateOccupyingInfoHi;
import com.thinkgem.jeesite.modules.rms.dao.RmsGateOccupyingInfoHiDao;

/**
 * 机位占用信息历史Service
 * @author wjp
 * @version 2016-05-16
 */
@Service
@Transactional(readOnly = true)
public class RmsGateOccupyingInfoHiService extends CrudService<RmsGateOccupyingInfoHiDao, RmsGateOccupyingInfoHi> {

	public RmsGateOccupyingInfoHi get(String id) {
		return super.get(id);
	}
	
	public List<RmsGateOccupyingInfoHi> findList(RmsGateOccupyingInfoHi rmsGateOccupyingInfoHi) {
		return super.findList(rmsGateOccupyingInfoHi);
	}
	
	public Page<RmsGateOccupyingInfoHi> findPage(Page<RmsGateOccupyingInfoHi> page, RmsGateOccupyingInfoHi rmsGateOccupyingInfoHi) {
		return super.findPage(page, rmsGateOccupyingInfoHi);
	}
	
	@Transactional(readOnly = false)
	public void save(RmsGateOccupyingInfoHi rmsGateOccupyingInfoHi) {
		super.save(rmsGateOccupyingInfoHi);
	}
	
	@Transactional(readOnly = false)
	public void delete(RmsGateOccupyingInfoHi rmsGateOccupyingInfoHi) {
		super.delete(rmsGateOccupyingInfoHi);
	}
	
}