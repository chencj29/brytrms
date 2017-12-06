<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>值机柜台占用信息</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="值机柜台占用信息" id="datagrid" sortName="intlCheckingCounterCode" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="intlCheckingCounterCode" width="100" align="center" sortable="true">值机柜台编号(国际)</th>
				<th field="expectedStartTime" width="100" align="center" sortable="true">预计开始占用时间</th>
				<th field="expectedOverTime" width="100" align="center" sortable="true">预计结束占用时间</th>
				<th field="inteActualStartTime" width="100" align="center" sortable="true">实际开始占用时间(国内)</th>
				<th field="inteActualOverTime" width="100" align="center" sortable="true">实际结束占用时间(国内)</th>
				<th field="intlActualStartTime" width="100" align="center" sortable="true">实际开始占用时间(国际)</th>
				<th field="intlActualOverTime" width="100" align="center" sortable="true">实际结束占用时间(国际)</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/checkingCounterOccupyingInfo/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>值机柜台编号(国际)：</td>
                    <td>
						<input type="text" name="intlCheckingCounterCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>预计开始占用时间：</td>
                    <td>
						<input name="expectedStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${checkingCounterOccupyingInfo.expectedStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>预计结束占用时间：</td>
                    <td>
						<input name="expectedOverTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${checkingCounterOccupyingInfo.expectedOverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>实际开始占用时间(国内)：</td>
                    <td>
						<input name="inteActualStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${checkingCounterOccupyingInfo.inteActualStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                </tr>
                <tr>
                    <td>实际结束占用时间(国内)：</td>
                    <td>
						<input name="inteActualOverTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${checkingCounterOccupyingInfo.inteActualOverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>实际开始占用时间(国际)：</td>
                    <td>
						<input name="intlActualStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${checkingCounterOccupyingInfo.intlActualStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>实际结束占用时间(国际)：</td>
                    <td>
						<input name="intlActualOverTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${checkingCounterOccupyingInfo.intlActualOverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
    var moduleContext = "checkingCounterOccupyingInfo";
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