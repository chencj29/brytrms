/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.WarnRemindConfigItemDao;
import com.thinkgem.jeesite.modules.rms.entity.WarnRemindConfigItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 预警提醒规则Service
 *
 * @author xiaowu
 * @version 2016-01-19
 */
@Service
@Transactional(readOnly = true)
public class WarnRemindConfigItemService extends CrudService<WarnRemindConfigItemDao, WarnRemindConfigItem> {

    public WarnRemindConfigItem get(String id) {
        return super.get(id);
    }

    public List<WarnRemindConfigItem> findList(WarnRemindConfigItem warnRemindConfigItem) {
        return super.findList(warnRemindConfigItem);
    }

    public Page<WarnRemindConfigItem> findPage(Page<WarnRemindConfigItem> page, WarnRemindConfigItem warnRemindConfigItem) {
        return super.findPage(page, warnRemindConfigItem);
    }

    @Transactional(readOnly = false)
    public void save(WarnRemindConfigItem warnRemindConfigItem) {
        super.save(warnRemindConfigItem);
    }

    @Transactional(readOnly = false)
    public void delete(WarnRemindConfigItem warnRemindConfigItem) {
        super.delete(warnRemindConfigItem);
    }

    public void deletePhysical(String configId) {
        dao.deletePhysical(configId);
    }

}