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
import com.thinkgem.jeesite.modules.rms.entity.IrregularServices;
import com.thinkgem.jeesite.modules.rms.service.IrregularServicesService;

/**
 * 不正常服务记录Controller
 * @author wjp
 * @version 2016-03-19
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/irregularServices")
public class IrregularServicesController extends BaseController {

	@Autowired
	private IrregularServicesService irregularServicesService;
	
	@RequiresPermissions("rms:irregularServices:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/irregularServices/irregularServices";
	}

	@RequiresPermissions("rms:irregularServices:view")
    @RequestMapping("get")
    @ResponseBody
    public IrregularServices get(@RequestParam("id") String id) {
        return irregularServicesService.get(id);
    }

	/*@RequiresPermissions("rms:irregularServices:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<IrregularServices> page, DataTablesPage dataTablesPage, IrregularServices irregularServices, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, irregularServices);
		return irregularServicesService.findDataTablesPage(page, dataTablesPage, irregularServices);
	}*/

	@RequiresPermissions("rms:irregularServices:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<IrregularServices> list(IrregularServices irregularServices) {
        return new DataGrid<>(irregularServicesService.findList(irregularServices));
    }

	@RequiresPermissions("rms:irregularServices:view")
	@RequestMapping(value = "form")
	public String form(IrregularServices irregularServices, Model model) {
		model.addAttribute("irregularServices", irregularServices);
		return "modules/rms/irregularServicesForm";
	}

	@RequiresPermissions("rms:irregularServices:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(IrregularServices irregularServices, Model model, Message message) {
		if (!beanValidator(model, irregularServices)){
			message.setMessage("数据校验错误!");
		}
		try {
			irregularServicesService.save(irregularServices);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:irregularServices:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(IrregularServices irregularServices, Message message) {
		try {
			irregularServicesService.delete(irregularServices);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}