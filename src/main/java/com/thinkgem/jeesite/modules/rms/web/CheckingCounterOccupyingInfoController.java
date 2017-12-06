/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.CheckingCounterOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.CheckingCounterOccupyingInfoService;
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
 * 值机柜台占用信息Controller
 *
 * @author xiaowu
 * @version 2016-04-13
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/checkingCounterOccupyingInfo")
public class CheckingCounterOccupyingInfoController extends BaseController {

    @Autowired
    private CheckingCounterOccupyingInfoService checkingCounterOccupyingInfoService;

    @RequiresPermissions("rms:checkingCounterOccupyingInfo:view")
    @RequestMapping("view")
    public String view() {
        return "rms/checkingCounterOccupyingInfo/checkingCounterOccupyingInfo";
    }

    @RequiresPermissions("rms:checkingCounterOccupyingInfo:view")
    @RequestMapping("get")
    @ResponseBody
    public CheckingCounterOccupyingInfo get(@RequestParam("id") String id) {
        return checkingCounterOccupyingInfoService.get(id);
    }


    @RequiresPermissions("rms:checkingCounterOccupyingInfo:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<CheckingCounterOccupyingInfo> list(CheckingCounterOccupyingInfo checkingCounterOccupyingInfo) {
        return new DataGrid<>(checkingCounterOccupyingInfoService.findList(checkingCounterOccupyingInfo));
    }

    @RequiresPermissions("rms:checkingCounterOccupyingInfo:view")
    @RequestMapping(value = "form")
    public String form(CheckingCounterOccupyingInfo checkingCounterOccupyingInfo, Model model) {
        model.addAttribute("checkingCounterOccupyingInfo", checkingCounterOccupyingInfo);
        return "modules/rms/checkingCounterOccupyingInfoForm";
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(CheckingCounterOccupyingInfo checkingCounterOccupyingInfo, Model model, Message message) {
        if (!beanValidator(model, checkingCounterOccupyingInfo)) {
            message.setMessage("数据校验错误!");
        }
        try {
            checkingCounterOccupyingInfoService.save(checkingCounterOccupyingInfo);
            message.setCode(1);
            message.setResult(ImmutableMap.of("ocid", checkingCounterOccupyingInfo.getId()));
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequestMapping("fetchOciDatas")
    @ResponseBody
    public List<CheckingCounterOccupyingInfo> fetchDatas(@RequestParam("pairIds[]") List<String> pairIds) {
        return checkingCounterOccupyingInfoService.fetchOciDatas(pairIds);
    }

    @RequiresPermissions("rms:checkingCounterOccupyingInfo:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(CheckingCounterOccupyingInfo checkingCounterOccupyingInfo, Message message) {
        try {
            checkingCounterOccupyingInfoService.delete(checkingCounterOccupyingInfo);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequestMapping("fetchByFlightDynamicId")
    @ResponseBody
    public CheckingCounterOccupyingInfo getOccupyingInfoByFlightDynamicId(String flightDynamicId) {
        return checkingCounterOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
    }

}