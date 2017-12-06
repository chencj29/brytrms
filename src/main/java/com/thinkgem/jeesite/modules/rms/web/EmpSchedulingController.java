/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import cn.net.metadata.utils.DateTimeUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.EmpScheduling;
import com.thinkgem.jeesite.modules.rms.entity.WorkingTypeEnum;
import com.thinkgem.jeesite.modules.rms.service.EmpSchedulingService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工作人员排班管理Controller
 *
 * @author xiaopo
 * @version 2016-05-19
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/empScheduling")
public class EmpSchedulingController extends BaseController {

	@Autowired
	private EmpSchedulingService empSchedulingService;

	@RequiresPermissions("rms:empScheduling:view")
	@RequestMapping("view")
	public String view() {
		return "rms/empScheduling/empScheduling";
	}

	@RequiresPermissions("rms:empScheduling:view")
	@RequestMapping("get")
	@ResponseBody
	public EmpScheduling get(@RequestParam("id") String id) {
		return empSchedulingService.get(id);
	}

	/*@RequiresPermissions("rms:empScheduling:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<EmpScheduling> page, DataTablesPage dataTablesPage, EmpScheduling empScheduling, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, empScheduling);
		return empSchedulingService.findDataTablesPage(page, dataTablesPage, empScheduling);
	}*/

	@RequiresPermissions("rms:empScheduling:view")
	@RequestMapping("list")
	@ResponseBody
	public DataGrid<EmpScheduling> list(EmpScheduling empScheduling) {
		return new DataGrid<>(empSchedulingService.findList(empScheduling));
	}

	@RequiresPermissions("rms:empScheduling:view")
	@RequestMapping(value = "form")
	public String form(EmpScheduling empScheduling, Model model) {
		model.addAttribute("empScheduling", empScheduling);
		return "modules/rms/empSchedulingForm";
	}

	@RequiresPermissions("rms:empScheduling:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(EmpScheduling empScheduling, Model model, Message message) {
		if(!beanValidator(model, empScheduling)) {
			message.setMessage("数据校验错误!");
		}
		try {
			empSchedulingService.save(empScheduling);
			message.setCode(1);
		} catch(Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:empScheduling:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(EmpScheduling empScheduling, Message message) {
		try {
			empSchedulingService.delete(empScheduling);
			message.setCode(1);
		} catch(Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}


	@RequiresPermissions("rms:empScheduling:scheduling")
	@RequestMapping("/scheduling")
	@ResponseBody
	public Message scheduling(Date startDate, Date endDate, String postId, EmpScheduling scheduling, Message message) {
		try {
			empSchedulingService.scheduling(startDate, endDate, postId, scheduling);
			message.setCode(1);
		} catch(Exception e) {
			e.printStackTrace();
			message.setMessage(e.getMessage());
		}
		return message;
	}


	@RequiresPermissions("rms:empScheduling:scheduling")
	@RequestMapping("/getWorkingArea")
	@ResponseBody
	public List<Map<String, Object>> getWorkingArea(WorkingTypeEnum typeEnum) {
		List<Map<String, Object>> resultMap = new ArrayList<>();
		empSchedulingService.getWorkingArea(resultMap, typeEnum);
		return resultMap;
	}


	@RequiresPermissions("rms:empScheduling:scheduling")
	@RequestMapping("/schedulingHistory")
	@ResponseBody
	public Message schedulingHistory(Date historyStartTime, Date historyEndTime, Date startTime, Date endTime, Message message) {
		try {
			empSchedulingService.schedulingHistory(historyStartTime, historyEndTime, startTime, endTime);
			message.setCode(1);
		} catch(Exception e) {
			e.printStackTrace();

			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:empScheduling:view")
	@RequestMapping("listStatistics")
	@ResponseBody
	public DataGrid<EmpScheduling> listStatistics(EmpScheduling empScheduling) {
		if(StringUtils.isBlank(empScheduling.getPostId()) && StringUtils.isBlank(empScheduling.getEmpName())
				&& empScheduling.getActualStartTime() ==null && empScheduling.getActualEndTime() == null) return new DataGrid<>(new ArrayList<>());
		List<EmpScheduling> empSchedulingList = empSchedulingService.findListStatistics(empScheduling);
		Long allCount = empSchedulingList.stream()
				.filter(scheduling -> scheduling.getActualStartTime() != null && scheduling.getActualEndTime() != null)
				.mapToLong(scheduling -> {
					return DateTimeUtils.getHourDiff(scheduling.getActualStartTime(), scheduling.getActualEndTime());
				}).sum();

		if(empSchedulingList.size()==0 || !"总计:".equals(empSchedulingList.get(empSchedulingList.size()-1).getJobNum())) {
			EmpScheduling schedulingStatistics = new EmpScheduling();
			schedulingStatistics.setJobNum("总计:");
			schedulingStatistics.setEmpName("工作时间:" + allCount + "小时");
			empSchedulingList.add(schedulingStatistics);
		}
		return new DataGrid<>(empSchedulingList);
	}

}