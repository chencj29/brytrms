<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>航空公司信息表</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="航空公司信息表" id="datagrid" sortName="twoCode" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="twoCode" width="100" align="center" sortable="true">二字码</th>
				<th field="threeCode" width="100" align="center" sortable="true">三字码</th>
				<th field="chineseShort" width="100" align="center" sortable="true">中文简称</th>
				<th field="chineseName" width="100" align="center" sortable="true">中文名称</th>
				<th field="englishName" width="100" align="center" sortable="true">英文名称</th>
				<th field="otherFlight" width="100" align="center" formatter="globalFormat" sortable="true">是否外航</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/flightCompanyInfo/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>二字码：</td>
                    <td>
						<input type="text" name="twoCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>三字码：</td>
                    <td>
						<input type="text" name="threeCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>中文简称：</td>
                    <td>
						<input type="text" name="chineseShort" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>中文名称：</td>
                    <td>
						<input type="text" name="chineseName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>英文名称：</td>
                    <td>
						<input type="text" name="englishName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>是否外航：</td>
                    <td>
						<input type="radio" name="otherFlight" value="1" checked >&nbsp;是
						<input type="radio" name="otherFlight" value="0" >&nbsp;不是
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
    var moduleContext = "flightCompanyInfo";
    // 拿到字典Map, key = tableName, value = columnName
           var dict = dictMap([{key: "", value: "nature"},{key: "", value: "yes_no"},{key: "", value: "ed_status"}]),
    // 构建字典与列字段的映射关系
    // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
            mapping = {
            	"@yes_no": ["otherFlight"],
            };
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];


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

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>
</body>