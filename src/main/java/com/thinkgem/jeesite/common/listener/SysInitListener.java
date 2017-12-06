package com.thinkgem.jeesite.common.listener;

import cn.net.metadata.annotation.Check;
import cn.net.metadata.annotation.Oci;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.PropertiesLoader;
import com.thinkgem.jeesite.modules.rms.service.FlightPlanPairService;
import com.thinkgem.jeesite.modules.rms.service.ResourceMockDistInfoService;
import com.thinkgem.jeesite.modules.rms.service.ResourceSharingService;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by wjp on 2017/5/10.
 */
public class SysInitListener implements ServletContextListener {
    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SysInitListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("系统初始化...");
        getOciCheckMethod();
        getOciClass();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    /**
     * 获取check类的占用类，并缓存
     */
    private void getOciCheckMethod(){
        Set<Method> checkMethods = new HashSet<>();
        checkMethods.addAll(ReflectionUtils.getAllMethods(ResourceSharingService.class, method -> method.isAnnotationPresent(Check.class)));
        checkMethods.addAll(ReflectionUtils.getAllMethods(FlightPlanPairService.class, method -> method.isAnnotationPresent(Check.class)));
        checkMethods.addAll(ReflectionUtils.getAllMethods(ResourceMockDistInfoService.class, method -> method.isAnnotationPresent(Check.class)));
        Map<String,Class> map = new HashMap<>();
        checkMethods.forEach(method -> {
            Check check = method.getAnnotation(Check.class);
            if(check.isOci()) map.put(method.getName(),check.oci());
        });

        CacheUtils.put("ociCheck",map);
    }

    /**
     * 获取oci类
     */
    public void getOciClass(){
        PropertiesLoader _loader = new PropertiesLoader("classpath:notification.monitor/config.properties");
        Reflections reflections = new Reflections(_loader.getProperty("oci.path", "com.thinkgem.jeesite.modules.rms.entity"));
        Set<Class<?>> ocis = reflections.getTypesAnnotatedWith(Oci.class);
        Map<String,Class> map = new HashMap<>();
        ocis.forEach(clzz->map.put(clzz.getSimpleName(),clzz));
        CacheUtils.put("ociClass",map);

        Reflections reflectionDaos = new Reflections(_loader.getProperty("ociDao.path", "com.thinkgem.jeesite.modules.rms.dao"));
        Set<Class<?>> daoOcis = reflectionDaos.getTypesAnnotatedWith(Oci.class);
        Map<String,Class> daoMap = new HashMap<>();
        daoOcis.forEach(clzz->{
            String ociName = clzz.getSimpleName().replace("Dao","");
            if(map.containsKey(ociName)) daoMap.put(ociName,clzz);
        });
        CacheUtils.put("ociDao",daoMap);
    }
}
