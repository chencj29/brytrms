/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.modules.ams.dao.FlightDynamicDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.FlightPairWrapper;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;
import com.thinkgem.jeesite.modules.rms.entity.SecurityPairWrapper;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.easyui.DataGrid.DataGrid;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.SecurityServiceInfo;
import com.thinkgem.jeesite.modules.rms.service.SecurityServiceInfoService;

import java.util.Collections;
import java.util.List;

/**
 * 安检航班信息Controller
 *
 * @author wjp
 * @version 2017-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/securityServiceInfo")
public class SecurityServiceInfoController extends BaseController {

    @Autowired
    private SecurityServiceInfoService securityServiceInfoService;

    @Autowired
    private FlightDynamicDao flightDynamicDao;

    @RequiresPermissions("rms:securityServiceInfo:view")
    @RequestMapping("view")
    public String view() {
        return "rms/securityServiceInfo/securityServiceInfo";
    }

    @RequiresPermissions("rms:securityServiceInfo:view")
    @RequestMapping("get")
    @ResponseBody
    public SecurityServiceInfo get(@RequestParam("id") String id) {
        return securityServiceInfoService.get(id);
    }

	/*@RequiresPermissions("rms:securityServiceInfo:view")
    @RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<SecurityServiceInfo> page, DataTablesPage dataTablesPage, SecurityServiceInfo securityServiceInfo, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, securityServiceInfo);
		return securityServiceInfoService.findDataTablesPage(page, dataTablesPage, securityServiceInfo);
	}*/

    @RequiresPermissions("rms:securityServiceInfo:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<SecurityPairWrapper> list(SecurityServiceInfo securityServiceInfo) {
        return new DataGrid<>(securityServiceInfoService.findListWapper(securityServiceInfo));
    }

    @RequiresPermissions("rms:securityServiceInfo:view")
    @RequestMapping(value = "form")
    public String form(SecurityServiceInfo securityServiceInfo, Model model) {
        model.addAttribute("securityServiceInfo", securityServiceInfo);
        return "modules/rms/securityServiceInfoForm";
    }

    @RequiresPermissions("rms:securityServiceInfo:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(SecurityServiceInfo securityServiceInfo, Model model, Message message, HttpServletRequest request) {
        if (!beanValidator(model, securityServiceInfo)) {
            message.setMessage("数据校验错误!");
        }
        try {
            String flightDynimicId = request.getParameter("flightDynimicId");
            String flightDynimicId2 = request.getParameter("flightDynimicId2");
            StringBuffer sb = new StringBuffer("安检和勤务信息(添加/修改)：航班[");
            if (StringUtils.isNotBlank(flightDynimicId)) {
                securityServiceInfo.setFlightDynamicIc(flightDynimicId);
                FlightDynamic fd = flightDynamicDao.get(flightDynimicId);
                if (fd != null) sb.append(LogUtils.msgFlightDynamic(fd));
            } else if (StringUtils.isNotBlank(flightDynimicId2)) {
                securityServiceInfo.setFlightDynamicIc(flightDynimicId2);
                FlightDynamic fd = flightDynamicDao.get(flightDynimicId);
                if (fd != null) sb.append(LogUtils.msgFlightDynamic(fd));
            } else {
                message.setMsg(0, "所选数据为空，无法保存！");
                return message;
            }

            if (StringUtils.isBlank(securityServiceInfo.getId())) {
                List<SecurityServiceInfo> serviceInfos = securityServiceInfoService.findByFlightDynamicIc(securityServiceInfo);
                if (!Collections3.isEmpty(serviceInfos)) {
                    securityServiceInfo.setId(serviceInfos.get(0).getId());
                }
            }
            securityServiceInfoService.save(securityServiceInfo);

            message.setResult(ImmutableMap.of("data", securityServiceInfo));
            sb.append("]").append("，修改信息为").append(securityServiceInfo.getServiceInfo());
            LogUtils.saveLog(request, sb.toString());
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:securityServiceInfo:del")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(SecurityServiceInfo securityServiceInfo, Message message) {
        try {
            securityServiceInfoService.delete(securityServiceInfo);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

}