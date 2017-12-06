/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.ProgressPostPermission;
import com.thinkgem.jeesite.modules.rms.wrapper.ProgressPostPrivilegeWrapper;

import java.util.List;

/**
 * 权限设置DAO接口
 *
 * @author xiaowu
 * @version 2016-06-16
 */
@MyBatisDao
public interface ProgressPostPermissionDao extends CrudDao<ProgressPostPermission> {
    List<ProgressPostPrivilegeWrapper> findPrivileges(String postId);

    List<String> listPrivileges(String postId);

    void deleteByPostId(String postId);
}