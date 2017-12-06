/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.net.metadata.utils.DateTimeUtils;
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
import com.thinkgem.jeesite.modules.rms.entity.DutyHistory;
import com.thinkgem.jeesite.modules.rms.service.DutyHistoryService;

import java.util.ArrayList;
import java.util.List;

/**
 * 配载工作人员值班记录Controller
 * @author wjp
 * @version 2016-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/dutyHistory")
public class DutyHistoryController extends BaseController {

	@Autowired
	private DutyHistoryService dutyHistoryService;
	
	@RequiresPermissions("rms:dutyHistory:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/dutyHistory/dutyHistory";
	}

	@RequiresPermissions("rms:dutyHistory:view")
    @RequestMapping("get")
    @ResponseBody
    public DutyHistory get(@RequestParam("id") String id) {
        return dutyHistoryService.get(id);
    }

	/*@RequiresPermissions("rms:dutyHistory:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<DutyHistory> page, DataTablesPage dataTablesPage, DutyHistory dutyHistory, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, dutyHistory);
		return dutyHistoryService.findDataTablesPage(page, dataTablesPage, dutyHistory);
	}*/

	@RequiresPermissions("rms:dutyHistory:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<DutyHistory> list(DutyHistory dutyHistory) {
        return new DataGrid<>(dutyHistoryService.findList(dutyHistory));
    }

	@RequiresPermissions("rms:dutyHistory:view")
	@RequestMapping(value = "form")
	public String form(DutyHistory dutyHistory, Model model) {
		model.addAttribute("dutyHistory", dutyHistory);
		return "modules/rms/dutyHistoryForm";
	}

	@RequiresPermissions("rms:dutyHistory:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(DutyHistory dutyHistory, Model model, Message message) {
		if (!beanValidator(model, dutyHistory)){
			message.setMessage("数据校验错误!");
		}
		try {
			dutyHistoryService.save(dutyHistory);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:dutyHistory:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(DutyHistory dutyHistory, Message message) {
		try {
			dutyHistoryService.delete(dutyHistory);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:dockList:view")
	@RequestMapping("listStatistics")
	@ResponseBody
	public DataGrid<DutyHistory> listStatistics(DutyHistory dutyHistory) {
		if(StringUtils.isBlank(dutyHistory.getPostId()) && StringUtils.isBlank(dutyHistory.getDutyPersonal())
				&& dutyHistory.getDutyStartTime() ==null && dutyHistory.getDutyEndTime() == null) return new DataGrid<>(new ArrayList<>());
		List<DutyHistory> dutyHistoryList = dutyHistoryService.findListStatistics(dutyHistory);
		Long allCount = dutyHistoryList.stream()
				.filter(scheduling -> scheduling.getDutyStartTime() != null && scheduling.getDutyEndTime() != null)
				.mapToLong(scheduling -> {
					return DateTimeUtils.getHourDiff(scheduling.getDutyStartTime(), scheduling.getDutyEndTime());
				}).sum();

		if(dutyHistoryList.size()==0||!"<b>总计:</b>".equals(dutyHistoryList.get(dutyHistoryList.size()-1).getDutyPersonal())) {
			DutyHistory schedulingStatistics = new DutyHistory();
			schedulingStatistics.setDutyPersonal("<b>总计:</b>");
			schedulingStatistics.setDutyDept("<b>工作时间:" + allCount + "小时</b>");
			dutyHistoryList.add(schedulingStatistics);
		}
		return new DataGrid<>(dutyHistoryList);
	}
}