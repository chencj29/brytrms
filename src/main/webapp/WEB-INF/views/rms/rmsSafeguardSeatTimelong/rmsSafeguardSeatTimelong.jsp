<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>保障过程及座位时长</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="保障过程及座位时长" id="datagrid" sortName="safeguardTypeName" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">id</th>
				<th field="safeguardTypeName" width="100" align="center" sortable="true">保障类型名称</th>
				<th field="safeguardProcessCode" width="100" align="center" sortable="true">保障过程编号</th>
				<th field="safeguardProcessName" width="100" align="center" sortable="true">保障过程名称</th>
				<th field="seatNum" width="100" align="center" sortable="true">座位数</th>
				<th field="safeguardProcessFrom" width="100" align="center" sortable="true">相对开始时间</th>
				<th field="safeguardProcessTo" width="100" align="center" sortable="true">相对结束时间</th>
				<th field="safeguardType.Id" width="100" align="center" sortable="true">保障类型ID</th>
				<th field="safeguardProcess.Id" width="100" align="center" sortable="true">保障过程ID</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/rmsSafeguardSeatTimelong/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>保障类型名称：</td>
                    <td>
						<input type="text" name="safeguardTypeName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>保障过程编号：</td>
                    <td>
						<input type="text" name="safeguardProcessCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>保障过程名称：</td>
                    <td>
						<input type="text" name="safeguardProcessName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>座位数：</td>
                    <td>
						<input type="text" name="seatNum" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>相对开始时间：</td>
                    <td>
						<input type="text" name="safeguardProcessFrom" class="easyui-textbox easyui-validatebox"
							
						>
					</td>
                    <td>相对结束时间：</td>
                    <td>
						<input type="text" name="safeguardProcessTo" class="easyui-textbox easyui-validatebox"
							
						>
					</td>
                    <td>保障类型ID：</td>
                    <td>
						<input type="text" name="safeguardType.Id" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>保障过程ID：</td>
                    <td>
						<input type="text" name="safeguardProcess.Id" class="easyui-textbox easyui-validatebox"
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
    var moduleContext = "rmsSafeguardSeatTimelong";
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];


     $(function () {
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