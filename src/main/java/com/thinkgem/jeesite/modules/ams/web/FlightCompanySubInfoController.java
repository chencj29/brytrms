/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.ams.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.FlightCompanySubInfo;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.FlightCompanySubInfoService;
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
 * 航空公司子公司管理Controller
 * @author xiaopo
 * @version 2016-08-24
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/flightCompanySubInfo")
public class FlightCompanySubInfoController extends BaseController {

	@Autowired
	private FlightCompanySubInfoService flightCompanySubInfoService;
	
	@RequiresPermissions("ams:flightCompanySubInfo:view")
	@RequestMapping("view")
	public String view(){
		return "ams/flightCompanySubInfo/flightCompanySubInfo";
	}

	@RequiresPermissions("ams:flightCompanySubInfo:view")
    @RequestMapping("get")
    @ResponseBody
    public FlightCompanySubInfo get(@RequestParam("id") String id) {
        return flightCompanySubInfoService.get(id);
    }

	/*@RequiresPermissions("ams:flightCompanySubInfo:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<FlightCompanySubInfo> page, DataTablesPage dataTablesPage, FlightCompanySubInfo flightCompanySubInfo, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, flightCompanySubInfo);
		return flightCompanySubInfoService.findDataTablesPage(page, dataTablesPage, flightCompanySubInfo);
	}*/

	/*@RequiresPermissions("ams:flightCompanySubInfo:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<FlightCompanySubInfo> list(FlightCompanySubInfo flightCompanySubInfo) {
        return new DataGrid<>(flightCompanySubInfoService.findList(flightCompanySubInfo));
    }*/

	@RequiresPermissions("ams:flightCompanySubInfo:view")
	@RequestMapping("list")
	@ResponseBody
	public DataGrid<FlightCompanySubInfo> list(FlightCompanySubInfo flightCompanySubInfo) {
		return new DataGrid<>(flightCompanySubInfoService.findList_EXT(flightCompanySubInfo));
	}

	@RequiresPermissions("ams:flightCompanySubInfo:view")
	@RequestMapping(value = "form")
	public String form(FlightCompanySubInfo flightCompanySubInfo, Model model) {
		model.addAttribute("flightCompanySubInfo", flightCompanySubInfo);
		return "modules/ams/flightCompanySubInfoForm";
	}

	@RequiresPermissions("ams:flightCompanySubInfo:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(FlightCompanySubInfo flightCompanySubInfo, Model model, Message message) {
		if (!beanValidator(model, flightCompanySubInfo)){
			message.setMessage("数据校验错误!");
		}

		flightCompanySubInfoService.checkRedo(flightCompanySubInfo,new String[]{"flightCompanyInfoId","threeCode"},message);
		if(message.isSuccess()) return message;

		try {
			flightCompanySubInfoService.save(flightCompanySubInfo);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("ams:flightCompanySubInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(FlightCompanySubInfo flightCompanySubInfo, Message message) {
		try {
			flightCompanySubInfoService.delete(flightCompanySubInfo);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	//@RequiresPermissions("ams:flightCompanyInfo:view")
	@RequestMapping(value = "jsonData")
	@ResponseBody
	public List<FlightCompanySubInfo> getAll4Ajax(Message message,FlightCompanySubInfo flightCompanySubInfo) {
		return flightCompanySubInfoService.findList(flightCompanySubInfo);
	}
}