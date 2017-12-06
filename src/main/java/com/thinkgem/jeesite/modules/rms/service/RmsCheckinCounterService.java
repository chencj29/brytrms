/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.RmsCheckinCounterDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsCheckinCounter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 值机柜台基础信息表Service
 *
 * @author wjp
 * @version 2016-03-07
 */
@Service
@Transactional(readOnly = true)
public class RmsCheckinCounterService extends CrudService<RmsCheckinCounterDao, RmsCheckinCounter> {

    public RmsCheckinCounter get(String id) {
        return super.get(id);
    }

    public List<RmsCheckinCounter> findList(RmsCheckinCounter rmsCheckinCounter) {
        return super.findList(rmsCheckinCounter);
    }

    public Page<RmsCheckinCounter> findPage(Page<RmsCheckinCounter> page, RmsCheckinCounter rmsCheckinCounter) {
        return super.findPage(page, rmsCheckinCounter);
    }

    public void save(RmsCheckinCounter rmsCheckinCounter) {
        super.save(rmsCheckinCounter);
    }

    public void delete(RmsCheckinCounter rmsCheckinCounter) {
        super.delete(rmsCheckinCounter);
    }


    @Transactional(readOnly = true)
    public List<RmsCheckinCounter> checkStatusByCheckinCounterNum(List<String> list) {
        return dao.findByCheckinCounterNum(list).stream().filter(rmsCheckinCounter -> !rmsCheckinCounter.getCheckinCounterTypeCode().equals("0")).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RmsCheckinCounter getByCode(String checkinCounterCode) {
        return dao.getByCode(checkinCounterCode);
    }

    public List<RmsCheckinCounter> findAvailableCheckingCounterByNature(String nature) {
        return dao.findAvailableCheckingCounterByNature(nature);
    }

}