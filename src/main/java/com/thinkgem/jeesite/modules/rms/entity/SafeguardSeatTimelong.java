/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.SafeguardSeatTimelongService;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Transient;
import java.util.Date;

/**
 * 保障过程及座位时长对应Entity
 *
 * @author xiaopo
 * @version 2016-03-29
 */

@Monitor(desc = "保障过程及座位时长对应信息", tableName = "rms_safeguard_seat_timelong", service = SafeguardSeatTimelongService.class, socketNS = "/rms/safeguardSeatTimelong")
public class SafeguardSeatTimelong extends DataEntity<SafeguardSeatTimelong> {

    private static final long serialVersionUID = 1L;
    @Label("保障类型编号")
    @MonitorField(desc = "保障类型编号")
    private String safeguardTypeCode;        // 保障类型编号
    @Label("保障类型名称")
    @MonitorField(desc = "保障类型名称")
    private String safeguardTypeName;        // 保障类型名称
    @Label("保障过程编号")
    @MonitorField(desc = "保障过程编号")
    private String safeguardProcessCode;        // 保障过程编号
    @Label("保障过程名称")
    @MonitorField(desc = "保障过程名称")
    private String safeguardProcessName;        // 保障过程名称
    @Label("座位数")
    @MonitorField(desc = "座位数")
    private Long seatNum;        // 座位数
    @Label("相对开始时间")
    @MonitorField(desc = "相对开始时间")
    private Double safeguardProcessFrom;        // 相对开始时间
    @Label("相对结束时间")
    @MonitorField(desc = "相对结束时间")
    private Double safeguardProcessTo;        // 相对结束时间
    @Label("保障类型ID")
    @MonitorField(desc = "保障类型ID")
    private SafeguardType safeguardType;        // 保障类型ID
    @Label("保障过程ID")
    @MonitorField(desc = "保障过程ID")
    private SafeguardProcess safeguardProcess;        // 保障过程ID
    @Label("过程复合名称")
    @MonitorField(desc = "过程复合名称")
    private String processCompoundName;             //过程复合名称

    @Ignore
    @Transient
    private Date _startTime; // 实际开始时间
    @Ignore
    @Transient
    private Date _endTime;  // 实际结束时间

    public SafeguardSeatTimelong() {
        super();
    }

    public SafeguardSeatTimelong(String id) {
        super(id);
    }

    @Length(min = 1, max = 36, message = "保障类型编号长度必须介于 1 和 36 之间")
    public String getSafeguardTypeCode() {
        return safeguardTypeCode;
    }

    public void setSafeguardTypeCode(String safeguardTypeCode) {
        this.safeguardTypeCode = safeguardTypeCode;
    }

    @Length(min = 1, max = 36, message = "保障类型名称长度必须介于 1 和 36 之间")
    public String getSafeguardTypeName() {
        return safeguardTypeName;
    }

    public void setSafeguardTypeName(String safeguardTypeName) {
        this.safeguardTypeName = safeguardTypeName;
    }

    @Length(min = 0, max = 36, message = "保障过程编号长度必须介于 0 和 36 之间")
    public String getSafeguardProcessCode() {
        return safeguardProcessCode;
    }

    public void setSafeguardProcessCode(String safeguardProcessCode) {
        this.safeguardProcessCode = safeguardProcessCode;
    }

    @Length(min = 0, max = 36, message = "保障过程名称长度必须介于 0 和 36 之间")
    public String getSafeguardProcessName() {
        return safeguardProcessName;
    }

    public void setSafeguardProcessName(String safeguardProcessName) {
        this.safeguardProcessName = safeguardProcessName;
    }

    public Long getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(Long seatNum) {
        this.seatNum = seatNum;
    }

    public Double getSafeguardProcessFrom() {
        return safeguardProcessFrom;
    }

    public void setSafeguardProcessFrom(Double safeguardProcessFrom) {
        this.safeguardProcessFrom = safeguardProcessFrom;
    }

    public Double getSafeguardProcessTo() {
        return safeguardProcessTo;
    }

    public void setSafeguardProcessTo(Double safeguardProcessTo) {
        this.safeguardProcessTo = safeguardProcessTo;
    }

    public SafeguardType getSafeguardType() {
        return safeguardType;
    }

    public void setSafeguardType(SafeguardType safeguardType) {
        this.safeguardType = safeguardType;
    }

    public SafeguardProcess getSafeguardProcess() {
        return safeguardProcess;
    }

    public void setSafeguardProcess(SafeguardProcess safeguardProcess) {
        this.safeguardProcess = safeguardProcess;
    }

    public Date get_startTime() {
        return _startTime;
    }

    public void set_startTime(Date _startTime) {
        this._startTime = _startTime;
    }

    public Date get_endTime() {
        return _endTime;
    }

    public void set_endTime(Date _endTime) {
        this._endTime = _endTime;
    }

    public String getProcessCompoundName() {
        return processCompoundName;
    }

    public void setProcessCompoundName(String processCompoundName) {
        this.processCompoundName = processCompoundName;
    }
}