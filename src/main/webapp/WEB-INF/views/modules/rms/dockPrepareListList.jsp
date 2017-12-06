<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>航班预配单信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/rms/dockPrepareList/">航班预配单信息列表</a></li>
		<shiro:hasPermission name="rms:dockPrepareList:edit"><li><a href="${ctx}/rms/dockPrepareList/form">航班预配单信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="dockPrepareList" action="${ctx}/rms/dockPrepareList/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>备注</th>
				<shiro:hasPermission name="rms:dockPrepareList:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dockPrepareList">
			<tr>
				<td><a href="${ctx}/rms/dockPrepareList/form?id=${dockPrepareList.id}">
					${dockPrepareList.remarks}
				</a></td>
				<shiro:hasPermission name="rms:dockPrepareList:edit"><td>
    				<a href="${ctx}/rms/dockPrepareList/form?id=${dockPrepareList.id}">修改</a>
					<a href="${ctx}/rms/dockPrepareList/delete?id=${dockPrepareList.id}" onclick="return confirmx('确认要删除该航班预配单信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>