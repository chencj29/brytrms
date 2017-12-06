/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.SafeguardDurationService;
import org.hibernate.validator.constraints.Length;

/**
 * 保障时长管理Entity
 *
 * @author chrischen
 * @version 2016-03-16
 */

@Monitor(desc = "保障时长管理信息", tableName = "rms_safeguard_duration", service = SafeguardDurationService.class, socketNS = "/rms/safeguardDuration")
public class SafeguardDuration extends DataEntity<SafeguardDuration> {

    private static final long serialVersionUID = 1L;
    @Label("保障类型ID")
    @MonitorField(desc = "保障类型ID")
    private String safeguardTypeId;        // 保障类型ID
    @Label("保障类型编号")
    @MonitorField(desc = "保障类型编号")
    private String safeguardTypeCode;        // 保障类型编号
    @Label("保障类型名称")
    @MonitorField(desc = "保障类型名称")
    private String safeguardTypeName;        // 保障类型名称
    @Label("最小座位数")
    @MonitorField(desc = "最小座位数")
    private Long minSeating;        // 最小座位数
    @Label("最大座位数")
    @MonitorField(desc = "最大座位数")
    private Long maxSeating;        // 最大座位数
    @Label("时长")
    @MonitorField(desc = "时长")
    private Long timeLength;        // 时长
    @Label("冗余1")
    @MonitorField(desc = "冗余1")
    private String redundant1;        // 冗余1
    @Label("冗余2")
    @MonitorField(desc = "冗余2")
    private String redundant2;        // 冗余2
    @Label("冗余3")
    @MonitorField(desc = "冗余3")
    private String redundant3;        // 冗余3
    @Label("冗余4")
    @MonitorField(desc = "冗余4")
    private String redundant4;        // 冗余4
    @Label("冗余5")
    @MonitorField(desc = "冗余5")
    private String redundant5;        // 冗余5
    @Label("冗余6")
    @MonitorField(desc = "冗余6")
    private String redundant6;        // 冗余6
    @Label("冗余7")
    @MonitorField(desc = "冗余7")
    private String redundant7;        // 冗余7
    @Label("冗余8")
    @MonitorField(desc = "冗余8")
    private String redundant8;        // 冗余8
    @Label("冗余9")
    @MonitorField(desc = "冗余9")
    private String redundant9;        // 冗余9

    public SafeguardDuration() {
        super();
    }

    public SafeguardDuration(String id) {
        super(id);
    }

    @Length(min = 0, max = 36, message = "保障类型ID长度必须介于 0 和 36 之间")
    public String getSafeguardTypeId() {
        return safeguardTypeId;
    }

    public void setSafeguardTypeId(String safeguardTypeId) {
        this.safeguardTypeId = safeguardTypeId;
    }

    @Length(min = 0, max = 10, message = "保障类型编号长度必须介于 0 和 10 之间")
    public String getSafeguardTypeCode() {
        return safeguardTypeCode;
    }

    public void setSafeguardTypeCode(String safeguardTypeCode) {
        this.safeguardTypeCode = safeguardTypeCode;
    }

    public Long getMinSeating() {
        return minSeating;
    }

    public void setMinSeating(Long minSeating) {
        this.minSeating = minSeating;
    }

    public Long getMaxSeating() {
        return maxSeating;
    }

    public void setMaxSeating(Long maxSeating) {
        this.maxSeating = maxSeating;
    }

    public Long getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(Long timeLength) {
        this.timeLength = timeLength;
    }

    @Length(min = 0, max = 30, message = "冗余1长度必须介于 0 和 30 之间")
    public String getRedundant1() {
        return redundant1;
    }

    public void setRedundant1(String redundant1) {
        this.redundant1 = redundant1;
    }

    @Length(min = 0, max = 30, message = "冗余2长度必须介于 0 和 30 之间")
    public String getRedundant2() {
        return redundant2;
    }

    public void setRedundant2(String redundant2) {
        this.redundant2 = redundant2;
    }

    @Length(min = 0, max = 30, message = "冗余3长度必须介于 0 和 30 之间")
    public String getRedundant3() {
        return redundant3;
    }

    public void setRedundant3(String redundant3) {
        this.redundant3 = redundant3;
    }

    @Length(min = 0, max = 30, message = "冗余4长度必须介于 0 和 30 之间")
    public String getRedundant4() {
        return redundant4;
    }

    public void setRedundant4(String redundant4) {
        this.redundant4 = redundant4;
    }

    @Length(min = 0, max = 30, message = "冗余5长度必须介于 0 和 30 之间")
    public String getRedundant5() {
        return redundant5;
    }

    public void setRedundant5(String redundant5) {
        this.redundant5 = redundant5;
    }

    @Length(min = 0, max = 30, message = "冗余6长度必须介于 0 和 30 之间")
    public String getRedundant6() {
        return redundant6;
    }

    public void setRedundant6(String redundant6) {
        this.redundant6 = redundant6;
    }

    @Length(min = 0, max = 30, message = "冗余7长度必须介于 0 和 30 之间")
    public String getRedundant7() {
        return redundant7;
    }

    public void setRedundant7(String redundant7) {
        this.redundant7 = redundant7;
    }

    @Length(min = 0, max = 30, message = "冗余8长度必须介于 0 和 30 之间")
    public String getRedundant8() {
        return redundant8;
    }

    public void setRedundant8(String redundant8) {
        this.redundant8 = redundant8;
    }

    @Length(min = 0, max = 30, message = "冗余9长度必须介于 0 和 30 之间")
    public String getRedundant9() {
        return redundant9;
    }

    public void setRedundant9(String redundant9) {
        this.redundant9 = redundant9;
    }

    public String getSafeguardTypeName() {
        return safeguardTypeName;
    }

    public void setSafeguardTypeName(String safeguardTypeName) {
        this.safeguardTypeName = safeguardTypeName;
    }
}