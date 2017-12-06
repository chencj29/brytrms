<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>公司机号管理</title>
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
		<li><a href="${ctx}/rms/companyAircraftNum/">公司机号列表</a></li>
		<li class="active"><a href="${ctx}/rms/companyAircraftNum/form?id=${companyAircraftNum.id}">公司机号<shiro:hasPermission name="rms:companyAircraftNum:edit">${not empty companyAircraftNum.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="rms:companyAircraftNum:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="companyAircraftNum" action="${ctx}/rms/companyAircraftNum/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">航空公司编码：</label>
			<div class="controls">
				<form:input path="flightCompanyCode" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">航空公司名称：</label>
			<div class="controls">
				<form:input path="flightCompanyName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">子航空公司编码：</label>
			<div class="controls">
				<form:input path="flightCompanySubCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">子公司名称：</label>
			<div class="controls">
				<form:input path="flightCompanySubName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">飞机参数表ID：</label>
			<div class="controls">
				<form:input path="aircraftParametersId" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">飞机型号：</label>
			<div class="controls">
				<form:input path="aircraftModel" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">飞机主型号：</label>
			<div class="controls">
				<form:input path="aircraftMainModel" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">飞机子型号：</label>
			<div class="controls">
				<form:input path="aircraftSubModel" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起飞重量：</label>
			<div class="controls">
				<form:input path="maxnumTakeoffWeight" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务载重：</label>
			<div class="controls">
				<form:input path="maxnumPlayload" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客座数：</label>
			<div class="controls">
				<form:input path="maxnumSeat" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">飞机类型编号：</label>
			<div class="controls">
				<form:input path="aircraftTypeCode" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">飞机类型名称：</label>
			<div class="controls">
				<form:input path="aircraftTypeName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">飞机号码：</label>
			<div class="controls">
				<form:input path="aircraftNum" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="rms:companyAircraftNum:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>