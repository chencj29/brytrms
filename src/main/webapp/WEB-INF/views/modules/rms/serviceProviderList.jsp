<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务提供者表管理</title>
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
		<li class="active"><a href="${ctx}/rms/serviceProvider/">服务提供者表列表</a></li>
		<shiro:hasPermission name="rms:serviceProvider:edit"><li><a href="${ctx}/rms/serviceProvider/form">服务提供者表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="serviceProvider" action="${ctx}/rms/serviceProvider/" method="post" class="breadcrumb form-search">
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
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="rms:serviceProvider:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="serviceProvider">
			<tr>
				<td><a href="${ctx}/rms/serviceProvider/form?id=${serviceProvider.id}">
					<fmt:formatDate value="${serviceProvider.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${serviceProvider.remarks}
				</td>
				<shiro:hasPermission name="rms:serviceProvider:edit"><td>
    				<a href="${ctx}/rms/serviceProvider/form?id=${serviceProvider.id}">修改</a>
					<a href="${ctx}/rms/serviceProvider/delete?id=${serviceProvider.id}" onclick="return confirmx('确认要删除该服务提供者表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>