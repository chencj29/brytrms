<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>机位与登机口对应表</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="机位与登机口对应表" id="datagrid" sortName="aircraftStandNum" sortOrder="asc">
            <thead>
            <tr>
                <th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
                <th field="aircraftStandNum" width="100" align="center" sortable="true">机位</th>
                <th field="boardingGateNum" width="100" align="center" sortable="true">登机口(国内)</th>
                <th field="intlBoardingGateNum" width="100" align="center" sortable="true">登机口(国际)</th>
            </tr>
            </thead>
        </table>
    </div>

    <div data-options="region:'south'" style="height:100px;">
        <form action="${ctx}/rms/aircraftBoarding/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>机位：</td>
                    <td>
                        <select class="easyui-validatebox" id="aircraftStandNum" name="aircraftStandNum" style="width:130px;" data-options="required:true">
                        </select>
                    </td>
                    <td>登机口(国内)：</td>
                    <td>
                        <select class="easyui-validatebox" id="boardingGateNum" name="boardingGateNum" style="width:130px;" data-options="multiple:true">
                        </select>
                    </td>
                    <td>登机口(国际)：</td>
                    <td>
                        <select class="easyui-validatebox" id="intlBoardingGateNum" name="intlBoardingGateNum" style="width:130px;" data-options="multiple:true">
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
    var moduleContext = "aircraftBoarding";/*,
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
    <shiro:hasPermission name="rms:aircraftBoarding:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:aircraftBoarding:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:aircraftBoarding:del">flagDgDel=true;</shiro:hasPermission>

    flagValidate = true; //关闭全部必填

    $(function () {

        //拿到机位数据
        $("#aircraftStandNum").combobox({
            panelHeight: 'auto', panelMaxHeight: '200', url: ctx + '/rms/aircraftStand/jsonData',
            valueField: 'aircraftStandNum', textField: 'aircraftStandNum'
        });

        //国内登机口数据
        $("#boardingGateNum").combobox({
            panelHeight: 'auto', panelMaxHeight: '200', url: ctx + '/rms/boardingGate/jsonData?type=inte',
            valueField: 'boardingGateNum', textField: 'boardingGateNum'
        });

        //国际登机口数据
        $("#intlBoardingGateNum").combobox({
            panelHeight: 'auto', panelMaxHeight: '200', url: ctx + '/rms/boardingGate/jsonData?type=intl',
            valueField: 'boardingGateNum', textField: 'boardingGateNum'
        });
    });

    valiFn = function () {
        if(!$("#boardingGateNum").combobox('getValue') && !$("#intlBoardingGateNum").combobox('getValue')) {
            $.messager.show({title: "保存失败", msg: "登机口(国内)、登机口(国际)登机口不能同时为空！", timeout: 5000, showType: 'slide'});
            return false;
        }
        return true;
    }
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