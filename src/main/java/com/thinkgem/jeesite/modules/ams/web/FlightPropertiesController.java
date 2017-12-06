/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.FlightProperties;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.FlightPropertiesService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 航班属性管理Controller
 * @author xiaopo
 * @version 2015-12-14
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/flightProperties")
public class FlightPropertiesController extends BaseController {

	@Autowired
	private FlightPropertiesService flightPropertiesService;
	
	/*@ModelAttribute
	public FlightProperties get(@RequestParam(required=false) String id) {
		FlightProperties entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = flightPropertiesService.get(id);
		}
		if (entity == null){
			entity = new FlightProperties();
		}
		return entity;
	}*/

	@RequiresPermissions("ams:flightProperties:view")
	@RequestMapping("get")
	@ResponseBody
	public FlightProperties get(@RequestParam("id") String id) {
		return flightPropertiesService.get(id);
	}

	@RequiresPermissions("ams:flightProperties:view")
	@RequestMapping(value = {"view", ""})
	public String view() {
		return "ams/flightParameters/flightProperties";
	}
	
	/*@RequiresPermissions("ams:flightProperties:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage<FlightProperties> list(Page<FlightProperties> page, FlightProperties flightProperties, DataTablesPage<FlightProperties> dataTablesPage, HttpServletRequest request, HttpServletResponse response, Model model) {
		dataTablesPage.setColumns(request, page, flightProperties);
		return  flightPropertiesService.findDataTablesPage(page,dataTablesPage,flightProperties);
	}*/

	@RequiresPermissions("ams:flightProperties:view")
	@RequestMapping("list")
	@ResponseBody
	public DataGrid<FlightProperties> list(FlightProperties flightProperties) {
		return new DataGrid<>(flightPropertiesService.findList(flightProperties));
	}

	@RequiresPermissions("ams:flightProperties:view")
	@RequestMapping(value = "form")
	public String form(FlightProperties flightProperties, Model model) {
		model.addAttribute("flightProperties", flightProperties);
		return "modules/ams/flightPropertiesForm";
	}

	@RequiresPermissions("ams:flightProperties:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(FlightProperties flightProperties, Model model, Message message) {
		if (!beanValidator(model, flightProperties)){
			message.setMessage("数据校验失败!");
		}

		flightPropertiesService.checkRedo(flightProperties,new String[]{"propertiesNo"},message);
		if(message.isSuccess()) return message;

		try {
			flightPropertiesService.save(flightProperties);
			message.setCode(1);
		}catch(Exception e){
			message.setMessage(e.getMessage());
		}
		return message;
	}
	
	@RequiresPermissions("ams:flightProperties:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(FlightProperties flightProperties, Message message) {
		try {
			flightPropertiesService.delete(flightProperties);
			message.setCode(1);
		}catch (Exception e){
			message.setMessage(e.getMessage());
		}
		message.setCode(1);
		return message;
	}

	@RequestMapping(value = "jsonData")
	@ResponseBody
	public List<FlightProperties> getAll4Ajax(Message message) {
		return flightPropertiesService.findList(new FlightProperties());
	}
}