/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.DepartureHallOccupyingInfoDao;
import com.thinkgem.jeesite.modules.rms.entity.DepartureHallOccupyingInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 候机厅占用信息Service
 *
 * @author 候机厅占用信息
 * @version 2016-04-18
 */
@Service
@Transactional(readOnly = true)
public class DepartureHallOccupyingInfoService extends CrudService<DepartureHallOccupyingInfoDao, DepartureHallOccupyingInfo> {

    public DepartureHallOccupyingInfo get(String id) {
        return super.get(id);
    }

    public List<DepartureHallOccupyingInfo> findList(DepartureHallOccupyingInfo departureHallOccupyingInfo) {
        return super.findList(departureHallOccupyingInfo);
    }

    public Page<DepartureHallOccupyingInfo> findPage(Page<DepartureHallOccupyingInfo> page, DepartureHallOccupyingInfo departureHallOccupyingInfo) {
        return super.findPage(page, departureHallOccupyingInfo);
    }

    public void save(DepartureHallOccupyingInfo departureHallOccupyingInfo) {
        super.save(departureHallOccupyingInfo);
    }

    public void delete(DepartureHallOccupyingInfo departureHallOccupyingInfo) {
        super.delete(departureHallOccupyingInfo);
    }


    @Transactional(readOnly = true)
    public DepartureHallOccupyingInfo getByFlightDynamicId(String flightDynamicId) {
        return dao.getByFlightDynamicId(flightDynamicId);
    }

    @Transactional(readOnly = true)
    public List<String> findCompatibleCodes(Map<String, Object> params) {
        return dao.findCompatibleCodes(params);
    }

    public List<DepartureHallOccupyingInfo> fetchOciDatas(List<String> pairIds) {
        return dao.fetchOciDatas(pairIds);
    }

}