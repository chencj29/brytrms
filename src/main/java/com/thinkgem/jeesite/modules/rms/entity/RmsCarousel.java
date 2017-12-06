/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.RmsCarouselService;
import org.hibernate.validator.constraints.Length;

/**
 * 行李转盘基础信息表Entity
 *
 * @author wjp
 * @version 2016-03-19
 */

@Monitor(desc = "行李转盘基础信息表信息", tableName = "rms_carousel", service = RmsCarouselService.class, socketNS = "/rms/rmsCarousel")
public class RmsCarousel extends DataEntity<RmsCarousel> {

    private static final long serialVersionUID = 1L;
    @Label("航站楼编号")
    @MonitorField(desc = "航站楼编号")
    private String airportTerminalCode;        // 航站楼编号
    @Label("航站楼名称")
    @MonitorField(desc = "航站楼名称")
    private String airportTerminalName;        // 航站楼名称
    @Label("行李转盘编号")
    @MonitorField(desc = "行李转盘编号")
    private String carouselNum;        // 行李转盘编号
    @Label("行李转盘性质")
    @MonitorField(desc = "行李转盘性质")
    private String carouselNature;        // 行李转盘性质
    @Label("行李转盘状态")
    @MonitorField(desc = "行李转盘状态")
    private String carouselTypeCode;        // 行李转盘状态
    @Label("行李转盘状态名称")
    @MonitorField(desc = "行李转盘状态名称")
    private String carouselTypeName;        // 行李转盘状态名称

    @Label("排序字段")
    @MonitorField(desc = "排序字段")
    private Integer orderIndex;

    public RmsCarousel() {
        super();
    }

    public RmsCarousel(String id) {
        super(id);
    }

    @Length(min = 0, max = 36, message = "航站楼编号长度必须介于 0 和 36 之间")
    public String getAirportTerminalCode() {
        return airportTerminalCode;
    }

    public void setAirportTerminalCode(String airportTerminalCode) {
        this.airportTerminalCode = airportTerminalCode;
    }

    @Length(min = 0, max = 200, message = "航站楼名称长度必须介于 0 和 200 之间")
    public String getAirportTerminalName() {
        return airportTerminalName;
    }

    public void setAirportTerminalName(String airportTerminalName) {
        this.airportTerminalName = airportTerminalName;
    }

    @Length(min = 0, max = 36, message = "行李转盘编号长度必须介于 0 和 36 之间")
    public String getCarouselNum() {
        return carouselNum;
    }

    public void setCarouselNum(String carouselNum) {
        this.carouselNum = carouselNum;
    }

    @Length(min = 0, max = 200, message = "行李转盘性质长度必须介于 0 和 200 之间")
    public String getCarouselNature() {
        return carouselNature;
    }

    public void setCarouselNature(String carouselNature) {
        this.carouselNature = carouselNature;
    }

    @Length(min = 0, max = 36, message = "行李转盘状态长度必须介于 0 和 36 之间")
    public String getCarouselTypeCode() {
        return carouselTypeCode;
    }

    public void setCarouselTypeCode(String carouselTypeCode) {
        this.carouselTypeCode = carouselTypeCode;
    }

    @Length(min = 0, max = 36, message = "行李转盘状态名称长度必须介于 0 和 36 之间")
    public String getCarouselTypeName() {
        return carouselTypeName;
    }

    public void setCarouselTypeName(String carouselTypeName) {
        this.carouselTypeName = carouselTypeName;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
}