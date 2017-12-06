/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.IrregularServicesService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 不正常服务记录Entity
 *
 * @author wjp
 * @version 2016-03-19
 */

@Monitor(desc = "不正常服务记录信息", tableName = "rms_irregular_services", service = IrregularServicesService.class, socketNS = "/rms/irregularServices")
public class IrregularServices extends DataEntity<IrregularServices> {

    private static final long serialVersionUID = 1L;
    @Label("日期")
    @MonitorField(desc = "日期")
    private Date irregularServicesDate;        // 日期
    @Label("航空公司代码")
    @MonitorField(desc = "航空公司代码")
    private String flightCompanyCode;        // 航空公司代码
    @Label("航空公司名称")
    @MonitorField(desc = "航空公司名称")
    private String flightCompanyName;        // 航空公司名称
    @Label("航班号")
    @MonitorField(desc = "航班号")
    private String flightNum;        // 航班号
    @Label("地点")
    @MonitorField(desc = "地点")
    private String place;        // 地点
    @Label("不正常服务编号")
    @MonitorField(desc = "不正常服务编号")
    private String irregularServiceCode;        // 不正常服务编号
    @Label("不正常服务类型")
    @MonitorField(desc = "不正常服务类型")
    private String irregularServiceName;        // 不正常服务类型
    @Label("产生原因")
    @MonitorField(desc = "产生原因")
    private String cause;        // 产生原因
    @Label("补救措施")
    @MonitorField(desc = "补救措施")
    private String remedialMeasure;        // 补救措施
    @Label("冗余1")
    @MonitorField(desc = "冗余1")
    private String redundance1;        // 冗余1
    @Label("冗余2")
    @MonitorField(desc = "冗余2")
    private String redundance2;        // 冗余2
    @Label("冗余3")
    @MonitorField(desc = "冗余3")
    private String redundance3;        // 冗余3
    @Label("冗余4")
    @MonitorField(desc = "冗余4")
    private String redundance4;        // 冗余4
    @Label("冗余5")
    @MonitorField(desc = "冗余5")
    private String redundance5;        // 冗余5

    public IrregularServices() {
        super();
    }

    public IrregularServices(String id) {
        super(id);
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8:00")
    public Date getIrregularServicesDate() {
        return irregularServicesDate;
    }

    public void setIrregularServicesDate(Date irregularServicesDate) {
        this.irregularServicesDate = irregularServicesDate;
    }

    @Length(min = 0, max = 20, message = "航空公司代码长度必须介于 0 和 20 之间")
    public String getFlightCompanyCode() {
        return flightCompanyCode;
    }

    public void setFlightCompanyCode(String flightCompanyCode) {
        this.flightCompanyCode = flightCompanyCode;
    }

    @Length(min = 0, max = 500, message = "航空公司名称长度必须介于 0 和 500 之间")
    public String getFlightCompanyName() {
        return flightCompanyName;
    }

    public void setFlightCompanyName(String flightCompanyName) {
        this.flightCompanyName = flightCompanyName;
    }

    @Length(min = 0, max = 36, message = "航班号长度必须介于 0 和 36 之间")
    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    @Length(min = 0, max = 36, message = "地点长度必须介于 0 和 36 之间")
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Length(min = 0, max = 36, message = "不正常服务编号长度必须介于 0 和 36 之间")
    public String getIrregularServiceCode() {
        return irregularServiceCode;
    }

    public void setIrregularServiceCode(String irregularServiceCode) {
        this.irregularServiceCode = irregularServiceCode;
    }

    @Length(min = 0, max = 36, message = "不正常服务类型长度必须介于 0 和 36 之间")
    public String getIrregularServiceName() {
        return irregularServiceName;
    }

    public void setIrregularServiceName(String irregularServiceName) {
        this.irregularServiceName = irregularServiceName;
    }

    @Length(min = 0, max = 500, message = "产生原因长度必须介于 0 和 500 之间")
    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Length(min = 0, max = 500, message = "补救措施长度必须介于 0 和 500 之间")
    public String getRemedialMeasure() {
        return remedialMeasure;
    }

    public void setRemedialMeasure(String remedialMeasure) {
        this.remedialMeasure = remedialMeasure;
    }

    @Length(min = 0, max = 10, message = "冗余1长度必须介于 0 和 10 之间")
    public String getRedundance1() {
        return redundance1;
    }

    public void setRedundance1(String redundance1) {
        this.redundance1 = redundance1;
    }

    @Length(min = 0, max = 10, message = "冗余2长度必须介于 0 和 10 之间")
    public String getRedundance2() {
        return redundance2;
    }

    public void setRedundance2(String redundance2) {
        this.redundance2 = redundance2;
    }

    @Length(min = 0, max = 10, message = "冗余3长度必须介于 0 和 10 之间")
    public String getRedundance3() {
        return redundance3;
    }

    public void setRedundance3(String redundance3) {
        this.redundance3 = redundance3;
    }

    @Length(min = 0, max = 10, message = "冗余4长度必须介于 0 和 10 之间")
    public String getRedundance4() {
        return redundance4;
    }

    public void setRedundance4(String redundance4) {
        this.redundance4 = redundance4;
    }

    @Length(min = 0, max = 10, message = "冗余5长度必须介于 0 和 10 之间")
    public String getRedundance5() {
        return redundance5;
    }

    public void setRedundance5(String redundance5) {
        this.redundance5 = redundance5;
    }

}