/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.EmergencyPlan;

/**
 * 应急救援方案DAO接口
 * @author wjp
 * @version 2016-08-25
 */
@MyBatisDao
public interface EmergencyPlanDao extends CrudDao<EmergencyPlan> {
	
}