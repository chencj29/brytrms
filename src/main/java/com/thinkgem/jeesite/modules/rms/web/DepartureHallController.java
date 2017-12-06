/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import cn.net.metadata.wrapper.CarouselAxisWrapper;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.DepartureHall;
import com.thinkgem.jeesite.modules.rms.service.DepartureHallService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 候机厅基础信息Controller
 *
 * @author wjp
 * @version 2016-03-19
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/departureHall")
public class DepartureHallController extends BaseController {

    @Autowired
    private DepartureHallService departureHallService;

    @RequiresPermissions("rms:departureHall:view")
    @RequestMapping("view")
    public String view() {
        return "rms/departureHall/departureHall";
    }

    @RequiresPermissions("rms:departureHall:view")
    @RequestMapping("get")
    @ResponseBody
    public DepartureHall get(@RequestParam("id") String id) {
        return departureHallService.get(id);
    }

    @RequiresPermissions("rms:departureHall:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<DepartureHall> list(DepartureHall departureHall) {
        return new DataGrid<>(departureHallService.findList(departureHall));
    }

    @RequiresPermissions("rms:departureHall:view")
    @RequestMapping(value = "form")
    public String form(DepartureHall departureHall, Model model) {
        model.addAttribute("departureHall", departureHall);
        return "modules/rms/departureHallForm";
    }

    @RequiresPermissions("rms:departureHall:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(DepartureHall departureHall, Model model, Message message) {
        if (!beanValidator(model, departureHall)) {
            message.setMessage("数据校验错误!");
        }

        departureHallService.checkRedo(departureHall, new String[]{"departureHallNum"}, message);
        if (message.isSuccess()) return message;

        try {
            departureHallService.save(departureHall);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:departureHall:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(DepartureHall departureHall, Message message) {
        try {
            departureHallService.delete(departureHall);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequestMapping("listJson")
    @ResponseBody
    public List<CarouselAxisWrapper> listJson() {
        List<CarouselAxisWrapper> datas = Lists.newArrayList();
        departureHallService.findList(new DepartureHall()).forEach(departureHall -> datas.add(new CarouselAxisWrapper(departureHall.getId(), departureHall.getDepartureHallNum(), departureHall.getDepartureHallStatus(), departureHall.getDepartureHallNature())));
        return datas;
    }

    @RequestMapping("list_json_by_condition")
    @ResponseBody
    public List<String> listJsonByCondition(String nature) {
        return departureHallService.findList(new DepartureHall()).stream().filter(departureHall -> departureHall.getDepartureHallNature().equals(nature) && departureHall.getDepartureHallStatus().equals("1")).map(entity -> entity.getDepartureHallNum()).collect(Collectors.toList());
    }

}