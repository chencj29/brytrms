<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>值机柜台基础信息表(基础专用)</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="值机柜台基础信息表(基础专用)" id="datagrid" sortName="airportTerminalCode" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="airportTerminalCode" width="100" align="center" sortable="true">航站楼编号</th>
				<th field="airportTerminalName" width="100" align="center" sortable="true">航站楼名称</th>
				<th field="checkinCounterNum" width="100" align="center" sortable="true">值机柜台编号</th>
				<th field="checkinCounterNature" width="100" align="center" sortable="true">值机柜台性质</th>
				<th field="checkinCounterTypeCode" width="100" align="center" sortable="true">值机柜台状态</th>
				<th field="checkinCounterTypeName" width="100" align="center" sortable="true">值机柜台状态名称</th>
				<th field="orderIndex" width="100" align="center" sortable="true">order_index</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/checkinCounterBasic/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>航站楼编号：</td>
                    <td>
						<input type="text" name="airportTerminalCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>航站楼名称：</td>
                    <td>
						<input type="text" name="airportTerminalName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>值机柜台编号：</td>
                    <td>
						<input type="text" name="checkinCounterNum" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>值机柜台性质：</td>
                    <td>
						<input type="text" name="checkinCounterNature" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>值机柜台状态：</td>
                    <td>
						<input type="text" name="checkinCounterTypeCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>值机柜台状态名称：</td>
                    <td>
						<input type="text" name="checkinCounterTypeName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>order_index：</td>
                    <td>
						<input type="text" name="orderIndex" class="easyui-textbox easyui-validatebox"
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
    var moduleContext = "checkinCounterBasic";
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:checkinCounterBasic:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:checkinCounterBasic:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:checkinCounterBasic:del">flagDgDel=true;</shiro:hasPermission>

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