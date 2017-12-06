/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightPlan;

import java.util.Map;

/**
 * 航班计划管理DAO接口
 * @author xiaopo
 * @version 2015-12-24
 */
@MyBatisDao
public interface FlightPlanDao extends CrudDao<FlightPlan> {

    public void audit(FlightPlan flightPlan);

	public FlightPlan findPlanDateByFlightPlan(FlightPlan flightPlan);

    void updateStatus(FlightPlan basePlan);

    void planPublish(FlightPlan flightPlan);

    public Map<String,Object> getAircraftTypeCodeByNum(String aircraftNum);
}