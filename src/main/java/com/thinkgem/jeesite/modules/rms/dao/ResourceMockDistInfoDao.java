/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistInfo;

import java.util.List;
import java.util.Map;

/**
 * 资源模拟分配DAO接口
 *
 * @author BigBrother5
 * @version 2017-03-09
 */
@MyBatisDao
public interface ResourceMockDistInfoDao extends CrudDao<ResourceMockDistInfo> {
    List<ResourceMockDistInfo> listByUser(Map<String, Object> params);
}