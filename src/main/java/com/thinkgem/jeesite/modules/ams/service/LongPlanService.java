/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.SysConstant;
import com.thinkgem.jeesite.modules.ams.dao.*;
import com.thinkgem.jeesite.modules.ams.entity.*;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 长期计划管理Service
 *
 * @author xiaopo
 * @version 2015-12-22
 */
@Service
@Transactional(readOnly = true)
public class LongPlanService extends CrudService<LongPlanDao, LongPlan> {

    private static Logger log = Logger.getLogger(LongPlanService.class);

    @Autowired
    private LongPlanStopDao longPlanStopDao;

    @Autowired
    private AircraftParametersDao aircraftParametersDao;

    @Autowired
    private FlightCompanyInfoDao flightCompanyInfoDao;

    @Autowired
    private AmsAddressDao amsAddressDao;

    @Autowired
    private SysDictDao sysDictDao;

    @Autowired
    private LongPlanDivideDao longPlanDivideDao;

    @Autowired
    private FlightPropertiesDao flightPropertiesDao;

    @Autowired
    private FlightNatureDao flightNatureDao;

    public LongPlan get(String id) {
        LongPlan longPlan = super.get(id);
        longPlan.setLongPlanStopList(longPlanStopDao.findList(new LongPlanStop(longPlan)));
        return longPlan;
    }

    public List<LongPlan> findList(LongPlan longPlan) {
        return super.findList(longPlan);
    }

    public Page<LongPlan> findPage(Page<LongPlan> page, LongPlan longPlan) {
        return super.findPage(page, longPlan);
    }

    @Transactional(readOnly = false)
    public void save(LongPlan longPlan) {

        // 在保存之前需要把连接查询的字典数据都查出来set到longPlan对象中

        // 进出港类型
        // longPlan.setInoutTypeName();
        // 全部交给前台查询完成，避免后台再次查询影响效率
        longPlan.setInoutTypeName(StringUtils.code2InOutType(longPlan.getInoutTypeCode()));

        // 航空公司代码、名称，已经在前台全部查询完成，不需要在后台查询
        // FlightCompanyInfo flightCompanyInfo = flightCompanyInfoDao.get(longPlan.getFlightCompanyId());
        // longPlan.setFlightCompanyCode(flightCompanyInfo.getThreeCode());
        // longPlan.setFlightCompanyName(flightCompanyInfo.getChineseName());

        // 机型
        // AircraftParameters aircraftParameters = aircraftParametersDao.get(longPlan.getAircraftTypeId());
        // longPlan.setAircraftTypeCode(aircraftParameters.getAircraftMainModel());
        // longPlan.setAircraftTypeName(aircraftParameters.getAircraftModel());

        super.save(longPlan);

        // 1.删除已经存在的经停站
        longPlanStopDao.deleteByLongPlanId(longPlan.getId());

        // 2.再新增经停止站
        for (LongPlanStop longPlanStop : longPlan.getLongPlanStopList()) {
            if (org.apache.commons.lang3.StringUtils.isNotBlank(longPlanStop.getStopPointCode())) {
                // ImmutableMap<String, String> params = ImmutableMap.of("threeCode", longPlanStop.getStopPointCode());
                // AmsAddress address = amsAddressDao.getByThreeCode(params);
                longPlanStop.setLongPlan(longPlan);
                // longPlanStop.setStopPointName(address.getChName());
                // longPlanStop.setStopPointCode(address.getThreeCode());
                longPlanStop.preInsert();
                longPlanStopDao.insert(longPlanStop);
            }
        }
    }

    @Transactional(readOnly = false)
    public void delete(LongPlan longPlan) {
        super.delete(longPlan);
        longPlanStopDao.delete(new LongPlanStop(longPlan));
    }

    public DataTablesPage<LongPlanVO> findDataTablesVOPage(Page<LongPlan> page, DataTablesPage<LongPlan> dataTablesPage, LongPlan entity) {
        DataTablesPage<LongPlan> dataTablePages = super.findDataTablesPage(page, dataTablesPage, entity);

        DataTablesPage<LongPlanVO> dataTableVOPages = new DataTablesPage<LongPlanVO>();

        org.springframework.beans.BeanUtils.copyProperties(dataTablePages, dataTableVOPages);

        List<LongPlan> longPlanList = dataTablePages.getData();
        List<LongPlanVO> longPlanVOList = new ArrayList<LongPlanVO>();

        for (LongPlan longPlan : longPlanList) {

            LongPlanStop longPlanStop = new LongPlanStop();
            longPlanStop.setLongPlan(longPlan);
            List<LongPlanStop> longPlanStopList = longPlanStopDao.findByLongPlanId(longPlanStop);
            LongPlanVO longPlanVO = new LongPlanVO();
            org.springframework.beans.BeanUtils.copyProperties(longPlan, longPlanVO);
            String longPlanStopStr = StringUtils.list2LongPlanStopStr(longPlanStopList, longPlan.getLegal());
            longPlanVO.setLongPlanStopStr(longPlanStopStr);
            longPlanVOList.add(longPlanVO);
        }

        dataTableVOPages.setData(longPlanVOList);

        return dataTableVOPages;
    }

    /**
     * @param @return
     * @return Message
     * @throws
     * @Title: saveDividedLongPlan
     * @Description: 拆分长期计划数据到长期计划拆分表，用于长期计划初始化成航班计划
     * @author: chencheng
     * @date： 2016年1月9日 下午9:05:13
     */
    public Message saveDividedLongPlan() {
        // TODO 拆分长期计划数据到长期计划拆分表，用于长期计划初始化成航班计划
        // 查询所有可用的长期计划
        List<LongPlan> longPlanList = super.dao.findAllList(new LongPlan());
        // 识别出本航站
        SysDict baseSite = new SysDict();
        baseSite.setType(SysConstant.BASE_SITE);
        baseSite.setTableName("sys_settings");
        baseSite = sysDictDao.getByTableNameAndType(baseSite);
        // 遍历所有长期计划的经过停地
        // 开始拆分，拆分规则如下：
        /* 例如本航站是武汉
		 * 1.HU111/5，武汉-北京-武汉，拆分结果：武汉-北京（出港），航班号 HU111；北京-武汉（进港），航班号 HU115
		 * 2.HU112，武汉-北京-上海，拆分结果：武汉-北京-上海（出港），经停地 北京，航班号 HU112
		 * 3.HU115，深圳-上海-武汉，拆分结果：深圳-上海-武汉（进港），经停地 上海，航班号 HU115
		 * 4.HU113/4，武汉-北京-上海-深圳-武汉，拆分结果：武汉-北京-上海（出港），经停地 北京，航班号 HU113；上海-深圳-武汉（进港），经停地 深圳，航班号 HU114
		 * 5.HU222/3，北京-上海-武汉-深圳，拆分结果：北京-上海-武汉（进港），经停地 上海，航班号 HU222；武汉-深圳，航班号 HU223
		 * 6.武汉-北京-深圳-武汉，这种情况返回此长期计划不合法，无法进行拆分
		 * 当本航站只在头尾出现，那么拆分成单出、单进航班
		 * 当本航站出现在中间，那么拆分成一进一出2个航班
		 * 当本航站同时出现在头尾，那么判断中间航站个数是否为奇数，是奇数那么拆分成本行站-中间站；中间站-本行站，否则长期计划不合法
		 * 能拆分为2个航班的，如果航班号未按照HU111/2书写规则填写的，那么判断为不合法
		 * 
		 * 拆分完后将拆分结果更新到长期计划拆分表
		 */
        for (LongPlan longPlan : longPlanList) {

            boolean isLegal = true;
            LongPlanStop longPlanStop = new LongPlanStop();
            longPlanStop.setLongPlan(longPlan);
            List<LongPlanStop> longPlanStopList = longPlanStopDao.findByLongPlanId(longPlanStop);
            // 获取所有经停地个数
            int stopCount = longPlanStopList.size();
            // 遍历后标注经停地出现的list下标
            List<Integer> baseSiteIndex = new ArrayList<Integer>();
            for (int i = 0; i < longPlanStopList.size(); i++) {
                // 如果经停站等于本行站，则记录下标index
                if (longPlanStopList.get(i).getStopPointCode().equals(baseSite.getValue())) {
                    baseSiteIndex.add(i);
                }
            }
            if (baseSiteIndex.isEmpty()) {
                // 本行站没有出现，那么计划是不合法的
                isLegal = false;
                longPlan.setLegal("0");
                super.dao.update(longPlan);
                continue;
            }
            boolean notok = false;
            for (int j = 0; j < baseSiteIndex.size(); j++) {
                if (j == 0) {
                    // 当第一个index != 0
                    if (baseSiteIndex.get(j) > 3) {
                        notok = true;
                        break;
                    }
                } else if (j > 0 && j < baseSiteIndex.size() - 1) {
					/*if ((baseSiteIndex.get(j) - baseSiteIndex.get(j - 1) - 1) % 2 != 1) {
						notok = true;
						break;
					}*/
                    if (baseSiteIndex.get(j) - baseSiteIndex.get(j - 1) - 1 > 5) {
                        notok = true;
                        break;
                    }
                } else if (j == baseSiteIndex.size() - 1) {
                    if ((stopCount - 1) - baseSiteIndex.get(j) - 1 > 2) {
                        notok = true;
                        break;
                    }
                }
            }
            if (notok) {
                isLegal = false;
                longPlan.setLegal("0");
                super.dao.update(longPlan);
                continue;
            }

            // 开始拆分工作
            List<Map<String, Object>> divideList = new ArrayList<Map<String, Object>>();

            try {
                // 解决头的问题
                if (baseSiteIndex.get(0) > 0) {
                    Map<String, Object> header_in_map = new HashMap<String, Object>();

                    List<LongPlanStop> header_in = new ArrayList<LongPlanStop>();
                    for (LongPlanStop lps : longPlanStopList.subList(0, baseSiteIndex.get(0) + 1)) {
                        header_in.add((LongPlanStop) BeanUtils.cloneBean(lps));
                    }
                    header_in_map.put("inoutType", "J");
                    header_in_map.put("stopPoints", header_in);
                    divideList.add(header_in_map);
                }

                // 中间的这样解决
                for (int j = 0; j < baseSiteIndex.size(); j++) {
                    if (j + 1 <= baseSiteIndex.size() - 1) {
                        int middleIndex = 0;
                        int arrange = (baseSiteIndex.get(j + 1) - baseSiteIndex.get(j)) / 2;
                        if ((baseSiteIndex.get(j + 1) - baseSiteIndex.get(j)) % 2 == 0) {
                            middleIndex = arrange + baseSiteIndex.get(j);
                        } else {
                            middleIndex = arrange + 1 + baseSiteIndex.get(j);
                        }
                        // int middleIndex = (baseSiteIndex.get(j + 1) - baseSiteIndex.get(j)) / 2 + baseSiteIndex.get(j);
                        Map<String, Object> millde_out_map = new HashMap<String, Object>();
                        List<LongPlanStop> millde_out = new ArrayList<LongPlanStop>();
                        for (int m = baseSiteIndex.get(j); m <= middleIndex; m++) {
                            millde_out.add((LongPlanStop) BeanUtils.cloneBean(longPlanStopList.get(m)));
                        }
                        millde_out_map.put("inoutType", "C");
                        millde_out_map.put("stopPoints", millde_out);
                        divideList.add(millde_out_map);

                        Map<String, Object> millde_in_map = new HashMap<String, Object>();

                        List<LongPlanStop> millde_in = new ArrayList<LongPlanStop>();
                        for (int m = middleIndex; m <= baseSiteIndex.get(j + 1); m++) {
                            millde_in.add((LongPlanStop) BeanUtils.cloneBean(longPlanStopList.get(m)));
                        }
                        millde_in_map.put("inoutType", "J");
                        millde_in_map.put("stopPoints", millde_in);
                        divideList.add(millde_in_map);
                    }
                }

                // 解决尾的问题
                if (baseSiteIndex.get(baseSiteIndex.size() - 1) < stopCount - 1) {
                    Map<String, Object> tail_out_map = new HashMap<String, Object>();

                    List<LongPlanStop> tail_out = new ArrayList<LongPlanStop>();
                    for (LongPlanStop lps : longPlanStopList.subList(baseSiteIndex.get(baseSiteIndex.size() - 1), stopCount)) {
                        tail_out.add((LongPlanStop) BeanUtils.cloneBean(lps));
                    }
                    tail_out_map.put("inoutType", "C");
                    tail_out_map.put("stopPoints", tail_out);
                    divideList.add(tail_out_map);
                }
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // 拆分航班号
            List<String> filghtNumList = null;
            try {
                filghtNumList = StringUtils.divideFlightNum(longPlan.getFlightNum());
            } catch (Exception e) {
                isLegal = false;
                longPlan.setLegal("0");
                super.dao.update(longPlan);
                continue;
            }

            // 如果航班号与拆分结果数量不对应，则不合法
            if (filghtNumList.size() > 1 && filghtNumList.size() != divideList.size()) {
                isLegal = false;
                longPlan.setLegal("0");
                super.dao.update(longPlan);
                continue;
            }

            List<LongPlanDivide> longPlanDivideList = new ArrayList<LongPlanDivide>();

            // 当只有一个航班号的时候，全部航班都是这一个航班号
            if (filghtNumList.size() == 1) {
                for (Map<String, Object> flighlongPlanStopListMap : divideList) {
                    String inoutTypeCode = (String) flighlongPlanStopListMap.get("inoutType");
                    List<LongPlanStop> stopPoints = (List<LongPlanStop>) flighlongPlanStopListMap.get("stopPoints");
                    LongPlanDivide longPlanDivide = createPlanDivedeByLongPlan(longPlan);

                    SysDict sysDict = new SysDict("ams_flight_plan_detail", "inout", inoutTypeCode);
                    sysDict = sysDictDao.getSysDict(sysDict);

                    longPlanDivide = addStopPoint(longPlanDivide, stopPoints, sysDict.getLabel(), inoutTypeCode);
                    longPlanDivide.setFlightNum(filghtNumList.get(0));
                    longPlanDivideList.add(longPlanDivide);
                }
            } else if (filghtNumList.size() > 1) {
                // 当只有多个航班号的时候，每个航班号对应一个拆分出来的航班
                for (int l = 0; l < divideList.size(); l++) {
                    Map<String, Object> flighlongPlanStopListMap = divideList.get(l);
                    String inoutTypeCode = (String) flighlongPlanStopListMap.get("inoutType");
                    List<LongPlanStop> stopPoints = (List<LongPlanStop>) flighlongPlanStopListMap.get("stopPoints");
                    LongPlanDivide longPlanDivide = createPlanDivedeByLongPlan(longPlan);

                    SysDict sysDict = new SysDict("ams_flight_plan_detail", "inout", inoutTypeCode);
                    sysDict = sysDictDao.getSysDict(sysDict);

                    longPlanDivide = addStopPoint(longPlanDivide, stopPoints, sysDict.getLabel(), inoutTypeCode);
                    longPlanDivide.setFlightNum(filghtNumList.get(l));
                    longPlanDivideList.add(longPlanDivide);
                }
            }

            // 保存拆分结果
            // 清空当前长期计划下的所有拆分结果，重新保存
            ImmutableMap<String, String> params = ImmutableMap.of("longPlanId", longPlan.getId());
            longPlanDivideDao.deleteByLongPlanId(params);
            for (LongPlanDivide longPlanDivide : longPlanDivideList) {
                longPlanDivide.preInsert();
                longPlanDivideDao.insert(longPlanDivide);
            }

            // 更新长期计划拆分是否合理结果
            isLegal = false;
            longPlan.setLegal("1");
            super.dao.update(longPlan);
        }

        Message message = new Message();
        message.setCode(1);
        return message;
    }

    /**
     * @param @param  longPlan
     * @param @return
     * @return LongPlanDivide
     * @throws
     * @Title: createPlanDivedeByLongPlan
     * @Description: 从长期计划生成
     * @author: chencheng
     * @date： 2016年1月11日 下午6:06:18
     */
    private LongPlanDivide createPlanDivedeByLongPlan(LongPlan longPlan) {
        LongPlanDivide longPlanDivide = new LongPlanDivide();

        longPlanDivide.setFlightNum(longPlan.getFlightNum());

        // 飞机号需要在航班计划制定的时候填写
        // longPlanDivide.setAircraftNum();

        // longPlanDivide.setAircraftParametersId(longPlan.getAircraftTypeId());
        longPlanDivide.setAircraftTypeCode(longPlan.getAircraftTypeCode());
        longPlanDivide.setAircraftTypeName(longPlan.getAircraftTypeName());

        // 进出港类型
        longPlanDivide.setInoutTypeCode(longPlan.getInoutTypeCode());
        longPlanDivide.setInoutTypeName(longPlan.getInoutTypeName());

        // 航站楼需要在航班计划制定的时候填写
		/*longPlanDivide.setTerminalId(terminalId);
		longPlanDivide.setTerminal(terminal);*/

        // 还有一个客货代理没确认

        // longPlanDivide.setFlightCompanyId(longPlan.getFlightCompanyId());
        longPlanDivide.setFlightCompanyCode(longPlan.getFlightCompanyCode());
        longPlanDivide.setFlightCompanyName(longPlan.getFlightCompanyName());

        longPlanDivide.setLongPlanId(longPlan.getId());
        return longPlanDivide;
    }

    private LongPlanDivide addStopPoint(LongPlanDivide longPlanDivide, List<LongPlanStop> longPlanStopList, String inoutTypeName, String inoutTypeCode) {

        // 离港航站
        longPlanDivide.setDepartureAirportId(longPlanStopList.get(0).getStopPointId());
        longPlanDivide.setDepartureAirportCode(longPlanStopList.get(0).getStopPointCode());
        longPlanDivide.setDepartureAirportName(longPlanStopList.get(0).getStopPointName());

        if (longPlanStopList.size() == 2) {
            longPlanDivide.setArrivalAirportId(longPlanStopList.get(1).getStopPointId());
            longPlanDivide.setArrivalAirportCode(longPlanStopList.get(1).getStopPointCode());
            longPlanDivide.setArrivalAirportName(longPlanStopList.get(1).getStopPointName());
        }

        if (longPlanStopList.size() == 3) {
            longPlanDivide.setPassAirport1Id(longPlanStopList.get(1).getStopPointId());
            longPlanDivide.setPassAirport1Code(longPlanStopList.get(1).getStopPointCode());
            longPlanDivide.setPassAirport1Name(longPlanStopList.get(1).getStopPointName());

            longPlanDivide.setArrivalAirportId(longPlanStopList.get(2).getStopPointId());
            longPlanDivide.setArrivalAirportCode(longPlanStopList.get(2).getStopPointCode());
            longPlanDivide.setArrivalAirportName(longPlanStopList.get(2).getStopPointName());
        }

        if (longPlanStopList.size() == 4) {
            longPlanDivide.setPassAirport1Id(longPlanStopList.get(1).getStopPointId());
            longPlanDivide.setPassAirport1Code(longPlanStopList.get(1).getStopPointCode());
            longPlanDivide.setPassAirport1Name(longPlanStopList.get(1).getStopPointName());

            longPlanDivide.setPassAirport2Id(longPlanStopList.get(2).getStopPointId());
            longPlanDivide.setPassAirport2Code(longPlanStopList.get(2).getStopPointCode());
            longPlanDivide.setPassAirport2Name(longPlanStopList.get(2).getStopPointName());

            longPlanDivide.setArrivalAirportId(longPlanStopList.get(3).getStopPointId());
            longPlanDivide.setArrivalAirportCode(longPlanStopList.get(3).getStopPointCode());
            longPlanDivide.setArrivalAirportName(longPlanStopList.get(3).getStopPointName());
        }

        longPlanDivide.setInoutTypeCode(inoutTypeCode);
        longPlanDivide.setInoutTypeName(inoutTypeName);

        // 设置航班属性（国内航班，国际航班）
        // 遍历所有经过的航站，判断航站的是否国内属性
        boolean isForeign = false;
        for (LongPlanStop lps : longPlanStopList) {
            ImmutableMap<String, String> params = ImmutableMap.of("threeCode", lps.getStopPointCode());
            AmsAddress address = amsAddressDao.getByThreeCode(params);
            if (address.getForeignornot().equals("1")) {
                isForeign = true;
                break;
            }
        }

        if (isForeign) {
            if (inoutTypeCode.equals("J")) {
                FlightProperties flightProperties = new FlightProperties();
                flightProperties.setPropertiesNo("11");
                flightProperties = flightPropertiesDao.findByCondition(flightProperties);
                longPlanDivide.setFlightAttrCode(flightProperties.getPropertiesNo());
                longPlanDivide.setFlightAttrName(flightProperties.getPropertiesName());
            } else if (inoutTypeCode.equals("C")) {
                FlightProperties flightProperties = new FlightProperties();
                flightProperties.setPropertiesNo("12");
                flightProperties = flightPropertiesDao.findByCondition(flightProperties);
                longPlanDivide.setFlightAttrCode(flightProperties.getPropertiesNo());
                longPlanDivide.setFlightAttrName(flightProperties.getPropertiesName());
            }
        } else {
            if (inoutTypeCode.equals("J")) {
                FlightProperties flightProperties = new FlightProperties();
                flightProperties.setPropertiesNo("21");
                flightProperties = flightPropertiesDao.findByCondition(flightProperties);
                longPlanDivide.setFlightPropertiesId(flightProperties.getId());
                longPlanDivide.setFlightAttrCode(flightProperties.getPropertiesNo());
                longPlanDivide.setFlightAttrName(flightProperties.getPropertiesName());
            } else if (inoutTypeCode.equals("C")) {
                FlightProperties flightProperties = new FlightProperties();
                flightProperties.setPropertiesNo("22");
                flightProperties = flightPropertiesDao.findByCondition(flightProperties);
                longPlanDivide.setFlightPropertiesId(flightProperties.getId());
                longPlanDivide.setFlightAttrCode(flightProperties.getPropertiesNo());
                longPlanDivide.setFlightAttrName(flightProperties.getPropertiesName());
            }
        }

        // 设置航班属性，默认全部都为正班
        FlightNature flightNature = new FlightNature();
        flightNature.setSita("W/Z");
        flightNature = flightNatureDao.findByCondition(flightNature);

        // longPlanDivide.setFlightNatureId(flightNature.getId());
        longPlanDivide.setFlightNatureCode(flightNature.getNatureNum());
        longPlanDivide.setFlightNatureName(flightNature.getNatureName());

        // 计划起飞时间，根据进出港类型判断
        longPlanDivide.setDeparturePlanTimeStr(longPlanStopList.get(0).getStopPointSTime());
        // 计划到达时间，根据进出港类型判断
        longPlanDivide.setArrivalPlanTimeStr(longPlanStopList.get(longPlanStopList.size() - 1).getStopPointETime());

        return longPlanDivide;
    }

    /**
     * @param @param  longPlan
     * @param @return
     * @return int
     * @throws
     * @Title: countLongPlan
     * @Description: 根据条件查询长期计划个数
     * @author: chencheng
     * @date： 2016年1月27日 下午7:16:30
     */
    public long countLongPlan(LongPlan longPlan) {
        return super.dao.countLongPlan(longPlan);
    }

    /**
     * @param @param copyParamsVO
     * @return void
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws
     * @Title: copyLongPlan
     * @Description: 复制长期计划
     * @author: chencheng
     * @date： 2016年1月28日 上午11:59:01
     */
    public void copyLongPlan(List<LongPlan> params) throws IllegalAccessException, InvocationTargetException {
        // 长期计划复制是根据原年、计划类型复制到目标年、计划类型，日期自动计算
        LongPlan oldParams = params.get(0);
        LongPlan newParams = params.get(1);

        List<LongPlan> oldLongPlans = super.dao.findList(oldParams);
        for (int i = 0; i < oldLongPlans.size(); i++) {
            // 复制长期计划同事也要复制长期计划经停地
            LongPlanStop lps = new LongPlanStop();
            lps.setLongPlan(oldLongPlans.get(i));
            List<LongPlanStop> oldLongPlanStops = longPlanStopDao.findByLongPlanId(lps);

            // new 一个新的长期计划
            LongPlan longPlan = new LongPlan();

            BeanUtils.copyProperties(longPlan, oldLongPlans.get(i));

            longPlan.setType(newParams.getType());
            longPlan.setYear(newParams.getYear());

            // 使用新的开始时间和结束时间
            // 用新的年份替换掉老的
            Calendar startCa = Calendar.getInstance();
            startCa.setTime(longPlan.getStartDate());
            startCa.set(Calendar.YEAR, newParams.getYear());
            longPlan.setStartDate(startCa.getTime());

            Calendar endCa = Calendar.getInstance();
            endCa.setTime(longPlan.getEndDate());
            endCa.set(Calendar.YEAR, newParams.getYear());
            longPlan.setEndDate(endCa.getTime());

            longPlan.setIsNewRecord(false);
            longPlan.preInsert();

            super.dao.insert(longPlan);

            for (int j = 0; j < oldLongPlanStops.size(); j++) {
                LongPlanStop longPlanStop = new LongPlanStop();
                BeanUtils.copyProperties(longPlanStop, oldLongPlanStops.get(j));
                longPlanStop.setLongPlan(longPlan);
                longPlanStop.preInsert();
                longPlanStopDao.insert(longPlanStop);
            }
        }
    }

}