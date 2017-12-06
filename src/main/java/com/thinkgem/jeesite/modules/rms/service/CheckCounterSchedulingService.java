/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.dao.FlightDynamicCheckinDao;
import com.thinkgem.jeesite.modules.rms.entity.FlightDynamicCheckin;
import com.thinkgem.jeesite.modules.rms.entity.RmsEmp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 值机管理Controller
 *
 * @author xiaopo
 * @version 2016-05-24
 */
@Service
public class CheckCounterSchedulingService {

	@Autowired
	private FlightDynamicService dynamicService;

	@Autowired
	private RmsEmpService empService;

	@Autowired
	private FlightDynamicCheckinService dynamicCheckinService;

	@Autowired
	private FlightDynamicCheckinDao dynamicCheckinDao;


	public void scheduling(String dynamicId, List<String> empIds) {
		if(StringUtils.isBlank(dynamicId))
			throw new RuntimeException("系统错误,请联系管理员.");

		FlightDynamic dynamic = dynamicService.get(dynamicId);
		if(dynamic == null)
			throw new RuntimeException("航班动态不存在.");

		//保存新设置的值机人员前删除老的值机数据
		FlightDynamicCheckin dc = new FlightDynamicCheckin();
		dc.setFlightDynamic(dynamic);
		List<FlightDynamicCheckin> list =  dynamicCheckinDao.findList(dc);
		if(!list.isEmpty()){
			list.forEach(dcTemp->{
				dynamicCheckinDao.delete(dcTemp);
			});
		}

		if(!Collections3.isEmpty(empIds)) {
			for (String empid : empIds) {
				RmsEmp emp = empService.get(empid);
				if (emp == null)
					throw new RuntimeException("工作人员不存在.");

				FlightDynamicCheckin dynamicCheckin = new FlightDynamicCheckin();
				dynamicCheckin.setFlightDynamic(dynamic);
				dynamicCheckin.setRmsEmp(emp);
				dynamicCheckin.setEmpName(emp.getEmpName());
				dynamicCheckinService.save(dynamicCheckin);
			}
		}
	}

	public void updateScheduling(FlightDynamicCheckin dynamicCheckin){
		dynamicCheckinDao.updateByDynamicIdAndEmpId(dynamicCheckin);
	}
}