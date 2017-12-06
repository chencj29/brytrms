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
import com.thinkgem.jeesite.modules.rms.entity.IrregularLuggage;
import com.thinkgem.jeesite.modules.rms.service.IrregularLuggageService;

/**
 * 不正常行李表Controller
 * @author wjp
 * @version 2016-04-11
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/irregularLuggage")
public class IrregularLuggageController extends BaseController {

	@Autowired
	private IrregularLuggageService irregularLuggageService;
	
	@RequiresPermissions("rms:irregularLuggage:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/irregularLuggage/irregularLuggage";
	}

	@RequiresPermissions("rms:irregularLuggage:view")
    @RequestMapping("get")
    @ResponseBody
    public IrregularLuggage get(@RequestParam("id") String id) {
        return irregularLuggageService.get(id);
    }

	/*@RequiresPermissions("rms:irregularLuggage:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<IrregularLuggage> page, DataTablesPage dataTablesPage, IrregularLuggage irregularLuggage, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, irregularLuggage);
		return irregularLuggageService.findDataTablesPage(page, dataTablesPage, irregularLuggage);
	}*/

	@RequiresPermissions("rms:irregularLuggage:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<IrregularLuggage> list(IrregularLuggage irregularLuggage) {
        return new DataGrid<>(irregularLuggageService.findList(irregularLuggage));
    }

	@RequiresPermissions("rms:irregularLuggage:view")
	@RequestMapping(value = "form")
	public String form(IrregularLuggage irregularLuggage, Model model) {
		model.addAttribute("irregularLuggage", irregularLuggage);
		return "modules/rms/irregularLuggageForm";
	}

	@RequiresPermissions("rms:irregularLuggage:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(IrregularLuggage irregularLuggage, Model model, Message message) {
		if (!beanValidator(model, irregularLuggage)){
			message.setMessage("数据校验错误!");
		}
		try {
			irregularLuggageService.save(irregularLuggage);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:irregularLuggage:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(IrregularLuggage irregularLuggage, Message message) {
		try {
			irregularLuggageService.delete(irregularLuggage);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}