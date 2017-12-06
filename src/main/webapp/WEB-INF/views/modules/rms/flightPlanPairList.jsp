<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>航班动态配对管理</title>
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
		<li class="active"><a href="${ctx}/rms/flightPlanPair/">航班动态配对列表</a></li>
		<shiro:hasPermission name="rms:flightPlanPair:edit"><li><a href="${ctx}/rms/flightPlanPair/form">航班动态配对添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="flightPlanPair" action="${ctx}/rms/flightPlanPair/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="rms:flightPlanPair:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="flightPlanPair">
			<tr>
				<td><a href="${ctx}/rms/flightPlanPair/form?id=${flightPlanPair.id}">
					<fmt:formatDate value="${flightPlanPair.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${flightPlanPair.remarks}
				</td>
				<shiro:hasPermission name="rms:flightPlanPair:edit"><td>
    				<a href="${ctx}/rms/flightPlanPair/form?id=${flightPlanPair.id}">修改</a>
					<a href="${ctx}/rms/flightPlanPair/delete?id=${flightPlanPair.id}" onclick="return confirmx('确认要删除该航班动态配对吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>