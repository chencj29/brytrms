/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import cn.net.metadata.annotation.Oci;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.BoardingGateOccupyingInfo;

import java.util.List;
import java.util.Map;

/**
 * 登机口占用信息DAO接口
 *
 * @author BigBrother5
 * @version 2016-04-11
 */
@Oci
@MyBatisDao
public interface BoardingGateOccupyingInfoDao extends CrudDao<BoardingGateOccupyingInfo> {
    BoardingGateOccupyingInfo getByFlightDynamicId(String flightDynamicId);

    List<Map<String, String>> findArrivalGateUsage(Map<String, Object> params);

    List<BoardingGateOccupyingInfo> fetchOciDatas(List<String> pairIds);
}