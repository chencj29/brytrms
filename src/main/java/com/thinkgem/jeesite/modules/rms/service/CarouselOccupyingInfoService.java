/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import cn.net.metadata.wrapper.AverageResourceCalcWrapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.CarouselOccupyingInfoDao;
import com.thinkgem.jeesite.modules.rms.entity.CarouselOccupyingInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 行李转盘占用信息Service
 *
 * @author xiaowu
 * @version 2016-03-28
 */
@Service
@Transactional(readOnly = true)
public class CarouselOccupyingInfoService extends CrudService<CarouselOccupyingInfoDao, CarouselOccupyingInfo> {

    public CarouselOccupyingInfo get(String id) {
        return super.get(id);
    }

    public List<CarouselOccupyingInfo> findList(CarouselOccupyingInfo carouselOccupyingInfo) {
        return super.findList(carouselOccupyingInfo);
    }

    public Page<CarouselOccupyingInfo> findPage(Page<CarouselOccupyingInfo> page, CarouselOccupyingInfo carouselOccupyingInfo) {
        return super.findPage(page, carouselOccupyingInfo);
    }

    public void save(CarouselOccupyingInfo carouselOccupyingInfo) {
        super.save(carouselOccupyingInfo);
    }

    public void delete(CarouselOccupyingInfo carouselOccupyingInfo) {
        super.delete(carouselOccupyingInfo);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> findCarouselUsage(Map<String, Object> params) {
        return dao.findCarouselUsage(params);
    }

    @Transactional(readOnly = true)
    public List<AverageResourceCalcWrapper> findCompatibleCodes(Map<String, Object> params) {
        return dao.findCompatibleCodes(params);
    }


    @Transactional(readOnly = true)
    public CarouselOccupyingInfo getByFlightDynamicId(String flightDynamicId) {
        return dao.getByFlightDynamicId(flightDynamicId);
    }

    public List<CarouselOccupyingInfo> fetchOciDatas(List<String> pairIds) {
        return dao.fetchOciDatas(pairIds);
    }
}