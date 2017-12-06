/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.SlideCoastOccupyingInfoDao;
import com.thinkgem.jeesite.modules.rms.entity.SlideCoastOccupyingInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 滑槽占用信息Service
 *
 * @author xiaowu
 * @version 2016-04-12
 */
@Service
@Transactional(readOnly = true)
public class SlideCoastOccupyingInfoService extends CrudService<SlideCoastOccupyingInfoDao, SlideCoastOccupyingInfo> {

    public SlideCoastOccupyingInfo get(String id) {
        return super.get(id);
    }

    public List<SlideCoastOccupyingInfo> findList(SlideCoastOccupyingInfo slideCoastOccupyingInfo) {
        return super.findList(slideCoastOccupyingInfo);
    }

    public Page<SlideCoastOccupyingInfo> findPage(Page<SlideCoastOccupyingInfo> page, SlideCoastOccupyingInfo slideCoastOccupyingInfo) {
        return super.findPage(page, slideCoastOccupyingInfo);
    }

    public void save(SlideCoastOccupyingInfo slideCoastOccupyingInfo) {
        super.save(slideCoastOccupyingInfo);
    }

    public void delete(SlideCoastOccupyingInfo slideCoastOccupyingInfo) {
        super.delete(slideCoastOccupyingInfo);
    }


    @Transactional(readOnly = true)
    public SlideCoastOccupyingInfo getByFlightDynamicId(String flightDynamicId) {
        return dao.getByFlightDynamicId(flightDynamicId);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> findSlideCoastUsage(Map<String, Object> params) {
        return dao.findSlideCoastUsage(params);
    }

    public List<SlideCoastOccupyingInfo> fetchOciDatas(List<String> pairIds) {
        return dao.fetchOciDatas(pairIds);
    }

}