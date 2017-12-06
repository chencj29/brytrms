/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.AircraftStandDao;
import com.thinkgem.jeesite.modules.rms.entity.AircraftStand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 机位基础信息Service
 *
 * @author wjp
 * @version 2016-03-09
 */
@Service
@Transactional(readOnly = true)
public class AircraftStandService extends CrudService<AircraftStandDao, AircraftStand> {

    public AircraftStand get(String id) {
        return super.get(id);
    }

    public List<AircraftStand> findList(AircraftStand aircraftStand) {
        return super.findList(aircraftStand);
    }

    public List<AircraftStand> findByIds(List<String> list) {
        return dao.findByIds(list);
    }

    public Page<AircraftStand> findPage(Page<AircraftStand> page, AircraftStand aircraftStand) {
        return super.findPage(page, aircraftStand);
    }

    @Transactional(readOnly = false)
    public void save(AircraftStand aircraftStand) {
        super.save(aircraftStand);
    }

    @Transactional(readOnly = false)
    public void delete(AircraftStand aircraftStand) {
        super.delete(aircraftStand);
    }

    @Transactional(readOnly = true)
    public List<AircraftStand> checkStatusByIds(List<String> list) {
        return dao.findByIds(list).stream().filter(aircraftStand -> aircraftStand.getAvailable().equals("1")).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> findStandUsage(Map<String, Object> params) {
        return dao.findStandUsage(params);
    }

    @Transactional(readOnly = true)
    public List<String> groupByAircraftStand() {
        return dao.groupByAircraftStand();
    }

    @Transactional(readOnly = true)
    public List<AircraftStand> findByAircraftNum(String aircraftStandNum) {
        return dao.findByAircraftNum(aircraftStandNum);
    }
}