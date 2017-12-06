<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>配载工作人员值班记录管理</title>
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
		<li class="active"><a href="${ctx}/rms/dutyHistory/">配载工作人员值班记录列表</a></li>
		<shiro:hasPermission name="rms:dutyHistory:edit"><li><a href="${ctx}/rms/dutyHistory/form">配载工作人员值班记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="dutyHistory" action="${ctx}/rms/dutyHistory/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="rms:dutyHistory:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dutyHistory">
			<tr>
				<td><a href="${ctx}/rms/dutyHistory/form?id=${dutyHistory.id}">
					${dutyHistory.remarks}
				</a></td>
				<shiro:hasPermission name="rms:dutyHistory:edit"><td>
    				<a href="${ctx}/rms/dutyHistory/form?id=${dutyHistory.id}">修改</a>
					<a href="${ctx}/rms/dutyHistory/delete?id=${dutyHistory.id}" onclick="return confirmx('确认要删除该配载工作人员值班记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>