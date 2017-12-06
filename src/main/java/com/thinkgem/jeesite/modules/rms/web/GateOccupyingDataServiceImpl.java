package com.thinkgem.jeesite.modules.rms.web;

import cn.net.metadata.gantt.wrapper.GateGanttWrapper;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * wjp 2016-4-23 16:50:26
 */
@Controller
@RequestMapping(value = "gateOccupyingDataService")
public class GateOccupyingDataServiceImpl {

    private static Logger logger = LoggerFactory.getLogger(GateOccupyingDataServiceImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    FlightDynamicService flightDynamicService;

    @RequestMapping(value = "gateOccpyingDate")
    @ResponseBody
    public String gateOccpyingDate() {
        String sql = "select FLIGHT_DYNAMIC_ID as \"flightDynamicId\",FLIGHT_DYNAMIC_CODE as \"flightDynamicCode\",ACTUAL_GATE_NUM as \"actualGateNum\","
                + "START_TIME as \"startTime\",OVER_TIME as \"overTime\","
                + "(SELECT FLIGHT_COMPANY_CODE||FLIGHT_NUM||' '||FLIGHT_COMPANY_CODE2||FLIGHT_NUM2 FROM AMS_FLIGHT_DYNAMIC_PAIR WHERE id = FLIGHT_DYNAMIC_ID) as \"flightNum\""
                + " from RMS_GATE_OCCUPYING_INFO "
                + " where DEL_FLAG=0 and ACTUAL_GATE_NUM is not null and SYSDATE>=START_TIME and SYSDATE<=OVER_TIME";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        JSONArray json = new JSONArray();
        list.stream().forEach(a -> {
            JSONObject jo = new JSONObject();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                jo.put("flightDynamicId", a.get("flightDynamicId"));
                jo.put("flightDynamicCode", a.get("flightDynamicCode"));
                jo.put("actualGateNum", a.get("actualGateNum"));
                jo.put("startTime", sdf.format(a.get("startTime")));
                jo.put("overTime", sdf.format(a.get("overTime")));
                jo.put("flightDynamicCode",((String)a.get("flightNum")).trim().replace(" ","/"));
                json.put(jo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return json.toString();
    }

    //提供给航班系统调用的机位占用甘特图接口
    @RequestMapping(value = "distedGateListJson")
    @ResponseBody
    public List<GateGanttWrapper> distedGateListJsonWebService(String startDate, String overDate) {
        List<GateGanttWrapper> wrappers = null;
        try {
            wrappers = flightDynamicService.listGateJson(DateUtils.parseDate(startDate, "yyyy-MM-dd"), DateUtils.parseDate(overDate, "yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
            LoggerFactory.getLogger(getClass()).error("转换时间的时候出现错误，无数据返回。");
        }

        return wrappers;
    }
}
