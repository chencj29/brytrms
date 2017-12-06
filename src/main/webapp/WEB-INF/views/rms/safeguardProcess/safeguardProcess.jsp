<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>保障过程管理</title>
    <link rel="stylesheet" type="text/css" href="${ctxAssets}/scripts/spectrum/spectrum.css">
    <script src="${ctxAssets}/scripts/spectrum/spectrum.js"></script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <table class="easyui-datagrid" title="保障过程管理" id="datagrid" sortName="safeguardProcessCode" sortOrder="asc">
            <thead>
            <tr>
				<th field="safeguardProcessCode" width="100" align="center" sortable="true">过程编号</th>
                <th field="safeguardProcessName" width="100" align="center" sortable="true">过程名称</th>
                <th field="safeguardAttrName" width="100" align="center" sortable="true">过程属性</th>
				<th field="processCompoundName" width="100" align="center" sortable="true">复合名称</th>
				<th field="planColor" width="100" align="center" sortable="true">计划显示颜色</th>
				<th field="actualColor" width="100" align="center" sortable="true">实际显示颜色</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south' ,split:true ,border:false" style="height:180px;">
        <form action="${ctx}/rms/safeguardProcess/save" id="modifyForm">
            <input type="hidden" id="id" name="id">

            <table width="100%" class="formtable">
                <tr>
                    <td>过程编号：</td>
                    <td><input type="text" id="safeguardProcessCode" name="safeguardProcessCode" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <td>过程名称：</td>
                    <td><input type="text" id="safeguardProcessName" name="safeguardProcessName" class="easyui-textbox easyui-validatebox"
                               data-options=""
                    ></td>
                    <td>过程属性：</td>
                    <td><input type="text" id="safeguardAttrName" name="safeguardAttrName" class="easyui-textbox easyui-validatebox"
                               data-options=""
                    ></td>
                    <td>复合名称：</td>
                    <td><input type="text" id="processCompoundName" name="processCompoundName" class="easyui-textbox easyui-validatebox"
                               data-options=""
                    ></td>
                </tr>
                <tr>
                    <td>计划显示颜色：</td>
                    <td><input type="text" id="planColor" name="planColor" value="#00F800"></td>
                    <td>实际显示颜色：</td>
                    <td><input type="text" id="actualColor" name="actualColor" value="#FF2600"></td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
            </table>
            <div style="text-align: center; margin-top:15px; margin-right:15px;">
                <a class="easyui-linkbutton" id="btnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
            </div>
        </form>

    </div>
</div>

<script>
    var moduleContext = "safeguardProcess";

    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:safeguardProcess:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:safeguardProcess:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:safeguardProcess:del">flagDgDel=true;</shiro:hasPermission>
    //flagValidate=true; //非空验证开关

    $(function () {
        $("#planColor").spectrum({
            chooseText: "确定",
            cancelText: "取消",
            color: "#00F800",
            change: function(color) {
                $("#planColor").val(color.toHexString());
            }
        });

        $("#actualColor").spectrum({
            chooseText: "确定",
            cancelText: "取消",
            color: "#FF2600",
            change: function(color) {
                $("#actualColor").val(color.toHexString());
            }
        });
    });

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

                    $("#planColor").spectrum("set", "#00F800");
                    $("#planColor").val("#00F800");
                    $("#actualColor").spectrum("set", "#FF2600");
                    $("#actualColor").val("#FF2600");
                }
            });
            $("#btnSave").linkbutton('enable');
        }

        //添加删除铵钮；
        if(flagDgDel){
            toolbarDate.push( {
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
                    form.form("load", $utils.ajaxLoadFragments(ctx + "/rms/" + moduleContext + "/get?id=" + row.id, function(data) {
                        $("#id").val(data.id);

                        $("#safeguardProcessCode").textbox('setValue', data.safeguardProcessCode);
                        $("#safeguardAttrName").textbox('setValue', data.safeguardAttrName);
                        $("#safeguardProcessName").textbox('setValue', data.safeguardProcessName);
                        $("#processCompoundName").textbox('setValue', data.processCompoundName);

                        $("#planColor").val(data.planColor);
                        $("#actualColor").val(data.actualColor);

                        $("#planColor").spectrum("set", data.planColor);
                        $("#actualColor").spectrum("set", data.actualColor);
                    }));
                }
            });
            $("#btnSave").linkbutton('enable');
        }
        dictColumns = $("#datagrid th[formatter=globalFormat]").map(function () {
            return $(this).attr("field");
        });

        datagrid = $("#datagrid").datagrid({
            rownumbers: true, url: "./list", method: 'get',
            fit: true, fitColumns: true, idField: "id", singleSelect: true,
            showFooter: true, remoteSort: false, multiSort: false,
            toolbar: toolbarDate
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
            if ($(this).linkbutton('options').disabled == false && $('#modifyForm').form('validate')){
                //Author fyc
                //此处两个if是避免在没有选择颜色时提交的颜色值是hsv(98,100%,100%)字符串，长度过长，后台验证不通过报错。
                if($("#planColor").val().length>7){
                    $("#planColor").val("#040404");
                }
                if($("#actualColor").val().length>7){
                    $("#actualColor").val("#ff2600");
                }
                $.post(ctx + "/rms/" + moduleContext + "/save", $("#modifyForm").serialize(), function (data) {
                    if (data.code === 1) datagrid.datagrid("reload");
                    $.messager.show({title: "提示", msg: data.message, timeout: 5000, showType: 'slide'});

                    form.form("reset");
                    $("input[name=id]").val("");

                    $("#planColor").spectrum("set", "#00F800");
                    $("#planColor").val("#00F800");
                    $("#actualColor").spectrum("set", "#FF2600");
                    $("#actualColor").val("#FF2600");

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
</script>

<%--<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>--%>
</body>
</html>