/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.DepartureHallDao;
import com.thinkgem.jeesite.modules.rms.entity.DepartureHall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 候机厅基础信息Service
 *
 * @author wjp
 * @version 2016-03-19
 */
@Service
@Transactional(readOnly = true)
public class DepartureHallService extends CrudService<DepartureHallDao, DepartureHall> {

    public DepartureHall get(String id) {
        return super.get(id);
    }

    public List<DepartureHall> findList(DepartureHall departureHall) {
        return super.findList(departureHall);
    }

    public Page<DepartureHall> findPage(Page<DepartureHall> page, DepartureHall departureHall) {
        return super.findPage(page, departureHall);
    }

    public void save(DepartureHall departureHall) {
        super.save(departureHall);
    }

    public void delete(DepartureHall departureHall) {
        super.delete(departureHall);
    }


    @Transactional(readOnly = true)
    public List<DepartureHall> checkStatusByDepartureHallNum(List<String> list) {
        return dao.findByDepartureHallNum(list).stream().filter(departureHall -> !departureHall.getDepartureHallStatus().equals("0")).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DepartureHall getByCode(String departureHallCode) {
        return dao.getByCode(departureHallCode);
    }

    public List<DepartureHall> findAvailableDepartureHallByNature(String nature) {
        return dao.findAvailableDepartureHallByNature(nature);
    }

}