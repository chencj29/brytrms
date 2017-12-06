package com.thinkgem.jeesite.modules.sys.utils;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.dao.AmsAddressDao;
import com.thinkgem.jeesite.modules.ams.entity.AmsAddress;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaopo on 15/12/30.
 */
public class AddressUtils {
    private static AmsAddressDao addressDao = SpringContextHolder.getBean(AmsAddressDao.class);


    public static AmsAddress get(List<AmsAddress> amsAddressList, String id){
        if(StringUtils.isNotBlank(id)){
            Map<String,AmsAddress> addressMap = getAddressMap(amsAddressList);
            if(addressMap.containsKey(id)){
                return addressMap.get(id);
            }
        }
        return new AmsAddress();
    }

    public static Map<String,AmsAddress> getAddressMap(List<AmsAddress> amsAddressList){
        Map<String,AmsAddress> addressMap = new HashMap<>();
        for (AmsAddress address : amsAddressList) {
            addressMap.put(address.getId(),address);
        }
        return addressMap;
    }
}
