<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>值机数据管理</title>
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
		<li><a href="${ctx}/rms/rmsFlightDuty/">值机数据列表</a></li>
		<li class="active"><a href="${ctx}/rms/rmsFlightDuty/form?id=${rmsFlightDuty.id}">值机数据<shiro:hasPermission name="rms:rmsFlightDuty:edit">${not empty rmsFlightDuty.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="rms:rmsFlightDuty:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="rmsFlightDuty" action="${ctx}/rms/rmsFlightDuty/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">航班动态ID：</label>
			<div class="controls">
				<form:input path="flightDynamicId" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">航班号：</label>
			<div class="controls">
				<form:input path="flightNum" htmlEscape="false" maxlength="10" class="input-xlarge "/>
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
				<form:input path="addrTwoCode" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地点性质：</label>
			<div class="controls">
				<form:input path="addrTwoNature" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成人数：</label>
			<div class="controls">
				<form:input path="personCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">儿童数：</label>
			<div class="controls">
				<form:input path="childCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">婴儿数：</label>
			<div class="controls">
				<form:input path="babyCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">无陪伴儿童人数：</label>
			<div class="controls">
				<form:input path="babyAloneCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">直达行李数量：</label>
			<div class="controls">
				<form:input path="nonstopLuggageCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">直达行李重量：</label>
			<div class="controls">
				<form:input path="nonstopLuggageWeight" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转运货物数量：</label>
			<div class="controls">
				<form:input path="transferLuggageCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转运货物重量：</label>
			<div class="controls">
				<form:input path="transferLuggageWeight" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">vip人数：</label>
			<div class="controls">
				<form:input path="vipPersonCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">vip座位：</label>
			<div class="controls">
				<form:input path="vipSeat" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">vip行李件数：</label>
			<div class="controls">
				<form:input path="vipLuggageCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">vip行李重量：</label>
			<div class="controls">
				<form:input path="vipLuggageWeight" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">头等舱人数：</label>
			<div class="controls">
				<form:input path="firstCabinPersonCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">头等舱座位：</label>
			<div class="controls">
				<form:input path="firstCabinSeat" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公务舱人数：</label>
			<div class="controls">
				<form:input path="businessCabinPersonCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公务舱座位：</label>
			<div class="controls">
				<form:input path="businessCabinSeat" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="rms:rmsFlightDuty:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>