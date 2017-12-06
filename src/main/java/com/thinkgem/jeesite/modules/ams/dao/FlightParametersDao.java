/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightParameters;

import java.util.List;
import java.util.Map;

/**
 * 航班状态呢管理DAO接口，（FlightParameters其实是模块总称。）
 *
 * @author xiaopo
 * @version 2015-12-14
 */
@MyBatisDao
public interface FlightParametersDao extends CrudDao<FlightParameters> {
    List<FlightParameters> getFlightParametersByRegex(Map map);

    List<FlightParameters> listJsons4Type(String type);

}