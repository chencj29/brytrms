/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightCompanySubInfo;

import java.util.List;

/**
 * 航空公司子公司管理DAO接口
 * @author xiaopo
 * @version 2016-08-24
 */
@MyBatisDao
public interface FlightCompanySubInfoDao extends CrudDao<FlightCompanySubInfo> {

    void deleteAllSub(FlightCompanySubInfo flightCompanySubInfo);

    List<FlightCompanySubInfo> findList_EXT(FlightCompanySubInfo flightCompanySubInfo);
}