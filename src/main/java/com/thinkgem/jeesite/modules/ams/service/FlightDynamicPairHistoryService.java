/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.dao.FlightDynamicHistoryDao;
import com.thinkgem.jeesite.modules.ams.dao.FlightDynamicPairHistoryDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamicPairHistory;
import com.thinkgem.jeesite.modules.rms.entity.*;
import com.thinkgem.jeesite.modules.rms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 航班动态配对历史信息信息Service
 *
 * @author wjp
 * @version 2016-08-26
 */
@Service
@Transactional(readOnly = true)
public class FlightDynamicPairHistoryService extends CrudService<FlightDynamicPairHistoryDao, FlightDynamicPairHistory> {

    public FlightDynamicPairHistory get(String id) {
        return super.get(id);
    }

    public List<FlightDynamicPairHistory> findList(FlightDynamicPairHistory flightDynamicPairHistory) {
        flightDynamicPairHistory.getSqlMap().put("dsfn", dataScopeFilterNew(flightDynamicPairHistory.getCurrentUser(), true));
        return super.findList(flightDynamicPairHistory);
    }

    public Page<FlightDynamicPairHistory> findPage(Page<FlightDynamicPairHistory> page, FlightDynamicPairHistory flightDynamicPairHistory) {
        return super.findPage(page, flightDynamicPairHistory);
    }

    @Transactional(readOnly = false)
    public void save(FlightDynamicPairHistory flightDynamicPairHistory) {
        super.save(flightDynamicPairHistory);
    }

    @Transactional(readOnly = false)
    public void delete(FlightDynamicPairHistory flightDynamicPairHistory) {
        super.delete(flightDynamicPairHistory);
    }

    public List<FlightPlanPair> findOciList(FlightPlanPair flightPlanPair, Map paramMap) {
        //处理权限
        Map dsfnMap = Maps.newHashMap();
        dsfnMap.put("dsfn", dataScopeFilterNew(flightPlanPair.getCurrentUser()));
        paramMap.put("sqlMap", dsfnMap);

        //查询条件进出港、航空公司、航班号、飞机号
        if (StringUtils.isNotBlank(flightPlanPair.getInoutTypeCode()))
            paramMap.put("inoutTypeCode", flightPlanPair.getInoutTypeCode());
        if (StringUtils.isNotBlank(flightPlanPair.getFlightCompanyCode()))
            paramMap.put("flightCompanyCode", flightPlanPair.getFlightCompanyCode());
        if (StringUtils.isNotBlank(flightPlanPair.getFlightNum()))
            paramMap.put("flightNum", flightPlanPair.getFlightNum());
        if (StringUtils.isNotBlank(flightPlanPair.getAircraftNum()))
            paramMap.put("aircraftNum", flightPlanPair.getAircraftNum());
        if (paramMap.entrySet().size() == 1 && paramMap.get("sqlMap") != null) {
            return new ArrayList<>();
        }

        return dao.findOciList(paramMap);
    }

}