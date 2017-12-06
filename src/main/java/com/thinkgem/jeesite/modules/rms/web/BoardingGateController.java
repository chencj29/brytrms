/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import cn.net.metadata.wrapper.CarouselAxisWrapper;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.StringSort;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.BoardingGate;
import com.thinkgem.jeesite.modules.rms.service.BoardingGateService;
import org.apache.commons.lang3.StringUtils;
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
 * 登机口信息Controller
 *
 * @author wjp
 * @version 2016-03-19
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/boardingGate")
public class BoardingGateController extends BaseController {

    @Autowired
    private BoardingGateService boardingGateService;

    @RequiresPermissions("rms:boardingGate:view")
    @RequestMapping("view")
    public String view() {
        return "rms/boardingGate/boardingGate";
    }

    @RequiresPermissions("rms:boardingGate:view")
    @RequestMapping("get")
    @ResponseBody
    public BoardingGate get(@RequestParam("id") String id) {
        return boardingGateService.get(id);
    }

    @RequiresPermissions("rms:boardingGate:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<BoardingGate> list(BoardingGate boardingGate) {
        return new DataGrid<>(boardingGateService.findList(boardingGate));
    }

    @RequiresPermissions("rms:boardingGate:view")
    @RequestMapping(value = "form")
    public String form(BoardingGate boardingGate, Model model) {
        model.addAttribute("boardingGate", boardingGate);
        return "modules/rms/boardingGateForm";
    }

    @RequiresPermissions("rms:boardingGate:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(BoardingGate boardingGate, Model model, Message message) {
        if (!beanValidator(model, boardingGate)) {
            message.setMessage("数据校验错误!");
        }

        boardingGateService.checkRedo(boardingGate, new String[]{"boardingGateNum"}, message);
        if (message.isSuccess()) return message;

        try {
            boardingGateService.save(boardingGate);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:boardingGate:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(BoardingGate boardingGate, Message message) {
        try {
            boardingGateService.delete(boardingGate);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequestMapping("/jsonData")
    @ResponseBody
    public List<BoardingGate> getAll4Ajax(@RequestParam("type") String type) {
        List<BoardingGate> datas = boardingGateService.findList(new BoardingGate());
        return StringUtils.isBlank(type) ? datas : StringUtils.equalsIgnoreCase(type, "inte") ? datas.stream().filter(gate -> gate.getBoardingGateNature().equals("1")).collect(Collectors.toList()) : datas.stream().filter(gate -> gate.getBoardingGateNature().equals("2")).collect(Collectors.toList());
    }

    @RequestMapping("listJson")
    @ResponseBody
    public List<CarouselAxisWrapper> listJson() {
        List<CarouselAxisWrapper> datas = Lists.newArrayList();
        boardingGateService.findList(new BoardingGate()).forEach(boardingGate -> datas.add(new CarouselAxisWrapper(boardingGate.getId(), boardingGate.getBoardingGateNum(), boardingGate.getBoardingGateStatus(), boardingGate.getBoardingGateNature())));
        return datas;
    }

    @RequestMapping("list_json_by_condition")
    @ResponseBody
    public List<String> listJsonByCondition(String nature) {
        return boardingGateService.findList(new BoardingGate()).stream().filter(boardingGate -> boardingGate.getBoardingGateNature().equals(nature) && boardingGate.getBoardingGateStatus().equals("1")).map(entity -> entity.getBoardingGateNum()).sorted(new StringSort()).collect(Collectors.toList());
    }

}