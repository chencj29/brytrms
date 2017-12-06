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
import com.thinkgem.jeesite.modules.rms.entity.DamagedLuggage;
import com.thinkgem.jeesite.modules.rms.service.DamagedLuggageService;

/**
 * 损坏行李表Controller
 * @author wjp
 * @version 2016-03-14
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/damagedLuggage")
public class DamagedLuggageController extends BaseController {

	@Autowired
	private DamagedLuggageService damagedLuggageService;
	
	@RequiresPermissions("rms:damagedLuggage:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/damagedLuggage/damagedLuggage";
	}

	@RequiresPermissions("rms:damagedLuggage:view")
    @RequestMapping("get")
    @ResponseBody
    public DamagedLuggage get(@RequestParam("id") String id) {
        return damagedLuggageService.get(id);
    }

	/*@RequiresPermissions("rms:damagedLuggage:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<DamagedLuggage> page, DataTablesPage dataTablesPage, DamagedLuggage damagedLuggage, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, damagedLuggage);
		return damagedLuggageService.findDataTablesPage(page, dataTablesPage, damagedLuggage);
	}*/

	@RequiresPermissions("rms:damagedLuggage:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<DamagedLuggage> list(DamagedLuggage damagedLuggage) {
        return new DataGrid<>(damagedLuggageService.findList(damagedLuggage));
    }

	@RequiresPermissions("rms:damagedLuggage:view")
	@RequestMapping(value = "form")
	public String form(DamagedLuggage damagedLuggage, Model model) {
		model.addAttribute("damagedLuggage", damagedLuggage);
		return "modules/rms/damagedLuggageForm";
	}

	@RequiresPermissions("rms:damagedLuggage:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(DamagedLuggage damagedLuggage, Model model, Message message) {
		if (!beanValidator(model, damagedLuggage)){
			message.setMessage("数据校验错误!");
		}
		try {
			damagedLuggageService.save(damagedLuggage);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:damagedLuggage:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(DamagedLuggage damagedLuggage, Message message) {
		try {
			damagedLuggageService.delete(damagedLuggage);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}