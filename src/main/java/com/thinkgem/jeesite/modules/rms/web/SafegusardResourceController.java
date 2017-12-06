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
import com.thinkgem.jeesite.modules.rms.entity.SafegusardResource;
import com.thinkgem.jeesite.modules.rms.service.SafegusardResourceService;

/**
 * 保障资源表Controller
 * @author wjp
 * @version 2016-04-15
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/safegusardResource")
public class SafegusardResourceController extends BaseController {

	@Autowired
	private SafegusardResourceService safegusardResourceService;
	
	@RequiresPermissions("rms:safegusardResource:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/safegusardResource/safegusardResource";
	}

	@RequiresPermissions("rms:safegusardResource:view")
    @RequestMapping("get")
    @ResponseBody
    public SafegusardResource get(@RequestParam("id") String id) {
        return safegusardResourceService.get(id);
    }

	/*@RequiresPermissions("rms:safegusardResource:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<SafegusardResource> page, DataTablesPage dataTablesPage, SafegusardResource safegusardResource, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, safegusardResource);
		return safegusardResourceService.findDataTablesPage(page, dataTablesPage, safegusardResource);
	}*/

	@RequiresPermissions("rms:safegusardResource:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<SafegusardResource> list(SafegusardResource safegusardResource) {
        return new DataGrid<>(safegusardResourceService.findList(safegusardResource));
    }

	@RequiresPermissions("rms:safegusardResource:view")
	@RequestMapping(value = "form")
	public String form(SafegusardResource safegusardResource, Model model) {
		model.addAttribute("safegusardResource", safegusardResource);
		return "modules/rms/safegusardResourceForm";
	}

	@RequiresPermissions("rms:safegusardResource:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(SafegusardResource safegusardResource, Model model, Message message) {
		if (!beanValidator(model, safegusardResource)){
			message.setMessage("数据校验错误!");
		}
		try {
			safegusardResourceService.save(safegusardResource);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:safegusardResource:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(SafegusardResource safegusardResource, Message message) {
		try {
			safegusardResourceService.delete(safegusardResource);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}