<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应急救援方案管理</title>
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
		<li class="active"><a href="${ctx}/rms/emergencyPlan/">应急救援方案列表</a></li>
		<shiro:hasPermission name="rms:emergencyPlan:edit"><li><a href="${ctx}/rms/emergencyPlan/form">应急救援方案添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="emergencyPlan" action="${ctx}/rms/emergencyPlan/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="rms:emergencyPlan:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="emergencyPlan">
			<tr>
				<td><a href="${ctx}/rms/emergencyPlan/form?id=${emergencyPlan.id}">
					<fmt:formatDate value="${emergencyPlan.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${emergencyPlan.remarks}
				</td>
				<shiro:hasPermission name="rms:emergencyPlan:edit"><td>
    				<a href="${ctx}/rms/emergencyPlan/form?id=${emergencyPlan.id}">修改</a>
					<a href="${ctx}/rms/emergencyPlan/delete?id=${emergencyPlan.id}" onclick="return confirmx('确认要删除该应急救援方案吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>