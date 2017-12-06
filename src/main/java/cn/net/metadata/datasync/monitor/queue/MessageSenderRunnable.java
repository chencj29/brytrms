package cn.net.metadata.datasync.monitor.queue;

import cn.net.metadata.datasync.monitor.service.runnable.SocketIOEventNotiCenterRunnable;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiaowu on 16/7/26.
 */
public class MessageSenderRunnable implements Runnable {
    BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1000);

    ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 10, 1, TimeUnit.HOURS, queue, new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public void run() {
        while (true) {
            try {
                SocketIOEventNotiCenterRunnable runnable = MessageBlockingQueue.MESSAGE_QUEUE.poll(100, TimeUnit.MILLISECONDS);
                if (runnable != null) executor.execute(runnable); // FIXME 需要线程池来进行runnable调用
            } catch (InterruptedException e) {
                LoggerFactory.getLogger(getClass()).error("消息发送线程失败: ", e.getMessage());
                e.printStackTrace();

            }
        }
    }
}
