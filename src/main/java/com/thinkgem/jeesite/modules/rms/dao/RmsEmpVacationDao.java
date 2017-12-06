/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.EmpWrapper;
import com.thinkgem.jeesite.modules.rms.entity.RmsEmpVacation;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工作人员请假管理DAO接口
 *
 * @author xiaopo
 * @version 2016-05-18
 */
@MyBatisDao
public interface RmsEmpVacationDao extends CrudDao<RmsEmpVacation> {

	public List<RmsEmpVacation> findVacationByDateAndEmpIds(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("jobNum") String jobNum);

	public List<EmpWrapper> findEmpWrapperByPostId(Map map);

}