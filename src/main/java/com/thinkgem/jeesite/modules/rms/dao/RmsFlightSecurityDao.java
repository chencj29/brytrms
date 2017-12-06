/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightSecurity;

import java.util.List;
import java.util.Map;

/**
 * 安检旅客信息DAO接口
 *
 * @author dingshuang
 * @version 2016-05-21
 */
@MyBatisDao
public interface RmsFlightSecurityDao extends CrudDao<RmsFlightSecurity> {
    List<RmsFlightSecurity> findListByHistory(Map map);
}