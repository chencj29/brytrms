/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.rms.service.ResourceMockDistInfoService;

import java.util.Date;

/**
 * 资源模拟分配Entity
 *
 * @author BigBrother5
 * @version 2017-03-09
 */

@Monitor(desc = "资源模拟分配信息", tableName = "rms_resource_mock_dist_info", service = ResourceMockDistInfoService.class, socketNS = "/rms/resourceMockDistInfo")
public class ResourceMockDistInfo extends DataEntity<ResourceMockDistInfo> {

    private static final long serialVersionUID = 1L;
    @Label("开始日期")
    @MonitorField(desc = "开始日期")
    private Date mockStartDate;        // 开始日期
    @Label("资源包代码")
    @MonitorField(desc = "资源包代码")
    private String packageCode;        // 资源包代码
    @Label("资源包名称")
    @MonitorField(desc = "资源包名称")
    private String packageName;        // 资源包名称
    @Label("是否过滤已分配的资源")
    @MonitorField(desc = "是否过滤已分配的资源")
    private boolean filterDistedRes;        // 是否过滤已分配的资源
    @Label("资源类型")
    @MonitorField(desc = "资源类型")
    private String resourceType;        // 资源类型
    @Label("是否忽略当前占用数据")
    @MonitorField(desc = "是否忽略当前占用数据")
    private boolean omitOciDatas;        // 是否忽略当前占用数据
    @Label("结束日期")
    @MonitorField(desc = "结束日期")
    private Date mockOverDate;        // 结束日期

    public ResourceMockDistInfo() {
        super();
    }

    public ResourceMockDistInfo(String id) {
        super(id);
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getMockStartDate() {
        return mockStartDate;
    }

    public void setMockStartDate(Date mockStartDate) {
        this.mockStartDate = mockStartDate;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean getFilterDistedRes() {
        return filterDistedRes;
    }

    public void setFilterDistedRes(boolean filterDistedRes) {
        this.filterDistedRes = filterDistedRes;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public boolean getOmitOciDatas() {
        return omitOciDatas;
    }

    public void setOmitOciDatas(boolean omitOciDatas) {
        this.omitOciDatas = omitOciDatas;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getMockOverDate() {
        return mockOverDate;
    }

    public void setMockOverDate(Date mockOverDate) {
        this.mockOverDate = mockOverDate;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("[");
        if(mockStartDate != null && mockOverDate != null) {
            sb.append("模拟日期:").append(DateUtils.formatDate(mockStartDate,"yyyy-MM-dd HH:mm")).append("~");
            sb.append(DateUtils.formatDate(mockOverDate,"yyyy-MM-dd HH:mm"));
        }
        sb.append(",资源包名称:").append(packageName);
        sb.append(", 资源类型:").append(resourceType);
        sb.append(", 过滤已分配资源:").append(filterDistedRes?"是":"否");
        sb.append(", 忽略占用数据:").append(omitOciDatas?"是":"否");
        sb.append(']');
        return sb.toString();
    }
}