/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.AircraftStand;

import java.util.List;
import java.util.Map;

/**
 * 机位基础信息DAO接口
 *
 * @author wjp
 * @version 2016-03-09
 */
@MyBatisDao
public interface AircraftStandDao extends CrudDao<AircraftStand> {
    List<AircraftStand> findByIds(List<String> list);

    List<Map<String, String>> findStandUsage(Map<String, Object> params);

    List<String> groupByAircraftStand();

    List<AircraftStand> findByAircraftNum(String aircraftNum);
}