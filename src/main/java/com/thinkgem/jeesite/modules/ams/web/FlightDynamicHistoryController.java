/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicHistoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 航班动态初始化Controller
 * @author xiaopo
 * @version 2016-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/flightDynamicHistory")
public class FlightDynamicHistoryController extends BaseController {

	@Autowired
	private FlightDynamicHistoryService flightDynamicHistoryService;
	
	@ModelAttribute
	public FlightDynamic get(@RequestParam(required=false) String id) {
		FlightDynamic entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = flightDynamicHistoryService.get(id);
		}
		if (entity == null){
			entity = new FlightDynamic();
		}
		return entity;
	}

	@RequiresPermissions("ams:flightDynamic:view")
	@RequestMapping(value = "get")
	@ResponseBody
	public FlightDynamic getEntity(FlightDynamic entity) {
		return entity;
	}

	@RequiresPermissions("ams:flightDynamic:view")
	@RequestMapping(value = "view")
	public String view(){
		return "ams/flightplan/flightDynamic";
	}
	
	@RequiresPermissions("ams:flightDynamic:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage<FlightDynamic> list(FlightDynamic flightDynamic,Page<FlightDynamic> page,DataTablesPage<FlightDynamic> dataTablesPage, HttpServletRequest request, HttpServletResponse response, Model model) {
		dataTablesPage = flightDynamicHistoryService.findDataTablesPage(page,dataTablesPage,flightDynamic);
		return dataTablesPage;
	}

	@RequiresPermissions("ams:flightDynamic:view")
	@RequestMapping(value = "form")
	public String form(FlightDynamic flightDynamic, Model model) {
		model.addAttribute("flightDynamic", flightDynamic);
		return "modules/ams/flightDynamicForm";
	}

	@RequiresPermissions("ams:flightDynamic:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(FlightDynamic flightDynamic, Model model, Message message) {
		if (!beanValidator(model, flightDynamic)){
			message.setMessage("数据校验失败.");
		}
		try {
			flightDynamicHistoryService.save(flightDynamic);
			message.setCode(1);
		}catch (Exception e){
			message.setMessage(e.getMessage());
		}
		return message;
	}
	
	/*@RequiresPermissions("ams:flightDynamic:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(FlightDynamic flightDynamic, Message message) {
		try {
			flightDynamicHistoryService.delete(flightDynamic);
			message.setCode(1);
		}catch (Exception e){
			message.setMessage(e.getMessage());
		}
		return message;
	}*/


	@RequiresPermissions("ams:flightPlan:edit")
	@RequestMapping(value = "initFlightPlanByDynamic")
	@ResponseBody
	public Message initFlightPlanByDynamic(Date planDate, Date dynaDate, Message message) {
		try {
			flightDynamicHistoryService.initFlightPlanByDynamic(planDate,dynaDate);
			message.setCode(1);
		} catch (Exception e) {
			e.printStackTrace();
			message.setMessage(e.getMessage());
		}
		return message;
	}
}