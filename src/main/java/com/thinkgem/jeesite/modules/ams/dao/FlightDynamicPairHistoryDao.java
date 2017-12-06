/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamicPairHistory;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;

import java.util.List;
import java.util.Map;

/**
 * 航班动态配对历史信息信息DAO接口
 * @author wjp
 * @version 2016-08-26
 */
@MyBatisDao
public interface FlightDynamicPairHistoryDao extends CrudDao<FlightDynamicPairHistory> {
    List<FlightPlanPair> findOciList(Map<String, Object> paramMap);
}