/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import cn.net.metadata.gantt.wrapper.GateGanttWrapper;
import cn.net.metadata.wrapper.ConflictPlaceNumWrapper;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 航班动态配对DAO接口
 *
 * @author xiaowu
 * @version 2016-05-04
 */
@MyBatisDao
public interface FlightPlanPairDao extends CrudDao<FlightPlanPair> {
    Long querySeatNumByAircraftNum(@Param("aircraftNum") String aircraftNum);

    Long getMinimalSeatNumByAircraftNum(@Param("safeguardTypeCode") String safeguardTypeCode);

    FlightPlanPair queryByInDynamicId(FlightDynamic flightDynamic);

    FlightPlanPair queryByOutDynamicId(FlightDynamic flightDynamic);

    List<FlightPlanPair> findUnDistPairs(Map<String, Object> params);

    List<FlightPlanPair> findMockPairs(Map<String, Object> params);

    void deleteByPlanDate(FlightPlanPair planPair);

    List<FlightPlanPair> findListPage(Map<String, Object> params);

    Integer getTotal();

    List<GateGanttWrapper> findPairByPlanDate(Map<String, Object> paramMap);

    List<GateGanttWrapper> findPlanPair4Mock(String infoId);

    int updateSafeguardTypeCode(FlightPlanPair planPair);

    List<FlightPlanPair> findOciList(Map<String, Object> paramMap);

    List<FlightPlanPair> findList4Mock(String infoId);

    List<FlightPlanPair> getPlaceNumList(String placeNum);

    void safeguardTypeToBak(@Param("pairId") String pairId);

    List<ConflictPlaceNumWrapper> findListByConflictPlaceNum();
}