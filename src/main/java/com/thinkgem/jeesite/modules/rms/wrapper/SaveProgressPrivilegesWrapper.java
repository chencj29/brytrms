package com.thinkgem.jeesite.modules.rms.wrapper;

import java.util.List;


public class SaveProgressPrivilegesWrapper {
    private String postId;
    private List<ProgressPostPrivilegeWrapper> privileges;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public List<ProgressPostPrivilegeWrapper> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<ProgressPostPrivilegeWrapper> privileges) {
        this.privileges = privileges;
    }
}
