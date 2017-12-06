/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;


import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.ResourceMockDistDetailService;

import java.util.Date;

/**
 * 资源模拟分配详情Entity
 *
 * @author BigBrother5
 * @version 2017-03-07
 */

@Monitor(desc = "资源模拟分配详情信息", tableName = "rms_resource_mock_dist_detail", service = ResourceMockDistDetailService.class, socketNS = "/rms/resourceMockDistDetail")
public class ResourceMockDistDetail extends DataEntity<ResourceMockDistDetail> {

    private static final long serialVersionUID = 1L;
    @Label("所属条目ID")
    @MonitorField(desc = "所属条目ID")
    private String infoId;        // 所属条目ID
    @Label("所属航班动态（配对）ID")
    @MonitorField(desc = "所属航班动态（配对）ID")
    private String dataId;        // 所属航班动态（配对）ID
    @Label("国内资源编号")
    @MonitorField(desc = "国内资源编号")
    private String inte;        // 国内资源编号
    @Label("国际资源编号")
    @MonitorField(desc = "国际资源编号")
    private String intl;        // 国际资源编号
    @Label("提示信息")
    @MonitorField(desc = "提示信息")
    private String info;        // 提示信息


    // wrapper field
    private String aircraftNum;
    private String aircraftTypeCode;
    private String flightDynamicCode;
    private Date planDate;
    private Date occupiedStart;
    private Date occupiedEnd;

    public String getAircraftNum() {
        return aircraftNum;
    }

    public void setAircraftNum(String aircraftNum) {
        this.aircraftNum = aircraftNum;
    }

    public String getAircraftTypeCode() {
        return aircraftTypeCode;
    }

    public void setAircraftTypeCode(String aircraftTypeCode) {
        this.aircraftTypeCode = aircraftTypeCode;
    }

    public String getFlightDynamicCode() {
        return flightDynamicCode;
    }

    public void setFlightDynamicCode(String flightDynamicCode) {
        this.flightDynamicCode = flightDynamicCode;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public ResourceMockDistDetail() {
        super();
    }

    public ResourceMockDistDetail(String id) {
        super(id);
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getInte() {
        return inte;
    }

    public void setInte(String inte) {
        this.inte = inte;
    }

    public String getIntl() {
        return intl;
    }

    public void setIntl(String intl) {
        this.intl = intl;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getOccupiedStart() {
        return occupiedStart;
    }

    public void setOccupiedStart(Date occupiedStart) {
        this.occupiedStart = occupiedStart;
    }

    public Date getOccupiedEnd() {
        return occupiedEnd;
    }

    public void setOccupiedEnd(Date occupiedEnd) {
        this.occupiedEnd = occupiedEnd;
    }
}