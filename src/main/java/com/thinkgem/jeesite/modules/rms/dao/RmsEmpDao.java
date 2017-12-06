/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsEmp;
import org.apache.ibatis.annotations.Param;

/**
 * 工作人员基础信息DAO接口
 * @author wjp
 * @version 2016-03-09
 */
@MyBatisDao
public interface RmsEmpDao extends CrudDao<RmsEmp> {

	public RmsEmp findEmpByJobNum(@Param("jobNum") String jobNum);
	
}