/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.rms.dao.WarnRemindConfigDao;
import com.thinkgem.jeesite.modules.rms.dao.WarnRemindConfigItemDao;
import com.thinkgem.jeesite.modules.rms.entity.WarnRemindConfig;
import com.thinkgem.jeesite.modules.rms.entity.WarnRemindConfigItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 预警提醒Service
 *
 * @author xiaowu
 * @version 2016-01-19
 */
@Service
@Transactional(readOnly = true)
public class WarnRemindConfigService extends CrudService<WarnRemindConfigDao, WarnRemindConfig> {

    @Autowired
    private WarnRemindConfigItemDao warnRemindConfigItemDao;
    @Autowired
    private WarnRemindConfigItemService itemService;

    public WarnRemindConfig get(String id) {
        WarnRemindConfig warnRemindConfig = super.get(id);
        warnRemindConfig.setWarnRemindConfigItemList(warnRemindConfigItemDao.findList(new WarnRemindConfigItem(warnRemindConfig)));
        return warnRemindConfig;
    }

    public List<WarnRemindConfig> findList(WarnRemindConfig warnRemindConfig) {
        return super.findList(warnRemindConfig);
    }

    public Page<WarnRemindConfig> findPage(Page<WarnRemindConfig> page, WarnRemindConfig warnRemindConfig) {
        return super.findPage(page, warnRemindConfig);
    }

    @Transactional(readOnly = false)
    public void save(WarnRemindConfig warnRemindConfig) {
        super.save(warnRemindConfig);
        for (WarnRemindConfigItem warnRemindConfigItem : warnRemindConfig.getWarnRemindConfigItemList()) {
            if (warnRemindConfigItem.getId() == null) {
                continue;
            }
            if (WarnRemindConfigItem.DEL_FLAG_NORMAL.equals(warnRemindConfigItem.getDelFlag())) {
                if (StringUtils.isBlank(warnRemindConfigItem.getId())) {
                    warnRemindConfigItem.setConfig(warnRemindConfig);
                    warnRemindConfigItem.preInsert();
                    warnRemindConfigItemDao.insert(warnRemindConfigItem);
                } else {
                    warnRemindConfigItem.preUpdate();
                    warnRemindConfigItemDao.update(warnRemindConfigItem);
                }
            } else {
                warnRemindConfigItemDao.delete(warnRemindConfigItem);
            }
        }
    }

    @Transactional(readOnly = false)
    public void delete(WarnRemindConfig warnRemindConfig) {
        super.delete(warnRemindConfig);
        warnRemindConfigItemDao.delete(new WarnRemindConfigItem(warnRemindConfig));
    }

    @Transactional(readOnly = false)
    public void deletePhysical(String configId, String type) {
        if (StringUtils.inString(type, "d", "c")) itemService.deletePhysical(configId);
        if (StringUtils.equalsIgnoreCase("c", type)) dao.deletePhysical(configId);
    }

    @Transactional(readOnly = false)
    public void deletePhysical(String configId) {
        itemService.deletePhysical(configId);
        dao.deletePhysical(configId);
    }

    public List<WarnRemindConfig> getConfigsByVairables(Map<String, Object> variables) {
        return dao.getConfigsByVairables(variables);
    }

    public List<WarnRemindConfig> getWarnConfigs() {
        return dao.getWarnConfigs();
    }

    public Integer checkEventItemExist(Map<String, Object> vars) {
        return dao.checkEventItemExist(vars);
    }

}