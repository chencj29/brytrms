<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
	<title>岗位班组</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center'">
		<table class="easyui-datagrid" title="岗位班组" id="datagrid" sortName="postName" sortOrder="asc">
			<thead>
			<tr>
				<%--<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>--%>
				<%--<th field="postId" width="100" align="center" sortable="true">岗位ID</th>--%>
				<th field="postName" width="100" align="center" sortable="true">岗位名称</th>
				<th field="groupName" width="100" align="center" sortable="true">班组名称</th>
				<th field="startTime" width="100" align="center" sortable="true">开始时间</th>
				<th field="endTime" width="100" align="center" sortable="true">结束时间</th>
				<th field="period" width="100" align="center" sortable="true">持续天数</th>
				<th field="workerCount" width="100" align="center" sortable="true">班组人数</th>
			</tr>
			</thead>
		</table>
	</div>

	<div data-options="region:'south'" style="height:180px;">
		<form action="${ctx}/rms/rmsDutyGroup/save" id="modifyForm">
			<input type="hidden" name="id">
			<table width="100%" class="formtable">
				<tr>
					<%--<td>岗位ID：</td>
					<td>
						<input type="text" name="postId" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>--%>
					<td>岗位名称：</td>
					<td>
						<input type="text" id="postId" name="postId" class="easyui-textbox easyui-validatebox"
						       data-options="">
					</td>
					<td>班组名称：</td>
					<td>
						<input type="text" name="groupName" class="easyui-textbox easyui-validatebox"
						       data-options=""
						>
					</td>
					<td>开始时间：</td>
					<td>
						<input type="text" name="startTime" class="easyui-timespinner easyui-validatebox"
						       data-options="showSeconds:true"
						>
					</td>
				</tr>
				<tr>
					<td>结束时间：</td>
					<td>
						<input type="text" name="endTime" class="easyui-timespinner easyui-validatebox"
						       data-options="showSeconds:true"
						>
					</td>
					<td>持续天数：</td>
					<td>
						<input type="text" name="period" class="easyui-numberspinner easyui-validatebox"
						       data-options="min:0,max:1,value:0"
						>
					</td>
					<td>班组人数：</td>
					<td>
						<input type="text" name="workerCount" class="easyui-numberspinner easyui-validatebox"
						       data-options="min:1,max:100,value:1"
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
	var moduleContext = "rmsDutyGroup";
	/**
	 * 过滤列声明接口(必须，可空)
	 */
	var specialFilteringColumnDefinition = [];
	<shiro:hasPermission name="rms:rmsDutyGroup:edit">flagDgEdit = true;
	</shiro:hasPermission>
	<shiro:hasPermission name="rms:rmsDutyGroup:insert">flagDgInsert = true;
	</shiro:hasPermission>
	<shiro:hasPermission name="rms:rmsDutyGroup:del">flagDgDel = true;
	</shiro:hasPermission>

	$(function () {
		//拿到岗位数据
		$("#postId").combotree({
			url: ctx + '/sys/office/treeNode?type=2',
			required: true,
			//选择树节点触发事件
			onSelect: function (node) {
				//返回树对象
				var tree = $(this).tree;
				//选中的节点是否为叶子节点,如果不是叶子节点,清除选中
				var isLeaf = tree('isLeaf', node.target);
				if (!isLeaf) {
					//清除选中
					$('#postId').combotree('clear');
					$.messager.slide('只能选择叶子节点.');
				}
			}
		});
	});

</script>

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>
</body>