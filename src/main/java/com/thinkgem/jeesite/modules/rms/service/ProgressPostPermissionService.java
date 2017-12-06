/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.ProgressPostPermissionDao;
import com.thinkgem.jeesite.modules.rms.entity.ProgressPostPermission;
import com.thinkgem.jeesite.modules.rms.wrapper.ProgressPostPrivilegeWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 权限设置Service
 *
 * @author xiaowu
 * @version 2016-06-16
 */
@Service
@Transactional(readOnly = true)
public class ProgressPostPermissionService extends CrudService<ProgressPostPermissionDao, ProgressPostPermission> {

    public ProgressPostPermission get(String id) {
        return super.get(id);
    }

    public List<ProgressPostPermission> findList(ProgressPostPermission progressPostPermission) {
        return super.findList(progressPostPermission);
    }

    public Page<ProgressPostPermission> findPage(Page<ProgressPostPermission> page, ProgressPostPermission progressPostPermission) {
        return super.findPage(page, progressPostPermission);
    }

    @Transactional
    public void save(ProgressPostPermission progressPostPermission) {
        super.save(progressPostPermission);
    }

    @Transactional
    public void delete(ProgressPostPermission progressPostPermission) {
        super.delete(progressPostPermission);
    }

    @Transactional(readOnly = true)
    public List<ProgressPostPrivilegeWrapper> findPrivileges(String postId) {
        return dao.findPrivileges(postId);
    }

    @Transactional
    public void deleteByPostId(String postId) {
        dao.deleteByPostId(postId);
    }

    @Transactional(readOnly = true)
    public List<String> listPrivileges(String postId) {
        return dao.listPrivileges(postId);
    }

}