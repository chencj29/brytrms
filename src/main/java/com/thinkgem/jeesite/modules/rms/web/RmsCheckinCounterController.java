/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import cn.net.metadata.wrapper.CarouselAxisWrapper;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.RmsCheckinCounter;
import com.thinkgem.jeesite.modules.rms.service.RmsCheckinCounterService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 值机柜台基础信息表Controller
 *
 * @author wjp
 * @version 2016-03-08
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/rmsCheckinCounter")
public class RmsCheckinCounterController extends BaseController {

    @Autowired
    private RmsCheckinCounterService rmsCheckinCounterService;

    @RequiresPermissions("rms:rmsCheckinCounter:view")
    @RequestMapping("view")
    public String view() {
        return "rms/rmsCheckinCounter/rmsCheckinCounter";
    }

    @RequiresPermissions("rms:rmsCheckinCounter:view")
    @RequestMapping("get")
    @ResponseBody
    public RmsCheckinCounter get(@RequestParam("id") String id) {
        return rmsCheckinCounterService.get(id);
    }

    @RequiresPermissions("rms:rmsCheckinCounter:view")
    @RequestMapping(value = {"list", ""})
    @ResponseBody
    public DataTablesPage list(Page<RmsCheckinCounter> page, DataTablesPage dataTablesPage, RmsCheckinCounter rmsCheckinCounter, HttpServletRequest request, HttpServletResponse response) {
        dataTablesPage.setColumns(request, page, rmsCheckinCounter);
        return rmsCheckinCounterService.findDataTablesPage(page, dataTablesPage, rmsCheckinCounter);
    }

    @RequiresPermissions("rms:rmsCheckinCounter:view")
    @RequestMapping(value = "form")
    public String form(RmsCheckinCounter rmsCheckinCounter, Model model) {
        model.addAttribute("rmsCheckinCounter", rmsCheckinCounter);
        return "modules/rms/rmsCheckinCounterForm";
    }

    @RequiresPermissions("rms:rmsCheckinCounter:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(RmsCheckinCounter rmsCheckinCounter, Model model, Message message) {
        if (!beanValidator(model, rmsCheckinCounter)) {
            message.setMessage("数据校验错误!");
        }
        try {
            rmsCheckinCounterService.save(rmsCheckinCounter);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rmsCheckinCounter:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(RmsCheckinCounter rmsCheckinCounter, Message message) {
        try {
            rmsCheckinCounterService.delete(rmsCheckinCounter);
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
        rmsCheckinCounterService.findList(new RmsCheckinCounter()).forEach(rmsCheckinCounter -> datas.add(new CarouselAxisWrapper(rmsCheckinCounter.getId(), rmsCheckinCounter.getCheckinCounterNum(), rmsCheckinCounter.getCheckinCounterTypeCode(), rmsCheckinCounter.getCheckinCounterNature())));
        return datas;
    }

    @RequestMapping("list_json_by_condition")
    @ResponseBody
    public List<String> listJsonByCondition(String nature) {
        return rmsCheckinCounterService.findList(new RmsCheckinCounter()).stream().filter(rmsCheckinCounter -> rmsCheckinCounter.getCheckinCounterNature().equals(nature) && rmsCheckinCounter.getCheckinCounterTypeCode().equals("1")).map(rmsCheckinCounter -> rmsCheckinCounter.getCheckinCounterNum()).collect(Collectors.toList());
    }
}