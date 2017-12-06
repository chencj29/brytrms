/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightDuty;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 值机数据管理DAO接口
 * @author dingshuang
 * @version 2016-05-18
 */
@MyBatisDao
public interface RmsFlightDutyDao extends CrudDao<RmsFlightDuty> {

    public List<RmsFlightDuty> getHomeFlightDuty(@Param("dynamicId") String dynamicId);

    public List<RmsFlightDuty>  getFlightDutys(RmsFlightDuty rmsFlightDuty);

    List<RmsFlightDuty> getFlightDutysByFlightDynamicId(@Param("dynamicId") String dynamicId);

    List<RmsFlightDuty> findByFdidAndAddr(RmsFlightDuty rmsFlightDuty);
}