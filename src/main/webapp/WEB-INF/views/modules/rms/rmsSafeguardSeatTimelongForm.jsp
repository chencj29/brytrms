<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保障过程及座位时长管理</title>
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
		<li><a href="${ctx}/rms/rmsSafeguardSeatTimelong/">保障过程及座位时长列表</a></li>
		<li class="active"><a href="${ctx}/rms/rmsSafeguardSeatTimelong/form?id=${rmsSafeguardSeatTimelong.id}">保障过程及座位时长<shiro:hasPermission name="rms:rmsSafeguardSeatTimelong:edit">${not empty rmsSafeguardSeatTimelong.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="rms:rmsSafeguardSeatTimelong:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="rmsSafeguardSeatTimelong" action="${ctx}/rms/rmsSafeguardSeatTimelong/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">保障类型编号：</label>
			<div class="controls">
				<form:input path="safeguardTypeCode" htmlEscape="false" maxlength="36" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保障类型名称：</label>
			<div class="controls">
				<form:input path="safeguardTypeName" htmlEscape="false" maxlength="36" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保障过程编号：</label>
			<div class="controls">
				<form:input path="safeguardProcessCode" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保障过程名称：</label>
			<div class="controls">
				<form:input path="safeguardProcessName" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">座位数：</label>
			<div class="controls">
				<form:input path="seatNum" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相对开始时间：</label>
			<div class="controls">
				<form:input path="safeguardProcessFrom" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相对结束时间：</label>
			<div class="controls">
				<form:input path="safeguardProcessTo" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保障类型ID：</label>
			<div class="controls">
				<form:input path="safeguardType.Id" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保障过程ID：</label>
			<div class="controls">
				<form:input path="safeguardProcess.Id" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="rms:rmsSafeguardSeatTimelong:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>