/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.FlightPairWrapper;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicHistoryService;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 航班动态初始化Controller
 *
 * @author xiaopo
 * @version 2016-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/flightDynamic")
public class FlightDynamicController extends BaseController {

    @Autowired
    private FlightDynamicService flightDynamicService;

    @Autowired
    private FlightDynamicHistoryService flightDynamicHistoryService;

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

    @RequiresPermissions("ams:flightDynamic:view")
    @RequestMapping(value = "get")
    @ResponseBody
    public FlightDynamic getEntity(FlightDynamic entity) {
        return entity;
    }

    @RequiresPermissions("ams:flightDynamic:view")
    @RequestMapping(value = "view")
    public String view() {
        return "ams/flightplan/flightDynamic";
    }

    @RequiresPermissions("ams:flightDynamic:view")
    @RequestMapping(value = {"list", ""})
    @ResponseBody
    public DataTablesPage<FlightDynamic> list(FlightDynamic flightDynamic, Page<FlightDynamic> page, DataTablesPage<FlightDynamic> dataTablesPage, HttpServletRequest request, HttpServletResponse response, Model model) {
        dataTablesPage = flightDynamicService.findDataTablesPage(page, dataTablesPage, flightDynamic);
        return dataTablesPage;
    }

    @RequiresPermissions("ams:flightDynamic:view")
    @RequestMapping(value = "form")
    public String form(FlightDynamic flightDynamic, Model model) {
        model.addAttribute("flightDynamic", flightDynamic);
        return "modules/ams/flightDynamicForm";
    }

    @RequiresPermissions("ams:flightDynamic:edit")
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

    @RequiresPermissions("ams:flightDynamic:edit")
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


    @RequiresPermissions("ams:flightDynamic:init")
    @RequestMapping(value = "dynamicInit")
    @ResponseBody
    public Message dynamicInit(Date planDate, Message message) {
        try {
            // 初始化航班动态
            flightDynamicService.dynamicInit(planDate);
            message.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("ams:flightDynamic:edit")
    @RequestMapping(value = "saveFlightDynamic")
    @ResponseBody
    public Message saveFlightDynamic(FlightDynamic flightDynamic, Message message, Model model) {
        try {
            flightDynamicService.saveFlightDynamic(flightDynamic);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("ams:flightDynamic:edit")
    @RequestMapping("queryListByIds")
    @ResponseBody
    public List<FlightPairWrapper> queryListByIds(@RequestParam("ids[]") ArrayList<String> ids) {
        return flightDynamicService.queryListByIds(ids);
    }

    /**
     * 查询已完成的动态数据
     *
     * @param flightDynamic
     * @param modelAndView
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("ams:flightDynamic:edit")
    @RequestMapping("completedList")
    @ResponseBody
    public ModelAndView completedList(FlightDynamic flightDynamic, ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<FlightDynamic> page = new Page<FlightDynamic>(request, response);
        // page.setPageSize(15);
        if (flightDynamic.getPlanDate() == null) {
            flightDynamic.setPlanDate(new Date());
        }
        Page<FlightDynamic> completedList = flightDynamicService.queryCompletedFlightDynamic(page, flightDynamic);
        modelAndView.addObject("page", completedList);
        modelAndView.setViewName("ams/flightplan/completedDynamic");
        return modelAndView;
    }

    @RequiresPermissions("ams:flightDynamic:edit")
    @RequestMapping("chang2History")
    @ResponseBody
    public Message chang2History(@RequestParam String ids, Message message) {
        flightDynamicHistoryService.saveFromCompletedDynamic(ids);
        message.setCode(1);
        return message;
    }

    @RequiresPermissions("ams:flightDynamic:view")
    @RequestMapping(value = {"listData", ""})
    @ResponseBody
    public DataGrid<FlightDynamic> listData(FlightDynamic flightDynamic, Page<FlightDynamic> page, HttpServletRequest request, HttpServletResponse response, Model model) {
        return new DataGrid<>(flightDynamicService.findList(flightDynamic));
    }

    @RequestMapping("getAgents")
    @ResponseBody
    public List<Map<String, String>> getAgents() {
        return flightDynamicService.getAgents();
    }
}