<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>航班动态配对历史信息信息</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="航班动态配对历史信息信息" id="datagrid" sortName="shareFlightNum" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="shareFlightNum" width="100" align="center" sortable="true">进港共享航班号</th>
				<th field="flightNatureCode" width="100" align="center" sortable="true">进港航班性质编号</th>
				<th field="flightNatureName" width="100" align="center" sortable="true">进港航班性质名称</th>
				<th field="inoutTypeCode" width="100" align="center" sortable="true">进港航班进出港类型编码</th>
				<th field="inoutTypeName" width="100" align="center" sortable="true">进港航班进出港类型名称</th>
				<th field="inoutStatusCode" width="100" align="center" sortable="true">进港航班进出港状态编号</th>
				<th field="inoutStatusName" width="100" align="center" sortable="true">进港航班进出港状态名称</th>
				<th field="departureAirportCode" width="100" align="center" sortable="true">进港航班起飞地编码</th>
				<th field="departureAirportThree" width="100" align="center" sortable="true">进港航班起飞地三字码</th>
				<th field="departureAirportName" width="100" align="center" sortable="true">进港航班起飞地名称</th>
				<th field="passAirport1Code" width="100" align="center" sortable="true">进港航班经停地1编号</th>
				<th field="passAirport1Name" width="100" align="center" sortable="true">进港航班经停地1名称</th>
				<th field="passAirport2Code" width="100" align="center" sortable="true">进港航班经停地2编号</th>
				<th field="passAirport2Name" width="100" align="center" sortable="true">进港航班经停地2名称</th>
				<th field="arrivalAirportCode" width="100" align="center" sortable="true">进港航班目的地编号</th>
				<th field="arrivalAirportThree" width="100" align="center" sortable="true">进港航班目的地三字码</th>
				<th field="arrivalAirportName" width="100" align="center" sortable="true">进港航班目的地名称</th>
				<th field="departurePlanTime" width="100" align="center" sortable="true">进港航班计划起飞时间</th>
				<th field="arrivalPlanTime" width="100" align="center" sortable="true">进港航班计划到达时间</th>
				<th field="etd" width="100" align="center" sortable="true">进港航班预计起飞时间</th>
				<th field="eta" width="100" align="center" sortable="true">进港航班预计到达时间</th>
				<th field="atd" width="100" align="center" sortable="true">进港航班实际起飞时间</th>
				<th field="ata" width="100" align="center" sortable="true">进港航班实际到达时间</th>
				<th field="travelTime" width="100" align="center" sortable="true">进港航班行程时间</th>
				<th field="carouselNum" width="100" align="center" sortable="true">进港航班行李转盘</th>
				<th field="vip" width="100" align="center" sortable="true">进港航班VIP</th>
				<th field="delayResonsCode" width="100" align="center" sortable="true">进港航班延误原因编号</th>
				<th field="delayResonsName" width="100" align="center" sortable="true">进港航班延误原因名称</th>
				<th field="delayResonsRemark" width="100" align="center" sortable="true">进港航班延误备注</th>
				<th field="gateCode" width="100" align="center" sortable="true">进港航班登机口编号</th>
				<th field="gateName" width="100" align="center" sortable="true">进港航班登机口名称</th>
				<th field="boardingStartTime" width="100" align="center" sortable="true">进港航班登机开始时间</th>
				<th field="boardingEndTime" width="100" align="center" sortable="true">进港航班登机结束时间</th>
				<th field="checkinIslandCode" width="100" align="center" sortable="true">进港航班值机岛编号</th>
				<th field="checkinIslandName" width="100" align="center" sortable="true">进港航班值机岛名称</th>
				<th field="checkinCounterCode" width="100" align="center" sortable="true">进港航班值机站台编号</th>
				<th field="checkinCounterName" width="100" align="center" sortable="true">进港航班值机站台名称</th>
				<th field="checkinStartTime" width="100" align="center" sortable="true">进港航班值机开始时间</th>
				<th field="checkinEndTime" width="100" align="center" sortable="true">进港航班值机结束时间</th>
				<th field="delayTimePend" width="100" align="center" sortable="true">进港航班是否延误</th>
				<th field="status" width="100" align="center" sortable="true">进港航班状态编码</th>
				<th field="statusName" width="100" align="center" sortable="true">进港航班状态名称</th>
				<th field="flightPropertiesId" width="100" align="center" sortable="true">进港航班机型参数ID</th>
				<th field="flightAttrCode" width="100" align="center" sortable="true">进港航班航班属性编码</th>
				<th field="flightAttrName" width="100" align="center" sortable="true">进港航班航班属性名称</th>
				<th field="planDayOfWeek" width="100" align="center" sortable="true">进港航班计划日期星期数</th>
				<th field="alternateRemarks" width="100" align="center" sortable="true">进港航班备降备注</th>
				<th field="delayResonsAreaCode" width="100" align="center" sortable="true">进港航班延误地点编码</th>
				<th field="delayResonsAreaName" width="100" align="center" sortable="true">进港航班延误地点名称</th>
				<th field="alternateAreaCode" width="100" align="center" sortable="true">进港航班备降地点编码</th>
				<th field="alternateAreaName" width="100" align="center" sortable="true">进港航班备降地点名称</th>
				<th field="cancelFlightResons" width="100" align="center" sortable="true">进港航班取消原因</th>
				<th field="isExtraFlight" width="100" align="center" sortable="true">进港航班是否外航</th>
				<th field="extraFlightTimePend" width="100" align="center" sortable="true">进港航班补班时间是否待定</th>
				<th field="urgeBoardTime" width="100" align="center" sortable="true">进港航班催促登机时间</th>
				<th field="planBoardingStarttime" width="100" align="center" sortable="true">进港航班登机开始时间</th>
				<th field="planBoardingEndtime" width="100" align="center" sortable="true">进港航班登机结束时间</th>
				<th field="guestTime" width="100" align="center" sortable="true">进港航班客齐时间</th>
				<th field="terminalId" width="100" align="center" sortable="true">进港航班航站楼ID</th>
				<th field="terminal" width="100" align="center" sortable="true">进港航班航站楼</th>
				<th field="turnBackTime" width="100" align="center" sortable="true">进港航班返航时间</th>
				<th field="flightDynimicId2" width="100" align="center" sortable="true">出港航班动态ID</th>
				<th field="flightNum2" width="100" align="center" sortable="true">出港航班号</th>
				<th field="placeNum2" width="100" align="center" sortable="true">出港航班机位号</th>
				<th field="flightCompanyId2" width="100" align="center" sortable="true">出港航班航空公司ID</th>
				<th field="flightCompanyCode2" width="100" align="center" sortable="true">出港航班航空公司代码</th>
				<th field="flightCompanyName2" width="100" align="center" sortable="true">出港航班航空公司名称</th>
				<th field="shareFlightNum2" width="100" align="center" sortable="true">出港航班共享航班号</th>
				<th field="flightNatureCode2" width="100" align="center" sortable="true">出港航班性质编号</th>
				<th field="flightNatureName2" width="100" align="center" sortable="true">出港航班性质名称</th>
				<th field="inoutTypeCode2" width="100" align="center" sortable="true">出港航班进出港类型编码</th>
				<th field="inoutTypeName2" width="100" align="center" sortable="true">出港航班进出港类型名称</th>
				<th field="inoutStatusCode2" width="100" align="center" sortable="true">出港航班进出港状态编号</th>
				<th field="inoutStatusName2" width="100" align="center" sortable="true">出港航班进出港状态名称</th>
				<th field="departureAirportCode2" width="100" align="center" sortable="true">出港航班起飞地编码</th>
				<th field="departureAirportThree2" width="100" align="center" sortable="true">出港航班起飞地三字码</th>
				<th field="departureAirportName2" width="100" align="center" sortable="true">出港航班起飞地名称</th>
				<th field="passAirport1Code2" width="100" align="center" sortable="true">出港航班经停地1编号</th>
				<th field="passAirport1Name2" width="100" align="center" sortable="true">出港航班经停地1名称</th>
				<th field="passAirport2Code2" width="100" align="center" sortable="true">出港航班经停地2编号</th>
				<th field="passAirport2Name2" width="100" align="center" sortable="true">出港航班经停地2名称</th>
				<th field="arrivalAirportCode2" width="100" align="center" sortable="true">出港航班目的地编号</th>
				<th field="arrivalAirportThree2" width="100" align="center" sortable="true">出港航班目的地三字码</th>
				<th field="arrivalAirportName2" width="100" align="center" sortable="true">出港航班目的地名称</th>
				<th field="departurePlanTime2" width="100" align="center" sortable="true">出港航班计划起飞时间</th>
				<th field="arrivalPlanTime2" width="100" align="center" sortable="true">出港航班计划到达时间</th>
				<th field="etd2" width="100" align="center" sortable="true">出港航班预计起飞时间</th>
				<th field="eta2" width="100" align="center" sortable="true">出港航班预计到达时间</th>
				<th field="atd2" width="100" align="center" sortable="true">出港航班实际起飞时间</th>
				<th field="ata2" width="100" align="center" sortable="true">出港航班实际到达时间</th>
				<th field="travelTime2" width="100" align="center" sortable="true">出港航班行程时间</th>
				<th field="carouselNum2" width="100" align="center" sortable="true">出港航班行李转盘</th>
				<th field="vip2" width="100" align="center" sortable="true">出港航班VIP</th>
				<th field="delayResonsCode2" width="100" align="center" sortable="true">出港航班延误原因编号</th>
				<th field="delayResonsName2" width="100" align="center" sortable="true">出港航班延误原因名称</th>
				<th field="delayResonsRemark2" width="100" align="center" sortable="true">出港航班延误备注</th>
				<th field="gateCode2" width="100" align="center" sortable="true">出港航班登机口编号</th>
				<th field="gateName2" width="100" align="center" sortable="true">出港航班登机口名称</th>
				<th field="boardingStartTime2" width="100" align="center" sortable="true">出港航班登机开始时间</th>
				<th field="boardingEndTime2" width="100" align="center" sortable="true">出港航班登机结束时间</th>
				<th field="checkinIslandCode2" width="100" align="center" sortable="true">出港航班值机岛编号</th>
				<th field="checkinIslandName2" width="100" align="center" sortable="true">出港航班值机岛名称</th>
				<th field="checkinCounterCode2" width="100" align="center" sortable="true">出港航班值机站台编号</th>
				<th field="checkinCounterName2" width="100" align="center" sortable="true">出港航班值机站台名称</th>
				<th field="checkinStartTime2" width="100" align="center" sortable="true">出港航班值机开始时间</th>
				<th field="checkinEndTime2" width="100" align="center" sortable="true">出港航班值机结束时间</th>
				<th field="delayTimePend2" width="100" align="center" sortable="true">出港航班是否延误</th>
				<th field="status2" width="100" align="center" sortable="true">出港航班状态编码</th>
				<th field="statusName2" width="100" align="center" sortable="true">出港航班状态名称</th>
				<th field="flightPropertiesId2" width="100" align="center" sortable="true">出港航班机型参数ID</th>
				<th field="flightAttrCode2" width="100" align="center" sortable="true">出港航班属性编码</th>
				<th field="flightAttrName2" width="100" align="center" sortable="true">出港航班属性名称</th>
				<th field="planDayOfWeek2" width="100" align="center" sortable="true">出港航班计划日期星期数</th>
				<th field="alternateRemarks2" width="100" align="center" sortable="true">出港航班备降备注</th>
				<th field="delayResonsAreaCode2" width="100" align="center" sortable="true">出港航班延误地名编码</th>
				<th field="delayResonsAreaName2" width="100" align="center" sortable="true">出港航班延误地名名称</th>
				<th field="alternateAreaCode2" width="100" align="center" sortable="true">出港航班备降地点编码</th>
				<th field="alternateAreaName2" width="100" align="center" sortable="true">出港航班备降地点名称</th>
				<th field="cancelFlightResons2" width="100" align="center" sortable="true">出港航班取消原因</th>
				<th field="isExtraFlight2" width="100" align="center" sortable="true">出港航班是否外航</th>
				<th field="extraFlightTimePend2" width="100" align="center" sortable="true">出港航班补班时间是否待定</th>
				<th field="urgeBoardTime2" width="100" align="center" sortable="true">出港航班催促登机时间</th>
				<th field="planBoardingStarttime2" width="100" align="center" sortable="true">出港航班开始登机时间</th>
				<th field="planBoardingEndtime2" width="100" align="center" sortable="true">出港航班结束登机时间</th>
				<th field="guestTime2" width="100" align="center" sortable="true">出港航班客齐时间</th>
				<th field="terminalId2" width="100" align="center" sortable="true">出港航班航站楼ID</th>
				<th field="terminal2" width="100" align="center" sortable="true">出港航班航站楼</th>
				<th field="turnBackTime2" width="100" align="center" sortable="true">出港航班返航时间</th>
				<th field="createBy.id" width="100" align="center" sortable="true">创建人</th>
				<th field="createDate" width="100" align="center" sortable="true">创建时间</th>
				<th field="updateBy.id" width="100" align="center" sortable="true">更新人</th>
				<th field="updateDate" width="100" align="center" sortable="true">更新时间</th>
				<th field="remarks" width="100" align="center" sortable="true">备注</th>
				<th field="delFlag" width="100" align="center" formatter="globalFormat" sortable="true">删除标记</th>
				<th field="planDate" width="100" align="center" sortable="true">计划日期</th>
				<th field="agentCode" width="100" align="center" sortable="true">代理人编号</th>
				<th field="agentName" width="100" align="center" sortable="true">代理人名称</th>
				<th field="aircraftNum" width="100" align="center" sortable="true">飞机号</th>
				<th field="aircraftTypeCode" width="100" align="center" sortable="true">机型编号</th>
				<th field="aircraftTypeName" width="100" align="center" sortable="true">机型名称</th>
				<th field="safecuardTypeCode" width="100" align="center" sortable="true">保障类型编码</th>
				<th field="safecuardTypeName" width="100" align="center" sortable="true">保障类型名称</th>
				<th field="travelLine" width="100" align="center" sortable="true">航线</th>
				<th field="delayStatus" width="100" align="center" sortable="true">航班延误状态</th>
				<th field="cancelFlightStatus" width="100" align="center" sortable="true">航班取消状态</th>
				<th field="ext1" width="100" align="center" sortable="true">扩展1</th>
				<th field="ext2" width="100" align="center" sortable="true">扩展2</th>
				<th field="ext3" width="100" align="center" sortable="true">扩展3</th>
				<th field="ext4" width="100" align="center" sortable="true">扩展4</th>
				<th field="ext5" width="100" align="center" sortable="true">扩展5</th>
				<th field="ext6" width="100" align="center" sortable="true">扩展6</th>
				<th field="ext7" width="100" align="center" sortable="true">扩展7</th>
				<th field="ext8" width="100" align="center" sortable="true">扩展8</th>
				<th field="ext9" width="100" align="center" sortable="true">扩展9</th>
				<th field="ext10" width="100" align="center" sortable="true">扩展10</th>
				<th field="delayStatus2" width="100" align="center" sortable="true">航班延误状态</th>
				<th field="cancelFlightStatus2" width="100" align="center" sortable="true">航班取消状态</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/ams/flightDynamicPairHistory/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>进港共享航班号：</td>
                    <td>
						<input type="text" name="shareFlightNum" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班性质编号：</td>
                    <td>
						<input type="text" name="flightNatureCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班性质名称：</td>
                    <td>
						<input type="text" name="flightNatureName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班进出港类型编码：</td>
                    <td>
						<input type="text" name="inoutTypeCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>进港航班进出港类型名称：</td>
                    <td>
						<input type="text" name="inoutTypeName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班进出港状态编号：</td>
                    <td>
						<input type="text" name="inoutStatusCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班进出港状态名称：</td>
                    <td>
						<input type="text" name="inoutStatusName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班起飞地编码：</td>
                    <td>
						<input type="text" name="departureAirportCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>进港航班起飞地三字码：</td>
                    <td>
						<input type="text" name="departureAirportThree" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班起飞地名称：</td>
                    <td>
						<input type="text" name="departureAirportName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班经停地1编号：</td>
                    <td>
						<input type="text" name="passAirport1Code" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班经停地1名称：</td>
                    <td>
						<input type="text" name="passAirport1Name" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>进港航班经停地2编号：</td>
                    <td>
						<input type="text" name="passAirport2Code" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班经停地2名称：</td>
                    <td>
						<input type="text" name="passAirport2Name" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班目的地编号：</td>
                    <td>
						<input type="text" name="arrivalAirportCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班目的地三字码：</td>
                    <td>
						<input type="text" name="arrivalAirportThree" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>进港航班目的地名称：</td>
                    <td>
						<input type="text" name="arrivalAirportName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班计划起飞时间：</td>
                    <td>
						<input name="departurePlanTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.departurePlanTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>进港航班计划到达时间：</td>
                    <td>
						<input name="arrivalPlanTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.arrivalPlanTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>进港航班预计起飞时间：</td>
                    <td>
						<input name="etd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.etd}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                </tr>
                <tr>
                    <td>进港航班预计到达时间：</td>
                    <td>
						<input name="eta" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.eta}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>进港航班实际起飞时间：</td>
                    <td>
						<input name="atd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.atd}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>进港航班实际到达时间：</td>
                    <td>
						<input name="ata" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.ata}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>进港航班行程时间：</td>
                    <td>
						<input type="text" name="travelTime" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>进港航班行李转盘：</td>
                    <td>
						<input type="text" name="carouselNum" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班VIP：</td>
                    <td>
						<input type="text" name="vip" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班延误原因编号：</td>
                    <td>
						<input type="text" name="delayResonsCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班延误原因名称：</td>
                    <td>
						<input type="text" name="delayResonsName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>进港航班延误备注：</td>
                    <td>
						<input type="text" name="delayResonsRemark" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班登机口编号：</td>
                    <td>
						<input type="text" name="gateCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班登机口名称：</td>
                    <td>
						<input type="text" name="gateName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班登机开始时间：</td>
                    <td>
						<input name="boardingStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.boardingStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                </tr>
                <tr>
                    <td>进港航班登机结束时间：</td>
                    <td>
						<input name="boardingEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.boardingEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>进港航班值机岛编号：</td>
                    <td>
						<input type="text" name="checkinIslandCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班值机岛名称：</td>
                    <td>
						<input type="text" name="checkinIslandName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班值机站台编号：</td>
                    <td>
						<input type="text" name="checkinCounterCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>进港航班值机站台名称：</td>
                    <td>
						<input type="text" name="checkinCounterName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班值机开始时间：</td>
                    <td>
						<input name="checkinStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.checkinStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>进港航班值机结束时间：</td>
                    <td>
						<input name="checkinEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.checkinEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>进港航班是否延误：</td>
                    <td>
						<input type="text" name="delayTimePend" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>进港航班状态编码：</td>
                    <td>
						<input type="text" name="status" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班状态名称：</td>
                    <td>
						<input type="text" name="statusName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班机型参数ID：</td>
                    <td>
						<input type="text" name="flightPropertiesId" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班航班属性编码：</td>
                    <td>
						<input type="text" name="flightAttrCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>进港航班航班属性名称：</td>
                    <td>
						<input type="text" name="flightAttrName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班计划日期星期数：</td>
                    <td>
						<input type="text" name="planDayOfWeek" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班备降备注：</td>
                    <td>
						<input type="text" name="alternateRemarks" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班延误地点编码：</td>
                    <td>
						<input type="text" name="delayResonsAreaCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>进港航班延误地点名称：</td>
                    <td>
						<input type="text" name="delayResonsAreaName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班备降地点编码：</td>
                    <td>
						<input type="text" name="alternateAreaCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班备降地点名称：</td>
                    <td>
						<input type="text" name="alternateAreaName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班取消原因：</td>
                    <td>
						<input type="text" name="cancelFlightResons" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>进港航班是否外航：</td>
                    <td>
						<input type="text" name="isExtraFlight" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班补班时间是否待定：</td>
                    <td>
						<input type="text" name="extraFlightTimePend" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班催促登机时间：</td>
                    <td>
						<input name="urgeBoardTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.urgeBoardTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>进港航班登机开始时间：</td>
                    <td>
						<input name="planBoardingStarttime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.planBoardingStarttime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                </tr>
                <tr>
                    <td>进港航班登机结束时间：</td>
                    <td>
						<input name="planBoardingEndtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.planBoardingEndtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>进港航班客齐时间：</td>
                    <td>
						<input name="guestTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.guestTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>进港航班航站楼ID：</td>
                    <td>
						<input type="text" name="terminalId" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进港航班航站楼：</td>
                    <td>
						<input type="text" name="terminal" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>进港航班返航时间：</td>
                    <td>
						<input name="turnBackTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.turnBackTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>出港航班动态ID：</td>
                    <td>
						<input type="text" name="flightDynimicId2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班号：</td>
                    <td>
						<input type="text" name="flightNum2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班机位号：</td>
                    <td>
						<input type="text" name="placeNum2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>出港航班航空公司ID：</td>
                    <td>
						<input type="text" name="flightCompanyId2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班航空公司代码：</td>
                    <td>
						<input type="text" name="flightCompanyCode2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班航空公司名称：</td>
                    <td>
						<input type="text" name="flightCompanyName2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班共享航班号：</td>
                    <td>
						<input type="text" name="shareFlightNum2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>出港航班性质编号：</td>
                    <td>
						<input type="text" name="flightNatureCode2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班性质名称：</td>
                    <td>
						<input type="text" name="flightNatureName2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班进出港类型编码：</td>
                    <td>
						<input type="text" name="inoutTypeCode2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班进出港类型名称：</td>
                    <td>
						<input type="text" name="inoutTypeName2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>出港航班进出港状态编号：</td>
                    <td>
						<input type="text" name="inoutStatusCode2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班进出港状态名称：</td>
                    <td>
						<input type="text" name="inoutStatusName2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班起飞地编码：</td>
                    <td>
						<input type="text" name="departureAirportCode2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班起飞地三字码：</td>
                    <td>
						<input type="text" name="departureAirportThree2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>出港航班起飞地名称：</td>
                    <td>
						<input type="text" name="departureAirportName2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班经停地1编号：</td>
                    <td>
						<input type="text" name="passAirport1Code2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班经停地1名称：</td>
                    <td>
						<input type="text" name="passAirport1Name2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班经停地2编号：</td>
                    <td>
						<input type="text" name="passAirport2Code2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>出港航班经停地2名称：</td>
                    <td>
						<input type="text" name="passAirport2Name2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班目的地编号：</td>
                    <td>
						<input type="text" name="arrivalAirportCode2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班目的地三字码：</td>
                    <td>
						<input type="text" name="arrivalAirportThree2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班目的地名称：</td>
                    <td>
						<input type="text" name="arrivalAirportName2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>出港航班计划起飞时间：</td>
                    <td>
						<input name="departurePlanTime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.departurePlanTime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>出港航班计划到达时间：</td>
                    <td>
						<input name="arrivalPlanTime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.arrivalPlanTime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>出港航班预计起飞时间：</td>
                    <td>
						<input name="etd2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.etd2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>出港航班预计到达时间：</td>
                    <td>
						<input name="eta2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.eta2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                </tr>
                <tr>
                    <td>出港航班实际起飞时间：</td>
                    <td>
						<input name="atd2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.atd2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>出港航班实际到达时间：</td>
                    <td>
						<input name="ata2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.ata2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>出港航班行程时间：</td>
                    <td>
						<input type="text" name="travelTime2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班行李转盘：</td>
                    <td>
						<input type="text" name="carouselNum2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>出港航班VIP：</td>
                    <td>
						<input type="text" name="vip2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班延误原因编号：</td>
                    <td>
						<input type="text" name="delayResonsCode2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班延误原因名称：</td>
                    <td>
						<input type="text" name="delayResonsName2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班延误备注：</td>
                    <td>
						<input type="text" name="delayResonsRemark2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>出港航班登机口编号：</td>
                    <td>
						<input type="text" name="gateCode2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班登机口名称：</td>
                    <td>
						<input type="text" name="gateName2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班登机开始时间：</td>
                    <td>
						<input name="boardingStartTime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.boardingStartTime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>出港航班登机结束时间：</td>
                    <td>
						<input name="boardingEndTime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.boardingEndTime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                </tr>
                <tr>
                    <td>出港航班值机岛编号：</td>
                    <td>
						<input type="text" name="checkinIslandCode2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班值机岛名称：</td>
                    <td>
						<input type="text" name="checkinIslandName2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班值机站台编号：</td>
                    <td>
						<input type="text" name="checkinCounterCode2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班值机站台名称：</td>
                    <td>
						<input type="text" name="checkinCounterName2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>出港航班值机开始时间：</td>
                    <td>
						<input name="checkinStartTime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.checkinStartTime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>出港航班值机结束时间：</td>
                    <td>
						<input name="checkinEndTime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.checkinEndTime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>出港航班是否延误：</td>
                    <td>
						<input type="text" name="delayTimePend2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班状态编码：</td>
                    <td>
						<input type="text" name="status2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>出港航班状态名称：</td>
                    <td>
						<input type="text" name="statusName2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班机型参数ID：</td>
                    <td>
						<input type="text" name="flightPropertiesId2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班属性编码：</td>
                    <td>
						<input type="text" name="flightAttrCode2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班属性名称：</td>
                    <td>
						<input type="text" name="flightAttrName2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>出港航班计划日期星期数：</td>
                    <td>
						<input type="text" name="planDayOfWeek2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班备降备注：</td>
                    <td>
						<input type="text" name="alternateRemarks2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班延误地名编码：</td>
                    <td>
						<input type="text" name="delayResonsAreaCode2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班延误地名名称：</td>
                    <td>
						<input type="text" name="delayResonsAreaName2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>出港航班备降地点编码：</td>
                    <td>
						<input type="text" name="alternateAreaCode2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班备降地点名称：</td>
                    <td>
						<input type="text" name="alternateAreaName2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班取消原因：</td>
                    <td>
						<input type="text" name="cancelFlightResons2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班是否外航：</td>
                    <td>
						<input type="text" name="isExtraFlight2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>出港航班补班时间是否待定：</td>
                    <td>
						<input type="text" name="extraFlightTimePend2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班催促登机时间：</td>
                    <td>
						<input name="urgeBoardTime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.urgeBoardTime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>出港航班开始登机时间：</td>
                    <td>
						<input name="planBoardingStarttime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.planBoardingStarttime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>出港航班结束登机时间：</td>
                    <td>
						<input name="planBoardingEndtime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.planBoardingEndtime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                </tr>
                <tr>
                    <td>出港航班客齐时间：</td>
                    <td>
						<input name="guestTime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.guestTime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>出港航班航站楼ID：</td>
                    <td>
						<input type="text" name="terminalId2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班航站楼：</td>
                    <td>
						<input type="text" name="terminal2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>出港航班返航时间：</td>
                    <td>
						<input name="turnBackTime2" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.turnBackTime2}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                </tr>
                <tr>
                    <td>创建人：</td>
                    <td>
						<input type="text" name="createBy.id" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>创建时间：</td>
                    <td>
						<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>更新人：</td>
                    <td>
						<input type="text" name="updateBy.id" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>更新时间：</td>
                    <td>
						<input name="updateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                </tr>
                <tr>
                    <td>备注：</td>
                    <td>
						<form:textarea path="remarks" htmlEscape="false" rows="4" data-options="required:true,maxlength='500'" class="textarea easyui-validatebox"/>
					</td>
                    <td>删除标记：</td>
                    <td>
						<input type="radio" name="delFlag" value="1" checked >&nbsp;是
						<input type="radio" name="delFlag" value="0" >&nbsp;不是
					</td>
                    <td>计划日期：</td>
                    <td>
						<input name="planDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightDynamicPairHistory.planDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>代理人编号：</td>
                    <td>
						<input type="text" name="agentCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>代理人名称：</td>
                    <td>
						<input type="text" name="agentName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>飞机号：</td>
                    <td>
						<input type="text" name="aircraftNum" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>机型编号：</td>
                    <td>
						<input type="text" name="aircraftTypeCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>机型名称：</td>
                    <td>
						<input type="text" name="aircraftTypeName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>保障类型编码：</td>
                    <td>
						<input type="text" name="safecuardTypeCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>保障类型名称：</td>
                    <td>
						<input type="text" name="safecuardTypeName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>航线：</td>
                    <td>
						<input type="text" name="travelLine" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>航班延误状态：</td>
                    <td>
						<input type="text" name="delayStatus" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>航班取消状态：</td>
                    <td>
						<input type="text" name="cancelFlightStatus" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>扩展1：</td>
                    <td>
						<input type="text" name="ext1" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>扩展2：</td>
                    <td>
						<input type="text" name="ext2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>扩展3：</td>
                    <td>
						<input type="text" name="ext3" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>扩展4：</td>
                    <td>
						<input type="text" name="ext4" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>扩展5：</td>
                    <td>
						<input type="text" name="ext5" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>扩展6：</td>
                    <td>
						<input type="text" name="ext6" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>扩展7：</td>
                    <td>
						<input type="text" name="ext7" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>扩展8：</td>
                    <td>
						<input type="text" name="ext8" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>扩展9：</td>
                    <td>
						<input type="text" name="ext9" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>扩展10：</td>
                    <td>
						<input type="text" name="ext10" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>航班延误状态：</td>
                    <td>
						<input type="text" name="delayStatus2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>航班取消状态：</td>
                    <td>
						<input type="text" name="cancelFlightStatus2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
            </table>
            <div style="text-align: center; margin-top:15px; margin-right:15px;">
                <a class="easyui-linkbutton" id="btnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
            </div>
        </form>

    </div>
</div>

<script>
    var moduleContext = "flightDynamicPairHistory";
    // 拿到字典Map, key = tableName, value = columnName
           var dict = dictMap([{key: "", value: "nature"},{key: "", value: "yes_no"},{key: "", value: "ed_status"}]),
    // 构建字典与列字段的映射关系
    // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
            mapping = {
            	"@yes_no": ["delFlag"],
            };
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="ams:flightDynamicPairHistory:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="ams:flightDynamicPairHistory:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="ams:flightDynamicPairHistory:del">flagDgDel=true;</shiro:hasPermission>

     $(function () {
    });

	/*
	//自定义验证规则。例如，定义一个 minLength 验证类 验证长度
    $.extend($.fn.validatebox.defaults.rules, {
		minLength: {
			validator: function(value, param){
				return value.length >= param[0];
			},
			message: 'Please enter at least {0} characters.'
		}
	});*/
</script>

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>
</body>