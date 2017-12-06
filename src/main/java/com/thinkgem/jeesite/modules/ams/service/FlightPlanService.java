/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.dao.FlightPlanDao;
import com.thinkgem.jeesite.modules.ams.dao.FlightPlanDetailDao;
import com.thinkgem.jeesite.modules.ams.dao.LongPlanDivideDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightPlan;
import com.thinkgem.jeesite.modules.ams.entity.FlightPlanDetail;
import com.thinkgem.jeesite.modules.ams.entity.LongPlanDivide;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 航班计划管理Service
 *
 * @author xiaopo
 * @version 2015-12-24
 */
@Service
@Transactional(readOnly = true)
public class FlightPlanService extends CrudService<FlightPlanDao, FlightPlan> {

    private static Logger log = Logger.getLogger(FlightPlanService.class);

    @Autowired
    private FlightPlanDetailDao flightPlanDetailDao;

    @Autowired
    private LongPlanDivideDao longPlanDivideDao;

    public FlightPlan get(String id) {
        FlightPlan flightPlan = super.get(new FlightPlan(id));
        flightPlan.setFlightPlanDetailList(flightPlanDetailDao.findList(new FlightPlanDetail(flightPlan)));
        return flightPlan;
    }

    public List<FlightPlan> findList(FlightPlan flightPlan) {
        return super.findList(flightPlan);
    }

    public Page<FlightPlan> findPage(Page<FlightPlan> page, FlightPlan flightPlan) {
        return super.findPage(page, flightPlan);
    }

    @Transactional(readOnly = false)
    public void save(FlightPlan flightPlan) {
        // 保存之前先检查当前日期是否已经存在记录，如果已经存在那么先进行删除
        FlightPlan fp = super.dao.findPlanDateByFlightPlan(flightPlan);
        if (fp != null) {
            super.delete(fp);
            flightPlanDetailDao.delete(new FlightPlanDetail(fp));
        }

        super.save(flightPlan);
        for (FlightPlanDetail flightPlanDetail : flightPlan.getFlightPlanDetailList()) {
            if (flightPlanDetail.getId() == null) {
                continue;
            }
            if (FlightPlanDetail.DEL_FLAG_NORMAL.equals(flightPlanDetail.getDelFlag())) {
                if (StringUtils.isBlank(flightPlanDetail.getId())) {
                    flightPlanDetail.setFlightPlan(flightPlan);
                    flightPlanDetail.preInsert();
                    flightPlanDetailDao.insert(flightPlanDetail);
                } else {
                    flightPlanDetail.preUpdate();
                    flightPlanDetailDao.update(flightPlanDetail);
                }
            } else {
                flightPlanDetailDao.delete(flightPlanDetail);
            }
        }
    }

    @Transactional(readOnly = false)
    public void delete(FlightPlan flightPlan) {
        super.delete(flightPlan);
        flightPlanDetailDao.delete(new FlightPlanDetail(flightPlan));
    }

    @Transactional(readOnly = false)
    public void audit(FlightPlan flightPlan) {
        flightPlan.setAuditTime(new Date());
        super.dao.audit(flightPlan);
        FlightPlanDetail detail = new FlightPlanDetail(flightPlan);
        detail.setStatus(flightPlan.getStatus().intValue());
        flightPlanDetailDao.audit(detail);
    }

    public void publish(FlightPlan flightPlan) {
        flightPlan.setStatus(2);
        flightPlan.setPublishTime(new Date());
        super.dao.planPublish(flightPlan);
        FlightPlanDetail detail = new FlightPlanDetail(flightPlan);
        detail.setStatus(flightPlan.getStatus().intValue());
        flightPlanDetailDao.audit(detail);
    }

    /**
     * @param @param flightPlan
     * @return void
     * @throws
     * @Title: saveFlightPlanFromLongPlan
     * @Description: 保存航班计划，航班计划详情从长期计划拆分表中得来
     * @author: chencheng
     * @date： 2016年1月13日 下午10:05:41
     */
    public void saveFlightPlanFromLongPlan(FlightPlan flightPlan) {
        // 保存之前先检查当前日期是否已经存在记录，如果已经存在那么先进行删除
        FlightPlan fp = super.dao.findPlanDateByFlightPlan(flightPlan);
        if (fp != null) {
            super.delete(fp);
            flightPlanDetailDao.delete(new FlightPlanDetail(fp));
        }

        super.save(flightPlan);
        // 根据初始化日期查询出命中的长期计划
        Date planTime = flightPlan.getPlanTime();
        String weekChinese = DateUtils.getWeekChinese(planTime);
        // select b.* from AMS_LONG_PLAN a join AMS_LONG_PLAN_DIVIDE b on a.ID=b.LONG_PLAN_ID where a.PERIOD like '%一%' and
        // to_date('2016-1-1','yyyy-MM-dd') BETWEEN a.START_DATE and a.END_DATE
        // 查询条件有 1.在长期计划的开始和结束时间范围内 2.根据初始化日期是周几进行检索匹配

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("planTime", planTime);
        params.put("weekChinese", weekChinese);

        List<LongPlanDivide> longPlanDivideList = longPlanDivideDao.findByPlanDateAndWeek(params);

        for (LongPlanDivide longPlanDivide : longPlanDivideList) {

            FlightPlanDetail flightPlanDetail = new FlightPlanDetail();
            BeanUtils.copyProperties(longPlanDivide, flightPlanDetail, "id");
            flightPlanDetail.setId(null);

            // 更新计划时间
            String planDateStr = DateUtils.formatDate(flightPlan.getPlanTime(), "yyyy-MM-dd");
            String departurePlanTimeStr = planDateStr + " " + longPlanDivide.getDeparturePlanTimeStr();
            String arrivalPlanTimeStr = planDateStr + " " + longPlanDivide.getArrivalPlanTimeStr();

            Date departurePlanTime = null;
            Date arrivalPlanTime = null;
            try {
                departurePlanTime = DateUtils.parseDate(departurePlanTimeStr, "yyyy-MM-dd HH:mm");
                arrivalPlanTime = DateUtils.parseDate(arrivalPlanTimeStr, "yyyy-MM-dd HH:mm");
            } catch (ParseException e) {
                log.error("从长期计划初始为航班计划异常，时间格式转换异常...", e);
            }
            flightPlanDetail.setDeparturePlanTime(departurePlanTime);
            flightPlanDetail.setArrivalPlanTime(arrivalPlanTime);
            flightPlanDetail.setFlightPlan(flightPlan);
            flightPlanDetail.setStatus(0);
            flightPlanDetail.preInsert();
            flightPlanDetailDao.insert(flightPlanDetail);
        }
    }

    /**
     * @param @param flightPlan
     * @return void
     * @throws
     * @Title: changeApprove
     * @Description: 计划变更审核
     * @author: chencheng
     * @date： 2016年1月23日 上午9:35:44
     */
    public void changeApprove(FlightPlan flightPlan) {
        // 查询老
        // flightPlanDetailDao.

    }

    /**
     * @param @param flightPlan
     * @return void
     * @throws
     * @Title: vaildPlanDate
     * @Description: 验证此计划日期是不是已经存在
     * @author: chencheng
     * @date： 2016年1月25日 下午1:06:05
     */
    public Message vaildPlanDate(FlightPlan flightPlan) {
        Message message = new Message();
        FlightPlan fp = super.dao.findPlanDateByFlightPlan(flightPlan);
        if (fp != null) {
            // 存在记录的情况下是判断是否已经审核通过，如果已经通过则不能初始化
            // 如果未经过审核那么是可以再次初始化，那么原计划将被覆盖
            Map<String, Object> params = new HashMap<String, Object>();

            if (fp.getStatus().intValue() == 0) {
                // 返回2表示已存在记录，可以覆盖，请选择
                message.setCode(2);
            } else if (fp.getStatus().intValue() > 0) {
                params.put("status", fp.getStatus());
                message.setResult(params);
                // 返回3表示存在记录，不能重新初始化
                message.setCode(3);
            }
        } else {
            // 返回1表示不存在记录，可以进行初始化工作
            message.setCode(1);
        }
        return message;
    }


    /**
     * @param aircraftNum
     * @return
     * @Description: 通过机号获取机型
     * @author: dingshuang
     * @date： 2016年3月2日 下午3:40:05
     */
    public Message getAircraftTypeCodeByNum(String aircraftNum){
        Message message = new Message();
        Map<String,Object> resultMap = super.dao.getAircraftTypeCodeByNum(aircraftNum);
        if(!resultMap.containsKey("AIRCRAFT_MODEL")){
            message.setCode(0);//获取数据失败
        }else{
            message.setCode(1);//获取数据成功
        }
        message.setResult(resultMap);
        return message;
    }

    /*public Message deleteByPlanDate(FlightPlan flightPlan) {
        Message message = new Message();
        FlightPlan fp = super.dao.findPlanDateByFlightPlan(flightPlan);
        super.delete(fp);
        flightPlanDetailDao.delete(new FlightPlanDetail(fp));
        message.setCode(1);
        return message;
    }*/
}