package com.thinkgem.jeesite.modules.rms.rd.container;

import com.thinkgem.jeesite.modules.rms.rd.ResourceType;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 任务队列容器及执行线程池
 * Created by xiaowu on 2017/2/8.
 */
public final class ResourceDistributionContainer {
    private static ConcurrentMap<ResourceType, BlockingQueue<Map<String, Object>>> container = null;

    public static void init() {
        synchronized (ResourceDistributionContainer.class) {
            container = new ConcurrentHashMap<>(ResourceType.values().length);
            container.put(ResourceType.AIRCRAFT_STND, new ArrayBlockingQueue<>(20));
            container.put(ResourceType.CHECKIN_COUNTER, new ArrayBlockingQueue<>(20));
            container.put(ResourceType.BOARDING_GATE, new ArrayBlockingQueue<>(20));
            container.put(ResourceType.ARRIVAL_GATE, new ArrayBlockingQueue<>(20));
            container.put(ResourceType.SLIDE_COAST, new ArrayBlockingQueue<>(20));
            container.put(ResourceType.CAROUSEL, new ArrayBlockingQueue<>(20));
            container.put(ResourceType.SECURITY_CHECKIN, new ArrayBlockingQueue<>(20));
            container.put(ResourceType.DEPARTURE_HALL, new ArrayBlockingQueue<>(20));
        }
    }

    public static BlockingQueue<Map<String, Object>> getQueue(ResourceType resourceType) {
        return container.get(resourceType);
    }
}
