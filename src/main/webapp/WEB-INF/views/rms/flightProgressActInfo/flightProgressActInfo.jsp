<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>保障过程时间</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/flightProgressActInfo/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
            </table>
            <div style="text-align: center; margin-top:15px; margin-right:15px;">
                <a class="easyui-linkbutton" id="btnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
            </div>
        </form>

    </div>
</div>

<script>
    var moduleContext = "flightProgressActInfo";
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:flightProgressActInfo:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:flightProgressActInfo:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:flightProgressActInfo:del">flagDgDel=true;</shiro:hasPermission>

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