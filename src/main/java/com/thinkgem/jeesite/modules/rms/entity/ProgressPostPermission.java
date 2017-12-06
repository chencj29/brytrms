/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;


import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.ProgressPostPermissionService;

/**
 * 权限设置Entity
 *
 * @author xiaowu
 * @version 2016-06-16
 */

@Monitor(desc = "权限设置信息", tableName = "rms_progress_perm", service = ProgressPostPermissionService.class, socketNS = "/rms/progressPostPermission")
public class ProgressPostPermission extends DataEntity<ProgressPostPermission> {

    private static final long serialVersionUID = 1L;
    @Label("保障过程ID")
    @MonitorField(desc = "保障过程ID")
    private String progressId;        // 保障过程ID
    @Label("岗位ID")
    @MonitorField(desc = "岗位ID")
    private String postId;        // 岗位ID

    public ProgressPostPermission() {
        super();
    }

    public ProgressPostPermission(String id) {
        super(id);
    }

    public String getProgressId() {
        return progressId;
    }

    public void setProgressId(String progressId) {
        this.progressId = progressId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

}