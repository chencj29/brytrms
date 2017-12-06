package com.thinkgem.jeesite.modules.rms.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xiaopo on 16/3/23.
 */
@Controller
@RequestMapping("${adminPath}/rms/safeguardGraph")
public class SafeguradGraphController {


	@RequiresPermissions("rms:safeguardGraph:view")
	@RequestMapping("view")
	public String view() {
		return "rms/safeguardGraph/safeguardGraph";
	}

}
