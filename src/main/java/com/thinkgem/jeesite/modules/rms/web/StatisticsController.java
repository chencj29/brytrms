package com.thinkgem.jeesite.modules.rms.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamicPairHistory;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicHistoryService;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicPairHistoryService;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.ams.service.SafeguardHistoryService;
import com.thinkgem.jeesite.modules.rms.dao.FlightPlanPairDao;
import com.thinkgem.jeesite.modules.rms.entity.*;
import com.thinkgem.jeesite.modules.rms.service.SafeguardSeatTimelongService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wjp on 2016/05/18.
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/statistics")
public class StatisticsController extends BaseController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FlightPlanPairDao flightPlanPairDao;
    @Autowired
    private SafeguardSeatTimelongService safeguardSeatTimelongService;
    @Autowired
    FlightDynamicService flightDynamicService;
    @Autowired
    FlightDynamicHistoryService flightDynamicHistoryService;
    @Autowired
    FlightDynamicPairHistoryService flightDynamicPairHistoryService;
    @Autowired
    SafeguardHistoryService safeguardHistoryService;

    //----------------------------------------机位使用统计---------------------------------begin
    private Map<String, Integer> flightGateUsedtemp = new HashMap<>();

    @RequiresPermissions("rms:statistics:flightGateUsed")
    @RequestMapping("flightGateUsed")
    public String view() {
        return "rms/statistics/flightGateUsed";
    }

    @RequiresPermissions("rms:statistics:flightGateUsed")
    @RequestMapping("flightGateUsedList")
    @ResponseBody
    public String list(String startTime_begin, String overTime_end) {
        StringBuffer sql = new StringBuffer().append("select ACTUAL_GATE_NUM as gateNum from  rms_gate_occupying_info_hi where 1=1");
        if (StringUtils.isNotBlank(startTime_begin)) {
            sql.append(" And ((start_time >= to_date('" + startTime_begin + " 00:00:00','yyyy-mm-dd hh24:mi:ss'))");
            sql.append(" or (over_time >= to_date('" + startTime_begin + " 00:00:00','yyyy-mm-dd hh24:mi:ss')))");
        }
        if (StringUtils.isNotBlank(overTime_end)) {
            sql.append(" And ((over_time <= to_date('" + overTime_end + " 23:59:59','yyyy-mm-dd hh24:mi:ss'))");
            sql.append(" or (start_time <= to_date('" + overTime_end + " 23:59:59','yyyy-mm-dd hh24:mi:ss')))");
        }
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
        Map<String, Integer> result = new HashMap<>();
        list.forEach(a -> {
            if (a.get("gateNum") != null) {
                String[] gateNums = StringUtils.split((String) a.get("gateNum"), ",");
                for (int i = 0; i < gateNums.length; i++) {
                    result.put(gateNums[i], result.get(gateNums[i]) != null ? result.get(gateNums[i]) + 1 : 1);
                }
            }
        });
        flightGateUsedtemp.clear();
        flightGateUsedtemp = result;//将查询的数据暂存,为导出准备
        logger.info("“机位使用统计”查询成功");
        return StringUtils.mapToEasyJson(result);
    }

    @RequiresPermissions("rms:statistics:flightGateUsed")
    @RequestMapping("flightGateUsedExpExcel")
    public void expExcel(HttpServletRequest request, HttpServletResponse response) {
        List<Object[]> dataList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : flightGateUsedtemp.entrySet()) {
            dataList.add(new Object[]{entry.getKey(), entry.getValue()});
        }
        ExcelExport excelExport = new ExcelExport();
        excelExport.setTitle("机位使用量统计");
        excelExport.setFileName("机位使用量统计");
        excelExport.setRowsName(new String[]{"机位", "使用次数"});
        excelExport.setDataList(dataList);
        excelExport.setRequest(request);
        excelExport.setResponse(response);
        //导出Excel公共方法调用
        excelExport.exp();
        logger.info("“机位使用统计”导出成功");
    }

    //----------------------------------------机位使用统计---------------------------------end
//----------------------------------------运输资源使用量统计---------------------------------begin
    private Map<String, Integer> transRestemp = new HashMap<>();

    @RequiresPermissions("rms:transResStatistics:view")
    @RequestMapping("transRes")
    public String transResView(Model model) {
        model.addAttribute("statisticsName", "transRes");
        return "rms/statistics/transRes";
    }

    @RequiresPermissions("rms:transResStatistics:view")
    @RequestMapping("transResList")
    @ResponseBody
    public String transResList(String startTime_begin, String overTime_end) {
        if (StringUtils.isBlank(startTime_begin) && StringUtils.isBlank(overTime_end)) return "";
        Map<String, Integer> result = new HashMap<>();
        final String sql_process = "SELECT safeguard_process_name name,count(safeguard_process_name) as num "
                + " from rms_safeguard_seat_timelong where safeguard_process_id=? GROUP BY safeguard_process_name ";

        StringBuffer sql = new StringBuffer("SELECT SAFECUARD_TYPE_CODE as \"typeCode\",FLIGHT_DYNIMIC_ID as \"fid1\",FLIGHT_DYNIMIC_ID2 as \"fid2\",AIRCRAFT_NUM as \"aircraftNum\" ");
        sql.append(" from AMS_FLIGHT_DYNAMIC_PAIR_H where 1=1 ");
        if (StringUtils.isNotBlank(startTime_begin)) {
            sql.append(" And ((DEPARTURE_PLAN_TIME >= to_date('" + startTime_begin + " 00:00:00','yyyy-mm-dd hh24:mi:ss'))");
            sql.append(" or (ARRIVAL_PLAN_TIME2 >= to_date('" + startTime_begin + " 00:00:00','yyyy-mm-dd hh24:mi:ss'))");
            sql.append(" or (ARRIVAL_PLAN_TIME2 >= to_date('" + startTime_begin + " 00:00:00','yyyy-mm-dd hh24:mi:ss'))");
            sql.append(" or (ARRIVAL_PLAN_TIME >= to_date('" + startTime_begin + " 00:00:00','yyyy-mm-dd hh24:mi:ss')))");
        }
        if (StringUtils.isNotBlank(overTime_end)) {
            sql.append(" And ((ARRIVAL_PLAN_TIME <= to_date('" + overTime_end + " 23:59:59','yyyy-mm-dd hh24:mi:ss'))");
            sql.append(" or (DEPARTURE_PLAN_TIME2 <= to_date('" + overTime_end + " 23:59:59','yyyy-mm-dd hh24:mi:ss'))");
            sql.append(" or (DEPARTURE_PLAN_TIME2 <= to_date('" + overTime_end + " 23:59:59','yyyy-mm-dd hh24:mi:ss'))");
            sql.append(" or (DEPARTURE_PLAN_TIME <= to_date('" + overTime_end + " 23:59:59','yyyy-mm-dd hh24:mi:ss')))");
        }

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
        list.forEach(pair -> {
            // 根据航班信息查询对应的时长及过程
            String typeCode = (String) pair.get("typeCode");
            String id1 = (String) pair.get("fid1");
            String id2 = (String) pair.get("fid2");

            typeCode = StringUtils.isNoneBlank(typeCode) ? typeCode : (StringUtils.isNoneBlank(id1, id2) ? "lb00" : (StringUtils.isNotBlank(id1) ? "dj00" : "dc00"));

            // 根据航班信息查询该飞机的座位数量
            Long seatNum = flightPlanPairDao.querySeatNumByAircraftNum((String) pair.get("aircraftNum"));
            // 设置默认座位数

            seatNum = seatNum != null ? seatNum : flightPlanPairDao.getMinimalSeatNumByAircraftNum(typeCode);

            // 根据座位数和默认保障过程查询各个子过程的时长 RMS_SAFEGUARD_SEAT_TIMELONG
            SafeguardSeatTimelong timelong = new SafeguardSeatTimelong();
            timelong.setSeatNum(seatNum);
            timelong.setSafeguardTypeCode(typeCode);
            List<SafeguardSeatTimelong> safeguardSeatTimelongs = safeguardSeatTimelongService.queryListBySeatSafeguardTypeCode(timelong);

            safeguardSeatTimelongs.forEach(sst -> {
                String safeguard_process_id = sst.getSafeguardProcess().getId();
                if (StringUtils.isNotBlank(safeguard_process_id)) {
                    List<Map<String, Object>> safePro = jdbcTemplate.queryForList(sql_process, safeguard_process_id);
                    safePro.forEach(map -> {
                        String key = (String) map.get("name");
                        Integer value = Integer.valueOf(map.get("num").toString());
                        result.put(key, result.get(key) != null ? result.get(key) + value : value);
                    });
                }
            });
        });
        transRestemp.clear();
        transRestemp = result;
        logger.info("“运输资源使用量统计”查询成功");
        return StringUtils.mapToEasyJson(result);
    }

    @RequiresPermissions("rms:transResStatistics:view")
    @RequestMapping("transResExpExcel")
    public void transResExpExcel(HttpServletRequest request, HttpServletResponse response) {
        List<Object[]> dataList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : transRestemp.entrySet()) {
            dataList.add(new Object[]{entry.getKey(), entry.getValue()});
        }
        ExcelExport excelExport = new ExcelExport();
        excelExport.setTitle("资源使用量统计");
        excelExport.setFileName("资源使用量统计");
        excelExport.setRowsName(new String[]{"资源类型", "使用次数"});
        excelExport.setDataList(dataList);
        excelExport.setRequest(request);
        excelExport.setResponse(response);
        //导出Excel公共方法调用
        excelExport.exp();
        logger.info("“运输资源使用量统计”导出成功");
    }

    //----------------------------------------运输资源使用量统计---------------------------------end
//----------------------------------------员工工作量统计---------------------------------begin
    @RequiresPermissions("rms:statistics:staff")
    @RequestMapping("/staffStatistics")
    public String staffStatistics(Model model) {

        model.addAttribute("statisticsName", "staffStatistics");
        return "rms/statistics/staffStatistics";
    }

    //----------------------------------------员工工作量统计---------------------------------end
//----------------------------------------机位信息查询---------------------------------begin
    private List<FlightDynamicPairHistory> flightGatetemp = new ArrayList<>();

    @RequiresPermissions("rms:flightGate:view")
    @RequestMapping("flightGate")
    public String flightGateView(Model model) {
        model.addAttribute("statisticsName", "flightGate");
        return "rms/statistics/flightGate";
    }

    @RequiresPermissions("rms:flightGate:view")
    @RequestMapping("flightGateList")
    @ResponseBody
    public DataGrid<FlightDynamicPairHistory> flightGateList(FlightPlanPair fpp) {
        if (fpp.getPlanDate() == null && StringUtils.isBlank(fpp.getFlightNum()) && StringUtils.isBlank(fpp.getPlaceNum())) {
            return new DataGrid<>(new ArrayList<>());
        }

        String placeNum = StringUtils.isNotBlank(fpp.getPlaceNum()) ? fpp.getPlaceNum() : null;

        FlightDynamicPairHistory param = new FlightDynamicPairHistory();
        param.setPlanDate(fpp.getPlanDate());
        param.setFlightNum(fpp.getFlightNum());

        List<FlightDynamicPairHistory> list_fpp = flightDynamicPairHistoryService.findList(param).stream().filter(
                        a -> placeNum != null ? (a.getPlaceNum() != null ? Arrays.asList(StringUtils.split(a.getPlaceNum(), ",")).contains(placeNum) : false || a.getPlaceNum2() != null ? Arrays.asList(StringUtils.split(a.getPlaceNum2(), ",")).contains(placeNum) : false) : true
                ).collect(Collectors.toList());
        flightGatetemp = list_fpp;
        logger.info("“机位信息查询”查询成功");
        return new DataGrid<>(list_fpp);
    }

    @RequiresPermissions("rms:flightGate:view")
    @RequestMapping("flightGateExpExcel")
    public void flightGateExpExcel(HttpServletRequest request, HttpServletResponse response) {
        String datas = FileUtils.readJson("flightGate");

        JSONArray jsonArray = JSON.parseArray(datas.toString());
        List<String> fields = new ArrayList<>();
        String[] rowsName = jsonTofiled(jsonArray, fields);

        List<Object[]> dataList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        flightGatetemp.forEach(a -> {
            List filedList = new ArrayList();
            fields.forEach(filed -> {
                try {
                    if (StringUtils.equals(filed, "planDate")) {
                        filedList.add(sdf.format(a.getPlanDate()));
                    } else if (StringUtils.equals(filed, "flightNum")) {
                        filedList.add(StringUtils.toStr(a.getFlightCompanyCode()) + StringUtils.toStr(a.getFlightNum()));
                    } else if (StringUtils.equals(filed, "flightNum2")) {
                        filedList.add(StringUtils.toStr(a.getFlightCompanyCode2()) + StringUtils.toStr(a.getFlightNum2()));
                    } else {
                        filedList.add(StringUtils.toStr(Reflections.getFieldValue(a, filed)));
                        //filedList.add(StringUtils.toStr(FlightPlanPair.class.getMethod(toGetter(filed)).invoke(a)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            dataList.add(filedList.toArray());
        });

        ExcelExport excelExport = new ExcelExport();
        excelExport.setTitle("机位信息查询");
        excelExport.setFileName("机位信息查询");

        excelExport.setRowsName(rowsName);
        excelExport.setDataList(dataList);
        excelExport.setRequest(request);
        excelExport.setResponse(response);
        //导出Excel公共方法调用
        excelExport.exp();
        logger.info("“机位信息查询”导出成功");

    }

    //----------------------------------------机位信息查询---------------------------------end
//----------------------------------------值机柜台信息查询---------------------------------begin
    private List<CheckingCounterOccupyingInfoWrapper> checkouingCountertemp = new ArrayList<>();

    @RequiresPermissions("rms:checkingCounter:view")
    @RequestMapping("checkingCounter")
    public String checkingCounterView(Model model) {
        model.addAttribute("statisticsName", "checkingCounter");
        return "rms/statistics/checkingCounter";
    }

    @RequiresPermissions("rms:checkingCounter:view")
    @RequestMapping("checkingCounterList")
    @ResponseBody
    public DataGrid<CheckingCounterOccupyingInfoWrapper> checkingCounterList(CheckingCounterOccupyingInfoWrapper info) {
        if (info.getPlanDate() == null && StringUtils.isBlank(info.getFlightDynamicCode()) && StringUtils.isBlank(info.getInteCheckingCounterCode())) {
            return new DataGrid<>(new ArrayList<>());
        }
        String flightDynCode = StringUtils.isNotBlank(info.getFlightDynamicCode()) ? info.getFlightDynamicCode().toUpperCase() : null;
        String checkingCounterCode = StringUtils.isNotBlank(info.getInteCheckingCounterCode()) ? info.getInteCheckingCounterCode().toUpperCase() : null;

        FlightDynamic param = new FlightDynamic();
        param.setPlanDate(info.getPlanDate());
        param.setFlightNum(flightDynCode);

        List<CheckingCounterOccupyingInfoWrapper> list_coi = flightDynamicHistoryService.checkinCounterList(param).stream().filter(
                a -> checkingCounterCode != null ? ((a.getInteCheckingCounterCode() != null ? a.getInteCheckingCounterCode().indexOf(checkingCounterCode) >= 0 : false) || (a.getIntlCheckingCounterCode() != null ? a.getIntlCheckingCounterCode().indexOf(checkingCounterCode) >= 0 : false)) : true
        ).map(checkingCounterOccupyingInfo -> {
            if (StringUtils.isBlank(checkingCounterOccupyingInfo.getFlightDynamicNature())) {
                FlightDynamic flightDynamic = flightDynamicHistoryService.getHis(checkingCounterOccupyingInfo.getFlightDynamicId());
                String nature = flightDynamicService.getTheFuckingFlightAttr(flightDynamic);
                checkingCounterOccupyingInfo.setFlightDynamicNature(nature.equals("国内航班") ? "1" : nature.equals("国际航班") ? "2" : nature.equals("混合航班") ? "3" : "");
            }
            return checkingCounterOccupyingInfo;
        }).collect(Collectors.toList());
        //checkouingCountertemp.clear();
        checkouingCountertemp = list_coi;
        logger.info("“值机柜台信息查询”查询成功");
        return new DataGrid<>(list_coi);
    }

    @RequiresPermissions("rms:checkingCounter:view")
    @RequestMapping("checkingCounterExpExcel")
    public void checkingCounterExpExcel(HttpServletRequest request, HttpServletResponse response) {
        List<Object[]> dataList = new ArrayList<>();
        Map<String, String> kNature = new HashMap<String, String>() {{
            put("1", "国内航班");
            put("2", "国际航班");
            put("3", "混合航班");
        }};
        checkouingCountertemp.forEach(a -> {
            dataList.add(new Object[]{
                    DateUtils.formatDate(a.getPlanDate()),
                    StringUtils.toStr(a.getFlightCompanyCode() + a.getFlightDynamicCode()),
                    StringUtils.toStr(a.getAircraftNum()),
                    StringUtils.toStr(a.getAircraftTypeCode()),
                    StringUtils.toStr(a.getPlaceNum()),
                    StringUtils.toStr(a.getStatusName()),
                    StringUtils.toStr(a.getFlightNatureName()),
                    kNature.get(StringUtils.toStr(a.getFlightDynamicNature())),
                    StringUtils.toStr(a.getInoutTypeName()),
                            /*StringUtils.toStr(a.getFlightDynamicId()),*/
                    StringUtils.toStr(a.getFlightCompanyCode()),
                    StringUtils.toStr(a.getInteCheckingCounterCode()),
                    StringUtils.toStr(a.getInteActualStartTime()),
                    StringUtils.toStr(a.getInteActualOverTime()),
                    StringUtils.toStr(a.getIntlCheckingCounterCode()),
                    StringUtils.toStr(a.getIntlActualStartTime()),
                    StringUtils.toStr(a.getIntlActualOverTime()),
                    StringUtils.toStr(a.getDepartureAirportName()),
                    StringUtils.toStr(a.getPassAirport1Name()),
                    StringUtils.toStr(a.getPassAirport2Name()),
                    StringUtils.toStr(a.getArrivalAirportName()),
                    StringUtils.toStr(a.getDeparturePlanTime()),
                    StringUtils.toStr(a.getEtd()),
                    StringUtils.toStr(a.getAtd()),
                    StringUtils.toStr(a.getExpectedCheckingCounterNum()),
                    StringUtils.toStr(a.getExpectedStartTime()),
                    StringUtils.toStr(a.getExpectedOverTime()),
                    StringUtils.toStr(a.getAgentCode()),
                    StringUtils.toStr(a.getAgentName()),
                    StringUtils.toStr(a.getDelayResonsName())
            });
        });
        ExcelExport excelExport = new ExcelExport();
        excelExport.setTitle("值机柜台信息");
        excelExport.setFileName("值机柜台信息");
        excelExport.setRowsName(new String[]{
                "计划日期", "航班号", "机号", "机型", "机位",
                "状态", "性质", "属性", "进出港", "航空公司", "国内航段", "实际开始时间",
                "实际结束时间", "国际航段", "实际开始时间", "实际结束时间", "起飞", "经停1", "经停2", "到达",
                "计飞", "预飞", "实飞", "预计占用数量", "预计开始时间", "预计结束时间", "代理ID", "代理名称", "延误原因"});
        excelExport.setDataList(dataList);
        excelExport.setRequest(request);
        excelExport.setResponse(response);
        //导出Excel公共方法调用
        excelExport.exp();
        logger.info("“值机柜台信息查询”导出成功");
    }

    //----------------------------------------值机柜台信息查询---------------------------------end
//----------------------------------------行李转盘查询---------------------------------begin
    private List<CarouselOccupyingInfoWrapper> carouseltemp = new ArrayList<>();

    @RequiresPermissions("rms:carousel:view")
    @RequestMapping("carousel")
    public String carouselView(Model model) {
        model.addAttribute("statisticsName", "carousel");
        return "rms/statistics/carousel";
    }

    @RequiresPermissions("rms:checkingCounter:view")
    @RequestMapping("carouselList")
    @ResponseBody
    public DataGrid<CarouselOccupyingInfoWrapper> carouselList(CarouselOccupyingInfoWrapper info) {
        if (info.getPlanDate() == null && StringUtils.isBlank(info.getFlightDynamicCode()) && StringUtils.isBlank(info.getInteCarouselCode())) {
            return new DataGrid<>(new ArrayList<>());
        }
        String flightDynCode = StringUtils.isNotBlank(info.getFlightDynamicCode()) ? info.getFlightDynamicCode().toUpperCase() : null;
        String inteCarouselCode = StringUtils.isNotBlank(info.getInteCarouselCode()) ? info.getInteCarouselCode().toUpperCase() : null;

        FlightDynamic param = new FlightDynamic();
        param.setPlanDate(info.getPlanDate());
        param.setFlightNum(flightDynCode);

        List<CarouselOccupyingInfoWrapper> list_coi = flightDynamicHistoryService.carouselList(param).stream().filter(
                        a -> inteCarouselCode != null ? ((a.getInteCarouselCode() != null ? a.getInteCarouselCode().indexOf(inteCarouselCode) >= 0 : false) || (a.getIntlCarouselCode() != null ? a.getIntlCarouselCode().indexOf(inteCarouselCode) >= 0 : false)) : true
                ).map(carouselWrapper -> {
                    if (StringUtils.isBlank(carouselWrapper.getFlightDynamicNature())) {
                        FlightDynamic flightDynamic = flightDynamicHistoryService.getHis(carouselWrapper.getFlightDynamicId());
                        String nature = flightDynamicService.getTheFuckingFlightAttr(flightDynamic);
                        carouselWrapper.setFlightDynamicNature(nature.equals("国内航班") ? "1" : nature.equals("国际航班") ? "2" : nature.equals("混合航班") ? "3" : "");
                    }
                    return carouselWrapper;
                }).collect(Collectors.toList());
        carouseltemp = list_coi;
        logger.info("“行李转盘查询”查询成功");
        return new DataGrid<>(list_coi);
    }

    @RequiresPermissions("rms:carousel:view")
    @RequestMapping("carouselExpExcel")
    public void carouselExpExcel(HttpServletRequest request, HttpServletResponse response) {
        String datas = FileUtils.readJson("carouse");
        JSONArray jsonArray = JSON.parseArray(datas.toString());
        List<String> fields = new ArrayList<>();
        String[] rowsName = jsonTofiled(jsonArray, fields);

        List<Object[]> dataList = new ArrayList<>();
        carouseltemp.forEach(carousel -> {
            List filedList = new ArrayList();
            fields.forEach(filed -> {
                try {
                    if (StringUtils.equals(filed, "planDate")) {
                        filedList.add(DateUtils.formatDate(carousel.getPlanDate()));
                    } else if (StringUtils.equals(filed, "flightNum")) {
                        filedList.add(StringUtils.toStr(carousel.getFlightCompanyCode(), carousel.getFlightNum()));
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
        excelExport.setTitle("行李转盘查询");
        excelExport.setFileName("行李转盘查询");

        excelExport.setRowsName(rowsName);
        excelExport.setDataList(dataList);
        excelExport.setRequest(request);
        excelExport.setResponse(response);
        //导出Excel公共方法调用
        excelExport.exp();
        logger.info("“行李转盘查询”导出成功");
    }
//----------------------------------------行李转盘查询---------------------------------end

    //----------------------------------------配载人员工作量统计---------------------------------begin
    @RequiresPermissions("rms:dockList:view")
    @RequestMapping("dockList")
    public String dockListView(Model model) {
        model.addAttribute("statisticsName", "dockList");
        return "rms/statistics/dockList";
    }
//----------------------------------------配载人员工作量统计---------------------------------end

    //----------------------------------------资源分配查询和导出---------------------------------begin
    private List<FlightPlanPair> sourceTemp = new ArrayList<>();

    @RequiresPermissions("rms:resourceList:view")
    @RequestMapping("resource")
    public String resourceView(Model model) {
        model.addAttribute("statisticsName", "resource");
        return "rms/statistics/resourceView";
    }

    @RequiresPermissions("rms:resourceList:view")
    @RequestMapping("resourceList")
    @ResponseBody
    public DataGrid resourceList(FlightPlanPair pair, HttpServletRequest request) {
        String planDate_begin = request.getParameter("planDate_begin");
        String planDate_end = request.getParameter("planDate_end");
        Map paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(planDate_begin)) paramMap.put("planDate_begin", planDate_begin);
        if (StringUtils.isNotBlank(planDate_end)) paramMap.put("planDate_end", planDate_end);
        List<FlightPlanPair> flightPlanPairs = flightDynamicPairHistoryService.findOciList(pair, paramMap);
        sourceTemp = flightPlanPairs;
        return new DataGrid(flightPlanPairs);
    }

    @RequiresPermissions("rms:resourceList:view")
    @RequestMapping("resourceExpExcel")
    public void resourceExpExcel(HttpServletRequest request, HttpServletResponse response) {
        String resourceTo = FileUtils.readJson("resourceTo");
        JSONObject resourceToJson = JSONObject.parseObject(resourceTo.toString());
        //过滤非选中的资源类开型
        List<String> names = new ArrayList<>();
        names.addAll((Collection<? extends String>) resourceToJson.get("sourceType"));
        String sourceType = request.getParameter("sourceType");
        if (StringUtils.isNotBlank(sourceType)) {
            names.remove(sourceType);
        } else {
            names = new ArrayList<>();
        }

        String inoutTypeCode = request.getParameter("inoutTypeCode");
        if (StringUtils.isNotBlank(inoutTypeCode)) {
            if ("C".equals(inoutTypeCode))
                names.addAll((Collection<? extends String>) resourceToJson.get("inCode"));
            else
                names.addAll((Collection<? extends String>) resourceToJson.get("outCode"));
        }

        //用于转化地址（地址code+name）
        List<String> toname = new ArrayList<>();
        toname.addAll((Collection<? extends String>) resourceToJson.get("addrCodeName"));

        String datas = FileUtils.readJson("resource");
        JSONArray jsonArray = JSON.parseArray(datas.toString());
        List<String> fields = new ArrayList<>();
        String[] rowsName = jsonTofiled(jsonArray, fields, names);

        List<Object[]> dataList = new ArrayList<>();
        sourceTemp.forEach(resource -> {
            List filedList = new ArrayList();
            fields.forEach(filed -> {
                try {
                    if (StringUtils.equals(filed, "planDate")) {
                        filedList.add(DateUtils.formatDate(resource.getPlanDate()));
                    } else if (StringUtils.equals(filed, "flightNum")) {
                        filedList.add(StringUtils.toStr(resource.getFlightCompanyCode(), resource.getFlightNum()));
                    } else if (StringUtils.equals(filed, "flightNum2")) {
                        filedList.add(StringUtils.toStr(resource.getFlightCompanyCode2(), resource.getFlightNum2()));
                    } else if (toname.contains(filed)) { //转换经停地址
                        if (filed.indexOf("ext") >= 0 && Integer.valueOf(filed.replace("ext", "")) > 9) {
                            filedList.add(StringUtils.toStr(Reflections.getFieldValue(resource, filed), Reflections.getFieldValue(resource, "ext" + (Integer.valueOf(filed.replace("ext", "")) + 1))));
                        } else {
                            filedList.add(StringUtils.toStr(Reflections.getFieldValue(resource, filed), Reflections.getFieldValue(resource, filed.replace("Code", "Name"))));
                        }
                    } else {
                        filedList.add(StringUtils.toStr(Reflections.getFieldValue(resource, filed)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            dataList.add(filedList.toArray());
        });
        ExcelExport excelExport = new ExcelExport();
        excelExport.setTitle("资源分配查询");
        excelExport.setFileName("资源分配查询");

        excelExport.setRowsName(rowsName);
        excelExport.setDataList(dataList);
        excelExport.setRequest(request);
        excelExport.setResponse(response);
        //导出Excel公共方法调用
        excelExport.exp();
        logger.info("“资源分配查询”导出成功");
    }
//----------------------------------------资源分配查询和导出---------------------------------end

//----------------------------------------生产保障历史(safeguardHistory)数据查询和导出---------------------------------begin
    @RequiresPermissions("rms:safeguardHistoryList:view")
    @RequestMapping("safeguardHistory")
    public String safeguardHistoryView(Model model) {
        return "rms/statistics/safeguardHistoryView";
    }

    @RequiresPermissions("rms:safeguardHistoryList:view")
    @RequestMapping("safeguardHistoryList")
    @ResponseBody
    public DataGrid safeguardHistoryList(FlightDynamic flightDynamic, HttpServletRequest request) {
        boolean dateFlag = false;
        String planDate_begin = request.getParameter("planDate_begin");
        String planDate_end = request.getParameter("planDate_end");
        Map paramMap = new HashMap<>();
        paramMap.put("dataType", request.getParameter("dType"));
        paramMap.put("addr", request.getParameter("addr"));
        if (StringUtils.isNotBlank(planDate_begin)) {
            paramMap.put("planDate_begin", planDate_begin);
            dateFlag = true;
        }
        if (StringUtils.isNotBlank(planDate_end)) {
            paramMap.put("planDate_end", planDate_end);
            dateFlag = true;
        }
        List datas = new ArrayList();
        if (dateFlag) {
            datas = safeguardHistoryService.findOutFlight(flightDynamic, paramMap);
        }
        return new DataGrid(datas);
    }

    @RequiresPermissions("rms:safeguardHistoryList:view")
    @RequestMapping("safeguardHistoryExpExcel")
    public void safeguardHistoryExpExcel(FlightDynamic flightDynamic, HttpServletRequest request, HttpServletResponse response) {
        safeguardHistoryService.expExcel(flightDynamic,request,response);
    }
//----------------------------------------生产保障历史(safeguardHistory)数据查询和导出---------------------------------end

//----------------------------------------航班动态历史数据查询和导出---------------------------------begin
    @RequiresPermissions("rms:fdHistoryList:view")
    @RequestMapping("fdHistory")
    public String fdHistoryView(Model model) {
        return "rms/statistics/fdHistoryView";
    }

    @RequiresPermissions("rms:fdHistoryList:view")
    @RequestMapping("fdHistoryList")
    @ResponseBody
    public DataGrid fdHistoryList(FlightDynamic flightDynamic, HttpServletRequest request) {
        boolean dateFlag = false;
        String planDate_begin = request.getParameter("planDate_begin");
        String planDate_end = request.getParameter("planDate_end");
        Map paramMap = new HashMap<>();
        paramMap.put("dataType", request.getParameter("dType"));
        paramMap.put("addr", request.getParameter("addr"));
        if (StringUtils.isNotBlank(planDate_begin)) {
            paramMap.put("planDate_begin", planDate_begin);
            dateFlag = true;
        }
        if (StringUtils.isNotBlank(planDate_end)) {
            paramMap.put("planDate_end", planDate_end);
            dateFlag = true;
        }
        List datas = new ArrayList();
        if (dateFlag) {
            datas = safeguardHistoryService.findFlightByHomeAndPre(flightDynamic, paramMap);
        }
        return new DataGrid(datas);
    }

    @RequiresPermissions("rms:fdHistoryList:view")
    @RequestMapping("fdHistoryExpExcel")
    public void fdHistoryExpExcel(FlightDynamic flightDynamic, HttpServletRequest request, HttpServletResponse response) {
        safeguardHistoryService.expExcelByHomeAndPre(flightDynamic,request,response);
    }
//----------------------------------------航班动态历史数据查询和导出---------------------------------end


    //将json对象转为导出数据的表和取值对象的属性名
    private String[] jsonTofiled(JSONArray jsonArray, List<String> fields, List<String>... filterFields) {
        List<String> rowsNameList = new ArrayList<>();
        jsonArray.forEach(a -> {
            if (a instanceof Map) {
                for (Map.Entry<String, String> entry : ((Map<String, String>) a).entrySet()) {
                    if (filterFields != null && filterFields.length == 1) { //过滤不导出字段
                        String key = entry.getKey();
                        boolean flag = false;
                        for (String s : filterFields[0]) {
                            if (key.toUpperCase().indexOf(s.toUpperCase()) >= 0) {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            fields.add(key);
                            rowsNameList.add(entry.getValue());
                        }
                    } else {
                        fields.add(entry.getKey());
                        rowsNameList.add(entry.getValue());
                    }
                }
            }
        });
        String[] rowsName = new String[rowsNameList.size()];
        for (int i = 0; i < rowsNameList.size(); i++) {
            rowsName[i] = rowsNameList.get(i);
        }
        return rowsName;
    }
}
