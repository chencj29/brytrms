/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.SlideCoastDao;
import com.thinkgem.jeesite.modules.rms.entity.SlideCoast;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 滑槽基础信息Service
 *
 * @author wjp
 * @version 2016-03-19
 */
@Service
@Transactional(readOnly = true)
public class SlideCoastService extends CrudService<SlideCoastDao, SlideCoast> {

    public SlideCoast get(String id) {
        return super.get(id);
    }

    public List<SlideCoast> findList(SlideCoast slideCoast) {
        return super.findList(slideCoast);
    }

    public Page<SlideCoast> findPage(Page<SlideCoast> page, SlideCoast slideCoast) {
        return super.findPage(page, slideCoast);
    }

    public void save(SlideCoast slideCoast) {
        super.save(slideCoast);
    }

    public void delete(SlideCoast slideCoast) {
        super.delete(slideCoast);
    }

    @Transactional(readOnly = true)
    public List<SlideCoast> checkStatusBySlideCoastNum(List<String> list) {
        return dao.findBySlideCoastNum(list).stream().filter(slideCoast -> !slideCoast.getSlideCoastStatus().equals("0")).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SlideCoast getByCode(String slideCoastNum) {
        return dao.getByCode(slideCoastNum);
    }

    public List<SlideCoast> findAvailableSlideCoastByNature(String nature) {
        return dao.findAvailableSlideCoastByNature(nature);
    }

}