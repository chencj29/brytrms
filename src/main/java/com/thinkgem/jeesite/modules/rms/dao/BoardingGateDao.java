/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.BoardingGate;

import java.util.List;

/**
 * 登机口信息DAO接口
 *
 * @author wjp
 * @version 2016-03-19
 */
@MyBatisDao
public interface BoardingGateDao extends CrudDao<BoardingGate> {
    List<BoardingGate> findByBoardingGateNum(List<String> list);

    BoardingGate getByCode(String boardingGateCode);

    List<BoardingGate> findAvailableBoardingGateByNature(String nature);
}