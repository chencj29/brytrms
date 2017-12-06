<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>保障时长管理</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">

    <div data-options="region:'west',split:true" title="保障类型" style="width:150px;">
        <ul class="easyui-tree" id="tree"></ul>
    </div>

    <div data-options="region:'center',border:false">

        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center' ,split:true ,border:false">
                <table class="easyui-datagrid" title="保障时长管理" id="datagrid" sortName="minSeating" sortOrder="asc">
                    <thead>
                    <tr>
                        <th field="safeguardTypeCode" width="100" align="center" sortable="true">保障类型编号</th>
                        <th field="safeguardTypeName" width="100" align="center" sortable="true">保障类型名称</th>
                        <th field="minSeating" width="100" align="center" sortable="true">最小座位数</th>
                        <th field="maxSeating" width="100" align="center" sortable="true">最大座位数</th>
                        <th field="timeLength" width="100" align="center" sortable="true">保障时长</th>
                    </tr>
                    </thead>
                </table>
            </div>

            <div data-options="region:'south' ,split:true" style="height:180px;">
                <form action="${ctx}/rms/safeguardDuration/save" id="modifyForm">
                    <input type="hidden" name="id">
                    <input type="hidden" name="safeguardTypeId" id="safeguardTypeId">
                    <table width="100%" class="formtable">
                        <tr>
                            <td>保障类型编号：</td>
                            <td><input type="text" id="safeguardTypeCode" name="safeguardTypeCode" readonly="readonly" class="easyui-textbox easyui-validatebox" data-options="">
                            </td>
                            <td>保障类型名称：</td>
                            <td><input type="text" id="safeguardTypeName" name="safeguardTypeName" readonly="readonly" class="easyui-textbox easyui-validatebox" data-options="">
                            </td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>最小座位数：</td>
                            <td><input type="text" name="minSeating" class="easyui-textbox easyui-numberspinner" data-options=""></td>
                            <td>最大座位数：</td>
                            <td><input type="text" name="maxSeating" class="easyui-textbox easyui-numberspinner" data-options=""></td>
                            <td>时长：</td>
                            <td><input type="text" name="timeLength" class="easyui-textbox easyui-numberspinner" data-options=""></td>
                    </table>
                    <div style="text-align: center; margin-top:15px; margin-right:15px;">
                        <a class="easyui-linkbutton" id="btnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
                    </div>
                </form>

            </div>
        </div>
    </div>

</div>

<script>
    var moduleContext = "safeguardDuration";
    /*,
     // 拿到字典Map, key = tableName, value = columnName
     dict = dictMap([{key: "", value: "yes_no"}, {key: "", value: "ed_status"}]),
     // 构建字典与列字段的映射关系
     // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
     mapping = {
     "@yes_no": ["hasSalon", "hasOilpipeline", "available"],
     "@ed_status": ["salonStatus", "oilpipelineStatus"]
     }*/

    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:safeguardDuration:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:safeguardDuration:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:safeguardDuration:del">flagDgDel=true;</shiro:hasPermission>
    flagValidate=true; //非空验证开关

    /**
     * form: crud表单对象
     * datagrid: 数据表格对象
     * dictColumns: 字典字段
     * comboboxFilterDefinetion: 字典类型字段过滤定义 （需传入字段名及JSON枚举值）
     *      例如：[{value: '', text: '全部'}, {value: '0', text: '不可用'}, {value: '1', text: '可用'}]
     */
    var form, datagrid, selectedId, dictColumns, comboboxFilterDefinetion = function (fieldCode, enumValues) {
        return {
            field: fieldCode, type: 'combobox', options: {
                panelHeight: 'auto', data: enumValues, onChange: function (value) {
                    if (value == '')
                        datagrid.datagrid('removeFilterRule', fieldCode);
                    else
                        datagrid.datagrid('addFilterRule', {
                            field: fieldCode, op: ['equal'], value: value
                        });

                    datagrid.datagrid('doFilter');
                }
            }
        }
    },toolbarDate=[] ;//按钮数据
    var flagAutoHight, flagValidate;//编辑框自动适应高度开关，所有非空验证开关
    var flagDgEdit,flagDgDel,flagDgInsert,flagDgImp;  //修改、删除、新增、导入按钮开关

    $(function () {
        form = $("#modifyForm");

        $('#tree').tree({
            url: '${ctx}/rms/safeguardType/getWholeTree',
            method: 'get',
            onClick: function (node) {
                form.form("reset");
                $("input[name=id]").val("");
                if (node.id == 'root' || node.params.obj.type != '2') {
                    return;
                }

                $("#safeguardTypeCode").textbox('setValue', node.params.obj.safeguardTypeCode);
                $("#safeguardTypeId").val(node.params.obj.id);
                $("#safeguardTypeName").textbox('setValue', node.params.obj.safeguardTypeName);
                selectedId = node.params.obj.id;

                datagrid.datagrid("reload", { "safeguardTypeId": selectedId });
            }
        });

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
            var h = (c.layout('panel', 'south').outerHeight() - ($(window).height() - $('#btnSave').offset().top) + 43);
            c.layout('panel', 'south').panel('resize', {height: h});
            c.layout('resize', {height: 'auto'});
        }

        $("#btnSave").linkbutton('disable');
        //添加新增按钮；
        if(flagDgInsert){
            toolbarDate.push({
                text: "新增", iconCls: "icon-add",
                handler: function () {
                    datagrid.datagrid("reload").datagrid("clearSelections");
                    form.form("reset");
                    $("input[name=id]").val("");

                    var node = $('#tree').tree("getSelected");

                    $("#safeguardTypeCode").textbox('setValue', node.params.obj.safeguardTypeCode);
                    $("#safeguardTypeId").val(node.params.obj.id);
                    $("#safeguardTypeName").textbox('setValue', node.params.obj.safeguardTypeName);
                }
            });
            $("#btnSave").linkbutton('enable');
        }

        //添加删除铵钮；
        if(flagDgDel){
            toolbarDate.push({
                text: "删除",
                iconCls: "icon-remove",
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
                                }
                                $.messager.show({title: "提示", msg: data.message, timeout: 5000, showType: 'slide'});
                                // $('#tree').tree("reload");
                                var node = $('#tree').tree("getSelected");

                                $("#safeguardTypeCode").textbox('setValue', node.params.obj.safeguardTypeCode);
                                $("#safeguardTypeId").val(node.params.obj.id);
                                $("#safeguardTypeName").textbox('setValue', node.params.obj.safeguardTypeName);
                            });
                        }
                    });
                }
            });
        }


        //添加导入按钮；
        if(flagDgImp){
            toolbarDate.push('-');
            toolbarDate.push({
                text: "导入",
                iconCls: "icon-tip",
                handler: function () {

                }
            });
        }

        //添加点击编辑事件；
        if(flagDgEdit){
            $("#datagrid").datagrid({
                onSelect: function (index, row) {
                    form.form("load", $utils.ajaxLoadFragments(ctx + "/rms/" + moduleContext + "/get?id=" + row.id));
                }
            });
            $("#btnSave").linkbutton('enable');
        }

        dictColumns = $("#datagrid th[formatter=globalFormat]").map(function () {
            return $(this).attr("field");
        });

        datagrid = $("#datagrid").datagrid({
            rownumbers: true, url: "./list", method: 'get',
            queryParams: {
                "safeguardTypeId": selectedId
            },
            fit: true, fitColumns: true, idField: "id", singleSelect: true,
            showFooter: true, remoteSort: false, multiSort: false,
            toolbar: toolbarDate
        });

        $("#btnSave").click(function () {
            if ($(this).linkbutton('options').disabled == false && $('#modifyForm').form('validate') && $("#safeguardTypeId").val() != null) {
                $.post(ctx + "/rms/" + moduleContext + "/save", $("#modifyForm").serialize(), function (data) {
                    if (data.code === 1) datagrid.datagrid("reload");
                    $.messager.show({title: "提示", msg: data.message, timeout: 5000, showType: 'slide'});

                    form.form("reset");
                    $("input[name=id]").val("");
                    
                    var node = $('#tree').tree("getSelected");

                    $("#safeguardTypeCode").textbox('setValue', node.params.obj.safeguardTypeCode);
                    $("#safeguardTypeId").val(node.params.obj.id);
                    $("#safeguardTypeName").textbox('setValue', node.params.obj.safeguardTypeName);
                });
            } else {

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
</script>

</body>
</html>