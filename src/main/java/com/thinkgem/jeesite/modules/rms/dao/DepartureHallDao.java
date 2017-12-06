/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.DepartureHall;

import java.util.List;

/**
 * 候机厅基础信息DAO接口
 *
 * @author wjp
 * @version 2016-03-19
 */
@MyBatisDao
public interface DepartureHallDao extends CrudDao<DepartureHall> {
    List<DepartureHall> findByDepartureHallNum(List<String> list);

    List<DepartureHall> findAvailableDepartureHallByNature(String nature);

    DepartureHall getByCode(String departureHallCode);
}