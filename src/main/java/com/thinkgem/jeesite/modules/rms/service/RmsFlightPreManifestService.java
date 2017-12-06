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
import com.thinkgem.jeesite.modules.rms.dao.RmsFlightPreManifestDao;
import com.thinkgem.jeesite.modules.rms.dao.RmsHiFlightPreManifestDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightPreManifest;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;

/**
 * 上站舱单数据管理Service
 *
 * @author dingshuang
 * @version 2016-05-21
 */
@Service
@Transactional(readOnly = true)
public class RmsFlightPreManifestService extends CrudService<RmsFlightPreManifestDao, RmsFlightPreManifest> {

    @Autowired
    private FlightDynamicService flightDynamicService;

    @Autowired
    FlightDynamicHistoryService flightDynamicHistoryService;

    @Autowired
    RmsHiFlightPreManifestDao rmsHiFlightPreManifestDao;

    public RmsFlightPreManifest get(String id) {
        return super.get(id);
    }

    public List<RmsFlightPreManifest> findList(RmsFlightPreManifest rmsFlightPreManifest) {
        return super.findList(rmsFlightPreManifest);
    }

    public Page<RmsFlightPreManifest> findPage(Page<RmsFlightPreManifest> page, RmsFlightPreManifest rmsFlightPreManifest) {
        return super.findPage(page, rmsFlightPreManifest);
    }

    @Transactional(readOnly = false)
    public void save(RmsFlightPreManifest rmsFlightPreManifest, Message message) {
        super.save(rmsFlightPreManifest);

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(rmsFlightPreManifest.getFlightDynamicId());
            if (flightDynamic != null)
                message.setMsg(1, "添加或更新上站舱单数据：" + LogUtils.msgFlightDynamic(flightDynamic) + "," + rmsFlightPreManifest.toString());
        } catch (Exception e) {
            logger.error("保存上站舱单数据(hiSaveManifestSecs)出错");
            message.setMessage("保存失败");
            //e.printStackTrace();
        }

    }

    @Transactional(readOnly = false)
    public void delete(RmsFlightPreManifest rmsFlightPreManifest) {
        super.delete(rmsFlightPreManifest);
    }


    public List<RmsFlightPreManifest> findFlightPreManifestList(RmsFlightPreManifest rmsFlightPreManifest) {
        List<RmsFlightPreManifest> preNanifs = new ArrayList<RmsFlightPreManifest>();
        try {
            FlightDynamic paramDynamic = new FlightDynamic();
            paramDynamic.setInoutTypeCode("J");
            List<FlightDynamic> dynamics = flightDynamicService.findListSimple(paramDynamic);
            flightPreManifestList(dynamics, preNanifs, false);
            return preNanifs;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return preNanifs;
    }

    public List<RmsFlightPreManifest> findHiFlightPreManifestList(RmsFlightPreManifest rmsFlightPreManifest, String dateStr) {
        List<RmsFlightPreManifest> preNanifs = new ArrayList<RmsFlightPreManifest>();
        if (StringUtils.isBlank(dateStr)) return preNanifs;
        try {
            Date planDate = DateUtils.parseDate(dateStr, "yyyy-MM-dd");
            FlightDynamic paramDynamic = new FlightDynamic();
            paramDynamic.setPlanDate(planDate);
            paramDynamic.setInoutTypeCode("J");
            List<FlightDynamic> dynamics = flightDynamicHistoryService.findList(paramDynamic);
            flightPreManifestList(dynamics, preNanifs, true);
            return preNanifs;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return preNanifs;
    }

    public void flightPreManifestList(List<FlightDynamic> dynamics, List<RmsFlightPreManifest> preNanifs, boolean isHistory) {
        if (!Collections3.isEmpty(dynamics)) {
            for (FlightDynamic dynamic : dynamics) {
                RmsFlightPreManifest enty = new RmsFlightPreManifest();
                enty.setFlightDynamicId(dynamic.getId());
//                enty.setAddrNature("DEPA");
                List<RmsFlightPreManifest> flightpreNanifs = null;
                if (isHistory) {
                    flightpreNanifs = rmsHiFlightPreManifestDao.getPreManifest(enty);
                } else {
                    flightpreNanifs = dao.getPreManifest(enty);
                }

//                String flightPreNanifId = null;
//                if (flightpreNanifs != null && flightpreNanifs.size() > 0) {
//                    RmsFlightPreManifest flightPreNanif = flightpreNanifs.get(0);
//                    //将本站出港数据复制到知己数据列表中
//                    BeanUtils.copyProperties(flightPreNanif, enty);
//                    flightPreNanifId = flightPreNanif.getId();
//                }

                //wjp_2017年9月13日10时23分 修复计算错误
                if (!Collections3.isEmpty(flightpreNanifs)) {
                    for (RmsFlightPreManifest flightpreNanif : flightpreNanifs) {
                        //计算各分支总人数 时港人数统计
                        enty.setBabyCountJ(enty.getBabyCountJ() != null ?
                                (enty.getBabyCountJ() + flightpreNanif.getBabyCountJ()) : flightpreNanif.getBabyCountJ());
                        enty.setChildCountJ(enty.getChildCountJ() != null ?
                                (enty.getChildCountJ() + flightpreNanif.getChildCountJ()) : flightpreNanif.getChildCountJ());
                        enty.setPersonCountJ(enty.getPersonCountJ() != null ?
                                (enty.getPersonCountJ() + flightpreNanif.getPersonCountJ()) : flightpreNanif.getPersonCountJ());

                        //过站人数统计
                        enty.setBabyCountP(enty.getBabyCountP() != null ?
                                (enty.getBabyCountP() + flightpreNanif.getBabyCountP()) : flightpreNanif.getBabyCountP());
                        enty.setChildCountP(enty.getChildCountP() != null ?
                                (enty.getChildCountP() + flightpreNanif.getChildCountP()) : flightpreNanif.getChildCountP());
                        enty.setPersonCountP(enty.getPersonCountP() != null ?
                                (enty.getPersonCountP() + flightpreNanif.getPersonCountP()) : flightpreNanif.getPersonCountP());

                        //货物统计
                        enty.setGoodsCount(enty.getGoodsCount() != null ?
                                (enty.getGoodsCount() + flightpreNanif.getGoodsCount()) : flightpreNanif.getGoodsCount());
                        enty.setGoodsWeight(enty.getGoodsWeight() != null ?
                                (enty.getGoodsWeight() + flightpreNanif.getGoodsWeight()) : flightpreNanif.getGoodsWeight());

                        //邮件统计
                        enty.setMailCount(enty.getMailCount() != null ?
                                (enty.getMailCount() + flightpreNanif.getMailCount()) : flightpreNanif.getMailCount());
                        enty.setMailWeight(enty.getMailWeight() != null ?
                                (enty.getMailWeight() + flightpreNanif.getMailWeight()) : flightpreNanif.getMailWeight());

                        //行李统计
                        enty.setLuggageCount(enty.getLuggageCount() != null ?
                                (enty.getLuggageCount() + flightpreNanif.getLuggageCount()) : flightpreNanif.getLuggageCount());
                        enty.setLuggageWeight(enty.getLuggageWeight() != null ?
                                (enty.getLuggageWeight() + flightpreNanif.getLuggageWeight()) : flightpreNanif.getLuggageWeight());
                    }
                }
                //将航班动态数据复制到值机列表中
                BeanUtils.copyProperties(dynamic, enty);
//                enty.setFlightDynamicId(dynamic.getId());
//                enty.setId(flightPreNanifId);
                //计算总数
                enty.setTouristCountJ(this.calculateTouristCountJ(enty));
                enty.setTouristCountP(this.calculateTouristCountP(enty));
                //航线
                enty.setAddressCode(dynamic.getDepartureAirportCode() + "-" + dynamic.getArrivalAirportCode());
                preNanifs.add(enty);
            }
        }
    }

    public List<RmsFlightPreManifest> getPreManifest(RmsFlightPreManifest flightPreManifest) {
        return dao.getPreManifest(flightPreManifest);
    }


    private Integer calculateTouristCountJ(RmsFlightPreManifest enty) {
        int allCount = 0;
        if (enty != null) {
            allCount = enty.getPersonCountJ() + enty.getBabyCountJ() + enty.getChildCountJ();
        }
        return allCount;
    }

    private Integer calculateTouristCountP(RmsFlightPreManifest enty) {
        int allCount = 0;
        if (enty != null) {
            allCount = enty.getPersonCountP() + enty.getBabyCountP() + enty.getChildCountP();
        }
        return allCount;
    }

    public void getHiFlightPreManifests(String flightDynamicId, Message message) {
        try {
            if (StringUtils.isBlank(flightDynamicId)) message.setMsg(0, "航班动态id为空!");
            //通过FlightDynamicId 获取该航班下的所有值机数据，没有数据的则自动补充
            List<RmsFlightPreManifest> flightPreManifests = new ArrayList<RmsFlightPreManifest>();
            //根据航班情况，计算航线信息,补充航某些航段没有的数据
            FlightDynamic dynamic = flightDynamicHistoryService.getHis(flightDynamicId);
            String passAirport1Code = dynamic.getPassAirport1Code();
            String passAirport2Code = dynamic.getPassAirport2Code();
            String arrivalAirCode = dynamic.getArrivalAirportCode();
            //经停地1
            setFlightPreManifestEntry(passAirport1Code, dynamic.getPassAirport1Name(), "PASS1", flightDynamicId, flightPreManifests, true);
            //经停地2
            setFlightPreManifestEntry(passAirport2Code, dynamic.getPassAirport2Name(), "PASS2", flightDynamicId, flightPreManifests, true);
            //出发地
            setFlightPreManifestEntry(arrivalAirCode, dynamic.getArrivalAirportName(), "DEPA", flightDynamicId, flightPreManifests, true);
            message.setCode(1);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("flightPreManifests", flightPreManifests);
            message.setResult(map);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
    }

    /**
     * 该方法用于填充航段数据
     *
     * @param addr
     * @param addrNature
     * @param flightDynamicId
     * @param flightDutys
     * @return
     */
    private List<RmsFlightPreManifest> setFlightPreManifestEntry(String addrCode, String addr, String addrNature, String flightDynamicId, List<RmsFlightPreManifest> flightDutys, boolean isHistory) {
        RmsFlightPreManifest flightPreManifest = new RmsFlightPreManifest();
        flightPreManifest.setAddrNature(addrNature);
        flightPreManifest.setFlightDynamicId(flightDynamicId);
        flightPreManifest.setAddr(addr);
        flightPreManifest.setAddrCode(addrCode);
        //如果地址(经停、到站等)不为空，查询此航段的数据.否则补足空数据
        if (StringUtils.isNotBlank(addrCode)) {
            List<RmsFlightPreManifest> results = null;
            if (isHistory) {
                results = rmsHiFlightPreManifestDao.getPreManifest(flightPreManifest);
            } else {
                results = this.getPreManifest(flightPreManifest);
            }
            if (results != null && results.size() > 0) {
                flightDutys.add(results.get(0));
            } else {
                //查询没有值机数据，补入空数据
                flightDutys.add(flightPreManifest);
            }
        } else {
            flightDutys.add(flightPreManifest);
        }
        return flightDutys;
    }

    public RmsFlightPreManifest hiGet(String id) {
        RmsFlightPreManifest entity = rmsHiFlightPreManifestDao.get(id);
        return entity != null ? entity : new RmsFlightPreManifest();
    }

    @Transactional(readOnly = false)
    public void hiSave(RmsFlightPreManifest rmsFlightPreManifest, Message message) {
        if (rmsFlightPreManifest.getIsNewRecord()) {
            rmsFlightPreManifest.preInsert();
            rmsHiFlightPreManifestDao.insert(rmsFlightPreManifest);
        } else {
            rmsFlightPreManifest.preUpdate();
            rmsHiFlightPreManifestDao.update(rmsFlightPreManifest);
        }

        try {
            FlightDynamic flightDynamic = flightDynamicHistoryService.getHis(rmsFlightPreManifest.getFlightDynamicId());
            if (flightDynamic != null)
                message.setMsg(1, "添加或更新上站舱单历史数据：" + LogUtils.msgFlightDynamic(flightDynamic) + "," + rmsFlightPreManifest.toString());
        } catch (Exception e) {
            logger.error("保存上站舱单历史数据(RmsFlightPreManifest)出错");
            message.setMessage("保存失败");
            //e.printStackTrace();
        }
    }
}