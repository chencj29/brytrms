/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.ArrivalGateService;
import org.hibernate.validator.constraints.Length;

/**
 * 到港口基础信息Entity
 *
 * @author wjp
 * @version 2016-03-19
 */

@Monitor(desc = "到港口基础信息", tableName = "rms_arrival_gate", service = ArrivalGateService.class, socketNS = "/rms/arrivalGate")
public class ArrivalGate extends DataEntity<ArrivalGate> {

    private static final long serialVersionUID = 1L;
    @Label("到港口编号")
    @MonitorField(desc = "到港口编号")
    private String arrivalGateNum;        // 到港口编号
    @Label("当港口性质")
    @MonitorField(desc = "当港口性质")
    private String arrivalGateNature;        // 当港口性质
    @Label("到港口状态")
    @MonitorField(desc = "到港口状态")
    private String arrivalGateStatus;        // 到港口状态
    @Label("航站楼")
    @MonitorField(desc = "航站楼")
    private String aircraftTerminalCode;        // 航站楼
    @Label("排序字段")
    @MonitorField(desc = "排序字段")
    private Integer orderIndex;

    public ArrivalGate() {
        super();
    }

    public ArrivalGate(String id) {
        super(id);
    }

    @Length(min = 0, max = 10, message = "到港口编号长度必须介于 0 和 10 之间")
    public String getArrivalGateNum() {
        return arrivalGateNum;
    }

    public void setArrivalGateNum(String arrivalGateNum) {
        this.arrivalGateNum = arrivalGateNum;
    }

    @Length(min = 0, max = 10, message = "当港口性质长度必须介于 0 和 10 之间")
    public String getArrivalGateNature() {
        return arrivalGateNature;
    }

    public void setArrivalGateNature(String arrivalGateNature) {
        this.arrivalGateNature = arrivalGateNature;
    }

    @Length(min = 0, max = 1, message = "到港口状态长度必须介于 0 和 1 之间")
    public String getArrivalGateStatus() {
        return arrivalGateStatus;
    }

    public void setArrivalGateStatus(String arrivalGateStatus) {
        this.arrivalGateStatus = arrivalGateStatus;
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