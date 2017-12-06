<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
	<title>机位信息查询</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
	<div region="center" style="padding:1px;">
		<table width="100%" id="staffStatisticsList" toolbar="#staffStatisticsListtb" data-options="fitColumns:true">
			<thead>
			<tr>
				<th field="jobNum" width="100">工号</th>
				<th field="empName" width="100">名称</th>
				<th field="deptName" width="100">部门</th>
				<th field="postName" width="100">岗位</th>
				<th field="workingType" width="100">工作类型</th>
				<th field="natureName" width="100">工作性质</th>
				<th field="actualStartTime" width="100">实际开始时间</th>
				<th field="actualEndTime" width="100">实际结束时间</th>
			</tr>
			</thead>
		</table>
		<div id="staffStatisticsListtb" style="padding:3px; height: auto">
			<div name="searchColums" style="display:inline">
				<input id="_sqlbuilder" name="sqlbuilder" type="hidden"/>
                 <span style="display:-moz-inline-box;display:inline-block;">
                    <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; " title="计划日期">岗位：</span>
                    <input class="easyui-combobox" type="text" id="postId" name="post" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
                    <span style="text-align:right;" title="工作人员">工作人员：</span>
                    <input class="easyui-combobox" type="text" id="empId" name="emp" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
                    <span style="text-align:right;" title="开始日期">开始日期：</span>
                     <input class="inuptxt" id="actualStartTime" name="actualStartTime" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
                     <span style="text-align:right;" title="结束日期">结束日期：</span>
                    <input class="inuptxt" id="actualEndTime" name="actualEndTime" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
                 </span>
			</div>
			<div style="height:30px;display:inline" class="datagrid-toolbar">
                <span style="float:right">
                    <a href="#" class="easyui-linkbutton" iconcls="icon-search" id="search" onclick="Listsearch()">查询</a>
                    <a href="#" class="easyui-linkbutton" iconcls="icon-reload" onclick="searchReset('${statisticsName}List')">重置</a>
                    <%--<a href="#" class="easyui-linkbutton" iconcls="icon-20130406125519344_easyicon_net_16" onclick="downloadExcel()">下载</a>--%>
                </span>
			</div>
		</div>
	</div>
</div>

<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
<script>
	$(function() {
        //给时间控件加上样式
        $("#${statisticsName}Listtb").find("input[name='actualStartTime']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {WdatePicker({ dateFmt: 'yyyy-MM-dd' });});
        $("#${statisticsName}Listtb").find("input[name='actualEndTime']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {WdatePicker({ dateFmt: 'yyyy-MM-dd' });});

        //拿到岗位数据
		$("#postId").combotree({
			url: ctx + '/sys/office/treeNode?type=2',
			required: true,
			//选择树节点触发事件
			onSelect: function(node) {
				//返回树对象
				var tree = $(this).tree;
				//选中的节点是否为叶子节点,如果不是叶子节点,清除选中
				var isLeaf = tree('isLeaf', node.target);
				if(!isLeaf) {
					//清除选中
					$('#postId').combotree('clear');
				}

				$("#empId").combobox('clear');
				$("#empId").combobox('reload', ctx + '/rms/rmsEmp/jsonData?postId=' + node.id);
			}
		});

		//
		$("#empId").combobox({
			panelHeight: 'auto',
			panelMaxHeight: '200',
			url: ctx + '/rms/rmsEmp/jsonData',
			valueField: 'id',
			textField: 'empName'
		});

		//
		$('#staffStatisticsList').datagrid({
			url: ctx + '/rms/empScheduling/listStatistics'
		});

	});

	function Listsearch() {
		var postId = $('#postId').combobox('getValue');
		var empName = $('#empId').combobox('getText');
//		var actualStartTime = $('#actualStartTime').datebox('getValue');
//		var actualEndTime = $('#actualEndTime').datebox('getValue');
        var actualStartTime = $('#actualStartTime').val();
        var actualEndTime = $('#actualEndTime').val();
		//
		$('#staffStatisticsList').datagrid('load', {
			postId: postId,
			empName: empName,
			actualStartTime: actualStartTime,
			actualEndTime: actualEndTime
		});
	}

    function EnterPress(e) {
        var e = e || window.event;
        if (e.keyCode == 13) {
            Listsearch();
        }
    }
</script>
<%--<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>--%>
</body>