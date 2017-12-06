/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.rms.service.VipplanService;

import java.util.Date;

/**
 * VIP计划表Entity
 *
 * @author wjp
 * @version 2016-08-03
 */

@Monitor(desc = "VIP计划表信息", tableName = "rms_vipplan", service = VipplanService.class, socketNS = "/rms/vipplan")
public class Vipplan extends DataEntity<Vipplan> {

    private static final long serialVersionUID = 1L;
    @Label("进出港")
    @MonitorField(desc = "进出港")
    private String inout;        // 进出港
    @Label("计划日期")
    @MonitorField(desc = "计划日期")
    private Date plandate;        // 计划日期
    @Label("航空公司")
    @MonitorField(desc = "航空公司")
    private String aircorp;        // 航空公司
    @Label("航班号")
    @MonitorField(desc = "航班号")
    private String fltno;        // 航班号
    @Label("地名")
    @MonitorField(desc = "地名")
    private String arrp;        // 地名
    @Label("团队编号")
    @MonitorField(desc = "团队编号")
    private String groupid;        // 团队编号
    @Label("VIP姓名")
    @MonitorField(desc = "VIP姓名")
    private String vipname;        // VIP姓名
    @Label("VIP单位")
    @MonitorField(desc = "VIP单位")
    private String vipcorp;        // VIP单位
    @Label("VIP职务")
    @MonitorField(desc = "VIP职务")
    private String viprank;        // VIP职务
    @Label("证件号码")
    @MonitorField(desc = "证件号码")
    private String vipid;        // 证件号码
    @Label("籍贯")
    @MonitorField(desc = "籍贯")
    private String orin;        // 籍贯
    @Label("车牌号")
    @MonitorField(desc = "车牌号")
    private String carid;        // 车牌号
    @Label("VIP性别")
    @MonitorField(desc = "VIP性别")
    private String vipsex;        // VIP性别
    @Label("地址")
    @MonitorField(desc = "地址")
    private String addr;        // 地址
    @Label("护照")
    @MonitorField(desc = "护照")
    private String passport;        // 护照
    @Label("家庭住址")
    @MonitorField(desc = "家庭住址")
    private String familyaddr;        // 家庭住址
    @Label("VIP外出事由")
    @MonitorField(desc = "VIP外出事由")
    private String rsntosz;        // VIP外出事由
    @Label("人数")
    @MonitorField(desc = "人数")
    private Long togethers;        // 人数
    @Label("是否免检")
    @MonitorField(desc = "是否免检")
    private String needcheck;        // 是否免检
    @Label("是否礼遇")
    @MonitorField(desc = "是否礼遇")
    private String isvip;        // 是否礼遇
    @Label("接待位置")
    @MonitorField(desc = "接待位置")
    private String receplace;        // 接待位置
    @Label("厅房安排")
    @MonitorField(desc = "厅房安排")
    private String hallno;        // 厅房安排
    @Label("迎送领导")
    @MonitorField(desc = "迎送领导")
    private String meetlead;        // 迎送领导
    @Label("信息来源")
    @MonitorField(desc = "信息来源")
    private String infofrom;        // 信息来源
    @Label("值班人")
    @MonitorField(desc = "值班人")
    private String workman;        // 值班人
    @Label("负责人")
    @MonitorField(desc = "负责人")
    private String principal;        // 负责人
    @Label("审核人")
    @MonitorField(desc = "审核人")
    private String auditer;        // 审核人
    @Label("传真")
    @MonitorField(desc = "传真")
    private String fax;        // 传真
    @Label("电话1")
    @MonitorField(desc = "电话1")
    private String tele1;        // 电话1
    @Label("电话2")
    @MonitorField(desc = "电话2")
    private String tele2;        // 电话2
    @Label("接待单位")
    @MonitorField(desc = "接待单位")
    private String meetcorp;        // 接待单位
    @Label("特殊要求")
    @MonitorField(desc = "特殊要求")
    private String esprequire;        // 特殊要求
    @Label("备注")
    @MonitorField(desc = "备注")
    private String note;        // 备注
    @Label("服务提供者")
    @MonitorField(desc = "服务提供者")
    private String providerid;        // 服务提供者

    private String[] _ids;

    public String[] get_ids() {
        return _ids;
    }

    public void set_ids(String[] _ids) {
        this._ids = _ids;
    }

    public Vipplan() {
        super();
    }

    public Vipplan(String id) {
        super(id);
    }

    public String getInout() {
        return inout;
    }

    public void setInout(String inout) {
        this.inout = inout;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8:00")
    public Date getPlandate() {
        return plandate;
    }

    public void setPlandate(Date plandate) {
        this.plandate = plandate;
    }

    public String getAircorp() {
        return aircorp;
    }

    public void setAircorp(String aircorp) {
        this.aircorp = aircorp;
    }

    public String getFltno() {
        return fltno;
    }

    public void setFltno(String fltno) {
        this.fltno = fltno;
    }

    public String getArrp() {
        return arrp;
    }

    public void setArrp(String arrp) {
        this.arrp = arrp;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getVipname() {
        return vipname;
    }

    public void setVipname(String vipname) {
        this.vipname = vipname;
    }

    public String getVipcorp() {
        return vipcorp;
    }

    public void setVipcorp(String vipcorp) {
        this.vipcorp = vipcorp;
    }

    public String getViprank() {
        return viprank;
    }

    public void setViprank(String viprank) {
        this.viprank = viprank;
    }

    public String getVipid() {
        return vipid;
    }

    public void setVipid(String vipid) {
        this.vipid = vipid;
    }

    public String getOrin() {
        return orin;
    }

    public void setOrin(String orin) {
        this.orin = orin;
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public String getVipsex() {
        return vipsex;
    }

    public void setVipsex(String vipsex) {
        this.vipsex = vipsex;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getFamilyaddr() {
        return familyaddr;
    }

    public void setFamilyaddr(String familyaddr) {
        this.familyaddr = familyaddr;
    }

    public String getRsntosz() {
        return rsntosz;
    }

    public void setRsntosz(String rsntosz) {
        this.rsntosz = rsntosz;
    }

    public Long getTogethers() {
        return togethers;
    }

    public void setTogethers(Long togethers) {
        this.togethers = togethers;
    }

    public String getNeedcheck() {
        return needcheck;
    }

    public void setNeedcheck(String needcheck) {
        this.needcheck = needcheck;
    }

    public String getIsvip() {
        return isvip;
    }

    public void setIsvip(String isvip) {
        this.isvip = isvip;
    }

    public String getReceplace() {
        return receplace;
    }

    public void setReceplace(String receplace) {
        this.receplace = receplace;
    }

    public String getHallno() {
        return hallno;
    }

    public void setHallno(String hallno) {
        this.hallno = hallno;
    }

    public String getMeetlead() {
        return meetlead;
    }

    public void setMeetlead(String meetlead) {
        this.meetlead = meetlead;
    }

    public String getInfofrom() {
        return infofrom;
    }

    public void setInfofrom(String infofrom) {
        this.infofrom = infofrom;
    }

    public String getWorkman() {
        return workman;
    }

    public void setWorkman(String workman) {
        this.workman = workman;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getAuditer() {
        return auditer;
    }

    public void setAuditer(String auditer) {
        this.auditer = auditer;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTele1() {
        return tele1;
    }

    public void setTele1(String tele1) {
        this.tele1 = tele1;
    }

    public String getTele2() {
        return tele2;
    }

    public void setTele2(String tele2) {
        this.tele2 = tele2;
    }

    public String getMeetcorp() {
        return meetcorp;
    }

    public void setMeetcorp(String meetcorp) {
        this.meetcorp = meetcorp;
    }

    public String getEsprequire() {
        return esprequire;
    }

    public void setEsprequire(String esprequire) {
        this.esprequire = esprequire;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProviderid() {
        return providerid;
    }

    public void setProviderid(String providerid) {
        this.providerid = providerid;
    }

}