package cn.net.metadata.listener;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Created by wjp on 2017/2/28.
 */
public class MyListener extends AppenderSkeleton {
    @Override
    protected void append(LoggingEvent event) {
        String leven = event.getLevel().toString();
        String msg = event.getMessage().toString();
        System.out.println("-------------leven:"+leven+"\n-------------msg:"+msg);
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}
