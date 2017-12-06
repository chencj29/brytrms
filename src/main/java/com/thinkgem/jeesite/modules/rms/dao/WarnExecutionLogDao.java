package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.WarnExecutionLog;

/**
 * Created by xiaowu on 16/1/28.
 */
@MyBatisDao
public interface WarnExecutionLogDao extends CrudDao<WarnExecutionLog> {
}
