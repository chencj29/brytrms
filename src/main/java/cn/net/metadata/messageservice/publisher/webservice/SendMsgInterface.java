package cn.net.metadata.messageservice.publisher.webservice;

import cn.net.metadata.messageservice.common.aidx.FlightDynamic;
import cn.net.metadata.messageservice.common.entity.FlightPairWrapper;

import javax.jws.WebService;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wjp on 2017/5/27.
 */
@WebService
public interface SendMsgInterface {
    String sendFlightDynamic(String var1, String var2, ArrayList<FlightDynamic> var3, ArrayList<FlightPairWrapper> var4);

    String sendOci(String syncNo,ArrayList<String> ociJsonList);
}
