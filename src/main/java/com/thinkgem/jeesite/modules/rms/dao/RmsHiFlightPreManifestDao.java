/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightPreManifest;

import java.util.List;

/**
 * 上站舱单数据管理DAO接口
 *
 * @author dingshuang
 * @version 2016-05-21
 */
@MyBatisDao
public interface RmsHiFlightPreManifestDao extends CrudDao<RmsFlightPreManifest> {


    List<RmsFlightPreManifest> getPreManifest(RmsFlightPreManifest rmsFlightPreManifest);

}