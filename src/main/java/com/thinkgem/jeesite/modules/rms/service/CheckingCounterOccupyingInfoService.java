/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import cn.net.metadata.wrapper.AverageResourceCalcWrapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.CheckingCounterOccupyingInfoDao;
import com.thinkgem.jeesite.modules.rms.entity.CheckingCounterOccupyingInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 值机柜台占用信息Service
 *
 * @author xiaowu
 * @version 2016-04-13
 */
@Service
@Transactional(readOnly = true)
public class CheckingCounterOccupyingInfoService extends CrudService<CheckingCounterOccupyingInfoDao, CheckingCounterOccupyingInfo> {

    public CheckingCounterOccupyingInfo get(String id) {
        return super.get(id);
    }

    public List<CheckingCounterOccupyingInfo> findList(CheckingCounterOccupyingInfo checkingCounterOccupyingInfo) {
        return super.findList(checkingCounterOccupyingInfo);
    }

    public Page<CheckingCounterOccupyingInfo> findPage(Page<CheckingCounterOccupyingInfo> page, CheckingCounterOccupyingInfo checkingCounterOccupyingInfo) {
        return super.findPage(page, checkingCounterOccupyingInfo);
    }

    public void save(CheckingCounterOccupyingInfo checkingCounterOccupyingInfo) {
        super.save(checkingCounterOccupyingInfo);
    }

    public void delete(CheckingCounterOccupyingInfo checkingCounterOccupyingInfo) {
        super.delete(checkingCounterOccupyingInfo);
    }

    @Transactional(readOnly = true)
    public CheckingCounterOccupyingInfo getByFlightDynamicId(String flightDynamicId) {
        return dao.getByFlightDynamicId(flightDynamicId);
    }

    @Transactional(readOnly = true)
    public List<AverageResourceCalcWrapper> findCompatibleCodes(Map<String, Object> params) {
        return dao.findCompatibleCodes(params);
    }

    public List<CheckingCounterOccupyingInfo> fetchOciDatas(List<String> pairIds) {
        return dao.fetchOciDatas(pairIds);
    }

}