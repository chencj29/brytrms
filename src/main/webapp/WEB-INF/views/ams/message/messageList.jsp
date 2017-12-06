<%--
  Created by IntelliJ IDEA.
  User: xiaopo
  Date: 16/2/3
  Time: 上午11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>发送新消息</title>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
</head>
<body>
<table class="easyui-datagrid" id="message-datagrid" fit="true" fitColumns="true"
       data-options="singleSelect:true,collapsible:true,url:'${ctx}/ams/message/listData',method:'get',rownumbers:true,pagination:true">
	<thead>
	<tr>
		<th data-options="field:'send_name',width:80">发送人</th>
		<th data-options="field:'rec_name',width:100">接收人</th>
		<th data-options="field:'post_date',width:80,align:'right'">发送时间</th>
		<th data-options="field:'status',width:80,align:'right',formatter:formatStatus">状态</th>
		<th data-options="field:'id',width:50,formatter:operationFmt">操作</th>
	</tr>
	</thead>
</table>
<div id="dialog-message">
	<div class="easyui-panel" style="width:600px;height: 300px;padding: 30px;">
		<div style="margin-bottom:20px">
			<span>发送时间:</span>
			<span id="post_date"></span>
		</div>
		<div style="margin-bottom:20px">
			<span>发送人:</span>
			<span id="send_name"></span>
		</div>
		<div style="margin-bottom:20px">
			<span>消息:</span>
			<span id="message"></span>
		</div>
	</div>
</div>
<script>
	function operationFmt(val, row, inx) {
		return '<a href="javascript:viewMessage(' + inx + ');">查看</a>';
	}
	function formatStatus(val, row, inx) {
		return val == 0 ? "未读" : "已读";
	}

	function viewMessage(inx) {
		var data = $('#message-datagrid').datagrid("getData");
		var viewData = data.rows[inx];
		// 发送请求修改为已读
		$.get(ctx+'/ams/message/view/'+viewData.id,{},function () {});
		//
		$('#post_date').html(viewData.post_date);
		$('#send_name').html(viewData.send_name);
		$('#message').html(viewData.message);
		$('#dialog-message').dialog({
			title: '查看消息',
			width: 500,
			height: 300,
			closed: false,
			cache: false,
			modal: true
		});
		//
		$("#message-datagrid").datagrid('reload');
	}
</script>
</body>
</html>
