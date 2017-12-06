<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保障类型保障过程关联管理</title>
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
		<li class="active"><a href="${ctx}/rms/safeguardTypeToProcess/">保障类型保障过程关联列表</a></li>
		<shiro:hasPermission name="rms:safeguardTypeToProcess:edit"><li><a href="${ctx}/rms/safeguardTypeToProcess/form">保障类型保障过程关联添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="safeguardTypeToProcess" action="${ctx}/rms/safeguardTypeToProcess/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="rms:safeguardTypeToProcess:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="safeguardTypeToProcess">
			<tr>
				<td><a href="${ctx}/rms/safeguardTypeToProcess/form?id=${safeguardTypeToProcess.id}">
					<fmt:formatDate value="${safeguardTypeToProcess.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${safeguardTypeToProcess.remarks}
				</td>
				<shiro:hasPermission name="rms:safeguardTypeToProcess:edit"><td>
    				<a href="${ctx}/rms/safeguardTypeToProcess/form?id=${safeguardTypeToProcess.id}">修改</a>
					<a href="${ctx}/rms/safeguardTypeToProcess/delete?id=${safeguardTypeToProcess.id}" onclick="return confirmx('确认要删除该保障类型保障过程关联吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>