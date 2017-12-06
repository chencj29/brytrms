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
import com.thinkgem.jeesite.modules.rms.entity.FlightSeat;
import com.thinkgem.jeesite.modules.rms.service.FlightSeatService;

/**
 * 座位交接单Controller
 * @author wjp
 * @version 2016-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/flightSeat")
public class FlightSeatController extends BaseController {

	@Autowired
	private FlightSeatService flightSeatService;
	
	@RequiresPermissions("rms:flightSeat:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/flightSeat/flightSeat";
	}

	@RequiresPermissions("rms:flightSeat:view")
    @RequestMapping("get")
    @ResponseBody
    public FlightSeat get(@RequestParam("id") String id) {
        return flightSeatService.get(id);
    }

	/*@RequiresPermissions("rms:flightSeat:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<FlightSeat> page, DataTablesPage dataTablesPage, FlightSeat flightSeat, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, flightSeat);
		return flightSeatService.findDataTablesPage(page, dataTablesPage, flightSeat);
	}*/

	@RequiresPermissions("rms:flightSeat:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<FlightSeat> list(FlightSeat flightSeat) {
        return new DataGrid<>(flightSeatService.findList(flightSeat));
    }

	@RequiresPermissions("rms:flightSeat:view")
	@RequestMapping(value = "form")
	public String form(FlightSeat flightSeat, Model model) {
		model.addAttribute("flightSeat", flightSeat);
		return "modules/rms/flightSeatForm";
	}

	@RequiresPermissions("rms:flightSeat:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(FlightSeat flightSeat, Model model, Message message) {
		if (!beanValidator(model, flightSeat)){
			message.setMessage("数据校验错误!");
		}
		try {
			flightSeatService.save(flightSeat);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:flightSeat:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(FlightSeat flightSeat, Message message) {
		try {
			flightSeatService.delete(flightSeat);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}