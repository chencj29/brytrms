/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.ShareFlight;

import java.util.List;

/**
 * 共享航班管理DAO接口
 * @author xiaopo
 * @version 2016-01-19
 */
@MyBatisDao
public interface ShareFlightDao extends CrudDao<ShareFlight> {

    List<ShareFlight> findByCondition(ShareFlight shareFlight);

    /**
     * 根据班期查询共享航班
     * @param sf
     * @return
     */
    List<ShareFlight> findByPeriod(ShareFlight sf);

    List<ShareFlight> findByDynamic(ShareFlight shareFlight);
}