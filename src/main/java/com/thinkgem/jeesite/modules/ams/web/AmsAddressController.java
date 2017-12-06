/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.AmsAddress;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.AmsAddressService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 地名管理Controller
 *
 * @author chrischen
 * @version 2015-12-22
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/amsAddress")
public class AmsAddressController extends BaseController {

    @Autowired
    private AmsAddressService amsAddressService;

    @RequiresPermissions("ams:amsAddress:view")
    @RequestMapping("get")
    @ResponseBody
    public AmsAddress get(@RequestParam("id") String id) {
        return amsAddressService.get(id);
    }

    /*@ModelAttribute
    public AmsAddress get(@RequestParam(required = false) String id) {
        AmsAddress entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = amsAddressService.get(id);
        }
        if (entity == null) {
            entity = new AmsAddress();
        }
        return entity;
    }*/

    @RequiresPermissions("ams:amsAddress:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<AmsAddress> list(AmsAddress amsAddress) {
        return new DataGrid<>(amsAddressService.findList(amsAddress));
    }

    @RequiresPermissions("ams:amsAddress:view")
    @RequestMapping("view")
    public String view() {
        return "ams/flightParameters/address";
    }

   /* @RequiresPermissions("ams:amsAddress:view")
    @RequestMapping(value = {"list", ""})
    @ResponseBody
    public DataTablesPage<AmsAddress> list(Page<AmsAddress> page, DataTablesPage<AmsAddress> dataTablesPage, AmsAddress amsAddress, HttpServletRequest request, HttpServletResponse response) {
        dataTablesPage.setColumns(request, page, amsAddress);
        DataTablesPage<AmsAddress> amsAddressList = amsAddressService.findDataTablesPage(page, dataTablesPage, amsAddress);
        return amsAddressList;
    }*/

    @RequiresPermissions("ams:amsAddress:view")
    @RequestMapping(value = "form")
    public String form(AmsAddress amsAddress, Model model) {
        model.addAttribute("amsAddress", amsAddress);
        return "modules/ams/amsAddressForm";
    }

    @RequiresPermissions("ams:amsAddress:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(AmsAddress amsAddress, Model model, RedirectAttributes redirectAttributes, Message message) {
        if (!beanValidator(model, amsAddress)) {
            message.setCode(0);
        }

        amsAddressService.checkRedo(amsAddress,new String[]{"threeCode","fourCode"},message,true);
        if(message.isSuccess()) return message;

        try {
            amsAddressService.save(amsAddress);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }

        return message;
    }

    /**
     * @param @param  amsAddress
     * @param @param  redirectAttributes
     * @param @return
     * @return Message
     * @throws
     * @Title: delete
     * @Description: 删除地名信息数据
     * @author: chencheng
     * @date： 2015年12月22日 下午5:40:32
     */
    @RequiresPermissions("ams:amsAddress:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(AmsAddress amsAddress, RedirectAttributes redirectAttributes) {
        Message message = new Message();
        try {
            amsAddressService.delete(amsAddress);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    //@RequiresPermissions("ams:amsAddress:view")
    @RequestMapping(value = "jsonData")
    @ResponseBody
    public List<AmsAddress> getAll4Ajax(Message message) {
        return amsAddressService.findList(new AmsAddress());
    }

}