/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightOverManifest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 过站仓单数据管理DAO接口
 * @author dingshuang
 * @version 2016-05-20
 */
@MyBatisDao
public interface RmsFlightOverManifestDao extends CrudDao<RmsFlightOverManifest> {

    List<RmsFlightOverManifest> getOverManifest(@Param("flightDynamicId") String flightDynamicId);
	
}