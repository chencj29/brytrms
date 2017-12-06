/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.DutyHistory;

import java.util.List;

/**
 * 配载工作人员值班记录DAO接口
 * @author wjp
 * @version 2016-03-21
 */
@MyBatisDao
public interface DutyHistoryDao extends CrudDao<DutyHistory> {

    public List<DutyHistory> findListStatistics(DutyHistory dutyHistory);
	
}