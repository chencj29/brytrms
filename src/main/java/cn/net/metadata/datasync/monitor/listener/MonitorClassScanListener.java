package cn.net.metadata.datasync.monitor.listener;

import cn.net.metadata.datasync.monitor.queue.MessageSenderRunnable;
import cn.net.metadata.datasync.monitor.scan.MonitorScannerTask;
import com.thinkgem.jeesite.modules.rms.rd.ResourceType;
import com.thinkgem.jeesite.modules.rms.rd.container.ResourceDistributionContainer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by xiaowu on 16/1/7.
 */
public class MonitorClassScanListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        MonitorScannerTask.scan();
        new Thread(new MessageSenderRunnable()).start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ResourceDistributionContainer.getQueue(ResourceType.AIRCRAFT_STND).clear();
    }
}
