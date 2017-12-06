<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>服务提供者表</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="服务提供者表" id="datagrid" sortName="serviceProviderNo" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="serviceProviderNo" width="100" align="center" sortable="true">服务提供者编号</th>
				<th field="serviceProviderName" width="100" align="center" sortable="true">服务提供者名称</th>
				<th field="serviceProviderShort" width="100" align="center" sortable="true">服务提供者简称</th>
				<th field="isdefault" width="100" align="center" formatter="globalFormat" sortable="true">是否默认</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:100px;">
        <form action="${ctx}/rms/serviceProvider/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>服务提供者编号：</td>
                    <td>
						<input type="text" name="serviceProviderNo" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>服务提供者名称：</td>
                    <td>
						<input type="text" name="serviceProviderName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>服务提供者简称：</td>
                    <td>
						<input type="text" name="serviceProviderShort" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>是否默认：</td>
                    <td>
						<input type="radio" name="isdefault" value="1" checked >&nbsp;是
						<input type="radio" name="isdefault" value="0" >&nbsp;否
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
    var moduleContext = "serviceProvider";
    // 拿到字典Map, key = tableName, value = columnName
          var dict = dictMap([{key: "", value: "yes_no"}]),
    // 构建字典与列字段的映射关系
    // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
            mapping = {
                "@yes_no": ["isdefault"]
            };
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:serviceProvider:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:serviceProvider:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:serviceProvider:del">flagDgDel=true;</shiro:hasPermission>

     $(function () {
         $('#datagrid').datagrid({onLoadSuccess : function(){
             //不能同时存在多个默认服务提供者
             isFilter = _.compact(_.map($("#datagrid").data('datagrid').filterSource.rows,function(tmp){return tmp.isdefault;})).length>0
         }});
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