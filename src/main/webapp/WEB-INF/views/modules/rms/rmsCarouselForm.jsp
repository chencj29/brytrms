<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>行李转盘基础信息表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/rms/rmsCarousel/">行李转盘基础信息表列表</a></li>
		<li class="active"><a href="${ctx}/rms/rmsCarousel/form?id=${rmsCarousel.id}">行李转盘基础信息表<shiro:hasPermission name="rms:rmsCarousel:edit">${not empty rmsCarousel.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="rms:rmsCarousel:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="rmsCarousel" action="${ctx}/rms/rmsCarousel/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">航站楼编号：</label>
			<div class="controls">
				<form:input path="airportTerminalCode" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">航站楼名称：</label>
			<div class="controls">
				<form:input path="airportTerminalName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行李转盘编号：</label>
			<div class="controls">
				<form:input path="carouselNum" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行李转盘性质：</label>
			<div class="controls">
				<form:radiobuttons path="carouselNature" items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行李转盘状态：</label>
			<div class="controls">
				<form:radiobuttons path="carouselTypeCode" items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行李转盘状态名称：</label>
			<div class="controls">
				<form:input path="carouselTypeName" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="rms:rmsCarousel:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>