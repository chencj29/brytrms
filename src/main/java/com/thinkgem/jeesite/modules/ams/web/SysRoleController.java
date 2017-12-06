package com.thinkgem.jeesite.modules.ams.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by xiaopo on 16/2/3.
 */
@Controller
@RequestMapping(value = "${adminPath}/ams/sysrole")
public class SysRoleController extends BaseController {

	@Autowired
	private SystemService systemService;


	@RequiresPermissions("ams:sysRole:view")
	@RequestMapping(value = "view")
	public String amsIndex(){
		return "ams/sys/role";
	}

	@RequiresPermissions("ams:sysRole:view")
	@RequestMapping(value = "list")
	@ResponseBody
	public DataTablesPage<Role> view(DataTablesPage<Role> ruleDataTablesPage){
		List<Role> roleList = systemService.findAllRole();
		ruleDataTablesPage.setData(roleList);


		return ruleDataTablesPage;
	}

}
