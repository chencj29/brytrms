/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.SafeguardDuration;

/**
 * 保障时长管理DAO接口
 * @author chrischen
 * @version 2016-03-16
 */
@MyBatisDao
public interface SafeguardDurationDao extends CrudDao<SafeguardDuration> {

    Long getLongTime(SafeguardDuration safeguardDuration);
}