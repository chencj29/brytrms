<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>机型参数</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="机型参数" id="datagrid" sortName="aircraftModel" sortOrder="asc" data-options="fitColumns:false, fit:true">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="aircraftModel" width="80" align="center" sortable="true">机型</th>
				<th field="aircraftMainModel" width="80" align="center" sortable="true">主机型</th>
				<th field="aircraftSubModel" width="80" align="center" sortable="true">子机型</th>
				<th field="useLift" width="60" align="center" formatter="globalFormat">是否<br/>备用梯</th>
				<th field="useBridge" width="60" align="center" formatter="globalFormat">是否<br/>用桥</th>
				<th field="powerTypeCode" width="80" align="center" sortable="true">电源供应方式<br/>编号</th>
				<th field="powerTypeName" width="80" align="center" sortable="true">电源供应方式<br/>名称</th>
				<th field="fuelTypeCode" width="80" align="center" sortable="true">燃料补充方式<br/>编号</th>
				<th field="fuelTypeName" width="80" align="center" sortable="true">燃料补充方式<br/>名称</th>
				<th field="maxnumSeat" width="80" align="center" sortable="true">最大座位数</th>
				<th field="maxnumCargoes" width="80" align="center" sortable="true">最大载货量</th>
				<th field="maxnumAmount" width="80" align="center" sortable="true">最大加油量</th>
				<th field="wingspan" width="80" align="center" sortable="true">翼展</th>
				<th field="fuselageLength" width="80" align="center" sortable="true">机身长</th>
				<th field="frontWheelLength" width="80" align="center" sortable="true">鼻轮前长</th>
				<th field="fuselageHeight" width="80" align="center" sortable="true">高度</th>
				<th field="aircraftWeight" width="80" align="center" sortable="true">空重</th>
				<th field="zeroFuelWeight" width="80" align="center" sortable="true">无油重量</th>
				<th field="wheelsWeight" width="80" align="center" sortable="true">着陆重量</th>
				<th field="maxnumPlayload" width="80" align="center" sortable="true">最大业栽</th>
				<th field="maxnumTakeoffWeight" width="100" align="center" sortable="true">最大起飞重量</th>
				<th field="cruisingAltitude" width="80" align="center" sortable="true">巡航高度</th>
				<th field="maxnumRange" width="80" align="center" sortable="true">最大航程</th>
				<th field="runwayLength" width="80" align="center" sortable="true">跑道长度</th>
				<th field="acnRigid" width="80" align="center" sortable="true">ACN刚度</th>
				<th field="acnFlexible" width="80" align="center" sortable="true">ACN柔性</th>
				<th field="engineNum" width="80" align="center" sortable="true">发动机<br/>台数</th>
				<th field="engineType" width="80" align="center" sortable="true">发动机<br/>型号</th>
				<th field="engineManufacturer" width="80" align="center" sortable="true">生产商</th>
				<th field="lostofStopTime" width="100" align="center" sortable="true">最少停留时间</th>
				<th field="articraftTypeCode" width="80" align="center" sortable="true">机型分类<br/>编号</th>
				<th field="articraftTypeName" width="80" align="center" sortable="true">机型分类<br/>名称</th>
				<th field="isbigtype" width="80" align="center" formatter="globalFormat">是否<br/>最大机型</th>
				<th field="arrationType" width="80" align="center" sortable="true">供气方式</th>
				<th field="remarks" width="80" align="center" sortable="true">显示机型</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/aircraftParameters/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>机型：</td>
                    <td>
						<input type="text" name="aircraftModel" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>主机型：</td>
                    <td>
						<input type="text" name="aircraftMainModel" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>子机型：</td>
                    <td>
						<input type="text" name="aircraftSubModel" class="easyui-textbox easyui-validatebox"
							 data-options="novalidate:true"
						>
					</td>
                    <td>是否备用梯：</td>
                    <td>
						<input type="radio" name="useLift" value="1" checked >&nbsp;是
						<input type="radio" name="useLift" value="0" >&nbsp;不是
					</td>
                </tr>
                <tr>
                    <td>是否用桥：</td>
                    <td>
						<input type="radio" name="useBridge" value="1" checked >&nbsp;是
						<input type="radio" name="useBridge" value="0" >&nbsp;不是
					</td>
                    <td>电源供应方式编号：</td>
                    <td>
						<input type="text" name="powerTypeCode" class="easyui-textbox easyui-validatebox"
							 data-options="novalidate:true"
						>
					</td>
                    <td>电源供应方式名称：</td>
                    <td>
						<input type="text" name="powerTypeName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>燃料补充方式编号：</td>
                    <td>
						<input type="text" name="fuelTypeCode" class="easyui-textbox easyui-validatebox"
							 data-options="novalidate:true"
						>
					</td>
                </tr>
                <tr>
                    <td>燃料补充方式名称：</td>
                    <td>
						<input type="text" name="fuelTypeName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>最大座位数：</td>
                    <td>
						<input type="text" name="maxnumSeat" class="easyui-textbox easyui-validatebox"
							 data-options="novalidate:true"
						>
					</td>
                    <td>最大载货量：</td>
                    <td>
						<input type="text" name="maxnumCargoes" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                    <td>最大加油量：</td>
                    <td>
						<input type="text" name="maxnumAmount" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                </tr>
                <tr>
                    <td>翼展：</td>
                    <td>
						<input type="text" name="wingspan" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                    <td>机身长：</td>
                    <td>
						<input type="text" name="fuselageLength" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                    <td>鼻轮前长：</td>
                    <td>
						<input type="text" name="frontWheelLength" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                    <td>高度：</td>
                    <td>
						<input type="text" name="fuselageHeight" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                </tr>
                <tr>
                    <td>空重：</td>
                    <td>
						<input type="text" name="aircraftWeight" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                    <td>无油重量：</td>
                    <td>
						<input type="text" name="zeroFuelWeight" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                    <td>着陆重量：</td>
                    <td>
						<input type="text" name="wheelsWeight" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                    <td>最大业栽：</td>
                    <td>
						<input type="text" name="maxnumPlayload" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                </tr>
                <tr>
                    <td>最大起飞重量：</td>
                    <td>
						<input type="text" name="maxnumTakeoffWeight" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                    <td>巡航高度：</td>
                    <td>
						<input type="text" name="cruisingAltitude" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                    <td>最大航程：</td>
                    <td>
						<input type="text" name="maxnumRange" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                    <td>跑道长度：</td>
                    <td>
						<input type="text" name="runwayLength" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                </tr>
                <tr>
                    <td>ACN刚度：</td>
                    <td>
						<input type="text" name="acnRigid" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                    <td>ACN柔性：</td>
                    <td>
						<input type="text" name="acnFlexible" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                    <td>发动机台数：</td>
                    <td>
						<input type="text" name="engineNum" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                    <td>发动机型号：</td>
                    <td>
						<input type="text" name="engineType" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                </tr>
                <tr>
                    <td>生产商：</td>
                    <td>
						<input type="text" name="engineManufacturer" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                    <td>最少停留时间：</td>
                    <td>
						<%--<input name="lostofStopTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${aircraftParameters.lostofStopTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>--%>
							<input name="lostofStopTime" class="easyui-textbox easyui-validatebox"
								   data-options="novalidate:true"
							>
					</td>
                    <td>机型分类编号：</td>
                    <td>
						<input type="text" name="articraftTypeCode" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                    <td>机型分类名称：</td>
                    <td>
						<input type="text" name="articraftTypeName" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
                </tr>
                <tr>
                    <td>是否最大机型：</td>
                    <td>
						<input type="radio" name="isbigtype" value="1" checked >&nbsp;是
						<input type="radio" name="isbigtype" value="0" >&nbsp;不是
					</td>
                    <td>供气方式：</td>
                    <td>
						<input type="text" name="arrationType" class="easyui-textbox easyui-validatebox"
							   data-options="novalidate:true"
						>
					</td>
					<td>显示机型：</td>
					<td>
						<input type="text" name="remarks" class="easyui-textbox easyui-validatebox"
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
    var moduleContext = "aircraftParameters";
    // 拿到字典Map, key = tableName, value = columnName
           var dict = dictMap([{key: "", value: "nature"},{key: "", value: "yes_no"},{key: "", value: "ed_status"}]),
    // 构建字典与列字段的映射关系
    // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
            mapping = {
            	"@yes_no": ["useLift","useBridge","isbigtype"]
            };
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
	<shiro:hasPermission name="ams:aircraftParameters:edit">flagDgEdit=true;</shiro:hasPermission>
	<shiro:hasPermission name="ams:aircraftParameters:insert">flagDgInsert=true;</shiro:hasPermission>
	<shiro:hasPermission name="ams:aircraftParameters:del">flagDgDel=true;</shiro:hasPermission>
	flagAutoHight=true;
    isFitColumns = false;
    flagValidate = true; //取消所有必填验证

</script>

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGridAms.js"></script>
</body>