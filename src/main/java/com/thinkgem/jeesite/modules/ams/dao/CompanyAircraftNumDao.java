/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.CompanyAircraftNum;

/**
 * 公司机号管理DAO接口
 *
 * @author xiaopo
 * @version 2015-12-15
 */
@MyBatisDao
public interface CompanyAircraftNumDao extends CrudDao<CompanyAircraftNum> {
    CompanyAircraftNum queryByFlightDynamicCodeAndAircraftNum(String flightCompanyCode, String aircraftNum);
}