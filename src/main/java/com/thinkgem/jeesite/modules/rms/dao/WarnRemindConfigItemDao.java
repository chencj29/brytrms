/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.WarnRemindConfigItem;

/**
 * 预警提醒DAO接口
 * @author xiaowu
 * @version 2016-01-19
 */
@MyBatisDao
public interface WarnRemindConfigItemDao extends CrudDao<WarnRemindConfigItem> {
    void deletePhysical(String configId);
}