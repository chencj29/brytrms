/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.FlightParameters;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.FlightParametersService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 航班状态管理 Controller
 *
 * @author xiaopo
 * @version 2015-12-14
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/flightParameters")
public class FlightParametersController extends BaseController {

    @Autowired
    private FlightParametersService flightParametersService;

    @RequiresPermissions("ams:flightParameters:view")
    @RequestMapping("get")
    @ResponseBody
    public FlightParameters get(@RequestParam("id") String id) {
        return flightParametersService.get(id);
    }

    @RequiresPermissions("ams:flightParameters:view")
    @RequestMapping(value = {"view", ""})
    public String view() {
        return "ams/flightParameters/flightParameters";
    }

    @RequiresPermissions("ams:flightParameters:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<FlightParameters> list(FlightParameters flightParameters) {
        return new DataGrid<>(flightParametersService.findList(flightParameters));
    }

    @RequiresPermissions("ams:flightParameters:view")
    @RequestMapping(value = "form")
    public String form(FlightParameters flightParameters, Model model) {
        model.addAttribute("flightParameters", flightParameters);
        return "modules/ams/flightParametersForm";
    }

    @RequiresPermissions("ams:flightParameters:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(FlightParameters flightParameters, Model model, Message message) {
        if (!beanValidator(model, flightParameters)) {
            message.setMessage("数据校验失败!");
        }

        flightParametersService.checkRedo(flightParameters,new String[]{"statusNum"},message);
        if(message.isSuccess()) return message;

        try {
            flightParametersService.save(flightParameters);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("ams:flightParameters:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(FlightParameters flightParameters, Message message) {
        try {
            flightParametersService.delete(flightParameters);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }

        return message;
    }

    @RequiresPermissions("ams:flightParameters:edit")
    @RequestMapping(value = "getFlightParametersByRegex")
    @ResponseBody
    public List<FlightParameters> getFlightParametersByRegex(String regex) {
        List<FlightParameters> flightParameterses = null;
        flightParameterses = flightParametersService.getFlightParametersByRegex(regex);
        return flightParameterses;
    }

    @RequestMapping("listJsons4Type")
    @ResponseBody
    public List<FlightParameters> listJsons4FlightStatus(@RequestParam("type") String type) {
        return flightParametersService.listJsons4Type(type);
    }
}