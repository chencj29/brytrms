package com.thinkgem.jeesite.modules.rms.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.FlightPairProgressInfo;
import com.thinkgem.jeesite.modules.rms.service.FlightPairProgressInfoService;
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
 * 航班运输保障信息Controller
 *
 * @author xiaowu
 * @version 2016-06-13
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/flightPairProgressInfo")
public class FlightPairProgressInfoController extends BaseController {

    @Autowired
    private FlightPairProgressInfoService flightPairProgressInfoService;

    @RequiresPermissions("rms:flightPairProgressInfo:view")
    @RequestMapping("view")
    public String view() {
        return "rms/flightPairProgressInfo/flightPairProgressInfo";
    }

    @RequiresPermissions("rms:flightPairProgressInfo:view")
    @RequestMapping("get")
    @ResponseBody
    public FlightPairProgressInfo get(@RequestParam("id") String id) {
        return flightPairProgressInfoService.get(id);
    }

    @RequiresPermissions("rms:flightPairProgressInfo:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<FlightPairProgressInfo> list(FlightPairProgressInfo flightPairProgressInfo) {
        return new DataGrid<>(flightPairProgressInfoService.findList(flightPairProgressInfo));
    }

    @RequiresPermissions("rms:flightPairProgressInfo:view")
    @RequestMapping(value = "form")
    public String form(FlightPairProgressInfo flightPairProgressInfo, Model model) {
        model.addAttribute("flightPairProgressInfo", flightPairProgressInfo);
        return "modules/rms/flightPairProgressInfoForm";
    }

    @RequiresPermissions("rms:flightPairProgressInfo:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(FlightPairProgressInfo flightPairProgressInfo, Model model, Message message) {
        if (!beanValidator(model, flightPairProgressInfo)) {
            message.setMessage("数据校验错误!");
        }
        try {
            flightPairProgressInfoService.save(flightPairProgressInfo);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:flightPairProgressInfo:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(FlightPairProgressInfo flightPairProgressInfo, Message message) {
        try {
            flightPairProgressInfoService.delete(flightPairProgressInfo);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequestMapping("findByPairId")
    @ResponseBody
    public List<FlightPairProgressInfo> findByPairId(String pairId) {
        return flightPairProgressInfoService.queryByPairId(pairId);
    }

}