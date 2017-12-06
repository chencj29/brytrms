/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.SafeguardType;

import java.util.List;

/**
 * 保障类型管理DAO接口
 * @author chrischen
 * @version 2016-03-15
 */
@MyBatisDao
public interface SafeguardTypeDao extends CrudDao<SafeguardType> {

    /**
     * 根据父类型编号查询
     * @param safeguardType
     * @return
     */
    List<SafeguardType> findByParentCode(SafeguardType safeguardType);

    List<SafeguardType> getSubList();
}