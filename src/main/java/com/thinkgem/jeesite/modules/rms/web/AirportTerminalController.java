/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.AirportTerminal;
import com.thinkgem.jeesite.modules.rms.service.AirportTerminalService;
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
 * 航站楼信息Controller
 *
 * @author hxx
 * @version 2016-01-26
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/airportTerminal")
public class AirportTerminalController extends BaseController {

    @Autowired
    private AirportTerminalService airportTerminalService;

    @RequiresPermissions("rms:airportTerminal:view")
    @RequestMapping("get")
    @ResponseBody
    public AirportTerminal get(@RequestParam("id") String id) {
        return airportTerminalService.get(id);
    }

    @RequiresPermissions("rms:airportTerminal:view")
    @RequestMapping("view")
    public String view() {
        return "rms/airportTerminal/airportTerminal";
    }

    /*@RequiresPermissions("rms:airportTerminal:view")
    @RequestMapping(value = {"list", ""})
    @ResponseBody
    public DataTablesPage list(Page<AirportTerminal> page, DataTablesPage dataTablesPage, AirportTerminal airportTerminal, HttpServletRequest request, HttpServletResponse response, Model model) {
        dataTablesPage.setColumns(request, page, airportTerminal);
        return airportTerminalService.findDataTablesPage(page, dataTablesPage, airportTerminal);
    }*/

    @RequiresPermissions("rms:airportTerminal:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<AirportTerminal> list(AirportTerminal flightCompanyInfo) {
        return new DataGrid<>(airportTerminalService.findList(flightCompanyInfo));
    }

    @RequiresPermissions("rms:airportTerminal:view")
    @RequestMapping(value = "form")
    public String form(AirportTerminal airportTerminal, Model model) {
        model.addAttribute("airportTerminal", airportTerminal);
        return "modules/rms/airportTerminalForm";
    }

    @RequiresPermissions("rms:airportTerminal:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(AirportTerminal airportTerminal, Model model, Message message) {
        if (!beanValidator(model, airportTerminal)) {
            message.setMessage("数据校验出错！");
        }

        airportTerminalService.checkRedo(airportTerminal,new String[]{"terminalName"},message);
        if(message.isSuccess()) return message;

        try {
            airportTerminalService.save(airportTerminal);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:airportTerminal:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(AirportTerminal airportTerminal, Message message) {
        try {
            airportTerminalService.delete(airportTerminal);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequestMapping("/jsonData")
    @ResponseBody
    public List<AirportTerminal> getAll4Ajax() {
        return airportTerminalService.findList(new AirportTerminal());
    }
}