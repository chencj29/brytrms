<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>航空公司子公司管理</title>
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
		<li class="active"><a href="${ctx}/ams/flightCompanySubInfo/">航空公司子公司列表</a></li>
		<shiro:hasPermission name="ams:flightCompanySubInfo:edit"><li><a href="${ctx}/ams/flightCompanySubInfo/form">航空公司子公司添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="flightCompanySubInfo" action="${ctx}/ams/flightCompanySubInfo/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="ams:flightCompanySubInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="flightCompanySubInfo">
			<tr>
				<td><a href="${ctx}/ams/flightCompanySubInfo/form?id=${flightCompanySubInfo.id}">
					<fmt:formatDate value="${flightCompanySubInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${flightCompanySubInfo.remarks}
				</td>
				<shiro:hasPermission name="ams:flightCompanySubInfo:edit"><td>
    				<a href="${ctx}/ams/flightCompanySubInfo/form?id=${flightCompanySubInfo.id}">修改</a>
					<a href="${ctx}/ams/flightCompanySubInfo/delete?id=${flightCompanySubInfo.id}" onclick="return confirmx('确认要删除该航空公司子公司吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>