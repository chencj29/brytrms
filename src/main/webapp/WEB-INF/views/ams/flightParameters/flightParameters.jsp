<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>航班状态</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="航班状态" id="datagrid" sortName="statusNum" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="statusNum" width="100" align="center" sortable="true">状态编号</th>
				<th field="statusName" width="100" align="center" sortable="true">状态类型</th>
				<th field="propertyDescription" width="100" align="center" sortable="true">性质描述</th>
				<th field="statusShort" width="100" align="center" sortable="true">状态简称</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/ams/flightParameters/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>状态编号：</td>
                    <td>
						<input type="text" name="statusNum" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>状态类型：</td>
                    <td>
						<%--<input type="text" name="statusName" class="easyui-textbox easyui-validatebox"--%>
							 <%--data-options=""--%>
						<%-->--%>
                        <select name="statusCode" id="statusCode" class="form-control">
                            <c:forEach items="${fns:getDictListByTableNameType('ams_flight_parameters', 'status')}" var="item">
                                <option value="${item.value}">${item.label}</option>
                            </c:forEach>
                        </select>
                         <input type="hidden" name="statusName" value="">
					</td>
                    <td>性质描述：</td>
                    <td>
						<input type="text" name="propertyDescription" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>状态简称：</td>
                    <td>
						<input type="text" name="statusShort" class="easyui-textbox easyui-validatebox"
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
    var moduleContext = "flightParameters";
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="ams:flightParameters:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="ams:flightParameters:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="ams:flightParameters:del">flagDgDel=true;</shiro:hasPermission>

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

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGridAms.js"></script>
</body>