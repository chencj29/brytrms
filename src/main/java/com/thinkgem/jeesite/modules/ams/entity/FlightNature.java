/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.ams.service.FlightNatureService;
import org.hibernate.validator.constraints.Length;

/**
 * 航班性质管理Entity
 *
 * @author xiaopo
 * @version 2015-12-14
 */
@Monitor(desc = "航班性质信息", tableName = "AMS_FLIGHT_NATURE", service = FlightNatureService.class, socketNS = "/rms/flightNature")
public class FlightNature extends DataEntity<FlightNature> {

    private static final long serialVersionUID = 1L;
    @TipMainField
    @MonitorField(desc = "性质编号")
    @Label("性质编号")
    private String natureNum;        // 性质编号
    @MonitorField(desc = "性质名称")
    @Label("性质名称")
    private String natureName;        // 性质名称
    @MonitorField(desc = "英文名称")
    @Label("英文名称")
    private String englishName;        // 英文名称
    @MonitorField(desc = "SITA编码")
    @Label("SITA编码")
    private String sita;        // SITA编码
    @MonitorField(desc = "AFTN编码")
    @Label("AFTN编码")
    private String aftn;        // AFTN编码


    public FlightNature() {
        super();
    }

    public FlightNature(String id) {
        super(id);
    }

    @Length(min = 0, max = 100, message = "性质编号长度必须介于 0 和 100 之间")
    public String getNatureNum() {
        return natureNum;
    }

    public void setNatureNum(String natureNum) {
        this.natureNum = natureNum;
    }

    @Length(min = 0, max = 300, message = "性质名称长度必须介于 0 和 300 之间")
    public String getNatureName() {
        return natureName;
    }

    public void setNatureName(String natureName) {
        this.natureName = natureName;
    }

    @Length(min = 0, max = 200, message = "英文名称长度必须介于 0 和 200 之间")
    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    @Length(min = 0, max = 100, message = "SITA编码长度必须介于 0 和 100 之间")
    public String getSita() {
        return sita;
    }

    public void setSita(String sita) {
        this.sita = sita;
    }

    @Length(min = 0, max = 100, message = "AFTN编码长度必须介于 0 和 100 之间")
    public String getAftn() {
        return aftn;
    }

    public void setAftn(String aftn) {
        this.aftn = aftn;
    }

}