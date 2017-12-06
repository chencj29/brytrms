/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightPlanDetailChange;

/**
 * 航班计划变更DAO接口
 * @author chrischen
 * @version 2016-01-21
 */
@MyBatisDao
public interface FlightPlanDetailChangeDao extends CrudDao<FlightPlanDetailChange> {

    FlightPlanDetailChange findNotApprove(FlightPlanDetailChange flightPlanDetailChange);

    FlightPlanDetailChange findWaitApprove(FlightPlanDetailChange flightPlanDetailChange);

    void updateByBaseId(FlightPlanDetailChange flightPlanDetailChange);
}