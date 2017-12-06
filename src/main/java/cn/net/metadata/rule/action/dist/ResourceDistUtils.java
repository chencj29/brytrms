package cn.net.metadata.rule.action.dist;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaowu on 3/18/16.
 */
public final class ResourceDistUtils {
    public static final String INTE_RES = "1";
    public static final String INTL_RES = "2";
    public static final String MIXING_RES = "3";

    private static final Logger logger = LoggerFactory.getLogger(ResourceDistUtils.class);

    public static String getFlightDynamicNature(FlightDynamic flightDynamic) {
        String result = SpringContextHolder.getBean(FlightDynamicService.class).getTheFuckingFlightAttr(flightDynamic);
        return StringUtils.equals(result, "国内航班") ? "1" : StringUtils.equals(result, "国际航班") ? "2" : "3";
    }

    /**
     * 根据航班配对信息计算资源占用时间
     *
     * @param flightPlanPair 航班配对信息
     * @return 航班类型，资源占用时间
     */
    public static Map<String, Object> calcOccupyingTimeByPair(FlightPlanPair flightPlanPair) {
        Map<String, Object> variables = new HashMap<>();
        Date startDate = null, overDate = null;
        if (StringUtils.isNotEmpty(flightPlanPair.getFlightDynimicId()) && StringUtils.isNotEmpty(flightPlanPair.getFlightDynimicId2())) {
            variables.put("inoutType", "RELATE");
            startDate = getClosestDate(false, flightPlanPair, "J");
            overDate = getClosestDate(true, flightPlanPair, "C");
        } else if (StringUtils.isNotEmpty(flightPlanPair.getFlightDynimicId())) {
            variables.put("inoutType", "SI");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(flightPlanPair.getPlanDate());  // 按照计划日期的第二天6点
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 6);
            calendar.set(Calendar.MINUTE, 0);
            startDate = getClosestDate(false, flightPlanPair, "J");
            overDate = calendar.getTime();
        } else if (StringUtils.isNotEmpty(flightPlanPair.getFlightDynimicId2())) {
            variables.put("inoutType", "SO"); //单出

            //wjp_2017年11月16日0时14分 全部调整为零点
            try {
                Date tmpStart = new Date();
                if (flightPlanPair.getEtd2() != null) tmpStart = flightPlanPair.getEtd2();
                if (flightPlanPair.getAtd2() != null) tmpStart = flightPlanPair.getAtd2();
                startDate = DateUtils.parseDate(DateUtils.formatDate(tmpStart) + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            } catch (ParseException e) {
                //e.printStackTrace();
                logger.error("[占用计算]单出航班计算开始占用时间出错:" + e.getMessage());
                startDate = new Date();
            }
//            startDate = new Date();
            overDate = getClosestDate(true, flightPlanPair, "C");
        }


        // 如果开始时间大于结束时间，调个儿
        if (startDate.after(overDate)) {
            variables.put("start", overDate);
            variables.put("over", startDate);
        } else {
            variables.put("start", startDate);
            variables.put("over", overDate);
        }

        return variables;
    }

    /**
     * 根据航班动态得到相应的时间
     *
     * @param direction     true 起飞时间 false 到达时间
     * @param flightDynamic 航班动态
     * @return 实际 - 预计 - 计划
     */
    public static Date getClosestDate(Boolean direction, FlightDynamic flightDynamic) {
        Date returnDate;

        if (flightDynamic == null) return null;

        if (direction) {
            if (flightDynamic.getAtd() != null) returnDate = flightDynamic.getAtd();
            else if (flightDynamic.getEtd() != null) returnDate = flightDynamic.getEtd();
            else returnDate = flightDynamic.getDeparturePlanTime();
        } else {
            if (flightDynamic.getAta() != null) returnDate = flightDynamic.getAta();
            else if (flightDynamic.getEta() != null) returnDate = flightDynamic.getEta();
            else returnDate = flightDynamic.getArrivalPlanTime();
        }

        return returnDate;
    }

    /**
     * 根据配对信息得到相应的时间
     *
     * @param direction      true 起飞时间 false 到达时间
     * @param flightPlanPair 配对信息
     * @param inoutType      航班类型，确定取值范围
     * @return 实际 - 预计 - 计划
     */
    public static Date getClosestDate(Boolean direction, FlightPlanPair flightPlanPair, String inoutType) {
        Date returnDate = null;

        if (flightPlanPair == null) return null;

        if (StringUtils.equalsIgnoreCase("J", inoutType)) {
            if (direction) {
                if (flightPlanPair.getAtd() != null) {
                    returnDate = flightPlanPair.getAtd();
                } else if (flightPlanPair.getEtd() != null) {
                    returnDate = flightPlanPair.getEtd();
                } else {
                    returnDate = flightPlanPair.getDeparturePlanTime();
                }
            } else {
                if (flightPlanPair.getAta() != null) {
                    returnDate = flightPlanPair.getAta();
                } else if (flightPlanPair.getEta() != null) {
                    returnDate = flightPlanPair.getEta();
                } else {
                    returnDate = flightPlanPair.getArrivalPlanTime();
                }
            }
        } else if (StringUtils.equalsIgnoreCase("C", inoutType)) {
            if (direction) {
                if (flightPlanPair.getAtd2() != null) returnDate = flightPlanPair.getAtd2();
                else if (flightPlanPair.getEtd2() != null) returnDate = flightPlanPair.getEtd2();
                else returnDate = flightPlanPair.getDeparturePlanTime2();
            } else {
                if (flightPlanPair.getAta2() != null) returnDate = flightPlanPair.getAta2();
                else if (flightPlanPair.getEta2() != null) returnDate = flightPlanPair.getEta2();
                else returnDate = flightPlanPair.getArrivalPlanTime2();
            }
        }

        return returnDate;
    }
}
