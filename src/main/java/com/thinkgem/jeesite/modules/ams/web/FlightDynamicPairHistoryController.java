/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.ams.web;

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
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamicPairHistory;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicPairHistoryService;

/**
 * 航班动态配对历史信息信息Controller
 * @author wjp
 * @version 2016-08-26
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/flightDynamicPairHistory")
public class FlightDynamicPairHistoryController extends BaseController {

	@Autowired
	private FlightDynamicPairHistoryService flightDynamicPairHistoryService;
	
	@RequiresPermissions("ams:flightDynamicPairHistory:view"	)
	@RequestMapping("view")
	public String view(){
		return "ams/flightDynamicPairHistory/flightDynamicPairHistory";
	}

	@RequiresPermissions("ams:flightDynamicPairHistory:view")
    @RequestMapping("get")
    @ResponseBody
    public FlightDynamicPairHistory get(@RequestParam("id") String id) {
        return flightDynamicPairHistoryService.get(id);
    }

	/*@RequiresPermissions("ams:flightDynamicPairHistory:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<FlightDynamicPairHistory> page, DataTablesPage dataTablesPage, FlightDynamicPairHistory flightDynamicPairHistory, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, flightDynamicPairHistory);
		return flightDynamicPairHistoryService.findDataTablesPage(page, dataTablesPage, flightDynamicPairHistory);
	}*/

	@RequiresPermissions("ams:flightDynamicPairHistory:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<FlightDynamicPairHistory> list(FlightDynamicPairHistory flightDynamicPairHistory) {
        return new DataGrid<>(flightDynamicPairHistoryService.findList(flightDynamicPairHistory));
    }

	@RequiresPermissions("ams:flightDynamicPairHistory:view")
	@RequestMapping(value = "form")
	public String form(FlightDynamicPairHistory flightDynamicPairHistory, Model model) {
		model.addAttribute("flightDynamicPairHistory", flightDynamicPairHistory);
		return "modules/ams/flightDynamicPairHistoryForm";
	}

	@RequiresPermissions("ams:flightDynamicPairHistory:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(FlightDynamicPairHistory flightDynamicPairHistory, Model model, Message message) {
		if (!beanValidator(model, flightDynamicPairHistory)){
			message.setMessage("数据校验错误!");
		}
		try {
			flightDynamicPairHistoryService.save(flightDynamicPairHistory);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("ams:flightDynamicPairHistory:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(FlightDynamicPairHistory flightDynamicPairHistory, Message message) {
		try {
			flightDynamicPairHistoryService.delete(flightDynamicPairHistory);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}