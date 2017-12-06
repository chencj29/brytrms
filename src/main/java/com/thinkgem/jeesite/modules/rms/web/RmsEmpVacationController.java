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
import com.thinkgem.jeesite.modules.rms.entity.RmsEmpVacation;
import com.thinkgem.jeesite.modules.rms.service.RmsEmpVacationService;

/**
 * 工作人员请假管理Controller
 * @author xiaopo
 * @version 2016-05-18
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/rmsEmpVacation")
public class RmsEmpVacationController extends BaseController {

	@Autowired
	private RmsEmpVacationService rmsEmpVacationService;
	
	@RequiresPermissions("rms:rmsEmpVacation:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/rmsEmpVacation/rmsEmpVacation";
	}

	@RequiresPermissions("rms:rmsEmpVacation:view")
    @RequestMapping("get")
    @ResponseBody
    public RmsEmpVacation get(@RequestParam("id") String id) {
        return rmsEmpVacationService.get(id);
    }

	/*@RequiresPermissions("rms:rmsEmpVacation:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<RmsEmpVacation> page, DataTablesPage dataTablesPage, RmsEmpVacation rmsEmpVacation, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, rmsEmpVacation);
		return rmsEmpVacationService.findDataTablesPage(page, dataTablesPage, rmsEmpVacation);
	}*/

	@RequiresPermissions("rms:rmsEmpVacation:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<RmsEmpVacation> list(RmsEmpVacation rmsEmpVacation) {
        return new DataGrid<>(rmsEmpVacationService.findList(rmsEmpVacation));
    }

	@RequiresPermissions("rms:rmsEmpVacation:view")
	@RequestMapping(value = "form")
	public String form(RmsEmpVacation rmsEmpVacation, Model model) {
		model.addAttribute("rmsEmpVacation", rmsEmpVacation);
		return "modules/rms/rmsEmpVacationForm";
	}

	@RequiresPermissions("rms:rmsEmpVacation:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(RmsEmpVacation rmsEmpVacation, Model model, Message message) {
		if (!beanValidator(model, rmsEmpVacation)){
			message.setMessage("数据校验错误!");
		}
		try {
			rmsEmpVacationService.save(rmsEmpVacation);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:rmsEmpVacation:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(RmsEmpVacation rmsEmpVacation, Message message) {
		try {
			rmsEmpVacationService.delete(rmsEmpVacation);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}