const TPL_NAME = 'aircraft-stand', moduleContext = 'aircraft', moduleName = '机位', inoutTypeCode = 'R';
var xAxis = enhanceXAxis4Mock(), asyncRenderCompleted = false, yAxis = $utils.ajaxLoadFragments(ctx + '/rms/aircraftStand/listJson');
var gateGanttOptions = {
    auto: true, xAxis: xAxis, yAxis: yAxis, timeline: true, draggableAxis: 'y', resizableHandler: false,
    ajaxUrl: ctx + '/rms/rs/distedGateListJson4Mock', collisionThresholds: 15, ajaxParams: { infoId: $("#infoId").val() },
    move2HoldAreaCallback: clearAircraftFn, asyncRenderCallback: asyncGanttElementRenderTaskFn, leftAxisClickHandler: function () {
        if (!datagrid.datagrid('getSelected') && !draggable_holder) {
            layer.msg('请选择一条记录再进行操作！');
            return false;
        }

        if ($(this).attr("_available") == '0') {
            layer.alert('当前机位「' + $(this).text() + '」不可用，无法分配！');
            return false;
        }
        dist_gate_manually($(this).text());
    },
    promptHandler: function (oData, event) { // 弹出提示层
        layer.tips(fetchReourceTipTpl(TPL_NAME, oData), $("div[_uid_=" + oData.uid + "]"), { tips: [1], tipsPosition: true, mouse: { left: event.pageX } })
    },
    draggableStartCallback: function (event, ui) {
        var index = datagrid.datagrid('getRowIndex', ui.helper.data('o').flightDynamicId);
        if (index !== -1) datagrid.datagrid('selectRecord', ui.helper.data('o').flightDynamicId);
        else datagrid.datagrid('clearSelections');
        draggable_holder = ui.helper.data('o');
        ui.helper.data('originPos', ui.helper.position());
        origin_drag_elem = ui.helper;
        origin_drag_target = ui.helper.data('o').gate;
    }
}, datagrid, dataGridMeta, draggable_holder, origin_drag_target, origin_drag_elem, toolbars = [];

$(function () {
    toolbars.push({
        text: '手动分配', iconCls: 'icon-bullet_wrench', handler: function () {
            var selectedRow = datagrid.datagrid('getSelected');
            if (selectedRow) {
                var placeNumberArray = !selectedRow.placeNum ? [] : selectedRow.placeNum.split(",");

                layer.open({
                    title: '手动分配', btn: ['确定', '取消'], area: ['500px', '150px'], type: 1, scrollbar: false, move: false,
                    content: fetchResourceDistTpl(TPL_NAME, { flightPlanPairId: selectedRow.flightDynamicId, list: yAxis, placeNumberArray: placeNumberArray }),
                    success: () => $("#selGate").select2({ allowClear: true, placeholder: '请选择机位', maximumSelectionLength: selectedRow.expectedGateNum }),
                    yes: function (index, layero) {
                        var selValue = $("#selGate").val();
                        if (!selValue) {
                            if (!placeNumberArray || placeNumberArray.length === 0) {
                                layer.close(index);
                                return false;
                            }

                            clearAircraftFn();
                            layer.close(index);
                        }

                        if (placeNumberArray.sort().join(",") != selValue.sort().join(",")) {
                            dist_gate_manually(selValue.sort().join(","));
                            layer.close(index);
                        } else {
                            layer.close(index);
                            return false;
                        }
                    }
                });
            } else layer.msg('请选择一条记录再进行操作！');
        }
    });


    //添加取消机位分配铵钮；
    toolbars.push({
        text: '取消分配', iconCls: 'icon-cross', handler: function () {
            var selectedRow = datagrid.datagrid('getSelected');
            if (selectedRow) clearAircraftFn(selectedRow);
            else layer.msg('请选择一条记录！');
        }
    });

    datagrid = $("#dataGrid4Preview").datagrid({
        rownumbers: true, url: ctx + '/rms/rs/findList4Mock', queryParams: { infoId: $("#infoId").val(), resourceType: $("#resourceType").val() },
        method: 'get', fitColumns: true, fit: true, idField: "id", singleSelect: true, showFooter: true, remoteSort: false, multiSort: true, toolbar: toolbars,
        onDblClickRow: scroll2CallbackFn
    })
    datagrid.datagrid('enableFilter')
    datagrid.datagrid('doCellTip', cellTooltipCommonProps)

    // 渲染Gantt图
    ganttRenderTaskCompleted = false;

    if ($(".gantt").html() == "") $(".gantt").width($(document).width()).gantt(gateGanttOptions);
    if ($(".airplane-wrapper > div").length === 0) $(".gantt").data('options').auto = true;
    $(".gantt-content-scroller-wrapper").width($(document).width() - 59);
    $(".gantt-content-scroller-wrapper").height($("#graphic-panel").height() - 90);
    ganttReload();
});


function dist_gate_manually (_aircraft_num) {
    var selectedRow = datagrid.datagrid('getSelected');
    if (!selectedRow) {
        if (draggable_holder) selectedRow = enhanceGetDGSelectedRow(draggable_holder.flightDynamicId)
        else return false;
    }

    var settings = $(".gantt").data("options"), old_place_num;
    if (!selectedRow || _aircraft_num == selectedRow.placeNum) return false;
    selectedRow.old_place_num = old_place_num = selectedRow.placeNum;
    var flightNum = $utils.superTrim(selectedRow.flightNum), flightNum2 = $utils.superTrim(selectedRow.flightNum2), finalFn;
    finalFn = $utils.superTrim(selectedRow.flightCompanyCode || selectedRow.flightCompanyCode2) + (!_.isEmpty(flightNum) && !_.isEmpty(flightNum2) ? flightNum + "/" + flightNum2 : flightNum + flightNum2);

    layer.confirm("您确定要将航班「" + finalFn + "」分配到「" + _aircraft_num + "」号机位吗？", function (index) {
        selectedRow.placeNum = _aircraft_num;
        checkingIndex = distributedWaitingDialog();

        $.post(ctx + '/rms/rs/aircraft_num_manual_dist_4_mock', { detailId: selectedRow.detailId, expectAircraftNum: _aircraft_num, force: false }, function (data) {
            layer.close(checkingIndex);
            if (data.success) {
                layer.msg(data.message);
                updateResourcePosition(selectedRow.id, data.result)
            } else {
                if (data.errorType == "1") { // 数据未同步，无需强制分配
                    layer.alert(data.message);
                    return;
                }

                layer.confirm(data.message + "<br/>是否要强制分配？", function (index) {
                    if (selectedRow.expectedGateNum != 1) { //多机位
                        //找到当前机位的index, 根据expectedGateNum进行遍历，拿到机位
                        var gateArray = _.pluck(settings.yAxis, "text"), currentIndex = _.indexOf(gateArray, _aircraft_num), aircraftStandArray = [_aircraft_num];
                        for (var i = 1, len = selectedRow.expectedGateNum; i < len; i++) aircraftStandArray.push(gateArray[++currentIndex]);
                        _aircraft_num = aircraftStandArray.join(",");
                    }

                    $.post(ctx + '/rms/rs/aircraft_num_manual_dist_4_mock', { detailId: selectedRow.detailId, expectAircraftNum: _aircraft_num, force: true }, function (data) {
                        if (data.code === 1) {
                            layer.msg(data.message);
                            updateResourcePosition(selectedRow.id, data.result);
                        } else {
                            layer.alert(data.message);
                            updateResourcePosition(selectedRow.id, selectedRow.old_place_num)
                        }
                    });
                }, () => {
                    layer.closeAll();
                    updateResourcePosition(selectedRow.id, old_place_num);
                });
            }
        });
    }, () => { if (origin_drag_elem) normalDragStopFn(origin_drag_elem) });
}

function scroll2CallbackFn () {
    var oData = datagrid.datagrid("getSelected");
    if (oData) {
        if (!oData.occupiedStart && !oData.occupiedEnd) return;

        $(".gantt-content-scroller-wrapper").gantt("scroll2Now", $(".gantt").data('options'), moment(oData.occupiedStart).toDate());

        try {
            var top = $(".airplane-wrapper > div").filter(function () {
                return $(this).data('o').flightDynamicId === oData.id;
            }).position().top;
        } catch (e) {
        }

        if (oData.placeNum && top) $(".gantt-content-scroller-wrapper").scrollTop(top);
        else $(".gantt-content-scroller-wrapper").scrollTop(0);

        $(".airplane-wrapper > div").filter(function () {
            return $(this).data('o').flightDynamicId === oData.id;
        }).each(function () {
            $(this).addClass("blink");
            var that = $(this);
            setTimeout(function () {
                that.removeClass('blink');
            }, 3600);
        });
    }
}

function ganttReload () {
    $(".gantt").gantt('reload', ctx + '/rms/rs/distedGateListJson4Mock', { infoId: $("#infoId").val() });
}

function clearAircraftFn () {
    var selectedRow = datagrid.datagrid('getSelected')
    if (!selectedRow) {
        if (draggable_holder) selectedRow = enhanceGetDGSelectedRow(draggable_holder.flightDynamicId)
        else {
            layer.msg('请选择您要操作的记录！')
            return false
        }
    }

    if (selectedRow) {
        if (selectedRow.placeNum) {
            layer.confirm(`是否要清除航班「${selectedRow.virtualTipField}」分配的「${selectedRow.placeNum}」号机位？`, { icon: 3, title: '提示' }, function (index) {
                $.post(ctx + '/rms/rs/clearAircraftNum4Mock', { detailId: selectedRow.detailId }, function (data) {
                    layer.msg(data.message);
                    if (data.code == 1) {
                        enhanceUpdateDGRow("id", selectedRow.id, { placeNum: '' });
                        var ganttOpts = $(".gantt").data('options');
                        $(".airplane-wrapper > div").filter(function () {
                            return $(this).data('o').flightDynamicId === selectedRow.id;
                        }).each(commonGanttElementRemoveFn);

                        ganttOpts.data = _(ganttOpts.data).reject((data) => data.flightDynamicId === selectedRow.id);
                        selectedRow.placeNum = "";
                        $.fn.gantt("append", generateGanttWrapper(selectedRow), ganttOpts);
                        layoutAutomaticallyAdapter();
                    }
                });
            }, () => normalDragStopFn(origin_drag_elem));
        }
    } else layer.msg("请选中一条记录再进行操作！");
}


function formatFlightNum (val, row, index) {
    if (row.flightNum != null && row.flightNum != '') return row.flightCompanyCode + row.flightNum;
    else return "";
}

function updateResourcePosition (resourceId, resourceNum) {
    enhanceUpdateDGRow("id", resourceId, { placeNum: resourceNum });
    if (!_.isEmpty(resourceNum)) {
        var $ganttEl = $(".airplane-wrapper > div").filter(function () {
            return $(this).data('o').flightDynamicId === resourceId;
        });

        var ganttData = $ganttEl.data('o'), ganttOpts = $(".gantt").data('options');
        ganttOpts.data = _(ganttOpts.data).reject((data) => data.id === ganttData.id);
        ganttData.gate = !_.isEmpty(resourceNum) ? resourceNum.split(",") : [];
        $ganttEl.each(commonGanttElementRemoveFn);
        $.fn.gantt("append", ganttData, ganttOpts);
    }
    immediateSortDate(datagrid, inoutTypeCode);
    layoutAutomaticallyAdapter();
    clearManuallyGanttRefs();
}

function formatFlightNum2 (val, row, index) {
    if (row.flightNum2 != null && row.flightNum2 != '') return row.flightCompanyCode2 + row.flightNum2;
    else return "";
}

function updateResourcePositionNew (resourceId, pair) {
    var templateData = _.findWhere(getAllRows(), { id: resourceId });

    $(".airplane-wrapper > div").filter(function () {
        return $(this).data('o').flightDynamicId === resourceId;
    }).each(commonGanttElementRemoveFn);

    var ganttData = generateGanttWrapper(pair, 1), ganttOpts = $(".gantt").data('options');
    ganttOpts.data = _(ganttOpts.data).reject((data) => data.id === ganttData.id);
    ganttData.gate = !_.isEmpty(pair.placeNum) ? pair.placeNum.split(",") : [];
    $.fn.gantt("append", ganttData, ganttOpts);
    immediateSortDate(datagrid, inoutTypeCode);
    layoutAutomaticallyAdapter();
}

function mockPlanDateFmt (v, r, i, f) {
    return moment(v).format('YYYY-MM-DD')
}