package cn.net.metadata.webservice;


import cn.net.metadata.messageservice.common.aidx.FlightDynamic;
import cn.net.metadata.messageservice.common.entity.FlightPairWrapper;
import cn.net.metadata.messageservice.publisher.webservice.ActiveMQTypeEnum;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaopo
 * @date 16/4/13
 */
@WebService
public interface IReceiveFlightDataService {

	String receiveFlightDyanmicData(List<FlightDynamic> dynamicList, List<FlightPairWrapper> pairWrappers);

	String receiveFlightDynamicStatus(FlightDynamic dynamic, ActiveMQTypeEnum activeMQTypeEnum);

	String receiveData(String sync,List<FlightDynamic> dynamicList, List<FlightPairWrapper> pairWrappers);

	//消息回复
	String msgCallback(String message);
}
