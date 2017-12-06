const TPL_NAME = 'carousel', moduleContext = "carousel", moduleName = '行李转盘', inoutTypeCode = 'J';
var xAxis = enhanceXAxis4Mock(), yAxis = $utils.ajaxLoadFragments(ctx + '/rms/rmsCarousel/listJson');

var gateGanttOptions = {
    auto: true, xAxis: xAxis, yAxis: yAxis, draggableAxis: 'y', collisionThresholds: getThresholdValue(), timeline: true, resizableHandler: false,
    ajaxUrl: ctx + '/rms/rs/disted_carousel_json_4_mock', ajaxParams: { infoId: $("#infoId").val() },
    asyncRenderCallback: asyncGanttElementRenderTaskFn, move2HoldAreaCallback: function (oData) {
        if (oData.flightDynamicId) { // 用户手动拖动到「预留区」
            if (!origin_drag_target || !origin_drag_target[0] || origin_drag_target[0].text === '预留区') return;
            // 根据origin_drag_target找到其原先的位置
            var originAxis = origin_drag_target[0], selectedRow = enhanceGetDGSelectedRow(oData.id),
                tipMessage = `您确定要清除「${selectedRow.flightCompanyCode + selectedRow.flightDynamicCode}」航班的「${originAxis.text}」号行李转盘吗？&nbsp;&nbsp;&nbsp;&nbsp;`;

            layer.confirm(tipMessage, function (index) {
                var checkingIndex = distributedWaitingDialog();

                var params = {}, oldCarousel = { inteCarouselCode: selectedRow.inteCarouselCode, intlCarouselCode: selectedRow.intlCarouselCode };
                if (selectedRow.inteCarouselCode === originAxis.text) selectedRow.inteCarouselCode = ''
                else selectedRow.intlCarouselCode = ''

                params.inte = selectedRow.inteCarouselCode;
                params.intl = selectedRow.intlCarouselCode;
                params.detailId = selectedRow.detailId;

                $.post(ctx + '/rms/rs/clearCarouselCode4Mock', params, function (data) {
                    layer.close(checkingIndex);
                    if (data.code == 1) {
                        layer.msg(data.message);
                        updateResourcePosition(selectedRow.id, { inteCarouselCode: params.inte, intlCarouselCode: params.intl });
                    } else {
                        layer.alert(data.message)
                        updateResourcePosition(selectedRow.id, oldCarousel);
                    }
                });
            }, () => normalDragStopFn(origin_drag_elem));
        } else clearCallbackFn();
    }, draggableStartCallback: normalModeGanttDraggableBeforeHandler,
    promptHandler: (oData, event) => commonGanttTips(TPL_NAME, multiModeGanttTipsArgsGenerator(oData), event),
    leftAxisClickHandler: function () {
        if (!datagrid.datagrid('getSelected') && !draggable_holder) {
            layer.msg('请选择一条记录再进行操作！');
            return;
        }

        if ($(this).attr("_available") === '0') {
            layer.msg('当前行李转盘「' + $(this).text() + '」不可用，无法分配！');
            return false;
        }

        dist_carousel_manually($(this).text());
    }
}, datagrid, draggable_holder, origin_drag_target, origin_drag_elem, dataGridMeta;

$(function () {

    datagrid = $("#dataGrid4Preview").datagrid({
        rownumbers: true, idField: "id", singleSelect: true, multiSort: false, url: ctx + "/rms/rs/carousel-list_4_mock",
        queryParams: { infoId: $("#infoId").val() }, method: 'get', fitColumns: true, fit: true, toolbar: [{
            text: '手动分配', iconCls: 'icon-bullet_wrench', handler: function () {
                var selectedRow = datagrid.datagrid('getSelected');
                if (selectedRow) {
                    if (multiModeGanttDistributedAvailableChecking(moduleName, '/rms/rs/getCarouselOccupyingTime?flightDynamicId=' + selectedRow.flightDynamicId, selectedRow)) {
                        if (selectedRow.flightDynamicNature !== '3') {
                            var availableInte = $utils.ajaxLoadFragments(ctx + '/rms/rmsCarousel/list_json_by_condition?nature=1'),
                                availableIntl = $utils.ajaxLoadFragments(ctx + '/rms/rmsCarousel/list_json_by_condition?nature=2'),
                                inte = !selectedRow.inteCarouselCode ? [] : _.without($utils.superTrim(selectedRow.inteCarouselCode).split(","), ""),
                                intl = !selectedRow.intlCarouselCode ? [] : _.without($utils.superTrim(selectedRow.intlCarouselCode).split(","), "");

                            availableInte = _.union(availableInte, inte);
                            availableIntl = _.union(availableIntl, intl);

                            layer.open({
                                title: '手动分配', btn: ['确定', '取消'], area: ['392px', '150px'], type: 1, scrollbar: false, move: false,
                                content: fetchResourceDistTpl(TPL_NAME, {
                                    inte, intl, availableInte, availableIntl,
                                    fightDynamicId: selectedRow.flightDynamicId, nature: selectedRow.flightDynamicNature
                                }),
                                success: function (layero, index) {
                                    var select2Opts = { multiple: true, closeOnSelect: false};
                                    $("#cbInteCarouselCode").select2($.extend({}, { placeholder: '请选择国内航段行李转盘' }, select2Opts));
                                    $("#cbIntlCarouselCode").select2($.extend({}, { placeholder: '请选择国际航段行李转盘' }, select2Opts));

                                    $("#cbInteCarouselCode").val(inte).trigger('change');
                                    $("#cbIntlCarouselCode").val(intl).trigger('change');
                                },
                                yes: function (index, layero) {
                                    var oldCarouselCode = { inte: selectedRow.inteCarouselCode, intl: selectedRow.intlCarouselCode };
                                    var cintec = $("#cbInteCarouselCode").val(), cintlc = $("#cbIntlCarouselCode").val();

                                    if (_.isEmpty(cintec) && _.isEmpty(cintlc)) {
                                        if (!selectedRow.inteCarouselCode && !selectedRow.intlCarouselCode) return;
                                        else clearCallbackFn();
                                        return;
                                    }

                                    if (cintec === oldCarouselCode.inte && cintlc === oldCarouselCode.intl) return;
                                    checkingIndex = distributedWaitingDialog();

                                    var pArgs = { flightDynamicId: selectedRow.detailId, nature: !_.isEmpty(cintec) ? "1" : "2", inte: cintec, intl: cintlc, force: false };
                                    var gArgs = {
                                        selectedRowId: selectedRow.id, nInte: cintec, nIntl: cintlc,
                                        oInte: oldCarouselCode.inte, oIntl: oldCarouselCode.intl, nature: !_.isEmpty(cintec) ? "1" : "2", force: true
                                    };
                                    commonManuallyDistributionFn(ctx + '/rms/rs/carousel_manual_dist_4_mock', ctx + '/rms/rs/carousel_manual_dist_4_mock', pArgs, gArgs, checkingIndex)
                                    return true;
                                }
                            });
                        } else dist_carousel_manually();
                    }
                } else layer.msg('请选择一条记录再进行操作!');
            }
        }, {
            text: '取消分配', iconCls: 'icon-cross', handler: clearCallbackFn
        }], onDblClickRow: scroll2CallbackFn, onLoadSuccess: layoutAutomaticallyAdapter
    });

    datagrid.datagrid('enableFilter');
    datagrid.datagrid('doCellTip', cellTooltipCommonProps);

    // 渲染Gantt图
    ganttRenderTaskCompleted = false;

    if ($(".gantt").html() == "") $(".gantt").width($(document).width()).gantt(gateGanttOptions);
    if ($(".airplane-wrapper > div").length === 0) $(".gantt").data('options').auto = true;
    ganttReload();
});

//移入hold区回调函数
function clearCallbackFn () {
    var selectedRow = datagrid.datagrid('getSelected');
    if (selectedRow) {
        if (selectedRow.inteCarouselCode || selectedRow.intlCarouselCode) {
            layer.confirm(`是否要清除当前航班「${selectedRow.flightCompanyCode + selectedRow.flightDynamicCode}」分配的行李转盘？`, { icon: 3, title: '提示' }, function (index) {
                $.post(ctx + '/rms/rs/clearCarouselCode4Mock', { detailId: selectedRow.detailId, inte: '', intl: '' }, function (data) {
                    if (data.code == 1) {
                        layer.msg(data.message);
                        updateResourcePosition(selectedRow.id, { inteCarouselCode: "", intlCarouselCode: "" });
                    } else layer.alert(data.message);
                });
            });
        }
    } else layer.msg("请选中一条记录再进行操作！");
}

function scroll2CallbackFn () {
    var oData = datagrid.datagrid("getSelected");
    if (oData) {
        if ((_.isEmpty(oData.expectedOverTime) && _.isEmpty(oData.expectedStartTime))) return;

        $(".gantt-content-scroller-wrapper").gantt("scroll2Now", $(".gantt").data('options'), moment(oData.expectedStartTime).toDate());
        try {
            var top = $(".airplane-wrapper > div").filter(function () {
                return $(this).data('o').flightDynamicId === oData.flightDynamicId;
            }).position().top;
        } catch (e) {
        }

        var carouselCode = oData.flightDynamicNature === "1" ? oData.inteCarouselCode : oData.flightDynamicNature === "2" ? oData.intlCarouselCode :
            _.isEmpty(oData.inteCarouselCode) ? oData.intlCarouselCode : oData.inteCarouselCode;
        if (carouselCode) $(".gantt-content-scroller-wrapper").scrollTop(top);
        else $(".gantt-content-scroller-wrapper").scrollTop(0);

        $(".airplane-wrapper > div").filter(function () {
            return $(this).data('o').id === oData.id;
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
    $(".gantt").gantt('reload', ctx + '/rms/rs/disted_carousel_json_4_mock', { infoId: $("#infoId").val() })
}

function dist_carousel_manually (_aircraft_num) {
    var selectedRow = datagrid.datagrid('getSelected');
    if (!selectedRow) {
        if (draggable_holder) selectedRow = enhanceGetDGSelectedRow(draggable_holder.id)
        else return false;
    }

    var settings = $(".gantt").data("options"), oldCarouselCode, checkingIndex;
    if (!selectedRow) return false;

    if (!multiModeGanttDistributedAvailableChecking(moduleName, '/rms/rs/getCarouselOccupyingTime?flightDynamicId=' + selectedRow.flightDynamicId, selectedRow)) return;

    if (!_aircraft_num) {

        var availableInte = $utils.ajaxLoadFragments(ctx + '/rms/rmsCarousel/list_json_by_condition?nature=1'),
            availableIntl = $utils.ajaxLoadFragments(ctx + '/rms/rmsCarousel/list_json_by_condition?nature=2'),
            inte = !selectedRow.inteCarouselCode ? [] : _.without($utils.superTrim(selectedRow.inteCarouselCode).split(","), ""),
            intl = !selectedRow.intlCarouselCode ? [] : _.without($utils.superTrim(selectedRow.intlCarouselCode).split(","), "");

        availableInte = _.union(availableInte, inte);
        availableIntl = _.union(availableIntl, intl);

        var confirmLayerIndex = layer.open({
            title: '行李转盘手动分配', btn: ['确定', '取消'], area: ['420px', selectedRow.flightDynamicNature === '3' ? '230px' : '180px'],
            type: 1, scrollbar: false, move: false, content: fetchResourceDistTpl(TPL_NAME, {
                flightDynamicId: selectedRow.flightDynamicId, id: selectedRow.id, nature: selectedRow.flightDynamicNature,
                inte, intl, availableInte, availableIntl
            }),
            success: function (layero, index) {
                var select2Opts = { allowClear: true };
                $("#cbInteCarouselCode").select2($.extend({}, { placeholder: '请选择国内航段行李转盘' }, select2Opts));
                $("#cbIntlCarouselCode").select2($.extend({}, { placeholder: '请选择国际航段行李转盘' }, select2Opts));

                $("#cbInteCarouselCode").val(inte).trigger('change');
                $("#cbIntlCarouselCode").val(intl).trigger('change');
            },
            yes: function (index, layero) {
                oldCarouselCode = { inte: selectedRow.inteCarouselCode, intl: selectedRow.intlCarouselCode };
                var cintec = $("#cbInteCarouselCode").val(), cintlc = $("#cbIntlCarouselCode").val();
                if (cintec === oldCarouselCode.inte && cintlc === oldCarouselCode.intl) return false;
                checkingIndex = distributedWaitingDialog();

                var pArgs = { flightDynamicId: selectedRow.detailId, inte: cintec, intl: cintlc, nature: selectedRow.flightDynamicNature, force: false };
                var gArgs = { selectedRowId: selectedRow.id, nInte: cintec, nIntl: cintlc, oInte: oldCarouselCode.inte, oIntl: oldCarouselCode.intl, nature: selectedRow.flightDynamicNature, force: true };
                commonManuallyDistributionFn(ctx + '/rms/rs/carousel_manual_dist_4_mock', ctx + '/rms/rs/carousel_manual_dist_4_mock', pArgs, gArgs, checkingIndex, confirmLayerIndex)
                return true;
            }
        });
    } else {
        var intes = $utils.superTrim(selectedRow.inteCarouselCode),  //国内航段
            intls = $utils.superTrim(selectedRow.intlCarouselCode),  //国际航段
            ganttOpts = $(".gantt").data('options'), yAxis = ganttOpts.yAxis, flightNum = selectedRow.flightCompanyCode + selectedRow.flightDynamicCode;

        if (draggable_holder) {  // 用户拖动Gantt上的元素
            var originAxis = origin_drag_target[0], newAxis = draggable_holder.gate[0],
                newAxisExists = newAxis.nature === "1" ? intes === newAxis.text : intls === newAxis.text, params = {};
            if (originAxis.text === newAxis.text) {
                clearManuallyGanttRefs();
                return;
            }

            if (!newAxisExists) {  // 移动到inte及intl都不存在的数值上

                let layerPromptMsgTpl = `您确定将「${newAxis.text}」号行李转盘分配给「${flightNum}」航班吗?`;
                if (selectedRow.flightDynamicNature != '3' && selectedRow.flightDynamicNature !== newAxis.nature)
                    layerPromptMsgTpl = `当前航班「${flightNum}」属性为${flightNatures[selectedRow.flightDynamicNature]}，${newAxis.text}为${newAxis.nature === '2' ? '国际航段' : '国内航段'}资源，是否强制分配？`;

                layer.confirm(layerPromptMsgTpl, function (index) {
                    params.flightDynamicId = selectedRow.detailId;
                    checkingIndex = distributedWaitingDialog();

                    // --- 保持用户拖动axis的性质来进行分配 -- changed by xiaowu 2017.1.24
                    params.inte = $utils.superTrim(selectedRow.inteCarouselCode);
                    params.intl = $utils.superTrim(selectedRow.intlCarouselCode);
                    params.type = selectedRow.inteCarouselCode === originAxis.text ? "1" : "2"

                    if (_.contains(inte_array, originAxis.text)) {
                        params.inte = [..._.without(inte_array, originAxis.text), ...[newAxis.text]].join(",")
                    } else if (_.contains(intl_array, originAxis.text)) {
                        params.intl = [..._.without(intl_array, originAxis.text), ...[newAxis.text]].join(",")
                    } else {
                        if (selectedRow.flightDynamicNature === '1' || (selectedRow.flightDynamicNature === '3' && newAxis.nature === '1')) {
                            params.inte = newAxis.text;
                            params.intl = $utils.superTrim(selectedRow.intlCarouselCode);
                        } else if (selectedRow.flightDynamicNature === '2' || (selectedRow.flightDynamicNature === '3' && newAxis.nature === '2')) {
                            params.inte = $utils.superTrim(selectedRow.inteCarouselCode);
                            params.intl = newAxis.text;
                        }
                    }

                    params.nature = selectedRow.flightDynamicNature;
                    params.force = false;

                    var gArgs = {
                        selectedRowId: selectedRow.id, nInte: params.inte, nIntl: params.intl,
                        oInte: selectedRow.inteCarouselCode, oIntl: selectedRow.intlCarouselCode, nature: selectedRow.flightDynamicNature, force: true
                    };
                    commonManuallyDistributionFn(ctx + '/rms/rs/carousel_manual_dist_4_mock', ctx + '/rms/rs/carousel_manual_dist_4_mock', params, gArgs, checkingIndex);
                }, () => normalDragStopFn(origin_drag_elem));
            } else normalDragStopFn(origin_drag_elem);
        } else {
            let clickedAxis = yAxis.filter(filterAxis => filterAxis.text === _aircraft_num)[0];
            let nature = selectedRow.flightDynamicNature === '3' ? clickedAxis.nature : selectedRow.flightDynamicNature;
            let tipMessage;
            if (intes === _aircraft_num || intls === _aircraft_num) { // 清除操作
                tipMessage = `您确定要清除「${flightNum}」航班的「${_aircraft_num}」号行李转盘吗？&nbsp;&nbsp;&nbsp;&nbsp;`;

                layer.confirm(tipMessage, function (index) {
                    checkingIndex = distributedWaitingDialog();

                    var params = {}, oldCarousel = { inteCarouselCode: selectedRow.inteCarouselCode, intlCarouselCode: selectedRow.intlCarouselCode }
                    var inteArray = _.without($utils.superTrim(selectedRow.inteCarouselCode).split(","), ""),
                        intlArray = _.without($utils.superTrim(selectedRow.intlCarouselCode).split(","), "");
                    if (_.contains(inteArray, _aircraft_num)) inteArray = _.without(inteArray, _aircraft_num);
                    else if (_.contains(intlArray, _aircraft_num)) intlArray = _.without(intlArray, _aircraft_num);

                    params.inte = inteArray.join(",");
                    params.intl = intlArray.join(",");
                    params.detailId = selectedRow.detailId;

                    $.post(ctx + '/rms/rs/clearCarouselCode4Mock', params, function (data) {
                        layer.close(checkingIndex);
                        if (data.code == 1) {
                            layer.msg(data.message);
                            updateResourcePosition(selectedRow.id, { inteCarouselCode: params.inte, intlCarouselCode: params.intl });
                        } else layer.alert(data.message)
                    });
                }, clearManuallyGanttRefs);
            } else { // 追加操作
                tipMessage = `您确定要将编号为「${_aircraft_num}」行李转盘分配给航班「${flightNum}」吗?`;
                if (selectedRow.flightDynamicNature != '3' && selectedRow.flightDynamicNature !== clickedAxis.nature)
                    tipMessage = `当前航班「${flightNum}」属性为${flightNatures[selectedRow.flightDynamicNature]}，${clickedAxis.text}为${flightNatures[clickedAxis.nature]}资源，是否强制分配？`;
                layer.confirm(tipMessage, function (index) {
                    var oldCarouselCode = { inte: intes, intl: intls }, expectCarouselCode = _aircraft_num,
                        cintec = nature === "1" ? _aircraft_num : intes, cintlc = nature === "2" ? _aircraft_num : intls;
                    checkingIndex = distributedWaitingDialog();

                    var pArgs = { flightDynamicId: selectedRow.detailId, inte: cintec, intl: cintlc, nature: nature, force: false };
                    var gArgs = { selectedRowId: selectedRow.id, nInte: cintec, nIntl: cintlc, oInte: oldCarouselCode.inte, oIntl: oldCarouselCode.intl, nature: nature, force: true };
                    commonManuallyDistributionFn(ctx + '/rms/rs/carousel_manual_dist_4_mock', ctx + '/rms/rs/carousel_manual_dist_4_mock', pArgs, gArgs, checkingIndex)
                }, clearManuallyGanttRefs);
            }
        }
    }
}

function updateResourcePosition (resourceId, definition) {
    enhanceUpdateDGRow("id", resourceId, definition);
    var templateData = _.findWhere(getAllRows(), { id: resourceId }),
        resourceNum = $utils.superTrim(templateData.inteCarouselCode) + "," + $utils.superTrim(templateData.intlCarouselCode);

    var ganttOpts = $(".gantt").data('options'), yAxis = ganttOpts.yAxis;
    $(".airplane-wrapper > div").filter(function () {
        return $(this).data('o').flightDynamicId === templateData.flightDynamicId;
    }).each(commonGanttElementRemoveFn).remove();

    ganttOpts.data = ganttOpts.data.filter(filterData => filterData.flightDynamicId !== templateData.flightDynamicId);
    var generateGanttData = newTempalteWrapper(templateData, templateData);
    if (resourceNum === ",") {
        //清除资源
        $.fn.gantt('append', $.extend(generateGanttData, { extra: { codeNature: templateData.flightDynamicNature }, gate: [], inteCode: [], intlCode: [] }), ganttOpts);
    } else {
        // 根据resourceNum来重新绘制Gantt Element
        for (var resourceCode of resourceNum.split(",")) {
            if (_.isEmpty(resourceCode)) continue;
            generateGanttData.extra = { codeNature: yAxis.filter(axis => axis.text === resourceCode)[0].nature };
            generateGanttData.gate = [resourceCode];
            $.fn.gantt('append', generateGanttData, ganttOpts);
        }
    }

    clearManuallyGanttRefs();
    immediateSortDate(datagrid, inoutTypeCode);
    layoutAutomaticallyAdapter();
}


function commonManuallyDistributionFn (url, forceDistUrl, pArgs, gArgs, checkingIndex, confirmIndex) {
    $.post(url, pArgs, function (data) {
        layer.close(checkingIndex);
        if (data.code == 1) {
            layer.closeAll();
            layer.msg(data.message);
            updateResourcePosition(gArgs.selectedRowId, { inteCarouselCode: gArgs.nInte, intlCarouselCode: gArgs.nIntl });
            clearManuallyGanttRefs();
            if (confirmIndex) layer.close(confirmIndex);
        } else {
            if (data.code == -2) {
                layer.alert(data.message)
                return;
            }

            layer.confirm(data.message + "<br/>是否要强制分配？", function (index) {
                $.post(forceDistUrl, { flightDynamicId: pArgs.flightDynamicId, inte: gArgs.nInte, intl: gArgs.nIntl, nature: gArgs.nature, force: gArgs.force }, function (data) {
                    if (data.code == 1) {
                        layer.closeAll();
                        updateResourcePosition(gArgs.selectedRowId, { inteCarouselCode: gArgs.nInte, intlCarouselCode: gArgs.nIntl });
                        clearManuallyGanttRefs();
                    } else {
                        layer.close(index);
                        layer.alert('未知错误！' + data.message);
                        return;
                    }
                });
            }, function (index) {
                layer.close(index)
                updateResourcePosition(gArgs.selectedRowId, { inteCarouselCode: gArgs.oInte, intlCarouselCode: gArgs.oIntl })
                clearManuallyGanttRefs()
            });
        }
    });
}