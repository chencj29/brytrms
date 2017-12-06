/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import cn.net.metadata.annotation.Oci;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.GateOccupyingInfo;

import java.util.List;

/**
 * 机位占用信息DAO接口
 *
 * @author xiaowu
 * @version 2016-03-16
 */
@Oci
@MyBatisDao
public interface GateOccupyingInfoDao extends CrudDao<GateOccupyingInfo> {

    GateOccupyingInfo getByFlightDynamicId(String flightDynamicId);

    List<GateOccupyingInfo> fetchOciDatas(List<String> pairIds);

}