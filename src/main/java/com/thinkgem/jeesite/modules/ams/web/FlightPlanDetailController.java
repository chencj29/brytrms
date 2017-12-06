/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.ams.dao.FlightPlanDetailDao;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.FlightPlan;
import com.thinkgem.jeesite.modules.ams.entity.FlightPlanDetail;
import com.thinkgem.jeesite.modules.ams.entity.FlightPlanDetailChange;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.FlightPlanDetailService;
import com.thinkgem.jeesite.modules.ams.service.FlightPlanService;

/**
 * 航班详情管理Controller
 *
 * @author xiaopo
 * @version 2015-12-30
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/flightPlanDetail")
public class FlightPlanDetailController extends BaseController {

    private static Logger log = Logger.getLogger(FlightPlanDetailController.class);

    @Autowired
    private FlightPlanDetailService flightPlanDetailService;

    @Autowired
    private FlightPlanService flightPlanService;

    @RequiresPermissions("ams:flightPlan:edit")
    @RequestMapping(value = {"get"})
    @ResponseBody
    public FlightPlanDetail get(FlightPlanDetail flightPlanDetail) {
        FlightPlanDetail entity = flightPlanDetailService.get(flightPlanDetail);
        return entity;
    }

    @RequiresPermissions("ams:flightPlan:edit")
    @RequestMapping(value = {"preEdit"})
    public ModelAndView preEdit(FlightPlanDetail flightPlanDetail, ModelAndView modelAndView) {
        FlightPlanDetail entity = flightPlanDetailService.get(flightPlanDetail);
        modelAndView.addObject("plan", entity);
        modelAndView.setViewName("ams/flightplan/editFlightPlanDetail");
        return modelAndView;
    }

    /**
     * 航班计划详情查询
     *
     * @param page
     * @param dataTablesPage
     * @param flightPlanDetail
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("ams:flightPlan:view")
    @RequestMapping(value = {"list", ""})
    @ResponseBody
    public DataTablesPage<FlightPlanDetail> detailList(Page<FlightPlanDetail> page, DataTablesPage<FlightPlanDetail> dataTablesPage,
                                                       FlightPlanDetail flightPlanDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
        dataTablesPage.setColumns(request, page, flightPlanDetail);
        dataTablesPage = flightPlanDetailService.findDataTablesPage(page, dataTablesPage, flightPlanDetail);
        return dataTablesPage;
    }

    @RequiresPermissions("ams:flightPlan:view")
    @RequestMapping(value = {"listByPlanDate", ""})
    @ResponseBody
    public DataTablesPage<FlightPlanDetail> listByPlanDate(Page<FlightPlanDetail> page, DataTablesPage<FlightPlanDetail> dataTablesPage,
                                                           FlightPlanDetail flightPlanDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
        dataTablesPage.setColumns(request, page, flightPlanDetail);
        dataTablesPage = flightPlanDetailService.findDataTablesPageByDate(page, dataTablesPage, flightPlanDetail);
        return dataTablesPage;
    }

    @RequiresPermissions("ams:flightPlanDetail:view")
    @RequestMapping(value = "form")
    public String form(FlightPlanDetail flightPlanDetail, Model model) {
        model.addAttribute("flightPlanDetail", flightPlanDetail);
        return "modules/ams/flightPlanDetailForm";
    }

    /**
     * 航班计划编辑
     *
     * @param flightPlanDetail
     * @param model
     * @param message
     * @return
     */
    @RequiresPermissions("ams:flightPlan:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(FlightPlanDetail flightPlanDetail, Model model, Message message) {
        if (!beanValidator(model, flightPlanDetail)) {
            message.setMessage("数据校验失败!");
        }
        try {
            // flightPlanDetail.setStatus(0);
            flightPlanDetailService.save(flightPlanDetail);
            message.setCode(1);
            if (flightPlanDetail.getStatus() < 1) {
                message.setMessage("保存成功");
            } else {
                message.setMessage("变更成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage(e.getMessage());
        }
        return message;
    }

    /**
     * 航班计划删除
     *
     * @param flightPlanDetail
     * @param message
     * @return
     */
    @RequiresPermissions("ams:flightPlan:delete")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(FlightPlanDetail flightPlanDetail, Message message) {
        try {
            flightPlanDetailService.delete(flightPlanDetail);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    /**
     * @param @param  flightPlanDetail
     * @param @param  model
     * @param @param  message
     * @param @return
     * @return Message
     * @throws
     * @Title: change
     * @Description: 保存变更，变更类型为新增或修改
     * @author: chencheng
     * @date： 2016年1月23日 上午10:11:19
     */
    @RequiresPermissions("ams:flightPlan:edit")
    @RequestMapping(value = "saveChange")
    @ResponseBody
    public Message change(FlightPlanDetail flightPlanDetail, Model model, Message message) {
        if (!beanValidator(model, flightPlanDetail)) {
            message.setMessage("数据校验失败!");
        }
        try {
            message = flightPlanDetailService.saveChange(flightPlanDetail);
            message.setCode(1);
            message.setMessage("变更成功");
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage(e.getMessage());
        }
        return message;
    }

    /**
     * @param @param  flightPlanDetail
     * @param @param  message
     * @param @return
     * @return Message
     * @throws
     * @Title: deleteChange
     * @Description: 保存变更，变更类型为删除
     * @author: chencheng
     * @date： 2016年1月23日 上午10:11:51
     */
    @RequiresPermissions("ams:flightPlan:edit")
    @RequestMapping(value = "deleteChange")
    @ResponseBody
    public Message deleteChange(FlightPlanDetail flightPlanDetail, Message message) {
        try {
            message = flightPlanDetailService.deleteChange(flightPlanDetail);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    /**
     * 展示变更审核确认页面
     *
     * @param flightPlanDetailChange
     * @return
     */
    @RequiresPermissions("ams:flightPlan:approve")
    @RequestMapping(value = "preApprove")
    public ModelAndView preApprove(FlightPlanDetailChange flightPlanDetailChange) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("ams/flightplan/changeApprove");
        FlightPlanDetail old = flightPlanDetailService.get(flightPlanDetailChange.getBaseId());
        FlightPlanDetailChange change = flightPlanDetailService.getFlightPlanDetailChangeById(flightPlanDetailChange.getId());
        mav.addObject("old", old);
        if (change.getChangeType().equals("3")) {
            mav.addObject("change", null);
        } else {
            mav.addObject("change", change);
        }
        String changeTypeStr = StringUtils.getChangeTypeByCode(change.getChangeType());
        mav.addObject("changeType", changeTypeStr);
        mav.addObject("planTime", change.getPlanDate());
        return mav;
    }

    @RequiresPermissions("ams:flightPlan:approve")
    @RequestMapping(value = "doApprove")
    @ResponseBody
    public void doApprove(String doit, FlightPlanDetailChange flightPlanDetailChange) {
        flightPlanDetailService.saveChangeDoit(flightPlanDetailChange, doit);
    }

    /**
     * 查询航班号为空的航班
     *
     * @return
     */
    @RequiresPermissions("ams:flightPlan:view")
    @RequestMapping(value = "listByNullFlightNo")
    @ResponseBody
    public DataTablesPage<FlightPlanDetail> findListByNullFlightNo(Page<FlightPlanDetail> page, DataTablesPage<FlightPlanDetail> dataTablesPage, FlightPlanDetail flightPlanDetail, HttpServletRequest request) {
        dataTablesPage.setColumns(request, page, flightPlanDetail);
        flightPlanDetailService.findListByNullFlightNo(page, dataTablesPage, flightPlanDetail);
        return dataTablesPage;
    }

    /**
     * 航班性质 航空公司 航班商务代理
     *
     * @return
     */
    @RequiresPermissions("ams:flightPlan:view")
    @RequestMapping(value = "listByNull")
    @ResponseBody
    public DataTablesPage<FlightPlanDetail> findListByNull(Page<FlightPlanDetail> page, DataTablesPage<FlightPlanDetail> dataTablesPage,
                                                           FlightPlanDetail flightPlanDetail, HttpServletRequest request) {
        dataTablesPage.setColumns(request, page, flightPlanDetail);
        flightPlanDetailService.findListByNull(page, dataTablesPage, flightPlanDetail);

        // List<Map> maps =
        // flightPlanDetailService.findInOutMulti("2016-01-07");
        return dataTablesPage;
    }


    @RequiresPermissions(("ams:flightPlan:view"))
    @RequestMapping(value = "findInOutMulti")
    @ResponseBody
    public List<Map> getInoutMulti(String flightPlanId) {
        FlightPlan flightPlan = flightPlanService.get(flightPlanId);

        LocalDateTime localDateTime = LocalDateTime.ofInstant(flightPlan.getPlanTime().toInstant(), TimeZone.getDefault().toZoneId());
        String dateStr = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<Map> maps = flightPlanDetailService.findInOutMulti(dateStr);
        return maps;
    }


    @RequiresPermissions("ams:flightPlan:edit")
    @RequestMapping(value = "setFlightNum")
    @ResponseBody
    public Message setFlightNum(Message message, @RequestParam("flightDetailIds[]") String[] flightDetailIds) {
        try {
            flightPlanDetailService.setFlightNum(flightDetailIds);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("ams:flightPlan:edit")
    @RequestMapping(value = "findListByInOut")
    @ResponseBody
    public DataTablesPage<FlightPlanDetail> findListByInOut(FlightPlanDetail flightPlanDetail) {
        return flightPlanDetailService.findListByInOut(flightPlanDetail);
    }

}