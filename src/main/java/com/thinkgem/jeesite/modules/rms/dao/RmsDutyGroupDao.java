/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsDutyGroup;
import org.apache.ibatis.annotations.Param;

/**
 * 岗位班组管理DAO接口
 * @author xiaopo
 * @version 2016-05-19
 */
@MyBatisDao
public interface RmsDutyGroupDao extends CrudDao<RmsDutyGroup> {

	public int findDuplicateDutyGroup(@Param("startDate") String startDate, @Param("endDate") String endDate,@Param("postId") String postId);
	
}