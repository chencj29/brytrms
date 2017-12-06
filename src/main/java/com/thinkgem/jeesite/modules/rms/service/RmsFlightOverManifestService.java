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
import com.thinkgem.jeesite.modules.rms.dao.RmsFlightOverManifestDao;
import com.thinkgem.jeesite.modules.rms.dao.RmsHiFlightOverManifestDao;
import com.thinkgem.jeesite.modules.rms.entity.RmsFlightOverManifest;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 过站仓单数据管理Service
 *
 * @author dingshuang
 * @version 2016-05-20
 */
@Service
@Transactional(readOnly = true)
public class RmsFlightOverManifestService extends CrudService<RmsFlightOverManifestDao, RmsFlightOverManifest> {

    @Autowired
    private FlightDynamicService flightDynamicService;

    @Autowired
    FlightDynamicHistoryService flightDynamicHistoryService;

    @Autowired
    RmsHiFlightOverManifestDao rmsHiFlightOverManifestDao;

    public RmsFlightOverManifest get(String id) {
        RmsFlightOverManifest enty = super.get(id);
        //前台列表是按照航班数据显示的，可能不存在过站仓单数据用于form回填
        enty = (enty == null) ? new RmsFlightOverManifest() : enty;
        return enty;
    }

    public List<RmsFlightOverManifest> findList(RmsFlightOverManifest rmsFlightOverManifest) {
        return super.findList(rmsFlightOverManifest);
    }

    public Page<RmsFlightOverManifest> findPage(Page<RmsFlightOverManifest> page, RmsFlightOverManifest rmsFlightOverManifest) {
        return super.findPage(page, rmsFlightOverManifest);
    }

    @Transactional(readOnly = false)
    public void save(RmsFlightOverManifest rmsFlightOverManifest, Message message) {
        super.save(rmsFlightOverManifest);

        try {
            FlightDynamic flightDynamic = flightDynamicService.get(rmsFlightOverManifest.getFlightDynamicId());
            if (flightDynamic != null)
                message.setMsg(1, "添加或更新过站仓单数据：" + LogUtils.msgFlightDynamic(flightDynamic) + "," + rmsFlightOverManifest.toString());
        } catch (Exception e) {
            logger.error("保存过站仓单数据(rmsFlightOverManifest)出错");
            //e.printStackTrace();
        }
    }

    @Transactional(readOnly = false)
    public void delete(RmsFlightOverManifest rmsFlightOverManifest) {
        super.delete(rmsFlightOverManifest);
    }


    public List<RmsFlightOverManifest> findFlightOverManifestList(RmsFlightOverManifest rmsFlightOverManifest) {
        List<RmsFlightOverManifest> homeNanifs = new ArrayList<RmsFlightOverManifest>();
        FlightDynamic paramDynamic = new FlightDynamic();
        paramDynamic.setInoutTypeCode("C");
        List<FlightDynamic> dynamics = flightDynamicService.findListSimple(paramDynamic);
        flightOverManifestList(dynamics, homeNanifs, false);
        return homeNanifs;
    }

    public List<RmsFlightOverManifest> findHiFlightOverManifestList(RmsFlightOverManifest rmsFlightOverManifest, String dateStr) {
        List<RmsFlightOverManifest> homeNanifs = new ArrayList<RmsFlightOverManifest>();
        if (StringUtils.isBlank(dateStr)) return homeNanifs;
        try {
            Date planDate = DateUtils.parseDate(dateStr, "yyyy-MM-dd");
            FlightDynamic paramDynamic = new FlightDynamic();
            paramDynamic.setInoutTypeCode("C");
            paramDynamic.setPlanDate(planDate);
            List<FlightDynamic> dynamics = flightDynamicHistoryService.findList(paramDynamic);
            flightOverManifestList(dynamics, homeNanifs, true);
            return homeNanifs;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return homeNanifs;
    }

    public void flightOverManifestList(List<FlightDynamic> dynamics, List<RmsFlightOverManifest> homeNanifs, boolean isHistory) {
        if (!Collections3.isEmpty(dynamics)) {
            for (FlightDynamic dynamic : dynamics) {
                RmsFlightOverManifest enty = new RmsFlightOverManifest();
                List<RmsFlightOverManifest> flightOverNanifs = null;
                if (isHistory) {
                    flightOverNanifs = rmsHiFlightOverManifestDao.getOverManifest(dynamic.getId());
                } else {
                    flightOverNanifs = dao.getOverManifest(dynamic.getId());
                }
                String flightHomeNanifId = null;
                if (flightOverNanifs != null && flightOverNanifs.size() > 0) {
                    RmsFlightOverManifest flightHomeNanif = flightOverNanifs.get(0);
                    //将本站出港数据复制到知己数据列表中
                    BeanUtils.copyProperties(flightHomeNanif, enty);
                    flightHomeNanifId = flightHomeNanif.getId();
                }

                //将航班动态数据复制到值机列表中
                BeanUtils.copyProperties(dynamic, enty);
                enty.setFlightDynamicId(dynamic.getId());
                enty.setId(flightHomeNanifId);
                homeNanifs.add(enty);
            }
        }
    }

    public RmsFlightOverManifest hiGet(String id) {
        RmsFlightOverManifest enty = rmsHiFlightOverManifestDao.get(id);
        //前台列表是按照航班数据显示的，可能不存在过站仓单数据用于form回填
        enty = (enty == null) ? new RmsFlightOverManifest() : enty;
        return enty;
    }

    @Transactional(readOnly = false)
    public void hiSave(RmsFlightOverManifest rmsFlightOverManifest, Message message) {
        if (rmsFlightOverManifest.getIsNewRecord()) {
            rmsFlightOverManifest.preInsert();
            rmsHiFlightOverManifestDao.insert(rmsFlightOverManifest);
        } else {
            rmsFlightOverManifest.preUpdate();
            rmsHiFlightOverManifestDao.update(rmsFlightOverManifest);
        }

        try {
            FlightDynamic flightDynamic = flightDynamicHistoryService.getHis(rmsFlightOverManifest.getFlightDynamicId());
            if (flightDynamic != null)
                message.setMsg(1, "添加或更新过站仓单历史数据：" + LogUtils.msgFlightDynamic(flightDynamic) + "," + rmsFlightOverManifest.toString());
        } catch (Exception e) {
            logger.error("保存过站仓单历史数据(rmsFlightOverManifest)出错");
            message.setMessage("保存失败");
            //e.printStackTrace();
        }
    }
}