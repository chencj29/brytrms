/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ams.dao.YesterAircraftNumPairDao;
import com.thinkgem.jeesite.modules.ams.entity.YesterAircraftNumPair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 单进航班次日无任务机位表Service
 *
 * @author wjp
 * @version 2017-09-13
 */
@Service
@Transactional(readOnly = true)
public class YesterAircraftNumPairService extends CrudService<YesterAircraftNumPairDao, YesterAircraftNumPair> {

    public YesterAircraftNumPair get(String id) {
        return super.get(id);
    }

    public List<YesterAircraftNumPair> findList(YesterAircraftNumPair yesterAircraftNumPair) {
        return super.findList(yesterAircraftNumPair);
    }

    public List<YesterAircraftNumPair> findListByPlanDate(YesterAircraftNumPair yesterAircraftNumPair) {
        return dao.findListByPlanDate(yesterAircraftNumPair);
    }

    public Page<YesterAircraftNumPair> findPage(Page<YesterAircraftNumPair> page, YesterAircraftNumPair yesterAircraftNumPair) {
        return super.findPage(page, yesterAircraftNumPair);
    }

    @Transactional(readOnly = false)
    public void save(YesterAircraftNumPair yesterAircraftNumPair) {
        super.save(yesterAircraftNumPair);
    }

    @Transactional(readOnly = false)
    public void updateByPlaceStatuse(YesterAircraftNumPair yesterAircraftNumPair) {
        dao.updateByPlaceStatuse(yesterAircraftNumPair);
    }

    @Transactional(readOnly = false)
    public void delete(YesterAircraftNumPair yesterAircraftNumPair) {
        super.delete(yesterAircraftNumPair);
    }

    @Transactional(readOnly = false)
    public Integer toUse(String pairId, Date planDate) {
        return dao.updateStatus(ImmutableMap.of("placeStatus", "1", "pairId", pairId, "planDate", planDate));
    }

    @Transactional(readOnly = false)
    public Integer toUseByNull(Date planDate, String aircraftNum) {
        return dao.updateStatusByNull(ImmutableMap.of("planDate", planDate, "aircraftNum", aircraftNum));
    }

    public List<YesterAircraftNumPair> findListByStatus(YesterAircraftNumPair yesterAircraftNumPair) {
        return dao.findListByStatus(yesterAircraftNumPair);
    }
}