/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ams.dao.FlightCompanyInfoDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightCompanyInfo;
import com.thinkgem.jeesite.modules.ams.entity.FlightCompanySubInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 航空公司信息管理Service
 *
 * @author xiaopo
 * @version 2015-12-09
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class FlightCompanyInfoService extends CrudService<FlightCompanyInfoDao, FlightCompanyInfo> {

    @Autowired
    private FlightCompanySubInfoService flightCompanySubInfoService;

    public FlightCompanyInfo get(String id) {
        return super.get(id);
    }

    public List<FlightCompanyInfo> findList(FlightCompanyInfo flightCompanyInfo) {
        return super.findList(flightCompanyInfo);
    }

    public Page<FlightCompanyInfo> findPage(Page<FlightCompanyInfo> page, FlightCompanyInfo flightCompanyInfo) {
        return super.findPage(page, flightCompanyInfo);
    }

    @Transactional(readOnly = false)
    public void save(FlightCompanyInfo flightCompanyInfo) {
        super.save(flightCompanyInfo);
    }

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public void delete(FlightCompanyInfo flightCompanyInfo) {
        super.delete(flightCompanyInfo);
        //删除下属子公司
        FlightCompanySubInfo flightCompanySubInfo = new FlightCompanySubInfo();
        flightCompanySubInfo.setFlightCompanyInfoId(flightCompanyInfo.getId());
        flightCompanySubInfoService.deleteAllSub(flightCompanySubInfo);
    }
}