/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.DepartureHallOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.DepartureHallOccupyingInfoService;
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
 * 候机厅占用信息Controller
 * @author 候机厅占用信息
 * @version 2016-04-18
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/departureHallOccupyingInfo")
public class DepartureHallOccupyingInfoController extends BaseController {

	@Autowired
	private DepartureHallOccupyingInfoService departureHallOccupyingInfoService;
	
	@RequiresPermissions("rms:departureHallOccupyingInfo:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/departureHallOccupyingInfo/departureHallOccupyingInfo";
	}

	@RequiresPermissions("rms:departureHallOccupyingInfo:view")
    @RequestMapping("get")
    @ResponseBody
    public DepartureHallOccupyingInfo get(@RequestParam("id") String id) {
        return departureHallOccupyingInfoService.get(id);
    }

	@RequiresPermissions("rms:departureHallOccupyingInfo:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<DepartureHallOccupyingInfo> list(DepartureHallOccupyingInfo departureHallOccupyingInfo) {
        return new DataGrid<>(departureHallOccupyingInfoService.findList(departureHallOccupyingInfo));
    }

	@RequiresPermissions("rms:departureHallOccupyingInfo:view")
	@RequestMapping(value = "form")
	public String form(DepartureHallOccupyingInfo departureHallOccupyingInfo, Model model) {
		model.addAttribute("departureHallOccupyingInfo", departureHallOccupyingInfo);
		return "modules/rms/departureHallOccupyingInfoForm";
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(DepartureHallOccupyingInfo departureHallOccupyingInfo, Model model, Message message) {
		if (!beanValidator(model, departureHallOccupyingInfo)){
			message.setMessage("数据校验错误!");
		}
		try {
			departureHallOccupyingInfoService.save(departureHallOccupyingInfo);
			message.setResult(ImmutableMap.of("ocid", departureHallOccupyingInfo.getId()));
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:departureHallOccupyingInfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(DepartureHallOccupyingInfo departureHallOccupyingInfo, Message message) {
		try {
			departureHallOccupyingInfoService.delete(departureHallOccupyingInfo);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}


	@RequestMapping("fetchByFlightDynamicId")
	@ResponseBody
	public DepartureHallOccupyingInfo getOccupyingInfoByFlightDynamicId(String flightDynamicId) {
		return departureHallOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
	}



	@RequestMapping("fetchOciDatas")
	@ResponseBody
	public List<DepartureHallOccupyingInfo> fetchDatas(@RequestParam("pairIds[]") List<String> pairIds) {
		return departureHallOccupyingInfoService.fetchOciDatas(pairIds);
	}

}