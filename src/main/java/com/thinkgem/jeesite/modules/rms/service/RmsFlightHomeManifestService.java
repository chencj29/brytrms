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
import com.thinkgem.jeesite.modules.rms.dao.RmsFlightHomeManifestDao;
import com.thinkgem.jeesite.modules.rms.dao.RmsHiFlightHomeManifestDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightHomeManifest;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightHomeManifestSec;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;

/**
 * 本站仓单数据管理Service
 *
 * @author dingshuang
 * @version 2016-05-19
 */
@Service
@Transactional(readOnly = true)
public class RmsFlightHomeManifestService extends CrudService<RmsFlightHomeManifestDao, RmsFlightHomeManifest> {

    @Autowired
    private FlightDynamicService flightDynamicService;

    @Autowired
    FlightDynamicHistoryService flightDynamicHistoryService;

    @Autowired
    RmsHiFlightHomeManifestDao rmsHiFlightHomeManifestDao;

    public RmsFlightHomeManifest get(String id) {
        RmsFlightHomeManifest enty = super.get(id);
        if (enty != null && StringUtils.isNotBlank(enty.getFlightDynamicId())) {
            FlightDynamic dynamic = flightDynamicService.get(new FlightDynamic(enty.getFlightDynamicId()));
            if (dynamic != null) {
                BeanUtils.copyProperties(dynamic, enty);
            }
            enty.setId(id);//恢复被覆盖的id
        }
        return enty;
    }

    public RmsFlightHomeManifest hiGet(String id) {
        RmsFlightHomeManifest enty = rmsHiFlightHomeManifestDao.get(id);
        if (enty != null && StringUtils.isNotBlank(enty.getFlightDynamicId())) {
            FlightDynamic dynamic = flightDynamicHistoryService.getHis(enty.getFlightDynamicId());
            if (dynamic != null) {
                BeanUtils.copyProperties(dynamic, enty);
            }
            enty.setId(id);//恢复被覆盖的id
        }
        return enty;
    }

    public List<RmsFlightHomeManifest> findList(RmsFlightHomeManifest rmsFlightHomeManifest) {
        return super.findList(rmsFlightHomeManifest);
    }

    public Page<RmsFlightHomeManifest> findPage(Page<RmsFlightHomeManifest> page, RmsFlightHomeManifest rmsFlightHomeManifest) {
        return super.findPage(page, rmsFlightHomeManifest);
    }

    @Transactional(readOnly = false)
    public void save(RmsFlightHomeManifest rmsFlightHomeManifest, Message message) {
        super.save(rmsFlightHomeManifest);

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(rmsFlightHomeManifest.getFlightDynamicId());
            if (flightDynamic != null)
                message.setMsg(1, "添加或更新本站仓单数据：" + LogUtils.msgFlightDynamic(flightDynamic) + "," + rmsFlightHomeManifest.toString());
        } catch (Exception e) {
            logger.error("保存本站仓单数据(rmsFlightHomeManifest)出错");
            //e.printStackTrace();
        }
    }

    @Transactional(readOnly = false)
    public void delete(RmsFlightHomeManifest rmsFlightHomeManifest) {
        super.delete(rmsFlightHomeManifest);
    }


    /**
     * 按照航班数据获取本站仓单数据
     *
     * @param rmsFlightHomeManifest
     * @return
     */
    public List<RmsFlightHomeManifest> findHomeManifestList(RmsFlightHomeManifest rmsFlightHomeManifest) {
        List<RmsFlightHomeManifest> homeNanifs = new ArrayList<RmsFlightHomeManifest>();
        FlightDynamic paramDynamic = new FlightDynamic();
        paramDynamic.setInoutTypeCode("C");
        List<FlightDynamic> dynamics = flightDynamicService.findListSimple(paramDynamic);
        homeManifestList(dynamics, homeNanifs, false);
        return homeNanifs;
    }

    //按照航班数据获取本站仓单历史数据
    public List<RmsFlightHomeManifest> findHiHomeManifestList(RmsFlightHomeManifest rmsFlightHomeManifest, String dateStr) {
        List<RmsFlightHomeManifest> homeNanifs = new ArrayList<RmsFlightHomeManifest>();
        if (StringUtils.isBlank(dateStr)) return homeNanifs;
        try {
            Date planDate = DateUtils.parseDate(dateStr, "yyyy-MM-dd");
            FlightDynamic paramDynamic = new FlightDynamic();
            paramDynamic.setInoutTypeCode("C");
            paramDynamic.setPlanDate(planDate);
            List<FlightDynamic> dynamics = flightDynamicHistoryService.findList(paramDynamic);
            homeManifestList(dynamics, homeNanifs, true);
            return homeNanifs;
        } catch (ParseException e) {
            logger.error("获取历史本登机数据，日期转换出错");
        }
        return homeNanifs;
    }

    public void homeManifestList(List<FlightDynamic> dynamics, List<RmsFlightHomeManifest> homeNanifs, boolean isHistory) {
        if (!Collections3.isEmpty(dynamics)) {
            for (FlightDynamic dynamic : dynamics) {
                RmsFlightHomeManifest enty = new RmsFlightHomeManifest();
                List<RmsFlightHomeManifest> flightHomeNanifs = null;
                if (isHistory) {
                    flightHomeNanifs = rmsHiFlightHomeManifestDao.getHomeManifest(dynamic.getId());
                } else {
                    flightHomeNanifs = dao.getHomeManifest(dynamic.getId());
                }
                String flightHomeNanifId = null;
                if (flightHomeNanifs != null && flightHomeNanifs.size() > 0) {
                    RmsFlightHomeManifest flightHomeNanif = flightHomeNanifs.get(0);
                    //将本站出港数据复制到知己数据列表中
                    BeanUtils.copyProperties(flightHomeNanif, enty);
                    flightHomeNanifId = flightHomeNanif.getId();
                }

                //将航班动态数据复制到值机列表中
                BeanUtils.copyProperties(dynamic, enty);
                enty.setFlightDynamicId(dynamic.getId());
                enty.setId(flightHomeNanifId);
                enty.setTravellerAllCount(calculateTravellerAllCount(enty));
                enty.setPersonActualCountP(calculateTravellerAllCountP(enty));
                //航线
                enty.setAddressCode(dynamic.getDepartureAirportCode() + "-" + dynamic.getArrivalAirportCode());
                homeNanifs.add(enty);
            }
        }
    }

    public List<RmsFlightHomeManifestSec> getManifestSecs(RmsFlightHomeManifestSec resultManifestSec) {
        return dao.getManifestSecs(resultManifestSec);
    }


    public void saveManifestSecs(RmsFlightHomeManifestSec resultManifestSec, Message message) {
        if (resultManifestSec.getId() != null) {
            if (StringUtils.isNotBlank(resultManifestSec.getId())) {
                dao.updateManifestSecs(resultManifestSec);
            } else {
                resultManifestSec.setId(UUID.randomUUID().toString());
                dao.saveManifestSecs(resultManifestSec);
            }
        }

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(resultManifestSec.getFlightDynamicId());
            if (flightDynamic != null)
                message.setMsg(1, "添加或更新本站仓单航段数据：" + LogUtils.msgFlightDynamic(flightDynamic) + "," + resultManifestSec.toString());
        } catch (Exception e) {
            logger.error("保存本站仓单航段数据(rmsFlightHomeManifestSec)出错");
            //e.printStackTrace();
        }
    }

    private Integer calculateTravellerAllCount(RmsFlightHomeManifest enty) {
        int allCount = 0;
        if (enty != null) {
            allCount = enty.getPersonCount() + enty.getChildCount() + enty.getBabyCount();
        }
        return new Integer(allCount);
    }

    private Integer calculateTravellerAllCountP(RmsFlightHomeManifest enty) {
        int allCount = 0;
        if (enty != null) {
            allCount = enty.getPersonCountP() + enty.getChildCoutP() + enty.getBabyCountP();
        }
        return new Integer(allCount);
    }

    public void getHiManifestSecs(String flightDynamicId, Message message) {
        try {
            List<RmsFlightHomeManifestSec> manifestSecs = new ArrayList();
            //根据航班情况，计算航线信息,补充航某些航段没有的数据
            FlightDynamic dynamic = flightDynamicHistoryService.getHis(flightDynamicId);
            String passAirport1Code = dynamic.getPassAirport1Code();
            String passAirport2Code = dynamic.getPassAirport2Code();
            String arrivalAirCode = dynamic.getArrivalAirportCode();
            //经停地1
            setManifestSecEntry(passAirport1Code, dynamic.getPassAirport1Name(), "PASS1", flightDynamicId, manifestSecs, true);
            //经停地2
            setManifestSecEntry(passAirport2Code, dynamic.getPassAirport2Name(), "PASS2", flightDynamicId, manifestSecs, true);
            //经停地1
            setManifestSecEntry(arrivalAirCode, dynamic.getArrivalAirportName(), "ARRI", flightDynamicId, manifestSecs, true);
            message.setCode(1);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("manifestSecs", manifestSecs);
            message.setResult(map);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage(e.getMessage());
        }
    }

    /**
     * 该方法用于填充航段本站仓单数据
     *
     * @param addr
     * @param addrNature
     * @param flightDynamicId
     * @param manifestSecs
     * @return
     */
    private List<RmsFlightHomeManifestSec> setManifestSecEntry(String addrCode, String addr, String addrNature, String flightDynamicId, List<RmsFlightHomeManifestSec> manifestSecs, boolean isHistory) {
        RmsFlightHomeManifestSec resultManifestSec = new RmsFlightHomeManifestSec();
        resultManifestSec.setFlightDynamicId(flightDynamicId);
        resultManifestSec.setAddr(addr);
        resultManifestSec.setAddrCode(addrCode);
        resultManifestSec.setAddrNature(addrNature);
        //如果地址(经停、到站等)不为空，查询此航段的数据.否则补足空数据
        if (StringUtils.isNotBlank(addrCode)) {
            List<RmsFlightHomeManifestSec> resultManifestSecs = null;
            if (isHistory) {
                resultManifestSecs = rmsHiFlightHomeManifestDao.getManifestSecs(resultManifestSec);
            } else {
                resultManifestSecs = this.getManifestSecs(resultManifestSec);
            }
            if (resultManifestSecs != null && resultManifestSecs.size() > 0) {
                RmsFlightHomeManifestSec manifestSec = resultManifestSecs.get(0);
                manifestSec.setFlightDynamicId(flightDynamicId);
                manifestSec.setAddr(addr);
                manifestSec.setAddrCode(addrCode);
                manifestSec.setAddrNature(addrNature);
                manifestSecs.add(manifestSec);
            } else {
                //查询没有值机数据，补入空数据
                manifestSecs.add(resultManifestSec);
            }
        } else {
            manifestSecs.add(resultManifestSec);
        }
        return manifestSecs;
    }

    public void homeManifestListByManifestSec(List<FlightDynamic> dynamics, List<RmsFlightHomeManifest> homeNanifs) {
        if (!Collections3.isEmpty(dynamics)) {
            for (FlightDynamic dynamic : dynamics) {
                if (StringUtils.isNotBlank(dynamic.getPassAirport1Code())) {
                    RmsFlightHomeManifestSec resultManifestSec = new RmsFlightHomeManifestSec();
                    resultManifestSec.setFlightDynamicId(dynamic.getId());
                    resultManifestSec.setAddr(dynamic.getPassAirport1Name());
                    resultManifestSec.setAddrCode(dynamic.getPassAirport1Code());
                    resultManifestSec.setAddrNature("PASS1");
                    List<RmsFlightHomeManifestSec> resultManifestSecs = rmsHiFlightHomeManifestDao.getManifestSecs(resultManifestSec);

                    if (!Collections3.isEmpty(resultManifestSecs)) {
                        for (RmsFlightHomeManifestSec manifestSec : resultManifestSecs) {
                            RmsFlightHomeManifest enty = new RmsFlightHomeManifest();
                            //BeanUtils.copyProperties(manifestSec, enty);
                            enty.setChildCount(Math.round(manifestSec.getChildCount()));
                            enty.setPersonCount(Math.round(manifestSec.getPersonCount()));
                            enty.setBabyCount(Math.round(manifestSec.getBabyCount()));
                            enty.setLuggageCount(Math.round(manifestSec.getLuggageCount()));
                            enty.setLuggageWeight(manifestSec.getLuggageWeight());
                            enty.setGoodsCount(Math.round(manifestSec.getGoodsCount()));
                            enty.setGoodsWeight(manifestSec.getGoodsCount());
                            enty.setMailCount(Math.round(manifestSec.getMailCount()));
                            enty.setMailWeight(manifestSec.getMailCount());

                            //将航班动态数据复制到值机列表中
                            BeanUtils.copyProperties(dynamic, enty);
                            enty.setFlightDynamicId(dynamic.getId());
                            enty.setId(manifestSec.getId());
                            enty.setTravellerAllCount(calculateTravellerAllCount(enty));
                            enty.setPersonActualCountP(calculateTravellerAllCountP(enty));
                            //航线
                            enty.setAddressCode(dynamic.getDepartureAirportCode() + "-" + dynamic.getPassAirport1Code());
                            homeNanifs.add(enty);
                        }
                    }

                    if (StringUtils.isNotBlank(dynamic.getPassAirport2Code())) {
                        RmsFlightHomeManifestSec resultManifestSec2 = new RmsFlightHomeManifestSec();
                        resultManifestSec2.setFlightDynamicId(dynamic.getId());
                        resultManifestSec2.setAddr(dynamic.getPassAirport2Name());
                        resultManifestSec2.setAddrCode(dynamic.getPassAirport2Code());
                        resultManifestSec2.setAddrNature("PASS2");
                        List<RmsFlightHomeManifestSec> resultManifestSec2s = rmsHiFlightHomeManifestDao.getManifestSecs(resultManifestSec2);

                        if (!Collections3.isEmpty(resultManifestSec2s)) {
                            for (RmsFlightHomeManifestSec manifestSec : resultManifestSec2s) {
                                RmsFlightHomeManifest enty = new RmsFlightHomeManifest();
                                BeanUtils.copyProperties(manifestSec, enty);

                                //将航班动态数据复制到值机列表中
                                BeanUtils.copyProperties(dynamic, enty);
                                enty.setFlightDynamicId(dynamic.getId());
                                enty.setId(manifestSec.getId());
                                enty.setTravellerAllCount(calculateTravellerAllCount(enty));
                                enty.setPersonActualCountP(calculateTravellerAllCountP(enty));
                                //航线
                                enty.setAddressCode(dynamic.getDepartureAirportCode() + "-" + dynamic.getPassAirport2Code());
                                homeNanifs.add(enty);
                            }
                        }
                    }
                }
            }
        }
    }

    @Transactional(readOnly = false)
    public void hiSave(RmsFlightHomeManifest rmsFlightHomeManifest, Message message) {
        if (rmsFlightHomeManifest.getIsNewRecord()) {
            rmsFlightHomeManifest.preInsert();
            rmsHiFlightHomeManifestDao.insert(rmsFlightHomeManifest);
        } else {
            rmsFlightHomeManifest.preUpdate();
            rmsHiFlightHomeManifestDao.update(rmsFlightHomeManifest);
        }

        try {
            FlightDynamic flightDynamic = flightDynamicHistoryService.getHis(rmsFlightHomeManifest.getFlightDynamicId());
            if (flightDynamic != null)
                message.setMsg(1, "添加或更新本站仓单历史数据：" + LogUtils.msgFlightDynamic(flightDynamic) + "," + rmsFlightHomeManifest.toString());
        } catch (Exception e) {
            logger.error("保存本站仓单历史数据(rmsFlightHomeManifest)出错！");
            message.setMessage("保存失败");
            //e.printStackTrace();
        }
    }

    public void hiSaveManifestSecs(RmsFlightHomeManifestSec resultManifestSec, Message message) {
        if (resultManifestSec.getIsNewRecord()) {
            resultManifestSec.preInsert();
            rmsHiFlightHomeManifestDao.saveManifestSecs(resultManifestSec);
        } else {
            resultManifestSec.preUpdate();
            rmsHiFlightHomeManifestDao.updateManifestSecs(resultManifestSec);
        }

        try {
            FlightDynamic flightDynamic = flightDynamicHistoryService.getHis(resultManifestSec.getFlightDynamicId());
            if (flightDynamic != null)
                message.setMsg(1, "添加或更新本站仓单航段历史数据：" + LogUtils.msgFlightDynamic(flightDynamic) + "," + resultManifestSec.toString());
        } catch (Exception e) {
            logger.error("保存本站仓单航段历史数据(hiSaveManifestSecs)出错");
            //e.printStackTrace();
        }
    }
}