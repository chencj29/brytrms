/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.GateOccupyingInfoDao;
import com.thinkgem.jeesite.modules.rms.entity.GateOccupyingInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 机位占用信息Service
 *
 * @author xiaowu
 * @version 2016-03-16
 */
@Service
@Transactional(readOnly = true)
public class GateOccupyingInfoService extends CrudService<GateOccupyingInfoDao, GateOccupyingInfo> {

    public GateOccupyingInfo get(String id) {
        return super.get(id);
    }

    public List<GateOccupyingInfo> findList(GateOccupyingInfo gateOccupyingInfo) {
        return super.findList(gateOccupyingInfo);
    }

    public Page<GateOccupyingInfo> findPage(Page<GateOccupyingInfo> page, GateOccupyingInfo gateOccupyingInfo) {
        return super.findPage(page, gateOccupyingInfo);
    }

    public void save(GateOccupyingInfo gateOccupyingInfo) {
        super.save(gateOccupyingInfo);
    }

    public void delete(GateOccupyingInfo gateOccupyingInfo) {
        super.delete(gateOccupyingInfo);
    }

    @Transactional(readOnly = true)
    public GateOccupyingInfo getByFlightDynamicId(String flightDynamicId) {
        return dao.getByFlightDynamicId(flightDynamicId);
    }

    public List<GateOccupyingInfo> fetchOciDatas(List<String> pairIds) {
        return dao.fetchOciDatas(pairIds);
    }

}