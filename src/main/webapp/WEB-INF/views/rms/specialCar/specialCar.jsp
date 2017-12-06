<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>特殊车辆基础信息</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="特殊车辆基础信息" id="datagrid" sortName="resourceName" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="resourceName" formatter="myFormat" width="100" align="center" sortable="true">资源名称</th>
				<th field="resourceType" formatter="myFormat" width="100" align="center" sortable="true">资源类型</th>
				<th field="chargeMethod" formatter="myFormat" width="100" align="center" sortable="true">计费方式</th>
				<th field="resourceCount" width="100" align="center" sortable="true">资源数量</th>
				<th field="maintanceDuty" width="100" align="center" sortable="true">维护岗位</th>
				<th field="uesDuty" width="100" align="center" sortable="true">使用岗位</th>
				<th field="aircraftTerminalCode" width="100" align="center" sortable="true">航站楼</th>
				<th field="serviceProvider" width="100" align="center" sortable="true">服务提供者</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:150px;">
        <form action="${ctx}/rms/specialCar/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>资源名称：</td>
                    <td>
						<%--<input type="text" name="resourceName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>--%>
						<select class="easyui-validatebox" id="resourceName" name="resourceName" style="width:130px;"
								data-options="">
						</select>
					</td>
                    <td>资源类型：</td>
                    <td>
						<%--<input type="text" name="resourceType" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>--%>
						<select class="easyui-validatebox" id="resourceType" name="resourceType" style="width:130px;"
								data-options="">
						</select>
					</td>
                    <td>计费方式：</td>
                    <td>
						<%--<input type="text" name="chargeMethod" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>--%>
						<select class="easyui-validatebox" id="chargeMethod" name="chargeMethod" style="width:130px;"
								data-options="">
						</select>
					</td>
                    <td>资源数量：</td>
                    <td>
						<input type="text" name="resourceCount" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>维护岗位：</td>
                    <td>
						<input type="text" name="maintanceDuty" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>使用岗位：</td>
                    <td>
						<input type="text" name="uesDuty" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>航站楼：</td>
                    <td>
						<select class="easyui-validatebox" id="aircraftTerminalCode" name="aircraftTerminalCode" style="width:130px;"
								data-options="">
						</select>
					</td>
                    <td>服务提供者：</td>
                    <td>
						<%--<input type="text" name="serviceProvider" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>--%>
					<select class="easyui-validatebox" id="serviceProvider" name="serviceProvider" style="width:130px;"
							data-options="">
					</select>
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
	var data;

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
		$("#resourceName").combobox({panelHeight: 'auto',panelMaxHeight: '200',//资源型号
			data:_.filter(data, function(d) {return d.categoryType === 'type'}),
			valueField: 'categoryId',
			textField: 'categoryName'
		});

		$("#resourceType").combobox({panelHeight: 'auto',panelMaxHeight: '200',//资源类型
			data:_.filter(data, function(d) {return d.categoryType === 'model'}),
			valueField: 'categoryId',
			textField: 'categoryName'
		});

		$("#chargeMethod").combobox({panelHeight: 'auto',panelMaxHeight: '200',//资源计费方式
			data:_.filter(data, function(d) {return d.categoryType === 'charge'}),
			valueField: 'categoryId',
			textField: 'categoryName'
		});

		//拿到航站楼数据
		$("#aircraftTerminalCode").combobox({panelHeight: 'auto',panelMaxHeight: '200',
			url: ctx + '/rms/airportTerminal/jsonData',
			valueField: 'terminalName',
			textField: 'terminalName'
		});

		//拿到服务提供者数据
		$("#serviceProvider").combobox({panelHeight: 'auto',panelMaxHeight: '200',
			url: ctx + '/rms/serviceProvider/jsonData',
			valueField: 'serviceProviderName',
			textField: 'serviceProviderName'
		});
	});


    var moduleContext = "specialCar";

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
			(field =='resourceType' && d.categoryType =='model' && d.categoryId==value)||
			(field=='chargeMethod' && d.categoryType =='charge' && d.categoryId==value)
		});

		if(result.length>0){
			return result[0].categoryName;
		}else{
			return value;
		}
		//return _.propertyOf()(value);
	}

    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
	<shiro:hasPermission name="rms:specialCar:edit">flagDgEdit=true;</shiro:hasPermission>
	<shiro:hasPermission name="rms:specialCar:insert">flagDgInsert=true;</shiro:hasPermission>
	<shiro:hasPermission name="rms:specialCar:del">flagDgDel=true;</shiro:hasPermission>


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

    //datagrid加载成功时调用  隐藏数据显示
    function loadsuccessFn(data) {
        datagrid.datagrid('doCellTip', {
            onlyShowInterrupt: true, position: 'bottom', maxWidth: '200px',
            tipStyler: {'backgroundColor': '#fff000', borderColor: '#ff0000', boxShadow: '1px 1px 3px #292929'}
        });
    }
</script>

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>
</body>