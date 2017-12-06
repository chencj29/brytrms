/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.DockPrepareList;

/**
 * 航班预配单信息DAO接口
 * @author wjp
 * @version 2016-03-21
 */
@MyBatisDao
public interface DockPrepareListDao extends CrudDao<DockPrepareList> {
	
}