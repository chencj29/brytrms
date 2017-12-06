/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import cn.net.metadata.annotation.Check;
import cn.net.metadata.datasync.socketio.ConcurrentClientsHolder;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.dao.*;
import com.thinkgem.jeesite.modules.rms.entity.*;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源模拟分配Service
 *
 * @author BigBrother5
 * @version 2017-03-09
 */
@Service
@Transactional(readOnly = true)
public class ResourceMockDistInfoService extends CrudService<ResourceMockDistInfoDao, ResourceMockDistInfo> {

    @Autowired
    FlightDynamicService flightDynamicService;

    @Autowired
    FlightPlanPairService flightPlanPairService;

    @Autowired
    ArrivalGateOccupyingInfoDao arrivalGateOccupyingInfoDao;

    @Autowired
    BoardingGateOccupyingInfoDao boardingGateOccupyingInfoDao;

    @Autowired
    CarouselOccupyingInfoDao carouselOccupyingInfoDao;

    @Autowired
    SlideCoastOccupyingInfoDao slideCoastOccupyingInfoDao;

    @Autowired
    CheckingCounterOccupyingInfoDao checkingCounterOccupyingInfoDao;

    @Autowired
    SecurityCheckinOccupyingInfoDao securityCheckinOccupyingInfoDao;

    @Autowired
    DepartureHallOccupyingInfoDao departureHallOccupyingInfoDao;

    @Autowired
    GateOccupyingInfoDao gateOccupyingInfoDao;

    public ResourceMockDistInfo get(String id) {
        return super.get(id);
    }

    public List<ResourceMockDistInfo> findList(ResourceMockDistInfo resourceMockDistInfo) {
        return super.findList(resourceMockDistInfo);
    }

    public Page<ResourceMockDistInfo> findPage(Page<ResourceMockDistInfo> page, ResourceMockDistInfo resourceMockDistInfo) {
        return super.findPage(page, resourceMockDistInfo);
    }

    @Transactional
    public void save(ResourceMockDistInfo resourceMockDistInfo) {
        super.save(resourceMockDistInfo);
    }

    @Transactional
    public void delete(ResourceMockDistInfo resourceMockDistInfo) {
        super.delete(resourceMockDistInfo);
    }

    @Transactional(readOnly = true)
    public List<ResourceMockDistInfo> listByUser(Map<String, Object> params) {
        return dao.listByUser(params);
    }

    @Transactional
    @Check(operationName = "机位模拟分配发布")
    public Message publishMockData4Gate(List<ResourceMockDistDetail> details, String random) {
        Message message = new Message(1, "发布成功");
        try {
            for (ResourceMockDistDetail detail : details) {
                if (StringUtils.isBlank(detail.getId()) || StringUtils.isBlank(detail.getDataId())) continue;
                FlightPlanPair flightPlanPair = flightPlanPairService.get(detail.getDataId());
                if (null == flightPlanPair) continue;

                flightPlanPair.setRandom(random);
                flightPlanPair.setPlaceNum(detail.getInte());
                flightPlanPairService.save(flightPlanPair);
                flightDynamicService.updateFlightDynamicInfoAfterAircraftDistributed(flightPlanPair);

                GateOccupyingInfo oci =  gateOccupyingInfoDao.getByFlightDynamicId(detail.getDataId());
                if(oci == null) continue;

                oci.setRandom(random);
                oci.setActualGateNum(flightPlanPair.getPlaceNum());
                gateOccupyingInfoDao.update(oci);
            }

            deliverResourcePublishMessage("机位");
        } catch (Exception e) {
            message.setCode(0);
            message.setMessage(e.getMessage());
        }

        return message;
    }

    @Transactional
    @Check(operationName = "到港门模拟分配发布")
    public Message publishMockData4ArrivalGate(List<ResourceMockDistDetail> details, String random) {
        Message message = new Message(1, "发布成功");
        try {
            for (ResourceMockDistDetail detail : details) {
                if (StringUtils.isBlank(detail.getId())) continue;
                ArrivalGateOccupyingInfo occupyingInfo = arrivalGateOccupyingInfoDao.getByFlightDynamicId(detail.getDataId());
                if (null == occupyingInfo) continue;

                occupyingInfo.setRandom(random);
                occupyingInfo.setInteArrivalGateCode(detail.getInte());
                occupyingInfo.setIntlArrivalGateCode(detail.getIntl());

                arrivalGateOccupyingInfoDao.update(occupyingInfo);
            }

            deliverResourcePublishMessage("到港门");
        } catch (Exception e) {
            message.setCode(0);
            message.setMessage(e.getMessage());
        }

        return message;
    }

    @Transactional
    @Check(operationName = "登机口模拟分配发布")
    public Message publishMockData4BoardingGate(List<ResourceMockDistDetail> details, String random) {
        Message message = new Message(1, "发布成功");
        try {
            for (ResourceMockDistDetail detail : details) {
                if (StringUtils.isBlank(detail.getId())) continue;
                BoardingGateOccupyingInfo occupyingInfo = boardingGateOccupyingInfoDao.getByFlightDynamicId(detail.getDataId());
                if (null == occupyingInfo) continue;

                occupyingInfo.setRandom(random);
                occupyingInfo.setInteBoardingGateCode(detail.getInte());
                occupyingInfo.setIntlBoardingGateCode(detail.getIntl());

                boardingGateOccupyingInfoDao.update(occupyingInfo);
            }

            deliverResourcePublishMessage("登机口");
        } catch (Exception e) {
            message.setCode(0);
            message.setMessage(e.getMessage());
        }

        return message;
    }

    @Transactional
    @Check(operationName = "行李转盘模拟分配发布")
    public Message publishMOckData4Carousel(List<ResourceMockDistDetail> details, String random) {
        Message message = new Message(1, "发布成功");
        try {
            for (ResourceMockDistDetail detail : details) {
                if (StringUtils.isBlank(detail.getId())) continue;
                CarouselOccupyingInfo occupyingInfo = carouselOccupyingInfoDao.getByFlightDynamicId(detail.getDataId());
                if (null == occupyingInfo) continue;

                occupyingInfo.setRandom(random);
                occupyingInfo.setInteCarouselCode(detail.getInte());
                occupyingInfo.setIntlCarouselCode(detail.getIntl());

                carouselOccupyingInfoDao.update(occupyingInfo);
            }

            deliverResourcePublishMessage("行李转盘");
        } catch (Exception e) {
            message.setCode(0);
            message.setMessage(e.getMessage());
        }

        return message;
    }

    @Transactional
    @Check(operationName = "滑槽模拟分配发布")
    public Message publishMockData4SlideCoast(List<ResourceMockDistDetail> details, String random) {
        Message message = new Message(1, "发布成功");
        try {
            for (ResourceMockDistDetail detail : details) {
                if (StringUtils.isBlank(detail.getId())) continue;
                SlideCoastOccupyingInfo occupyingInfo = slideCoastOccupyingInfoDao.getByFlightDynamicId(detail.getDataId());
                if (null == occupyingInfo) continue;

                occupyingInfo.setRandom(random);
                occupyingInfo.setInteSlideCoastCode(detail.getInte());
                occupyingInfo.setIntlSlideCoastCode(detail.getIntl());

                slideCoastOccupyingInfoDao.update(occupyingInfo);
            }

            deliverResourcePublishMessage("滑槽");
        } catch (Exception e) {
            message.setCode(0);
            message.setMessage(e.getMessage());
        }

        return message;
    }

    @Transactional
    @Check(operationName = "值机柜台模拟分配发布")
    public Message publishMockData4CheckinCounter(List<ResourceMockDistDetail> details, String random) {
        Message message = new Message(1, "发布成功");
        try {
            for (ResourceMockDistDetail detail : details) {
                if (StringUtils.isBlank(detail.getId())) continue;
                CheckingCounterOccupyingInfo occupyingInfo = checkingCounterOccupyingInfoDao.getByFlightDynamicId(detail.getDataId());
                if (null == occupyingInfo) continue;

                occupyingInfo.setRandom(random);
                occupyingInfo.setInteCheckingCounterCode(detail.getInte());
                occupyingInfo.setIntlCheckingCounterCode(detail.getIntl());

                checkingCounterOccupyingInfoDao.update(occupyingInfo);
            }

            deliverResourcePublishMessage("值机柜台");
        } catch (Exception e) {
            message.setMsg(0,e.getMessage());
        }

        return message;
    }

    @Transactional
    @Check(operationName = "安检口模拟分配发布")
    public Message publishMockData4SecurityCheckin(List<ResourceMockDistDetail> details, String random) {
        Message message = new Message(1, "发布成功");
        try {
            for (ResourceMockDistDetail detail : details) {
                if (StringUtils.isBlank(detail.getId())) continue;
                SecurityCheckinOccupyingInfo occupyingInfo = securityCheckinOccupyingInfoDao.getByFlightDynamicId(detail.getDataId());
                if (null == occupyingInfo) continue;

                occupyingInfo.setRandom(random);
                occupyingInfo.setInteSecurityCheckinCode(detail.getInte());
                occupyingInfo.setIntlSecurityCheckinCode(detail.getIntl());

                securityCheckinOccupyingInfoDao.update(occupyingInfo);
            }

            deliverResourcePublishMessage("安检口");
        } catch (Exception e) {
            message.setCode(0);
            message.setMessage(e.getMessage());
        }

        return message;
    }

    @Transactional
    @Check(operationName = "候机厅模拟分配发布")
    public Message publishMockData4DepartureHall(List<ResourceMockDistDetail> details, String random) {
        Message message = new Message(1, "发布成功");
        try {
            for (ResourceMockDistDetail detail : details) {
                if (StringUtils.isBlank(detail.getId())) continue;
                DepartureHallOccupyingInfo occupyingInfo = departureHallOccupyingInfoDao.getByFlightDynamicId(detail.getDataId());
                if (null == occupyingInfo) continue;

                occupyingInfo.setRandom(random);
                occupyingInfo.setInteDepartureHallCode(detail.getInte());
                occupyingInfo.setIntlDepartureHallCode(detail.getIntl());

                departureHallOccupyingInfoDao.update(occupyingInfo);
            }

            deliverResourcePublishMessage("候机厅");
        } catch (Exception e) {
            message.setCode(0);
            message.setMessage(e.getMessage());
        }

        return message;
    }

    private void deliverResourcePublishMessage(String resourceType) {
        Map<String, Object> params = new HashMap<>();
        params.put("occur", System.currentTimeMillis());
        params.put("message", StringUtils.tpl("{}发布了{}模拟分配信息", UserUtils.getUser().getName(), resourceType));

        try {
            ConcurrentClientsHolder.getSocket("/dynamic_realtime_message").emit("dynamic_message_dispatcher", new ObjectMapper().writeValueAsString(params));
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error("广播{模拟分配发布}成功消息时出现异常");
            e.printStackTrace();
        }
    }
}