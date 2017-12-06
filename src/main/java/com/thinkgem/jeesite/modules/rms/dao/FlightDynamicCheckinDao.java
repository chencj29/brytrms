/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.FlightDynamicCheckin;

/**
 * 值机柜台员工分配DAO接口
 * @author xiaopo
 * @version 2016-05-24
 */
@MyBatisDao
public interface FlightDynamicCheckinDao extends CrudDao<FlightDynamicCheckin> {

	public void updateByDynamicIdAndEmpId(FlightDynamicCheckin dynamicCheckin);
}