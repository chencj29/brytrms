<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
	<title>过程时序设置</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">

	<div data-options="region:'west',split:true" title="保障类型" style="width:150px;">
		<ul class="easyui-tree" id="tree"></ul>
	</div>

	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false">
				<table class="easyui-datagrid" title="过程时序管理" id="datagrid">
					<thead>
					<tr>
						<th field="safeguardTypeCode" width="100" align="center" sortable="false">保障类型编号</th>
						<th field="safeguardTypeName" width="100" align="center" sortable="false">保障类型名称</th>

						<th field="safeguardProcessCode" width="100" align="center" sortable="false">过程编号</th>
						<th field="safeguardProcessName" width="100" align="center" sortable="false">过程名称</th>

						<th field="startTimeExp" width="100" align="center" sortable="false">开始时间</th>
						<%--<th field="startArrExp" width="100" align="center" sortable="false">开始相对于计达间隔</th>--%>
						<th field="endTimeExp" width="100" align="center" sortable="false">结束时间</th>
						<%--<th field="endArrExp" width="100" align="center" sortable="false">结束相对于计达间隔</th>--%>


						<th field="processTimeExp" width="100" align="center" sortable="false">过程时长</th>
						<%--<th field="startDepRange" width="100" align="center" sortable="false">前束间隔</th>--%>
						<%--<th field="backProcessName" width="100" align="center" sortable="false">后束过程</th>--%>
						<%--<th field="timeLength" width="100" align="center" sortable="false">后束间隔</th>--%>
						<%--<th field="selectedStatus" width="100" disabled="disabled" align="center" sortable="false">选中状态</th>--%>
					</tr>
					</thead>
				</table>
			</div>
			<div data-options="region:'east',split:true,border:false" style="width: 300px">
				<div class="easyui-panel" title="时序设置" style="width:100%;height:100%">
					<form id="modifyForm" action="${ctx}/rms/safeguardTypeToProcess/saveStp" method="post">

						<input name="id" id="id" type="hidden" value=""/>

						<input name="safeguardTypeCode" id="safeguardTypeCode" type="hidden" value=""/>
						<input name="safeguardTypeId" id="safeguardTypeId" type="hidden" value=""/>

						<input name="safeguardProcessCode" id="safeguardProcessCode" type="hidden" value=""/>
						<input name="safeguardProcessId" id="safeguardProcessId" type="hidden" value=""/>
						<table cellpadding="5" width="100%">
							<tr>
								<td align="right">保障子类型：</td>
								<td colspan="2"><input class="easyui-textbox" type="text" id="safeguardTypeName" name="safeguardTypeName" editable="false" style="width:90%;"/></td>
							</tr>
							<tr>
								<td align="right">保障过程：</td>
								<td colspan="2"><input class="easyui-textbox" type="text" id="safeguardProcessName" name="safeguardProcessName" editable="false"
								                       style="width:90%;"/></td>
							</tr>
							<tr>
								<td colspan="2" style="border-bottom: 1px #94B7E9 solid">过程时间类别</td>
							</tr>
							<tr>
								<td colspan="2" name="time_params">
									<input type="checkbox" name="proTimeType" lang="start_time_param" value="1">开始时间<br>
									<input type="checkbox" name="proTimeType" lang="end_time_param" value="2">结束时间<br>
									<input type="checkbox" name="proTimeType" lang="process_time_param" value="3">过程时长<br>
								</td>
							</tr>
							<tr>
								<td colspan="2" style="border-bottom: 1px #94B7E9 solid">开始时间参数</td>
							</tr>
							<tr name="start_time_param">
								<td align="right"><input type="radio" name="startTime_r" checked="checked" value="1"/>相对关舱门：</td>
								<td>
									<select class="easyui-combobox" id="startDepFb" name="startDepFb" style="width:30%;">
										<option value="-">前</option>
										<option value="+">后</option>
									</select>
									<input class="easyui-numberspinner" id="startDepRange" name="startDepRange" style="width:45%;" data-options="min:0,max:100,value:0">
									<span>分钟</span>
								</td>
							</tr>
							<tr name="start_time_param">
								<td align="right"><input type="radio" name="startTime_r" value="2"/>相对开舱门：</td>
								<td>
									<select class="easyui-combobox" id="startArrFb" name="startArrFb" style="width:30%;">
										<option value="-">前</option>
										<option value="+">后</option>
									</select>
									<input class="easyui-numberspinner" id="startArrRange" name="startArrRange" style="width:45%;" data-options="min:0,max:100,value:0">
									<span>分钟</span>
								</td>
							</tr>
							<tr name="start_time_param">
								<td align="right"><input type="radio" name="startTime_r" value="3"/>相对过程：</td>
								<td>
									<%--<input id="startTime_s" name="startProId" style="width:75%;">--%>
									<select id="startProId" name="startProId" class="easyui-combobox" style="width:75%;">
									</select>
									<input type="hidden" name="startProName" value="">
								</td>
							</tr>
							<tr name="start_time_param">
								<td align="right">&nbsp;</td>
								<td>
									<select class="easyui-combobox" id="startProFb" name="startProFb" style="width:30%;">
										<option value="-">前</option>
										<option value="+">后</option>
									</select>
									<input class="easyui-numberspinner" id="startProRange" name="startProRange" style="width:45%;" data-options="min:0,value:0">
									<span>分钟</span>
								</td>
							</tr>
							<tr>
								<td colspan="2" style="border-bottom: 1px #94B7E9 solid">结束时间参数</td>
							</tr>
							<tr name="end_time_param">
								<td align="right"><input type="radio" name="endTime_r" checked="checked" value="1"/>相对关舱门：</td>
								<td>
									<select class="easyui-combobox" id="endDepFb" name="endDepFb" style="width:30%;">
										<option value="-">前</option>
										<option value="+">后</option>
									</select>
									<input class="easyui-numberspinner" id="endDepRange" name="endDepRange" style="width:45%;" data-options="min:0,value:0">
									<span>分钟</span>
								</td>
							</tr>
							<tr name="end_time_param">
								<td align="right"><input type="radio" name="endTime_r" value="2"/>相对开舱门：</td>
								<td>
									<select class="easyui-combobox" id="endArrFb" name="endArrFb" style="width:30%;">
										<option value="-">前</option>
										<option value="+">后</option>
									</select>
									<input class="easyui-numberspinner" id="endArrRange" name="endArrRange" style="width:45%;" data-options="min:0,value:0">
									<span>分钟</span>
								</td>
							</tr>
							<tr name="end_time_param">
								<td align="right"><input type="radio" name="endTime_r" value="3"/>相对过程：</td>
								<td>
									<select id="endProId" class="easyui-combobox" name="endProId" style="width:75%;">
									</select>
									<input type="hidden" name="endProName" value="">
								</td>
							</tr>
							<tr name="end_time_param">
								<td align="right">&nbsp;</td>
								<td>
									<select class="easyui-combobox" id="endProFb" name="endProFb" style="width:30%;">
										<option value="-">前</option>
										<option value="+">后</option>
									</select>
									<input class="easyui-numberspinner" id="endProRange" name="endProRange" style="width:45%;" data-options="min:0,max:100,value:0">
									<span>分钟</span>
								</td>
							</tr>

							<tr>
								<td colspan="2" style="border-bottom: 1px #94B7E9 solid">过程时长参数</td>
							</tr>
							<tr name="process_time_param">
								<td align="right"><input type="radio" id="proRange_r" checked="checked" name="proRange_r" value="1"/>绝对时长：</td>
								<td>
									<input class="easyui-numberspinner" id="absoluteRange" name="absoluteRange" style="width:75%;" data-options="min:1,value:1">
									<span>分钟</span>
								</td>
							</tr>
							<tr name="process_time_param">
								<td align="right"><input type="radio" name="proRange_r" value="2"/>相对比例：</td>
								<td>
									<input class="easyui-numberspinner" id="percentRange" name="percentRange" style="width:75%;" data-options="min:1,max:100,value:1">
									<span>%</span>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="center"><a id="saveStpVO" class="easyui-linkbutton" style="width: 80px;">保存</a></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	var moduleContext = "safeguardTypeToProcess";
	/**
	 * 过滤列声明接口(必须，可空)
	 */
	var specialFilteringColumnDefinition = [];

	/**
	 * form: crud表单对象
	 * datagrid: 数据表格对象
	 * dictColumns: 字典字段
	 * comboboxFilterDefinetion: 字典类型字段过滤定义 （需传入字段名及JSON枚举值）
	 *      例如：[{value: '', text: '全部'}, {value: '0', text: '不可用'}, {value: '1', text: '可用'}]
	 */
	var form, datagrid, selectedId, loadedData, dictColumns, datagridSelectedRow, comboboxFilterDefinetion = function (fieldCode, enumValues) {
		return {
			field: fieldCode, type: 'combobox', options: {
				panelHeight: 'auto', data: enumValues, onChange: function (value) {
					if (value == '')
						datagrid.datagrid('removeFilterRule', fieldCode);
					else
						datagrid.datagrid('addFilterRule', {
							field: fieldCode, op: ['equal'], value: value
						});

					datagrid.datagrid('doFilter');
				}
			}
		}
	};
	$(function () {
		<shiro:lacksPermission name="rms:safeguardTypeToProcess:timeSeq:edit">$("#saveStpVO").linkbutton('disable');
		</shiro:lacksPermission>

		$('#tree').tree({
			url: '${ctx}/rms/safeguardType/getWholeTree',
			method: 'get',
			onClick: function (node) {
				datagrid.datagrid("reload", {
					"safeguardTypeId": node.params.obj.id
				});
			}
		});

		form = $("#modifyForm");

		dictColumns = $("#datagrid th[formatter=globalFormat]").map(function () {
			return $(this).attr("field");
		});

		var pro_selector = $('#startProId, #endProId').combobox({
			valueField: 'id',
			textField: 'label',
			onSelect: function (param) {
				$(this).siblings('input').val(param.label);
			}
		});

		datagrid = $("#datagrid").datagrid({
			rownumbers: true, url: "./pTimeSeq", method: 'get',
			queryParams: {
				"safeguardTypeId": selectedId
			},
			fit: true, fitColumns: true, idField: "id", singleSelect: true,
			showFooter: true, remoteSort: false, multiSort: false,
			toolbar: [/*{
				text: "新增", iconCls: "icon-add",
				handler: function () {
					datagrid.datagrid("reload").datagrid("clearSelections");
					form.form("reset");
					$("input[name=id]").val("");

					var node = $('#tree').tree("getSelected");

					$("#safeguardTypeCode").textbox('setValue', node.params.obj.safeguardTypeCode);
					$("#safeguardTypeId").val(node.params.obj.id);
					$("#safeguardTypeName").textbox('setValue', node.params.obj.safeguardTypeName);
				}
			}, {
				text: "删除",
				iconCls: "icon-remove",
				handler: function () {
					var selected = datagrid.datagrid("getSelected");
					if (!selected) {
						$.messager.alert("提示", "请选择一条数据再进行操作！");
						return false;
					}

					$.messager.confirm("提示", "确定要删除选中的记录吗？", function (r) {
						if (r) {
							$.post(ctx + "/rms/" + moduleContext + "/delete", {id: selected.id}, function (data) {
								if (data.code === 1) {
									datagrid.datagrid("reload").datagrid("clearSelections");
									form.form("reset");
									$("input[name=id]").val("");
								}
								$.messager.show({title: "提示", msg: data.message, timeout: 5000, showType: 'slide'});
								// $('#tree').tree("reload");

								var node = $('#tree').tree("getSelected");

								$("#safeguardTypeCode").textbox('setValue', node.params.obj.safeguardTypeCode);
								$("#safeguardTypeId").val(node.params.obj.id);
								$("#safeguardTypeName").textbox('setValue', node.params.obj.safeguardTypeName);
							});
						}
					});
				}
			}*/], onSelect: function (index, row) {
				datagridSelectedRow = row;
				fillback(row)
			}, onLoadSuccess: function () {
				loadedData = datagrid.datagrid("getData");
				var items = [];
				_.each(loadedData.rows, function (data) {
					var item = _.pick(data, 'safeguardProcessName', 'id');
					items.push({"label": item.safeguardProcessName, "id": item.id});
				});
				pro_selector.combobox("loadData", items);
			}


		});

		function fillback(data) {
			// 禁用所有控件
			$("td[name='time_params'] input").each(function () {
				disable_timeparams(this.lang, true);
			})
			// 回填之前清空选中的数据
			$("td[name='time_params']").find('input').attr('checked', false);
			$("input[type='radio']").attr("ckecked", false);
			$("select[id$='Fb'],select[id$='ProId']").combobox("clear");
			$("input[id$='Range']").spinner('clear');


			// ----- 开始回填
			$("#id").val(data.id);
			$("#safeguardTypeId").val(data.safeguardTypeId);
			$("#safeguardProcessId").val(data.safeguardProcessId);
			$("#safeguardTypeCode").val(data.safeguardTypeCode);
			$("#safeguardProcessCode").val(data.safeguardProcessCode);
			$("#safeguardTypeName").textbox("setValue", data.safeguardTypeName);
			$("#safeguardProcessName").textbox("setValue", data.safeguardProcessName);


			// ---
			var selectedStatus = JSON.parse(data.selectedStatus);
			// 选中[过程时间类别]
			if(selectedStatus){
				_.each(selectedStatus.proTimeType, function (v, i) {
					$("td[name='time_params']").find("input[value='" + v + "']").click();
				});
			}
			// 选中类别下的子项目
			_.each(selectedStatus, function (v, k, o) {
				if (typeof(selectedStatus[k]) == "string") {
					$("input[name='" + k + "'][value='" + v + "']").click();
				}
			});
			// 回填子项目的具体属性
			_.each(data, function (v, k) {
				if (v && v != null && v != '') {
					// 判断是combobox 就回填选择的值
					if (k.lastIndexOf('Fb') != -1 || k.lastIndexOf('ProId') != -1) {
						$('#' + k).combobox('select', v);
					}
					// 判断是分钟 回填
					if (k.lastIndexOf('Range') != -1) {
						$('#' + k).spinner('setValue', v);
					}
				}
			});
			// --

		}


		// 更新设置保障过程中的参数
		$("#saveStpVO").click(function () {
			if ($(this).linkbutton('options').disabled == false) {
				var $selectedType = $("td[name='time_params'] input:checked");
				// 判断是否选中两个[过程时间类别]
				if ($selectedType.length != 2) {
					$.messager.show({title: '提示', msg: '必须选择2个过程时间类别.', timeout: 5000, showType: 'slide'});
					return false;
				}
				//
				var requestData = {};
				// 保存选中状态
				var selectedStatus = {};
				var startTime_r = $("input[name='startTime_r']:checked").val() == undefined ? null : $("input[name='startTime_r']:checked").val();
				var endTime_r = $("input[name='endTime_r']:checked").val() == undefined ? null : $("input[name='endTime_r']:checked").val();

				var proRange_r = $("input[name='proRange_r']:checked").val() == undefined ? null : $("input[name='proRange_r']:checked").val();
				var proTimeType = [];

				$("input[name='proTimeType']:checked").each(function () {
					proTimeType.push($(this).val());
				});
				selectedStatus.proTimeType = proTimeType;
				selectedStatus.startTime_r = startTime_r;
				selectedStatus.endTime_r = endTime_r;
				selectedStatus.proRange_r = proRange_r;
				//  end

				// ----------- start ---------
				requestData['selectedStatus'] = JSON.stringify(selectedStatus);
				// 获取选择的[过程时间类别]
				$selectedType.each(function () {
					var $seltd = $("tr[name='" + this.lang + "']").find("input[type='radio']:checked");
					var $sub = $seltd.parent().siblings("td");
					if (this.value == 1 || this.value == 2) {
						// 1,2都是相对计起计达
						if ($seltd.val() == 1 || $seltd.val() == 2) {
							var $jia = $sub.find("select"), $min = $sub.find("input.easyui-numberspinner");
							requestData[$jia.attr("id")] = $jia.combo("getValue");
							requestData[$min.attr("id")] = $min.spinner("getValue");
						} else if ($seltd.val() == 3) {// 相对于过程
							var $process = $sub.find("select");
							requestData[$process.attr("comboname")] = $process.combo("getValue");
							requestData[$process.siblings("input").attr("name")] = $process.siblings("input").val();
							var $siblings = $seltd.parent().parent().next("tr");
							var $jia = $siblings.find("select"), $min = $siblings.find("input.easyui-numberspinner");
							requestData[$jia.attr("id")] = $jia.combo("getValue");
							requestData[$min.attr("id")] = $min.spinner("getValue");
						}
					} else if (this.value == 3) {
						var $jue = $sub.find("input.easyui-numberspinner");
						requestData[$jue.attr("id")] = $jue.spinner("getValue");
					}
				});
				// extend baseData
				// requestData = $.extend({}, datagridSelectedRow, requestData);
				requestData['id'] = datagridSelectedRow.id;
				// request url
				$.post(ctx + "/rms/" + moduleContext + "/setTimeSeq", requestData, function (data) {
					if (data.code === 1) datagrid.datagrid("reload");
					$.messager.show({title: "提示", msg: data.message, timeout: 5000, showType: 'slide'});
				});

			}
		});

		//拿到specialFilteringColumnDefinition进行合并操作
		_.each(dictColumns, function (field) {

			var enumValues = [{value: '', text: '全部'}], dictValue = getAllDictValue(field);
			// 遍历可选值，构造datagrid-filter能够识别的JSON对象
			_.each(_.allKeys(dictValue), function (key) {
				enumValues.push({value: _.propertyOf(dictValue)(key), text: _.propertyOf(dictValue)(key)});
			});

			specialFilteringColumnDefinition.push(comboboxFilterDefinetion(field, enumValues));
		});
		datagrid.datagrid('enableFilter', specialFilteringColumnDefinition);


		// 是否禁用控件
		function disable_timeparams(name, is) {
			var $tr = $("tr[name='" + name + "']");
			$tr.find("input").attr("disabled", is);
			// 根据true or false 禁用或启用
			var dis = is ? "disable" : "enable";
			$tr.find("input.easyui-numberspinner").spinner(dis);
			$tr.find("select").combo(dis);
		}

		// 初始化时禁用所有控件, 点击选择解除禁用
		$("td[name='time_params'] input").each(function () {
			disable_timeparams(this.lang, true);
		}).click(function () { // 点击 解禁控件
			// 判断已经选择几个checkbox
			if ($("td[name='time_params'] input:checked").length > 2) {
				$.messager.slide('已经选择2个过程,不需要继续选择.');
				return false;
			}
			if ($(this).is(":checked")) {
				disable_timeparams(this.lang, false);
			} else {
				disable_timeparams(this.lang, true);
			}
		});


	});

	/***
	 * 通用的Easyui字典值转换方法
	 * @param value 值
	 * @param row 行
	 * @param index 索引
	 * @param field 字段名
	 */
	function globalFormat(value, row, index, field) {
		return _.propertyOf(getAllDictValue(field))(value);
	}

	/**
	 * 拿到可用的字典值
	 * @param fieldCode 字段名
	 */
	function getAllDictValue(field) {
		return _.last(_.values(_.pick(dict, _.last(_.keys(_.pick(mapping, function (value, key, object) {
			return _.contains(value, field);
		}))))));
	}
</script>

<%--<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>--%>
</body>
</html>