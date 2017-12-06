/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.SafeguardResource;

/**
 * 保障资源表DAO接口
 * @author wjp
 * @version 2016-04-15
 */
@MyBatisDao
public interface SafeguardResourceDao extends CrudDao<SafeguardResource> {
	
}