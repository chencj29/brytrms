/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.ShareFlight;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.ShareFlightService;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 共享航班管理Controller
 *
 * @author xiaopo
 * @version 2016-01-19
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/shareflight")
public class ShareFlightController extends BaseController {

    private static Logger log = Logger.getLogger(ShareFlightController.class);

    @Autowired
    private ShareFlightService shareFlightService;

    @ModelAttribute
    public ShareFlight get(@RequestParam(required = false) String id) {
        ShareFlight entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = shareFlightService.get(id);
        }
        if (entity == null) {
            entity = new ShareFlight();
        }
        return entity;
    }


    @RequiresPermissions("ams:shareFlight:view")
    @RequestMapping(value = {"view", ""})
    public String view() {
        return "ams/flightplan/shareflight";
    }

    @RequiresPermissions("ams:shareFlight:view")
    @RequestMapping(value = {"list", ""})
    @ResponseBody
    public DataTablesPage<ShareFlight> list(DataTablesPage<ShareFlight> dataTablesPage, Page<ShareFlight> page, ShareFlight shareFlight,
                                            HttpServletRequest request, HttpServletResponse response, Model model) {
        dataTablesPage.setColumns(request, page, shareFlight);
        dataTablesPage = shareFlightService.findDataTablesPage(page, dataTablesPage, shareFlight);
        return dataTablesPage;
    }

    @RequiresPermissions("ams:shareFlight:view")
    @RequestMapping(value = "form")
    public String form(ShareFlight shareFlight, Model model) {
        model.addAttribute("shareFlight", shareFlight);
        return "modules/ams/shareFlightForm";
    }

    /**
     * 编辑共享航班
     * @param shareFlight
     * @param model
     * @param message
     * @return
     */
    @RequiresPermissions("ams:shareFlight:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(ShareFlight shareFlight, Model model, Message message) {
        if (!beanValidator(model, shareFlight)) {
            message.setMessage("数据校验失败!");
        }
        try {
            message = shareFlightService.saveAndCheck(shareFlight);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(-1);
            message.setMessage(e.getMessage());
        }
        return message;
    }

    /**
     * 删除共享航班
     * @param shareFlight
     * @param message
     * @return
     */
    @RequiresPermissions("ams:shareFlight:delete")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(ShareFlight shareFlight, Message message) {
        try {
            shareFlightService.delete(shareFlight);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    /**
     * @return 应用共享航班, 将共享航班中的数据更新到航班动态
     */
    @RequiresPermissions("ams:shareFlight:apply")
    @RequestMapping(value = "apply")
    @ResponseBody
    public Message apply(Message message, @RequestParam Date applyDate) {
        try {
            shareFlightService.updateApply(applyDate);
            message.setCode(1);
        } catch (Exception e) {
            log.error("共享航班应用异常...", e);
            message.setCode(0);
        }
        return message;
    }

}