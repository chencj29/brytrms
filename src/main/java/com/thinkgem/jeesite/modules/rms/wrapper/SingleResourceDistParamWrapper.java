package com.thinkgem.jeesite.modules.rms.wrapper;

import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistDetail;
import com.thinkgem.jeesite.modules.rms.entity.ResourceMockDistInfo;

import java.io.Serializable;

/**
 * 单资源手动分配参数包装器
 * Created by Administrator on 2017/3/31.
 */
public class SingleResourceDistParamWrapper implements Serializable {
    String flightDynamicId; // 航班动态ID
    String inte; // 国内航段
    String intl; // 国际航段
    String nature; // 航班属性
    Boolean force = false; // 是否强制分配
    Boolean isOccupied = false; // 是否被占用
    String resourceCode; // 资源号（用于提示）
    String random; // 随机值

    ResourceMockDistDetail resourceMockDistDetail;
    ResourceMockDistInfo resourceMockDistInfo;

    public String getFlightDynamicId() {
        return flightDynamicId;
    }

    public void setFlightDynamicId(String flightDynamicId) {
        this.flightDynamicId = flightDynamicId;
    }

    public String getInte() {
        return inte;
    }

    public void setInte(String inte) {
        this.inte = inte;
    }

    public String getIntl() {
        return intl;
    }

    public void setIntl(String intl) {
        this.intl = intl;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public Boolean getForce() {
        return force;
    }

    public void setForce(Boolean force) {
        this.force = force;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public Boolean getOccupied() {
        return isOccupied;
    }

    public void setOccupied(Boolean occupied) {
        isOccupied = occupied;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public ResourceMockDistDetail getResourceMockDistDetail() {
        return resourceMockDistDetail;
    }

    public void setResourceMockDistDetail(ResourceMockDistDetail resourceMockDistDetail) {
        this.resourceMockDistDetail = resourceMockDistDetail;
    }

    public ResourceMockDistInfo getResourceMockDistInfo() {
        return resourceMockDistInfo;
    }

    public void setResourceMockDistInfo(ResourceMockDistInfo resourceMockDistInfo) {
        this.resourceMockDistInfo = resourceMockDistInfo;
    }
}
