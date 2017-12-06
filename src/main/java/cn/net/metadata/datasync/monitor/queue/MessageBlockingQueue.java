package cn.net.metadata.datasync.monitor.queue;

import cn.net.metadata.datasync.monitor.service.runnable.SocketIOEventNotiCenterRunnable;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 保存ExecutorPlugins中的消息请求有序的阻塞队列
 * Created by xiaowu on 16/7/26.
 */
public final class MessageBlockingQueue {
    public static final BlockingQueue<SocketIOEventNotiCenterRunnable> MESSAGE_QUEUE = new LinkedBlockingDeque<>(9999);
}
