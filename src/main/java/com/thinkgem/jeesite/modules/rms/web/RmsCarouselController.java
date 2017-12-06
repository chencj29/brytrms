/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import cn.net.metadata.wrapper.CarouselAxisWrapper;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.StringSort;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.RmsCarousel;
import com.thinkgem.jeesite.modules.rms.service.RmsCarouselService;
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
 * 行李转盘基础信息表Controller
 *
 * @author wjp
 * @version 2016-03-19
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/rmsCarousel")
public class RmsCarouselController extends BaseController {

    @Autowired
    private RmsCarouselService rmsCarouselService;

    @RequiresPermissions("rms:rmsCarousel:view")
    @RequestMapping("view")
    public String view() {
        return "rms/rmsCarousel/rmsCarousel";
    }

    @RequiresPermissions("rms:rmsCarousel:view")
    @RequestMapping("get")
    @ResponseBody
    public RmsCarousel get(@RequestParam("id") String id) {
        return rmsCarouselService.get(id);
    }

    @RequiresPermissions("rms:rmsCarousel:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<RmsCarousel> list(RmsCarousel rmsCarousel) {
        return new DataGrid<>(rmsCarouselService.findList(rmsCarousel));
    }

    @RequiresPermissions("rms:rmsCarousel:view")
    @RequestMapping(value = "form")
    public String form(RmsCarousel rmsCarousel, Model model) {
        model.addAttribute("rmsCarousel", rmsCarousel);
        return "modules/rms/rmsCarouselForm";
    }

    @RequiresPermissions("rms:rmsCarousel:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(RmsCarousel rmsCarousel, Model model, Message message) {
        if (!beanValidator(model, rmsCarousel)) {
            message.setMessage("数据校验错误!");
        }
        rmsCarouselService.checkRedo(rmsCarousel, new String[]{"carouselNum"}, message);
        if (message.isSuccess()) return message;

        try {
            rmsCarouselService.save(rmsCarousel);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rmsCarousel:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(RmsCarousel rmsCarousel, Message message) {
        try {
            rmsCarouselService.delete(rmsCarousel);
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
        rmsCarouselService.findList(new RmsCarousel()).forEach(rmsCarousel -> datas.add(new CarouselAxisWrapper(rmsCarousel.getId(), rmsCarousel.getCarouselNum(), rmsCarousel.getCarouselTypeCode(), rmsCarousel.getCarouselNature())));
        return datas;
    }

    @RequestMapping("list_json_by_condition")
    @ResponseBody
    public List<String> listJsonByCondition(String nature) {
        return rmsCarouselService.findList(new RmsCarousel()).stream().filter(rmsCarousel -> rmsCarousel.getCarouselNature().equals(nature) && rmsCarousel.getCarouselTypeCode().equals("1")).map(entity -> entity.getCarouselNum()).sorted(new StringSort()).collect(Collectors.toList());
    }

}