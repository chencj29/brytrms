/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;


import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.EmergencyPlanService;

/**
 * 应急救援方案Entity
 *
 * @author wjp
 * @version 2016-08-25
 */

@Monitor(desc = "应急救援方案信息", tableName = "rms_emergency_plan", service = EmergencyPlanService.class, socketNS = "/rms/emergencyPlan")
public class EmergencyPlan extends DataEntity<EmergencyPlan> {

    private static final long serialVersionUID = 1L;
    @Label("方案编号")
    @MonitorField(desc = "方案编号")
    private String emPlanCode;        // 方案编号
    @Label("方案名称")
    @MonitorField(desc = "方案名称")
    private String emPlanName;        // 方案名称
    @Label("救援流程说明")
    @MonitorField(desc = "救援流程说明")
    private String emPlanDesc;        // 救援流程说明
    @Label("应急救援文件路径")
    @MonitorField(desc = "应急救援文件路径")
    private String emPlanFilePath;        // 应急救援文件路径

    public EmergencyPlan() {
        super();
    }

    public EmergencyPlan(String id) {
        super(id);
    }

    public String getEmPlanCode() {
        return emPlanCode;
    }

    public void setEmPlanCode(String emPlanCode) {
        this.emPlanCode = emPlanCode;
    }

    public String getEmPlanName() {
        return emPlanName;
    }

    public void setEmPlanName(String emPlanName) {
        this.emPlanName = emPlanName;
    }

    public String getEmPlanDesc() {
        return emPlanDesc;
    }

    public void setEmPlanDesc(String emPlanDesc) {
        this.emPlanDesc = emPlanDesc;
    }

    public String getEmPlanFilePath() {
        return emPlanFilePath;
    }

    public void setEmPlanFilePath(String emPlanFilePath) {
        this.emPlanFilePath = emPlanFilePath;
    }

}