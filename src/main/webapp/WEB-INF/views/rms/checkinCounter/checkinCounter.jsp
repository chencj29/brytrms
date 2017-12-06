<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>值机柜台基础信息表</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="值机柜台基础信息表" id="datagrid" sortName="orderIndex" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<%--<th field="airportTerminalCode" width="100" align="center" sortable="true">航站楼编号</th>--%>
				<th field="airportTerminalName" width="100" align="center" sortable="true">航站楼名称</th>
				<th field="checkinCounterNum" width="100" align="center" sortable="true">值机柜台编号</th>
				<th field="checkinCounterNature" width="100" align="center" formatter="globalFormat" sortable="true">值机柜台性质</th>
				<th field="checkinCounterTypeCode" width="100" align="center" formatter="globalFormat" sortable="true">值机柜台状态</th>
				<th field="orderIndex" width="100" align="center" sortable="true">排序字段</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:150px;">
        <form action="${ctx}/rms/checkinCounter/save" id="modifyForm">
            <input type="hidden" name="id">
            <input type="hidden" name="airportTerminalCode">
            <input type="hidden" name="checkinCounterTypeName">
            <table width="100%" class="formtable">
                <tr>
                    <td>航站楼名称：</td>
                    <td>
                        <select class="easyui-validatebox" id="airportTerminalName" name="airportTerminalName" style="width:130px;"
                                data-options="">
                        </select>
					</td>
                    <td>值机柜台编号：</td>
                    <td>
						<input type="text" name="checkinCounterNum" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>值机柜台性质：</td>
                    <td>
                        <input type="radio" name="checkinCounterNature" value="1" checked >&nbsp;国内
                        <input type="radio" name="checkinCounterNature" value="2" >&nbsp;国际 <%--
                        <input type="radio" name="checkinCounterNature" value="2" >&nbsp;混合 --%>
                    </td>
                </tr>
                <tr>
                    <td>值机柜台状态：</td>
                    <td>
						<input type="radio" name="checkinCounterTypeCode" value="1" checked >&nbsp;可用
						<input type="radio" name="checkinCounterTypeCode" value="0" >&nbsp;不可用
                        <%--<input type="radio" name="checkinCounterTypeCode" value="2" >&nbsp;专用 --%>
					</td>
                    <td>排序字段：</td>
                    <td>
						<input type="text" name="orderIndex" class="easyui-numberbox">
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
    var moduleContext = "checkinCounter",
    // 拿到字典Map, key = tableName, value = columnName
            dict = dictMap([{key: "", value: "nature"},{key: "", value: "ed_status"}]),
    // 构建字典与列字段的映射关系
    // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
            mapping = {
                "@nature":["checkinCounterNature"],
                "@ed_status":["checkinCounterTypeCode"]
            };

    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:checkinCounter:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:checkinCounter:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:checkinCounter:del">flagDgDel=true;</shiro:hasPermission>
    //console.log(dict,mapping);

    $(function () {

        //拿到航站楼数据
        /*$("#airportTerminalCode").combobox({panelHeight: 'auto',panelMaxHeight: '200',
            url: ctx + '/rms/airportTerminal/jsonData',
            valueField: 'terminalCode',
            textField: 'terminalCode'
        });*/

        //改变单选值时，通过选中值后回填内容
        $("#airportTerminalName").combobox({panelHeight: 'auto',panelMaxHeight: '200',
            onSelect : function(rec){
                form.form('load',{airportTerminalCode:rec.terminalCode});
            }
        });

        $("#airportTerminalName").combobox({panelHeight: 'auto',panelMaxHeight: '200',
            url: ctx + '/rms/airportTerminal/jsonData',
            valueField: 'terminalName',
            textField: 'terminalName'
        });

        //改变单选值时，通过选中值后回填内容
        $('[name=checkinCounterTypeCode]').change(function(){
            var v = $('[name=checkinCounterTypeCode]:checked ')[0].nextSibling.nodeValue.trim();
            form.form('load',{checkinCounterTypeName:v});
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
</script>

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>
</body>