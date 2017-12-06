package com.thinkgem.jeesite.modules.rms.wrapper;

import com.thinkgem.jeesite.modules.rms.entity.ProgressPostPermission;
import org.apache.ibatis.type.Alias;

/**
 * 岗位 - 保障过程 权限包装器
 * Created by xiaowu on 16/6/17.
 */
@Alias("ProgressPostPrivilegeWrapper")
public class ProgressPostPrivilegeWrapper extends ProgressPostPermission {
    private String progressCode;
    private String progressName; //保障过程名称
    private String progressAttr;
    private String privilege; // 是否拥有编辑权限

    public String getProgressName() {
        return progressName;
    }

    public void setProgressName(String progressName) {
        this.progressName = progressName;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getProgressCode() {
        return progressCode;
    }

    public void setProgressCode(String progressCode) {
        this.progressCode = progressCode;
    }

    public String getProgressAttr() {
        return progressAttr;
    }

    public void setProgressAttr(String progressAttr) {
        this.progressAttr = progressAttr;
    }
}
