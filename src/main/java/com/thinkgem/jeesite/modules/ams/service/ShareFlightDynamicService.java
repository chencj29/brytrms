/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ams.dao.ShareFlightDynamicDao;
import com.thinkgem.jeesite.modules.ams.entity.ShareFlightDynamic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 共享航班与航班动态关联Service
 *
 * @author chrischen
 * @version 2016-02-03
 */
@Service
@Transactional(readOnly = true)
public class ShareFlightDynamicService extends CrudService<ShareFlightDynamicDao, ShareFlightDynamic> {

    public ShareFlightDynamic get(String id) {
        return super.get(id);
    }

    public List<ShareFlightDynamic> findList(ShareFlightDynamic shareFlightDynamic) {
        return super.findList(shareFlightDynamic);
    }

    public Page<ShareFlightDynamic> findPage(Page<ShareFlightDynamic> page, ShareFlightDynamic shareFlightDynamic) {
        return super.findPage(page, shareFlightDynamic);
    }

    @Transactional(readOnly = false)
    public void save(ShareFlightDynamic shareFlightDynamic) {
        super.save(shareFlightDynamic);
    }

    @Transactional(readOnly = false)
    public void delete(ShareFlightDynamic shareFlightDynamic) {
        super.delete(shareFlightDynamic);
    }

}