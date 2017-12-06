<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>座位交接单</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="座位交接单" id="datagrid" sortName="flightNum" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="flightDate" width="100" align="center" formatter="dateFmt" sortable="true">航班日期</th>
				<th field="flightNum" width="100" align="center" sortable="true">航班号</th>
				<th field="adultCount" width="100" align="center" sortable="true">成人数</th>
				<th field="childrenCount" width="100" align="center" sortable="true">儿童数</th>
				<th field="babyCount" width="100" align="center" sortable="true">婴儿数</th>
				<th field="firstClassCount" width="100" align="center" sortable="true">头等舱人数</th>
				<th field="businessClassCount" width="100" align="center" sortable="true">公务舱人数</th>
				<th field="touristClassCount" width="100" align="center" sortable="true">经济舱人数</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/flightSeat/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
				<tr>
					<td>航班日期：</td>
					<td>
						<input name="flightDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							   value="<fmt:formatDate value="${flightSeat.flightDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</td>
					<td>航班号：</td>
					<td>
						<input class="easyui-textbox" id="flightNum" name="flightNum">
						<%--<select class="easyui-validatebox" id="flightNum" name="flightNum" style="width:130px;"
								data-options="">
						</select>--%>
					</td>
                    <td>成人数：</td>
                    <td>
						<input type="text" name="adultCount" class="easyui-numberbox"
							 data-options=""
						>
					</td>
                    <td>儿童数：</td>
                    <td>
						<input type="text" name="childrenCount" class="easyui-numberbox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>婴儿数：</td>
                    <td>
						<input type="text" name="babyCount" class="easyui-numberbox"
							 data-options=""
						>
					</td>
                    <td>头等舱人数：</td>
                    <td>
						<input type="text" name="firstClassCount" class="easyui-numberbox"
							 data-options=""
						>
					</td>
                    <td>公务舱人数：</td>
                    <td>
						<input type="text" name="businessClassCount" class="easyui-numberbox"
							 data-options=""
						>
					</td>
                    <td>经济舱人数：</td>
                    <td>
						<input type="text" name="touristClassCount" class="easyui-numberbox"
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
    var moduleContext = "flightSeat";

    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
	<shiro:hasPermission name="rms:flightSeat:edit">flagDgEdit=true;</shiro:hasPermission>
	<shiro:hasPermission name="rms:flightSeat:insert">flagDgInsert=true;</shiro:hasPermission>
	<shiro:hasPermission name="rms:flightSeat:del">flagDgDel=true;</shiro:hasPermission>

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