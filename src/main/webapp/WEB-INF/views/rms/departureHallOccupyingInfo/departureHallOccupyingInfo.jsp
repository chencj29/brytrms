<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>候机厅占用信息</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="候机厅占用信息" id="datagrid" sortName="intlDepartureHallCode" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="intlDepartureHallCode" width="100" align="center" sortable="true">候机厅编号(国际)</th>
				<th field="expectedStartTime" width="100" align="center" sortable="true">预计开始占用时间</th>
				<th field="expectedOverTime" width="100" align="center" sortable="true">预计结束占用时间</th>
				<th field="inteActualStartTime" width="100" align="center" sortable="true">实际开始占用时间(国内)</th>
				<th field="inteActualOverTime" width="100" align="center" sortable="true">实际结束占用时间(国内)</th>
				<th field="intlActualStartTime" width="100" align="center" sortable="true">实际开始占用时间(国际)</th>
				<th field="intlActualOverTime" width="100" align="center" sortable="true">实际结束占用时间(国际)</th>
				<th field="createBy.id" width="100" align="center" sortable="true">创建人</th>
				<th field="createDate" width="100" align="center" sortable="true">创建时间</th>
				<th field="updateBy.id" width="100" align="center" sortable="true">更新人</th>
				<th field="updateDate" width="100" align="center" sortable="true">更新时间</th>
				<th field="remarks" width="100" align="center" sortable="true">备注</th>
				<th field="delFlag" width="100" align="center" formatter="globalFormat" sortable="true">删除标识</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/departureHallOccupyingInfo/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>候机厅编号(国际)：</td>
                    <td>
						<input type="text" name="intlDepartureHallCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>预计开始占用时间：</td>
                    <td>
						<input name="expectedStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${departureHallOccupyingInfo.expectedStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>预计结束占用时间：</td>
                    <td>
						<input name="expectedOverTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${departureHallOccupyingInfo.expectedOverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>实际开始占用时间(国内)：</td>
                    <td>
						<input name="inteActualStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${departureHallOccupyingInfo.inteActualStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                </tr>
                <tr>
                    <td>实际结束占用时间(国内)：</td>
                    <td>
						<input name="inteActualOverTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${departureHallOccupyingInfo.inteActualOverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>实际开始占用时间(国际)：</td>
                    <td>
						<input name="intlActualStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${departureHallOccupyingInfo.intlActualStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>实际结束占用时间(国际)：</td>
                    <td>
						<input name="intlActualOverTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${departureHallOccupyingInfo.intlActualOverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>创建人：</td>
                    <td>
						<input type="text" name="createBy.id" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>创建时间：</td>
                    <td>
						<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${departureHallOccupyingInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>更新人：</td>
                    <td>
						<input type="text" name="updateBy.id" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>更新时间：</td>
                    <td>
						<input name="updateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${departureHallOccupyingInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>备注：</td>
                    <td>
						<form:textarea path="remarks" htmlEscape="false" rows="4" data-options="required:true,maxlength='500'" class="textarea easyui-validatebox"/>
					</td>
                </tr>
                <tr>
                    <td>删除标识：</td>
                    <td>
						<input type="radio" name="delFlag" value="1" checked >&nbsp;是
						<input type="radio" name="delFlag" value="0" >&nbsp;不是
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
    var moduleContext = "departureHallOccupyingInfo";
    // 拿到字典Map, key = tableName, value = columnName
           var dict = dictMap([{key: "", value: "nature"},{key: "", value: "yes_no"},{key: "", value: "ed_status"}]),
    // 构建字典与列字段的映射关系
    // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
            mapping = {
            	"@yes_no": ["delFlag"],
            };
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:departureHallOccupyingInfo:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:departureHallOccupyingInfo:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:departureHallOccupyingInfo:del">flagDgDel=true;</shiro:hasPermission>

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