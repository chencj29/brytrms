package com.thinkgem.jeesite.modules.rms.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.rms.service.CommonService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xiaowu on 2/27/16.
 */
@Controller
public class CommonController extends BaseController {
    @Autowired
    CommonService service;


    @RequiresPermissions("user")
    @RequestMapping("/common/exists")
    @ResponseBody
    public Boolean exists(@RequestParam("tn") String tableName, @RequestParam("fc") String fieldCode, @RequestParam("fv") String fieldValue, @RequestParam("id") String id) {
        return service.exists(tableName, fieldCode, fieldValue, id);
    }
}
