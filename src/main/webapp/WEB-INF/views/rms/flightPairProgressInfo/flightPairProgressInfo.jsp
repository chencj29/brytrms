<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>航班运输保障信息</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="航班运输保障信息" id="datagrid" sortName="progressCode" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">id</th>
				<th field="progressCode" width="100" align="center" sortable="true">progress_code</th>
				<th field="planStartTime" width="100" align="center" sortable="true">plan_start_time</th>
				<th field="planOverTime" width="100" align="center" sortable="true">plan_over_time</th>
				<th field="actualStartTime" width="100" align="center" sortable="true">actual_start_time</th>
				<th field="actualOverTime" width="100" align="center" sortable="true">actual_over_time</th>
				<th field="createBy.id" width="100" align="center" sortable="true">create_by</th>
				<th field="createDate" width="100" align="center" sortable="true">create_date</th>
				<th field="updateBy.id" width="100" align="center" sortable="true">update_by</th>
				<th field="updateDate" width="100" align="center" sortable="true">update_date</th>
				<th field="remarks" width="100" align="center" sortable="true">remarks</th>
				<th field="delFlag" width="100" align="center" formatter="globalFormat" sortable="true">del_flag</th>
				<th field="progressRefId" width="100" align="center" sortable="true">progress_ref_id</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/flightPairProgressInfo/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>progress_code：</td>
                    <td>
						<input type="text" name="progressCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>plan_start_time：</td>
                    <td>
						<input name="planStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightPairProgressInfo.planStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>plan_over_time：</td>
                    <td>
						<input name="planOverTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightPairProgressInfo.planOverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>actual_start_time：</td>
                    <td>
						<input name="actualStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightPairProgressInfo.actualStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                </tr>
                <tr>
                    <td>actual_over_time：</td>
                    <td>
						<input name="actualOverTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightPairProgressInfo.actualOverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>create_by：</td>
                    <td>
						<input type="text" name="createBy.id" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>create_date：</td>
                    <td>
						<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightPairProgressInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>update_by：</td>
                    <td>
						<input type="text" name="updateBy.id" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>update_date：</td>
                    <td>
						<input name="updateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${flightPairProgressInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>remarks：</td>
                    <td>
						<form:textarea path="remarks" htmlEscape="false" rows="4" data-options="required:true,maxlength='1000'" class="textarea easyui-validatebox"/>
					</td>
                    <td>del_flag：</td>
                    <td>
						<input type="radio" name="delFlag" value="1" checked >&nbsp;是
						<input type="radio" name="delFlag" value="0" >&nbsp;不是
					</td>
                    <td>progress_ref_id：</td>
                    <td>
						<input type="text" name="progressRefId" class="easyui-textbox easyui-validatebox"
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
    var moduleContext = "flightPairProgressInfo";
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
    <shiro:hasPermission name="rms:flightPairProgressInfo:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:flightPairProgressInfo:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:flightPairProgressInfo:del">flagDgDel=true;</shiro:hasPermission>

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