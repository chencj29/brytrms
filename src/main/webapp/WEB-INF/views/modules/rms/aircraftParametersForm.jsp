<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机型参数管理</title>
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
		<li><a href="${ctx}/rms/aircraftParameters/">机型参数列表</a></li>
		<li class="active"><a href="${ctx}/rms/aircraftParameters/form?id=${aircraftParameters.id}">机型参数<shiro:hasPermission name="rms:aircraftParameters:edit">${not empty aircraftParameters.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="rms:aircraftParameters:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="aircraftParameters" action="${ctx}/rms/aircraftParameters/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机型：</label>
			<div class="controls">
				<form:input path="aircraftModel" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主机型：</label>
			<div class="controls">
				<form:input path="aircraftMainModel" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">子机型：</label>
			<div class="controls">
				<form:input path="aircraftSubModel" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否备用梯：</label>
			<div class="controls">
				<form:radiobuttons path="useLift" items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否用桥：</label>
			<div class="controls">
				<form:radiobuttons path="useBridge" items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电源供应方式编号：</label>
			<div class="controls">
				<form:input path="powerTypeCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电源供应方式名称：</label>
			<div class="controls">
				<form:input path="powerTypeName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">燃料补充方式编号：</label>
			<div class="controls">
				<form:input path="fuelTypeCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">燃料补充方式名称：</label>
			<div class="controls">
				<form:input path="fuelTypeName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最大座位数：</label>
			<div class="controls">
				<form:input path="maxnumSeat" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最大载货量：</label>
			<div class="controls">
				<form:input path="maxnumCargoes" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最大加油量：</label>
			<div class="controls">
				<form:input path="maxnumAmount" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">翼展：</label>
			<div class="controls">
				<form:input path="wingspan" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机身长：</label>
			<div class="controls">
				<form:input path="fuselageLength" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">鼻轮前长：</label>
			<div class="controls">
				<form:input path="frontWheelLength" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">高度：</label>
			<div class="controls">
				<form:input path="fuselageHeight" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">空重：</label>
			<div class="controls">
				<form:input path="aircraftWeight" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">无油重量：</label>
			<div class="controls">
				<form:input path="zeroFuelWeight" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">着陆重量：</label>
			<div class="controls">
				<form:input path="wheelsWeight" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最大业栽：</label>
			<div class="controls">
				<form:input path="maxnumPlayload" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最大起飞重量：</label>
			<div class="controls">
				<form:input path="maxnumTakeoffWeight" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">巡航高度：</label>
			<div class="controls">
				<form:input path="cruisingAltitude" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最大航程：</label>
			<div class="controls">
				<form:input path="maxnumRange" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">跑道长度：</label>
			<div class="controls">
				<form:input path="runwayLength" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">ACN刚度：</label>
			<div class="controls">
				<form:input path="acnRigid" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">ACN柔性：</label>
			<div class="controls">
				<form:input path="acnFlexible" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发动机台数：</label>
			<div class="controls">
				<form:input path="engineNum" htmlEscape="false" maxlength="5" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发动机型号：</label>
			<div class="controls">
				<form:input path="engineType" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生产商：</label>
			<div class="controls">
				<form:input path="engineManufacturer" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最少停留时间：</label>
			<div class="controls">
				<input name="lostofStopTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${aircraftParameters.lostofStopTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机型分类编号：</label>
			<div class="controls">
				<form:input path="articraftTypeCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机型分类名称：</label>
			<div class="controls">
				<form:input path="articraftTypeName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否最大机型：</label>
			<div class="controls">
				<form:radiobuttons path="isbigtype" items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">供气方式：</label>
			<div class="controls">
				<form:input path="arrationType" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="rms:aircraftParameters:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>