/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.LongPlanDivide;

import java.util.List;
import java.util.Map;

/**
 * 长期计划拆分DAO接口
 * @author chrischen
 * @version 2016-01-11
 */
@MyBatisDao
public interface LongPlanDivideDao extends CrudDao<LongPlanDivide> {
	
	public void deleteByLongPlanId(Map<String, String> params);

	public List<LongPlanDivide> findByPlanDateAndWeek(Map<String, Object> params);

	void deleteByApplyDate(Map<String, Object> params);

	List<LongPlanDivide> findByPlanDate(Map<String, Object> params);
}