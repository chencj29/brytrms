/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.SafeguardProcess;

import java.util.List;

/**
 * 保障过程管理DAO接口
 * @author chrischen
 * @version 2016-03-16
 */
@MyBatisDao
public interface SafeguardProcessDao extends CrudDao<SafeguardProcess> {

    List<SafeguardProcess> getWaitSelectedPorcess(SafeguardProcess safeguardProcess);

    List<SafeguardProcess> getSelectedPorcess(SafeguardProcess safeguardProcess);

    List<SafeguardProcess> findListByCodeOrName(SafeguardProcess safeguardProcess);
}