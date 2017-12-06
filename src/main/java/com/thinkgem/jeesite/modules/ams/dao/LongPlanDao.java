/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.LongPlan;
import com.thinkgem.jeesite.modules.ams.entity.LongPlanVO;

/**
 * 长期计划管理DAO接口
 * @author xiaopo
 * @version 2015-12-22
 */
@MyBatisDao
public interface LongPlanDao extends CrudDao<LongPlan> {

	long countLongPlan(LongPlan longPlan);

	List<LongPlan> findLongPlan(LongPlan queryParams);

	List<LongPlan> findAllListByPeroid(LongPlanVO longPlanVO);
}