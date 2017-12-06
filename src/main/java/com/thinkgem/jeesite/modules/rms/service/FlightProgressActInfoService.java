/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.FlightPlanPairDao;
import com.thinkgem.jeesite.modules.rms.dao.FlightProgressActInfoDao;
import com.thinkgem.jeesite.modules.rms.entity.FlightProgressActInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 保障过程时间Service
 *
 * @author xiaowu
 * @version 2016-04-25
 */
@Service
@Transactional(readOnly = true)
public class FlightProgressActInfoService extends CrudService<FlightProgressActInfoDao, FlightProgressActInfo> {
    @Autowired
    FlightPlanPairDao flightPlanPairDao;

    @Autowired
    SafeguardSeatTimelongService safeguardSeatTimelongService;


    public FlightProgressActInfo get(String id) {
        return super.get(id);
    }

    public List<FlightProgressActInfo> findList(FlightProgressActInfo flightProgressActInfo) {
        return super.findList(flightProgressActInfo);
    }

    public Page<FlightProgressActInfo> findPage(Page<FlightProgressActInfo> page, FlightProgressActInfo flightProgressActInfo) {
        return super.findPage(page, flightProgressActInfo);
    }

    public void save(FlightProgressActInfo flightProgressActInfo) {
        super.save(flightProgressActInfo);
    }

    public void delete(FlightProgressActInfo flightProgressActInfo) {
        super.delete(flightProgressActInfo);
    }

    public void setProgressActualTime(FlightProgressActInfo flightProgressActInfo, Integer progressIndex) {
        FlightProgressActInfo infoInDB = dao.findByPairIdAndProgressId(ImmutableMap.of("pairId", flightProgressActInfo.getPairId(), "progressId", flightProgressActInfo.getProgressId()));

        if (infoInDB == null) {
            flightProgressActInfo.setId(UUID.randomUUID().toString());
            dao.insert(flightProgressActInfo);
        } else {
            flightProgressActInfo.setId(infoInDB.getId());
            dao.update(flightProgressActInfo);
        }
    }
}