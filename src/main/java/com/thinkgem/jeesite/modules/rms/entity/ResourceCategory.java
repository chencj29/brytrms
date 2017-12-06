/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.ResourceCategoryService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 资源类别信息Entity
 *
 * @author wjp
 * @version 2016-03-24
 */

@Monitor(desc = "资源类别信息", tableName = "rms_resource_category", service = ResourceCategoryService.class, socketNS = "/rms/resourceCategory")
public class ResourceCategory extends DataEntity<ResourceCategory> {

    private static final long serialVersionUID = 1L;
    @Label("创建时间")
    @MonitorField(desc = "创建时间")
    private Date createTime;        // 创建时间
    @Label("更新时间")
    @MonitorField(desc = "更新时间")
    private Date updateTime;        // 更新时间
    @Label("类别ID")
    @MonitorField(desc = "类别ID")
    private String categoryId;        // 类别ID
    @Label("名称")
    @MonitorField(desc = "名称")
    private String categoryName;        // 名称
    @Label("类别")
    @MonitorField(desc = "类别")
    private String categoryType;        // 类别

    public ResourceCategory() {
        super();
    }

    public ResourceCategory(String id) {
        super(id);
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Length(min = 0, max = 20, message = "类别ID长度必须介于 0 和 20 之间")
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Length(min = 0, max = 100, message = "名称长度必须介于 0 和 100 之间")
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Length(min = 0, max = 10, message = "类别长度必须介于 0 和 10 之间")
    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

}