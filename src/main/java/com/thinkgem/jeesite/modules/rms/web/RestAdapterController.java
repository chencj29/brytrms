package com.thinkgem.jeesite.modules.rms.web;

import cn.net.metadata.te.ResourceDataInitThreadFactory;
import cn.net.metadata.wrapper.PairAndDynamicCollections;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.dao.FlightDynamicDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.FlightPairWrapper;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;
import com.thinkgem.jeesite.modules.rms.service.CarouselOccupyingInfoService;
import com.thinkgem.jeesite.modules.rms.service.FlightPlanPairService;
import com.thinkgem.jeesite.modules.rms.service.ResourceSharingService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.UsernamePasswordToken;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by xiaowu on 16/8/1.
 */
@RestController
@RequestMapping(value = "rest/api")
public class RestAdapterController {
    private static final Logger logger = LoggerFactory.getLogger(RestAdapterController.class);
    @Autowired
    FlightPlanPairService flightPlanPairService;
    @Autowired
    FlightDynamicService flightDynamicService;
    @Autowired
    ResourceSharingService resourceSharingService;
    @Autowired
    CarouselOccupyingInfoService carouselOccupyingInfoService;
    @Autowired
    DefaultSecurityManager securityManager;

    @Autowired
    SystemService systemService;

    @Autowired
    FlightDynamicDao flightDynamicDao;

    @RequestMapping(value = "dynamic2History/{uuid}/{dynamicIds}/{pairIds}", method = RequestMethod.GET)
    public Message dynamics2Histroy(@PathVariable("uuid") String uuid, @PathVariable("dynamicIds") String dynamicIds, @PathVariable("pairIds") String pairIds) {
        Message message = new Message();
        message.setCode(1);
        message.setMessage("123");
        return message;
    }

    /**
     * 航班初始化后计算资源占用时间
     *
     * @param httpEntity
     * @return
     */
    @RequestMapping(value = "calcResourceDataAfterInited", method = RequestMethod.POST)
    public Message calcResourceDataAfterInited(HttpEntity<String> httpEntity) {
        PairAndDynamicCollections collections;
        Message message = new Message();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            collections = objectMapper.readValue(httpEntity.getBody(), PairAndDynamicCollections.class);
        } catch (IOException e) {
            e.printStackTrace();
            message.setCode(-1);
            message.setMessage("资源系统解析数据失败:" + e.getMessage());
            return message;
        }

        Map<String, List> ociDatas = new HashMap<>();

        if (flightPlanPairService.calcResourcDataInited(collections, ociDatas)) {
            message.setMsg(1, "成功");
            message.setResult(ImmutableMap.of("oci", ociDatas));
        }
        return message;
    }


    /**
     * 清除配对信息资源
     *
     * @param httpEntity 航班配对信息
     * @return
     */
    @RequestMapping(value = "clearResourceByPair", method = RequestMethod.POST)
    public Message clearResourceByPair(HttpEntity<FlightPlanPair> httpEntity) {
        Message message = new Message();
        if (httpEntity == null) {
            message.setCode(0);
            message.setMessage("参数不合法!");
            return message;
        }

        resourceSharingService.clearResourceByPair(httpEntity.getBody());
        message.setCode(1);
        message.setMessage("清除资源成功");
        return message;
    }

    /**
     * 更新保障过程
     *
     * @param httpEntity
     * @return
     */
    @RequestMapping(value = "updateSafeguardType", method = RequestMethod.POST)
    public Message updateSafeguardType(HttpEntity<FlightPlanPair> httpEntity) {
        Message message = new Message();

        if (httpEntity == null) {
            message.setCode(0);
            message.setMessage("参数不合法!");
            return message;
        }

        flightPlanPairService.generateProgressInfos(httpEntity.getBody());
        message.setCode(1);
        message.setMessage("更新保障过程成功!");
        return message;
    }

    @RequestMapping(value = "safeguardTypeToBak", method = RequestMethod.POST)
    public Message safeguardTypeToBak(HttpEntity<FlightPlanPair> httpEntity) {
        Message message = new Message();

        if (httpEntity == null) {
            message.setMsg(0, "参数不合法!");
            return message;
        }

        flightPlanPairService.safeguardTypeToBak(httpEntity.getBody());
        message.setMsg(1, "备份保障过程成功!");
        return message;
    }

    /**
     * 更新机位占用时间
     *
     * @param httpEntity
     * @return
     */
    @RequestMapping(value = "updateGateOccupyingTime", method = RequestMethod.POST)
    public Message updateGateOccupyingTime(HttpEntity<FlightPlanPair> httpEntity) {
        Message message = new Message();


        if (httpEntity == null) {
            message.setCode(0);
            message.setMessage("参数不合法!");
            return message;
        }

        boolean isOccupied = resourceSharingService.calcPairAircraftStanOccupyingTime(httpEntity.getBody());

        message.setMessage("保存成功");
        message.setCode(1);
        message.setResult(ImmutableMap.of("occupied", isOccupied));

        return message;
    }

    /**
     * 为动态生成资源占用信息
     * 进港： 行李转盘、到港门
     * 出洪： 值机柜台、安检口、滑槽、登机口、候机厅
     *
     * @param httpEntity
     * @return
     */
    @RequestMapping(value = "generateResourceOccupiedInfoForDynamic", method = RequestMethod.POST)
    public Message generateResourceOccupiedInfoForDynamic(HttpEntity<FlightDynamic> httpEntity) {
        Message message = new Message();

        FlightDynamic flightDynamic = httpEntity.getBody();

        if (flightDynamic == null) {
            message.setCode(0);
            message.setMessage("航班动态参数不合法！");
            return message;
        }

        boolean mockLogin = false;
        if (UserUtils.getPrincipal() == null) {
            mockLogin = true;
            mockAdminUserLogin4ApiCalling();
        }


        if (flightDynamic.getInoutTypeCode().equals("J")) generateInDynamicRes(flightDynamic);
        else if (flightDynamic.getInoutTypeCode().equals("C")) generateOutDynamicRes(flightDynamic);
        else {
            message.setCode(0);
            message.setMessage("无法识别的航班类型");
            return message;
        }

        message.setCode(1);
        if (mockLogin) securityManager.logout(SecurityUtils.getSubject());

        return message;
    }

    private void generateInDynamicRes(FlightDynamic flightDynamic) {
        resourceSharingService.batchGenerateCarouselOccupiedEntity(ImmutableList.of(flightDynamic));
        resourceSharingService.batchGenerateArrivalGateOccupiedEntity(ImmutableList.of(flightDynamic));
    }

    private void generateOutDynamicRes(FlightDynamic flightDynamic) {
        resourceSharingService.batchGenerateCheckinCounterOccupiedEntity(ImmutableList.of(flightDynamic));
        resourceSharingService.batchGenerateSlideCoastOccupiedEntity(ImmutableList.of(flightDynamic));
        resourceSharingService.batchGenerateBoardingGateOccupiedEntity(ImmutableList.of(flightDynamic));
        resourceSharingService.batchGenerateDepartureHallOccupiedEntity(ImmutableList.of(flightDynamic));
        resourceSharingService.batchGenerateSecurityCheckinOccupyingInfos(ImmutableList.of(flightDynamic));
    }

    private void generatePairRes(FlightPlanPair flightPlanPair) { // 生成联班机位及保障过程数据
        //通过存储过程删除占用 wjp_2017年8月9日20时57分
        flightDynamicDao.ociDelete(flightPlanPair.getId());
        resourceSharingService.saveGateoccupiedEntity(flightPlanPair);  // 机位占用数据
        flightPlanPairService.generateProgressInfos(flightPlanPair);    // 保障过程占用数据
    }


    /**
     * 为配对信息生成资源占用信息
     *
     * @return
     */
    @RequestMapping(value = "generateResourceOccupiedInfoForPair", method = RequestMethod.POST)
    public Message generateResourceOccupiedInfoForPair(HttpEntity<FlightPlanPair> httpEntity) {
        Message message = new Message();
        FlightPlanPair flightPlanPair = httpEntity.getBody();
        if (flightPlanPair == null) {
            message.setCode(0);
            message.setMessage("航班配对参数不合法！");
            return message;
        }

        boolean mockLogin = false;
        if (UserUtils.getPrincipal() == null) {
            mockLogin = true;
            mockAdminUserLogin4ApiCalling();
        }

        generatePairRes(flightPlanPair);

        message.setCode(1);
        if (mockLogin) securityManager.logout(SecurityUtils.getSubject());

        message.setCode(1);
        return message;
    }


    @RequestMapping(value = "updateOccAttr", method = RequestMethod.POST)
    public Message updateOccAttr(HttpEntity<FlightDynamic> httpEntity) {
        Message message = new Message();

        FlightDynamic flightDynamic = httpEntity.getBody();

        if (flightDynamic == null) {
            message.setCode(0);
            message.setMessage("航班动态参数不合法！");
            return message;
        }

        List<Message> msgs = new ArrayList<>();
        if (flightDynamic.getInoutTypeCode().equals("J")) {
            msgs.add(resourceSharingService.updateAttrCarousel(flightDynamic));//更新行李转盘占用航班属性
            msgs.add(resourceSharingService.updateAttrArrivalGate(flightDynamic));//更新到港门占用航班属性
        } else if (flightDynamic.getInoutTypeCode().equals("C")) {
            msgs.add(resourceSharingService.updateAttrCheckinCounter(flightDynamic));//更新值机柜台占用航班属性
            msgs.add(resourceSharingService.updateAttrSlideCoast(flightDynamic));//更新滑槽占用航班属性
            msgs.add(resourceSharingService.updateAttrBoardingGate(flightDynamic));//更新登机口占用航班属性
            msgs.add(resourceSharingService.updateAttrDepartureHall(flightDynamic));//更新候机厅占用航班属性
            msgs.add(resourceSharingService.updateAttrSecurityChedkin(flightDynamic));//更新安检口占用航班属性
        } else {
            message.setCode(0);
            message.setMessage("无法识别的航班类型");
            return message;
        }

        List<String> msgList = msgs.stream().filter(tmp -> (tmp.getCode() != 1)).map(tmp -> tmp.getMessage()).collect(Collectors.toList());
        if (msgList != null && msgList.size() > 0) {
            String msg = StringUtils.join(msgList, ",");
            logger.info("更新占用的航班属性：" + msg);
            message.setMessage(msg);
            return message;
        } else {
            message.setCode(1);
        }

        return message;
    }

    /**
     * 获取保障总时长
     *
     * @param httpEntity
     * @return
     */
    @RequestMapping(value = "getSafeguardTotalLongTime", method = RequestMethod.POST)
    public Message getSafeguardTotalLongTime(HttpEntity<String> httpEntity) {
        ObjectMapper objectMapper = new ObjectMapper();

        Message message = new Message();
        try {
            FlightPlanPair pair = objectMapper.readValue(httpEntity.getBody(), FlightPlanPair.class);
            if (pair == null) {
                message.setCode(0);
                message.setMessage("航班配对参数不合法！");
                return message;
            }

            Long longTime = flightPlanPairService.getSafeguardTotalLongTime(pair);
            message.setMsg(1, "成功！");
            message.setResult(ImmutableMap.of("longTime", longTime));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * 根据预飞、预达，实飞、实达时间更新资源占用表中的开始占用时间后结束占用时间；航班配对信息和航班动态信息
     *
     * @param httpEntity 配对和实体信息
     * @return
     */
    @RequestMapping(value = "updateTimeResourceByPiar", method = RequestMethod.POST)
    public Message updateTimeResourceByPiar(HttpEntity<PairAndFlightDynamic> httpEntity) {
        Message message = new Message();
        PairAndFlightDynamic pairAndFlightDynamic = httpEntity.getBody();
        FlightPlanPair flightPlanPair = pairAndFlightDynamic.getFlightPlanPair();
        FlightDynamic flightDynamic = pairAndFlightDynamic.getFlightDynamic();
        if (flightPlanPair == null || flightDynamic == null) {
            message.setMessage("航班动态参数不合法！");
            return message;
        }

        boolean mockLogin = false;
        if (UserUtils.getPrincipal() == null) {
            mockLogin = true;
            mockAdminUserLogin4ApiCalling();
        }


        List<Message> msgs = new ArrayList<>();
        try {
            msgs.add(resourceSharingService.updateTimeGate(flightPlanPair, flightDynamic));//更新机位占用时间
            msgs.add(flightPlanPairService.updateTimeProgress(flightPlanPair));//更新运输调度占用时间

            if (flightDynamic.getInoutTypeCode().equals("J")) {
                msgs.add(resourceSharingService.updateTimeCarousel(flightDynamic));//更新行李转盘占用时间
                msgs.add(resourceSharingService.updateTimeArrivalGate(flightDynamic));//更新到港门占用时间
            } else if (flightDynamic.getInoutTypeCode().equals("C")) {
                msgs.add(resourceSharingService.updateTimeCheckinCounter(flightDynamic));//更新值机柜台占用时间
                msgs.add(resourceSharingService.updateTimeSlideCoast(flightDynamic));//更新滑槽占用时间
                msgs.add(resourceSharingService.updateTimeBoardingGate(flightDynamic));//更新登机口占用时间
                msgs.add(resourceSharingService.updateTimeDepartureHall(flightDynamic));//更新候机厅占用时间
                msgs.add(resourceSharingService.updateTimeSecurityChedkin(flightDynamic));//更新安检口占用时间
            } else {
                message.setMessage("无法识别的航班类型");
                return message;
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage("更新相应资源错误");
        } finally {
            if (mockLogin) securityManager.logout(SecurityUtils.getSubject());
        }

        List<String> msgList = msgs.stream().filter(tmp -> (tmp.getCode() != 1)).map(tmp -> tmp.getMessage()).collect(Collectors.toList());
        if (msgList != null && msgList.size() > 0) {
            String msg = StringUtils.join(msgList, ",");
            logger.info("更新占用时间：" + msg);
            message.setMessage(msg);
            return message;
        } else {
            message.setCode(1);
        }
        return message;
    }

    private void mockAdminUserLogin4ApiCalling() {
        User mockAdmin = systemService.getUserByLoginName(new String(Encodes.decodeHex("7379735f64656661756c745f75736572")));
        AuthenticationToken token = new UsernamePasswordToken(mockAdmin.getLoginName(), new String(Encodes.decodeHex("4162633132337e")).toCharArray(), false, "mockLogin4ApiCalling", null, false);
        SecurityUtils.setSecurityManager(securityManager);
        SecurityUtils.getSubject().login(token);
    }
}

//包装配对与动态的
class PairAndFlightDynamic {
    private FlightPlanPair flightPlanPair;
    private FlightDynamic flightDynamic;

    public FlightPlanPair getFlightPlanPair() {
        return flightPlanPair;
    }

    public void setFlightPlanPair(FlightPlanPair flightPlanPair) {
        this.flightPlanPair = flightPlanPair;
    }

    public FlightDynamic getFlightDynamic() {
        return flightDynamic;
    }

    public void setFlightDynamic(FlightDynamic flightDynamic) {
        this.flightDynamic = flightDynamic;
    }
}