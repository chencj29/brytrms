/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.bstek.urule.model.Label;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.ams.service.FlightCompanyInfoService;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import java.util.List;

/**
 * 航空公司信息管理Entity
 *
 * @author xiaopo
 * @version 2015-12-09
 */
//@Table(name = "ams_flight_company_info")
//@Entity
@Monitor(desc = "航空公司信息", tableName = "AMS_FLIGHT_COMPANY_INFO", service = FlightCompanyInfoService.class, socketNS = "/rms/basicData/flightCompanyInfo")
public class FlightCompanyInfo extends DataEntity<FlightCompanyInfo> {
    private static final long serialVersionUID = 1L;

    @MonitorField(desc = "二字码")
    @Label("二字码")
    private String twoCode;        // 二字码
    @MonitorField(desc = "三字码")
    @Label("三字码")
    private String threeCode;        // 三字码
    @MonitorField(desc = "中文简称")
    @Label("中文简称")
    private String chineseShort;        // 中文简称
    @TipMainField
    @MonitorField(desc = "中文名称")
    @Label("中文名称")
    private String chineseName;        // 中文名称
    @MonitorField(desc = "英文名称")
    @Label("英文名称")
    private String englishName;        // 英文名称
    @MonitorField(desc = "是否外航")
    @Label("是否外航")
    private String otherFlight;        // 是否外航

    @Label("航空子公司列表")
    private List<FlightCompanySubInfo> flightCompanySubInfoList = Lists.newArrayList();        // 子表列表

    public FlightCompanyInfo() {
        super();
    }

    public FlightCompanyInfo(String id) {
        super(id);
    }

    @Column(name = "two_code")
    @Length(min = 0, max = 2, message = "二字码长度必须介于 0 和 2 之间")
    public String getTwoCode() {
        return twoCode;
    }

    public void setTwoCode(String twoCode) {
        this.twoCode = twoCode;
    }

    @Column(name = "three_code")
    @Length(min = 0, max = 3, message = "三字码长度必须介于 0 和 3 之间")
    public String getThreeCode() {
        return threeCode;
    }

    public void setThreeCode(String threeCode) {
        this.threeCode = threeCode;
    }

    @Column(name = "chinese_short")
    @Length(min = 0, max = 100, message = "中文简称长度必须介于 0 和 100 之间")
    public String getChineseShort() {
        return chineseShort;
    }

    public void setChineseShort(String chineseShort) {
        this.chineseShort = chineseShort;
    }

    @Column(name = "chinese_name")
    @Length(min = 0, max = 200, message = "中文名称长度必须介于 0 和 200 之间")
    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    @Column(name = "english_name")
    @Length(min = 0, max = 500, message = "英文名称长度必须介于 0 和 500 之间")
    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    @Column(name = "other_flight")
    @Length(min = 0, max = 100, message = "是否外航长度必须介于 0 和 100 之间")
    public String getOtherFlight() {
        return otherFlight;
    }

    public void setOtherFlight(String otherFlight) {
        this.otherFlight = otherFlight;
    }

    public List<FlightCompanySubInfo> getFlightCompanySubInfoList() {
        return flightCompanySubInfoList;
    }

    public void setFlightCompanySubInfoList(List<FlightCompanySubInfo> flightCompanySubInfoList) {
        this.flightCompanySubInfoList = flightCompanySubInfoList;
    }
}