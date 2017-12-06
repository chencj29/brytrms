/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightProperties;

/**
 * 航班属性管理DAO接口
 * @author xiaopo
 * @version 2015-12-14
 */
@MyBatisDao
public interface FlightPropertiesDao extends CrudDao<FlightProperties> {

	public FlightProperties findByCondition(FlightProperties flightProperties);
	
}