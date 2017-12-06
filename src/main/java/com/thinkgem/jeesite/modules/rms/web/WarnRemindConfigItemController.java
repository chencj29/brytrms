/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.rms.entity.WarnRemindConfigItem;
import com.thinkgem.jeesite.modules.rms.service.WarnRemindConfigItemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 预警提醒规则Controller
 * @author xiaowu
 * @version 2016-01-19
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/warnRemindConfigItem")
public class WarnRemindConfigItemController extends BaseController {

	@Autowired
	private WarnRemindConfigItemService warnRemindConfigItemService;
	
	@ModelAttribute
	public WarnRemindConfigItem get(@RequestParam(required=false) String id) {
		WarnRemindConfigItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = warnRemindConfigItemService.get(id);
		}
		if (entity == null){
			entity = new WarnRemindConfigItem();
		}
		return entity;
	}
	
	@RequiresPermissions("rms:warnRemindConfigItem:view")
	@RequestMapping(value = {"list", ""})
	public String list(WarnRemindConfigItem warnRemindConfigItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WarnRemindConfigItem> page = warnRemindConfigItemService.findPage(new Page<WarnRemindConfigItem>(request, response), warnRemindConfigItem); 
		model.addAttribute("page", page);
		return "modules/rms/warnRemindConfigItemList";
	}

	@RequiresPermissions("rms:warnRemindConfigItem:view")
	@RequestMapping(value = "form")
	public String form(WarnRemindConfigItem warnRemindConfigItem, Model model) {
		model.addAttribute("warnRemindConfigItem", warnRemindConfigItem);
		return "modules/rms/warnRemindConfigItemForm";
	}

	@RequiresPermissions("rms:warnRemindConfigItem:edit")
	@RequestMapping(value = "save")
	public String save(WarnRemindConfigItem warnRemindConfigItem, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, warnRemindConfigItem)){
			return form(warnRemindConfigItem, model);
		}
		warnRemindConfigItemService.save(warnRemindConfigItem);
		addMessage(redirectAttributes, "保存预警提醒规则成功");
		return "redirect:"+Global.getAdminPath()+"/rms/warnRemindConfigItem/?repage";
	}
	
	@RequiresPermissions("rms:warnRemindConfigItem:edit")
	@RequestMapping(value = "delete")
	public String delete(WarnRemindConfigItem warnRemindConfigItem, RedirectAttributes redirectAttributes) {
		warnRemindConfigItemService.delete(warnRemindConfigItem);
		addMessage(redirectAttributes, "删除预警提醒规则成功");
		return "redirect:"+Global.getAdminPath()+"/rms/warnRemindConfigItem/?repage";
	}

}