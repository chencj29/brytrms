/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.EmergencyUnit;

/**
 * 应急救援单位信息DAO接口
 * @author wjp
 * @version 2016-04-06
 */
@MyBatisDao
public interface EmergencyUnitDao extends CrudDao<EmergencyUnit> {

    /**
     * 批量删除（一般为逻辑删除，更新del_flag字段为1）
     * @param ids
     * @return
     */
    public int batchDelete(String[] ids);
	
}