/** Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.CheckinCounter;
import com.thinkgem.jeesite.modules.rms.service.CheckinCounterService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 值机柜台基础信息表Controller
 * @author wjp
 * @version 2016-03-09
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/checkinCounter")
public class CheckinCounterController extends BaseController {

	@Autowired
	private CheckinCounterService checkinCounterService;
	
	@RequiresPermissions("rms:checkinCounter:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/checkinCounter/checkinCounter";
	}

	@RequiresPermissions("rms:checkinCounter:view")
    @RequestMapping("get")
    @ResponseBody
    public CheckinCounter get(@RequestParam("id") String id) {
        return checkinCounterService.get(id);
    }

	@RequiresPermissions("rms:checkinCounter:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<CheckinCounter> list(CheckinCounter checkinCounter) {
        return new DataGrid<>(checkinCounterService.findList(checkinCounter));
    }

	@RequiresPermissions("rms:checkinCounter:view")
	@RequestMapping(value = "form")
	public String form(CheckinCounter checkinCounter, Model model) {
		model.addAttribute("checkinCounter", checkinCounter);
		return "modules/rms/checkinCounterForm";
	}

	@RequiresPermissions("rms:checkinCounter:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(CheckinCounter checkinCounter, Model model, Message message) {
		if (!beanValidator(model, checkinCounter)){
			message.setMessage("数据校验错误!");
		}

		checkinCounterService.checkRedo(checkinCounter,new String[]{"checkinCounterNum"},message);
		if(message.isSuccess()) return message;

		try {
			checkinCounterService.save(checkinCounter);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:checkinCounter:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(CheckinCounter checkinCounter, Message message) {
		try {
			checkinCounterService.delete(checkinCounter);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

}