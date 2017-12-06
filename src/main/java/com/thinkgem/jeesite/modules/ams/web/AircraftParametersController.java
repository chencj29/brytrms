/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.web;


import cn.net.metadata.wrapper.KeyValuePairsWapper;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.AircraftParameters;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.AircraftParametersService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

/**
 * 机型参数管理Controller
 *
 * @author xiaopo
 * @version 2015-12-14
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/aircraftParameters")
public class AircraftParametersController extends BaseController {

    @Autowired
    private AircraftParametersService aircraftParametersService;

    @RequiresPermissions("ams:aircraftParameters:view")
    @RequestMapping("get")
    @ResponseBody
    public AircraftParameters get(@RequestParam("id") String id) {
        return aircraftParametersService.get(id);
    }

    @RequiresPermissions("ams:aircraftParameters:view")
    @RequestMapping(value = "form")
    public String form(AircraftParameters aircraftParameters, Model model) {
        model.addAttribute("aircraftParameters", aircraftParameters);
        return "modules/ams/aircraftParametersForm";
    }

    @RequiresPermissions("ams:aircraftParameters:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(AircraftParameters aircraftParameters, Model model, Message message) {
        if (!beanValidator(model, aircraftParameters)) {
            message.setMessage("数据校验错误!");
        }

        aircraftParametersService.checkRedo(aircraftParameters,new String[]{"aircraftModel"},message);
        if(message.isSuccess()) return message;

        try {
            aircraftParametersService.save(aircraftParameters);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("ams:aircraftParameters:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(AircraftParameters aircraftParameters, Message message) {
        try {
            aircraftParametersService.delete(aircraftParameters);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("ams:aircraftParameters:view")
    @RequestMapping("view")
    public String view(){
        return "ams/aircraft/aircraftParameters";
    }

    @RequiresPermissions("ams:aircraftParameters:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<AircraftParameters> list(AircraftParameters aircraftParameters) {
        return new DataGrid<>(aircraftParametersService.findList(aircraftParameters));
    }

    @RequestMapping("listJson")
    @ResponseBody
    public List<KeyValuePairsWapper> listModels() {
        List<KeyValuePairsWapper> keyValuePairsWappers = new ArrayList<>();

        aircraftParametersService.groupByModel().forEach(str -> {
            keyValuePairsWappers.add(new KeyValuePairsWapper(str, str));
        });

        return keyValuePairsWappers;
    }

    @RequestMapping(value = "jsonData")
    @ResponseBody
    public List<AircraftParameters> getAll4Ajax(Message message) {
        List<AircraftParameters> list = aircraftParametersService.findList(new AircraftParameters());
        return list;
    }

}