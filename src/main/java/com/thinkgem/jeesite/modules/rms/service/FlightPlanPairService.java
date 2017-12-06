package com.thinkgem.jeesite.modules.rms.service;

import cn.net.metadata.annotation.Check;
import cn.net.metadata.datasync.socketio.ConcurrentClientsHolder;
import cn.net.metadata.gantt.wrapper.CommonWrapper;
import cn.net.metadata.rule.action.dist.ResourceDistUtils;
import cn.net.metadata.te.ResourceDataInitThreadFactory;
import cn.net.metadata.utils.DateTimeUtils;
import cn.net.metadata.wrapper.GanttAxisWrapper;
import cn.net.metadata.wrapper.PairAndDynamicCollections;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.dao.FlightDynamicDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.event.SyncTypeEnum;
import com.thinkgem.jeesite.modules.ams.utils.AddEventUtils;
import com.thinkgem.jeesite.modules.rms.dao.CommonBatchDao;
import com.thinkgem.jeesite.modules.rms.dao.FlightPlanPairDao;
import com.thinkgem.jeesite.modules.rms.entity.*;
import com.thinkgem.jeesite.modules.rms.wrapper.AddProgressWrapper;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.UsernamePasswordToken;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 航班动态配对Service
 *
 * @author xiaowu
 * @version 2016-05-04
 */
@Service
@Transactional(readOnly = true)
public class FlightPlanPairService extends CrudService<FlightPlanPairDao, FlightPlanPair> {

    private static final String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    @Autowired
    ResourceSharingService resourceSharingService;
    @Autowired
    DefaultSecurityManager securityManager;
    @Autowired
    SystemService systemService;

    @Autowired
    private SafeguardSeatTimelongService safeguardSeatTimelongService;
    @Autowired
    private FlightDynamicDao flightDynamicDao;
    @Autowired
    private FlightPairProgressInfoService flightPairProgressInfoService;
    @Autowired
    private CommonBatchDao commonBatchDao;
    @Autowired
    private SafeguardProcessService safeguardProcessService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private SafeguardDurationService safeguardDurationService;

    public FlightPlanPair get(String id) {
        return super.get(id);
    }

    public List<FlightPlanPair> findList(FlightPlanPair flightPlanPair) {
        flightPlanPair.getSqlMap().put("dsfn", dataScopeFilterNew(flightPlanPair.getCurrentUser(), true));
        return super.findList(flightPlanPair);
    }

    public List<FlightPlanPair> findMockList(ResourceMockDistInfo info) {
        ImmutableMap params = ImmutableMap.of("startTime", info.getMockStartDate(), "endTime", info.getMockOverDate(),
                "filterDistedRes", info.getFilterDistedRes(), "dsfn", dataScopeFilterNew(UserUtils.getUser(), true));
        return dao.findMockPairs(params);
    }

    public Integer getTotal() {
        return dao.getTotal();
    }

    public Page<FlightPlanPair> findPage(Page<FlightPlanPair> page, FlightPlanPair flightPlanPair) {
        return super.findPage(page, flightPlanPair);
    }

    @Transactional
    public void save(FlightPlanPair flightPlanPair) {
        super.save(flightPlanPair);
    }

    @Transactional
    public void delete(FlightPlanPair flightPlanPair) {
        super.delete(flightPlanPair);
    }

    /**
     * 返回该配对航班gantt数据
     *
     * @param pairId
     * @return
     */
    public HashMap<String, Object> queryFlightTimeLong(String pairId) {
        //
        HashMap<String, Object> ganttMap = new HashMap();
        // 根据 pairId 查询配对航班表数据
        FlightPlanPair pair = get(pairId);

        // 拼装yAxis,Datas
        LinkedHashSet<GanttAxisWrapper> yAxiss = new LinkedHashSet<>();
        List<CommonWrapper> datas = new ArrayList<>();

        ganttMap.put("xAxis", Lists.newArrayList());
        ganttMap.put("yAxis", yAxiss);
        ganttMap.put("data", datas);
        ganttMap.put("minutesDiff", 0); // 默认设置为0;

        calcuateGanttDatas(ganttMap, pair);

        return ganttMap;
    }

    /**
     * 计算甘特图数据
     */
    private void calcuateGanttDatas(HashMap<String, Object> ganttMap, FlightPlanPair pair) {
        LinkedHashSet<GanttAxisWrapper> yAxiss = new LinkedHashSet<>();
        List<CommonWrapper> datas = new LinkedList<>();

        List<FlightPairProgressInfo> infoList;

        if (!flightPairProgressInfoService.checkPairExists(pair.getId())) infoList = generateProgressInfos(pair);
        else infoList = flightPairProgressInfoService.queryByPairId(pair.getId());

        // 排序找到第一个过程，将其设置为基准过程节点，用户在点击此过程设置实际开始时间之后，其余的过程要相应的平移
        infoList.sort((info1, info2) -> info1.getPlanStartTime().before(info2.getPlanStartTime()) ? -1 : info1.getPlanStartTime().equals(info2.getPlanStartTime()) ? 0 : 1);
        List<String> alreadyAddedAxis = new ArrayList<>();
        infoList.forEach(info -> {
            if (StringUtils.equals(info.getProgressName(), DictUtils.getDictValue("保障过程基点", "progress_basic", "挡轮档")) && info.getActualStartTime() != null) {
                ganttMap.put("minutesDiff", Minutes.minutesBetween(new DateTime(info.getPlanStartTime().getTime()), new DateTime(info.getActualStartTime().getTime())).getMinutes());
            }

            if (!alreadyAddedAxis.contains(info.getProgressName())) {
                yAxiss.add(new GanttAxisWrapper(info.getId(), info.getProgressName(), "1"));
                alreadyAddedAxis.add(info.getProgressName());
            }
        });

        // 通过保障过程编号获取当前保障的计划显示颜色和实际显示颜色\
        Map<String, Map<String, String>> toColor = safeguardProcessService.getColor();

        CommonWrapper commonWrapper; //data
        for (FlightPairProgressInfo info : infoList) {
            Map<String, Object> commonVariable = new HashMap<>();
            // datas
            commonWrapper = new CommonWrapper();
            Date startTime = DateUtils.addMinutes(info.getPlanStartTime(), Integer.parseInt(ganttMap.get("minutesDiff").toString()));
            Date endTime = DateUtils.addMinutes(info.getPlanOverTime(), Integer.parseInt(ganttMap.get("minutesDiff").toString()));
            commonWrapper.setStartDate(DateTimeUtils.dateToShortString(startTime));
            commonWrapper.setStartTime(DateTimeUtils.dateToShortString(startTime, "HH:mm"));
            commonWrapper.setEndDate(DateTimeUtils.dateToShortString(endTime));
            commonWrapper.setEndTime(DateTimeUtils.dateToShortString(endTime, "HH:mm"));
            commonWrapper.setText(info.getProgressName());
            commonWrapper.setGate(ImmutableList.of(info.getProgressName()));
            commonWrapper.setFlightDynamicId(pair.getId());
            commonWrapper.setId(info.getId());

            commonVariable.put("dynamic", false);
            commonVariable.put("actual", false);
            commonVariable.put("progressId", info.getProgressRefId());
            commonVariable.put("opName", info.getOpName());
            commonVariable.put("carType", info.getSpecialCarType());
            commonVariable.put("carCode", info.getSpecialCarCode());

            //设置计划颜色为保障过程图的默认颜色(未完成）
            if (StringUtils.isNotBlank(info.getProgressCode()) && toColor != null && toColor.get(info.getProgressCode()) != null) {
                Map<String, String> colorMap = toColor.get(info.getProgressCode());
                commonVariable.put("bgColor", colorMap.get("planColor"));
            }

            if (info.getPlanStartTime().equals(info.getPlanOverTime())) commonWrapper.setMoment(true);

            if (null != info.getActualStartTime()) {
                Map<String, Object> variables = new HashMap<>();
                variables.put("progressId", info.getProgressRefId());

                if (info.getActualOverTime() == null) variables.put("dynamic", true);
                else variables.put("dynamic", false);
                variables.put("actual", true);

                // 查询实际元素
                CommonWrapper actualCommonWrapper = new CommonWrapper();
                actualCommonWrapper.setText(info.getProgressName());
                actualCommonWrapper.setGate(ImmutableList.of(info.getProgressName()));
                actualCommonWrapper.setFlightDynamicId(pair.getId());
                actualCommonWrapper.setId(info.getId());
                actualCommonWrapper.setStartDate(DateTimeUtils.dateToShortString(info.getActualStartTime()));
                actualCommonWrapper.setStartTime(DateTimeUtils.dateToShortString(info.getActualStartTime(), "HH:mm"));
                if (null != info.getActualOverTime()) {
                    actualCommonWrapper.setEndDate(DateTimeUtils.dateToShortString(info.getActualOverTime()));
                    actualCommonWrapper.setEndTime(DateTimeUtils.dateToShortString(info.getActualOverTime(), "HH:mm"));
                }
                //添加计划时间
                String planStartDate = DateTimeUtils.dateToShortString(startTime) + " " + DateTimeUtils.dateToShortString(startTime, "HH:mm");
                String planEndDate = DateTimeUtils.dateToShortString(endTime) + " " + DateTimeUtils.dateToShortString(endTime, "HH:mm");
                variables.put("planStartDate", planStartDate);
                variables.put("planEndDate", planEndDate);

                if (info.getPlanStartTime().equals(info.getPlanOverTime())) actualCommonWrapper.setMoment(true);
                variables.put("actualStartTime", info.getActualStartTime());
                variables.put("actualOverTime", info.getActualOverTime());
                variables.put("opName", info.getOpName());
                variables.put("carType", info.getSpecialCarType());
                variables.put("carCode", info.getSpecialCarCode());
                //设置计划颜色为保障过程图的实际开始颜色
                if (StringUtils.isNotBlank(info.getProgressCode()) && toColor != null && toColor.get(info.getProgressCode()) != null) {
                    Map<String, String> colorMap = toColor.get(info.getProgressCode());
                    //实际开始不为空来判断保障是否已开始
                    variables.put("bgColor", colorMap.get("actualColor"));
                }

                commonVariable.put("actualStartTime", info.getActualStartTime());
                commonVariable.put("actualOverTime", info.getActualOverTime());

                actualCommonWrapper.setExtra(variables);
                datas.add(actualCommonWrapper);
            }

            commonWrapper.setExtra(commonVariable);

            datas.add(commonWrapper);
        }

        String xAxis = DateTimeUtils.dateToShortString(pair.getPlanDate());
        List<String> xAxisList = (List<String>) ganttMap.get("xAxis");
        if (!xAxisList.contains(xAxis)) xAxisList.add(xAxis);
        // 生成前后三天的数据
        String beforePlanDateStr = DateTimeUtils.dateToShortString(DateUtils.addDays(pair.getPlanDate(), -1));
        String afterPlanDateStr = DateTimeUtils.dateToShortString(DateUtils.addDays(pair.getPlanDate(), 1));

        if (!xAxisList.contains(beforePlanDateStr)) xAxisList.add(beforePlanDateStr);
        if (!xAxisList.contains(afterPlanDateStr)) xAxisList.add(afterPlanDateStr);

        LinkedHashSet<GanttAxisWrapper> yAxisList = (LinkedHashSet<GanttAxisWrapper>) ganttMap.get("yAxis");
        yAxisList.addAll(yAxiss);
        List<CommonWrapper> dataList = (List<CommonWrapper>) ganttMap.get("data");
        dataList.addAll(datas);

        ganttMap.put("xAxis", xAxisList);
        ganttMap.put("yAxis", yAxisList);
        ganttMap.put("data", dataList);
    }

    @Check(operationName = "更新保障类型")
    public Message modifySafeguardTypeCode(FlightPlanPair planPair, String safeguardTypeCode, String safeguardTypeName, Message message) {
        if (!isPair(planPair.getId(), message)) return message
                ;
        StringBuffer msg = new StringBuffer();
        // 更新flightdynamicpair表的数据
        msg.append(LogUtils.msgPair(planPair)).append(",保障类型[原：(");
        msg.append("子类型编号：").append(planPair.getSafecuardTypeCode()).append(",");
        msg.append("子类型名称：").append(planPair.getSafecuardTypeName()).append(")");

        planPair.setSafecuardTypeCode(safeguardTypeCode);
        planPair.setSafecuardTypeName(safeguardTypeName);
        dao.updateSafeguardTypeCode(planPair);

        // 更新flightdynamic
        FlightDynamic dynamic = new FlightDynamic();
        dynamic.setSafeguardTypeCode(safeguardTypeCode);
        dynamic.setSafeguardTypeName(safeguardTypeName);

        ArrayList<FlightDynamic> flightDynamics = new ArrayList<>();
        ArrayList<FlightPlanPair> pairs = new ArrayList<>();

        if (StringUtils.isNotBlank(planPair.getFlightDynimicId())) {
            dynamic.setId(planPair.getFlightDynimicId());
            flightDynamicDao.updateSafeguardTypeCode(dynamic);
            flightDynamics.add(dynamic);
        }

        if (StringUtils.isNotBlank(planPair.getFlightDynimicId2())) {
            dynamic.setId(planPair.getFlightDynimicId2());
            flightDynamicDao.updateSafeguardTypeCode(dynamic);
            flightDynamics.add(dynamic);
        }

        pairs.add(planPair);
        //添加事件驱动，将动态及配对数据同步到ams
        AddEventUtils.addEvent(flightDynamics, pairs, SyncTypeEnum.SAFEGUARD_TYPE, applicationContext);

        // remove first
        flightPairProgressInfoService.deleteByPairId(planPair.getId());
        generateProgressInfos(planPair);  // re-generating
        message.setCode(1);

        try {
            if (message.isSuccess()) {
                Map<String, Object> mockVariables = new HashMap<>();
                mockVariables.put("id", "");
                mockVariables.put("identify", "FlightPairProgressInfo");
                mockVariables.put("monitorType", "UPDATE");
                mockVariables.put("changeList", null);
                mockVariables.put("data", new ObjectMapper().writeValueAsString(ImmutableMap.of("id", "", "flightDynamicId", "", "pairId", planPair.getId())));
                mockVariables.put("uuid", UUID.randomUUID().toString());

                ConcurrentClientsHolder.getSocket("/global/dynamics").emit("modification", mockVariables);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        msg.append(",现：(");
        msg.append("子类型编号：").append(planPair.getSafecuardTypeCode()).append(",");
        msg.append("子类型名称：").append(planPair.getSafecuardTypeName()).append(")]");
        message.setMsg(1, msg.toString());
        return message;
    }

    public List<FlightPairProgressInfo> generateProgressInfos(FlightPlanPair flightPlanPair) {
        try {
            return generateProgressInfos(flightPlanPair, null);
        } catch (Exception e) {
            logger.error("保障进程计算错误！");
            //e.printStackTrace();
            throw e;
        }
    }

    //获取保障总时长 wjp_2017年8月15日17时01分
    public Long getSafeguardTotalLongTime(FlightPlanPair flightPlanPair) {
        Map<String, Object> variables = ResourceDistUtils.calcOccupyingTimeByPair(flightPlanPair);
        String inoutTypeCode = String.valueOf(variables.get("inoutType"));

        String typeCode = flightPlanPair.getSafecuardTypeCode();
        if (StringUtils.isEmpty(typeCode))
            typeCode = StringUtils.isNoneBlank(typeCode) ? typeCode : (inoutTypeCode.equalsIgnoreCase("SI") ? "dj00" : inoutTypeCode.equalsIgnoreCase("SO") ? "dc00" : "lb00");

        // 根据航班信息查询该飞机的座位数量
        Long seatNum = dao.querySeatNumByAircraftNum(flightPlanPair.getAircraftNum());
        // 设置默认座位数
        seatNum = seatNum != null ? seatNum : dao.getMinimalSeatNumByAircraftNum(typeCode);
        //获取指定座位数  wjp_2017年7月19日20时44分
        seatNum = getSeatNum(seatNum);

        SafeguardDuration safeguardDuration = new SafeguardDuration();
        safeguardDuration.setSafeguardTypeCode(typeCode);
        safeguardDuration.setMaxSeating(seatNum);
        return safeguardDurationService.getLongTime(safeguardDuration);
    }

    /**
     * 生成运输调度计划
     *
     * @param flightPlanPair    航班配对信息
     * @param pairProgressInfos 恢复进程中的已完的部分
     * @return
     */
    public List<FlightPairProgressInfo> generateProgressInfos(FlightPlanPair flightPlanPair, List<FlightPairProgressInfo> pairProgressInfos) {
        List<FlightPairProgressInfo> infoList = Lists.newArrayList();

        if (null == flightPlanPair || flightPairProgressInfoService.checkPairExists(flightPlanPair.getId()))
            return infoList;

        Map<String, Object> variables = ResourceDistUtils.calcOccupyingTimeByPair(flightPlanPair);
        String inoutTypeCode = String.valueOf(variables.get("inoutType"));

        // 根据航班信息查询对应的时长及过程
        String typeCode = flightPlanPair.getSafecuardTypeCode();
        if (StringUtils.isEmpty(typeCode))
            typeCode = StringUtils.isNoneBlank(typeCode) ? typeCode : (inoutTypeCode.equalsIgnoreCase("SI") ? "dj00" : inoutTypeCode.equalsIgnoreCase("SO") ? "dc00" : "lb00");

        // 根据航班信息查询该飞机的座位数量
        Long seatNum = dao.querySeatNumByAircraftNum(flightPlanPair.getAircraftNum());
        // 设置默认座位数
        seatNum = seatNum != null ? seatNum : dao.getMinimalSeatNumByAircraftNum(typeCode);
        //获取指定座位数  wjp_2017年7月19日20时44分
        seatNum = getSeatNum(seatNum);

        // 根据座位数和默认保障过程查询各个子过程的时长 RMS_SAFEGUARD_SEAT_TIMELONG
        SafeguardSeatTimelong timelong = new SafeguardSeatTimelong();
        timelong.setSeatNum(seatNum);
        timelong.setSafeguardTypeCode(typeCode);
        List<SafeguardSeatTimelong> safeguardSeatTimelongs = safeguardSeatTimelongService.queryListBySeatSafeguardTypeCode(timelong);

        Date baseDate = null;

        //修正单出航班（保障过程明细->更改保障类型）保存时出现计划时间错误 wjp_2017年1月18日17时14分
        if (inoutTypeCode.equals("SO")) baseDate = flightPlanPair.getDeparturePlanTime2();
        else if (variables.get("start") != null) baseDate = (Date) variables.get("start");
        else if (StringUtils.inString(inoutTypeCode, "SI", "RELATE")) baseDate = flightPlanPair.getArrivalPlanTime();
        //else if (inoutTypeCode.equals("SO")) baseDate = flightPlanPair.getDeparturePlanTime2();

        for (SafeguardSeatTimelong progress : safeguardSeatTimelongs) {
            FlightPairProgressInfo info = new FlightPairProgressInfo();
            info.setPairId(flightPlanPair.getId());
            info.setPlanStartTime(DateUtils.addMinutes(baseDate, progress.getSafeguardProcessFrom().intValue()));
            info.setPlanOverTime(DateUtils.addMinutes(baseDate, progress.getSafeguardProcessTo().intValue()));
            info.setProgressCode(progress.getSafeguardProcessCode());
            info.setProgressName(progress.getSafeguardProcessName());
            info.setProgressRefId(progress.getSafeguardProcess().getId());
            info.setSubTypeCode(progress.getSafeguardTypeCode());
            info.setSubTypeName(progress.getSafeguardTypeName());


            if (pairProgressInfos != null && pairProgressInfos.size() > 0) //更新运输调度项的时间
                pairProgressInfos.stream().filter(prog -> (StringUtils.equals(prog.getProgressRefId(), info.getProgressRefId()))).forEach(prog -> {
                    info.setActualStartTime(prog.getActualStartTime());
                    info.setActualOverTime(prog.getActualOverTime());
                });
            infoList.add(info);
        }
        commonBatchDao.batchInsertPairProgressInfos(infoList);
        return infoList;
    }

    /**
     * 更新运输调度时间
     *
     * @param pair
     */
    @Transactional
    public Message updateTimeProgress(FlightPlanPair pair) {
        Message message = new Message();
        try {
            List<FlightPairProgressInfo> infos = flightPairProgressInfoService.queryByPairId(pair.getId());
            if (infos != null && infos.size() > 0) {
                //保存已完成的保障项目的时间
                List<FlightPairProgressInfo> saveAtdList = infos.stream().filter(info -> (info.getActualStartTime() != null || info.getActualOverTime() != null)).collect(Collectors.toList());
                //删除保障过程
                infos.stream().forEach(info -> {
                    flightPairProgressInfoService.delete(info);
                });
                //更新保障过程
                generateProgressInfos(pair, saveAtdList);
                message.setCode(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage("更新运输调度时间失败：" + e.getCause().getMessage());
        }
        return message;
    }

    @Transactional
    @Check(operationName = "添加保障过程")
    public void addProgress(AddProgressWrapper wrapper, Message message) {
        if (!isPair(wrapper.getPairId(), message)) return;
        try {
            FlightPlanPair pair = this.get(wrapper.getPairId());
            StringBuffer msg = new StringBuffer(pair != null ? LogUtils.msgPair(pair) : "").append(",过程详情[");
            for (SafeguardProcess progress : wrapper.getInfos()) {
                FlightPairProgressInfo info = new FlightPairProgressInfo();
                info.setPairId(wrapper.getPairId());
                info.setProgressCode(progress.getSafeguardProcessCode());
                info.setProgressName(progress.getSafeguardProcessName());
                info.setProgressRefId(progress.getId());
                info.setPlanStartTime(DateUtils.parseDate(progress.getRedundant1(), yyyyMMddHHmm));
                info.setPlanOverTime(DateUtils.parseDate(progress.getRedundant2(), yyyyMMddHHmm));

                flightPairProgressInfoService.save(info);
                msg.append("(").append(progress.toString()).append("),");
            }
            msg.append("]");

            Map<String, Object> mockVariables = new HashMap<>();
            mockVariables.put("id", "");
            mockVariables.put("identify", "FlightPairProgressInfo");
            mockVariables.put("monitorType", "UPDATE");
            mockVariables.put("changeList", null);
            mockVariables.put("data", new ObjectMapper().writeValueAsString(ImmutableMap.of("id", "", "flightDynamicId", "", "pairId", wrapper.getPairId())));

            mockVariables.put("uuid", wrapper.getRandom());


            ConcurrentClientsHolder.getSocket("/global/dynamics").emit("modification", mockVariables);

            message.setMsg(1, "保障过程添加成功!" + msg.toString());
        } catch (Exception e) {
            message.setMsg(0, "保障过程添加失败!");
        }
    }

    public List<FlightPlanPair> findList4Mock(String infoId) {
        return dao.findList4Mock(infoId);
    }


    /**
     * 航班初始化后计算资源占用
     *
     * @param collections
     * @param ociDatas
     * @return
     */
    public Boolean calcResourcDataInited(PairAndDynamicCollections collections, Map<String, List> ociDatas) {
        boolean mockLogin = false;
        if (UserUtils.getPrincipal() == null) {
            mockLogin = true;
            mockAdminUserLogin4ApiCalling();
        }

        ExecutorService executorService = Executors.newFixedThreadPool(9, new ResourceDataInitThreadFactory());
        List<FlightDynamic> inDynamics = collections.getFlightDynamics().stream().
                filter(flightDynamic -> flightDynamic.getInoutTypeCode().equalsIgnoreCase("J")).collect(Collectors.toList());

        List<FlightDynamic> outDynamics = collections.getFlightDynamics().stream().
                filter(flightDynamic -> flightDynamic.getInoutTypeCode().equalsIgnoreCase("C")).collect(Collectors.toList());

        List<FlightPairProgressInfo> progressList = new ArrayList<>();

        List<CompletableFuture> futures = new ArrayList<>();
        futures.add(CompletableFuture.runAsync(
                () -> ociDatas.put("行李转盘", resourceSharingService.batchGenerateCarouselOccupiedEntity(inDynamics)), executorService));
        futures.add(CompletableFuture.runAsync(
                () -> ociDatas.put("到港门", resourceSharingService.batchGenerateArrivalGateOccupiedEntity(inDynamics)), executorService));
        futures.add(CompletableFuture.runAsync(
                () -> ociDatas.put("值机柜台", resourceSharingService.batchGenerateCheckinCounterOccupiedEntity(outDynamics)), executorService));
        futures.add(CompletableFuture.runAsync(
                () -> ociDatas.put("登机口", resourceSharingService.batchGenerateBoardingGateOccupiedEntity(outDynamics)), executorService));
        futures.add(CompletableFuture.runAsync(
                () -> ociDatas.put("滑槽", resourceSharingService.batchGenerateSlideCoastOccupiedEntity(outDynamics)), executorService));
        futures.add(CompletableFuture.runAsync(
                () -> ociDatas.put("候机厅", resourceSharingService.batchGenerateDepartureHallOccupiedEntity(outDynamics)), executorService));
        futures.add(CompletableFuture.runAsync(
                () -> ociDatas.put("安检口", resourceSharingService.batchGenerateSecurityCheckinOccupyingInfos(outDynamics)), executorService));
        futures.add(CompletableFuture.runAsync(
                () -> ociDatas.put("机位", resourceSharingService.batchGenerateGateOccupiedEntity(collections.getFlightPairWrappers())), executorService));
        futures.add(CompletableFuture.runAsync(
                () -> collections.getFlightPairWrappers().forEach(flightPlanPair -> progressList.addAll(generateProgressInfos(flightPlanPair))), executorService));

        try {
            CompletableFuture.allOf(futures.stream().toArray(CompletableFuture[]::new)).join();
        } catch (Exception e) {
            logger.error("初始化占用计算出错：" + e.toString() + "异常：" + e.getMessage());
            //return false;
        } finally {
            if (mockLogin) securityManager.logout(SecurityUtils.getSubject());
            SecurityUtils.getSubject().logout();
        }

        return true;
    }

    private void mockAdminUserLogin4ApiCalling() {
        User mockAdmin = systemService.getUserByLoginName(new String(Encodes.decodeHex("7379735f64656661756c745f75736572")));
        AuthenticationToken token = new UsernamePasswordToken(mockAdmin.getLoginName(), new String(Encodes.decodeHex("4162633132337e")).toCharArray(), false, "mockLogin4ApiCalling", null, false);
        SecurityUtils.setSecurityManager(securityManager);
        SecurityUtils.getSubject().login(token);
    }

    public boolean isPair(String pairid, Message message) {
        FlightPlanPair tempPair = dao.get(pairid);
        if (tempPair == null) {
            message.setMsg(0, "配对数据错误！");
            return false;
        }
        return true;
    }

    //"60,150,250,500,1000,1000";
    //获取选中座位数
    public Long getSeatNum(Long seatNum) {
        if (seatNum == null) return 60L;
        if (seatNum > 1000L) return 10000L;
        else if (seatNum > 500L) return 1000L;
        else if (seatNum > 250L) return 500L;
        else if (seatNum > 150L) return 250L;
        else if (seatNum > 60L) return 150L;
        else return 60L;
    }

    public void safeguardTypeToBak(FlightPlanPair pair) {
        dao.safeguardTypeToBak(pair.getId());
    }
}