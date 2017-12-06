/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.SlideCoastOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.SlideCoastOccupyingInfoService;
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
 * 滑槽占用信息Controller
 *
 * @author xiaowu
 * @version 2016-04-12
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/slideCoastOccupyingInfo")
public class SlideCoastOccupyingInfoController extends BaseController {

    @Autowired
    private SlideCoastOccupyingInfoService slideCoastOccupyingInfoService;

    @RequiresPermissions("rms:slideCoastOccupyingInfo:view")
    @RequestMapping("view")
    public String view() {
        return "rms/slideCoastOccupyingInfo/slideCoastOccupyingInfo";
    }

    @RequiresPermissions("rms:slideCoastOccupyingInfo:view")
    @RequestMapping("get")
    @ResponseBody
    public SlideCoastOccupyingInfo get(@RequestParam("id") String id) {
        return slideCoastOccupyingInfoService.get(id);
    }

    @RequiresPermissions("rms:slideCoastOccupyingInfo:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<SlideCoastOccupyingInfo> list(SlideCoastOccupyingInfo slideCoastOccupyingInfo) {
        return new DataGrid<>(slideCoastOccupyingInfoService.findList(slideCoastOccupyingInfo));
    }

    @RequiresPermissions("rms:slideCoastOccupyingInfo:view")
    @RequestMapping(value = "form")
    public String form(SlideCoastOccupyingInfo slideCoastOccupyingInfo, Model model) {
        model.addAttribute("slideCoastOccupyingInfo", slideCoastOccupyingInfo);
        return "modules/rms/slideCoastOccupyingInfoForm";
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(SlideCoastOccupyingInfo slideCoastOccupyingInfo, Model model, Message message) {
        if (!beanValidator(model, slideCoastOccupyingInfo)) {
            message.setMessage("数据校验错误!");
        }
        try {
            slideCoastOccupyingInfoService.save(slideCoastOccupyingInfo);
            message.setCode(1);
            message.setResult(ImmutableMap.of("ocid", slideCoastOccupyingInfo.getId()));
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:slideCoastOccupyingInfo:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(SlideCoastOccupyingInfo slideCoastOccupyingInfo, Message message) {
        try {
            slideCoastOccupyingInfoService.delete(slideCoastOccupyingInfo);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequestMapping("fetchByFlightDynamicId")
    @ResponseBody
    public SlideCoastOccupyingInfo getOccupyingInfoByFlightDynamicId(String flightDynamicId) {
        return slideCoastOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
    }


    @RequestMapping("fetchOciDatas")
    @ResponseBody
    public List<SlideCoastOccupyingInfo> fetchDatas(@RequestParam("pairIds[]") List<String> pairIds) {
        return slideCoastOccupyingInfoService.fetchOciDatas(pairIds);
    }

}