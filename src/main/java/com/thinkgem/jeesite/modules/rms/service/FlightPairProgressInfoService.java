/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.FlightPairProgressInfoDao;
import com.thinkgem.jeesite.modules.rms.entity.FlightPairProgressInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 航班运输保障信息Service
 *
 * @author xiaowu
 * @version 2016-06-13
 */
@Service
@Transactional(readOnly = true)
public class FlightPairProgressInfoService extends CrudService<FlightPairProgressInfoDao, FlightPairProgressInfo> {

    public FlightPairProgressInfo get(String id) {
        return super.get(id);
    }

    public List<FlightPairProgressInfo> findList(FlightPairProgressInfo flightPairProgressInfo) {
        return super.findList(flightPairProgressInfo);
    }

    public Page<FlightPairProgressInfo> findPage(Page<FlightPairProgressInfo> page, FlightPairProgressInfo flightPairProgressInfo) {
        return super.findPage(page, flightPairProgressInfo);
    }

    @Transactional
    public void save(FlightPairProgressInfo flightPairProgressInfo) {
        super.save(flightPairProgressInfo);
    }

    @Transactional
    public void delete(FlightPairProgressInfo flightPairProgressInfo) {
        super.delete(flightPairProgressInfo);
    }

    @Transactional(readOnly = true)
    public List<FlightPairProgressInfo> queryByPairId(String pairId) {
        return dao.queryByPairId(pairId);
    }

    /**
     * 检测FlightPlanPair是否存在
     *
     * @param pairId 配对ID
     * @return { true : 存在 · false : 不存在}
     */
    @Transactional(readOnly = true)
    public boolean checkPairExists(String pairId) {
        return dao.checkPairExists(pairId);
    }

    @Transactional
    public void deleteByPairId(String pairId) {
        dao.deleteByPairId(pairId);
    }

}