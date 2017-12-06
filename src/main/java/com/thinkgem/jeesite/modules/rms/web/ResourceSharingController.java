package com.thinkgem.jeesite.modules.rms.web;

import cn.net.metadata.datasync.monitor.wrapper.PageListenerEnhanceEntity;
import cn.net.metadata.datasync.socketio.ConcurrentClientsHolder;
import cn.net.metadata.gantt.wrapper.CarouselGanttWrapper;
import cn.net.metadata.gantt.wrapper.GateGanttWrapper;
import cn.net.metadata.rule.action.dist.ResourceDistUtils;
import cn.net.metadata.wrapper.KeyValuePairsWapper;
import com.bstek.urule.Utils;
import com.bstek.urule.console.repository.RepositoryService;
import com.bstek.urule.console.repository.model.ResourcePackage;
import com.bstek.urule.runtime.KnowledgePackage;
import com.bstek.urule.runtime.KnowledgeSession;
import com.bstek.urule.runtime.KnowledgeSessionFactory;
import com.bstek.urule.runtime.service.KnowledgeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.dao.YesterAircraftNumPairDao;
import com.thinkgem.jeesite.modules.ams.entity.CheckAspect;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.YesterAircraftNumPair;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.entity.wrapper.CarouselListWrapper;
import com.thinkgem.jeesite.modules.ams.service.CheckAspectService;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.dao.CheckinCounterDao;
import com.thinkgem.jeesite.modules.rms.entity.*;
import com.thinkgem.jeesite.modules.rms.rd.ResourceType;
import com.thinkgem.jeesite.modules.rms.service.*;
import com.thinkgem.jeesite.modules.rms.wrapper.SingleResourceDistParamWrapper;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * p wh od et gf wvsg uqid
 * Created by xiaowu on 3/17/16.
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/rs")
public class ResourceSharingController extends BaseController {

    private final static PropertiesLoader REALTIME_DYNAMIC_MESSAGE_CONFS = new PropertiesLoader("classpath:notification.realtime-dynamic-message-conf/config.properties");
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final KnowledgeService knowledgeService = (KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
    private static final RepositoryService repositoryService = (RepositoryService) Utils.getApplicationContext().getBean(RepositoryService.BEAN_ID);
    //是否启用次日航班机位检测
    Boolean placeNumFlag = Boolean.parseBoolean(DictUtils.getDictValueConstant("urule_yester_place_num", "flag"));

    @Autowired
    FlightDynamicService flightDynamicService;

    @Autowired
    GateOccupyingInfoService gateOccupyingInfoService;

    @Autowired
    CarouselOccupyingInfoService carouselOccupyingInfoService;

    @Autowired
    ArrivalGateOccupyingInfoService arrivalGateOccupyingInfoService;

    @Autowired
    BoardingGateOccupyingInfoService boardingGateOccupyingInfoService;

    @Autowired
    SlideCoastOccupyingInfoService slideCoastOccupyingInfoService;

    @Autowired
    CheckingCounterOccupyingInfoService checkingCounterOccupyingInfoService;

    @Autowired
    DepartureHallOccupyingInfoService departureHallOccupyingInfoService;

    @Autowired
    SecurityCheckinOccupyingInfoService securityCheckinOccupyingInfoService;

    @Autowired
    RmsCarouselService rmsCarouselService;

    @Autowired
    ArrivalGateService arrivalGateService;

    @Autowired
    BoardingGateService boardingGateService;

    @Autowired
    SlideCoastService slideCoastService;

    @Autowired
    FlightPlanPairService flightPlanPairService;

    @Autowired
    ResourceSharingService resourceSharingService;

    @Autowired
    CheckinCounterDao checkinCounterDao;

    @Autowired
    CheckAspectService checkAspectService;

    @Autowired
    ResourceMockDistDetailService resourceMockDistDetailService;
    @Autowired
    ResourceMockDistInfoService resourceMockDistInfoService;
    @Autowired
    YesterAircraftNumPairDao yesterAircraftNumPairDao;

    public static KnowledgePackage getAircraftStandPkg(String packageId) throws Exception {
        return knowledgeService.getKnowledge("机位/" + packageId);
    }

    public static KnowledgePackage getCheckinCounterPkg(String packageId) throws Exception {
        return knowledgeService.getKnowledge("值机柜台/" + packageId);
    }

    public static KnowledgePackage getBoardingGatePkg(String packageId) throws Exception {
        return knowledgeService.getKnowledge("登机口/" + packageId);
    }

    public static KnowledgePackage getArrivalGatePkg(String packageId) throws Exception {
        return knowledgeService.getKnowledge("到港门/" + packageId);
    }

    public static KnowledgePackage getCarouselPkg(String packageId) throws Exception {
        return knowledgeService.getKnowledge("行李转盘/" + packageId);
    }

    public static KnowledgePackage getSlideCoastPkg(String packageId) throws Exception {
        return knowledgeService.getKnowledge("滑槽/" + packageId);
    }

    public static KnowledgePackage getSecurityCheckinPkg(String packageId) throws Exception {
        return knowledgeService.getKnowledge("安检口/" + packageId);
    }

    public static KnowledgePackage getDepartureHallPkg(String packageId) throws Exception {
        return knowledgeService.getKnowledge("候机厅/" + packageId);
    }

    @RequiresPermissions("rms:rs:aircraft_dist")
    @RequestMapping("aircraft_dist")
    public String aircraft_dist_view() {
        return "rms/rs/aircraft_dist";
    }

    @RequiresPermissions("rms:rs:carousel_dist")
    @RequestMapping("carousel_dist")
    public String carousel_dist_view() {
        return "rms/rs/carousel_dist";
    }

    @RequiresPermissions("rms:rs:arrival_gate_dist")
    @RequestMapping("arrival_gate_dist")
    public String arrival_gate_dist_view() {
        return "rms/rs/arrival_gate_dist";
    }

    @RequiresPermissions("rms:rs:boarding_gate_dist")
    @RequestMapping("boarding_gate_dist")
    public String boarding_gate_dist_view() {
        return "rms/rs/boarding_gate_dist";
    }

    @RequiresPermissions("rms:rs:slide_coast_dist")
    @RequestMapping("slide_coast_dist")
    public String slide_coast_dist() {
        return "rms/rs/slide_coast_dist";
    }

    @RequiresPermissions("rms:rs:checkin_counter_dist")
    @RequestMapping("checkin_counter_dist")
    public String checkin_counter_dist_view() {
        return "rms/rs/checkin_counter_dist";
    }

    @RequiresPermissions("rms:rs:departure_hall_dist")
    @RequestMapping("departure_hall_dist")
    public String departure_hall_dist_view() {
        return "rms/rs/departure_hall_dist";
    }

    @RequiresPermissions("rms:rs:security_checkin_dist")
    @RequestMapping("security_checkin_dist")
    public String security_checkin_dist_view() {
        return "rms/rs/security_checkin_dist";
    }

    @RequestMapping("urule_welcome_page")
    public String urule_welcome_page() {
        return "urule/welcome-page";
    }

    @RequiresPermissions("rms:rs:carousel_dist")
    @RequestMapping("disted_carousel_json")
    @ResponseBody
    public List<CarouselGanttWrapper> distedCarouselJson(@RequestParam("startDate") String startDate, @RequestParam("overDate") String overDate) {
        Date start, over;
        List<CarouselGanttWrapper> wrappers = null;
        try {
            start = DateUtils.parseDate(startDate, "yyyy-MM-dd");
            over = DateUtils.parseDate(overDate, "yyyy-MM-dd");
            wrappers = flightDynamicService.listCarouselJson(start, over);
        } catch (ParseException e) {
            e.printStackTrace();
            LoggerFactory.getLogger(getClass()).error("转换时间的时候出现错误，无数据返回。");
        }
        return wrappers;
    }

    @RequiresPermissions("rms:rs:carousel_dist")
    @RequestMapping("disted_carousel_json_4_mock")
    @ResponseBody
    public List<CarouselGanttWrapper> distedCarouselJson4Mock(String infoId) {
        return flightDynamicService.listCarouselJson4Mock(infoId);
    }

    @RequiresPermissions("rms:rs:arrival_gate_dist")
    @RequestMapping("disted_arrival_gate_Json_4_mock")
    @ResponseBody
    public List<CarouselGanttWrapper> distedArrivalGateJson4Mock(String infoId) {
        return flightDynamicService.listArrivalGateJson4Mock(infoId);
    }

    @RequiresPermissions("rms:rs:boarding_gate_dist")
    @RequestMapping("disted_boarding_gate_Json_4_mock")
    @ResponseBody
    public List<CarouselGanttWrapper> distedBoardingGateJson4Mock(String infoId) {
        return flightDynamicService.listBoardingGateJson4Mock(infoId);
    }

    @RequiresPermissions("rms:rs:slide_coast_dist")
    @RequestMapping("disted_slide_coast_Json_4_mock")
    @ResponseBody
    public List<CarouselGanttWrapper> distedSlideCoastJson4Mock(String infoId) {
        return flightDynamicService.listSlideCoastJson4Mock(infoId);
    }

    @RequiresPermissions("rms:rs:checkin_counter_dist")
    @RequestMapping("disted_checkin_counter_Json_4_mock")
    @ResponseBody
    public List<CarouselGanttWrapper> distedCheckinCounterJson4Mock(String infoId) {
        return flightDynamicService.listCheckinCounterJson4Mock(infoId);
    }

    @RequiresPermissions("rms:rs:security_checkin_dist")
    @RequestMapping("disted_security_checkin_Json_4_mock")
    @ResponseBody
    public List<CarouselGanttWrapper> distedSecurityCheckinJson4Mock(String infoId) {
        return flightDynamicService.listSecurityCheckinJson4Mock(infoId);
    }

    @RequiresPermissions("rms:rs:departure_hall_dist")
    @RequestMapping("disted_departure_hall_Json_4_mock")
    @ResponseBody
    public List<CarouselGanttWrapper> distedDepartureHallJson4Mock(String infoId) {
        return flightDynamicService.listDepartureHallJson4Mock(infoId);
    }

    @RequiresPermissions("rms:rs:arrival_gate_dist")
    @RequestMapping("disted_arrival_gate_Json")
    @ResponseBody
    public List<CarouselGanttWrapper> distedArrivalGateListJson(@RequestParam("startDate") String startDate, @RequestParam("overDate") String overDate) {
        Date start, over;
        List<CarouselGanttWrapper> wrappers = null;
        try {
            start = DateUtils.parseDate(startDate, "yyyy-MM-dd");
            over = DateUtils.parseDate(overDate, "yyyy-MM-dd");
            wrappers = flightDynamicService.listArrivalGateJson(start, over);
        } catch (ParseException e) {
            e.printStackTrace();
            LoggerFactory.getLogger(getClass()).error("转换时间的时候出现错误，无数据返回。");
        }

        return wrappers;
    }

    @RequiresPermissions("rms:rs:boarding_gate_dist")
    @RequestMapping("disted_boarding_gate_Json")
    @ResponseBody
    public List<CarouselGanttWrapper> disted_boarding_gate_Json(@RequestParam("startDate") String startDate, @RequestParam("overDate") String overDate) {
        Date start, over;
        List<CarouselGanttWrapper> wrappers = null;
        try {
            start = DateUtils.parseDate(startDate, "yyyy-MM-dd");
            over = DateUtils.parseDate(overDate, "yyyy-MM-dd");
            wrappers = flightDynamicService.listBoardingGateJson(start, over);
        } catch (ParseException e) {
            e.printStackTrace();
            LoggerFactory.getLogger(getClass()).error("转换时间的时候出现错误，无数据返回。");
        }

        return wrappers;
    }

    @RequiresPermissions("rms:rs:slide_coast_dist")
    @RequestMapping("disted_slide_coast_Json")
    @ResponseBody
    public List<CarouselGanttWrapper> disted_slide_coast_Json(@RequestParam("startDate") String startDate, @RequestParam("overDate") String overDate) {
        Date start, over;
        List<CarouselGanttWrapper> wrappers = null;
        try {
            start = DateUtils.parseDate(startDate, "yyyy-MM-dd");
            over = DateUtils.parseDate(overDate, "yyyy-MM-dd");
            wrappers = flightDynamicService.listSlideCoastJson(start, over);
        } catch (ParseException e) {
            e.printStackTrace();
            LoggerFactory.getLogger(getClass()).error("转换时间的时候出现错误，无数据返回。");
        }

        return wrappers;
    }

    @RequiresPermissions("rms:rs:checkin_counter_dist")
    @RequestMapping("disted_checkin_counter_Json")
    @ResponseBody
    public List<CarouselGanttWrapper> disted_checkin_counter_Json(@RequestParam("startDate") String startDate, @RequestParam("overDate") String overDate) {
        Date start, over;
        List<CarouselGanttWrapper> wrappers = null;
        try {
            start = DateUtils.parseDate(startDate, "yyyy-MM-dd");
            over = DateUtils.parseDate(overDate, "yyyy-MM-dd");
            wrappers = flightDynamicService.listCheckinCounterJson(start, over);
        } catch (ParseException e) {
            e.printStackTrace();
            LoggerFactory.getLogger(getClass()).error("转换时间的时候出现错误，无数据返回。");
        }
        return wrappers;
    }

    @RequiresPermissions("rms:rs:security_checkin_dist")
    @RequestMapping("disted_security_checkin_Json")
    @ResponseBody
    public List<CarouselGanttWrapper> disted_security_checkin_Json(@RequestParam("startDate") String startDate, @RequestParam("overDate") String overDate) {
        Date start, over;
        List<CarouselGanttWrapper> wrappers = null;
        try {
            start = DateUtils.parseDate(startDate, "yyyy-MM-dd");
            over = DateUtils.parseDate(overDate, "yyyy-MM-dd");
            wrappers = flightDynamicService.listSecurityCheckinJson(start, over);
        } catch (ParseException e) {
            e.printStackTrace();
            LoggerFactory.getLogger(getClass()).error("转换时间的时候出现错误，无数据返回。");
        }

        return wrappers;
    }

    @RequiresPermissions("rms:rs:departure_hall_dist")
    @RequestMapping("disted_departure_hall_Json")
    @ResponseBody
    public List<CarouselGanttWrapper> disted_departure_hall_Json(@RequestParam("startDate") String startDate, @RequestParam("overDate") String overDate) {
        Date start, over;
        List<CarouselGanttWrapper> wrappers = null;
        try {
            start = DateUtils.parseDate(startDate, "yyyy-MM-dd");
            over = DateUtils.parseDate(overDate, "yyyy-MM-dd");
            wrappers = flightDynamicService.listDepartureHallJson(start, over);
        } catch (ParseException e) {
            e.printStackTrace();
            LoggerFactory.getLogger(getClass()).error("转换时间的时候出现错误，无数据返回。");
        }

        return wrappers;
    }

    @RequiresPermissions("rms:rs:aircraft_dist")
    @RequestMapping("flightDynamics")
    @ResponseBody
    public List<FlightPlanPair> listFlightDynamics() {
        List<FlightPlanPair> flightPlanPairs = flightPlanPairService.findList(new FlightPlanPair());
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
        for (FlightPlanPair fpw : flightPlanPairs) {
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
        pairPairList = pairPairList.stream().sorted(Comparator.comparing(byPlanDate)).collect(Collectors.toList());

        return pairPairList;

//        return flightPlanPairs.stream().sorted(
//                Comparator.comparing(byPlanDate).thenComparing(byAtd).thenComparing(byAta).thenComparing(byInAtd).thenComparing(byEtd)
//                        .thenComparing(byDeparture).thenComparing(byEta).thenComparing(byArrival).thenComparing(byInDeparture)
//        ).collect(Collectors.toList());
    }


    @RequiresPermissions("rms:rs:aircraft_dist")
    @RequestMapping("findList4Mock")
    @ResponseBody
    public List<FlightPlanPair> findList4Mock(String infoId) {
        List<FlightPlanPair> flightPlanPairs = flightPlanPairService.findList4Mock(infoId);
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
        for (FlightPlanPair fpw : flightPlanPairs) {
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
        pairPairList = pairPairList.stream().sorted(Comparator.comparing(byPlanDate)).collect(Collectors.toList());

        return pairPairList;
//        return flightPlanPairs.stream().sorted(
//                Comparator.comparing(byPlanDate).thenComparing(byAtd).thenComparing(byAta).thenComparing(byInAtd).thenComparing(byEtd)
//                        .thenComparing(byDeparture).thenComparing(byEta).thenComparing(byArrival).thenComparing(byInDeparture)
//        ).collect(Collectors.toList());
    }

    @RequiresPermissions("rms:rs:carousel_dist")
    @RequestMapping("carousel-list")
    @ResponseBody
    public List<CarouselOccupyingInfoWrapper> carousellist(FlightDynamic flightDynamic) {
        return flightDynamicService.carouselList(flightDynamic);
    }

    @RequiresPermissions("rms:rs:carousel_dist")
    @RequestMapping("carousel-list_4_mock")
    @ResponseBody
    public List<CarouselOccupyingInfoWrapper> carouselList4Mock(String infoId) {
        return flightDynamicService.carouselList4Mock(infoId);
    }

    @RequiresPermissions("rms:rs:arrival_gate_dist")
    @RequestMapping("arrival-gate-list_4-mock")
    @ResponseBody
    public List<CarouselOccupyingInfoWrapper> arrivalGateList4Mock(String infoId) {
        return flightDynamicService.arrivalGateList4Mock(infoId);
    }

    @RequiresPermissions("rms:rs:boarding_gate_dist")
    @RequestMapping("boarding-gate-list_4-mock")
    @ResponseBody
    public List<CarouselOccupyingInfoWrapper> boardingGateList4Mock(String infoId) {
        return flightDynamicService.boardingGateList4Mock(infoId);
    }

    @RequiresPermissions("rms:rs:slide_coast_dist")
    @RequestMapping("slide-coast-list_4-mock")
    @ResponseBody
    public List<CarouselOccupyingInfoWrapper> slideCoastList4Mock(String infoId) {
        return flightDynamicService.slideCoastList4Mock(infoId);
    }

    @RequiresPermissions("rms:rs:checkin_counter_dist")
    @RequestMapping("checkin-counter-list_4-mock")
    @ResponseBody
    public List<CheckingCounterOccupyingInfoWrapper> checkinCounterList4Mock(String infoId) {
        return flightDynamicService.checkinCounterList4Mock(infoId);
    }

    @RequiresPermissions("rms:rs:security_checkin_dist")
    @RequestMapping("security-checkin-list_4-mock")
    @ResponseBody
    public List<SecurityCheckinOccupyingInfoWrapper> securityCheckinList4Mock(String infoId) {
        return flightDynamicService.securityCheckinList4Mock(infoId);
    }

    @RequiresPermissions("rms:rs:departure_hall_dist")
    @RequestMapping("departure-hall-list_4-mock")
    @ResponseBody
    public List<DepartureHallOccupyingInfoWrapper> departureHallList4Mock(String infoId) {
        return flightDynamicService.departureHallList4Mock(infoId);
    }

    @RequiresPermissions("rms:rs:arrival_gate_dist")
    @RequestMapping("arrival-gate-list")
    @ResponseBody
    public List<BoardingGateOccupyingInfoWrapper> arrival_gate_list(FlightDynamic flightDynamic) {
        return flightDynamicService.arrivalGateList(flightDynamic);
    }

    @RequiresPermissions("rms:rs:boarding_gate_dist")
    @RequestMapping("boarding-gate-list")
    @ResponseBody
    public List<CarouselListWrapper> boarding_gate_list(FlightDynamic flightDynamic) {
        return flightDynamicService.boardingGateList(flightDynamic);
    }

    @RequiresPermissions("rms:rs:slide_coast_dist")
    @RequestMapping("slide-coast-list")
    @ResponseBody
    public List<CarouselOccupyingInfoWrapper> slide_coast_dist(FlightDynamic flightDynamic) {
        return flightDynamicService.slideCoastList(flightDynamic);
    }

    @RequiresPermissions("rms:rs:checkin_counter_dist")
    @RequestMapping("checkin-counter-list")
    @ResponseBody
    public List<CheckingCounterOccupyingInfoWrapper> checkin_counter_list(FlightDynamic flightDynamic) {
        return flightDynamicService.checkinCounterList(flightDynamic);
    }

    @RequiresPermissions("rms:rs:security_checkin_dist")
    @RequestMapping("security-checkin-list")
    @ResponseBody
    public List<SecurityCheckinOccupyingInfoWrapper> security_checkin_list(FlightDynamic flightDynamic) {
        return flightDynamicService.securityCheckinList(flightDynamic);
    }

    @RequiresPermissions("rms:rs:departure_hall_dist")
    @RequestMapping("departure-hall-list")
    @ResponseBody
    public List<DepartureHallOccupyingInfoWrapper> departure_hall_list(FlightDynamic flightDynamic) {
        return flightDynamicService.departureHallList(flightDynamic);
    }

    private Map<String, Object> buildAutoDistParams(boolean isMock, String startTime, String endTime, String random) {
        Map<String, Object> params = new HashMap<>();
        params.put("mock_dist", isMock);
        params.put("currentUser", UserUtils.getUser());
        params.put("random", random);
        params.put("endTime", endTime);
        params.put("startTime", startTime);

        return params;
    }


    @RequiresPermissions("rms:rs:carousel_dist")
    @RequestMapping("carousel_dist_auto")
    @ResponseBody
    public Message carousel_dist_automatically(String random, String startTime, String endTime, String packageId, Message message, HttpServletRequest request) {
        try {
            getCarouselPkg(packageId);
            resourceSharingService.carouselAuto(ResourceType.CAROUSEL, buildAutoDistParams(false, startTime, endTime, random), packageId, message);
            if (message.isSuccess()) {
                message.setCode(1);
                message.setMessage("行李转盘自动分配已成功添加到任务队列中！");
            } else if (message.getCode() == -3) {

            } else {
                message.setCode(0);
                message.setMessage("行李转盘自动分配任务队列已满，请稍候再试！");
            }
        } catch (Exception e) {
            message.setCode(0);
            if (e.getMessage().contains("not found")) message.setMessage("找不到行李转盘的规则文件！");
            else if (e.getMessage().contains("Permission denied")) message.setMessage("您没有调用行李转盘规则文件的权限！");
        }
        LogUtils.saveLogToMsg(request, message);
        return message;
    }

    @RequiresPermissions("rms:rs:arrival_gate_dist")
    @RequestMapping("arrival_gate_dist_auto")
    @ResponseBody
    public Message arrival_gate_dist_auto(String random, String startTime, String endTime, String packageId, Message message, HttpServletRequest request) {
        try {
            getArrivalGatePkg(packageId);
            resourceSharingService.arrivalGateAuto(ResourceType.ARRIVAL_GATE, buildAutoDistParams(false, startTime, endTime, random), packageId, message);
            if (message.isSuccess()) {
                message.setMsg(1, "到港门自动分配已成功添加到任务队列中！");
            } else if (message.getCode() == -3) {

            } else {
                message.setCode(0);
                message.setMessage("到港门自动分配任务队列已满，请稍候再试！");
            }
        } catch (Exception e) {
            message.setCode(0);
            if (e.getMessage().contains("not found")) message.setMessage("找不到到港门的规则文件！");
            else if (e.getMessage().contains("Permission denied")) message.setMessage("您没有调用到港门规则文件的权限！");
        }
        LogUtils.saveLogToMsg(request, message);
        return message;
    }

    @RequiresPermissions("rms:boarding_gate_dist:auto_allo")
    @RequestMapping("boarding_gate_dist_auto")
    @ResponseBody
    public Message boarding_gate_dist_auto(String random, String startTime, String endTime, String packageId, Message message, HttpServletRequest request) {
        try {
            getBoardingGatePkg(packageId);
            resourceSharingService.boardingGateAuto(ResourceType.BOARDING_GATE, buildAutoDistParams(false, startTime, endTime, random), packageId, message);
            if (message.isSuccess()) {
                message.setCode(1);
                message.setMessage("登机口自动分配已成功添加到任务队列中！");
            } else if (message.getCode() == -3) {

            } else {
                message.setCode(0);
                message.setMessage("登机口自动分配任务队列已满，请稍候再试！");
            }
        } catch (Exception e) {
            message.setCode(0);
            if (e.getMessage().contains("not found")) message.setMessage("找不到登机口的规则文件！");
            else if (e.getMessage().contains("Permission denied")) message.setMessage("您没有调用登机口规则文件的权限！");
        }
        LogUtils.saveLogToMsg(request, message);
        return message;
    }

    @RequiresPermissions("rms:slide_coast_dist:auto_allo")
    @RequestMapping("slide_coast_dist_auto")
    @ResponseBody
    public Message slide_coast_dist_auto(String random, String startTime, String endTime, String packageId, Message message, HttpServletRequest request) {
        try {
            getSlideCoastPkg(packageId);
            resourceSharingService.slideCoastAuto(ResourceType.SLIDE_COAST, buildAutoDistParams(false, startTime, endTime, random), packageId, message);
            LogUtils.saveLogToMsg(request, message);
            if (message.isSuccess()) {
                message.setCode(1);
                message.setMessage("滑槽自动分配已成功添加到任务队列中！");
            } else if (message.getCode() == -3) {

            } else {
                message.setCode(0);
                message.setMessage("滑槽自动分配任务队列已满，请稍候再试！");
            }
        } catch (Exception e) {
            message.setCode(0);
            if (e.getMessage().contains("not found")) message.setMessage("找不到滑槽的规则文件！");
            else if (e.getMessage().contains("Permission denied")) message.setMessage("您没有调用滑槽规则文件的权限！");
        }
        return message;
    }

    @RequiresPermissions("rms:rs:checkin_counter_dist")
    @RequestMapping("checkin_counter_dist_auto")
    @ResponseBody
    public Message checkin_counter_dist_auto(Message message, String random, String packageId, String startTime, String endTime, HttpServletRequest request) {
        try {
            getCheckinCounterPkg(packageId);
            resourceSharingService.checkingCounterAuto(ResourceType.CHECKIN_COUNTER, buildAutoDistParams(false, startTime, endTime, random), packageId, message);
            if (message.isSuccess()) {
                message.setMsg(1, "值机柜台自动分配已成功添加到任务队列中！");
            } else if (message.getCode() == -3) {

            } else {
                message.setMsg(0, "值机柜台自动分配任务队列已满，请稍候再试！");
            }
        } catch (Exception e) {
            message.setCode(0);
            if (e.getMessage().contains("not found")) message.setMessage("找不到值机柜台的规则文件！");
            else if (e.getMessage().contains("Permission denied")) message.setMessage("您没有调用值机柜台规则文件的权限！");
        }
        LogUtils.saveLogToMsg(request, message);
        return message;
    }

    //wjp_2017年8月12日21时29分 新加功能
    @RequiresPermissions("rms:rs:checkin_counter_dist")
    @RequestMapping("checkin_counter_dist_auto_manual")
    @ResponseBody
    public Message checkin_counter_dist_auto_manual(Message message, String random, String agentIds, String companyCodes, String id, String inteCode, String intlCode, HttpServletRequest request) {
        try {
            resourceSharingService.checkingCounterManualForAgent(message, random, id, agentIds, companyCodes, inteCode, intlCode);
            if (message.isSuccess()) {
                LogUtils.saveLogToMsg(request, message);
                //message.setMsg(1, "值机柜台批量分配成功！");
            } else if (message.getCode() == -3) {

            } else {
                //message.setMsg(0, "值机柜台自动分配任务队列已满，请稍候再试！");
            }
        } catch (Exception e) {
            message.setCode(0);
            if (e.getMessage().contains("not found")) message.setMessage("找不到值机柜台的规则文件！");
            else if (e.getMessage().contains("Permission denied")) message.setMessage("您没有调用值机柜台规则文件的权限！");
        }
        return message;
    }

    @RequiresPermissions("rms:rs:security_checkin_dist")
    @RequestMapping("security_checkin_dist_auto")
    @ResponseBody
    public Message security_checkin_dist_auto(String random, String startTime, String endTime, String packageId, Message message, HttpServletRequest request) {
        try {
            getSecurityCheckinPkg(packageId);
            resourceSharingService.securityCheckinAuto(ResourceType.SECURITY_CHECKIN, buildAutoDistParams(false, startTime, endTime, random), packageId, message);
            if (message.isSuccess()) {
                message.setCode(1);
                message.setMessage("安检口自动分配已成功添加到任务队列中！");
            } else if (message.getCode() == -3) {

            } else {
                message.setCode(0);
                message.setMessage("安检口自动分配任务队列已满，请稍候再试！");
            }
        } catch (Exception e) {
            message.setCode(0);
            if (e.getMessage().contains("not found")) message.setMessage("找不到安检口的规则文件！");
            else if (e.getMessage().contains("Permission denied")) message.setMessage("您没有调用安检口规则文件的权限！");
        }
        LogUtils.saveLogToMsg(request, message);
        return message;
    }

    @RequiresPermissions("rms:rs:departure_hall_dist")
    @RequestMapping("departure_hall_dist_auto")
    @ResponseBody
    public Message departure_hall_dist_auto(String random, String startTime, String endTime, String packageId, Message message, HttpServletRequest request) {
        try {
            getDepartureHallPkg(packageId);
            resourceSharingService.departureHallAuto(ResourceType.DEPARTURE_HALL, buildAutoDistParams(false, startTime, endTime, random), packageId, message);
            if (message.isSuccess()) {
                message.setCode(1);
                message.setMessage("候机厅自动分配已成功添加到任务队列中！");
            } else if (message.getCode() == -3) {

            } else {
                message.setCode(0);
                message.setMessage("候机厅自动分配任务队列已满，请稍候再试！");
            }
        } catch (Exception e) {
            message.setCode(0);
            if (e.getMessage().contains("not found")) message.setMessage("找不到候机厅的规则文件！");
            else if (e.getMessage().contains("Permission denied")) message.setMessage("您没有调用候机厅规则文件的权限！");
        }
        LogUtils.saveLogToMsg(request, message);
        return message;
    }

    @RequiresPermissions("rms:aircraft_dist:auto_allo")
    @RequestMapping("gateDistAutomatically")
    @ResponseBody
    public Message gateDistAutomatically(PageListenerEnhanceEntity entity, String startTime, String endTime, String packageId, Message message, HttpServletRequest request) {
        try {
            getAircraftStandPkg(packageId);

            //类型为出港(out_pair)/单进(in)，选择出港，则查询出港计划起飞时间在时间段内的航班数据,及连班；选择单进，则选择进港计划到达在时间段内的单进航班数据
            String allotType = request.getParameter("allotType");

            Map<String, Object> params = buildAutoDistParams(false, startTime, endTime, entity.getRandom());
            params.put("need_save", true);
            params.put("allotType", allotType); //wjp_2017年8月19日14时17分
            resourceSharingService.aircraftNumAuto(ResourceType.AIRCRAFT_STND, params, packageId, message);
            if (message.isSuccess()) {
                message.setCode(1);
                message.setMessage("机位自动分配已成功添加到任务队列中！");
            } else if (message.getCode() == -3) {

            } else {
                message.setCode(0);
                message.setMessage("机位自动分配任务队列已满，请稍候再试！");
            }
        } catch (Exception e) {
            message.setCode(0);
            if (e.getMessage().contains("not found")) message.setMessage("找不到机位的规则文件！");
            else if (e.getMessage().contains("Permission denied")) message.setMessage("您没有调用机位规则文件的权限！");
        }
        LogUtils.saveLogToMsg(request, message);
        return message;
    }

    @RequiresPermissions("rms:rs:aircraft_dist")
    @RequestMapping("distedGateListJson")
    @ResponseBody
    public List<GateGanttWrapper> distedGateListJson(@RequestParam("startDate") String startDate, @RequestParam("overDate") String overDate) {
        List<GateGanttWrapper> wrappers = null;
        try {
            wrappers = flightDynamicService.listGateJson(DateUtils.parseDate(startDate, "yyyy-MM-dd"), DateUtils.parseDate(overDate, "yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
            LoggerFactory.getLogger(getClass()).error("转换时间的时候出现错误，无数据返回。");
        }

        return wrappers;
    }

    @RequiresPermissions("rms:rs:aircraft_dist")
    @RequestMapping("distedGateListJson4Mock")
    @ResponseBody
    public List<GateGanttWrapper> distedGateListJson4Mock(@RequestParam("infoId") String infoId) {
        return flightDynamicService.listGateJson4Mock(infoId);
    }


    @RequiresPermissions("rms:rs:aircraft_dist")
    @RequestMapping("generateGanttWrapper")
    @ResponseBody
    public List<GateGanttWrapper> distedGateJson(FlightPlanPair flightPlanPair) {
        return flightDynamicService.convertGanttWrappersByFlightPlanPair(Lists.newArrayList(flightPlanPair));
    }

    @RequiresPermissions("rms:rs:aircraft_dist")
    @RequestMapping("saveGateModification")
    @ResponseBody
    public Message saveGateModification(GateOccupyingInfo gateOccupyingInfo) {
        Message message = new Message();
        FlightDynamic flightDynamic = flightDynamicService.get(gateOccupyingInfo.getFlightDynamicId());
        gateOccupyingInfo.setFlightDynamicCode(flightDynamic.getFlightNum());
        gateOccupyingInfoService.save(gateOccupyingInfo);
        flightDynamic.setPlaceNum(gateOccupyingInfo.getActualGateNum());
        flightDynamic.setRandom(gateOccupyingInfo.getRandom());
        flightDynamicService.save(flightDynamic);
        message.setCode(1);
        message.setMessage("更新成功");
        return message;
    }

    @RequiresPermissions("rms:rs:aircraft_dist")
    @RequestMapping("aircraftLeaveGate")
    @ResponseBody
    public Message aircraftLeaveGate(String flightDynamicId, String leaveTime, String random) {
        Message message = new Message();

        try {
            GateOccupyingInfo queryEntity = gateOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            queryEntity.setLeave("1");
            queryEntity.setLeaveTime(DateUtils.parseDate(leaveTime, "yyyy-MM-dd HH:mm:ss"));
            queryEntity.setRandom(random);
            gateOccupyingInfoService.save(queryEntity);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage(e.getMessage());
        }

        message.setCode(1);
        message.setMessage("操作成功！");
        return message;
    }

    @RequiresPermissions("rms:rs:aircraft_dist")
    @RequestMapping("getOccupyingTime")
    @ResponseBody
    public Map<String, Object> getOccupyingTime(@RequestParam("flightDynamicId") String flightDynamicId) {
        FlightPlanPair resultEntity = flightPlanPairService.get(flightDynamicId);
        return resultEntity == null ? null : ResourceDistUtils.calcOccupyingTimeByPair(resultEntity);
    }

    @RequiresPermissions("rms:rs:carousel_dist")
    @RequestMapping("getCarouselOccupyingTime")
    @ResponseBody
    public Map<String, Object> getCarouselOccupyingTime(@RequestParam("flightDynamicId") String flightDynamicId) {
        FlightDynamic resultEntity = flightDynamicService.get(flightDynamicId);
        if (null == resultEntity) return null;
        return getCarouselOccupyingTime(resultEntity);
    }

    @RequiresPermissions("rms:rs:carousel_dist")
    @RequestMapping("getCarouselOccupyingTimeByDynamic")
    @ResponseBody
    public Map<String, Object> getCarouselOccupyingTime(FlightDynamic resultEntity) {
        return resourceSharingService.getCarouselOccupyingTime(resultEntity);
    }

    @RequiresPermissions("rms:rs:arrival_gate_dist")
    @RequestMapping("getArrivalGateOccupyingTime")
    @ResponseBody
    public Map<String, Object> getArrivalGateOccupyingTime(@RequestParam("flightDynamicId") String flightDynamicId) {
        FlightDynamic resultEntity = flightDynamicService.get(flightDynamicId);
        if (null == resultEntity) return null;
        return getArrivalGateOccupyingTime(resultEntity);
    }

    @RequiresPermissions("rms:rs:arrival_gate_dist")
    @RequestMapping("getArrivalGateOccupyingTimeByDynamic")
    @ResponseBody
    public Map<String, Object> getArrivalGateOccupyingTime(FlightDynamic resultEntity) {
        return resourceSharingService.getArrivalGateOccupyingTime(resultEntity);
    }

    @RequiresPermissions("rms:rs:boarding_gate_dist")
    @RequestMapping("getBoardingGateOccupyingTime")
    @ResponseBody
    public Map<String, Object> getBoardingGateOccupyingTime(@RequestParam("flightDynamicId") String flightDynamicId) {
        FlightDynamic resultEntity = flightDynamicService.get(flightDynamicId);
        if (null == resultEntity) return null;
        return getBoardingGateOccupyingTime(resultEntity);
    }

    @RequiresPermissions("rms:rs:boarding_gate_dist")
    @RequestMapping("getBoardingGateOccupyingTimeByDynamic")
    @ResponseBody
    public Map<String, Object> getBoardingGateOccupyingTime(FlightDynamic resultEntity) {
        return resourceSharingService.getBoardingGateOccupyingTime(resultEntity);
    }

    @RequiresPermissions("rms:rs:slide_coast_dist")
    @RequestMapping("getSlideCoastOccupyingTime")
    @ResponseBody
    public Map<String, Object> getSlideCoastOccupyingTime(@RequestParam("flightDynamicId") String flightDynamicId) {
        FlightDynamic resultEntity = flightDynamicService.get(flightDynamicId);
        if (null == resultEntity) return null;
        return getSlideCoastOccupyingTime(resultEntity);
    }

    @RequiresPermissions("rms:rs:slide_coast_dist")
    @RequestMapping("getSlideCoastOccupyingTimeByDynamic")
    @ResponseBody
    public Map<String, Object> getSlideCoastOccupyingTime(FlightDynamic resultEntity) {
        return resourceSharingService.getSlideCoastOccupyingTime(resultEntity);
    }

    @RequiresPermissions("rms:rs:checkin_counter_dist")
    @RequestMapping("getCheckinCounterOccupyingTime")
    @ResponseBody
    public Map<String, Object> getCheckinCounterOccupyingTime(@RequestParam("flightDynamicId") String flightDynamicId) {
        FlightDynamic resultEntity = flightDynamicService.get(flightDynamicId);
        if (null == resultEntity) return null;
        return getCheckinCounterOccupyingTime(resultEntity);
    }

    @RequiresPermissions("rms:rs:checkin_counter_dist")
    @RequestMapping("getCheckinCounterOccupyingTimeByDynamic")
    @ResponseBody
    public Map<String, Object> getCheckinCounterOccupyingTime(FlightDynamic flightDynamic) {
        return resourceSharingService.getCheckinCounterOccupyingTime(flightDynamic);
    }

    @RequiresPermissions("rms:rs:security_checkin_dist")
    @RequestMapping("getSecurityCheckinOccupyingTime")
    @ResponseBody
    public Map<String, Object> getSecurityCheckinOccupyingTime(@RequestParam("flightDynamicId") String flightDynamicId) {
        FlightDynamic resultEntity = flightDynamicService.get(flightDynamicId);
        if (null == resultEntity) return null;
        return getSecurityCheckinOccupyingTime(resultEntity);
    }

    @RequiresPermissions("rms:rs:security_checkin_dist")
    @RequestMapping("getSecurityCheckinOccupyingTimeByDynamic")
    @ResponseBody
    public Map<String, Object> getSecurityCheckinOccupyingTime(FlightDynamic resultEntity) {
        return resourceSharingService.getSecurityCheckinOccupyingTime(resultEntity);
    }

    @RequiresPermissions("rms:rs:departure_hall_dist")
    @RequestMapping("getDepartureHallOccupyingTime")
    @ResponseBody
    public Map<String, Object> getDepartureHallOccupyingTime(@RequestParam("flightDynamicId") String flightDynamicId) {
        FlightDynamic resultEntity = flightDynamicService.get(flightDynamicId);
        if (null == resultEntity) return null;
        return getDepartureHallOccupyingTime(resultEntity);

    }

    @RequiresPermissions("rms:rs:departure_hall_dist")
    @RequestMapping("getDepartureHallOccupyingTimeByDynamic")
    @ResponseBody
    public Map<String, Object> getDepartureHallOccupyingTime(FlightDynamic flightDynamic) {
        return resourceSharingService.getDepartureHallOccupyingTime(flightDynamic);
    }


    @RequiresPermissions("rms:rs:carousel_dist")
    @RequestMapping("carousel_manual_dist")
    @ResponseBody
    public Message carousel_manual_dist(SingleResourceDistParamWrapper params, HttpServletRequest request) {
        Message message = new Message(0);

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(params.getFlightDynamicId());
            if (null == flightDynamic) return throwsMessageError(request, -2, "行李转盘手动分配失败：航班动态数据同步中，请稍候再试！");
            CarouselOccupyingInfo carouselOccupyingInfo = carouselOccupyingInfoService.getByFlightDynamicId(params.getFlightDynamicId());
            if (null == carouselOccupyingInfo)
                return throwsMessageError(request, -2, "行李转盘手动分配失败：当前航班信息找不到相应的行李转盘占用数据，无法分配！");

//            if (!params.getForce()) {
//                List<String> resList = fillResListDependsOnNature(params);
//
//                if (!Collections3.isEmpty(resList)) {
//                    for (String res : resList) {
//                        RmsCarousel rmsCarousel = rmsCarouselService.getByCode(res);
//                        if (null == rmsCarousel) return throwsMessageError(request, 0, "行李转盘手动分配失败：不存在的行李转盘！");
//                        if (rmsCarousel.getCarouselTypeCode().equals("0"))
//                            return throwsMessageError(request, 0, "行李转盘手动分配失败：行李转盘不可用！");
//                    }
//                }
//
//                checkOccupied(params, flightDynamicService.findOccupiedCarouselCodes(buildOccupiedQueryCritera(flightDynamic, carouselOccupyingInfo)));
//            }

//            if (params.getOccupied() && !params.getForce()) {
//                message.setCode(0);
//                message.setMessage("行李转盘手动分配失败：当前行李转盘「" + params.getResourceCode() + "」已被占用！");
//                LogUtils.saveLog(request, message.getMessage());
//            } else {
//                carouselOccupyingInfo.setInteCarouselCode(params.getInte());
//                carouselOccupyingInfo.setIntlCarouselCode(params.getIntl());
//                carouselOccupyingInfo.setRandom(params.getRandom());
//                resourceSharingService.carouselManual(flightDynamic, carouselOccupyingInfo, message);
//                LogUtils.saveLogToMsg(request, message);
//            }

            carouselOccupyingInfo.setInteCarouselCode(params.getInte());
            carouselOccupyingInfo.setIntlCarouselCode(params.getIntl());
            carouselOccupyingInfo.setRandom(params.getRandom());
            resourceSharingService.carouselManual(flightDynamic, carouselOccupyingInfo, message);
            if (message.isSuccess()) {
                LogUtils.saveLogToMsg(request, message);
                message.setMessage("分配成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("行李转盘手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:carousel_dist")
    @RequestMapping("carousel_manual_dist_4_mock")
    @ResponseBody
    public Message common_manual_dist_4_mock(SingleResourceDistParamWrapper params, HttpServletRequest request) {
        Message message = argsValidate4MockDist(params, request);
        if (!message.isSuccess()) return message;

        ResourceMockDistDetail resourceMockDistDetail = params.getResourceMockDistDetail();
        ResourceMockDistInfo resourceMockDistInfo = params.getResourceMockDistInfo();

        try {
            CarouselOccupyingInfo occupyingInfo = carouselOccupyingInfoService.getByFlightDynamicId(resourceMockDistDetail.getDataId());
            if (null == occupyingInfo) return throwsMessageError(request, 0, "资源占用信息不存在！");

            if (!params.getForce())
                checkOccupied(params, flightDynamicService.findOccupiedCarouselCodes4Mock(buildOccupiedQueryCriteria4Mock(resourceMockDistDetail, resourceMockDistInfo, occupyingInfo)));
            commonMockDistPersistence2DB(params, message, request);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("模拟分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:arrival_gate_dist")
    @RequestMapping("arrival_gate_manual_dist_4_mock")
    @ResponseBody
    public Message arrival_gate_manual_dist_4_mock(SingleResourceDistParamWrapper params, HttpServletRequest request) {
        Message message = argsValidate4MockDist(params, request);
        if (!message.isSuccess()) return message;

        ResourceMockDistDetail resourceMockDistDetail = params.getResourceMockDistDetail();
        ResourceMockDistInfo resourceMockDistInfo = params.getResourceMockDistInfo();

        try {
            ArrivalGateOccupyingInfo occupyingInfo = arrivalGateOccupyingInfoService.getByFlightDynamicId(resourceMockDistDetail.getDataId());
            if (null == occupyingInfo) return throwsMessageError(request, 0, "资源占用信息不存在！");

            if (!params.getForce())
                checkOccupied(params, flightDynamicService.findOccupiedArrivalGateCodes4Mock(buildOccupiedQueryCriteria4Mock(resourceMockDistDetail, resourceMockDistInfo, occupyingInfo)));
            commonMockDistPersistence2DB(params, message, request);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("模拟分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:boarding_gate_dist")
    @RequestMapping("boarding_gate_manual_dist_4_mock")
    @ResponseBody
    public Message boarding_gate_manual_dist_4_mock(SingleResourceDistParamWrapper params, HttpServletRequest request) {
        Message message = argsValidate4MockDist(params, request);
        if (!message.isSuccess()) return message;

        ResourceMockDistDetail resourceMockDistDetail = params.getResourceMockDistDetail();
        ResourceMockDistInfo resourceMockDistInfo = params.getResourceMockDistInfo();

        try {
            BoardingGateOccupyingInfo occupyingInfo = boardingGateOccupyingInfoService.getByFlightDynamicId(resourceMockDistDetail.getDataId());
            if (null == occupyingInfo) return throwsMessageError(request, 0, "资源占用信息不存在！");

            if (!params.getForce())
                checkOccupied(params, flightDynamicService.findOccupiedBoardingGateCodes4Mock(buildOccupiedQueryCriteria4Mock(resourceMockDistDetail, resourceMockDistInfo, occupyingInfo)));
            commonMockDistPersistence2DB(params, message, request);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("模拟分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }


    @RequiresPermissions("rms:rs:slide_coast_dist")
    @RequestMapping("slide_coast_manual_dist_4_mock")
    @ResponseBody
    public Message slide_coast_manual_dist_4_mock(SingleResourceDistParamWrapper params, HttpServletRequest request) {
        Message message = argsValidate4MockDist(params, request);
        if (!message.isSuccess()) return message;

        ResourceMockDistDetail resourceMockDistDetail = params.getResourceMockDistDetail();
        ResourceMockDistInfo resourceMockDistInfo = params.getResourceMockDistInfo();

        try {
            SlideCoastOccupyingInfo occupyingInfo = slideCoastOccupyingInfoService.getByFlightDynamicId(resourceMockDistDetail.getDataId());
            if (null == occupyingInfo) return throwsMessageError(request, 0, "资源占用信息不存在！");

            if (!params.getForce())
                checkOccupied(params, flightDynamicService.findOccupiedSlideCoastCodes4Mock(buildOccupiedQueryCriteria4Mock(resourceMockDistDetail, resourceMockDistInfo, occupyingInfo)));
            commonMockDistPersistence2DB(params, message, request);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("模拟分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    /**
     * 根据属性添加资源到集合中
     *
     * @param params
     * @return
     */
    private List<String> fillResListDependsOnNature(SingleResourceDistParamWrapper params) {
        List<String> resList = Lists.newArrayList();
        switch (params.getNature()) {
            case ResourceDistUtils.INTE_RES:
                if (StringUtils.isNotEmpty(params.getInte())) resList.add(params.getInte());
                break;
            case ResourceDistUtils.INTL_RES:
                if (StringUtils.isNotEmpty(params.getIntl())) resList.add(params.getIntl());
                break;
            default:
                if (StringUtils.isNotEmpty(params.getInte())) resList.add(params.getInte());
                if (StringUtils.isNotEmpty(params.getIntl())) resList.add(params.getIntl());
                break;
        }
        return resList;
    }

    /**
     * 将模拟分配信息存在到数据库中
     *
     * @param params
     * @param message 传递给前台的消息
     * @param request
     */
    private void commonMockDistPersistence2DB(SingleResourceDistParamWrapper params, Message message, HttpServletRequest request) {
        if (params.getOccupied() && !params.getForce()) {
            message.setCode(0);
            message.setMessage("手动分配失败：当前资源「" + params.getResourceCode() + "」已被占用！");
            LogUtils.saveLog(request, message.getMessage());
        } else {
            params.getResourceMockDistDetail().setInte(params.getInte());
            params.getResourceMockDistDetail().setIntl(params.getIntl());
            resourceMockDistDetailService.save(params.getResourceMockDistDetail());
            message.setCode(1);
            message.setMessage("分配成功！");
        }
    }

    /**
     * 通用的模拟分配资源验证
     *
     * @param params
     * @param request
     * @return
     */
    private Message argsValidate4MockDist(SingleResourceDistParamWrapper params, HttpServletRequest request) {
        if (null == params) return throwsMessageError(request, 0, "不合法的分配参数！");
        if (StringUtils.isBlank(params.getFlightDynamicId())) return throwsMessageError(request, 0, "模拟分配详情ID不能为空！");

        params.setResourceMockDistDetail(resourceMockDistDetailService.get(params.getFlightDynamicId()));
        if (null == params.getResourceMockDistDetail()) return throwsMessageError(request, 0, "模拟分配详情信息不存在！");

        params.setResourceMockDistInfo(resourceMockDistInfoService.get(params.getResourceMockDistDetail().getInfoId()));
        if (null == params.getResourceMockDistInfo()) return throwsMessageError(request, 0, "模拟分配信息不存在！");

        return new Message(1);
    }

    /**
     * 构建占用情况查询参数
     *
     * @param flightDynamic 航班动态
     * @param occupyingInfo 占用信息
     * @return Map
     */
    private Map<String, Object> buildOccupiedQueryCritera(FlightDynamic flightDynamic, Object occupyingInfo) {
        Date startDate = (Date) Reflections.getFieldValue(occupyingInfo, "expectedStartTime");
        Date overDate = (Date) Reflections.getFieldValue(occupyingInfo, "expectedOverTime");
        Integer thresholdValue = Integer.parseInt(DictUtils.getDictValue("资源分配间隔值", "threshold_value", "15"));
        return ImmutableMap.of(
                "start", "'" + DateUtils.formatDateTime(DateUtils.addMinutes(startDate, -thresholdValue)) + "'",
                "over", "'" + DateUtils.formatDateTime(DateUtils.addMinutes(overDate, thresholdValue)) + "'",
                "flightDynamic", flightDynamic.getId());
    }

    /**
     * 构建模拟分配占用情况查询参数
     *
     * @param resourceMockDistDetail
     * @param resourceMockDistInfo
     * @param occupyingInfo
     * @return
     */
    private Map<String, Object> buildOccupiedQueryCriteria4Mock(ResourceMockDistDetail resourceMockDistDetail, ResourceMockDistInfo resourceMockDistInfo, Object occupyingInfo) {
        Integer thresholdValue = Integer.parseInt(DictUtils.getDictValue("资源分配间隔值", "threshold_value", "15"));
        Map<String, Object> criteria = new HashMap<>();
        criteria.put("mockInfoId", resourceMockDistDetail.getInfoId());
        criteria.put("omitOci", true);
        String startMockDate = DateUtils.formatDate(resourceMockDistInfo.getMockStartDate()), overMockDate = DateUtils.formatDate(resourceMockDistInfo.getMockOverDate());
        if (startMockDate.equals(overMockDate)) criteria.put("samePlanDate", true);
        criteria.put("startMockPlanDate", DateUtils.parseDate(startMockDate));
        criteria.put("overMockPlanDate", DateUtils.parseDate(overMockDate));
        criteria.put("flightDynamic", resourceMockDistDetail.getDataId());
        Date startDate = (Date) Reflections.getFieldValue(occupyingInfo, "expectedStartTime");
        Date overDate = (Date) Reflections.getFieldValue(occupyingInfo, "expectedOverTime");
        criteria.put("start", "'" + DateUtils.formatDateTime(DateUtils.addMinutes(startDate, -thresholdValue)) + "'");
        criteria.put("over", "'" + DateUtils.formatDateTime(DateUtils.addMinutes(overDate, thresholdValue)) + "'");

        return criteria;
    }

    /**
     * 检查占用情况
     *
     * @param params
     * @param occupiedCodes
     */
    private void checkOccupied(SingleResourceDistParamWrapper params, List<String> occupiedCodes) {
//        if (StringUtils.equals(params.getNature(), ResourceDistUtils.INTE_RES)) {   // 国内
//            if (occupiedCodes.contains(params.getInte())) {
//                params.setOccupied(true);
//                params.setResourceCode(params.getInte());
//            }
//        } else if (StringUtils.equals(params.getNature(), ResourceDistUtils.INTE_RES)) { // 国际
//            if (occupiedCodes.contains(params.getIntl())) {
//                params.setOccupied(true);
//                params.setResourceCode(params.getIntl());
//            }
//        } else if (StringUtils.equals(params.getNature(), ResourceDistUtils.MIXING_RES)) { // 混合
//            if (occupiedCodes.contains(params.getInte())) {
//                params.setOccupied(true);
//                params.setResourceCode(params.getInte());
//            } else if (occupiedCodes.contains(params.getIntl())) {
//                params.setOccupied(true);
//                params.setResourceCode(params.getIntl());
//            }
//        }

        // 占用不应区分资源类型，只要时间上有冲突，统一认为是被占用

        occupiedCodes = occupiedCodes.stream().filter(str -> !org.apache.commons.lang3.StringUtils.isEmpty(str)).collect(Collectors.toList());
        occupiedCodes.removeAll(Collections.singleton(null));

        if (!Collections3.isEmpty(occupiedCodes)) {
            if (occupiedCodes.contains(params.getInte())) {
                params.setOccupied(true);
                params.setResourceCode(params.getInte());
            } else if (occupiedCodes.contains(params.getIntl())) {
                params.setOccupied(true);
                params.setResourceCode(params.getIntl());
            }
        }
    }

    @RequiresPermissions("rms:rs:arrival_gate_dist")
    @RequestMapping("arrival_gate_manual_dist")
    @ResponseBody
    public Message arrival_gate_manual_dist(SingleResourceDistParamWrapper params, HttpServletRequest request) {
        Message message = new Message(0);

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(params.getFlightDynamicId());
            if (null == flightDynamic) return throwsMessageError(request, -2, "到港门手动分配失败：航班动态数据同步中，请稍候再试！");
            ArrivalGateOccupyingInfo arrivalGateOccupyingInfo = arrivalGateOccupyingInfoService.getByFlightDynamicId(params.getFlightDynamicId());
            if (null == arrivalGateOccupyingInfo)
                return throwsMessageError(request, -2, "到港门手动分配失败：当前航班信息找不到相应的到港门占用数据，无法分配！");

            if (!params.getForce()) {
                List<String> resList = fillResListDependsOnNature(params);

                if (!Collections3.isEmpty(resList)) {
                    for (String res : resList) {
                        ArrivalGate arrivalGate = arrivalGateService.getByCode(res);
                        if (null == arrivalGate) return throwsMessageError(request, 0, "到港门手动分配失败：不存在的到港门！");
                        if (arrivalGate.getArrivalGateStatus().equals("0"))
                            return throwsMessageError(request, 0, "到港门手动分配失败：到港门不可用！");
                    }
                }

                checkOccupied(params, flightDynamicService.findOccupiedArrivalGateCodes(buildOccupiedQueryCritera(flightDynamic, arrivalGateOccupyingInfo)));
            }

            if (params.getOccupied() && !params.getForce()) {
                message.setCode(0);
                message.setMessage("到港门手动分配失败：当前到港门「" + params.getResourceCode() + "」已被占用！");
                LogUtils.saveLog(request, message.getMessage());
            } else {
                arrivalGateOccupyingInfo.setInteArrivalGateCode(params.getInte());
                arrivalGateOccupyingInfo.setIntlArrivalGateCode(params.getIntl());
                arrivalGateOccupyingInfo.setRandom(params.getRandom());
                resourceSharingService.arrivalGateManual(flightDynamic, arrivalGateOccupyingInfo, message);
                LogUtils.saveLogToMsg(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("到港门手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:boarding_gate_dist")
    @RequestMapping("boarding_gate_manual_dist")
    @ResponseBody
    public Message boarding_gate_manual_dist(SingleResourceDistParamWrapper params, HttpServletRequest request) {
        Message message = new Message(0);

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(params.getFlightDynamicId());
            if (null == flightDynamic) return throwsMessageError(request, -2, "登机口手动分配失败：航班动态数据同步中，请稍候再试！");
            BoardingGateOccupyingInfo boardingGateOccupyingInfo = boardingGateOccupyingInfoService.getByFlightDynamicId(params.getFlightDynamicId());
            if (null == boardingGateOccupyingInfo)
                return throwsMessageError(request, -2, "登机口手动分配失败：当前航班信息找不到相应的登机口占用数据，无法分配！");

            if (!params.getForce()) {
                List<String> resList = fillResListDependsOnNature(params);

                if (!Collections3.isEmpty(resList)) {
                    for (String res : resList) {
                        BoardingGate boardingGate = boardingGateService.getByCode(res);
                        if (null == boardingGate) return throwsMessageError(request, 0, "登机口手动分配失败：不存在的登机口！");
                        if (boardingGate.getBoardingGateStatus().equals("0"))
                            return throwsMessageError(request, 0, "登机口手动分配失败：登机口不可用！");
                    }
                }

                checkOccupied(params, flightDynamicService.findOccupiedBoardingGateCodes(buildOccupiedQueryCritera(flightDynamic, boardingGateOccupyingInfo)));
            }

            if (params.getOccupied() && !params.getForce()) {
                message.setCode(0);
                message.setMessage("登机口手动分配失败：当前登机口「" + params.getResourceCode() + "」已被占用！");
                LogUtils.saveLog(request, message.getMessage());
            } else {
                boardingGateOccupyingInfo.setInteBoardingGateCode(params.getInte());
                boardingGateOccupyingInfo.setIntlBoardingGateCode(params.getIntl());
                boardingGateOccupyingInfo.setRandom(params.getRandom());
                resourceSharingService.boardingGateManual(flightDynamic, boardingGateOccupyingInfo, message);
                LogUtils.saveLogToMsg(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("登机口手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:slide_coast_dist")
    @RequestMapping("slide_coast_manual_dist")
    @ResponseBody
    public Message slide_coast_manual_dist(SingleResourceDistParamWrapper params, HttpServletRequest request) {
        Message message = new Message(0);

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(params.getFlightDynamicId());
            if (null == flightDynamic) return throwsMessageError(request, -2, "滑槽手动分配失败：航班动态数据同步中，请稍候再试！");
            SlideCoastOccupyingInfo slideCoastOccupyingInfo = slideCoastOccupyingInfoService.getByFlightDynamicId(params.getFlightDynamicId());
            if (null == slideCoastOccupyingInfo)
                return throwsMessageError(request, -2, "滑槽手动分配失败：当前航班信息找不到相应的滑槽占用数据，无法分配！");


            if (!params.getForce()) {
                List<String> resList = fillResListDependsOnNature(params);

                if (!Collections3.isEmpty(resList)) {
                    for (String res : resList) {
                        SlideCoast slideCoast = slideCoastService.getByCode(res);
                        if (null == slideCoast) return throwsMessageError(request, 0, "滑槽手动分配失败：不存在的滑槽！");
                        if (slideCoast.getSlideCoastStatus().equals("0"))
                            return throwsMessageError(request, 0, "滑槽手动分配失败：滑槽不可用！");
                    }
                }

                checkOccupied(params, flightDynamicService.findOccupiedSlideCoastCodes(buildOccupiedQueryCritera(flightDynamic, slideCoastOccupyingInfo)));
            }

            if (params.getOccupied() && !params.getForce()) {
                message.setCode(0);
                message.setMessage("滑槽手动分配失败：当前滑槽「" + params.getResourceCode() + "」已被占用！");
                LogUtils.saveLog(request, message.getMessage());
            } else {
                slideCoastOccupyingInfo.setInteSlideCoastCode(params.getInte());
                slideCoastOccupyingInfo.setIntlSlideCoastCode(params.getIntl());
                slideCoastOccupyingInfo.setRandom(params.getRandom());
                resourceSharingService.slideCoastManual(flightDynamic, slideCoastOccupyingInfo, message);
                LogUtils.saveLogToMsg(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("滑槽手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }


    @RequiresPermissions("rms:rs:carousel_dist")
    @RequestMapping("carousel_manual_checking_4_mixing")
    @ResponseBody
    public Message carousel_manual_checking_4_mixing(@RequestParam("flightDynamicId") String flightDynamicId,
                                                     @RequestParam("inteCarouselCode") String inteCarouselCode,
                                                     @RequestParam("intlCarouselCode") String intlCarouselCode,
                                                     @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();
        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, -2, "行李转盘手动分配失败：航班动态数据同步中，请稍候再试！");
            CarouselOccupyingInfo carouselOccupyingInfo = carouselOccupyingInfoService.getByFlightDynamicId(flightDynamicId);

            if (carouselOccupyingInfo == null)
                return throwsMessageError(request, -2, "行李转盘手动分配失败：当前航班信息找不到相应的行李转盘占用数据，无法分配！");

            carouselOccupyingInfo.setInteCarouselCode(inteCarouselCode);
            carouselOccupyingInfo.setIntlCarouselCode(intlCarouselCode);
            carouselOccupyingInfo.setRandom(random);
            resourceSharingService.carouselManual(flightDynamic, carouselOccupyingInfo, message);

            ConcurrentClientsHolder.getSocket("/global/dynamics").emit("assignment_complete_message", "complete");

            if (message.isSuccess()) LogUtils.saveLog(request, message.getMessage());
            message.setMessage("分配成功!");
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("行李转盘手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:arrival_gate_dist")
    @RequestMapping("arrivalGate_manual_checking_4_mixing")
    @ResponseBody
    public Message arrivalGate_manual_checking_4_mixing(@RequestParam("flightDynamicId") String flightDynamicId,
                                                        @RequestParam("inteArrivalGateCode") String inteArrivalGateCode,
                                                        @RequestParam("intlArrivalGateCode") String intlArrivalGateCode,
                                                        @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();
        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, -2, "到港门手动分配失败：航班动态数据同步中，请稍候再试！");

            ArrivalGateOccupyingInfo arrivalGateOccupyingInfo = arrivalGateOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (null == arrivalGateOccupyingInfo)
                return throwsMessageError(request, -2, "到港门手动分配失败：当前航班信息找不到相应的到港门占用数据，无法分配！");
            List<String> occupiedCarousels = flightDynamicService.findOccupiedArrivalGateCodes(buildOccupiedQueryCritera(flightDynamic, arrivalGateOccupyingInfo));

            if (occupiedCarousels.contains(inteArrivalGateCode) || occupiedCarousels.contains(intlArrivalGateCode)) {
                message.setCode(0);
                message.setMessage("到港门手动分配失败：当前到港门「" + (StringUtils.isNotBlank(inteArrivalGateCode) ? inteArrivalGateCode : intlArrivalGateCode) + "」已被占用！");
                LogUtils.saveLog(request, message.getMessage());
            } else {
                message.setCode(1);
                message.setMessage("分配成功！");
            }

            arrivalGateOccupyingInfo.setInteArrivalGateCode(inteArrivalGateCode);
            arrivalGateOccupyingInfo.setIntlArrivalGateCode(intlArrivalGateCode);
            arrivalGateOccupyingInfo.setRandom(random);
            arrivalGateOccupyingInfoService.save(arrivalGateOccupyingInfo);

            ConcurrentClientsHolder.getSocket("/global/dynamics").emit("assignment_complete_message", "complete");

            StringBuffer sb = new StringBuffer();
            sb.append("到港门手动分配成功！分配" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
            sb.append("到港门,").append(arrivalGateOccupyingInfo.toString());
            LogUtils.saveLog(request, sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("到港门手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:boarding_gate_dist")
    @RequestMapping("boardingGate_manual_checking_4_mixing")
    @ResponseBody
    public Message boardingGate_manual_checking_4_mixing(@RequestParam("flightDynamicId") String flightDynamicId,
                                                         @RequestParam("inteBoardingGateCode") String inteBoardingGateCode,
                                                         @RequestParam("intlBoardingGateCode") String intlBoardingGateCode,
                                                         @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();
        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, -2, "登机口手动分配失败：航班动态数据同步中，请稍候再试！");
            BoardingGateOccupyingInfo boardingGateOccupyingInfo = boardingGateOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (null == boardingGateOccupyingInfo)
                return throwsMessageError(request, -2, "登机口手动分配失败：当前航班信息找不到相应的登机口占用数据，无法分配！");

            List<String> occupiedBoardingGateCodes = flightDynamicService.findOccupiedBoardingGateCodes(buildOccupiedQueryCritera(flightDynamic, boardingGateOccupyingInfo));
            if (occupiedBoardingGateCodes.contains(inteBoardingGateCode) || occupiedBoardingGateCodes.contains(intlBoardingGateCode)) {
                message.setCode(0);
                message.setMessage("登机口手动分配失败：当前登机口「" + (StringUtils.isNotBlank(inteBoardingGateCode) ? inteBoardingGateCode : intlBoardingGateCode) + "」已被占用！");
                LogUtils.saveLog(request, message.getMessage());
            } else {
                message.setCode(1);
                message.setMessage("分配成功！");
            }

            boardingGateOccupyingInfo.setInteBoardingGateCode(inteBoardingGateCode);
            boardingGateOccupyingInfo.setIntlBoardingGateCode(intlBoardingGateCode);
            boardingGateOccupyingInfo.setRandom(random);
            boardingGateOccupyingInfoService.save(boardingGateOccupyingInfo);
            ConcurrentClientsHolder.getSocket("/global/dynamics").emit("assignment_complete_message", "complete");

            StringBuffer sb = new StringBuffer();
            sb.append("登机口手动分配成功！分配" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
            sb.append("登机口,").append(boardingGateOccupyingInfo.toString());
            LogUtils.saveLog(request, sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("登机口手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:slide_coast_dist")
    @RequestMapping("slideCoast_manual_checking_4_mixing")
    @ResponseBody
    public Message slideCoast_manual_checking_4_mixing(@RequestParam("flightDynamicId") String flightDynamicId,
                                                       @RequestParam("inteSlideCoastCode") String inteSlideCoastCode,
                                                       @RequestParam("intlSlideCoastCode") String intlSlideCoastCode,
                                                       @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();
        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, -2, "滑槽手动分配失败：航班动态数据同步中，请稍候再试！");
            SlideCoastOccupyingInfo slideCoastOccupyingInfo = slideCoastOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (null == slideCoastOccupyingInfo)
                return throwsMessageError(request, -2, "滑槽手动分配失败：当前航班信息找不到相应的滑槽占用数据，无法分配！");
            List<String> occupiedSlideCoastCodes = flightDynamicService.findOccupiedSlideCoastCodes(buildOccupiedQueryCritera(flightDynamic, slideCoastOccupyingInfo));

            if (occupiedSlideCoastCodes.contains(inteSlideCoastCode) || occupiedSlideCoastCodes.contains(intlSlideCoastCode)) {
                message.setCode(0);
                message.setMessage("滑槽手动分配失败：当前滑槽「" + inteSlideCoastCode + " 或 " + intlSlideCoastCode + "」已被占用！");
                LogUtils.saveLog(request, message.getMessage());
            } else {
                message.setCode(1);
                message.setMessage("分配成功！");
            }

            slideCoastOccupyingInfo.setInteSlideCoastCode(inteSlideCoastCode);
            slideCoastOccupyingInfo.setIntlSlideCoastCode(intlSlideCoastCode);
            slideCoastOccupyingInfo.setRandom(random);
            slideCoastOccupyingInfoService.save(slideCoastOccupyingInfo);
            ConcurrentClientsHolder.getSocket("/global/dynamics").emit("assignment_complete_message", "complete");

            StringBuffer sb = new StringBuffer();
            sb.append("滑槽手动分配成功！分配" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
            sb.append("滑槽,").append(slideCoastOccupyingInfo.toString());
            LogUtils.saveLog(request, sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("滑槽手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    /**
     * 手动分配占用多值机柜台 或 混合的航班
     *
     * @return
     */
    @RequiresPermissions("rms:rs:checkin_counter_dist")
    @RequestMapping("checkinCounter_manual_checking_4_mixing")
    @ResponseBody
    public Message checkinCounter_manual_checking_4_mixing(CheckingCounterOccupyingInfo checkingCounterOccupyingInfo, HttpServletRequest request) {
        Message message = new Message();

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(checkingCounterOccupyingInfo.getFlightDynamicId());
            if (null == flightDynamic) return throwsMessageError(request, -2, "值机柜台手动分配失败：航班动态数据同步中，请稍候再试！");

            if (null == checkingCounterOccupyingInfoService.getByFlightDynamicId(checkingCounterOccupyingInfo.getFlightDynamicId()))
                return throwsMessageError(request, -2, "值机柜台手动分配失败：当前航班信息找不到相应的值机柜台占用数据，无法分配！");

            resourceSharingService.checkingCounterManual(flightDynamic, checkingCounterOccupyingInfo, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("值机柜台手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:security_checkin_dist")
    @RequestMapping("securityCheckin_manual_checking_4_mixing")
    @ResponseBody
    public Message securityCheckin_manual_checking_4_mixing(SecurityCheckinOccupyingInfo securityCheckinOccupyingInfo, HttpServletRequest request) {
        Message message = new Message();

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(securityCheckinOccupyingInfo.getFlightDynamicId());
            if (null == flightDynamic) return throwsMessageError(request, -2, "安检口手动分配失败：航班动态数据同步中，请稍候再试！");
            if (securityCheckinOccupyingInfoService.getByFlightDynamicId(securityCheckinOccupyingInfo.getFlightDynamicId()) == null)
                return throwsMessageError(request, -2, "安检口手动分配失败：当前航班信息找不到相应的安检口占用数据，无法分配！");

            resourceSharingService.securityCheckinManual(flightDynamic, securityCheckinOccupyingInfo, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("安检口手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:departure_hall_dist")
    @RequestMapping("departureHall_manual_checking_4_mixing")
    @ResponseBody
    public Message departureHall_manual_checking_4_mixing(DepartureHallOccupyingInfo departureHallOccupyingInfo, HttpServletRequest request) {
        Message message = new Message();

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(departureHallOccupyingInfo.getFlightDynamicId());
            if (null == flightDynamic) return throwsMessageError(request, -2, "候机厅手动分配失败：航班动态数据同步中，请稍候再试！");
            if (departureHallOccupyingInfoService.getByFlightDynamicId(departureHallOccupyingInfo.getFlightDynamicId()) == null)
                return throwsMessageError(request, -2, "候机厅手动分配失败：当前航班信息找不到相应的候机厅占用数据，无法分配！");

            resourceSharingService.departureHallManual(flightDynamic, departureHallOccupyingInfo, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("候机厅手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:checkin_counter_dist")
    @RequestMapping("checkingCounter_manual_checking")
    @ResponseBody
    public Message checkingCounter_manual_checking(CheckingCounterOccupyingInfo checkingCounterOccupyingInfo, String type, String opCode, HttpServletRequest request) {
        Message message = new Message();

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(checkingCounterOccupyingInfo.getFlightDynamicId());
            if (null == flightDynamic) return throwsMessageError(request, -2, "值机柜台手动分配失败：航班动态数据同步中，请稍候再试！");

            CheckingCounterOccupyingInfo dbinfo = checkingCounterOccupyingInfoService.get(checkingCounterOccupyingInfo.getId());
            if (null == dbinfo) return throwsMessageError(request, -2, "值机柜台手动分配失败：当前航班信息找不到相应的值机柜台占用数据，无法分配！");

            if ("inte".equals(type)) dbinfo.setInteCheckingCounterCode(opCode);
            else dbinfo.setIntlCheckingCounterCode(opCode);

            dbinfo.setRandom(checkingCounterOccupyingInfo.getRandom());
            resourceSharingService.checkingCounterManual(flightDynamic, dbinfo, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("值机柜台手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:checkin_counter_dist")
    @RequestMapping("common_multi_manual_dist_4_mock")
    @ResponseBody
    public Message common_multi_manual_dist_4_mock(String detailId, String inte, String intl, HttpServletRequest request) {
        Message message = new Message();

        try {
            ResourceMockDistDetail resourceMockDistDetail = resourceMockDistDetailService.get(detailId);
            if (null == resourceMockDistDetail) return throwsMessageError(request, 0, "不存在的模拟分配详情信息！");

            resourceMockDistDetail.setInte(inte);
            resourceMockDistDetail.setIntl(intl);

            resourceMockDistDetailService.save(resourceMockDistDetail);
            message.setCode(1);
            message.setMessage("分配成功！");
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:security_checkin_dist")
    @RequestMapping("securityCheckin_manual_checking")
    @ResponseBody
    public Message securityCheckin_manual_checking(SecurityCheckinOccupyingInfo securityCheckinOccupyingInfo, String type, String opCode, String originCode, HttpServletRequest request) {
        Message message = new Message();

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(securityCheckinOccupyingInfo.getFlightDynamicId());
            if (null == flightDynamic) return throwsMessageError(request, -2, "安检口手动分配失败：航班动态数据同步中，请稍候再试！");
            SecurityCheckinOccupyingInfo dbinfo = securityCheckinOccupyingInfoService.get(securityCheckinOccupyingInfo.getId());
            if (null == dbinfo) return throwsMessageError(request, -2, "安检口手动分配失败：当前航班信息找不到相应的安检口占用数据，无法分配！");

            if ("inte".equals(type)) dbinfo.setInteSecurityCheckinCode(opCode);
            else dbinfo.setIntlSecurityCheckinCode(opCode);

            dbinfo.setRandom(securityCheckinOccupyingInfo.getRandom());
            resourceSharingService.securityCheckinManual(flightDynamic, dbinfo, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("安检口手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:departure_hall_dist")
    @RequestMapping("departureHall_manual_checking")
    @ResponseBody
    public Message departureHall_manual_checking(DepartureHallOccupyingInfo departureHallOccupyingInfo, String type, String opCode, String originCode, HttpServletRequest request) {
        Message message = new Message();

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(departureHallOccupyingInfo.getFlightDynamicId());
            if (null == flightDynamic) return throwsMessageError(request, -2, "候机厅手动分配失败：航班动态数据同步中，请稍候再试！");

            DepartureHallOccupyingInfo dbinfo = departureHallOccupyingInfoService.get(departureHallOccupyingInfo.getId());
            if (null == dbinfo) return throwsMessageError(request, -2, "候机厅手动分配失败：当前航班信息找不到相应的候机厅占用数据，无法分配！");

            if ("inte".equals(type)) dbinfo.setInteDepartureHallCode(opCode);
            else dbinfo.setIntlDepartureHallCode(opCode);

            dbinfo.setRandom(departureHallOccupyingInfo.getRandom());
            resourceSharingService.departureHallManual(flightDynamic, dbinfo, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("候机厅手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:carousel_dist")
    @RequestMapping("carousel_manual_checking")
    @ResponseBody
    public Message carousel_manual_checking(@RequestParam("flightDynamicId") String flightDynamicId,
                                            @RequestParam("expectCarouselCode") String carouselCode,
                                            @RequestParam("nature") String nature,
                                            @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, -2, "行李转盘手动分配失败：航班动态数据同步中，请稍候再试！");
            CarouselOccupyingInfo carouselOccupyingInfo = carouselOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (null == carouselOccupyingInfo)
                return throwsMessageError(request, -2, "行李转盘手动分配失败：当前航班信息找不到相应的行李转盘占用数据，无法分配！");

            RmsCarousel rmsCarousel = rmsCarouselService.getByCode(carouselCode);
            if (null == rmsCarousel) return throwsMessageError(request, 0, "行李转盘手动分配失败：不存在的行李转盘！");
            if (rmsCarousel.getCarouselTypeCode().equals("0"))
                return throwsMessageError(request, 0, "行李转盘手动分配失败：行李转盘不可用！");

            List<String> occupiedCarousels = flightDynamicService.findOccupiedCarouselCodes(buildOccupiedQueryCritera(flightDynamic, carouselOccupyingInfo));

            if (occupiedCarousels.contains(rmsCarousel.getCarouselNum())) {
                message.setCode(0);
                message.setMessage("行李转盘手动分配失败：当前行李转盘「" + rmsCarousel.getCarouselNum() + "」已被占用！");
                LogUtils.saveLog(request, message.getMessage());
            }

            if (nature.equals("1")) carouselOccupyingInfo.setInteCarouselCode(rmsCarousel.getCarouselNum());
            else if (nature.equals("2")) carouselOccupyingInfo.setIntlCarouselCode(rmsCarousel.getCarouselNum());

            carouselOccupyingInfo.setRandom(random);
            resourceSharingService.carouselManual(flightDynamic, carouselOccupyingInfo, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("行李转盘手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:arrival_gate_dist")
    @RequestMapping("arrival_gate_manual_checking")
    @ResponseBody
    public Message arrival_gate_manual_checking(@RequestParam("flightDynamicId") String flightDynamicId,
                                                @RequestParam("expectArrivalGateCode") String arrivalGateCode,
                                                @RequestParam("nature") String nature,
                                                @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, -2, "到港门手动分配失败：航班动态数据同步中，请稍候再试！");
            ArrivalGateOccupyingInfo arrivalGateOccupyingInfo = arrivalGateOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (arrivalGateOccupyingInfo == null)
                return throwsMessageError(request, -2, "到港门手动分配失败：当前航班信息找不到相应的到港门占用数据，无法分配！");

            ArrivalGate arrivalGate = arrivalGateService.getByCode(arrivalGateCode);
            if (null == arrivalGate) return throwsMessageError(request, 0, "到港门手动分配失败：不存在的到港门！");
            if (arrivalGate.getArrivalGateStatus().equals("0"))
                return throwsMessageError(request, 0, "到港门手动分配失败：到港门不可用！");

            List<String> occupiedArrivalGates = flightDynamicService.findOccupiedArrivalGateCodes(buildOccupiedQueryCritera(flightDynamic, arrivalGateOccupyingInfo));

            if (occupiedArrivalGates.contains(arrivalGate.getArrivalGateNum())) {
                message.setCode(0);
                message.setMessage("到港门手动分配失败：当前到港门「" + arrivalGate.getArrivalGateNum() + "」已被占用！");
                LogUtils.saveLog(request, message.getMessage());
            } else {
                message.setCode(1);
                message.setMessage("分配成功！");
            }

            if (nature.equals("1")) arrivalGateOccupyingInfo.setInteArrivalGateCode(arrivalGate.getArrivalGateNum());
            else if (nature.equals("2"))
                arrivalGateOccupyingInfo.setIntlArrivalGateCode(arrivalGate.getArrivalGateNum());
            arrivalGateOccupyingInfo.setRandom(random);
            resourceSharingService.arrivalGateManual(flightDynamic, arrivalGateOccupyingInfo, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("到港门手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:boarding_gate_dist")
    @RequestMapping("boarding_gate_manual_checking")
    @ResponseBody
    public Message boarding_gate_manual_checking(@RequestParam("flightDynamicId") String flightDynamicId,
                                                 @RequestParam("expectBoardingGateCode") String boardingGateCode,
                                                 @RequestParam("nature") String nature,
                                                 @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, -2, "登机口手动分配失败：航班动态数据同步中，请稍候再试！");
            BoardingGateOccupyingInfo boardingGateOccupyingInfo = boardingGateOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (boardingGateOccupyingInfo == null)
                return throwsMessageError(request, -2, "登机口手动分配失败：当前航班信息找不到相应的登机口占用数据，无法分配！");

            BoardingGate boardingGate = boardingGateService.getByCode(boardingGateCode);
            if (null == boardingGate) return throwsMessageError(request, 0, "登机口手动分配失败：不存在的登机口！");
            if (boardingGate.getBoardingGateStatus().equals("0"))
                return throwsMessageError(request, 0, "登机口手动分配失败：登机口不可用！");

            List<String> occupiedBoardingGateCodes = flightDynamicService.findOccupiedBoardingGateCodes(buildOccupiedQueryCritera(flightDynamic, boardingGateOccupyingInfo));

            if (occupiedBoardingGateCodes.contains(boardingGate.getBoardingGateNum())) {
                message.setMsg(0, "登机口手动分配失败：当前到登机口「" + boardingGate.getBoardingGateNum() + "」已被占用！");
                LogUtils.saveLog(request, message.getMessage());
            } else {
                message.setCode(1);
                message.setMessage("分配成功！");
            }

            if (nature.equals("1"))
                boardingGateOccupyingInfo.setInteBoardingGateCode(boardingGate.getBoardingGateNum());
            else if (nature.equals("2"))
                boardingGateOccupyingInfo.setIntlBoardingGateCode(boardingGate.getBoardingGateNum());

            boardingGateOccupyingInfo.setRandom(random);
            resourceSharingService.boardingGateManual(flightDynamic, boardingGateOccupyingInfo, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("登机口手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:slide_coast_dist")
    @RequestMapping("slide_coast_manual_checking")
    @ResponseBody
    public Message slide_coast_manual_checking(@RequestParam("flightDynamicId") String flightDynamicId,
                                               @RequestParam("expectSlideCoastCode") String slideCoastCode,
                                               @RequestParam("nature") String nature,
                                               @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, -2, "滑槽手动分配失败：航班动态数据同步中，请稍候再试！");

            SlideCoastOccupyingInfo slideCoastOccupyingInfo = slideCoastOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (null == slideCoastOccupyingInfo)
                return throwsMessageError(request, -2, "滑槽手动分配失败：当前航班信息找不到相应的滑槽占用数据，无法分配！");

            SlideCoast slideCoast = slideCoastService.getByCode(slideCoastCode);
            if (null == slideCoast) return throwsMessageError(request, 0, "滑槽手动分配失败：不存在的滑槽！");
            if (slideCoast.getSlideCoastStatus().equals("0")) return throwsMessageError(request, 0, "滑槽手动分配失败：滑槽不可用！");

            List<String> occupiedSlideCoastCodes = flightDynamicService.findOccupiedSlideCoastCodes(buildOccupiedQueryCritera(flightDynamic, slideCoastOccupyingInfo));

            if (occupiedSlideCoastCodes.contains(slideCoast.getSlideCoastNum())) {
                message.setCode(0);
                message.setMessage("滑槽手动分配失败：当前滑槽「" + slideCoast.getSlideCoastNum() + "」已被占用！");
                LogUtils.saveLog(request, message.getMessage());
            } else {
                message.setCode(1);
                message.setMessage("分配成功！");
            }

            if (nature.equals("1")) slideCoastOccupyingInfo.setInteSlideCoastCode(slideCoast.getSlideCoastNum());
            else if (nature.equals("2")) slideCoastOccupyingInfo.setIntlSlideCoastCode(slideCoast.getSlideCoastNum());

            slideCoastOccupyingInfo.setRandom(random);
            resourceSharingService.slideCoastManual(flightDynamic, slideCoastOccupyingInfo, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("滑槽手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:aircraft_dist:manu_allo")
    @RequestMapping("aircraft_num_manual_dist")
    @ResponseBody
    public Map<String, Object> aircraft_num_manual_dist(
            @RequestParam("id") String id,
            @RequestParam("expectAircraftNum") String expectAircraftNum,
            @RequestParam("force") Boolean force,
            String random, HttpServletRequest request) {

        Map<String, Object> variables = new HashMap<>();
        Message message = new Message(0);

        FlightPlanPair flightPlanPair = flightPlanPairService.get(id);
        if (null == flightPlanPair) return throwsDistributedError(request, "机位手动分配失败：航班配对数据同步中，请稍候再试！", "1");

        //实飞后不能再分配 wjp_2017年7月12日20时43分
        if (flightPlanPair.getAtd2() != null) return throwsDistributedError(request, "机位手动分配失败：航班已起飞,不能再分配机位！", "1");

        GateOccupyingInfo info = gateOccupyingInfoService.getByFlightDynamicId(flightPlanPair.getId());
        if (null == info) return throwsDistributedError(request, "机位手动分配失败：当前航班信息找不到相应的机位占用数据，无法分配", "1");
        flightPlanPair.setRandom(random);
        flightPlanPair.setPlaceNum2(flightPlanPair.getPlaceNum());   // 旧资源号
        flightPlanPair.setPlaceNum(expectAircraftNum);

        if (!force) {
            //处理次日航班机位占用问题 wjp_2017年11月22日16时44分
            if (placeNumFlag) {
                if (StringUtils.isNotBlank(flightPlanPair.getAircraftNum())) {
                    String ypNum = expectAircraftNum;
                    List<YesterAircraftNumPair> numPair = yesterAircraftNumPairDao.findListByOci(flightPlanPair.getPlaceNum());
                    if (!Collections3.isEmpty(numPair)) {
                        logger.info("过滤次日航班已经被占用的机号：" + numPair.get(0).getAircraftNum() + ",机位：" + ypNum);
                        String msg = "警告：当前机位[" + ypNum + "]已经被【机号" + numPair.get(0).getAircraftNum() + "停场飞机】占用！";
                        variables.put("success", false);
                        variables.put("message", msg);
                        LogUtils.saveLog(request, msg);
                        return variables;
                    }
                }
            }

            try {
                KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(getAircraftStandPkg("AIRCRAFT_STAND_DIST.PKG"));
                session.insert(flightPlanPair);
                session.insert(info);
                session.startProcess("AircraftDistributionFlow", ImmutableMap.of("need_save", false, "mock_dist", false, "dist_type", "手动分配", "threshold_value", DictUtils.getDictValue("资源分配间隔值", "threshold_value", "15")));

                variables.put("success", session.getParameter("success"));
                variables.put("message", session.getParameter("message"));
                variables.put("result", session.getParameter("result"));

                StringBuffer sb = new StringBuffer();
                if (variables.get("success") != null && (boolean) variables.get("success") == true) {
                    resourceSharingService.aircraftNumManual(flightPlanPair, info, message, expectAircraftNum);
                    variables = message.getResult();
                    LogUtils.saveLogToMsg(request, message);
                } else {
                    sb.append("机位手动分配失败！").append(variables.get("message"));
                    message.setMessage(String.valueOf(variables.get("message")));
                    variables.put("success", false);
                }
            } catch (Exception e) {
                e.printStackTrace();
                variables.put("success", false);
                variables.put("message", e.getMessage());
                LogUtils.saveLog(request, e.getMessage());
            }
        } else {
            resourceSharingService.aircraftNumManual(flightPlanPair, info, message, expectAircraftNum);
            LogUtils.saveLogToMsg(request, message);
            variables = message.getResult();
        }
        return variables;
    }

    @RequiresPermissions("rms:rs:aircraft_dist")
    @RequestMapping("aircraft_num_manual_dist_4_mock")
    @ResponseBody
    public Map<String, Object> aircraft_num_manual_dist_4_mock(@RequestParam("detailId") String detailId,
                                                               @RequestParam("expectAircraftNum") String expectAircraftNum,
                                                               @RequestParam("force") Boolean force, HttpServletRequest request) {

        Map<String, Object> variables = new HashMap<>();
        Message message = new Message(0);


        ResourceMockDistDetail detail = resourceMockDistDetailService.get(detailId);
        if (null == detail) return throwsDistributedError(request, "模拟分配失败：找不到相应的模拟详情信息", "1");
        FlightPlanPair flightPlanPair = flightPlanPairService.get(detail.getDataId());
        GateOccupyingInfo info = gateOccupyingInfoService.getByFlightDynamicId(detail.getDataId());


        if (null == flightPlanPair) return throwsDistributedError(request, "模拟分配失败：找不到相应的航班配对信息", "1");
        if (null == info) return throwsDistributedError(request, "模拟分配失败：找不到相应的资源占用信息", "1");

        ResourceMockDistInfo resourceMockDistInfo = resourceMockDistInfoService.get(detail.getInfoId());

        flightPlanPair.setPlaceNum(expectAircraftNum);

        if (null == resourceMockDistInfo) return throwsDistributedError(request, "模拟分配失败：找不到相应的模拟分配信息", "1");

        if (!force) {
            try {
                KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(getAircraftStandPkg("AIRCRAFT_STAND_DIST.PKG"));
                session.insert(flightPlanPair);
                session.insert(info);
                session.insert(resourceMockDistInfo);
                session.startProcess("AircraftDistributionFlow", ImmutableMap.of("need_save", false, "omit_oci", true, "mock_dist", true, "dist_type", "手动分配", "threshold_value", DictUtils.getDictValue("资源分配间隔值", "threshold_value", "15")));

                variables.put("success", session.getParameter("success"));
                variables.put("message", session.getParameter("message"));
                variables.put("result", session.getParameter("result"));

                StringBuffer sb = new StringBuffer();
                if (variables.get("success") != null && (boolean) variables.get("success") == true) {
                    detail.setInte(expectAircraftNum);
                    resourceMockDistDetailService.save(detail);
                    variables.put("message", "分配成功！");
                    variables.put("result", expectAircraftNum);
                }
            } catch (Exception e) {
                e.printStackTrace();
                variables.put("success", false);
                variables.put("message", e.getMessage());
                LogUtils.saveLog(request, e.getMessage());
            }
        } else {
            detail.setInte(expectAircraftNum);
            resourceMockDistDetailService.save(detail);
            variables.put("code", 1);
            variables.put("result", expectAircraftNum);
            variables.put("message", "分配成功！");
        }

        return variables;
    }

    @RequiresPermissions("rms:rs:carousel_dist")
    @RequestMapping("clearCarouselCode")
    @ResponseBody
    public Message clearCarouselCode(@RequestParam("flightDynamicId") String flightDynamicId, @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();
        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, 0, "清除行李转盘失败：航班动态数据不存在！");

            CarouselOccupyingInfo info = carouselOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (null == info) return throwsMessageError(request, 0, "清除行李转盘失败：资源占用数据不存在！");
            info.setRandom(random);

            resourceSharingService.clearCarouselCode(flightDynamic, info, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("清除行李转盘失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:arrival_gate_dist")
    @RequestMapping("clearArrivalGateCode")
    @ResponseBody
    public Message clearArrivalGateCode(@RequestParam("flightDynamicId") String flightDynamicId, @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();
        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, 0, "清除到港门失败：航班动态数据不存在！");

            ArrivalGateOccupyingInfo info = arrivalGateOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (null == info) return throwsMessageError(request, 0, "清除到港门失败：资源占用数据不存在！");
            info.setRandom(random);
            resourceSharingService.clearArrivalGateCode(flightDynamic, info, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("清除到港门失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:boarding_gate_dist")
    @RequestMapping("clearBoardingGateCodeByNature")
    @ResponseBody
    public Message clearBoardingGateCodeByNature(@RequestParam("flightDynamicId") String flightDynamicId, @RequestParam("type") String type,
                                                 @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, 0, "清除登机口失败：航班动态数据不存在！");

            BoardingGateOccupyingInfo info = boardingGateOccupyingInfoService.getByFlightDynamicId(flightDynamicId);

            if (null != info) {
                if (type.equals("1")) info.setInteBoardingGateCode("");
                else info.setIntlBoardingGateCode("");
                info.setRandom(random);
                resourceSharingService.clearBoardingGateCodeByNature(flightDynamic, info, message);
                LogUtils.saveLogToMsg(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("登机口清除失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:arrival_gate_dist")
    @RequestMapping("clearArrivalGateCodeByNature")
    @ResponseBody
    public Message clearArrivalGateCodeByNature(@RequestParam("flightDynamicId") String flightDynamicId, @RequestParam("type") String type,
                                                @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, 0, "清除到港门失败：航班动态数据不存在！");

            ArrivalGateOccupyingInfo info = arrivalGateOccupyingInfoService.getByFlightDynamicId(flightDynamicId);

            if (null != info) {
                if (type.equals("1")) info.setInteArrivalGateCode("");
                else info.setIntlArrivalGateCode("");
                info.setRandom(random);
                resourceSharingService.clearArrivalGateCodeByNature(flightDynamic, info, message);
                LogUtils.saveLogToMsg(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("到港门清除失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:carousel_dist")
    @RequestMapping("clearCarouselCodeByNature")
    @ResponseBody
    public Message clearCarouselCodeByNature(@RequestParam("flightDynamicId") String flightDynamicId, @RequestParam("type") String type,
                                             @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, 0, "清除行李转盘失败：航班动态数据不存在！");

            CarouselOccupyingInfo info = carouselOccupyingInfoService.getByFlightDynamicId(flightDynamicId);

            if (null != info) {
                if (type.equals("1")) info.setInteCarouselCode("");
                else info.setIntlCarouselCode("");
                info.setRandom(random);
                resourceSharingService.clearCarouselCodeByNature(flightDynamic, info, message);
                LogUtils.saveLogToMsg(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("行李转盘清除失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:carousel_dist")
    @RequestMapping("clearCarouselCode4Mock")
    @ResponseBody
    public Message clearCarouselCode4Mock(String detailId, String inte, String intl, HttpServletRequest request) {
        Message message = new Message(0);

        if (StringUtils.isBlank(detailId)) return throwsMessageError(request, 0, "模拟分配详情ID不能为空！");

        ResourceMockDistDetail resourceMockDistDetail = resourceMockDistDetailService.get(detailId);
        if (null == resourceMockDistDetail) return throwsMessageError(request, 0, "不存在的模拟分配详情信息！");

        resourceMockDistDetail.setInte(inte);
        resourceMockDistDetail.setIntl(intl);

        resourceMockDistDetailService.save(resourceMockDistDetail);
        message.setCode(1);
        message.setMessage("操作成功！");

        return message;
    }

    @RequiresPermissions("rms:rs:slide_coast_dist")
    @RequestMapping("clearSlideCoastByNature")
    @ResponseBody
    public Message clearSlideCoastByNature(@RequestParam("flightDynamicId") String flightDynamicId, @RequestParam("type") String type,
                                           @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, 0, "清除滑槽失败：航班动态数据不存在！");

            SlideCoastOccupyingInfo info = slideCoastOccupyingInfoService.getByFlightDynamicId(flightDynamicId);

            if (null != info) {
                if (type.equals("1")) info.setInteSlideCoastCode("");
                else info.setIntlSlideCoastCode("");
                info.setRandom(random);
                resourceSharingService.clearSlideCoastByNature(flightDynamic, info, message);
                LogUtils.saveLogToMsg(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("滑槽清除失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:boarding_gate_dist")
    @RequestMapping("clearBoardingGateCode")
    @ResponseBody
    public Message clearBoardingGateCode(@RequestParam("flightDynamicId") String flightDynamicId, @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();
        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, 0, "清除登机口失败：航班动态数据不存在！");

            BoardingGateOccupyingInfo info = boardingGateOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (null == info) return throwsMessageError(request, 0, "清除登机口失败：资源占用数据不存在！");

            info.setRandom(random);
            resourceSharingService.clearBoardingGateCode(flightDynamic, info, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("清除登机口失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:slide_coast_dist")
    @RequestMapping("clearSlideCoastCode")
    @ResponseBody
    public Message clearSlideCoastCode(@RequestParam("flightDynamicId") String flightDynamicId, @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();
        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (flightDynamic == null) return throwsMessageError(request, 0, "清除滑槽失败：航班动态数据不存在！");

            SlideCoastOccupyingInfo info = slideCoastOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (null == info) return throwsMessageError(request, 0, "清除滑槽失败，资源占用数据不存在！");

            info.setRandom(random);
            resourceSharingService.clearSlideCoastCode(flightDynamic, info, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("清除滑槽失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:checkin_counter_dist")
    @RequestMapping("clearCheckinCounter")
    @ResponseBody
    public Message clearCheckinCounter(@RequestParam("flightDynamicId") String flightDynamicId, String random, HttpServletRequest request) {
        Message message = new Message();
        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, 0, "清除值机柜台失败：航班动态数据不存在！");

            CheckingCounterOccupyingInfo info = checkingCounterOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (null == info) return throwsMessageError(request, 0, "清除值机柜台失败：资源占用数据不存在！");

            info.setRandom(random);
            resourceSharingService.clearCheckinCounter(flightDynamic, info, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("清除值机柜台失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:security_checkin_dist")
    @RequestMapping("clearSecurityCheckin")
    @ResponseBody
    public Message clearSecurityCheckin(@RequestParam("flightDynamicId") String flightDynamicId, @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();
        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, 0, "清除安检口失败：航班动态数据不存在！");
            SecurityCheckinOccupyingInfo info = securityCheckinOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (null == info) return throwsMessageError(request, 0, "清除安检口失败：资源占用数据不存在！");

            info.setRandom(random);
            resourceSharingService.clearSecurityCheckin(flightDynamic, info, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("清除安检口失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:departure_hall_dist")
    @RequestMapping("clearDepartureHall")
    @ResponseBody
    public Message clearDepartureHall(@RequestParam("flightDynamicId") String flightDynamicId, @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();
        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (flightDynamic == null) return throwsMessageError(request, 0, "清除候机厅失败：航班动态数据不存在！");

            DepartureHallOccupyingInfo info = departureHallOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (null == info) return throwsMessageError(request, 0, "清除候机厅失败：资源占用数据不存在！");

            info.setRandom(random);
            resourceSharingService.clearDepartureHall(flightDynamic, info, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("取消失败，出现异常：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:rs:aircraft_dist")
    @RequestMapping("clearAircraftNum")
    @ResponseBody
    public Message clearAircraftNum(@RequestParam("flightPlanPairId") String flightPlanPairId, String random, HttpServletRequest request) {
        Message message = new Message();
        FlightPlanPair flightPlanPair = flightPlanPairService.get(flightPlanPairId);
        if (flightPlanPair == null) return throwsMessageError(request, 0, "清除机位失败：航班配对数据不存在！");

        //已起飞航班不能清除机位
        if (flightPlanPair.getAtd2() != null) return throwsMessageError(request, 0, "清除机位失败：航班已起飞,不能再分配机位！");

        GateOccupyingInfo gateOccupyingInfo = gateOccupyingInfoService.getByFlightDynamicId(flightPlanPairId);
        if (gateOccupyingInfo == null) return throwsMessageError(request, 0, "清除机位失败：资源占用数据不存在！");

        flightPlanPair.setRandom(random);
        resourceSharingService.clearAircraftNum(flightPlanPair, gateOccupyingInfo, message);
        LogUtils.saveLogToMsg(request, message);
        return message;
    }

    @RequiresPermissions("rms:rs:aircraft_dist")
    @RequestMapping("clearAircraftNum4Mock")
    @ResponseBody
    public Message clearAircraftNum4Mock(@RequestParam("detailId") String detailId, HttpServletRequest request) {
        Message message = new Message();
        ResourceMockDistDetail detail = resourceMockDistDetailService.get(detailId);
        if (detail != null) {
            detail.setInte("");
            resourceMockDistDetailService.save(detail);
            message.setCode(1);
            message.setMessage("机位清除成功！");
        }
        return message;
    }

    @RequiresPermissions("rms:rs:carousel_dist")
    @RequestMapping("forceCarouselDistMixing")
    @ResponseBody
    public Message forceCarouselDistMixing(@RequestParam("flightDynamicId") String flightDynamicId,
                                           @RequestParam("inte") String inte,
                                           @RequestParam("intl") String intl,
                                           @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, 0, "行李转盘手动分配失败：航班动态数据同步中，请稍候再试！");

            CarouselOccupyingInfo carouselOccupyingInfo = carouselOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (null == carouselOccupyingInfo)
                return throwsMessageError(request, 0, "行李转盘手动分配失败：当前航班信息找不到相应的行李转盘占用数据，无法分配！");

            carouselOccupyingInfo.setInteCarouselCode(inte);
            carouselOccupyingInfo.setIntlCarouselCode(intl);

            carouselOccupyingInfo.setRandom(random);
            carouselOccupyingInfoService.save(carouselOccupyingInfo);
            message.setCode(1);
            message.setMessage("分配成功！");

            StringBuffer sb = new StringBuffer();
            sb.append("行李转盘手动分配成功！分配" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
            sb.append("行李转盘,").append(carouselOccupyingInfo.toString());
            LogUtils.saveLog(request, sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("行李转盘手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:arrival_gate_dist")
    @RequestMapping("forceArrivalGateDistMixing")
    @ResponseBody
    public Message forceArrivalGateDistMixing(@RequestParam("flightDynamicId") String flightDynamicId,
                                              @RequestParam("inte") String inte,
                                              @RequestParam("intl") String intl,
                                              @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();

        try {

            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, 0, "到港门手动分配失败：航班动态数据同步中，请稍候再试！");

            ArrivalGateOccupyingInfo arrivalGateOccupyingInfo = arrivalGateOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (arrivalGateOccupyingInfo == null)
                return throwsMessageError(request, 0, "到港门手动分配失败：当前航班信息找不到相应的到港门占用数据，无法分配！");

            arrivalGateOccupyingInfo.setInteArrivalGateCode(inte);
            arrivalGateOccupyingInfo.setIntlArrivalGateCode(intl);
            arrivalGateOccupyingInfo.setRandom(random);
            arrivalGateOccupyingInfoService.save(arrivalGateOccupyingInfo);

            message.setCode(1);
            message.setMessage("分配成功！");

            StringBuffer sb = new StringBuffer();
            sb.append("到港门手动分配成功！分配" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
            sb.append("到港门,").append(arrivalGateOccupyingInfo.toString());
            LogUtils.saveLog(request, sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("到港门手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:boarding_gate_dist")
    @RequestMapping("forceBoardingGateDistMixing")
    @ResponseBody
    public Message forceBoardingGateDistMixing(@RequestParam("flightDynamicId") String flightDynamicId,
                                               @RequestParam("inte") String inte,
                                               @RequestParam("intl") String intl,
                                               @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, 0, "登机口手动分配失败：航班动态数据同步中，请稍候再试！");

            BoardingGateOccupyingInfo boardingGateOccupyingInfo = boardingGateOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (null == boardingGateOccupyingInfo)
                return throwsMessageError(request, 0, "登机口手动分配失败：当前航班信息找不到相应的登机口占用数据，无法分配！");

            boardingGateOccupyingInfo.setInteBoardingGateCode(inte);
            boardingGateOccupyingInfo.setIntlBoardingGateCode(intl);
            boardingGateOccupyingInfo.setRandom(random);
            boardingGateOccupyingInfoService.save(boardingGateOccupyingInfo);
            message.setCode(1);
            message.setMessage("分配成功！");

            StringBuffer sb = new StringBuffer();
            sb.append("登机口手动分配成功！分配" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
            sb.append("登机口,").append(boardingGateOccupyingInfo.toString());
            LogUtils.saveLog(request, sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("登机口手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:slide_coast_dist")
    @RequestMapping("forceSlideCoastDistMixing")
    @ResponseBody
    public Message forceSlideCoastDistMixing(@RequestParam("flightDynamicId") String flightDynamicId,
                                             @RequestParam("inte") String inte, @RequestParam("intl") String intl,
                                             @RequestParam("random") String random, HttpServletRequest request) {
        Message message = new Message();

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, 0, "滑槽手动分配失败：航班动态数据同步中，请稍候再试！");
            SlideCoastOccupyingInfo slideCoastOccupyingInfo = slideCoastOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (null == slideCoastOccupyingInfo)
                return throwsMessageError(request, 0, "滑槽手动分配失败：当前航班信息找不到相应的滑槽占用数据，无法分配！");

            slideCoastOccupyingInfo.setInteSlideCoastCode(inte);
            slideCoastOccupyingInfo.setIntlSlideCoastCode(intl);

            slideCoastOccupyingInfo.setRandom(random);
            slideCoastOccupyingInfoService.save(slideCoastOccupyingInfo);

            message.setCode(1);
            message.setMessage("分配成功！");

            StringBuffer sb = new StringBuffer();
            sb.append("滑槽手动分配成功！分配" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
            sb.append("滑槽,").append(slideCoastOccupyingInfo.toString());
            LogUtils.saveLog(request, sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("滑槽手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:arrival_gate_dist")
    @RequestMapping("forceArrivalGateDist")
    @ResponseBody
    public Message forceArrivalGateDist(String flightDynamicId, String expectCarouselCode, String nature, String random, HttpServletRequest request) {
        Message message = new Message();

        try {

            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, 0, "到港门手动分配失败：航班动态数据同步中，请稍候再试！");

            ArrivalGateOccupyingInfo arrivalGateOccupyingInfo = arrivalGateOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (null == arrivalGateOccupyingInfo)
                return throwsMessageError(request, 0, "到港门手动分配失败：当前航班信息找不到相应的到港门占用数据，无法分配！");

            if (nature.equals("1")) arrivalGateOccupyingInfo.setInteArrivalGateCode(expectCarouselCode);
            else if (nature.equals("2")) arrivalGateOccupyingInfo.setIntlArrivalGateCode(expectCarouselCode);

            arrivalGateOccupyingInfo.setRandom(random);
            arrivalGateOccupyingInfoService.save(arrivalGateOccupyingInfo);

            message.setCode(1);
            message.setMessage("分配成功！");
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("到港门手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:boarding_gate_dist")
    @RequestMapping("forceBoardingGateDist")
    @ResponseBody
    public Message forceBoardingGateDist(String flightDynamicId, String expectCarouselCode, String nature, String random, HttpServletRequest request) {
        Message message = new Message();

        try {

            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, 0, "登机口手动分配失败：航班动态数据同步中，请稍候再试！");

            BoardingGateOccupyingInfo boardingGateOccupyingInfo = boardingGateOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (null == boardingGateOccupyingInfo)
                return throwsMessageError(request, 0, "登机口手动分配失败：当前航班信息找不到相应的登机口占用数据，无法分配！");

            if (nature.equals("1")) boardingGateOccupyingInfo.setInteBoardingGateCode(expectCarouselCode);
            else if (nature.equals("2")) boardingGateOccupyingInfo.setIntlBoardingGateCode(expectCarouselCode);

            boardingGateOccupyingInfo.setRandom(random);
            boardingGateOccupyingInfoService.save(boardingGateOccupyingInfo);

            message.setCode(1);
            message.setMessage("分配成功！");
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("登机口手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:slide_coast_dist")
    @RequestMapping("forceSlideCoastDist")
    @ResponseBody
    public Message forceSlideCoastDist(String flightDynamicId, String expectCarouselCode, String nature, String random, HttpServletRequest request) {
        Message message = new Message();

        try {

            FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);
            if (null == flightDynamic) return throwsMessageError(request, 0, "滑槽手动分配失败：航班动态数据同步中，请稍候再试！");
            SlideCoastOccupyingInfo slideCoastOccupyingInfo = slideCoastOccupyingInfoService.getByFlightDynamicId(flightDynamicId);
            if (null == slideCoastOccupyingInfo)
                return throwsMessageError(request, 0, "滑槽手动分配失败：当前航班信息找不到相应的滑槽占用数据，无法分配！");

            if (nature.equals("1")) slideCoastOccupyingInfo.setInteSlideCoastCode(expectCarouselCode);
            else if (nature.equals("2")) slideCoastOccupyingInfo.setIntlSlideCoastCode(expectCarouselCode);

            slideCoastOccupyingInfo.setRandom(random);
            slideCoastOccupyingInfoService.save(slideCoastOccupyingInfo);

            message.setCode(1);
            message.setMessage("分配成功！");
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("滑槽手动分配失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:carousel_dist")
    @RequestMapping(value = "setActualTime")
    @ResponseBody
    public Message setActualTime(CarouselOccupyingInfo carouselOccupyingInfo, HttpServletRequest request) {
        Message message = new Message();

        try {
            CarouselOccupyingInfo infoInDB = carouselOccupyingInfoService.getByFlightDynamicId(carouselOccupyingInfo.getFlightDynamicId());
            if (infoInDB == null) {
                message.setCode(0);
                message.setMessage("设置实际占用时间失败：行李转盘分配记录不存在！");
                LogUtils.saveLog(request, message.getMessage());
                return message;
            }
            FlightDynamic flightDynamic = flightDynamicService.get(carouselOccupyingInfo.getFlightDynamicId());
            if (flightDynamic == null) {
                message.setMsg(0, "设置实际占用时间失败：航班动态记录不存在！");
                return message;
            }

            infoInDB.setInteActualStartTime(carouselOccupyingInfo.getInteActualStartTime());
            infoInDB.setInteActualOverTime(carouselOccupyingInfo.getInteActualOverTime());
            infoInDB.setRandom(carouselOccupyingInfo.getRandom());
            resourceSharingService.setActualTime(flightDynamic, infoInDB, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            message.setCode(0);
            message.setMessage("设置实际占用时间失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:arrival_gate_dist")
    @RequestMapping(value = "setArrivalGateActualTime")
    @ResponseBody
    public Message setArrivalGateActualTime(ArrivalGateOccupyingInfo arrivalGateOccupyingInfo, HttpServletRequest request) {
        Message message = new Message();

        try {
            ArrivalGateOccupyingInfo infoInDB = arrivalGateOccupyingInfoService.getByFlightDynamicId(arrivalGateOccupyingInfo.getFlightDynamicId());
            if (infoInDB == null) {
                message.setCode(0);
                message.setMessage("设置实际占用时间失败：到港门分配记录不存在！");
                return message;
            }
            FlightDynamic flightDynamic = flightDynamicService.get(arrivalGateOccupyingInfo.getFlightDynamicId());
            if (flightDynamic == null) {
                message.setMsg(0, "设置实际占用时间失败：航班动态记录不存在！");
                return message;
            }

            infoInDB.setInteActualStartTime(arrivalGateOccupyingInfo.getInteActualStartTime());
            infoInDB.setInteActualOverTime(arrivalGateOccupyingInfo.getInteActualOverTime());

            infoInDB.setRandom(arrivalGateOccupyingInfo.getRandom());
            resourceSharingService.setArrivalGateActualTime(flightDynamic, infoInDB, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            message.setCode(0);
            message.setMessage("设置实际占用时间失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:boarding_gate_dist")
    @RequestMapping(value = "setBoardingGateActualTime")
    @ResponseBody
    public Message setBoardingGateActualTime(BoardingGateOccupyingInfo boardingGateOccupyingInfo, HttpServletRequest request) {
        Message message = new Message();

        try {
            BoardingGateOccupyingInfo infoInDB = boardingGateOccupyingInfoService.getByFlightDynamicId(boardingGateOccupyingInfo.getFlightDynamicId());
            if (infoInDB == null) {
                message.setCode(0);
                message.setMessage("设置实际占用时间失败：登机口分配记录不存在！");
                return message;
            }
            FlightDynamic flightDynamic = flightDynamicService.get(boardingGateOccupyingInfo.getFlightDynamicId());
            if (flightDynamic == null) {
                message.setMsg(0, "设置实际占用时间失败：航班动态记录不存在！");
                return message;
            }

            infoInDB.setInteActualStartTime(boardingGateOccupyingInfo.getInteActualStartTime());
            infoInDB.setInteActualOverTime(boardingGateOccupyingInfo.getInteActualOverTime());
            infoInDB.setRandom(boardingGateOccupyingInfo.getRandom());
            resourceSharingService.setBoardingGateActualTime(flightDynamic, infoInDB, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            message.setCode(0);
            message.setMessage("设置实际占用时间失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:slide_coast_dist")
    @RequestMapping(value = "setSlideCoastActualTime")
    @ResponseBody
    public Message setSlideCoastActualTime(SlideCoastOccupyingInfo slideCoastOccupyingInfo, HttpServletRequest request) {
        Message message = new Message();

        try {
            SlideCoastOccupyingInfo infoInDB = slideCoastOccupyingInfoService.getByFlightDynamicId(slideCoastOccupyingInfo.getFlightDynamicId());
            if (infoInDB == null) {
                message.setCode(0);
                message.setMessage("设置实际占用时间失败：滑槽分配记录不存在！");
                return message;
            }
            FlightDynamic flightDynamic = flightDynamicService.get(slideCoastOccupyingInfo.getFlightDynamicId());
            if (flightDynamic == null) {
                message.setMsg(0, "设置实际占用时间失败：航班动态记录不存在！");
                return message;
            }

            infoInDB.setInteActualStartTime(slideCoastOccupyingInfo.getInteActualStartTime());
            infoInDB.setInteActualOverTime(slideCoastOccupyingInfo.getInteActualOverTime());

            infoInDB.setRandom(slideCoastOccupyingInfo.getRandom());
            resourceSharingService.setSlideCoastActualTime(flightDynamic, infoInDB, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            message.setCode(0);
            message.setMessage("设置实际占用时间失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequestMapping("clearResourceByPair")
    @ResponseBody
    public Message clearResource(FlightPlanPair flightPlanPair, HttpServletRequest request) {
        Message message = new Message();

        try {
            resourceSharingService.clearResourceByPair(flightPlanPair);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage("清除失败:" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
            e.printStackTrace();
        }

        return message;
    }

    @RequestMapping("clearResourceByDynamic")
    @ResponseBody
    public Message clearResource(FlightDynamic flightDynamic) {
        Message message = new Message();

        try {
            if (StringUtils.isNotEmpty(flightDynamic.getId())) {
                if (StringUtils.equalsIgnoreCase("J", flightDynamic.getInoutTypeCode())) {
                    // 清除到港门
                    arrivalGateOccupyingInfoService.delete(arrivalGateOccupyingInfoService.getByFlightDynamicId(flightDynamic.getId()));
                    // 清除行李转盘
                    carouselOccupyingInfoService.delete(carouselOccupyingInfoService.getByFlightDynamicId(flightDynamic.getId()));
                } else if (StringUtils.equals("C", flightDynamic.getInoutTypeCode())) {
                    // 清除登机口
                    boardingGateOccupyingInfoService.delete(boardingGateOccupyingInfoService.getByFlightDynamicId(flightDynamic.getId()));
                    // 清除滑槽
                    slideCoastOccupyingInfoService.delete(slideCoastOccupyingInfoService.getByFlightDynamicId(flightDynamic.getId()));
                    // 清除值机柜台
                    checkingCounterOccupyingInfoService.delete(checkingCounterOccupyingInfoService.getByFlightDynamicId(flightDynamic.getId()));
                    // 清除候机厅
                    departureHallOccupyingInfoService.delete(departureHallOccupyingInfoService.getByFlightDynamicId(flightDynamic.getId()));
                    // 清除安检口
                    securityCheckinOccupyingInfoService.delete(securityCheckinOccupyingInfoService.getByFlightDynamicId(flightDynamic.getId()));
                }

                flightDynamicService.delete(flightDynamic);
            }
            message.setCode(1);
        } catch (Exception e) {
            message.setCode(0);
            message.setMessage("清除失败:" + e.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:checkin_counter_dist")
    @RequestMapping(value = "setCheckinCounterActualTime")
    @ResponseBody
    public Message setCheckinCounterActualTime(CheckingCounterOccupyingInfo checkingCounterOccupyingInfo, HttpServletRequest request) {
        Message message = new Message();

        try {
            CheckingCounterOccupyingInfo infoInDB = checkingCounterOccupyingInfoService.getByFlightDynamicId(checkingCounterOccupyingInfo.getFlightDynamicId());
            if (infoInDB == null) {
                message.setMsg(0, "设置实际占用时间失败：值机柜台分配记录不存在！");
                return message;
            }
            FlightDynamic flightDynamic = flightDynamicService.get(checkingCounterOccupyingInfo.getFlightDynamicId());
            if (flightDynamic == null) {
                message.setMsg(0, "设置实际占用时间失败：航班动态记录不存在！");
                return message;
            }

            infoInDB.setInteActualStartTime(checkingCounterOccupyingInfo.getInteActualStartTime());
            infoInDB.setInteActualOverTime(checkingCounterOccupyingInfo.getInteActualOverTime());
            infoInDB.setRandom(checkingCounterOccupyingInfo.getRandom());

            resourceSharingService.setCheckinCounterActualTime(flightDynamic, infoInDB, message);

            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            message.setCode(0);
            message.setMessage("设置实际占用时间失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:security_checkin_dist")
    @RequestMapping(value = "setSecurityCheckinActualTime")
    @ResponseBody
    public Message setSecurityCheckinActualTime(SecurityCheckinOccupyingInfo securityCheckinOccupyingInfo, HttpServletRequest request) {
        Message message = new Message();

        try {
            SecurityCheckinOccupyingInfo infoInDB = securityCheckinOccupyingInfoService.getByFlightDynamicId(securityCheckinOccupyingInfo.getFlightDynamicId());
            if (infoInDB == null) {
                message.setCode(0);
                message.setMessage("设置实际占用时间失败：安检口分配记录不存在！");
                return message;
            }
            FlightDynamic flightDynamic = flightDynamicService.get(securityCheckinOccupyingInfo.getFlightDynamicId());
            if (flightDynamic == null) {
                message.setMsg(0, "设置实际占用时间失败：航班动态记录不存在！");
            }

            infoInDB.setInteActualStartTime(securityCheckinOccupyingInfo.getInteActualStartTime());
            infoInDB.setInteActualOverTime(securityCheckinOccupyingInfo.getInteActualOverTime());
            infoInDB.setRandom(securityCheckinOccupyingInfo.getRandom());

            resourceSharingService.setSecurityCheckinActualTime(flightDynamic, infoInDB, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            message.setCode(0);
            message.setMessage("设置实际占用时间失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    @RequiresPermissions("rms:rs:departure_hall_dist")
    @RequestMapping(value = "setDepartureHallActualTime")
    @ResponseBody
    public Message setDepartureHallActualTime(DepartureHallOccupyingInfo departureHallOccupyingInfo, HttpServletRequest request) {
        Message message = new Message();

        try {
            DepartureHallOccupyingInfo infoInDB = departureHallOccupyingInfoService.getByFlightDynamicId(departureHallOccupyingInfo.getFlightDynamicId());
            if (infoInDB == null) {
                message.setCode(0);
                message.setMessage("设置实际占用时间失败：候机厅分配记录不存在！");
                return message;
            }
            FlightDynamic flightDynamic = flightDynamicService.get(departureHallOccupyingInfo.getFlightDynamicId());
            if (flightDynamic == null) {
                message.setMsg(0, "设置实际占用时间失败：航班动态记录不存在！");
            }

            infoInDB.setInteActualStartTime(departureHallOccupyingInfo.getInteActualStartTime());
            infoInDB.setInteActualOverTime(departureHallOccupyingInfo.getInteActualOverTime());

            infoInDB.setRandom(departureHallOccupyingInfo.getRandom());

            resourceSharingService.setDepartureHallActualTime(flightDynamic, infoInDB, message);
            LogUtils.saveLogToMsg(request, message);
        } catch (Exception e) {
            message.setCode(0);
            message.setMessage("设置实际占用时间失败：" + e.getMessage());
            LogUtils.saveLog(request, message.getMessage());
        }

        return message;
    }

    private Message throwsMessageError(HttpServletRequest request, Integer code, String messge) {
        if (request != null) LogUtils.saveLog(request, messge);
        return new Message(code, messge);
    }

    /**
     * 错误消息返回方法
     *
     * @param request
     * @param message   错误消息
     * @param errorType 0 普通异常， 1 数据未同步（这种情况不需要强制分配）
     * @return
     */
    public Map<String, Object> throwsDistributedError(HttpServletRequest request, String message, String errorType) {
//        LogUtils.saveLog(request, message);
        return ImmutableMap.of("success", false, "message", message, "errorType", errorType);
    }

    @ResponseBody
    @RequestMapping(value = "getDateRange")
    public List<String> getDateRange() {
        return flightDynamicService.getDateRange();
    }

    private void aircraftGateMessageSender(boolean isChanged, FlightPlanPair flightPlanPair, String newGate, String oldGate) {
        String flightNumber = "";
        if (StringUtils.isNotEmpty(flightPlanPair.getFlightNum2()))
            flightNumber += flightPlanPair.getFlightCompanyCode2() + flightPlanPair.getFlightNum2();
        if (StringUtils.isNotEmpty(flightPlanPair.getFlightNum2()) && StringUtils.isNotEmpty(flightPlanPair.getFlightNum()))
            flightNumber += "/";
        if (StringUtils.isNotEmpty(flightPlanPair.getFlightNum()))
            flightNumber += flightPlanPair.getFlightCompanyCode() + flightPlanPair.getFlightNum();

        Map<String, Object> params = new HashMap<>();
        params.put("occur", System.currentTimeMillis());

        if (isChanged)
            params.put("message", String.format(REALTIME_DYNAMIC_MESSAGE_CONFS.getProperty("FLIGHT_GATE_NUMBER_CHANGED"), flightNumber, oldGate, newGate));
        else
            params.put("message", String.format(REALTIME_DYNAMIC_MESSAGE_CONFS.getProperty("FLIGHT_GATE_NUMBER_MANUAL_DIST"), flightNumber, newGate));

        try {
            ConcurrentClientsHolder.getSocket("/dynamic_realtime_message").emit("dynamic_message_dispatcher", OBJECT_MAPPER.writeValueAsString(params));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取最大日期（资源系统资源分配甘特图的时间范围是航班动态数据中最晚日期的前后一天）
     *
     * @return
     */
    @RequestMapping("getMaxPlanDate")
    @ResponseBody
    public String getMaxPlanDate() {
        return resourceSharingService.getMaxPlanDate();
    }

    @RequestMapping("getMaxPlanDate4Mock")
    @ResponseBody
    public String getMaxPlanDate4Mock(String infoId, String resourceType) {
        return resourceSharingService.getMaxPlanDate4Mock(infoId, resourceType);
    }

    @RequestMapping("aircraft-stand-mock-dist")
    @RequiresPermissions("rms:rs:aircraft_dist")
    @ResponseBody
    public Message aircraftStandMockDist(ResourceMockDistInfo resourceMockDistInfo, HttpServletRequest request) {
        Message message = new Message(0);
        if (null == resourceMockDistInfo.getMockStartDate() || null == resourceMockDistInfo.getMockOverDate())
            return throwsMessageError(request, 0, "开始时间与结束时间不能为空！");

        if (resourceMockDistInfo.getMockStartDate().after(resourceMockDistInfo.getMockOverDate()))
            return throwsMessageError(request, 0, "开始时间不能大于结束时间！");

        if (StringUtils.isBlank(resourceMockDistInfo.getPackageName()) || StringUtils.isBlank(resourceMockDistInfo.getPackageCode()))
            return throwsMessageError(request, 0, "模拟分配资源包名称不能为空！");
        List<FlightPlanPair> mockDatas = flightPlanPairService.findMockList(resourceMockDistInfo);
        if (Collections3.isEmpty(mockDatas)) return throwsMessageError(request, 0, "没找到符合条件的数据！");
        KnowledgePackage aircraftStandPkg = null;
        try {
            aircraftStandPkg = getAircraftStandPkg(resourceMockDistInfo.getPackageCode());
        } catch (Exception e) {
            if (e.getMessage().contains("not found")) return throwsMessageError(request, 0, "找不到机位的规则文件！");
            else if (e.getMessage().contains("Permission denied"))
                return throwsMessageError(request, 0, "您没有调用机位规则文件的权限！");
        }

        return aircraftStandPkg != null ? resourceSharingService.mockDistAircraftStand(resourceMockDistInfo, mockDatas, aircraftStandPkg, message) : throwsMessageError(request, 0, "无法调用指定的资源包！");
    }

    @RequestMapping("arrival-gate-mock-dist")
    @RequiresPermissions("rms:rs:arrival_gate_dist")
    @ResponseBody
    public Message arrivalGateMockDist(ResourceMockDistInfo resourceMockDistInfo, HttpServletRequest request) {
        Message message = mockDistCommonValidation(resourceMockDistInfo, request);

        if (message.getCode() == 0) return message;

        List<FlightDynamic> mockDatas = flightDynamicService.findMockDynamics4ArrivalGate(ImmutableMap.of("startTime", resourceMockDistInfo.getMockStartDate(), "endTime", resourceMockDistInfo.getMockOverDate(),
                "filterDistedRes", resourceMockDistInfo.getFilterDistedRes(), "dsfn", BaseService.dataScopeFilterNew(UserUtils.getUser())));
        if (Collections3.isEmpty(mockDatas)) return throwsMessageError(request, 0, "没找到符合条件的航班动态数据！");
        KnowledgePackage arrivalGatePkg = null;
        try {
            arrivalGatePkg = getArrivalGatePkg(resourceMockDistInfo.getPackageCode());
        } catch (Exception e) {
            if (e.getMessage().contains("not found")) return throwsMessageError(request, 0, "找不到到港门的规则文件！");
            else if (e.getMessage().contains("Permission denied"))
                return throwsMessageError(request, 0, "您没有调用到港门规则文件的权限！");
        }

        return arrivalGatePkg != null ? resourceSharingService.mockDistArrivalGate(resourceMockDistInfo, mockDatas, arrivalGatePkg, message) : throwsMessageError(request, 0, "无法调用指定的资源包！");
    }

    @RequestMapping("boarding-gate-mock-dist")
    @RequiresPermissions("rms:rs:boarding_gate_dist")
    @ResponseBody
    public Message boardingGateMockDist(ResourceMockDistInfo resourceMockDistInfo, HttpServletRequest request) {
        Message message = mockDistCommonValidation(resourceMockDistInfo, request);

        if (message.getCode() == 0) return message;

        List<FlightDynamic> mockDatas = flightDynamicService.findMockDynamics4BoardingGate(ImmutableMap.of("startTime", resourceMockDistInfo.getMockStartDate(), "endTime", resourceMockDistInfo.getMockOverDate(),
                "filterDistedRes", resourceMockDistInfo.getFilterDistedRes(), "dsfn", BaseService.dataScopeFilterNew(UserUtils.getUser())));
        if (Collections3.isEmpty(mockDatas)) return throwsMessageError(request, 0, "没找到符合条件的航班动态数据！");
        KnowledgePackage boardingGatePkg = null;
        try {
            boardingGatePkg = getBoardingGatePkg(resourceMockDistInfo.getPackageCode());
        } catch (Exception e) {
            if (e.getMessage().contains("not found")) return throwsMessageError(request, 0, "找不到登机口的规则文件！");
            else if (e.getMessage().contains("Permission denied"))
                return throwsMessageError(request, 0, "您没有调用登机口规则文件的权限！");
        }

        return boardingGatePkg != null ? resourceSharingService.mockDistBoardingGate(resourceMockDistInfo, mockDatas, boardingGatePkg, message) : throwsMessageError(request, 0, "无法调用指定的资源包！");
    }

    @RequestMapping("carousel-mock-dist")
    @RequiresPermissions("rms:rs:carousel_dist")
    @ResponseBody
    public Message carouselMockDist(ResourceMockDistInfo resourceMockDistInfo, HttpServletRequest request) {
        Message message = mockDistCommonValidation(resourceMockDistInfo, request);

        if (message.getCode() == 0) return message;

        List<FlightDynamic> mockDatas = flightDynamicService.findMockDynamics4Carousel(ImmutableMap.of("startTime", resourceMockDistInfo.getMockStartDate(), "endTime", resourceMockDistInfo.getMockOverDate(),
                "filterDistedRes", resourceMockDistInfo.getFilterDistedRes(), "dsfn", BaseService.dataScopeFilterNew(UserUtils.getUser())));
        if (Collections3.isEmpty(mockDatas)) return throwsMessageError(request, 0, "没找到符合条件的航班动态数据！");
        KnowledgePackage carouselPkg = null;
        try {
            carouselPkg = getCarouselPkg(resourceMockDistInfo.getPackageCode());
        } catch (Exception e) {
            if (e.getMessage().contains("not found")) return throwsMessageError(request, 0, "找不到行李转盘的规则文件！");
            else if (e.getMessage().contains("Permission denied"))
                return throwsMessageError(request, 0, "您没有调用行李转盘规则文件的权限！");
        }

        return carouselPkg != null ? resourceSharingService.mockDistCarousel(resourceMockDistInfo, mockDatas, carouselPkg, message) : throwsMessageError(request, 0, "无法调用指定的资源包！");
    }

    @RequestMapping("slide-coast-mock-dist")
    @RequiresPermissions("rms:rs:slide_coast_dist")
    @ResponseBody
    public Message slideCoastMockDist(ResourceMockDistInfo resourceMockDistInfo, HttpServletRequest request) {
        Message message = mockDistCommonValidation(resourceMockDistInfo, request);

        if (message.getCode() == 0) return message;

        List<FlightDynamic> mockDatas = flightDynamicService.findMockDynamics4SlideCoast(ImmutableMap.of("startTime", resourceMockDistInfo.getMockStartDate(), "endTime", resourceMockDistInfo.getMockOverDate(),
                "filterDistedRes", resourceMockDistInfo.getFilterDistedRes(), "dsfn", BaseService.dataScopeFilterNew(UserUtils.getUser())));
        if (Collections3.isEmpty(mockDatas)) return throwsMessageError(request, 0, "没找到符合条件的航班动态数据！");
        KnowledgePackage slideCoastPkg = null;
        try {
            slideCoastPkg = getSlideCoastPkg(resourceMockDistInfo.getPackageCode());
        } catch (Exception e) {
            if (e.getMessage().contains("not found")) return throwsMessageError(request, 0, "找不到滑槽的规则文件！");
            else if (e.getMessage().contains("Permission denied"))
                return throwsMessageError(request, 0, "您没有调用滑槽规则文件的权限！");
        }

        return slideCoastPkg != null ? resourceSharingService.mockDistSlideCoast(resourceMockDistInfo, mockDatas, slideCoastPkg, message) : throwsMessageError(request, 0, "无法调用指定的资源包！");
    }

    @RequestMapping("checkin-counter-mock-dist")
    @RequiresPermissions("rms:rs:checkin_counter_dist")
    @ResponseBody
    public Message checkingCounterMockDist(ResourceMockDistInfo resourceMockDistInfo, HttpServletRequest request) {
        Message message = mockDistCommonValidation(resourceMockDistInfo, request);

        if (message.getCode() == 0) return message;

        List<FlightDynamic> mockDatas = flightDynamicService.findMockDynamics4CheckinCounter(ImmutableMap.of("startTime", resourceMockDistInfo.getMockStartDate(), "endTime", resourceMockDistInfo.getMockOverDate(),
                "filterDistedRes", resourceMockDistInfo.getFilterDistedRes(), "dsfn", BaseService.dataScopeFilterNew(UserUtils.getUser())));
        if (Collections3.isEmpty(mockDatas)) return throwsMessageError(request, 0, "没找到符合条件的航班动态数据！");
        KnowledgePackage checkinCounterPkg = null;
        try {
            checkinCounterPkg = getCheckinCounterPkg(resourceMockDistInfo.getPackageCode());
        } catch (Exception e) {
            if (e.getMessage().contains("not found")) return throwsMessageError(request, 0, "找不到值机柜台的规则文件！");
            else if (e.getMessage().contains("Permission denied"))
                return throwsMessageError(request, 0, "您没有调用值机柜台规则文件的权限！");
        }

        return checkinCounterPkg != null ? resourceSharingService.mockDistCheckinCounter(resourceMockDistInfo, mockDatas, checkinCounterPkg, message) : throwsMessageError(request, 0, "无法调用指定的资源包！");
    }

    @RequestMapping("security-checkin-mock-dist")
    @RequiresPermissions("rms:rs:security_checkin_dist")
    @ResponseBody
    public Message securityCheckinMockDist(ResourceMockDistInfo resourceMockDistInfo, HttpServletRequest request) {
        Message message = mockDistCommonValidation(resourceMockDistInfo, request);

        if (message.getCode() == 0) return message;

        List<FlightDynamic> mockDatas = flightDynamicService.findMockDynamics4SecurityCheckin(ImmutableMap.of("startTime", resourceMockDistInfo.getMockStartDate(), "endTime", resourceMockDistInfo.getMockOverDate(),
                "filterDistedRes", resourceMockDistInfo.getFilterDistedRes(), "dsfn", BaseService.dataScopeFilterNew(UserUtils.getUser())));
        if (Collections3.isEmpty(mockDatas)) return throwsMessageError(request, 0, "没找到符合条件的航班动态数据！");
        KnowledgePackage securityCheckinPkg = null;
        try {
            securityCheckinPkg = getSecurityCheckinPkg(resourceMockDistInfo.getPackageCode());
        } catch (Exception e) {
            if (e.getMessage().contains("not found")) return throwsMessageError(request, 0, "找不到安检口的规则文件！");
            else if (e.getMessage().contains("Permission denied"))
                return throwsMessageError(request, 0, "您没有调用安检口规则文件的权限！");
        }

        return securityCheckinPkg != null ? resourceSharingService.mockDistSecurityCheckin(resourceMockDistInfo, mockDatas, securityCheckinPkg, message) : throwsMessageError(request, 0, "无法调用指定的资源包！");
    }

    @RequestMapping("departure-hall-mock-dist")
    @RequiresPermissions("rms:rs:departure_hall_dist")
    @ResponseBody
    public Message departureHallMockDist(ResourceMockDistInfo resourceMockDistInfo, HttpServletRequest request) {
        Message message = mockDistCommonValidation(resourceMockDistInfo, request);

        if (message.getCode() == 0) return message;

        List<FlightDynamic> mockDatas = flightDynamicService.findMockDynamics4DepartureHall(ImmutableMap.of("startTime", resourceMockDistInfo.getMockStartDate(), "endTime", resourceMockDistInfo.getMockOverDate(),
                "filterDistedRes", resourceMockDistInfo.getFilterDistedRes(), "dsfn", BaseService.dataScopeFilterNew(UserUtils.getUser())));
        if (Collections3.isEmpty(mockDatas)) return throwsMessageError(request, 0, "没找到符合条件的航班动态数据！");
        KnowledgePackage departureHallPkg = null;
        try {
            departureHallPkg = getDepartureHallPkg(resourceMockDistInfo.getPackageCode());
        } catch (Exception e) {
            if (e.getMessage().contains("not found")) return throwsMessageError(request, 0, "找不到候机厅的规则文件！");
            else if (e.getMessage().contains("Permission denied"))
                return throwsMessageError(request, 0, "您没有调用候机厅规则文件的权限！");
        }

        return departureHallPkg != null ? resourceSharingService.mockDistDepartureHall(resourceMockDistInfo, mockDatas, departureHallPkg, message) : throwsMessageError(request, 0, "无法调用指定的资源包！");
    }

    public Message mockDistCommonValidation(ResourceMockDistInfo resourceMockDistInfo, HttpServletRequest request) {
        if (null == resourceMockDistInfo.getMockStartDate() || null == resourceMockDistInfo.getMockOverDate())
            return throwsMessageError(request, 0, "开始时间与结束时间不能为空！");

        if (resourceMockDistInfo.getMockStartDate().after(resourceMockDistInfo.getMockOverDate()))
            return throwsMessageError(request, 0, "开始时间不能大于结束时间！");

        if (StringUtils.isBlank(resourceMockDistInfo.getPackageName()) || StringUtils.isBlank(resourceMockDistInfo.getPackageCode()))
            return throwsMessageError(request, 0, "模拟分配资源包名称不能为空！");
        return new Message(1);
    }

    @RequiresPermissions("user")
    @RequestMapping("fetch-rule-packages")
    @ResponseBody
    public Message fetchAvailablePackages(String projectCode) {
        Message message = new Message();
        message.setCode(0);

        List<KeyValuePairsWapper> vars = Lists.newArrayList();
        List<String> existProjects = repositoryService.loadProject("bryt").stream().map((project) -> project.getName()).collect(Collectors.toList());

        if (StringUtils.isEmpty(projectCode)) {
            message.setMessage("项目名称不能为空！");
            return message;
        }

        if (!existProjects.contains(projectCode)) {
            message.setMessage("不存在的项目名称，请检查！");
            return message;
        }

        // 限定包含` _DIST.PKG `字符的才算为资源分配规则包
        List<ResourcePackage> resourcePackages = null;
        try {
            resourcePackages = repositoryService.loadProjectResourcePackages(projectCode);
        } catch (Exception e) {
            message.setMessage("获取知识包时出现异常：" + e.getMessage());
            return message;
        }

        if (null == resourcePackages || resourcePackages.isEmpty()) {
            message.setMessage("该资源没有配置知识包，无法进行分配！");
            return message;
        }

        resourcePackages = resourcePackages.stream().filter(resourcePackage -> resourcePackage.getId().endsWith("_DIST.PKG")).collect(Collectors.toList());

        if (resourcePackages.isEmpty()) {
            message.setMessage("没有符合条件的资源分配知识包！");
            return message;
        }

        resourcePackages.forEach(resourcePackage -> vars.add(new KeyValuePairsWapper(resourcePackage.getName(), resourcePackage.getId())));

        message.setCode(1);
        message.setResult(ImmutableMap.of("data", vars));
        return message;
    }


    //判断值机柜台编号是国际还是国内 2017年3月15日11时16分  1-国内 2-国际
    private void isCheckinCounterInteOrIntl(CheckingCounterOccupyingInfo checkingCounterOccupyingInfo) {
        String inteCheckingCounterCode = checkingCounterOccupyingInfo.getInteCheckingCounterCode();
        String intlCheckingCounterCode = checkingCounterOccupyingInfo.getIntlCheckingCounterCode();
        List<String> inteCCResult = new ArrayList(), intlCCResult = new ArrayList();
        if (StringUtils.isNotBlank(inteCheckingCounterCode)) {
            String[] inteCC = inteCheckingCounterCode.split(",");
            for (String s : inteCC) {
                String nature = checkinCounterDao.findByNature(s);
                if (StringUtils.equals(nature, "2")) {
                    intlCCResult.add(s);
                } else {
                    inteCCResult.add(s);
                }
            }
        }
        if (StringUtils.isNotBlank(intlCheckingCounterCode)) {
            String[] intlCC = intlCheckingCounterCode.split(",");
            for (String s : intlCC) {
                String nature = checkinCounterDao.findByNature(s);
                if (StringUtils.equals(nature, "1")) {
                    inteCCResult.add(s);
                } else {
                    intlCCResult.add(s);
                }
            }
        }
        inteCheckingCounterCode = Collections3.isEmpty(inteCCResult) ? null : StringUtils.join(inteCCResult, ",");
        intlCheckingCounterCode = Collections3.isEmpty(intlCCResult) ? null : StringUtils.join(intlCCResult, ",");

        checkingCounterOccupyingInfo.setInteCheckingCounterCode(inteCheckingCounterCode);
        checkingCounterOccupyingInfo.setIntlCheckingCounterCode(intlCheckingCounterCode);
    }

    @RequestMapping("preview")
    public String mockDataPreview(String infoId, String resourceType, String path, ModelMap params) {
        params.addAttribute("infoId", infoId);
        path = path.toLowerCase();
        params.addAttribute("resourceType", resourceType);
        params.addAttribute("path", path);
        return "rms/rs/mock-preview/" + path;
    }

    /**
     * 跳转审核页面
     *
     * @param map
     * @return
     */
    @RequiresPermissions("ams:resource:check")
    @RequestMapping("checkView")
    public String checkFlightdynamic(ModelMap map) {
        List<CheckAspect> allList = checkAspectService.findList(new CheckAspect()).stream()
                .filter(checkAspect -> (UserUtils.dataScopeFilterCheckAsp(checkAspect.getCreateBy()))).collect(Collectors.toList());
        map.put("adviceYes", allList.stream().filter(c -> (StringUtils.equals(c.getAdviceFlag(), "1"))).collect(Collectors.toList()));
        map.put("adviceNo", allList.stream().filter(c -> (StringUtils.equals(c.getAdviceFlag(), "0"))).collect(Collectors.toList()));
        map.put("adviceNull", allList.stream().filter(c -> (c.getAdviceFlag() == null)).collect(Collectors.toList()));
        return "ams/flightplan/checkView";
    }

    @RequiresPermissions("ams:resource:check")
    @RequestMapping("cView")
    public String checkFlightdynamicList(ModelMap map) {
        List<CheckAspect> allList = checkAspectService.findAllList(new CheckAspect()).stream()
                .filter(checkAspect -> (UserUtils.dataScopeFilterCheckAsp(checkAspect.getCreateBy()))).collect(Collectors.toList());
        map.put("adviceYes", allList.stream().filter(c -> (StringUtils.equals(c.getAdviceFlag(), "1"))).collect(Collectors.toList()));
        map.put("adviceNo", allList.stream().filter(c -> (StringUtils.equals(c.getAdviceFlag(), "0"))).collect(Collectors.toList()));
        map.put("adviceNull", allList.stream().filter(c -> (c.getAdviceFlag() == null)).collect(Collectors.toList()));
        return "ams/flightplan/checkView";
    }

    /**
     * 处理审核方法
     *
     * @param ids
     * @param adviceFlag
     * @param message
     * @return
     */
    @RequiresPermissions("ams:resource:check")
    @RequestMapping("doCheck")
    @ResponseBody
    public Message doCheckFlightdynamic(@RequestParam("ids[]") ArrayList<String> ids, String adviceFlag, Message message) {
        if (!Collections3.isEmpty(ids) && StringUtils.isNotBlank(adviceFlag)) {
            checkAspectService.updateList(ids, adviceFlag, UserUtils.getUser().getId(), message);
        }
        return message;
    }
}