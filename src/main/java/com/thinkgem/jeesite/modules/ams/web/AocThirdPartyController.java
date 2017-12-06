/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.ams.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.AocThirdParty;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.AocThirdPartyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 空管动态变更表Controller
 * @author wjp
 * @version 2017-05-22
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/aocThirdParty")
public class AocThirdPartyController extends BaseController {

	@Autowired
	private AocThirdPartyService aocThirdPartyService;
	
	@RequiresPermissions("ams:aocThirdParty:view"	)
	@RequestMapping("view")
	public String view(){
		return "ams/aocThirdParty/aocThirdParty";
	}

	@RequiresPermissions("ams:aocThirdParty:view")
    @RequestMapping("get")
    @ResponseBody
    public AocThirdParty get(@RequestParam("id") String id) {
        return aocThirdPartyService.get(id);
    }

	@RequiresPermissions("ams:aocThirdParty:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<AocThirdParty> page, DataTablesPage dataTablesPage, AocThirdParty aocThirdParty, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, aocThirdParty);
		return aocThirdPartyService.findDataTablesPage(page, dataTablesPage, aocThirdParty);
	}
	
	@RequiresPermissions("ams:aocThirdParty:view")
	@RequestMapping(value = "form")
	public String form(AocThirdParty aocThirdParty, Model model) {
		model.addAttribute("aocThirdParty", aocThirdParty);
		return "modules/ams/aocThirdPartyForm";
	}

	@RequiresPermissions("ams:aocThirdParty:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(AocThirdParty aocThirdParty, Model model, Message message) {
		if (!beanValidator(model, aocThirdParty)){
			message.setMessage("数据校验错误!");
		}
		try {
			aocThirdPartyService.save(aocThirdParty);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("ams:aocThirdParty:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(AocThirdParty aocThirdParty, Message message) {
		try {
			aocThirdPartyService.delete(aocThirdParty);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}