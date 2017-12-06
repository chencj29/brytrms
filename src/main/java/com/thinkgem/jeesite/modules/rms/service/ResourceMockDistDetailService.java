/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.ResourceMockDistDetailDao;
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 资源模拟分配详情Service
 *
 * @author BigBrother5
 * @version 2017-03-07
 */
@Service
@Transactional(readOnly = true)
public class ResourceMockDistDetailService extends CrudService<ResourceMockDistDetailDao, ResourceMockDistDetail> {

    public ResourceMockDistDetail get(String id) {
        return super.get(id);
    }

    public List<ResourceMockDistDetail> findList(ResourceMockDistDetail resourceMockDistDetail) {
        return super.findList(resourceMockDistDetail);
    }

    public Page<ResourceMockDistDetail> findPage(Page<ResourceMockDistDetail> page, ResourceMockDistDetail resourceMockDistDetail) {
        return super.findPage(page, resourceMockDistDetail);
    }

    @Transactional
    public void save(ResourceMockDistDetail resourceMockDistDetail) {
        super.save(resourceMockDistDetail);
    }

    @Transactional
    public void delete(ResourceMockDistDetail resourceMockDistDetail) {
        super.delete(resourceMockDistDetail);
    }

    @Transactional(readOnly = true)
    public List<ResourceMockDistDetail> findByInfoId(Map<String, Object> params) {
        return dao.findByInfoId(params);
    }

    @Transactional
    public Long fetchDetailCount(Map<String, Object> params) {
        return dao.fetchDetailCount(params);
    }

    @Transactional
    public void deleteByInfoId(String infoId) {
        dao.deleteByInfoId(infoId);
    }
}