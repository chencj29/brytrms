/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.ShareFlightDynamic;
import com.thinkgem.jeesite.modules.ams.service.ShareFlightDynamicService;

/**
 * 共享航班与航班动态关联Controller
 * @author chrischen
 * @version 2016-02-03
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/shareFlightDynamic")
public class ShareFlightDynamicController extends BaseController {

	@Autowired
	private ShareFlightDynamicService shareFlightDynamicService;
	
	@ModelAttribute
	public ShareFlightDynamic get(@RequestParam(required=false) String id) {
		ShareFlightDynamic entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shareFlightDynamicService.get(id);
		}
		if (entity == null){
			entity = new ShareFlightDynamic();
		}
		return entity;
	}
	
	@RequiresPermissions("ams:shareFlightDynamic:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShareFlightDynamic shareFlightDynamic, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShareFlightDynamic> page = shareFlightDynamicService.findPage(new Page<ShareFlightDynamic>(request, response), shareFlightDynamic); 
		model.addAttribute("page", page);
		return "modules/ams/shareFlightDynamicList";
	}

	@RequiresPermissions("ams:shareFlightDynamic:view")
	@RequestMapping(value = "form")
	public String form(ShareFlightDynamic shareFlightDynamic, Model model) {
		model.addAttribute("shareFlightDynamic", shareFlightDynamic);
		return "modules/ams/shareFlightDynamicForm";
	}

	@RequiresPermissions("ams:shareFlightDynamic:edit")
	@RequestMapping(value = "save")
	public String save(ShareFlightDynamic shareFlightDynamic, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shareFlightDynamic)){
			return form(shareFlightDynamic, model);
		}
		shareFlightDynamicService.save(shareFlightDynamic);
		addMessage(redirectAttributes, "保存共享航班与航班动态关联成功");
		return "redirect:"+Global.getAdminPath()+"/ams/shareFlightDynamic/?repage";
	}
	
	@RequiresPermissions("ams:shareFlightDynamic:edit")
	@RequestMapping(value = "delete")
	public String delete(ShareFlightDynamic shareFlightDynamic, RedirectAttributes redirectAttributes) {
		shareFlightDynamicService.delete(shareFlightDynamic);
		addMessage(redirectAttributes, "删除共享航班与航班动态关联成功");
		return "redirect:"+Global.getAdminPath()+"/ams/shareFlightDynamic/?repage";
	}

}