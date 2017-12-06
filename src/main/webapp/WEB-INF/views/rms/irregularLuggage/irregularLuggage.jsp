<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>不正常行李表</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="不正常行李表" id="datagrid" sortName="irregularLuggageDate" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="irregularLuggageDate" width="100" align="center" formatter="dateFmt" sortable="true">日期</th>
				<th field="flightNum" width="100" align="center" sortable="true">航班号</th>
				<th field="inoutTypeCode" width="100" align="center" sortable="true">进出港类型编码</th>
				<th field="inoutTypeName" width="100" align="center" sortable="true">进出港类型名称</th>
				<th field="irregularLuggageTypeCode" width="100" align="center" sortable="true">不正常行李类别编号</th>
				<th field="irregularLuggageTypeName" width="100" formatter="globalFormat" align="center" sortable="true">不正常行李类别名称</th>
				<th field="luggageDescription" width="100" align="center" sortable="true">行李描述</th>
				<th field="irregularCause" width="100" align="center" sortable="true">不正常原因描述</th>
				<th field="processingResult" width="100" align="center" sortable="true">处理结果</th>
				<th field="processingTime" width="100" align="center" formatter="dateFmt" sortable="true">处理日期</th>
				<%--<th field="redundance1" width="100" align="center" sortable="true">冗余1</th>
				<th field="redundance2" width="100" align="center" sortable="true">冗余2</th>
				<th field="redundance3" width="100" align="center" sortable="true">冗余3</th>
				<th field="redundance4" width="100" align="center" sortable="true">冗余4</th>
				<th field="redundance5" width="100" align="center" sortable="true">冗余5</th>--%>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/irregularLuggage/save" id="modifyForm">
            <input type="hidden" name="id">
            <input type="hidden" name="irregularLuggageTypeCode">
            <table width="100%" class="formtable">
                <tr>
                    <td>日期：</td>
                    <td>
						<input name="irregularLuggageDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${irregularLuggage.irregularLuggageDate}" pattern="yyyy-MM-dd"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</td>
                    <td>航班号：</td>
                    <td>
						<%--<select class="easyui-validatebox" id="flightNum" name="flightNum" style="width:130px;"
								data-options="">
						</select>--%>
                            <input type="text" name="flightNum" class="easyui-textbox easyui-validatebox"
                                   data-options=""
                            >
					</td>
                    <td>进出港类型编码：</td>
                    <td>
						<input type="text" name="inoutTypeCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>进出港类型名称：</td>
                    <td>
						<input type="text" name="inoutTypeName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <%--<td>不正常行李类别编号：</td>
                    <td>
						<input type="text" name="irregularLuggageTypeCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>--%>
                    <td>不正常行李类别名称：</td>
                    <td>
						<select class="easyui-validatebox" id="irregularLuggageTypeName" name="irregularLuggageTypeName" style="width:130px;"
								data-options="">
						</select>
					</td>
                    <td>行李描述：</td>
                    <td>
						<input type="text" name="luggageDescription" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>不正常原因描述：</td>
                    <td>
						<input type="text" name="irregularCause" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>处理结果：</td>
                    <td>
                        <input type="text" name="processingResult" class="easyui-textbox easyui-validatebox"
                               data-options=""
                        >
                    </td>
                </tr>
                <tr>
                    <td>处理日期：</td>
                    <td>
						<input name="processingTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${irregularLuggage.processingTime}" pattern="yyyy-MM-dd"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</td>
                    <%--<td>冗余1：</td>
                    <td>
						<input type="text" name="redundance1" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>冗余2：</td>
                    <td>
						<input type="text" name="redundance2" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>--%>
                </tr>
                <%--<tr>
                    <td>冗余3：</td>
                    <td>
						<input type="text" name="redundance3" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>冗余4：</td>
                    <td>
						<input type="text" name="redundance4" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>冗余5：</td>
                    <td>
						<input type="text" name="redundance5" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>--%>
            </table>
            <div style="text-align: center; margin-top:15px; margin-right:15px;">
                <a class="easyui-linkbutton" id="btnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
            </div>
        </form>

    </div>
</div>

<script>
    var moduleContext = "irregularLuggage";
    // 拿到字典Map, key = tableName, value = columnName
    var dict = dictMap([{key: "rms_irregular_luggage", value: "irregularLuggageType"}]),
        // 构建字典与列字段的映射关系
        // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
            mapping = {
                "rms_irregular_luggage@irregularLuggageType": ["irregularLuggageTypeName"]
            };
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:irregularLuggage:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:irregularLuggage:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:irregularLuggage:del">flagDgDel=true;</shiro:hasPermission>


     $(function () {
        //拿到航班号数据
        /*$("#flightNum").combobox({
            url: ctx + '/rms/airportTerminal/jsonData',
            valueField: 'terminalName',
            textField: 'terminalName',
            panelHeight: 'auto',
            panelMaxHeight: '200'
        });*/

         //将字典数据格式转为下拉数据格式
         var d = _.map(_.keys(dict['rms_irregular_luggage@irregularLuggageType']),function(n){return {"id":n,"text":dict['rms_irregular_luggage@irregularLuggageType'][n]}});
         d[0]["selected"]=true;//设置第一个为默认选项
        //拿到不正常行李类别名称数据
        $("#irregularLuggageTypeName").combobox({
            data:d,
            valueField: 'id',
            textField: 'text',
            panelHeight: 'auto',
            panelMaxHeight: '200',
            onChange:function(n){
                var o = $("#modifyForm input:visible");
                if(n==1){
                    o.validatebox({required: false});
                    o.eq(0).validatebox({ required: true});
                }else{
                    o.validatebox({required: true});
                }
            },
            onSelect:function(rec){
                form.form('load',{irregularLuggageTypeCode:rec.id});
            }

        });
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