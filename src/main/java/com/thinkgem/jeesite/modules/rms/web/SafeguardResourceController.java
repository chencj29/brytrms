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
import com.thinkgem.jeesite.modules.rms.entity.SafeguardResource;
import com.thinkgem.jeesite.modules.rms.service.SafeguardResourceService;

/**
 * 保障资源表Controller
 * @author wjp
 * @version 2016-04-15
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/safeguardResource")
public class SafeguardResourceController extends BaseController {

	@Autowired
	private SafeguardResourceService safeguardResourceService;
	
	@RequiresPermissions("rms:safeguardResource:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/safeguardResource/safeguardResource";
	}

	@RequiresPermissions("rms:safeguardResource:view")
    @RequestMapping("get")
    @ResponseBody
    public SafeguardResource get(@RequestParam("id") String id) {
        return safeguardResourceService.get(id);
    }

	/*@RequiresPermissions("rms:safeguardResource:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<SafeguardResource> page, DataTablesPage dataTablesPage, SafeguardResource safeguardResource, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, safeguardResource);
		return safeguardResourceService.findDataTablesPage(page, dataTablesPage, safeguardResource);
	}*/

	@RequiresPermissions("rms:safeguardResource:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<SafeguardResource> list(SafeguardResource safeguardResource) {
        return new DataGrid<>(safeguardResourceService.findList(safeguardResource));
    }

	@RequiresPermissions("rms:safeguardResource:view")
	@RequestMapping(value = "form")
	public String form(SafeguardResource safeguardResource, Model model) {
		model.addAttribute("safeguardResource", safeguardResource);
		return "modules/rms/safeguardResourceForm";
	}

	@RequiresPermissions("rms:safeguardResource:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(SafeguardResource safeguardResource, Model model, Message message) {
		if (!beanValidator(model, safeguardResource)){
			message.setMessage("数据校验错误!");
		}
		try {
			safeguardResourceService.save(safeguardResource);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:safeguardResource:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(SafeguardResource safeguardResource, Message message) {
		try {
			safeguardResourceService.delete(safeguardResource);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}