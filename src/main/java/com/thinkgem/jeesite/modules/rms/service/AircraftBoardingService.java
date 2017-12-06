/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.AircraftBoardingDao;
import com.thinkgem.jeesite.modules.rms.entity.AircraftBoarding;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 机位与登机口对应表Service
 *
 * @author wjp
 * @version 2016-03-09
 */
@Service
@Transactional(readOnly = true)
public class AircraftBoardingService extends CrudService<AircraftBoardingDao, AircraftBoarding> {

    public AircraftBoarding get(String id) {
        return super.get(id);
    }

    public List<AircraftBoarding> findList(AircraftBoarding aircraftBoarding) {
        return super.findList(aircraftBoarding);
    }

    public Page<AircraftBoarding> findPage(Page<AircraftBoarding> page, AircraftBoarding aircraftBoarding) {
        return super.findPage(page, aircraftBoarding);
    }

    public void save(AircraftBoarding aircraftBoarding) {
        super.save(aircraftBoarding);
    }

    public void delete(AircraftBoarding aircraftBoarding) {
        super.delete(aircraftBoarding);
    }

    public List<AircraftBoarding> findByAircraftStandNum(List<String> aircraftStandNums) {
        return dao.findByAircraftStandNum(aircraftStandNums);
    }

}