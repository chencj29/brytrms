<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>航空公司联系信息管理</title>
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
		<li class="active"><a href="${ctx}/rms/flightCompany/">航空公司联系信息列表</a></li>
		<shiro:hasPermission name="rms:flightCompany:edit"><li><a href="${ctx}/rms/flightCompany/form">航空公司联系信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="flightCompany" action="${ctx}/rms/flightCompany/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="rms:flightCompany:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="flightCompany">
			<tr>
				<td><a href="${ctx}/rms/flightCompany/form?id=${flightCompany.id}">
					${flightCompany.remarks}
				</a></td>
				<shiro:hasPermission name="rms:flightCompany:edit"><td>
    				<a href="${ctx}/rms/flightCompany/form?id=${flightCompany.id}">修改</a>
					<a href="${ctx}/rms/flightCompany/delete?id=${flightCompany.id}" onclick="return confirmx('确认要删除该航空公司联系信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>