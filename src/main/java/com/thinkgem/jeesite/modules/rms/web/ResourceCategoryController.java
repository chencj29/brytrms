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
import com.thinkgem.jeesite.modules.rms.entity.ResourceCategory;
import com.thinkgem.jeesite.modules.rms.service.ResourceCategoryService;

import java.util.List;

/**
 * 资源类别信息Controller
 * @author wjp
 * @version 2016-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/resourceCategory")
public class ResourceCategoryController extends BaseController {

	@Autowired
	private ResourceCategoryService resourceCategoryService;
	
	@RequiresPermissions("rms:resourceCategory:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/resourceCategory/resourceCategory";
	}

	@RequiresPermissions("rms:resourceCategory:view")
    @RequestMapping("get")
    @ResponseBody
    public ResourceCategory get(@RequestParam("id") String id) {
        return resourceCategoryService.get(id);
    }

	/*@RequiresPermissions("rms:resourceCategory:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<ResourceCategory> page, DataTablesPage dataTablesPage, ResourceCategory resourceCategory, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, resourceCategory);
		return resourceCategoryService.findDataTablesPage(page, dataTablesPage, resourceCategory);
	}*/

	@RequiresPermissions("rms:resourceCategory:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<ResourceCategory> list(ResourceCategory resourceCategory) {
        return new DataGrid<>(resourceCategoryService.findList(resourceCategory));
    }

	@RequiresPermissions("rms:resourceCategory:view")
	@RequestMapping(value = "form")
	public String form(ResourceCategory resourceCategory, Model model) {
		model.addAttribute("resourceCategory", resourceCategory);
		return "modules/rms/resourceCategoryForm";
	}

	@RequiresPermissions("rms:resourceCategory:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(ResourceCategory resourceCategory, Model model, Message message) {
		if (!beanValidator(model, resourceCategory)){
			message.setMessage("数据校验错误!");
		}

		resourceCategoryService.checkRedo(resourceCategory,new String[]{"categoryId"},message);
		if(message.isSuccess()) return message;

		try {
			resourceCategoryService.save(resourceCategory);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:resourceCategory:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(ResourceCategory resourceCategory, Message message) {
		try {
			resourceCategoryService.delete(resourceCategory);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequestMapping("/jsonData")
	@ResponseBody
	public List<ResourceCategory>getAll4Ajax(){
		return resourceCategoryService.findList(new ResourceCategory());
	}

}