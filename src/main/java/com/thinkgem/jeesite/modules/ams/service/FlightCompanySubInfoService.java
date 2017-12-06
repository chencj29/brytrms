/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ams.entity.FlightCompanySubInfo;
import com.thinkgem.jeesite.modules.ams.dao.FlightCompanySubInfoDao;

/**
 * 航空公司子公司管理Service
 * @author xiaopo
 * @version 2016-08-24
 */
@Service
@Transactional(readOnly = true)
public class FlightCompanySubInfoService extends CrudService<FlightCompanySubInfoDao, FlightCompanySubInfo> {

	public FlightCompanySubInfo get(String id) {
		return super.get(id);
	}
	
	public List<FlightCompanySubInfo> findList(FlightCompanySubInfo flightCompanySubInfo) {
		return super.findList(flightCompanySubInfo);
	}
	
	public Page<FlightCompanySubInfo> findPage(Page<FlightCompanySubInfo> page, FlightCompanySubInfo flightCompanySubInfo) {
		return super.findPage(page, flightCompanySubInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(FlightCompanySubInfo flightCompanySubInfo) {
		super.save(flightCompanySubInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(FlightCompanySubInfo flightCompanySubInfo) {
		super.delete(flightCompanySubInfo);
	}

	public void deleteAllSub(FlightCompanySubInfo flightCompanySubInfo) {
		dao.deleteAllSub(flightCompanySubInfo);
	}

	public List<FlightCompanySubInfo> findList_EXT(FlightCompanySubInfo flightCompanySubInfo) {
		return dao.findList_EXT(flightCompanySubInfo);
	}
}