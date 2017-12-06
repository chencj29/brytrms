/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.FlightPlan;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.FlightPlanDetailService;
import com.thinkgem.jeesite.modules.ams.service.FlightPlanService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 航班计划管理Controller
 *
 * @author xiaopo
 * @version 2015-12-24
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/flightplan")
public class FlightPlanController extends BaseController {

    @Autowired
    private FlightPlanService flightPlanService;

    @Autowired
    private FlightPlanDetailService flightPlanDetailService;

    @ModelAttribute
    public FlightPlan get(@RequestParam(required = false) String id) {
        FlightPlan entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = flightPlanService.get(id);
        }
        if (entity == null) {
            entity = new FlightPlan();
        }
        return entity;
    }

    /**
     * 航班计划浏览
     * @param message
     * @return
     */
    @RequiresPermissions("ams:flightPlan:view")
    @RequestMapping(value = {"view", ""})
    public String view(Message message,Model model) {
        // 获取本站代码并存储
        model.addAttribute("baseSiteCode", flightPlanService.getBaseSiteCode());
        return "ams/flightplan/flightPlan";
    }

    /**
     * 航班计划浏览
     * @param page
     * @param dataTablesPage
     * @param flightPlan
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("ams:flightPlan:view")
    @RequestMapping(value = {"list", ""})
    @ResponseBody
    public DataTablesPage<FlightPlan> list(Page<FlightPlan> page, DataTablesPage<FlightPlan> dataTablesPage, FlightPlan flightPlan, HttpServletRequest request, HttpServletResponse response, Model model) {
        dataTablesPage = flightPlanService.findDataTablesPage(page, dataTablesPage, flightPlan);
        return dataTablesPage;
    }

    @RequiresPermissions("ams:flightPlan:view")
    @RequestMapping(value = "form")
    public String form(FlightPlan flightPlan, Model model) {
        model.addAttribute("flightPlan", flightPlan);
        return "modules/ams/flightPlanForm";
    }

    /**
     * 航班计划初始
     * @param flightPlan
     * @param model
     * @param message
     * @return
     */
    @RequiresPermissions("ams:flightPlan:init")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(FlightPlan flightPlan, Model model, Message message) {
        if (!beanValidator(model, flightPlan)) {
            message.setMessage("数据校验失败!");
        }
        try {
            flightPlanService.save(flightPlan);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    /**
     * 手工录入初始化
     * @param flightPlan
     * @param model
     * @param message
     * @return
     */
    @RequiresPermissions("ams:flightPlan:init")
    @RequestMapping(value = "init")
    @ResponseBody
    public Message initFlightPlan(FlightPlan flightPlan, Model model, Message message) {
        flightPlan.setStatus(0);
        flightPlan.setInitTime(new Date());
        flightPlan.setIschanged(0);

        if (!beanValidator(model, flightPlan)) {
            message.setMessage("数据校验失败!");
        }
        try {
            flightPlanService.save(flightPlan);
            message.setCode(1);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("id", flightPlan.getId());
            message.setResult(result);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    /**
     * @param @param  flightPlan
     * @param @param  model
     * @param @param  message
     * @param @return
     * @return Message
     * @throws
     * @Title: initFromLongPlan
     * @Description: 从长期计划初始化航班计划
     * @author: chencheng
     * @date： 2016年1月13日 下午9:56:40
     */
    @RequiresPermissions("ams:flightPlan:init")
    @RequestMapping(value = "initFromLongPlan")
    @ResponseBody
    public Message initFromLongPlan(FlightPlan flightPlan, Model model, Message message) {
        flightPlan.setStatus(0);
        flightPlan.setInitTime(new Date());
        flightPlan.setIschanged(0);

        if (!beanValidator(model, flightPlan)) {
            message.setMessage("数据校验失败!");
        }
        try {
            flightPlanService.saveFlightPlanFromLongPlan(flightPlan);
            message.setCode(1);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("id", flightPlan.getId());
            message.setResult(result);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    /**
     * 航班计划删除
     * @param flightPlan
     * @param message
     * @return
     */
    @RequiresPermissions("ams:flightPlan:delete")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(FlightPlan flightPlan, Message message) {
        try {
            flightPlanService.delete(flightPlan);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }


    /**
     * step1
     **/
    @RequiresPermissions("ams:flightPlan:approve")
    @RequestMapping(value = "step{wizard}")
    public String checkStep(@PathVariable int wizard, ModelMap modelMap) {
        /*if(wizard == 3){
            flightPlanDetailService.findInOutMulti();
        }*/
        return "ams/flightplan/wizard/step" + wizard;
    }




    /**
     * 航班计划审核
     *
     * @param flightPlan
     * @param message
     * @return
     */
    @RequiresPermissions("ams:flightPlan:approve")
    @RequestMapping(value = "audit")
    @ResponseBody
    public Message audit(FlightPlan flightPlan, Message message) {
        try {
            flightPlan.setStatus(1);
            flightPlanService.audit(flightPlan);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    /**
     * 航班计划发布
     *
     * @param flightPlan
     * @param message
     * @return
     */
    @RequiresPermissions("ams:flightPlan:publish")
    @RequestMapping(value = "publish")
    @ResponseBody
    public Message publish(FlightPlan flightPlan, Message message) {
        try {
            flightPlanService.publish(flightPlan);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    /**
     * @param @return
     * @return Message
     * @throws
     * @Title: changeApprove
     * @Description: 航班计划变更审核
     * @author: chencheng
     * @date： 2016年1月23日 上午9:34:41
     */
    @RequiresPermissions("ams:flightPlan:approve")
    @RequestMapping(value = "changeApprove")
    @ResponseBody
    public Message changeApprove(FlightPlan flightPlan, Message message) {
        try {
            flightPlanService.changeApprove(flightPlan);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    /**
     * @param @param  flightPlan
     * @param @param  message
     * @param @return
     * @return Message
     * @throws
     * @Title: vaildPlanDate
     * @Description: 验证此计划日期是不是已经存在
     * @author: chencheng
     * @date： 2016年1月25日 下午1:04:22
     */
    @RequiresPermissions("ams:flightPlan:init")
    @RequestMapping(value = "vaildPlanDate")
    @ResponseBody
    public Message vaildPlanDate(FlightPlan flightPlan, Message message) {
        try {
            message = flightPlanService.vaildPlanDate(flightPlan);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }


    /**
     * @param aircraftNum
     * @param message
     * @return
     * @Description: 通过飞行器的机号获取机形
     * @author: dingshuang
     * @date： 2016年3月2日 下午15:11:12
     */
    @RequiresPermissions("ams:getAircraftTypeCodeByNum:init")
    @RequestMapping(value = "getAircraftTypeCodeByNum")
    @ResponseBody
    public Message getAircraftTypeCodeByNum(String aircraftNum, Message message) {
        try {
            message = flightPlanService.getAircraftTypeCodeByNum(aircraftNum);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }



    /**
     * 重新初始化航班计划，其实后台的操作是直接删除了
     *
     * @return
     */
    /*@RequiresPermissions("ams:flightPlan:edit")
    @RequestMapping(value = "reinit")
    @ResponseBody
    public Message reinit(FlightPlan flightPlan, Message message) {
        try {
            message = flightPlanService.deleteByPlanDate(flightPlan);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }*/
}
