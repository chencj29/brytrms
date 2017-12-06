package com.thinkgem.jeesite.modules.ams.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExcelUtils;
import com.thinkgem.jeesite.modules.ams.dao.FlightDynamicHistoryDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.rms.dao.RmsFlightSecurityDao;
import com.thinkgem.jeesite.modules.rms.dao.SecurityServiceInfoDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightDutyWrap;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightHomeManifest;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightOverManifest;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightPreManifest;
import com.thinkgem.jeesite.modules.rms.service.RmsFlightDutyService;
import com.thinkgem.jeesite.modules.rms.service.RmsFlightHomeManifestService;
import com.thinkgem.jeesite.modules.rms.service.RmsFlightOverManifestService;
import com.thinkgem.jeesite.modules.rms.service.RmsFlightPreManifestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class SafeguardHistoryService {
    @Autowired
    FlightDynamicHistoryDao flightDynamicHistoryDao;

    ImmutableSet<String> nameList = ImmutableSet.of("homeManifest", "preManifest", "flightDuty", "overManifest");
    ImmutableMap<String, String> typeName1 = ImmutableMap.of("homeManifest", "本站仓单历史数据统计", "preManifest", "上站仓单历史数据统计", "flightDuty", "值机数据统计", "overManifest", "过站仓单历史数据统计");
    ImmutableMap<String, String> typeName2 = ImmutableMap.of("securityService", "安检航班信息历史数据统计", "flightSecurity", "安检旅客信息历史数据统计");

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public List findOutFlight(FlightDynamic flightDynamic, Map paramMap) {
        //处理权限
        Map dsfnMap = Maps.newHashMap();
        dsfnMap.put("dsfn", BaseService.dataScopeFilterNew(flightDynamic.getCurrentUser()));
        paramMap.put("sqlMap", dsfnMap);

        String dataType = (String) paramMap.get("dataType");

        paramMap.put("inoutTypeCode", "C");
        paramMap.put("flightCompanyCode", flightDynamic.getFlightCompanyCode());
        paramMap.put("flightNum", flightDynamic.getFlightNum());
        paramMap.put("flightAttrCode", flightDynamic.getFlightAttrCode());
        paramMap.put("flightNatureCode", flightDynamic.getFlightNatureCode());
        if (StringUtils.equals("preManifest", dataType)) paramMap.put("inoutTypeCode", "J");

        List list = new ArrayList();
        if (StringUtils.isNotBlank(dataType)) {
            if (nameList.contains(dataType)) {
                List<FlightDynamic> flightDynamics = flightDynamicHistoryDao.findListByFlightOut(paramMap);
                if (StringUtils.equals("homeManifest", dataType)) {
                    RmsFlightHomeManifestService service = SpringContextHolder.getBean(RmsFlightHomeManifestService.class);
                    List<RmsFlightHomeManifest> homeNanifs = new ArrayList<>();
                    service.homeManifestList(flightDynamics, homeNanifs, true);
                    list = homeNanifs.stream().sorted(Comparator.comparing(a->a.getPlanDate())).collect(Collectors.toList());
                } else if (StringUtils.equals("preManifest", dataType)) {
                    RmsFlightPreManifestService service = SpringContextHolder.getBean(RmsFlightPreManifestService.class);
                    List<RmsFlightPreManifest> preNanifs = new ArrayList<>();
                    service.flightPreManifestList(flightDynamics, preNanifs, true);
                    list = preNanifs.stream().sorted(Comparator.comparing(a->a.getPlanDate())).collect(Collectors.toList());
                } else if (StringUtils.equals("flightDuty", dataType)) {
                    RmsFlightDutyService service = SpringContextHolder.getBean(RmsFlightDutyService.class);
                    List<RmsFlightDutyWrap> dutyWraps = new ArrayList<>();
                    service.dutyWrapList(flightDynamics, dutyWraps, true);
                    list = dutyWraps.stream().sorted(Comparator.comparing(a->a.getPlanDate())).collect(Collectors.toList());
                } else if (StringUtils.equals("overManifest", dataType)) {
                    RmsFlightOverManifestService service = SpringContextHolder.getBean(RmsFlightOverManifestService.class);
                    List<RmsFlightOverManifest> homeNanifs = new ArrayList<>();
                    service.flightOverManifestList(flightDynamics, homeNanifs, true);
                    list = homeNanifs.stream().sorted(Comparator.comparing(a->a.getPlanDate())).collect(Collectors.toList());
                }
            } else if (StringUtils.equals("securityService", dataType)) {
                SecurityServiceInfoDao serviceInfoDao = SpringContextHolder.getBean(SecurityServiceInfoDao.class);
                list = serviceInfoDao.findListWapperByHistory(paramMap);
            } else if (StringUtils.equals("flightSecurity", dataType)) {
                RmsFlightSecurityDao flightSecurityDao = SpringContextHolder.getBean(RmsFlightSecurityDao.class);
                list = flightSecurityDao.findListByHistory(paramMap);
            }
        }
        return list;
    }

    public void expExcel(FlightDynamic flightDynamic, HttpServletRequest request, HttpServletResponse response) {
        Map dataMap = new HashMap();

        boolean dateFlag = false;
        String planDate_begin = request.getParameter("planDate_begin");
        String planDate_end = request.getParameter("planDate_end");
        String datatype = request.getParameter("dType");
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
        if (dateFlag && StringUtils.isNotBlank(datatype)) {
            dataMap.put("list", findOutFlight(flightDynamic, paramMap));
            String ftlName = request.getParameter("ftlName");
            String xlsName = "";
            if (typeName1.containsKey(datatype)) {
                xlsName = typeName1.get(datatype);
            } else if (typeName2.containsKey(datatype)) {
                xlsName = typeName2.get(datatype);
            }

            if (StringUtils.isBlank(ftlName)) ftlName = datatype;

            ExcelUtils.exportExcel(request, response, dataMap, "myExcel", ftlName + ".ftl", xlsName + ".xls");
            logger.info("“{}”导出成功", xlsName);
        }
    }

    public List findFlightByHomeAndPre(FlightDynamic flightDynamic, Map paramMap) {
        //处理权限
        Map dsfnMap = Maps.newHashMap();
        dsfnMap.put("dsfn", BaseService.dataScopeFilterNew(flightDynamic.getCurrentUser()));
        paramMap.put("sqlMap", dsfnMap);

        String dataType = (String) paramMap.get("dataType");

        paramMap.put("inoutTypeCode", dataType);
        paramMap.put("flightCompanyCode", flightDynamic.getFlightCompanyCode());
        paramMap.put("flightNum", flightDynamic.getFlightNum());
        paramMap.put("flightNatureCode", flightDynamic.getFlightNatureCode());

        List list = new ArrayList();
        if (StringUtils.isNotBlank(dataType)) {
            List<FlightDynamic> flightDynamics = flightDynamicHistoryDao.findListByFlightOut(paramMap);
            if (StringUtils.equals("C", dataType)) {
                List<RmsFlightHomeManifest> homeList = new ArrayList();
                RmsFlightHomeManifestService service = SpringContextHolder.getBean(RmsFlightHomeManifestService.class);
                service.homeManifestList(flightDynamics, homeList, true);
                //处理本站仓单详情
                service.homeManifestListByManifestSec(flightDynamics, homeList);
                final Function<RmsFlightHomeManifest, Date> byPlanDate = e -> e.getPlanDate(); //按航班日期
                final Function<RmsFlightHomeManifest, String> byFlightNum = e -> e.getFlightNum(); //按航班号
                list = homeList.stream().sorted(Comparator.comparing(byPlanDate).thenComparing(byFlightNum)).collect(Collectors.toList());
            } else if (StringUtils.equals("J", dataType)) {
                List<RmsFlightPreManifest> preNanifs = new ArrayList<>();
                RmsFlightPreManifestService service = SpringContextHolder.getBean(RmsFlightPreManifestService.class);
                service.flightPreManifestList(flightDynamics, preNanifs, true);
                final Function<RmsFlightPreManifest, Date> byPlanDate = e -> e.getPlanDate(); //按航班日期
                final Function<RmsFlightPreManifest, String> byFlightNum = e -> e.getFlightNum(); //按航班号
                list = preNanifs.stream().sorted(Comparator.comparing(byPlanDate).thenComparing(byFlightNum)).collect(Collectors.toList());
            }
        }
        return list;
    }

    public void expExcelByHomeAndPre(FlightDynamic flightDynamic, HttpServletRequest request, HttpServletResponse response) {
        Map dataMap = new HashMap();

        boolean dateFlag = false;
        String planDate_begin = request.getParameter("planDate_begin");
        String planDate_end = request.getParameter("planDate_end");
        String datatype = request.getParameter("dType");
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
        if (dateFlag && StringUtils.isNotBlank(datatype)) {
            //获取数据
            dataMap.put("list", findFlightByHomeAndPre(flightDynamic, paramMap));
            String ftlName = "dynamicOut";
            String xlsName = "航班动态历史数据统计(出港)";
            if (StringUtils.equals("J",datatype)) {
                xlsName = "航班动态历史数据统计(进港)";
                ftlName = "dynamicIn";
            }

            ExcelUtils.exportExcel(request, response, dataMap, "myExcel", ftlName + ".ftl", xlsName + ".xls");
            logger.info("“{}”导出成功", xlsName);
        }
    }
}
