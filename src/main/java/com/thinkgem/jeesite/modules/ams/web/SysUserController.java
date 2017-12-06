package com.thinkgem.jeesite.modules.ams.web;

import com.thinkgem.jeesite.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xiaopo on 16/2/3.
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/sysuser")
public class SysUserController extends BaseController {


	@RequiresPermissions("ams:sysUser:view")
	@RequestMapping(value = "view")
	public String amsIndex(){
		return "ams/sys/user";
	}

}
