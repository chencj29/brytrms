/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.entity.ExcelExport;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightOverManifest;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightPreManifest;
import com.thinkgem.jeesite.modules.rms.service.RmsFlightPreManifestService;
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
 * 上站舱单数据管理Controller
 *
 * @author dingshuang
 * @version 2016-05-21
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/rmsFlightPreManifest")
public class RmsFlightPreManifestController extends BaseController {

    @Autowired
    private RmsFlightPreManifestService rmsFlightPreManifestService;

    @Autowired
    private FlightDynamicService flightDynamicService;


    @RequiresPermissions("rms:rmsFlightPreManifest:view")
    @RequestMapping("view")
    public String view() {
        return "rms/rmsFlightPreManifest/rmsFlightPreManifest";
    }

    @RequiresPermissions("rms:rmsFlightPreManifest:view")
    @RequestMapping("get")
    @ResponseBody
    public RmsFlightPreManifest get(@RequestParam("id") String id) {
        return rmsFlightPreManifestService.get(id);
    }

	/*@RequiresPermissions("rms:rmsFlightPreManifest:view")
    @RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<RmsFlightPreManifest> page, DataTablesPage dataTablesPage, RmsFlightPreManifest rmsFlightPreManifest, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, rmsFlightPreManifest);
		return rmsFlightPreManifestService.findDataTablesPage(page, dataTablesPage, rmsFlightPreManifest);
	}*/

    @RequiresPermissions("rms:rmsFlightPreManifest:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<RmsFlightPreManifest> list(RmsFlightPreManifest rmsFlightPreManifest) {
        return new DataGrid<>(rmsFlightPreManifestService.findFlightPreManifestList(rmsFlightPreManifest));
    }

    @RequiresPermissions("rms:rmsFlightPreManifest:view")
    @RequestMapping(value = "form")
    public String form(RmsFlightPreManifest rmsFlightPreManifest, Model model) {
        model.addAttribute("rmsFlightPreManifest", rmsFlightPreManifest);
        return "modules/rms/rmsFlightPreManifestForm";
    }

    @RequiresPermissions("rms:rmsFlightPreManifest:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(HttpServletRequest request, Model model, Message message) {
        List<RmsFlightPreManifest> list = new ArrayList<RmsFlightPreManifest>();
        list = this.fitParamToRmsFlightPreManifests(request);
        if (list != null && list.size() > 0) {
            for (RmsFlightPreManifest enty : list) {
                if (!beanValidator(model, enty)) {
                    message.setMessage("数据校验错误!");
                }
                try {
                    rmsFlightPreManifestService.save(enty, message);
                    if (message.isSuccess()) {
                        LogUtils.saveLog(request, message.getMessage());
                        message.setMessage("更新成功！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    message.setMessage(e.getMessage());
                }
            }
        }
        return message;
    }

    @RequiresPermissions("rms:rmsFlightPreManifest:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(RmsFlightPreManifest rmsFlightPreManifest, Message message) {
        try {
            rmsFlightPreManifestService.delete(rmsFlightPreManifest);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }


    @RequiresPermissions("rms:rmsFlightPreManifest:edit")
    @RequestMapping(value = "getFlightPreManifests")
    @ResponseBody
    public Message getFlightPreManifests(String flightDynamicId, Message message) {
        try {
            //通过FlightDynamicId 获取该航班下的所有值机数据，没有数据的则自动补充
            List<RmsFlightPreManifest> flightPreManifests = new ArrayList<RmsFlightPreManifest>();
            //根据航班情况，计算航线信息,补充航某些航段没有的数据
            FlightDynamic dynamic = flightDynamicService.get(new FlightDynamic(flightDynamicId));
            String passAirport1Code = dynamic.getPassAirport1Code();
            String passAirport2Code = dynamic.getPassAirport2Code();
            String arrivalAirCode = dynamic.getArrivalAirportCode();
            //经停地1
            setFlightPreManifestEntry(passAirport1Code, dynamic.getPassAirport1Name(), "PASS1", flightDynamicId, flightPreManifests);
            //经停地2
            setFlightPreManifestEntry(passAirport2Code, dynamic.getPassAirport2Name(), "PASS2", flightDynamicId, flightPreManifests);
            //出发地
            setFlightPreManifestEntry(arrivalAirCode, dynamic.getArrivalAirportName(), "DEPA", flightDynamicId, flightPreManifests);
            message.setCode(1);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("flightPreManifests", flightPreManifests);
            message.setResult(map);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    //-----------------------------------------------------------历史----begin
    //历史上站仓单数
    @RequiresPermissions("rms:rmsFlightPreManifest:hiView")
    @RequestMapping("hiView")
    public String hiView() {
        return "rms/rmsFlightPreManifest/rmsHiFlightPreManifest";
    }

    @RequiresPermissions("rms:rmsFlightPreManifest:view")
    @RequestMapping("hiGet")
    @ResponseBody
    public RmsFlightPreManifest hiGet(@RequestParam("id") String id) {
        return rmsFlightPreManifestService.hiGet(id);
    }


    @RequiresPermissions("rms:rmsFlightPreManifest:hiView")
    @RequestMapping("hiList")
    @ResponseBody
    public DataGrid<RmsFlightPreManifest> hiList(RmsFlightPreManifest rmsFlightPreManifest, String dateStr) {
        return new DataGrid<>(rmsFlightPreManifestService.findHiFlightPreManifestList(rmsFlightPreManifest, dateStr));
    }

    @RequiresPermissions("rms:rmsFlightPreManifest:hiEdit")
    @RequestMapping(value = "getHiFlightPreManifests")
    @ResponseBody
    public Message getHiFlightPreManifests(String flightDynamicId, Message message) {
        rmsFlightPreManifestService.getHiFlightPreManifests(flightDynamicId, message);
        return message;
    }

    @RequiresPermissions("rms:rmsFlightPreManifest:hiEdit")
    @RequestMapping(value = "hiSave")
    @ResponseBody
    public Message hiSave(HttpServletRequest request, Model model, Message message) {
        List<RmsFlightPreManifest> list = new ArrayList<RmsFlightPreManifest>();
        list = this.fitParamToRmsFlightPreManifests(request);
        if (list != null && list.size() > 0) {
            for (RmsFlightPreManifest enty : list) {
                if (!beanValidator(model, enty)) {
                    message.setMessage("数据校验错误!");
                }
                try {
                    rmsFlightPreManifestService.hiSave(enty, message);
                    if (message.isSuccess()) {
                        LogUtils.saveLog(request, message.getMessage());
                        message.setMessage("更新成功！");
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
    public void expExcel(RmsFlightPreManifest manifest, String dateStr, HttpServletRequest request, HttpServletResponse response) {
        String datasJson = request.getParameter("fields");
        JSONArray jsonArray = null;
        if (StringUtils.isNotBlank(datasJson)) {
            jsonArray = JSON.parseArray(datasJson);
        } else {
            String datas = FileUtils.readJson("preManifest");
            jsonArray = JSON.parseArray(datas.toString());
        }

        List<String> fields = new ArrayList<>();
        String[] rowsName = rmsFlightPreManifestService.jsonTofiled(jsonArray, fields);

        String expDate = dateStr;
        List<Object[]> dataList = new ArrayList<>();

        List<RmsFlightPreManifest> listtemp = null;
        if (StringUtils.isNotBlank(dateStr)) {
            listtemp = rmsFlightPreManifestService.findHiFlightPreManifestList(manifest, dateStr);
        } else {
            listtemp = rmsFlightPreManifestService.findFlightPreManifestList(manifest);
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
//                        filedList.add(DateUtils.formatDate(carousel.getDeparturePlanTime(), "yyyy-MM-dd HH:mm"));
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
     * 该方法用于填充航段数据
     *
     * @param addr
     * @param addrNature
     * @param flightDynamicId
     * @param flightDutys
     * @return
     */
    private List<RmsFlightPreManifest> setFlightPreManifestEntry(String addrCode, String addr, String addrNature, String flightDynamicId, List<RmsFlightPreManifest> flightDutys) {
        RmsFlightPreManifest flightPreManifest = new RmsFlightPreManifest();
        flightPreManifest.setAddrNature(addrNature);
        flightPreManifest.setFlightDynamicId(flightDynamicId);
        flightPreManifest.setAddr(addr);
        flightPreManifest.setAddrCode(addrCode);
        //如果地址(经停、到站等)不为空，查询此航段的数据.否则补足空数据
        if (StringUtils.isNotBlank(addrCode)) {
            List<RmsFlightPreManifest> results = rmsFlightPreManifestService.getPreManifest(flightPreManifest);
            if (results != null && results.size() > 0) {
                flightDutys.add(results.get(0));
            } else {
                //查询没有值机数据，补入空数据
                flightDutys.add(flightPreManifest);
            }
        } else {
            flightDutys.add(flightPreManifest);
        }
        return flightDutys;
    }


    /**
     * 将传递来的仓单数据组装成对象列表
     *
     * @param request
     * @return
     */
    private List<RmsFlightPreManifest> fitParamToRmsFlightPreManifests(HttpServletRequest request) {
        String flightDynamicId = request.getParameter("flightDynamicId");
        List<RmsFlightPreManifest> homeManifestSecList = new ArrayList<RmsFlightPreManifest>();
        String[] addrNatures = request.getParameterValues("addrNature");
        String[] ids = request.getParameterValues("id");
        String[] addrs = request.getParameterValues("addr");
        String[] addrCodes = request.getParameterValues("addrCode");

        String[] personCountPs = request.getParameterValues("personCountP");
        String[] childCountPs = request.getParameterValues("childCountP");
        String[] babyCountPs = request.getParameterValues("babyCountP");

        String[] personCountJs = request.getParameterValues("personCountJ");
        String[] childCountJs = request.getParameterValues("childCountJ");
        String[] babyCountJs = request.getParameterValues("babyCountJ");

        String[] goodsCounts = request.getParameterValues("goodsCount");
        String[] goodsWeights = request.getParameterValues("goodsWeight");
        String[] mailCounts = request.getParameterValues("mailCount");
        String[] mailWeights = request.getParameterValues("mailWeight");
        String[] luggageCounts = request.getParameterValues("luggageCount");
        String[] luggageWeights = request.getParameterValues("luggageWeight");
        //航段的地点性质不可能为空，且一定有经停地1、经停地2、到达地三个项目
        for (int i = 0; i < addrNatures.length; i++) {
            if (StringUtils.isNotBlank(addrCodes[i])) {
                RmsFlightPreManifest enty = new RmsFlightPreManifest();
                enty.setAddrNature(addrNatures[i]);
                enty.setId(ids[i]);
                enty.setAddr(addrs[i]);
                enty.setAddrCode(addrCodes[i]);
                enty.setPersonCountP(Integer.parseInt(personCountPs[i]));
                enty.setChildCountP(Integer.parseInt(childCountPs[i]));
                enty.setBabyCountP(Integer.parseInt(babyCountPs[i]));
                enty.setPersonCountJ(Integer.parseInt(personCountJs[i]));
                enty.setChildCountJ(Integer.parseInt(childCountJs[i]));
                enty.setBabyCountJ(Integer.parseInt(babyCountJs[i]));
                enty.setGoodsCount(Integer.parseInt(goodsCounts[i]));
                enty.setGoodsWeight(Float.parseFloat(goodsWeights[i]));
                enty.setMailCount(Integer.parseInt(mailCounts[i]));
                enty.setMailWeight(Float.parseFloat(mailWeights[i]));
                enty.setLuggageCount(Integer.parseInt(luggageCounts[i]));
                enty.setLuggageWeight(Float.parseFloat(luggageWeights[i]));
                enty.setFlightDynamicId(request.getParameter("flightDynamicId"));
                homeManifestSecList.add(enty);
            }
        }
        return homeManifestSecList;
    }

}