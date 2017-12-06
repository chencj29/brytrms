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
import com.thinkgem.jeesite.modules.rms.entity.DockOutList;
import com.thinkgem.jeesite.modules.rms.service.DockOutListService;

/**
 * 航班出仓机单信息Controller
 * @author wjp
 * @version 2016-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/dockOutList")
public class DockOutListController extends BaseController {

	@Autowired
	private DockOutListService dockOutListService;
	
	@RequiresPermissions("rms:dockOutList:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/dockOutList/dockOutList";
	}

	@RequiresPermissions("rms:dockOutList:view")
    @RequestMapping("get")
    @ResponseBody
    public DockOutList get(@RequestParam("id") String id) {
        return dockOutListService.get(id);
    }

	/*@RequiresPermissions("rms:dockOutList:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<DockOutList> page, DataTablesPage dataTablesPage, DockOutList dockOutList, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, dockOutList);
		return dockOutListService.findDataTablesPage(page, dataTablesPage, dockOutList);
	}*/

	@RequiresPermissions("rms:dockOutList:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<DockOutList> list(DockOutList dockOutList) {
        return new DataGrid<>(dockOutListService.findList(dockOutList));
    }

	@RequiresPermissions("rms:dockOutList:view")
	@RequestMapping(value = "form")
	public String form(DockOutList dockOutList, Model model) {
		model.addAttribute("dockOutList", dockOutList);
		return "modules/rms/dockOutListForm";
	}

	@RequiresPermissions("rms:dockOutList:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(DockOutList dockOutList, Model model, Message message) {
		if (!beanValidator(model, dockOutList)){
			message.setMessage("数据校验错误!");
		}
		try {
			dockOutListService.save(dockOutList);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:dockOutList:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(DockOutList dockOutList, Message message) {
		try {
			dockOutListService.delete(dockOutList);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}