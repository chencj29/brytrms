/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.AocThirdParty;

import java.util.Map;

/**
 * 空管动态变更表DAO接口
 * @author wjp
 * @version 2017-05-22
 */
@MyBatisDao
public interface AocThirdPartyDao extends CrudDao<AocThirdParty> {
    void updateList(Map map);

    int isChackAll(Map map);
}