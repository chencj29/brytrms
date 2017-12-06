/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.LongPlanStop;

/**
 * 长期计划管理DAO接口
 * @author xiaopo
 * @version 2015-12-22
 */
@MyBatisDao
public interface LongPlanStopDao extends CrudDao<LongPlanStop> {
	
	public void deleteByLongPlanId(String longPlanIds);

	public List<LongPlanStop> findByLongPlanId(LongPlanStop longPlanStop);
	
}