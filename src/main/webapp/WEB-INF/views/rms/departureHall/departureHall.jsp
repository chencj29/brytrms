<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>候机厅基础信息</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="候机厅基础信息" id="datagrid" sortName="orderIndex" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="departureHallNum" width="100" align="center" sortable="true">候机厅编号</th>
				<th field="departureHallStatus" width="100" align="center" formatter="globalFormat" sortable="true">候机厅状态</th>
				<th field="departureHallNature" width="100" align="center" formatter="globalFormat" sortable="true">候机厅性质</th>
				<th field="aircraftTerminalCode" width="100" align="center" sortable="true">航站楼</th>
                <th field="orderIndex" width="100" align="center" sortable="true">排序字段</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:100px;">
        <form action="${ctx}/rms/departureHall/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>候机厅编号：</td>
                    <td>
						<input type="text" name="departureHallNum" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>候机厅状态：</td>
                    <td>
						<input type="radio" name="departureHallStatus" value="1" checked >&nbsp;可用
						<input type="radio" name="departureHallStatus" value="0" >&nbsp;不可用
					</td>
                    <td>候机厅性质：</td>
                    <td>
						<input type="radio" name="departureHallNature" value="1" checked >&nbsp;国内
						<input type="radio" name="departureHallNature" value="2" >&nbsp;国际 <%--
						<input type="radio" name="departureHallNature" value="3" >&nbsp;混合 --%>
					</td>
                    </tr>
                <tr>
                    <td>航站楼：</td>
                    <td>
                        <select class="easyui-validatebox" id="aircraftTerminalCode" name="aircraftTerminalCode" style="width:130px;"
                                data-options="">
                        </select>
					</td>
                    <td>排序字段：</td>
                    <td>
                        <input type="text" name="orderIndex" class="easyui-numberbox">
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
    var moduleContext = "departureHall";
    // 拿到字典Map, key = tableName, value = columnName
           var dict = dictMap([{key: "", value: "nature"},{key: "", value: "yes_no"},{key: "", value: "ed_status"}]),
    // 构建字典与列字段的映射关系
    // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
            mapping = {
            	"@ed_status": ["departureHallStatus"],
            	"@nature":["departureHallNature"]
            };
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:departureHall:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:departureHall:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:departureHall:del">flagDgDel=true;</shiro:hasPermission>

     $(function () {
         //拿到航站楼数据
         $("#aircraftTerminalCode").combobox({panelHeight: 'auto',panelMaxHeight: '200',
             url: ctx + '/rms/airportTerminal/jsonData',
             valueField: 'terminalName',
             textField: 'terminalName'
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