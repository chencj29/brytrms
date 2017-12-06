/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import cn.net.metadata.datasync.socketio.ConcurrentClientsHolder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.FlightPairWrapper;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.event.SyncTypeEnum;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.ams.utils.AddEventUtils;
import com.thinkgem.jeesite.modules.rms.entity.ExcelExport;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightDuty;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightDutyWrap;
import com.thinkgem.jeesite.modules.rms.service.FlightPlanPairService;
import com.thinkgem.jeesite.modules.rms.service.RmsFlightDutyService;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.logging.Logger;

/**
 * 值机数据管理Controller
 *
 * @author dingshuang
 * @version 2016-05-18
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/rmsFlightDuty")
public class RmsFlightDutyController extends BaseController {

    @Autowired
    private RmsFlightDutyService rmsFlightDutyService;

    @Autowired
    private FlightDynamicService flightDynamicService;

    @Autowired
    private FlightPlanPairService flightPlanPairService;

    @Autowired
    private ApplicationContext applicationContext;

    @RequiresPermissions("rms:rmsFlightDuty:view")
    @RequestMapping("view")
    public String view() {
        return "rms/rmsFlightDuty/rmsFlightDuty";
    }

    @RequiresPermissions("rms:rmsFlightDuty:view")
    @RequestMapping("get")
    @ResponseBody
    public RmsFlightDuty get(@RequestParam("id") String id) {
        return rmsFlightDutyService.get(id);
    }

	/*@RequiresPermissions("rms:rmsFlightDuty:view")
    @RequestMapping(value = {"list", ""})
	@ResponseBody
	public DataTablesPage list(Page<RmsFlightDuty> page, DataTablesPage dataTablesPage, RmsFlightDuty rmsFlightDuty, HttpServletRequest request, HttpServletResponse response) {
		dataTablesPage.setColumns(request,page, rmsFlightDuty);
		return rmsFlightDutyService.findDataTablesPage(page, dataTablesPage, rmsFlightDuty);
	}*/

    @RequiresPermissions("rms:rmsFlightDuty:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<RmsFlightDutyWrap> list(RmsFlightDutyWrap dutyWrap) {
        return new DataGrid<>(rmsFlightDutyService.findDutyWrapList(dutyWrap));
    }

    @RequiresPermissions("rms:rmsFlightDuty:view")
    @RequestMapping(value = "form")
    public String form(RmsFlightDuty rmsFlightDuty, Model model) {
        model.addAttribute("rmsFlightDuty", rmsFlightDuty);
        return "modules/rms/rmsFlightDutyForm";
    }

    @RequiresPermissions("rms:rmsFlightDuty:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(Model model, Message message, HttpServletRequest request, Map map) {
        //将传递来的值机数据组装成对象列表
        List<RmsFlightDuty> dutys = this.fitParamToRmsFlightDutys(request, map);
        if (dutys != null && dutys.size() > 0) {
            for (RmsFlightDuty rmsFlightDuty : dutys) {
                if (!beanValidator(model, rmsFlightDuty)) {
                    message.setMessage("数据校验错误!");
                }
                try {
                    if (StringUtils.isBlank(rmsFlightDuty.getId())) {
                        List<RmsFlightDuty> flightDutys = rmsFlightDutyService.findByFdidAndAddr(rmsFlightDuty);
                        if (!Collections3.isEmpty(flightDutys)) rmsFlightDuty.setId(flightDutys.get(0).getId());
                    }

                    rmsFlightDutyService.save(rmsFlightDuty, message);
                    if (message.isSuccess()) {
                        LogUtils.saveLog(request, message.getMessage());
                        message.setMessage("数据保存成功！");
                    }
                } catch (Exception e) {
                    message.setMessage(e.getMessage());
                }
            }
        }
        return message;
    }

    /**
     * 配对为ext6 动态为ext9 保存值机结载后VIP行李总数、中转行李总数 到ams显示  wjp_2017年9月8日10时17分
     *
     * @return
     */
    @RequiresPermissions("rms:rmsFlightDuty:edit")
    @RequestMapping(value = "loadSave")
    @ResponseBody
    public Message loadSave(Model model, Message message, HttpServletRequest request) {
        Map<String, Object> tatisticsLuggageCounts = new HashMap<>();
        Message msg = save(model, message, request, tatisticsLuggageCounts);

        if (msg.isSuccess()) {
            String flightDynamicId = (String) tatisticsLuggageCounts.get("flightDynamicId");
            tatisticsLuggageCounts.remove("flightDynamicId");
            try {
                if (StringUtils.isNotBlank(flightDynamicId)) {
                    FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
                    ArrayList<FlightDynamic> flightDynamics = new ArrayList<>();
                    ArrayList<FlightPlanPair> pairs = new ArrayList<>();
                    String msgFlightNum = "";
                    if (flightDynamic != null) {
                        flightDynamic.setRandom(UUID.randomUUID().toString());
                        flightDynamic.setExt9(JSON.toJSONString(tatisticsLuggageCounts));
                        flightDynamicService.save(flightDynamic);
                        flightDynamics.add(flightDynamic);
                        LogUtils.saveLog(request, LogUtils.msgFlightDynamic(flightDynamic) + "值机结载成功！");
                        msgFlightNum = flightDynamic.getFlightCompanyCode() + flightDynamic.getFlightNum();
                    }

                    FlightPlanPair pair = flightDynamicService.getPairByDynamic(flightDynamicId);
                    if (pair != null) {
                        pair.setRandom(UUID.randomUUID().toString());
                        pair.setExt6(JSON.toJSONString(tatisticsLuggageCounts));
                        flightPlanPairService.save(pair);
                        pairs.add(pair);
                    }

                    //添加事件驱动，将动态及配对数据同步到ams  VIP行李总数、中转行李总数 更新时限制范围
                    AddEventUtils.addEvent(flightDynamics, pairs, SyncTypeEnum.OTHER, applicationContext);

                    //刷新ams前端数据
                    ConcurrentClientsHolder.getSocket("/global/dynamics").emit("flightDynamicTime2Change", "");
                    if (StringUtils.isNotBlank(msgFlightNum)) {
                        Map<String, Object> params = new HashMap<>();
                        params.put("occur", System.currentTimeMillis());
                        params.put("message", msgFlightNum + "值机结载完成!");

                        try {
                            ConcurrentClientsHolder.getSocket("/dynamic_realtime_message").emit("dynamic_message_dispatcher", new ObjectMapper().writeValueAsString(params));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    msg.setMessage("值机结载成功！");

                }
            } catch (Exception e) {
                msg.setMsg(0, "值机结载保存出错：" + e.getMessage());
                logger.error("值机结载保存出错：{}", e.getMessage());
                //e.printStackTrace();
            }

        }
        return msg;
    }

    @RequiresPermissions("rms:rmsFlightDuty:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(RmsFlightDuty rmsFlightDuty, Message message) {
        try {
            rmsFlightDutyService.delete(rmsFlightDuty);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }


    @RequiresPermissions("rms:rmsFlightDuty:view")
    @RequestMapping(value = "getFlightDutys")
    @ResponseBody
    public Message getFlightDutys(String flightDynamicId, Message message) {
        try {
            //通过FlightDynamicId 获取该航班下的所有值机数据，没有数据的则自动补充
            List<RmsFlightDuty> flightDutys = new ArrayList<RmsFlightDuty>();
            //根据航班情况，计算航线信息,补充航某些航段没有的数据
            FlightDynamic dynamic = flightDynamicService.get(new FlightDynamic(flightDynamicId));
            String passAirport1Code = dynamic.getPassAirport1Code();
            String passAirport2Code = dynamic.getPassAirport2Code();
            String arrivalAirCode = dynamic.getArrivalAirportCode();
            //经停地1
            setFlightDutysEntry(passAirport1Code, dynamic.getPassAirport1Name(), "PASS1", flightDynamicId, flightDutys);
            //经停地2
            setFlightDutysEntry(passAirport2Code, dynamic.getPassAirport2Name(), "PASS2", flightDynamicId, flightDutys);
            //目的地
            setFlightDutysEntry(arrivalAirCode, dynamic.getArrivalAirportName(), "ARRI", flightDynamicId, flightDutys);
            message.setCode(1);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("flightDutys", flightDutys);
            message.setResult(map);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }


    //----------------------------------------------------历史 begin

    @RequiresPermissions("rms:rmsFlightDuty:hiView")
    @RequestMapping("hiView")
    public String hiView() {
        return "rms/rmsFlightDuty/rmsHiFlightDuty";
    }

    //获取历史值机数据
    @RequiresPermissions("rms:rmsFlightDuty:hiView")
    @RequestMapping(value = "getHiFlightDutys")
    @ResponseBody
    public Message getHiFlightDutys(String flightDynamicId, Message message) {
        rmsFlightDutyService.getHiFlightDutys(flightDynamicId, message);
        return message;
    }


    //获取历史动态数据
    @RequiresPermissions("rms:rmsFlightDuty:hiView")
    @RequestMapping("hiList")
    @ResponseBody
    public DataGrid<RmsFlightDutyWrap> hiList(RmsFlightDutyWrap dutyWrap, String dateStr) {
        return new DataGrid<>(rmsFlightDutyService.findHiDutyWrapList(dutyWrap, dateStr));
    }

    //保存历史
    @RequiresPermissions("rms:rmsFlightDuty:edit")
    @RequestMapping(value = "hiSave")
    @ResponseBody
    public Message hiSave(Model model, Message message, HttpServletRequest request, Map map) {
        //将传递来的值机数据组装成对象列表
        List<RmsFlightDuty> dutys = this.fitParamToRmsFlightDutys(request, map);
        if (dutys != null && dutys.size() > 0) {
            for (RmsFlightDuty rmsFlightDuty : dutys) {
                if (!beanValidator(model, rmsFlightDuty)) {
                    message.setMessage("数据校验错误!");
                }
                try {
                    rmsFlightDutyService.hiSave(rmsFlightDuty, message);
                    if (message.isSuccess()) {
                        LogUtils.saveLog(request, message.getMessage());
                        message.setMessage("数据保存成功！");
                    }
                } catch (Exception e) {
                    message.setMessage(e.getMessage());
                }
            }
        }
        return message;
    }
    //----------------------------------------------------历史 end

    /**
     * 值机excel数据导出
     *
     * @param request
     * @param response
     */
    @RequiresPermissions("rms:rmsFlightDuty:hiView")
    @RequestMapping("expExcel")
    public void expExcel(RmsFlightDutyWrap dutyWrap, String dateStr, HttpServletRequest request, HttpServletResponse response) {
        String datasJson = request.getParameter("fields");
        JSONArray jsonArray = null;
        if (StringUtils.isNotBlank(datasJson)) {
            jsonArray = JSON.parseArray(datasJson);
        } else {
            String datas = FileUtils.readJson("duty");
            jsonArray = JSON.parseArray(datas.toString());
        }

        List<String> fields = new ArrayList<>();
        String[] rowsName = rmsFlightDutyService.jsonTofiled(jsonArray, fields);

        String expDate = dateStr;
        List<Object[]> dataList = new ArrayList<>();
        List<RmsFlightDutyWrap> listtemp = null;
        if (StringUtils.isNotBlank(dateStr)) {
            listtemp = rmsFlightDutyService.findHiDutyWrapList(dutyWrap, dateStr);
        } else {
            listtemp = rmsFlightDutyService.findDutyWrapList(dutyWrap);
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
        excelExport.setFileName(expDate + "值机数据");

        excelExport.setRowsName(rowsName);
        excelExport.setDataList(dataList);
        excelExport.setRequest(request);
        excelExport.setResponse(response);
        //导出Excel公共方法调用
        excelExport.exp();
        logger.info(expDate + "“值机数据”导出成功");
    }

    /**
     * 该方法用于填充航段值机数据
     *
     * @param addr
     * @param addrNature
     * @param flightDynamicId
     * @param flightDutys
     * @return
     */

    private List<RmsFlightDuty> setFlightDutysEntry(String addrTwoCode, String addr, String addrNature, String flightDynamicId, List<RmsFlightDuty> flightDutys) {
        RmsFlightDuty rmsFlightDuty = new RmsFlightDuty();
        rmsFlightDuty.setAddrTwoNature(addrNature);
        rmsFlightDuty.setFlightDynamicId(flightDynamicId);
        rmsFlightDuty.setAddr(addr);
        rmsFlightDuty.setAddrTwoCode(addrTwoCode);
        //如果地址(经停、到站等)不为空，查询此航段的数据.否则补足空数据
        if (StringUtils.isNotBlank(addrTwoCode)) {
            List<RmsFlightDuty> resultDutys = rmsFlightDutyService.getFlightDutys(rmsFlightDuty);
            if (resultDutys != null && resultDutys.size() > 0) {
                flightDutys.add(resultDutys.get(0));
            } else {
                //查询没有值机数据，补入空数据
                flightDutys.add(rmsFlightDuty);
            }
        } else {
            flightDutys.add(rmsFlightDuty);
        }
        return flightDutys;
    }

    /**
     * 将传递来的值机数据组装成对象列表
     *
     * @param request
     * @return
     */
    private List<RmsFlightDuty> fitParamToRmsFlightDutys(HttpServletRequest request, Map<String, Object> tatisticsLuggageCounts) {
        String flightDynamicId = request.getParameter("flightDynamicId");
        List<RmsFlightDuty> dutyList = new ArrayList<RmsFlightDuty>();
        String[] addrTwoNatures = request.getParameterValues("addrTwoNature");
        String[] ids = request.getParameterValues("id");
        String[] addrs = request.getParameterValues("addr");
        String[] addrTwoCodes = request.getParameterValues("addrTwoCode");
        String[] personCounts = request.getParameterValues("personCount");
        String[] childCounts = request.getParameterValues("childCount");
        String[] babyCounts = request.getParameterValues("babyCount");
        String[] nonstopLuggageCounts = request.getParameterValues("nonstopLuggageCount");
        String[] nonstopLuggageWeights = request.getParameterValues("nonstopLuggageWeight");
        String[] transferLuggageCounts = request.getParameterValues("transferLuggageCount");
        String[] transferLuggageWeights = request.getParameterValues("transferLuggageWeight");
        String[] vipPersonCounts = request.getParameterValues("vipPersonCount");
        String[] vipSeats = request.getParameterValues("vipSeat");
        String[] vipLuggageCounts = request.getParameterValues("vipLuggageCount");
        String[] vipLuggageWeights = request.getParameterValues("vipLuggageWeight");
        String[] firstCabinPersonCounts = request.getParameterValues("firstCabinPersonCount");
        String[] firstCabinSeats = request.getParameterValues("firstCabinSeat");
        String[] businessCabinPersonCounts = request.getParameterValues("businessCabinPersonCount");
        String[] businessCabinSeats = request.getParameterValues("businessCabinSeat");

        //航段的地点性质不可能为空，且一定有经停地1、经停地2、到达地三个项目
        for (int i = 0; i < addrTwoNatures.length; i++) {
            if (StringUtils.isNotBlank(addrTwoCodes[i])) {
                RmsFlightDuty rmsFlightDuty = new RmsFlightDuty();
                rmsFlightDuty.setFlightDynamicId(flightDynamicId);
                rmsFlightDuty.setAddrTwoNature(addrTwoNatures[i]);
                rmsFlightDuty.setId(ids[i]);
                rmsFlightDuty.setAddr(addrs[i]);
                rmsFlightDuty.setAddrTwoCode(addrTwoCodes[i]);
                rmsFlightDuty.setPersonCount(StringUtils.toInteger(personCounts[i]));
                rmsFlightDuty.setChildCount(StringUtils.toInteger(childCounts[i]));
                rmsFlightDuty.setBabyCount(StringUtils.toInteger(babyCounts[i]));
                rmsFlightDuty.setNonstopLuggageCount(StringUtils.toInteger(nonstopLuggageCounts[i]));
                rmsFlightDuty.setNonstopLuggageWeight(StringUtils.toFloat(nonstopLuggageWeights[i]));
                rmsFlightDuty.setTransferLuggageCount(StringUtils.toInteger(transferLuggageCounts[i]));
                rmsFlightDuty.setTransferLuggageWeight(StringUtils.toFloat(transferLuggageWeights[i]));
                rmsFlightDuty.setVipPersonCount(StringUtils.toInteger(vipPersonCounts[i]));
                rmsFlightDuty.setVipSeat(vipSeats[i]);
                rmsFlightDuty.setVipLuggageCount(StringUtils.toInteger(vipLuggageCounts[i]));
                rmsFlightDuty.setVipLuggageWeight(StringUtils.toFloat(vipLuggageWeights[i]));
                rmsFlightDuty.setFirstCabinPersonCount(StringUtils.toInteger(firstCabinPersonCounts[i]));
                rmsFlightDuty.setFirstCabinSeat(firstCabinSeats[i]);
                rmsFlightDuty.setBusinessCabinPersonCount(StringUtils.toInteger(businessCabinPersonCounts[i]));
                rmsFlightDuty.setBusinessCabinSeat(businessCabinSeats[i]);
                dutyList.add(rmsFlightDuty);

                //统计各种行李件数 wjp_2017年9月7日18时44分 需求新增
                if (tatisticsLuggageCounts.get("flightDynamicId") == null) {
                    tatisticsLuggageCounts.put("flightDynamicId", flightDynamicId);
                }

                if (tatisticsLuggageCounts.get("nonstop") == null) {
                    tatisticsLuggageCounts.put("nonstop", rmsFlightDuty.getNonstopLuggageCount());
                } else {
                    tatisticsLuggageCounts.put("nonstop", rmsFlightDuty.getNonstopLuggageCount() + (Integer) tatisticsLuggageCounts.get("nonstop"));
                }

                if (tatisticsLuggageCounts.get("transfer") == null) {
                    tatisticsLuggageCounts.put("transfer", rmsFlightDuty.getTransferLuggageCount());
                } else {
                    tatisticsLuggageCounts.put("transfer", rmsFlightDuty.getTransferLuggageCount() + (Integer) tatisticsLuggageCounts.get("transfer"));
                }
                if (tatisticsLuggageCounts.get("vip") == null) {
                    tatisticsLuggageCounts.put("vip", rmsFlightDuty.getVipLuggageCount());
                } else {
                    tatisticsLuggageCounts.put("vip", rmsFlightDuty.getVipLuggageCount() + (Integer) tatisticsLuggageCounts.get("vip"));
                }

                //统计各人数 wjp_2017年11月7日14时10分
                if (tatisticsLuggageCounts.get("personCount") == null) {
                    tatisticsLuggageCounts.put("personCount", rmsFlightDuty.getPersonCount());
                } else {
                    tatisticsLuggageCounts.put("personCount", rmsFlightDuty.getPersonCount() + (Integer) tatisticsLuggageCounts.get("personCount"));
                }

                if (tatisticsLuggageCounts.get("childCount") == null) {
                    tatisticsLuggageCounts.put("childCount", rmsFlightDuty.getChildCount());
                } else {
                    tatisticsLuggageCounts.put("childCount", rmsFlightDuty.getChildCount() + (Integer) tatisticsLuggageCounts.get("childCount"));
                }

                if (tatisticsLuggageCounts.get("babyCount") == null) {
                    tatisticsLuggageCounts.put("babyCount", rmsFlightDuty.getBabyCount());
                } else {
                    tatisticsLuggageCounts.put("babyCount", rmsFlightDuty.getBabyCount() + (Integer) tatisticsLuggageCounts.get("babyCount"));
                }
            }
        }
        return dutyList;
    }
}