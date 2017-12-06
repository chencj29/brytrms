package cn.net.metadata.spring;

import com.thinkgem.jeesite.modules.rms.rd.container.ResourceDistributionContainer;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by xiaowu on 2017/2/9.
 */
@Component
public class RmsInitializer {

    @PostConstruct
    public void init() {
        LoggerFactory.getLogger(getClass()).info("初始化资源分配队列");
        ResourceDistributionContainer.init(); // 初始化资源分配队列
    }
}
