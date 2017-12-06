/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.ArrivalGateOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.ArrivalGateOccupyingInfoService;
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
 * 到港门占用信息Controller
 *
 * @author bb5
 * @version 2016-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/arrivalGateOccupyingInfo")
public class ArrivalGateOccupyingInfoController extends BaseController {

    @Autowired
    private ArrivalGateOccupyingInfoService arrivalGateOccupyingInfoService;

    @RequiresPermissions("rms:arrivalGateOccupyingInfo:view")
    @RequestMapping("view")
    public String view() {
        return "rms/arrivalGateOccupyingInfo/arrivalGateOccupyingInfo";
    }

    @RequiresPermissions("rms:arrivalGateOccupyingInfo:view")
    @RequestMapping("get")
    @ResponseBody
    public ArrivalGateOccupyingInfo get(@RequestParam("id") String id) {
        return arrivalGateOccupyingInfoService.get(id);
    }

    @RequiresPermissions("rms:arrivalGateOccupyingInfo:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<ArrivalGateOccupyingInfo> list(ArrivalGateOccupyingInfo arrivalGateOccupyingInfo) {
        return new DataGrid<>(arrivalGateOccupyingInfoService.findList(arrivalGateOccupyingInfo));
    }

    @RequiresPermissions("rms:arrivalGateOccupyingInfo:view")
    @RequestMapping(value = "form")
    public String form(ArrivalGateOccupyingInfo arrivalGateOccupyingInfo, Model model) {
        model.addAttribute("arrivalGateOccupyingInfo", arrivalGateOccupyingInfo);
        return "modules/rms/arrivalGateOccupyingInfoForm";
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(ArrivalGateOccupyingInfo arrivalGateOccupyingInfo, Model model, Message message) {
        if (!beanValidator(model, arrivalGateOccupyingInfo)) {
            message.setMessage("数据校验错误!");
        }
        try {
            arrivalGateOccupyingInfoService.save(arrivalGateOccupyingInfo);
            message.setResult(ImmutableMap.of("ocid", arrivalGateOccupyingInfo.getId()));
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:arrivalGateOccupyingInfo:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(ArrivalGateOccupyingInfo arrivalGateOccupyingInfo, Message message) {
        try {
            arrivalGateOccupyingInfoService.delete(arrivalGateOccupyingInfo);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequestMapping("fetchByFlightDynamicId")
    @ResponseBody
    public ArrivalGateOccupyingInfo getOccupyingInfoByFlightDynamicId(String flightDynamicId) {
        return arrivalGateOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
    }


    @RequestMapping("fetchOciDatas")
    @ResponseBody
    public List<ArrivalGateOccupyingInfo> fetchDatas(@RequestParam("pairIds[]") List<String> pairIds) {
        return arrivalGateOccupyingInfoService.fetchOciDatas(pairIds);
    }

}