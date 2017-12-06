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
import com.thinkgem.jeesite.modules.rms.entity.CheckinCounterBasic;
import com.thinkgem.jeesite.modules.rms.service.CheckinCounterBasicService;

/**
 * 值机柜台基础信息表(基础专用)Controller
 * @author wjp
 * @version 2017-09-22
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/checkinCounterBasic")
public class CheckinCounterBasicController extends BaseController {

	@Autowired
	private CheckinCounterBasicService checkinCounterBasicService;
	
	@RequiresPermissions("rms:checkinCounterBasic:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/checkinCounterBasic/checkinCounterBasic";
	}

	@RequiresPermissions("rms:checkinCounterBasic:view")
    @RequestMapping("get")
    @ResponseBody
    public CheckinCounterBasic get(@RequestParam("id") String id) {
        return checkinCounterBasicService.get(id);
    }

	/*@RequiresPermissions("rms:checkinCounterBasic:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<CheckinCounterBasic> page, DataTablesPage dataTablesPage, CheckinCounterBasic checkinCounterBasic, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, checkinCounterBasic);
		return checkinCounterBasicService.findDataTablesPage(page, dataTablesPage, checkinCounterBasic);
	}*/

	@RequiresPermissions("rms:checkinCounterBasic:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<CheckinCounterBasic> list(CheckinCounterBasic checkinCounterBasic) {
        return new DataGrid<>(checkinCounterBasicService.findList(checkinCounterBasic));
    }

	@RequiresPermissions("rms:checkinCounterBasic:view")
	@RequestMapping(value = "form")
	public String form(CheckinCounterBasic checkinCounterBasic, Model model) {
		model.addAttribute("checkinCounterBasic", checkinCounterBasic);
		return "modules/rms/checkinCounterBasicForm";
	}

	@RequiresPermissions("rms:checkinCounterBasic:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(CheckinCounterBasic checkinCounterBasic, Model model, Message message) {
		if (!beanValidator(model, checkinCounterBasic)){
			message.setMessage("数据校验错误!");
		}
		try {
			checkinCounterBasicService.save(checkinCounterBasic);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:checkinCounterBasic:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(CheckinCounterBasic checkinCounterBasic, Message message) {
		try {
			checkinCounterBasicService.delete(checkinCounterBasic);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}