/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import java.util.List;

import com.thinkgem.jeesite.modules.rms.entity.SecurityPairWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.entity.SecurityServiceInfo;
import com.thinkgem.jeesite.modules.rms.dao.SecurityServiceInfoDao;

/**
 * 安检航班信息Service
 *
 * @author wjp
 * @version 2017-11-03
 */
@Service
@Transactional(readOnly = true)
public class SecurityServiceInfoService extends CrudService<SecurityServiceInfoDao, SecurityServiceInfo> {

    public SecurityServiceInfo get(String id) {
        return super.get(id);
    }

    public List<SecurityPairWrapper> findListWapper(SecurityServiceInfo securityServiceInfo) {
        return dao.findListWapper(securityServiceInfo);
    }

    public Page<SecurityServiceInfo> findPage(Page<SecurityServiceInfo> page, SecurityServiceInfo securityServiceInfo) {
        return super.findPage(page, securityServiceInfo);
    }

    @Transactional(readOnly = false)
    public void save(SecurityServiceInfo securityServiceInfo) {
        super.save(securityServiceInfo);
    }

    @Transactional(readOnly = false)
    public void delete(SecurityServiceInfo securityServiceInfo) {
        super.delete(securityServiceInfo);
    }

    public List<SecurityServiceInfo> findByFlightDynamicIc(SecurityServiceInfo securityServiceInfo) {
        return dao.findByFlightDynamicIc(securityServiceInfo);
    }
}