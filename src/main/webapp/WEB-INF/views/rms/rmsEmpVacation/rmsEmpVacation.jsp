<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
	<title>工作人员请假</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'west',split:true,border:false" title="部门岗位" style="width:150px;">
		<ul class="easyui-tree" id="tree"></ul>
	</div>
	<div data-options="region:'center', border:false">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center'">
				<table class="easyui-datagrid" title="工作人员请假" id="datagrid" sortName="startTime" sortOrder="asc">
					<thead>
					<tr>
						<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
						<%--<th field="deptId" width="100" align="center" sortable="true">部门ID</th>--%>
						<th field="deptName" width="100" align="center" sortable="true">部门名称</th>
						<%--<th field="postId" width="100" align="center" sortable="true">岗位ID</th>--%>
						<th field="postName" width="100" align="center" sortable="true">岗位名称</th>
						<th field="empName" width="100" align="center" sortable="true">工作人员姓名</th>
						<th field="jobNum" width="100" align="center" sortable="true">工号</th>
						<th field="startTime" width="100" align="center" sortable="true">请假开始时间</th>
						<th field="endTime" width="100" align="center" sortable="true">请假结束时间</th>
						<th field="vacationTpye" width="100" align="center" sortable="true">请假类型</th>
						<th field="vacationReason" width="100" align="center" sortable="true">请假事由</th>
						<th field="comments" width="100" align="center" sortable="true">备注</th>
					</tr>
					</thead>
				</table>
			</div>

			<div data-options="region:'south'" style="height:180px;">
				<form action="${ctx}/rms/rmsEmpVacation/save" id="modifyForm">
					<input type="hidden" name="id">
					<table width="100%" class="formtable">
						<tr>
							<td>工作人员：</td>
							<td>
								<input type="text" id="jobNum" name="jobNum" class="easyui-validatebox">
							</td>
							<td>请假开始时间：</td>
							<td>
								<input name="startTime" id="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
								       value="<fmt:formatDate value="${rmsEmpVacation.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false ,maxDate:'#F{$dp.$D(\'endTime\')}'});"/>
							</td>
							<td>请假结束时间：</td>
							<td>
								<input name="endTime" id="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
								       value="<fmt:formatDate value="${rmsEmpVacation.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:'#F{$dp.$D(\'startTime\')}'});"/>
							</td>
						</tr>
						<tr>
							<td>请假类型：</td>
							<td>
								<input type="text" name="vacationTpye" class="easyui-textbox easyui-validatebox"
								       data-options=""
								>
							</td>
							<td>请假事由：</td>
							<td>
								<input type="text" name="vacationReason" class="easyui-textbox easyui-validatebox"
								       data-options=""
								>
							</td>
							<td>备注：</td>
							<td>
								<input type="text" name="comments" class="easyui-textbox easyui-validatebox"
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
	</div>
</div>

<script>
	var moduleContext = "rmsEmpVacation";
	/**
	 * 过滤列声明接口(必须，可空)
	 */
	var specialFilteringColumnDefinition = [];
	<shiro:hasPermission name="rms:rmsEmpVacation:edit">flagDgEdit = true;
	</shiro:hasPermission>
	<shiro:hasPermission name="rms:rmsEmpVacation:insert">flagDgInsert = true;
	</shiro:hasPermission>
	<shiro:hasPermission name="rms:rmsEmpVacation:del">flagDgDel = true;
	</shiro:hasPermission>

	$(function () {
		$('#tree').tree({
			url: ctx + '/sys/office/treeNode?type=2',
			method: 'get',
			onClick: function (node) {
				if (node.attributes.office.type == '4') {
					// 类型为4的都是岗位,岗位点击后出现岗位下的所有员工
					// 员工只能在岗位下添加
					selectedId = node.id;
					datagrid.datagrid("reload", {"postId": selectedId});
				}
			}
		});

		//拿到部门数据
		$("#jobNum").combobox({
			url: ctx + '/rms/rmsEmp/jsonData',
			valueField: 'jobNum',
			textField: 'empName',
			panelHeight: 'auto',
			onSelect: function (node) {

			}
		});

	});

</script>

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>
</body>