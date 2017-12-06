/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Log;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 指挥中心审核操作Entity
 *
 * @author wjp
 * @version 2017-03-20
 */

//@Monitor(desc = "指挥中心审核操作信息", tableName = "ams_check_aspect", service = CheckAspectService.class , socketNS = "/rms/checkAspect")
public class CheckAspect extends DataEntity<CheckAspect> {

    private static final long serialVersionUID = 1L;
    @TipMainField
    @MonitorField(desc = "监控方法")
    private String method;        // 监控方法
    @MonitorField(desc = "方法参数")
    private String arguments;        // 方法参数
    @MonitorField(desc = "日志记录")
    private Log log;        // 日志记录
    @MonitorField(desc = "申请动作")
    private String description;        // 申请动作
    @MonitorField(desc = "航班动态")
    private FlightDynamic flightDynamic;        // 航班动态
    @MonitorField(desc = "审核意见")
    private String adviceFlag;        // 审核意见
    @MonitorField(desc = "审核人")
    private String adviceBy;        // 审核人
    @MonitorField(desc = "动作描述")
    private String ext1;        // 动作描述
    @MonitorField(desc = "扩展2")
    private String ext2;        // 扩展2
    @MonitorField(desc = "扩展3")
    private String ext3;        // 扩展3

    @MonitorField(desc = "航班日期")
    private Date planDate;
    @MonitorField(desc = "航班类型")
    private String inoutTypeName;        // 进出港类型名称
    @MonitorField(desc = "航班号")
    private String flightNum;            //航班号

    public CheckAspect() {
        super();
    }

    public CheckAspect(String id) {
        super(id);
    }

    @Length(min = 0, max = 500, message = "监控方法长度必须介于 0 和 36 之间")
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public Log getLog() {
        return this.log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    @Length(min = 0, max = 500, message = "动作描述长度必须介于 0 和 500 之间")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonBackReference
    public FlightDynamic getFlightDynamic() {
        return flightDynamic;
    }

    public void setFlightDynamic(FlightDynamic flightDynamic) {
        this.flightDynamic = flightDynamic;
    }

    @Length(min = 0, max = 10, message = "审核意见长度必须介于 0 和 10 之间")
    public String getAdviceFlag() {
        return adviceFlag;
    }

    public void setAdviceFlag(String adviceFlag) {
        this.adviceFlag = adviceFlag;
    }

    @Length(min = 0, max = 36, message = "审核人长度必须介于 0 和 36 之间")
    public String getAdviceBy() {
        return adviceBy;
    }

    public void setAdviceBy(String adviceBy) {
        this.adviceBy = adviceBy;
    }

    @Length(min = 0, max = 200, message = "扩展1长度必须介于 0 和 200 之间")
    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    @Length(min = 0, max = 200, message = "扩展2长度必须介于 0 和 200 之间")
    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    @Length(min = 0, max = 200, message = "扩展3长度必须介于 0 和 200 之间")
    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8:00")
    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getInoutTypeName() {
        return inoutTypeName;
    }

    public void setInoutTypeName(String inoutTypeName) {
        this.inoutTypeName = inoutTypeName;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }
}