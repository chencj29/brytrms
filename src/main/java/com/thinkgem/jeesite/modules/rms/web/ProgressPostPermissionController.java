package com.thinkgem.jeesite.modules.rms.web;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.ProgressPostPermission;
import com.thinkgem.jeesite.modules.rms.service.ProgressPostPermissionService;
import com.thinkgem.jeesite.modules.rms.wrapper.ProgressPostPrivilegeWrapper;
import com.thinkgem.jeesite.modules.rms.wrapper.SaveProgressPrivilegesWrapper;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.easyui.DataGrid.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 权限设置Controller
 *
 * @author xiaowu
 * @version 2016-06-16
 */
@Controller
@RequestMapping(value = "${adminPath}/rms/progressPostPermission")
public class ProgressPostPermissionController extends BaseController {

    @Autowired
    private ProgressPostPermissionService progressPostPermissionService;

    @Autowired
    private OfficeService officeService;

    @RequiresPermissions("rms:progressPostPermission:view")
    @RequestMapping("view")
    public String view() {
        return "rms/progressPostPermission/progressPostPermission";
    }

    @RequiresPermissions("rms:progressPostPermission:view")
    @RequestMapping("get")
    @ResponseBody
    public ProgressPostPermission get(@RequestParam("id") String id) {
        return progressPostPermissionService.get(id);
    }

    @RequiresPermissions("rms:progressPostPermission:view")
    @RequestMapping("list")
    @ResponseBody
    public DataGrid<ProgressPostPermission> list(ProgressPostPermission progressPostPermission) {
        return new DataGrid<>(progressPostPermissionService.findList(progressPostPermission));
    }

    @RequiresPermissions("rms:progressPostPermission:view")
    @RequestMapping("queryPrivileges")
    @ResponseBody
    public DataGrid<ProgressPostPrivilegeWrapper> queryPrivileges(String postId) {
        return new DataGrid<>(progressPostPermissionService.findPrivileges(postId));
    }

    @RequiresPermissions("rms:progressPostPermission:view")
    @RequestMapping("savePrivileges")
    @ResponseBody
    public Message savePrivileges(@RequestBody SaveProgressPrivilegesWrapper wrapper) {
        Message message = new Message();

        try {
            // 先删除所有的权限数据
            if (StringUtils.isEmpty(wrapper.getPostId()) || officeService.get(wrapper.getPostId()) == null) {
                message.setCode(0);
                message.setMessage("不存在的岗位!");
                return message;
            }

            progressPostPermissionService.deleteByPostId(wrapper.getPostId());

            wrapper.getPrivileges().forEach(progress -> {
                ProgressPostPermission ppp = new ProgressPostPermission();
                ppp.setPostId(wrapper.getPostId());
                ppp.setProgressId(progress.getProgressId());

                progressPostPermissionService.save(ppp);
            });

            message.setMessage("操作成功");
            message.setCode(1);
        } catch (Exception e) {
            message.setCode(0);
            message.setMessage(e.getMessage());
        }


        return message;
    }

    @RequiresPermissions("rms:progressPostPermission:view")
    @RequestMapping(value = "form")
    public String form(ProgressPostPermission progressPostPermission, Model model) {
        model.addAttribute("progressPostPermission", progressPostPermission);
        return "modules/rms/progressPostPermissionForm";
    }

    @RequiresPermissions("rms:progressPostPermission:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Message save(ProgressPostPermission progressPostPermission, Model model, Message message) {
        if (!beanValidator(model, progressPostPermission)) {
            message.setMessage("数据校验错误!");
        }
        try {
            progressPostPermissionService.save(progressPostPermission);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

    @RequiresPermissions("rms:progressPostPermission:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Message delete(ProgressPostPermission progressPostPermission, Message message) {
        try {
            progressPostPermissionService.delete(progressPostPermission);
            message.setCode(1);
        } catch (Exception e) {
            message.setMessage(e.getMessage());
        }
        return message;
    }

}