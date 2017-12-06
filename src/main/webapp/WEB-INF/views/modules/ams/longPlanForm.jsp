<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>长期计划管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/ams/longPlan/">长期计划管理列表</a></li>
		<li class="active"><a href="${ctx}/ams/longPlan/form?id=${longPlan.id}">长期计划管理<shiro:hasPermission name="ams:longPlan:edit">${not empty longPlan.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ams:longPlan:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="longPlan" action="${ctx}/ams/longPlan/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年度：</label>
			<div class="controls">
				<form:input path="year" htmlEscape="false" maxlength="4" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计划类型：</label>
			<div class="controls">
				<form:input path="type" htmlEscape="false" maxlength="18" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${longPlan.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${longPlan.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进出港类型编码：</label>
			<div class="controls">
				<form:input path="inoutTypeCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">进出港类型名称：</label>
			<div class="controls">
				<form:input path="inoutTypeName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">航空公司代码：</label>
			<div class="controls">
				<form:input path="flightCompanyCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">航空公司名称：</label>
			<div class="controls">
				<form:input path="flightCompanyName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">航班号：</label>
			<div class="controls">
				<form:input path="flightNum" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机型编号：</label>
			<div class="controls">
				<form:input path="aircraftTypeCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机型名称：</label>
			<div class="controls">
				<form:input path="aircraftTypeName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起飞地编号：</label>
			<div class="controls">
				<form:input path="departureAirportCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起飞地名称：</label>
			<div class="controls">
				<form:input path="departureAirportName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">到达地编号：</label>
			<div class="controls">
				<form:input path="arrivalAirportCode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">到达地名称：</label>
			<div class="controls">
				<form:input path="arrivalAirportName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计划起飞时间：</label>
			<div class="controls">
				<input name="departurePlanTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${longPlan.departurePlanTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计划到达时间：</label>
			<div class="controls">
				<input name="arrivalPlanTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${longPlan.arrivalPlanTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">长期计划航班经停表：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>备注</th>
								<th>经停站编号</th>
								<th>经停站名称</th>
								<th>时间</th>
								<shiro:hasPermission name="ams:longPlan:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="longPlanStopList">
						</tbody>
						<shiro:hasPermission name="ams:longPlan:edit"><tfoot>
							<tr><td colspan="6"><a href="javascript:" onclick="addRow('#longPlanStopList', longPlanStopRowIdx, longPlanStopTpl);longPlanStopRowIdx = longPlanStopRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="longPlanStopTpl">//<!--
						<tr id="longPlanStopList{{idx}}">
							<td class="hide">
								<input id="longPlanStopList{{idx}}_id" name="longPlanStopList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="longPlanStopList{{idx}}_delFlag" name="longPlanStopList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<textarea id="longPlanStopList{{idx}}_remarks" name="longPlanStopList[{{idx}}].remarks" rows="4" maxlength="500" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<td>
								<input id="longPlanStopList{{idx}}_stopPointCode" name="longPlanStopList[{{idx}}].stopPointCode" type="text" value="{{row.stopPointCode}}" maxlength="100" class="input-small "/>
							</td>
							<td>
								<input id="longPlanStopList{{idx}}_stopPointName" name="longPlanStopList[{{idx}}].stopPointName" type="text" value="{{row.stopPointName}}" maxlength="200" class="input-small "/>
							</td>
							<td>
								<input id="longPlanStopList{{idx}}_stopPointTime" name="longPlanStopList[{{idx}}].stopPointTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
									value="{{row.stopPointTime}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</td>
							<shiro:hasPermission name="ams:longPlan:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#longPlanStopList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var longPlanStopRowIdx = 0, longPlanStopTpl = $("#longPlanStopTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(longPlan.longPlanStopList)};
							for (var i=0; i<data.length; i++){
								addRow('#longPlanStopList', longPlanStopRowIdx, longPlanStopTpl, data[i]);
								longPlanStopRowIdx = longPlanStopRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="ams:longPlan:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>