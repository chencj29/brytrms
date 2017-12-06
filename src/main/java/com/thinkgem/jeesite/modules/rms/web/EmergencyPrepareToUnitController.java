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
import com.thinkgem.jeesite.modules.rms.entity.EmergencyPrepareToUnit;
import com.thinkgem.jeesite.modules.rms.service.EmergencyPrepareToUnitService;

/**
 * 应急救援预案救援单位关联表Controller
 * @author wjp
 * @version 2016-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/emergencyPrepareToUnit")
public class EmergencyPrepareToUnitController extends BaseController {

	@Autowired
	private EmergencyPrepareToUnitService emergencyPrepareToUnitService;
	
	@RequiresPermissions("rms:emergencyPrepareToUnit:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/emergencyPrepareToUnit/emergencyPrepareToUnit";
	}

	@RequiresPermissions("rms:emergencyPrepareToUnit:view")
    @RequestMapping("get")
    @ResponseBody
    public EmergencyPrepareToUnit get(@RequestParam("id") String id) {
        return emergencyPrepareToUnitService.get(id);
    }

	/*@RequiresPermissions("rms:emergencyPrepareToUnit:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<EmergencyPrepareToUnit> page, DataTablesPage dataTablesPage, EmergencyPrepareToUnit emergencyPrepareToUnit, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, emergencyPrepareToUnit);
		return emergencyPrepareToUnitService.findDataTablesPage(page, dataTablesPage, emergencyPrepareToUnit);
	}*/

	@RequiresPermissions("rms:emergencyPrepareToUnit:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<EmergencyPrepareToUnit> list(EmergencyPrepareToUnit emergencyPrepareToUnit) {
        return new DataGrid<>(emergencyPrepareToUnitService.findList(emergencyPrepareToUnit));
    }

	@RequiresPermissions("rms:emergencyPrepareToUnit:view")
	@RequestMapping(value = "form")
	public String form(EmergencyPrepareToUnit emergencyPrepareToUnit, Model model) {
		model.addAttribute("emergencyPrepareToUnit", emergencyPrepareToUnit);
		return "modules/rms/emergencyPrepareToUnitForm";
	}

	@RequiresPermissions("rms:emergencyPrepareToUnit:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(EmergencyPrepareToUnit emergencyPrepareToUnit, Model model, Message message) {
		if (!beanValidator(model, emergencyPrepareToUnit)){
			message.setMessage("数据校验错误!");
		}
		try {
			emergencyPrepareToUnitService.save(emergencyPrepareToUnit);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:emergencyPrepareToUnit:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(EmergencyPrepareToUnit emergencyPrepareToUnit, Message message) {
		try {
			emergencyPrepareToUnitService.delete(emergencyPrepareToUnit);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}