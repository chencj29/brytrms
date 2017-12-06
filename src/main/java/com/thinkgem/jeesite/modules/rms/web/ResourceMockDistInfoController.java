/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistDetail;
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistInfo;
import com.thinkgem.jeesite.modules.rms.service.FlightPlanPairService;
import com.thinkgem.jeesite.modules.rms.service.ResourceMockDistDetailService;
import com.thinkgem.jeesite.modules.rms.service.ResourceMockDistInfoService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 资源模拟分配Controller
 *
 * @author BigBrother5
 * @version 2017-03-09
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/resourceMockDistInfo")
public class ResourceMockDistInfoController extends BaseController {

    @Autowired
    private ResourceMockDistInfoService resourceMockDistInfoService;

    @Autowired
    ResourceMockDistDetailService resourceMockDistDetailService;

    @Autowired
    FlightPlanPairService flightPlanPairService;

    @Autowired
    FlightDynamicService flightDynamicService;

    @RequiresPermissions("rms:resourceMockDistInfo:view")
    @RequestMapping("view")
    public String view() {
        return "rms/resourceMockDistInfo/resourceMockDistInfo";
    }

    @RequiresPermissions("rms:resourceMockDistInfo:view")
    @RequestMapping("get")
    @ResponseBody
    public ResourceMockDistInfo get(@RequestParam("id") String id) {
        return resourceMockDistInfoService.get(id);
    }

    @RequiresPermissions("rms:resourceMockDistInfo:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<ResourceMockDistInfo> list(ResourceMockDistInfo resourceMockDistInfo) {
        return new DataGrid<>(resourceMockDistInfoService.findList(resourceMockDistInfo));
    }

    @RequiresPermissions("rms:resourceMockDistInfo:view")
    @RequestMapping(value = "form")
    public String form(ResourceMockDistInfo resourceMockDistInfo, Model model) {
        model.addAttribute("resourceMockDistInfo", resourceMockDistInfo);
        return "modules/rms/resourceMockDistInfoForm";
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(ResourceMockDistInfo resourceMockDistInfo, Model model, Message message) {
        if (!beanValidator(model, resourceMockDistInfo)) {
            message.setMessage("数据校验错误!");
        }
        try {
            resourceMockDistInfoService.save(resourceMockDistInfo);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(ResourceMockDistInfo resourceMockDistInfo, Message message) {
        try {
            resourceMockDistInfoService.delete(resourceMockDistInfo);
            resourceMockDistDetailService.deleteByInfoId(resourceMockDistInfo.getId());
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequestMapping("listByUser")
    @ResponseBody
    public List<ResourceMockDistInfo> list(String resourceType) {
        return resourceMockDistInfoService.listByUser(ImmutableMap.of("userId", UserUtils.getUser().getId(), "resourceType", resourceType));
    }

    @RequestMapping("publish")
    @ResponseBody
    public Message publish(String infoId, String resourceType, String random, HttpServletRequest request) {
        Message message = new Message(0);

        if (StringUtils.isBlank(infoId) || null == resourceMockDistInfoService.get(infoId)) {
            message.setMessage("模拟分配数据发布失败：不存在的模拟分配信息！");
            return message;
        }

        List<ResourceMockDistDetail> details = resourceMockDistDetailService.findByInfoId(ImmutableMap.of("infoId", infoId, "resourceType", resourceType));

        if (Collections3.isEmpty(details)) {
            message.setMessage("模拟分配数据发布失败：详情分配数据为空！");
            return message;
        }

        if (resourceType.equals("机位")) message = resourceMockDistInfoService.publishMockData4Gate(details, random);
        else if (resourceType.equals("登机口")) message = resourceMockDistInfoService.publishMockData4BoardingGate(details, random);
        else if (resourceType.equals("到港门")) message = resourceMockDistInfoService.publishMockData4ArrivalGate(details, random);
        else if (resourceType.equals("行李转盘")) message = resourceMockDistInfoService.publishMOckData4Carousel(details, random);
        else if (resourceType.equals("滑槽")) message = resourceMockDistInfoService.publishMockData4SlideCoast(details, random);
        else if (resourceType.equals("值机柜台")) message = resourceMockDistInfoService.publishMockData4CheckinCounter(details, random);
        else if (resourceType.equals("安检口")) message = resourceMockDistInfoService.publishMockData4SecurityCheckin(details, random);
        else if (resourceType.equals("候机厅")) message = resourceMockDistInfoService.publishMockData4DepartureHall(details, random);

        return message;
    }
}