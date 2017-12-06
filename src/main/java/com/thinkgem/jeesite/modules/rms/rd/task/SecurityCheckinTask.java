package com.thinkgem.jeesite.modules.rms.rd.task;

import com.thinkgem.jeesite.modules.rms.rd.ResourceType;
import com.thinkgem.jeesite.modules.rms.rd.container.ResourceDistributionContainer;
import com.thinkgem.jeesite.modules.rms.service.ResourceSharingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by xiaowu on 2017/2/8.
 */
@Component
@Lazy(false)
public class SecurityCheckinTask implements DistributionTaskInterface {

    @Autowired
    private ResourceSharingService resourceSharingService;

    @Override
    @Scheduled(fixedDelay = 2000, initialDelay = 2000)
    public void execute() {
        try {
            Map<String, Object> conditions = ResourceDistributionContainer.getQueue(ResourceType.SECURITY_CHECKIN).poll();
            if (conditions != null) resourceSharingService.distSecurityCheckinAuto(conditions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
