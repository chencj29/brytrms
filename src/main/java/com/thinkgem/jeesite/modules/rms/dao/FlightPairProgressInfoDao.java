/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.FlightPairProgressInfo;

import java.util.List;

/**
 * 航班运输保障信息DAO接口
 *
 * @author xiaowu
 * @version 2016-06-13
 */
@MyBatisDao
public interface FlightPairProgressInfoDao extends CrudDao<FlightPairProgressInfo> {
    List<FlightPairProgressInfo> queryByPairId(String pairId);

    boolean checkPairExists(String pairId);

    void deleteByPairId(String pairId);

    FlightPairProgressInfo getProgressInfo(FlightPairProgressInfo info);
}