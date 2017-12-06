/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import cn.net.metadata.datasync.socketio.ConcurrentClientsHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.Vipplan;
import com.thinkgem.jeesite.modules.rms.service.VipplanService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * VIP计划表Controller
 * @author wjp
 * @version 2016-08-03
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/vipplan")
public class VipplanController extends BaseController {

	@Autowired
	private VipplanService vipplanService;
	
	@RequiresPermissions("rms:vipplan:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/vipplan/vipplan";
	}

	@RequiresPermissions("rms:vipplan:view")
    @RequestMapping("get")
    @ResponseBody
    public Vipplan get(@RequestParam("id") String id) {
        return vipplanService.get(id);
    }

	@RequiresPermissions("rms:vipplan:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<Vipplan> list(Vipplan vipplan) {
        return new DataGrid<>(vipplanService.findList(vipplan));
    }

	@RequiresPermissions("rms:vipplan:view")
	@RequestMapping(value = "form")
	public String form(Vipplan vipplan, Model model) {
		model.addAttribute("vipplan", vipplan);
		return "modules/rms/vipplanForm";
	}

	@RequiresPermissions("rms:vipplan:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(Vipplan vipplan, Model model, Message message) {
		if (!beanValidator(model, vipplan)){
			message.setMessage("数据校验错误!");
		}
		//实现vip更新后的航班系统也能同步数据
		Map<String,Object> param = new HashMap<>();
		if (StringUtils.isNotBlank(vipplan.getId())) param.put("opt", "U");
		else param.put("opt", "I");

		try {
			vipplanService.save(vipplan);
			message.setCode(1);
			//发送vip同步数据
			try {
				param.put("id",vipplan.getId());
				param.put("inoutTypeCode",vipplan.getInout());
				param.put("planDate", DateUtils.formatDate(vipplan.getPlandate())+" 00:00:00");
				param.put("flightCompanyCode",vipplan.getAircorp());
				param.put("flightNum",vipplan.getFltno());
				ConcurrentClientsHolder.getSocket("/vip_message").emit("vip_message_dispatcher", new ObjectMapper().writeValueAsString(param));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:vipplan:del")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(Vipplan vipplan, Message message) {
		Map<String,Object> param = new HashMap<>();
		param.put("opt", "D");
		try {
			vipplanService.delete(vipplan);
			message.setCode(1);

			//发送vip同步数据
			try {
				param.put("id",vipplan.getId());
				ConcurrentClientsHolder.getSocket("/vip_message").emit("vip_message_dispatcher", new ObjectMapper().writeValueAsString(param));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:vipplan:view")
	@RequestMapping(value = "vipinfo")
	@ResponseBody
	public Message vipView(String id, Message message){
		if(id==null) return message;
		try {
			String[] ids = id.split(",");
			Vipplan vipplan = new Vipplan();
			vipplan.set_ids(ids);
			Map<String,Object> result = new HashMap<>();
			result.put("vip",vipplanService.findVipList(vipplan));
			message.setResult(result);
			message.setCode(1);
		}catch (Exception e){
			e.printStackTrace();
			message.setMessage(e.getMessage());
		}
		return message;
	}

}