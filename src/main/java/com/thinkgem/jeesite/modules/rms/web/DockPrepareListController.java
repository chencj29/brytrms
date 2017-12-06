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
import com.thinkgem.jeesite.modules.rms.entity.DockPrepareList;
import com.thinkgem.jeesite.modules.rms.service.DockPrepareListService;

/**
 * 航班预配单信息Controller
 * @author wjp
 * @version 2016-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/dockPrepareList")
public class DockPrepareListController extends BaseController {

	@Autowired
	private DockPrepareListService dockPrepareListService;
	
	@RequiresPermissions("rms:dockPrepareList:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/dockPrepareList/dockPrepareList";
	}

	@RequiresPermissions("rms:dockPrepareList:view")
    @RequestMapping("get")
    @ResponseBody
    public DockPrepareList get(@RequestParam("id") String id) {
        return dockPrepareListService.get(id);
    }

	/*@RequiresPermissions("rms:dockPrepareList:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<DockPrepareList> page, DataTablesPage dataTablesPage, DockPrepareList dockPrepareList, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, dockPrepareList);
		return dockPrepareListService.findDataTablesPage(page, dataTablesPage, dockPrepareList);
	}*/

	@RequiresPermissions("rms:dockPrepareList:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<DockPrepareList> list(DockPrepareList dockPrepareList) {
        return new DataGrid<>(dockPrepareListService.findList(dockPrepareList));
    }

	@RequiresPermissions("rms:dockPrepareList:view")
	@RequestMapping(value = "form")
	public String form(DockPrepareList dockPrepareList, Model model) {
		model.addAttribute("dockPrepareList", dockPrepareList);
		return "modules/rms/dockPrepareListForm";
	}

	@RequiresPermissions("rms:dockPrepareList:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(DockPrepareList dockPrepareList, Model model, Message message) {
		if (!beanValidator(model, dockPrepareList)){
			message.setMessage("数据校验错误!");
		}
		try {
			dockPrepareListService.save(dockPrepareList);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:dockPrepareList:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(DockPrepareList dockPrepareList, Message message) {
		try {
			dockPrepareListService.delete(dockPrepareList);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}