<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机位占用信息历史管理</title>
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
		<li class="active"><a href="${ctx}/rms/rmsGateOccupyingInfoHi/">机位占用信息历史列表</a></li>
		<shiro:hasPermission name="rms:rmsGateOccupyingInfoHi:edit"><li><a href="${ctx}/rms/rmsGateOccupyingInfoHi/form">机位占用信息历史添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="rmsGateOccupyingInfoHi" action="${ctx}/rms/rmsGateOccupyingInfoHi/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="rms:rmsGateOccupyingInfoHi:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="rmsGateOccupyingInfoHi">
			<tr>
				<td><a href="${ctx}/rms/rmsGateOccupyingInfoHi/form?id=${rmsGateOccupyingInfoHi.id}">
					<fmt:formatDate value="${rmsGateOccupyingInfoHi.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${rmsGateOccupyingInfoHi.remarks}
				</td>
				<shiro:hasPermission name="rms:rmsGateOccupyingInfoHi:edit"><td>
    				<a href="${ctx}/rms/rmsGateOccupyingInfoHi/form?id=${rmsGateOccupyingInfoHi.id}">修改</a>
					<a href="${ctx}/rms/rmsGateOccupyingInfoHi/delete?id=${rmsGateOccupyingInfoHi.id}" onclick="return confirmx('确认要删除该机位占用信息历史吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>