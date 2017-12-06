/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.BoardingGateService;
import org.hibernate.validator.constraints.Length;

/**
 * 登机口信息Entity
 *
 * @author wjp
 * @version 2016-03-19
 */

@Monitor(desc = "登机口信息", tableName = "rms_boarding_gate", service = BoardingGateService.class, socketNS = "/rms/boardingGate")
public class BoardingGate extends DataEntity<BoardingGate> {

    private static final long serialVersionUID = 1L;
    @Label("登机口编号")
    @MonitorField(desc = "登机口编号")
    private String boardingGateNum;        // 登机口编号
    @Label("登机口性质")
    @MonitorField(desc = "登机口性质")
    private String boardingGateNature;        // 登机口性质
    @Label("登机口状态")
    @MonitorField(desc = "登机口状态")
    private String boardingGateStatus;        // 登机口状态
    @Label("所属航站楼")
    @MonitorField(desc = "所属航站楼")
    private String airportTerminalCode;        // 所属航站楼

    @Label("排序字段")
    @MonitorField(desc = "排序字段")
    private Integer orderIndex;

    public BoardingGate() {
        super();
    }

    public BoardingGate(String id) {
        super(id);
    }

    @Length(min = 0, max = 36, message = "登机口编号长度必须介于 0 和 36 之间")
    public String getBoardingGateNum() {
        return boardingGateNum;
    }

    public void setBoardingGateNum(String boardingGateNum) {
        this.boardingGateNum = boardingGateNum;
    }

    @Length(min = 0, max = 10, message = "登机口性质长度必须介于 0 和 10 之间")
    public String getBoardingGateNature() {
        return boardingGateNature;
    }

    public void setBoardingGateNature(String boardingGateNature) {
        this.boardingGateNature = boardingGateNature;
    }

    @Length(min = 0, max = 1, message = "登机口状态长度必须介于 0 和 1 之间")
    public String getBoardingGateStatus() {
        return boardingGateStatus;
    }

    public void setBoardingGateStatus(String boardingGateStatus) {
        this.boardingGateStatus = boardingGateStatus;
    }

    @Length(min = 0, max = 5, message = "所属航站楼长度必须介于 0 和 5 之间")
    public String getAirportTerminalCode() {
        return airportTerminalCode;
    }

    public void setAirportTerminalCode(String airportTerminalCode) {
        this.airportTerminalCode = airportTerminalCode;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }


}