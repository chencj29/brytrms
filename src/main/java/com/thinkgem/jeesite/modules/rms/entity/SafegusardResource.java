/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;


import cn.net.metadata.datasync.monitor.annotation.Monitor;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.SafegusardResourceService;

/**
 * 保障资源表Entity
 *
 * @author wjp
 * @version 2016-04-15
 */

@Monitor(desc = "保障资源表信息", tableName = "rms_safegusard_resource", service = SafegusardResourceService.class, socketNS = "/rms/safegusardResource")
public class SafegusardResource extends DataEntity<SafegusardResource> {

    private static final long serialVersionUID = 1L;

    public SafegusardResource() {
        super();
    }

    public SafegusardResource(String id) {
        super(id);
    }

}