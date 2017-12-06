/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.entity.ExcelExport;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightDuty;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightHomeManifest;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightHomeManifestSec;
import com.thinkgem.jeesite.modules.rms.service.RmsFlightHomeManifestService;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本站仓单数据管理Controller
 *
 * @author dingshuang
 * @version 2016-05-19
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/rmsFlightHomeManifest")
public class RmsFlightHomeManifestController extends BaseController {

    @Autowired
    private RmsFlightHomeManifestService rmsFlightHomeManifestService;

    @Autowired
    private FlightDynamicService flightDynamicService;

    @RequiresPermissions("rms:rmsFlightHomeManifest:view")
    @RequestMapping("view")
    public String view() {
        return "rms/rmsFlightHomeManifest/rmsFlightHomeManifest";
    }

    @RequiresPermissions("rms:rmsFlightHomeManifest:view")
    @RequestMapping("get")
    @ResponseBody
    public RmsFlightHomeManifest get(@RequestParam("id") String id) {
        RmsFlightHomeManifest enty = rmsFlightHomeManifestService.get(id);
        if (enty == null) {
            enty = new RmsFlightHomeManifest();
            //此时传递过来的ID实际上是 flightDynamicID
            enty.setFlightDynamicId(id);
        }
        return enty;
    }

	/*@RequiresPermissions("rms:rmsFlightHomeManifest:view")
    @RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<RmsFlightHomeManifest> page, DataTablesPage dataTablesPage, RmsFlightHomeManifest rmsFlightHomeManifest, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, rmsFlightHomeManifest);
		return rmsFlightHomeManifestService.findDataTablesPage(page, dataTablesPage, rmsFlightHomeManifest);
	}*/

    @RequiresPermissions("rms:rmsFlightHomeManifest:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<RmsFlightHomeManifest> list(RmsFlightHomeManifest rmsFlightHomeManifest) {
        return new DataGrid<>(rmsFlightHomeManifestService.findHomeManifestList(rmsFlightHomeManifest));
    }

    @RequiresPermissions("rms:rmsFlightHomeManifest:view")
    @RequestMapping(value = "form")
    public String form(RmsFlightHomeManifest rmsFlightHomeManifest, Model model) {
        model.addAttribute("rmsFlightHomeManifest", rmsFlightHomeManifest);
        return "modules/rms/rmsFlightHomeManifestForm";
    }

    @RequiresPermissions("rms:rmsFlightHomeManifest:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(RmsFlightHomeManifest rmsFlightHomeManifest, Model model, Message message, HttpServletRequest request) {
        if (!beanValidator(model, rmsFlightHomeManifest)) {
            message.setMessage("数据校验错误!");
        }
        try {
            rmsFlightHomeManifestService.save(rmsFlightHomeManifest, message);
            if (message.isSuccess()) {
                LogUtils.saveLog(request, message.getMessage());
                message.setMessage("数据保存成功！");
            }
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rmsFlightHomeManifest:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(RmsFlightHomeManifest rmsFlightHomeManifest, Message message) {
        try {
            rmsFlightHomeManifestService.delete(rmsFlightHomeManifest);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }


    @RequiresPermissions("rms:rmsFlightHomeManifest:edit")
    @RequestMapping(value = "getManifestSecs")
    @ResponseBody
    public Message getManifestSecs(String flightDynamicId, Message message) {
        if (StringUtils.isBlank(flightDynamicId)) return new Message(0, "航班动态id为空!");
        try {
            //通过FlightDynamicId 获取该航班下的所有值机数据，没有数据的则自动补充
            List<RmsFlightHomeManifestSec> manifestSecs = new ArrayList();
            //根据航班情况，计算航线信息,补充航某些航段没有的数据
            FlightDynamic dynamic = flightDynamicService.get(new FlightDynamic(flightDynamicId));
            String passAirport1Code = dynamic.getPassAirport1Code();
            String passAirport2Code = dynamic.getPassAirport2Code();
            String arrivalAirCode = dynamic.getArrivalAirportCode();
            //经停地1
            setManifestSecEntry(passAirport1Code, dynamic.getPassAirport1Name(), "PASS1", flightDynamicId, manifestSecs);
            //经停地2
            setManifestSecEntry(passAirport2Code, dynamic.getPassAirport2Name(), "PASS2", flightDynamicId, manifestSecs);
            //经停地1
            setManifestSecEntry(arrivalAirCode, dynamic.getArrivalAirportName(), "ARRI", flightDynamicId, manifestSecs);
            message.setCode(1);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("manifestSecs", manifestSecs);
            message.setResult(map);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage(e.getMessage());
        }
        return message;

    }


    /**
     * 该方法用于填充航段本站仓单数据
     *
     * @param addr
     * @param addrNature
     * @param flightDynamicId
     * @param manifestSecs
     * @return
     */
    private List<RmsFlightHomeManifestSec> setManifestSecEntry(String addrCode, String addr, String addrNature, String flightDynamicId, List<RmsFlightHomeManifestSec> manifestSecs) {
        RmsFlightHomeManifestSec resultManifestSec = new RmsFlightHomeManifestSec();
        resultManifestSec.setFlightDynamicId(flightDynamicId);
        resultManifestSec.setAddr(addr);
        resultManifestSec.setAddrCode(addrCode);
        resultManifestSec.setAddrNature(addrNature);
        //如果地址(经停、到站等)不为空，查询此航段的数据.否则补足空数据
        if (StringUtils.isNotBlank(addrCode)) {
            List<RmsFlightHomeManifestSec> resultManifestSecs = rmsFlightHomeManifestService.getManifestSecs(resultManifestSec);
            if (resultManifestSecs != null && resultManifestSecs.size() > 0) {
                RmsFlightHomeManifestSec manifestSec = resultManifestSecs.get(0);
                manifestSec.setFlightDynamicId(flightDynamicId);
                manifestSec.setAddr(addr);
                manifestSec.setAddrCode(addrCode);
                manifestSec.setAddrNature(addrNature);
                manifestSecs.add(manifestSec);
            } else {
                //查询没有值机数据，补入空数据
                manifestSecs.add(resultManifestSec);
            }
        } else {
            manifestSecs.add(resultManifestSec);
        }
        return manifestSecs;
    }


    @RequiresPermissions("rms:rmsFlightHomeManifest:edit")
    @RequestMapping(value = "saveSec")
    @ResponseBody
    public Message saveSec(HttpServletRequest request, Model model, Message message) {
        List<RmsFlightHomeManifestSec> list = new ArrayList<RmsFlightHomeManifestSec>();
        list = this.fitParamToRmsFlightHomeManifestSecs(request);
        if (list != null && list.size() > 0) {
            for (RmsFlightHomeManifestSec enty : list) {
                if (!beanValidator(model, enty)) {
                    message.setMessage("数据校验错误!");
                }
                try {
                    rmsFlightHomeManifestService.saveManifestSecs(enty, message);
                    if (message.isSuccess()) {
                        LogUtils.saveLog(request, message.getMessage());
                        message.setMessage("数据保存成功！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    message.setMessage(e.getMessage());
                }
            }
        }
        return message;
    }

    //-----------------------------------------------------------历史----begin
    //历史本站仓单数
    @RequiresPermissions("rms:rmsFlightHomeManifest:hiView")
    @RequestMapping("hiView")
    public String hiView() {
        return "rms/rmsFlightHomeManifest/rmsHiFlightHomeManifest";
    }

    @RequiresPermissions("rms:rmsFlightHomeManifest:hiView")
    @RequestMapping("hiGet")
    @ResponseBody
    public RmsFlightHomeManifest hiGet(@RequestParam("id") String id) {
        RmsFlightHomeManifest enty = rmsFlightHomeManifestService.hiGet(id);
        if (enty == null) {
            enty = new RmsFlightHomeManifest();
            //此时传递过来的ID实际上是 flightDynamicID
            enty.setFlightDynamicId(id);
        }
        return enty;
    }

    @RequiresPermissions("rms:rmsFlightHomeManifest:hiView")
    @RequestMapping("hiList")
    @ResponseBody
    public DataGrid<RmsFlightHomeManifest> hiList(RmsFlightHomeManifest rmsFlightHomeManifest, String dateStr) {
        return new DataGrid<>(rmsFlightHomeManifestService.findHiHomeManifestList(rmsFlightHomeManifest, dateStr));
    }

    @RequiresPermissions("rms:rmsFlightHomeManifest:hiEdit")
    @RequestMapping(value = "getHiManifestSecs")
    @ResponseBody
    public Message getHiManifestSecs(String flightDynamicId, Message message) {
        if (StringUtils.isBlank(flightDynamicId)) return new Message(0, "航班动态id为空!");
        rmsFlightHomeManifestService.getHiManifestSecs(flightDynamicId, message);
        return message;

    }

    @RequiresPermissions("rms:rmsFlightHomeManifest:hiEdit")
    @RequestMapping(value = "hiSave")
    @ResponseBody
    public Message hiSave(RmsFlightHomeManifest rmsFlightHomeManifest, Model model, Message message, HttpServletRequest request) {
        if (!beanValidator(model, rmsFlightHomeManifest)) {
            message.setMessage("数据校验错误!");
        }
        try {
            rmsFlightHomeManifestService.hiSave(rmsFlightHomeManifest, message);
            if (message.isSuccess()) {
                LogUtils.saveLog(request, message.getMessage());
                message.setMessage("数据保存成功！");
            }
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rmsFlightHomeManifest:hiEdit")
    @RequestMapping(value = "hiSaveSec")
    @ResponseBody
    public Message hiSaveSec(HttpServletRequest request, Model model, Message message) {
        List<RmsFlightHomeManifestSec> list = new ArrayList<RmsFlightHomeManifestSec>();
        list = this.fitParamToRmsFlightHomeManifestSecs(request);
        if (list != null && list.size() > 0) {
            for (RmsFlightHomeManifestSec enty : list) {
                if (!beanValidator(model, enty)) {
                    message.setMessage("数据校验错误!");
                }
                try {
                    rmsFlightHomeManifestService.hiSaveManifestSecs(enty, message);
                    if (message.isSuccess()) {
                        LogUtils.saveLog(request, message.getMessage());
                        message.setMessage("数据保存成功！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    message.setMessage(e.getMessage());
                }
            }
        }
        return message;
    }
    //-----------------------------------------------------------历史----end

    @RequestMapping("expExcel")
    public void expExcel(RmsFlightHomeManifest manifest, String dateStr, HttpServletRequest request, HttpServletResponse response) {
        String datasJson = request.getParameter("fields");
        JSONArray jsonArray = null;
        if (StringUtils.isNotBlank(datasJson)) {
            jsonArray = JSON.parseArray(datasJson);
        } else {
            String datas = FileUtils.readJson("overManifest");
            jsonArray = JSON.parseArray(datas.toString());
        }
        List<String> fields = new ArrayList<>();
        String[] rowsName = rmsFlightHomeManifestService.jsonTofiled(jsonArray, fields);

        String expDate = dateStr;
        List<Object[]> dataList = new ArrayList<>();

        List<RmsFlightHomeManifest> listtemp = null;
        if (StringUtils.isNotBlank(dateStr)) {
            listtemp = rmsFlightHomeManifestService.findHiHomeManifestList(manifest, dateStr);
        } else {
            listtemp = rmsFlightHomeManifestService.findHomeManifestList(manifest);
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

    /**
     * 将传递来的本站仓单数据组装成对象列表
     *
     * @param request
     * @return
     */
    private List<RmsFlightHomeManifestSec> fitParamToRmsFlightHomeManifestSecs(HttpServletRequest request) {
        String flightDynamicId = request.getParameter("flightDynamicId");
        List<RmsFlightHomeManifestSec> homeManifestSecList = new ArrayList<RmsFlightHomeManifestSec>();
        String[] addrNatures = request.getParameterValues("addrNature");
        String[] ids = request.getParameterValues("id");
        String[] addrs = request.getParameterValues("addr");
        String[] addrCodes = request.getParameterValues("addrCode");
        String[] personCounts = request.getParameterValues("personCount");
        String[] childCounts = request.getParameterValues("childCount");
        String[] babyCounts = request.getParameterValues("babyCount");
        String[] goodsCounts = request.getParameterValues("goodsCount");
        String[] goodsWeights = request.getParameterValues("goodsWeight");
        String[] mailCounts = request.getParameterValues("mailCount");
        String[] mailWeights = request.getParameterValues("mailWeight");
        String[] luggageCounts = request.getParameterValues("luggageCount");
        String[] luggageWeights = request.getParameterValues("luggageWeight");
        //航段的地点性质不可能为空，且一定有经停地1、经停地2、到达地三个项目
        for (int i = 0; i < addrNatures.length; i++) {
            if (StringUtils.isNotBlank(addrCodes[i])) {
                RmsFlightHomeManifestSec enty = new RmsFlightHomeManifestSec();
                enty.setAddrNature(addrNatures[i]);
                enty.setId(ids[i]);
                enty.setAddr(addrs[i]);
                enty.setAddrCode(addrCodes[i]);
                enty.setPersonCount(Float.parseFloat(personCounts[i]));
                enty.setChildCount(Float.parseFloat(childCounts[i]));
                enty.setBabyCount(Float.parseFloat(babyCounts[i]));
                enty.setGoodsCount(Float.parseFloat(goodsCounts[i]));
                enty.setGoodsWeight(Float.parseFloat(goodsWeights[i]));
                enty.setMailCount(Float.parseFloat(mailCounts[i]));
                enty.setMailWeight(Float.parseFloat(mailWeights[i]));
                enty.setLuggageCount(Float.parseFloat(luggageCounts[i]));
                enty.setLuggageWeight(Float.parseFloat(luggageWeights[i]));
                enty.setFlightDynamicId(flightDynamicId);
                homeManifestSecList.add(enty);
            }
        }
        return homeManifestSecList;
    }

}