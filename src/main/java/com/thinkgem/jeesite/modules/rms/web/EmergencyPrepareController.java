/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.EmergencyPrepare;
import com.thinkgem.jeesite.modules.rms.entity.EmergencyUnit;
import com.thinkgem.jeesite.modules.rms.service.EmergencyPrepareService;
import com.thinkgem.jeesite.modules.rms.service.EmergencyPrepareToUnitService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 应急救援预案Controller
 * @author wjp
 * @version 2016-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/emergencyPrepare")
public class EmergencyPrepareController extends BaseController {

	@Autowired
	private EmergencyPrepareService emergencyPrepareService;

	@Autowired
	private EmergencyPrepareToUnitService emergencyPrepareToUnitService;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@RequiresPermissions("rms:emergencyPrepare:view")
	@RequestMapping("view")
	public String view(){
		return "rms/emergencyPrepare/emergencyPrepare";
	}

	@RequiresPermissions("rms:emergencyPrepare:handle")
	@RequestMapping("handle")
	public String viewHandle(Model model){
		//List<String> ecid = jdbcTemplate.queryForList("select id from RMS_EMERGENCY_UNIT where ntype='2'",String.class);
		//List<Map<String,Object>> euid = jdbcTemplate.queryForList("select id,unit_name as \"unitName\" from RMS_EMERGENCY_UNIT where ntype='1' and DEL_FLAG=0");
		//List<String> euid = jdbcTemplate.queryForList("select id from RMS_EMERGENCY_UNIT where ntype='1' and DEL_FLAG=0",String.class);
		//String euids = JSON.toJSONString(euid,true);
		//model.addAttribute("ecid",ecid);//员工id
		//model.addAttribute("euid",euids);//机构
		return "rms/emergencyPrepare/handle";
	}

	@RequiresPermissions("rms:emergencyPrepare:view")
	@RequestMapping("msg")
	public String msg(){
		return "rms/emergencyPrepare/msg";
	}

	@RequiresPermissions("rms:emergencyPrepare:view")
    @RequestMapping("get")
    @ResponseBody
    public EmergencyPrepare get(@RequestParam("id") String id) {
        return emergencyPrepareService.get(id);
    }


	/*@RequiresPermissions("rms:emergencyPrepare:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<EmergencyPrepare> page, DataTablesPage dataTablesPage, EmergencyPrepare emergencyPrepare, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, emergencyPrepare);
		return emergencyPrepareService.findDataTablesPage(page, dataTablesPage, emergencyPrepare);
	}*/

	@RequiresPermissions("rms:emergencyPrepare:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<EmergencyPrepare> list(EmergencyPrepare emergencyPrepare) {
		List<EmergencyPrepare> list = emergencyPrepareService.findList(emergencyPrepare);
		/*Map<String,Object> map = new HashMap<>();
		for(String epid: list.stream().map(n->n.getId()).collect(Collectors.toList())){
			List<String> l = emergencyPrepareToUnitService.findByEUIds(epid).stream().map(n->emergencyUnitService.get(n).getUnitName()).collect(Collectors.toList());
			String names = StringUtils.join(l);
			map.put(epid,names);
		}
		CacheUtils.put("findByEUNames",map);*/
        return new DataGrid<>(list);
    }

	@RequiresPermissions("rms:emergencyPrepare:view")
	@RequestMapping(value = "form")
	public String form(EmergencyPrepare emergencyPrepare, Model model) {
		model.addAttribute("emergencyPrepare", emergencyPrepare);
		return "modules/rms/emergencyPrepareForm";
	}

	@RequiresPermissions("rms:emergencyPrepare:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(EmergencyPrepare emergencyPrepare, Model model, Message message,@RequestParam String _emergencyUnit ) {
		if (!beanValidator(model, emergencyPrepare)){
			message.setMessage("数据校验错误!");
		}
		try {
			emergencyPrepareService.save(emergencyPrepare);
			//保存救援单位关联
			emergencyPrepareToUnitService.batchSave(emergencyPrepare.getId(),_emergencyUnit);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:emergencyPrepare:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(EmergencyPrepare emergencyPrepare, Message message) {
		try {
			emergencyPrepareService.delete(emergencyPrepare);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:emergencyPrepare:view")
	@RequestMapping(value = "findByEUNames")
	@ResponseBody
	public Message findByEUNames(String epid,Message message){
		if(StringUtils.isNotBlank(epid)) {
			try {
				List<Map> list = emergencyPrepareToUnitService.findByEUIds(epid);
				Map map = new HashMap<String,Object>(){
					{
						put("euids", StringUtils.join(list.stream().map(n -> n.get("euid")).collect(Collectors.toList()), ","));
						put("names", StringUtils.join(list.stream().map(n -> n.get("unitName")).collect(Collectors.toList()), "; "));
					}
				};
				message.setCode(1);
				message.setResult(new HashMap<String,Object>(){{put(epid,map);}});
			} catch (Exception e) {
				message.setMessage(e.getMessage());
			}
		}
		return message;

	}

}