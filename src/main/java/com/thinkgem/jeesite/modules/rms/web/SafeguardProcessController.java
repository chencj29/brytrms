/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.SafeguardProcess;
import com.thinkgem.jeesite.modules.rms.service.SafeguardProcessService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 保障过程管理Controller
 *
 * @author chrischen
 * @version 2016-03-16
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/safeguardProcess")
public class SafeguardProcessController extends BaseController {

    @Autowired
    private SafeguardProcessService safeguardProcessService;

    @RequiresPermissions("rms:safeguardProcess:view")
    @RequestMapping("view")
    public String view() {
        return "rms/safeguardProcess/safeguardProcess";
    }

    @RequiresPermissions("rms:safeguardProcess:view")
    @RequestMapping("get")
    @ResponseBody
    public SafeguardProcess get(@RequestParam("id") String id) {
        return safeguardProcessService.get(id);
    }

    @RequiresPermissions("rms:safeguardProcess:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<SafeguardProcess> list(SafeguardProcess safeguardProcess) {
        return new DataGrid<>(safeguardProcessService.findList(safeguardProcess));
    }

    @RequiresPermissions("rms:safeguardProcess:view")
    @RequestMapping(value = "form")
    public String form(SafeguardProcess safeguardProcess, Model model) {
        model.addAttribute("safeguardProcess", safeguardProcess);
        return "modules/rms/safeguardProcessForm";
    }

    @RequiresPermissions("rms:safeguardProcess:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(SafeguardProcess safeguardProcess, Model model, Message message) {
        if (!beanValidator(model, safeguardProcess)) {
            message.setMessage("数据校验错误!");
        }
        try {
            safeguardProcessService.save(safeguardProcess);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:safeguardProcess:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(SafeguardProcess safeguardProcess, Message message) {
        try {
            safeguardProcessService.delete(safeguardProcess);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:safeguardProcess:view")
    @RequestMapping(value = "waitSelectedPorcess")
    @ResponseBody
    public List<SafeguardProcess> getWaitSelectedPorcess(SafeguardProcess safeguardProcess) {
        return safeguardProcessService.getWaitSelectedPorcess(safeguardProcess);
    }

    @RequiresPermissions("rms:safeguardProcess:view")
    @RequestMapping(value = "selectedPorcess")
    @ResponseBody
    public List<SafeguardProcess> getSelectedPorcess(SafeguardProcess safeguardProcess) {
        return safeguardProcessService.getSelectedPorcess(safeguardProcess);
    }

    @RequestMapping(value = "jsonData")
    @ResponseBody
    public Map getAll4Ajax() {
        return safeguardProcessService.getColor();
        //return safeguardProcessService.findList(new SafeguardProcess());
    }
}