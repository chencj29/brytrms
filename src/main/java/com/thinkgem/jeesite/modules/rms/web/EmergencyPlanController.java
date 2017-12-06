/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.ckfinder.connector.ServletContextFactory;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.EmergencyPlan;
import com.thinkgem.jeesite.modules.rms.service.EmergencyPlanService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 应急救援方案Controller
 *
 * @author wjp
 * @version 2016-08-25
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/emergencyPlan")
public class EmergencyPlanController extends BaseController {

    @Autowired
    private EmergencyPlanService emergencyPlanService;

    @RequiresPermissions("rms:emergencyPlan:view")
    @RequestMapping("view")
    public String view() {
        return "rms/emergencyPlan/emergencyPlan";
    }

    @RequiresPermissions("rms:emergencyPlan:view")
    @RequestMapping("get")
    @ResponseBody
    public EmergencyPlan get(@RequestParam("id") String id) {
        return emergencyPlanService.get(id);
    }

	/*@RequiresPermissions("rms:emergencyPlan:view")
    @RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<EmergencyPlan> page, DataTablesPage dataTablesPage, EmergencyPlan emergencyPlan, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, emergencyPlan);
		return emergencyPlanService.findDataTablesPage(page, dataTablesPage, emergencyPlan);
	}*/

    @RequiresPermissions("rms:emergencyPlan:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<EmergencyPlan> list(EmergencyPlan emergencyPlan) {
        return new DataGrid<>(emergencyPlanService.findList(emergencyPlan));
    }

    @RequiresPermissions("rms:emergencyPlan:view")
    @RequestMapping(value = "form")
    public String form(EmergencyPlan emergencyPlan, Model model) {
        model.addAttribute("emergencyPlan", emergencyPlan);
        return "modules/rms/emergencyPlanForm";
    }

    @RequiresPermissions("rms:emergencyPlan:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(EmergencyPlan emergencyPlan, Model model, Message message) {
        if (!beanValidator(model, emergencyPlan)) {
            message.setMessage("数据校验错误!");
        }
        try {
            emergencyPlanService.save(emergencyPlan);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:emergencyPlan:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(EmergencyPlan emergencyPlan, Message message) {
        try {
            emergencyPlanService.delete(emergencyPlan);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequestMapping(value = "impDoc", method = RequestMethod.POST)
    @ResponseBody
    public String impDoc(@RequestParam("fileImport") MultipartFile multipartFile, EmergencyPlan emergencyPlan) {
        if (multipartFile != null) {
            //取得当前上传文件的文件名称
            String priNames = multipartFile.getOriginalFilename();
            if (priNames.toLowerCase().endsWith(".doc") || priNames.toLowerCase().endsWith(".docx")) {
                // 得到上传文件的保存目录，将上传文件存放在upload目录下
                String savePath = null;
                try {
                    savePath = ServletContextFactory.getServletContext().getRealPath("downs");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                File saveFileDir = new File(savePath);
                if (!saveFileDir.exists()) {
                    // 创建临时目录
                    saveFileDir.mkdirs();
                }

                try {
                    InputStream is = multipartFile.getInputStream();
                    //创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(savePath + "\\" + priNames);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标致
                    int len = 0;
                    while ((len = is.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
                    //关闭输出流
                    out.close();
                    //关闭输入流
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String url = "<a href=\"/downs/" + priNames + "\" target=\"_blank\" title=\"" + priNames + "\">" + priNames + "</a>";
                emergencyPlan.setEmPlanFilePath(url);

                emergencyPlanService.save(emergencyPlan);
                return emergencyPlan.getId();
            }
        }

        return "";
    }

    @RequestMapping(value = "jsonData")
    @ResponseBody
    public List<EmergencyPlan> getAll4Ajax(Message message) {
        return emergencyPlanService.findList(new EmergencyPlan());
    }
}