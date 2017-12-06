/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.SlideCoastService;
import org.hibernate.validator.constraints.Length;

/**
 * 滑槽基础信息Entity
 *
 * @author wjp
 * @version 2016-03-19
 */

@Monitor(desc = "滑槽基础信息", tableName = "rms_slide_coast", service = SlideCoastService.class, socketNS = "/rms/slideCoast")
public class SlideCoast extends DataEntity<SlideCoast> {

    private static final long serialVersionUID = 1L;
    @Label("滑槽编号")
    @MonitorField(desc = "滑槽编号")
    private String slideCoastNum;        // 滑槽编号
    @Label("滑槽性质")
    @MonitorField(desc = "滑槽性质")
    private String slideCoastNature;        // 滑槽性质
    @Label("滑槽状态")
    @MonitorField(desc = "滑槽状态")
    private String slideCoastStatus;        // 滑槽状态
    @Label("航站楼")
    @MonitorField(desc = "航站楼")
    private String aircraftTermainalCode;        // 航站楼

    @Label("排序字段")
    @MonitorField(desc = "排序字段")
    private Integer orderIndex;

    public SlideCoast() {
        super();
    }

    public SlideCoast(String id) {
        super(id);
    }

    @Length(min = 0, max = 10, message = "滑槽编号长度必须介于 0 和 10 之间")
    public String getSlideCoastNum() {
        return slideCoastNum;
    }

    public void setSlideCoastNum(String slideCoastNum) {
        this.slideCoastNum = slideCoastNum;
    }

    @Length(min = 0, max = 10, message = "滑槽性质长度必须介于 0 和 10 之间")
    public String getSlideCoastNature() {
        return slideCoastNature;
    }

    public void setSlideCoastNature(String slideCoastNature) {
        this.slideCoastNature = slideCoastNature;
    }

    @Length(min = 0, max = 1, message = "滑槽状态长度必须介于 0 和 1 之间")
    public String getSlideCoastStatus() {
        return slideCoastStatus;
    }

    public void setSlideCoastStatus(String slideCoastStatus) {
        this.slideCoastStatus = slideCoastStatus;
    }

    @Length(min = 0, max = 10, message = "航站楼长度必须介于 0 和 10 之间")
    public String getAircraftTermainalCode() {
        return aircraftTermainalCode;
    }

    public void setAircraftTermainalCode(String aircraftTermainalCode) {
        this.aircraftTermainalCode = aircraftTermainalCode;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

}