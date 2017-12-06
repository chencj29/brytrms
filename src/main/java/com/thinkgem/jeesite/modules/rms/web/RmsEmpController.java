/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.RmsEmp;
import com.thinkgem.jeesite.modules.rms.service.RmsEmpService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 工作人员基础信息Controller
 *
 * @author wjp
 * @version 2016-03-10
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/rmsEmp")
public class RmsEmpController extends BaseController {

	@Autowired
	private RmsEmpService rmsEmpService;

	@RequiresPermissions("rms:rmsEmp:view")
	@RequestMapping("view")
	public String view() {
		return "rms/rmsEmp/rmsEmp";
	}

	@RequiresPermissions("rms:rmsEmp:view")
	@RequestMapping("get")
	@ResponseBody
	public RmsEmp get(@RequestParam("id") String id) {
		return rmsEmpService.get(id);
	}

	/*@RequiresPermissions("rms:rmsEmp:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<RmsEmp> page, DataTablesPage dataTablesPage, RmsEmp rmsEmp, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, rmsEmp);
		return rmsEmpService.findDataTablesPage(page, dataTablesPage, rmsEmp);
	}*/

	@RequiresPermissions("rms:rmsEmp:view")
	@RequestMapping("list")
	@ResponseBody
	public DataGrid<RmsEmp> list(RmsEmp rmsEmp) {
		return new DataGrid<>(rmsEmpService.findList(rmsEmp));
	}

	@RequiresPermissions("rms:rmsEmp:view")
	@RequestMapping(value = "form")
	public String form(RmsEmp rmsEmp, Model model) {
		model.addAttribute("rmsEmp", rmsEmp);
		return "modules/rms/rmsEmpForm";
	}

	@RequiresPermissions("rms:rmsEmp:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(RmsEmp rmsEmp, Model model, Message message) {
		if(!beanValidator(model, rmsEmp)) {
			message.setMessage("数据校验错误!");
		}

		rmsEmpService.checkRedo(rmsEmp,new String[]{"jobNum"},message);
		if(message.isSuccess()) return message;

		try {
			rmsEmpService.save(rmsEmp);
			message.setCode(1);
		} catch(Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:rmsEmp:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(RmsEmp rmsEmp, Message message) {
		try {
			rmsEmpService.delete(rmsEmp);
			message.setCode(1);
		} catch(Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequestMapping("/jsonData")
	@ResponseBody
	public List<RmsEmp> getAll4Ajax(String postId) {
		RmsEmp emp = new RmsEmp();
		if(StringUtils.isNotBlank(postId))
			emp.setPostId(postId);
		return rmsEmpService.findList(emp);
	}

	@RequestMapping("/getPostId")
	@ResponseBody
	public String getPostId(String empId){
		if(StringUtils.isNotBlank(empId)){
			RmsEmp emp = get(empId);
			if(emp != null) return emp.getPostId();
		}
		return "";
	}

}