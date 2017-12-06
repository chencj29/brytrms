/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightNature;

/**
 * 航班性质管理DAO接口
 * @author xiaopo
 * @version 2015-12-14
 */
@MyBatisDao
public interface FlightNatureDao extends CrudDao<FlightNature> {

	public FlightNature findByCondition(FlightNature flightNature);

	public FlightNature getBy(FlightNature flightNature);

}