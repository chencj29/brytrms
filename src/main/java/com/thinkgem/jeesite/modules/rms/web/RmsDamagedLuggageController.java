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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.RmsDamagedLuggage;
import com.thinkgem.jeesite.modules.rms.service.RmsDamagedLuggageService;

/**
 * 损坏行李表Controller
 * @author wjp
 * @version 2016-02-03
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/rmsDamagedLuggage")
public class RmsDamagedLuggageController extends BaseController {

	@Autowired
	private RmsDamagedLuggageService rmsDamagedLuggageService;
	
	@ModelAttribute
	public RmsDamagedLuggage get(@RequestParam(required=false) String id) {
		RmsDamagedLuggage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = rmsDamagedLuggageService.get(id);
		}
		if (entity == null){
			entity = new RmsDamagedLuggage();
		}
		return entity;
	}
	
	@RequiresPermissions("rms:rmsDamagedLuggage:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/rmsDamagedLuggage/rmsDamagedLuggage";
	}

	@RequiresPermissions("rms:rmsDamagedLuggage:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<RmsDamagedLuggage> page, DataTablesPage dataTablesPage, RmsDamagedLuggage rmsDamagedLuggage, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request, page, rmsDamagedLuggage);
		return rmsDamagedLuggageService.findDataTablesPage(page, dataTablesPage, rmsDamagedLuggage);
	}
	
	@RequiresPermissions("rms:rmsDamagedLuggage:view")
	@RequestMapping(value = "form")
	public String form(RmsDamagedLuggage rmsDamagedLuggage, Model model) {
		model.addAttribute("rmsDamagedLuggage", rmsDamagedLuggage);
		return "modules/rms/rmsDamagedLuggageForm";
	}

	@RequiresPermissions("rms:rmsDamagedLuggage:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(RmsDamagedLuggage rmsDamagedLuggage, Model model, Message message) {
		if (!beanValidator(model, rmsDamagedLuggage)){
			message.setMessage("数据校验错误!");
		}
		try {
			rmsDamagedLuggageService.save(rmsDamagedLuggage);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:rmsDamagedLuggage:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(RmsDamagedLuggage rmsDamagedLuggage, Message message) {
		try {
			rmsDamagedLuggageService.delete(rmsDamagedLuggage);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}