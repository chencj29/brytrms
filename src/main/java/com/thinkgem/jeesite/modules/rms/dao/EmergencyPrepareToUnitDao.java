/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.EmergencyPrepareToUnit;

import java.util.List;
import java.util.Map;

/**
 * 应急救援预案救援单位关联表DAO接口
 * @author wjp
 * @version 2016-04-08
 */
@MyBatisDao
public interface EmergencyPrepareToUnitDao extends CrudDao<EmergencyPrepareToUnit> {
    /**
     * 通过epid查询救援单位的id及其名称
     * @param epid
     * @return
     */
    public List<Map> findByEUNames(String epid);

    /**
     * 删除所有epid的数据（一般为逻辑删除，更新del_flag字段为1）
     * @param epid
     * @return
     */
    public int EPDelete(String epid);
}