<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>VIP计划表</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
		<table title="VIP计划表" id="datagrid" sortName="plandate" sortOrder="asc" group="false" style="width: 100%;" data-options="fitColumns:false, fit:true">
            <thead>
            <tr>
				<th field="id" width="150" hidden="true" sortable="false" order="false">主键</th>
				<th field="inout" width="50" align="center" formatter="inoutFormat" sortable="true">进出港</th>
				<th field="plandate" width="80" align="center" sortable="true">计划日期</th>
				<th field="aircorp" width="60" align="center" sortable="true">航空公司</th>
				<th field="fltno" width="50" align="center" sortable="true">航班号</th>
				<th field="arrp" width="50" align="center" sortable="true">地名</th>
				<th field="groupid" width="50" align="center" sortable="true">团队编号</th>
				<th field="vipname" width="80" align="center" sortable="true">VIP姓名</th>
				<th field="vipcorp" width="90" align="center" sortable="true">VIP单位</th>
				<th field="viprank" width="60" align="center" sortable="true">VIP职务</th>
				<th field="vipid" width="150" align="center" sortable="true">证件号码</th>
				<th field="orin" width="50" align="center" sortable="true">籍贯</th>
				<th field="carid" width="60" align="center" sortable="true">车牌号</th>
				<th field="vipsex" width="50" align="center" formatter="globalFormat" sortable="true">VIP性别</th>
				<th field="addr" width="150" align="center" sortable="true">地址</th>
				<th field="passport" width="100" align="center" sortable="true">护照</th>
				<th field="familyaddr" width="150" align="center" sortable="true">家庭住址</th>
				<th field="rsntosz" width="150" align="center" sortable="true">VIP外出事由</th>
				<th field="togethers" width="50" align="center" sortable="true">人数</th>
				<th field="needcheck" width="50" align="center" formatter="globalFormat" sortable="true">是否免检</th>
				<th field="isvip" width="50" align="center" formatter="globalFormat" sortable="true">是否礼遇</th>
				<th field="receplace" width="150" align="center" sortable="true">接待位置</th>
				<th field="hallno" width="100" align="center" sortable="true">厅房安排</th>
				<th field="meetlead" width="50" align="center" sortable="true">迎送领导</th>
				<th field="infofrom" width="60" align="center" sortable="true">信息来源</th>
				<th field="workman" width="50" align="center" sortable="true">值班人</th>
				<th field="principal" width="50" align="center" sortable="true">负责人</th>
				<th field="auditer" width="50" align="center" sortable="true">审核人</th>
				<th field="fax" width="60" align="center" sortable="true">传真</th>
				<th field="tele1" width="60" align="center" sortable="true">电话1</th>
				<th field="tele2" width="60" align="center" sortable="true">电话2</th>
				<th field="meetcorp" width="100" align="center" sortable="true">接待单位</th>
				<th field="esprequire" width="100" align="center" sortable="true">特殊要求</th>
				<th field="note" width="50" align="center" sortable="true">备注</th>
				<th field="providerid" width="60" align="center" sortable="true">服务提供者</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="width:400px;height:180px;">
		 <form action="${ctx}/rms/vipplan/save" id="modifyForm">
            <input type="hidden" name="id">
			 <table width="100%" class="formtable">
				 <tr>
					 <td>进出港：</td>
					 <td>
						 <input type="radio" name="inout" value="J" checked>&nbsp;进港
						 <input type="radio" name="inout" value="C">&nbsp;出港
					 </td>
					 <td>计划日期：</td>
					 <td>
						 <input name="plandate" type="text" readonly="readonly" maxlength="20"
								class="easyui-validatebox input-medium Wdate" data-options="required:true"
								value="<fmt:formatDate value="${vipplan.plandate}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					 </td>
					 <td>航空公司：</td>
					 <td>
						 <select class="easyui-validatebox" id="aircorp" name="aircorp" style="width:170px;">
						 </select>
					 </td>
					 <td>航班号：</td>
					 <td>
						 <input type="text" name="fltno" class="easyui-validatebox"
								data-options="required:true"
						 >
					 </td>
				 </tr>
				 <tr>
					 <td>地名：</td>
					 <td>
						 <select class="easyui-validatebox" id="arrp" name="arrp" style="width:170px;"
						 >
						 </select>
					 </td>
					 <td>团队编号：</td>
					 <td>
						 <input type="text" name="groupid" class="easyui-validatebox"

						 >
					 </td>
					 <td>VIP姓名：</td>
					 <td>
						 <input type="text" name="vipname" class="easyui-validatebox"

						 >
					 </td>
					 <td>VIP单位：</td>
					 <td>
						 <input type="text" name="vipcorp" class="easyui-validatebox"

						 >
					 </td>
				 </tr>
				 <tr>
					 <td>VIP职务：</td>
					 <td>
						 <input type="text" name="viprank" class="easyui-validatebox"

						 >
					 </td>
					 <td>证件号码：</td>
					 <td>
						 <input type="text" name="vipid" class="easyui-validatebox"

						 >
					 </td>
					 <td>籍贯：</td>
					 <td>
						 <input type="text" name="orin" class="easyui-validatebox"

						 >
					 </td>
					 <td>车牌号：</td>
					 <td>
						 <input type="text" name="carid" class="easyui-validatebox"

						 >
					 </td>
				 </tr>
				 <tr>
					 <td>VIP性别：</td>
					 <td>
						 <input type="radio" name="vipsex" value="1" checked>&nbsp;男
						 <input type="radio" name="vipsex" value="0">&nbsp;女
					 </td>
					 <td>地址：</td>
					 <td>
						 <input type="text" name="addr" class="easyui-validatebox"

						 >
					 </td>
					 <td>护照：</td>
					 <td>
						 <input type="text" name="passport" class="easyui-validatebox"

						 >
					 </td>
					 <td>家庭住址：</td>
					 <td>
						 <input type="text" name="familyaddr" class="easyui-validatebox"

						 >
					 </td>
				 </tr>
				 <tr>
					 <td>VIP外出事由：</td>
					 <td>
						 <input type="text" name="rsntosz" class="easyui-validatebox"

						 >
					 </td>
					 <td>人数：</td>
					 <td>
						 <input type="text" name="togethers" class="easyui-numberbox easyui-validatebox"

						 >
					 </td>
					 <td>是否免检：</td>
					 <td>
						 <input type="radio" name="needcheck" value="1" checked>&nbsp;是
						 <input type="radio" name="needcheck" value="0">&nbsp;否
					 </td>
					 <td>是否礼遇：</td>
					 <td>
						 <input type="radio" name="isvip" value="1" checked>&nbsp;是
						 <input type="radio" name="isvip" value="0">&nbsp;否
					 </td>
				 </tr>
				 <tr>
					 <td>接待位置：</td>
					 <td>
						 <input type="text" name="receplace" class="easyui-validatebox"

						 >
					 </td>
					 <td>厅房安排：</td>
					 <td>
						 <input type="text" name="hallno" class="easyui-validatebox"

						 >
					 </td>
					 <td>迎送领导：</td>
					 <td>
						 <input type="text" name="meetlead" class="easyui-validatebox"

						 >
					 </td>
					 <td>信息来源：</td>
					 <td>
						 <input type="text" name="infofrom" class="easyui-validatebox"

						 >
					 </td>
				 </tr>
				 <tr>
					 <td>值班人：</td>
					 <td>
						 <input type="text" name="workman" class="easyui-validatebox"

						 >
					 </td>
					 <td>负责人：</td>
					 <td>
						 <input type="text" name="principal" class="easyui-validatebox"

						 >
					 </td>
					 <td>审核人：</td>
					 <td>
						 <input type="text" name="auditer" class="easyui-validatebox"

						 >
					 </td>
					 <td>传真：</td>
					 <td>
						 <input type="text" name="fax" class="easyui-validatebox"

						 >
					 </td>
				 </tr>
				 <tr>
					 <td>电话1：</td>
					 <td>
						 <input type="text" name="tele1" class="easyui-validatebox"

						 >
					 </td>
					 <td>电话2：</td>
					 <td>
						 <input type="text" name="tele2" class="easyui-validatebox"

						 >
					 </td>
					 <td>接待单位：</td>
					 <td>
						 <input type="text" name="meetcorp" class="easyui-validatebox"

						 >
					 </td>
					 <td>特殊要求：</td>
					 <td>
						 <input type="text" name="esprequire" class="easyui-validatebox"

						 >
					 </td>
				 </tr>
				 <tr>
					 <td>备注：</td>
					 <td>
						 <input type="text" name="note" class="easyui-validatebox"

						 >
					 </td>
					 <td>服务提供者：</td>
					 <td>
						 <input type="text" name="providerid" class="easyui-validatebox"

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
	var isFitColumns = false;

    var moduleContext = "vipplan";
    // 拿到字典Map, key = tableName, value = columnName
    //var dict = dictMap([{key: "", value: "yes_no"},{key: "", value: "sex"}]),
	var dict = {"@yes_no":{"0":"否","1":"是"},"@sex":{"0":"女","1":"男"}},
    // 构建字典与列字段的映射关系
    // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
	mapping = {
		"@sex": ["vipsex"],
		"@yes_no": ["needcheck","isvip"]
	};


    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:vipplan:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:vipplan:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:vipplan:del">flagDgDel=true;</shiro:hasPermission>
    flagAutoHight = true;
	flagValidate = true;
	var flightCompanyCodeDates,addressDatas;
    if(!flightCompanyCodeDates) {
        $.ajax({
            url:ctx + '/ams/flightCompanyInfo/jsonData',
            dataType: "json",
            async: false,
            success: function (data) {
                flightCompanyCodeDates = _.map(data,function(o){
                    var result = {};
                    result.twoCode = o.twoCode;
                    result.chineseName = o.chineseName;
                    result.tCodechName = o.twoCode+o.chineseName;
                    return result;
                });
            }
        });
    }

    if(!addressDatas){
        $.ajax({
            url:ctx + '/ams/amsAddress/jsonData',
            dataType: "json",
            async: false,
            success: function (data) {
                addressDatas = _.map(data,function(o){
                    var result = {};
                    result.threeCode = o.threeCode;
                    result.chName = o.chName;
                    result.tCodechName = o.chName + o.threeCode;
                    return result;
                });
            }
        });
    }

    $.fn.combobox.defaults.filter = function(q, row){
        var opts = $(this).combobox('options');
        return row[opts.textField].toUpperCase().indexOf(q.toUpperCase()) >= 0;
    }

     $(function () {
        //拿到航空公司数据
        $("#aircorp").combobox({
            data:flightCompanyCodeDates,
            valueField: 'twoCode',
            textField: 'tCodechName',
            panelHeight: 'auto',
            panelMaxHeight: '200',
            required:true
        });

        //拿到地名数据
        $("#arrp").combobox({
            data:addressDatas,
            valueField: 'threeCode',
            textField: 'tCodechName',
            panelHeight: 'auto',
            panelMaxHeight: '200'
        });

         /*datagrid = $("#datagrid").datagrid({
             rownumbers: true, url: "./list", method: 'get',
             fit: ((typeof(isFit) != 'undefined' && isFit == false) ? false : true),
             fitColumns: ((typeof(isFitColumns) != 'undefined' && isFitColumns == false ) ? false : true),
             idField: "id", singleSelect: true, remoteFilter: false,
             showFooter: true, remoteSort: false, multiSort: false,
             toolbar: toolbarDate
         });*/
    });
</script>

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid_vip.js"></script>
</body>