/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.ShareFlightDynamic;

/**
 * 共享航班与航班动态关联DAO接口
 * @author chrischen
 * @version 2016-02-03
 */
@MyBatisDao
@Deprecated
public interface ShareFlightDynamicDao extends CrudDao<ShareFlightDynamic> {

    /**
     * 清空所有的关联关系
     */
    void deleteAll();

    void deleteByShareFlightId(ShareFlightDynamic shareFlightDynamic);
}