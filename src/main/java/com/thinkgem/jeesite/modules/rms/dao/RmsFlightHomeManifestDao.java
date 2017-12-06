/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightHomeManifest;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightHomeManifestSec;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 本站仓单数据管理DAO接口
 * @author dingshuang
 * @version 2016-05-19
 */
@MyBatisDao
public interface RmsFlightHomeManifestDao extends CrudDao<RmsFlightHomeManifest> {


   List<RmsFlightHomeManifest> getHomeManifest(@Param("dynamicId") String dynamicId);

   List<RmsFlightHomeManifestSec> getManifestSecs(RmsFlightHomeManifestSec resultManifestSec);

   void saveManifestSecs(RmsFlightHomeManifestSec resultManifestSec);


   void updateManifestSecs(RmsFlightHomeManifestSec resultManifestSec);
}