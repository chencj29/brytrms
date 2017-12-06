/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.ams.service.ShareFlightDynamicService;
import org.hibernate.validator.constraints.Length;

/**
 * 共享航班与航班动态关联Entity
 *
 * @author chrischen
 * @version 2016-02-03
 */
@Monitor(desc = "共享航班与航班动态关联", tableName = "AMS_SHARE_FLIGHT_DYNAMIC", service = ShareFlightDynamicService.class, socketNS = "/rms/shaeFlightDynamic")
public class ShareFlightDynamic extends DataEntity<ShareFlightDynamic> {

    private static final long serialVersionUID = 1L;
    @TipMainField
    @MonitorField(desc = "共享航班ID")
    @Label("共享航班ID")
    private String shareFlightId;        // 共享航班ID
    @MonitorField(desc = "航班动态ID")
    @Label("航班动态ID")
    private String flightDynamicId;        // 航班动态ID

    public ShareFlightDynamic() {
        super();
    }

    public ShareFlightDynamic(String id) {
        super(id);
    }

    @Length(min = 1, max = 36, message = "共享航班ID长度必须介于 1 和 36 之间")
    public String getShareFlightId() {
        return shareFlightId;
    }

    public void setShareFlightId(String shareFlightId) {
        this.shareFlightId = shareFlightId;
    }

    @Length(min = 1, max = 36, message = "航班动态ID长度必须介于 1 和 36 之间")
    public String getFlightDynamicId() {
        return flightDynamicId;
    }

    public void setFlightDynamicId(String flightDynamicId) {
        this.flightDynamicId = flightDynamicId;
    }

}