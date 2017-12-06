<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>安检旅客信息管理</title>
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
		<li class="active"><a href="${ctx}/rms/rmsFlightSecurity/">安检旅客信息列表</a></li>
		<shiro:hasPermission name="rms:rmsFlightSecurity:edit"><li><a href="${ctx}/rms/rmsFlightSecurity/form">安检旅客信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="rmsFlightSecurity" action="${ctx}/rms/rmsFlightSecurity/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>旅客姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>旅客姓名</th>
				<shiro:hasPermission name="rms:rmsFlightSecurity:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="rmsFlightSecurity">
			<tr>
				<td><a href="${ctx}/rms/rmsFlightSecurity/form?id=${rmsFlightSecurity.id}">
					${rmsFlightSecurity.name}
				</a></td>
				<shiro:hasPermission name="rms:rmsFlightSecurity:edit"><td>
    				<a href="${ctx}/rms/rmsFlightSecurity/form?id=${rmsFlightSecurity.id}">修改</a>
					<a href="${ctx}/rms/rmsFlightSecurity/delete?id=${rmsFlightSecurity.id}" onclick="return confirmx('确认要删除该安检旅客信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>