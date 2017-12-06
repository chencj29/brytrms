/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.AmsAddress;
import com.thinkgem.jeesite.modules.ams.entity.common.AutoCompleteParams;

import java.util.List;
import java.util.Map;

/**
 * 地名管理DAO接口
 *
 * @author chrischen
 * @version 2015-12-22
 */
@MyBatisDao
public interface AmsAddressDao extends CrudDao<AmsAddress> {

    public List<Map<String, Object>> findTypeCode(AutoCompleteParams autoCompleteParams);

	public AmsAddress getByThreeCode(AmsAddress address);

	public AmsAddress getByThreeCode(Map<String, String> params);


}