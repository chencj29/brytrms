/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.DepartureHallService;
import org.hibernate.validator.constraints.Length;

/**
 * 候机厅基础信息Entity
 *
 * @author wjp
 * @version 2016-03-19
 */

@Monitor(desc = "候机厅基础信息", tableName = "rms_departure_hall", service = DepartureHallService.class, socketNS = "/rms/departureHall")
public class DepartureHall extends DataEntity<DepartureHall> {

    private static final long serialVersionUID = 1L;
    @Label("候机厅编号")
    @MonitorField(desc = "候机厅编号")
    private String departureHallNum;        // 候机厅编号
    @Label("候机厅状态")
    @MonitorField(desc = "候机厅状态")
    private String departureHallStatus;        // 候机厅状态
    @Label("候机厅性质")
    @MonitorField(desc = "候机厅性质")
    private String departureHallNature;        // 候机厅性质
    @Label("航站楼")
    @MonitorField(desc = "航站楼")
    private String aircraftTerminalCode;        // 航站楼

    @Label("排序字段")
    @MonitorField(desc = "排序字段")
    private Integer orderIndex;


    public DepartureHall() {
        super();
    }

    public DepartureHall(String id) {
        super(id);
    }

    @Length(min = 0, max = 10, message = "候机厅编号长度必须介于 0 和 10 之间")
    public String getDepartureHallNum() {
        return departureHallNum;
    }

    public void setDepartureHallNum(String departureHallNum) {
        this.departureHallNum = departureHallNum;
    }

    @Length(min = 0, max = 1, message = "候机厅状态长度必须介于 0 和 1 之间")
    public String getDepartureHallStatus() {
        return departureHallStatus;
    }

    public void setDepartureHallStatus(String departureHallStatus) {
        this.departureHallStatus = departureHallStatus;
    }

    @Length(min = 0, max = 10, message = "候机厅性质长度必须介于 0 和 10 之间")
    public String getDepartureHallNature() {
        return departureHallNature;
    }

    public void setDepartureHallNature(String departureHallNature) {
        this.departureHallNature = departureHallNature;
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