package cn.net.metadata.datasync.monitor.scan;

/**
 * Created by xiaowu on 16/1/7.
 */

import cn.net.metadata.datasync.monitor.annotation.ActionService;
import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.factory.MonitorFactory;
import cn.net.metadata.datasync.monitor.wrapper.MonitorEntityWrapper;
import cn.net.metadata.datasync.socketio.ConcurrentClientsHolder;
import com.thinkgem.jeesite.common.utils.PropertiesLoader;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import io.socket.client.IO;
import org.apache.commons.lang3.StringUtils;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.*;

/**
 * 扫描所有@Monitor & @MonitorField的类及字段
 *
 * @author xiaowu
 * @version 1.0
 *          wirtten by 2016-01-07
 */
public class MonitorScannerTask {
    private static final Logger logger = LoggerFactory.getLogger(MonitorScannerTask.class);
    private static PropertiesLoader _loader = null;
    private static Reflections reflections = null;
    private static String socketServerUrl = DictUtils.getDictValueConstant("socket_io_setting", "url");


    public static void scan() {
        synchronized (MonitorScannerTask.class) {
            logger.info("Start Scanning Entities");
            _loader = new PropertiesLoader("classpath:notification.monitor/config.properties");
            reflections = new Reflections(_loader.getProperty("scan.path", "com.thinkgem.jeesite.modules.ams.entity"));
            Set<Class<?>> entities = reflections.getTypesAnnotatedWith(Monitor.class);


            entities.forEach(clazz -> {
                Monitor monitor = clazz.getAnnotation(Monitor.class);
                if (monitor != null) {
                    logger.info("Entity Scanned - Desc:{}, TableName:{}, Class:{}", monitor.desc(), monitor.tableName(), clazz.getName());
                    MonitorEntityWrapper entityWrapper = new MonitorEntityWrapper();
                    entityWrapper.setClazz(clazz.getName());
                    entityWrapper.setDesc(monitor.desc());
                    entityWrapper.setTableName(monitor.tableName());
                    entityWrapper.setService(monitor.service());
                    entityWrapper.setSocketNS(monitor.socketNS());
                    if (!ConcurrentClientsHolder.exists(monitor.socketNS())) {
                        IO.Options opts = new IO.Options();
                        opts.forceNew = true;
                        opts.query = "ns=" + monitor.socketNS();
                        try {
                            ConcurrentClientsHolder.put(monitor.socketNS(), IO.socket(socketServerUrl, opts).connect());
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                            logger.error("初始化监听对象失败, namesace={}", monitor.socketNS());
                            System.exit(-1);
                        }
                    }


                    Map<String, String> fieldWrappers = new HashMap<>();
                    ReflectionUtils.getFields(clazz, (field) -> field.isAnnotationPresent(MonitorField.class)).forEach(field -> {
                        MonitorField monitorField = field.getAnnotation(MonitorField.class);
                        fieldWrappers.put(field.getName(), monitorField.desc());
                        logger.info("Field Scanned - Desc:{}, FieldName:{}", monitorField.desc(), field.getName());
                    });

                    entityWrapper.setFields(fieldWrappers);
                    MonitorFactory.getDatas().put(clazz.getName(), entityWrapper);
                }
            });

            Map<String, List<String>> actionServiceMap = MonitorFactory.getActionServices();
            for (Class<?> clazz : new Reflections("cn.net.metadata").getTypesAnnotatedWith(ActionService.class)) {
                ActionService actionService = clazz.getAnnotation(ActionService.class);
                List<String> propList = new ArrayList<>();
                String serviceId = StringUtils.uncapitalize(clazz.getSimpleName());
                propList.add(actionService.desc());
                propList.add(actionService.type().name());

                actionServiceMap.put(serviceId, propList);
            }

            logger.info("Entities Scanning Finish");
        }
    }
}

