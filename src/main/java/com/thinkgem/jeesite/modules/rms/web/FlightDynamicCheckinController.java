/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.FlightDynamicCheckin;
import com.thinkgem.jeesite.modules.rms.service.FlightDynamicCheckinService;
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
 * 值机柜台员工分配Controller
 *
 * @author xiaopo
 * @version 2016-05-24
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/flightDynamicCheckin")
public class FlightDynamicCheckinController extends BaseController {

	@Autowired
	private FlightDynamicCheckinService flightDynamicCheckinService;

	@RequiresPermissions("rms:flightDynamicCheckin:view")
	@RequestMapping("view")
	public String view() {
		return "rms/flightDynamicCheckin/flightDynamicCheckin";
	}

	@RequiresPermissions("rms:flightDynamicCheckin:view")
	@RequestMapping("get")
	@ResponseBody
	public FlightDynamicCheckin get(@RequestParam("id") String id) {
		return flightDynamicCheckinService.get(id);
	}

	/*@RequiresPermissions("rms:flightDynamicCheckin:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<FlightDynamicCheckin> page, DataTablesPage dataTablesPage, FlightDynamicCheckin flightDynamicCheckin, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, flightDynamicCheckin);
		return flightDynamicCheckinService.findDataTablesPage(page, dataTablesPage, flightDynamicCheckin);
	}*/

	@RequiresPermissions("rms:flightDynamicCheckin:view")
	@RequestMapping("list")
	@ResponseBody
	public DataGrid<FlightDynamicCheckin> list(FlightDynamicCheckin flightDynamicCheckin) {
		return new DataGrid<>(flightDynamicCheckinService.findList(flightDynamicCheckin));
	}

	@RequiresPermissions("rms:flightDynamicCheckin:view")
	@RequestMapping(value = "form")
	public String form(FlightDynamicCheckin flightDynamicCheckin, Model model) {
		model.addAttribute("flightDynamicCheckin", flightDynamicCheckin);
		return "modules/rms/flightDynamicCheckinForm";
	}

	@RequiresPermissions("rms:flightDynamicCheckin:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(FlightDynamicCheckin flightDynamicCheckin, Model model, Message message) {
		if(!beanValidator(model, flightDynamicCheckin)) {
			message.setMessage("数据校验错误!");
		}
		try {
			flightDynamicCheckinService.save(flightDynamicCheckin);
			message.setCode(1);
		} catch(Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:flightDynamicCheckin:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(FlightDynamicCheckin flightDynamicCheckin, Message message) {
		try {
			flightDynamicCheckinService.delete(flightDynamicCheckin);
			message.setCode(1);
		} catch(Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:flightDynamicCheckin:view")
	@RequestMapping("listData")
	@ResponseBody
	public List<FlightDynamicCheckin> listData(FlightDynamicCheckin flightDynamicCheckin) {
		return flightDynamicCheckinService.findList(flightDynamicCheckin);
	}



	@RequiresPermissions("rms:flightDynamicCheckin:view")
	@RequestMapping("getByDynamicIdAndEmpId")
	@ResponseBody
	public FlightDynamicCheckin getByDynamicIdAndEmpId(FlightDynamicCheckin dynamicCheckin){
		dynamicCheckin = flightDynamicCheckinService.get(dynamicCheckin);
		return dynamicCheckin;
	}
}