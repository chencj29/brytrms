<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>本站仓单数据管理管理</title>
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
		<li><a href="${ctx}/rms/rmsFlightHomeManifest/">本站仓单数据管理列表</a></li>
		<li class="active"><a href="${ctx}/rms/rmsFlightHomeManifest/form?id=${rmsFlightHomeManifest.id}">本站仓单数据管理<shiro:hasPermission name="rms:rmsFlightHomeManifest:edit">${not empty rmsFlightHomeManifest.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="rms:rmsFlightHomeManifest:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="rmsFlightHomeManifest" action="${ctx}/rms/rmsFlightHomeManifest/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">航班动态ID：</label>
			<div class="controls">
				<form:input path="flightDynamicId" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港成人数量：</label>
			<div class="controls">
				<form:input path="personCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港儿童数量：</label>
			<div class="controls">
				<form:input path="childCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港婴儿数量：</label>
			<div class="controls">
				<form:input path="babyCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港旅客总数：</label>
			<div class="controls">
				<form:input path="travellerAllCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机组人数：</label>
			<div class="controls">
				<form:input path="flightCrewCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">油料重量：</label>
			<div class="controls">
				<form:input path="oilWeight" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港邮件件数：</label>
			<div class="controls">
				<form:input path="mailCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港邮件重量：</label>
			<div class="controls">
				<form:input path="mailWeight" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港货物数量：</label>
			<div class="controls">
				<form:input path="goodsCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港货物重量：</label>
			<div class="controls">
				<form:input path="goodsWeight" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港行李件数：</label>
			<div class="controls">
				<form:input path="luggageCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港行李重量：</label>
			<div class="controls">
				<form:input path="luggageWeight" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港VIP人数：</label>
			<div class="controls">
				<form:input path="vipCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">头等舱旅客人数：</label>
			<div class="controls">
				<form:input path="firstCabinCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公务舱旅客人数：</label>
			<div class="controls">
				<form:input path="businessCabinCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经济舱旅客人数：</label>
			<div class="controls">
				<form:input path="touristCabinCount" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际过站旅客总数：</label>
			<div class="controls">
				<form:input path="personActualCountP" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际过站成人数：</label>
			<div class="controls">
				<form:input path="personCountP" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际过站儿童数：</label>
			<div class="controls">
				<form:input path="childCoutP" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际过站婴儿数：</label>
			<div class="controls">
				<form:input path="babyCountP" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="rms:rmsFlightHomeManifest:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>