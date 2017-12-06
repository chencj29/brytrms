/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.ArrivalGateDao;
import com.thinkgem.jeesite.modules.rms.entity.ArrivalGate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 到港口基础信息Service
 *
 * @author wjp
 * @version 2016-03-19
 */
@Service
@Transactional(readOnly = true)
public class ArrivalGateService extends CrudService<ArrivalGateDao, ArrivalGate> {

    public ArrivalGate get(String id) {
        return super.get(id);
    }

    public List<ArrivalGate> findList(ArrivalGate arrivalGate) {
        return super.findList(arrivalGate);
    }

    public Page<ArrivalGate> findPage(Page<ArrivalGate> page, ArrivalGate arrivalGate) {
        return super.findPage(page, arrivalGate);
    }

    @Transactional(readOnly = true)
    public void save(ArrivalGate arrivalGate) {
        super.save(arrivalGate);
    }

    @Transactional(readOnly = true)
    public void delete(ArrivalGate arrivalGate) {
        super.delete(arrivalGate);
    }

    @Transactional(readOnly = true)
    public List<ArrivalGate> checkStatusByArrivalGateNum(List<String> list) {
        return dao.findByArrivalGateNum(list).stream().filter(arrivalGate -> !arrivalGate.getArrivalGateStatus().equals("0")).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArrivalGate getByCode(String arrivalGateCode) {
        return dao.getByCode(arrivalGateCode);
    }

    public List<ArrivalGate> findAvailableArrivalGateByNature(String nature) {
        return dao.findAvailableArrivalGateByNature(nature);
    }
}