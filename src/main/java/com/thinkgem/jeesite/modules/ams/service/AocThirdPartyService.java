/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import cn.net.metadata.datasync.socketio.ConcurrentClientsHolder;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.PropertiesLoader;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.dao.AocThirdPartyDao;
import com.thinkgem.jeesite.modules.ams.entity.AocThirdParty;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.dao.FlightPairProgressInfoDao;
import com.thinkgem.jeesite.modules.rms.entity.FlightPairProgressInfo;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;
import com.thinkgem.jeesite.modules.rms.service.FlightPlanPairService;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 空管动态变更表Service
 *
 * @author wjp
 * @version 2017-05-22
 */
@Service
@Transactional(readOnly = true)
public class AocThirdPartyService extends CrudService<AocThirdPartyDao, AocThirdParty> {

    @Autowired
    FlightDynamicService flightDynamicService;
    @Autowired
    private FlightPairProgressInfoDao flightPairProgressInfoDao;
    @Autowired
    private FlightPlanPairService flightPlanPairService;

    private final static PropertiesLoader REALTIME_DYNAMIC_MESSAGE_CONFS = new PropertiesLoader("classpath:notification.realtime-dynamic-message-conf/config.properties");

    public AocThirdParty get(String id) {
        return super.get(id);
    }

    public List<AocThirdParty> findList(AocThirdParty aocThirdParty) {
        return super.findList(aocThirdParty);
    }

    public List<AocThirdParty> findAllList(AocThirdParty aocThirdParty) {
        return dao.findAllList(aocThirdParty);
    }

    public Page<AocThirdParty> findPage(Page<AocThirdParty> page, AocThirdParty aocThirdParty) {
        return super.findPage(page, aocThirdParty);
    }

    @Transactional(readOnly = false)
    public void save(AocThirdParty aocThirdParty) {
        super.save(aocThirdParty);
    }

    @Transactional(readOnly = false)
    public void delete(AocThirdParty aocThirdParty) {
        super.delete(aocThirdParty);
    }

    @Transactional(readOnly = false)
    public void updateList(ArrayList<String> ids, ArrayList<String> flightDynamicIds, ArrayList<String> descs, String adviceFlag, String adviceBy, Message msg) {
        if (isCheck(ids)) {
            msg.setMsg(0, "审核无效！你选中的项目，已被审核过！");
            return;
        }
        //todo 解析调用相关方法发送日志
        if (StringUtils.equals("1", adviceFlag)) {
            for (int i = 0; i < ids.size(); i++) {
                String id = ids.get(i);
                AocThirdParty aocThirdParty = get(id);
                if (aocThirdParty != null) {
                    String processInfoJson = aocThirdParty.getExt3();
                    FlightPairProgressInfo infoParms = JSONObject.parseObject(processInfoJson, FlightPairProgressInfo.class);
                    infoParms.setPairId(flightDynamicIds.get(i));
                    FlightPairProgressInfo progressInfo = flightPairProgressInfoDao.getProgressInfo(infoParms);
                    if(progressInfo != null) {
                        if (infoParms.getActualStartTime() != null) {
                            String oldStart = DateUtils.formatDate(progressInfo.getActualStartTime(), "yyyy-MM-dd HH:mm");
                            progressInfo.setActualStartTime(infoParms.getActualStartTime());
                            if (StringUtils.equals(oldStart, DateUtils.formatDate(progressInfo.getActualStartTime(), "yyyy-MM-dd HH:mm")))
                                progressActTimeMessageSender("PROGRESS_ACTUAL_START_TIME_RECORDING", progressInfo, oldStart);
                        }
                        if (infoParms.getActualOverTime() != null) {
                            String oldOver = DateUtils.formatDate(progressInfo.getActualOverTime());
                            progressInfo.setActualOverTime(infoParms.getActualOverTime());
                            if (StringUtils.equals(oldOver, DateUtils.formatDate(progressInfo.getActualStartTime(), "yyyy-MM-dd HH:mm")))
                                progressActTimeMessageSender("PROGRESS_ACTUAL_OVER_TIME_RECORDING", progressInfo, oldOver);
                        }

                        if (infoParms.getOpName() != null) progressInfo.setOpName(infoParms.getOpName());
                        if (infoParms.getSpecialCarCode() != null)
                            progressInfo.setSpecialCarCode(infoParms.getSpecialCarCode());
                        if (infoParms.getSpecialCarType() != null)
                            progressInfo.setSpecialCarType(infoParms.getSpecialCarType());

                        progressInfo.setRandom(UUID.randomUUID().toString());
                        flightPairProgressInfoDao.update(progressInfo);

                        LogUtils.type2Save("", "空管消息：" + aocThirdParty.getDescription());

                        aocThirdParty.setAdviceFlag(adviceFlag);
                        aocThirdParty.setDaviceBy(adviceBy);
                        aocThirdParty.setExt2(descs.get(i));//记录选择的动态描述
                        dao.update(aocThirdParty);
                    }else {
                        LogUtils.type2Save("", "审核出错！查询进程对象失败！");
                        msg.setMsg(0,"审核出错！");
                        return;
                    }
                }
            }
        } else {
            dao.updateList(ImmutableMap.of("ids", ids, "adviceFlag", adviceFlag, "adviceBy", adviceBy));
        }
        msg.setMsg(1, "审核成功!");
    }

    /**
     * 判断是否已审核过
     *
     * @param ids
     * @return
     */
    public Boolean isCheck(ArrayList<String> ids) {
        int i = dao.isChackAll(ImmutableMap.of("ids", ids));
        return i == 0 ? false : true;
    }

    private void progressActTimeMessageSender(String fmtName, FlightPairProgressInfo info, String time) {
        FlightPlanPair flightPlanPair = flightPlanPairService.get(info.getPairId());

        if (flightPlanPair != null) {
            String flightNumber = "";
            if (StringUtils.isNotEmpty(flightPlanPair.getFlightNum2()))
                flightNumber += flightPlanPair.getFlightCompanyCode2() + flightPlanPair.getFlightNum2();
            if (StringUtils.isNotEmpty(flightPlanPair.getFlightNum2()) && StringUtils.isNotEmpty(flightPlanPair.getFlightNum()))
                flightNumber += "/";
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(flightPlanPair.getFlightNum()))
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
}