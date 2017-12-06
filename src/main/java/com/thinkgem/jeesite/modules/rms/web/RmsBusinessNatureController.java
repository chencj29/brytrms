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
import com.thinkgem.jeesite.modules.rms.entity.RmsBusinessNature;
import com.thinkgem.jeesite.modules.rms.service.RmsBusinessNatureService;

/**
 * 岗位业务性质管理Controller
 * @author xiaopo
 * @version 2016-05-18
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/rmsBusinessNature")
public class RmsBusinessNatureController extends BaseController {

	@Autowired
	private RmsBusinessNatureService rmsBusinessNatureService;
	
	@RequiresPermissions("rms:rmsBusinessNature:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/rmsBusinessNature/rmsBusinessNature";
	}

	@RequiresPermissions("rms:rmsBusinessNature:view")
    @RequestMapping("get")
    @ResponseBody
    public RmsBusinessNature get(@RequestParam("id") String id) {
        return rmsBusinessNatureService.get(id);
    }

	/*@RequiresPermissions("rms:rmsBusinessNature:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<RmsBusinessNature> page, DataTablesPage dataTablesPage, RmsBusinessNature rmsBusinessNature, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, rmsBusinessNature);
		return rmsBusinessNatureService.findDataTablesPage(page, dataTablesPage, rmsBusinessNature);
	}*/

	@RequiresPermissions("rms:rmsBusinessNature:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<RmsBusinessNature> list(RmsBusinessNature rmsBusinessNature) {
        return new DataGrid<>(rmsBusinessNatureService.findList(rmsBusinessNature));
    }

	@RequiresPermissions("rms:rmsBusinessNature:view")
	@RequestMapping(value = "form")
	public String form(RmsBusinessNature rmsBusinessNature, Model model) {
		model.addAttribute("rmsBusinessNature", rmsBusinessNature);
		return "modules/rms/rmsBusinessNatureForm";
	}

	@RequiresPermissions("rms:rmsBusinessNature:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(RmsBusinessNature rmsBusinessNature, Model model, Message message) {
		if (!beanValidator(model, rmsBusinessNature)){
			message.setMessage("数据校验错误!");
		}
		try {
			rmsBusinessNatureService.save(rmsBusinessNature);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:rmsBusinessNature:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(RmsBusinessNature rmsBusinessNature, Message message) {
		try {
			rmsBusinessNatureService.delete(rmsBusinessNature);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}