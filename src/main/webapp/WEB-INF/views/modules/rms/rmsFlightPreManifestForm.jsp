<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>上站舱单数据管理管理</title>
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
		<li><a href="${ctx}/rms/rmsFlightPreManifest/">上站舱单数据管理列表</a></li>
		<li class="active"><a href="${ctx}/rms/rmsFlightPreManifest/form?id=${rmsFlightPreManifest.id}">上站舱单数据管理<shiro:hasPermission name="rms:rmsFlightPreManifest:edit">${not empty rmsFlightPreManifest.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="rms:rmsFlightPreManifest:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="rmsFlightPreManifest" action="${ctx}/rms/rmsFlightPreManifest/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">航班动态ID：</label>
			<div class="controls">
				<form:input path="flightDynamicId" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地点：</label>
			<div class="controls">
				<form:input path="addr" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地点二字码：</label>
			<div class="controls">
				<form:input path="addrCode" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地点性质：</label>
			<div class="controls">
				<form:input path="addrNature" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港成人数：</label>
			<div class="controls">
				<form:input path="personCountJ" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港儿童数：</label>
			<div class="controls">
				<form:input path="childCountJ" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港婴儿数：</label>
			<div class="controls">
				<form:input path="babyCountJ" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">过站成人数：</label>
			<div class="controls">
				<form:input path="personCountP" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">过站儿童数：</label>
			<div class="controls">
				<form:input path="childCountP" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">过站婴儿数：</label>
			<div class="controls">
				<form:input path="babyCountP" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行李数量：</label>
			<div class="controls">
				<form:input path="luggageCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行李重量：</label>
			<div class="controls">
				<form:input path="luggageWeight" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">货物数量：</label>
			<div class="controls">
				<form:input path="goodsCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">货物重量：</label>
			<div class="controls">
				<form:input path="goodsWeight" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮件数量：</label>
			<div class="controls">
				<form:input path="mailCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮件重量：</label>
			<div class="controls">
				<form:input path="mailWeight" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="rms:rmsFlightPreManifest:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>