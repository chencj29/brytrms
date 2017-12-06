package cn.net.metadata.utils;

import com.thinkgem.jeesite.common.utils.CacheUtils;

import java.util.Map;

/**
 * Created by wjp on 2017/5/10.
 */
public class CheckUtils {
    public static Map<String,Class> map = (Map<String, Class>) CacheUtils.get("ociCheck");

    public static Class getOci(String methodName){
        return map.get(methodName);
    }
}
