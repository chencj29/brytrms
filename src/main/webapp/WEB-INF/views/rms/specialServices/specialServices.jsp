<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>特殊服务记录表</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" id="datagrid" sortName="flightCompanyCode" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
                <th field="passengerDate" width="120" align="center" formatter="dateFmt" sortable="true">日期</th>
				<th field="flightCompanyCode" width="100" align="center" sortable="true">航空公司代码</th>
				<th field="flightCompanyName" width="100" align="center" sortable="true">航空公司名称</th>
				<th field="flightNum" width="100" align="center" sortable="true">航班号</th>
				<th field="place" width="100" align="center" sortable="true">地点</th>
				<th field="passengerName" width="100" align="center" sortable="true">特服旅客姓名</th>
				<th field="passengerAge" width="100" align="center" sortable="true">特服旅客年龄</th>
				<th field="pickUpName" width="100" align="center" sortable="true">接站人姓名</th>
				<th field="pickUpPhone" width="100" align="center" sortable="true">接站人电话</th>
				<th field="pickUpAddress" width="100" align="center" sortable="true">接站人地址</th>
				<th field="waiterCode" width="100" align="center" sortable="true">服务员编号</th>
				<th field="waiterName" width="100" align="center" sortable="true">服务员姓名</th>
				<th field="operatorCode" width="100" align="center" sortable="true">值机员编号</th>
				<th field="operatorName" width="100" align="center" sortable="true">值机员名称</th>
				<th field="redundance1" width="100" align="center" sortable="true">进出港类型</th>
				<th field="redundance2" width="100" align="center" sortable="true">特服旅客类型</th>
                <%--<th field="redundance3" width="100" align="center" sortable="true">冗余3</th>
                <th field="redundance4" width="100" align="center" sortable="true">冗余4</th>
                <th field="redundance5" width="100" align="center" sortable="true">冗余5</th>--%>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:230px;">
        <form action="${ctx}/rms/specialServices/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>日期</td>
                    <td>
                        <input name="passengerDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                               value="<fmt:formatDate value="${specialServices.passengerDate}" pattern="yyyy-MM-dd"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>

                    </td>
                    <td>航空公司代码：</td>
                    <%--<td><input type="text" name="flightCompanyCode" class="easyui-textbox" readonly
                    data-options=""
                    ></td>--%>
                    <td>
                        <select class="easyui-validatebox" id="flightCompanyCode" name="flightCompanyCode" style="width:168px;"
                                data-options="">
                        </select>
                    </td>
                    <td>航空公司名称：</td>
                    <td><input type="text" name="flightCompanyName" class="easyui-textbox easyui-validatebox"
                    data-options="novalidate:true" readonly
                    ></td>
                    <%--<td>
                        <select class="easyui-validatebox" id="flightCompanyName" name="flightCompanyName" style="width:130px;"
                                data-options="">
                        </select>
                    </td>--%>
                    <td>航班号：</td>
                    <td><input type="text" id="flightNum" name="flightNum" class="easyui-textbox easyui-validatebox" prompt=""
                    <%--data-options="required:true,validType:['flightnum']"--%>
                    ></td>
                </tr>
                <tr>
                    <td>地点：</td>
                    <td><input type="text" name="place" class="easyui-textbox easyui-validatebox"
                               data-options=""
                    ></td>
                    <td>特服旅客姓名：</td>
                    <td><input type="text" name="passengerName" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <td>特服旅客年龄：</td>
                    <td><input type="text" name="passengerAge" class="easyui-numberbox"
                    data-options="novalidate:true"
                    ></td>
                    <td>接站人姓名：</td>
                    <td><input type="text" name="pickUpName" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                </tr>
                <tr>
                    <td>接站人电话：</td>
                    <td><input type="text" name="pickUpPhone" class="easyui-textbox easyui-validatebox"
                               data-options=""
                    ></td>
                    <td>接站人地址：</td>
                    <td><input type="text" name="pickUpAddress" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <td>服务员编号：</td>
                    <td><input type="text" name="waiterCode" class="easyui-textbox easyui-validatebox"
                    data-options="novalidate:true"
                    ></td>
                    <td>服务员姓名：</td>
                    <td><input type="text" name="waiterName" class="easyui-textbox easyui-validatebox"
                    data-options="novalidate:true"
                    ></td>
                </tr>
                <tr>
                    <td>值机员编号：</td>
                    <td><input type="text" name="operatorCode" class="easyui-textbox easyui-validatebox"
                               data-options="novalidate:true"
                    ></td>
                    <td>值机员名称：</td>
                    <td><input type="text" name="operatorName" class="easyui-textbox easyui-validatebox"
                    data-options="novalidate:true"
                    ></td>
                    <td>进出港类型：</td>
                    <td><input type="text" name="redundance1" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>
                    <td>特服旅客类型：</td>
                    <td><input type="text" name="redundance2" class="easyui-textbox easyui-validatebox"
                    data-options="novalidate:true"
                    ></td>
                    <%--<td>冗余3：</td>
                    <td><input type="text" name="redundance3" class="easyui-textbox easyui-validatebox"
                    data-options=""
                    ></td>--%>
                </tr>
                <%--<tr>
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
    var moduleContext = "specialServices"/*,
    // 拿到字典Map, key = tableName, value = columnName
            dict = dictMap([{key: "", value: "yes_no"}, {key: "", value: "ed_status"}]),
    // 构建字典与列字段的映射关系
    // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
            mapping = {
                "@yes_no": ["hasSalon", "hasOilpipeline", "available"],
                "@ed_status": ["salonStatus", "oilpipelineStatus"]
            }*/;

    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:specialServices:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:specialServices:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:specialServices:del">flagDgDel=true;</shiro:hasPermission>

    var flightCompanyCodeDates;
    $(function () {

        $.extend($.fn.validatebox.defaults.rules, {
            flightnum : {
                validator : function (value) {
                    var pre = $("#flightNum").textbox('options').prompt;
                    pre = pre.substr(0,2);
                    var reg = "/^" + pre + "[0-9]{2,6}$/";
                    $.fn.validatebox.defaults.rules.flightnum.message = "航班号请以"+pre+"加数字";
                    return eval(reg).test(value);
                },
            message : ''
            }
        });

        if(flagDgEdit || flagDgInsert) {
            if (!flightCompanyCodeDates) {
                $.ajax({
                    url: ctx + '/ams/flightCompanyInfo/jsonData',
                    dataType: "json",
                    async: false,
                    success: function (data) {
                        flightCompanyCodeDates = _.map(data, function (o) {
                            var result = {};
                            result.twoCode = o.twoCode;
                            result.chineseName = o.chineseName;
                            result.tCodechName = o.twoCode + o.chineseName;
                            return result;
                        });
                    }
                });
            }

            //拿到航班数据
            $("#flightCompanyCode").combobox({
                panelHeight: 'auto', panelMaxHeight: '200',
                data: flightCompanyCodeDates,
                valueField: 'twoCode',
                textField: 'tCodechName',
                onSelect: function (rec) { //改变单选值时，通过选中值后回填内容
                    form.form('load', {
                        flightCompanyName: rec.chineseName
                    });
                    $("#flightNum").textbox({prompt: rec.twoCode + "开头"});
                }
            });
        }

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

    //datagrid加载成功时调用  隐藏数据显示
    function loadsuccessFn(data) {
        datagrid.datagrid('doCellTip', {
            onlyShowInterrupt: true, position: 'bottom', maxWidth: '200px',
            tipStyler: {'backgroundColor': '#fff000', borderColor: '#ff0000', boxShadow: '1px 1px 3px #292929'}
        });
    }
</script>
<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>
</body>
</html>