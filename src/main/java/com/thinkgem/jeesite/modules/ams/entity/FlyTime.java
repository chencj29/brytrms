/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.ams.service.FlyTimeService;
import org.hibernate.validator.constraints.Length;

/**
 * 飞越时间管理Entity
 *
 * @author chrischen
 * @version 2016-02-04
 */
@Monitor(desc = "飞越时间信息", tableName = "AMS_FLY_TIME", service = FlyTimeService.class, socketNS = "/ams/flyTime")
public class FlyTime extends DataEntity<FlyTime> {

    private static final long serialVersionUID = 1L;
    @TipMainField
    @MonitorField(desc = "起飞地")
    @Label("起飞地")
    private String startSite;        // 起飞地
    @MonitorField(desc = "起飞地3字码")
    @Label("起飞地3字码")
    private String startSiteCode;        // 起飞地3字码
    @MonitorField(desc = "到达地")
    @Label("到达地")
    private String endSite;        // 到达地
    @MonitorField(desc = "到达地3字码")
    @Label("到达地3字码")
    private String endSiteCode;        // 到达地3字码
    @MonitorField(desc = "飞越时间")
    @Label("飞越时间")
    private Integer flyTime;        // 飞越时间

    public FlyTime() {
        super();
    }

    public FlyTime(String id) {
        super(id);
    }

    @Length(min = 0, max = 50, message = "起飞地长度必须介于 0 和 50 之间")
    public String getStartSite() {
        return startSite;
    }

    public void setStartSite(String startSite) {
        this.startSite = startSite;
    }

    @Length(min = 0, max = 3, message = "起飞地3字码长度必须介于 0 和 3 之间")
    public String getStartSiteCode() {
        return startSiteCode;
    }

    public void setStartSiteCode(String startSiteCode) {
        this.startSiteCode = startSiteCode;
    }

    @Length(min = 0, max = 50, message = "到达地长度必须介于 0 和 50 之间")
    public String getEndSite() {
        return endSite;
    }

    public void setEndSite(String endSite) {
        this.endSite = endSite;
    }

    @Length(min = 0, max = 3, message = "到达地3字码长度必须介于 0 和 3 之间")
    public String getEndSiteCode() {
        return endSiteCode;
    }

    public void setEndSiteCode(String endSiteCode) {
        this.endSiteCode = endSiteCode;
    }

    public Integer getFlyTime() {
        return flyTime;
    }

    public void setFlyTime(Integer flyTime) {
        this.flyTime = flyTime;
    }

}