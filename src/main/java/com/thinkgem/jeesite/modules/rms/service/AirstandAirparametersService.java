/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.AirstandAirparametersDao;
import com.thinkgem.jeesite.modules.rms.entity.AirstandAirparameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 机位与机型对应表Service
 *
 * @author wjp
 * @version 2016-03-17
 */
@Service
@Transactional(readOnly = true)
public class AirstandAirparametersService extends CrudService<AirstandAirparametersDao, AirstandAirparameters> {

    public AirstandAirparameters get(String id) {
        return super.get(id);
    }

    public List<AirstandAirparameters> findList(AirstandAirparameters airstandAirparameters) {
        return super.findList(airstandAirparameters);
    }

    public Page<AirstandAirparameters> findPage(Page<AirstandAirparameters> page, AirstandAirparameters airstandAirparameters) {
        return super.findPage(page, airstandAirparameters);
    }

    @Transactional(readOnly = false)
    public void save(AirstandAirparameters airstandAirparameters) {
        super.save(airstandAirparameters);
    }

    @Transactional(readOnly = false)
    public void delete(AirstandAirparameters airstandAirparameters) {
        super.delete(airstandAirparameters);
    }


    @Transactional(readOnly = true)
    public AirstandAirparameters findByAircraftModel(String aircraftModel) {
        return dao.findByAircraftModel(aircraftModel);
    }
}