<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>值机柜台基础信息表(基础专用)管理</title>
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
		<li class="active"><a href="${ctx}/rms/checkinCounterBasic/">值机柜台基础信息表(基础专用)列表</a></li>
		<shiro:hasPermission name="rms:checkinCounterBasic:edit"><li><a href="${ctx}/rms/checkinCounterBasic/form">值机柜台基础信息表(基础专用)添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="checkinCounterBasic" action="${ctx}/rms/checkinCounterBasic/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>航站楼编号：</label>
				<form:input path="airportTerminalCode" htmlEscape="false" maxlength="36" class="input-medium"/>
			</li>
			<li><label>航站楼名称：</label>
				<form:input path="airportTerminalName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>值机柜台编号：</label>
				<form:input path="checkinCounterNum" htmlEscape="false" maxlength="36" class="input-medium"/>
			</li>
			<li><label>值机柜台性质：</label>
				<form:input path="checkinCounterNature" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>值机柜台状态：</label>
				<form:input path="checkinCounterTypeCode" htmlEscape="false" maxlength="36" class="input-medium"/>
			</li>
			<li><label>值机柜台状态名称：</label>
				<form:input path="checkinCounterTypeName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>order_index：</label>
				<form:input path="orderIndex" htmlEscape="false" maxlength="18" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>航站楼编号</th>
				<th>航站楼名称</th>
				<th>值机柜台编号</th>
				<th>值机柜台性质</th>
				<th>值机柜台状态</th>
				<th>值机柜台状态名称</th>
				<th>order_index</th>
				<shiro:hasPermission name="rms:checkinCounterBasic:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="checkinCounterBasic">
			<tr>
				<td><a href="${ctx}/rms/checkinCounterBasic/form?id=${checkinCounterBasic.id}">
					${checkinCounterBasic.airportTerminalCode}
				</a></td>
				<td>
					${checkinCounterBasic.airportTerminalName}
				</td>
				<td>
					${checkinCounterBasic.checkinCounterNum}
				</td>
				<td>
					${checkinCounterBasic.checkinCounterNature}
				</td>
				<td>
					${checkinCounterBasic.checkinCounterTypeCode}
				</td>
				<td>
					${checkinCounterBasic.checkinCounterTypeName}
				</td>
				<td>
					${checkinCounterBasic.orderIndex}
				</td>
				<shiro:hasPermission name="rms:checkinCounterBasic:edit"><td>
    				<a href="${ctx}/rms/checkinCounterBasic/form?id=${checkinCounterBasic.id}">修改</a>
					<a href="${ctx}/rms/checkinCounterBasic/delete?id=${checkinCounterBasic.id}" onclick="return confirmx('确认要删除该值机柜台基础信息表(基础专用)吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>