<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>值机柜台员工分配</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="值机柜台员工分配" id="datagrid" sortName="flightDynamic.id" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">id</th>
				<th field="flightDynamic.id" width="100" align="center" sortable="true">dynamic_id</th>
				<th field="checkinStartTime" width="100" align="center" sortable="true">checkin_start_time</th>
				<th field="checkinEndTime" width="100" align="center" sortable="true">checkin_end_time</th>
				<th field="rmsEmp.id" width="100" align="center" sortable="true">emp_id</th>
				<th field="empName" width="100" align="center" sortable="true">emp_name</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/flightDynamicCheckin/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>dynamic_id：</td>
                    <td>
						<input type="text" name="flightDynamic.id" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>checkin_start_time：</td>
                    <td>
						<input name="checkinStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicCheckin.checkinStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>checkin_end_time：</td>
                    <td>
						<input name="checkinEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicCheckin.checkinEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>emp_id：</td>
                    <td>
						<input type="text" name="rmsEmp.id" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>emp_name：</td>
                    <td>
						<input type="text" name="empName" class="easyui-textbox easyui-validatebox"
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
    var moduleContext = "flightDynamicCheckin";
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:flightDynamicCheckin:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:flightDynamicCheckin:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:flightDynamicCheckin:del">flagDgDel=true;</shiro:hasPermission>

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