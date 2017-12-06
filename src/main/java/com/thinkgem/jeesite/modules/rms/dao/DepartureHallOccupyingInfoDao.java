/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import cn.net.metadata.annotation.Oci;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.CheckingCounterOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.entity.DepartureHallOccupyingInfo;

import java.util.List;
import java.util.Map;

/**
 * 候机厅占用信息DAO接口
 *
 * @author 候机厅占用信息
 * @version 2016-04-18
 */
@Oci
@MyBatisDao
public interface DepartureHallOccupyingInfoDao extends CrudDao<DepartureHallOccupyingInfo> {
    DepartureHallOccupyingInfo getByFlightDynamicId(String flightDynamicId);

    List<String> findCompatibleCodes(Map<String, Object> params);

    List<DepartureHallOccupyingInfo> fetchOciDatas(List<String> pairIds);
}