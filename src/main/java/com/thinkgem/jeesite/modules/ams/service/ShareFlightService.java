/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.ams.dao.FlightDynamicDao;
import com.thinkgem.jeesite.modules.ams.dao.ShareFlightDynamicDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.ShareFlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ams.entity.ShareFlight;
import com.thinkgem.jeesite.modules.ams.dao.ShareFlightDao;

/**
 * 共享航班管理Service
 *
 * @author xiaopo
 * @version 2016-01-19
 */
@Service
@Transactional(readOnly = true)
public class ShareFlightService extends CrudService<ShareFlightDao, ShareFlight> {

    private static Logger log = Logger.getLogger(ShareFlightService.class);

    @Autowired
    private FlightDynamicDao flightDynamicDao;

    // @Autowired
    // private ShareFlightDynamicDao shareFlightDynamicDao;

    public ShareFlight get(String id) {
        return super.get(id);
    }

    public List<ShareFlight> findList(ShareFlight shareFlight) {
        return super.findList(shareFlight);
    }

    public Page<ShareFlight> findPage(Page<ShareFlight> page, ShareFlight shareFlight) {
        return super.findPage(page, shareFlight);
    }

    @Transactional(readOnly = false)
    public Message saveAndCheck(ShareFlight shareFlight) {
        Message message = new Message();
        // 保存之前需要做验证，验证条件如下：
        // 同一班期、进出港条件下，同一个共享航班不能同时共享多个主航班

        // 将班期拆分开来，一个个的检索
        String[] weekArray = shareFlight.getDaysOpt().split(",");
        boolean checkExist = false;
        for (int i = 0; i < weekArray.length; i++) {
            ShareFlight queryCondition = new ShareFlight();
            queryCondition.setDaysOpt(weekArray[i]);
            queryCondition.setSlaveAirlineCode(shareFlight.getSlaveAirlineCode());
            queryCondition.setSlaveFlightNo(shareFlight.getSlaveFlightNo());
            queryCondition.setIoOptCode(shareFlight.getIoOptCode());
            List<ShareFlight> sfList = super.dao.findByCondition(shareFlight);
            if (sfList != null && sfList.size() > 0) {
                checkExist = true;
                break;
            }
        }

        if (checkExist) {
            // 如果存在，那么不能进行保存，返回失败状态给用户
            message.setCode(0);
        } else {
            message.setCode(1);
            super.save(shareFlight);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("shareFlight", shareFlight);
            message.setResult(map);
        }
        return message;
    }

    @Transactional(readOnly = false)
    public void delete(ShareFlight shareFlight) {
        super.delete(shareFlight);
    }

    @Transactional(readOnly = false)
    public void updateApply(Date applyDate) {
        // 查询所有的符合日期班期的共享航班记录
        ShareFlight sf = new ShareFlight();
        String week = DateUtils.getWeekChinese(applyDate);
        sf.setDaysOpt(week);
        List<ShareFlight> shareFlightList = super.dao.findByPeriod(sf);
        // 遍历所有的共享航班
        // List<ShareFlight> shareFlightList = super.dao.findList(new ShareFlight());
        // 每次应用共享航班的时候把已存在的记录都删除
        // chrischen，修改为不保存中间关联表
        // shareFlightDynamicDao.deleteAll();

        // 把需要更新的航班动态信息的共享航班字段清空
        // 按照航空公司、航班号、计划日期查询更新
        for (ShareFlight shareFlight : shareFlightList) {
            FlightDynamic fd = new FlightDynamic();
            fd.setPlanDate(applyDate);
            fd.setFlightCompanyCode(shareFlight.getMasterAirlineCode());
            fd.setFlightNum(shareFlight.getMasterFlightNo());
            flightDynamicDao.updateShareFlight2Null(fd);
        }

        for (int i = 0; i < shareFlightList.size(); i++) {
            // 寻找能够匹配上的航班,根据(航班号,航空公司,班期)来匹配更新
            FlightDynamic fd = new FlightDynamic();
            fd.setFlightCompanyCode(shareFlightList.get(i).getMasterAirlineCode());
            fd.setFlightNum(shareFlightList.get(i).getMasterFlightNo());


            // fd.setPlanDayOfWeek(queryWeekStr.toString());
            fd.setPlanDate(applyDate);
            List<FlightDynamic> flightDynamicList = flightDynamicDao.getFlighgtDynamicByConditon(fd);
            for (int j = 0; j < flightDynamicList.size(); j++) {
                String newShareFlightNum = null;
                if (StringUtils.isNotBlank(flightDynamicList.get(j).getShareFlightNum())) {
                    String existShareFlightNum = flightDynamicList.get(j).getShareFlightNum();
                    newShareFlightNum = existShareFlightNum + " " + shareFlightList.get(i).getSlaveFlightNo();
                } else {
                    newShareFlightNum = shareFlightList.get(i).getSlaveFlightNo();
                }
                flightDynamicList.get(j).setShareFlightNum(newShareFlightNum);
                // log.debug("*****" + flightDynamicList.get(j).getFlightNum() + ", " + flightDynamicList.get(j).getFlightCompanyCode() + ", shareFlightNo=" + newShareFlightNum);
                flightDynamicDao.update(flightDynamicList.get(j));
                // 往共享航班、航班动态关联表中写入关联关系
            }
        }
    }
}