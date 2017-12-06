/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.FlightDynamicCheckin;
import com.thinkgem.jeesite.modules.rms.service.CheckCounterSchedulingService;
import com.thinkgem.jeesite.modules.rms.service.FlightDynamicCheckinService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 值机管理Controller
 *
 * @author xiaopo
 * @version 2016-05-24
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/checkCounterScheduling")
public class CheckCounterSchedulingController extends BaseController {

	@Autowired
	private CheckCounterSchedulingService checkCounterSchedulingService;

	@Autowired
	private FlightDynamicCheckinService dynamicCheckinService;


	@RequiresPermissions("rms:checkCounterScheduling:view")
	@RequestMapping("view")
	public String view() {
		return "rms/empScheduling/checkCounterScheduling";
	}


	@RequiresPermissions("rms:checkCounterScheduling:scleduling")
	@RequestMapping("scheduling")
	@ResponseBody
	public Message scheduling(String dynamicId, @RequestParam(value = "empId[]",required = false) List<String> empId, Message message) {
		try {
			checkCounterSchedulingService.scheduling(dynamicId, empId);
			message.setCode(1);
		} catch(Exception e) {
			message.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return message;
	}


	@RequiresPermissions("rms:checkCounterScheduling:edit")
	@RequestMapping("updateScheduling")
	@ResponseBody
	public Message updateScheduling(FlightDynamicCheckin flightDynamicCheckin, Message message) {
		try {
			checkCounterSchedulingService.updateScheduling(flightDynamicCheckin);
			message.setCode(1);
		} catch(Exception e) {
			message.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}
}