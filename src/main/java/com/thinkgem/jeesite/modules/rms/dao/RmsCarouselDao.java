/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsCarousel;

import java.util.List;

/**
 * 行李转盘基础信息表DAO接口
 *
 * @author wjp
 * @version 2016-03-19
 */
@MyBatisDao
public interface RmsCarouselDao extends CrudDao<RmsCarousel> {
    List<RmsCarousel> findByCarouselNum(List<String> list);

    RmsCarousel getByCode(String carouselCode);

    List<RmsCarousel> findAvailableCarouselByNature(String nature);
}