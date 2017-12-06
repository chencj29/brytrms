<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保障类型保障过程关联管理</title>
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
		<li><a href="${ctx}/rms/safeguardTypeToProcess/">保障类型保障过程关联列表</a></li>
		<li class="active"><a href="${ctx}/rms/safeguardTypeToProcess/form?id=${safeguardTypeToProcess.id}">保障类型保障过程关联<shiro:hasPermission name="rms:safeguardTypeToProcess:edit">${not empty safeguardTypeToProcess.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="rms:safeguardTypeToProcess:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="safeguardTypeToProcess" action="${ctx}/rms/safeguardTypeToProcess/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保障类型ID：</label>
			<div class="controls">
				<form:input path="safeguardTypeId" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保障过程ID：</label>
			<div class="controls">
				<form:input path="safeguardProcessId" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间相对于计起前后：</label>
			<div class="controls">
				<form:input path="startDepFb" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间相对于计起间隔：</label>
			<div class="controls">
				<form:input path="startDepRange" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间相对于计达前后：</label>
			<div class="controls">
				<form:input path="startArrFb" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间相对于计达间隔：</label>
			<div class="controls">
				<form:input path="startArrRange" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间相对于过程ID：</label>
			<div class="controls">
				<form:input path="startProId" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间相对于过程前后：</label>
			<div class="controls">
				<form:input path="startProFb" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间相对于过程间隔：</label>
			<div class="controls">
				<form:input path="startProRange" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间相对于计起前后：</label>
			<div class="controls">
				<form:input path="endDepFb" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间相对于计起间隔：</label>
			<div class="controls">
				<form:input path="endDepRange" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间相对于计达前后：</label>
			<div class="controls">
				<form:input path="endArrFb" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间相对于计达间隔：</label>
			<div class="controls">
				<form:input path="endArrRange" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间相对于过程ID：</label>
			<div class="controls">
				<form:input path="endProId" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间相对于过程前后：</label>
			<div class="controls">
				<form:input path="endProFb" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间相对于过程间隔：</label>
			<div class="controls">
				<form:input path="endProRange" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">绝对时长：</label>
			<div class="controls">
				<form:input path="absoluteRange" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相对于整体时长百分比：</label>
			<div class="controls">
				<form:input path="percentRange" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冗余1：</label>
			<div class="controls">
				<form:input path="redundant1" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冗余2：</label>
			<div class="controls">
				<form:input path="redundant2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冗余3：</label>
			<div class="controls">
				<form:input path="redundant3" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冗余4：</label>
			<div class="controls">
				<form:input path="redundant4" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冗余5：</label>
			<div class="controls">
				<form:input path="redundant5" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冗余6：</label>
			<div class="controls">
				<form:input path="redundant6" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冗余7：</label>
			<div class="controls">
				<form:input path="redundant7" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冗余8：</label>
			<div class="controls">
				<form:input path="redundant8" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冗余9：</label>
			<div class="controls">
				<form:input path="redundant9" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冗余10：</label>
			<div class="controls">
				<form:input path="redundant10" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="rms:safeguardTypeToProcess:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>