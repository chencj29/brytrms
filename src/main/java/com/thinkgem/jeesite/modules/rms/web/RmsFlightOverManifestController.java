/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.ExcelExport;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightHomeManifest;
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
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightOverManifest;
import com.thinkgem.jeesite.modules.rms.service.RmsFlightOverManifestService;

import java.util.ArrayList;
import java.util.List;

/**
 * 过站仓单数据管理Controller
 *
 * @author dingshuang
 * @version 2016-05-20
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/rmsFlightOverManifest")
public class RmsFlightOverManifestController extends BaseController {

    @Autowired
    private RmsFlightOverManifestService rmsFlightOverManifestService;

    @RequiresPermissions("rms:rmsFlightOverManifest:view")
    @RequestMapping("view")
    public String view() {
        return "rms/rmsFlightOverManifest/rmsFlightOverManifest";
    }

    @RequiresPermissions("rms:rmsFlightOverManifest:view")
    @RequestMapping("get")
    @ResponseBody
    public RmsFlightOverManifest get(@RequestParam("id") String id) {
        return rmsFlightOverManifestService.get(id);
    }

	/*@RequiresPermissions("rms:rmsFlightOverManifest:view")
    @RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<RmsFlightOverManifest> page, DataTablesPage dataTablesPage, RmsFlightOverManifest rmsFlightOverManifest, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, rmsFlightOverManifest);
		return rmsFlightOverManifestService.findDataTablesPage(page, dataTablesPage, rmsFlightOverManifest);
	}*/

    @RequiresPermissions("rms:rmsFlightOverManifest:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<RmsFlightOverManifest> list(RmsFlightOverManifest rmsFlightOverManifest) {
        return new DataGrid<>(rmsFlightOverManifestService.findFlightOverManifestList(rmsFlightOverManifest));
    }

    @RequiresPermissions("rms:rmsFlightOverManifest:view")
    @RequestMapping(value = "form")
    public String form(RmsFlightOverManifest rmsFlightOverManifest, Model model) {
        model.addAttribute("rmsFlightOverManifest", rmsFlightOverManifest);
        return "modules/rms/rmsFlightOverManifestForm";
    }

    @RequiresPermissions("rms:rmsFlightOverManifest:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(RmsFlightOverManifest rmsFlightOverManifest, Model model, Message message, HttpServletRequest request) {
        if (!beanValidator(model, rmsFlightOverManifest)) {
            message.setMessage("数据校验错误!");
        }
        try {
            rmsFlightOverManifestService.save(rmsFlightOverManifest, message);
            if (message.isSuccess()) {
                LogUtils.saveLog(request, message.getMessage());
                message.setMessage("数据保存成功！");
            }
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rmsFlightOverManifest:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(RmsFlightOverManifest rmsFlightOverManifest, Message message) {
        try {
            rmsFlightOverManifestService.delete(rmsFlightOverManifest);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    //-----------------------------------------------------------历史----begin
    //历史过站仓单数
    @RequiresPermissions("rms:rmsFlightOverManifest:hiView")
    @RequestMapping("hiView")
    public String hiView() {
        return "rms/rmsFlightOverManifest/rmsHiFlightOverManifest";
    }

    @RequiresPermissions("rms:rmsFlightOverManifest:hiView")
    @RequestMapping("hiGet")
    @ResponseBody
    public RmsFlightOverManifest hiGet(@RequestParam("id") String id) {
        return rmsFlightOverManifestService.hiGet(id);
    }

    @RequiresPermissions("rms:rmsFlightOverManifest:hiView")
    @RequestMapping("hiList")
    @ResponseBody
    public DataGrid<RmsFlightOverManifest> hiList(RmsFlightOverManifest rmsFlightOverManifest, String dateStr) {
        return new DataGrid<>(rmsFlightOverManifestService.findHiFlightOverManifestList(rmsFlightOverManifest, dateStr));
    }

    @RequiresPermissions("rms:rmsFlightOverManifest:hiEdit")
    @RequestMapping(value = "hiSave")
    @ResponseBody
    public Message hisave(RmsFlightOverManifest rmsFlightOverManifest, Model model, Message message, HttpServletRequest request) {
        if (!beanValidator(model, rmsFlightOverManifest)) {
            message.setMessage("数据校验错误!");
        }

        try {
            rmsFlightOverManifestService.hiSave(rmsFlightOverManifest, message);
            if (message.isSuccess()) {
                LogUtils.saveLog(request, message.getMessage());
                message.setMessage("数据保存成功！");
            }
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }
    //-----------------------------------------------------------历史----end

    @RequestMapping("expExcel")
    public void expExcel(RmsFlightOverManifest manifest, String dateStr, HttpServletRequest request, HttpServletResponse response) {
        String datasJson = request.getParameter("fields");
        JSONArray jsonArray = null;
        if (StringUtils.isNotBlank(datasJson)) {
            jsonArray = JSON.parseArray(datasJson);
        } else {
            String datas = FileUtils.readJson("overManifest");
            jsonArray = JSON.parseArray(datas.toString());
        }

        List<String> fields = new ArrayList<>();
        String[] rowsName = rmsFlightOverManifestService.jsonTofiled(jsonArray, fields);

        String expDate = dateStr;
        List<Object[]> dataList = new ArrayList<>();

        List<RmsFlightOverManifest> listtemp = null;
        if (StringUtils.isNotBlank(dateStr)) {
            listtemp = rmsFlightOverManifestService.findHiFlightOverManifestList(manifest, dateStr);
        } else {
            listtemp = rmsFlightOverManifestService.findFlightOverManifestList(manifest);
        }

        if (!Collections3.isEmpty(listtemp)) {
            expDate = DateUtils.formatDate(listtemp.get(0).getPlanDate());
        }

        listtemp.forEach(carousel -> {
            List filedList = new ArrayList();
            fields.forEach(filed -> {
                try {
                    if (StringUtils.equals(filed, "planDate")) {
                        filedList.add(DateUtils.formatDate(carousel.getPlanDate()));
                    } else if (StringUtils.equals(filed, "flightNum")) {
                        filedList.add(StringUtils.toStr(carousel.getFlightCompanyCode(), carousel.getFlightNum()));
                    } else if (StringUtils.equals(filed, "departurePlanTime")) {
                        filedList.add(DateUtils.formatDate(carousel.getDeparturePlanTime(), "yyyy-MM-dd HH:mm"));
                    } else {
                        filedList.add(StringUtils.toStr(Reflections.getFieldValue(carousel, filed)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            dataList.add(filedList.toArray());
        });

        ExcelExport excelExport = new ExcelExport();
        excelExport.setTitle("每日航班动态及结载数据打印导出模板（航班动态和结载数据通用）(" + expDate + ")");
        excelExport.setFileName(expDate + "本站仓单数据");

        excelExport.setRowsName(rowsName);
        excelExport.setDataList(dataList);
        excelExport.setRequest(request);
        excelExport.setResponse(response);
        //导出Excel公共方法调用
        excelExport.exp();
        logger.info(expDate + "“本站仓单数据”导出成功");
    }

}