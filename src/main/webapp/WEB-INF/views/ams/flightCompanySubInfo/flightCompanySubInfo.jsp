<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>航空公司子公司</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="航空公司子公司" id="datagrid" sortName="flightCompanyInfoName" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="flightCompanyInfoName" width="100" align="center" sortable="true">航空公司</th>
				<th field="subCode" width="100" align="center" sortable="true">子公司代码</th>
				<th field="threeCode" width="100" align="center" sortable="true">三字码</th>
				<th field="chineseName" width="100" align="center" sortable="true">中文名称</th>
				<th field="englishName" width="100" align="center" sortable="true">英文名称</th>
				<th field="isbase" width="100" align="center" formatter="globalFormat" sortable="true">是否基地</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/ams/flightCompanySubInfo/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>航空公司：</td>
                    <td>
                        <select class="easyui-validatebox" id="flightCompanyInfoName" name="flightCompanyInfoId" style="width:130px;"
                                data-options="">
                        </select>
					</td>
                    <td>子公司代码：</td>
                    <td>
						<input type="text" name="subCode" class="easyui-textbox easyui-validatebox"
							 data-options="novalidate:true"
						>
					</td>
                    <td>三字码：</td>
                    <td>
						<input type="text" name="threeCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>中文名称：</td>
                    <td>
						<input type="text" name="chineseName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>英文名称：</td>
                    <td>
						<input type="text" name="englishName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>是否基地：</td>
                    <td>
						<input type="radio" name="isbase" value="1" checked >&nbsp;是
						<input type="radio" name="isbase" value="0" >&nbsp;不是
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
    var moduleContext = "flightCompanySubInfo";
    // 拿到字典Map, key = tableName, value = columnName
           var dict = dictMap([{key: "", value: "nature"},{key: "", value: "yes_no"},{key: "", value: "ed_status"}]),
    // 构建字典与列字段的映射关系
    // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
            mapping = {
            	"@yes_no": ["isbase"],
            };
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="ams:flightCompanySubInfo:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="ams:flightCompanySubInfo:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="ams:flightCompanySubInfo:del">flagDgDel=true;</shiro:hasPermission>
    var flightCompanyCodeDates;
     $(function () {
         if(!flightCompanyCodeDates) {
             $.ajax({
                 url:ctx + '/ams/flightCompanyInfo/jsonData',
                 dataType: "json",
                 async: false,
                 success: function (data) {
                     flightCompanyCodeDates = _.map(data,function(o){
                         var result = {};
                         result.id = o.id;
                         result.tCodechName = o.twoCode+o.chineseName;
                         return result;
                     });
                 }
             });
         }

         $.fn.combobox.defaults.filter = function(q, row){
             var opts = $(this).combobox('options');
             return row[opts.textField].toUpperCase().indexOf(q.toUpperCase()) >= 0;
         }
         //拿到父公司数据
         $("#flightCompanyInfoName").combobox({
             panelHeight: 'auto',panelMaxHeight: '200',
             data:flightCompanyCodeDates,
             valueField: 'id',
             textField: 'tCodechName',
             onSelect : function(rec){ //改变单选值时，通过选中值后回填内容
                 form.form('load',{
                     flightCompanyInfoId:rec.id
                 });
             }
         });
    });

</script>

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGridAms.js"></script>
</body>