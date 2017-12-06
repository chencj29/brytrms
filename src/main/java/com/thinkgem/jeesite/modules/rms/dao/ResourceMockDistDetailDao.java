/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistDetail;

import java.util.List;
import java.util.Map;

/**
 * 资源模拟分配详情DAO接口
 *
 * @author BigBrother5
 * @version 2017-03-07
 */
@MyBatisDao
public interface ResourceMockDistDetailDao extends CrudDao<ResourceMockDistDetail> {
    List<ResourceMockDistDetail> findByInfoId(Map<String, Object> params);

    void deleteByInfoId(String infoId);

    Long fetchDetailCount(Map<String, Object> params);
}