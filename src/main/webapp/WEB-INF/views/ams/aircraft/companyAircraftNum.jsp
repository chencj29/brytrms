<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>公司机号</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="公司机号" id="datagrid" sortName="flightCompanyCode" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="flightCompanyCode" width="100" align="center" sortable="true">航空公司编码</th>
				<th field="flightCompanyName" width="100" align="center" sortable="true">航空公司名称</th>
				<th field="flightCompanySubCode" width="100" align="center" sortable="true">子航空公司编码</th>
				<th field="flightCompanySubName" width="100" align="center" sortable="true">子公司名称</th>
				<th field="aircraftParametersId" width="100" align="center" sortable="true">飞机参数表ID</th>
				<th field="aircraftModel" width="100" align="center" sortable="true">飞机型号</th>
				<th field="aircraftMainModel" width="100" align="center" sortable="true">飞机主型号</th>
				<th field="aircraftSubModel" width="100" align="center" sortable="true">飞机子型号</th>
				<th field="maxnumTakeoffWeight" width="100" align="center" sortable="true">起飞重量</th>
				<th field="maxnumPlayload" width="100" align="center" sortable="true">业务载重</th>
				<th field="maxnumSeat" width="100" align="center" sortable="true">客座数</th>
				<th field="aircraftTypeCode" width="100" align="center" sortable="true">飞机类型编号</th>
				<th field="aircraftTypeName" width="100" align="center" sortable="true">飞机类型名称</th>
				<th field="aircraftNum" width="100" align="center" sortable="true">飞机号</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/companyAircraftNum/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>航空公司编码：</td>
                    <td>
						<%--<input type="text" name="flightCompanyCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>--%>
						<select class="easyui-validatebox" id="flightCompanyCode" name="flightCompanyCode" style="width:170px;"
								data-options="">
						</select>
					</td>
                    <td>航空公司名称：</td>
                    <td>
						<input type="text" name="flightCompanyName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>子航空公司编码：</td>
                    <td>
						<%--<input type="text" name="flightCompanySubCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>--%>
						<select class="easyui-validatebox" id="flightCompanySubCode" name="flightCompanySubCode" style="width:170px;"
								data-options="">
						</select>
					</td>
                    <td>子公司名称：</td>
                    <td>
						<input type="text" name="flightCompanySubName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>飞机参数表ID：</td>
                    <td>
						<input type="text" name="aircraftParametersId" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>飞机型号：</td>
                    <td>
						<%--<input type="text" name="aircraftModel" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>--%>
						<select class="easyui-validatebox" id="aircraftModel" name="aircraftModel" style="width:170px;"
								data-options="">
						</select>
					</td>
                    <td>飞机主型号：</td>
                    <td>
						<input type="text" name="aircraftMainModel" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>飞机子型号：</td>
                    <td>
						<input type="text" name="aircraftSubModel" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>起飞重量：</td>
                    <td>
						<input type="text" name="maxnumTakeoffWeight" class="easyui-textbox easyui-validatebox"
							
						>
					</td>
                    <td>业务载重：</td>
                    <td>
						<input type="text" name="maxnumPlayload" class="easyui-textbox easyui-validatebox"
							
						>
					</td>
                    <td>客座数：</td>
                    <td>
						<input type="text" name="maxnumSeat" class="easyui-textbox easyui-validatebox"
							
						>
					</td>
                    <td>飞机类型编号：</td>
                    <td>
						<%--<input type="text" name="aircraftTypeCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>--%>
						<select class="easyui-validatebox" id="aircraftTypeCode" name="aircraftTypeCode" style="width:170px;"
								data-options="">
						</select>
					</td>
                </tr>
                <tr>
                    <td>飞机类型名称：</td>
                    <td>
						<input type="text" name="aircraftTypeName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>飞机号：</td>
                    <td>
						<input type="text" name="aircraftNum" class="easyui-textbox easyui-validatebox"
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
    var moduleContext = "companyAircraftNum";
	//获取飞机类型数据
	var dict = _.invert(dictMap([{key: "company_aircraft_num", value: "aircraft_type"}])["company_aircraft_num@aircraft_type"]);
	var aircraftTypeCodeDatas=[];
	for (var p in dict){
		if (typeof(dict[p]) != "function"){
			var o={};o.k=dict[p];o.v=p;o.kv= dict[p]+"-"+p;
			aircraftTypeCodeDatas.push(o);
		}
	}
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
	<shiro:hasPermission name="ams:companyAircraftNum:edit">flagDgEdit=true;</shiro:hasPermission>
	<shiro:hasPermission name="ams:companyAircraftNum:insert">flagDgInsert=true;</shiro:hasPermission>
	<shiro:hasPermission name="ams:companyAircraftNum:del">flagDgDel=true;</shiro:hasPermission>

	var flightCompanyCodeDates,aircraftModelDates,flightCompanySub,copyFCSub;
     $(function () {
		 if(!flightCompanyCodeDates) {
			 $.ajax({
				 url:ctx + '/ams/flightCompanyInfo/jsonData',
				 dataType: "json",
				 async: false,
				 success: function (data) {
					 flightCompanyCodeDates = _.map(data,function(o){
						 var result = {};
						 result.id = o.id;
						 result.twoCode = o.twoCode;
						 result.chineseName = o.chineseName;
						 result.tCodechName = o.twoCode+o.chineseName;
						 return result;
					 });
				 }
			 });
			 $.ajax({
				 url:ctx + '/ams/aircraftParameters/jsonData',
				 dataType: "json",
				 async: false,
				 success: function (data) {
					 aircraftModelDates = _.map(data,function(o){
						 var result = {};
						 result.aircraftModel = o.aircraftModel;// 机型
						 result.aircraftMainModel = o.aircraftMainModel;// 主机型
						 result.aircraftSubModel = o.aircraftSubModel;// 子机型
						 return result;
					 });
				 }
			 });
			 $.ajax({
                 url:ctx + '/ams/flightCompanySubInfo/jsonData?flightCompanyInfoId',
                 dataType: "json",
                 async: false,
				 success:function (data) {
                     flightCompanySub = _.map(data,function(o){
                         var result = {};
                         result.flightCompanyInfoId = o.flightCompanyInfoId;// 航空公司 ID
                         result.chineseName = o.chineseName;// 子公司名称
						 result.subCode = o.subCode; // 子公司代码
                         result.threeCode = o.threeCode;// 三字码
                         return result;
                     });
                 }
			 });
		 }
		 //拿到航空公司数据
		 $("#flightCompanyCode").combobox({
			 panelHeight: 'auto',panelMaxHeight: '200',
			 data: flightCompanyCodeDates,
			 valueField: 'twoCode',
			 textField: 'tCodechName',
			 onSelect : function(rec){ //改变单选值时，通过选中值后回填内容
				 form.form('load',{flightCompanyName:rec.chineseName});
                 copyFCSub = _.filter(flightCompanySub,sub=> sub.flightCompanyInfoId == rec.id);
                 $("#flightCompanySubCode").combobox({
                     panelHeight: 'auto',panelMaxHeight: '200',
                     data: copyFCSub ||flightCompanySub,
                     valueField: 'subCode',
                     textField: 'subCode',
                     onSelect : function(rec){ //改变单选值时，通过选中值后回填内容
                         form.form('load',{flightCompanySubName:rec.chineseName});
                     }
                 });
			 }
		 });
		 $("#flightCompanySubCode").combobox({
			 panelHeight: 'auto',panelMaxHeight: '200',
			 data: copyFCSub ||flightCompanySub,
			 valueField: 'subCode',
			 textField: 'subCode',
			 onSelect : function(rec){ //改变单选值时，通过选中值后回填内容
				 form.form('load',{flightCompanySubName:rec.chineseName});
			 }
		 });
		 $("#aircraftModel").combobox({
			 panelHeight: 'auto',panelMaxHeight: '200',
			 data: aircraftModelDates,
			 valueField: 'aircraftModel',
			 textField: 'aircraftModel',
			 onSelect : function(rec){ //改变单选值时，通过选中值后回填内容
				 form.form('load',{aircraftMainModel:rec.aircraftMainModel,aircraftSubModel:rec.aircraftSubModel});
			 }
		 });
		 $("#aircraftTypeCode").combobox({
					 panelHeight: 'auto',panelMaxHeight: '200',
					 data: aircraftTypeCodeDatas,
					 valueField: 'k',
					 textField: 'kv',
					 onSelect : function(rec){ //改变单选值时，通过选中值后回填内容
						 form.form('load',{aircraftTypeName:rec.v});
					 }
				 });
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

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGridAms.js"></script>
</body>