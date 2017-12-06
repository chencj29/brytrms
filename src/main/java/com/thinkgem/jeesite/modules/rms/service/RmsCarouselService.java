/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.RmsCarouselDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsCarousel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 行李转盘基础信息表Service
 *
 * @author wjp
 * @version 2016-03-19
 */
@Service
@Transactional(readOnly = true)
public class RmsCarouselService extends CrudService<RmsCarouselDao, RmsCarousel> {

    public RmsCarousel get(String id) {
        return super.get(id);
    }

    public List<RmsCarousel> findList(RmsCarousel rmsCarousel) {
        return super.findList(rmsCarousel);
    }

    public Page<RmsCarousel> findPage(Page<RmsCarousel> page, RmsCarousel rmsCarousel) {
        return super.findPage(page, rmsCarousel);
    }

    @Transactional(readOnly = false)
    public void save(RmsCarousel rmsCarousel) {
        super.save(rmsCarousel);
    }

    @Transactional(readOnly = false)
    public void delete(RmsCarousel rmsCarousel) {
        super.delete(rmsCarousel);
    }

    @Transactional(readOnly = true)
    public List<RmsCarousel> checkStatusByCarouselNum(List<String> list) {
        return dao.findByCarouselNum(list).stream().filter(carousel -> !carousel.getCarouselTypeCode().equals("0")).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RmsCarousel> findByCarouselNum(List<String> list) {
        return dao.findByCarouselNum(list);
    }

    @Transactional(readOnly = true)
    public RmsCarousel getByCode(String carouselCode) {
        return dao.getByCode(carouselCode);
    }

    public List<RmsCarousel> findAvailableCarouselByNature(String nature) {
        return dao.findAvailableCarouselByNature(nature);
    }


}