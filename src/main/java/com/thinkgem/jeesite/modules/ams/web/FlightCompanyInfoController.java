/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.FlightCompanyInfo;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.FlightCompanyInfoService;

import java.util.Date;
import java.util.List;

/**
 * 航空公司信息管理Controller
 *
 * @author xiaopo
 * @version 2015-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/flightCompanyInfo")
public class FlightCompanyInfoController extends BaseController {

	@Autowired
	private FlightCompanyInfoService flightCompanyInfoService;

    /*@ModelAttribute
    public FlightCompanyInfo get(@RequestParam(required = false) String id) {
        FlightCompanyInfo entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = flightCompanyInfoService.get(id);
        }
        if (entity == null) {
            entity = new FlightCompanyInfo();
        }
        return entity;
    }*/

	@RequiresPermissions("ams:flightCompanyInfo:view")
	@RequestMapping("get")
	@ResponseBody
	public FlightCompanyInfo get(@RequestParam("id") String id) {
		return flightCompanyInfoService.get(id);
	}

	/**
	 * 展示航空公司列表
	 *
	 * @return
	 */
	@RequiresPermissions("ams:flightCompanyInfo:view")
	@RequestMapping(value = {"view", ""})
	public String view() {
		return "ams/flightCompany/flightCompanyInfo";
	}

    /*@RequiresPermissions("ams:flightCompanyInfo:view")
    @RequestMapping(value = {"list", ""})
    @ResponseBody
    public DataTablesPage<FlightCompanyInfo> list(Page<FlightCompanyInfo> flightCompanyInfoPage, DataTablesPage<FlightCompanyInfo> dataTablesPage, FlightCompanyInfo flightCompanyInfo,
                                                  HttpServletRequest request, HttpServletResponse response) {
        dataTablesPage.setColumns(request, flightCompanyInfoPage, flightCompanyInfo);
        return flightCompanyInfoService.findDataTablesPage(flightCompanyInfoPage, dataTablesPage, flightCompanyInfo);
    }*/

	@RequiresPermissions("ams:flightCompanyInfo:view")
	@RequestMapping("list")
	@ResponseBody
	public DataGrid<FlightCompanyInfo> list(FlightCompanyInfo flightCompanyInfo) {
		return new DataGrid<>(flightCompanyInfoService.findList(flightCompanyInfo));
	}

	@RequiresPermissions("ams:flightCompanyInfo:view")
	@RequestMapping(value = "form")
	public String form(FlightCompanyInfo flightCompanyInfo, Model model) {
		return "modules/ams/flightCompanyInfoForm";
	}

	@RequiresPermissions("ams:flightCompanyInfo:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(FlightCompanyInfo flightCompanyInfo, Model model, RedirectAttributes redirectAttributes, Message message) {
		flightCompanyInfoService.checkRedo(flightCompanyInfo,new String[]{"twoCode"},message);
		if(message.isSuccess()) return message;

		try {
			flightCompanyInfoService.save(flightCompanyInfo);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}

		return message;
	}

	@RequiresPermissions("ams:flightCompanyInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(FlightCompanyInfo flightCompanyInfo, Message message) {
		try {
			flightCompanyInfoService.delete(flightCompanyInfo);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	//@RequiresPermissions("ams:flightCompanyInfo:view")
	@RequestMapping(value = "jsonData")
	@ResponseBody
	public List<FlightCompanyInfo> getAll4Ajax(Message message) {
		return flightCompanyInfoService.findList(new FlightCompanyInfo());
	}
}