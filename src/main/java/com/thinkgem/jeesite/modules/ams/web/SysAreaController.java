package com.thinkgem.jeesite.modules.ams.web;

import com.thinkgem.jeesite.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xiaopo on 16/2/18.
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/sysarea")
public class SysAreaController extends BaseController {

	@RequiresPermissions("ams:sysArea:view")
	@RequestMapping("view")
	public String view(){
		return "ams/sys/area";
	}
}
