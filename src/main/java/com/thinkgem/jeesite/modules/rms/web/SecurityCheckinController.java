/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import cn.net.metadata.wrapper.CarouselAxisWrapper;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.StringSort;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.SecurityCheckin;
import com.thinkgem.jeesite.modules.rms.service.SecurityCheckinService;
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
 * 安检口基本信息Controller
 *
 * @author wjp
 * @version 2016-03-09
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/securityCheckin")
public class SecurityCheckinController extends BaseController {

    @Autowired
    private SecurityCheckinService securityCheckinService;

    @RequiresPermissions("rms:securityCheckin:view")
    @RequestMapping("view")
    public String view() {
        return "rms/securityCheckin/securityCheckin";
    }

    @RequiresPermissions("rms:securityCheckin:view")
    @RequestMapping("get")
    @ResponseBody
    public SecurityCheckin get(@RequestParam("id") String id) {
        return securityCheckinService.get(id);
    }

    @RequiresPermissions("rms:securityCheckin:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<SecurityCheckin> list(SecurityCheckin securityCheckin) {
        return new DataGrid<>(securityCheckinService.findList(securityCheckin));
    }

    @RequiresPermissions("rms:securityCheckin:view")
    @RequestMapping(value = "form")
    public String form(SecurityCheckin securityCheckin, Model model) {
        model.addAttribute("securityCheckin", securityCheckin);
        return "modules/rms/securityCheckinForm";
    }

    @RequiresPermissions("rms:securityCheckin:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(SecurityCheckin securityCheckin, Model model, Message message) {
        if (!beanValidator(model, securityCheckin)) {
            message.setMessage("数据校验错误!");
        }

        securityCheckinService.checkRedo(securityCheckin, new String[]{"scecurityCheckinNum"}, message);
        if (message.isSuccess()) return message;

        try {
            securityCheckinService.save(securityCheckin);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:securityCheckin:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(SecurityCheckin securityCheckin, Message message) {
        try {
            securityCheckinService.delete(securityCheckin);
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
        securityCheckinService.findList(new SecurityCheckin()).forEach(securityCheckin -> datas.add(new CarouselAxisWrapper(securityCheckin.getId(), securityCheckin.getScecurityCheckinNum(), securityCheckin.getScecurityStatus(), securityCheckin.getScecurityCheckinNature())));
        return datas;
    }

    @RequestMapping("list_json_by_condition")
    @ResponseBody
    public List<String> listJsonByCondition(String nature) {
        return securityCheckinService.findList(new SecurityCheckin()).stream().filter(securityCheckin -> securityCheckin.getScecurityCheckinNature().equals(nature) && securityCheckin.getScecurityStatus().equals("1")).map(entity -> entity.getScecurityCheckinNum()).sorted(new StringSort()).collect(Collectors.toList());
    }


}