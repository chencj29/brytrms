/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.AirportTerminal;

/**
 * 航站楼信息DAO接口
 * @author hxx
 * @version 2016-01-26
 */
@MyBatisDao
public interface AirportTerminalDao extends CrudDao<AirportTerminal> {
	
}