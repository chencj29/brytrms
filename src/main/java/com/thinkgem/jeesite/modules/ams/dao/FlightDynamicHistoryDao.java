/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.rms.entity.CarouselOccupyingInfoWrapper;
import com.thinkgem.jeesite.modules.rms.entity.CheckingCounterOccupyingInfoWrapper;

import java.util.List;
import java.util.Map;

/**
 * 航班动态管理DAO接口
 *
 * @author xiaopo
 * @version 2016-01-10
 */
@MyBatisDao
public interface FlightDynamicHistoryDao extends CrudDao<FlightDynamic> {

    void delay(FlightDynamic flightDynamic);

    void cancel(FlightDynamic flightDynamic);

    void alternate(FlightDynamic flightDynamic);

    List<Map> getPairFlightList(FlightDynamic flightDynamic);

    List<FlightDynamic> findListAgo(Map map);

    List<CarouselOccupyingInfoWrapper> carouselList(FlightDynamic flightDynamic);

    List<CheckingCounterOccupyingInfoWrapper> checkinCounterList(FlightDynamic flightDynamic);

    FlightDynamic getHis(String id);

    List<FlightDynamic> findListByFlightOut(Map map);
}