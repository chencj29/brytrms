package com.thinkgem.jeesite.common.utils;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 * Created by wjp on 2017/5/11.
 */
public class CXFClientUtil {
    //与获取服务端连接的超时时间，单位为毫秒
    public static final int CXF_CLIENT_CONNECT_TIMEOUT = 3 * 1000;
    //获取连接后接收数据的超时时间，单位为毫秒
    public static final int CXF_CLIENT_RECEIVE_TIMEOUT = 60 * 1000;

    public static void configTimeout(Object service) {
        Client proxy = ClientProxy.getClient(service);
        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setConnectionTimeout(CXF_CLIENT_CONNECT_TIMEOUT);
        policy.setReceiveTimeout(CXF_CLIENT_RECEIVE_TIMEOUT);
        conduit.setClient(policy);
    }
}
