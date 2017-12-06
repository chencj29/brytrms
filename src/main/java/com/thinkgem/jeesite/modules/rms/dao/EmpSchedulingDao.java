/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.EmpScheduling;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工作人员排班管理DAO接口
 *
 * @author xiaopo
 * @version 2016-05-19
 */
@MyBatisDao
public interface EmpSchedulingDao extends CrudDao<EmpScheduling> {

	public void deleteByDateAndPostId(@Param("nowDate") String nowDate, @Param("postId") String postId);

	public List<EmpScheduling> findListStatistics(EmpScheduling empScheduling);
}