/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.FlyTime;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.FlyTimeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 飞越时间管理Controller
 *
 * @author chrischen
 * @version 2016-02-04
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/flyTime")
public class FlyTimeController extends BaseController {

    @Autowired
    private FlyTimeService flyTimeService;

   /* @ModelAttribute
    public FlyTime get(@RequestParam(required = false) String id) {
        FlyTime entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = flyTimeService.get(id);
        }
        if (entity == null) {
            entity = new FlyTime();
        }
        return entity;
    }*/

    @RequiresPermissions("ams:flyTime:view")
    @RequestMapping("get")
    @ResponseBody
    public FlyTime get(@RequestParam("id") String id) {
        return flyTimeService.get(id);
    }
    /**
     * 进入飞越时间管理页面
     *
     * @return
     */
    @RequiresPermissions("ams:flyTime:view")
    @RequestMapping(value = {"view", ""})
    public String view() {
        return "ams/flyTime/flyTime";
    }

   /* @RequiresPermissions("ams:flyTime:view")
    @RequestMapping(value = {"list", ""})
    @ResponseBody
    public DataTablesPage<FlyTime> list(Page<FlyTime> page, DataTablesPage<FlyTime> dataTablesPage, FlyTime flyTime, HttpServletRequest request, HttpServletResponse response) {
        dataTablesPage.setColumns(request, page, flyTime);
        DataTablesPage<FlyTime> flytimeList = flyTimeService.findDataTablesPage(page, dataTablesPage, flyTime);
        return flytimeList;
    }*/

    @RequiresPermissions("ams:flyTime:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<FlyTime> list(FlyTime flyTime) {
        return new DataGrid<>(flyTimeService.findList(flyTime));
    }

    @RequiresPermissions("ams:flyTime:view")
    @RequestMapping(value = "form")
    public String form(FlyTime flyTime, Model model) {
        model.addAttribute("flyTime", flyTime);
        return "modules/ams/flyTimeForm";
    }

    @RequiresPermissions("ams:flyTime:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(FlyTime flyTime, Model model, Message message) {
        if (!beanValidator(model, flyTime)) {
            message.setCode(0);
        }

        flyTimeService.checkRedo(flyTime,new String[]{"startSiteCode","endSiteCode"},message);
        if(message.isSuccess()) return message;

        try {
            flyTimeService.save(flyTime);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("ams:flyTime:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(FlyTime flyTime, RedirectAttributes redirectAttributes) {
        Message message = new Message();
        try {
            flyTimeService.delete(flyTime);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

}