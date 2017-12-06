<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>数据字典管理</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="数据字典管理" id="datagrid" sortOrder="asc">
            <thead>
            <tr>
                <th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
                <th field="tableName" width="100" align="center" sortable="true">表名</th>
                <th field="type" width="100" align="center" sortable="true">类型</th>
                <th field="description" width="100" align="center" sortable="true">描述</th>
                <th field="value" width="100" align="center" sortable="true">字段值</th>
                <th field="label" width="100" align="center" sortable="true">字段说明</th>
                <th field="sort" width="100" align="center" sortable="true">排序</th>
            </tr>
            </thead>
        </table>
    </div>

    <div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/sysdict/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>表名：</td>
                    <td>
                        <input type="text" name="tableName" class="easyui-textbox easyui-validatebox"
                               data-options=""
                        >
                    </td>
                    <td>类型：</td>
                    <td>
                        <input type="text" name="type" class="easyui-textbox easyui-validatebox"
                               data-options=""
                        >
                    </td>
                    <td>描述：</td>
                    <td>
                        <input type="text" name="description" class="easyui-textbox easyui-validatebox"
                               data-options=""
                        >
                    </td>
                </tr>
                <tr>
                    <td>字段值：</td>
                    <td>
                        <input type="text" name="value" class="easyui-textbox easyui-validatebox"
                               data-options=""
                        >
                    </td>
                    <td>字段说明：</td>
                    <td>
                    <input type="text" name="label" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    >
                    </td>
                    <td>排序：</td>
                    <td>
                        <input type="text" name="sort" class="easyui-textbox easyui-numberspinner"
                               data-options=""
                        >
                    </td>
                </tr>
            </table>
            <div style="text-align: center; margin-top:15px; margin-right:15px;">
                <a class="easyui-linkbutton" id="btnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
            </div>
        </form>

    </div>
</div>

<script>
    var moduleContext = "sysdict";
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="sysdict:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="sysdict:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="sysdict:del">flagDgDel=true;</shiro:hasPermission>
    flagValidate=true;


    /**
     * form: crud表单对象
     * datagrid: 数据表格对象
     */
    var form, datagrid,toolbarDate=[] ;//按钮数据

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
            var h = (c.layout('panel', 'south').outerHeight() - ($(window).height() - $('#btnSave').offset().top) + 38);
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
                }
            });
            $("#btnSave").linkbutton('enable');
        }

        //添加删除铵钮；
        if(flagDgDel){
            toolbarDate.push({
                text: "删除",iconCls: "icon-remove",
                handler: function () {
                    var selected = datagrid.datagrid("getSelected");
                    if (!selected) {
                        $.messager.alert("提示", "请选择一条数据再进行操作！");
                        return false;
                    }

                    $.messager.confirm("提示", "确定要删除选中的记录吗？", function (r) {
                        if (r) {
                            $.post(ctx + "/" + moduleContext + "/delete", {id: selected.id}, function (data) {
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
                    form.form("load", $utils.ajaxLoadFragments(ctx + "/" + moduleContext + "/get?id=" + row.id));
                }
            });
            $("#btnSave").linkbutton('enable');
        }

        datagrid = $("#datagrid").datagrid({
            rownumbers: true, url: "./list", method: 'get',
            fit: ((typeof(isFit) != 'undefined' && isFit == false) ? false : true),
            fitColumns: ((typeof(isFitColumns) != 'undefined' && isFitColumns == false ) ? false : true),
            idField: "id", singleSelect: true,
            showFooter: true, remoteSort: false, multiSort: false,
            toolbar: toolbarDate
        });


        datagrid.datagrid('enableFilter', specialFilteringColumnDefinition);


        $("#btnSave").click(function () {
            if ($(this).linkbutton('options').disabled == false && $('#modifyForm').form('validate')) {
                $.post(ctx + "/" + moduleContext + "/save", $("#modifyForm").serialize(), function (data) {
                    if (data.code === 1) datagrid.datagrid("reload");
                    $.messager.show({title: "提示", msg: data.message, timeout: 5000, showType: 'slide'});
                });
            }
        });

    });

</script>
</body>