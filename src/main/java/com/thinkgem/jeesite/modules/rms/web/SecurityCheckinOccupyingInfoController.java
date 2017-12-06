/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.SecurityCheckinOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.SecurityCheckinOccupyingInfoService;
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
 * 安检口占用信息Controller
 *
 * @author xiaowu
 * @version 2016-04-18
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/securityCheckinOccupyingInfo")
public class SecurityCheckinOccupyingInfoController extends BaseController {

    @Autowired
    private SecurityCheckinOccupyingInfoService securityCheckinOccupyingInfoService;

    @RequiresPermissions("rms:securityCheckinOccupyingInfo:view")
    @RequestMapping("view")
    public String view() {
        return "rms/securityCheckinOccupyingInfo/securityCheckinOccupyingInfo";
    }

    @RequiresPermissions("rms:securityCheckinOccupyingInfo:view")
    @RequestMapping("get")
    @ResponseBody
    public SecurityCheckinOccupyingInfo get(@RequestParam("id") String id) {
        return securityCheckinOccupyingInfoService.get(id);
    }

    @RequiresPermissions("rms:securityCheckinOccupyingInfo:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<SecurityCheckinOccupyingInfo> list(SecurityCheckinOccupyingInfo securityCheckinOccupyingInfo) {
        return new DataGrid<>(securityCheckinOccupyingInfoService.findList(securityCheckinOccupyingInfo));
    }

    @RequiresPermissions("rms:securityCheckinOccupyingInfo:view")
    @RequestMapping(value = "form")
    public String form(SecurityCheckinOccupyingInfo securityCheckinOccupyingInfo, Model model) {
        model.addAttribute("securityCheckinOccupyingInfo", securityCheckinOccupyingInfo);
        return "modules/rms/securityCheckinOccupyingInfoForm";
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(SecurityCheckinOccupyingInfo securityCheckinOccupyingInfo, Model model, Message message) {
        if (!beanValidator(model, securityCheckinOccupyingInfo)) {
            message.setMessage("数据校验错误!");
        }
        try {
            securityCheckinOccupyingInfoService.save(securityCheckinOccupyingInfo);
            message.setCode(1);
            message.setResult(ImmutableMap.of("ocid", securityCheckinOccupyingInfo.getId()));
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:securityCheckinOccupyingInfo:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(SecurityCheckinOccupyingInfo securityCheckinOccupyingInfo, Message message) {
        try {
            securityCheckinOccupyingInfoService.delete(securityCheckinOccupyingInfo);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequestMapping("fetchByFlightDynamicId")
    @ResponseBody
    public SecurityCheckinOccupyingInfo getOccupyingInfoByFlightDynamicId(String flightDynamicId) {
        return securityCheckinOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
    }

    @RequestMapping("fetchOciDatas")
    @ResponseBody
    public List<SecurityCheckinOccupyingInfo> fetchDatas(@RequestParam("pairIds[]") List<String> pairIds) {
        return securityCheckinOccupyingInfoService.fetchOciDatas(pairIds);
    }

}