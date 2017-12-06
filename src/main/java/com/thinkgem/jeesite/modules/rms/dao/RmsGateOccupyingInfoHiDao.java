/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsGateOccupyingInfoHi;

/**
 * 机位占用信息历史DAO接口
 * @author wjp
 * @version 2016-05-16
 */
@MyBatisDao
public interface RmsGateOccupyingInfoHiDao extends CrudDao<RmsGateOccupyingInfoHi> {
	
}