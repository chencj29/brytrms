/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.FlightCompany;
import com.thinkgem.jeesite.modules.rms.service.FlightCompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 航空公司联系信息Controller
 * @author wjp
 * @version 2016-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/flightCompany")
public class FlightCompanyController extends BaseController {

	@Autowired
	private FlightCompanyService flightCompanyService;
	
	@RequiresPermissions("rms:flightCompany:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/flightCompany/flightCompany";
	}

	@RequiresPermissions("rms:flightCompany:view")
    @RequestMapping("get")
    @ResponseBody
    public FlightCompany get(@RequestParam("id") String id) {
        return flightCompanyService.get(id);
    }

	/*@RequiresPermissions("rms:flightCompany:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<FlightCompany> page, DataTablesPage dataTablesPage, FlightCompany flightCompany, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, flightCompany);
		return flightCompanyService.findDataTablesPage(page, dataTablesPage, flightCompany);
	}*/

	@RequiresPermissions("rms:flightCompany:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<FlightCompany> list(FlightCompany flightCompany) {
        return new DataGrid<>(flightCompanyService.findList(flightCompany));
    }

	@RequiresPermissions("rms:flightCompany:view")
	@RequestMapping(value = "form")
	public String form(FlightCompany flightCompany, Model model) {
		model.addAttribute("flightCompany", flightCompany);
		return "modules/rms/flightCompanyForm";
	}

	@RequiresPermissions("rms:flightCompany:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(FlightCompany flightCompany, Model model, Message message) {
		if (!beanValidator(model, flightCompany)){
			message.setMessage("数据校验错误!");
		}
		try {
			flightCompanyService.save(flightCompany);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:flightCompany:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(FlightCompany flightCompany, Message message) {
		try {
			flightCompanyService.delete(flightCompany);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}