<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>资源类别信息</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="资源类别信息" id="datagrid" sortName="categoryType" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="categoryId" width="100" align="center" sortable="true">ID</th>
				<th field="categoryName" width="100" align="center" sortable="true">名称</th>
				<th field="categoryType" field="aircraftTerminalCode" formatter="globalFormat" width="100" align="center" sortable="true">类别</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:100px;">
        <form action="${ctx}/rms/resourceCategory/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>ID：</td>
                    <td>
						<input type="text" name="categoryId" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>名称：</td>
                    <td>
						<input type="text" name="categoryName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>类别：</td>
                    <td>
						<%--<input type="text" name="categoryType" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>--%>
                        <select class="easyui-validatebox" id="categoryType" name="categoryType" style="width:130px;"
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
    var moduleContext = "resourceCategory";

    // 拿到字典Map, key = tableName, value = columnName
    var dict = dictMap([{key: "RMS_RESOURCE_CATEGORY", value: "categoryType"}]),
        // 构建字典与列字段的映射关系
        // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
            mapping = {
                "RMS_RESOURCE_CATEGORY@categoryType":["categoryType"]
            };
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:resourceCategory:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:resourceCategory:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:resourceCategory:del">flagDgDel=true;</shiro:hasPermission>

     $(function () {
         //拿到类别类型
         $("#categoryType").combobox({panelHeight: 'auto',panelMaxHeight: '200',
             valueField: 'k',
             textField: 'v',
             data:_.map(dict["RMS_RESOURCE_CATEGORY@categoryType"],function(v,k){ return {"k":k,"v":v}})
         });
         //$("#categoryType").combobox('setValue',jsonDate);
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