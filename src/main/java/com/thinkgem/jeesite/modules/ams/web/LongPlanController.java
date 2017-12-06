/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.web;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.LongPlan;
import com.thinkgem.jeesite.modules.ams.entity.LongPlanVO;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.LongPlanService;

/**
 * 长期计划管理Controller
 *
 * @author xiaopo
 * @version 2015-12-22
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/longplan")
public class LongPlanController extends BaseController {

	private static Logger log = Logger.getLogger(LongPlanController.class);

	@Autowired
	private LongPlanService longPlanService;

	/*@ModelAttribute
	public LongPlan get(@RequestParam(required = false) String id) {
		LongPlan entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = longPlanService.get(id);
		}
		if (entity == null) {
			entity = new LongPlan();
		}
		return entity;
	}*/

	@RequiresPermissions("ams:longPlan:view")
	@RequestMapping(value = { "view", "" })
	public String view() {
		return "ams/flightplan/longplan";
	}

	@RequiresPermissions("ams:longPlan:view")
	@RequestMapping(value = { "list", "" })
	@ResponseBody
	public DataTablesPage<LongPlanVO> list(Page<LongPlan> page, DataTablesPage<LongPlan> dataTablesPage, LongPlan longPlan,
			HttpServletRequest request) {
		dataTablesPage.setColumns(request, page, longPlan);
		DataTablesPage<LongPlanVO> dataTablesVOPage = longPlanService.findDataTablesVOPage(page, dataTablesPage, longPlan);
		return dataTablesVOPage;
	}

	@RequiresPermissions("ams:longPlan:view")
	@RequestMapping(value = "form")
	public String form(LongPlan longPlan, Model model) {
		model.addAttribute("longPlan", longPlan);
		return "modules/ams/longPlanForm";
	}

	@RequiresPermissions("ams:longPlan:edit")
	@RequestMapping(value = "preEdit")
	public ModelAndView preEdit(String id) {
		ModelAndView mav = new ModelAndView();
		if (org.apache.commons.lang3.StringUtils.isNotBlank(id)) {
			mav.addObject("id", id);
			LongPlan longPlan = longPlanService.get(id);
			mav.addObject("longPlan", longPlan);
		}
		mav.setViewName("ams/flightplan/editLongPlan");
		return mav;
	}

	/*@RequiresPermissions("ams:longPlan:apply")
	@RequestMapping(value = "divide")
	@ResponseBody
	public Message divideLongPlan() {
		Message message = longPlanService.saveDividedLongPlan();
		return message;
	}*/

	/**
	 * 编辑
	 * @param longPlan
	 * @param model
	 * @param message
     * @return
     */
	@RequiresPermissions("ams:longPlan:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(LongPlan longPlan, Model model, Message message) {
		if (!beanValidator(model, longPlan)) {
			message.setMessage("数据校验失败!");
		}
		try {
			// 保存
			longPlanService.save(longPlan);
			// 保存完毕之后开始拆分工作
			longPlanService.saveDividedLongPlan();
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	/**
	 * 删除长期计划
	 * @param longPlan
	 * @param message
     * @return
     */
	@RequiresPermissions("ams:longPlan:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(LongPlan longPlan, Message message) {
		try {
			longPlanService.delete(longPlan);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	/**
	 * 长期计划复制
	 * @param longPlan
	 * @param message
     * @return
     */
	@RequiresPermissions("ams:longPlan:copy")
	@RequestMapping(value = "preCopy")
	@ResponseBody
	public Message preCopyLongPlan(LongPlan longPlan, Message message) {
		long count = longPlanService.countLongPlan(longPlan);
		if (count > 0) {
			message.setCode(1);
		} else {
			message.setCode(0);
		}
		return message;
	}

	@RequiresPermissions("ams:longPlan:copy")
	@RequestMapping(value = "doCopy")
	@ResponseBody
	public Message doCopyLongPlan(@RequestBody ArrayList<LongPlan> omg, Message message) {
		try {
			longPlanService.copyLongPlan(omg);
			message.setCode(1);
		} catch (Exception e) {
			message.setCode(0);
			message.setMessage(e.getMessage());
			log.error("复制长期计划失败...", e);
		}
		return message;
	}

}