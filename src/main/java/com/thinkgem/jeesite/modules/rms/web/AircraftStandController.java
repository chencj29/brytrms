/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import cn.net.metadata.wrapper.AircraftYAxisWrapper;
import cn.net.metadata.wrapper.KeyValuePairsWapper;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.entity.AircraftStand;
import com.thinkgem.jeesite.modules.rms.service.AircraftStandService;
import org.apache.commons.lang3.StringUtils;
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

/**
 * 机位基础信息Controller
 *
 * @author wjp
 * @version 2016-03-09
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/aircraftStand")
public class AircraftStandController extends BaseController {

    @Autowired
    private AircraftStandService aircraftStandService;

    @Autowired
    private FlightDynamicService flightDynamicService;

    @RequiresPermissions("rms:aircraftStand:view")
    @RequestMapping("view")
    public String view() {
        return "rms/aircraftStand/aircraftStand";
    }

    @RequiresPermissions("rms:aircraftStand:view")
    @RequestMapping("get")
    @ResponseBody
    public AircraftStand get(@RequestParam("id") String id) {
        return aircraftStandService.get(id);
    }

    @RequiresPermissions("rms:aircraftStand:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<AircraftStand> list(AircraftStand aircraftStand) {
        return new DataGrid<>(aircraftStandService.findList(aircraftStand));
    }

    @RequestMapping("listJson")
    @ResponseBody
    public List<AircraftYAxisWrapper> listJson() {
        List<AircraftYAxisWrapper> wrappers = new ArrayList<>();
        List<AircraftStand> aircraftStands = aircraftStandService.findList(new AircraftStand());

        aircraftStands.forEach(aircraftStand -> wrappers.add(new AircraftYAxisWrapper(aircraftStand.getId(), aircraftStand.getAircraftStandNum(), aircraftStand.getAvailable(), aircraftStand.getLeftAsNum(), aircraftStand.getRightAsNum(), StringUtils.isEmpty(aircraftStand.getLeftAsNum()) && StringUtils.isEmpty(aircraftStand.getRightAsNum()))));

        return wrappers;
    }

    @RequestMapping("groupByAircraftStand")
    @ResponseBody
    public List<KeyValuePairsWapper> groupByAircraftStand() {
        List<KeyValuePairsWapper> wrappers = new ArrayList<>();

        aircraftStandService.groupByAircraftStand().forEach(s -> {
            wrappers.add(new KeyValuePairsWapper(s, s));
        });

        return wrappers;
    }


    @RequiresPermissions("rms:aircraftStand:view")
    @RequestMapping(value = "form")
    public String form(AircraftStand aircraftStand, Model model) {
        model.addAttribute("aircraftStand", aircraftStand);
        return "modules/rms/aircraftStandForm";
    }

    @RequiresPermissions("rms:aircraftStand:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(AircraftStand aircraftStand, Model model, Message message) {
        if (!beanValidator(model, aircraftStand)) {
            message.setMessage("数据校验错误!");
        }
        aircraftStandService.checkRedo(aircraftStand,new String[]{"aircraftStandNum"},message);
        if(message.isSuccess()) return message;

        try {
            aircraftStandService.save(aircraftStand);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:aircraftStand:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(AircraftStand aircraftStand, Message message) {
        try {
            aircraftStandService.delete(aircraftStand);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequestMapping("/jsonData")
    @ResponseBody
    public List<AircraftStand> getAll4Ajax() {
        return aircraftStandService.findList(new AircraftStand());
    }
}
