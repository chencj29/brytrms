/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.SafeguardTypeToProcessService;
import org.hibernate.validator.constraints.Length;

/**
 * 保障类型保障过程关联Entity
 *
 * @author chrischen
 * @version 2016-03-17
 */

@Monitor(desc = "保障类型保障过程关联信息", tableName = "rms_safeguard_type_to_process", service = SafeguardTypeToProcessService.class, socketNS = "/rms/safeguardTypeToProcess")
public class SafeguardTypeToProcess extends DataEntity<SafeguardTypeToProcess> {

    private static final long serialVersionUID = 1L;
    @Label("保障类型ID")
    @MonitorField(desc = "保障类型ID")
    private String safeguardTypeId;        // 保障类型ID
    @Label("保障过程ID")
    @MonitorField(desc = "保障过程ID")
    private String safeguardProcessId;        // 保障过程ID


    @Label("开始时间相对于计起前后")
    @MonitorField(desc = "开始时间相对于计起前后")
    private String startDepFb;        // 开始时间相对于计起前后
    @Label("开始时间相对于计起间隔")
    @MonitorField(desc = "开始时间相对于计起间隔")
    private Integer startDepRange;        // 开始时间相对于计起间隔

    @Label("开始时间相对于计达前后")
    @MonitorField(desc = "开始时间相对于计达前后")
    private String startArrFb;        // 开始时间相对于计达前后
    @Label("开始时间相对于计达间隔")
    @MonitorField(desc = "开始时间相对于计达间隔")
    private Integer startArrRange;        // 开始时间相对于计达间隔


    @Label("开始时间相对于过程ID")
    @MonitorField(desc = "开始时间相对于过程ID")
    private String startProId;        // 开始时间相对于过程ID
    @Label("开始时间相对于过程名称")
    @MonitorField(desc = "开始时间相对于过程名称")
    private String startProName;        // 开始时间相对于过程name
    @Label("开始时间相对于过程前后")
    @MonitorField(desc = "开始时间相对于过程前后")
    private String startProFb;        // 开始时间相对于过程前后
    @Label("开始时间相对于过程间隔")
    @MonitorField(desc = "开始时间相对于过程间隔")
    private Integer startProRange;        // 开始时间相对于过程间隔


    @Label("结束时间相对于计起前后")
    @MonitorField(desc = "结束时间相对于计起前后")
    private String endDepFb;        // 结束时间相对于计起前后
    @Label("结束时间相对于计起间隔")
    @MonitorField(desc = "结束时间相对于计起间隔")
    private Integer endDepRange;        // 结束时间相对于计起间隔

    @Label("结束时间相对于计达前后")
    @MonitorField(desc = "结束时间相对于计达前后")
    private String endArrFb;        // 结束时间相对于计达前后
    @Label("结束时间相对于计达间隔")
    @MonitorField(desc = "结束时间相对于计达间隔")
    private Integer endArrRange;        // 结束时间相对于计达间隔

    @Label("结束时间相对于过程ID")
    @MonitorField(desc = "结束时间相对于过程ID")
    private String endProId;        // 结束时间相对于过程ID
    @Label("结束时间相对于过程名称")
    @MonitorField(desc = "结束时间相对于过程名称")
    private String endProName;        // 结束时间相对于过程名称
    @Label("结束时间相对于过程前后")
    @MonitorField(desc = "结束时间相对于过程前后")
    private String endProFb;        // 结束时间相对于过程前后
    @Label("结束时间相对于过程间隔")
    @MonitorField(desc = "结束时间相对于过程间隔")
    private Integer endProRange;        // 结束时间相对于过程间隔

    @Label("排序")
    @MonitorField(desc = "排序")
    private Integer sort;        // 排序

    @Label("绝对时长")
    @MonitorField(desc = "绝对时长")
    private Integer absoluteRange;        // 绝对时长
    @Label("相对于整体时长百分比")
    @MonitorField(desc = "相对于整体时长百分比")
    private Integer percentRange;        // 相对于整体时长百分比

    /**
     * 表单勾选状态保存，用于数据回填，保存内容格式为json
     * {
     * porTimeType: [1, 2, 3], // checkbox 勾选了哪些类型
     * startTime_r: 1, // radio 选择了那个类型
     * endTime: 1, // radio 选择了那个类型
     * proRange_r: 2 // radio 选择了那个类型
     * }
     */
    private String selectedStatus;  // 表单勾选状态保存，用于数据回填


    @Label("冗余1")
    @MonitorField(desc = "冗余1")
    private String redundant1;        // 冗余1
    @Label("冗余2")
    @MonitorField(desc = "冗余2")
    private String redundant2;        // 冗余2
    @Label("冗余3")
    @MonitorField(desc = "冗余3")
    private String redundant3;        // 冗余3
    @Label("冗余4")
    @MonitorField(desc = "冗余4")
    private String redundant4;        // 冗余4
    @Label("冗余5")
    @MonitorField(desc = "冗余5")
    private String redundant5;        // 冗余5
    @Label("冗余6")
    @MonitorField(desc = "冗余6")
    private String redundant6;        // 冗余6
    @Label("冗余7")
    @MonitorField(desc = "冗余7")
    private String redundant7;        // 冗余7
    @Label("冗余8")
    @MonitorField(desc = "冗余8")
    private String redundant8;        // 冗余8
    @Label("冗余9")
    @MonitorField(desc = "冗余9")
    private String redundant9;        // 冗余9
    @Label("冗余10")
    @MonitorField(desc = "冗余10")
    private String redundant10;        // 冗余10

    public SafeguardTypeToProcess() {
        super();
    }

    public SafeguardTypeToProcess(String id) {
        super(id);
    }

    @Length(min = 0, max = 36, message = "保障类型ID长度必须介于 0 和 36 之间")
    public String getSafeguardTypeId() {
        return safeguardTypeId;
    }

    public void setSafeguardTypeId(String safeguardTypeId) {
        this.safeguardTypeId = safeguardTypeId;
    }

    @Length(min = 0, max = 36, message = "保障过程ID长度必须介于 0 和 36 之间")
    public String getSafeguardProcessId() {
        return safeguardProcessId;
    }

    public void setSafeguardProcessId(String safeguardProcessId) {
        this.safeguardProcessId = safeguardProcessId;
    }

    @Length(min = 0, max = 1, message = "开始时间相对于计起前后长度必须介于 0 和 1 之间")
    public String getStartDepFb() {
        return startDepFb;
    }

    public void setStartDepFb(String startDepFb) {
        this.startDepFb = startDepFb;
    }

    public Integer getStartDepRange() {
        return startDepRange;
    }

    public void setStartDepRange(Integer startDepRange) {
        this.startDepRange = startDepRange;
    }

    @Length(min = 0, max = 1, message = "开始时间相对于计达前后长度必须介于 0 和 1 之间")
    public String getStartArrFb() {
        return startArrFb;
    }

    public void setStartArrFb(String startArrFb) {
        this.startArrFb = startArrFb;
    }

    public Integer getStartArrRange() {
        return startArrRange;
    }

    public void setStartArrRange(Integer startArrRange) {
        this.startArrRange = startArrRange;
    }

    @Length(min = 0, max = 36, message = "开始时间相对于过程ID长度必须介于 0 和 36 之间")
    public String getStartProId() {
        return startProId;
    }

    public void setStartProId(String startProId) {
        this.startProId = startProId;
    }

    @Length(min = 0, max = 1, message = "开始时间相对于过程前后长度必须介于 0 和 1 之间")
    public String getStartProFb() {
        return startProFb;
    }

    public void setStartProFb(String startProFb) {
        this.startProFb = startProFb;
    }

    public Integer getStartProRange() {
        return startProRange;
    }

    public void setStartProRange(Integer startProRange) {
        this.startProRange = startProRange;
    }

    @Length(min = 0, max = 1, message = "结束时间相对于计起前后长度必须介于 0 和 1 之间")
    public String getEndDepFb() {
        return endDepFb;
    }

    public void setEndDepFb(String endDepFb) {
        this.endDepFb = endDepFb;
    }

    public Integer getEndDepRange() {
        return endDepRange;
    }

    public void setEndDepRange(Integer endDepRange) {
        this.endDepRange = endDepRange;
    }

    @Length(min = 0, max = 1, message = "结束时间相对于计达前后长度必须介于 0 和 1 之间")
    public String getEndArrFb() {
        return endArrFb;
    }

    public void setEndArrFb(String endArrFb) {
        this.endArrFb = endArrFb;
    }

    public Integer getEndArrRange() {
        return endArrRange;
    }

    public void setEndArrRange(Integer endArrRange) {
        this.endArrRange = endArrRange;
    }

    @Length(min = 0, max = 36, message = "结束时间相对于过程ID长度必须介于 0 和 36 之间")
    public String getEndProId() {
        return endProId;
    }

    public void setEndProId(String endProId) {
        this.endProId = endProId;
    }

    @Length(min = 0, max = 1, message = "结束时间相对于过程前后长度必须介于 0 和 1 之间")
    public String getEndProFb() {
        return endProFb;
    }

    public void setEndProFb(String endProFb) {
        this.endProFb = endProFb;
    }

    public Integer getEndProRange() {
        return endProRange;
    }

    public void setEndProRange(Integer endProRange) {
        this.endProRange = endProRange;
    }

    public Integer getAbsoluteRange() {
        return absoluteRange;
    }

    public void setAbsoluteRange(Integer absoluteRange) {
        this.absoluteRange = absoluteRange;
    }

    public Integer getPercentRange() {
        return percentRange;
    }

    public void setPercentRange(Integer percentRange) {
        this.percentRange = percentRange;
    }

    @Length(min = 0, max = 100, message = "冗余1长度必须介于 0 和 100 之间")
    public String getRedundant1() {
        return redundant1;
    }

    public void setRedundant1(String redundant1) {
        this.redundant1 = redundant1;
    }

    @Length(min = 0, max = 100, message = "冗余2长度必须介于 0 和 100 之间")
    public String getRedundant2() {
        return redundant2;
    }

    public void setRedundant2(String redundant2) {
        this.redundant2 = redundant2;
    }

    @Length(min = 0, max = 100, message = "冗余3长度必须介于 0 和 100 之间")
    public String getRedundant3() {
        return redundant3;
    }

    public void setRedundant3(String redundant3) {
        this.redundant3 = redundant3;
    }

    @Length(min = 0, max = 100, message = "冗余4长度必须介于 0 和 100 之间")
    public String getRedundant4() {
        return redundant4;
    }

    public void setRedundant4(String redundant4) {
        this.redundant4 = redundant4;
    }

    @Length(min = 0, max = 100, message = "冗余5长度必须介于 0 和 100 之间")
    public String getRedundant5() {
        return redundant5;
    }

    public void setRedundant5(String redundant5) {
        this.redundant5 = redundant5;
    }

    @Length(min = 0, max = 100, message = "冗余6长度必须介于 0 和 100 之间")
    public String getRedundant6() {
        return redundant6;
    }

    public void setRedundant6(String redundant6) {
        this.redundant6 = redundant6;
    }

    @Length(min = 0, max = 100, message = "冗余7长度必须介于 0 和 100 之间")
    public String getRedundant7() {
        return redundant7;
    }

    public void setRedundant7(String redundant7) {
        this.redundant7 = redundant7;
    }

    @Length(min = 0, max = 100, message = "冗余8长度必须介于 0 和 100 之间")
    public String getRedundant8() {
        return redundant8;
    }

    public void setRedundant8(String redundant8) {
        this.redundant8 = redundant8;
    }

    @Length(min = 0, max = 100, message = "冗余9长度必须介于 0 和 100 之间")
    public String getRedundant9() {
        return redundant9;
    }

    public void setRedundant9(String redundant9) {
        this.redundant9 = redundant9;
    }

    @Length(min = 0, max = 100, message = "冗余10长度必须介于 0 和 100 之间")
    public String getRedundant10() {
        return redundant10;
    }

    public void setRedundant10(String redundant10) {
        this.redundant10 = redundant10;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(String selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public String getStartProName() {
        return startProName;
    }

    public void setStartProName(String startProName) {
        this.startProName = startProName;
    }

    public String getEndProName() {
        return endProName;
    }

    public void setEndProName(String endProName) {
        this.endProName = endProName;
    }
}