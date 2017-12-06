/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.AircraftBoarding;
import com.thinkgem.jeesite.modules.rms.service.AircraftBoardingService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 机位与登机口对应表Controller
 * @author wjp
 * @version 2016-03-09
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/aircraftBoarding")
public class AircraftBoardingController extends BaseController {

	@Autowired
	private AircraftBoardingService aircraftBoardingService;
	
	@RequiresPermissions("rms:aircraftBoarding:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/aircraftBoarding/aircraftBoarding";
	}

	@RequiresPermissions("rms:aircraftBoarding:view")
    @RequestMapping("get")
    @ResponseBody
    public AircraftBoarding get(@RequestParam("id") String id) {
        return aircraftBoardingService.get(id);
    }

	/*@RequiresPermissions("rms:aircraftBoarding:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<AircraftBoarding> page, DataTablesPage dataTablesPage, AircraftBoarding aircraftBoarding, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, aircraftBoarding);
		return aircraftBoardingService.findDataTablesPage(page, dataTablesPage, aircraftBoarding);
	}*/

	@RequiresPermissions("rms:aircraftBoarding:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<AircraftBoarding> list(AircraftBoarding aircraftBoarding) {
        return new DataGrid<>(aircraftBoardingService.findList(aircraftBoarding));
    }

	@RequiresPermissions("rms:aircraftBoarding:view")
	@RequestMapping(value = "form")
	public String form(AircraftBoarding aircraftBoarding, Model model) {
		model.addAttribute("aircraftBoarding", aircraftBoarding);
		return "modules/rms/aircraftBoardingForm";
	}

	@RequiresPermissions("rms:aircraftBoarding:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(AircraftBoarding aircraftBoarding, Model model, Message message) {
		if (!beanValidator(model, aircraftBoarding)){
			message.setMessage("数据校验错误!");
		}
		aircraftBoardingService.checkRedo(aircraftBoarding,new String[]{"aircraftStandNum"},message);
		if(message.isSuccess()) return message;

		try {
			aircraftBoardingService.save(aircraftBoarding);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:aircraftBoarding:del")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(AircraftBoarding aircraftBoarding, Message message) {
		try {
			aircraftBoardingService.delete(aircraftBoarding);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}