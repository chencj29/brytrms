<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>损坏行李表</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="损坏行李表" id="datagrid" sortName="damagedDate" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="damagedDate" width="100" align="center" formatter="dateFmt" sortable="true">日期</th>
				<th field="flightNum" width="100" align="center" sortable="true">航班号</th>
				<th field="name" width="100" align="center" sortable="true">姓名</th>
				<th field="luggageName" width="100" align="center" sortable="true">行李名称</th>
				<th field="damagedConditionCode" width="100" align="center" sortable="true">破损程度编号</th>
				<th field="damagedConditionName" width="100" align="center" sortable="true">破损程度名称</th>
				<th field="damagedPart" width="100" align="center" sortable="true">破损部位描述</th>
				<th field="luggagePrice" width="100" align="center" sortable="true">购买行李价格</th>
				<th field="purchaseDate" width="100" align="center" formatter="dateFmt" sortable="true">购买行李日期</th>
				<th field="purchasePrice" width="100" align="center" sortable="true">购买地点</th>
				<th field="isreceipt" width="100" align="center" formatter="globalFormat" sortable="true">是否有发票</th>
				<th field="processingMode" width="100" align="center" sortable="true">处理方式</th>
				<th field="indemnify" width="100" align="center" sortable="true">赔偿金额</th>
				<%--<th field="redundance1" width="100" align="center" sortable="true">冗余1</th>
				<th field="redundance2" width="100" align="center" sortable="true">冗余2</th>
				<th field="redundance3" width="100" align="center" sortable="true">冗余3</th>
				<th field="redundance4" width="100" align="center" sortable="true">冗余4</th>
				<th field="redundance5" width="100" align="center" sortable="true">冗余5</th>--%>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:245px;">
        <form action="${ctx}/rms/damagedLuggage/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>日期：</td>
                    <td><input type="text" name="damagedDate" class="Wdate easyui-validatebox"
                     value="<fmt:formatDate value="${damagedLuggage.damagedDate}" pattern="yyyy-MM-dd"/>"
                    data-options="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
                    ></td>
                    <td>航班号：</td>
                    <td><input type="text" name="flightNum" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <td>姓名：</td>
                    <td><input type="text" name="name" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <td>行李名称：</td>
                    <td><input type="text" name="luggageName" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                </tr>
                <tr>
                    <td>破损程度编号：</td>
                    <td><input type="text" name="damagedConditionCode" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <td>破损程度名称：</td>
                    <td><input type="text" name="damagedConditionName" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <td>破损部位描述：</td>
                    <td><input type="text" name="damagedPart" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <td>购买行李价格：</td>
                    <td><input type="text" name="luggagePrice" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                </tr>
                <tr>
                    <td>购买行李日期：</td>
                    <td>
                        <input type="text" name="purchaseDate" class="Wdate"
                            value="<fmt:formatDate value="${damagedLuggage.purchaseDate}" pattern="yyyy-MM-dd"/>"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
                    >
                    </td>
                    <td>购买地点：</td>
                    <td><input type="text" name="purchasePrice" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <td>是否有发票：</td>
                    <td>
                        <input type="radio" name="isreceipt" value="1" checked >&nbsp;有
                        <input type="radio" name="isreceipt" value="0" >&nbsp;否
                    </td>
                    <td>处理方式：</td>
                    <td><input type="text" name="processingMode" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                </tr>
                <tr>
                    <td>赔偿金额：</td>
                    <td><input type="text" name="indemnify" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <%--<td>冗余1：</td>
                    <td><input type="text" name="redundance1" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <td>冗余2：</td>
                    <td><input type="text" name="redundance2" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <td>冗余3：</td>
                    <td><input type="text" name="redundance3" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>--%>
                </tr>
               <%-- <tr>
                    <td>冗余4：</td>
                    <td><input type="text" name="redundance4" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <td>冗余5：</td>
                    <td><input type="text" name="redundance5" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                </tr>--%>
            </table>
            <div style="text-align: center; margin-top:15px; margin-right:15px;">
                <a class="easyui-linkbutton" id="btnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
            </div>
        </form>

    </div>
</div>

<script>
    var moduleContext = "damagedLuggage",
    // 拿到字典Map, key = tableName, value = columnName
            dict = dictMap([{key: "", value: "yes_no"}, {key: "", value: "ed_status"}]),
    // 构建字典与列字段的映射关系
    // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
            mapping = {
                "@yes_no": ["isreceipt"]
            };

    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:damagedLuggage:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:damagedLuggage:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:damagedLuggage:del">flagDgDel=true;</shiro:hasPermission>


    $(function () {
    	/*
        //拿到航站楼数据
        $("#airportTerminalCode").combobox({panelHeight: 'auto',panelMaxHeight: '200',
            url: ctx + '/rms/airportTerminal/jsonData',
            valueField: 'terminalName',
            textField: 'terminalName'
        });*/
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
    function dateFmt(v, r, i, f) {
        return !v ? "" : moment(v).format("YYYY-MM-DD");
    }
</script>
<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>
</body>
</html>