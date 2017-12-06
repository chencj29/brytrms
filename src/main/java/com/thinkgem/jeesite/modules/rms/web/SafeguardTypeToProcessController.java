/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.SafeguardTypeToProcess;
import com.thinkgem.jeesite.modules.rms.entity.StpVO;
import com.thinkgem.jeesite.modules.rms.service.SafeguardTypeToProcessService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

/**
 * 保障类型保障过程关联Controller
 *
 * @author chrischen
 * @version 2016-03-17
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/safeguardTypeToProcess")
public class SafeguardTypeToProcessController extends BaseController {

	@Autowired
	private SafeguardTypeToProcessService safeguardTypeToProcessService;

	@RequiresPermissions("rms:safeguardTypeToProcess:view")
	@RequestMapping("view")
	public String view() {
		return "rms/safeguardTypeToProcess/safeguardTypeToProcess";
	}

	@RequiresPermissions("rms:safeguardTypeToProcess:view")
	@RequestMapping("get")
	@ResponseBody
	public SafeguardTypeToProcess get(@RequestParam("id") String id) {
		return safeguardTypeToProcessService.get(id);
	}

	/*@RequiresPermissions("rms:safeguardTypeToProcess:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<SafeguardTypeToProcess> page, DataTablesPage dataTablesPage, SafeguardTypeToProcess safeguardTypeToProcess, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, safeguardTypeToProcess);
		return safeguardTypeToProcessService.findDataTablesPage(page, dataTablesPage, safeguardTypeToProcess);
	}*/

	@RequiresPermissions("rms:safeguardTypeToProcess:view")
	@RequestMapping("list")
	@ResponseBody
	public DataGrid<SafeguardTypeToProcess> list(SafeguardTypeToProcess safeguardTypeToProcess) {
		return new DataGrid<>(safeguardTypeToProcessService.findList(safeguardTypeToProcess));
	}

	@RequiresPermissions("rms:safeguardTypeToProcess:view")
	@RequestMapping(value = "form")
	public String form(SafeguardTypeToProcess safeguardTypeToProcess, Model model) {
		model.addAttribute("safeguardTypeToProcess", safeguardTypeToProcess);
		return "modules/rms/safeguardTypeToProcessForm";
	}

	@RequiresPermissions("rms:safeguardTypeToProcess:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(SafeguardTypeToProcess safeguardTypeToProcess, Model model, Message message) {
		if (!beanValidator(model, safeguardTypeToProcess)) {
			message.setMessage("数据校验错误!");
		}
		try {
			safeguardTypeToProcessService.save(safeguardTypeToProcess);
			message.setCode(1);
			logger.info("更新成功！");
		} catch (Exception e) {
			message.setMessage(e.getMessage());
			logger.error(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:safeguardTypeToProcess:edit")
	@RequestMapping(value = "setTimeSeq")
	@ResponseBody
	public Message setTimeSeq(SafeguardTypeToProcess safeguardTypeToProcess, Model model, Message message) {
		if (!beanValidator(model, safeguardTypeToProcess)) {
			message.setMessage("数据校验错误!");
		}
		try {
			safeguardTypeToProcessService.updateTimeSeq(safeguardTypeToProcess);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:safeguardTypeToProcess:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(SafeguardTypeToProcess safeguardTypeToProcess, Message message) {
		try {
			safeguardTypeToProcessService.delete(safeguardTypeToProcess);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:safeguardTypeToProcess:edit")
	@RequestMapping(value = "deleteByRelate")
	@ResponseBody
	public Message deleteByRelate(SafeguardTypeToProcess safeguardTypeToProcess, Message message) {
		try {
			safeguardTypeToProcessService.deleteByRelate(safeguardTypeToProcess);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}


	/*********
	 * 过程时序设置
	 **********************/
	@RequiresPermissions("rms:safeguardTypeToProcess:view")
	@RequestMapping("timeSeq")
	public String timeSeq() {
		return "/rms/safeguardTypeToProcess/processTimeSeq";
	}

	@RequiresPermissions("rms:safeguardTypeToProcess:view")
	@RequestMapping(value = "pTimeSeq")
	@ResponseBody
	public DataGrid<StpVO> pTimeSeqList(SafeguardTypeToProcess safeguardTypeToProcess) {
		return new DataGrid<StpVO>(safeguardTypeToProcessService.findStpVOList(safeguardTypeToProcess));
	}


	@RequiresPermissions("rms:safeguardTypeToProcess:sort")
	@RequestMapping(value = "updateSort")
	@ResponseBody
	public Message updateSort(@RequestParam("sort[]") ArrayList<Integer> sort, @RequestParam("ids[]") ArrayList<String> ids,String typeId,Message message) {
		try {
			safeguardTypeToProcessService.updateSort(sort,ids,typeId);
			message.setCode(1);
		}catch (Exception e){
			message.setMessage(e.getMessage());
		}
		return message;
	}
}