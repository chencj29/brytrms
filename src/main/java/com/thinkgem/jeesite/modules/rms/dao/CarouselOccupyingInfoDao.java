/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import cn.net.metadata.annotation.Oci;
import cn.net.metadata.wrapper.AverageResourceCalcWrapper;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.CarouselOccupyingInfo;

import java.util.List;
import java.util.Map;

/**
 * 行李转盘占用信息DAO接口
 *
 * @author xiaowu
 * @version 2016-03-28
 */
@Oci
@MyBatisDao
public interface CarouselOccupyingInfoDao extends CrudDao<CarouselOccupyingInfo> {
    List<Map<String, String>> findCarouselUsage(Map<String, Object> params);

    CarouselOccupyingInfo getByFlightDynamicId(String flightDynamicId);

    List<CarouselOccupyingInfo> fetchOciDatas(List<String> pairIds);

    List<AverageResourceCalcWrapper> findCompatibleCodes(Map<String, Object> params);
}