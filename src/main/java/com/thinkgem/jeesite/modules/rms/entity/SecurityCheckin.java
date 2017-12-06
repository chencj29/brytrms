/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.SecurityCheckinService;
import org.hibernate.validator.constraints.Length;

/**
 * 安检口基本信息Entity
 *
 * @author wjp
 * @version 2016-03-09
 */

@Monitor(desc = "安检口基本信息", tableName = "rms_security_checkin", service = SecurityCheckinService.class, socketNS = "/rms/securityCheckin")
public class SecurityCheckin extends DataEntity<SecurityCheckin> {

    private static final long serialVersionUID = 1L;
    @Label("安检口编号")
    @MonitorField(desc = "安检口编号")
    private String scecurityCheckinNum;        // 安检口编号
    @Label("安检口性质")
    @MonitorField(desc = "安检口性质")
    private String scecurityCheckinNature;        // 安检口性质
    @Label("安检口状态")
    @MonitorField(desc = "安检口状态")
    private String scecurityStatus;        // 安检口状态
    @Label("航站楼")
    @MonitorField(desc = "航站楼")
    private String aircraftTerminalCode;        // 航站楼

    @Label("排序字段")
    @MonitorField(desc = "排序字段")
    private Integer orderIndex;

    public SecurityCheckin() {
        super();
    }

    public SecurityCheckin(String id) {
        super(id);
    }

    @Length(min = 0, max = 10, message = "安检口编号长度必须介于 0 和 10 之间")
    public String getScecurityCheckinNum() {
        return scecurityCheckinNum;
    }

    public void setScecurityCheckinNum(String scecurityCheckinNum) {
        this.scecurityCheckinNum = scecurityCheckinNum;
    }

    @Length(min = 0, max = 20, message = "安检口性质长度必须介于 0 和 20 之间")
    public String getScecurityCheckinNature() {
        return scecurityCheckinNature;
    }

    public void setScecurityCheckinNature(String scecurityCheckinNature) {
        this.scecurityCheckinNature = scecurityCheckinNature;
    }

    @Length(min = 0, max = 1, message = "安检口状态长度必须介于 0 和 1 之间")
    public String getScecurityStatus() {
        return scecurityStatus;
    }

    public void setScecurityStatus(String scecurityStatus) {
        this.scecurityStatus = scecurityStatus;
    }

    @Length(min = 0, max = 10, message = "航站楼长度必须介于 0 和 10 之间")
    public String getAircraftTerminalCode() {
        return aircraftTerminalCode;
    }

    public void setAircraftTerminalCode(String aircraftTerminalCode) {
        this.aircraftTerminalCode = aircraftTerminalCode;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

}