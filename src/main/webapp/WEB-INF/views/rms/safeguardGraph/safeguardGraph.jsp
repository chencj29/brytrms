<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
	<title>保障过程图</title>
	<link rel="stylesheet" href="${ctxAssets}/scripts/gantt/gantt.css">
</head>
<body>
<div class="easyui-layout" data-options="fit:true">

	<div data-options="region:'west',split:true" title="保障类型和座位数" style="width:260px;">
		<%--<ul class="easyui-tree" id="tree"></ul>--%>
		<div id="p" class="easyui-panel" fit="true"
		     data-options="border:false">
			<table>
				<tr>
					<td style="width: 220px;">保障子类型:</td>
					<td style="width: 50%;"><select style="width: 80%;" class="easyui-combobox" name="" id="safeguardType"></select></td>
					<td style="width: 10%;"><input type="button" id="viewCharts" value="查看"></td>
				</tr>
				<tr>
					<td style="width: 220px;">座位数:</td>
					<td style="width: 50%;">
						<select style="width: 80%;" class="easyui-combobox" name="" id="seat_num"></select>
					</td>
					<td style="width: 10%;"><input type="button" id="generateTemplate" value="生成"></td>
				</tr>
			</table>
			<table class="easyui-datagrid" id="dyna-datagrid" style="width:99%;height:250px"
			       data-options="singleSelect:true,collapsible:true">
				<thead>
				<tr>
					<th data-options="field:'safeguardTypeName'">保障类型</th>
					<th data-options="field:'safeguardProcessName'">进程名称</th>
					<th data-options="field:'seatNum',align:'right'">座位</th>
					<th data-options="field:'from',align:'right'">开始</th>
					<th data-options="field:'to'">结束</th>
				</tr>
				</thead>
			</table>
		</div>
	</div>

	<div data-options="region:'center'" title="保障过程图">
		<div>
			<div id="container" class="gantt" style="width: 99%;height:99%;"></div>
		</div>
	</div>
</div>


<%--
<script src="${ctxAssets}/scripts/Highcharts-4.2.3/js/highcharts.js"></script>
<script src="${ctxAssets}/scripts/Highcharts-4.2.3/js/highcharts-more.js"></script>
--%>
<script src="${ctxAssets}/scripts/Highcharts-4.2.3/stock/highstock.js"></script>
<script>
	var processTimeLengths = [], processTime = {}, processTimeArray = [], processTimeArrayTemp = [],
			processCount = [], datagrid_datas = [], seat, safeguard, datas, parentSafeguardCode, kcm, gcm;

	$(function () {
		$('#generateTemplate').attr('disabled', 'disabled');
		<shiro:hasPermission name="rms:safeguardDuration:insert">$('#generateTemplate').removeAttr('disabled');
		</shiro:hasPermission>
		// 初始化保障进程
		$("#safeguardType").combobox({
			panelHeight: 'auto', panelMaxHeight: '200',
			url: ctx + '/rms/safeguardType/getSubList',
			valueField: 'id',
			textField: 'safeguardTypeName',
			onSelect: function (v) {
				$("#seat_num").combobox({
					panelHeight: 'auto', panelMaxHeight: '200',
					url: ctx + '/rms/safeguardDuration/listJson?safeguardTypeId=' + v.id,
					valueField: 'id',
					textField: 'maxSeating'
				});
			}
		});
		$("#seat_num").combobox(); // 加载后初始化 解决错位的问题

		//


		// 查看保障子类型的保障图
		$("#viewCharts").click(function () {
			var safeguardTypeId = $("#safeguardType").combobox('getValue');
			if (!safeguardTypeId || safeguardTypeId.length <= 0) {
				$.messager.show({title: "提示", msg: "请选择保障子类型! ", timeout: 3000});
				return;
			}
			var safeguards = $("#safeguardType").combobox('getData');
			safeguard = _.find(safeguards, function (v) {
				return v.id == safeguardTypeId;
			});
			parentSafeguardCode = safeguard['parentSafeguardCode'];

			//
			var seatId = $("#seat_num").combobox('getValue');
			if (!seatId || seatId.length <= 0) {
				$.messager.show({title: "提示", msg: "请选择座位数! ", timeout: 3000});
				return;
			}
			var seats = $("#seat_num").combobox('getData');
			seat = _.find(seats, function (v) {
				return v.id == seatId;
			});
			if (!seat || !seat['timeLength']) {
				$.messager.show({title: "提示", msg: "系统错误,请联系管理员! ", timeout: 3000});
				return;
			}
			var timeLength = seat['timeLength'];
			// 根据保障子类型 查询保障过程
			$.getJSON(ctx + '/rms/safeguardTypeToProcess/pTimeSeq', {'safeguardTypeId': safeguardTypeId}, function (resp) {
				if (resp && resp.rows) {
					datas = resp.rows, processTimeArray = [], processTimeArrayTemp = [], datagrid_datas = [];
					//
					var datagird_temp = {};

					// 提取组合名称,相同组合名称的放在一起
					var catagories = _.uniq(_.pluck(datas, 'processCompoundName'));
					// 计算展示时间
					_.each(datas, function (v, k) {
						var temp = {name: v['processCompoundName'], intervals: []};
						if (v && v.selectedStatus) {
							var fromTo = calcProcessTime(v, timeLength, datas);
							temp.intervals.push(fromTo);
						} else {
							temp.intervals.push({from: 0, to: 0, label: v['safeguardProcessName']});
						}
						processTimeArrayTemp.push(temp);
						//
						datagird_temp = {
							safeguardTypeName: v.safeguardTypeName, safeguardProcessName: v.safeguardProcessName,
							seatNum: seat['maxSeating'], from: temp.intervals[0].from, to: temp.intervals[0].to,
							id: v['id'], safeguardProcessId: v['safeguardProcessId'], safeguardTypeId: v['safeguardTypeId'],
							safeguardProcessCode: v['safeguardProcessCode'], safeguardProcessName: v['safeguardProcessName'],
							safeguardTypeCode: v['safeguardTypeCode'], safeguardTypeName: v['safeguardTypeName'],
							processCompoundName: v['processCompoundName']
						};
						datagrid_datas.push(datagird_temp);
					});

					var commName = '开关舱门';
					// 构造开舱门 关舱门时间点
					if (parentSafeguardCode == 'dj') {
						var data = {name: commName, intervals: [{from: 0, to: 0, label: '开舱门'}]};
						catagories.push(commName);
						processTimeArrayTemp.push(data);
					} else if (parentSafeguardCode == 'dc') {
						var data = {name: commName, intervals: [{from: 0, to: 0, label: '关舱门'}]};
						catagories.push(commName);
						processTimeArrayTemp.push(data);
					} else if (parentSafeguardCode == 'lb') {
						catagories.push(commName);
						var data = {name: commName, intervals: [{from: 0, to: 0, label: '开舱门'}]};
						processTimeArrayTemp.push(data);
						//
						var data2 = {name: commName, intervals: [{from: timeLength, to: timeLength, label: '关舱门'}]};
						processTimeArrayTemp.push(data2);
					}


					// 合并相同的符合名称
					_.each(catagories, function (v, k) {
						var arr = _.where(processTimeArrayTemp, {name: v});
						var temp;
						if (arr && arr.length > 1) {
							_.each(arr, function (v, k) {
								if (k == 0) {
									temp = v;
								} else {
									temp.intervals = temp.intervals.concat(v.intervals);
								}
							});
							processTimeArray.push(temp);
						} else if (arr && arr.length == 1) {
							processTimeArray.push(arr[0]);
						}
					});

					// 初始化保障过程图
					initHighCharts(_.sortBy(catagories, function (v, k) {
						return 0 - k;
					}), processTimeArray);

					// 初始化datagrid
					$('#dyna-datagrid').datagrid({
						data: datagrid_datas
					});

                    $('.highcharts-credits').hide();
				}
			});
		});


		$("#generateTemplate").click(function () {
			if (!datagrid_datas || datagrid_datas.length == 0) {
				$.messager.alert("提示", "请先点击[查看]按钮");
				return;
			}

			// 构造数据
			var array = [], temp = {};
			_.each(datagrid_datas, function (v, k) {
				temp = {
					'safeguardType': v['safeguardTypeId'], 'safeguardProcess': v['safeguardProcessId'],
					'safeguardTypeCode': v['safeguardTypeCode'], 'safeguardTypeName': v['safeguardTypeName'],
					'safeguardProcessCode': v['safeguardProcessCode'], 'safeguardProcessName': v['safeguardProcessName'],
					'seatNum': v['seatNum'], 'safeguardProcessFrom': v['from'], 'safeguardProcessTo': v['to'],
					'processCompoundName': v['processCompoundName']
				};
				array.push(temp);
			});

			$.ajax({
				url: ctx + '/rms/safeguardSeatTimelong/batchModify',
				type: "post",
				contentType: "application/json",
				dataType: "json",
				data: JSON.stringify(array),
				success: function (resp) {
					if (resp && resp.code == 1) {
						$.messager.alert('提示', '保存成功');
					}
				}
			});
		});

	});


	function calcProcessTime(processTemp, timeLength, datas) {
		if (!processTemp || !processTemp.selectedStatus) {
			return {form: 0, to: 0, label: processTemp['safeguardProcessName']};
		}
		var selectedStatus = JSON.parse(processTemp.selectedStatus), calcSubType;
		// def variable
		var start_oper, start_vairable, start_process;
		var end_oper, end_vairable, end_process, time_lang;
		var point = 0;

		// 获取 两个过程的参数
		var processTimes = calcStartEndTime(selectedStatus, timeLength, processTemp);
		var firstTemp = processTimes[0], twoTemp = processTimes[1];
		var time_langtemp, processTimeTemp, from, to;
		// 判断选择项是否正常
		if (parentSafeguardCode == 'dj') {
			if (firstTemp.type == 0 || twoTemp.type == 0) {
				$.messager.alert('提示', '单进航班不能设置相对于关舱门');
				throw new Error('safeguardProcess settings is error !');
			}
			kcm = 0;
		} else if (parentSafeguardCode == 'dc') {
			if (firstTemp.type == 1 || twoTemp.type == 1) {
				$.messager.alert('提示', '单出航班不能设置相对于开舱门');
				throw new Error('safeguardProcess settings is error !');
			}
			gcm = 0;
		} else if (parentSafeguardCode == 'lb') {
			kcm = 0, gcm = kcm + timeLength;
		}

		// 如果第三个存在则根据1,2互推
		if (_.contains(selectedStatus.proTimeType, '3')) {
			time_langtemp = twoTemp.time_lang;
			if (_.contains(selectedStatus.proTimeType, '1')) {
				if (firstTemp.process) {
					var point = recurGetProcessTime(firstTemp, datas, timeLength);
					from = eval(point + firstTemp.oper + firstTemp.variable);
					to = from + time_langtemp;
				} else {
					if (firstTemp.type == 0) {
						from = eval(gcm + firstTemp.oper + firstTemp.variable);
						to = eval(gcm + twoTemp.time_lang);
					} else if (firstTemp.type == 1) {
						from = eval(kcm + firstTemp.oper + firstTemp.variable);
						to = eval(kcm + twoTemp.time_lang);
					}
				}
			} else if (_.contains(selectedStatus.proTimeType, '2')) {
				if (firstTemp.process) {
					var point = recurGetProcessTime(firstTemp, datas, timeLength);
					to = eval(point + firstTemp.oper + firstTemp.variable);
					from = to - time_langtemp;
				} else {
					if (firstTemp.type == 0) {
						from = eval(gcm + firstTemp.oper + firstTemp.variable);
						to = eval(gcm - twoTemp.time_lang);
					} else if (firstTemp.type == 1) {
						from = eval(kcm + firstTemp.oper + firstTemp.variable);
						to = eval(kcm - twoTemp.time_lang);
					}
				}
			}
		} else {
			if (firstTemp.process) {
				var point = recurGetProcessTime(firstTemp, datas, timeLength);
				from = eval(point + firstTemp.oper + firstTemp.variable);
			} else {
				if (firstTemp.type == 0) {
					from = eval(gcm + firstTemp.oper + firstTemp.variable);
				} else if (firstTemp.type == 1) {
					from = eval(kcm + firstTemp.oper + firstTemp.variable);
				}
			}
			if (twoTemp.process) {
				var point2 = recurGetProcessTime(twoTemp, datas, timeLength);
				to = eval(point2 + twoTemp.oper + twoTemp.variable);
			} else {
				if (twoTemp.type == 0) {
					to = eval(gcm + twoTemp.oper + twoTemp.variable);
				} else if (twoTemp.type == 1) {
					to = eval(kcm + twoTemp.oper + twoTemp.variable);
				}
			}
		}

		return {from: parseFloat(from), to: parseFloat(to), label: processTemp['safeguardProcessName']};

	}

	// 获取过程数据 的起点
	function recurGetProcessTime(firstTemp, datas, timeLength) {
		var processTimeTemp, point = 0;
		if (firstTemp.process) {
			var temp = _.find(datas, function (v) {
				return v.id == firstTemp.process;
			});
			if (temp) {
				processTimeTemp = calcProcessTime(temp, timeLength, datas);
				point = processTimeTemp.to;
			} else {
				$.messager.alert('提示', '保障过程[' + firstTemp['safeguardProcessName'] + ']时序设置错误,请检查过程时序设置!');
				throw new Error('safeguardProcess is not found !');
			}
		}
		return point;
	}

	function calcStartEndTime(selectedStatus, timeLength, processTemp) {
		// 获取选择的两个值
		var firstSelected = selectedStatus.proTimeType[0];
		var twoSelected = selectedStatus.proTimeType[1];

		var firstProcessTime = getELVal(firstSelected, selectedStatus, timeLength, processTemp);
		var twoProcessTime = getELVal(twoSelected, selectedStatus, timeLength, processTemp);

		return [firstProcessTime, twoProcessTime];
	}

	function getELVal(timeType, selectedStatus, timeLength, processTemp) {
		var calcSubType, oper, variable, process, time_lang, type;
		if (timeType == 1) {
			calcSubType = selectedStatus.startTime_r;
			if (calcSubType == 1) {
				oper = processTemp['startDepFb'];
				variable = processTemp['startDepRange'];
				type = 0;
			} else if (calcSubType == 2) {
				oper = processTemp['startArrFb'];
				variable = processTemp['startArrRange'];
				type = 1;
			} else if (calcSubType == 3) {
				oper = processTemp['startProFb'];
				variable = processTemp['startProRange'];
				process = processTemp['startProId'];
			}
		} else if (timeType == 2) {
			calcSubType = selectedStatus.endTime_r;
			if (calcSubType == 1) {
				oper = processTemp['endDepFb'];
				variable = processTemp['endDepRange'];
				type = 0;
			} else if (calcSubType == 2) {
				oper = processTemp['endArrFb'];
				variable = processTemp['endArrRange'];
				type = 1;
			} else if (calcSubType == 3) {
				oper = processTemp['endProFb'];
				variable = processTemp['endProRange'];
				process = processTemp['endProId'];
			}
		} else if (timeType == 3) {
			calcSubType = selectedStatus.proRange_r;
			if (calcSubType == 1) {
				time_lang = processTemp['absoluteRange'];
			} else if (calcSubType == 2) {
				time_lang = (parseFloat(processTemp['percentRange']) / 100) * timeLength;
			}
		}
		return {oper: oper, variable: variable, process: process, time_lang: time_lang, safeguardProcessName: processTemp['safeguardProcessName'], type: type};
	}


	function initHighCharts(categories, tasks) {
		// Define tasks
		var tasks = tasks;

		// Define milestones
		// re-structure the tasks into line seriesvar series = [];
		var series = [];
		$.each(tasks.reverse(), function (i, task) {
			var item = {
				name: task.name,
				data: []
			};
			$.each(task.intervals, function (j, interval) {
				item.data.push({
					x: interval.from,
					y: i,
					label: interval.label,
					from: interval.from,
					to: interval.to
				}, {
					x: interval.to,
					y: i,
					from: interval.from,
					to: interval.to
				});

				// add a null value between intervals
				if (task.intervals[j + 1]) {
					item.data.push([(interval.to + task.intervals[j + 1].from) / 2, null]);
				}

			});
			series.push(item);
		});

		// create the chart
		var chart = new Highcharts.Chart({
			chart: {
				renderTo: 'container'
			},
			title: {
				text: '航班保障过程图'
			},
			xAxis: {
				type: 'linear'
			},
			yAxis: {
				categories: categories,
				tickInterval: 1,
				tickPixelInterval: 200,
				labels: {
					style: {
						color: '#525151',
						font: '12px Helvetica',
						fontWeight: 'bold'
					}
				},
				startOnTick: false,
				endOnTick: false,
				title: {
					text: '保障过程'
				},
				minPadding: 0.2,
				maxPadding: 0.2,
				fontSize: '15px'

			},
			legend: {
				enabled: false
			},
			tooltip: {
				formatter: function () {
					return '<b>' + tasks[this.y].name + '</b><br/>' + this.point.options.from + ' ~ ' + this.point.options.to;
				}
			},
			plotOptions: {
				line: {
					lineWidth: 10,
					marker: {
						enabled: false
					},
					dataLabels: {
						enabled: true,
						align: 'left',
						formatter: function () {
							return this.point.options && this.point.options.label;
						}
					}
				}
			},
			series: series
		});
	}
</script>


</body>
</html>