/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.AocThirdParty;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.FlightPairWrapper;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.AocThirdPartyService;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.ams.utils.AmsConstants;
import com.thinkgem.jeesite.modules.rms.dao.FlightPairProgressInfoDao;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 航班动态管理Controller
 *
 * @author xiaopo
 * @version 2016-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/flightDynamicManager")
public class FlightDynamicManagerController extends BaseController {

	@Autowired
	private FlightDynamicService flightDynamicService;

	@Autowired
	private AocThirdPartyService aocThirdPartyService;

	@ModelAttribute
	public FlightDynamic get(@RequestParam(required = false) String id) {
		FlightDynamic entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = flightDynamicService.get(id);
		}
		if (entity == null) {
			entity = new FlightDynamic();
		}
		return entity;
	}

	@RequiresPermissions("ams:flightDynamicManager:view")
	@RequestMapping(value = "view")
	public String view() {
		return "ams/flightplan/flightDynamicManager";
	}

	@RequiresPermissions("ams:flightDynamicManager:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage<FlightDynamic> list(FlightDynamic flightDynamic, Page<FlightDynamic> page, DataTablesPage<FlightDynamic> dataTablesPage, HttpServletRequest request, HttpServletResponse response, Model model) {
		dataTablesPage.setColumns(request, page, flightDynamic);
		dataTablesPage = flightDynamicService.findDataTablesPage(page, dataTablesPage, flightDynamic);
		return dataTablesPage;
	}

	@RequiresPermissions("ams:flightDynamicManager:view")
	@RequestMapping(value = {"pairlist", ""})
	@ResponseBody
	public DataTablesPage<Map> findPairList(FlightPairWrapper flightPairWrapper, Page<Map> page, DataTablesPage<Map> dataTablesPage, HttpServletRequest request, HttpServletResponse response, Model model) {
		dataTablesPage.setColumns(request, page, flightPairWrapper);
		dataTablesPage = flightDynamicService.findDataTablesPagePair(page, dataTablesPage, flightPairWrapper);
		return dataTablesPage;
	}

	@RequiresPermissions("ams:flightDynamicManager:view")
	@RequestMapping(value = "form")
	public String form(FlightDynamic flightDynamic, Model model) {
		model.addAttribute("flightDynamic", flightDynamic);
		return "modules/ams/flightDynamicForm";
	}

	@RequiresPermissions("ams:flightDynamicManager:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(FlightDynamic flightDynamic, Model model, Message message) {
		if (!beanValidator(model, flightDynamic)) {
			message.setMessage("数据校验失败.");
		}
		try {
			flightDynamicService.save(flightDynamic);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("ams:flightDynamicManager:updatetime")
	@RequestMapping(value = "updateTime")
	@ResponseBody
	public Message updateTime(FlightDynamic flightDynamic, Model model, Message message) {
		return save(flightDynamic, model, message);
	}


	@RequiresPermissions("ams:flightDynamicManager:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(FlightDynamic flightDynamic, Message message) {
		try {
			flightDynamicService.delete(flightDynamic);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("ams:flightDynamicManager:edit")
	@RequestMapping(value = "deletes")
	@ResponseBody
	public Message deletes(@RequestParam("id[]") ArrayList<String> id, Message message) {
		if (id != null && id.size() > 0) {
			try {
				for (String s : id) {
					FlightDynamic temp = new FlightDynamic(s);
					flightDynamicService.delete(temp);
				}
				message.setCode(1);
			} catch (Exception e) {
				message.setMessage(e.getMessage());
			}
		}
		return message;
	}


	@RequiresPermissions("ams:flightDynamicManager:delay")
	@RequestMapping(value = "delay")
	@ResponseBody
	public Message delay(FlightDynamic flightDynamic, Message message, HttpServletRequest request) {
		try {
			flightDynamicService.delay(flightDynamic);
			LogUtils.saveLog(request, UserUtils.getUser().getLoginName() + "发布了航班延迟消息.航班号:" + flightDynamic.getFlightNum());
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("ams:flightDynamicManager:cancel")
	@RequestMapping(value = "cancel")
	@ResponseBody
	public Message cancel(FlightDynamic flightDynamic, Message message, HttpServletRequest request) {
		try {
			flightDynamic.setStatus(AmsConstants.FLIGHT_STATUS_CANCEL);
			flightDynamic.setStatusName(AmsConstants.FLIGHT_STATUS_CANCEL_NAME);
			flightDynamicService.save(flightDynamic);
			LogUtils.saveLog(request, UserUtils.getUser().getLoginName() + "取消了该航班.航班号:" + flightDynamic.getFlightNum());
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("ams:flightDynamicManager:alterNate")
	@RequestMapping(value = "alterNate")
	@ResponseBody
	public Message alterNate(FlightDynamic flightDynamic, Message message, HttpServletRequest request) {
		try {
			flightDynamic.setStatus(AmsConstants.FLIGHT_STATUS_ALTERNATE);
			flightDynamic.setStatusName(AmsConstants.FLIGHT_STATUS_ALTERNATE_NAME);
			flightDynamicService.save(flightDynamic);
			LogUtils.saveLog(request, UserUtils.getUser().getLoginName() + "发布航班备降信息.航班号:" + flightDynamic.getFlightNum());
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("ams:flightDynamicManager:takeOff")
	@RequestMapping(value = "takeOff")
	@ResponseBody
	public Message takeOff(FlightDynamic flightDynamic, Message message, HttpServletRequest request) {
		try {
			flightDynamic.setStatus(AmsConstants.FLIGHT_STATUS_TAKEOFF);
			flightDynamic.setStatusName(AmsConstants.FLIGHT_STATUS_TAKEOFF_NAME);
			flightDynamicService.save(flightDynamic);
			LogUtils.saveLog(request, UserUtils.getUser().getLoginName() + "发布航班起飞信息.航班号:" + flightDynamic.getFlightNum());
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("ams:flightDynamicManager:haveIn")
	@RequestMapping(value = "haveIn")
	@ResponseBody
	public Message haveIn(FlightDynamic flightDynamic, Message message, HttpServletRequest request) {
		try {
			flightDynamic.setStatus(AmsConstants.FLIGHT_STATUS_HAVEIN);
			flightDynamic.setStatusName(AmsConstants.FLIGHT_STATUS_HAVEIN_NAME);
			flightDynamicService.save(flightDynamic);
			LogUtils.saveLog(request, UserUtils.getUser().getLoginName() + "发布航班到达信息.航班号:" + flightDynamic.getFlightNum());
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}


	@RequiresPermissions("ams:flightDynamicManager:clear")
	@RequestMapping(value = "clearStatus")
	@ResponseBody
	public Message clearStatus(FlightDynamic flightDynamic, Message message, HttpServletRequest request) {
		try {
			//重置动态数据，恢复到初始化状态
			flightDynamicService.restoreFlightDynamic(flightDynamic);
			flightDynamicService.save(flightDynamic);
			LogUtils.saveLog(request, UserUtils.getUser().getLoginName() + "清除航班状态.航班号:" + flightDynamic.getFlightNum());
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

//	@RequiresPermissions("ams:flightDynamicManager:edit")
//	@RequestMapping("/testWrapper")
//	@ResponseBody
	/*public Message testWrapper(@RequestBody FlightPlanWrapper flightPlanWrapper){
		System.out.println(flightPlanWrapper);
		return null;
	}*/

	@RequiresPermissions("ams:flightDynamicManager:guest")
	@RequestMapping("guest")
	@ResponseBody
	public Message guest(FlightDynamic flightDynamic, Message message) {
		try {
			flightDynamic.setStatus(AmsConstants.FLIGHT_STATUS_GUEST);
			flightDynamic.setStatusName(AmsConstants.FLIGHT_STATUS_GUEST_NAME);
			flightDynamicService.save(flightDynamic);
			message.setCode(1);
		} catch (Exception e) {
			e.printStackTrace();
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("ams:flightDynamicManager:board")
	@RequestMapping("board")
	@ResponseBody
	public Message board(FlightDynamic flightDynamic, Message message) {
		try {
			flightDynamic.setStatus(AmsConstants.FLIGHT_STATUS_BOARD);
			flightDynamic.setStatusName(AmsConstants.FLIGHT_STATUS_BOARD_NAME);
			flightDynamicService.save(flightDynamic);
			message.setCode(1);
		} catch (Exception e) {
			e.printStackTrace();
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("ams:flightDynamicManager:stboard")
	@RequestMapping("stboard")
	@ResponseBody
	public Message stboard(String id, Message message) {
		try {
			flightDynamicService.updateStatus(id, AmsConstants.FLIGHT_STATUS_STBOD, AmsConstants.FLIGHT_STATUS_STBOD_NAME);
			message.setCode(1);
		} catch (Exception e) {
			e.printStackTrace();
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("ams:flightDynamicManager:boardend")
	@RequestMapping("boardend")
	@ResponseBody
	public Message boardEnd(FlightDynamic dynamic, Message message) {
		try {
			dynamic.setStatus(AmsConstants.FLIGHT_STATUS_BOARDEND);
			dynamic.setStatusName(AmsConstants.FLIGHT_STATUS_BOARDEND_NAME);
			flightDynamicService.save(dynamic);
			message.setCode(1);
		} catch (Exception e) {
			e.printStackTrace();
			message.setMessage(e.getMessage());
		}
		return message;
	}


	@RequiresPermissions("ams:flightDynamicManager:takeoffend")
	@RequestMapping("takeoffend")
	@ResponseBody
	public Message takeoffend(String id, Message message) {
		try {
			flightDynamicService.updateStatus(id, AmsConstants.FLIGHT_STATUS_TAKEOFFEND, AmsConstants.FLIGHT_STATUS_TAKEOFFEND_NAME);
			message.setCode(1);
		} catch (Exception e) {
			e.printStackTrace();
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("ams:flightDynamicManager:change")
	@RequestMapping("changeAircraftNum")
	@ResponseBody
	public Message changeAircraftNum(@RequestParam("id[]") ArrayList<String> id, @RequestParam("newAircraftNum[]") ArrayList<String> newAircraftNum, Message message) {
		try {
			flightDynamicService.changeAircraftNum(id, newAircraftNum);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}

	@RequiresPermissions("ams:flightDynamicManager:urgeBoard")
	@RequestMapping("flightUrgeBoard")
	@ResponseBody
	public Message flightUrgeBoard(FlightDynamic flightDynamic, Date urgeBoardTime , Message message) {
		try {
			flightDynamicService.flightUrgeBoard(flightDynamic, urgeBoardTime);
			message.setCode(1);
		} catch (Exception e) {
			e.printStackTrace();
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("ams:flightDynamicManager:sildeback")
	@RequestMapping("sildeback")
	@ResponseBody
	public Message sildeBack(FlightDynamic dynamic,Message message){

		try {
			dynamic.setStatus(AmsConstants.FLIGHT_STATUS_SILDEBACK);
			dynamic.setStatusName(AmsConstants.FLIGHT_STATUS_SILDEBACK_NAME);
			flightDynamicService.save(dynamic);
			message.setCode(1);
		} catch (Exception e) {
			e.printStackTrace();
			message.setMessage(e.getMessage());
		}
		return message;
	}

	//-------------第三方消息审核处理-------------

	/**
	 * 消息审核页面(弹出页)
	 *
	 * @param map
	 * @return
	 */
	@RequiresPermissions("ams:flightDynamicManager:aocThirdParty")
	@RequestMapping("aocThirdParty")
	public String aocThirdPartyView(ModelMap map) {
		List<AocThirdParty> allList = aocThirdPartyService.findList(new AocThirdParty()).stream().filter(aocThirdParty ->
				UserUtils.dataScopeFilterAocThirdParty(aocThirdParty.getFlightCompanyCode())
		).collect(Collectors.toList());

		map.put("advice", allList.stream().filter(c -> c.getAdviceFlag() != null).collect(Collectors.toList()));
		map.put("adviceNull", allList.stream().filter(c -> c.getAdviceFlag() == null).collect(Collectors.toList()));
		return "ams/flightplan/aocThirdParty";
	}

	//消息审核页面(菜单页)
	@RequiresPermissions("ams:flightDynamicManager:aocThirdParty")
	@RequestMapping("aocThirdPartyList")
	public String aocThirdPartyList(ModelMap map) {
		List<AocThirdParty> allList = aocThirdPartyService.findAllList(new AocThirdParty()).stream().filter(aocThirdParty ->
				UserUtils.dataScopeFilterAocThirdParty(aocThirdParty.getFlightCompanyCode())
		).collect(Collectors.toList());

		map.put("advice", allList.stream().filter(c -> c.getAdviceFlag() != null).collect(Collectors.toList()));
		map.put("adviceNull", allList.stream().filter(c -> c.getAdviceFlag() == null).collect(Collectors.toList()));
		return "ams/flightplan/aocThirdParty";
	}

	@RequiresPermissions("ams:flightDynamicManager:aocThirdParty")
	@RequestMapping("aocView")
	public String aocView() {
		return "ams/flightplan/aocView";
	}

	/**
	 * 处理审核方法
	 *
	 * @param ids
	 * @param adviceFlag
	 * @param message
	 * @return
	 */
	@RequiresPermissions("ams:flightDynamicManager:aocThirdParty")
	@RequestMapping("doAocThirdParty")
	@ResponseBody
	public Message doAocThirdParty(@RequestParam("ids[]") ArrayList<String> ids,
								   @RequestParam(value = "flightDynamicIds[]", required = false) ArrayList<String> flightDynamicIds,
								   @RequestParam(value = "descs[]", required = false) ArrayList<String> descs,
								   String adviceFlag, Message message) {
		if (!Collections3.isEmpty(ids) && StringUtils.isNotBlank(adviceFlag)) {
			aocThirdPartyService.updateList(ids, flightDynamicIds, descs, adviceFlag, UserUtils.getUser().getId(), message);
		}
		return message;
	}
}