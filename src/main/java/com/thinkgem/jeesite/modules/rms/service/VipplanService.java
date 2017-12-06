/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.VipplanDao;
import com.thinkgem.jeesite.modules.rms.entity.Vipplan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * VIP计划表Service
 * @author wjp
 * @version 2016-08-03
 */
@Service
@Transactional(readOnly = true)
public class VipplanService extends CrudService<VipplanDao, Vipplan> {

	public Vipplan get(String id) {
		return super.get(id);
	}
	
	public List<Vipplan> findList(Vipplan vipplan) {
		vipplan.getSqlMap().put("dsfn",dataScopeFilterSpecial(vipplan.getCurrentUser(),"1"));
		return super.findList(vipplan);
	}
	
	public Page<Vipplan> findPage(Page<Vipplan> page, Vipplan vipplan) {
		return super.findPage(page, vipplan);
	}
	
	@Transactional(readOnly = false)
	public void save(Vipplan vipplan) {
		super.save(vipplan);
	}
	
	@Transactional(readOnly = false)
	public void delete(Vipplan vipplan) {
		super.delete(vipplan);
	}

	public List<Vipplan> findVipList(Vipplan vipplan){
		return dao.findVipList(vipplan);
	}


}