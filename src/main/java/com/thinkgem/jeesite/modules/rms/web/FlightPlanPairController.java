/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.2016 change;
 */
package com.thinkgem.jeesite.modules.rms.web;

import cn.net.metadata.datasync.socketio.ConcurrentClientsHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.PropertiesLoader;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.dao.FlightPlanPairDao;
import com.thinkgem.jeesite.modules.rms.entity.FlightPairProgressInfo;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;
import com.thinkgem.jeesite.modules.rms.entity.SpecialServices;
import com.thinkgem.jeesite.modules.rms.entity.Vipplan;
import com.thinkgem.jeesite.modules.rms.service.*;
import com.thinkgem.jeesite.modules.rms.wrapper.AddProgressWrapper;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 航班动态配对Controller
 *
 * @author xiaowu
 * @version 2016-05-04
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/flightPlanPair")
public class FlightPlanPairController extends BaseController {

    @Autowired
    FlightPairProgressInfoService flightPairProgressInfoService;
    @Autowired
    ProgressPostPermissionService progressPostPermissionService;
    @Autowired
    private FlightPlanPairService flightPlanPairService;
    @Autowired
    private SpecialServicesService specialServicesService;
    @Autowired
    private VipplanService vipplanService;
    @Autowired
    private FlightPlanPairDao flightPlanPairDao;

    private final static PropertiesLoader REALTIME_DYNAMIC_MESSAGE_CONFS = new PropertiesLoader("classpath:notification.realtime-dynamic-message-conf/config.properties");


    private static final String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";

    @RequiresPermissions("rms:flightPlanPair:view")
    @RequestMapping("view")
    public String view() {
        return "rms/flightPlanPair/flightPlanPair";
    }

    @RequiresPermissions("rms:flightPlanPair:view")
    @RequestMapping("get")
    @ResponseBody
    public FlightPlanPair get(@RequestParam("id") String id) {
        return flightPlanPairService.get(id);
    }

    @RequiresPermissions("rms:flightPlanPair:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<FlightPlanPair> list(FlightPlanPair flightPlanPair) {
        return new DataGrid<>(flightPlanPairService.findList(flightPlanPair));
    }

    @RequiresPermissions("rms:flightPlanPair:view")
    @RequestMapping("listPage")
    @ResponseBody
    public DataGrid<FlightPlanPair> listPage(Integer page, Integer rows) {
        List<FlightPlanPair> pList = flightPlanPairService.findList(new FlightPlanPair()).stream().filter(pair -> StringUtils.isNotEmpty(pair.getFlightNum()) || StringUtils.isNotEmpty(pair.getFlightNum2())).collect(Collectors.toList());
        //配对排序 wjp_2016年7月28日12时14分
        Date maxDate = DateUtils.addYears(new Date(), 5);
        final Function<FlightPlanPair, Date> byPlanDate = pair -> pair.getPlanDate(); //按航班日期
        final Function<FlightPlanPair, Date> byAtd = pair -> pair.getAtd2() == null ? maxDate : pair.getAtd2();//按出港实飞
        final Function<FlightPlanPair, Date> byAta = pair -> pair.getAta() == null ? maxDate : pair.getAta();//按进港实达
        final Function<FlightPlanPair, Date> byInAtd = pair -> pair.getAtd() == null ? maxDate : pair.getAtd();//按进港实飞
        final Function<FlightPlanPair, Date> byEtd = pair -> pair.getEtd2() == null ? maxDate : pair.getEtd2();//按出港预飞
        final Function<FlightPlanPair, Date> byDeparture = pair -> pair.getDeparturePlanTime2() == null ? maxDate : pair.getDeparturePlanTime2();//按出港计飞
        final Function<FlightPlanPair, Date> byEta = pair -> pair.getEta() == null ? maxDate : pair.getEta();//按进港预达
        final Function<FlightPlanPair, Date> byArrival = pair -> pair.getArrivalPlanTime() == null ? maxDate : pair.getArrivalPlanTime();//按进港计达
        final Function<FlightPlanPair, Date> byInDeparture = pair -> pair.getDeparturePlanTime() == null ? maxDate : pair.getDeparturePlanTime();//按进港计飞

        //配对表排序，分为四个部分，出港已起飞/出港未起飞，进港到达进港起飞/其他
        List<FlightPlanPair> pairDList = new ArrayList<>();//出港已起飞
        List<FlightPlanPair> pairIList = new ArrayList<>();//进港到达
        List<FlightPlanPair> pairIDList = new ArrayList<>();//进港已起飞
        List<FlightPlanPair> pairEList = new ArrayList<>();//其他
        for (FlightPlanPair fpw : pList) {
            if (fpw.getStatusName2() != null && fpw.getStatusName2().indexOf("已起飞") > -1) {
                pairDList.add(fpw);
            } else {
                if (fpw.getStatusName() != null && fpw.getStatusName().indexOf("到达") > -1) {
                    pairIList.add(fpw);
                } else if (fpw.getStatusName() != null && fpw.getStatusName().indexOf("前方起飞") > -1) {
                    pairIDList.add(fpw);
                } else {
                    pairEList.add(fpw);
                }
            }
        }

        pairDList = pairDList.stream().sorted(
                Comparator.comparing(byPlanDate).thenComparing(byAtd).thenComparing(byDeparture).thenComparing(byAta).thenComparing(byEta).thenComparing(byArrival)
        ).collect(Collectors.toList());
        pairIList = pairIList.stream().sorted(
                Comparator.comparing(byPlanDate).thenComparing(byDeparture).thenComparing(byAta).thenComparing(byEta).thenComparing(byArrival)
        ).collect(Collectors.toList());
        pairIDList = pairIDList.stream().sorted(
                Comparator.comparing(byPlanDate).thenComparing(byEta).thenComparing(byArrival)
        ).collect(Collectors.toList());
        pairEList = pairEList.stream().sorted(
                Comparator.comparing(byPlanDate).thenComparing(byDeparture).thenComparing(byArrival)
        ).collect(Collectors.toList());
        List<FlightPlanPair> pairPairList = new ArrayList<>();
        pairPairList.addAll(pairDList);
        pairPairList.addAll(pairIList);
        pairPairList.addAll(pairIDList);
        pairPairList.addAll(pairEList);

        //pairPairList = pairPairList.stream().sorted(Comparator.comparing(byPlanDate)).collect(Collectors.toList());

        //配对表排序 排完序后的数据
        List<FlightPlanPair> result = pairPairList.stream().sorted(Comparator.comparing(byPlanDate))
                .map(pair -> {
                    vipidsToPair(pair);//添加vip信息
                    return pair;
                }).collect(Collectors.toList());

//        List<FlightPlanPair> pairPairList = pList.stream().sorted(
//                Comparator.comparing(byPlanDate).thenComparing(byAtd).thenComparing(byAta).thenComparing(byInAtd).thenComparing(byEtd)
//                        .thenComparing(byDeparture).thenComparing(byEta).thenComparing(byArrival).thenComparing(byInDeparture)
//        ).map(pair -> {
//            vipidsToPair(pair);//添加vip信息
//            return pair;
//        }).collect(Collectors.toList());
        return new DataGrid<>(result);
    }

    @RequiresPermissions("rms:flightPlanPair:view")
    @RequestMapping(value = "form")
    public String form(FlightPlanPair flightPlanPair, Model model) {
        model.addAttribute("flightPlanPair", flightPlanPair);
        return "modules/rms/flightPlanPairForm";
    }

    @RequiresPermissions("rms:flightPlanPair:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(FlightPlanPair flightPlanPair, Model model, Message message) {
        if (!beanValidator(model, flightPlanPair)) {
            message.setMessage("数据校验错误!");
        }
        try {
            flightPlanPairService.save(flightPlanPair);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:flightPlanPair:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(FlightPlanPair flightPlanPair, Message message) {
        try {
            flightPlanPairService.delete(flightPlanPair);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:flightPlanPair:view")
    @RequestMapping("queryFlightTimeLong")
    @ResponseBody
    public Map<String, Object> queryFlightTimeLong(String pairId) {
        return flightPlanPairService.queryFlightTimeLong(pairId);
    }

    @RequiresPermissions("rms:flightPlanPair:view")
    @RequestMapping("specialServiceList")
    @ResponseBody
    public DataGrid<SpecialServices> specialServiceList(SpecialServices specialServices) {
        specialServices.setFlightNum(specialServices.getFlightCompanyCode() + specialServices.getFlightNum());
        return new DataGrid<>(specialServicesService.findSpecialServicesList(specialServices));
    }

    @RequiresPermissions("rms:flightPlanPair:view")
    @RequestMapping("queryFlightTimeLongForDatagrid")
    @ResponseBody
    public DataGrid<FlightPairProgressInfo> queryFlightTimeLongForDatagrid(String pairId) {
        List<FlightPairProgressInfo> infoList = Lists.newArrayList();
        if (StringUtils.isNotEmpty(pairId)) {
            infoList = flightPairProgressInfoService.queryByPairId(pairId);
            if (infoList == null || infoList.isEmpty())
                infoList = flightPlanPairService.generateProgressInfos(flightPlanPairService.get(pairId));
        }
        infoList.sort((info1, info2) -> info1.getPlanStartTime().before(info2.getPlanStartTime()) ? -1 : info1.getPlanStartTime().equals(info2.getPlanStartTime()) ? 0 : 1);
        return new DataGrid<>(infoList);
    }

    @RequestMapping("getPrivileges")
    @ResponseBody
    public List<String> getPrivileges() {
        List<String> privileges = Lists.newArrayList();

        try {
            String officeId = UserUtils.getUser().getOffice().getId();
            privileges = progressPostPermissionService.listPrivileges(officeId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return privileges;
    }

    @RequiresPermissions("rms:flightPlanPair:view")
    @RequestMapping("removeProgress")
    @ResponseBody
    public Message removeProgress(String progressId, String pairId, String random) {
        Message message = new Message();

        FlightPairProgressInfo info = flightPairProgressInfoService.get(progressId);

        if (info == null || !flightPairProgressInfoService.checkPairExists(pairId)) {
            message.setCode(0);
            message.setMessage("不存在的过程或航班配对编码!");
            return message;
        }

        try {
            info.setRandom(random);
            flightPairProgressInfoService.delete(info);
            message.setCode(1);
            message.setMessage("移除成功");
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage(e.getMessage());
        }

        return message;
    }


    @RequiresPermissions("rms:flightPlanPair:edit")
    @RequestMapping("modifySafeguardTypeCode")
    @ResponseBody
    public Message modifySafeguardTypeCode(String pairId, String safeguardTypeCode, String safeguardTypeName, String random, Message message, HttpServletRequest request) {
        FlightPlanPair flightPlanPair = get(pairId);

        if (flightPlanPair == null) {
            message.setMsg(0, "不存在的航班配对信息「id = " + pairId + "」");
            return message;
        }

        // 根据航班信息查询该飞机的座位数量
        Long seatNum = flightPlanPairDao.querySeatNumByAircraftNum(flightPlanPair.getAircraftNum());
        // 设置默认座位数
        seatNum = seatNum != null ? seatNum : flightPlanPairDao.getMinimalSeatNumByAircraftNum(safeguardTypeCode);

        if (seatNum == null) {
            message.setMsg(0, "找不到符合当前保障类型「code = " + safeguardTypeCode + ", name = " + safeguardTypeName + "」的座位记录，请在「运输保障 - 保障过程图」中进行设置");
            return message;
        }

        try {
            flightPlanPair.setRandom(random);
            flightPlanPairService.modifySafeguardTypeCode(flightPlanPair, safeguardTypeCode, safeguardTypeName, message);
            if (message.isSuccess()) LogUtils.saveLog(request, "更新保障类型成功！" + message.getMessage());
        } catch (Exception e) {
            message.setMessage(e.getMessage());
            e.printStackTrace();
        }

        return message;
    }

    private void progressActTimeMessageSender(String fmtName, FlightPairProgressInfo info, String time) {
        FlightPlanPair flightPlanPair = flightPlanPairService.get(info.getPairId());

        if (flightPlanPair != null) {
            String flightNumber = "";
            if (StringUtils.isNotEmpty(flightPlanPair.getFlightNum2()))
                flightNumber += flightPlanPair.getFlightCompanyCode2() + flightPlanPair.getFlightNum2();
            if (StringUtils.isNotEmpty(flightPlanPair.getFlightNum2()) && StringUtils.isNotEmpty(flightPlanPair.getFlightNum()))
                flightNumber += "/";
            if (StringUtils.isNotEmpty(flightPlanPair.getFlightNum()))
                flightNumber += flightPlanPair.getFlightCompanyCode() + flightPlanPair.getFlightNum();

            Map<String, Object> params = new HashMap<>();
            params.put("occur", System.currentTimeMillis());
            params.put("message", String.format(REALTIME_DYNAMIC_MESSAGE_CONFS.getProperty(fmtName), flightNumber, info.getProgressName(), time));

            try {
                ConcurrentClientsHolder.getSocket("/dynamic_realtime_message").emit("dynamic_message_dispatcher", new ObjectMapper().writeValueAsString(params));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("set-progress-act-time")
    @ResponseBody
    public Message setProgressActTime(String start, String over, String opName, String carType, String carCode, String progressId, String random, HttpServletRequest request) {
        Message message = new Message();
        if (StringUtils.isEmpty(progressId)) {
            message.setCode(0);
            message.setMessage("不存在的保障过程");
            return message;
        }

        try {

            FlightPairProgressInfo info = flightPairProgressInfoService.get(progressId);

            if (null == info) {
                message.setCode(0);
                message.setMessage("不存在的保障过程");
                return message;
            }

            info.setRandom(random);

            FlightPlanPair pair = flightPlanPairService.get(info.getPairId());
            StringBuffer msg = new StringBuffer(pair != null ? LogUtils.msgPair(pair) : "").append(",详情[原：(");
            msg.append(info.toString()).append(");现：(");

            // 发送实际开始时间的消息
//            if (StringUtils.isNotEmpty(start) && !DateUtils.parseDate(start, "yyyy-MM-dd HH:mm").equals(DateUtils.formatDate(info.getActualStartTime(), yyyyMMddHHmm)))
            if (StringUtils.isNotEmpty(start) && !start.equals(DateUtils.formatDate(info.getActualStartTime(), yyyyMMddHHmm)))
                progressActTimeMessageSender("PROGRESS_ACTUAL_START_TIME_RECORDING", info, start);

            // 发送实际结束时间的消息
            if (StringUtils.isNotEmpty(over) && !over.equals(DateUtils.formatDate(info.getActualOverTime(), yyyyMMddHHmm)))
                progressActTimeMessageSender("PROGRESS_ACTUAL_OVER_TIME_RECORDING", info, over);


            info.setActualStartTime(StringUtils.isBlank(start) ? null : DateUtils.parseDate(start, yyyyMMddHHmm));
            info.setActualOverTime(StringUtils.isBlank(over) ? null : DateUtils.parseDate(over, yyyyMMddHHmm));
            info.setOpName(opName);
            info.setSpecialCarType(carType);
            info.setSpecialCarCode(carCode);

            flightPairProgressInfoService.save(info);
            msg.append(info.toString()).append(")]");
            LogUtils.saveLog(request, "保障子过程修改成功!" + msg.toString());
            message.setCode(1);
            message.setMessage("设置成功!");
        } catch (Exception e) {
            message.setCode(0);
            message.setMessage(e.getMessage());
        }

        return message;
    }

    @RequestMapping("addProgress")
    @ResponseBody
    public Message addProgress(@RequestBody AddProgressWrapper wrapper, HttpServletRequest request) {
        Message message = new Message();
        try {
            flightPlanPairService.addProgress(wrapper, message);
            if (message.isSuccess()) LogUtils.saveLog(request, message.toString());
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage(e.getMessage());
        }
        return message;
    }

    public void vipidsToPair(FlightPlanPair flightPlanPair) {
        if (com.thinkgem.jeesite.common.utils.StringUtils.isNotBlank(flightPlanPair.getFlightDynimicId())) {
            Vipplan tmp = new Vipplan();
            tmp.setInout(flightPlanPair.getInoutTypeCode());
            tmp.setPlandate(flightPlanPair.getPlanDate());
            tmp.setAircorp(flightPlanPair.getFlightCompanyCode());
            tmp.setFltno(flightPlanPair.getFlightNum());
            List<String> vipids = vipplanService.findVipList(tmp).stream().map(vip -> vip.getId()).collect(Collectors.toList());
            if (vipids != null && vipids.size() > 0)
                flightPlanPair.setVip(com.thinkgem.jeesite.common.utils.StringUtils.join(vipids, ","));
        }
        if (com.thinkgem.jeesite.common.utils.StringUtils.isNotBlank(flightPlanPair.getFlightDynimicId2())) {
            Vipplan tmp = new Vipplan();
            tmp.setInout(flightPlanPair.getInoutTypeCode2());
            tmp.setPlandate(flightPlanPair.getPlanDate());
            tmp.setAircorp(flightPlanPair.getFlightCompanyCode2());
            tmp.setFltno(flightPlanPair.getFlightNum2());
            List<String> vipids = vipplanService.findVipList(tmp).stream().map(vip -> vip.getId()).collect(Collectors.toList());
            if (vipids != null && vipids.size() > 0)
                flightPlanPair.setVip2(com.thinkgem.jeesite.common.utils.StringUtils.join(vipids, ","));
        }
    }
}