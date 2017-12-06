/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.bstek.urule.model.Label;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.ams.service.AmsAddressService;
import org.hibernate.validator.constraints.Length;

/**
 * 地名管理Entity
 *
 * @author chrischen
 * @version 2015-12-22
 */
@Monitor(tableName = "ams_address", desc = "地名管理", service = AmsAddressService.class, socketNS = "/rms/address")
public class AmsAddress extends DataEntity<AmsAddress> {

    private static final long serialVersionUID = 1L;
    @TipMainField
    @MonitorField(desc = "中文名称")
    @Label("中文名称")
    private String chName;        // 中文名称
    @MonitorField(desc = "中文简称")
    @Label("中文简称")
    private String chShortname;        // 中文简称
    @MonitorField(desc = "英文名称")
    @Label("英文名称")
    private String enName;        // 英文名称
    @MonitorField(desc = "航管名称")
    @Label("航管名称")
    private String fmName;        // 航管名称
    @MonitorField(desc = "三字码")
    @Label("三字码")
    private String threeCode;        // 三字码
    @MonitorField(desc = "四字码")
    @Label("四字码")
    private String fourCode;        // 四字码
    @MonitorField(desc = "是否外航")
    @Label("是否国外")
    private String foreignornot;        // 是否国外
    @MonitorField(desc = "是否成员机场")
    @Label("是否成员机场")
    private String memberornot;        // 是否成员机场

    @Label("是否国外")
    private String foreignName;   // 是否国外
    @Label("是否成员机场")
    private String memberName;    // 是否成员机场

    public AmsAddress() {
        super();
    }

    public AmsAddress(String id) {
        super(id);
    }

    @Length(min = 0, max = 3, message = "三字码长度必须介于 0 和 3 之间")
    public String getThreeCode() {
        return threeCode;
    }

    public void setThreeCode(String threeCode) {
        this.threeCode = threeCode;
    }

    @Length(min = 0, max = 4, message = "四字码长度必须介于 0 和 4 之间")
    public String getFourCode() {
        return fourCode;
    }

    public void setFourCode(String fourCode) {
        this.fourCode = fourCode;
    }

    @Length(min = 0, max = 50, message = "中文名称长度必须介于 0 和 50 之间")
    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    @Length(min = 0, max = 50, message = "英文名称长度必须介于 0 和 50 之间")
    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    @Length(min = 0, max = 50, message = "航管名称长度必须介于 0 和 50 之间")
    public String getFmName() {
        return fmName;
    }

    public void setFmName(String fmName) {
        this.fmName = fmName;
    }

    @Length(min = 0, max = 50, message = "中文简称长度必须介于 0 和 50 之间")
    public String getChShortname() {
        return chShortname;
    }

    public void setChShortname(String chShortname) {
        this.chShortname = chShortname;
    }

    @Length(min = 0, max = 1, message = "是否国外长度必须介于 0 和 1 之间")
    public String getForeignornot() {
        return foreignornot;
    }

    public void setForeignornot(String foreignornot) {
        this.foreignornot = foreignornot;
    }

    @Length(min = 0, max = 1, message = "是否成员机场长度必须介于 0 和 1 之间")
    public String getMemberornot() {
        return memberornot;
    }

    public void setMemberornot(String memberornot) {
        this.memberornot = memberornot;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {

        this.foreignName = foreignName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

}