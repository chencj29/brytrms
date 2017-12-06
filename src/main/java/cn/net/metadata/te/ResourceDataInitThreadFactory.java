package cn.net.metadata.te;

import java.util.concurrent.ThreadFactory;

/**
 * Created by xiaowu on 2017/3/3.
 */
public class ResourceDataInitThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setUncaughtExceptionHandler(new ResourceDataInitExceptionHandler());
        return thread;
    }
}
