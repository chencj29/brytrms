package com.thinkgem.jeesite.modules.rms.wrapper;

import com.thinkgem.jeesite.modules.rms.entity.SafeguardProcess;

import java.util.List;

public class AddProgressWrapper {
    private String pairId;
    private List<SafeguardProcess> infos;
    private String random;

    public String getPairId() {
        return pairId;
    }

    public void setPairId(String pairId) {
        this.pairId = pairId;
    }

    public List<SafeguardProcess> getInfos() {
        return infos;
    }

    public void setInfos(List<SafeguardProcess> infos) {
        this.infos = infos;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public AddProgressWrapper(String pairId, List<SafeguardProcess> infos) {

        this.pairId = pairId;
        this.infos = infos;
    }

    public AddProgressWrapper() {
    }
}