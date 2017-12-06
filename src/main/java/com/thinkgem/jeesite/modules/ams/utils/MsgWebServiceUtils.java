package com.thinkgem.jeesite.modules.ams.utils;

import cn.net.metadata.messageservice.common.entity.FlightPairWrapper;
import cn.net.metadata.messageservice.publisher.webservice.SendMsgInterface;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.CXFClientUtil;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * webservice调用工具,调用后将向消息中间件的数据发布者发布航班计划消息
 * Created by chencheng on 16/4/11.
 */
public class MsgWebServiceUtils {
    private static final Logger logger = LoggerFactory.getLogger(MsgWebServiceUtils.class);

    private static SendMsgInterface sendMsgService = null;


    static {
        //创建WebService客户端代理工厂
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        //注册WebService接口
        factory.setServiceClass(SendMsgInterface.class);
        //设置WebService地址
        factory.setAddress(Global.getConfig("message-service.webservice.url"));
        //初始化sendMsgService
        sendMsgService = (SendMsgInterface) factory.create();
        //设置超时时间
        CXFClientUtil.configTimeout(sendMsgService);
    }

    /**
     * 给航班系统发送信息
     *
     */
    public static String publishFlightDynamic(String syncNo, ArrayList<FlightDynamic> flightDynamics, ArrayList<FlightPlanPair> pairs) {
        ArrayList< cn.net.metadata.messageservice.common.aidx.FlightDynamic> dynamics = new ArrayList<>();
        ArrayList<FlightPairWrapper> wrappers = new ArrayList<>();
        if(!Collections3.isEmpty(flightDynamics)){
            for (FlightDynamic flightDynamic : flightDynamics) {
                cn.net.metadata.messageservice.common.aidx.FlightDynamic tempDynamic = new cn.net.metadata.messageservice.common.aidx.FlightDynamic();
                BeanUtils.copyProperties(flightDynamic,tempDynamic);
                dynamics.add(tempDynamic);
            }
        }

        if(!Collections3.isEmpty(pairs)){
            for (FlightPlanPair pair : pairs) {
                FlightPairWrapper wrapper = new FlightPairWrapper();
                BeanUtils.copyProperties(pair,wrapper);
                wrappers.add(wrapper);
            }
        }

        try {
            return sendMsgService.sendFlightDynamic(cn.net.metadata.messageservice.common.aidx.FlightDynamic.class.getName(), syncNo, dynamics, wrappers);
        } catch (Exception e) {
            copeException(e);
        }
        return "false";
    }

    //多占用数同步
    public static String publishOci(String syncNo,ArrayList<String> ociJsonList){
        try {
            return sendMsgService.sendOci(syncNo,ociJsonList);
        } catch (Exception e) {
            copeException(e);
        }
        return "false";
    }

    public static String publishOciUniq(String syncNo,String ociJson){
        ArrayList<String> ociJsonList = new ArrayList();
        ociJsonList.add(ociJson);
        return publishOci(syncNo,ociJsonList);
    }

    /**
     * 将webservic exception转换处理.
     */
    public static void copeException(Exception e) {
        if (e instanceof javax.xml.ws.WebServiceException) logger.error("中间件连接超时！");
        else logger.error("消息发布异常！msg:" + e.getMessage() + ",class:" + e.getClass());
    }
}
