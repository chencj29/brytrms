/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import cn.net.metadata.wrapper.CarouselAxisWrapper;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.StringSort;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.SlideCoast;
import com.thinkgem.jeesite.modules.rms.service.SlideCoastService;
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
 * 滑槽基础信息Controller
 *
 * @author wjp
 * @version 2016-03-19
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/slideCoast")
public class SlideCoastController extends BaseController {

    @Autowired
    private SlideCoastService slideCoastService;

    @RequiresPermissions("rms:slideCoast:view")
    @RequestMapping("view")
    public String view() {
        return "rms/slideCoast/slideCoast";
    }

    @RequiresPermissions("rms:slideCoast:view")
    @RequestMapping("get")
    @ResponseBody
    public SlideCoast get(@RequestParam("id") String id) {
        return slideCoastService.get(id);
    }

    @RequiresPermissions("rms:slideCoast:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<SlideCoast> list(SlideCoast slideCoast) {
        return new DataGrid<>(slideCoastService.findList(slideCoast));
    }

    @RequiresPermissions("rms:slideCoast:view")
    @RequestMapping(value = "form")
    public String form(SlideCoast slideCoast, Model model) {
        model.addAttribute("slideCoast", slideCoast);
        return "modules/rms/slideCoastForm";
    }

    @RequiresPermissions("rms:slideCoast:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(SlideCoast slideCoast, Model model, Message message) {
        if (!beanValidator(model, slideCoast)) {
            message.setMessage("数据校验错误!");
        }

        slideCoastService.checkRedo(slideCoast, new String[]{"slideCoastNum"}, message);
        if (message.isSuccess()) return message;

        try {
            slideCoastService.save(slideCoast);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:slideCoast:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(SlideCoast slideCoast, Message message) {
        try {
            slideCoastService.delete(slideCoast);
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
        slideCoastService.findList(new SlideCoast()).forEach(slideCoast -> datas.add(new CarouselAxisWrapper(slideCoast.getId(), slideCoast.getSlideCoastNum(), slideCoast.getSlideCoastStatus(), slideCoast.getSlideCoastNature())));
        return datas;
    }

    @RequestMapping("list_json_by_condition")
    @ResponseBody
    public List<String> listJsonByCondition(String nature) {
        return slideCoastService.findList(new SlideCoast()).stream().filter(slideCoast -> slideCoast.getSlideCoastNature().equals(nature) && slideCoast.getSlideCoastStatus().equals("1")).map(entity -> entity.getSlideCoastNum()).sorted(new StringSort()).collect(Collectors.toList());
    }

}