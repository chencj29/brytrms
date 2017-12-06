/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.ArrivalGateOccupyingInfoDao;
import com.thinkgem.jeesite.modules.rms.entity.ArrivalGateOccupyingInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 到港门占用信息Service
 *
 * @author bb5
 * @version 2016-04-08
 */
@Service
@Transactional(readOnly = true)
public class ArrivalGateOccupyingInfoService extends CrudService<ArrivalGateOccupyingInfoDao, ArrivalGateOccupyingInfo> {

    public ArrivalGateOccupyingInfo get(String id) {
        return super.get(id);
    }

    public List<ArrivalGateOccupyingInfo> findList(ArrivalGateOccupyingInfo arrivalGateOccupyingInfo) {
        return super.findList(arrivalGateOccupyingInfo);
    }

    public Page<ArrivalGateOccupyingInfo> findPage(Page<ArrivalGateOccupyingInfo> page, ArrivalGateOccupyingInfo arrivalGateOccupyingInfo) {
        return super.findPage(page, arrivalGateOccupyingInfo);
    }

    public void save(ArrivalGateOccupyingInfo arrivalGateOccupyingInfo) {
        super.save(arrivalGateOccupyingInfo);
    }

    public void delete(ArrivalGateOccupyingInfo arrivalGateOccupyingInfo) {
        super.delete(arrivalGateOccupyingInfo);
    }

    @Transactional(readOnly = true)
    public ArrivalGateOccupyingInfo getByFlightDynamicId(String flightDynamicId) {
        return dao.getByFlightDynamicId(flightDynamicId);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> findArrivalGateUsage(Map<String, Object> params) {
        return dao.findArrivalGateUsage(params);
    }

    public List<ArrivalGateOccupyingInfo> fetchOciDatas(List<String> pairIds) {
        return dao.fetchOciDatas(pairIds);
    }

}