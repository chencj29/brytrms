package cn.net.metadata.webservice.dynamic;

import cn.net.metadata.messageservice.publisher.webservice.ActiveMQTypeEnum;
import cn.net.metadata.webservice.dynamic.impl.*;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaopo
 * @date 16/4/21
 */
@Service
public class FlightDynamicStatusModifyFactory {
    static Map<ActiveMQTypeEnum, Class<?>> dynamicStatusDataMap = new HashMap<>();

    static {
        dynamicStatusDataMap.put(ActiveMQTypeEnum.FLIGHT_ALTERNATE, FlightDynamicStatusAlternate.class);
        dynamicStatusDataMap.put(ActiveMQTypeEnum.FLIGHT_BOARD, FlightDynamicStatusBoard.class);
        dynamicStatusDataMap.put(ActiveMQTypeEnum.FLIGHT_BOARDEND, FlightDynamicStatusBoardend.class);
        dynamicStatusDataMap.put(ActiveMQTypeEnum.FLIGHT_CANCEL, FlightDynamicStatusCancel.class);
        dynamicStatusDataMap.put(ActiveMQTypeEnum.FLIGHT_DELAY, FlightDynamicStatusDelay.class);
        dynamicStatusDataMap.put(ActiveMQTypeEnum.FLIGHT_GUEST, FlightDynamicStatusGuest.class);
        dynamicStatusDataMap.put(ActiveMQTypeEnum.FLIGHT_HAVEIN, FlightDynamicStatusHaveIn.class);
        dynamicStatusDataMap.put(ActiveMQTypeEnum.FLIGHT_MODIFY, FlightDynamicStatusModify.class);
        dynamicStatusDataMap.put(ActiveMQTypeEnum.FLIGHT_ETA, FlightDynamicStatusEta.class);
        dynamicStatusDataMap.put(ActiveMQTypeEnum.FLIGHT_TAKEOFF, FlightDynamicStatusTakeoff.class);
        dynamicStatusDataMap.put(ActiveMQTypeEnum.FLIGHT_TAKEOFFEND, FlightDynamicStatusTakeoffEnd.class);
        dynamicStatusDataMap.put(ActiveMQTypeEnum.FLIGHT_TURNBACK, FlightDynamicStatusTurnBack.class);
        dynamicStatusDataMap.put(ActiveMQTypeEnum.FLIGHT_SILDEBACK, FlightDynamicStatusSildeBack.class);
        dynamicStatusDataMap.put(ActiveMQTypeEnum.FLIGHT_DEL, FlightDynamicStatusDelete.class);
        dynamicStatusDataMap.put(ActiveMQTypeEnum.FLIGHT_CLEAR, FlightDynamicStatusClear.class);
    }

    public IFlightDynamicStatus creator(ActiveMQTypeEnum activeMQTypeEnum) {
        IFlightDynamicStatus iFlightDynamicStatus = (IFlightDynamicStatus) SpringContextHolder.getBean(dynamicStatusDataMap.get(activeMQTypeEnum));
        if (iFlightDynamicStatus == null) throw new RuntimeException("no such this class ! enum:" + dynamicStatusDataMap.get(activeMQTypeEnum));
        return iFlightDynamicStatus;
    }


}
