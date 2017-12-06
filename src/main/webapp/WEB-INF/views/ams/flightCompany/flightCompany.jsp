<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>航空公司联系信息</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="航空公司联系信息" id="datagrid" sortName="flightCompanyName" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="flightCompanyName" width="100" align="center" sortable="true">航空公司</th>
				<th field="contactDept" width="100" align="center" sortable="true">联系部门</th>
				<th field="contactPersonal" width="100" align="center" sortable="true">联系人</th>
				<th field="duty" width="100" align="center" sortable="true">职务</th>
				<th field="sex" width="100" align="center" formatter="globalFormat" sortable="true">性别</th>
				<th field="tel" width="100" align="center" sortable="true">联系电话</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/ams/flightCompany/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>航空公司：</td>
                    <td>
						<select class="easyui-validatebox" id="flightCompanyName" name="flightCompanyName" style="width:130px;"
								data-options="">
						</select>
					</td>
                    <td>联系部门：</td>
                    <td>
						<select class="easyui-validatebox" id="contactDept" name="contactDept" style="width:130px;"
								data-options="">
						</select>
					</td>
                    <td>联系人：</td>
                    <td>
						<input type="text" name="contactPersonal" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>职务：</td>
                    <td>
						<input type="text" name="duty" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>性别：</td>
                    <td>
						<input type="radio" name="sex" value="1" checked >&nbsp;男
						<input type="radio" name="sex" value="0" >&nbsp;女					</td>
                    <td>联系电话：</td>
                    <td>
						<input type="text" name="tel" class="easyui-textbox easyui-validatebox"
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
    var moduleContext = "flightCompany";
    // 拿到字典Map, key = tableName, value = columnName
           var dict = dictMap([{key: "", value: "sex"}]),
    // 构建字典与列字段的映射关系
    // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
            mapping = {
            	"@sex": ["sex"]
            };
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];


     $(function () {
        //拿到航空公司数据
        $("#flightCompanyName").combobox({panelHeight: 'auto',panelMaxHeight: '200',
            url: ctx + '/rms/airportTerminal/jsonData',
            valueField: 'terminalName',
            textField: 'terminalName'
        });
        //拿到联系部门数据
        $("#contactDept").combobox({panelHeight: 'auto',panelMaxHeight: '200',
            url: ctx + '/rms/airportTerminal/jsonData',
            valueField: 'terminalName',
            textField: 'terminalName'
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