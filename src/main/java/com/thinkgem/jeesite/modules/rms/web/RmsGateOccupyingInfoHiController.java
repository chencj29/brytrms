/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;
import com.thinkgem.jeesite.modules.rms.service.FlightPlanPairService;
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
import com.thinkgem.jeesite.modules.rms.entity.RmsGateOccupyingInfoHi;
import com.thinkgem.jeesite.modules.rms.service.RmsGateOccupyingInfoHiService;

import java.util.List;

/**
 * 机位占用信息历史Controller
 * @author wjp
 * @version 2016-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/rmsGateOccupyingInfoHi")
public class RmsGateOccupyingInfoHiController extends BaseController {

	@Autowired
	private RmsGateOccupyingInfoHiService rmsGateOccupyingInfoHiService;
	
	@RequiresPermissions("rms:rmsGateOccupyingInfoHi:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/rmsGateOccupyingInfoHi/rmsGateOccupyingInfoHi";
	}

	@RequiresPermissions("rms:rmsGateOccupyingInfoHi:view")
    @RequestMapping("get")
    @ResponseBody
    public RmsGateOccupyingInfoHi get(@RequestParam("id") String id) {
        return rmsGateOccupyingInfoHiService.get(id);
    }

	/*@RequiresPermissions("rms:rmsGateOccupyingInfoHi:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<RmsGateOccupyingInfoHi> page, DataTablesPage dataTablesPage, RmsGateOccupyingInfoHi rmsGateOccupyingInfoHi, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, rmsGateOccupyingInfoHi);
		return rmsGateOccupyingInfoHiService.findDataTablesPage(page, dataTablesPage, rmsGateOccupyingInfoHi);
	}*/

	@RequiresPermissions("rms:rmsGateOccupyingInfoHi:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<RmsGateOccupyingInfoHi> list(RmsGateOccupyingInfoHi rmsGateOccupyingInfoHi) {
        return new DataGrid<>(rmsGateOccupyingInfoHiService.findList(rmsGateOccupyingInfoHi));
    }

	@RequiresPermissions ("rms:rmsGateOccupyingInfoHi:view")
	@RequestMapping ("flightDynamics")
	@ResponseBody
	public List<FlightPlanPair> listFlightDynamics() {
		List<FlightPlanPair> flightPlanPairs = flightPlanPairService.findList(new FlightPlanPair());
		return flightPlanPairs;
	}

	@RequiresPermissions("rms:rmsGateOccupyingInfoHi:view")
	@RequestMapping(value = "form")
	public String form(RmsGateOccupyingInfoHi rmsGateOccupyingInfoHi, Model model) {
		model.addAttribute("rmsGateOccupyingInfoHi", rmsGateOccupyingInfoHi);
		return "modules/rms/rmsGateOccupyingInfoHiForm";
	}

	@RequiresPermissions("rms:rmsGateOccupyingInfoHi:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(RmsGateOccupyingInfoHi rmsGateOccupyingInfoHi, Model model, Message message) {
		if (!beanValidator(model, rmsGateOccupyingInfoHi)){
			message.setMessage("数据校验错误!");
		}
		try {
			rmsGateOccupyingInfoHiService.save(rmsGateOccupyingInfoHi);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:rmsGateOccupyingInfoHi:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(RmsGateOccupyingInfoHi rmsGateOccupyingInfoHi, Message message) {
		try {
			rmsGateOccupyingInfoHiService.delete(rmsGateOccupyingInfoHi);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@Autowired
	FlightPlanPairService flightPlanPairService;
}