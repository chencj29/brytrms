/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.dao.FlightPlanDao;
import com.thinkgem.jeesite.modules.ams.dao.FlightPlanDetailChangeDao;
import com.thinkgem.jeesite.modules.ams.dao.FlightPlanDetailDao;
import com.thinkgem.jeesite.modules.ams.entity.AmsAddress;
import com.thinkgem.jeesite.modules.ams.entity.FlightPlan;
import com.thinkgem.jeesite.modules.ams.entity.FlightPlanDetail;
import com.thinkgem.jeesite.modules.ams.entity.FlightPlanDetailChange;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.AddressUtils;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 航班计划管理Service
 *
 * @author xiaopo
 * @version 2015-12-24
 */
@Service
@Transactional(readOnly = true)
public class FlightPlanDetailService extends CrudService<FlightPlanDetailDao, FlightPlanDetail> {

    @Autowired
    private FlightPlanService flightPlanService;

    @Autowired
    private FlightPlanDao flightPlanDao;

    @Autowired
    private AmsAddressService amsAddressService;

    @Autowired
    private FlightPlanDetailChangeDao flightPlanDetailChangeDao;

    @Autowired
    private FlightPlanDetailDao flightPlanDetailDao;

    public FlightPlanDetail get(String id) {
        FlightPlanDetail flightPlanDetail = super.get(id);
        // flightPlanDetail.setFlightPlan(flightPlanService.get(flightPlanDetail.getFlightPlan()));
        return flightPlanDetail;
    }

    /**
     * 根据ID查询航班计划变更记录
     *
     * @param id
     * @return
     */
    public FlightPlanDetailChange getFlightPlanDetailChangeById(String id) {
        return flightPlanDetailChangeDao.get(id);
    }

    public List<FlightPlanDetail> findList(FlightPlanDetail flightPlanDetail) {
        return super.findList(flightPlanDetail);
    }

    public Page<FlightPlanDetail> findPage(Page<FlightPlanDetail> page, FlightPlanDetail flightPlanDetail) {
        // flightPlanDetail.getSqlMap().put("dsf", dataScopeFilter(flightPlanDetail.getCurrentUser(), "o", "u"));
        return super.findPage(page, flightPlanDetail);
    }

    @Transactional(readOnly = false)
    public void save(FlightPlanDetail flightPlanDetail) {
        String inoutTypeName = StringUtils.code2InOutType(flightPlanDetail.getInoutTypeCode());
        flightPlanDetail.setInoutTypeName(inoutTypeName);
        FlightPlan flightPlan = flightPlanDao.get(flightPlanDetail.getFlightPlan());
        flightPlanDetail.setPlanDate(flightPlan.getPlanTime());
        super.save(flightPlanDetail);
    }

    /**
     * 保存航班计划变更，变更类型为修改
     *
     * @param flightPlanDetail
     */
    @Transactional(readOnly = false)
    public Message saveChange(FlightPlanDetail flightPlanDetail) {
        String inoutTypeName = StringUtils.code2InOutType(flightPlanDetail.getInoutTypeCode());
        flightPlanDetail.setInoutTypeName(inoutTypeName);

        // 如果状态小于1，那么是在审批前修改的记录，可以直接更新到原表
        // 如果状态大于等于1，那么计划是审批过的，需要走变更流程
        FlightPlanDetailChange flightPlanDetailChange = new FlightPlanDetailChange();
        BeanUtils.copyProperties(flightPlanDetail, flightPlanDetailChange);

        // 设置变更的计划时间
        FlightPlan fp = flightPlanDao.get(flightPlanDetail.getFlightPlan());
        flightPlanDetailChange.setPlanDate(fp.getPlanTime());

        flightPlanDetailChange.setFlightPlanId(flightPlanDetail.getFlightPlan().getId());
        // 把原纪录的ID set到baseId字段
        flightPlanDetailChange.setBaseId(flightPlanDetail.getId());

        User user = UserUtils.getUser();
        flightPlanDetailChange.setApplyUserId(user.getId());
        flightPlanDetailChange.setApplyUserName(user.getName());
        flightPlanDetailChange.setApplyTime(new Date());
        // if (flightPlanDetail.getChangeType().equals("1")) {
        // changType为1是修改类型
        // 查询已存在的变更记录

        // 如果已有变更还没有被审核，那么修改的时候就直接更新掉原来的记录
        // FlightPlanDetailChange existChange = flightPlanDetailChangeDao.findNotApprove(flightPlanDetailChange);

        // if (existChange != null) {
        //     flightPlanDetailChangeDao.updateByBaseId(flightPlanDetailChange);
        // } else {
        flightPlanDetailChange.setId(UUID.randomUUID().toString());
        flightPlanDetailChangeDao.insert(flightPlanDetailChange);
        // }
        // } else if (flightPlanDetail.getChangeType().equals("2")) {
        // changType为2是新增类型
        // flightPlanDetailChange.setId(UUID.randomUUID().toString());
        // flightPlanDetailChangeDao.insert(flightPlanDetailChange);
        //}
        Message message = new Message();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("change", flightPlanDetailChange);
        message.setResult(result);
        return message;
    }

    @Transactional(readOnly = false)
    public void delete(FlightPlanDetail flightPlanDetail) {
        super.delete(flightPlanDetail);
    }

    @Transactional(readOnly = false)
    public Message deleteChange(FlightPlanDetail flightPlanDetail) {
        // 如果状态小于1，那么是在审批前修改的记录，可以直接更新到原表
        // 如果状态大于等于1，那么计划是审批过的，需要走变更流程
        FlightPlanDetailChange flightPlanDetailChange = new FlightPlanDetailChange();
        FlightPlanDetail fpd = super.dao.get(flightPlanDetail.getId());
        BeanUtils.copyProperties(fpd, flightPlanDetailChange);
        flightPlanDetailChange.setFlightPlanId(flightPlanDetail.getFlightPlan().getId());
        // 把原纪录的ID set到baseId字段
        flightPlanDetailChange.setBaseId(flightPlanDetail.getId());
        // if (flightPlanDetail.getChangeType().equals("3")) {
        flightPlanDetailChange.setChangeType(flightPlanDetail.getChangeType());

        // 查询是否已经存在的操作记录
        // FlightPlanDetailChange existChange = flightPlanDetailChangeDao.findNotApprove(flightPlanDetailChange);

        // if (existChange != null) {
        //     flightPlanDetailChangeDao.updateByBaseId(flightPlanDetailChange);
        // } else {
        flightPlanDetailChange.setId(UUID.randomUUID().toString());
        flightPlanDetailChangeDao.insert(flightPlanDetailChange);
        // }
        //}
        Message message = new Message();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("change", flightPlanDetailChange);
        message.setResult(result);

        return message;
    }

    public DataTablesPage<FlightPlanDetail> findDataTablesPage(Page<FlightPlanDetail> page, DataTablesPage<FlightPlanDetail> dataTablesPage,
                                                               FlightPlanDetail entity) {
        page.setPageNo(dataTablesPage.getPageNo());
        page.setPageSize(dataTablesPage.getLength());

        Page<FlightPlanDetail> oldPage = new Page<FlightPlanDetail>();
        if (entity.getFlightPlan() != null && entity.getFlightPlan().getId() != null) {
            oldPage = findPage(page, entity);
        }
        // 获取所有地址
        setAddress(oldPage.getList());
        //
        dataTablesPage.setRecordsTotal((int) oldPage.getCount());
        dataTablesPage.setRecordsFiltered((int) oldPage.getCount());
        dataTablesPage.setData(oldPage.getList());
        return dataTablesPage;
    }

    public DataTablesPage<FlightPlanDetail> findDataTablesPageByDate(Page<FlightPlanDetail> page, DataTablesPage<FlightPlanDetail> dataTablesPage,
                                                                     FlightPlanDetail entity) {
        page.setPageNo(dataTablesPage.getPageNo());
        page.setPageSize(dataTablesPage.getLength());

        Page<FlightPlanDetail> oldPage = new Page<FlightPlanDetail>();
        if (entity.getPlanDate() != null) {
            entity.setPage(page);
            oldPage.setList(dao.findListForInit(entity));
        }
        // 获取所有地址
        setAddress(oldPage.getList());
        //
        dataTablesPage.setRecordsTotal((int) oldPage.getCount());
        dataTablesPage.setRecordsFiltered((int) oldPage.getCount());
        dataTablesPage.setData(oldPage.getList());
        return dataTablesPage;
    }

    public DataTablesPage<FlightPlanDetail> findListByNullFlightNo(Page<FlightPlanDetail> page,
                                                                   DataTablesPage<FlightPlanDetail> dataTablesPage, FlightPlanDetail entity) {
        page.setPageNo(dataTablesPage.getPageNo());
        page.setPageSize(dataTablesPage.getLength());
        Page<FlightPlanDetail> oldPage = findPage2(page, entity);
        //
        setAddress(oldPage.getList());
        //
        dataTablesPage.setRecordsTotal((int) oldPage.getCount());
        dataTablesPage.setRecordsFiltered((int) oldPage.getCount());
        dataTablesPage.setData(oldPage.getList());
        return dataTablesPage;
    }

    public DataTablesPage<FlightPlanDetail> findListByNull(Page<FlightPlanDetail> page, DataTablesPage<FlightPlanDetail> dataTablesPage,
                                                           FlightPlanDetail entity) {
        page.setPageNo(dataTablesPage.getPageNo());
        page.setPageSize(dataTablesPage.getLength());
        Page<FlightPlanDetail> oldPage = findPageByNull(page, entity);
        //
        setAddress(oldPage.getList());
        //
        dataTablesPage.setRecordsTotal((int) oldPage.getCount());
        dataTablesPage.setRecordsFiltered((int) oldPage.getCount());
        dataTablesPage.setData(oldPage.getList());
        return dataTablesPage;
    }

    private void setAddress(List<FlightPlanDetail> oldPage) {
        if (oldPage != null && oldPage.size() > 0) {

            // 获取所有地址
            List<AmsAddress> amsAddresses = amsAddressService.findList(new AmsAddress());
            // set address
            for (FlightPlanDetail detail : oldPage) {
                if (detail.getArrivalAirport() != null)
                    detail.setArrivalAirport(AddressUtils.get(amsAddresses, detail.getArrivalAirport().getId()));
                if (detail.getPassAirport1() != null)
                    detail.setPassAirport1(AddressUtils.get(amsAddresses, detail.getPassAirport1().getId()));
                if (detail.getPassAirport2() != null)
                    detail.setPassAirport2(AddressUtils.get(amsAddresses, detail.getPassAirport2().getId()));
                if (detail.getDepartureAirport() != null)
                    detail.setDepartureAirport(AddressUtils.get(amsAddresses, detail.getDepartureAirport().getId()));
            }
        }
    }

    public Page<FlightPlanDetail> findPage2(Page<FlightPlanDetail> page, FlightPlanDetail entity) {
        entity.setPage(page);
        page.setList(dao.findListByNullFlightNo(entity));
        return page;
    }

    public Page<FlightPlanDetail> findPageByNull(Page<FlightPlanDetail> page, FlightPlanDetail entity) {
        entity.setPage(page);
        page.setList(dao.findListByNull(entity));
        return page;
    }

    public void setFlightNum(String[] flightNumIds) {
        // 生成一个虚拟航班号
        String flightNum = "#" + StringUtils.getRandomCharAndNumr(5);
        Map<String, Object> map = new HashMap<>();
        map.put("flightNum", flightNum);
        map.put("ids", flightNumIds);
        dao.setFlightNum(map);
    }

    public List<Map> findInOutMulti(String planDate) {
        return dao.findInOutMulti(planDate);
    }

    public DataTablesPage<FlightPlanDetail> findListByInOut(FlightPlanDetail flightPlanDetail) {
        List<FlightPlanDetail> flightPlanDetails = dao.findListByInOut(flightPlanDetail);
        DataTablesPage<FlightPlanDetail> dataTablesPage = new DataTablesPage<>();

        // 获取所有地址
        setAddress(flightPlanDetails);

        dataTablesPage.setRecordsTotal(flightPlanDetails.size());
        dataTablesPage.setRecordsFiltered(flightPlanDetails.size());
        dataTablesPage.setData(flightPlanDetails);

        return dataTablesPage;
    }

    public List<FlightPlanDetail> findListByIds(Map map) {
        return dao.findListByIds(map);
    }

    public void audit(FlightPlanDetail flightPlanDetail) {
        dao.audit(flightPlanDetail);
    }

    /**
     * @param @param  id
     * @param @return
     * @return FlightPlanDetailChange
     * @throws
     * @Title: getChangeDetail
     * @Description: 查询修改后的详情
     * @author: chencheng
     * @date： 2016年1月23日 上午11:45:48
     */
    public FlightPlanDetailChange getChangeDetail(FlightPlanDetailChange flightPlanDetailChange) {
        flightPlanDetailChange.setBaseId(flightPlanDetailChange.getId());
        return flightPlanDetailChangeDao.findWaitApprove(flightPlanDetailChange);
    }

    /**
     * @param doit
     * @param @param flightPlanDetailChange
     * @return void
     * @throws 1:确认变更；2：拒绝变更
     * @Title: saveChangeDoit
     * @Description: 执行
     * @author: chencheng
     * @date： 2016年1月23日 下午1:50:23
     */
    @Transactional(readOnly = false)
    public void saveChangeDoit(FlightPlanDetailChange flightPlanDetailChange, String doit) {
        // 如果传过来的flightPlanDetailChange为null就什么都不做，想当与审批员不理睬这件事情
        if (flightPlanDetailChange.getId() == null) {
            return;
        }

        User user = UserUtils.getUser();

        // 查询变更后的记录，这里要根据ID、审核状态=null查询
        //flightPlanDetailChange.setBaseId(flightPlanDetailChange.getId());
        // 改为全部用ID匹配就没有问题了
        FlightPlanDetailChange fpdc = flightPlanDetailChangeDao.get(flightPlanDetailChange);
        // 确认变更
        if (doit.equals("1")) {
            FlightPlanDetail fpd = new FlightPlanDetail();
            BeanUtils.copyProperties(fpdc, fpd);
            FlightPlan flightPlan = new FlightPlan();
            flightPlan.setId(fpdc.getFlightPlanId());
            fpd.setFlightPlan(flightPlan);
            // 将计划状态调整为4：已变更
            fpd.setStatus(4);
            // 变更类型如果为1：修改，那么将新记录更新到老记录中
            if (fpdc.getChangeType().equals("1")) {
                fpd.setId(fpdc.getBaseId());
                super.dao.update(fpd);
            } else if (fpdc.getChangeType().equals("2")) {
                // 如果变更类型为2：新增，那么将新记录直接插入到表中
                super.dao.insert(fpd);
            } else if (fpdc.getChangeType().equals("3")) {
                // 如果变更类型为3：删除，那么将原表中的数据删除
                fpd.setId(fpdc.getBaseId());
                super.dao.delete(fpd);
            }
            // 保存审核记录
            fpdc.setApproveStatus("1");
            fpdc.setApproveTime(new Date());
            fpdc.setApproveUserId(user.getId());
            fpdc.setApproveUserName(user.getName());
            flightPlanDetailChangeDao.update(fpdc);

            FlightPlan basePlan = new FlightPlan();
            // 修改原计划概要状态为4：已变更状态
            basePlan.setId(fpdc.getFlightPlanId());
            basePlan.setStatus(4);
            flightPlanDao.updateStatus(basePlan);
        } else if (doit.equals("2")) {
            // 管理员拒绝变更，那么保存审核记录
            fpdc.setApproveStatus("0");
            fpdc.setApproveTime(new Date());
            fpdc.setApproveUserId(user.getId());
            fpdc.setApproveUserName(user.getName());
            flightPlanDetailChangeDao.update(fpdc);
        }
    }

    /**
     * 航班计划审核，返回审核结果和错误的步骤
     * 审核步骤编号说明：
     * 1:存在飞机号为null的记录
     * 2:存在填写信息不完整记录
     * 3:存在不合理的航班
     * 4:航班计划正确
     * <p>
     * 返回的审核结果将以list返回方式返回
     * 一次性返回所有存在问题的记录
     * <p>
     * chrischen
     *
     * @param flightPlan
     * @return
     */
    public Map<String, Object> approveCheck(FlightPlan flightPlan) {
        FlightPlanDetail flightPlanDetail = new FlightPlanDetail();
        flightPlanDetail.setFlightPlan(flightPlan);

        Map<String, Object> stepAndResult = new HashMap<String, Object>();
        // 默认步骤是4，表示是通过的
        Integer step = 4;

        // 第一步，首先验证是否存在飞机号为null的记录
        List<FlightPlanDetail> flightNumNullList = flightPlanDetailDao.findListByNullFlightNo(flightPlanDetail);
        if (flightNumNullList != null && !flightNumNullList.isEmpty()) {
            step = 1;
            stepAndResult.put("step", step);
            stepAndResult.put("flightPlanList", flightNumNullList);
            return stepAndResult;
        }

        // 第二步，验证是否存在必填项为null的记录
        List<FlightPlanDetail> attrNullList = flightPlanDetailDao.findListByNull(flightPlanDetail);
        if (attrNullList != null && !attrNullList.isEmpty()) {
            step = 2;
            stepAndResult.put("step", step);
            stepAndResult.put("attrNullList", attrNullList);
            return stepAndResult;
        }

        // 第三步，验证是否存在不合理的航班，同一天、同一个航空公司、同一个航班性质不允许存在相同的航班号
        List<FlightPlanDetail> abnormallList = flightPlanDetailDao.findAbnormalList(flightPlanDetail);
        if (abnormallList != null && !abnormallList.isEmpty()) {
            step = 3;
            stepAndResult.put("step", step);
            stepAndResult.put("aircraftList", abnormallList);
            return stepAndResult;
        }

        // 以上三步验证全部通过，那么直接到成功页面，填写审核信息
        return stepAndResult;
    }
}