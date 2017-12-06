/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.IrregularServices;

/**
 * 不正常服务记录DAO接口
 * @author wjp
 * @version 2016-03-19
 */
@MyBatisDao
public interface IrregularServicesDao extends CrudDao<IrregularServices> {
	
}