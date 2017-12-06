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
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightSecurity;
import com.thinkgem.jeesite.modules.rms.service.RmsFlightSecurityService;

/**
 * 安检旅客信息Controller
 * @author dingshuang
 * @version 2016-05-21
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/rmsFlightSecurity")
public class RmsFlightSecurityController extends BaseController {

	@Autowired
	private RmsFlightSecurityService rmsFlightSecurityService;
	
	@RequiresPermissions("rms:rmsFlightSecurity:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/rmsFlightSecurity/rmsFlightSecurity";
	}

	@RequiresPermissions("rms:rmsFlightSecurity:view")
    @RequestMapping("get")
    @ResponseBody
    public RmsFlightSecurity get(@RequestParam("id") String id) {
        return rmsFlightSecurityService.get(id);
    }

	/*@RequiresPermissions("rms:rmsFlightSecurity:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<RmsFlightSecurity> page, DataTablesPage dataTablesPage, RmsFlightSecurity rmsFlightSecurity, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, rmsFlightSecurity);
		return rmsFlightSecurityService.findDataTablesPage(page, dataTablesPage, rmsFlightSecurity);
	}*/

	@RequiresPermissions("rms:rmsFlightSecurity:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<RmsFlightSecurity> list(RmsFlightSecurity rmsFlightSecurity) {
        return new DataGrid<>(rmsFlightSecurityService.findList(rmsFlightSecurity));
    }

	@RequiresPermissions("rms:rmsFlightSecurity:view")
	@RequestMapping(value = "form")
	public String form(RmsFlightSecurity rmsFlightSecurity, Model model) {
		model.addAttribute("rmsFlightSecurity", rmsFlightSecurity);
		return "modules/rms/rmsFlightSecurityForm";
	}

	@RequiresPermissions("rms:rmsFlightSecurity:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(RmsFlightSecurity rmsFlightSecurity, Model model, Message message) {
		if (!beanValidator(model, rmsFlightSecurity)){
			message.setMessage("数据校验错误!");
		}
		try {
			rmsFlightSecurityService.save(rmsFlightSecurity);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:rmsFlightSecurity:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(RmsFlightSecurity rmsFlightSecurity, Message message) {
		try {
			rmsFlightSecurityService.delete(rmsFlightSecurity);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}