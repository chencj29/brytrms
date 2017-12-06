/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import cn.net.metadata.annotation.Oci;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.SecurityCheckinOccupyingInfo;

import java.util.List;
import java.util.Map;

/**
 * 安检口占用信息DAO接口
 *
 * @author xiaowu
 * @version 2016-04-18
 */
@Oci
@MyBatisDao
public interface SecurityCheckinOccupyingInfoDao extends CrudDao<SecurityCheckinOccupyingInfo> {
    SecurityCheckinOccupyingInfo getByFlightDynamicId(String flightDynamicId);

    List<String> findCompatibleCodes(Map<String, Object> params);

    List<SecurityCheckinOccupyingInfo> fetchOciDatas(List<String> pairIds);
}