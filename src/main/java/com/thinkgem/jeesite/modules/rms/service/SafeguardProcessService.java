/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.SafeguardProcess;
import com.thinkgem.jeesite.modules.rms.dao.SafeguardProcessDao;

/**
 * 保障过程管理Service
 *
 * @author chrischen
 * @version 2016-03-16
 */
@Service
@Transactional(readOnly = true)
public class SafeguardProcessService extends CrudService<SafeguardProcessDao, SafeguardProcess> {

    public SafeguardProcess get(String id) {
        return super.get(id);
    }

    public List<SafeguardProcess> findList(SafeguardProcess safeguardProcess) {
        return super.findList(safeguardProcess);
    }

    public Page<SafeguardProcess> findPage(Page<SafeguardProcess> page, SafeguardProcess safeguardProcess) {
        return super.findPage(page, safeguardProcess);
    }

    @Transactional(readOnly = false)
    public void save(SafeguardProcess safeguardProcess) {
        super.save(safeguardProcess);
    }

    @Transactional(readOnly = false)
    public void delete(SafeguardProcess safeguardProcess) {
        super.delete(safeguardProcess);
    }

    @Transactional(readOnly = true)
    public List<SafeguardProcess> getWaitSelectedPorcess(SafeguardProcess safeguardProcess) {
        if (StringUtils.isNotBlank(safeguardProcess.getId())) {
            return super.dao.getWaitSelectedPorcess(safeguardProcess);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<SafeguardProcess> getSelectedPorcess(SafeguardProcess safeguardProcess) {
        if (StringUtils.isNotBlank(safeguardProcess.getId())) {
            return super.dao.getSelectedPorcess(safeguardProcess);
        }
        return null;
    }

    //获取保障过程中的颜色信息
    public Map<String,Map<String,String>> getColor(){
        Map<String,Map<String,String>> result = new HashMap<>();
        findList(new SafeguardProcess()).stream().forEach(entity->{
            Map map = new HashMap();
            map.put("planColor", entity.getPlanColor());
            map.put("actualColor", entity.getActualColor());
            if (StringUtils.isNotBlank(entity.getSafeguardProcessCode()))
                result.put(entity.getSafeguardProcessCode(), map);
        });
        return result;
    }
}