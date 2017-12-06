/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.SecurityCheckinOccupyingInfoDao;
import com.thinkgem.jeesite.modules.rms.entity.CheckingCounterOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.entity.SecurityCheckinOccupyingInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 安检口占用信息Service
 *
 * @author xiaowu
 * @version 2016-04-18
 */
@Service
@Transactional(readOnly = true)
public class SecurityCheckinOccupyingInfoService extends CrudService<SecurityCheckinOccupyingInfoDao, SecurityCheckinOccupyingInfo> {

    public SecurityCheckinOccupyingInfo get(String id) {
        return super.get(id);
    }

    public List<SecurityCheckinOccupyingInfo> findList(SecurityCheckinOccupyingInfo securityCheckinOccupyingInfo) {
        return super.findList(securityCheckinOccupyingInfo);
    }

    public Page<SecurityCheckinOccupyingInfo> findPage(Page<SecurityCheckinOccupyingInfo> page, SecurityCheckinOccupyingInfo securityCheckinOccupyingInfo) {
        return super.findPage(page, securityCheckinOccupyingInfo);
    }

    public void save(SecurityCheckinOccupyingInfo securityCheckinOccupyingInfo) {
        super.save(securityCheckinOccupyingInfo);
    }

    public void delete(SecurityCheckinOccupyingInfo securityCheckinOccupyingInfo) {
        super.delete(securityCheckinOccupyingInfo);
    }

    @Transactional(readOnly = true)
    public SecurityCheckinOccupyingInfo getByFlightDynamicId(String flightDynamicId) {
        return dao.getByFlightDynamicId(flightDynamicId);
    }

    @Transactional(readOnly = true)
    public List<String> findCompatibleCodes(Map<String, Object> params) {
        return dao.findCompatibleCodes(params);
    }

    public List<SecurityCheckinOccupyingInfo> fetchOciDatas(List<String> pairIds) {
        return dao.fetchOciDatas(pairIds);
    }

}