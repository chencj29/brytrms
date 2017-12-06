<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>资源模拟分配详情</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="资源模拟分配详情" id="datagrid" sortName="remarks" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="remarks" width="100" align="center" sortable="true">备注</th>
				<th field="createBy.id" width="100" align="center" sortable="true">创建人</th>
				<th field="createDate" width="100" align="center" sortable="true">创建时间</th>
				<th field="updateBy.id" width="100" align="center" sortable="true">更新人</th>
				<th field="updateDate" width="100" align="center" sortable="true">update_date</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/resourceMockDistDetail/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>备注：</td>
                    <td>
						<form:textarea path="remarks" htmlEscape="false" rows="4" data-options="required:true,maxlength='500'" class="textarea easyui-validatebox"/>
					</td>
                    <td>创建人：</td>
                    <td>
						<input type="text" name="createBy.id" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>创建时间：</td>
                    <td>
						<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
							value="<fmt:formatDate value="${resourceMockDistDetail.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>更新人：</td>
                    <td>
						<input type="text" name="updateBy.id" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>update_date：</td>
                    <td>
						<input name="updateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
							value="<fmt:formatDate value="${resourceMockDistDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
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
    var moduleContext = "resourceMockDistDetail";
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:resourceMockDistDetail:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:resourceMockDistDetail:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:resourceMockDistDetail:del">flagDgDel=true;</shiro:hasPermission>

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