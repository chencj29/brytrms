/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import javax.crypto.spec.PSource;
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
import com.thinkgem.jeesite.modules.rms.entity.RmsDutyGroup;
import com.thinkgem.jeesite.modules.rms.service.RmsDutyGroupService;

import java.util.List;

/**
 * 岗位班组管理Controller
 * @author xiaopo
 * @version 2016-05-19
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/rmsDutyGroup")
public class RmsDutyGroupController extends BaseController {

	@Autowired
	private RmsDutyGroupService rmsDutyGroupService;
	
	@RequiresPermissions("rms:rmsDutyGroup:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/rmsDutyGroup/rmsDutyGroup";
	}

	@RequiresPermissions("rms:rmsDutyGroup:view")
    @RequestMapping("get")
    @ResponseBody
    public RmsDutyGroup get(@RequestParam("id") String id) {
        return rmsDutyGroupService.get(id);
    }

	/*@RequiresPermissions("rms:rmsDutyGroup:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<RmsDutyGroup> page, DataTablesPage dataTablesPage, RmsDutyGroup rmsDutyGroup, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, rmsDutyGroup);
		return rmsDutyGroupService.findDataTablesPage(page, dataTablesPage, rmsDutyGroup);
	}*/

	@RequiresPermissions("rms:rmsDutyGroup:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<RmsDutyGroup> list(RmsDutyGroup rmsDutyGroup) {
        return new DataGrid<>(rmsDutyGroupService.findList(rmsDutyGroup));
    }

	@RequiresPermissions("rms:rmsDutyGroup:view")
	@RequestMapping(value = "form")
	public String form(RmsDutyGroup rmsDutyGroup, Model model) {
		model.addAttribute("rmsDutyGroup", rmsDutyGroup);
		return "modules/rms/rmsDutyGroupForm";
	}

	@RequiresPermissions("rms:rmsDutyGroup:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(RmsDutyGroup rmsDutyGroup, Model model, Message message) {
		if (!beanValidator(model, rmsDutyGroup)){
			message.setMessage("数据校验错误!");
		}
		try {
			rmsDutyGroupService.save(rmsDutyGroup);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:rmsDutyGroup:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(RmsDutyGroup rmsDutyGroup, Message message) {
		try {
			rmsDutyGroupService.delete(rmsDutyGroup);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	//@RequiresPermissions("rms:rmsDutyGroup:view")
	@RequestMapping("/listData")
	@ResponseBody
	public List<RmsDutyGroup> listDataByPostId(String postId){
		List<RmsDutyGroup> dutyGroups = null;
		dutyGroups = rmsDutyGroupService.listDataByPostId(postId);
		return dutyGroups;
	}

}