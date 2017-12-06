/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicHistoryService;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.dao.RmsFlightDutyDao;
import com.thinkgem.jeesite.modules.rms.dao.RmsHiFlightDutyDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightDuty;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightDutyWrap;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;

/**
 * 值机数据管理Service
 *
 * @author dingshuang
 * @version 2016-05-18
 */
@Service
@Transactional(readOnly = true)
public class RmsFlightDutyService extends CrudService<RmsFlightDutyDao, RmsFlightDuty> {

    @Autowired
    FlightDynamicService flightDynamicService;

    @Autowired
    FlightDynamicHistoryService flightDynamicHistoryService;

    @Autowired
    RmsHiFlightDutyDao rmsHiFlightDutyDao;

    public RmsFlightDuty get(String id) {
        return super.get(id);
    }

    public List<RmsFlightDuty> findList(RmsFlightDuty rmsFlightDuty) {
        return super.findList(rmsFlightDuty);
    }

    public Page<RmsFlightDuty> findPage(Page<RmsFlightDuty> page, RmsFlightDuty rmsFlightDuty) {
        return super.findPage(page, rmsFlightDuty);
    }

    @Transactional(readOnly = false)
    public void save(RmsFlightDuty rmsFlightDuty, Message message) {
        super.save(rmsFlightDuty);
        try {
            FlightDynamic flightDynamic = flightDynamicService.get(rmsFlightDuty.getFlightDynamicId());
            if (flightDynamic != null)
                message.setMsg(1, "添加或更新值机数据：" + LogUtils.msgFlightDynamic(flightDynamic) + "," + rmsFlightDuty.toString());
        } catch (Exception e) {
            logger.error("保存值机数据(hiSaveManifestSecs)出错");
            //e.printStackTrace();
        }
    }

    @Transactional(readOnly = false)
    public void delete(RmsFlightDuty rmsFlightDuty) {
        super.delete(rmsFlightDuty);
    }

    /**
     * 获取目的站的值机器数据  历史
     *
     * @param rmsFlightDutyWrap
     * @return
     */
    public List<RmsFlightDutyWrap> findHiDutyWrapList(RmsFlightDutyWrap rmsFlightDutyWrap, String dateStr) {
        List<RmsFlightDutyWrap> dutyWraps = new ArrayList<RmsFlightDutyWrap>();
        if (StringUtils.isBlank(dateStr)) return dutyWraps;

        try {
            Date planDate = DateUtils.parseDate(dateStr, "yyyy-MM-dd");
            FlightDynamic paramDynamic = new FlightDynamic();
            paramDynamic.setInoutTypeCode("C");
            paramDynamic.setPlanDate(planDate);
            List<FlightDynamic> dynamics = flightDynamicHistoryService.findList(paramDynamic);
            dutyWrapList(dynamics, dutyWraps, true);
            return dutyWraps;
        } catch (ParseException e) {
            logger.error("获取历史值机数据，日期转换出错");
        }

        return dutyWraps;
    }

    /**
     * 获取目的站的值机器数据
     *
     * @param rmsFlightDutyWrap
     * @return
     */
    public List<RmsFlightDutyWrap> findDutyWrapList(RmsFlightDutyWrap rmsFlightDutyWrap) {
        List<RmsFlightDutyWrap> dutyWraps = new ArrayList<RmsFlightDutyWrap>();

        FlightDynamic paramDynamic = new FlightDynamic();
        paramDynamic.setInoutTypeCode("C");
        List<FlightDynamic> dynamics = flightDynamicService.findListSimple(paramDynamic);
        dutyWrapList(dynamics, dutyWraps, false);

        return dutyWraps;
    }

    public void dutyWrapList(List<FlightDynamic> dynamics, List<RmsFlightDutyWrap> dutyWraps, boolean isHistory) {
        if (!Collections3.isEmpty(dynamics)) {
            for (FlightDynamic dynamic : dynamics) {
                RmsFlightDutyWrap enty = new RmsFlightDutyWrap();
                List<RmsFlightDuty> flightDutys = null;
                if (isHistory) {
                    flightDutys = rmsHiFlightDutyDao.getFlightDutysByFlightDynamicId(dynamic.getId());
                } else {
                    flightDutys = dao.getFlightDutysByFlightDynamicId(dynamic.getId());
                }
                if (!Collections3.isEmpty(flightDutys)) {
//                    RmsFlightDuty flightDuty = flightDutys.get(0);
//                    //将本站出港数据复制到知己数据列表中
//                    BeanUtils.copyProperties(flightDuty, enty);
//                    int travellersAllCount = this.caculateTravellersAllCount(flightDuty);
//                    enty.setTravellersAllCount(travellersAllCount);//计算旅客总数
                    for (RmsFlightDuty flightDuty : flightDutys) { //wjp_2017年9月8日14时37分 修改
                        int travellersAllCount = this.caculateTravellersAllCount(flightDuty);
                        //计算旅客总数
                        enty.setTravellersAllCount(enty.getTravellersAllCount() != null ?
                                (travellersAllCount + enty.getTravellersAllCount()) : travellersAllCount);

                        //计算各分支总人数
                        enty.setPersonCount(enty.getPersonCount() != null ?
                                (enty.getPersonCount() + flightDuty.getPersonCount()) : flightDuty.getPersonCount());
                        enty.setBabyCount(enty.getBabyCount() != null ?
                                (enty.getBabyCount() + flightDuty.getBabyCount()) : flightDuty.getBabyCount());
                        enty.setChildCount(enty.getChildCount() != null ?
                                (enty.getChildCount() + flightDuty.getChildCount()) : flightDuty.getChildCount());
                        enty.setVipPersonCount(enty.getVipPersonCount() != null ?
                                (enty.getVipPersonCount() + flightDuty.getVipPersonCount()) : flightDuty.getVipPersonCount());


                        //计算直达、转运行李总数
                        enty.setNonstopLuggageCount(enty.getNonstopLuggageCount() != null ?
                                (enty.getNonstopLuggageCount() + flightDuty.getNonstopLuggageCount()) : flightDuty.getNonstopLuggageCount());
                        enty.setTransferLuggageCount(enty.getTransferLuggageCount() != null ?
                                (enty.getTransferLuggageCount() + flightDuty.getTransferLuggageCount()) : flightDuty.getTransferLuggageCount());
                        //vip行李总数
                        enty.setVipLuggageCount(enty.getVipLuggageCount() != null ?
                                (enty.getVipLuggageCount() + flightDuty.getVipLuggageCount()) : flightDuty.getVipLuggageCount());

                        //计算直达、转运行李总重量
                        enty.setNonstopLuggageWeight(enty.getNonstopLuggageWeight() != null ?
                                (enty.getNonstopLuggageWeight() + flightDuty.getNonstopLuggageWeight()) : flightDuty.getNonstopLuggageWeight());
                        enty.setTransferLuggageWeight(enty.getTransferLuggageWeight() != null ?
                                (enty.getTransferLuggageWeight() + flightDuty.getTransferLuggageWeight()) : flightDuty.getTransferLuggageWeight());

                        //头等舱总人数
                        enty.setFirstCabinPersonCount(enty.getFirstCabinPersonCount() != null ?
                                (enty.getFirstCabinPersonCount() + flightDuty.getFirstCabinPersonCount()) : flightDuty.getFirstCabinPersonCount());

                        //公务舱总人数
                        enty.setBusinessCabinPersonCount(enty.getBusinessCabinPersonCount() != null ?
                                (enty.getBusinessCabinPersonCount() + flightDuty.getBusinessCabinPersonCount()) : flightDuty.getBusinessCabinPersonCount());
                    }
                }
                enty.setAllLuggageCount(enty.getNonstopLuggageCount() + enty.getTransferLuggageCount());
                //将航班动态数据复制到值机列表中
                BeanUtils.copyProperties(dynamic, enty);
                //todo 是否编号加名称 wjp_2017年9月17日14时25分
//                if (StringUtils.isNotBlank(dynamic.getPassAirport1Code()))
//                    enty.setPassAirport1Code(dynamic.getPassAirport1Code() + dynamic.getPassAirport1Name());
//                if (StringUtils.isNotBlank(dynamic.getPassAirport2Code()))
//                    enty.setPassAirport2Code(dynamic.getPassAirport2Code() + dynamic.getPassAirport2Name());

                enty.setArrivalAirportCode(dynamic.getArrivalAirportCode() + dynamic.getArrivalAirportName());
                dutyWraps.add(enty);
            }
        }
    }


    /**
     * 该方法用于计算旅客总数
     *
     * @param flightDuty
     * @return
     */
    private int caculateTravellersAllCount(RmsFlightDuty flightDuty) {
        int count = 0;
        if (flightDuty != null) {
            int childCount = flightDuty.getChildCount();
            int personCount = flightDuty.getPersonCount();
            int babyCount = flightDuty.getBabyCount();
            count = childCount + personCount + babyCount;
        }
        return count;
    }


    /**
     * 该方法用于获取某个航班的某个落点所有值机数据
     *
     * @param rmsFlightDuty
     * @return
     */
    public List<RmsFlightDuty> getFlightDutys(RmsFlightDuty rmsFlightDuty) {
        return dao.getFlightDutys(rmsFlightDuty);
    }

    public void getHiFlightDutys(String flightDynamicId, Message message) {
        try {
            //通过FlightDynamicId 获取该航班下的所有值机数据，没有数据的则自动补充
            List<RmsFlightDuty> flightDutys = new ArrayList<RmsFlightDuty>();
            //根据航班情况，计算航线信息,补充航某些航段没有的数据
            FlightDynamic dynamic = flightDynamicHistoryService.getHis(flightDynamicId);
            String passAirport1Code = dynamic.getPassAirport1Code();
            String passAirport2Code = dynamic.getPassAirport2Code();
            String arrivalAirCode = dynamic.getArrivalAirportCode();
            //经停地1
            setFlightDutysEntry(passAirport1Code, dynamic.getPassAirport1Name(), "PASS1", flightDynamicId, flightDutys, true);
            //经停地2
            setFlightDutysEntry(passAirport2Code, dynamic.getPassAirport2Name(), "PASS2", flightDynamicId, flightDutys, true);
            //目的地
            setFlightDutysEntry(arrivalAirCode, dynamic.getArrivalAirportName(), "ARRI", flightDynamicId, flightDutys, true);
            message.setCode(1);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("flightDutys", flightDutys);
            message.setResult(map);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
    }

    /**
     * 该方法用于填充航段值机数据
     *
     * @param addr
     * @param addrNature
     * @param flightDynamicId
     * @param flightDutys
     * @return
     */
    private List<RmsFlightDuty> setFlightDutysEntry(String addrTwoCode, String addr, String addrNature, String flightDynamicId, List<RmsFlightDuty> flightDutys, boolean isHistory) {
        RmsFlightDuty rmsFlightDuty = new RmsFlightDuty();
        rmsFlightDuty.setAddrTwoNature(addrNature);
        rmsFlightDuty.setFlightDynamicId(flightDynamicId);
        rmsFlightDuty.setAddr(addr);
        rmsFlightDuty.setAddrTwoCode(addrTwoCode);
        //如果地址(经停、到站等)不为空，查询此航段的数据.否则补足空数据
        if (StringUtils.isNotBlank(addrTwoCode)) {
            List<RmsFlightDuty> resultDutys = null;
            if (isHistory) {
                resultDutys = rmsHiFlightDutyDao.getFlightDutys(rmsFlightDuty);
            } else {
                resultDutys = this.getFlightDutys(rmsFlightDuty);
            }

            if (resultDutys != null && resultDutys.size() > 0) {
                flightDutys.add(resultDutys.get(0));
            } else {
                //查询没有值机数据，补入空数据
                flightDutys.add(rmsFlightDuty);
            }
        } else {
            flightDutys.add(rmsFlightDuty);
        }
        return flightDutys;
    }

    @Transactional(readOnly = false)
    public void hiSave(RmsFlightDuty rmsFlightDuty, Message message) {
        if (StringUtils.isBlank(rmsFlightDuty.getId())) {
            List<RmsFlightDuty> flightDutys = rmsHiFlightDutyDao.findByFdidAndAddr(rmsFlightDuty);
            if (!Collections3.isEmpty(flightDutys)) rmsFlightDuty.setId(flightDutys.get(0).getId());
        }

        if (rmsFlightDuty.getIsNewRecord()) {
            rmsFlightDuty.preInsert();
            rmsHiFlightDutyDao.insert(rmsFlightDuty);
        } else {
            rmsFlightDuty.preUpdate();
            rmsHiFlightDutyDao.update(rmsFlightDuty);
        }

        try {
            FlightDynamic flightDynamic = flightDynamicHistoryService.getHis(rmsFlightDuty.getFlightDynamicId());
            if (flightDynamic != null)
                message.setMsg(1, "添加或更新值机历史数据：" + LogUtils.msgFlightDynamic(flightDynamic) + "," + rmsFlightDuty.toString());
        } catch (Exception e) {
            logger.error("保存值机历史数据(hiSaveManifestSecs)出错");
            message.setMessage("保存失败");
            //e.printStackTrace();
        }
    }

    public List<RmsFlightDuty> findByFdidAndAddr(RmsFlightDuty rmsFlightDuty) {
        return dao.findByFdidAndAddr(rmsFlightDuty);
    }
}