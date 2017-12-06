/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.AirstandAirparametersService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 机位与机型对应表Entity
 *
 * @author wjp
 * @version 2016-03-17
 */

@Monitor(desc = "机位与机型对应表信息", tableName = "rms_airstand_airparameters", service = AirstandAirparametersService.class, socketNS = "/rms/airstandAirparameters")
public class AirstandAirparameters extends DataEntity<AirstandAirparameters> {

    private static final long serialVersionUID = 1L;
    @Label("创建时间")
    @MonitorField(desc = "创建时间")
    private Date createTime;        // 创建时间
    @Label("更新时间")
    @MonitorField(desc = "更新时间")
    private Date updateTime;        // 更新时间
    @Label("机位号")
    @MonitorField(desc = "机位号")
    private String aircraftStandNum;        // 机位号
    @Label("机位号ID")
    @MonitorField(desc = "机位号ID")
    private String aircraftStandId;        // 机位号ID
    @Label("机型")
    @MonitorField(desc = "机型")
    private String aircraftModel;        // 机型
    @Label("机型ID")
    @MonitorField(desc = "机型ID")
    private String aircraftId;        // 机型ID

    public AirstandAirparameters() {
        super();
    }

    public AirstandAirparameters(String id) {
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

    @Length(min = 0, max = 1000, message = "机位号长度必须介于 0 和 1000 之间")
    public String getAircraftStandNum() {
        return aircraftStandNum;
    }

    public void setAircraftStandNum(String aircraftStandNum) {
        this.aircraftStandNum = aircraftStandNum;
    }

    @Length(min = 0, max = 36, message = "机位号ID长度必须介于 0 和 36 之间")
    public String getAircraftStandId() {
        return aircraftStandId;
    }

    public void setAircraftStandId(String aircraftStandId) {
        this.aircraftStandId = aircraftStandId;
    }

    @Length(min = 0, max = 200, message = "机型长度必须介于 0 和 200 之间")
    public String getAircraftModel() {
        return aircraftModel;
    }

    public void setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
    }

    @Length(min = 0, max = 36, message = "机型ID长度必须介于 0 和 36 之间")
    public String getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(String aircraftId) {
        this.aircraftId = aircraftId;
    }

}