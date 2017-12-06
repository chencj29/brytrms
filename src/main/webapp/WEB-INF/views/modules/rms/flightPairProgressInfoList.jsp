<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>航班运输保障信息管理</title>
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
		<li class="active"><a href="${ctx}/rms/flightPairProgressInfo/">航班运输保障信息列表</a></li>
		<shiro:hasPermission name="rms:flightPairProgressInfo:edit"><li><a href="${ctx}/rms/flightPairProgressInfo/form">航班运输保障信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="flightPairProgressInfo" action="${ctx}/rms/flightPairProgressInfo/" method="post" class="breadcrumb form-search">
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
				<th>update_date</th>
				<th>remarks</th>
				<shiro:hasPermission name="rms:flightPairProgressInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="flightPairProgressInfo">
			<tr>
				<td><a href="${ctx}/rms/flightPairProgressInfo/form?id=${flightPairProgressInfo.id}">
					<fmt:formatDate value="${flightPairProgressInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${flightPairProgressInfo.remarks}
				</td>
				<shiro:hasPermission name="rms:flightPairProgressInfo:edit"><td>
    				<a href="${ctx}/rms/flightPairProgressInfo/form?id=${flightPairProgressInfo.id}">修改</a>
					<a href="${ctx}/rms/flightPairProgressInfo/delete?id=${flightPairProgressInfo.id}" onclick="return confirmx('确认要删除该航班运输保障信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>