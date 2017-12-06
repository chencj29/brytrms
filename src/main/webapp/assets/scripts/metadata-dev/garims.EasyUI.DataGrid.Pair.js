var moduleContext = "flightPlanPair", isExpand = true;
/**
 * 过滤列声明接口(必须，可空)
 */
var specialFilteringColumnDefinition = [], isFit = isFitColumns = false;

function formatFlightNum(val, row, index) {
    if (row.flightNum != null && row.flightNum != '') {
        return row.flightCompanyCode + row.flightNum;
    } else {
        return "";
    }
}

function formatFlightNum2(val, row, index) {
    if (row.flightNum2 != null && row.flightNum2 != '') {
        return row.flightCompanyCode2 + row.flightNum2;
    } else {
        return "";
    }
}

/**
 * form: crud表单对象
 * datagrid: 数据表格对象
 * dictColumns: 字典字段
 * comboboxFilterDefinetion: 字典类型字段过滤定义 （需传入字段名及JSON枚举值）
 *      例如：[{value: '', text: '全部'}, {value: '0', text: '不可用'}, {value: '1', text: '可用'}]
 */
var dict = dictMap([{key: "sys", value: "progress_remind_percent_val"}]), form, datagrid, dictColumns,
    comboboxFilterDefinetion = function (fieldCode, enumValues) {
        return {
            field: fieldCode, type: 'combobox', options: {
                panelHeight: 'auto', data: enumValues, onChange: function (value) {
                    if (value == '') datagrid.datagrid('removeFilterRule', fieldCode);
                    else datagrid.datagrid('addFilterRule', {field: fieldCode, op: ['equal'], value: value});
                    datagrid.datagrid('doFilter');
                }
            }
        }
    }, ganttOptions = {
        resizableHandler: false,
        draggable: false,
        multi: true,
        auto: false,
        holdCount: 0,
        collisionThresholds: 0,
        timeline: true,
        showLabel: true,
        showLabelWidth: 30,
        defaultLabel: '',
        minWidth: 5,
        draggableHandler: false,
        collisionDetectionHandler: progressCollisionDetectionHandler,
        dblClick: function (evt) {
            if (_(permissionList).isEmpty() || !permissionList.includes(evt.data.extra.progressId)) {
                layer.msg('您没有操作权限!');
                return false;
            }

            //通过Gantt图中的ID找到DataGrid相应的行
            var rowData = datagrid.datagrid('getRows').filter((x) => x.id === evt.data.flightDynamicId)[0];

            // 弹出对话框让用户填写实际开始及完成时间
            var templateArgs = {
                planDate: formatDate(rowData.planDate),
                inCode: formatFlightNum("", rowData),
                outCode: formatFlightNum2("", rowData),
                aircraftTypeCode: rowData.aircraftTypeCode,
                placeNum: rowData.placeNum,
                text: evt.data.text,
                pst: evt.data.extra['planStartDate'] || (evt.data.startDate + " " + evt.data.startTime),
                pot: evt.data.extra['planEndDate'] || (evt.data.endDate + " " + evt.data.endTime),
                flightDynamicId: evt.data.flightDynamicId,
                processId: evt.data.id,
                ast: evt.data.extra.actualStartTime ? moment(evt.data.extra.actualStartTime).format('YYYY-MM-DD HH:mm') : '',
                aot: evt.data.extra.actualOverTime ? moment(evt.data.extra.actualOverTime).format('YYYY-MM-DD HH:mm') : '',
                opName: evt.data.extra.opName,
                carType: evt.data.extra.carType,
                carCode: evt.data.extra.carCode
            };

            layer.open({
                title: '实际时间设置', btn: ['确定', '取消'], area: ['630px', '405px'],
                type: 1, scrollbar: false, move: false, content: template('timeFormContainer')(templateArgs),
                yes: function (index, layero) {
                    var planStartTime = evt.data.startDate + ' ' + evt.data.startTime,
                        planOverTime = evt.data.endDate + ' ' + evt.data.endTime;
                    // 获取表单里的数据，更新「进程」数据，如果当前「进程」有状态（预警或提醒），填写完成时间后要清除状态。
                    // 如果element的index为0，填写实际开始时间后，其余的进程要相应的推移。
                    // 在填写实际开始时间之后，要绘制实际进程的element。
                    if ($("#iptActualOverTime").val() && !$("#iptActualStartTime").val()) {
                        layer.msg("请填写实际开始时间！");
                        return false;
                    }

                    $.post(ctx + "/rms/flightPlanPair/set-progress-act-time", $.extend({}, {
                        progressId: evt.data.id, start: $("#iptActualStartTime").val(), over: $("#iptActualOverTime").val(),
                        opName: $("#iptOpName").val(), carType: $("#iptCarType").val(), carCode: $("#iptCarNumber").val()
                    }, extraData), function (data) {
                        layer.msg(data.message);
                        ganttRerender($(".gantt[id=" + evt.data.flightDynamicId + "]"));
                        layer.close(index);
                    });
                    return false;
                }
            });
        },
        leftAxisClickHandler: function () {
            var $current = $(this), // 当前yAxis对象
                $container = $current.parents('.gantt'), // Gantt图容器
                $ganttRows = $(".rowDiv[_rowindex=" + $current.attr('_idx') + "]", $container), // Gantt图与yAxis对称的行
                settings = $container.data('options'), progressId = $current.attr('_value'), pairId = $container.attr("id");

            layer.confirm('您确定要移除「' + $current.text() + '」保障过程吗？', {icon: 3, title: '提示'}, function (index) {
                $.post(ctx + '/rms/flightPlanPair/removeProgress', $.extend({
                    progressId: progressId,
                    pairId: pairId
                }, extraData), function (data) {
                    if (data.code) {
                        layer.msg('移除成功!');
                        ganttRerender($container);
                    } else layer.alert('移除失败：' + data.message);
                });
            });

        }
    }, timerKeyHoler, pagination, safeguardTypeCode_Value, record, recordId, progressList, permissionList, dataGridMeta;


function ganttRerender($container) {
    $container = $container.gantt($.extend({}, ganttOptions, $utils.ajaxLoadFragments(ctx + '/rms/flightPlanPair/queryFlightTimeLong?pairId=' + $container.attr("id"))));
    var height = $(".gantt-left-panel", $container).height() + 45;
    $container.parents(".wrapper, .container").height(height);
    var pos = $(".airplane-wrapper > div:first", $container).position();
    var ganttContent = $(".gantt-content", $container);
    ganttContent.scrollLeft(pos.left - 200);
}

$(function () {
    loadDataGridContent(); //加载表格内容

    $utils.ajaxLoadFragments(ctx + '/rms/safeguardProcess/list', (resp) => progressList = resp);
    $utils.ajaxLoadFragments(ctx + '/rms/flightPlanPair/getPrivileges', (resp) => permissionList = resp);

    form = $("#modifyForm");

    datagrid = $("#datagrid").datagrid({
        rownumbers: true,
        url: "./listPage",
        method: 'get',
        border: true,
        fit: true,
        fitColumns: false,
        pagination: false,
        pageList: [15],
        pageNumber: 1,
        pageSize: 15,
        idField: "id",
        singleSelect: true,
        showFooter: true,
        remoteSort: false,
        remoteFilter: false,
        multiSort: false,
        rowStyler: commonRowStyler,
        toolbar: [{
            text: "特服信息", iconCls: "icon-edit", handler: function () {
                var row = datagrid.datagrid("getSelected");
                if (!row) {
                    $.messager.slide('请选择一条数据');
                    return;
                }
                $("#specialServiceDatagrid").datagrid({
                    url: ctx + "/rms/flightPlanPair/specialServiceList",
                    queryParams: {flightCompanyCode: row['flightCompanyCode'], flightNum: row['flightNum']}
                });
                $("#specialServiceDialog").dialog({
                    title: '特服信息',
                    width: 800,
                    height: 400,
                    closed: false,
                    cache: false,
                    modal: true
                });
            }
        }, {
            text: '保障过程明细', iconCls: 'icon-application_view_detail', handler: function () {
                if (_.isEmpty(recordId)) {
                    $.messager.slide('请选择一个航班数据。');
                    return;
                }
                $('#dynamic-detail-template').html(template('showDynamicDetail', {pair: record}));

                $('#_safeguardMainType').combobox({
                    url: ctx + '/rms/safeguardType/listData?parentSafeguardCode=root&sort=safeguardTypeCode&order=asc',
                    valueField: 'safeguardTypeCode', textField: 'safeguardTypeName',
                    onChange: function (val) {
                        var $safeguardTypeCode = $('#safeguardTypeCode');
                        $safeguardTypeCode.combobox('clear');
                        $safeguardTypeCode.combobox('reload', ctx + '/rms/safeguardType/listData?parentSafeguardCode=' + val + '&sort=safeguardTypeCode&order=asc');
                    }
                });

                safeguardTypeCode_Value = $('#safeguardTypeCode').val();

                $('#safeguardTypeCode').combobox({
                    valueField: 'safeguardTypeCode', textField: 'safeguardTypeName',
                    onLoadSuccess: function () {
                        var typeCodes = $('#safeguardTypeCode').combobox('getData');
                        if (!_.isEmpty(safeguardTypeCode_Value) && _.chain(typeCodes).pluck('safeguardTypeCode').contains(safeguardTypeCode_Value).value())
                            $('#safeguardTypeCode').combobox('setValue', safeguardTypeCode_Value)
                    }
                });

                if (!_.isEmpty(safeguardTypeCode_Value)) $('#_safeguardMainType').combobox('setValue', safeguardTypeCode_Value.substring(0, 2));

                // 保障过程详情
                $('#safeguard_process_grid').datagrid({url: ctx + '/rms/flightPlanPair/queryFlightTimeLongForDatagrid?pairId=' + recordId});

                // 保存 更改保障类型
                $('#modifySafeguardTypeButton').linkbutton({
                    onClick: function () {
                        var safeguardTypeCode = $('#safeguardTypeCode').combobox('getValue');
                        var safeguardTypeName = $('#safeguardTypeCode').combobox('getText');
                        if (_.isEmpty(safeguardTypeCode)) {
                            $.messager.alert('提示', '请选择保障子类型!');
                            return;
                        }

                        var currentRow = datagrid.datagrid('getSelected');

                        if (currentRow && safeguardTypeName === currentRow.safecuardTypeName && safeguardTypeCode === currentRow.safecuardTypeCode) {
                            $.messager.slide('更新成功!');
                            $('#dynamic-detail-dialog').dialog('close');
                            return;
                        }

                        $.post(ctx + '/rms/flightPlanPair/modifySafeguardTypeCode', {
                            pairId: recordId,
                            safeguardTypeCode: safeguardTypeCode,
                            safeguardTypeName: safeguardTypeName,
                            random: _socket_uuid
                        }, function (data) {
                            if (data.code == -3) {
                                $('#dynamic-detail-dialog').dialog('close');
                                $.messager.alert('提示', data.message);
                            } else if (!_.isEmpty(safeguardTypeName) && data.code == 1) {
                                $.messager.slide('更新成功!');
                                $('#dynamic-detail-dialog').dialog('close');
                                datagrid.datagrid('updateRow', {
                                    index: datagrid.datagrid('getRowIndex', record),
                                    row: {safecuardTypeCode: safeguardTypeCode, safecuardTypeName: safeguardTypeName}
                                });
                                if (isExpand && $(".gantt#" + recordId).length !== 0) ganttRerender($(".gantt#" + recordId));
                            } else $.messager.alert('提示', data.message);
                        });
                    }
                });
                $('#dynamic-detail-dialog').dialog('open');
            }
        }, {
            text: '添加保障过程', iconCls: 'icon-add', handler: function () {
                var selectedRow = datagrid.datagrid('getSelected');
                if (selectedRow) {
                    $('#add-progress-template').html(template('addProgressTpl', {
                        inFlightNum: $utils.superTrim(selectedRow.flightCompanyCode) + $utils.superTrim(selectedRow.flightNum),
                        outFlightNum: $utils.superTrim(selectedRow.flightCompanyCode2) + $utils.superTrim(selectedRow.flightNum2),
                        planDate: dateFmt(selectedRow.planDate),
                        placeNum: selectedRow.placeNum,
                        aircraftNum: selectedRow.aircraftNum
                    }));

                    let $gantt = $(".gantt#" + selectedRow.id), filterGridData = {}, yAxisValues;

                    if ($gantt.length !== 0) {
                        yAxisValues = _.pluck($gantt.data('options').yAxis, "text");
                        filterGridData.rows = progressList.rows.filter(function (progress) {
                            return !_(yAxisValues).contains(progress.safeguardProcessName);
                        });
                    } else {
                        yAxisValues = _.pluck($utils.ajaxLoadFragments(ctx + '/rms/flightPairProgressInfo/findByPairId?pairId=' + selectedRow.id), "progressRefId");
                        filterGridData.rows = progressList.rows.filter(function (progress) {
                            return !_(yAxisValues).contains(progress.id);
                        });
                    }
                    filterGridData.total = filterGridData.rows.length;

                    _.each(filterGridData.rows, row => {
                        row.redundant1 = null;
                        row.redundant2 = null;
                    });

                    $("#progress-datagrid").datagrid().datagrid("loadData", filterGridData).datagrid('enableFilter', []).datagrid('enableCellEditing');
                    $('#add-progress-dialog').dialog('open');

                    $("#btnSetPlanTime").linkbutton({
                        iconCls: 'icon-time', onClick: function () {
                        }
                    });

                    $("#btnAddProgress").linkbutton({
                        iconCls: 'icon-add',
                        onClick: function () {
                            var progressDataGrid = $("#progress-datagrid"),
                                checkedRows = progressDataGrid.datagrid('getChecked');
                            if (checkedRows.length !== 0) {
                                var invalidProgress = _(checkedRows).find(function (progress) {
                                    return !progress.redundant1 || !progress.redundant2;
                                });

                                if (invalidProgress) {
                                    layer.msg("「" + invalidProgress.safeguardProcessName + "」未填写计划开始或计划结束时间");
                                    return false;
                                }

                                var flag = true, index;
                                var rowsData = _.map(checkedRows, function (a, i) {
                                    if (!!a.redundant1) a.redundant1 = moment(a.redundant1).format("YYYY-MM-DD HH:mm");
                                    if (!!a.redundant2) a.redundant2 = moment(a.redundant2).format("YYYY-MM-DD HH:mm");
                                    if (!!a.redundant1 && !!a.redundant2) {
                                        var tmp = (moment(a.redundant2).diff(moment(a.redundant1)) >= 0);
                                        flag = flag && tmp;
                                        if (!tmp) index = i;
                                    }
                                    return a;
                                });

                                if (!flag) {
                                    layer.msg("错误：「" + rowsData[index].safeguardProcessName + "」结束时间小于开始时间！");
                                    return false;
                                }

                                $.ajax({
                                    url: ctx + '/rms/flightPlanPair/addProgress',
                                    type: 'POST',
                                    data: JSON.stringify({
                                        pairId: selectedRow.id,
                                        infos: rowsData,
                                        random: _socket_uuid
                                    }),
                                    dataType: 'json',
                                    success: function (data) {
                                        if (data.code == -3) {
                                            layer.msg(data.message);
                                            $('#add-progress-dialog').dialog('close');
                                        } else if (data.code) {
                                            layer.msg('保障过程添加成功');
                                            _(progressList).each(function (progress) {
                                                progress.redundant1 = "";
                                                progress.redundant2 = "";
                                            });
                                            var $container = $(".gantt#" + selectedRow.id);
                                            if (isExpand && $container.length !== 0) ganttRerender($container);
                                            $('#add-progress-dialog').dialog('close');
                                        } else layer.msg('添加过程失败:' + data.message);
                                    },
                                    contentType: 'application/json'
                                });
                            } else layer.msg('请选择要添加的保障过程!');
                        }
                    });

                    $("#btnCloseWindow").linkbutton({
                        iconCls: 'icon-cross',
                        onClick: function () {
                            $('#add-progress-dialog').dialog('close');
                        }
                    })
                } else layer.msg('请选择一条记录再进行操作！');
            }
        }, {
            text: '颜色设置', iconCls: 'icon-color', handler: colorSettingHandler
        }, dataGridViewSettingToolbar, {
            text: '刷新数据', iconCls: 'icon-arrow_refresh_small', handler: function () {
                datagrid.datagrid('reload');
                if (isExpand) pagination.pagination('refresh');
            }
        }],
        onSelect: function (index, row) {
            record = row;
            recordId = row.id;
        },
        onLoadSuccess: function (data) {
            $(".ganttArea").html("");

            //1.得到数据总量, 生成Pagination Bar
            pagination = $(".panelBotoomPageBar").pagination({
                total: data.total, pageSize: 3, pageList: [3], onSelectPage: loadGanttData, onRefresh: loadGanttData
            });

            //2.初始化第一页数据
            loadGanttData(pagination.pagination('options').pageNumber, pagination.pagination('options').pageSize);
        },
        onDblClickRow: function (index, row) {
            var pageNumber = Math.ceil((index + 1) / pagination.pagination('options').pageSize);
            if (pageNumber != pagination.pagination('options').pageNumber) pagination.pagination('select', pageNumber);
            var $container = $("#graphic-panel"), $current = $(".gantt#" + row.id);
            $container.scrollTop($container.scrollTop() + $current.offset().top - $container.height() / 3 + $current.height() / 3);
        },
        onClickCell: function (rowIndex, field, value) {
            if ((field == "vip" || field == "vip2") && !!value) {
                $.getJSON(ctx + "/rms/vipplan/vipinfo?id=" + value, function (msg) {
                    if (msg.code == 1) {
                        var vipDatas = {};
                        vipDatas.vipList = _.map(msg.result.vip, function (o) {
                            return vipToStr(o);
                        });
                        var vipcount = 1;
                        if (msg.result.vip.length > 1) vipcount = 1.6;
                        layer.open({
                            title: "vip信息",
                            type: 1,
                            area: ["800px", (260 * vipcount) + "px"],
                            content: template('vip_info', vipDatas)
                        });
                    }
                });
            }
        }
    });

    function loadGanttData(pageIndex, pageSize) {
        $(".ganttArea").html("");
        var localData = datagrid.datagrid('getData'), start = (pageIndex - 1) * pageSize, over = start + pageSize;
        if (over > localData.total) over = localData.total;

        localData.rows.slice(start, over).forEach(function (dynamic) {
            var $gantt = $(template("ganttContainer")({info: dynamic.virtualTipField, id: dynamic.id}));

            $.post(ctx + '/rms/flightPlanPair/queryFlightTimeLong', {pairId: dynamic.id}, function (resp) {
                if (resp && !_.isEmpty(resp.data) && !_.isEmpty(resp.yAxis)) {
                    resp.xAxis = enhanceXAxis(resp.xAxis)
                    $(".gantt", $gantt).gantt($.extend({}, ganttOptions, resp));
                }
                var height = $(".gantt-left-panel", $gantt).height() + 45;
                $gantt.css("height", height);
                $(".container", $gantt).css("height", height);

                setTimeout(function () {
                    var pos = $(".airplane-wrapper > div:first", $("#" + dynamic.id)).position();
                    var ganttContent = $(".gantt-content", $("#" + dynamic.id));
                    try {
                        ganttContent.scrollLeft(pos.left - 200);
                    } catch (e) {
                    }
                }, 0);
            });

            $gantt.appendTo($(".ganttArea"));
        });
    }


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

    $("#btnSave").click(function () {
        $.post(ctx + "/rms/" + moduleContext + "/save", $("#modifyForm").serialize(), function (data) {
            if (data.code === 1) datagrid.datagrid("reload");
            $.messager.show({title: "提示", msg: data.message, timeout: 5000, showType: 'slide'});
        });
    });

    // 10秒以后启动定时器进行监测
    setTimeout(function () {
        (function looper() {
            $(".airplane-wrapper > div[_dynamic_=true]").each(function () {
                var $planEl = $(".airplane-wrapper > div[_id_=" + $(this).attr("_id_") + "]").filter(function () {
                    return !$(this).data('o').extra.actual;
                }), planElData = $planEl.data('o'), actualData = $(this).data('o');

                // 拿到计划节点的结束时间。用当前时间与其对比
                var planStartMt = moment(planElData.startDate + " " + planElData.startTime),
                    planOverMt = moment(planElData.endDate + " " + planElData.endTime),
                    now = moment(), remindPercentVal, planMinutes = planOverMt.diff(planStartMt, "minutes"),
                    actualStartMt = moment(actualData.startDate + " " + actualData.startTime);

                try {
                    // reminPercentVal - 提醒百分比
                    remindPercentVal = _.keys(dict['sys@progress_remind_percent_val'])[0] / 100.0;
                } catch (e) {
                    remindPercentVal = .1;
                }

                // 如果实际用时超过计划用时，报警闪烁 注：计算是按分钟计时的，为了避免超时间格一分钟，后改为大于等于来判断此报警
                if (now.diff(actualStartMt, 'minutes') >= planMinutes && !$(this).hasClass("gantt-collision blink")) {
                    $(this).removeClass("gantt-warning");
                    $(this).addClass("gantt-collision blink");
                } else if (now.diff(actualStartMt, 'minutes') >= (planMinutes * (1 - remindPercentVal)) && !$(this).hasClass("gantt-warning")) {  // 预警闪烁（提醒）
                    $(this).removeClass("gantt-collision");
                    $(this).addClass("gantt-warning blink");
                }

                // 根据时间推移增加元素的长度
                $.fn.gantt("adjustPos", $(this), actualStartMt.format("YYYY-MM-DD HH:mm"), now.format("YYYY-MM-DD HH:mm"), $(".gantt[id= " + planElData.flightDynamicId + "]").data('options'));
            });
            timerKeyHoler = setTimeout(looper, 1000 * 3);
        })();
    }, 1000 * 10)

});

var _socket_uuid = $uuid(), extraData = {random: _socket_uuid},
    socket = io.connect(message_socket_server_url, {query: "ns=/global/dynamics&uuid=" + _socket_uuid});

socket.on("modification", function (data) {
    try {
        if ($('#maintabs', window.parent.document).find('.tabs-selected').text() != document.title) return;
    } catch (e) {
    }

    if (data.uuid === "" || data.uuid === "null" || !data.uuid) return false;
    _.defer(function (data,pagination) {
        let _entity = $.parseJSON(data.data), _changeList = $.parseJSON(data.changeList),
            _identify = data.identify, _compareId = _entity.id, rowIndex = -1, rowData,
            monitorType = data.monitorType, ganttOpts = $(".gantt").data("options"),
            paginationOpts = pagination.pagination('options'),
            filtered,           // 是否过滤(标识)
            filteredRowIndex,   // 过滤后的下标
            filteredRowData;    // 过滤后的数据
        //console.log(_identify, monitorType);

        // 权限判断
        if (!_.contains(currentUserPrivileges, ALL_PRIVILEGE) &&
            (!_.contains(currentUserPrivileges, _entity.flightCompanyCode) && !_.contains(currentUserPrivileges, _entity.flightCompanyCode2)) &&
            !_.contains(currentServicePrivileges, ALL_PRIVILEGE) && !_.contains(currentServicePrivileges, _entity.agentCode)) return;

        _compareId = ['FlightPairWrapper', 'FlightPlanPair'].includes(_identify) ? _entity.id : _entity.flightDynamicId;

        [filtered, filteredRowIndex, filteredRowData, rowIndex, rowData] = getRowIndex("id", _compareId);

        if ((!filtered && rowIndex !== undefined && rowIndex != -1) || (filtered && filteredRowIndex != -1)) {
            var changeWrapper = getChangeWrapper(_changeList);
            if (!filtered) datagrid.datagrid("updateRow", {index: rowIndex, row: changeWrapper});
            else {
                if (rowIndex !== undefined && rowIndex !== -1) datagrid.datagrid('updateRow', {
                    index: rowIndex,
                    row: changeWrapper
                });
                datagrid.data('datagrid').filterSource.rows[filteredRowIndex] = $.extend(datagrid.data('datagrid').filterSource.rows[filteredRowIndex], changeWrapper);
            }
            //refreshRowBGColor(changeWrapper, rowIndex);
            //判断包含有的字段才排序
            if (_.intersection(_.keys(changeWrapper), sortRules.R).length > 0) sortDateR(datagrid);
        }

        // 保障过程Gantt图监控信息, 会出现的类型为: UPDATE、INSERT
        if ((_identify === 'FlightProgressActInfo' || _identify === 'FlightPairProgressInfo') || (_identify === 'FlightPairProgressInfo' && monitorType === 'DELETE')) {
            // 重新请求Gantt Data数据
            var $gantt = $(".gantt[id=" + _entity.pairId + "]");
            $.post(ctx + '/rms/flightPlanPair/queryFlightTimeLong', {pairId: _entity.pairId}, function (resp) {
                if (resp && !_.isEmpty(resp.data) && !_.isEmpty(resp.yAxis)) {
                    resp.xAxis = enhanceXAxis(resp.xAxis)
                    $gantt.gantt($.extend({}, ganttOptions, resp));
                }
                var calcFinalHeight = $(".gantt-left-panel", $gantt).height() + 45;
                $gantt.parents(".wrapper").css("height", calcFinalHeight);
                $gantt.parents(".container").css('height', calcFinalHeight);
                try {
                    var pos = $(".airplane-wrapper > div:first", $gantt).position();
                    $(".gantt-content", $gantt).scrollLeft(pos.left - 200);
                } catch (e) {
                }
            });
        }

        if (_identify === "FlightPairWrapper" && monitorType === "INSERT") {
            _entity.placeNum = "";
            _entity.random = extraData.random;
            var filterData = $.fn.datagrid.defaults.filterMatcher.call(datagrid[0], {total: 1, rows: [_entity]});
            // 将命中的数据添加到datagrid中
            for (var matchData of filterData.rows) datagrid.datagrid("appendRow", matchData);
            // 将未命中的数据添加到filterSource中
            if (filterData.unMatchedRows.length !== 0) for (var unMatchData of filterData.unMatchedRows) datagrid.datagrid('getData').unMatchedRows.push(unMatchData);
            pagination.pagination('refresh', {total: paginationOpts.total + 1});
            sortDateR(datagrid);
        } else if (_identify === "FlightPairWrapper" && monitorType === "DELETE") {
            if (!filtered && rowIndex > -1) datagrid.datagrid('deleteRow', rowIndex);
            else {
                if (rowIndex !== undefined && rowIndex !== -1) datagrid.datagrid('deleteRow', rowIndex);
                var tmp = datagrid.data('datagrid').filterSource.rows.splice(filteredRowIndex, 1);
                var unFilteredRowIndex = _.findIndex(datagrid.datagrid('getData').unMatchedRows, a => tmp[0].id == a.id);
                if (unFilteredRowIndex != -1) datagrid.datagrid('getData').unMatchedRows.splice(unFilteredRowIndex, 1);
                //if(!_.isEmpty(_entity.id) && filteredRowIndex>-1) datagrid.datagrid('doFilter');
            }
            pagination.pagination('refresh', {total: paginationOpts.total - 1});
            sortDateR(datagrid);
        }
    }, data,pagination);
}).on("flightDynamicChange2History", function (data) {
    try {
        if ($('#maintabs', window.parent.document).find('.tabs-selected').text() != document.title) return;
    } catch (e) {
    }

    let rowIndex = -1,      // 未过滤的下标
        rowData,            // 未过滤的数据
        filtered,           // 是否过滤(标识)
        filteredRowIndex,   // 过滤后的下标
        filteredRowData;    // 过滤后的数据
    if (!data || _.isEmpty(data)) return;
    try {
        for (var _key of _(data).keys()) {
            if (_key.indexOf("flightPairWrapper") != -1) {
                let valuesArray = _key.split("#"), mType = valuesArray[0], pairId = valuesArray[1],
                    pairObject = JSON.parse(_.propertyOf(data)(_key));
                [filtered, filteredRowIndex, filteredRowData, rowIndex, rowData] = getRowIndex("id", pairId);
                if ((!filtered && rowIndex !== undefined && rowIndex != -1) || (filtered && filteredRowIndex !== -1)) {
                    // 这里要分过滤及非过滤两种情况的处理  此处是非过滤的情况
                    if (!filtered)
                        datagrid.datagrid('deleteRow', rowIndex);
                    else {
                        if (rowIndex !== undefined && rowIndex !== -1) datagrid.datagrid('deleteRow', rowIndex);
                        datagrid.data('datagrid').filterSource.rows.splice(filteredRowIndex, 1);
                    }

                    pagination.pagination('refresh', {total: pagination.pagination("options").total - 1});
                }
            }
        }
        sortDateR(datagrid);
    } catch (e) {
        console.error(e);
    }
}).on("flightDynamicInit", function (resp) {
    try {
        if ($('#maintabs', window.parent.document).find('.tabs-selected').text() != document.title) return;
    } catch (e) {
    }

    var data = JSON.parse(resp), filtered = isFiltered(), planDate = formatDate(data.planDate);

    if (filtered)
        datagrid.data('datagrid').filterSource.rows = datagrid.data('datagrid').filterSource.rows.filter(row => formatDate(row.planDate) != planDate);

    datagrid.datagrid('getData').rows.filter(row => formatDate(row.planDate) == planDate).forEach(row => {
        datagrid.datagrid('deleteRow', datagrid.datagrid('getRowIndex', row));
        pagination.pagination('refresh', {total: pagination.pagination("options").total - 1});
    });

    // 新增的时候要考虑列表被筛选的情况
    // 调用filterMatcher方法后， 命中规则的数据放在rows中, 未命中的数据会放到unMatchedRows中
    // --  add feature @ datagrid-filter.js line 157, 180 by xiaowu
    var filterData = $.fn.datagrid.defaults.filterMatcher.call(datagrid[0], {
        total: data.pairWrappers.length,
        rows: data.pairWrappers
    });

    // 将命中的数据添加到datagrid中
    for (var pair of filterData.rows) {
        datagrid.datagrid("appendRow", pair);
        pagination.pagination('refresh', {total: pagination.pagination("options").total + 1});
    }

    // 将未命中的数据添加到filterSource中
    if (filterData.unMatchedRows.length !== 0) for (var unMatchedPair of filterData.unMatchedRows) datagrid.data('datagrid').filterSource.rows.push(unMatchedPair);

    sortDateR(datagrid);
    //if (isExpand) for (var pair of data.pairWrappers) $.fn.gantt("append", generateGanttWrapper(pair), ganttOptions);
}).on('flightDynamicTime2Change', function (data) {
    try {
        if ($('#maintabs', window.parent.document).find('.tabs-selected').text() != document.title) return;
    } catch (e) {
    }

    if (!data) return;
    if (isExpand) {
        var data = $.parseJSON(data);
        try {
            var $gantt = $(".gantt[id=" + data.pair.id + "]");
            if (!!$gantt && $gantt.length > 0) {
                ganttRerender($gantt);
            }
        } catch (e) {
        }
    }
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