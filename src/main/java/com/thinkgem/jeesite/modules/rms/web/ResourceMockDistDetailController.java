/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistDetail;
import com.thinkgem.jeesite.modules.rms.service.ResourceMockDistDetailService;
import com.thinkgem.jeesite.modules.rms.service.ResourceMockDistInfoService;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 资源模拟分配详情Controller
 *
 * @author BigBrother5
 * @version 2017-03-07
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/resourceMockDistDetail")
public class ResourceMockDistDetailController extends BaseController {

    @Autowired
    private ResourceMockDistDetailService resourceMockDistDetailService;
    @Autowired
    ResourceMockDistInfoService resourceMockDistInfoService;

    @RequiresPermissions("rms:resourceMockDistDetail:view")
    @RequestMapping("view")
    public String view() {
        return "rms/resourceMockDistDetail/resourceMockDistDetail";
    }

    @RequiresPermissions("rms:resourceMockDistDetail:view")
    @RequestMapping("get")
    @ResponseBody
    public ResourceMockDistDetail get(@RequestParam("id") String id) {
        return resourceMockDistDetailService.get(id);
    }

    @RequiresPermissions("rms:resourceMockDistDetail:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<ResourceMockDistDetail> list(ResourceMockDistDetail resourceMockDistDetail) {
        return new DataGrid<>(resourceMockDistDetailService.findList(resourceMockDistDetail));
    }

    @RequiresPermissions("rms:resourceMockDistDetail:view")
    @RequestMapping(value = "form")
    public String form(ResourceMockDistDetail resourceMockDistDetail, Model model) {
        model.addAttribute("resourceMockDistDetail", resourceMockDistDetail);
        return "modules/rms/resourceMockDistDetailForm";
    }

    @RequiresPermissions("rms:resourceMockDistDetail:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(ResourceMockDistDetail resourceMockDistDetail, Model model, Message message) {
        if (!beanValidator(model, resourceMockDistDetail)) {
            message.setMessage("数据校验错误!");
        }
        try {
            resourceMockDistDetailService.save(resourceMockDistDetail);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(ResourceMockDistDetail resourceMockDistDetail, Message message) {
        try {
            resourceMockDistDetailService.delete(resourceMockDistDetail);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }


    @RequestMapping(value = "checkData")
    @ResponseBody
    public Message checkData(String infoId, String resourceType, HttpServletRequest request) {
        Message message = new Message(0, "获取预览数据失败！");
        if (StringUtils.isBlank(infoId) || null == resourceMockDistInfoService.get(infoId)) {
            message.setMessage("不存在的模拟分配信息编号！");
            LogUtils.saveLog(request, message.getMessage());
            return message;
        }


        Long detailDataCount = resourceMockDistDetailService.fetchDetailCount(ImmutableMap.of("infoId", infoId, "resourceType", resourceType));
        if (detailDataCount == 0L) {
            message.setMessage("没有详情分配数据！");
            LogUtils.saveLog(request, message.getMessage());
            return message;
        }

        message.setCode(1);
        message.setMessage("获取预览数据成功");
        return message;
    }

    @RequestMapping(value = "preview")
    @ResponseBody
    public DataGrid<ResourceMockDistDetail> preview(String infoId, String resourceType, HttpServletRequest request) {
        return new DataGrid<>(resourceMockDistDetailService.findByInfoId(ImmutableMap.of("infoId", infoId, "resourceType", resourceType)));
    }
}