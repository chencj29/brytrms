/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import cn.net.metadata.gantt.wrapper.CarouselGanttWrapper;
import cn.net.metadata.gantt.wrapper.GateGanttWrapper;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.dao.*;
import com.thinkgem.jeesite.modules.ams.entity.*;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.ams.entity.wrapper.CarouselListWrapper;
import com.thinkgem.jeesite.modules.ams.entity.wrapper.CarouselWrapper;
import com.thinkgem.jeesite.modules.ams.event.FlightDynamicEvent;
import com.thinkgem.jeesite.modules.ams.event.FlightDynamicInitEvent;
import com.thinkgem.jeesite.modules.ams.event.SyncTypeEnum;
import com.thinkgem.jeesite.modules.ams.utils.AddEventUtils;
import com.thinkgem.jeesite.modules.ams.utils.AmsConstants;
import com.thinkgem.jeesite.modules.rms.dao.FlightPlanPairDao;
import com.thinkgem.jeesite.modules.rms.entity.*;
import com.thinkgem.jeesite.modules.rms.service.GateOccupyingInfoService;
import org.apache.velocity.util.ArrayListWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 航班动态管理Service
 *
 * @author xiaopo
 * @version 2016-01-10
 */
@Service
@Transactional(readOnly = true)
public class FlightDynamicService extends CrudService<FlightDynamicDao, FlightDynamic> {

    private final static Logger logger = LoggerFactory.getLogger(FlightDynamicService.class);

    @Autowired
    GateOccupyingInfoService gateOccupyingInfoService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private FlightPlanDetailService flightPlanDetailService;
    @Autowired
    private ShareFlightDao shareFlightDao;
    @Autowired
    private FlightPlanDao flightPlanDao;
    @Autowired
    private FlightNatureDao flightNatureDao;
    @Autowired
    private AmsAddressDao amsAddressDao;
    @Autowired
    private FlightPlanPairDao flightPlanPairDao;

    public FlightDynamic get(String id) {
        return super.get(id);
    }

    public List<FlightDynamic> findList(FlightDynamic flightDynamic) {
        flightDynamic.getSqlMap().put("dsfn", dataScopeFilterNew(flightDynamic.getCurrentUser()));
        return super.findList(flightDynamic);
    }

    public Page<FlightDynamic> findPage(Page<FlightDynamic> page, FlightDynamic flightDynamic) {
        return super.findPage(page, flightDynamic);
    }

    @Transactional
    public void save(FlightDynamic flightDynamic) {
        super.save(flightDynamic);
    }

    @Transactional
    public void delete(FlightDynamic flightDynamic) {
        super.delete(flightDynamic);
    }


    public void dynamicInit(Date planDate) {
        // 查询出flightplandetail 添加到flightdynamic中
        List<FlightDynamic> dynamics = new ArrayList<>();
        FlightDynamic dynamic = null;
        FlightPlanDetail flightPlanDetail = new FlightPlanDetail();
        flightPlanDetail.setPlanDate(planDate);
        List<FlightPlanDetail> details = flightPlanDetailService.findList(flightPlanDetail);
        for (FlightPlanDetail detail : details) {
            //更改计划状态
            detail.setStatus(3);
            flightPlanDetailService.audit(detail);

            // 查询是否已经存在当前计划被初始化后的值
            dynamic = new FlightDynamic();
            BeanUtils.copyProperties(detail, dynamic);
            dynamic.setId(null);
            //
            String flightPlanId = detail.getFlightPlan().getId();
            //查询计划日期
            FlightPlan flightPlan = flightPlanDao.get(new FlightPlan(flightPlanId));
            //
            dynamic.setFlightPlan(flightPlan);
            dynamic.setPlanDate(flightPlan.getPlanTime());

            // set一下航班动态的星期数，planDayOfWeek
            dynamic.setPlanDayOfWeek(DateUtils.getWeekChinese(flightPlan.getPlanTime()));

            // 查询是否已经存在的动态
            FlightDynamic dynamicTemp = this.get(dynamic);
            if (dynamicTemp != null) {
                this.delete(dynamicTemp);
            }

            // 设置计划登机时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dynamic.getDeparturePlanTime());
            calendar.add(Calendar.MINUTE, -30);
            dynamic.setPlanBoardingStartTime(calendar.getTime());
            // 设置计划登机结束时间
            dynamic.setPlanBoardingEndTime(dynamic.getDeparturePlanTime());

            // 写入共享航班数据
            getShareFlightNum(dynamic);

            this.save(dynamic);
            //

            // 初始化了一个航班动态,发布航班动态初始化事件
            FlightDynamicInitEvent flightPlanInitEvent = new FlightDynamicInitEvent(dynamic);
            applicationContext.publishEvent(flightPlanInitEvent);
        }
        // 更改flightplan的status
        if (details != null && details.size() > 0) {
            FlightPlan flightPlan = new FlightPlan();
            flightPlan.setId(details.get(0).getFlightPlan().getId());
            flightPlan.setStatus(3);
            flightPlanDao.updateStatus(flightPlan);
        }

    }

    /**
     * 将共享航班号写入航班动态
     *
     * @param dynamic
     */
    private void getShareFlightNum(FlightDynamic dynamic) {
        // 按照班期、航空公司、航班号查询出共享航班
        ShareFlight sf = new ShareFlight();
        String week = DateUtils.getWeekChinese(dynamic.getPlanDate());
        sf.setDaysOpt(week);
        sf.setMasterAirlineCode(dynamic.getFlightCompanyCode());
        sf.setMasterFlightNo(dynamic.getFlightNum());
        List<ShareFlight> shareFlightList = shareFlightDao.findByDynamic(sf);

        if (shareFlightList != null && !shareFlightList.isEmpty()) {
            StringBuffer newShareFlightNum = new StringBuffer();
            for (int i = 0; i < shareFlightList.size(); i++) {
                // 寻找能够匹配上的航班,根据(航班号,航空公司,班期)来匹配更新
                if (i < shareFlightList.size() - 1) {
                    newShareFlightNum.append(shareFlightList.get(i).getSlaveFlightNo()).append(" ");
                } else {
                    newShareFlightNum.append(shareFlightList.get(i).getSlaveFlightNo());
                }
            }
            dynamic.setShareFlightNum(newShareFlightNum.toString());
        }
    }


    public void delay(FlightDynamic flightDynamic) {
        if (flightDynamic.getDelayTimePend() == 1) {
            flightDynamic.setEtd(null);
            flightDynamic.setEta(null);
        }
        // 设置航班状态
        flightDynamic.setStatus(AmsConstants.FLIGHT_STATUS_DELAY);
        flightDynamic.setStatusName(AmsConstants.FLIGHT_STATUS_DELAY_NAME);
        // 时间待定
        dao.delay(flightDynamic);
    }

    public DataTablesPage<Map> findDataTablesPagePair(Page<Map> page, DataTablesPage<Map> dataTablesPage, FlightPairWrapper entity) {
        page.setPageNo(dataTablesPage.getPageNo());
        page.setPageSize(dataTablesPage.getLength());

        page.setList(dao.getPairFlightList(entity));
        //
        dataTablesPage.setRecordsTotal((int) page.getCount());
        dataTablesPage.setRecordsFiltered((int) page.getCount());
        dataTablesPage.setData(page.getList());
        return dataTablesPage;
    }

    public void saveFlightDynamic(FlightDynamic flightDynamic) {
        // 判断该航班是否存在同一个计划日期 同一个飞机号 相同的连进 连出问题
        int count = dao.queryDoubleInOutFlight(flightDynamic);
        if (count != 0) {
            throw new RuntimeException("当前计划日期,该飞机号已经存在相同任务");
        }
        save(flightDynamic);
    }

    public void updateStatus(String id, String statusCode, String statusName) {
        if (id.isEmpty() || statusCode.isEmpty() || statusCode.isEmpty()) {
            throw new RuntimeException("ID,状态编码,状态名称不能缺少");
        }
        FlightDynamic dynamic = new FlightDynamic();
        dynamic.setId(id);
        dynamic.setStatus(statusCode);
        dynamic.setStatusName(statusName);

        dao.updateStatus(dynamic);
    }

    public List<FlightPairWrapper> queryListByIds(ArrayList<String> ids) {
        List<FlightPairWrapper> dynamics = new ArrayList<>();
        dynamics = dao.queryPairForChangeAircraft(ImmutableMap.of("ids", ids));
        return dynamics;
    }

    /**
     * 定义事物隔离级别  可以让该循环读取到未提交的数据
     *
     * @param id
     * @param aircraftNum
     */
    public void changeAircraftNum(ArrayList<String> id, ArrayList<String> aircraftNum) {
        for (int i = 0; i < id.size(); i++) {
            //
            if (id == null || aircraftNum == null || id.size() != aircraftNum.size())
                throw new RuntimeException("系统错误,请联系管理员");
            if (StringUtils.isNotBlank(id.get(i)) && StringUtils.isNotBlank(aircraftNum.get(i))) {
                // 判断新机号是否具有连进练出
                // --根据id查询该航班是进出港状态
                FlightDynamic fd = dao.get(new FlightDynamic(id.get(0)));
                fd.setAircraftNum(aircraftNum.get(i));
                // --根据进出港状态和新机号和计划日期查询是否已经存在航班
                Integer count = dao.queryCountByPlanDateFlightNumInout(fd);
                if (count != null && count > 1) {
                    throw new RuntimeException(String.format("机号:%s,计划日期:%s,进出港:%s.已经存在,请更换新机号", fd.getAircraftNum(), DateUtils.formatDate(fd.getPlanDate()), fd.getInoutTypeCode()));
                }
                // 更换新机号
                FlightDynamic dynamic = new FlightDynamic();
                dynamic.setId(id.get(i));
                dynamic.setAircraftNum(aircraftNum.get(i));
                dao.updateAircraftNum(dynamic);
            }
        }
    }

    public void flightUrgeBoard(FlightDynamic flightDynamic, Date urgeBoradTime) {
        FlightDynamic outDynamic = new FlightDynamic();
        BeanUtils.copyProperties(flightDynamic, outDynamic);
        // 根据固定英文名称找到对应的性质名称
        FlightNature nature = new FlightNature();
        nature.setEnglishName("RTN");
        nature = flightNatureDao.getBy(nature);
        // 修改航班性质为返航
        flightDynamic.setFlightNatureCode(nature.getEnglishName());
        flightDynamic.setFlightNatureName(nature.getNatureName());
        // 如果urgeBoardTime 存在,修改计划到达时间
        if (urgeBoradTime != null) {
            flightDynamic.setEta(urgeBoradTime);
        }
        // update
        save(flightDynamic);

        // 依据当前航班 添加一个出港航班
        outDynamic.setId(null);
        outDynamic.setFlightNum(outDynamic.getFlightNum() + "/F");
        outDynamic.setStatus("");
        outDynamic.setStatusName("");
        if (urgeBoradTime == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR, 1);
            outDynamic.setEtd(calendar.getTime());
        }
        save(outDynamic);
    }

    /**
     * 该方法用于重新还原航班动态对象
     */
    public FlightDynamic restoreFlightDynamic(FlightDynamic flightDynamic) {
        flightDynamic.setStatus(AmsConstants.FLIGHT_STATUS_INIT);
        flightDynamic.setStatusName(AmsConstants.FLIGHT_STATUS_INIT_NAME);
        flightDynamic.setPlaceNum(""); // 机位号
        flightDynamic.setShareFlightNum("");//共享航班号
        flightDynamic.setInoutStatusCode("");//进出港编码
        flightDynamic.setInoutStatusName("");//进出港名称
        flightDynamic.setDepartureAirportThree("");//起飞地三字码
        flightDynamic.setArrivalAirportCode("");//到达地三字码
        flightDynamic.setEtd(null);//预计起飞时间
        flightDynamic.setEta(null);//预计到达时间
        flightDynamic.setAtd(null);//实际起飞时间
        flightDynamic.setAta(null);//实际到达时间
        flightDynamic.setTravelTime(null);//行程时间
        flightDynamic.setCarouselNum("");//行李转盘编号
        flightDynamic.setVip("");//vip
        flightDynamic.setDelayResonsAreaCode("");//延误原因编号
        flightDynamic.setDelayResonsAreaName("");//延误原因名称
        flightDynamic.setDelayResonsRemark("");//延误备注
        flightDynamic.setGateCode("");//登机口编号
        flightDynamic.setGateCode("");//登机口名称
        flightDynamic.setBoardingStartTime(null);//登机开始时间
        flightDynamic.setBoardingEndTime(null);//登机结束时间
        flightDynamic.setCheckinIslandCode("");//值机岛编号
        flightDynamic.setCheckinIslandCode("");//值机岛名称
        flightDynamic.setCheckinCounterCode("");//值机站台编号
        flightDynamic.setCheckinCounterName("");//值机站台名称
        flightDynamic.setCheckinStartTime(null);//值机开始时间
        flightDynamic.setCheckinEndTime(null);//值机结束时间
        flightDynamic.setDelayTimePend(0);
        flightDynamic.setAlterNateRemarks("");
        flightDynamic.setPlanDayOfWeek("");
        flightDynamic.setDelayResonsAreaCode("");
        flightDynamic.setDelayResonsAreaName("");
        flightDynamic.setAlterNateAreaCode("");
        flightDynamic.setAlterNateAreaName("");
        flightDynamic.setCancelFlightResons("");
        flightDynamic.setIsExtraFlight(0);
        flightDynamic.setExtraFlightTimePend("");
        return flightDynamic;
    }

    /**
     * 分页查询已完成的动态数据
     *
     * @param page
     * @param flightDynamic
     * @return
     */
    public Page<FlightDynamic> queryCompletedFlightDynamic(Page<FlightDynamic> page, FlightDynamic flightDynamic) {
        flightDynamic.setPage(page);
        page.setList(dao.findCompletedByDate(flightDynamic));
        return page;
    }

    @Transactional(readOnly = true)
    public List<FlightDynamic> findByPlaceNumber(String placeNumber) {
        return dao.findByPlaceNum(placeNumber);
    }

    /**
     * 查询进出港属性,使用航班配对表
     *
     * @param flightDynamic
     * @return
     */
    @Transactional(readOnly = true)
    public Map<String, Object> queryInOutTypeByDynamic(FlightDynamic flightDynamic) {
        Map<String, Object> result = new HashMap<>();

        // 查询是否是进港航班
        FlightPlanPair flightPlanPair = flightPlanPairDao.queryByInDynamicId(flightDynamic);
        FlightDynamic inDynamic = null;
        FlightDynamic outDynamic = null;
        if (flightPlanPair != null) {
            // 查询进港航班记录
            inDynamic = super.dao.get(flightPlanPair.getFlightDynimicId());
            // 如果出港不为null,那么是连班
            if (flightPlanPair.getFlightDynimicId2() != null) {
                // 那么是连班的
                outDynamic = super.dao.get(flightPlanPair.getFlightDynimicId2());
                result.put("inoutType", "RELATE");
            } else {
                // 那么是单进的
                result.put("inoutType", "SI");
            }
        } else {
            flightPlanPair = flightPlanPairDao.queryByOutDynamicId(flightDynamic);
            // 那么是单出的
            result.put("inoutType", "SO");
            outDynamic = super.dao.get(flightDynamic.getId());
        }

        result.put("J", inDynamic);
        result.put("C", outDynamic);

        return result;
    }


    public Map<String, Object> queryInoutTypeByPair(FlightPlanPair flightPlanPair) {
        Map<String, Object> result = new HashMap<>();

        // 查询是否是进港航班
        FlightDynamic inDynamic = null;
        FlightDynamic outDynamic = null;
        if (flightPlanPair != null) {
            if (flightPlanPair.getFlightDynimicId() != null) {
                // 查询进港航班记录
                try {
                    inDynamic = super.dao.get(flightPlanPair.getFlightDynimicId());
                } catch (Exception e) {
                    e.printStackTrace();
                    inDynamic = null;
                }
                // 如果出港不为null,那么是连班
                if (flightPlanPair.getFlightDynimicId2() != null) {
                    // 那么是连班的
                    try {
                        outDynamic = super.dao.get(flightPlanPair.getFlightDynimicId2());
                    } catch (Exception e) {
                        outDynamic = null;
                    }
                    result.put("inoutType", "RELATE");
                } else {
                    // 那么是单进的
                    result.put("inoutType", "SI");
                }
            } else {
                if (flightPlanPair.getFlightDynimicId2() != null) {
                    // 那么是单出的
                    result.put("inoutType", "SO");
                    try {
                        outDynamic = super.dao.get(flightPlanPair.getFlightDynimicId2());
                    } catch (Exception e) {
                        outDynamic = null;
                    }
                }
            }

            result.put("J", inDynamic);
            result.put("C", outDynamic);
        }
        return result;
    }

    /**
     * @param pairId
     * @return
     */
    @Transactional(readOnly = true)
    public Map<String, Object> queryInOutTypeByPairId(String pairId) {
        return queryInoutTypeByPair(flightPlanPairDao.get(pairId));
    }


    @Transactional(readOnly = true)
    public List<String> findOccupiedGates(Map<String, Object> params) {
        return dao.findOccupiedGates(params);
    }

    @Transactional(readOnly = true)
    public List<String> findoccupiedGates4Mock(Map<String, Object> params) {
        return dao.findoccupiedGates4Mock(params);
    }

    @Transactional(readOnly = true)
    public List<FlightDynamic> findMockDynamics4ArrivalGate(Map<String, Object> params) {
        return dao.findMockDynamics4ArrivalGate(params);
    }

    @Transactional(readOnly = true)
    public List<FlightDynamic> findMockDynamics4BoardingGate(Map<String, Object> params) {
        return dao.findMockDynamics4BoardingGate(params);
    }

    @Transactional(readOnly = true)
    public List<FlightDynamic> findMockDynamics4SlideCoast(Map<String, Object> params) {
        return dao.findMockDynamics4SlideCoast(params);
    }

    @Transactional(readOnly = true)
    public List<FlightDynamic> findMockDynamics4CheckinCounter(Map<String, Object> params) {
        return dao.findMockDynamics4CheckinCounter(params);
    }

    @Transactional(readOnly = true)
    public List<FlightDynamic> findMockDynamics4SecurityCheckin(Map<String, Object> params) {
        return dao.findMockDynamics4SecurityCheckin(params);
    }

    @Transactional(readOnly = true)
    public List<FlightDynamic> findMockDynamics4DepartureHall(Map<String, Object> params) {
        return dao.findMockDynamics4DepartureHall(params);
    }

    @Transactional(readOnly = true)
    public List<FlightDynamic> findMockDynamics4Carousel(Map<String, Object> params) {
        return dao.findMockDynamics4Carousel(params);
    }

    @Transactional(readOnly = true)
    public List<String> findOccupiedArrivalGateCodes4Mock(Map<String, Object> params) {
        return dao.findOccupiedArrivalGateCodes4Mock(params);
    }

    @Transactional(readOnly = true)
    public List<String> findOccupiedBoardingGateCodes4Mock(Map<String, Object> params) {
        return dao.findOccupiedBoardingGateCodes4Mock(params);
    }

    @Transactional(readOnly = true)
    public List<String> findOccupiedSlideCoastCodes4Mock(Map<String, Object> params) {
        return dao.findOccupiedSlideCoastCodes4Mock(params);
    }

    @Transactional(readOnly = true)
    public List<String> findOccupiedCarouselCodes4Mock(Map<String, Object> params) {
        return dao.findOccupiedCarouselCodes4Mock(params);
    }

    @Transactional(readOnly = true)
    public List<String> findOccupiedCarouselCodes(Map<String, Object> params) {
        return dao.findOccupiedCarouselCodes(params);
    }

    @Transactional(readOnly = true)
    public List<String> findOccupiedArrivalGateCodes(Map<String, Object> params) {
        return dao.findOccupiedArrivalGateCodes(params);
    }

    @Transactional(readOnly = true)
    public List<String> findOccupiedBoardingGateCodes(Map<String, Object> params) {
        return dao.findOccupiedBoardingGateCodes(params);
    }

    @Transactional(readOnly = true)
    public List<String> findOccupiedSlideCoastCodes(Map<String, Object> params) {
        return dao.findOccupiedSlideCoastCodes(params);
    }

    @Transactional(readOnly = true)
    public List<FlightDynamic> findUnDistDynamics() {
        return dao.findUnDistDynamics();
    }

    @Transactional(readOnly = true)
    public List<FlightDynamic> findCarouselUnDistDynamics(Map<String, Object> params) {
        return dao.findCarouselUnDistDynamics(params);
    }

    @Transactional(readOnly = true)
    public List<FlightDynamic> findArrivalGateUnDistDynamics(Map<String, Object> params) {
        return dao.findArrivalGateUnDistDynamics(params);
    }

    @Transactional(readOnly = true)
    public List<FlightDynamic> findBoardingGateUnDistDynamics(Map<String, Object> params) {
        return dao.findBoardingGateUnDistDynamics(params);
    }

    @Transactional(readOnly = true)
    public List<FlightDynamic> findSlideCoastUnDistDynamics(Map<String, Object> params) {
        return dao.findSlideCoastUnDistDynamics(params);
    }

    @Transactional(readOnly = true)
    public List<FlightDynamic> findCheckinCounterUnDistDynamics(Map<String, Object> params) {
        return dao.findCheckinCounterUnDistDynamics(params);
    }

    @Transactional(readOnly = true)
    public List<FlightDynamic> findDepartureHallUnDistDynamics(Map<String, Object> params) {
        return dao.findDepartureHallUnDistDynamics(params);
    }

    @Transactional(readOnly = true)
    public List<FlightDynamic> findSecurityCheckinUnDistDynamics(Map<String, Object> params) {
        return dao.findSecurityCheckinUnDistDynamics(params);
    }

    @Transactional(readOnly = true)
    public List<CarouselOccupyingInfoWrapper> carouselList(FlightDynamic flightDynamic) {
        flightDynamic.getSqlMap().put("dsfn", dataScopeFilterNew(flightDynamic.getCurrentUser()));
        return dao.carouselList(flightDynamic);
    }

    @Transactional(readOnly = true)
    public List<BoardingGateOccupyingInfoWrapper> arrivalGateList(FlightDynamic flightDynamic) {
        flightDynamic.getSqlMap().put("dsfn", dataScopeFilterNew(flightDynamic.getCurrentUser()));
        return dao.arrivalGateList(flightDynamic);
    }

    @Transactional(readOnly = true)
    public List<CarouselListWrapper> boardingGateList(FlightDynamic flightDynamic) {
        flightDynamic.getSqlMap().put("dsfn", dataScopeFilterNew(flightDynamic.getCurrentUser()));
        return dao.boardingGateList(flightDynamic);
    }

    @Transactional(readOnly = true)
    public List<CarouselOccupyingInfoWrapper> slideCoastList(FlightDynamic flightDynamic) {
        flightDynamic.getSqlMap().put("dsfn", dataScopeFilterNew(flightDynamic.getCurrentUser()));
        return dao.slideCoastList(flightDynamic);
    }

    @Transactional(readOnly = true)
    public List<CheckingCounterOccupyingInfoWrapper> checkinCounterList(FlightDynamic flightDynamic) {
        flightDynamic.getSqlMap().put("dsfn", dataScopeFilterNew(flightDynamic.getCurrentUser()));
        return dao.checkinCounterList(flightDynamic);
    }

    @Transactional(readOnly = true)
    public List<DepartureHallOccupyingInfoWrapper> departureHallList(FlightDynamic flightDynamic) {
        flightDynamic.getSqlMap().put("dsfn", dataScopeFilterNew(flightDynamic.getCurrentUser()));
        return dao.departureHallList(flightDynamic);
    }

    @Transactional(readOnly = true)
    public List<SecurityCheckinOccupyingInfoWrapper> securityCheckinList(FlightDynamic flightDynamic) {
        flightDynamic.getSqlMap().put("dsfn", dataScopeFilterNew(flightDynamic.getCurrentUser()));
        return dao.securityCheckinList(flightDynamic);
    }

    @Transactional(readOnly = true)
    public List<CarouselGanttWrapper> listCarouselJson(Date startDate, Date overDate) {
        overDate = DateUtils.addDays(overDate, 1);
        List<CarouselWrapper> carouselWrapperList = dao.distedCarouselList(ImmutableMap.of("start", startDate, "over", overDate));
        return listDistedData(carouselWrapperList);
    }

    @Transactional(readOnly = true)
    public List<CarouselGanttWrapper> listArrivalGateJson(Date startDate, Date overDate) {
        overDate = DateUtils.addDays(overDate, 1);
        List<CarouselWrapper> carouselWrapperList = dao.distedArrivalGateList(ImmutableMap.of("start", startDate, "over", overDate));
        return listDistedData(carouselWrapperList);
    }

    @Transactional(readOnly = true)
    public List<CarouselGanttWrapper> listBoardingGateJson(Date startDate, Date overDate) {
        overDate = DateUtils.addDays(overDate, 1);
        List<CarouselWrapper> carouselWrapperList = dao.distedBoardingGateList(ImmutableMap.of("start", startDate, "over", overDate));
        return listDistedData(carouselWrapperList);
    }

    @Transactional(readOnly = true)
    public List<CarouselGanttWrapper> listSlideCoastJson(Date startDate, Date overDate) {
        overDate = DateUtils.addDays(overDate, 1);
        List<CarouselWrapper> carouselWrapperList = dao.distedSlideCoastList(ImmutableMap.of("start", startDate, "over", overDate));
        return listDistedData(carouselWrapperList);
    }

    @Transactional(readOnly = true)
    public List<CarouselGanttWrapper> listCheckinCounterJson(Date startDate, Date overDate) {
        overDate = DateUtils.addDays(overDate, 1);
        List<CarouselWrapper> carouselWrapperList = dao.distedCheckinCounterList(ImmutableMap.of("start", startDate, "over", overDate));
        return listDistedData(carouselWrapperList);
    }

    @Transactional(readOnly = true)
    public List<CarouselGanttWrapper> listDepartureHallJson(Date startDate, Date overDate) {
        List<CarouselWrapper> carouselWrapperList = dao.distedDepartureHallList(ImmutableMap.of("start", startDate, "over", overDate));
        return listDistedData(carouselWrapperList);
    }

    @Transactional(readOnly = true)
    public List<CarouselGanttWrapper> listSecurityCheckinJson(Date startDate, Date overDate) {
        overDate = DateUtils.addDays(overDate, 1);
        List<CarouselWrapper> carouselWrapperList = dao.distedSecurityCheckinList(ImmutableMap.of("start", startDate, "over", overDate));
        return listDistedData(carouselWrapperList);
    }

    @Transactional(readOnly = true)
    public List<GateGanttWrapper> listGateJson(Date startDate, Date overDate) {
        overDate = DateUtils.addDays(overDate, 1);
        return flightPlanPairDao.findPairByPlanDate(ImmutableMap.of("startDate", startDate, "overDate", overDate));
    }

    public List<String> getDateRange() {
        Date today = new Date();
        List<String> dateList = dao.getDateRange().stream().map(range -> range.get("PLAN_DATE")).collect(Collectors.toList());
        if (!dateList.isEmpty()) {
            try {
                Date lastDate = DateUtils.parseDate(dateList.get(dateList.size() - 1));
                dateList.add(DateUtils.formatDate(DateUtils.addDays(lastDate, 1), "yyyy-MM-dd"));
                String previousDate = DateUtils.formatDate(DateUtils.addDays(today, -1), "yyyy-MM-dd");
                String nextDate = DateUtils.formatDate(DateUtils.addDays(today, 1), "yyyy-MM-dd");
                if (!dateList.contains(previousDate)) dateList.add(previousDate);
                if (!dateList.contains(nextDate)) dateList.add(nextDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return dateList;
    }

    public List<GateGanttWrapper> convertGanttWrappersByFlightPlanPair(List<FlightPlanPair> flightPlanPairs) {
        List<GateGanttWrapper> wrappers = Lists.newArrayList();
        for (FlightPlanPair flightPlanPair : flightPlanPairs) {
            GateOccupyingInfo resultEntity = gateOccupyingInfoService.getByFlightDynamicId(flightPlanPair.getId());
            if (resultEntity == null) continue;

            GateGanttWrapper ganttWrapper = new GateGanttWrapper();
            ganttWrapper.setFlightDynamicId(flightPlanPair.getId());
            FlightDynamic inDynamic = dao.get(flightPlanPair.getFlightDynimicId());
            FlightDynamic outDynamic = dao.get(flightPlanPair.getFlightDynimicId2());

            if (inDynamic != null) {
                ganttWrapper.setInCode(inDynamic.getFlightCompanyCode() + inDynamic.getFlightNum());
                ganttWrapper.setText(ganttWrapper.getInCode());
            }

            if (outDynamic != null) {
                ganttWrapper.setOutCode(outDynamic.getFlightCompanyCode() + outDynamic.getFlightNum());
                String text = StringUtils.trimToEmpty(ganttWrapper.getText());
                if (StringUtils.isNotBlank(text)) text += "/";
                ganttWrapper.setText(text + ganttWrapper.getOutCode());
            }

            ganttWrapper.setText(ganttWrapper.getText() + " " + StringUtils.trimToEmpty(flightPlanPair.getAircraftTypeCode()));
            ganttWrapper.setPlanDate(DateUtils.formatDate(flightPlanPair.getPlanDate(), "yyyy-MM-dd"));

            if (StringUtils.isNotEmpty(flightPlanPair.getPlaceNum()))
                ganttWrapper.setGate(Splitter.on(",").splitToList(flightPlanPair.getPlaceNum()));
            else ganttWrapper.setGate(Lists.newArrayList());

            ganttWrapper.setTypeCode(StringUtils.trimToEmpty(flightPlanPair.getAircraftTypeCode()));  // 机型

            //设置航班状态
            String flightStatus = StringUtils.isNotBlank(flightPlanPair.getStatus2()) ? StringUtils.trim(flightPlanPair.getStatus2()) : StringUtils.trim(flightPlanPair.getStatus());
            ganttWrapper.setStatus(StringUtils.trimToEmpty(flightStatus));
            ganttWrapper.setStartDate(DateUtils.formatDate(resultEntity.getStartTime(), "yyyy-MM-dd"));
            ganttWrapper.setEndDate(DateUtils.formatDate(resultEntity.getOverTime(), "yyyy-MM-dd"));
            ganttWrapper.setStartTime(DateUtils.formatDate(resultEntity.getStartTime(), "HH:mm"));
            ganttWrapper.setEndTime(DateUtils.formatDate(resultEntity.getOverTime(), "HH:mm"));
            ganttWrapper.setLeave(resultEntity.getLeave());
            ganttWrapper.setLeaveTime(resultEntity.getLeaveTime());
            ganttWrapper.setExpectedGate(resultEntity.getExpectedGateNum().intValue());
            ganttWrapper.setAircraftStartDate(ganttWrapper.getStartDate() + " " + ganttWrapper.getStartTime()); // 起飞时间
            ganttWrapper.setAircraftOverDate(ganttWrapper.getEndDate() + " " + ganttWrapper.getEndTime()); // 到达时间
            ganttWrapper.setId(resultEntity.getId());

            wrappers.add(ganttWrapper);
        }
        return wrappers;
    }

    private List<CarouselGanttWrapper> listDistedData(List<CarouselWrapper> datas) {
        List<CarouselGanttWrapper> wrappers = Lists.newArrayList();
        datas.forEach(carouselWrapper -> {
            CarouselGanttWrapper template = new CarouselGanttWrapper();
            template.setFlightDynamicNature(carouselWrapper.getFlightDynamicNature());
            template.setInteActualOverTime(DateUtils.formatDateTime(carouselWrapper.getInteActualOverTime()));
            template.setInteActualStartTime(DateUtils.formatDateTime(carouselWrapper.getInteActualStartTime()));
            template.setIntlActualOverTime(DateUtils.formatDateTime(carouselWrapper.getIntlActualOverTime()));
            template.setIntlActualStartTime(DateUtils.formatDateTime(carouselWrapper.getIntlActualStartTime()));
            if (StringUtils.isNotEmpty(carouselWrapper.getInteCarouselCode()))
                template.setInteCode(Splitter.on(",").splitToList(carouselWrapper.getInteCarouselCode()));
            else template.setInteCode(Lists.newArrayList());
            if (StringUtils.isNotEmpty(carouselWrapper.getIntlCarouselCode()))
                template.setIntlCode(Splitter.on(",").splitToList(carouselWrapper.getIntlCarouselCode()));
            else template.setIntlCode(Lists.newArrayList());
            template.setFlightDynamicId(carouselWrapper.getFlightDynamicId());
            template.setFlightDynamicCode(carouselWrapper.getFlightDynamicCode());

            template.setId(carouselWrapper.getId());
            template.setStartDate(DateUtils.formatDate(carouselWrapper.getExpectedStartTime()));
            template.setEndDate(DateUtils.formatDate(carouselWrapper.getExpectedOverTime()));
            template.setStartTime(DateUtils.formatDate(carouselWrapper.getExpectedStartTime(), "HH:mm"));
            template.setEndTime(DateUtils.formatDate(carouselWrapper.getExpectedOverTime(), "HH:mm"));
            template.setText(carouselWrapper.getFlightCompanyCode() + template.getFlightDynamicCode() + " " + StringUtils.trimToEmpty(carouselWrapper.getAircraftTypeCode()));
            template.setStatus(carouselWrapper.getStatus());
            template.setFlightCompanyCode(carouselWrapper.getFlightCompanyCode());
            template.setPlanDate(DateUtils.formatDate(carouselWrapper.getPlanDate(), "yyyy-MM-dd"));
            template.setAgentCode(carouselWrapper.getAgentCode());

            if (template.getInteCode().isEmpty() && template.getIntlCode().isEmpty()) {
                CarouselGanttWrapper wrapper = new CarouselGanttWrapper();
                BeanUtils.copyProperties(template, wrapper);
                wrapper.setExpectedGate(1);
                wrapper.getExtra().put("codeNature", carouselWrapper.getFlightDynamicNature());
                wrappers.add(wrapper);
            } else {
                if (StringUtils.equals(template.getFlightDynamicNature(), "1")) template.getInteCode().forEach(code -> {
                    CarouselGanttWrapper wrapper = new CarouselGanttWrapper();
                    BeanUtils.copyProperties(template, wrapper);
                    wrapper.setGate(Lists.newArrayList(code));
                    wrapper.setExpectedGate(1);
                    wrapper.getExtra().put("codeNature", "1");
                    wrappers.add(wrapper);
                });
                else if (StringUtils.equals(template.getFlightDynamicNature(), "2"))
                    template.getIntlCode().forEach(code -> {
                        CarouselGanttWrapper wrapper = new CarouselGanttWrapper();
                        BeanUtils.copyProperties(template, wrapper);
                        wrapper.setExpectedGate(1);
                        wrapper.getExtra().put("codeNature", "2");
                        wrapper.setGate(Lists.newArrayList(code));
                        wrappers.add(wrapper);
                    });
                else if (StringUtils.equals(template.getFlightDynamicNature(), "3")) {
                    template.getInteCode().forEach(code -> {
                        CarouselGanttWrapper wrapper = new CarouselGanttWrapper();
                        BeanUtils.copyProperties(template, wrapper);
                        wrapper.setExpectedGate(1);
                        wrapper.setGate(Lists.newArrayList(code));
                        wrapper.setExtra(ImmutableMap.of("codeNature", "1"));
                        wrappers.add(wrapper);
                    });
                    template.getIntlCode().forEach(code -> {
                        CarouselGanttWrapper wrapper = new CarouselGanttWrapper();
                        BeanUtils.copyProperties(template, wrapper);
                        wrapper.setExpectedGate(1);
                        wrapper.setGate(Lists.newArrayList(code));
                        wrapper.setExtra(ImmutableMap.of("codeNature", "2"));
                        wrappers.add(wrapper);
                    });
                }
            }
        });

        return wrappers;
    }

    /**
     * 判断一个航班是否为:
     * 国内航班 - 国际航班 - 混合航班
     * 国内航班: 起飞地,经停地*2,到达地 都是国内
     * 国际航班: 起飞地=国内,目的地=国外,且没有经停地
     * 混合航班: 起飞地,经停地*2,到达地中存在国内和国外
     *
     * @param flightDynamicId
     * @return
     */
    public String getTheFuckingFlightAttr(String flightDynamicId) {
        return getTheFuckingFlightAttr(super.dao.get(flightDynamicId));
    }


    /**
     * 判断一个航班是否为:
     * 国内航班 - 国际航班 - 混合航班
     * 国内航班: 起飞地,经停地*2,到达地 都是国内
     * 国际航班: 起飞地=国内,目的地=国外,且没有经停地
     * 混合航班: 起飞地,经停地*2,到达地中存在国内和国外
     *
     * @param flightDynamic
     * @return
     */
    public String getTheFuckingFlightAttr(FlightDynamic flightDynamic) {
        String fuckingflightAttr = "国内航班";
        try {
            //跟据要求获取改为此 wjp_2017年11月1日22时27分
            if ("11".equals(flightDynamic.getFlightAttrCode()) || "12".equals(flightDynamic.getFlightAttrCode())) {
                return "国际航班";
            } else if ("22".equals(flightDynamic.getFlightAttrCode()) || "21".equals(flightDynamic.getFlightAttrCode())) {
                return "国内航班";
            } else if ("13".equals(flightDynamic.getFlightAttrCode())) {
                return "混合航班";
            }

//            List<String> addressThreeCodes = new ArrayList<>();
//            if (org.apache.commons.lang3.StringUtils.isNotBlank(flightDynamic.getDepartureAirportCode()))
//                addressThreeCodes.add(flightDynamic.getDepartureAirportCode());
//            if (org.apache.commons.lang3.StringUtils.isNotBlank(flightDynamic.getPassAirport1Code()))
//                addressThreeCodes.add(flightDynamic.getPassAirport1Code());
//            if (org.apache.commons.lang3.StringUtils.isNotBlank(flightDynamic.getPassAirport2Code()))
//                addressThreeCodes.add(flightDynamic.getPassAirport2Code());
//            if (org.apache.commons.lang3.StringUtils.isNotBlank(flightDynamic.getExt11()))
//                addressThreeCodes.add(flightDynamic.getExt11());
//            if (org.apache.commons.lang3.StringUtils.isNotBlank(flightDynamic.getExt13()))
//                addressThreeCodes.add(flightDynamic.getExt13());
//            if (org.apache.commons.lang3.StringUtils.isNotBlank(flightDynamic.getExt15()))
//                addressThreeCodes.add(flightDynamic.getExt15());
//            if (org.apache.commons.lang3.StringUtils.isNotBlank(flightDynamic.getExt17()))
//                addressThreeCodes.add(flightDynamic.getExt17());
//            if (org.apache.commons.lang3.StringUtils.isNotBlank(flightDynamic.getArrivalAirportCode()))
//                addressThreeCodes.add(flightDynamic.getArrivalAirportCode());
//
//            List<AmsAddress> addressList = new ArrayList<>();
//            addressThreeCodes.forEach(threeCode -> {
//                AmsAddress address = new AmsAddress();
//                address.setThreeCode(threeCode);
//                address = amsAddressDao.getByThreeCode(address);
//                addressList.add(address);
//            });
//
//            boolean isChina = true;
//
//            // 如果查询的地名信息为null,那么直接返回空字符串
//            if (addressList.isEmpty()) return fuckingflightAttr;
//
//            // 地点中存在国外地点,那么肯定不是国内航班
//            for (int i = 0; i < addressList.size(); i++) {
//                AmsAddress amsAddress = addressList.get(i);
//                if (amsAddress != null && "1".equals(amsAddress.getForeignornot())) {
//                    isChina = false;
//                    break;
//                }
//            }
//
//            if (isChina) fuckingflightAttr = "国内航班";
//            else {
//                // 判断是否为国际航班
//                // 起飞地是国内,目的地为国外,中间经停地都是国外
//                if (addressList.get(0).getForeignornot() == null || "0".equals(addressList.get(0).getForeignornot())) {
//                    boolean isForeign = true;
//                    for (int i = 1; i < addressList.size(); i++) {
//                        if ("0".equals(addressList.get(i).getForeignornot())) {
//                            isForeign = false;
//                            break;
//                        }
//                    }
//                    if (isForeign) fuckingflightAttr = "国际航班";
//                    else fuckingflightAttr = "混合航班";
//                }
//                // 起飞地是国外,目的地为国内,中间经停地都是国外
//                if ("1".equals(addressList.get(0).getForeignornot())) {
//                    boolean isForeign = true;
//                    for (int i = 0; i < addressList.size() - 1; i++) {
//                        if ("0".equals(addressList.get(i).getForeignornot())) {
//                            isForeign = false;
//                            break;
//                        }
//                    }
//                    if (isForeign && "0".equals(addressList.get(addressList.size() - 1).getForeignornot()))
//                        fuckingflightAttr = "国际航班";
//                    else fuckingflightAttr = "混合航班";
//                }
//            }
        } catch (Exception e) {
            logger.error("判断航班动态的航班属性异常...", e);
        }

        return fuckingflightAttr;
    }

    //处理配对数据中的各动态的机位数据 并发送同步消息
    public void updateFlightDynamicInfoAfterAircraftDistributed(FlightPlanPair flightPlanPair) {
        ArrayList<FlightDynamic> flightDynamics = new ArrayList<>();
        ArrayList<FlightPlanPair> pairs = new ArrayList<>();
        if (StringUtils.isNotEmpty(flightPlanPair.getFlightDynimicId())) {
            FlightDynamic flightDynamic = super.dao.get(flightPlanPair.getFlightDynimicId());
            if (flightDynamic != null) {
                flightDynamic.setPlaceNum(flightPlanPair.getPlaceNum());
                flightDynamic.setRandom(flightPlanPair.getRandom());
                dao.update(flightDynamic);
                flightDynamics.add(flightDynamic);
            }
        }

        if (StringUtils.isNotEmpty(flightPlanPair.getFlightDynimicId2())) {
            FlightDynamic flightDynamic = super.dao.get(flightPlanPair.getFlightDynimicId2());
            if (flightDynamic != null) {
                flightDynamic.setPlaceNum(flightPlanPair.getPlaceNum());
                flightDynamic.setRandom(flightPlanPair.getRandom());
                dao.update(flightDynamic);
                flightDynamics.add(flightDynamic);
            }
        }

        pairs.add(flightPlanPair);
        //添加事件驱动，将动态及配对数据同步到ams
        AddEventUtils.addEvent(flightDynamics, pairs, SyncTypeEnum.PLACE_NUM, applicationContext);
    }

    public List<GateGanttWrapper> listGateJson4Mock(String infoId) {
        return flightPlanPairDao.findPlanPair4Mock(infoId);
    }

    public List<CarouselGanttWrapper> listCarouselJson4Mock(String infoId) {
        return listDistedData(dao.distedCarouselList4Mock(ImmutableMap.of("infoId", infoId)));
    }

    public List<CarouselOccupyingInfoWrapper> carouselList4Mock(String infoId) {
        return dao.carouselList4Mock(ImmutableMap.of("infoId", infoId));
    }

    public List<CarouselGanttWrapper> listArrivalGateJson4Mock(String infoId) {
        return listDistedData(dao.distedArrivalGateList4Mock(ImmutableMap.of("infoId", infoId)));
    }

    public List<CarouselOccupyingInfoWrapper> arrivalGateList4Mock(String infoId) {
        return dao.arrivalGateList4Mock(ImmutableMap.of("infoId", infoId));
    }

    public List<CarouselGanttWrapper> listBoardingGateJson4Mock(String infoId) {
        return listDistedData(dao.distedBoardingGateList4Mock(ImmutableMap.of("infoId", infoId)));
    }

    public List<CarouselOccupyingInfoWrapper> boardingGateList4Mock(String infoId) {
        return dao.boardingGateList4Mock(ImmutableMap.of("infoId", infoId));
    }

    public List<CarouselGanttWrapper> listSlideCoastJson4Mock(String infoId) {
        return listDistedData(dao.distedSlideCoastList4Mock(ImmutableMap.of("infoId", infoId)));
    }

    public List<CarouselOccupyingInfoWrapper> slideCoastList4Mock(String infoId) {
        return dao.slideCoastList4Mock(ImmutableMap.of("infoId", infoId));
    }

    public List<CarouselGanttWrapper> listCheckinCounterJson4Mock(String infoId) {
        return listDistedData(dao.distedCheckinCounterList4Mock(infoId));
    }

    public List<CarouselGanttWrapper> listDepartureHallJson4Mock(String infoId) {
        return listDistedData(dao.distedDepartureHallList4Mock(infoId));
    }

    public List<CarouselGanttWrapper> listSecurityCheckinJson4Mock(String infoId) {
        return listDistedData(dao.distedSecurityCheckinList4Mock(infoId));
    }

    public List<CheckingCounterOccupyingInfoWrapper> checkinCounterList4Mock(String infoId) {
        return dao.checkinCounterList4Mock(infoId);
    }

    public List<SecurityCheckinOccupyingInfoWrapper> securityCheckinList4Mock(String infoId) {
        return dao.securityCheckinList4Mock(infoId);
    }

    public List<DepartureHallOccupyingInfoWrapper> departureHallList4Mock(String infoId) {
        return dao.departureHallList4Mock(infoId);
    }

    //获取代理信息
    public List<Map<String, String>> getAgents() {
        FlightDynamic flightDynamic = new FlightDynamic();
        flightDynamic.getSqlMap().put("dsfn", dataScopeFilterNew(flightDynamic.getCurrentUser()));
        List<ServiceProvider> serviceProviderList = dao.getAgents(flightDynamic);
        List<Map<String, String>> agents = Lists.newArrayList();
        if (!Collections3.isEmpty(serviceProviderList)) {
            for (ServiceProvider provider : serviceProviderList) {
                Map<String, String> map = new HashMap<>();
                map.put("k", provider.getServiceProviderNo());
                map.put("v", provider.getServiceProviderName());
                agents.add(map);
            }
        }
        return agents;
    }

    public List<FlightDynamic> findListSimple(FlightDynamic flightDynamic) {
        return dao.findListSimple(flightDynamic);
    }

    public String getPairIdByDynamic(FlightDynamic flightDynamic) {
        return dao.getPairIdByDynamic(flightDynamic.getId());
    }

    public FlightPlanPair getPairByDynamic(String flightDynamicId) {
        return dao.getPairByDynamic(flightDynamicId);
    }
}
