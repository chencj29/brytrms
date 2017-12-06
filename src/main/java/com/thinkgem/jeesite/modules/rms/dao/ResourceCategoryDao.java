/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.ResourceCategory;

/**
 * 资源类别信息DAO接口
 * @author wjp
 * @version 2016-03-24
 */
@MyBatisDao
public interface ResourceCategoryDao extends CrudDao<ResourceCategory> {
	
}