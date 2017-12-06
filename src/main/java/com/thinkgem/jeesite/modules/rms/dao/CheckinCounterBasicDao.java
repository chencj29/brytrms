/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.CheckinCounterBasic;

/**
 * 值机柜台基础信息表(基础专用)DAO接口
 * @author wjp
 * @version 2017-09-22
 */
@MyBatisDao
public interface CheckinCounterBasicDao extends CrudDao<CheckinCounterBasic> {
	
}