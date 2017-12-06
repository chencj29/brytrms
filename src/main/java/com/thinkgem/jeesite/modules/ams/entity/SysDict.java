/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.entity;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.annotation.TipMainField;
import com.bstek.urule.model.Label;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.ams.service.SysDictService;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 数据字典管理Entity
 *
 * @author xiaopo
 * @version 2015-12-15
 */
@Monitor(desc = "数据字典信息", tableName = "SYS_DICT", service = SysDictService.class, socketNS = "/rms/sysDict")
public class SysDict extends DataEntity<SysDict> {

    private static final long serialVersionUID = 1L;
    @MonitorField(desc = "具体值")
    @Label("具体值")
    private String value;        // 具体值
    @MonitorField(desc = "字段解释说明")
    @Label("字段解释说明")
    private String label;        // 字段解释说明
    @MonitorField(desc = "类型字段名")
    @Label("类型")
    private String type;        // 类型 字段名
    @TipMainField
    @MonitorField(desc = "描述")
    @Label("描述")
    private String description;

    private Integer sort;

    private SysDict parent;
    @MonitorField(desc = "表名")
    @Label("表名")
    private String tableName;        // 表名

    public SysDict() {
        super();
    }

    public SysDict(String tableName, String type, String value) {
        super();
        this.type = type;
        this.tableName = tableName;
        this.value = value;
    }

    public SysDict(String id) {
        super(id);
    }

    @Length(min = 1, max = 100, message = "具体值长度必须介于 1 和 100 之间")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Length(min = 1, max = 100, message = "字段解释说明长度必须介于 1 和 100 之间")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Length(min = 1, max = 200, message = "类型 字段名长度必须介于 1 和 200 之间")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Length(min = 1, max = 100, message = "description长度必须介于 1 和 100 之间")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "sort不能为空")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @JsonBackReference
    public SysDict getParent() {
        return parent;
    }

    public void setParent(SysDict parent) {
        this.parent = parent;
    }

    @Length(min = 0, max = 100, message = "表名长度必须介于 0 和 100 之间")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}