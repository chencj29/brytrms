<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资源模拟分配详情管理</title>
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
		<li class="active"><a href="${ctx}/rms/resourceMockDistDetail/">资源模拟分配详情列表</a></li>
		<shiro:hasPermission name="rms:resourceMockDistDetail:edit"><li><a href="${ctx}/rms/resourceMockDistDetail/form">资源模拟分配详情添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="resourceMockDistDetail" action="${ctx}/rms/resourceMockDistDetail/" method="post" class="breadcrumb form-search">
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
				<th>update_date</th>
				<shiro:hasPermission name="rms:resourceMockDistDetail:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="resourceMockDistDetail">
			<tr>
				<td><a href="${ctx}/rms/resourceMockDistDetail/form?id=${resourceMockDistDetail.id}">
					${resourceMockDistDetail.remarks}
				</a></td>
				<td>
					<fmt:formatDate value="${resourceMockDistDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="rms:resourceMockDistDetail:edit"><td>
    				<a href="${ctx}/rms/resourceMockDistDetail/form?id=${resourceMockDistDetail.id}">修改</a>
					<a href="${ctx}/rms/resourceMockDistDetail/delete?id=${resourceMockDistDetail.id}" onclick="return confirmx('确认要删除该资源模拟分配详情吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>