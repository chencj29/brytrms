/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.CompanyAircraftNum;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.CompanyAircraftNumService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 公司机号管理Controller
 * @author xiaopo
 * @version 2015-12-15
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/companyAircraftNum")
public class CompanyAircraftNumController extends BaseController {

	@Autowired
	private CompanyAircraftNumService companyAircraftNumService;
	
	/*@ModelAttribute
	public CompanyAircraftNum get(@RequestParam(required=false) String id) {
		CompanyAircraftNum entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = companyAircraftNumService.get(id);
		}
		if (entity == null){
			entity = new CompanyAircraftNum();
		}
		return entity;
	}*/
	@RequiresPermissions("ams:companyAircraftNum:view")
	@RequestMapping("get")
	@ResponseBody
	public CompanyAircraftNum get(@RequestParam("id") String id) {
		return companyAircraftNumService.get(id);
	}

	@RequiresPermissions("ams:companyAircraftNum:view")
	@RequestMapping(value = {"view", ""})
	public String view() {
		return "ams/aircraft/companyAircraftNum";
	}

	
	/*@RequiresPermissions("ams:companyAircraftNum:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage<CompanyAircraftNum> list(DataTablesPage<CompanyAircraftNum> dataTablesPage,Page<CompanyAircraftNum> page,CompanyAircraftNum companyAircraftNum, HttpServletRequest request, HttpServletResponse response, Model model) {
		dataTablesPage.setColumns(request, page, companyAircraftNum);
		return companyAircraftNumService.findDataTablesPage(page,dataTablesPage,companyAircraftNum);
	}*/
	@RequiresPermissions("ams:companyAircraftNum:view")
	@RequestMapping("list")
	@ResponseBody
	public DataGrid<CompanyAircraftNum> list(CompanyAircraftNum companyAircraftNum) {
		return new DataGrid<>(companyAircraftNumService.findList(companyAircraftNum));
	}

	@RequiresPermissions("ams:companyAircraftNum:view")
	@RequestMapping(value = "form")
	public String form(CompanyAircraftNum companyAircraftNum, Model model) {
		model.addAttribute("companyAircraftNum", companyAircraftNum);
		return "modules/ams/companyAircraftNumForm";
	}

	@RequiresPermissions("ams:companyAircraftNum:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(CompanyAircraftNum companyAircraftNum, Model model, Message message) {
		if (!beanValidator(model, companyAircraftNum)){
			message.setMessage("数据校验失败!");
		}
		try {
			companyAircraftNumService.save(companyAircraftNum);
			message.setCode(1);
		}catch (Exception e){
			message.setMessage(e.getMessage());
		}
		return message;
	}
	
	@RequiresPermissions("ams:companyAircraftNum:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(CompanyAircraftNum companyAircraftNum, Message message) {
		try {
			companyAircraftNumService.delete(companyAircraftNum);
			message.setCode(1);
		}catch (Exception e){
			message.setMessage(e.getMessage());
		}
		return message;
	}

}