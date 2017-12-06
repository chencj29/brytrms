package cn.net.metadata.datasync.monitor.action;

import com.thinkgem.jeesite.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xiaowu on 16/1/11.
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/monitor")
public class MonitorController extends BaseController {

    @RequiresPermissions("rms:monitor:view")
    @RequestMapping("view")
    public String view() {
        return "ams/sys/monitor";
    }
}
