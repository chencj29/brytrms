/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.rms.service.SafeguardProcessService;
import org.hibernate.validator.constraints.Length;

/**
 * 保障过程管理Entity
 *
 * @author chrischen
 * @version 2016-03-16
 */

@Monitor(desc = "保障过程管理信息", tableName = "rms_safeguard_process", service = SafeguardProcessService.class, socketNS = "/rms/safeguardProcess")
public class SafeguardProcess extends DataEntity<SafeguardProcess> {

    private static final long serialVersionUID = 1L;
    @Label("过程编号")
    @MonitorField(desc = "过程编号")
    private String safeguardProcessCode;        // 过程编号
    @Label("过程名称")
    @MonitorField(desc = "过程名称")
    private String safeguardProcessName;        // 过程名称
    @Label("过程属性名称")
    @MonitorField(desc = "过程属性名称")
    private String safeguardAttrName;        // 过程属性名称
    @Label("过程复合名称")
    @MonitorField(desc = "过程复合名称")
    private String processCompoundName;        // 过程复合名称
    @Label("计划显示颜色")
    @MonitorField(desc = "计划显示颜色")
    private String planColor;        // 计划显示颜色
    @Label("实际显示颜色")
    @MonitorField(desc = "实际显示颜色")
    private String actualColor;        // 实际显示颜色
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

    public SafeguardProcess() {
        super();
    }

    public SafeguardProcess(String id) {
        super(id);
    }

    @Length(min = 0, max = 10, message = "过程编号长度必须介于 0 和 10 之间")
    public String getSafeguardProcessCode() {
        return safeguardProcessCode;
    }

    public void setSafeguardProcessCode(String safeguardProcessCode) {
        this.safeguardProcessCode = safeguardProcessCode;
    }

    @Length(min = 0, max = 50, message = "过程名称长度必须介于 0 和 50 之间")
    public String getSafeguardProcessName() {
        return safeguardProcessName;
    }

    public void setSafeguardProcessName(String safeguardProcessName) {
        this.safeguardProcessName = safeguardProcessName;
    }

    @Length(min = 0, max = 20, message = "过程属性名称长度必须介于 0 和 20 之间")
    public String getSafeguardAttrName() {
        return safeguardAttrName;
    }

    public void setSafeguardAttrName(String safeguardAttrName) {
        this.safeguardAttrName = safeguardAttrName;
    }

    @Length(min = 0, max = 50, message = "过程复合名称长度必须介于 0 和 50 之间")
    public String getProcessCompoundName() {
        return processCompoundName;
    }

    public void setProcessCompoundName(String processCompoundName) {
        this.processCompoundName = processCompoundName;
    }

    @Length(min = 0, max = 7, message = "计划显示颜色长度必须介于 0 和 7 之间")
    public String getPlanColor() {
        return planColor;
    }

    public void setPlanColor(String planColor) {
        this.planColor = planColor;
    }

    @Length(min = 0, max = 7, message = "实际显示颜色长度必须介于 0 和 7 之间")
    public String getActualColor() {
        return actualColor;
    }

    public void setActualColor(String actualColor) {
        this.actualColor = actualColor;
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

    @Override
    public String toString() {
        StringBuffer msg = new StringBuffer();
        if (safeguardProcessCode != null) msg.append("过程编号:").append(safeguardProcessCode).append(",");
        if (safeguardProcessName != null) msg.append("过程名称:").append(safeguardProcessName).append(",");
        if (redundant1 != null) msg.append("计划开始占用时间:").append(redundant1).append(",");
        if (redundant2 != null) msg.append("计划结束占用时间:").append(redundant2);

        return msg.toString();
    }
}