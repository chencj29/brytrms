/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.FlightProgressActInfo;
import com.thinkgem.jeesite.modules.rms.service.FlightProgressActInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 保障过程时间Controller
 *
 * @author xiaowu
 * @version 2016-04-25
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/flightProgressActInfo")
public class FlightProgressActInfoController extends BaseController {

    @Autowired
    private FlightProgressActInfoService flightProgressActInfoService;

    @RequiresPermissions("rms:flightProgressActInfo:view")
    @RequestMapping("view")
    public String view() {
        return "rms/flightProgressActInfo/flightProgressActInfo";
    }

    @RequiresPermissions("rms:flightProgressActInfo:view")
    @RequestMapping("get")
    @ResponseBody
    public FlightProgressActInfo get(@RequestParam("id") String id) {
        return flightProgressActInfoService.get(id);
    }

    @RequiresPermissions("rms:flightProgressActInfo:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<FlightProgressActInfo> list(FlightProgressActInfo flightProgressActInfo) {
        return new DataGrid<>(flightProgressActInfoService.findList(flightProgressActInfo));
    }

    @RequiresPermissions("rms:flightProgressActInfo:view")
    @RequestMapping(value = "form")
    public String form(FlightProgressActInfo flightProgressActInfo, Model model) {
        model.addAttribute("flightProgressActInfo", flightProgressActInfo);
        return "modules/rms/flightProgressActInfoForm";
    }

    @RequiresPermissions("rms:flightProgressActInfo:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(FlightProgressActInfo flightProgressActInfo, Model model, Message message) {
        if (!beanValidator(model, flightProgressActInfo)) {
            message.setMessage("数据校验错误!");
        }
        try {
            flightProgressActInfoService.save(flightProgressActInfo);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:flightProgressActInfo:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(FlightProgressActInfo flightProgressActInfo, Message message) {
        try {
            flightProgressActInfoService.delete(flightProgressActInfo);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequestMapping("set-progress-act-time")
    @ResponseBody
    public Message setProgressActualTime(FlightProgressActInfo flightProgressActInfo, Integer progressIndex, Message message) {

        try {
            flightProgressActInfoService.setProgressActualTime(flightProgressActInfo, progressIndex);
            message.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage(e.getMessage());
        }

        return message;
    }

}