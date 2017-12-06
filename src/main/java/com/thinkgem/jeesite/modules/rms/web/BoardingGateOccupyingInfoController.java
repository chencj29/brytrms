/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.BoardingGateOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.BoardingGateOccupyingInfoService;
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
 * 登机口占用信息Controller
 *
 * @author BigBrother5
 * @version 2016-04-11
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/boardingGateOccupyingInfo")
public class BoardingGateOccupyingInfoController extends BaseController {

    @Autowired
    private BoardingGateOccupyingInfoService boardingGateOccupyingInfoService;

    @RequiresPermissions("rms:boardingGateOccupyingInfo:view")
    @RequestMapping("view")
    public String view() {
        return "rms/boardingGateOccupyingInfo/boardingGateOccupyingInfo";
    }

    @RequiresPermissions("rms:boardingGateOccupyingInfo:view")
    @RequestMapping("get")
    @ResponseBody
    public BoardingGateOccupyingInfo get(@RequestParam("id") String id) {
        return boardingGateOccupyingInfoService.get(id);
    }

    @RequiresPermissions("rms:boardingGateOccupyingInfo:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<BoardingGateOccupyingInfo> list(BoardingGateOccupyingInfo boardingGateOccupyingInfo) {
        return new DataGrid<>(boardingGateOccupyingInfoService.findList(boardingGateOccupyingInfo));
    }

    @RequiresPermissions("rms:boardingGateOccupyingInfo:view")
    @RequestMapping(value = "form")
    public String form(BoardingGateOccupyingInfo boardingGateOccupyingInfo, Model model) {
        model.addAttribute("boardingGateOccupyingInfo", boardingGateOccupyingInfo);
        return "modules/rms/boardingGateOccupyingInfoForm";
    }


    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(BoardingGateOccupyingInfo boardingGateOccupyingInfo, Model model, Message message) {
        if (!beanValidator(model, boardingGateOccupyingInfo)) {
            message.setMessage("数据校验错误!");
        }
        try {
            boardingGateOccupyingInfoService.save(boardingGateOccupyingInfo);
            message.setResult(ImmutableMap.of("ocid", boardingGateOccupyingInfo.getId()));
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:boardingGateOccupyingInfo:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(BoardingGateOccupyingInfo boardingGateOccupyingInfo, Message message) {
        try {
            boardingGateOccupyingInfoService.delete(boardingGateOccupyingInfo);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequestMapping("fetchByFlightDynamicId")
    @ResponseBody
    public BoardingGateOccupyingInfo getOccupyingInfoByFlightDynamicId(String flightDynamicId) {
        return boardingGateOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
    }


    @RequestMapping("fetchOciDatas")
    @ResponseBody
    public List<BoardingGateOccupyingInfo> fetchDatas(@RequestParam("pairIds[]") List<String> pairIds) {
        return boardingGateOccupyingInfoService.fetchOciDatas(pairIds);
    }

}