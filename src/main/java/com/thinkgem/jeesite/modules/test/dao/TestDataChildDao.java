/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.test.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.test.entity.TestDataChild;

/**
 * 主子表生成DAO接口
 * @author ThinkGem
 * @version 2015-12-12
 */
@MyBatisDao
public interface TestDataChildDao extends CrudDao<TestDataChild> {
	
}