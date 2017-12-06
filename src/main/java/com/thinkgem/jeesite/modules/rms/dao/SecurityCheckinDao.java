/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.SecurityCheckin;

import java.util.List;

/**
 * 安检口基本信息DAO接口
 *
 * @author wjp
 * @version 2016-03-09
 */
@MyBatisDao
public interface SecurityCheckinDao extends CrudDao<SecurityCheckin> {

    List<SecurityCheckin> findBySecurityCheckinNum(List<String> list);

    SecurityCheckin getByCode(String securityCheckinCode);

    List<SecurityCheckin> findAvailableSecurityCheckinNature(String nature);
}