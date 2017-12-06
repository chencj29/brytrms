<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保障过程管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function aaa(){
			alert("1");
		}
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
		<li><a href="${ctx}/rms/safeguardProcess/">保障过程管理列表</a></li>
		<li class="active"><a href="${ctx}/rms/safeguardProcess/form?id=${safeguardProcess.id}">保障过程管理<shiro:hasPermission name="rms:safeguardProcess:edit">${not empty safeguardProcess.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="rms:safeguardProcess:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="safeguardProcess" action="${ctx}/rms/safeguardProcess/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">过程编号：</label>
			<div class="controls">
				<form:input path="safeguardProcessCode" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">过程名称：</label>
			<div class="controls">
				<form:input path="safeguardProcessName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">过程属性名称：</label>
			<div class="controls">
				<form:input path="safeguardAttrName" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">过程复合名称：</label>
			<div class="controls">
				<form:input path="processCompoundName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计划显示颜色：</label>
			<div class="controls">
				<form:input path="planColor" htmlEscape="false" maxlength="6" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际显示颜色：</label>
			<div class="controls">
				<form:input path="actualColor" htmlEscape="false" maxlength="6" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冗余1：</label>
			<div class="controls">
				<form:input path="redundant1" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冗余2：</label>
			<div class="controls">
				<form:input path="redundant2" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冗余3：</label>
			<div class="controls">
				<form:input path="redundant3" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冗余4：</label>
			<div class="controls">
				<form:input path="redundant4" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冗余5：</label>
			<div class="controls">
				<form:input path="redundant5" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冗余6：</label>
			<div class="controls">
				<form:input path="redundant6" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冗余7：</label>
			<div class="controls">
				<form:input path="redundant7" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冗余8：</label>
			<div class="controls">
				<form:input path="redundant8" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冗余9：</label>
			<div class="controls">
				<form:input path="redundant9" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="rms:safeguardProcess:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" onclick="aaa()" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>