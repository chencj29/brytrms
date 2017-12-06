/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.SecurityPairWrapper;
import com.thinkgem.jeesite.modules.rms.entity.SecurityServiceInfo;

import java.util.List;
import java.util.Map;

/**
 * 安检航班信息DAO接口
 *
 * @author wjp
 * @version 2017-11-03
 */
@MyBatisDao
public interface SecurityServiceInfoDao extends CrudDao<SecurityServiceInfo> {

    List<SecurityPairWrapper> findListWapper(SecurityServiceInfo securityServiceInfo);

    List<SecurityServiceInfo> findByFlightDynamicIc(SecurityServiceInfo securityServiceInfo);

    List<SecurityPairWrapper> findListWapperByHistory(Map map);
}