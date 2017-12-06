/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import cn.net.metadata.annotation.Oci;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.ArrivalGateOccupyingInfo;

import java.util.List;
import java.util.Map;

/**
 * 到港门占用信息DAO接口
 *
 * @author bb5
 * @version 2016-04-08
 */
@Oci
@MyBatisDao
public interface ArrivalGateOccupyingInfoDao extends CrudDao<ArrivalGateOccupyingInfo> {
    ArrivalGateOccupyingInfo getByFlightDynamicId(String flightDynamicId);

    List<Map<String, String>> findArrivalGateUsage(Map<String, Object> params);

    List<ArrivalGateOccupyingInfo> fetchOciDatas(List<String> pairIds);
}