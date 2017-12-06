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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.easyui.DataGrid.DataGrid;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.SafeguardSeatTimelong;
import com.thinkgem.jeesite.modules.rms.service.SafeguardSeatTimelongService;

import java.util.ArrayList;

/**
 * 保障过程及座位时长对应Controller
 * @author xiaopo
 * @version 2016-03-29
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/safeguardSeatTimelong")
public class SafeguardSeatTimelongController extends BaseController {

	@Autowired
	private SafeguardSeatTimelongService safeguardSeatTimelongService;
	
	@RequiresPermissions("rms:safeguardSeatTimelong:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/safeguardSeatTimelong/safeguardSeatTimelong";
	}

	@RequiresPermissions("rms:safeguardSeatTimelong:view")
    @RequestMapping("get")
    @ResponseBody
    public SafeguardSeatTimelong get(@RequestParam("id") String id) {
        return safeguardSeatTimelongService.get(id);
    }

	/*@RequiresPermissions("rms:safeguardSeatTimelong:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<SafeguardSeatTimelong> page, DataTablesPage dataTablesPage, SafeguardSeatTimelong safeguardSeatTimelong, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, safeguardSeatTimelong);
		return safeguardSeatTimelongService.findDataTablesPage(page, dataTablesPage, safeguardSeatTimelong);
	}*/

	@RequiresPermissions("rms:safeguardSeatTimelong:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<SafeguardSeatTimelong> list(SafeguardSeatTimelong safeguardSeatTimelong) {
        return new DataGrid<>(safeguardSeatTimelongService.findList(safeguardSeatTimelong));
    }

	@RequiresPermissions("rms:safeguardSeatTimelong:view")
	@RequestMapping(value = "form")
	public String form(SafeguardSeatTimelong safeguardSeatTimelong, Model model) {
		model.addAttribute("safeguardSeatTimelong", safeguardSeatTimelong);
		return "modules/rms/safeguardSeatTimelongForm";
	}

	@RequiresPermissions("rms:safeguardSeatTimelong:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(SafeguardSeatTimelong safeguardSeatTimelong, Model model, Message message) {
		if (!beanValidator(model, safeguardSeatTimelong)){
			message.setMessage("数据校验错误!");
		}
		try {
			safeguardSeatTimelongService.save(safeguardSeatTimelong);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:safeguardSeatTimelong:edit")
	@RequestMapping(value = "batchModify")
	@ResponseBody
	public Message batchModify(@RequestBody ArrayList<SafeguardSeatTimelong> safeguardSeatTimelong, Message message) {
		try {
			safeguardSeatTimelongService.batchModify(safeguardSeatTimelong);
			message.setCode(1);
		}catch(Exception e){
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:safeguardSeatTimelong:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(SafeguardSeatTimelong safeguardSeatTimelong, Message message) {
		try {
			safeguardSeatTimelongService.delete(safeguardSeatTimelong);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}