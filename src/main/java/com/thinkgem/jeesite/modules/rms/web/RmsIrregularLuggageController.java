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
import com.thinkgem.jeesite.modules.rms.entity.RmsIrregularLuggage;
import com.thinkgem.jeesite.modules.rms.service.RmsIrregularLuggageService;

/**
 * 不正常行李表Controller
 * @author wjp
 * @version 2016-02-03
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/rmsIrregularLuggage")
public class RmsIrregularLuggageController extends BaseController {

	@Autowired
	private RmsIrregularLuggageService rmsIrregularLuggageService;
	
	@ModelAttribute
	public RmsIrregularLuggage get(@RequestParam(required=false) String id) {
		RmsIrregularLuggage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = rmsIrregularLuggageService.get(id);
		}
		if (entity == null){
			entity = new RmsIrregularLuggage();
		}
		return entity;
	}
	
	@RequiresPermissions("rms:rmsIrregularLuggage:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/rmsIrregularLuggage/rmsIrregularLuggage";
	}

	@RequiresPermissions("rms:rmsIrregularLuggage:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<RmsIrregularLuggage> page, DataTablesPage dataTablesPage, RmsIrregularLuggage rmsIrregularLuggage, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request, page, rmsIrregularLuggage);
		return rmsIrregularLuggageService.findDataTablesPage(page, dataTablesPage, rmsIrregularLuggage);
	}
	
	@RequiresPermissions("rms:rmsIrregularLuggage:view")
	@RequestMapping(value = "form")
	public String form(RmsIrregularLuggage rmsIrregularLuggage, Model model) {
		model.addAttribute("rmsIrregularLuggage", rmsIrregularLuggage);
		return "modules/rms/rmsIrregularLuggageForm";
	}

	@RequiresPermissions("rms:rmsIrregularLuggage:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(RmsIrregularLuggage rmsIrregularLuggage, Model model, Message message) {
		if (!beanValidator(model, rmsIrregularLuggage)){
			message.setMessage("数据校验错误!");
		}
		try {
			rmsIrregularLuggageService.save(rmsIrregularLuggage);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:rmsIrregularLuggage:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(RmsIrregularLuggage rmsIrregularLuggage, Message message) {
		try {
			rmsIrregularLuggageService.delete(rmsIrregularLuggage);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}