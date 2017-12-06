/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.FlyTime;

/**
 * 飞越时间管理DAO接口
 * @author chrischen
 * @version 2016-02-04
 */
@MyBatisDao
public interface FlyTimeDao extends CrudDao<FlyTime> {
	
}