<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>航班性质表</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="航班性质表" id="datagrid" sortName="natureNum" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="natureNum" width="100" align="center" sortable="true">性质编号</th>
				<th field="natureName" width="100" align="center" sortable="true">性质名称</th>
				<th field="englishName" width="100" align="center" sortable="true">英文名称</th>
				<th field="sita" width="100" align="center" sortable="true">SITA编码</th>
				<th field="aftn" width="100" align="center" sortable="true">AFTN编码</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/ams/flightNature/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>性质编号：</td>
                    <td>
						<input type="text" name="natureNum" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>性质名称：</td>
                    <td>
						<input type="text" name="natureName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>英文名称：</td>
                    <td>
						<input type="text" name="englishName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>SITA编码：</td>
                    <td>
						<input type="text" name="sita" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>AFTN编码：</td>
                    <td>
						<input type="text" name="aftn" class="easyui-textbox easyui-validatebox"
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
    var moduleContext = "flightNature";
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="ams:flightNature:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="ams:flightNature:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="ams:flightNature:del">flagDgDel=true;</shiro:hasPermission>

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