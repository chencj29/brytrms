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
import com.thinkgem.jeesite.modules.rms.entity.SpecialCar;
import com.thinkgem.jeesite.modules.rms.service.SpecialCarService;

/**
 * 特殊车辆基础信息Controller
 * @author wjp
 * @version 2016-03-09
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/specialCar")
public class SpecialCarController extends BaseController {

	@Autowired
	private SpecialCarService specialCarService;
	
	@RequiresPermissions("rms:specialCar:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/specialCar/specialCar";
	}

	@RequiresPermissions("rms:specialCar:view")
    @RequestMapping("get")
    @ResponseBody
    public SpecialCar get(@RequestParam("id") String id) {
        return specialCarService.get(id);
    }

	/*@RequiresPermissions("rms:specialCar:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<SpecialCar> page, DataTablesPage dataTablesPage, SpecialCar specialCar, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, specialCar);
		return specialCarService.findDataTablesPage(page, dataTablesPage, specialCar);
	}*/

	@RequiresPermissions("rms:specialCar:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<SpecialCar> list(SpecialCar specialCar) {
        return new DataGrid<>(specialCarService.findList(specialCar));
    }

	@RequiresPermissions("rms:specialCar:view")
	@RequestMapping(value = "form")
	public String form(SpecialCar specialCar, Model model) {
		model.addAttribute("specialCar", specialCar);
		return "modules/rms/specialCarForm";
	}

	@RequiresPermissions("rms:specialCar:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(SpecialCar specialCar, Model model, Message message) {
		if (!beanValidator(model, specialCar)){
			message.setMessage("数据校验错误!");
		}
		try {
			specialCarService.save(specialCar);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:specialCar:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(SpecialCar specialCar, Message message) {
		try {
			specialCarService.delete(specialCar);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}