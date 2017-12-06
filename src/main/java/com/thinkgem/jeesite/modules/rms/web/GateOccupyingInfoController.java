/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.GateOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.GateOccupyingInfoService;
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
 * 机位占用信息Controller
 *
 * @author xiaowu
 * @version 2016-03-16
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/gateOccupyingInfo")
public class GateOccupyingInfoController extends BaseController {

    @Autowired
    private GateOccupyingInfoService gateOccupyingInfoService;

    @RequiresPermissions("rms:gateOccupyingInfo:view")
    @RequestMapping("view")
    public String view() {
        return "rms/gateOccupyingInfo/gateOccupyingInfo";
    }

    @RequiresPermissions("rms:gateOccupyingInfo:view")
    @RequestMapping("get")
    @ResponseBody
    public GateOccupyingInfo get(@RequestParam("id") String id) {
        return gateOccupyingInfoService.get(id);
    }

    @RequiresPermissions("rms:gateOccupyingInfo:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<GateOccupyingInfo> list(GateOccupyingInfo gateOccupyingInfo) {
        return new DataGrid<>(gateOccupyingInfoService.findList(gateOccupyingInfo));
    }

    @RequiresPermissions("rms:gateOccupyingInfo:view")
    @RequestMapping(value = "form")
    public String form(GateOccupyingInfo gateOccupyingInfo, Model model) {
        model.addAttribute("gateOccupyingInfo", gateOccupyingInfo);
        return "modules/rms/gateOccupyingInfoForm";
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(GateOccupyingInfo gateOccupyingInfo, Model model, Message message) {
        if (!beanValidator(model, gateOccupyingInfo)) {
            message.setMessage("数据校验错误!");
        }
        try {
            gateOccupyingInfoService.save(gateOccupyingInfo);
            message.setCode(1);
            message.setResult(ImmutableMap.of("ocid", gateOccupyingInfo.getId()));
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequestMapping("fetchOciDatas")
    @ResponseBody
    public List<GateOccupyingInfo> fetchDatas(@RequestParam("pairIds[]") List<String> pairIds) {
        return gateOccupyingInfoService.fetchOciDatas(pairIds);
    }

    @RequiresPermissions("rms:gateOccupyingInfo:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(GateOccupyingInfo gateOccupyingInfo, Message message) {
        try {
            gateOccupyingInfoService.delete(gateOccupyingInfo);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequestMapping("fetchByFlightDynamicId")
    @ResponseBody
    public GateOccupyingInfo getOccupyingInfoByFlightDynamicId(String flightDynamicId) {
        return gateOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
    }

}