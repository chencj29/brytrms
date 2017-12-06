/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Joiner;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 单进航班次日无任务机位表Entity
 *
 * @author wjp
 * @version 2017-09-13
 */
public class YesterAircraftNumPair extends DataEntity<YesterAircraftNumPair> {

    private static final long serialVersionUID = 1L;
    @MonitorField(desc = "配对ID")
    private String pairId;        // 配对ID
    @MonitorField(desc = "日期")
    private Date planDate;        // 日期
    @MonitorField(desc = "飞机号")
    private String aircraftNum;        // 飞机号
    @MonitorField(desc = "机位号")
    private String placeNum;        // 机位号
    @MonitorField(desc = "机位处理状态")
    private String placeStatus;        // 机位处理状态(null:未处理,1:已处理(航班初始化能匹配上的))
    @MonitorField(desc = "冲突时间计录")
    private String ext1;        // 扩展1 冲突时间计录
    @MonitorField(desc = "非次日航班使用时间计录")
    private String ext2;        // 扩展2 非次日航班使用时间计录
    @MonitorField(desc = "扩展3")
    private String ext3;        // 扩展3

    public YesterAircraftNumPair() {
        super();
    }

    public YesterAircraftNumPair(String id) {
        super(id);
    }

    @Length(min = 0, max = 100, message = "配对ID长度必须介于 0 和 100 之间")
    public String getPairId() {
        return pairId;
    }

    public void setPairId(String pairId) {
        this.pairId = pairId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    @Length(min = 0, max = 100, message = "飞机号长度必须介于 0 和 100 之间")
    public String getAircraftNum() {
        return aircraftNum;
    }

    public void setAircraftNum(String aircraftNum) {
        this.aircraftNum = aircraftNum;
    }

    @Length(min = 0, max = 100, message = "机位号长度必须介于 0 和 100 之间")
    public String getPlaceNum() {
        return placeNum;
    }

    public void setPlaceNum(String placeNum) {
        this.placeNum = placeNum;
    }

    @Length(min = 0, max = 100, message = "机位处理状态长度必须介于 0 和 100 之间")
    public String getPlaceStatus() {
        return placeStatus;
    }

    public void setPlaceStatus(String placeStatus) {
        this.placeStatus = placeStatus;
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
}