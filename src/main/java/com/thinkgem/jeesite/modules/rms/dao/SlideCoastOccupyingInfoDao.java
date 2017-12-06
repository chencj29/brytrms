/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import cn.net.metadata.annotation.Oci;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.SlideCoastOccupyingInfo;

import java.util.List;
import java.util.Map;

/**
 * 滑槽占用信息DAO接口
 *
 * @author xiaowu
 * @version 2016-04-12
 */
@Oci
@MyBatisDao
public interface SlideCoastOccupyingInfoDao extends CrudDao<SlideCoastOccupyingInfo> {
    SlideCoastOccupyingInfo getByFlightDynamicId(String flightDynamicId);

    List<Map<String, String>> findSlideCoastUsage(Map<String, Object> params);

    List<SlideCoastOccupyingInfo> fetchOciDatas(List<String> pairIds);
}