/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.DamagedLuggageService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 损坏行李表Entity
 *
 * @author wjp
 * @version 2016-03-14
 */

@Monitor(desc = "损坏行李表信息", tableName = "rms_damaged_luggage", service = DamagedLuggageService.class, socketNS = "/rms/damagedLuggage")
public class DamagedLuggage extends DataEntity<DamagedLuggage> {

    private static final long serialVersionUID = 1L;
    @Label( "创建时间")
    @MonitorField(desc = "创建时间")
    private Date createTime;        // 创建时间
    @Label( "更新时间")
    @MonitorField(desc = "更新时间")
    private Date updateTime;        // 更新时间
    @Label( "日期")
    @MonitorField(desc = "日期")
    private Date damagedDate;        // 日期
    @Label( "航班号")
    @MonitorField(desc = "航班号")
    private String flightNum;        // 航班号
    @Label( "姓名")
    @MonitorField(desc = "姓名")
    private String name;        // 姓名
    @Label( "行李名称")
    @MonitorField(desc = "行李名称")
    private String luggageName;        // 行李名称
    @Label( "破损程度编号")
    @MonitorField(desc = "破损程度编号")
    private String damagedConditionCode;        // 破损程度编号
    @Label( "破损程度名称")
    @MonitorField(desc = "破损程度名称")
    private String damagedConditionName;        // 破损程度名称
    @Label( "破损部位描述")
    @MonitorField(desc = "破损部位描述")
    private String damagedPart;        // 破损部位描述
    @Label( "购买行李价格")
    @MonitorField(desc = "购买行李价格")
    private Double luggagePrice;        // 购买行李价格
    @Label( "购买行日期")
    @MonitorField(desc = "购买行日期")
    private Date purchaseDate;        // 购买行日期
    @Label( "购买地点")
    @MonitorField(desc = "购买地点")
    private String purchasePrice;        // 购买地点
    @Label( "是否有发票")
    @MonitorField(desc = "是否有发票")
    private String isreceipt;        // 是否有发票
    @Label( "处理方式")
    @MonitorField(desc = "处理方式")
    private String processingMode;        // 处理方式
    @Label( "赔偿金额")
    @MonitorField(desc = "赔偿金额")
    private Double indemnify;        // 赔偿金额
    @Label( "冗余1")
    @MonitorField(desc = "冗余1")
    private String redundance1;        // 冗余1
    @Label( "冗余2")
    @MonitorField(desc = "冗余2")
    private String redundance2;        // 冗余2
    @Label( "冗余3")
    @MonitorField(desc = "冗余3")
    private String redundance3;        // 冗余3
    @Label( "冗余4")
    @MonitorField(desc = "冗余4")
    private String redundance4;        // 冗余4
    @Label( "冗余5")
    @MonitorField(desc = "冗余5")
    private String redundance5;        // 冗余5

    public DamagedLuggage() {
        super();
    }

    public DamagedLuggage(String id) {
        super(id);
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8:00")
    public Date getDamagedDate() {
        return damagedDate;
    }

    public void setDamagedDate(Date damagedDate) {
        this.damagedDate = damagedDate;
    }

    @Length(min = 0, max = 36, message = "航班号长度必须介于 0 和 36 之间")
    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    @Length(min = 0, max = 36, message = "姓名长度必须介于 0 和 36 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 36, message = "行李名称长度必须介于 0 和 36 之间")
    public String getLuggageName() {
        return luggageName;
    }

    public void setLuggageName(String luggageName) {
        this.luggageName = luggageName;
    }

    @Length(min = 0, max = 36, message = "破损程度编号长度必须介于 0 和 36 之间")
    public String getDamagedConditionCode() {
        return damagedConditionCode;
    }

    public void setDamagedConditionCode(String damagedConditionCode) {
        this.damagedConditionCode = damagedConditionCode;
    }

    @Length(min = 0, max = 500, message = "破损程度名称长度必须介于 0 和 500 之间")
    public String getDamagedConditionName() {
        return damagedConditionName;
    }

    public void setDamagedConditionName(String damagedConditionName) {
        this.damagedConditionName = damagedConditionName;
    }

    @Length(min = 0, max = 100, message = "破损部位描述长度必须介于 0 和 100 之间")
    public String getDamagedPart() {
        return damagedPart;
    }

    public void setDamagedPart(String damagedPart) {
        this.damagedPart = damagedPart;
    }

    public Double getLuggagePrice() {
        return luggagePrice;
    }

    public void setLuggagePrice(Double luggagePrice) {
        this.luggagePrice = luggagePrice;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8:00")
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Length(min = 0, max = 300, message = "购买地点长度必须介于 0 和 300 之间")
    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @Length(min = 0, max = 2, message = "是否有发票长度必须介于 0 和 2 之间")
    public String getIsreceipt() {
        return isreceipt;
    }

    public void setIsreceipt(String isreceipt) {
        this.isreceipt = isreceipt;
    }

    @Length(min = 0, max = 2000, message = "处理方式长度必须介于 0 和 2000 之间")
    public String getProcessingMode() {
        return processingMode;
    }

    public void setProcessingMode(String processingMode) {
        this.processingMode = processingMode;
    }

    public Double getIndemnify() {
        return indemnify;
    }

    public void setIndemnify(Double indemnify) {
        this.indemnify = indemnify;
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