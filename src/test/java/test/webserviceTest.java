package test;

import cn.net.metadata.messageservice.publisher.webservice.SendMsgInterface;
import com.thinkgem.jeesite.common.utils.CXFClientUtil;
import com.thinkgem.jeesite.common.utils.StringUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wjp on 2017/4/25.
 */
public class webserviceTest {
    private static SendMsgInterface sendMsgService = null;
//    private static String url = Global.getConfig("message-service.webservice.url");
    private static String url = "http://192.168.16.191:8686/msp/ws/sendMsgService1";

    static {
        //创建WebService客户端代理工厂
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        //注册WebService接口
        factory.setServiceClass(SendMsgInterface.class);
        //设置WebService地址
//        factory.setAddress(Global.getConfig("message-service.webservice.url"));
        factory.setAddress(url);
//        if (!isWsdlConnection(url)) {
//            throw new RuntimeException("初始化sendMsgService失败！");
//        }

        //初始化sendMsgService
        sendMsgService = (SendMsgInterface) factory.create();
        //设置超时时间
        CXFClientUtil.configTimeout(sendMsgService);
    }

    public static void main(String[] args) {

        try {
            System.out.println("result:"+sendMsgService.sendFlightDynamic("", "", null, null));
        } catch (Exception e) {
            System.out.println("msg:"+e.getMessage()+",class:"+e.getClass());
        }
    }

    public static boolean isWsdlConnection(String address) {
        if (StringUtils.isBlank(address)) return false;
        try {
            URL urlObj = new URL(address + "?wsdl");
            HttpURLConnection oc = (HttpURLConnection) urlObj.openConnection();
            oc.setUseCaches(false);
            oc.setConnectTimeout(1000); //设置超时时间
            int status = oc.getResponseCode();//请求状态
            if (200 == status) {
                return true;
            }
            System.out.println("状态码：" + status);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "异常：" + e.getClass().getSimpleName());
        }
        return false;
    }
}
