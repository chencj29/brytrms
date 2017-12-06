<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机型参数管理</title>
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
		<li class="active"><a href="${ctx}/rms/aircraftParameters/">机型参数列表</a></li>
		<shiro:hasPermission name="rms:aircraftParameters:edit"><li><a href="${ctx}/rms/aircraftParameters/form">机型参数添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="aircraftParameters" action="${ctx}/rms/aircraftParameters/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="rms:aircraftParameters:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="aircraftParameters">
			<tr>
				<td><a href="${ctx}/rms/aircraftParameters/form?id=${aircraftParameters.id}">
					<fmt:formatDate value="${aircraftParameters.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${aircraftParameters.remarks}
				</td>
				<shiro:hasPermission name="rms:aircraftParameters:edit"><td>
    				<a href="${ctx}/rms/aircraftParameters/form?id=${aircraftParameters.id}">修改</a>
					<a href="${ctx}/rms/aircraftParameters/delete?id=${aircraftParameters.id}" onclick="return confirmx('确认要删除该机型参数吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>