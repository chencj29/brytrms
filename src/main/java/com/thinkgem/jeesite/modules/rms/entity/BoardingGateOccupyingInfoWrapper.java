/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

public class BoardingGateOccupyingInfoWrapper extends FlightDynamic {

    private static final long serialVersionUID = 1L;
    private String flightDynamicId;        // 航班动态ID
    private String flightDynamicCode;        // 航班号
    private String flightDynamicNature;        // 航班属性(1 - 国内, 2 - 国际, 3 - 混合)
    private Long expectedCarouselNum;        // 预计占用行李转盘个数
    private Long actualCarouselNum;        // 实际占用行李转盘个数
    private String inteCarouselCode;        // 行李转盘编号(国内)
    private String intlCarouselCode;        // 行李转盘编号(国际)
    private Date expectedStartTime;        // 预计开始占用时间
    private Date expectedOverTime;        // 预计结束占用时间
    private Date inteActualStartTime;       // 实际开始占用时间(国内)
    private Date inteActualOverTime;        // 实际结束占用时间(国内)
    private Date intlActualStartTime;        // 实际开始占用时间(国际)
    private Date intlActualOverTime;        // 实际结束占用时间(国际)
    private String detailId;

    public BoardingGateOccupyingInfoWrapper() {
        super();
    }

    public BoardingGateOccupyingInfoWrapper(String id) {
        super(id);
    }

    @Length(min = 1, max = 36, message = "航班动态ID长度必须介于 1 和 36 之间")
    public String getFlightDynamicId() {
        return flightDynamicId;
    }

    public void setFlightDynamicId(String flightDynamicId) {
        this.flightDynamicId = flightDynamicId;
    }

    @Length(min = 1, max = 100, message = "航班号长度必须介于 1 和 100 之间")
    public String getFlightDynamicCode() {
        return flightDynamicCode;
    }

    public void setFlightDynamicCode(String flightDynamicCode) {
        this.flightDynamicCode = flightDynamicCode;
    }

    @Length(min = 1, max = 1, message = "航班属性长度必须介于 1 和 1 之间")
    public String getFlightDynamicNature() {
        return flightDynamicNature;
    }

    public void setFlightDynamicNature(String flightDynamicNature) {
        this.flightDynamicNature = flightDynamicNature;
    }

    public Long getExpectedCarouselNum() {
        return expectedCarouselNum;
    }

    public void setExpectedCarouselNum(Long expectedCarouselNum) {
        this.expectedCarouselNum = expectedCarouselNum;
    }

    public Long getActualCarouselNum() {
        return actualCarouselNum;
    }

    public void setActualCarouselNum(Long actualCarouselNum) {
        this.actualCarouselNum = actualCarouselNum;
    }

    @Length(min = 0, max = 100, message = "行李转盘编号(国内)长度必须介于 0 和 100 之间")
    public String getInteCarouselCode() {
        return inteCarouselCode;
    }

    public void setInteCarouselCode(String inteCarouselCode) {
        this.inteCarouselCode = inteCarouselCode;
    }

    @Length(min = 0, max = 100, message = "行李转盘编号(国际)长度必须介于 0 和 100 之间")
    public String getIntlCarouselCode() {
        return intlCarouselCode;
    }

    public void setIntlCarouselCode(String intlCarouselCode) {
        this.intlCarouselCode = intlCarouselCode;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getExpectedStartTime() {
        return expectedStartTime;
    }

    public void setExpectedStartTime(Date expectedStartTime) {
        this.expectedStartTime = expectedStartTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getExpectedOverTime() {
        return expectedOverTime;
    }

    public void setExpectedOverTime(Date expectedOverTime) {
        this.expectedOverTime = expectedOverTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getInteActualStartTime() {
        return inteActualStartTime;
    }

    public void setInteActualStartTime(Date inteActualStartTime) {
        this.inteActualStartTime = inteActualStartTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getInteActualOverTime() {
        return inteActualOverTime;
    }

    public void setInteActualOverTime(Date inteActualOverTime) {
        this.inteActualOverTime = inteActualOverTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getIntlActualStartTime() {
        return intlActualStartTime;
    }

    public void setIntlActualStartTime(Date intlActualStartTime) {
        this.intlActualStartTime = intlActualStartTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8:00")
    public Date getIntlActualOverTime() {
        return intlActualOverTime;
    }

    public void setIntlActualOverTime(Date intlActualOverTime) {
        this.intlActualOverTime = intlActualOverTime;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }
}