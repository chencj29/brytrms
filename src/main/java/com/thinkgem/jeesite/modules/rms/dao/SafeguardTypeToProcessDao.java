/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.SafeguardTypeToProcess;
import com.thinkgem.jeesite.modules.rms.entity.StpVO;

import java.util.List;
import java.util.Map;

/**
 * 保障类型保障过程关联DAO接口
 * @author chrischen
 * @version 2016-03-17
 */
@MyBatisDao
public interface SafeguardTypeToProcessDao extends CrudDao<SafeguardTypeToProcess> {

    void deleteByRelate(SafeguardTypeToProcess safeguardTypeToProcess);

    List<StpVO> findStpVOList(SafeguardTypeToProcess safeguardTypeToProcess);

    void updateClearParams(SafeguardTypeToProcess safeguardTypeToProcess);

    void updateSort(Map map);
}