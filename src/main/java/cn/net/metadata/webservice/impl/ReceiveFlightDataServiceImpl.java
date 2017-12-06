package cn.net.metadata.webservice.impl;

import cn.net.metadata.datasync.socketio.ConcurrentClientsHolder;
import cn.net.metadata.event.OciPublishEvent;
import cn.net.metadata.messageservice.common.aidx.FlightDynamic;
import cn.net.metadata.messageservice.common.entity.FlightPairWrapper;
import cn.net.metadata.messageservice.publisher.webservice.ActiveMQTypeEnum;
import cn.net.metadata.webservice.IReceiveFlightDataService;
import cn.net.metadata.webservice.dynamic.FlightDynamicStatusModifyFactory;
import cn.net.metadata.webservice.dynamic.IFlightDynamicStatus;
import cn.net.metadata.wrapper.PairAndDynamicCollections;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.ams.dao.FlightDynamicDao;
import com.thinkgem.jeesite.modules.rms.dao.FlightPlanPairDao;
import com.thinkgem.jeesite.modules.rms.entity.*;
import com.thinkgem.jeesite.modules.rms.service.FlightPlanPairService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.jws.WebService;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiaopo
 * @date 16/4/13
 */
@Service
@WebService(endpointInterface = "cn.net.metadata.webservice.IReceiveFlightDataService")
public class ReceiveFlightDataServiceImpl implements IReceiveFlightDataService {

    private static String flightIntervalTime = DictUtils.getDictValueConstant("flight_interval_setting", "minutes");
    private static Logger logger = LoggerFactory.getLogger(ReceiveFlightDataServiceImpl.class);

    @Autowired
    private FlightDynamicDao dynamicDao;
    @Autowired
    private FlightPlanPairDao pairDao;
    @Autowired
    private FlightDynamicStatusModifyFactory flightDynamicStatusModifyFactory;
    @Autowired
    private FlightPlanPairService flightPlanPairService;
    @Autowired
    private DataSourceTransactionManager txManager;
    @Autowired
    private ApplicationContext applicationContext;

    public static final ObjectMapper mapper = new ObjectMapper();
    public static Map<String, String> ociTitleToClassNameMap = new HashMap<>();

    static {
        ociTitleToClassNameMap.put("行李转盘", CarouselOccupyingInfo.class.getSimpleName());
        ociTitleToClassNameMap.put("到港门", ArrivalGateOccupyingInfo.class.getSimpleName());
        ociTitleToClassNameMap.put("值机柜台", CheckingCounterOccupyingInfo.class.getSimpleName());
        ociTitleToClassNameMap.put("登机口", BoardingGateOccupyingInfo.class.getSimpleName());
        ociTitleToClassNameMap.put("滑槽", SlideCoastOccupyingInfo.class.getSimpleName());
        ociTitleToClassNameMap.put("候机厅", DepartureHallOccupyingInfo.class.getSimpleName());
        ociTitleToClassNameMap.put("安检口", SecurityCheckinOccupyingInfo.class.getSimpleName());
        ociTitleToClassNameMap.put("机位", GateOccupyingInfo.class.getSimpleName());
    }

    /**
     * 接收ActiveMQ发过来的航班动态初始化数据
     *
     * @param dynamicList
     * @param pairWrappers
     * @return
     */
    public String receiveFlightDyanmicData(List<FlightDynamic> dynamicList, List<FlightPairWrapper> pairWrappers) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
        TransactionStatus status = txManager.getTransaction(def); // 获得事务状态

        PairAndDynamicCollections pairAndDynamicCollections = new PairAndDynamicCollections();
        Date planDate = null;
        // 把数据分别插入Dynamic表中,如果表中存在数据则删除
        try {
            if (!Collections3.isEmpty(dynamicList)) {
                // 删除当前日期的动态
                // 查询是否已经存在初始化日期时的动态数据
                com.thinkgem.jeesite.modules.ams.entity.FlightDynamic paramEntity = new com.thinkgem.jeesite.modules.ams.entity.FlightDynamic();
                planDate = dynamicList.get(0).getPlanDate();
                //通过计划日期清除占用、动态及配对数据 wjp_2017年7月19日13时52分
                dynamicDao.clearResourceBeforeDynamicInit(planDate);

                paramEntity.setPlanDate(dynamicList.get(0).getPlanDate());
                List<com.thinkgem.jeesite.modules.ams.entity.FlightDynamic> dynamicTemps = dynamicDao.findList(paramEntity);
                if (!Collections3.isEmpty(dynamicTemps)) {
                    //如果重复动态数据尚未清除，且存在，清除该数据
                    for (com.thinkgem.jeesite.modules.ams.entity.FlightDynamic dynamicTemp : dynamicTemps) {
                        dynamicDao.delete(dynamicTemp);
                    }
                }

                List<com.thinkgem.jeesite.modules.ams.entity.FlightDynamic> flightDynamics = new ArrayList<>();
                for (FlightDynamic dynamic : dynamicList) {
                    // 保存新的数据
                    com.thinkgem.jeesite.modules.ams.entity.FlightDynamic dynamictemp = new com.thinkgem.jeesite.modules.ams.entity.FlightDynamic();
                    BeanUtils.copyProperties(dynamic, dynamictemp);
                    dynamicDao.insert(dynamictemp);
                    flightDynamics.add(dynamictemp);
                }
                pairAndDynamicCollections.setFlightDynamics(flightDynamics);
            }

            if (!Collections3.isEmpty(pairWrappers)) {
                // 删除当前日期的配对
                // 查询是否已经存在初始化日期时的配对数据
                FlightPlanPair tempPair = new FlightPlanPair();
                if (planDate == null) planDate = pairWrappers.get(0).getPlanDate();
                tempPair.setPlanDate(pairWrappers.get(0).getPlanDate());
                List<FlightPlanPair> oldPairs = pairDao.findList(tempPair);
                if (!Collections3.isEmpty(oldPairs)) {
                    for (FlightPlanPair oldPair : oldPairs) {
                        pairDao.delete(oldPair);
                    }
                }

                List<FlightPlanPair> pairs = new ArrayList<>();
                for (FlightPairWrapper pairWrapper : pairWrappers) {
                    FlightPlanPair newPair = new FlightPlanPair();
                    BeanUtils.copyProperties(pairWrapper, newPair);
                    pairDao.insert(newPair);
                    pairs.add(newPair);
                }
                pairAndDynamicCollections.setFlightPairWrappers(pairs);
            }
        } catch (BeansException e) {
            //e.printStackTrace();
            logger.error("航班计划初始化失败：{}", e.getMessage());
            return "err";
        }

        txManager.commit(status);

        //计算占用 成功后 通知rms前端
        Map<String, List> ociDatas = new HashMap<>();
        try {
            if (flightPlanPairService.calcResourcDataInited(pairAndDynamicCollections, ociDatas)) {
                //初始化占用传到ams系统 wjp_2017年7月19日18时11分
                ociDatas.forEach((k, v) -> eventSends(v, ociTitleToClassNameMap.get(k)));

                ConcurrentClientsHolder.getSocket("/global/dynamics")
                        .emit("flightDynamicInit", new ObjectMapper().writeValueAsString(ImmutableMap.of("dynamics", pairAndDynamicCollections.getFlightDynamics(),
                                "pairWrappers", pairAndDynamicCollections.getFlightPairWrappers(), "planDate", DateUtils.formatDate(planDate), "oci", ociDatas)));

            } else {
                //throw new RuntimeException("占用计算失败！");
                logger.error("航班计划初始化失败：{}", "占用计算失败！");
                dynamicDao.clearResourceBeforeDynamicInit(planDate);
                return "err";
            }
        } catch (JsonProcessingException e) {
            //e.printStackTrace();
            logger.error("航班计划初始化失败：{}", e.getMessage());
            dynamicDao.clearResourceBeforeDynamicInit(planDate);
            return "err";
        }
        return "success";
    }

    public String receiveFlightDynamicStatus(FlightDynamic dynamic, ActiveMQTypeEnum activeMQTypeEnum) {
        String result = "failed";
        com.thinkgem.jeesite.modules.ams.entity.FlightDynamic dynamictemp = new com.thinkgem.jeesite.modules.ams.entity.FlightDynamic();
        BeanUtils.copyProperties(dynamic, dynamictemp);
        //
        IFlightDynamicStatus iFlightDynamicStatus = flightDynamicStatusModifyFactory.creator(activeMQTypeEnum);
        iFlightDynamicStatus.updateStatusData(dynamictemp);
        result = "success";
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String receiveData(String sync, List<FlightDynamic> dynamicList, List<FlightPairWrapper> pairWrappers) {
        if (StringUtils.isNotBlank(sync) && sync.indexOf("sync_") == 0) {
            if (!Collections3.isEmpty(dynamicList)) {
                for (FlightDynamic dynamic : dynamicList) {
                    com.thinkgem.jeesite.modules.ams.entity.FlightDynamic dynamictemp = new com.thinkgem.jeesite.modules.ams.entity.FlightDynamic();
                    BeanUtils.copyProperties(dynamic, dynamictemp);
                    if (sync.indexOf("_INSERT") > 0) {
                        dynamicDao.insert(dynamictemp);
                    } else if (sync.indexOf("_UPDATE") > 0) {
                        com.thinkgem.jeesite.modules.ams.entity.FlightDynamic flnTmp = dynamicDao.get(dynamictemp);
                        dynamicDao.update(dynamictemp);

                        //添加时间隔发消息功能
                        if (flnTmp != null && flnTmp.getAtd() == null && dynamictemp.getAtd() != null)
                            flightIntervalCalculate(dynamictemp);
                    } else if (sync.indexOf("_DELETE") > 0) {
                        //判断是否为转历史 wjp_2017年7月21日18时57分
                        if ("toHistory".equals(dynamic.getExt21())) {
                            dynamicDao.ociToHistory(dynamictemp.getId());
                            dynamicDao.manifestToHistory(dynamictemp.getId());
                        } else dynamicDao.ociDelete(dynamictemp.getId());
                        dynamicDao.delete(dynamictemp);
                    }
                }
            }

            if (!Collections3.isEmpty(pairWrappers)) {
                for (FlightPairWrapper pair : pairWrappers) {
                    com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair pairtemp = new com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair();
                    BeanUtils.copyProperties(pair, pairtemp);
                    if (sync.indexOf("_INSERT") > 0) {
                        pairDao.insert(pairtemp);
                    } else if (sync.indexOf("_UPDATE") > 0) {
                        pairDao.update(pairtemp);
                    } else if (sync.indexOf("_DELETE") > 0) {
                        //判断是否为转历史 wjp_2017年7月21日18时57分
                        if ("toHistory".equals(pair.getExt7())) dynamicDao.ociToHistory(pair.getId());
                        else dynamicDao.ociDelete(pair.getId());
                        pairDao.delete(pairtemp);
                    }
                }
            }
        }
        return "success";
    }

    @Override
    public String msgCallback(String message) {
        try {
            if (Boolean.parseBoolean(Global.getConfig("syncMiddle")) && com.thinkgem.jeesite.common.utils.StringUtils.isNotBlank(message)) {
                if (message.indexOf("err_") == 0) {
                    String syncNo = message.replace("err_", "");
                    logger.error("航班占用数据同步失败!" + message);
                    if (CacheUtils.get(syncNo) != null) {
                        CacheUtils.remove(syncNo);
                    }
                } else {
                    notiSend(message);
                    logger.info("====航班占用数据(" + message + ")同步成功!===");
                }
                return "ok";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "err";
    }

    //装配消息，并发送
    public void notiSend(String syncNo) {
        Map noMap = (Map) CacheUtils.get(syncNo);
        if (noMap != null) {
            String namespace = (String) noMap.get("namespace");
            String event = (String) noMap.get("event");
            ConcurrentClientsHolder.getSocket(namespace).emit(event, noMap.get("args"));
            CacheUtils.remove(syncNo);
        }
    }

    /**
     * 通过事件传递初始化的占用数据
     *
     * @param ociList
     * @param className
     */
    public void eventSends(List ociList, String className) {
        Map<String, Object> map = new HashMap();
        String sync = className + "_" + UUID.randomUUID().toString() + "_INSERT";
        map.put("syncNo", sync);
        map.put("ociList", ociList.stream().map(o -> {
            try {
                return mapper.writeValueAsString(o);
            } catch (JsonProcessingException e) {
                //e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList()));
        OciPublishEvent ociEvent = new OciPublishEvent(map);
        applicationContext.publishEvent(ociEvent);
    }

    /**
     * 计算出港起飞与下一个航班的计达或预达时间隔，并发消息提醒 wjp_2017年7月21日19时13分
     *
     * @param dynamictemp
     */
    private void flightIntervalCalculate(com.thinkgem.jeesite.modules.ams.entity.FlightDynamic dynamictemp) {
        new Thread(() -> {
            //添加已起飞判断 计算后面航班的到达时间与现起飞时间的时间差；wjp_2017年7月13日13时30分
            if (dynamictemp.getPlaceNum() != null && "DEP".equals(dynamictemp.getStatus())) {
                logger.info("航班间隔，已起飞航班{}", LogUtils.msgFlightDynamic(dynamictemp));
                List<com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair> list = pairDao.getPlaceNumList(dynamictemp.getPlaceNum());
                if (!Collections3.isEmpty(list)) {
                    for (com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair pair : list) {
                        if (StringUtils.equals(dynamictemp.getId(), pair.getFlightDynimicId2())) {
                            if (list.indexOf(pair) + 1 >= list.size()) break;
                            com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair nextPair = list.get(list.indexOf(pair) + 1);
                            if (nextPair.getFlightDynimicId() != null) {
                                long t = DateUtils.pastMinutes(dynamictemp.getAtd(), nextPair.getArrivalPlanTime());
                                if (nextPair.getEta() != null)
                                    t = DateUtils.pastMinutes(dynamictemp.getAtd(), nextPair.getEta());
                                //两航班间隔时时大于设置时间将发送消息提醒
                                if (t >= Long.parseLong(flightIntervalTime)) {
                                    Map<String, Object> params = new HashMap<>();
                                    params.put("occur", System.currentTimeMillis());
                                    params.put("message", com.thinkgem.jeesite.common.utils.StringUtils.tpl("{}号机位已起飞航班{}，离下个到达航班{}还有{}分钟间隔",
                                            dynamictemp.getPlaceNum(), pair.getFlightCompanyCode2() + pair.getFlightNum2(), nextPair.getFlightCompanyCode() + nextPair.getFlightNum(), t));

                                    try {
                                        ConcurrentClientsHolder.getSocket("/dynamic_realtime_message").emit("dynamic_message_dispatcher", new org.codehaus.jackson.map.ObjectMapper().writeValueAsString(params));
                                    } catch (Exception e) {
                                        LoggerFactory.getLogger(getClass()).error("广播{航班间隔}消息时出现异常");
                                        e.printStackTrace();
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }).start();
    }
}
