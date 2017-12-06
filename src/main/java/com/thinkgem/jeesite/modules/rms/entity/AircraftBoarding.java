/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.AircraftBoardingService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 机位与登机口对应表Entity
 *
 * @author wjp
 * @version 2016-03-09
 */

@Monitor(desc = "机位与登机口对应表信息", tableName = "rms_aircraft_boarding", service = AircraftBoardingService.class, socketNS = "/rms/aircraftBoarding")
public class AircraftBoarding extends DataEntity<AircraftBoarding> {

    private static final long serialVersionUID = 1L;
    @Label("创建时间")
    @MonitorField(desc = "创建时间")
    private Date createTime;                // 创建时间
    @Label("更新时间")
    @MonitorField(desc = "更新时间")
    private Date updateTime;                // 更新时间
    @Label("机位号")
    @MonitorField(desc = "机位号")
    private String aircraftStandNum;        // 机位号
    @Label("登机口号(国内)")
    @MonitorField(desc = "登机口号(国际)")
    private String boardingGateNum;         // 登机口号
    @Label("登机口号(国际)")
    @MonitorField(desc = "登机口号(国际)")
    private String intlBoardingGateNum;

    public AircraftBoarding() {
        super();
    }

    public AircraftBoarding(String id) {
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

    @Length(min = 0, max = 36, message = "机位号长度必须介于 0 和 36 之间")
    public String getAircraftStandNum() {
        return aircraftStandNum;
    }

    public void setAircraftStandNum(String aircraftStandNum) {
        this.aircraftStandNum = aircraftStandNum;
    }

    public String getBoardingGateNum() {
        return boardingGateNum;
    }

    public void setBoardingGateNum(String boardingGateNum) {
        this.boardingGateNum = boardingGateNum;
    }

    public String getIntlBoardingGateNum() {
        return intlBoardingGateNum;
    }

    public void setIntlBoardingGateNum(String intlBoardingGateNum) {
        this.intlBoardingGateNum = intlBoardingGateNum;
    }
}