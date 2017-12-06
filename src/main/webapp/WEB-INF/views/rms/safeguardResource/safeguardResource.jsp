<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>保障资源表</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="保障资源表" id="datagrid" sortName="serviceProviderCode" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="serviceProviderCode" width="100" align="center" sortable="true">服务提供者编号</th>
				<th field="serviceProviderName" width="100" align="center" sortable="true">服务提供者名称</th>
				<th field="resourceName" formatter="myFormat" width="100" align="center" sortable="true">资源名称</th>
				<th field="resourceTypeCode" width="100" align="center" sortable="true">资源型号编号</th>
				<th field="resourceTypeName" formatter="myFormat" width="100" align="center" sortable="true">资源型号名称</th>
				<th field="billingModelsCode" width="100" align="center" sortable="true">计费方式编号</th>
				<th field="billingModelsName" formatter="myFormat" width="100" align="center" sortable="true">计费方式名称</th>
				<th field="resourceNum" width="100" align="center" sortable="true">资源数量</th>
				<th field="maintainedStation" width="100" align="center" sortable="true">维护岗位</th>
				<th field="useStation" width="100" align="center" sortable="true">使用岗位</th>
				<th field="carName" width="100" align="center" sortable="true">车辆名称</th>
				<th field="carModelCode" width="100" align="center" sortable="true">车辆类型编号</th>
				<th field="carModelName" width="100" align="center" sortable="true">车辆类型名称</th>
				<th field="carStatus" width="100" align="center" formatter="globalFormat" sortable="true">车辆状态</th>
				<%--<th field="redundance1" width="100" align="center" sortable="true">冗余1</th>
				<th field="redundance2" width="100" align="center" sortable="true">冗余2</th>
				<th field="redundance3" width="100" align="center" sortable="true">冗余3</th>
				<th field="redundance4" width="100" align="center" sortable="true">冗余4</th>
				<th field="redundance5" width="100" align="center" sortable="true">冗余5</th>--%>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/safeguardResource/save" id="modifyForm">
            <input type="hidden" name="id">
			<input type="hidden" id="serviceProviderCode" name="serviceProviderCode">
			<input type="hidden" id="resourceTypeCode" name="resourceTypeCode">
			<input type="hidden" id="billingModelsCode" name="billingModelsCode">
            <table width="100%" class="formtable">
                <tr>
                    <td>服务提供者名称：</td>
                    <td>
						<select class="easyui-validatebox" id="serviceProviderName" name="serviceProviderName" style="width:130px;"
								data-options="">
						</select>
					</td>
                    <td>资源名称：</td>
                    <td>
						<%--<input type="text" name="resourceName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>--%>
						<select class="easyui-validatebox" id="resourceName" name="resourceName" style="width:130px;"
								data-options="">
						</select>
					</td>
                    <td>资源型号名称：</td>
                    <td>
						<select class="easyui-validatebox" id="resourceTypeName" name="resourceTypeName" style="width:130px;"
								data-options="">
						</select>
					</td>
                    <td>计费方式名称：</td>
                    <td>
						<select class="easyui-validatebox" id="billingModelsName" name="billingModelsName" style="width:130px;"
								data-options="">
						</select>
					</td>
                </tr>
                <tr>
					<td>资源数量：</td>
					<td>
						<input type="text" name="resourceNum" class="easyui-textbox easyui-validatebox"
							   data-options=""
						>
					</td>
                    <td>维护岗位：</td>
                    <td>
						<input type="text" name="maintainedStation" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>使用岗位：</td>
                    <td>
						<input type="text" name="useStation" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>车辆名称：</td>
                    <td>
						<input type="text" name="carName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>

					<td>车辆类型编号：</td>
					<td>
						<input type="text" name="carModelCode" class="easyui-textbox easyui-validatebox"
							   data-options=""
						>
					</td>
                    <td>车辆类型名称：</td>
                    <td>
						<input type="text" name="carModelName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>车辆状态：</td>
                    <td>
						<input type="radio" name="carStatus" value="1" checked >正常
						<input type="radio" name="carStatus" value="2" >维护
						<input type="radio" name="carStatus" value="3" >报废
					</td>
                    <%--<td>冗余1：</td>
                    <td>
						<input type="text" name="redundance1" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>冗余2：</td>
                    <td>
						<input type="text" name="redundance2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>冗余3：</td>
                    <td>
						<input type="text" name="redundance3" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>冗余4：</td>
                    <td>
						<input type="text" name="redundance4" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>冗余5：</td>
                    <td>
						<input type="text" name="redundance5" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>--%>
                </tr>
            </table>
            <div style="text-align: center; margin-top:15px; margin-right:15px;">
                <a class="easyui-linkbutton" id="btnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
            </div>
        </form>

    </div>
</div>

<script>
    var moduleContext = "safeguardResource";
    // 拿到字典Map, key = tableName, value = columnName
           var dict = dictMap([{key: "rms_safeguard_resource", value: "car_status"}]),
    // 构建字典与列字段的映射关系
    // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
            mapping = {
            	"rms_safeguard_resource@car_status": ["carStatus"],
            };
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [],data;
    <shiro:hasPermission name="rms:safeguardResource:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:safeguardResource:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:safeguardResource:del">flagDgDel=true;</shiro:hasPermission>

     $(function () {
		 if(!data){
			 $.ajax({
				 url : ctx + '/rms/resourceCategory/jsonData',
				 async : false,
				 success : function(d){
					 data= d;
				 }
			 });
		 }

		 //拿到资源类别数据
		 $("#resourceName").combobox({//资源型号
			 data:_.filter(data, function(d) {return d.categoryType === 'type'}),
			 valueField: 'categoryId',
			 textField: 'categoryName',
			 panelHeight: 'auto',
			 panelMaxHeight: '200'
		 });

        //拿到服务提供者名称数据
        $("#serviceProviderName").combobox({
            url: ctx + '/rms/serviceProvider/jsonData',
            valueField: 'serviceProviderName',
            textField: 'serviceProviderName',
            panelHeight: 'auto',
            panelMaxHeight: '200',
			onSelect: function (rec) {
				$("#serviceProviderCode").val(rec.id);
			}
        });
        //拿到资源型号名称数据
        $("#resourceTypeName").combobox({  //资源类型
			data:_.filter(data, function(d) {return d.categoryType === 'model'}),
            valueField: 'categoryId',
            textField: 'categoryName',
            panelHeight: 'auto',
            panelMaxHeight: '200',
			onSelect: function (rec) {
				$("#resourceTypeCode").val(rec.categoryId);
			}
        });
        //拿到计费方式名称数据
        $("#billingModelsName").combobox({
			data:_.filter(data, function(d) {return d.categoryType === 'charge'}),
            valueField: 'categoryId',
            textField: 'categoryName',
            panelHeight: 'auto',
            panelMaxHeight: '200',
			onSelect: function (rec) {
				$("#billingModelsCode").val(rec.categoryId);
			}
        });
    });

	function myFormat(value, row, index, field) {
		if(!data){
			$.ajax({
				url : ctx + '/rms/resourceCategory/jsonData',
				async : false,
				success : function(d){
					data= d;
				}
			});
		}

		var result = _.filter(data,function(d){
			return (field=='resourceName' && d.categoryType =='type' && d.categoryId==value)||
					(field =='resourceTypeName' && d.categoryType =='model' && d.categoryId==value)||
					(field=='billingModelsName' && d.categoryType =='charge' && d.categoryId==value)
		});

		if(result.length>0){
			return result[0].categoryName;
		}else{
			return value;
		}
		//return _.propertyOf()(value);
	}
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