/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.SpecialServices;

import java.util.List;

/**
 * 特殊服务记录表DAO接口
 * @author wjp
 * @version 2016-03-14
 */
@MyBatisDao
public interface SpecialServicesDao extends CrudDao<SpecialServices> {

    List<SpecialServices> getSpecialServicesList(SpecialServices specialServices);
}