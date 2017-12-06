/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;


import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.RmsBusinessNatureService;

/**
 * 岗位业务性质管理Entity
 *
 * @author xiaopo
 * @version 2016-05-18
 */

@Monitor(desc = "岗位业务性质管理信息", tableName = "rms_business_nature", service = RmsBusinessNatureService.class, socketNS = "/rms/rmsBusinessNature")
public class RmsBusinessNature extends DataEntity<RmsBusinessNature> {

    private static final long serialVersionUID = 1L;
    @Label("岗位ID")
    @MonitorField(desc = "岗位ID")
    private String postId;        // 岗位ID
    @Label("岗位名称")
    @MonitorField(desc = "岗位名称")
    private String postName;        // 岗位名称
    @Label("业务性质名称")
    @MonitorField(desc = "业务性质名称")
    private String bussinessNatureName;        // 业务性质名称
    @Label("备注")
    @MonitorField(desc = "备注")
    private String comments;        // 备注

    public RmsBusinessNature() {
        super();
    }

    public RmsBusinessNature(String id) {
        super(id);
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getBussinessNatureName() {
        return bussinessNatureName;
    }

    public void setBussinessNatureName(String bussinessNatureName) {
        this.bussinessNatureName = bussinessNatureName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}