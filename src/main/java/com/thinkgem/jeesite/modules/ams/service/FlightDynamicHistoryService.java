/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ams.dao.FlightDynamicDao;
import com.thinkgem.jeesite.modules.ams.dao.FlightDynamicHistoryDao;
import com.thinkgem.jeesite.modules.ams.dao.FlightPlanDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.FlightPairWrapper;
import com.thinkgem.jeesite.modules.ams.entity.FlightPlan;
import com.thinkgem.jeesite.modules.ams.entity.FlightPlanDetail;
import com.thinkgem.jeesite.modules.ams.utils.AmsConstants;
import com.thinkgem.jeesite.modules.rms.entity.CarouselOccupyingInfoWrapper;
import com.thinkgem.jeesite.modules.rms.entity.CheckingCounterOccupyingInfoWrapper;
import com.thinkgem.jeesite.modules.rms.service.ServiceProviderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 航班动态管理Service
 *
 * @author xiaopo
 * @version 2016-01-10
 */
@Service
@Transactional(readOnly = true)
public class FlightDynamicHistoryService extends CrudService<FlightDynamicHistoryDao, FlightDynamic> {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private FlightPlanDetailService flightPlanDetailService;

	@Autowired
	private ServiceProviderService serviceProviderService;

	@Autowired
	private AmsAddressService addressService;

	@Autowired
	private FlightPlanDao flightPlanDao;

	@Autowired
	private FlightDynamicHistoryDao flightDynamicHistoryDao;

	@Autowired
	private FlightDynamicDao flightDynamicDao;

	@Autowired
	private FlightPlanService flightPlanService;

	public FlightDynamic get(String id) {
		return super.get(id);
	}

	public FlightDynamic getHis(String id){
		return flightDynamicHistoryDao.getHis(id);
	}

	public List<FlightDynamic> findList(FlightDynamic flightDynamic) {
		return super.findList(flightDynamic);
	}

	public Page<FlightDynamic> findPage(Page<FlightDynamic> page, FlightDynamic flightDynamic) {
		return super.findPage(page, flightDynamic);
	}

	@Transactional(readOnly = false)
	public void save(FlightDynamic flightDynamic) {
		super.save(flightDynamic);
	}

	@Transactional(readOnly = false)
	public void delete(FlightDynamic flightDynamic) {
		super.delete(flightDynamic);
	}

	public void delay(FlightDynamic flightDynamic) {
		if (flightDynamic.getDelayTimePend() == 1) {
			flightDynamic.setArrivalPlanTime(null);
		}
		// 设置航班状态
		flightDynamic.setStatus(AmsConstants.FLIGHT_STATUS_DELAY);
		flightDynamic.setStatusName(AmsConstants.FLIGHT_STATUS_DELAY_NAME);
		// 时间待定
		dao.delay(flightDynamic);
	}

	public List<FlightDynamic> findListAgo(Date date){
		List<FlightDynamic> dynamicList = null;
		dynamicList = dao.findListAgo(ImmutableMap.of("planDate",date));
		return dynamicList;
	}


	public void initFlightPlanByDynamic(Date planDate,Date dynaDate){
		// 创建flightplan

		FlightPlan flightPlan = new FlightPlan();
		flightPlan.setPlanTime(planDate);
		flightPlan.setStatus(0);
		flightPlan.setInitTime(new Date());
		flightPlan.setIschanged(0);

		// 根据dynaDate 查询出该日期下的所有动态
		List<FlightDynamic> flightDynamics = findListAgo(dynaDate);
		FlightPlanDetail flightPlanDetail = null;
		for (FlightDynamic dynamic : flightDynamics) {
			flightPlanDetail = new FlightPlanDetail();
			BeanUtils.copyProperties(dynamic,flightPlanDetail);
			flightPlanDetail.setId(null);
			flightPlan.getFlightPlanDetailList().add(flightPlanDetail);
		}

		flightPlanService.save(flightPlan);
	}

	/**
	 * 保存完成的数据到历史数据
	 * @param ids
     */
	public void saveFromCompletedDynamic(String ids) {
		if (StringUtils.isBlank(ids)) {
			return;
		}
		String[] idArray = ids.split(",");
		for (String dynamicId: idArray) {
			FlightDynamic fd = flightDynamicDao.get(dynamicId);
			flightDynamicDao.delete(fd);
			fd.setId(UUID.randomUUID().toString());
			flightDynamicHistoryDao.insert(fd);
		}
	}

	@Transactional(readOnly = true)
	public List<CheckingCounterOccupyingInfoWrapper> checkinCounterList(FlightDynamic flightDynamic) {
		flightDynamic.getSqlMap().put("dsfn",dataScopeFilterNew(flightDynamic.getCurrentUser()));
		return dao.checkinCounterList(flightDynamic);
	}

	@Transactional(readOnly = true)
	public List<CarouselOccupyingInfoWrapper> carouselList(FlightDynamic flightDynamic) {
		flightDynamic.getSqlMap().put("dsfn",dataScopeFilterNew(flightDynamic.getCurrentUser()));
		return dao.carouselList(flightDynamic);
	}

}