package com.thinkgem.jeesite.modules.test.service;

import com.thinkgem.jeesite.modules.ams.dao.FlightDynamicDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by chencheng on 16/3/14.
 */
@Service
@Transactional(readOnly = true)
public class TestFlightDynamicService {

    @Autowired
    private FlightDynamicDao flightDynamicDao;

    @Autowired
    private FlightDynamicService flightDynamicService;

    public void testFlightDynamic() {
        FlightDynamic flightDynamic = flightDynamicDao.get("");
        Map<String, Object> result = flightDynamicService.queryInOutTypeByDynamic(flightDynamic);
    }
}
