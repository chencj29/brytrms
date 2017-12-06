/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightPlanDetail;

import java.util.List;
import java.util.Map;

/**
 * 航班计划管理DAO接口
 * @author xiaopo
 * @version 2015-12-24
 */
@MyBatisDao
public interface FlightPlanDetailDao extends CrudDao<FlightPlanDetail> {


    public List<FlightPlanDetail> findListByNullFlightNo(FlightPlanDetail flightPlanDetail);


    public List<FlightPlanDetail> findListByNull(FlightPlanDetail flightPlanDetail);

    public List<Map> findInOutMulti(String createDate);

    public List<FlightPlanDetail> findListByInOut(FlightPlanDetail flightPlanDetail);

    public void setFlightNum(Map map);

    public List<FlightPlanDetail> findListByIds(Map map);

    public void audit(FlightPlanDetail flightPlanDetail);

    public List<FlightPlanDetail> findListForInit(FlightPlanDetail flightPlanDetail);

    /**
     * 查询某一天航班计划不合理的记录
     * 规则：
     * 同一天、同一个航空公司、同一个航班性质、同一个进出港类型、不允许存在相同的航班号
     *
     * 查询方法：
     * 根据航空公司、飞机号、航班性质、进出类型分组查询，获取结果大于1的记录
     * @param flightPlanDetail
     * @return
     */
    List<FlightPlanDetail> findAbnormalList(FlightPlanDetail flightPlanDetail);
}