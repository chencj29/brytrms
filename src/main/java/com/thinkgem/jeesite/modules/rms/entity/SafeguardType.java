/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.SafeguardTypeService;
import org.hibernate.validator.constraints.Length;

/**
 * 保障类型管理Entity
 *
 * @author chrischen
 * @version 2016-03-15
 */

@Monitor(desc = "保障类型管理信息", tableName = "rms_safeguard_type", service = SafeguardTypeService.class, socketNS = "/rms/safeguardType")
public class SafeguardType extends DataEntity<SafeguardType> {

    private static final long serialVersionUID = 1L;
    @Label("保障类型编号")
    @MonitorField(desc = "保障类型编号")
    private String safeguardTypeCode;        // 保障类型编号
    @Label("保障类型")
    @MonitorField(desc = "保障类型")
    private String safeguardTypeName;        // 保障类型
    @Label("父类型编号")
    @MonitorField(desc = "父类型编号")
    private String parentSafeguardCode;        // 父ID
    @Label("父保障类型")
    @MonitorField(desc = "父保障类型")
    private String parentSafeguardName;        // 父保障类型
    @Label("类型")
    @MonitorField(desc = "类型")
    private String type;        // 类型
    @Label("航空公司编号")
    @MonitorField(desc = "航空公司编号")
    private String flightCompanyCode;        // 航空公司编号
    @Label("航空公司名称")
    @MonitorField(desc = "航空公司名称")
    private String flightCompanyName;        // 航空公司名称
    @Label("机型编号")
    @MonitorField(desc = "机型编号")
    private String aircraftCode;        // 机型编号
    @Label("飞机号")
    @MonitorField(desc = "飞机号")
    private String aircraftId;        // 飞机号
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

    public SafeguardType() {
        super();
    }

    public SafeguardType(String id) {
        super(id);
    }

    @Length(min = 0, max = 10, message = "保障类型编号长度必须介于 0 和 10 之间")
    public String getSafeguardTypeCode() {
        return safeguardTypeCode;
    }

    public void setSafeguardTypeCode(String safeguardTypeCode) {
        this.safeguardTypeCode = safeguardTypeCode;
    }

    @Length(min = 0, max = 50, message = "保障类型长度必须介于 0 和 50 之间")
    public String getSafeguardTypeName() {
        return safeguardTypeName;
    }

    public void setSafeguardTypeName(String safeguardTypeName) {
        this.safeguardTypeName = safeguardTypeName;
    }

    @Length(min = 0, max = 10, message = "父ID长度必须介于 0 和 10 之间")
    public String getParentSafeguardCode() {
        return parentSafeguardCode;
    }

    public void setParentSafeguardCode(String parentSafeguardCode) {
        this.parentSafeguardCode = parentSafeguardCode;
    }

    @Length(min = 0, max = 50, message = "父保障类型长度必须介于 0 和 50 之间")
    public String getParentSafeguardName() {
        return parentSafeguardName;
    }

    public void setParentSafeguardName(String parentSafeguardName) {
        this.parentSafeguardName = parentSafeguardName;
    }

    @Length(min = 0, max = 10, message = "类型长度必须介于 0 和 10 之间")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Length(min = 0, max = 4, message = "航空公司编号长度必须介于 0 和 4 之间")
    public String getFlightCompanyCode() {
        return flightCompanyCode;
    }

    public void setFlightCompanyCode(String flightCompanyCode) {
        this.flightCompanyCode = flightCompanyCode;
    }

    @Length(min = 0, max = 20, message = "航空公司名称长度必须介于 0 和 20 之间")
    public String getFlightCompanyName() {
        return flightCompanyName;
    }

    public void setFlightCompanyName(String flightCompanyName) {
        this.flightCompanyName = flightCompanyName;
    }

    @Length(min = 0, max = 20, message = "机型编号长度必须介于 0 和 20 之间")
    public String getAircraftCode() {
        return aircraftCode;
    }

    public void setAircraftCode(String aircraftCode) {
        this.aircraftCode = aircraftCode;
    }

    @Length(min = 0, max = 30, message = "飞机号长度必须介于 0 和 30 之间")
    public String getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(String aircraftId) {
        this.aircraftId = aircraftId;
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

}