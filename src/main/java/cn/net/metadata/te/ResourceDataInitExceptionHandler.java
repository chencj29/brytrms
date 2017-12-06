package cn.net.metadata.te;

/**
 * Created by xiaowu on 2017/3/3.
 */
public class ResourceDataInitExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(e);
    }
}
