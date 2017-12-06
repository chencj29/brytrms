/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.SafeguardSeatTimelong;

import java.util.List;

/**
 * 保障过程及座位时长对应DAO接口
 * @author xiaopo
 * @version 2016-03-29
 */
@MyBatisDao
public interface SafeguardSeatTimelongDao extends CrudDao<SafeguardSeatTimelong> {
	void deleteByTypeProcess(SafeguardSeatTimelong safeguardSeatTimelong);


	List<SafeguardSeatTimelong> queryListBySeatSafeguardTypeCode(SafeguardSeatTimelong safeguardSeatTimelong);
}