<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>值机柜台占用信息管理</title>
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
		<li><a href="${ctx}/rms/checkingCounterOccupyingInfo/">值机柜台占用信息列表</a></li>
		<li class="active"><a href="${ctx}/rms/checkingCounterOccupyingInfo/form?id=${checkingCounterOccupyingInfo.id}">值机柜台占用信息<shiro:hasPermission name="rms:checkingCounterOccupyingInfo:edit">${not empty checkingCounterOccupyingInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="rms:checkingCounterOccupyingInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="checkingCounterOccupyingInfo" action="${ctx}/rms/checkingCounterOccupyingInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">航班动态ID：</label>
			<div class="controls">
				<form:input path="flightDynamicId" htmlEscape="false" maxlength="36" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">航班号：</label>
			<div class="controls">
				<form:input path="flightDynamicCode" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">航班属性(1 - 国内, 2 - 国际, 3 - 混合)：</label>
			<div class="controls">
				<form:input path="flightDynamicNature" htmlEscape="false" maxlength="1" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预计占用值机柜台个数：</label>
			<div class="controls">
				<form:input path="expectedCheckingCounterNum" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际占用值机柜台个数：</label>
			<div class="controls">
				<form:input path="actualCheckingCounterNum" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">值机柜台编号(国内)：</label>
			<div class="controls">
				<form:input path="inteCheckingCounterCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">值机柜台编号(国际)：</label>
			<div class="controls">
				<form:input path="intlCheckingCounterCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预计开始占用时间：</label>
			<div class="controls">
				<input name="expectedStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${checkingCounterOccupyingInfo.expectedStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预计结束占用时间：</label>
			<div class="controls">
				<input name="expectedOverTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${checkingCounterOccupyingInfo.expectedOverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际开始占用时间(国内)：</label>
			<div class="controls">
				<input name="inteActualStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${checkingCounterOccupyingInfo.inteActualStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际结束占用时间(国内)：</label>
			<div class="controls">
				<input name="inteActualOverTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${checkingCounterOccupyingInfo.inteActualOverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际开始占用时间(国际)：</label>
			<div class="controls">
				<input name="intlActualStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${checkingCounterOccupyingInfo.intlActualStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际结束占用时间(国际)：</label>
			<div class="controls">
				<input name="intlActualOverTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${checkingCounterOccupyingInfo.intlActualOverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="rms:checkingCounterOccupyingInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>