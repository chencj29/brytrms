/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.SecurityCheckinDao;
import com.thinkgem.jeesite.modules.rms.entity.SecurityCheckin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 安检口基本信息Service
 * @author wjp
 * @version 2016-03-09
 */
@Service
@Transactional(readOnly = true)
public class SecurityCheckinService extends CrudService<SecurityCheckinDao, SecurityCheckin> {

	public SecurityCheckin get(String id) {
		return super.get(id);
	}

	public List<SecurityCheckin> findList(SecurityCheckin securityCheckin) {
		return super.findList(securityCheckin);
	}

	public Page<SecurityCheckin> findPage(Page<SecurityCheckin> page, SecurityCheckin securityCheckin) {
		return super.findPage(page, securityCheckin);
	}

	public void save(SecurityCheckin securityCheckin) {
		super.save(securityCheckin);
	}

	public void delete(SecurityCheckin securityCheckin) {
		super.delete(securityCheckin);
	}

	@Transactional(readOnly = true)
	public List<SecurityCheckin> checkStatusBySecurityCheckinNum(List<String> list) {
		return dao.findBySecurityCheckinNum(list).stream().filter(securityCheckin -> !securityCheckin.getScecurityStatus().equals("0")).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public SecurityCheckin getByCode(String securityCheckinCode) {
		return dao.getByCode(securityCheckinCode);
	}

	public List<SecurityCheckin> findAvailableSecurityCheckinNature(String nature) {
		return dao.findAvailableSecurityCheckinNature(nature);
	}
}