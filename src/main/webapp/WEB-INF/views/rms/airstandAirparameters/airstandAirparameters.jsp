<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>机位与机型对应表</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="机位与机型对应表" id="datagrid" sortName="aircraftStandNum" sortOrder="asc">
            <thead>
            <tr>
                <th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
                <th field="aircraftModel" width="100" align="center" sortable="true">机型</th>
                <th field="aircraftStandNum" width="100" align="center" sortable="true">机位号</th>
            </tr>
            </thead>
        </table>
    </div>

    <div data-options="region:'south'" style="height:100px;">
        <form action="${ctx}/rms/airstandAirparameters/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>机型：</td>
                    <td>
                        <select class="easyui-validatebox" id="aircraftModel" name="aircraftModel" style="width:130px;"
                                data-options="">
                        </select>
                    </td>
                    <td>机位号：</td>
                    <td>
                        <select class="easyui-validatebox" id="aircraftStandNum" name="aircraftStandNum" style="width:130px;"
                                data-options="">
                        </select>
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
    var moduleContext = "airstandAirparameters"/*,
     // 拿到字典Map, key = tableName, value = columnName
     dict = dictMap([{key: "", value: "yes_no"}, {key: "", value: "ed_status"}]),
     // 构建字典与列字段的映射关系
     // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
     mapping = {
     "@yes_no": ["hasSalon", "hasOilpipeline", "available"],
     "@ed_status": ["salonStatus", "oilpipelineStatus"]
     }*/;

    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:airstandAirparameters:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:airstandAirparameters:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:airstandAirparameters:del">flagDgDel=true;</shiro:hasPermission>

    $(function () {

        //拿到机型数据
        $("#aircraftModel").combobox({panelHeight: 'auto',panelMaxHeight: '200',
            url: ctx + '/ams/aircraftParameters/listJson',
            valueField: 'value',
            textField: 'key'
        });

        $("#aircraftStandNum").combobox({panelHeight: 'auto',panelMaxHeight: '200',
            url: ctx + '/rms/aircraftStand/groupByAircraftStand',
            valueField: 'value',
            textField: 'key', multiple: true
        });

    });

    /*
     //自定义验证规则。例如，定义一个 minLength 验证类 验证长度
     $.extend($.fn.validatebox.defaults.rules, {
     minLength: {
     validator: function(value, param){
     return value.length >= param[0];
     },
     message: 'Please enter at least {0} characters.'
     }
     });*/
</script>

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>
</body>
</html>