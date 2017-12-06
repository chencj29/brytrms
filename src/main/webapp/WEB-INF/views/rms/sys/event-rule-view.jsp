<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>规则列表</title>
</head>
<body>
<input id="configId" type="hidden" value="${configId }"/>
<div class="easyui-layout" id="ruleMainLayout" data-options="fit:true">
    <div data-options="region:'center'">
        <div class="easyui-layout" data-options="fit:true" id="ruleCenterLayout">
            <div data-options="region:'center'">
                <table class="easyui-datagrid" title="规则列表" id="ruleDataGrid">
                    <thead>
                    <tr>
                        <th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
                        <th field="fieldName" width="150" align="center" sortable="true">字段名称</th>
                        <th field="fieldCode" width="150" align="center" sortable="true">字段编码</th>
                    </tr>
                    </thead>
                </table>
            </div>

            <div data-options="region:'south'">
                <form action="${ctx}/rms/monitor/item-save" id="ruleModifyForm">
                    <input type="hidden" name="id">
                    <table width="100%" class="formtable">
                        <tr>
                            <td>监控字段：</td>
                            <td>
                                <select id="selField" class="easyui-combobox" style="width: 300px; height:50px;" name="fields"
                                        multiple="true" multiline="true" data-options="editable:false">
                                    <c:forEach items="${fields}" var="field">
                                        <option value="${field.value}">${field.text}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </table>
                    <div style="text-align: center; margin-top:15px; margin-right:15px;">
                        <a class="easyui-linkbutton" id="ruleBtnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        var configId = $("#configId").val(), monitorClasses = $utils._loadFragments({url: './monitor-list', method: 'post', dataType: 'json'}),
                warnRemindConfig = $utils._loadFragments({url: './get?id=' + configId, method: 'get', dataType: 'json'}),
                comboData = [];

        var ruleDataGrid = $("#ruleDataGrid").datagrid({
            rownumbers: true, url: "./item-list?configId=" + configId, method: 'get',
            fit: ((typeof(isFit) != 'undefined' && isFit == false) ? false : true),
            fitColumns: ((typeof(isFitColumns) != 'undefined' && isFitColumns == false ) ? false : true),
            idField: "id", singleSelect: true, showFooter: true, remoteSort: false, multiSort: false,
            onLoadSuccess: function (data) {
                //数据回填
                $("#selField").combobox("setValues", _.pluck(data.rows, "fieldCode"));
            }
        });

        $("#ruleBtnSave").click(function () {
            var codes = $("#selField").combobox("getValues"), names = $("#selField").combobox("getText");
            if (_.isEmpty(codes)) {
                $.messager.show({title: "提示", msg: "请选择监控字段", timeout: 5000, showType: 'slide'});
                return false;
            }

            $.post("./item-save", {
                names: codes, values: names.split(","), configId: warnRemindConfig.id,
                class: warnRemindConfig.monitorClass, configType: warnRemindConfig.configType
            }, function (data) {
                if (data && data.code == 1) {
                    ruleDataGrid.datagrid('reload');
                    $.messager.show({title: "提示", msg: "添加成功！", timeout: 5000, showType: 'slide'});
                } else $.messager.show({title: "提示", msg: '添加失败：' + data.message, timeout: 5000, showType: 'slide'});

            });
        });
    });
</script>
</body>