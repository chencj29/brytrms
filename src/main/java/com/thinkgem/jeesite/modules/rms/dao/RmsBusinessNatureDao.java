/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsBusinessNature;

/**
 * 岗位业务性质管理DAO接口
 * @author xiaopo
 * @version 2016-05-18
 */
@MyBatisDao
public interface RmsBusinessNatureDao extends CrudDao<RmsBusinessNature> {
	
}