<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>损坏行李表管理</title>
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
		<li class="active"><a href="${ctx}/rms/damagedLuggage/">损坏行李表列表</a></li>
		<shiro:hasPermission name="rms:damagedLuggage:edit"><li><a href="${ctx}/rms/damagedLuggage/form">损坏行李表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="damagedLuggage" action="${ctx}/rms/damagedLuggage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="36" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>备注</th>
				<th>姓名</th>
				<shiro:hasPermission name="rms:damagedLuggage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="damagedLuggage">
			<tr>
				<td><a href="${ctx}/rms/damagedLuggage/form?id=${damagedLuggage.id}">
					${damagedLuggage.remarks}
				</a></td>
				<td>
					${damagedLuggage.name}
				</td>
				<shiro:hasPermission name="rms:damagedLuggage:edit"><td>
    				<a href="${ctx}/rms/damagedLuggage/form?id=${damagedLuggage.id}">修改</a>
					<a href="${ctx}/rms/damagedLuggage/delete?id=${damagedLuggage.id}" onclick="return confirmx('确认要删除该损坏行李表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>