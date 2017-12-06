/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.dao;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.FlightPairWrapper;
import com.thinkgem.jeesite.modules.ams.entity.wrapper.CarouselListWrapper;
import com.thinkgem.jeesite.modules.ams.entity.wrapper.CarouselWrapper;
import com.thinkgem.jeesite.modules.rms.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 航班动态管理DAO接口
 *
 * @author xiaopo
 * @version 2016-01-10
 */
@MyBatisDao
public interface FlightDynamicDao extends CrudDao<FlightDynamic> {

    void delay(FlightDynamic flightDynamic);

    void cancel(FlightDynamic flightDynamic);

    void alternate(FlightDynamic flightDynamic);

    List<Map> getPairFlightList(FlightPairWrapper flightDynamic);

    List<FlightDynamic> getFlighgtDynamicByConditon(FlightDynamic flightDynamic);

    /**
     * 将所有航班动态的共享航班动态置空
     */
    void updateShareFlight2Null(FlightDynamic flightDynamic);

    int queryDoubleInOutFlight(FlightDynamic flightDynamic);

    void updateStatus(FlightDynamic dynamic);

    List<FlightDynamic> queryListByIds(Map map);

    void updateAircraftNum(FlightDynamic flightDynamic);

    List<FlightPairWrapper> queryPairForChangeAircraft(Map map);

    Integer queryCountByPlanDateFlightNumInout(FlightDynamic flightDynamic);

    List<FlightDynamic> findCompletedByDate(FlightDynamic flightDynamic);

    List<FlightDynamic> findByPlaceNum(String placeNumber);

    FlightDynamic findOutTypeDynamicByIn(FlightDynamic flightDynamic);

    FlightDynamic findInTypeDynamicByOut(FlightDynamic flightDynamic);

    List<String> findOccupiedGates(Map<String, Object> params);

    List<String> findoccupiedGates4Mock(Map<String, Object> params);

    List<FlightDynamic> findUnDistDynamics();

    List<CarouselOccupyingInfoWrapper> carouselList(FlightDynamic flightDynamic);

    List<CarouselWrapper> distedCarouselList(Map<String, Date> variables);

    List<String> findOccupiedCarouselCodes(Map<String, Object> params);

    List<BoardingGateOccupyingInfoWrapper> arrivalGateList(FlightDynamic flightDynamic);

    List<CarouselListWrapper> boardingGateList(FlightDynamic flightDynamic);

    List<CarouselOccupyingInfoWrapper> slideCoastList(FlightDynamic flightDynamic);

    List<CheckingCounterOccupyingInfoWrapper> checkinCounterList(FlightDynamic flightDynamic);

    List<DepartureHallOccupyingInfoWrapper> departureHallList(FlightDynamic flightDynamic);

    List<SecurityCheckinOccupyingInfoWrapper> securityCheckinList(FlightDynamic flightDynamic);

    List<CarouselWrapper> distedArrivalGateList(Map<String, Date> variables);

    List<CarouselWrapper> distedBoardingGateList(Map<String, Date> variables);

    List<CarouselWrapper> distedSlideCoastList(Map<String, Date> variables);

    List<CarouselWrapper> distedCheckinCounterList(Map<String, Date> variables);

    List<CarouselWrapper> distedDepartureHallList(Map<String, Date> variables);

    List<CarouselWrapper> distedSecurityCheckinList(Map<String, Date> variables);

    List<String> findOccupiedArrivalGateCodes(Map<String, Object> params);

    List<String> findOccupiedArrivalGateCodes4Mock(Map<String, Object> params);

    List<String> findOccupiedBoardingGateCodes4Mock(Map<String, Object> params);

    List<String> findOccupiedCarouselCodes4Mock(Map<String, Object> params);

    List<FlightDynamic> findMockDynamics4ArrivalGate(Map<String, Object> params);

    List<FlightDynamic> findMockDynamics4BoardingGate(Map<String, Object> params);

    List<FlightDynamic> findMockDynamics4SlideCoast(Map<String, Object> params);

    List<FlightDynamic> findMockDynamics4Carousel(Map<String, Object> params);

    List<String> findOccupiedBoardingGateCodes(Map<String, Object> params);

    List<String> findOccupiedSlideCoastCodes(Map<String, Object> params);

    List<String> findOccupiedSlideCoastCodes4Mock(Map<String, Object> params);

    List<FlightDynamic> findCarouselUnDistDynamics(Map<String, Object> params);

    List<FlightDynamic> findArrivalGateUnDistDynamics(Map<String, Object> params);

    List<FlightDynamic> findBoardingGateUnDistDynamics(Map<String, Object> params);

    List<FlightDynamic> findSlideCoastUnDistDynamics(Map<String, Object> params);

    List<FlightDynamic> findCheckinCounterUnDistDynamics(Map<String, Object> params);

    List<FlightDynamic> findDepartureHallUnDistDynamics(Map<String, Object> params);

    List<FlightDynamic> findSecurityCheckinUnDistDynamics(Map<String, Object> params);

    void insertFlightPair(FlightPairWrapper pairWrapper);

    List<Map<String, String>> getDateRange();

    int updateSafeguardTypeCode(FlightDynamic dynamic);

    String getMaxPlanDate();

    String getMaxPlanDate4Mock(Map<String, Object> params);

    void insertAmsNo(String no);

    void insertRmsNo(String no);

    List<FlightDynamic> findMockDynamics4CheckinCounter(Map<String, Object> params);

    List<FlightDynamic> findMockDynamics4SecurityCheckin(Map<String, Object> params);

    List<FlightDynamic> findMockDynamics4DepartureHall(Map<String, Object> params);

    List<CarouselWrapper> distedCarouselList4Mock(ImmutableMap<String, String> infoId);

    List<CarouselWrapper> distedArrivalGateList4Mock(ImmutableMap<String, String> infoId);

    List<CarouselOccupyingInfoWrapper> carouselList4Mock(ImmutableMap<String, String> infoId);

    List<CarouselOccupyingInfoWrapper> arrivalGateList4Mock(ImmutableMap<String, String> infoId);

    List<CarouselWrapper> distedBoardingGateList4Mock(ImmutableMap<String, String> infoId);

    List<CarouselOccupyingInfoWrapper> boardingGateList4Mock(ImmutableMap<String, String> infoId);

    List<CarouselWrapper> distedSlideCoastList4Mock(ImmutableMap<String, String> infoId);

    List<CarouselOccupyingInfoWrapper> slideCoastList4Mock(ImmutableMap<String, String> infoId);

    List<CarouselWrapper> distedCheckinCounterList4Mock(String infoId);

    List<CarouselWrapper> distedDepartureHallList4Mock(String infoId);

    List<CarouselWrapper> distedSecurityCheckinList4Mock(String infoId);

    List<CheckingCounterOccupyingInfoWrapper> checkinCounterList4Mock(String infoId);

    List<SecurityCheckinOccupyingInfoWrapper> securityCheckinList4Mock(String infoId);

    List<DepartureHallOccupyingInfoWrapper> departureHallList4Mock(String infoId);

    void clearResourceBeforeDynamicInit(Date planDate);

    /**
     * 占用转历史
     */
    Integer ociToHistory(@Param("flightDynamicId") String flightDynamicId);

    /**
     * 删除占用
     *
     * @param flightDynamicId
     * @return
     */
    Integer ociDelete(@Param("flightDynamicId") String flightDynamicId);

    /**
     * 仓单转历史
     */
    Integer manifestToHistory(@Param("flightDynamicId") String flightDynamicId);

    /**
     * 获取代理
     *
     * @return
     */
    List<ServiceProvider> getAgents(FlightDynamic flightDynamic);

    List<Map<String,String>> getFIdByAgents(Map<String, Object> params);

    List<FlightDynamic> findListSimple(FlightDynamic flightDynamic);

    String getPairIdByDynamic(@Param("flightDynamicId") String flightDynamicId);

    FlightPlanPair getPairByDynamic(@Param("flightDynamicId") String flightDynamicId);
}
