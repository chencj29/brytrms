<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>行李转盘占用信息</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="行李转盘占用信息" id="datagrid" sortName="intlCarouselCode" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="intlCarouselCode" width="100" align="center" sortable="true">行李转盘编号(国际)</th>
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
        <form action="${ctx}/rms/carouselOccupyingInfo/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>行李转盘编号(国际)：</td>
                    <td>
						<input type="text" name="intlCarouselCode" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                    <td>预计开始占用时间：</td>
                    <td>
						<input name="expectedStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${carouselOccupyingInfo.expectedStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>预计结束占用时间：</td>
                    <td>
						<input name="expectedOverTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${carouselOccupyingInfo.expectedOverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>实际开始占用时间(国内)：</td>
                    <td>
						<input name="inteActualStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${carouselOccupyingInfo.inteActualStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                </tr>
                <tr>
                    <td>实际结束占用时间(国内)：</td>
                    <td>
						<input name="inteActualOverTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${carouselOccupyingInfo.inteActualOverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>实际开始占用时间(国际)：</td>
                    <td>
						<input name="intlActualStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${carouselOccupyingInfo.intlActualStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					</td>
                    <td>实际结束占用时间(国际)：</td>
                    <td>
						<input name="intlActualOverTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${carouselOccupyingInfo.intlActualOverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
    var moduleContext = "carouselOccupyingInfo";
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