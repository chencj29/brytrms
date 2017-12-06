<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
	<title>工作人员排班</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'west',split:true,border:true" title="部门岗位" style="width:150px;">
		<ul class="easyui-tree" id="tree"></ul>
	</div>
	<div data-options="region:'center', border:false">
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'center'">
				<table class="easyui-datagrid" title="工作人员排班" id="datagrid" sortName="deptName" sortOrder="asc">
					<thead>
					<tr>
						<%--<th field="id" width="100" hidden="true" sortable="false" order="false">id</th>--%>
						<th field="deptName" width="60" align="center" sortable="true">部门名字</th>
						<th field="postName" width="60" align="center" sortable="true">岗位名称</th>
						<%--<th field="groupId" width="100" align="center" sortable="true">班组ID</th>--%>
						<th field="groupName" width="40" align="center" sortable="true">班组名称</th>
						<th field="jobNum" width="60" align="center" sortable="true">工号</th>
						<th field="empName" width="60" align="center" sortable="true">工作人员名称</th>
						<th field="natureName" width="60" align="center" sortable="true">业务性质名称</th>
						<th field="dutyStartTime" width="100" align="center" sortable="true">排班开始时间</th>
						<th field="dutyEndTime" width="100" align="center" sortable="true">排班结束时间</th>
						<th field="workingType" width="60" align="center" sortable="true">工作地点类型</th>
						<th field="workingArea" width="100" align="center" sortable="true">工作地点</th>
						<th field="actualStartTime" width="100" align="center" sortable="true">实际开始时间</th>
						<th field="actualEndTime" width="100" align="center" sortable="true">实际结束时间</th>
						<%--<th field="id" width="100" data-options="formatter:formatterModify">操作</th>--%>
					</tr>
					</thead>
				</table>
			</div>

			<div data-options="region:'south'">
				<form action="${ctx}/rms/empScheduling/save" id="modifyForm">
					<input type="hidden" name="id">
					<table width="100%" class="formtable">
						<tr>
							<td>部门名字：</td>
							<td>
								<input type="text" id="deptId" name="deptId" class="easyui-combotree easyui-validatebox"
								       data-options=""
								>
							</td>
							<td>岗位名称：</td>
							<td>
								<input type="text" id="postId" name="postId" class="easyui-combotree easyui-validatebox"
								       data-options=""
								>
							</td>
							<td>班组名称：</td>
							<td>
								<input type="text" id="groupId" name="groupId" class="easyui-combobox easyui-validatebox"
								       data-options=""
								>
							</td>
						</tr>
						<tr>
							<td>工号：</td>
							<td>
								<input type="text" name="jobNum" class="easyui-textbox easyui-validatebox"
								       data-options=""
								>
							</td>
							<td>工作人员名称：</td>
							<td>
								<input type="text" name="empName" class="easyui-textbox easyui-validatebox"
								       data-options=""
								>
							</td>

							<td>业务性质名称：</td>
							<td>
								<input type="text" name="natureName" class="easyui-textbox easyui-validatebox"
								       data-options=""
								>
							</td>
						</tr>
						<tr>
							<td>排班开始时间：</td>
							<td>
								<input name="dutyStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								       value="<fmt:formatDate value="${empScheduling.dutyStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</td>
							<td>排班结束时间：</td>
							<td>
								<input name="dutyEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								       value="<fmt:formatDate value="${empScheduling.dutyEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</td>
						</tr>
						<tr>
							<td>实际开始时间：</td>
							<td>
								<input name="actualStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								       value="<fmt:formatDate value="${empScheduling.actualStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</td>
							<td>实际结束时间：</td>
							<td>
								<input name="actualEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								       value="<fmt:formatDate value="${empScheduling.actualEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</td>
						</tr>
						<tr>
							<td>工作地点类型：</td>
							<td>
								<select class="easyui-combobox" id="working_type_form" name="workingTypeId" style="width:75%;" data-options="">
									<option value="CHECKIN">值机柜台</option>
									<option value="BOARDING_GATE">登机口</option>
									<option value="DUTY_DOOR">到岗门</option>
									<option value="CAROUSEL">行李转盘</option>
									<option value="SEATS">机位</option>
									<option value="CHUTE">滑槽</option>
									<option value="CHECKPOINT">安检口</option>
									<option value="TERMINAL">候机厅</option>
								</select>
							</td>
							<td>工作地点：</td>
							<td>
								<select class="easyui-combobox" id="working_area_form" name="workingAreaId" style="width:75%;height:56px;" data-options="textField: 'name',valueField: 'id',multiple:true, multiline:true"></select>
								<%--<input type="hidden" name="workingAreaId" value="${empScheduling.workingAreaId}">--%>
							</td>
							<td>其他工作地点：</td>
							<td>
								<input type="text" class="easyui-textbox" name="workingAreaId" value="${empScheduling.otherWorkingArea}">
							</td>
						</tr>
					</table>
					<div style="text-align: center; margin-top:15px; margin-right:15px;">
						<a class="easyui-linkbutton" id="btnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
					</div>
				</form>
			</div>
			<div id="scheduling_dialog" class="easyui-dialog" title="自动排班" data-options="iconCls:'icon-man',closed:true" style="width:500px;height:300px;">
				<div id="scheduling_panel" class="easyui-panel" fit="true" style="width: 100%;text-align:center;padding:5px" border="false">
					<form id="scheduling_form" method="post" fit="true">
						<table cellpadding="3" style="width: 100%">
							<tr>
								<td>工作地点类别:</td>
								<td>
									<select class="easyui-combobox" id="working_type" name="workingType" style="width:85%;height:26px;" data-options="required:true">
										<option value="CHECKIN">值机柜台</option>
										<option value="BOARDING_GATE">登机口</option>
										<option value="DUTY_DOOR">到岗门</option>
										<option value="CAROUSEL">行李转盘</option>
										<option value="SEATS">机位</option>
										<option value="CHUTE">滑槽</option>
										<option value="CHECKPOINT">安检口</option>
										<option value="TERMINAL">候机厅</option>
									</select>
								</td>
							</tr>
							<tr>
								<td>工作地点</td>
								<td>
									<select class="easyui-combobox" id="working_area" name="workingArea" style="width:85%;height:56px;" data-options="textField: 'name',valueField: 'id',multiple:true, multiline:true,required:true"></select>
								</td>
							</tr>
							<tr>
								<td>其他工作地点</td>
								<td>
									<input type="text" class="easyui-textbox" id="other_working_area" name="otherWorkingArea" style="width: 85%;">
								</td>
							</tr>
							<tr>
								<td>开始时间:</td>
								<td><input class="easyui-datebox" style="width: 85%" type="text" id="startDate" data-options="required:true"/></td>
							</tr>
							<tr>
								<td>结束时间:</td>
								<td><input class="easyui-datebox" style="width: 85%" type="text" id="endDate" data-options="required:true"/></td>
							</tr>
						</table>
						<div style="text-align:center;padding:5px;padding-top: 15px;">
							<a href="javascript:void(0)" class="easyui-linkbutton" onclick="scheduling()">开始排班</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#scheduling_dialog').dialog('close');">取消</a>
						</div>
					</form>
				</div>
			</div>

			<div id="scheduling_history_dialog" class="easyui-dialog" title="按历史数据排班" data-options="iconCls:'icon-undo',closed:true" style="width:500px;height:280px;">
				<div id="scheduling_history_panel" class="easyui-panel" fit="true" style="width: 100%;text-align:center;padding:5px" border="false">
					<form id="scheduling_history_form" method="post" fit="true">
						<table cellpadding="3" style="width: 100%">
							<tr>
								<td>历史数据开始时间:</td>
								<td>
									<input type="text" class="easyui-datebox" name="historyStartTime" style="width: 80%;" data-options="required:true">
								</td>
							</tr>
							<tr>
								<td>历史数据结束时间:</td>
								<td>
									<input type="text" class="easyui-datebox" name="historyEndTime" style="width: 80%;"  data-options="required:true">
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<hr style="border: #F2F7FE 1px solid;">
								</td>
							</tr>
							<tr>
								<td>排班开始时间:</td>
								<td>
									<input type="text" class="easyui-datebox" name="startTime" style="width: 80%;"  data-options="required:true">
								</td>
							</tr>
							<tr>
								<td>排班结束时间:</td>
								<td>
									<input type="text" class="easyui-datebox" name="endTime" style="width: 80%;"  data-options="required:true">
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<small style="color: #e8d497;">排班时间必须是历史时间的整倍数</small>
								</td>
							</tr>
						</table>
						<div style="text-align:center;padding-top: 15px;">
							<a href="javascript:void(0)" class="easyui-linkbutton" onclick="history_scheduling()">开始排班</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#scheduling_history_dialog').dialog('close');">取消</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	var moduleContext = "empScheduling";
	/**
	 * 过滤列声明接口(必须，可空)
	 */
	var specialFilteringColumnDefinition = [];
	<shiro:hasPermission name="rms:empScheduling:edit">flagDgEdit = true;
	</shiro:hasPermission>
	<shiro:hasPermission name="rms:empScheduling:insert">flagDgInsert = true;
	</shiro:hasPermission>
	<shiro:hasPermission name="rms:empScheduling:del">flagDgDel = true;
	</shiro:hasPermission>

	// 准备数据
	function prepareData(node) {
		var postId = node.id;
		var startDate = $('#startDate').datebox('getValue');
		var endDate = $('#endDate').datebox('getValue');
		var workingType = $('#working_type').combobox('getText');
		var workingTypeId = $('#working_type').combobox('getValue');
		var workingArea = $('#working_area').combobox('getText');
		var workingAreaId = $('#working_area').combobox('getValues').toString();
		var otherWorkingArea = $('#other_working_area').textbox('getValue');

		//
		var data = {'postId': postId, 'startDate': startDate, 'endDate': endDate};
		data.workingType = workingType;
		data.workingArea = workingArea;
		data.otherWorkingArea = otherWorkingArea;
		data.workingTypeId = workingTypeId;
		data.workingAreaId = workingAreaId;
		return data;
	}
	function scheduling() {
		var valid = $('#scheduling_form').form('enableValidation').form('validate');
		if (valid) {
			var node = $('#tree').tree('getSelected');
			/*if (!node) {
				//$.messager.alert('提示', '请选择要排班的岗位!');
				$.messager.alert('提示', '请选择要排班的岗位(选择左侧部门岗位)!');
				return;
			}*/
			var data = prepareData(node);

			$.post(ctx + '/rms/empScheduling/scheduling', data, function (data) {
				if (data && data.code == 1) {
					$('#scheduling_dialog').dialog("close");
					datagrid.datagrid("reload");
				} else {
					$.messager.slide(data.message);
				}
			});

		}
	}


	function history_scheduling() {
		var valid = $('#scheduling_history_form').form('enableValidation').form('validate');
		if (valid) {
			var data = $('#scheduling_history_form').serializeObject();

			$.post(ctx + '/rms/empScheduling/schedulingHistory', data, function (data) {
				if (data && data.code == 1) {
					$('#scheduling_history_dialog').dialog("close");
					datagrid.datagrid("reload");
				} else {
					$.messager.slide(data.message);
				}
			});

		}
	}


	function formatterModify(val, row) {
		return "<a href='###' style='text-decoration:underline;margin-left: 15px;'>修改</a><a href='###' style='text-decoration:underline;margin-left: 15px;'>删除</a>";
	}

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
		$("#deptId").combotree({
			panelHeight: 'auto', panelMaxHeight: '200',
			url: ctx + '/sys/office/treeNode?type=2',
			required: true
		});

		//拿到岗位数据
		$("#postId").combotree({
			panelHeight: 'auto', panelMaxHeight: '200',
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
				}
			}
		});

		$('#groupId').combobox({
			panelHeight: 'auto', panelMaxHeight: '200',
			url: ctx + '/rms/rmsDutyGroup/listData?postId=',
			valueField: 'id',
			textField: '_groupName'
		});


		// 工作地点
		$('#working_type').combobox({
			onSelect: function (rec) {
				var val = $(this).combobox('getValue');
				$('#working_area').combobox('clear');
				$('#working_area').combobox('reload', ctx + '/rms/empScheduling/getWorkingArea?typeEnum=' + val);
			}
		});

		$('#working_type').combobox('clear');

		$('#working_type_form').combobox({
			onChange: function (rec) {
				var val = $(this).combobox('getValue');
				$('#working_area_form').combobox('clear');
				$('#working_area_form').combobox('reload', ctx + '/rms/empScheduling/getWorkingArea?typeEnum=' + val);
			}
		});

	});

</script>

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.Scheduling.js"></script>
</body>