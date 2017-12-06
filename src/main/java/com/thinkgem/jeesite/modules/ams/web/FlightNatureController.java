/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.FlightNature;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.FlightNatureService;
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
 * 航班性质管理Controller
 * @author xiaopo
 * @version 2015-12-14
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/flightNature")
public class FlightNatureController extends BaseController {

	@Autowired
	private FlightNatureService flightNatureService;
	
	/*@ModelAttribute
	public FlightNature get(@RequestParam(required=false) String id) {
		FlightNature entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = flightNatureService.get(id);
		}
		if (entity == null){
			entity = new FlightNature();
		}
		return entity;
	}*/

	@RequiresPermissions("ams:flightNature:view")
	@RequestMapping("get")
	@ResponseBody
	public FlightNature get(@RequestParam("id") String id) {
		return flightNatureService.get(id);
	}

	@RequiresPermissions("ams:flightNature:view")
	@RequestMapping(value = {"view", ""})
	public String view(){
		return "ams/flightParameters/flightNature";
	}
	
	/*@RequiresPermissions("ams:flightNature:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public String list(FlightNature flightNature, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FlightNature> page = flightNatureService.findPage(new Page<FlightNature>(request, response), flightNature);
		model.addAttribute("page", page);
		return "modules/ams/flightNatureList";
	}*/

	/*@RequiresPermissions("ams:flightNature:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<FlightNature> flightCompanyInfoPage, DataTablesPage dataTablesPage, FlightNature flightNature, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request, flightCompanyInfoPage, flightNature);
		return flightNatureService.findDataTablesPage(flightCompanyInfoPage, dataTablesPage, flightNature);
	}*/

	@RequiresPermissions("ams:flightNature:view")
	@RequestMapping("list")
	@ResponseBody
	public DataGrid<FlightNature> list(FlightNature flightNature) {
		return new DataGrid<>(flightNatureService.findList(flightNature));
	}

	@RequiresPermissions("ams:flightNature:view")
	@RequestMapping(value = "form")
	public String form(FlightNature flightNature, Model model) {
		model.addAttribute("flightNature", flightNature);
		return "modules/ams/flightNatureForm";
	}

	@RequiresPermissions("ams:flightNature:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(FlightNature flightNature, Model model, Message message) {
		if (!beanValidator(model, flightNature)){
			message.setMessage("数据校验失败!");
		}

		flightNatureService.checkRedo(flightNature,new String[]{"natureNum"},message);
		if(message.isSuccess()) return message;

		try {
			flightNatureService.save(flightNature);
			message.setCode(1);
		}catch(Exception e){
			message.setMessage(e.getMessage());
		}
		return message;
	}
	
	@RequiresPermissions("ams:flightNature:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(FlightNature flightNature, Message message) {
		try {
			flightNatureService.delete(flightNature);
			message.setCode(1);
		}catch (Exception e){
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequestMapping(value = "jsonData")
	@ResponseBody
	public List<FlightNature> getAll4Ajax(Message message) {
		return flightNatureService.findList(new FlightNature());
	}
}