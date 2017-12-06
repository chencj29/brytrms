/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.SafeguardDuration;
import com.thinkgem.jeesite.modules.rms.service.SafeguardDurationService;
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
 * 保障时长管理Controller
 *
 * @author chrischen
 * @version 2016-03-16
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/safeguardDuration")
public class SafeguardDurationController extends BaseController {

    @Autowired
    private SafeguardDurationService safeguardDurationService;

    @RequiresPermissions("rms:safeguardDuration:view")
    @RequestMapping("view")
    public String view() {
        return "rms/safeguardDuration/safeguardDuration";
    }

    @RequiresPermissions("rms:safeguardDuration:view")
    @RequestMapping("get")
    @ResponseBody
    public SafeguardDuration get(@RequestParam("id") String id) {
        return safeguardDurationService.get(id);
    }

    @RequiresPermissions("rms:safeguardDuration:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<SafeguardDuration> list(SafeguardDuration safeguardDuration) {
        return new DataGrid<>(safeguardDurationService.findList(safeguardDuration));
    }

    @RequiresPermissions("rms:safeguardDuration:view")
    @RequestMapping("listJson")
    @ResponseBody
    public List<SafeguardDuration> listJson(SafeguardDuration safeguardDuration) {
        return safeguardDurationService.findList(safeguardDuration);
    }

    @RequiresPermissions("rms:safeguardDuration:view")
    @RequestMapping(value = "form")
    public String form(SafeguardDuration safeguardDuration, Model model) {
        model.addAttribute("safeguardDuration", safeguardDuration);
        return "modules/rms/safeguardDurationForm";
    }

    @RequiresPermissions("rms:safeguardDuration:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(SafeguardDuration safeguardDuration, Model model, Message message) {
        if (!beanValidator(model, safeguardDuration)) {
            message.setMessage("数据校验错误!");
        }
        try {
            safeguardDurationService.save(safeguardDuration);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:safeguardDuration:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(SafeguardDuration safeguardDuration, Message message) {
        try {
            safeguardDurationService.delete(safeguardDuration);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

}