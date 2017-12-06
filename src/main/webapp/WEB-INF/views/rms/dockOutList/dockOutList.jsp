<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>航班出仓机单信息</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="航班出仓机单信息" id="datagrid" sortName="hopperNum" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
                <th field="flightDate" width="100" align="center" sortable="true">航班日期</th>
                <th field="flightNum" width="100" align="center" sortable="true">航班号</th>
                <th field="hopperNum" width="100" align="center" sortable="true">斗号</th>
                <th field="cargoName" width="100" align="center" sortable="true">货物名称</th>
                <th field="cargoNum" width="100" align="center" sortable="true">件数</th>
                <th field="weight" width="100" align="center" sortable="true">重量</th>
                <th field="specification" width="100" align="center" sortable="true">规格</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/dockOutList/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>航班日期：</td>
                    <td>
                        <input name="flightDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                               value="<fmt:formatDate value="${dockOutList.flightDate}" pattern="yyyy-MM-dd"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                    </td>
                    <td>航班号：</td>
                    <td>
                        <input type="text" name="flightNum" class="easyui-textbox easyui-validatebox"
                               data-options=""
                        >
                        <%--<select class="easyui-validatebox" id="flightNum" name="flightNum" style="width:130px;"
                        data-options="">
                        </select>--%>
                    </td>
                    <td>斗号：</td>
                    <td>
						<input type="text" name="hopperNum" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>货物名称：</td>
                    <td>
						<input type="text" name="cargoName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>件数：</td>
                    <td>
                        <input type="text" name="cargoNum" class="easyui-numberbox"
                               data-options=""
                        >
                    </td>
                    <td>重量：</td>
                    <td>
                        <input type="text" name="weight" class="easyui-numberbox" precision="2" min="0.01" max="9999.99"

                        >
                    </td>
                    <td>规格：</td>
                    <td>
						<input type="text" name="specification" class="easyui-textbox easyui-validatebox"
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
    var moduleContext = "dockOutList";

    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:dockOutList:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:dockOutList:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:dockOutList:del">flagDgDel=true;</shiro:hasPermission>


     $(function () {
        //拿到航班号数据
        /*$("#flightNum").combobox({panelHeight: 'auto',panelMaxHeight: '200',
            url: ctx + '/rms/airportTerminal/jsonData',
            valueField: 'terminalName',
            textField: 'terminalName'
        });*/
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