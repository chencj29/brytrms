/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import cn.net.metadata.annotation.Oci;
import cn.net.metadata.wrapper.AverageResourceCalcWrapper;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.CheckingCounterOccupyingInfo;

import java.util.List;
import java.util.Map;

/**
 * 值机柜台占用信息DAO接口
 *
 * @author xiaowu
 * @version 2016-04-13
 */
@Oci
@MyBatisDao
public interface CheckingCounterOccupyingInfoDao extends CrudDao<CheckingCounterOccupyingInfo> {
    CheckingCounterOccupyingInfo getByFlightDynamicId(String flightDynamicId);

    List<AverageResourceCalcWrapper> findCompatibleCodes(Map<String, Object> params);

    List<CheckingCounterOccupyingInfo> fetchOciDatas(List<String> pairIds);

    void updateCheckinOciForAgent(Map<String, Object> params);
}