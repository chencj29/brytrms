/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.CheckAspect;

import java.util.Map;

/**
 * 指挥中心审核操作DAO接口
 * @author wjp
 * @version 2017-03-20
 */
@MyBatisDao
public interface CheckAspectDao extends CrudDao<CheckAspect> {

    void updateList(Map map);

    int isChackAll(Map map);
}