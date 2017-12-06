<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>机位占用信息</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="机位占用信息" id="datagrid" sortName="leave" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="leave" width="100" align="center" sortable="true">中途是否离开</th>
				<th field="leaveTime" width="100" align="center" sortable="true">中途离开开始时间</th>
				<th field="createBy.id" width="100" align="center" sortable="true">创建者</th>
				<th field="createDate" width="100" align="center" sortable="true">创建时间</th>
				<th field="updateBy.id" width="100" align="center" sortable="true">更新者</th>
				<th field="updateDate" width="100" align="center" sortable="true">更新时间</th>
				<th field="remarks" width="100" align="center" sortable="true">备注</th>
				<th field="delFlag" width="100" align="center" sortable="true">删除标记</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/gateOccupyingInfo/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>中途是否离开：</td>
                    <td><input type="text" name="leave" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <td>中途离开开始时间：</td>
                    <td><input type="text" name="leaveTime" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <td>创建者：</td>
                    <td><input type="text" name="createBy.id" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <td>创建时间：</td>
                    <td><input type="text" name="createDate" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                </tr>
                <tr>
                    <td>更新者：</td>
                    <td><input type="text" name="updateBy.id" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <td>更新时间：</td>
                    <td><input type="text" name="updateDate" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <td>备注：</td>
                    <td><input type="text" name="remarks" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <td>删除标记：</td>
                    <td><input type="text" name="delFlag" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                </tr>
            </table>
            <div style="text-align: center; margin-top:15px; margin-right:15px;">
                <a class="easyui-linkbutton" id="btnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
            </div>
        </form>

    </div>
</div>

<script>
    var moduleContext = "gateOccupyingInfo"/*,
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


    $(function () {
    	/*
        //拿到航站楼数据
        $("#airportTerminalCode").combobox({panelHeight: 'auto',panelMaxHeight: '200',
            url: ctx + '/rms/airportTerminal/jsonData',
            valueField: 'terminalName',
            textField: 'terminalName'
        });*/
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