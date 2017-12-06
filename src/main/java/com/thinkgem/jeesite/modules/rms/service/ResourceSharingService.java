package com.thinkgem.jeesite.modules.rms.service;

import cn.net.metadata.annotation.Check;
import cn.net.metadata.datasync.socketio.ConcurrentClientsHolder;
import cn.net.metadata.rule.action.dist.ResourceDistUtils;
import com.bstek.urule.Utils;
import com.bstek.urule.runtime.KnowledgePackage;
import com.bstek.urule.runtime.KnowledgeSession;
import com.bstek.urule.runtime.KnowledgeSessionFactory;
import com.bstek.urule.runtime.service.KnowledgeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.PropertiesLoader;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.dao.FlightDynamicDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.dao.*;
import com.thinkgem.jeesite.modules.rms.entity.*;
import com.thinkgem.jeesite.modules.rms.rd.ResourceType;
import com.thinkgem.jeesite.modules.rms.rd.container.ResourceDistributionContainer;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 资源分配业务类
 * Created by xiaowu on 16/8/3.
 */
@Service
public class ResourceSharingService {


    private final static PropertiesLoader REALTIME_DYNAMIC_MESSAGE_CONFS = new PropertiesLoader("classpath:notification.realtime-dynamic-message-conf/config.properties");
    private final static String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final KnowledgeService knowledgeService = (KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);

    private static final Logger logger = LoggerFactory.getLogger(ResourceSharingService.class);

    @Autowired
    GateOccupyingInfoDao gateOccupyingInfoDao;

    @Autowired
    GateOccupyingInfoService gateOccupyingInfoService;

    @Autowired
    FlightPairProgressInfoDao flightPairProgressInfoDao;

    @Autowired
    ArrivalGateOccupyingInfoDao arrivalGateOccupyingInfoDao;

    @Autowired
    CarouselOccupyingInfoDao carouselOccupyingInfoDao;

    @Autowired
    FlightDynamicDao flightDynamicDao;

    @Autowired
    BoardingGateOccupyingInfoDao boardingGateOccupyingInfoDao;

    @Autowired
    SlideCoastOccupyingInfoDao slideCoastOccupyingInfoDao;

    @Autowired
    DepartureHallOccupyingInfoDao departureHallOccupyingInfoDao;

    @Autowired
    CheckingCounterOccupyingInfoDao checkingCounterOccupyingInfoDao;

    @Autowired
    SecurityCheckinOccupyingInfoDao securityCheckinOccupyingInfoDao;

    @Autowired
    FlightPlanPairDao flightPlanPairDao;

    @Autowired
    FlightDynamicService flightDynamicService;

    @Autowired
    FlightPlanPairService flightPlanPairService;

    @Autowired
    CommonBatchDao commonBatchDao;
    @Autowired
    ResourceMockDistInfoService resourceMockDistInfoService;
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

    /**
     * 自动分配机位
     *
     * @return
     */
    public Message distGateAuto(Map<String, Object> conditions) {
        Message message = new Message();
        StringBuffer msg = new StringBuffer();
        try {
            String endTimeStr = String.valueOf(conditions.get("endTime"));
            String startTimeStr = String.valueOf(conditions.get("startTime"));
            Date endTime = DateUtils.parseDate(endTimeStr, yyyyMMddHHmm);
            Date startTime = DateUtils.parseDate(startTimeStr, yyyyMMddHHmm);
            logger.info("开始自动分配机位, 分配时间为 {} ~ {}", startTimeStr, endTimeStr);
            msg.append(StringUtils.tpl("开始自动分配机位, 分配时间为 {} ~ {}", startTimeStr, endTimeStr));
            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession((KnowledgePackage) conditions.get("urule-package"));
            List<FlightPlanPair> flightPlanPairs = flightPlanPairDao.findUnDistPairs(ImmutableMap.of("startTime", startTime, "endTime", endTime, "dsfn", BaseService.dataScopeFilterNew((User) conditions.get("currentUser"), true), "allotType", conditions.get("allotType")));
            logger.info("共找到{}个未分配机位的航班信息", flightPlanPairs.size());
            msg.append(StringUtils.tpl("共找到{}个未分配机位的航班信息", flightPlanPairs.size()));
            Map<String, Object> parameters = ImmutableMap.of("need_save", conditions.get("need_save"), "mock_dist", conditions.get("mock_dist"), "dist_type", "自动分配", "threshold_value", DictUtils.getDictValue("资源分配间隔值", "threshold_value", "15"));
            int counter = 1;
            Date maxDate = DateUtils.addYears(new Date(), 5);
            final Function<FlightPlanPair, Date> byPlanDate = pair -> pair.getPlanDate(); //按航班日期
            final Function<FlightPlanPair, Date> byArrOrDep = pair -> pair.getFlightDynimicId() == null ? pair.getDeparturePlanTime2():pair.getArrivalPlanTime();
            //todo 为机位自动分配前排序 单出计起+进港计达
            flightPlanPairs = flightPlanPairs.stream().sorted(Comparator.comparing(byPlanDate).thenComparing(byArrOrDep)).collect(Collectors.toList());
            for (FlightPlanPair flightPlanPair : flightPlanPairs) {
                flightPlanPair.setRandom(String.valueOf(conditions.get("random")));
                logger.info("{}/{}, 机型：{}, id:{}", counter++, flightPlanPairs.size(), flightPlanPair.getAircraftTypeCode(), flightPlanPair.getId());
                session.insert(flightPlanPair);
                GateOccupyingInfo info = gateOccupyingInfoDao.getByFlightDynamicId(flightPlanPair.getId());
                if (info == null) {
                    logger.info("找不到对应的占用信息，忽略！");
                    continue;
                }
                info.setRandom(flightPlanPair.getRandom());
                session.insert(info);
                session.startProcess("AircraftDistributionFlow", parameters);
                if (session.getParameter("success") != null && (Boolean) session.getParameter("success") == true) {
                    info.setActualGateNum((String) session.getParameter("result"));
                    msg.append(StringUtils.tpl("{}/{} - {},{};", (counter - 1), flightPlanPairs.size(), LogUtils.msgPair(flightPlanPair), info.toString()));
                } else msg.append(session.getParameter("message") + ";");
            }

            message.setMsg(1, msg.toString());
            resourceAutoDistCompletedMessageSender(ImmutableMap.of("startTime", startTime, "endTime", endTime, "resourceName", "机位"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("自动分配机位失败:{}", e.getMessage());
            msg.append(StringUtils.tpl("自动分配机位失败:{}", e.getMessage()));
            message.setMessage("自动分配机位失败: " + e.getMessage());
            message.setCode(0);
        } finally {
            //分配完成消息
            ConcurrentClientsHolder.getSocket("/global/dynamics").emit("assignment_complete_message", "complete");
        }
        return message;
    }

    /**
     * 自动分配行李转盘
     */
    public Message distCarouselAuto(Map<String, Object> conditions) {
        Message message = new Message();
        StringBuffer msg = new StringBuffer();
        try {
            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession((KnowledgePackage) conditions.get("urule-package"));
            String startTimeStr = String.valueOf(conditions.get("startTime"));
            String endTimeStr = String.valueOf(conditions.get("endTime"));
            Date startTime = DateUtils.parseDate(startTimeStr, yyyyMMddHHmm);
            Date endTime = DateUtils.parseDate(endTimeStr, yyyyMMddHHmm);
            logger.info("开始自动分配行李转盘，分配时间为：{} ~ {}", startTimeStr, endTimeStr);
            msg.append(StringUtils.tpl("开始自动分配行李转盘，分配时间为：{} ~ {}", startTimeStr, endTimeStr));
            List<FlightDynamic> flightDynamics = flightDynamicService.findCarouselUnDistDynamics(ImmutableMap.of("startTime", startTime, "endTime", endTime, "dsfn", BaseService.dataScopeFilterNew((User) conditions.get("currentUser"))));
            logger.info("共找到{}个未分配行李转盘的航班信息", flightDynamics.size());
            msg.append(StringUtils.tpl("共找到{}个未分配行李转盘的航班信息:", flightDynamics.size()));

            Map<String, Object> parameters = ImmutableMap.of("mock_dist", conditions.get("mock_dist"), "dist_type", "自动分配",
                    "carousel_deftime", DictUtils.getDictValue("行李转盘默认时长", "carousel_deftime", "50"),
                    "threshold_value", DictUtils.getDictValue("资源分配间隔值", "threshold_value", "15"));

            int counter = 1;
            for (FlightDynamic flightDynamic : flightDynamics) {
                logger.info("{}/{} - 航班号：{}, 机型：{}, id:{}", counter++, flightDynamics.size(), flightDynamic.getFlightNum(), flightDynamic.getAircraftTypeCode(), flightDynamic.getId());
                session.insert(flightDynamic);
                CarouselOccupyingInfo carouselOccupyingInfo = carouselOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
                if (carouselOccupyingInfo == null) {
                    logger.info("找不到对应的占用信息，忽略！");
                    continue;
                }
                carouselOccupyingInfo.setRandom(String.valueOf(conditions.get("random")));
                session.insert(carouselOccupyingInfo);
                session.startProcess("CarouselDistributionFlow", parameters);
                if (session.getParameter("success") != null && (Boolean) session.getParameter("success") == false)
                    msg.append(session.getParameter("message") + ";");
            }
            message.setMessage(msg.toString());
            message.setCode(1);

            resourceAutoDistCompletedMessageSender(ImmutableMap.of("startTime", startTime, "endTime", endTime, "resourceName", "行李转盘"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("自动分配行李转盘失败:{}", e.getMessage());
            msg.append(StringUtils.tpl("自动分配行李转盘失败:{}", e.getMessage()));
            message.setMessage("自动分配行李转盘失败: " + e.getMessage());
            message.setCode(0);
        } finally {
            logger.info("自动分配行李转盘成功");
            ConcurrentClientsHolder.getSocket("/global/dynamics").emit("assignment_complete_message", "complete");
        }

        return message;
    }

    /**
     * 自动分配值机柜台
     */
    public Message distCheckingCounterAuto(Map<String, Object> conditions) {
        Message message = new Message();
        StringBuffer msg = new StringBuffer();
        try {
            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession((KnowledgePackage) conditions.get("urule-package"));
            String startTimeStr = String.valueOf(conditions.get("startTime"));
            String endTimeStr = String.valueOf(conditions.get("endTime"));
            Date startTime = DateUtils.parseDate(startTimeStr, yyyyMMddHHmm);
            Date endTime = DateUtils.parseDate(endTimeStr, yyyyMMddHHmm);
            logger.info("开始自动分配值机柜台，分配时间为：{} ~ {}", startTimeStr, endTimeStr);
            msg.append(StringUtils.tpl("开始自动分配值机柜台，分配时间为：{} ~ {}", startTimeStr, endTimeStr));
            List<FlightDynamic> flightDynamics = flightDynamicService.findCheckinCounterUnDistDynamics(ImmutableMap.of("startTime", startTime, "endTime", endTime, "dsfn", BaseService.dataScopeFilterNew((User) conditions.get("currentUser"))));
            logger.info("共找到{}个未分配值机柜台的航班信息", flightDynamics.size());
            msg.append(",共找到" + flightDynamics.size() + "个未分配值机柜台的航班信息:");
            int counter = 1;
            for (FlightDynamic flightDynamic : flightDynamics) {
                logger.info("{}/{} - 航班号：{}, 机型：{}, id:{}", counter++, flightDynamics.size(), flightDynamic.getFlightNum(), flightDynamic.getAircraftTypeCode(), flightDynamic.getId());
                session.insert(flightDynamic);
                CheckingCounterOccupyingInfo info = checkingCounterOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
                if (info == null) {
                    logger.info("找不到对应的占用信息，忽略！");
                    continue;
                }
                info.setRandom(String.valueOf(conditions.get("random")));
                session.insert(info);
                session.startProcess("CheckingCounterDistributionFlow");
                if (session.getParameter("success") != null && (Boolean) session.getParameter("success") == false)
                    msg.append(session.getParameter("message") + ";");
            }
            message.setMessage(msg.toString());
            message.setCode(1);

            resourceAutoDistCompletedMessageSender(ImmutableMap.of("startTime", startTime, "endTime", endTime, "resourceName", "值机柜台"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("自动分配值机柜台失败:{}", e.getMessage());
            message.setMessage("自动分配值机柜台失败: " + e.getMessage());
            message.setCode(0);
        } finally {
            logger.info("自动分配值机柜台成功");
            ConcurrentClientsHolder.getSocket("/global/dynamics").emit("assignment_complete_message", "complete");
        }

        return message;
    }

    /**
     * 自动分配登机口
     */
    public Message distBoardingGateAuto(Map<String, Object> conditions) {
        Message message = new Message();
        StringBuffer msg = new StringBuffer();
        try {
            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession((KnowledgePackage) conditions.get("urule-package"));
            String startTimeStr = String.valueOf(conditions.get("startTime"));
            String endTimeStr = String.valueOf(conditions.get("endTime"));
            Date startTime = DateUtils.parseDate(startTimeStr, yyyyMMddHHmm);
            Date endTime = DateUtils.parseDate(endTimeStr, yyyyMMddHHmm);
            logger.info("开始自动分配登机口，分配时间为：{} ~ {}", startTimeStr, endTimeStr);
            msg.append(StringUtils.tpl("开始自动分配登机口，分配时间为：{} ~ {}", startTimeStr, endTimeStr));
            List<FlightDynamic> flightDynamics = flightDynamicService.findBoardingGateUnDistDynamics(ImmutableMap.of("startTime", startTime, "endTime", endTime, "dsfn", BaseService.dataScopeFilterNew((User) conditions.get("currentUser"))));
            logger.info("共找到{}个未分配登机口的航班信息", flightDynamics.size());
            msg.append(StringUtils.tpl("共找到{}个未分配登机口的航班信息", flightDynamics.size()));
            Map<String, Object> parameters = ImmutableMap.of("mock_dist", conditions.get("mock_dist"), "dist_type", "自动分配", "threshold_value", DictUtils.getDictValue("资源分配间隔值", "threshold_value", "15"));
            int counter = 1;
            for (FlightDynamic flightDynamic : flightDynamics) {
                logger.info("{}/{} - 航班号：{}, 机型：{}, id：{}", counter++, flightDynamics.size(), flightDynamic.getFlightNum(), flightDynamic.getAircraftTypeCode(), flightDynamic.getId());
                session.insert(flightDynamic);
                BoardingGateOccupyingInfo boardingGateOccupyingInfo = boardingGateOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
                if (boardingGateOccupyingInfo == null) {
                    logger.info("找不到对应的占用信息，忽略！");
                    continue;
                }
                boardingGateOccupyingInfo.setRandom(String.valueOf(conditions.get("random")));
                session.insert(boardingGateOccupyingInfo);
                session.startProcess("BoardingGateDistributionFlow", parameters);
                if (session.getParameter("success") != null && (Boolean) session.getParameter("success") == false)
                    msg.append(session.getParameter("message") + ";");
            }
            message.setMessage(msg.toString());
            message.setCode(1);

            resourceAutoDistCompletedMessageSender(ImmutableMap.of("startTime", startTime, "endTime", endTime, "resourceName", "登机口"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("自动分配登机口失败：{}", e.getMessage());
            msg.append(StringUtils.tpl("自动分配登机口失败：{}", e.getMessage()));
            message.setMessage("自动分配登机口失败：" + e.getMessage());
            message.setCode(0);
        } finally {
            logger.info("自动分配登机口成功");
            ConcurrentClientsHolder.getSocket("/global/dynamics").emit("assignment_complete_message", "complete");
        }

        return message;
    }

    /**
     * 自动分配到港门
     */
    public Message distArrivalGateAuto(Map<String, Object> conditions) {
        Message message = new Message();
        StringBuffer msg = new StringBuffer();
        try {
            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession((KnowledgePackage) conditions.get("urule-package"));
            String startTimeStr = String.valueOf(conditions.get("startTime"));
            String endTimeStr = String.valueOf(conditions.get("endTime"));
            Date startTime = DateUtils.parseDate(startTimeStr, yyyyMMddHHmm);
            Date endTime = DateUtils.parseDate(endTimeStr, yyyyMMddHHmm);
            logger.info("开始自动分配到港门，分配时间为：{} ~ {}", startTimeStr, endTimeStr);
            msg.append(StringUtils.tpl("开始自动分配到港门，分配时间为：{} ~ {}", startTimeStr, endTimeStr));
            List<FlightDynamic> flightDynamics = flightDynamicService.findArrivalGateUnDistDynamics(ImmutableMap.of("startTime", startTime, "endTime", endTime, "dsfn", BaseService.dataScopeFilterNew((User) conditions.get("currentUser"))));
            logger.info("共找到{}个未分配到港门的航班信息", flightDynamics.size());
            msg.append(StringUtils.tpl("共找到{}个未分配到港门的航班信息", flightDynamics.size()));

            Map<String, Object> parameters = ImmutableMap.of("mock_dist", conditions.get("mock_dist"), "dist_type", "自动分配", "threshold_value", DictUtils.getDictValue("资源分配间隔值", "threshold_value", "15"));
            int counter = 1;
            for (FlightDynamic flightDynamic : flightDynamics) {
                logger.info("{}/{} - 航班号：{}, 机型：{}, id:{}", counter++, flightDynamics.size(), flightDynamic.getFlightNum(), flightDynamic.getAircraftTypeCode(), flightDynamic.getId());
                session.insert(flightDynamic);
                ArrivalGateOccupyingInfo info = arrivalGateOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
                if (info == null) {
                    logger.info("找不到对应的占用信息，忽略！");
                    continue;
                }
                info.setRandom(String.valueOf(conditions.get("random")));
                session.insert(info);
                session.startProcess("ArrivalGateDistributionFlow", parameters);
                if (session.getParameter("success") != null && (Boolean) session.getParameter("success") == false)
                    msg.append(session.getParameter("message") + ";");
            }
            message.setMessage(msg.toString());
            message.setCode(1);

            resourceAutoDistCompletedMessageSender(ImmutableMap.of("startTime", startTime, "endTime", endTime, "resourceName", "到港门"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("自动分配到港门失败：{}", e.getMessage());
            msg.append(StringUtils.tpl("自动分配到港门失败：{}", e.getMessage()));
            message.setMessage("自动分配到港门失败：" + e.getMessage());
            message.setCode(0);
        } finally {
            logger.info("自动分配到港门成功");
            ConcurrentClientsHolder.getSocket("/global/dynamics").emit("assignment_complete_message", "complete");
        }

        return message;
    }

    /**
     * 自动分配滑槽
     */
    public Message distSlideCoastAuto(Map<String, Object> conditions) {
        Message message = new Message();
        StringBuffer msg = new StringBuffer();
        try {
            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession((KnowledgePackage) conditions.get("urule-package"));
            String startTimeStr = String.valueOf(conditions.get("startTime"));
            String endTimeStr = String.valueOf(conditions.get("endTime"));
            Date startTime = DateUtils.parseDate(startTimeStr, yyyyMMddHHmm);
            Date endTime = DateUtils.parseDate(endTimeStr, yyyyMMddHHmm);
            logger.info("开始自动分配滑槽，分配时间为：{} ~ {}", startTimeStr, endTimeStr);
            msg.append(StringUtils.tpl("开始自动分配滑槽，分配时间为：{} ~ {}", startTimeStr, endTimeStr));
            List<FlightDynamic> flightDynamics = flightDynamicService.findSlideCoastUnDistDynamics(ImmutableMap.of("startTime", startTime, "endTime", endTime, "dsfn", BaseService.dataScopeFilterNew((User) conditions.get("currentUser"))));
            logger.info("共找到{}个未分配滑槽的航班信息", flightDynamics.size());
            msg.append(StringUtils.tpl("共找到{}个未分配滑槽的航班信息:", flightDynamics.size()));
            Map<String, Object> parameters = ImmutableMap.of("mock_dist", conditions.get("mock_dist"), "dist_type", "自动分配", "threshold_value", DictUtils.getDictValue("资源分配间隔值", "threshold_value", "33"));
            int counter = 1;
            for (FlightDynamic flightDynamic : flightDynamics) {
                logger.info("{}/{} - 航班号：{}, 机型：{}, id:{}", counter++, flightDynamics.size(), flightDynamic.getFlightNum(), flightDynamic.getAircraftTypeCode(), flightDynamic.getId());
                session.insert(flightDynamic);
                SlideCoastOccupyingInfo info = slideCoastOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
                if (info == null) {
                    logger.info("找不到对应的占用信息，忽略！");
                    continue;
                }
                info.setRandom(String.valueOf(conditions.get("random")));
                session.insert(info);
                session.startProcess("SlideCoastDistributionFlow", parameters);
                if (session.getParameter("success") != null && (Boolean) session.getParameter("success") == false)
                    msg.append(session.getParameter("message") + ";");
            }
            message.setMessage(msg.toString());
            message.setCode(1);
            resourceAutoDistCompletedMessageSender(ImmutableMap.of("startTime", startTime, "endTime", endTime, "resourceName", "滑槽"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("自动分配滑槽失败：{}", e.getMessage());
            msg.append(StringUtils.tpl("自动分配滑槽失败：{}", e.getMessage()));
            message.setMessage("自动分配滑槽失败：" + e.getMessage());
            message.setCode(0);
        } finally {
            logger.info("自动分配滑槽成功");
            ConcurrentClientsHolder.getSocket("/global/dynamics").emit("assignment_complete_message", "complete");
        }

        return message;
    }

    /**
     * 自动分配安检口
     */
    public Message distSecurityCheckinAuto(Map<String, Object> conditions) {
        Message message = new Message();
        StringBuffer msg = new StringBuffer();
        try {
            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession((KnowledgePackage) conditions.get("urule-package"));
            String startTimeStr = String.valueOf(conditions.get("startTime"));
            String endTimeStr = String.valueOf(conditions.get("endTime"));
            Date startTime = DateUtils.parseDate(startTimeStr, yyyyMMddHHmm);
            Date endTime = DateUtils.parseDate(endTimeStr, yyyyMMddHHmm);
            logger.info("开始自动分配安检口，分配时间为：{} ~ {}", startTimeStr, endTimeStr);
            msg.append(StringUtils.tpl("开始自动分配安检口，分配时间为：{} ~ {}", startTimeStr, endTimeStr));
            List<FlightDynamic> flightDynamics = flightDynamicService.findSecurityCheckinUnDistDynamics(ImmutableMap.of("startTime", startTime, "endTime", endTime, "dsfn", BaseService.dataScopeFilterNew((User) conditions.get("currentUser"))));
            logger.info("共找到{}个未分配安检口的航班信息", flightDynamics.size());
            msg.append(StringUtils.tpl("共找到{}个未分配安检口的航班信息:", flightDynamics.size()));

            int counter = 1;
            for (FlightDynamic flightDynamic : flightDynamics) {
                logger.info("{}/{} - 航班号：{}, 机型：{}, id:{}", counter++, flightDynamics.size(), flightDynamic.getFlightNum(), flightDynamic.getAircraftTypeCode(), flightDynamic.getId());
                SecurityCheckinOccupyingInfo info = securityCheckinOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
                if (info == null) {
                    logger.info("找不到对应的占用信息，忽略！");
                    continue;
                }
                info.setRandom(String.valueOf(conditions.get("random")));
                session.insert(info);
                session.insert(flightDynamic);
                session.startProcess("SecurityCheckinDistributionFlow");
                if (session.getParameter("success") != null && (Boolean) session.getParameter("success") == false)
                    msg.append(session.getParameter("message") + ";");
            }
            message.setMessage(msg.toString());
            message.setCode(1);

            resourceAutoDistCompletedMessageSender(ImmutableMap.of("startTime", startTime, "endTime", endTime, "resourceName", "安检口"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("自动分配安检口失败：{}", e.getMessage());
            message.setMessage("自动分配安检口失败：" + e.getMessage());
            message.setCode(0);
        } finally {
            logger.info("自动分配安检口成功");
            ConcurrentClientsHolder.getSocket("/global/dynamics").emit("assignment_complete_message", "complete");
        }

        return message;
    }

    /**
     * 自动分配候机厅
     */
    public Message distDepartureHallAuto(Map<String, Object> conditions) {
        Message message = new Message();
        StringBuffer msg = new StringBuffer();
        try {
            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession((KnowledgePackage) conditions.get("urule-package"));
            String startTimeStr = String.valueOf(conditions.get("startTime"));
            String endTimeStr = String.valueOf(conditions.get("endTime"));
            Date startTime = DateUtils.parseDate(startTimeStr, yyyyMMddHHmm);
            Date endTime = DateUtils.parseDate(endTimeStr, yyyyMMddHHmm);
            logger.info("开始自动分配候机厅，分配时间为：{} ~ {}", startTimeStr, endTimeStr);
            msg.append(StringUtils.tpl("开始自动分配候机厅，分配时间为：{} ~ {}", startTimeStr, endTimeStr));
            List<FlightDynamic> flightDynamics = flightDynamicService.findDepartureHallUnDistDynamics(ImmutableMap.of("startTime", startTime, "endTime", endTime, "dsfn", BaseService.dataScopeFilterNew((User) conditions.get("currentUser"))));
            logger.info("共找到{}个未分配候机厅的航班信息", flightDynamics.size());
            msg.append(StringUtils.tpl("共找到{}个未分配候机厅的航班信息:", flightDynamics.size()));
            int counter = 1;
            for (FlightDynamic flightDynamic : flightDynamics) {
                logger.info("{}/{} - 航班号：{}, 机型：{}, id:{}", counter++, flightDynamics.size(), flightDynamic.getFlightNum(), flightDynamic.getAircraftTypeCode(), flightDynamic.getId());
                session.insert(flightDynamic);
                DepartureHallOccupyingInfo info = departureHallOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
                if (info == null) {
                    logger.info("找不到对应的占用信息，忽略！");
                    continue;
                }
                info.setRandom(String.valueOf(conditions.get("random")));
                session.insert(info);
                session.startProcess("DepartureHallDistributionFlow");
                if (session.getParameter("success") != null && (Boolean) session.getParameter("success") == false)
                    msg.append(session.getParameter("message") + ";");
            }
            message.setMessage(msg.toString());
            message.setCode(1);
            resourceAutoDistCompletedMessageSender(ImmutableMap.of("startTime", startTime, "endTime", endTime, "resourceName", "候机厅"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("自动分配候机厅失败：{}", e.getMessage());
            msg.append(StringUtils.tpl("自动分配候机厅失败：{}", e.getMessage()));
            message.setMessage("自动分配候机厅失败：" + e.getMessage());
            message.setCode(0);
        } finally {
            logger.info("自动分配候机厅成功");
            ConcurrentClientsHolder.getSocket("/global/dynamics").emit("assignment_complete_message", "complete");
        }

        return message;
    }

    @Transactional
    public void clearResourceByPair(FlightPlanPair flightPlanPair) {
        try {
            if (StringUtils.isNotEmpty(flightPlanPair.getId())) {
                GateOccupyingInfo info = gateOccupyingInfoDao.getByFlightDynamicId(flightPlanPair.getId());
                info.setRandom(UUID.randomUUID().toString());
                gateOccupyingInfoDao.delete(info); // 清除机位占用信息
                flightPairProgressInfoDao.deleteByPairId(flightPlanPair.getId()); // 清除保障信息
                flightPlanPair.setPlaceNum("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到行李转盘占用时间
     *
     * @param resultEntity 航班动态
     * @return 开始时间，结束时间， 航班属性
     */
    @Transactional
    public Map<String, Object> getCarouselOccupyingTime(FlightDynamic resultEntity) {
        Date startDate = ResourceDistUtils.getClosestDate(false, resultEntity);
        String flightAttr = flightDynamicService.getTheFuckingFlightAttr(resultEntity);
        return ImmutableMap.of("start", startDate, "over", DateUtils.addMinutes(startDate, Integer.parseInt(DictUtils.getDictValue("行李转盘默认时长", "carousel_deftime", "50"))),
                "nature", flightAttr.equals("国内航班") ? "1" : flightAttr.equals("国际航班") ? "2" : "3");
    }

    /**
     * 生成行李转盘资源占用信息
     *
     * @param flightDynamic
     * @return
     */
    @Transactional
    public CarouselOccupyingInfo generateCarouselOccupiedEntity(FlightDynamic flightDynamic) {
        Map<String, Object> variables = getCarouselOccupyingTime(flightDynamic);
        CarouselOccupyingInfo occupyingInfo = new CarouselOccupyingInfo();
        occupyingInfo.setFlightDynamicNature(String.valueOf(variables.get("nature")));
        occupyingInfo.setExpectedCarouselNum(occupyingInfo.getFlightDynamicNature().equals("3") ? 2L : 1L);
        occupyingInfo.setExpectedStartTime((Date) variables.get("start"));
        occupyingInfo.setExpectedOverTime((Date) variables.get("over"));
        occupyingInfo.setFlightDynamicCode(StringUtils.trimToEmpty(flightDynamic.getFlightCompanyCode()).concat(StringUtils.trimToEmpty(flightDynamic.getFlightNum())));
        occupyingInfo.setFlightDynamicId(flightDynamic.getId());
        occupyingInfo.setId(UUID.randomUUID().toString());
        return occupyingInfo;
    }

    public List<CarouselOccupyingInfo> batchGenerateCarouselOccupiedEntity(List<FlightDynamic> flightDynamics) {
        List<CarouselOccupyingInfo> infos = Lists.newArrayList();
        try {
            flightDynamics.forEach(flightDynamic -> infos.add(generateCarouselOccupiedEntity(flightDynamic)));
            //System.out.println(1/0);
            commonBatchDao.batchInsertCarouselOCI(infos);
        } catch (Exception e) {
            logger.error("行李转盘占用计算错误！");
            //e.printStackTrace();
            throw e;
        }
        return infos;
    }

    /**
     * 得到到港门占用时间
     *
     * @param resultEntity 航班动态
     * @return 开始时间，结束时间， 航班属性
     */
    @Transactional
    public Map<String, Object> getArrivalGateOccupyingTime(FlightDynamic resultEntity) {
        Date startDate = ResourceDistUtils.getClosestDate(false, resultEntity);
        String flightAttr = flightDynamicService.getTheFuckingFlightAttr(resultEntity);
        return ImmutableMap.of("start", startDate, "over", DateUtils.addMinutes(startDate, Integer.parseInt(DictUtils.getDictValue("到港门默认时长", "arrival_gate_deftime", "50"))),
                "nature", flightAttr.equals("国内航班") ? "1" : flightAttr.equals("国际航班") ? "2" : "3");
    }

    /**
     * 生成到港门资源占用信息
     *
     * @param flightDynamic
     * @return
     */
    public ArrivalGateOccupyingInfo generateArrivalGateOccupiedEntity(FlightDynamic flightDynamic) {
        Map<String, Object> variables = getArrivalGateOccupyingTime(flightDynamic);
        ArrivalGateOccupyingInfo occupyingInfo = new ArrivalGateOccupyingInfo();
        occupyingInfo.setFlightDynamicNature(String.valueOf(variables.get("nature")));
        occupyingInfo.setExpectedArrivalGateNum(occupyingInfo.getFlightDynamicNature().equals("3") ? 2 : 1);
        occupyingInfo.setExpectedStartTime((Date) variables.get("start"));
        occupyingInfo.setExpectedOverTime((Date) variables.get("over"));
        occupyingInfo.setFlightDynamicCode(StringUtils.trimToEmpty(flightDynamic.getFlightCompanyCode()).concat(StringUtils.trimToEmpty(flightDynamic.getFlightNum())));
        occupyingInfo.setFlightDynamicId(flightDynamic.getId());
        occupyingInfo.setId(UUID.randomUUID().toString());
        return occupyingInfo;
    }

    public List<ArrivalGateOccupyingInfo> batchGenerateArrivalGateOccupiedEntity(List<FlightDynamic> flightDynamics) {
        List<ArrivalGateOccupyingInfo> infos = Lists.newArrayList();
        try {
            flightDynamics.forEach(flightDynamic -> infos.add(generateArrivalGateOccupiedEntity(flightDynamic)));
            commonBatchDao.batchInsertArrivalGateOCI(infos);
        } catch (Exception e) {
            logger.error("到港门占用计算错误！");
            //e.printStackTrace();
            throw e;
        }
        return infos;
    }

    /**
     * 得到登机口占用时间
     *
     * @param resultEntity 航班动态
     * @return 开始时间，结束时间， 航班属性
     */
    @Transactional
    public Map<String, Object> getBoardingGateOccupyingTime(FlightDynamic resultEntity) {
        Date overDate = ResourceDistUtils.getClosestDate(true, resultEntity);
        String flightAttr = flightDynamicService.getTheFuckingFlightAttr(resultEntity);
        return ImmutableMap.of("start", DateUtils.addMinutes(overDate, -Integer.parseInt(DictUtils.getDictValue("登机口默认提前时间", "boardingGate_beftime", "40"))),
                "over", overDate, "nature", flightAttr.equals("国内航班") ? "1" : flightAttr.equals("国际航班") ? "2" : "3");
    }

    /**
     * 生成登机口资源占用信息
     *
     * @param flightDynamic
     * @return
     */
    public BoardingGateOccupyingInfo generateBoardingGateOccupiedEntity(FlightDynamic flightDynamic) {
        Map<String, Object> variables = getBoardingGateOccupyingTime(flightDynamic);
        BoardingGateOccupyingInfo occupyingInfo = new BoardingGateOccupyingInfo();
        occupyingInfo.setFlightDynamicNature(String.valueOf(variables.get("nature")));
        occupyingInfo.setExpectedBoardingGateNum(occupyingInfo.getFlightDynamicNature().equals("3") ? 2 : 1);
        occupyingInfo.setExpectedStartTime((Date) variables.get("start"));
        occupyingInfo.setExpectedOverTime((Date) variables.get("over"));
        occupyingInfo.setFlightDynamicCode(StringUtils.trimToEmpty(flightDynamic.getFlightCompanyCode()).concat(StringUtils.trimToEmpty(flightDynamic.getFlightNum())));
        occupyingInfo.setFlightDynamicId(flightDynamic.getId());
        occupyingInfo.setId(UUID.randomUUID().toString());
        return occupyingInfo;
    }

    public List<BoardingGateOccupyingInfo> batchGenerateBoardingGateOccupiedEntity(List<FlightDynamic> flightDynamics) {
        List<BoardingGateOccupyingInfo> infos = Lists.newArrayList();
        try {
            flightDynamics.forEach(flightDynamic -> infos.add(generateBoardingGateOccupiedEntity(flightDynamic)));
            commonBatchDao.batchInsertBoardingGateOCI(infos);
        } catch (Exception e) {
            logger.error("登机口占用计算错误！");
            //e.printStackTrace();
            throw e;
        }
        return infos;
    }

    /**
     * 得到滑槽占用时间
     *
     * @param resultEntity 航班动态
     * @return 开始时间，结束时间， 航班属性
     */
    @Transactional
    public Map<String, Object> getSlideCoastOccupyingTime(FlightDynamic resultEntity) {
        Date overDate = ResourceDistUtils.getClosestDate(true, resultEntity);
        String flightAttr = flightDynamicService.getTheFuckingFlightAttr(resultEntity);
        return ImmutableMap.of("start", DateUtils.addMinutes(overDate, -Integer.parseInt(DictUtils.getDictValue("滑槽默认提前时间", "slideCoast_beftime", "40"))),
                "over", overDate, "nature", flightAttr.equals("国内航班") ? "1" : flightAttr.equals("国际航班") ? "2" : "3");
    }

    /**
     * 生成滑槽资源占用信息
     *
     * @param flightDynamic
     * @return
     */
    public SlideCoastOccupyingInfo generateSlideCoastOccupiedEntity(FlightDynamic flightDynamic) {
        Map<String, Object> variables = getSlideCoastOccupyingTime(flightDynamic);
        SlideCoastOccupyingInfo occupyingInfo = new SlideCoastOccupyingInfo();
        occupyingInfo.setFlightDynamicNature(String.valueOf(variables.get("nature")));
        occupyingInfo.setExpectedSlideCoastNum(occupyingInfo.getFlightDynamicNature().equals("3") ? 2 : 1);
        occupyingInfo.setExpectedStartTime((Date) variables.get("start"));
        occupyingInfo.setExpectedOverTime((Date) variables.get("over"));
        occupyingInfo.setFlightDynamicCode(StringUtils.trimToEmpty(flightDynamic.getFlightCompanyCode()).concat(StringUtils.trimToEmpty(flightDynamic.getFlightNum())));
        occupyingInfo.setFlightDynamicId(flightDynamic.getId());
        occupyingInfo.setId(UUID.randomUUID().toString());
        return occupyingInfo;
    }

    public List<SlideCoastOccupyingInfo> batchGenerateSlideCoastOccupiedEntity(List<FlightDynamic> flightDynamics) {
        List<SlideCoastOccupyingInfo> infos = Lists.newArrayList();
        try {
            flightDynamics.forEach(flightDynamic -> infos.add(generateSlideCoastOccupiedEntity(flightDynamic)));
            commonBatchDao.batchInsertSlideCoastOCI(infos);
        } catch (Exception e) {
            logger.error("滑槽占用计算错误！");
            //e.printStackTrace();
            throw e;
        }
        return infos;
    }

    /**
     * 得到值机柜台占用时间
     *
     * @param flightDynamic 航班动态
     * @return 开始时间，结束时间， 航班属性，预计占用资源数量
     */
    @Transactional
    public Map<String, Object> getCheckinCounterOccupyingTime(FlightDynamic flightDynamic, boolean... flag) {
        // 默认地开始及结束时间
        Integer defaultStartMinutes = null, defaultOverMinutes = null;
        CheckingCounterOccupyingInfo checkingCounterOccupyingInfo = new CheckingCounterOccupyingInfo();
        // 航班离开时间
        Date flightLeaveTime = ResourceDistUtils.getClosestDate(true, flightDynamic);

        try {
            KnowledgeService service = (KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
            KnowledgePackage knowledgePackage = (flag.length == 1 && flag[0]) ? service.getKnowledge("值机柜台/SYS_CHECKIN_COUNTER_OCI_CALC.PKG") : service.getKnowledge("值机柜台/CHECKIN_COUNTER_OCI_CALC.PKG");

            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);
            //logger.info(" 航班号：{}, 机型：{}, id:{}", flightDynamic.getFlightNum(), flightDynamic.getAircraftTypeCode(), flightDynamic.getId());
            session.insert(flightDynamic);
            session.insert(checkingCounterOccupyingInfo);
            session.fireRules();

            try {
                defaultStartMinutes = (Integer) session.getParameter("cc_bf_minutes");
                defaultOverMinutes = (Integer) session.getParameter("cc_af_minutes");
            } catch (Exception e) {
                defaultStartMinutes = Integer.parseInt(DictUtils.getDictValue("值机柜台开始提前分钟", "cc_bf_minutes", "100"));
                defaultOverMinutes = Integer.parseInt(DictUtils.getDictValue("值机柜台结束提前分钟", "cc_af_minutes", "40"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ImmutableMap.of("start", DateUtils.addMinutes(flightLeaveTime, -defaultStartMinutes),
                "over", DateUtils.addMinutes(flightLeaveTime, -defaultOverMinutes),
                "expectedNum", checkingCounterOccupyingInfo.getExpectedCheckingCounterNum(),
                "nature", checkingCounterOccupyingInfo.getFlightDynamicNature());
    }

    /**
     * 生成值机柜台资源占用信息 RestTemplate
     *
     * @param flightDynamic
     * @return
     */
    public CheckingCounterOccupyingInfo generateCheckinCounterOccupiedEntity(FlightDynamic flightDynamic, boolean... flag) {
        Map<String, Object> variables = getCheckinCounterOccupyingTime(flightDynamic, flag);
        CheckingCounterOccupyingInfo occupyingInfo = new CheckingCounterOccupyingInfo();
        occupyingInfo.setFlightDynamicNature(String.valueOf(variables.get("nature")));
        occupyingInfo.setExpectedCheckingCounterNum(occupyingInfo.getFlightDynamicNature().equals("3") ? 2 : 1);
        occupyingInfo.setExpectedStartTime((Date) variables.get("start"));
        occupyingInfo.setExpectedOverTime((Date) variables.get("over"));
        occupyingInfo.setFlightDynamicCode(StringUtils.trimToEmpty(flightDynamic.getFlightCompanyCode()).concat(StringUtils.trimToEmpty(flightDynamic.getFlightNum())));
        occupyingInfo.setFlightDynamicId(flightDynamic.getId());
        occupyingInfo.setId(UUID.randomUUID().toString());
        return occupyingInfo;
    }

    public List<CheckingCounterOccupyingInfo> batchGenerateCheckinCounterOccupiedEntity(List<FlightDynamic> flightDynamics) {
        List<CheckingCounterOccupyingInfo> infos = Lists.newArrayList();
        try {
            flightDynamics.forEach(flightDynamic -> infos.add(generateCheckinCounterOccupiedEntity(flightDynamic)));
            commonBatchDao.batchInsertCheckingCounterOCI(infos);
        } catch (Exception e) {
            logger.error("值机柜台占用计算错误！将重新按默认配置计算。。。");
            //e.printStackTrace();
            //重新计算 wjp_2017年8月15日18时28分
            if (infos.size() > 0) {
                Map<String, List> ociD = new HashMap<>();
                ociD.put("值机柜台", infos);
                commonBatchDao.batchDelete(ociD, null);
            }

            try {
                flightDynamics.forEach(flightDynamic -> infos.add(generateCheckinCounterOccupiedEntity(flightDynamic, true)));
                commonBatchDao.batchInsertCheckingCounterOCI(infos);
            } catch (Exception e1) {
                logger.error("值机柜台按默认配置计算出误！请通知管理员!详情{}", e1.toString() + "异常：" + e1.getMessage());
                //e1.printStackTrace();
            }
        }
        return infos;
    }

    /**
     * 得到安检口占用时间
     *
     * @param resultEntity 航班动态
     * @return 开始时间，结束时间， 航班属性，预计占用资源数量
     */
    @Transactional
    public Map<String, Object> getSecurityCheckinOccupyingTime(FlightDynamic resultEntity, boolean... flag) {
        // 默认地开始及结束时间
        Integer defaultStartMinutes = null, defaultOverMinutes = null;
        SecurityCheckinOccupyingInfo securityCheckinOccupyingInfo = new SecurityCheckinOccupyingInfo();
        // 航班离开时间
        Date flightLeaveTime = ResourceDistUtils.getClosestDate(true, resultEntity);

        try {
            KnowledgeService service = (KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
            KnowledgePackage knowledgePackage = (flag.length == 1 && flag[0]) ? service.getKnowledge("安检口/SYS_SECURITY_CHECKIN_OCI_CALC.PKG") : service.getKnowledge("安检口/SECURITY_CHECKIN_OCI_CALC.PKG");
            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);
            //logger.info(" 航班号：{}, 机型：{}, id:{}", resultEntity.getFlightNum(), resultEntity.getAircraftTypeCode(), resultEntity.getId());
            session.insert(resultEntity);
            session.insert(securityCheckinOccupyingInfo);
            session.fireRules();

            try {
                defaultStartMinutes = (Integer) session.getParameter("sc_bf_minutes");
                defaultOverMinutes = (Integer) session.getParameter("sc_af_minutes");
            } catch (Exception e) {
                defaultStartMinutes = Integer.parseInt(DictUtils.getDictValue("安检口开始提前分钟", "sc_bf_minutes", "80"));
                defaultOverMinutes = Integer.parseInt(DictUtils.getDictValue("安检口结束提前分钟", "sc_af_minutes", "35"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ImmutableMap.of("start", DateUtils.addMinutes(flightLeaveTime, -defaultStartMinutes),
                "over", DateUtils.addMinutes(flightLeaveTime, -defaultOverMinutes),
                "expectedNum", securityCheckinOccupyingInfo.getExpectedSecurityCheckinNum(),
                "nature", securityCheckinOccupyingInfo.getFlightDynamicNature());
    }

    /**
     * 生成安检口资源占用信息
     *
     * @param flightDynamic
     * @return
     */
    public SecurityCheckinOccupyingInfo generateSecurityCheckinOccupiedEntity(FlightDynamic flightDynamic, boolean... flag) {
        Map<String, Object> variables = getSecurityCheckinOccupyingTime(flightDynamic, flag);
        SecurityCheckinOccupyingInfo occupyingInfo = new SecurityCheckinOccupyingInfo();
        occupyingInfo.setFlightDynamicNature(String.valueOf(variables.get("nature")));
        occupyingInfo.setExpectedSecurityCheckinNum(occupyingInfo.getFlightDynamicNature().equals("3") ? 2 : 1);
        occupyingInfo.setExpectedStartTime((Date) variables.get("start"));
        occupyingInfo.setExpectedOverTime((Date) variables.get("over"));
        occupyingInfo.setFlightDynamicCode(StringUtils.trimToEmpty(flightDynamic.getFlightCompanyCode()).concat(StringUtils.trimToEmpty(flightDynamic.getFlightNum())));
        occupyingInfo.setFlightDynamicId(flightDynamic.getId());
        occupyingInfo.setId(UUID.randomUUID().toString());
        return occupyingInfo;
    }

    public List<SecurityCheckinOccupyingInfo> batchGenerateSecurityCheckinOccupyingInfos(List<FlightDynamic> flightDynamics) {
        List<SecurityCheckinOccupyingInfo> infoList = Lists.newArrayList();
        try {
            flightDynamics.forEach(flightDynamic -> infoList.add(generateSecurityCheckinOccupiedEntity(flightDynamic)));
            commonBatchDao.batchInsertSecurityCheckinOCI(infoList);
        } catch (Exception e) {
            logger.error("安检口占用计算错误！将重新按默认配置计算。。。");
            //e.printStackTrace();
            //重新计算 wjp_2017年8月15日18时28分
            if (infoList.size() > 0) {
                Map<String, List> ociD = new HashMap<>();
                ociD.put("安检口", infoList);
                commonBatchDao.batchDelete(ociD, null);
            }

            try {
                flightDynamics.forEach(flightDynamic -> infoList.add(generateSecurityCheckinOccupiedEntity(flightDynamic, true)));
                commonBatchDao.batchInsertSecurityCheckinOCI(infoList);
            } catch (Exception e1) {
                logger.error("安检口按默认配置计算出误！请通知管理员!详情{}", e1.toString() + "异常：" + e1.getMessage());
                //e1.printStackTrace();
            }
            //throw e;
        }
        return infoList;
    }

    /**
     * 得到候机厅占用时间
     *
     * @param flightDynamic 航班动态
     * @return 开始时间，结束时间， 航班属性，预计占用资源数量
     */
    @Transactional
    public Map<String, Object> getDepartureHallOccupyingTime(FlightDynamic flightDynamic, boolean... flag) {
        // 默认地开始及结束时间
        Integer defaultStartMinutes = null, defaultOverMinutes = null;
        DepartureHallOccupyingInfo departureHallOccupyingInfo = new DepartureHallOccupyingInfo();
        // 航班离开时间
        Date flightLeaveTime = ResourceDistUtils.getClosestDate(true, flightDynamic);

        try {
            KnowledgeService service = (KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
            KnowledgePackage knowledgePackage = (flag.length == 1 && flag[0]) ? service.getKnowledge("候机厅/SYS_DEPARTURE_HALL_OCI_CALC.PKG") : service.getKnowledge("候机厅/SYS_DEPARTURE_HALL_OCI_CALC.PKG");
            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);
            //logger.info(" 航班号：{}, 机型：{}, id:{}", flightDynamic.getFlightNum(), flightDynamic.getAircraftTypeCode(), flightDynamic.getId());
            session.insert(flightDynamic);
            session.insert(departureHallOccupyingInfo);
            session.fireRules();

            try {
                defaultStartMinutes = (Integer) session.getParameter("dh_bf_minutes");
                defaultOverMinutes = (Integer) session.getParameter("dh_af_minutes");
            } catch (Exception e) {
                defaultStartMinutes = Integer.parseInt(DictUtils.getDictValue("候机厅开始提前分钟", "dh_bf_minutes", "60"));
                defaultOverMinutes = Integer.parseInt(DictUtils.getDictValue("候机厅结束提前分钟", "dh_af_minutes", "15"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ImmutableMap.of("start", DateUtils.addMinutes(flightLeaveTime, -defaultStartMinutes),
                "over", DateUtils.addMinutes(flightLeaveTime, -defaultOverMinutes),
                "expectedNum", departureHallOccupyingInfo.getExpectedDepartureHallNum(),
                "nature", departureHallOccupyingInfo.getFlightDynamicNature());
    }

    /**
     * 生成候机厅资源占用信息
     *
     * @param flightDynamic
     * @return
     */
    public DepartureHallOccupyingInfo generateDepartureHallOccupiedEntity(FlightDynamic flightDynamic, boolean... flag) {
        Map<String, Object> variables = getDepartureHallOccupyingTime(flightDynamic, flag);
        DepartureHallOccupyingInfo occupyingInfo = new DepartureHallOccupyingInfo();
        occupyingInfo.setFlightDynamicNature(String.valueOf(variables.get("nature")));
        occupyingInfo.setExpectedDepartureHallNum(occupyingInfo.getFlightDynamicNature().equals("3") ? 2 : 1);
        occupyingInfo.setExpectedStartTime((Date) variables.get("start"));
        occupyingInfo.setExpectedOverTime((Date) variables.get("over"));
        occupyingInfo.setFlightDynamicCode(StringUtils.trimToEmpty(flightDynamic.getFlightCompanyCode()).concat(StringUtils.trimToEmpty(flightDynamic.getFlightNum())));
        occupyingInfo.setFlightDynamicId(flightDynamic.getId());
        occupyingInfo.setId(UUID.randomUUID().toString());
        return occupyingInfo;
    }

    public List<DepartureHallOccupyingInfo> batchGenerateDepartureHallOccupiedEntity(List<FlightDynamic> flightDynamics) {
        List<DepartureHallOccupyingInfo> infos = Lists.newArrayList();
        try {
            flightDynamics.forEach(flightDynamic -> infos.add(generateDepartureHallOccupiedEntity(flightDynamic)));
            commonBatchDao.batchInsertDepartureHallOCI(infos);
        } catch (Exception e) {
            logger.error("候机厅占用计算错误！将重新按默认配置计算。。。");
            //e.printStackTrace();
            //重新计算 wjp_2017年8月15日18时28分
            if (infos.size() > 0) {
                Map<String, List> ociD = new HashMap<>();
                ociD.put("候机厅", infos);
                commonBatchDao.batchDelete(ociD, null);
            }

            try {
                flightDynamics.forEach(flightDynamic -> infos.add(generateDepartureHallOccupiedEntity(flightDynamic, true)));
                commonBatchDao.batchInsertDepartureHallOCI(infos);
            } catch (Exception e1) {
                logger.error("候机厅按默认配置计算出误！请通知管理员!详情{}", e1.toString() + "异常：" + e1.getMessage());
                //e1.printStackTrace();
            }
            //throw e;
        }
        return infos;
    }

    public List<GateOccupyingInfo> batchGenerateGateOccupiedEntity(List<FlightPlanPair> flightPlanPairs) {
        List<GateOccupyingInfo> infos = Lists.newArrayList();
        try {
            flightPlanPairs.forEach(flightPlanPair -> {
                GateOccupyingInfo oci = generateGateOccupiedEntity(flightPlanPair);
                handleSingleOutPair(flightPlanPair, oci);
                infos.add(oci);
            });
            commonBatchDao.batchInsertGateOCI(infos);
        } catch (Exception e) {
            logger.error("机位占用计算错误！将重新按默认配置计算。。。");
            //e.printStackTrace();
            //重新计算 wjp_2017年8月15日18时28分
            if (infos.size() > 0) {
                Map<String, List> ociD = new HashMap<>();
                ociD.put("机位", infos);
                commonBatchDao.batchDelete(ociD, null);
            }

            try {
                flightPlanPairs.forEach(flightPlanPair -> {
                    GateOccupyingInfo oci = generateGateOccupiedEntity(flightPlanPair, true);
                    handleSingleOutPair(flightPlanPair, oci);
                    infos.add(oci);
                });
                commonBatchDao.batchInsertGateOCI(infos);
            } catch (Exception e1) {
                logger.error("机位按默认配置计算出误！请通知管理员!详情{}", e1.toString() + "异常：" + e1.getMessage());
                //e1.printStackTrace();
            }
            //throw e;
        }
        return infos;
    }

    /**
     * 生成机位占用信息
     *
     * @param flightPlanPair
     */
    public GateOccupyingInfo generateGateOccupiedEntity(FlightPlanPair flightPlanPair, boolean... flag) {  // AMS - FlightPairWrapper
        GateOccupyingInfo occupyingInfo = gateOccupyingInfoDao.getByFlightDynamicId(flightPlanPair.getId());
        if (null == occupyingInfo) {
            occupyingInfo = new GateOccupyingInfo();
            occupyingInfo.setId(UUID.randomUUID().toString());
        }
        Map<String, Object> variables = ResourceDistUtils.calcOccupyingTimeByPair(flightPlanPair);
        occupyingInfo.setFlightDynamicId(flightPlanPair.getId());
        occupyingInfo.setStartTime((Date) variables.get("start"));
        occupyingInfo.setOverTime((Date) variables.get("over"));

        try {
            KnowledgeService service = (KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
            KnowledgePackage knowledgePackage = (flag.length == 1 && flag[0]) ? service.getKnowledge("机位/SYS_AIRCRAFT_STAND_OCI_CALC.PKG") : service.getKnowledge("机位/AIRCRAFT_STAND_OCI_CALC.PKG");
            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);
            session.insert(flightPlanPair);
            session.insert(occupyingInfo);
            session.fireRules();
            if (null == occupyingInfo.getExpectedGateNum()) occupyingInfo.setExpectedGateNum(1L);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            occupyingInfo.setExpectedGateNum(1L);
        }

        return occupyingInfo;
    }

    @Transactional
    public GateOccupyingInfo saveGateoccupiedEntity(FlightPlanPair flightPlanPair) {
        GateOccupyingInfo gateOccupyingInfo = generateGateOccupiedEntity(flightPlanPair);
        gateOccupyingInfo.setIsNewRecord(true);
        handleSingleOutPair(flightPlanPair, gateOccupyingInfo);
        gateOccupyingInfo.setRandom(UUID.randomUUID().toString());
        gateOccupyingInfoService.save(gateOccupyingInfo);
        return gateOccupyingInfo;
    }

    private void handleSingleOutPair(FlightPlanPair pair, GateOccupyingInfo oci) {
        if (StringUtils.isBlank(pair.getFlightDynimicId()) && StringUtils.isNotBlank(pair.getFlightDynimicId2())
                && StringUtils.isNotBlank(pair.getPlaceNum())) { // 单出
            oci.setActualGateNum(pair.getPlaceNum());
        }
    }

    /**
     * 重新计算配对信息机位的占用时间并更新到数据库
     *
     * @param pair
     * @return 是否被占用
     */
    @Transactional
    public boolean calcPairAircraftStanOccupyingTime(FlightPlanPair pair) {
        boolean isOccupied = false;
        Map<String, Object> results = ResourceDistUtils.calcOccupyingTimeByPair(pair);

        Date startDate = (Date) results.get("start"), overDate = (Date) results.get("over");

        if (startDate == null || overDate == null) return false;
        Integer thresholdValue = 15;

        try {
            thresholdValue = Integer.parseInt(DictUtils.getDictValue("资源分配间隔值", "threshold_value", "15"));
        } catch (NumberFormatException e) {

        }

        if (StringUtils.isNotEmpty(pair.getPlaceNum())) {
            // 验证当前机位在更新占用时间之后是否冲突
            List<String> occupiedGateList = flightDynamicDao.findOccupiedGates(
                    ImmutableMap.of("start", "'" + DateUtils.formatDateTime(DateUtils.addMinutes(startDate, -thresholdValue)) + "'",
                            "over", "'" + DateUtils.formatDateTime(DateUtils.addMinutes(overDate, thresholdValue)) + "'",
                            "flightDynamic", pair.getId()
                    ));

            for (String placeNum : Splitter.on(",").splitToList(pair.getPlaceNum())) {
                if (occupiedGateList.contains(placeNum)) {
                    isOccupied = true;
                    break;
                }
            }
        }

        // 将最新的占用数据添加到数据库
        GateOccupyingInfo gateOccupyingInfo = gateOccupyingInfoDao.getByFlightDynamicId(pair.getId());
        if (gateOccupyingInfo != null) {
            gateOccupyingInfo.setStartTime(startDate);
            gateOccupyingInfo.setOverTime(overDate);
            gateOccupyingInfo.setRandom(UUID.randomUUID().toString());
            gateOccupyingInfoDao.update(gateOccupyingInfo);
        }

        return isOccupied;
    }

    /**
     * 更新李转盘占用时间
     *
     * @param flightDynamic
     */
    @Transactional
    public Message updateTimeCarousel(FlightDynamic flightDynamic) {
        Message message = new Message();
        try {
            Map<String, Object> vars = getCarouselOccupyingTime(flightDynamic);
            CarouselOccupyingInfo info = carouselOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
            if (info != null) {
                info.setExpectedStartTime((Date) vars.get("start"));
                info.setExpectedOverTime((Date) vars.get("over"));
                info.setRandom(flightDynamic.getRandom());
                carouselOccupyingInfoDao.update(info);
            }
            message.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage("更新李转盘占用时间失败：" + e.getCause().getMessage());
        }
        return message;
    }

    /**
     * 更新安检口占用时间
     *
     * @param flightDynamic
     */
    @Transactional
    public Message updateTimeSecurityChedkin(FlightDynamic flightDynamic) {
        Message message = new Message();
        try {
            Map<String, Object> vars = getSecurityCheckinOccupyingTime(flightDynamic);
            SecurityCheckinOccupyingInfo info = securityCheckinOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
            if (info != null) {
                info.setExpectedStartTime((Date) vars.get("start"));
                info.setExpectedOverTime((Date) vars.get("over"));
                info.setRandom(flightDynamic.getRandom());
                securityCheckinOccupyingInfoDao.update(info);
            }
            message.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("更新安检口占用时间失败：" + e.getCause().getMessage());
        }
        return message;
    }

    /**
     * 更新候机厅占用时间
     *
     * @param flightDynamic
     */
    @Transactional
    public Message updateTimeDepartureHall(FlightDynamic flightDynamic) {
        Message message = new Message();
        try {
            Map<String, Object> vars = getDepartureHallOccupyingTime(flightDynamic);
            DepartureHallOccupyingInfo info = departureHallOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
            if (info != null) {
                info.setExpectedStartTime((Date) vars.get("start"));
                info.setExpectedOverTime((Date) vars.get("over"));
                info.setRandom(flightDynamic.getRandom());
                departureHallOccupyingInfoDao.update(info);
            }
            message.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage("更新候机厅占用时间失败：" + e.getCause().getMessage());
        }
        return message;
    }

    /**
     * 更新登机口占用时间
     *
     * @param flightDynamic
     */
    @Transactional
    public Message updateTimeBoardingGate(FlightDynamic flightDynamic) {
        Message message = new Message();
        try {
            Map<String, Object> vars = getBoardingGateOccupyingTime(flightDynamic);
            BoardingGateOccupyingInfo info = boardingGateOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
            if (info != null) {
                info.setExpectedStartTime((Date) vars.get("start"));
                info.setExpectedOverTime((Date) vars.get("over"));
                info.setRandom(flightDynamic.getRandom());
                boardingGateOccupyingInfoDao.update(info);
            }
            message.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage("更新登机口占用时间失败：" + e.getCause().getMessage());
        }
        return message;
    }

    /**
     * 更新滑槽占用时间
     *
     * @param flightDynamic
     */
    @Transactional
    public Message updateTimeSlideCoast(FlightDynamic flightDynamic) {
        Message message = new Message();
        try {
            Map<String, Object> vars = getSlideCoastOccupyingTime(flightDynamic);
            SlideCoastOccupyingInfo info = slideCoastOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
            if (info != null) {
                info.setExpectedStartTime((Date) vars.get("start"));
                info.setExpectedOverTime((Date) vars.get("over"));
                info.setRandom(flightDynamic.getRandom());
                slideCoastOccupyingInfoDao.update(info);
            }
            message.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage("更新滑槽占用时间失败：" + e.getCause().getMessage());
        }
        return message;
    }

    /**
     * 更新值机柜台占用时间
     *
     * @param flightDynamic
     */
    @Transactional
    public Message updateTimeCheckinCounter(FlightDynamic flightDynamic) {
        Message message = new Message();
        try {
            Map<String, Object> vars = getCheckinCounterOccupyingTime(flightDynamic);
            CheckingCounterOccupyingInfo info = checkingCounterOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
            if (info != null) {
                info.setExpectedStartTime((Date) vars.get("start"));
                info.setExpectedOverTime((Date) vars.get("over"));
                info.setRandom(flightDynamic.getRandom());
                checkingCounterOccupyingInfoDao.update(info);
            }
            message.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage("更新值机柜台占用时间失败：" + e.getCause().getMessage());
        }
        return message;
    }

    /**
     * 更新到港门占用时间
     *
     * @param flightDynamic
     */
    @Transactional
    public Message updateTimeArrivalGate(FlightDynamic flightDynamic) {
        Message message = new Message();
        try {
            Map<String, Object> vars = getArrivalGateOccupyingTime(flightDynamic);
            ArrivalGateOccupyingInfo info = arrivalGateOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
            if (info != null) {
                info.setExpectedStartTime((Date) vars.get("start"));
                info.setExpectedOverTime((Date) vars.get("over"));
                info.setRandom(flightDynamic.getRandom());
                arrivalGateOccupyingInfoDao.update(info);
            }
            message.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage("更新到港门占用时间失败：" + e.getCause().getMessage());
        }
        return message;
    }

    /**
     * 更新机位占用时间
     *
     * @param pair
     * @param flightDynamic
     */
    @Transactional
    public Message updateTimeGate(FlightPlanPair pair, FlightDynamic flightDynamic) {
        Message message = new Message();
        try {
            Map<String, Object> variables = ResourceDistUtils.calcOccupyingTimeByPair(pair);
            GateOccupyingInfo info = gateOccupyingInfoDao.getByFlightDynamicId(pair.getId());
            if (info != null) {
                info.setStartTime((Date) variables.get("start"));
                info.setOverTime((Date) variables.get("over"));
                info.setRandom(pair.getRandom());
                gateOccupyingInfoDao.update(info);
            }
            message.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage("更新机位占用时间失败：" + e.getCause().getMessage());
        }
        return message;
    }

    private void resourceAutoDistCompletedMessageSender(Map<String, Object> map) {
        Map<String, Object> params = ImmutableMap.of(
                "occur", System.currentTimeMillis(),
                "message", String.format(REALTIME_DYNAMIC_MESSAGE_CONFS.getProperty("RESOURCE_AUTO_DIST_COMPLETED"),
                        DateUtils.formatDate((Date) map.get("startTime"), yyyyMMddHHmm), // 开始时间
                        DateUtils.formatDate((Date) map.get("endTime"), yyyyMMddHHmm), // 结束时间
                        String.valueOf(map.get("resourceName"))) // 资源名称
        );
        try {
            ConcurrentClientsHolder.getSocket("/dynamic_realtime_message")
                    .emit("dynamic_message_dispatcher", OBJECT_MAPPER.writeValueAsString(params));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取航班属性code
    private String getNature(FlightDynamic flightDynamic) {
        if (StringUtils.isBlank(flightDynamic.getFlightAttrName())) return "1";
        return flightDynamic.getFlightAttrName().indexOf("国内") != -1 ? "1" : flightDynamic.getFlightAttrName().indexOf("国际") != -1 ? "2" : "3";
    }

    public Message updateAttrCarousel(FlightDynamic flightDynamic) {
        Message message = new Message();
        try {
            CarouselOccupyingInfo info = carouselOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
            if (info != null) {
                info.setFlightDynamicNature(getNature(flightDynamic));
                carouselOccupyingInfoDao.update(info);
            }
            message.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage("更新李转盘占用时间失败：" + e.getCause().getMessage());
        }
        return message;
    }

    public Message updateAttrArrivalGate(FlightDynamic flightDynamic) {
        Message message = new Message();
        try {
            ArrivalGateOccupyingInfo info = arrivalGateOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
            if (info != null) {
                info.setFlightDynamicNature(getNature(flightDynamic));
                arrivalGateOccupyingInfoDao.update(info);
            }
            message.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage("更新李转盘占用时间失败：" + e.getCause().getMessage());
        }
        return message;
    }

    public Message updateAttrCheckinCounter(FlightDynamic flightDynamic) {
        Message message = new Message();
        try {
            CheckingCounterOccupyingInfo info = checkingCounterOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
            if (info != null) {
                info.setFlightDynamicNature(getNature(flightDynamic));
                checkingCounterOccupyingInfoDao.update(info);
            }
            message.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage("更新李转盘占用时间失败：" + e.getCause().getMessage());
        }
        return message;
    }

    public Message updateAttrSlideCoast(FlightDynamic flightDynamic) {
        Message message = new Message();
        try {
            SlideCoastOccupyingInfo info = slideCoastOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
            if (info != null) {
                info.setFlightDynamicNature(getNature(flightDynamic));
                slideCoastOccupyingInfoDao.update(info);
            }
            message.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage("更新李转盘占用时间失败：" + e.getCause().getMessage());
        }
        return message;
    }

    public Message updateAttrBoardingGate(FlightDynamic flightDynamic) {
        Message message = new Message();
        try {
            BoardingGateOccupyingInfo info = boardingGateOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
            if (info != null) {
                info.setFlightDynamicNature(getNature(flightDynamic));
                boardingGateOccupyingInfoDao.update(info);
            }
            message.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage("更新李转盘占用时间失败：" + e.getCause().getMessage());
        }
        return message;
    }

    public Message updateAttrDepartureHall(FlightDynamic flightDynamic) {
        Message message = new Message();
        try {
            DepartureHallOccupyingInfo info = departureHallOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
            if (info != null) {
                info.setFlightDynamicNature(getNature(flightDynamic));
                departureHallOccupyingInfoDao.update(info);
            }
            message.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage("更新李转盘占用时间失败：" + e.getCause().getMessage());
        }
        return message;
    }

    public Message updateAttrSecurityChedkin(FlightDynamic flightDynamic) {
        Message message = new Message();
        try {
            SecurityCheckinOccupyingInfo info = securityCheckinOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
            if (info != null) {
                info.setFlightDynamicNature(getNature(flightDynamic));
                securityCheckinOccupyingInfoDao.update(info);
            }
            message.setCode(1);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage("更新李转盘占用时间失败：" + e.getCause().getMessage());
        }
        return message;
    }

    public String getMaxPlanDate() {
        return flightDynamicDao.getMaxPlanDate();
    }

    public String getMaxPlanDate4Mock(String infoId, String resourceType) {
        return flightDynamicDao.getMaxPlanDate4Mock(ImmutableMap.of("infoId", infoId, "resourceType", resourceType));
    }

    @Transactional
    public Message mockDistAircraftStand(ResourceMockDistInfo resourceMockDistInfo, List<FlightPlanPair> datas, KnowledgePackage aircraftStandPkg, Message message) {
        try {
            resourceMockDistInfoService.save(resourceMockDistInfo);
            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(aircraftStandPkg);

            Map<String, Object> mockDistParams = ImmutableMap.of("need_save", true, "mock_dist", true, "omit_oci", resourceMockDistInfo.getOmitOciDatas(), "dist_type", "自动分配", "threshold_value", DictUtils.getDictValue("资源分配间隔值", "threshold_value", "15"));
            for (FlightPlanPair flightPlanPair : datas) {
                session.insert(flightPlanPair);
                GateOccupyingInfo info = gateOccupyingInfoDao.getByFlightDynamicId(flightPlanPair.getId());
                if (info == null) {
                    logger.info("找不到对应的占用信息，忽略！");
                    continue;
                }

                session.insert(info);
                session.insert(resourceMockDistInfo);
                session.startProcess("AircraftDistributionFlow", mockDistParams);
            }

            message.setCode(1);
            message.setMessage("模拟分配完成！");

        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("模拟分配失败：" + e.getCause().getMessage());
            throw e;
        }
        return message;
    }

    @Transactional
    public Message mockDistArrivalGate(ResourceMockDistInfo resourceMockDistInfo, List<FlightDynamic> datas, KnowledgePackage arrivalGatePkg, Message message) {
        try {
            resourceMockDistInfoService.save(resourceMockDistInfo);
            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(arrivalGatePkg);

            Map<String, Object> mockDistParams = ImmutableMap.of("mock_dist", true, "omit_oci", resourceMockDistInfo.getOmitOciDatas(), "dist_type", "自动分配", "threshold_value", DictUtils.getDictValue("资源分配间隔值", "threshold_value", "15"));
            for (FlightDynamic flightDynamic : datas) {
                session.insert(flightDynamic);
                ArrivalGateOccupyingInfo info = arrivalGateOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
                if (info == null) {
                    logger.info("找不到对应的占用信息，忽略！");
                    continue;
                }
                session.insert(info);
                session.insert(resourceMockDistInfo);
                session.startProcess("ArrivalGateDistributionFlow", mockDistParams);
            }

            message.setCode(1);
            message.setMessage("模拟分配完成！");
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("模拟分配失败：" + e.getCause().getMessage());
            throw e;
        }
        return message;
    }

    @Transactional
    public Message mockDistBoardingGate(ResourceMockDistInfo resourceMockDistInfo, List<FlightDynamic> datas, KnowledgePackage boardingGatePkg, Message message) {
        try {
            resourceMockDistInfoService.save(resourceMockDistInfo);
            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(boardingGatePkg);

            Map<String, Object> mockDistParams = ImmutableMap.of("mock_dist", true, "omit_oci", resourceMockDistInfo.getOmitOciDatas(), "dist_type", "自动分配", "threshold_value", DictUtils.getDictValue("资源分配间隔值", "threshold_value", "15"));
            for (FlightDynamic flightDynamic : datas) {
                session.insert(flightDynamic);
                BoardingGateOccupyingInfo info = boardingGateOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
                if (info == null) {
                    logger.info("找不到对应的占用信息，忽略！");
                    continue;
                }
                session.insert(info);
                session.insert(resourceMockDistInfo);
                session.startProcess("BoardingGateDistributionFlow", mockDistParams);
            }

            message.setCode(1);
            message.setMessage("模拟分配完成！");
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("模拟分配失败：" + e.getCause().getMessage());
            throw e;
        }
        return message;
    }

    @Transactional
    public Message mockDistCarousel(ResourceMockDistInfo resourceMockDistInfo, List<FlightDynamic> datas, KnowledgePackage carouselPkg, Message message) {
        try {
            resourceMockDistInfoService.save(resourceMockDistInfo);
            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(carouselPkg);

            Map<String, Object> mockDistParams = ImmutableMap.of("mock_dist", true, "omit_oci", resourceMockDistInfo.getOmitOciDatas(), "dist_type", "自动分配", "threshold_value", DictUtils.getDictValue("资源分配间隔值", "threshold_value", "15"));
            for (FlightDynamic flightDynamic : datas) {
                session.insert(flightDynamic);
                CarouselOccupyingInfo info = carouselOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
                if (info == null) {
                    logger.info("找不到对应的占用信息，忽略！");
                    continue;
                }
                session.insert(info);
                session.insert(resourceMockDistInfo);
                session.startProcess("CarouselDistributionFlow", mockDistParams);
            }

            message.setCode(1);
            message.setMessage("模拟分配完成！");
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("模拟分配失败：" + e.getCause().getMessage());
            throw e;
        }
        return message;
    }

    @Transactional
    public Message mockDistSlideCoast(ResourceMockDistInfo resourceMockDistInfo, List<FlightDynamic> mockDatas, KnowledgePackage slideCoastPkg, Message message) {
        try {
            resourceMockDistInfoService.save(resourceMockDistInfo);
            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(slideCoastPkg);

            Map<String, Object> mockDistParams = ImmutableMap.of("mock_dist", true, "omit_oci", resourceMockDistInfo.getOmitOciDatas(), "dist_type", "自动分配", "threshold_value", DictUtils.getDictValue("资源分配间隔值", "threshold_value", "15"));
            for (FlightDynamic flightDynamic : mockDatas) {
                session.insert(flightDynamic);
                SlideCoastOccupyingInfo info = slideCoastOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
                if (info == null) {
                    logger.info("找不到对应的占用信息，忽略！");
                    continue;
                }
                session.insert(info);
                session.insert(resourceMockDistInfo);
                session.startProcess("SlideCoastDistributionFlow", mockDistParams);
            }

            message.setCode(1);
            message.setMessage("模拟分配完成！");
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("模拟分配失败：" + e.getCause().getMessage());
            throw e;
        }
        return message;
    }

    @Transactional
    public Message mockDistCheckinCounter(ResourceMockDistInfo resourceMockDistInfo, List<FlightDynamic> mockDatas, KnowledgePackage checkinCounterPkg, Message message) {
        try {
            resourceMockDistInfoService.save(resourceMockDistInfo);
            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(checkinCounterPkg);

            Map<String, Object> mockDistParams = ImmutableMap.of("mock_dist", true, "omit_oci", resourceMockDistInfo.getOmitOciDatas());
            for (FlightDynamic flightDynamic : mockDatas) {
                session.insert(flightDynamic);
                CheckingCounterOccupyingInfo info = checkingCounterOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
                if (info == null) {
                    logger.info("找不到对应的占用信息，忽略！");
                    continue;
                }
                session.insert(info);
                session.insert(resourceMockDistInfo);
                session.startProcess("CheckingCounterDistributionFlow", mockDistParams);
            }

            message.setCode(1);
            message.setMessage("模拟分配完成！");
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("模拟分配失败：" + e.getCause().getMessage());
            throw e;
        }
        return message;
    }

    @Transactional
    public Message mockDistSecurityCheckin(ResourceMockDistInfo resourceMockDistInfo, List<FlightDynamic> mockDatas, KnowledgePackage securityCheckinPkg, Message message) {
        try {
            resourceMockDistInfoService.save(resourceMockDistInfo);
            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(securityCheckinPkg);

            Map<String, Object> mockDistParams = ImmutableMap.of("mock_dist", true, "omit_oci", resourceMockDistInfo.getOmitOciDatas());
            for (FlightDynamic flightDynamic : mockDatas) {
                session.insert(flightDynamic);
                SecurityCheckinOccupyingInfo info = securityCheckinOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
                if (info == null) {
                    logger.info("找不到对应的占用信息，忽略！");
                    continue;
                }
                session.insert(info);
                session.insert(resourceMockDistInfo);
                session.startProcess("SecurityCheckinDistributionFlow", mockDistParams);
            }

            message.setCode(1);
            message.setMessage("模拟分配完成！");
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("模拟分配失败：" + e.getCause().getMessage());
            throw e;
        }
        return message;
    }

    @Transactional
    public Message mockDistDepartureHall(ResourceMockDistInfo resourceMockDistInfo, List<FlightDynamic> mockDatas, KnowledgePackage departureHallPkg, Message message) {
        try {
            resourceMockDistInfoService.save(resourceMockDistInfo);
            KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(departureHallPkg);

            Map<String, Object> mockDistParams = ImmutableMap.of("mock_dist", true, "omit_oci", resourceMockDistInfo.getOmitOciDatas());
            for (FlightDynamic flightDynamic : mockDatas) {
                session.insert(flightDynamic);
                DepartureHallOccupyingInfo info = departureHallOccupyingInfoDao.getByFlightDynamicId(flightDynamic.getId());
                if (info == null) {
                    logger.info("找不到对应的占用信息，忽略！");
                    continue;
                }
                session.insert(info);
                session.insert(resourceMockDistInfo);
                session.startProcess("DepartureHallDistributionFlow", mockDistParams);
            }

            message.setCode(1);
            message.setMessage("模拟分配完成！");
        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(0);
            message.setMessage("模拟分配失败：" + e.getCause().getMessage());
            throw e;
        }
        return message;
    }

    @Transactional
    @Check(operationName = "值机柜台手动分配", isOci = true, oci = CheckingCounterOccupyingInfo.class)
    public void checkingCounterManual(FlightDynamic flightDynamic, CheckingCounterOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        checkingCounterOccupyingInfoService.save(info);

        StringBuffer sb = new StringBuffer();
        sb.append("值机柜台手动分配成功！分配" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
        sb.append("值机柜台,").append(info.toString());
        message.setMsg(1, sb.toString());

        ConcurrentClientsHolder.getSocket("/global/dynamics").emit("assignment_complete_message", "complete");
    }

    @Transactional
    @Check(operationName = "安检口手动分配", isOci = true, oci = SecurityCheckinOccupyingInfo.class)
    public void securityCheckinManual(FlightDynamic flightDynamic, SecurityCheckinOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        securityCheckinOccupyingInfoService.save(info);

        StringBuffer sb = new StringBuffer();
        sb.append("安检口手动分配成功！分配" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
        sb.append("安检口,").append(info.toString());
        message.setMsg(1, sb.toString());

        ConcurrentClientsHolder.getSocket("/global/dynamics").emit("assignment_complete_message", "complete");
    }

    @Transactional
    @Check(operationName = "候机厅手动分配", isOci = true, oci = DepartureHallOccupyingInfo.class)
    public void departureHallManual(FlightDynamic flightDynamic, DepartureHallOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        departureHallOccupyingInfoService.save(info);

        StringBuffer sb = new StringBuffer();
        sb.append("候机厅手动分配成功！分配" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
        sb.append("候机厅,").append(info.toString());
        message.setMsg(1, sb.toString());

        ConcurrentClientsHolder.getSocket("/global/dynamics").emit("assignment_complete_message", "complete");
    }

    @Transactional
    @Check(operationName = "行李转盘手动分配", isOci = true, oci = CarouselOccupyingInfo.class)
    public void carouselManual(FlightDynamic flightDynamic, CarouselOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        carouselOccupyingInfoService.save(info);

        StringBuffer sb = new StringBuffer();
        sb.append("行李转盘手动分配成功！分配" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
        sb.append("行李转盘,").append(info.toString());
        message.setMsg(1, sb.toString());

    }

    @Transactional
    @Check(operationName = "到港门手动分配", isOci = true, oci = ArrivalGateOccupyingInfo.class)
    public void arrivalGateManual(FlightDynamic flightDynamic, ArrivalGateOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;

        arrivalGateOccupyingInfoService.save(info);

        StringBuffer sb = new StringBuffer();
        sb.append("到港门手动分配成功！分配" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
        sb.append("到港门,").append(info.toString());
        message.setMsg(1, sb.toString());

    }

    @Transactional
    @Check(operationName = "登机口手动分配", isOci = true, oci = BoardingGateOccupyingInfo.class)
    public void boardingGateManual(FlightDynamic flightDynamic, BoardingGateOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        boardingGateOccupyingInfoService.save(info);

        StringBuffer sb = new StringBuffer();
        sb.append("登机口手动分配成功！分配" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
        sb.append("登机口,").append(info.toString());
        message.setMsg(1, sb.toString());

    }

    @Transactional
    @Check(operationName = "滑槽手动分配", isOci = true, oci = SlideCoastOccupyingInfo.class)
    public void slideCoastManual(FlightDynamic flightDynamic, SlideCoastOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        slideCoastOccupyingInfoService.save(info);

        StringBuffer sb = new StringBuffer();
        sb.append("滑槽手动分配成功！分配" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
        sb.append("滑槽,").append(info.toString());
        message.setMsg(1, sb.toString());

    }

    @Transactional
    @Check(operationName = "机位手动分配", isOci = true, oci = GateOccupyingInfo.class)
    public void aircraftNumManual(FlightPlanPair flightPlanPair, GateOccupyingInfo info, Message message, String expectAircraftNum) {
        if (!isPair(flightPlanPair, message)) return;

        boolean changed = true; // 变更机位：指原来有机位，然后换了一个机位
        if (StringUtils.isEmpty(flightPlanPair.getPlaceNum())) changed = false; // 分机位：指原来没有机位
        String oldPlaceNumber = flightPlanPair.getPlaceNum2();

        flightPlanPair.setPlaceNum(expectAircraftNum);
        info.setActualGateNum(expectAircraftNum);
        info.setRandom(info.getRandom());
        //todo 获取机位时清理登机口 wjp_2017年9月16日16时36分 跟据需求变更
        String outFdId = flightPlanPair.getFlightDynimicId2();
        if (StringUtils.isNotBlank(outFdId)) {
            BoardingGateOccupyingInfo boardingGateOccupyingInfo = boardingGateOccupyingInfoDao.getByFlightDynamicId(outFdId);
            if (boardingGateOccupyingInfo != null && (StringUtils.isNotBlank(boardingGateOccupyingInfo.getInteBoardingGateCode()) ||
                    StringUtils.isNotBlank(boardingGateOccupyingInfo.getIntlBoardingGateCode()))) {
                boardingGateOccupyingInfo.setRandom(UUID.randomUUID().toString());
                //this.clearBoardingGateCode(flightDynamicService.get(outFdId), boardingGateOccupyingInfo, new Message());
                boardingGateOccupyingInfo.setInteBoardingGateCode(null);
                boardingGateOccupyingInfo.setIntlBoardingGateCode(null);
                boardingGateOccupyingInfoService.save(boardingGateOccupyingInfo);
                logger.info("手动分机位时清除登机口："+boardingGateOccupyingInfo.toString());
            }
        }

        gateOccupyingInfoService.save(info);
        flightPlanPairService.save(flightPlanPair);
        flightDynamicService.updateFlightDynamicInfoAfterAircraftDistributed(flightPlanPair);

        aircraftGateMessageSender(changed, flightPlanPair, expectAircraftNum, oldPlaceNumber);
        StringBuffer sb = new StringBuffer();
        sb.append("机位手动分配成功！分配" + LogUtils.msgPair(flightPlanPair) + " 的");
        sb.append("机位由").append(StringUtils.trimToEmpty(oldPlaceNumber)).append("号机位变更为");
        sb.append(StringUtils.trimToEmpty(flightPlanPair.getPlaceNum())).append("号机位");
        message.setMsg(1, sb.toString());

        //机位分配成功，判断是否是单出，并判断是否有同机号的第二天单出航班，有则更新相应机位 wjp_2017年9月27日17时11分 新需求
        try {
            if (flightPlanPair.getFlightDynimicId() == null) {
                FlightDynamic param = new FlightDynamic();
                Date tomorrowPlanDate = DateUtils.addDays(flightPlanPair.getPlanDate(), 1);
                param.setPlanDate(tomorrowPlanDate);
                param.setInoutTypeCode("C");
                param.setAircraftNum(flightPlanPair.getAircraftNum());
                List<FlightDynamic> tomorrowFDs = flightDynamicDao.findListSimple(param);
                if (!Collections3.isEmpty(tomorrowFDs)) {
                    if (tomorrowFDs.size() == 1) { //更新机号
                        FlightDynamic fd = tomorrowFDs.get(0);
                        FlightPlanPair pair = flightDynamicDao.getPairByDynamic(fd.getId());
                        pair.setPlaceNum(flightPlanPair.getPlaceNum());
                        pair.setRandom(info.getRandom());
                        GateOccupyingInfo info2 = gateOccupyingInfoService.getByFlightDynamicId(pair.getId());
                        info2.setRandom(UUID.randomUUID().toString());
                        if (info2 != null) {
                            this.aircraftNumManual(pair, info2, new Message(), flightPlanPair.getPlaceNum());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("手动换机位的单出，更新第二天单进航班的机位，出错！");
            //e.printStackTrace();
        }

        Map<String, Object> variables = new HashMap<>();
        variables.put("success", message.isSuccess());
        variables.put("code", 1);
        variables.put("message", message.getMessage());
        variables.put("result", expectAircraftNum);
        message.setResult(variables);
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

    @Transactional
    @Check(operationName = "取消机位", isOci = true, oci = GateOccupyingInfo.class)
    public void clearAircraftNum(FlightPlanPair flightPlanPair, GateOccupyingInfo info, Message message) {
        if (!isPair(flightPlanPair, message)) return;
        StringBuffer sb = new StringBuffer();
        sb.append("清除机位分配！" + LogUtils.msgPair(flightPlanPair) + " 的");
        sb.append("机位[").append(flightPlanPair.getPlaceNum()).append("]被清除！");

        flightPlanPair.setPlaceNum("");
        flightPlanPairService.save(flightPlanPair);
        //处理同步和配对对应的动态 wjp_2017年7月12日11时46分
        flightDynamicService.updateFlightDynamicInfoAfterAircraftDistributed(flightPlanPair);

        //todo 获取机位时清理登机口 wjp_2017年9月16日16时36分 跟据需求变更
        String outFdId = flightPlanPair.getFlightDynimicId2();
        if (StringUtils.isNotBlank(outFdId)) {
            BoardingGateOccupyingInfo boardingGateOccupyingInfo = boardingGateOccupyingInfoDao.getByFlightDynamicId(outFdId);
            if (boardingGateOccupyingInfo != null && (StringUtils.isNotBlank(boardingGateOccupyingInfo.getInteBoardingGateCode()) ||
                    StringUtils.isNotBlank(boardingGateOccupyingInfo.getIntlBoardingGateCode()))) {
                boardingGateOccupyingInfo.setRandom(UUID.randomUUID().toString());
                this.clearBoardingGateCode(flightDynamicService.get(outFdId), boardingGateOccupyingInfo, new Message());
            }
        }

        info.setActualGateNum("");
        info.setRandom(flightPlanPair.getRandom());
        gateOccupyingInfoService.save(info);

        Map<String, Object> resultMap = flightDynamicService.queryInOutTypeByPairId(flightPlanPair.getId());

        // 更新相应的flightDynamic信息
        String inoutType = String.valueOf(resultMap.get("inoutType"));

        if (StringUtils.inString(inoutType, "SI", "RELATE")) {
            if (resultMap.get("J") != null) {
                FlightDynamic flightDynamic = flightDynamicService.get(((FlightDynamic) resultMap.get("J")).getId());
                if (flightDynamic != null) {
                    flightDynamic.setPlaceNum("");
                    flightDynamic.setRandom(flightPlanPair.getRandom());
                    flightDynamicService.save(flightDynamic);
                }
            }
        }

        if (StringUtils.inString(inoutType, "SO", "RELATE")) {
            if (resultMap.get("C") != null) {
                FlightDynamic flightDynamic = flightDynamicService.get(((FlightDynamic) resultMap.get("C")).getId());
                if (flightDynamic != null) {
                    flightDynamic.setPlaceNum("");
                    flightDynamic.setRandom(flightPlanPair.getRandom());
                    flightDynamicService.save(flightDynamic);
                }
            }
        }
        message.setMsg(1, sb.toString());
    }

    @Transactional
    @Check(operationName = "取消候机厅", isOci = true, oci = DepartureHallOccupyingInfo.class)
    public void clearDepartureHall(FlightDynamic flightDynamic, DepartureHallOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        StringBuffer sb = new StringBuffer();
        sb.append("清除候机厅分配！" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
        sb.append("候机厅,").append(info.toString()).append("被清除！");

        info.setInteDepartureHallCode("");
        info.setInteActualStartTime(null);
        info.setInteActualOverTime(null);
        info.setIntlDepartureHallCode("");
        info.setIntlActualStartTime(null);
        info.setIntlActualOverTime(null);
        info.setActualDepartureHallNum(0);
        departureHallOccupyingInfoService.save(info);
        message.setMsg(1, sb.toString());
    }

    @Transactional
    @Check(operationName = "取消安检口", isOci = true, oci = SecurityCheckinOccupyingInfo.class)
    public void clearSecurityCheckin(FlightDynamic flightDynamic, SecurityCheckinOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        StringBuffer sb = new StringBuffer();
        sb.append("清除安检口分配！" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
        sb.append("安检口,").append(info.toString()).append("被清除！");

        info.setInteSecurityCheckinCode("");
        info.setInteActualStartTime(null);
        info.setInteActualOverTime(null);

        info.setIntlSecurityCheckinCode("");
        info.setIntlActualStartTime(null);
        info.setIntlActualOverTime(null);
        info.setActualSecurityCheckinNum(0);

        securityCheckinOccupyingInfoService.save(info);
        message.setMsg(1, sb.toString());
    }

    @Transactional
    @Check(operationName = "取消值机柜台", isOci = true, oci = CheckingCounterOccupyingInfo.class)
    public void clearCheckinCounter(FlightDynamic flightDynamic, CheckingCounterOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        StringBuffer sb = new StringBuffer();
        sb.append("清除值机柜台分配！" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
        sb.append("值机柜台,").append(info.toString()).append("被清除！");

        info.setInteCheckingCounterCode("");
        info.setInteActualStartTime(null);
        info.setInteActualOverTime(null);

        info.setIntlCheckingCounterCode("");
        info.setIntlActualStartTime(null);
        info.setIntlActualOverTime(null);
        info.setActualCheckingCounterNum(0);

        checkingCounterOccupyingInfoService.save(info);

        message.setMsg(1, sb.toString());
    }

    @Transactional
    @Check(operationName = "取消滑槽", isOci = true, oci = SlideCoastOccupyingInfo.class)
    public void clearSlideCoastCode(FlightDynamic flightDynamic, SlideCoastOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        StringBuffer sb = new StringBuffer();
        sb.append("清除滑槽分配！" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
        sb.append("滑槽,").append(info.toString()).append("被清除！");

        info.setInteSlideCoastCode("");
        info.setInteActualStartTime(null);
        info.setInteActualOverTime(null);

        info.setIntlSlideCoastCode("");
        info.setIntlActualStartTime(null);
        info.setIntlActualOverTime(null);
        info.setExpectedSlideCoastNum(0);
        info.setActualSlideCoastNum(0);

        slideCoastOccupyingInfoService.save(info);

        message.setMsg(1, sb.toString());
    }

    @Transactional
    @Check(operationName = "取消登机口", isOci = true, oci = BoardingGateOccupyingInfo.class)
    public void clearBoardingGateCode(FlightDynamic flightDynamic, BoardingGateOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        StringBuffer sb = new StringBuffer();
        sb.append("清除登机口分配！" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
        sb.append("登机口,").append(info.toString()).append("被清除！");

        info.setInteBoardingGateCode("");
        info.setInteActualStartTime(null);
        info.setInteActualOverTime(null);

        info.setIntlBoardingGateCode("");
        info.setIntlActualStartTime(null);
        info.setIntlActualOverTime(null);
        info.setExpectedBoardingGateNum(0);
        info.setActualBoardingGateNum(0);


        boardingGateOccupyingInfoService.save(info);
        message.setMsg(1, sb.toString());
    }

    @Transactional
    @Check(operationName = "按属性取消滑槽", isOci = true, oci = SlideCoastOccupyingInfo.class)
    public void clearSlideCoastByNature(FlightDynamic flightDynamic, SlideCoastOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        StringBuffer sb = new StringBuffer();
        sb.append("清除滑槽分配！" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
        sb.append("滑槽,").append(info.toString()).append("被清除！");

        slideCoastOccupyingInfoService.save(info);
        message.setMsg(1, sb.toString());
    }

    @Transactional
    @Check(operationName = "按属性取消行李转盘", isOci = true, oci = CarouselOccupyingInfo.class)
    public void clearCarouselCodeByNature(FlightDynamic flightDynamic, CarouselOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        StringBuffer sb = new StringBuffer();
        sb.append("清除行李转盘分配！" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
        sb.append("行李转盘,").append(info.toString()).append("被清除！");
        carouselOccupyingInfoService.save(info);
        message.setMsg(1, sb.toString());
    }

    @Transactional
    @Check(operationName = "按属性取消到港门", isOci = true, oci = ArrivalGateOccupyingInfo.class)
    public void clearArrivalGateCodeByNature(FlightDynamic flightDynamic, ArrivalGateOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        StringBuffer sb = new StringBuffer();
        sb.append("清除到港门分配！" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
        sb.append("到港门,").append(info.toString()).append("被清除！");

        arrivalGateOccupyingInfoService.save(info);

        message.setMsg(1, sb.toString());
    }

    @Transactional
    @Check(operationName = "按属性取消登机口", isOci = true, oci = BoardingGateOccupyingInfo.class)
    public void clearBoardingGateCodeByNature(FlightDynamic flightDynamic, BoardingGateOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        boardingGateOccupyingInfoService.save(info);
        StringBuffer sb = new StringBuffer();
        sb.append("清除登机口分配！" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
        sb.append("登机口,").append(info.toString()).append("被清除！");
        message.setMsg(1, sb.toString());
    }

    @Transactional
    @Check(operationName = "取消到港门", isOci = true, oci = ArrivalGateOccupyingInfo.class)
    public void clearArrivalGateCode(FlightDynamic flightDynamic, ArrivalGateOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        info.setInteArrivalGateCode("");
        info.setInteActualStartTime(null);
        info.setInteActualOverTime(null);

        info.setIntlArrivalGateCode("");
        info.setIntlActualStartTime(null);
        info.setIntlActualOverTime(null);
        info.setExpectedArrivalGateNum(0);
        info.setActualArrivalGateNum(0);

        arrivalGateOccupyingInfoService.save(info);
        StringBuffer sb = new StringBuffer();
        sb.append("清除到港门分配！" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
        sb.append("到港门,").append(info.toString()).append("被清除！");
        message.setMsg(1, sb.toString());
    }

    @Transactional
    @Check(operationName = "取消行李转盘", isOci = true, oci = CarouselOccupyingInfo.class)
    public void clearCarouselCode(FlightDynamic flightDynamic, CarouselOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        info.setInteCarouselCode("");
        info.setInteActualStartTime(null);
        info.setInteActualOverTime(null);

        info.setIntlCarouselCode("");
        info.setIntlActualStartTime(null);
        info.setIntlActualOverTime(null);
        info.setExpectedCarouselNum(0L);
        info.setActualCarouselNum(0L);

        carouselOccupyingInfoService.save(info);

        StringBuffer sb = new StringBuffer();
        sb.append("清除行李转盘分配！" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
        sb.append("行李转盘,").append(info.toString()).append("被清除！");
        message.setMsg(1, sb.toString());
    }


    @Transactional
    @Check(operationName = "自动分配到港门")
    public void arrivalGateAuto(ResourceType type, Map<String, Object> params, String packageId, Message message) {
        try {
            params.put("urule-package", getArrivalGatePkg(packageId));
            ResourceDistributionContainer.getQueue(type).offer(params);
            message.setMsg(1, "到港门自动分配已成功添加到任务队列中！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Check(operationName = "自动分配行李转盘")
    public void carouselAuto(ResourceType type, Map<String, Object> params, String packageId, Message message) {
        try {
            params.put("urule-package", getCarouselPkg(packageId));
            ResourceDistributionContainer.getQueue(type).offer(params);
            message.setMsg(1, "行李转盘自动分配已成功添加到任务队列中！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Check(operationName = "自动分配登机口")
    public void boardingGateAuto(ResourceType type, Map<String, Object> params, String packageId, Message message) {
        try {
            params.put("urule-package", getBoardingGatePkg(packageId));
            ResourceDistributionContainer.getQueue(type).offer(params);
            message.setMsg(1, "登机口自动分配已成功添加到任务队列中！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Check(operationName = "自动分配滑槽")
    public void slideCoastAuto(ResourceType type, Map<String, Object> params, String packageId, Message message) {
        try {
            params.put("urule-package", getSlideCoastPkg(packageId));
            ResourceDistributionContainer.getQueue(type).offer(params);
            message.setMsg(1, "滑槽自动分配已成功添加到任务队列中！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Check(operationName = "自动分配值机柜台")
    public void checkingCounterAuto(ResourceType type, Map<String, Object> params, String packageId, Message message) {
        try {
            params.put("urule-package", getCheckinCounterPkg(packageId));
            ResourceDistributionContainer.getQueue(type).offer(params);
            message.setMsg(1, "值机柜台自动分配已成功添加到任务队列中！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Check(operationName = "自动分配安检口")
    public void securityCheckinAuto(ResourceType type, Map<String, Object> params, String packageId, Message message) {
        try {
            params.put("urule-package", getSecurityCheckinPkg(packageId));
            ResourceDistributionContainer.getQueue(type).offer(params);
            message.setMsg(1, "安检口自动分配已成功添加到任务队列中！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Check(operationName = "自动分配候机厅")
    public void departureHallAuto(ResourceType type, Map<String, Object> params, String packageId, Message message) {
        try {
            params.put("urule-package", getDepartureHallPkg(packageId));
            ResourceDistributionContainer.getQueue(type).offer(params);
            message.setMsg(1, "候机厅自动分配已成功添加到任务队列中！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Check(operationName = "自动分配机位")
    public void aircraftNumAuto(ResourceType type, Map<String, Object> params, String packageId, Message message) {
        try {
            params.put("urule-package", getAircraftStandPkg(packageId));
            ResourceDistributionContainer.getQueue(type).offer(params);
            message.setMsg(1, "机位自动分配已成功添加到任务队列中！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Check(operationName = "机位强制手动分配", oci = GateOccupyingInfo.class, isOci = true)
    public void forceAircraftStandNumDist(FlightPlanPair pair, GateOccupyingInfo info, Message message) {
        if (!isPair(pair, message)) return;
        String oldPlaceNum = pair.getPlaceNum2();
        pair.setPlaceNum2(null);
        boolean isChanged = true;
        if (StringUtils.isEmpty(oldPlaceNum)) isChanged = false;

        flightDynamicService.updateFlightDynamicInfoAfterAircraftDistributed(pair);
        flightPlanPairService.save(pair);
        gateOccupyingInfoService.save(info);

        // flightPlanPairService.generateProgressInfos(flightPlanPair);
        message.setResult(ImmutableMap.of("placeNum", pair.getPlaceNum()));

        StringBuffer sb = new StringBuffer();
        sb.append("机位手动分配成功！分配" + LogUtils.msgPair(pair) + " 的");
        sb.append("机位由").append(StringUtils.trimToEmpty(oldPlaceNum)).append("号机位变更为");
        sb.append(StringUtils.trimToEmpty(pair.getPlaceNum())).append("号机位");

        message.setMsg(1, sb.toString());


        if (!StringUtils.equals(StringUtils.trimToEmpty(oldPlaceNum), pair.getPlaceNum()))
            aircraftGateMessageSender(isChanged, pair, pair.getPlaceNum(), oldPlaceNum);
    }

    @Transactional
    @Check(operationName = "值机柜台设置实际占用时间", oci = CheckingCounterOccupyingInfo.class, isOci = true)
    public void setCheckinCounterActualTime(FlightDynamic flightDynamic, CheckingCounterOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        checkingCounterOccupyingInfoService.save(info);
        StringBuffer sb = new StringBuffer(LogUtils.msgFlightDynamic(flightDynamic));
        sb.append("值机柜台设置实际占用[开始时间:").append(DateUtils.formatDateTime(info.getInteActualStartTime()));
        sb.append("结束时间：").append(DateUtils.formatDateTime(info.getInteActualOverTime())).append("]");
        message.setMsg(1, sb.toString());
    }

    @Transactional
    @Check(operationName = "安检口设置实际占用时间", oci = SecurityCheckinOccupyingInfo.class, isOci = true)
    public void setSecurityCheckinActualTime(FlightDynamic flightDynamic, SecurityCheckinOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        securityCheckinOccupyingInfoService.save(info);
        StringBuffer sb = new StringBuffer(LogUtils.msgFlightDynamic(flightDynamic));
        sb.append("安检口设置实际占用[开始时间:").append(DateUtils.formatDateTime(info.getInteActualStartTime()));
        sb.append("结束时间：").append(DateUtils.formatDateTime(info.getInteActualOverTime())).append("]");
        message.setMsg(1, sb.toString());
    }

    @Transactional
    @Check(operationName = "候机厅设置实际占用时间", oci = DepartureHallOccupyingInfo.class, isOci = true)
    public void setDepartureHallActualTime(FlightDynamic flightDynamic, DepartureHallOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        departureHallOccupyingInfoService.save(info);
        StringBuffer sb = new StringBuffer(LogUtils.msgFlightDynamic(flightDynamic));
        sb.append("候机厅设置实际占用[开始时间:").append(DateUtils.formatDateTime(info.getInteActualStartTime()));
        sb.append("结束时间：").append(DateUtils.formatDateTime(info.getInteActualOverTime())).append("]");
        message.setMsg(1, sb.toString());
    }

    @Transactional
    @Check(operationName = "滑槽设置实际占用时间", oci = SlideCoastOccupyingInfo.class, isOci = true)
    public void setSlideCoastActualTime(FlightDynamic flightDynamic, SlideCoastOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        slideCoastOccupyingInfoService.save(info);
        StringBuffer sb = new StringBuffer(LogUtils.msgFlightDynamic(flightDynamic));
        sb.append("滑槽设置实际占用[开始时间:").append(DateUtils.formatDateTime(info.getInteActualStartTime()));
        sb.append("结束时间：").append(DateUtils.formatDateTime(info.getInteActualOverTime())).append("]");
        message.setMsg(1, sb.toString());
    }

    @Transactional
    @Check(operationName = "登机口设置实际占用时间", oci = BoardingGateOccupyingInfo.class, isOci = true)
    public void setBoardingGateActualTime(FlightDynamic flightDynamic, BoardingGateOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        boardingGateOccupyingInfoService.save(info);
        StringBuffer sb = new StringBuffer(LogUtils.msgFlightDynamic(flightDynamic));
        sb.append("登机口设置实际占用[开始时间:").append(DateUtils.formatDateTime(info.getInteActualStartTime()));
        sb.append("结束时间：").append(DateUtils.formatDateTime(info.getInteActualOverTime())).append("]");
        message.setMsg(1, sb.toString());
    }

    @Transactional
    @Check(operationName = "到港门设置实际占用时间", oci = ArrivalGateOccupyingInfo.class, isOci = true)
    public void setArrivalGateActualTime(FlightDynamic flightDynamic, ArrivalGateOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        arrivalGateOccupyingInfoService.save(info);
        StringBuffer sb = new StringBuffer(LogUtils.msgFlightDynamic(flightDynamic));
        sb.append("到港门设置实际占用[开始时间:").append(DateUtils.formatDateTime(info.getInteActualStartTime()));
        sb.append("结束时间：").append(DateUtils.formatDateTime(info.getInteActualOverTime())).append("]");
        message.setMsg(1, sb.toString());
    }

    @Transactional
    @Check(operationName = "行李转盘设置实际占用时间", isOci = true, oci = CarouselOccupyingInfo.class)
    public void setActualTime(FlightDynamic flightDynamic, CarouselOccupyingInfo info, Message message) {
        if (!isFlightDynamic(flightDynamic, message)) return;
        carouselOccupyingInfoService.save(info);
        StringBuffer sb = new StringBuffer(LogUtils.msgFlightDynamic(flightDynamic));
        sb.append("行李转盘设置实际占用[开始时间:").append(DateUtils.formatDateTime(info.getInteActualStartTime()));
        sb.append("结束时间：").append(DateUtils.formatDateTime(info.getInteActualOverTime())).append("]");
        message.setMsg(1, sb.toString());
    }

    private boolean isFlightDynamic(FlightDynamic flightDynamic, Message message) {
        FlightDynamic temp = flightDynamicDao.get(flightDynamic);
        if (temp == null) {
            message.setMsg(0, "航班动态数据错误！");
            return false;
        }
        return true;
    }

    public boolean isPair(FlightPlanPair pair, Message message) {
        FlightPlanPair tempPair = flightPlanPairDao.get(pair);
        if (tempPair == null) {
            message.setMsg(0, "航班配对数据错误！");
            return false;
        }
        return true;
    }

    @Transactional
    public void checkingCounterManualForAgent(Message message, String random, String id, String agentIds, String companyCodes, String inteCode, String intlCode) {
        if ((StringUtils.isBlank(inteCode) && StringUtils.isBlank(intlCode))) {
            message.setMsg(0, "参数错误！");
            return;
        }

        random = UUID.randomUUID().toString();

        if (StringUtils.isNotBlank(agentIds)) {
            List<String> agentIdList = Arrays.asList(agentIds.split(","));
            Map<String, Object> params = new HashMap<>();
            params.put("dsfn", BaseService.dataScopeFilterNew(new FlightDynamic().getCurrentUser()));
            params.put("agentIdList", agentIdList);
            if (StringUtils.isNotBlank(companyCodes)) {
                List<String> companyList = Arrays.asList(companyCodes.split(","));
                params.put("companyList", companyList);
            }
            List<Map<String, String>> fids = flightDynamicDao.getFIdByAgents(params);
            for (Map<String, String> fid : fids) {
                if (StringUtils.isNotBlank(fid.get("id"))) {
                    CheckingCounterOccupyingInfo info = checkingCounterOccupyingInfoDao.getByFlightDynamicId(fid.get("id"));
                    if (info != null) {
                        info.setRandom(random);
                        if ("1".equals(info.getFlightDynamicNature())) {
                            info.setInteCheckingCounterCode(inteCode);
                        } else if ("2".equals(info.getFlightDynamicNature())) {
                            info.setIntlCheckingCounterCode(intlCode);
                        } else if ("3".equals(info.getFlightDynamicNature())) {
                            info.setIntlCheckingCounterCode(intlCode);
                            info.setInteCheckingCounterCode(inteCode);
                        }
                        checkingCounterOccupyingInfoService.save(info);
                    }
                }
            }

            message.setMsg(1, "值机柜台批量分配成功!");
            ConcurrentClientsHolder.getSocket("/global/dynamics").emit("assignment_complete_message", "complete");
            return;
        } else if (StringUtils.isNotBlank(id)) {
            CheckingCounterOccupyingInfo info = checkingCounterOccupyingInfoDao.getByFlightDynamicId(id);
            if (info != null) {
                info.setRandom(random);
                info.setIntlCheckingCounterCode(intlCode);
                info.setInteCheckingCounterCode(inteCode);
                checkingCounterOccupyingInfoService.save(info);
                message.setMsg(1, "值机柜台手动分配成功!");
                ConcurrentClientsHolder.getSocket("/global/dynamics").emit("assignment_complete_message", "complete");
                return;
            }
        }
//        StringBuffer sb = new StringBuffer();
//        sb.append("值机柜台手动分配成功！分配" + LogUtils.msgFlightDynamic(flightDynamic) + " 的");
//        sb.append("值机柜台,").append(info.toString());
//        message.setMsg(1, sb.toString());

        message.setMsg(0, "错误！");
    }
}
