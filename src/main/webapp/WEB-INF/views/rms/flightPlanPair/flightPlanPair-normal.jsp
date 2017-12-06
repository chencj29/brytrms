<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
	<title>运输调度计划</title>

	<link rel="stylesheet" href="${ctxAssets}/scripts/gantt/gantt.css">
</head>
<body>
<div class="easyui-layout" id="layout" data-options="fit:true">

	<div data-options="region:'center',fit:false,border:true" style="width: 100%;">
		<table class="easyui-datagrid" id="datagrid" title="运输调度计划" data-options="fitColumns:false,fit:true,pageSize:100000,border:true">
			<thead>
			<tr>
				<th field="id" width="66" hidden="true" sortable="false" order="false" rowspan="2">主键</th>
				<th field="planDate" width="66" align="center" sortable="true" order="false" rowspan="2" formatter="formatPlanDate">计划日期</th>
				<th width="66" align="center" sortable="true" order="false" colspan="2">代理</th>
				<th width="50" align="center" sortable="true" colspan="3">飞机</th>
				<th field="travelLine" width="150" align="center" sortable="false" rowspan="2">航线</th>

				<th width="50" align="center" sortable="true">进港</th>

				<th width="50" align="center" sortable="true">出港</th>

				<th colspan="11">进港</th>
				<th colspan="14">出港</th>
			</tr>
			<tr>
				<th field="agentCode" width="60" align="center" sortable="false" order="false">代理人ID</th>
				<th field="agentName" width="60" align="center" sortable="false" order="false">代理人名称</th>

				<!-- 飞机 -->
				<th field="aircraftNum" width="50" align="center" sortable="true">机号</th>
				<th field="aircraftTypeCode" width="50" align="center" sortable="true">机型</th>
				<th field="placeNum" width="50" align="center" sortable="true">机位</th>

				<th field="flightNum" hidden="true" width="80" align="center">进港航班ID</th>
				<th field="flightNum2" hidden="true" width="80" align="center">出港航班ID</th>

				<!-- 进港 -->
				<th field="mergeFlightNum" width="80" align="center" formatter="formatFlightNum">综合航班号</th>
				<!-- 出港 -->
				<th field="mergeFlightNum2" width="80" align="center" formatter="formatFlightNum2">综合航班号</th>


				<!-- 进港长条开始 -->
				<th field="flightDynimicId" width="50" hidden="true" align="center" sortable="false">进港</th>

				<th field="shareFlightNum" width="60" align="center" sortable="false">共享航班</th>
				<th field="flightNatureName" width="50" align="center" sortable="false">性质</th>
				<th field="statusName" width="60" align="center" sortable="false">航班状态</th>

				<th field="departureAirportCode" width="60" align="center" sortable="false" formatter="formatDepartureAirportCode">起飞</th>
				<th field="passAirport1Code" width="60" align="center" sortable="false" formatter="formatPassAirport1Code">经停1</th>
				<th field="passAirport2Code" width="60" align="center" sortable="false" formatter="formatPassAirport2Code">经停2</th>

				<%--<th field="departurePlanTime" width="70" align="center" sortable="true" formatter="formatDateTime">计飞</th>--%>
				<th field="arrivalPlanTime" width="70" align="center" sortable="true" formatter="formatDateTime">计达</th>
				<%--<th field="etd" width="70" align="center" sortable="true" formatter="formatDateTime">预飞</th>--%>
				<th field="eta" width="70" align="center" sortable="true" formatter="formatDateTime">预达</th>
				<%--<th field="atd" width="70" align="center" sortable="true" formatter="formatDateTime">实飞</th>--%>
				<th field="ata" width="70" align="center" sortable="true" formatter="formatDateTime">实达</th>

				<th field="delayResonsName" width="120" align="center" sortable="false">延误原因</th>
				<th field="alternateAreaName" width="120" align="center" sortable="false">备降</th>

				<!-- 出港长条开始 -->
				<th field="flightDynimicId2" width="50" hidden="true" align="center" sortable="true">出港</th>

				<th field="shareFlightNum2" width="60" align="center" sortable="false">共享航班</th>
				<th field="flightNatureName2" width="50" align="center" sortable="false">性质</th>
				<th field="statusName2" width="60" align="center" sortable="false">航班状态</th>

				<th field="arrivalairportcode" width="50" align="center" sortable="false">到达</th>
				<th field="passAirport1Code2" width="50" align="center" sortable="false">经停1</th>
				<th field="passAirport2Code2" width="50" align="center" sortable="false">经停2</th>

				<th field="departurePlanTime2" width="70" align="center" sortable="true" formatter="formatDateTime">计飞</th>
				<th field="etd2" width="70" align="center" sortable="true" formatter="formatDateTime">预飞</th>
				<th field="atd2" width="70" align="center" sortable="true" formatter="formatDateTime">实飞</th>

				<th field="delayResonsName2" width="120" align="center" sortable="false">延误原因</th>
			</tr>
			</thead>
		</table>
	</div>

	<div title="保障过程图" id="graphic-panel" style="width: 100%;height: 100%;"
		 data-options="region:'south', hideCollapsedContent:false, collapsed:true,border:false">
		<div>
			<div class="gantt" style="height: 100%;overflow: auto; width: 100%;position: absolute;"></div>
		</div>
	</div>
</div>

<div id="specialServiceDialog" style="width: 800px;margin-bottom:7px;">
	<table class="easyui-datagrid" id="specialServiceDatagrid" data-options="fitColumns:false,fit:true,pageSize:100000,border:false" style="width:100%;height: 100%;">
		<thead>
		<tr>
			<th field="flightCompanyName" width="100">公司名称</th>
			<th field="flightNum" width="100">航班号</th>
			<th field="place" width="100">地点</th>
			<th field="passengerName" width="100">姓名</th>
			<th field="passengerAge" width="100">年龄</th>
			<th field="pickUpName" width="100">接站人</th>
			<th field="pickUpPhone" width="100">接站人电话</th>
			<th field="waiterName" width="100">服务人员</th>
			<th field="operatorName" width="100">值班人员</th>
		</tr>
		</thead>
	</table>
</div>

<script type="text/javascript" src="${ctxAssets}/scripts/gantt/collision.detection.plugin.js"></script>
<script type="text/javascript" src="${ctxAssets}/scripts/gantt/gantt4progress.js"></script>
<%--<script src="${ctxAssets}/scripts/jquery-ui/jquery-ui.min.js"></script>--%>
<script src="${ctxAssets}/scripts/layer/layer.js"></script>
<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
<script src="${ctxAssets}/scripts/My97DatePicker/WdatePicker.js"></script>
<script src="${ctxAssets}/scripts/aui-artTemplate/dist/template.js"></script>
<script src="${ctxAssets}/scripts/select2/dist/js/select2.min.js"></script>
<script>
	var moduleContext = "flightPlanPair";
	/**
	 * 过滤列声明接口(必须，可空)
	 */
	var specialFilteringColumnDefinition = [], isFit = isFitColumns = false;

	function dateFmt(v, r, i, f) {
		return !v ? "" : moment(v).format("YYYY-MM-DD HH:mm");
	}

	function yesNoFmt(v, r, i, f) {
		return v === "0" ? "否" : "是";
	}

	function formatFlightNum(val, row, index) {
		if (row.flightNum != null && row.flightNum != '') {
			return row.flightCompanyCode + row.flightNum;
		} else {
			return "";
		}
	}
	function formatFlightNum2(val, row, index) {
		if (row.flightNum2 != null && row.flightNum2 != '') {
			return row.flightCompanyCode2 + row.flightNum2;
		} else {
			return "";
		}
	}
	function formatDepartureAirportCode(val, row, index) {
		if (row.departureAirportCode != null && row.departureAirportCode != '') {
			return row.departureAirportCode + " " + row.departureAirportName;
		} else {
			return "";
		}
	}
	function formatPassAirport1Code(val, row, index) {
		if (row.passAirport1Code != null && row.passAirport1Code != '') {
			return row.passAirport1Code + " " + row.passAirport1Name;
		} else {
			return "";
		}
	}
	function formatPassAirport2Code(val, row, index) {
		if (row.passAirport2Code != null && row.passAirport2Code != '') {
			return row.passAirport2Code + " " + row.passAirport2Name;
		} else {
			return "";
		}
	}
	function formatDateTime(val, row, index) {
		if (val != null && val != '') {
			return moment(val).format("DD HH:mm");
		} else {
			return "";
		}
	}
	function formatPlanDate(val, row, index) {
		if (val != null && val != '') {
			return moment(val).format("YYYY-MM-DD");
		} else {
			return "";
		}
	}
</script>

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.Pair.js"></script>
<script type="text/html" id="timeFormContainer">
	<form id="timeForm" style="padding:15px;">
		<input type="hidden" name="flightDynamicId" value="{{flightDynamicId}}">
		<input type="hidden" name="processId" value="{{processId}}">
		<table cellpadding="5" width="100%">
			<tr>
				<td width="40%">实际开始时间：</td>
				<td>
					<input type="text" name="actualStartTime" id="iptActualStartTime" class="Wdate"
						   onClick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm', readOnly:true})"
						   style="width: 200px;height: 25px; line-height: 25px;" value="{{ast}}"/>
				</td>
			</tr>
			<tr>
				<td width="40%">实际完成时间：</td>
				<td>
					<input type="text" name="actualOverTime" id="iptActualOverTime" class="Wdate"
						   onClick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm', readOnly:true})"
						   style="width: 200px;height: 25px; line-height: 25px;" value="{{aot}}"/>
				</td>
			</tr>
		</table>
	</form>
</script>
</body>