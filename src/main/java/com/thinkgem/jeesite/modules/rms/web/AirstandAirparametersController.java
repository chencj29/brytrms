/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.easyui.DataGrid.DataGrid;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.AirstandAirparameters;
import com.thinkgem.jeesite.modules.rms.service.AirstandAirparametersService;

/**
 * 机位与机型对应表Controller
 * @author wjp
 * @version 2016-03-17
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/airstandAirparameters")
public class AirstandAirparametersController extends BaseController {

	@Autowired
	private AirstandAirparametersService airstandAirparametersService;
	
	@RequiresPermissions("rms:airstandAirparameters:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/airstandAirparameters/airstandAirparameters";
	}

	@RequiresPermissions("rms:airstandAirparameters:view")
    @RequestMapping("get")
    @ResponseBody
    public AirstandAirparameters get(@RequestParam("id") String id) {
        return airstandAirparametersService.get(id);
    }

	/*@RequiresPermissions("rms:airstandAirparameters:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<AirstandAirparameters> page, DataTablesPage dataTablesPage, AirstandAirparameters airstandAirparameters, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, airstandAirparameters);
		return airstandAirparametersService.findDataTablesPage(page, dataTablesPage, airstandAirparameters);
	}*/

	@RequiresPermissions("rms:airstandAirparameters:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<AirstandAirparameters> list(AirstandAirparameters airstandAirparameters) {
        return new DataGrid<>(airstandAirparametersService.findList(airstandAirparameters));
    }

	@RequiresPermissions("rms:airstandAirparameters:view")
	@RequestMapping(value = "form")
	public String form(AirstandAirparameters airstandAirparameters, Model model) {
		model.addAttribute("airstandAirparameters", airstandAirparameters);
		return "modules/rms/airstandAirparametersForm";
	}

	@RequiresPermissions("rms:airstandAirparameters:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(AirstandAirparameters airstandAirparameters, Model model, Message message) {
		if (!beanValidator(model, airstandAirparameters)){
			message.setMessage("数据校验错误!");
		}

		airstandAirparametersService.checkRedo(airstandAirparameters,new String[]{"aircraftModel"},message);
		if(message.isSuccess()) return message;

		try {
			airstandAirparametersService.save(airstandAirparameters);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:airstandAirparameters:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(AirstandAirparameters airstandAirparameters, Message message) {
		try {
			airstandAirparametersService.delete(airstandAirparameters);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}