/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.AmsAddress;
import com.thinkgem.jeesite.modules.ams.dao.AmsAddressDao;

/**
 * 地名管理Service
 * @author chrischen
 * @version 2015-12-22
 */
@Service
@Transactional(readOnly = true)
public class AmsAddressService extends CrudService<AmsAddressDao, AmsAddress> {

	public AmsAddress get(String id) {
		return super.get(id);
	}
	
	public List<AmsAddress> findList(AmsAddress amsAddress) {
		return super.findList(amsAddress);
	}
	
	public Page<AmsAddress> findPage(Page<AmsAddress> page, AmsAddress amsAddress) {
		return super.findPage(page, amsAddress);
	}
	
	@Transactional(readOnly = false)
	public void save(AmsAddress amsAddress) {
		// 将1、0转换成是、否，再去保存
		amsAddress.setForeignName(StringUtils.code2YesOrNo(amsAddress.getForeignornot()));
		amsAddress.setMemberName(StringUtils.code2YesOrNo(amsAddress.getMemberornot()));
		super.save(amsAddress);
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsAddress amsAddress) {
		super.delete(amsAddress);
	}
	
}