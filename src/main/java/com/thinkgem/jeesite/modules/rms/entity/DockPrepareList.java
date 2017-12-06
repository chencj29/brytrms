/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.DockPrepareListService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 航班预配单信息Entity
 *
 * @author wjp
 * @version 2016-03-21
 */

@Monitor(desc = "航班预配单信息", tableName = "rms_dock_prepare_list", service = DockPrepareListService.class, socketNS = "/rms/dockPrepareList")
public class DockPrepareList extends DataEntity<DockPrepareList> {

    private static final long serialVersionUID = 1L;
    @Label("创建时间")
    @MonitorField(desc = "创建时间")
    private Date createTime;        // 创建时间
    @Label("更新时间")
    @MonitorField(desc = "更新时间")
    private Date updateTime;        // 更新时间
    @Label("货单号")
    @MonitorField(desc = "货单号")
    private String cargoNum;        // 货单号
    @Label("货物名称")
    @MonitorField(desc = "货物名称")
    private String cargoName;        // 货物名称
    @Label("件数")
    @MonitorField(desc = "件数")
    private Long count;        // 件数
    @Label("重量")
    @MonitorField(desc = "重量")
    private Double weight;        // 重量
    @Label("规格")
    @MonitorField(desc = "规格")
    private String specification;        // 规格
    @Label("航班号")
    @MonitorField(desc = "航班号")
    private String flightNum;        // 航班号
    @Label("航班日期")
    @MonitorField(desc = "航班日期")
    private Date flightDate;        // 航班日期

    public DockPrepareList() {
        super();
    }

    public DockPrepareList(String id) {
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

    @Length(min = 0, max = 30, message = "货单号长度必须介于 0 和 30 之间")
    public String getCargoNum() {
        return cargoNum;
    }

    public void setCargoNum(String cargoNum) {
        this.cargoNum = cargoNum;
    }

    @Length(min = 0, max = 200, message = "货物名称长度必须介于 0 和 200 之间")
    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Length(min = 0, max = 20, message = "规格长度必须介于 0 和 20 之间")
    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    @Length(min = 1, max = 36, message = "航班号长度必须介于 1 和 36 之间")
    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8:00")
    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

}