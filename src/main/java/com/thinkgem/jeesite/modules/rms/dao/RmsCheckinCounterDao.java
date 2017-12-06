/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsCheckinCounter;

import java.util.List;

/**
 * 值机柜台基础信息表DAO接口
 *
 * @author wjp
 * @version 2016-03-07
 */
@MyBatisDao
public interface RmsCheckinCounterDao extends CrudDao<RmsCheckinCounter> {
    List<RmsCheckinCounter> findByCheckinCounterNum(List<String> list);

    RmsCheckinCounter getByCode(String checkinCounterCode);

    List<RmsCheckinCounter> findAvailableCheckingCounterByNature(String nature);
}