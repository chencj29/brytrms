/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.FlightProgressActInfo;

import java.util.Map;

/**
 * 保障过程时间DAO接口
 *
 * @author xiaowu
 * @version 2016-04-25
 */
@MyBatisDao
public interface FlightProgressActInfoDao extends CrudDao<FlightProgressActInfo> {
    FlightProgressActInfo findByPairIdAndProgressId(Map<String, Object> params);
}