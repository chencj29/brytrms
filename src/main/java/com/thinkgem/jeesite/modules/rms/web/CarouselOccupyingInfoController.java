/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.CarouselOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.CarouselOccupyingInfoService;
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
 * 行李转盘占用信息Controller
 *
 * @author xiaowu
 * @version 2016-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/carouselOccupyingInfo")
public class CarouselOccupyingInfoController extends BaseController {

    @Autowired
    private CarouselOccupyingInfoService carouselOccupyingInfoService;

    @RequiresPermissions("rms:carouselOccupyingInfo:view")
    @RequestMapping("view")
    public String view() {
        return "rms/carouselOccupyingInfo/carouselOccupyingInfo";
    }

    @RequiresPermissions("rms:carouselOccupyingInfo:view")
    @RequestMapping("get")
    @ResponseBody
    public CarouselOccupyingInfo get(@RequestParam("id") String id) {
        return carouselOccupyingInfoService.get(id);
    }

    @RequiresPermissions("rms:carouselOccupyingInfo:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<CarouselOccupyingInfo> list(CarouselOccupyingInfo carouselOccupyingInfo) {
        return new DataGrid<>(carouselOccupyingInfoService.findList(carouselOccupyingInfo));
    }

    @RequiresPermissions("rms:carouselOccupyingInfo:view")
    @RequestMapping(value = "form")
    public String form(CarouselOccupyingInfo carouselOccupyingInfo, Model model) {
        model.addAttribute("carouselOccupyingInfo", carouselOccupyingInfo);
        return "modules/rms/carouselOccupyingInfoForm";
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(CarouselOccupyingInfo carouselOccupyingInfo, Model model, Message message) {
        if (!beanValidator(model, carouselOccupyingInfo)) {
            message.setMessage("数据校验错误!");
        }
        try {
            carouselOccupyingInfoService.save(carouselOccupyingInfo);
            message.setResult(ImmutableMap.of("ocid", carouselOccupyingInfo.getId()));
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:carouselOccupyingInfo:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(CarouselOccupyingInfo carouselOccupyingInfo, Message message) {
        try {
            carouselOccupyingInfoService.delete(carouselOccupyingInfo);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequestMapping("fetchByFlightDynamicId")
    @ResponseBody
    public CarouselOccupyingInfo getOccupyingInfoByFlightDynamicId(String flightDynamicId) {
        return carouselOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
    }

    @RequestMapping("fetchOciDatas")
    @ResponseBody
    public List<CarouselOccupyingInfo> fetchDatas(@RequestParam("pairIds[]") List<String> pairIds) {
        return carouselOccupyingInfoService.fetchOciDatas(pairIds);
    }

}