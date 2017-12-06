/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.modules.rms.entity.ServiceProvider;
import com.thinkgem.jeesite.modules.rms.service.ServiceProviderService;

import java.util.List;

/**
 * 服务提供者表Controller
 * @author wjp
 * @version 2016-03-10
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/serviceProvider")
public class ServiceProviderController extends BaseController {

	@Autowired
	private ServiceProviderService serviceProviderService;
	
	@RequiresPermissions("rms:serviceProvider:view"	)
	@RequestMapping("view")
	public String view(){
		return "rms/serviceProvider/serviceProvider";
	}

	@RequiresPermissions("rms:serviceProvider:view")
    @RequestMapping("get")
    @ResponseBody
    public ServiceProvider get(@RequestParam("id") String id) {
        return serviceProviderService.get(id);
    }

	/*@RequiresPermissions("rms:serviceProvider:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<ServiceProvider> page, DataTablesPage dataTablesPage, ServiceProvider serviceProvider, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, serviceProvider);
		return serviceProviderService.findDataTablesPage(page, dataTablesPage, serviceProvider);
	}*/

	@RequiresPermissions("rms:serviceProvider:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<ServiceProvider> list(ServiceProvider serviceProvider) {
        return new DataGrid<>(serviceProviderService.findList(serviceProvider));
    }

	@RequiresPermissions("rms:serviceProvider:view")
	@RequestMapping(value = "form")
	public String form(ServiceProvider serviceProvider, Model model) {
		model.addAttribute("serviceProvider", serviceProvider);
		return "modules/rms/serviceProviderForm";
	}

	@RequiresPermissions("rms:serviceProvider:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Message save(ServiceProvider serviceProvider, Model model, Message message) {
		if (!beanValidator(model, serviceProvider)){
			message.setMessage("数据校验错误!");
		}

		serviceProviderService.checkRedo(serviceProvider,new String[]{"serviceProviderNo"},message);
		if(message.isSuccess()) return message;

		try {
			serviceProviderService.save(serviceProvider);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequiresPermissions("rms:serviceProvider:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Message delete(ServiceProvider serviceProvider, Message message) {
		try {
			serviceProviderService.delete(serviceProvider);
			message.setCode(1);
		} catch (Exception e) {
			message.setMessage(e.getMessage());
		}
		return message;
	}

	@RequestMapping("/jsonData")
	@ResponseBody
	public List<ServiceProvider> getAll4Ajax(){
		return serviceProviderService.findList(new ServiceProvider());
	}

}