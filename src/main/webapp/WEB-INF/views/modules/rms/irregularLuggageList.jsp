<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>不正常行李表管理</title>
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
		<li class="active"><a href="${ctx}/rms/irregularLuggage/">不正常行李表列表</a></li>
		<shiro:hasPermission name="rms:irregularLuggage:edit"><li><a href="${ctx}/rms/irregularLuggage/form">不正常行李表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="irregularLuggage" action="${ctx}/rms/irregularLuggage/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="rms:irregularLuggage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="irregularLuggage">
			<tr>
				<td><a href="${ctx}/rms/irregularLuggage/form?id=${irregularLuggage.id}">
					${irregularLuggage.remarks}
				</a></td>
				<shiro:hasPermission name="rms:irregularLuggage:edit"><td>
    				<a href="${ctx}/rms/irregularLuggage/form?id=${irregularLuggage.id}">修改</a>
					<a href="${ctx}/rms/irregularLuggage/delete?id=${irregularLuggage.id}" onclick="return confirmx('确认要删除该不正常行李表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>