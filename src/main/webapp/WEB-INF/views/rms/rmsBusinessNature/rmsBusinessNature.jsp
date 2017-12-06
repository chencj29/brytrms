<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
	<title>岗位业务性质</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'west',split:true,border:false" title="部门岗位" style="width:150px;">
		<ul class="easyui-tree" id="tree"></ul>
	</div>
	<div data-options="region:'center', border:false">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false">
				<table class="easyui-datagrid" title="岗位业务性质" id="datagrid" sortName="postId" sortOrder="asc">
					<thead>
					<tr>
						<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
						<th field="postId" width="100" hidden="true" align="center" sortable="true">岗位ID</th>
						<th field="postName" width="100" align="center" sortable="true">岗位名称</th>
						<th field="bussinessNatureName" width="100" align="center" sortable="true">业务性质名称</th>
						<th field="comments" width="100" align="center" sortable="true">备注</th>
					</tr>
					</thead>
				</table>
			</div>

			<div data-options="region:'south',border:false" style="height:180px;">
				<form action="${ctx}/rms/rmsBusinessNature/save" id="modifyForm">
					<input type="hidden" name="id">
					<table width="100%" class="formtable">
						<tr>
							<td>岗位ID：</td>
							<td>
								<select class="easyui-combotree easyui-validatebox" id="postId" name="postId"
								        style="width:130px;"
								        data-options="">
								</select>
								<input type="hidden" id="postName" name="postName">
							</td>
							<td>业务性质名称：</td>
							<td>
								<input type="text" name="bussinessNatureName" class="easyui-textbox easyui-validatebox"
								       data-options=""
								>
							</td>
						</tr>
						<tr>
							<td>备注：</td>
							<td colspan="3">
								<input type="text" name="comments" data-options="multiline:true" class="easyui-textbox easyui-validatebox" style="height: 50px;width: 300px;" data-options="">
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
	var moduleContext = "rmsBusinessNature";
	/**
	 * 过滤列声明接口(必须，可空)
	 */
	var specialFilteringColumnDefinition = [];
	<shiro:hasPermission name="rms:rmsBusinessNature:edit">flagDgEdit = true;
	</shiro:hasPermission>
	<shiro:hasPermission name="rms:rmsBusinessNature:insert">flagDgInsert = true;
	</shiro:hasPermission>
	<shiro:hasPermission name="rms:rmsBusinessNature:del">flagDgDel = true;
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

		//拿到岗位数据
		$("#postId").combotree({
			url: ctx + '/sys/office/treeNode?type=2',
			required: true,
			//选择树节点触发事件
			onSelect: function (node) {
				$("#postId").val(node.id);
				$("#modifyForm input[name=post]").val(node.text);
				//返回树对象
				var tree = $(this).tree;
				//选中的节点是否为叶子节点,如果不是叶子节点,清除选中
				var isLeaf = tree('isLeaf', node.target);
				if (!isLeaf) {
					//清除选中
					$('#postId').combotree('clear');
					$.messager.slide('请选择叶子节点');
				}

				$('#postName').val(node.text);
			}
		});
	});

</script>

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>
</body>