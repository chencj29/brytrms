/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import cn.net.metadata.wrapper.CarouselAxisWrapper;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.StringSort;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.ArrivalGate;
import com.thinkgem.jeesite.modules.rms.service.ArrivalGateService;
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
 * 到港口基础信息Controller
 *
 * @author wjp
 * @version 2016-03-19
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/arrivalGate")
public class ArrivalGateController extends BaseController {

    @Autowired
    private ArrivalGateService arrivalGateService;

    @RequiresPermissions("rms:arrivalGate:view")
    @RequestMapping("view")
    public String view() {
        return "rms/arrivalGate/arrivalGate";
    }

    @RequiresPermissions("rms:arrivalGate:view")
    @RequestMapping("get")
    @ResponseBody
    public ArrivalGate get(@RequestParam("id") String id) {
        return arrivalGateService.get(id);
    }

    @RequiresPermissions("rms:arrivalGate:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<ArrivalGate> list(ArrivalGate arrivalGate) {
        return new DataGrid<>(arrivalGateService.findList(arrivalGate));
    }

    @RequiresPermissions("rms:arrivalGate:view")
    @RequestMapping(value = "form")
    public String form(ArrivalGate arrivalGate, Model model) {
        model.addAttribute("arrivalGate", arrivalGate);
        return "modules/rms/arrivalGateForm";
    }

    @RequiresPermissions("rms:arrivalGate:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(ArrivalGate arrivalGate, Model model, Message message) {
        if (!beanValidator(model, arrivalGate)) {
            message.setMessage("数据校验错误!");
        }

        arrivalGateService.checkRedo(arrivalGate, new String[]{"arrivalGateNum"}, message);
        if (message.isSuccess()) return message;

        try {
            arrivalGateService.save(arrivalGate);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:arrivalGate:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(ArrivalGate arrivalGate, Message message) {
        try {
            arrivalGateService.delete(arrivalGate);
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
        arrivalGateService.findList(new ArrivalGate()).forEach(arrivalGate -> datas.add(new CarouselAxisWrapper(arrivalGate.getId(), arrivalGate.getArrivalGateNum(), arrivalGate.getArrivalGateStatus(), arrivalGate.getArrivalGateNature())));
        return datas;
    }

    @RequestMapping("list_json_by_condition")
    @ResponseBody
    public List<String> listJsonByCondition(String nature) {
        return arrivalGateService.findList(new ArrivalGate()).stream().filter(arrivalGate -> arrivalGate.getArrivalGateNature().equals(nature) && arrivalGate.getArrivalGateStatus().equals("1")).map(entity -> entity.getArrivalGateNum()).sorted(new StringSort()).collect(Collectors.toList());
    }

}