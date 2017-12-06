/**
 * form: crud表单对象
 * datagrid: 数据表格对象
 * dictColumns: 字典字段
 * comboboxFilterDefinetion: 字典类型字段过滤定义 （需传入字段名及JSON枚举值）
 *      例如：[{value: '', text: '全部'}, {value: '0', text: '不可用'}, {value: '1', text: '可用'}]
 */
var form, datagrid, dictColumns, comboboxFilterDefinetion = function (fieldCode, enumValues) {
    return {
        field: fieldCode, type: 'combobox', options: {
            panelHeight: 'auto', data: enumValues, onChange: function (value) {
                if (value == '') datagrid.datagrid('removeFilterRule', fieldCode);
                else datagrid.datagrid('addFilterRule', {field: fieldCode, op: ['equal'], value: value});
                datagrid.datagrid('doFilter');
            }
        }
    }
}, toolbarDate = [];//按钮数据
var clearCallFn;

var flagAutoHight, flagValidate;//编辑框自动适应高度开关，所有非空验证开关
var flagDgEdit, flagDgDel, flagDgInsert, flagDgImp, flagRefresh;  //修改、删除、新增、导入、刷新按钮开关
var isFilter;//服务提供者特定的开关
var valiFn;//非空验证关闭时回调验证；
var loadsuccessFn, afterSaveFn;//datagrid的onLoadSuccess方法/保存成功后回调

$(function () {
    form = $("#modifyForm");

    //增加非空验证 wjp 2016-4-5
    if (!flagValidate) {
        $("#modifyForm input:visible").validatebox({
            required: true
        });
    }

    //编辑框自动适应高度
    if (!flagAutoHight) {
        //var c = $('body>div').eq(0);
        var c = $('#datagrid').parents('.easyui-layout').eq(0);
        c.layout('panel', 'south').outerHeight();
        var h = (c.layout('panel', 'south').outerHeight() - ($(window).height() - $('#btnSave').offset().top) + 35);
        if (h > 230) {
            h = 230
        }
        ;
        c.layout('panel', 'south').panel('resize', {height: h});
        c.layout('resize', {height: 'auto'});
    }

    $("#btnSave").linkbutton('disable');
    //添加新增按钮；
    if (flagDgInsert) {
        toolbarDate.push({
            text: "新增", iconCls: "icon-add",
            handler: function () {

                try {
                    preInsertFn();
                } catch (e) {
                }

                datagrid.datagrid("reload").datagrid("clearSelections");
                //form.form("reset");
                form[0].reset();
                $("input[name=id]").val("");
                if (clearCallFn) clearCallFn();
            }
        });
        $("#btnSave").linkbutton('enable');
    }

    //添加删除铵钮；
    if (flagDgDel) {
        toolbarDate.push({
            text: "删除", iconCls: "icon-remove",
            handler: function () {
                var selected = datagrid.datagrid("getSelected");
                if (!selected) {
                    $.messager.alert("提示", "请选择一条数据再进行操作！");
                    return false;
                }

                $.messager.confirm("提示", "确定要删除选中的记录吗？", function (r) {
                    if (r) {
                        $.post(ctx + "/rms/" + moduleContext + "/delete", {id: selected.id}, function (data) {
                            if (data.code === 1) {
                                datagrid.datagrid("reload").datagrid("clearSelections");
                                form.form("reset");
                                $("input[name=id]").val("");

                                try {
                                    afterDelSuccess();
                                } catch (e) {
                                }
                            }
                            $.messager.show({title: "提示", msg: data.message, timeout: 5000, showType: 'slide'});
                        });
                    }
                });
            }
        });
    }

    //添加导入按钮；
    if (flagDgImp) {
        toolbarDate.push('-');
        toolbarDate.push({
            text: "导入",
            iconCls: "icon-tip",
            handler: function () {

            }
        });
    }

    //添加点击编辑事件；
    if (flagDgEdit) {
        $("#datagrid").datagrid({
            onSelect: function (index, row) {
                try {
                    if (preSelectFn) preSelectFn(index, row);
                } catch (e) {
                }
                form.form("load", $utils.ajaxLoadFragments(ctx + "/rms/" + moduleContext + "/get?id=" + row.id));
            }
        });
        $("#btnSave").linkbutton('enable');
    }

    if (flagRefresh) {
        toolbarDate.push({
            text: '刷新数据', iconCls: 'icon-arrow_refresh_small', handler: function () {
                var opt = datagrid.datagrid('options');
                if (opt && opt.sortName) opt.sortName = "";
                datagrid.datagrid('reload');
            }
        })
    }


    dictColumns = $("#datagrid th[formatter=globalFormat]").map(function () {
        return $(this).attr("field");
    });

    datagrid = $("#datagrid").datagrid({
        rownumbers: true, url: "./list", method: 'get',
        fit: ((typeof(isFit) != 'undefined' && isFit == false) ? false : true),
        fitColumns: ((typeof(isFitColumns) != 'undefined' && isFitColumns == false) ? false : true),
        idField: "id", singleSelect: true, remoteFilter: false,
        showFooter: true, remoteSort: false, multiSort: false,
        toolbar: toolbarDate,
        onLoadSuccess: function (data) {
            try {
                if (loadsuccessFn) loadsuccessFn(data);
            } catch (e) {
            }
        }
    });

    //拿到specialFilteringColumnDefinition进行合并操作
    _.each(dictColumns, function (field) {

        var enumValues = [{value: '', text: '全部'}], dictValue = getAllDictValue(field);
        // 遍历可选值，构造datagrid-filter能够识别的JSON对象
        _.each(_.allKeys(dictValue), function (key) {
            enumValues.push({value: _.propertyOf(dictValue)(key), text: _.propertyOf(dictValue)(key)});
        });

        specialFilteringColumnDefinition.push(comboboxFilterDefinetion(field, enumValues));
    });
    datagrid.datagrid('enableFilter', specialFilteringColumnDefinition);


    $("#btnSave").click(function () {
        if ($(this).linkbutton('options').disabled == false && $('#modifyForm').form('validate')) {
            try {
                if (preSaveFn) {
                    var status = preSaveFn();//回调为false继续，为true时跳出保存
                    if (status) return;
                }
            } catch (e) {}
            if (!!isFilter) {//服务提供者
                if ($("#modifyForm [name=isdefault]:checked").val() == 1) {
                    $.messager.show({title: "保存失败", msg: "不能同时存在多个默认服务提供者!", timeout: 5000, showType: 'slide'});
                    return false;
                }
            }
            if (flagValidate) { //关闭非空验证时
                if (!!valiFn) {
                    if (!valiFn()) return false;
                }
            }

            $.post(ctx + "/rms/" + moduleContext + "/save", $("#modifyForm").serialize(), function (data) {
                if (data.code === 1) {
                    if (afterSaveFn) {
                        afterSaveFn(data);
                    } else {
                        datagrid.datagrid("reload");
                    }
                }
                $.messager.show({title: "提示", msg: data.message, timeout: 5000, showType: 'slide'});
            });
        }
    });

});


/***
 * 通用的Easyui字典值转换方法
 * @param value 值
 * @param row 行
 * @param index 索引
 * @param field 字段名
 */
function globalFormat(value, row, index, field) {
    return _.propertyOf(getAllDictValue(field))(value);
}

/**
 * 拿到可用的字典值
 * @param fieldCode 字段名
 */
function getAllDictValue(field) {
    return _.last(_.values(_.pick(dict, _.last(_.keys(_.pick(mapping, function (value, key, object) {
        return _.contains(value, field);
    }))))));
}