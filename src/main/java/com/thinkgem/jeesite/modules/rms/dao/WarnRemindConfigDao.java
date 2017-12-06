/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.WarnRemindConfig;

import java.util.List;
import java.util.Map;

/**
 * 预警提醒DAO接口
 *
 * @author xiaowu
 * @version 2016-01-19
 */
@MyBatisDao
public interface WarnRemindConfigDao extends CrudDao<WarnRemindConfig> {
    List<WarnRemindConfig> getConfigsByVairables(Map<String, Object> vars);

    List<WarnRemindConfig> getWarnConfigs();

    Integer checkEventItemExist(Map<String, Object> vars);

    void deletePhysical(String configId);
}