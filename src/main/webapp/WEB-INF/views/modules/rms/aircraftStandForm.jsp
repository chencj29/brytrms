<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机位基础信息管理</title>
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
		<li><a href="${ctx}/rms/aircraftStand/">机位基础信息列表</a></li>
		<li class="active"><a href="${ctx}/rms/aircraftStand/form?id=${aircraftStand.id}">机位基础信息<shiro:hasPermission name="rms:aircraftStand:edit">${not empty aircraftStand.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="rms:aircraftStand:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="aircraftStand" action="${ctx}/rms/aircraftStand/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机位号：</label>
			<div class="controls">
				<form:input path="aircraftStandNum" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机坪号：</label>
			<div class="controls">
				<form:input path="aircraftParkNum" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机坪调度者：</label>
			<div class="controls">
				<form:input path="aircraftParkDispatcher" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否有廊桥：</label>
			<div class="controls">
				<form:input path="hasSalon" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">廊桥状态：</label>
			<div class="controls">
				<form:input path="salonStatus" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否有油管：</label>
			<div class="controls">
				<form:input path="hasOilpipeline" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">油管状态：</label>
			<div class="controls">
				<form:input path="oilpipelineStatus" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">左机位号：</label>
			<div class="controls">
				<form:input path="leftAsNum" htmlEscape="false" maxlength="5" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">右机位号：</label>
			<div class="controls">
				<form:input path="rightAsNum" htmlEscape="false" maxlength="5" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">前滑行道：</label>
			<div class="controls">
				<form:input path="frontCoast" htmlEscape="false" maxlength="5" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">后滑行道：</label>
			<div class="controls">
				<form:input path="backCoast" htmlEscape="false" maxlength="5" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">左鼻轮线距：</label>
			<div class="controls">
				<form:input path="leftWheelLine" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">右鼻轮线距：</label>
			<div class="controls">
				<form:input path="rightWheelLine" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">翼展：</label>
			<div class="controls">
				<form:input path="wingLength" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否可用：</label>
			<div class="controls">
				<form:input path="available" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属航站楼：</label>
			<div class="controls">
				<form:input path="airportTerminalCode" htmlEscape="false" maxlength="5" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="rms:aircraftStand:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>