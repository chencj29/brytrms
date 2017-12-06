/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.BoardingGateOccupyingInfoDao;
import com.thinkgem.jeesite.modules.rms.entity.BoardingGateOccupyingInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 登机口占用信息Service
 *
 * @author BigBrother5
 * @version 2016-04-11
 */
@Service
@Transactional(readOnly = true)
public class BoardingGateOccupyingInfoService extends CrudService<BoardingGateOccupyingInfoDao, BoardingGateOccupyingInfo> {

    public BoardingGateOccupyingInfo get(String id) {
        return super.get(id);
    }

    public List<BoardingGateOccupyingInfo> findList(BoardingGateOccupyingInfo boardingGateOccupyingInfo) {
        return super.findList(boardingGateOccupyingInfo);
    }

    public Page<BoardingGateOccupyingInfo> findPage(Page<BoardingGateOccupyingInfo> page, BoardingGateOccupyingInfo boardingGateOccupyingInfo) {
        return super.findPage(page, boardingGateOccupyingInfo);
    }


    public void save(BoardingGateOccupyingInfo boardingGateOccupyingInfo) {
        super.save(boardingGateOccupyingInfo);
    }

    public void delete(BoardingGateOccupyingInfo boardingGateOccupyingInfo) {
        super.delete(boardingGateOccupyingInfo);
    }

    @Transactional(readOnly = true)
    public BoardingGateOccupyingInfo getByFlightDynamicId(String flightDynamicId) {
        return dao.getByFlightDynamicId(flightDynamicId);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> findArrivalGateUsage(Map<String, Object> params) {
        return dao.findArrivalGateUsage(params);
    }

    public List<BoardingGateOccupyingInfo> fetchOciDatas(List<String> pairIds) {
        return dao.fetchOciDatas(pairIds);
    }

}