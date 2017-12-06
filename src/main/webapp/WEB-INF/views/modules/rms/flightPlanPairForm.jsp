<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>航班动态配对管理</title>
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
		<li><a href="${ctx}/rms/flightPlanPair/">航班动态配对列表</a></li>
		<li class="active"><a href="${ctx}/rms/flightPlanPair/form?id=${flightPlanPair.id}">航班动态配对<shiro:hasPermission name="rms:flightPlanPair:edit">${not empty flightPlanPair.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="rms:flightPlanPair:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="flightPlanPair" action="${ctx}/rms/flightPlanPair/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">进港航班动态ID：</label>
			<div class="controls">
				<form:input path="flightDynimicId" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班号：</label>
			<div class="controls">
				<form:input path="flightNum" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机位号：</label>
			<div class="controls">
				<form:input path="placeNum" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班航空公司ID：</label>
			<div class="controls">
				<form:input path="flightCompanyId" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班航班公司代码：</label>
			<div class="controls">
				<form:input path="flightCompanyCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班航空公司名称：</label>
			<div class="controls">
				<form:input path="flightCompanyName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港共享航班号：</label>
			<div class="controls">
				<form:input path="shareFlightNum" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班性质编号：</label>
			<div class="controls">
				<form:input path="flightNatureCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班性质名称：</label>
			<div class="controls">
				<form:input path="flightNatureName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班进出港类型编码：</label>
			<div class="controls">
				<form:input path="inoutTypeCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班进出港类型名称：</label>
			<div class="controls">
				<form:input path="inoutTypeName" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班进出港状态编号：</label>
			<div class="controls">
				<form:input path="inoutStatusCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班进出港状态名称：</label>
			<div class="controls">
				<form:input path="inoutStatusName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班起飞地编码：</label>
			<div class="controls">
				<form:input path="departureAirportCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班起飞地三字码：</label>
			<div class="controls">
				<form:input path="departureAirportThree" htmlEscape="false" maxlength="3" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班起飞地名称：</label>
			<div class="controls">
				<form:input path="departureAirportName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班经停地1编号：</label>
			<div class="controls">
				<form:input path="passAirport1Code" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班经停地1名称：</label>
			<div class="controls">
				<form:input path="passAirport1Name" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班经停地2编号：</label>
			<div class="controls">
				<form:input path="passAirport2Code" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班经停地2名称：</label>
			<div class="controls">
				<form:input path="passAirport2Name" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班目的地编号：</label>
			<div class="controls">
				<form:input path="arrivalAirportCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班目的地三字码：</label>
			<div class="controls">
				<form:input path="arrivalAirportThree" htmlEscape="false" maxlength="3" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班目的地名称：</label>
			<div class="controls">
				<form:input path="arrivalAirportName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班计划起飞时间：</label>
			<div class="controls">
				<input name="departurePlanTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.departurePlanTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班计划到达时间：</label>
			<div class="controls">
				<input name="arrivalPlanTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.arrivalPlanTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班预计起飞时间：</label>
			<div class="controls">
				<input name="etd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.etd}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班预计到达时间：</label>
			<div class="controls">
				<input name="eta" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.eta}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班实际起飞时间：</label>
			<div class="controls">
				<input name="atd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.atd}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班实际到达时间：</label>
			<div class="controls">
				<input name="ata" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.ata}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班行程时间：</label>
			<div class="controls">
				<form:input path="travelTime" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班行李转盘：</label>
			<div class="controls">
				<form:input path="carouselNum" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班VIP：</label>
			<div class="controls">
				<form:input path="vip" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班延误原因编号：</label>
			<div class="controls">
				<form:input path="delayResonsCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班延误原因名称：</label>
			<div class="controls">
				<form:input path="delayResonsName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班延误备注：</label>
			<div class="controls">
				<form:input path="delayResonsRemark" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班登机口编号：</label>
			<div class="controls">
				<form:input path="gateCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班登机口名称：</label>
			<div class="controls">
				<form:input path="gateName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班登机开始时间：</label>
			<div class="controls">
				<input name="boardingStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.boardingStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班登机结束时间：</label>
			<div class="controls">
				<input name="boardingEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.boardingEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班值机岛编号：</label>
			<div class="controls">
				<form:input path="checkinIslandCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班值机岛名称：</label>
			<div class="controls">
				<form:input path="checkinIslandName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班值机站台编号：</label>
			<div class="controls">
				<form:input path="checkinCounterCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班值机站台名称：</label>
			<div class="controls">
				<form:input path="checkinCounterName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班值机开始时间：</label>
			<div class="controls">
				<input name="checkinStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.checkinStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班值机结束时间：</label>
			<div class="controls">
				<input name="checkinEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.checkinEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班是否延误：</label>
			<div class="controls">
				<form:input path="delayTimePend" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班状态编码：</label>
			<div class="controls">
				<form:input path="status" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班状态名称：</label>
			<div class="controls">
				<form:input path="statusName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班机型参数ID：</label>
			<div class="controls">
				<form:input path="flightPropertiesId" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班航班属性编码：</label>
			<div class="controls">
				<form:input path="flightAttrCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班航班属性名称：</label>
			<div class="controls">
				<form:input path="flightAttrName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班计划日期星期数：</label>
			<div class="controls">
				<form:input path="planDayOfWeek" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班备降备注：</label>
			<div class="controls">
				<form:input path="alternateRemarks" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班延误地点编码：</label>
			<div class="controls">
				<form:input path="delayResonsAreaCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班延误地点名称：</label>
			<div class="controls">
				<form:input path="delayResonsAreaName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班备降地点编码：</label>
			<div class="controls">
				<form:input path="alternateAreaCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班备降地点名称：</label>
			<div class="controls">
				<form:input path="alternateAreaName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班取消原因：</label>
			<div class="controls">
				<form:input path="cancelFlightResons" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班是否外航：</label>
			<div class="controls">
				<form:input path="isExtraFlight" htmlEscape="false" maxlength="1" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班补班时间是否待定：</label>
			<div class="controls">
				<form:input path="extraFlightTimePend" htmlEscape="false" maxlength="1" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班催促登机时间：</label>
			<div class="controls">
				<input name="urgeBoardTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.urgeBoardTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班登机开始时间：</label>
			<div class="controls">
				<input name="planBoardingStarttime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.planBoardingStarttime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班登机结束时间：</label>
			<div class="controls">
				<input name="planBoardingEndtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.planBoardingEndtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班客齐时间：</label>
			<div class="controls">
				<input name="guestTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.guestTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班航站楼ID：</label>
			<div class="controls">
				<form:input path="terminalId" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班航站楼：</label>
			<div class="controls">
				<form:input path="terminal" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进港航班返航时间：</label>
			<div class="controls">
				<input name="turnBackTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.turnBackTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班动态ID：</label>
			<div class="controls">
				<form:input path="flightDynimicId2" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班号：</label>
			<div class="controls">
				<form:input path="flightNum2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班机位号：</label>
			<div class="controls">
				<form:input path="placeNum2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班航空公司ID：</label>
			<div class="controls">
				<form:input path="flightCompanyId2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班航空公司代码：</label>
			<div class="controls">
				<form:input path="flightCompanyCode2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班航空公司名称：</label>
			<div class="controls">
				<form:input path="flightCompanyName2" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班共享航班号：</label>
			<div class="controls">
				<form:input path="shareFlightNum2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班性质编号：</label>
			<div class="controls">
				<form:input path="flightNatureCode2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班性质名称：</label>
			<div class="controls">
				<form:input path="flightNatureName2" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班进出港类型编码：</label>
			<div class="controls">
				<form:input path="inoutTypeCode2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班进出港类型名称：</label>
			<div class="controls">
				<form:input path="inoutTypeName2" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班进出港状态编号：</label>
			<div class="controls">
				<form:input path="inoutStatusCode2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班进出港状态名称：</label>
			<div class="controls">
				<form:input path="inoutStatusName2" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班起飞地编码：</label>
			<div class="controls">
				<form:input path="departureAirportCode2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班起飞地三字码：</label>
			<div class="controls">
				<form:input path="departureAirportThree2" htmlEscape="false" maxlength="3" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班起飞地名称：</label>
			<div class="controls">
				<form:input path="departureAirportName2" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班经停地1编号：</label>
			<div class="controls">
				<form:input path="passAirport1Code2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班经停地1名称：</label>
			<div class="controls">
				<form:input path="passAirport1Name2" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班经停地2编号：</label>
			<div class="controls">
				<form:input path="passAirport2Code2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班经停地2名称：</label>
			<div class="controls">
				<form:input path="passAirport2Name2" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班目的地编号：</label>
			<div class="controls">
				<form:input path="arrivalAirportCode2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班目的地三字码：</label>
			<div class="controls">
				<form:input path="arrivalAirportThree2" htmlEscape="false" maxlength="3" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班目的地名称：</label>
			<div class="controls">
				<form:input path="arrivalAirportName2" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班计划起飞时间：</label>
			<div class="controls">
				<input name="departurePlanTime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.departurePlanTime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班计划到达时间：</label>
			<div class="controls">
				<input name="arrivalPlanTime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.arrivalPlanTime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班预计起飞时间：</label>
			<div class="controls">
				<input name="etd2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.etd2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班预计到达时间：</label>
			<div class="controls">
				<input name="eta2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.eta2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班实际起飞时间：</label>
			<div class="controls">
				<input name="atd2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.atd2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班实际到达时间：</label>
			<div class="controls">
				<input name="ata2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.ata2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班行程时间：</label>
			<div class="controls">
				<form:input path="travelTime2" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班行李转盘：</label>
			<div class="controls">
				<form:input path="carouselNum2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班VIP：</label>
			<div class="controls">
				<form:input path="vip2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班延误原因编号：</label>
			<div class="controls">
				<form:input path="delayResonsCode2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班延误原因名称：</label>
			<div class="controls">
				<form:input path="delayResonsName2" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班延误备注：</label>
			<div class="controls">
				<form:input path="delayResonsRemark2" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班登机口编号：</label>
			<div class="controls">
				<form:input path="gateCode2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班登机口名称：</label>
			<div class="controls">
				<form:input path="gateName2" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班登机开始时间：</label>
			<div class="controls">
				<input name="boardingStartTime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.boardingStartTime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班登机结束时间：</label>
			<div class="controls">
				<input name="boardingEndTime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.boardingEndTime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班值机岛编号：</label>
			<div class="controls">
				<form:input path="checkinIslandCode2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班值机岛名称：</label>
			<div class="controls">
				<form:input path="checkinIslandName2" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班值机站台编号：</label>
			<div class="controls">
				<form:input path="checkinCounterCode2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班值机站台名称：</label>
			<div class="controls">
				<form:input path="checkinCounterName2" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班值机开始时间：</label>
			<div class="controls">
				<input name="checkinStartTime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.checkinStartTime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班值机结束时间：</label>
			<div class="controls">
				<input name="checkinEndTime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.checkinEndTime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班是否延误：</label>
			<div class="controls">
				<form:input path="delayTimePend2" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班状态编码：</label>
			<div class="controls">
				<form:input path="status2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班状态名称：</label>
			<div class="controls">
				<form:input path="statusName2" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班机型参数ID：</label>
			<div class="controls">
				<form:input path="flightPropertiesId2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班属性编码：</label>
			<div class="controls">
				<form:input path="flightAttrCode2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班属性名称：</label>
			<div class="controls">
				<form:input path="flightAttrName2" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班计划日期星期数：</label>
			<div class="controls">
				<form:input path="planDayOfWeek2" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班备降备注：</label>
			<div class="controls">
				<form:input path="alternateRemarks2" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班延误地名编码：</label>
			<div class="controls">
				<form:input path="delayResonsAreaCode2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班延误地名名称：</label>
			<div class="controls">
				<form:input path="delayResonsAreaName2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班备降地点编码：</label>
			<div class="controls">
				<form:input path="alternateAreaCode2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班备降地点名称：</label>
			<div class="controls">
				<form:input path="alternateAreaName2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班取消原因：</label>
			<div class="controls">
				<form:input path="cancelFlightResons2" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班是否外航：</label>
			<div class="controls">
				<form:input path="isExtraFlight2" htmlEscape="false" maxlength="1" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班补班时间是否待定：</label>
			<div class="controls">
				<form:input path="extraFlightTimePend2" htmlEscape="false" maxlength="1" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班催促登机时间：</label>
			<div class="controls">
				<input name="urgeBoardTime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.urgeBoardTime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班开始登机时间：</label>
			<div class="controls">
				<input name="planBoardingStarttime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.planBoardingStarttime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班结束登机时间：</label>
			<div class="controls">
				<input name="planBoardingEndtime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.planBoardingEndtime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班客齐时间：</label>
			<div class="controls">
				<input name="guestTime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.guestTime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班航站楼ID：</label>
			<div class="controls">
				<form:input path="terminalId2" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班航站楼：</label>
			<div class="controls">
				<form:input path="terminal2" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出港航班返航时间：</label>
			<div class="controls">
				<input name="turnBackTime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.turnBackTime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计划日期：</label>
			<div class="controls">
				<input name="planDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${flightPlanPair.planDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">代理人编号：</label>
			<div class="controls">
				<form:input path="agentCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">代理人名称：</label>
			<div class="controls">
				<form:input path="agentName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">飞机号：</label>
			<div class="controls">
				<form:input path="aircraftNum" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机型编号：</label>
			<div class="controls">
				<form:input path="aircraftTypeCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机型名称：</label>
			<div class="controls">
				<form:input path="aircraftTypeName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保障类型编码：</label>
			<div class="controls">
				<form:input path="safecuardTypeCode" htmlEscape="false" maxlength="36" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保障类型名称：</label>
			<div class="controls">
				<form:input path="safecuardTypeName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="rms:flightPlanPair:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>