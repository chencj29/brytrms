/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.YesterAircraftNumPair;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 单进航班次日无任务机位表DAO接口
 *
 * @author wjp
 * @version 2017-09-13
 */
@MyBatisDao
public interface YesterAircraftNumPairDao extends CrudDao<YesterAircraftNumPair> {
    Integer updateStatus(Map<String, Object> param);

    Integer updateStatusByNull(Map<String, Object> param);

    List<YesterAircraftNumPair> findListByStatus(YesterAircraftNumPair yesterAircraftNumPair);

    List<YesterAircraftNumPair> findListByPlanDate(YesterAircraftNumPair yesterAircraftNumPair);

    //查询机位对应的停场飞机机号
    List<YesterAircraftNumPair> findListByOci(@Param("placeNum") String placeNum);

    int updateByPlaceStatuse(YesterAircraftNumPair yesterAircraftNumPair);
}