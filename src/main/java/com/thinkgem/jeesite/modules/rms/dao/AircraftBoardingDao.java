/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.AircraftBoarding;

import java.util.List;

/**
 * 机位与登机口对应表DAO接口
 * @author wjp
 * @version 2016-03-09
 */
@MyBatisDao
public interface AircraftBoardingDao extends CrudDao<AircraftBoarding> {
    List<AircraftBoarding> findByAircraftStandNum(List<String> aircraftStandNums);
}