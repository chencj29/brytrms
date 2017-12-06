package test;

import com.thinkgem.jeesite.modules.ams.entity.AmsAddress;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chencheng on 16/4/8.
 */
public class TestFuckingFlightAttr {

    public static void main(String[] args) {
        System.out.println(TestFuckingFlightAttr.getTheFuckingFlightAttr("1"));
    }

    public static String getTheFuckingFlightAttr(String flightDynamicId) {
        List<AmsAddress> addressList = new ArrayList<>();

        AmsAddress a = new AmsAddress();
        a.setForeignornot("0");
        addressList.add(a);

        AmsAddress b = new AmsAddress();
        b.setForeignornot("1");
        addressList.add(b);

        AmsAddress c = new AmsAddress();
        c.setForeignornot("1");
        addressList.add(c);

        AmsAddress d = new AmsAddress();
        d.setForeignornot("0");
        addressList.add(d);

        boolean isChina = true;
        String fuckingflightAttr = null;
        // 地点中存在国外地点,那么肯定不是国内航班
        for (int i = 0; i < addressList.size(); i++) {
            if (addressList.get(i).getForeignornot().equals("1")) {
                isChina = false;
                break;
            }
        }

        if (isChina) {
            fuckingflightAttr = "国内航班";
        } else {
            // 判断是否为国际航班
            // 起飞地是国内,目的地为国外,中间经停地都是国外
            if (addressList.get(0).getForeignornot().equals("0")) {
                boolean isForeign = true;
                for (int i = 1; i < addressList.size(); i++) {
                    if (addressList.get(i).getForeignornot().equals("0")) {
                        isForeign = false;
                        break;
                    }
                }
                if (isForeign) {
                    fuckingflightAttr = "国际航班";
                } else {
                    fuckingflightAttr = "混合航班";
                }
            }
            // 起飞地是国外,目的地为国内,中间经停地都是国外
            if (addressList.get(0).getForeignornot().equals("1")) {
                boolean isForeign = true;
                for (int i = 0; i < addressList.size() - 1; i++) {
                    if (addressList.get(i).getForeignornot().equals("0")) {
                        isForeign = false;
                        break;
                    }
                }
                if (isForeign && addressList.get(addressList.size() - 1).getForeignornot().equals("0")) {
                    fuckingflightAttr = "国际航班";
                } else {
                    fuckingflightAttr = "混合航班";
                }
            }
        }
        return fuckingflightAttr;
    }
}
