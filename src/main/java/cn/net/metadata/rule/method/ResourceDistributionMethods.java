package cn.net.metadata.rule.method;

import com.bstek.urule.model.ExposeAction;
import com.bstek.urule.model.flow.ActionNode;
import com.bstek.urule.model.flow.FlowAction;
import com.bstek.urule.model.flow.ins.FlowContext;
import com.bstek.urule.model.flow.ins.ProcessInstance;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.ams.dao.AircraftParametersDao;
import com.thinkgem.jeesite.modules.ams.dao.CompanyAircraftNumDao;
import com.thinkgem.jeesite.modules.ams.entity.AircraftParameters;
import com.thinkgem.jeesite.modules.ams.entity.CompanyAircraftNum;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.dao.GateOccupyingInfoDao;
import com.thinkgem.jeesite.modules.rms.dao.RmsGateOccupyingInfoHiDao;
import com.thinkgem.jeesite.modules.rms.entity.*;
import com.thinkgem.jeesite.modules.rms.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 资源分配规则方法集
 * Created by xiaowu on 4/9/16.
 */
@Component
public class ResourceDistributionMethods implements FlowAction {
    private static final String AVAILABLE = "1";  // 可用资源标识
    private static final String UNAVAILABLE = "0"; // 不可用资源标识


    @Autowired
    FlightDynamicService flightDynamicService;

    @Autowired
    AircraftBoardingService aircraftBoardingService;

    @Autowired
    AircraftParametersDao aircraftParametersDao;

    @Autowired
    CompanyAircraftNumDao companyAircraftNumDao;

    @Autowired
    RmsCarouselService rmsCarouselService;

    @Autowired
    RmsCheckinCounterService rmsCheckinCounterService;

    @Autowired
    ArrivalGateService arrivalGateService;

    @Autowired
    AircraftStandService aircraftStandService;

    @Autowired
    SlideCoastService slideCoastService;

    @Autowired
    SecurityCheckinService securityCheckinService;

    @Autowired
    DepartureHallService departureHallService;

    @Autowired
    AirstandAirparametersService airstandAirparametersService;

    @Autowired
    GateOccupyingInfoDao gateOccupyingInfoDao;


    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(ActionNode actionNode, FlowContext flowContext, ProcessInstance processInstance) {

    }

    @ExposeAction("获取航班属性")
    public String getFlightAttrName(FlightDynamic flightDynamic) {
        return flightDynamicService.getTheFuckingFlightAttr(flightDynamic);
    }

    @ExposeAction("获取可用登机口")
    @SuppressWarnings("unused")
    public String getAvailableBoardingCodes(String flightDynamicId, String aircraftGateNum, String flightDynamicNature) {
        FlightDynamic flightDynamic = flightDynamicService.get(flightDynamicId);

        if (flightDynamic == null) {
            logger.error("不合法的航班动态记录");
            return "";
        }
        if (StringUtils.isBlank(aircraftGateNum)) {
            logger.error("当前航班{id={}, code={}}的机位号为空", flightDynamicId, flightDynamic.getFlightNum());
            return "";
        }
        if (StringUtils.isBlank(flightDynamicNature)) {
            logger.error("当前航班{id={}, code={}}的航班属性为空", flightDynamicId, flightDynamic.getFlightNum());
            return "";
        }
        List<AircraftBoarding> datas = aircraftBoardingService.findByAircraftStandNum(Splitter.on(",").splitToList(aircraftGateNum));
        return (null != datas && !datas.isEmpty()) ? (StringUtils.equals(flightDynamicNature, ResourceDistributionMethods.AVAILABLE) ? Joiner.on(",").join(datas.stream().map(AircraftBoarding::getBoardingGateNum).collect(Collectors.toList())) : Joiner.on(",").join(datas.stream().map(AircraftBoarding::getIntlBoardingGateNum).collect(Collectors.toList()))) : "";
    }

    @ExposeAction("获取行李转盘")
    @SuppressWarnings("unused")
    public String getAvailableCarouselByNature(String nature) {
        return Joiner.on(",").join(rmsCarouselService.findAvailableCarouselByNature(nature).stream().map(rmsCarousel -> rmsCarousel.getCarouselNum()).collect(Collectors.toList()));
    }

    @ExposeAction("获取值机柜台")
    @SuppressWarnings("unused")
    public String getAvailableCheckingCounterByNature(String nature) {
        return Joiner.on(",").join(rmsCheckinCounterService.findAvailableCheckingCounterByNature(nature).stream().map(rmsCheckinCounter -> rmsCheckinCounter.getCheckinCounterNum()).collect(Collectors.toList()));
    }

    @ExposeAction("获取到港门")
    @SuppressWarnings("unused")
    public String getAvailableArrivalGateByNature(String nature) {
        return Joiner.on(",").join(arrivalGateService.findAvailableArrivalGateByNature(nature).stream().map(arrivalGate -> arrivalGate.getArrivalGateNum()).collect(Collectors.toList()));
    }

    @ExposeAction("获取机位")
    @SuppressWarnings("unused")
    public String getAvailableGate() {
        return Joiner.on(",").join(aircraftStandService.findList(new AircraftStand()).stream().map(aircraftStand -> aircraftStand.getAircraftStandNum()).collect(Collectors.toList()));
    }

    @ExposeAction("获取可用机位")
    public String getAvailableGateFilterByAircraftTypeCode(String aircraftTypeCode) {
        String availableGates = "";

        // 获取状态为可用的机位
        List<String> avaiableGateList = aircraftStandService.findList(new AircraftStand()).stream().filter(result -> result.getAvailable().equals(ResourceDistributionMethods.AVAILABLE)).map(result -> result.getAircraftStandNum()).collect(Collectors.toList());
        // 查询「机位与机型对应表」，得出当前机型可以使用的机位列表
        AirstandAirparameters airstandAirparameters = airstandAirparametersService.findByAircraftModel(aircraftTypeCode);
        if (null != airstandAirparameters && StringUtils.isNotEmpty(airstandAirparameters.getAircraftStandNum())) {
            List<String> gate4Type = Splitter.on(",").splitToList(airstandAirparameters.getAircraftStandNum());
            List<String> availableList = gate4Type.stream().filter(result -> avaiableGateList.contains(result)).collect(Collectors.toList());
            availableGates = Joiner.on(",").join(availableList);
        }

        return availableGates;
    }

    @ExposeAction("机位翼展过滤")
    public String filterByAircraftWings(String availableGates, String aircraftTypeCode) {
        AircraftParameters aircraftParameters = aircraftParametersDao.findByAircraftModelCode(aircraftTypeCode);
        if (aircraftParameters == null) return "";

        List<String> gates = Splitter.on(",").splitToList(availableGates);
//        gates.stream().filter(result -> )

        return null;
    }


    @ExposeAction("获取滑槽")
    @SuppressWarnings("unused")
    public String getAvailableSlideCoastByNature(String nature) {
        return Joiner.on(",").join(slideCoastService.findAvailableSlideCoastByNature(nature).stream().map(slideCoast -> slideCoast.getSlideCoastNum()).collect(Collectors.toList()));
    }

    @ExposeAction("获取安检口")
    @SuppressWarnings("unused")
    public String getAvailableSecurityCheckinByNature(String nature) {
        return Joiner.on(",").join(securityCheckinService.findAvailableSecurityCheckinNature(nature).stream().map(securityCheckin -> securityCheckin.getScecurityCheckinNum()).collect(Collectors.toList()));
    }

    @ExposeAction("获取候机厅")
    @SuppressWarnings("unused")
    public String getAvailableDepartureHallByNature(String nature) {
        return Joiner.on(",").join(departureHallService.findAvailableDepartureHallByNature(nature).stream().map(departureHall -> departureHall.getDepartureHallNum()).collect(Collectors.toList()));
    }


    @ExposeAction("获取座位数")
    @SuppressWarnings("unused")
    public Integer getSeatNum(FlightDynamic flightDynamic) {
        Integer seatNumber = 100;
        if (null == flightDynamic) return seatNumber;
        // 先根据 航空公司编码 及 飞机号 在 「公司机号」信息中查询最大座位数
        CompanyAircraftNum companyAircraftNum = companyAircraftNumDao.queryByFlightDynamicCodeAndAircraftNum(flightDynamic.getFlightCompanyCode(), flightDynamic.getAircraftNum());
        if (companyAircraftNum != null && companyAircraftNum != null && companyAircraftNum.getMaxnumSeat() != 0)
            return companyAircraftNum.getMaxnumSeat().intValue();
        // 如果不存在有效数据, 根据 「机型」 在 「机型参数」信息中查询座位数
        AircraftParameters aircraftParameters = aircraftParametersDao.findByAircraftModelCode(flightDynamic.getAircraftTypeCode());
        if (null != aircraftParameters && null != aircraftParameters.getMaxnumSeat())
            seatNumber = aircraftParameters.getMaxnumSeat().intValue();

        return seatNumber;
    }

    @ExposeAction("获取机位占用时间")
    @SuppressWarnings("unused")
    public Integer getOciTimes(GateOccupyingInfo info) {
        Integer ociTimes = 0;
//        GateOccupyingInfo info = gateOccupyingInfoDao.get(pair.getId());
        if (info != null) {
            ociTimes = new Long(DateUtils.pastMinutes(info.getStartTime(), info.getOverTime())).intValue();
        }
        return ociTimes;
    }
}
