/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;


import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.ams.service.FlightCompanySubInfoService;

/**
 * 航空公司子公司管理Entity
 *
 * @author xiaopo
 * @version 2016-08-24
 */

@Monitor(desc = "航空公司子公司管理信息", tableName = "ams_flight_company_sub_info", service = FlightCompanySubInfoService.class, socketNS = "/rms/flightCompanySubInfo")
public class FlightCompanySubInfo extends DataEntity<FlightCompanySubInfo> {

    private static final long serialVersionUID = 1L;
    @Label("航空公司 ID")
    @MonitorField(desc = "航空公司 ID")
    private String flightCompanyInfoId;        // 航空公司 ID
    @Label("子公司代码")
    @MonitorField(desc = "子公司代码")
    private String subCode;        // 子公司代码
    @Label("三字码")
    @MonitorField(desc = "三字码")
    private String threeCode;        // 三字码
    @Label("中文名称")
    @MonitorField(desc = "中文名称")
    private String chineseName;        // 中文名称
    @Label("英文名称")
    @MonitorField(desc = "英文名称")
    private String englishName;        // 英文名称
    @Label("是否基地")
    @MonitorField(desc = "是否基地")
    private Long isbase;        // 是否基地


    //EXT FIELD Author fyc
    @Label("父航空公司名称")
    @MonitorField(desc = "父航空公司名称")
    private String flightCompanyInfoName;        // 父航空公司名称


    public FlightCompanySubInfo() {
        super();
    }

    public FlightCompanySubInfo(String id) {
        super(id);
    }

    public String getFlightCompanyInfoId() {
        return flightCompanyInfoId;
    }

    public void setFlightCompanyInfoId(String flightCompanyInfoId) {
        this.flightCompanyInfoId = flightCompanyInfoId;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getThreeCode() {
        return threeCode;
    }

    public void setThreeCode(String threeCode) {
        this.threeCode = threeCode;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Long getIsbase() {
        return isbase;
    }

    public void setIsbase(Long isbase) {
        this.isbase = isbase;
    }

    public String getFlightCompanyInfoName() {
        return flightCompanyInfoName;
    }

    public void setFlightCompanyInfoName(String flightCompanyInfoName) {
        this.flightCompanyInfoName = flightCompanyInfoName;
    }
}