/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.AircraftParameters;
import com.thinkgem.jeesite.modules.ams.entity.common.AutoCompleteData;
import com.thinkgem.jeesite.modules.ams.entity.common.AutoCompleteParams;

import java.util.List;

/**
 * 机型参数管理DAO接口
 * @author xiaopo
 * @version 2015-12-14
 */
@MyBatisDao
public interface AircraftParametersDao extends CrudDao<AircraftParameters> {
	
	List<AutoCompleteData> findModelCode(AutoCompleteParams autoCompleteParams);

	AircraftParameters findByAircraftModelCode(String modelCode);


	List<String> groupByModel();
}