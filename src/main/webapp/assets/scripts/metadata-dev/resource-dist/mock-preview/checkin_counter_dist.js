const TPL_NAME = 'checkin-counter', moduleContext = 'checkincounter', moduleName = '值机柜台', inoutTypeCode = 'C';
var xAxis = enhanceXAxis4Mock(), yAxis = $utils.ajaxLoadFragments(ctx + '/rms/rmsCheckinCounter/listJson');

var gateGanttOptions = {
    auto: true, xAxis: xAxis, yAxis: yAxis, multi: true, draggableAxis: 'y', collisionThresholds: 0, timeline: true,
    ajaxUrl: ctx + '/rms/rs/disted_checkin_counter_Json_4_mock', ajaxParams: { infoId: $("#infoId").val() },
    collisionDetectionHandler: multiModeCollisionHandler, draggableHandler: multiModeGanttDraggableHandler, resizableHandler: false,
    asyncRenderCallback: asyncGanttElementRenderTaskFn, move2HoldAreaCallback: function (oData) {
        if (oData.flightDynamicId) { // 用户手动拖动到「预留区」
            if (!origin_drag_target || !origin_drag_target[0] || origin_drag_target[0].text === '预留区') return;
            // 根据origin_drag_target找到其原先的位置
            var originAxis = origin_drag_target[0], selectedRow = enhanceGetDGSelectedRow(oData.id),
                intes = _.without($utils.superTrim(selectedRow.inteCheckingCounterCode).split(","), ""),
                intls = _.without($utils.superTrim(selectedRow.intlCheckingCounterCode).split(","), ""), tipMessage;

            tipMessage = `您确定要清除「${selectedRow.flightCompanyCode + selectedRow.flightDynamicCode}」航班的「${originAxis.text}」号值机柜台吗?`;

            layer.confirm(tipMessage, function () {
                var checkingIndex = distributedWaitingDialog();

                if (intes.includes(originAxis.text)) {
                    selectedRow.type = 'inte'
                    selectedRow.opCode = filterValues(intes, originAxis.text)
                } else {
                    selectedRow.type = 'intl'
                    selectedRow.opCode = filterValues(intls, originAxis.text)
                }

                if (selectedRow.opCode.indexOf(",") === 0) selectedRow.opCode = selectedRow.opCode.slice(1);

                selectedRow.inte = selectedRow.inteCheckingCounterCode;
                selectedRow.intl = selectedRow.intlCheckingCounterCode;

                if (selectedRow.type == "inte") selectedRow.inte = selectedRow.inteCheckingCounterCode = selectedRow.opCode
                else selectedRow.intl = selectedRow.intlCheckingCounterCode = selectedRow.opCode

                $.post(ctx + '/rms/rs/common_multi_manual_dist_4_mock', selectedRow, function (data) {
                    layer.close(checkingIndex);
                    if (data.code == 1) {
                        layer.msg(data.message);
                        updateResourcePosition(selectedRow.id, selectedRow.type == "inte" ? { inteCheckingCounterCode: selectedRow.opCode } : { intlCheckingCounterCode: selectedRow.opCode });
                    } else layer.alert(data.message);
                });
            }, () => multiModeDragStopFn(origin_drag_elem));
        } else clearCallbackFn();
    },
    draggableStartCallback: multiModeGanttDraggableBeforeHandler,
    promptHandler: (oData, event) => commonGanttTips(TPL_NAME, multiModeGanttTipsArgsGenerator(oData), event),
    leftAxisClickHandler: function () {
        if (!datagrid.datagrid('getSelected') && !draggable_holder) {
            layer.msg('请选择一条记录再进行操作！');
            return;
        }

        if ($(this).attr("_available") === '0') {
            layer.msg('当前值机柜台「' + $(this).text() + '」不可用，无法分配！');
            return false;
        }

        dist_checkin_counter_manually($(this).text());
    }
}, datagrid, draggable_holder, origin_drag_target, origin_drag_elem, dataGridMeta;

$(function () {
    datagrid = $("#dataGrid4Preview").datagrid({
        rownumbers: true, idField: "id", singleSelect: true, multiSort: false, url: ctx + "/rms/rs/checkin-counter-list_4-mock",
        queryParams: { infoId: $("#infoId").val() }, method: 'get', fitColumns: true, fit: true, toolbar: [{
            text: '手动分配', iconCls: 'icon-bullet_wrench', handler: function () {
                var selectedRow = datagrid.datagrid('getSelected');
                if (selectedRow) {
                    if (multiModeGanttDistributedAvailableChecking(moduleName, '/rms/rs/getCheckinCounterOccupyingTime?flightDynamicId=' + selectedRow.flightDynamicId, selectedRow)) {
                        if (selectedRow.flightDynamicNature !== '3') {

                            var availableInte = $utils.ajaxLoadFragments(ctx + '/rms/rmsCheckinCounter/list_json_by_condition?nature=1'),
                                availableIntl = $utils.ajaxLoadFragments(ctx + '/rms/rmsCheckinCounter/list_json_by_condition?nature=2'),
                                inte = !selectedRow.inteCheckingCounterCode ? [] : _.without(selectedRow.inteCheckingCounterCode.split(","), ""),
                                intl = !selectedRow.intlCheckingCounterCode ? [] : _.without(selectedRow.intlCheckingCounterCode.split(","), "");

                            availableInte = _.union(availableInte, inte);
                            availableIntl = _.union(availableIntl, intl);

                            layer.open({
                                title: '手动分配', btn: ['确定', '取消'], area: ['480px', '180px'], type: 1,
                                scrollbar: false, move: false, content: fetchResourceDistTpl(TPL_NAME, {
                                    inte, intl, availableInte, availableIntl,
                                    fightDynamicId: selectedRow.flightDynamicId, nature: selectedRow.flightDynamicNature
                                }),
                                success: function (layero, index) {
                                    var select2Opts = { allowClear: true, closeOnSelect: false };
                                    $("#cbInteCarouselCode").select2($.extend({}, { placeholder: '请选择国内航段值机柜台' }, select2Opts));
                                    $("#cbIntlCarouselCode").select2($.extend({}, { placeholder: '请选择国际航段值机柜台' }, select2Opts));

                                    $("#cbInteCarouselCode").val(inte).trigger('change');
                                    $("#cbIntlCarouselCode").val(intl).trigger('change');
                                }, yes: function (index, layero) {
                                    var intes = $("#cbInteCarouselCode").val(), intls = $("#cbIntlCarouselCode").val();
                                    if (!intes && !intls) { //如果两者皆为空
                                        if (!selectedRow.inteCheckingCounterCode && !selectedRow.intlCheckingCounterCode) return;
                                        else clearResource(selectedRow);
                                    } else {
                                        var checkingIndex = distributedWaitingDialog();
                                        selectedRow.type = _.isEmpty(intls) ? "inte" : "intl";
                                        selectedRow.opCode = _.isEmpty(intls) ? intes.join(",") : intls.join(",");

                                        if (selectedRow.opCode.indexOf(",") === 0) selectedRow.opCode = selectedRow.opCode.slice(1);

                                        selectedRow.inte = selectedRow.inteCheckingCounterCode;
                                        selectedRow.intl = selectedRow.intlCheckingCounterCode;

                                        if (selectedRow.type == "inte") selectedRow.inte = selectedRow.inteCheckingCounterCode = selectedRow.opCode
                                        else selectedRow.intl = selectedRow.intlCheckingCounterCode = selectedRow.opCode

                                        $.post(ctx + '/rms/rs/common_multi_manual_dist_4_mock', selectedRow, function (data) {
                                            layer.close(checkingIndex);
                                            if (data.code == 1) {
                                                layer.msg(data.message);
                                                updateResourcePosition(selectedRow.id, _.isEmpty(intls) ? { inteCheckingCounterCode: selectedRow.opCode } : { intlCheckingCounterCode: selectedRow.opCode });
                                            } else layer.alert(data.message);
                                        });
                                    }
                                    layer.close(index);
                                }
                            });
                        } else dist_checkin_counter_manually();
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

function clearCallbackFn () {
    var selectedRow = datagrid.datagrid('getSelected');
    if (selectedRow) clearResource(selectedRow);
    else layer.msg("请选中一条记录再进行操作！");
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

        var carouselCode = oData.flightDynamicNature === "1" ? oData.inteCheckingCounterCode : oData.flightDynamicNature === "2" ? oData.intlCheckingCounterCode :
            _.isEmpty(oData.inteCheckingCounterCode) ? oData.intlCheckingCounterCode : oData.inteCheckingCounterCode;

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
    $(".gantt").gantt('reload', ctx + '/rms/rs/disted_checkin_counter_Json_4_mock', { infoId: $("#infoId").val() });
}

function dist_checkin_counter_manually (_aircraft_num) {
    var selectedRow = datagrid.datagrid('getSelected');
    if (!selectedRow) {
        if (draggable_holder) selectedRow = enhanceGetDGSelectedRow(draggable_holder.id)
        else return false;
    }

    var settings = $(".gantt").data("options"), oldCarouselCode, checkingIndex;
    if (!selectedRow) return false;
    if (!multiModeGanttDistributedAvailableChecking(moduleName, '/rms/rs/getCheckinCounterOccupyingTime?flightDynamicId=' + selectedRow.flightDynamicId, selectedRow)) return;

    if (!_aircraft_num) {  // 混合航班，弹出界面分配

        var availableInte = $utils.ajaxLoadFragments(ctx + '/rms/rmsCheckinCounter/list_json_by_condition?nature=1'),
            availableIntl = $utils.ajaxLoadFragments(ctx + '/rms/rmsCheckinCounter/list_json_by_condition?nature=2'),
            inte = !selectedRow.inteCheckingCounterCode ? [] : _.without($utils.superTrim(selectedRow.inteCheckingCounterCode).split(","), ""),
            intl = !selectedRow.intlCheckingCounterCode ? [] : _.without($utils.superTrim(selectedRow.intlCheckingCounterCode).split(","), "");

        availableInte = _.union(availableInte, inte);
        availableIntl = _.union(availableIntl, intl);

        var confirmLayerIndex = layer.open({
            title: '值机柜台手动分配', btn: ['确定', '取消'],
            area: ['480px', selectedRow.flightDynamicNature === '3' ? '230px' : '180px'],
            type: 1, scrollbar: false, move: false, content: fetchResourceDistTpl(TPL_NAME, {
                flightDynamicId: selectedRow.flightDynamicId, id: selectedRow.id, nature: selectedRow.flightDynamicNature,
                inte, intl, availableInte, availableIntl
            }),
            success: function (layero, index) {
                var select2Opts = { allowClear: true };
                $("#cbInteCarouselCode").select2($.extend({}, { placeholder: '请选择国内航段值机柜台' }, select2Opts));
                $("#cbIntlCarouselCode").select2($.extend({}, { placeholder: '请选择国际航段值机柜台' }, select2Opts));

                $("#cbInteCarouselCode").val(inte).trigger('change');
                $("#cbIntlCarouselCode").val(intl).trigger('change');
            },
            yes: function (index, layero) {
                oldCarouselCode = { inte: selectedRow.inteCheckingCounterCode, intl: selectedRow.intlCheckingCounterCode };
                var cintec = $("#cbInteCarouselCode").val(), cintlc = $("#cbIntlCarouselCode").val();
                if (cintec === oldCarouselCode.inte && cintlc === oldCarouselCode.intl) return false;

                selectedRow.actualCheckingCounterNum = (cintec ? cintec.length : 0) + (cintlc ? cintlc.length : 0);
                selectedRow.inte = selectedRow.inteCheckingCounterCode = cintec ? cintec.join(",") : "";
                selectedRow.intl = selectedRow.intlCheckingCounterCode = cintlc ? cintlc.join(",") : "";

                selectedRow.expectedStartTime = _formatDateTime(selectedRow.expectedStartTime);
                selectedRow.expectedOverTime = _formatDateTime(selectedRow.expectedOverTime);

                checkingIndex = distributedWaitingDialog();

                $.post(ctx + '/rms/rs/common_multi_manual_dist_4_mock', selectedRow, function (data) {
                    layer.close(checkingIndex);
                    layer.close(confirmLayerIndex);
                    if (data.code == 1) {
                        layer.msg(data.message);
                        updateResourcePosition(selectedRow.id, { inteCheckingCounterCode: selectedRow.inteCheckingCounterCode, intlCheckingCounterCode: selectedRow.intlCheckingCounterCode });
                    } else layer.alert(data.message);
                });
                return true;
            }
        });
    } else {

        // 如果draggable_holder没有值, 表示用户是直接选中表格中的内容, 再点击左侧的资源编号面板进行分配
        // 得到当前航班的「国内航段」及「国际航段」的资源, 根据当前点击的资源判断其类型, 得到编号, 如果编号存在, 则将当前操作视为「清除当前资源」。反之视为「新增当前资源」
        // draggable没有值的情况,用户选中的航班点击「预留区」则清除所有分配的资源


        // 如果draggable_holder有值, 表示用户是直接拖动Gantt Element。 直接拖动的处理方式为: 将拖动前的值替换为拖动后的值,
        // 假设当前 「国内航段 = 1, 3, 5」, 「国际航段 = 2, 4」, 用户将编号为5的资源拖动到第「6 - 国际航段」, 此时将5清除, 国际航段变为 「2,4,6」

        // 拖动到「预留区」,清除当前拖动的资源编号
        // 拖动到已有的资源上暂时不作处理

        // 得到当前航班的inte及intl值
        var intes = $utils.superTrim(selectedRow.inteCheckingCounterCode).split(","),  //国内航段
            intls = $utils.superTrim(selectedRow.intlCheckingCounterCode).split(","),  //国际航段
            ganttOpts = $(".gantt").data('options'), yAxis = ganttOpts.yAxis, flightNum = selectedRow.flightCompanyCode + selectedRow.flightDynamicCode;


        if (draggable_holder) {  // 用户拖动Gantt上的元素
            var originAxis = origin_drag_target[0], newAxis = draggable_holder.gate[0], newAxisExists = false;

            if (selectedRow.flightDynamicNature !== "3") newAxisExists = selectedRow.flightDynamicNature === "1" ? intes.includes(newAxis.text) : intls.includes(newAxis.text);
            else {
                if (intes.includes(originAxis.text)) newAxisExists = intls.includes(newAxis.text)
                else newAxisExists = intes.includes(newAxis.text);
            }

            if (originAxis.text === newAxis.text) {
                clearManuallyGanttRefs();
                return;
            }

            if (!newAxisExists) {  // 移动到inte及intl都不存在的数值上
                let layerPromptMsgTpl = `您确定将「${newAxis.text}」号值机柜台分配给「${flightNum}」航班吗?`;
                if (selectedRow.flightDynamicNature != '3' && selectedRow.flightDynamicNature !== newAxis.nature)
                    layerPromptMsgTpl = `当前航班「${flightNum}」属性为${flightNatures[selectedRow.flightDynamicNature]}，${newAxis.text}为${flightNatures[newAxis.nature]}资源，是否强制分配？`;

                layer.confirm(layerPromptMsgTpl, function (index) {
                    checkingIndex = distributedWaitingDialog();

                    // --- 保持用户拖动axis的性质来进行分配 -- changed by xiaowu 2017.1.24
                    selectedRow.inteCheckingCounterCode = $utils.superTrim(selectedRow.inteCheckingCounterCode);
                    selectedRow.intlCheckingCounterCode = $utils.superTrim(selectedRow.intlCheckingCounterCode);
                    var inte_array = selectedRow.inteCheckingCounterCode.split(","), intl_array = selectedRow.intlCheckingCounterCode.split(",");

                    if (_.contains(inte_array, originAxis.text)) {
                        selectedRow.inteCheckingCounterCode = [..._.without(inte_array, originAxis.text), ...[newAxis.text]].join(",")
                    } else if (_.contains(intl_array, originAxis.text)) {
                        selectedRow.intlCheckingCounterCode = [..._.without(intl_array, originAxis.text), ...[newAxis.text]].join(",")
                    } else {
                        if (selectedRow.flightDynamicNature === '1' || (selectedRow.flightDynamicNature === '3' && newAxis.nature === '1')) {
                            selectedRow.inteCheckingCounterCode = $utils.superTrim(filterValues(intes, originAxis.text)) + "," + newAxis.text;
                            selectedRow.intlCheckingCounterCode = $utils.superTrim(selectedRow.intlCheckingCounterCode);
                        } else if (selectedRow.flightDynamicNature === '2' || (selectedRow.flightDynamicNature === '3' && newAxis.nature === '2')) {
                            selectedRow.inteCheckingCounterCode = $utils.superTrim(selectedRow.inteCheckingCounterCode);
                            selectedRow.intlCheckingCounterCode = $utils.superTrim(filterValues(intls, originAxis.text)) + "," + newAxis.text;
                        }
                    }

                    //清除首尾多余的逗号
                    selectedRow.inte = selectedRow.inteCheckingCounterCode = emptyFirstAndLastCommas(selectedRow.inteCheckingCounterCode);
                    selectedRow.intl = selectedRow.intlCheckingCounterCode = emptyFirstAndLastCommas(selectedRow.intlCheckingCounterCode);

                    $.post(ctx + '/rms/rs/common_multi_manual_dist_4_mock', selectedRow, function (data) {
                        layer.close(checkingIndex);
                        if (data.code == 1) {
                            layer.msg(data.message);
                            updateResourcePosition(selectedRow.id, { inteCheckingCounter: selectedRow.inteCheckingCounterCode, intlCheckingCounterCode: selectedRow.intlCheckingCounterCode });
                        } else layer.alert(data.message);
                    })
                }, () => multiModeDragStopFn(origin_drag_elem));
            } else multiModeDragStopFn(origin_drag_elem);
        } else { // 用户点击左侧的资源面板
            let clickedAxis = yAxis.filter(filterAxis => filterAxis.text === _aircraft_num)[0];
            let nature = selectedRow.flightDynamicNature === '3' ? clickedAxis.nature : selectedRow.flightDynamicNature;
            let values, tipMessage;
            if (intes.includes(_aircraft_num) || intls.includes(_aircraft_num)) { // 清除操作
                values = nature === "1" ? filterValues(intes, _aircraft_num) : filterValues(intls, _aircraft_num);
                tipMessage = `您确定要清除「${flightNum}」航班的「${_aircraft_num}」号值机柜台吗?`;
            } else { // 追加操作
                if (nature === "1") {
                    intes.push(_aircraft_num);
                    values = intes;
                } else {
                    intls.push(_aircraft_num);
                    values = intls;
                }

                tipMessage = `您确定要将编号为「${_aircraft_num}」值机柜台分配给航班「${flightNum}」吗?`;
                if (selectedRow.flightDynamicNature !== 3 && (selectedRow.flightDynamicNature !== clickedAxis.nature))
                    tipMessage = `当前航班「${flightNum}」属性为${flightNatures[selectedRow.flightDynamicNature]}，${clickedAxis.text}为${flightNatures[clickedAxis.nature]}资源，是否强制分配？`;
            }

            layer.confirm(tipMessage, function (index) {
                checkingIndex = distributedWaitingDialog();

                selectedRow.opCode = values.join(",");
                if (selectedRow.opCode.indexOf(",") === 0) selectedRow.opCode = selectedRow.opCode.slice(1);

                selectedRow.inte = selectedRow.inteCheckingCounterCode;
                selectedRow.intl = selectedRow.intlCheckingCounterCode;

                if (nature == "1") selectedRow.inte = selectedRow.inteCheckingCounterCode = selectedRow.opCode
                else selectedRow.intl = selectedRow.intlCheckingCounterCode = selectedRow.opCode

                $.post(ctx + '/rms/rs/common_multi_manual_dist_4_mock', selectedRow, function (data) {
                    layer.close(checkingIndex);
                    if (data.code == 1) {
                        layer.msg(data.message);
                        updateResourcePosition(selectedRow.id, nature === "1" ? { inteCheckingCounterCode: selectedRow.opCode } : { intlCheckingCounterCode: selectedRow.opCode });
                    } else layer.alert(data.message);
                });
            }, clearManuallyGanttRefs);
        }
    }
}

function clearResource (selectedRow) {
    if (!selectedRow.inteCheckingCounterCode && !selectedRow.intlCheckingCounterCode) return;
    layer.confirm(`是否要清除「${selectedRow.flightCompanyCode + selectedRow.flightDynamicCode}」航班分配的值机柜台？`, { icon: 3, title: '提示' }, function (index) {
        $.post(ctx + '/rms/rs/clearCarouselCode4Mock', { detailId: selectedRow.detailId, inte: '', intl: '' }, function (data) {
            if (data.code == 1) {
                layer.msg(data.message);
                updateResourcePosition(selectedRow.id, { inteCheckingCounterCode: "", intlCheckingCounterCode: "", inteActualStartTime: "", inteActualOverTime: "" });
            } else layer.alert(data.message);
        });
    });
}

function updateResourcePosition (resourceId, definition) {
    enhanceUpdateDGRow("id", resourceId, definition);
    var templateData = _.findWhere(getAllRows(), { id: resourceId }),
        resourceNum = $utils.superTrim(templateData.inteCheckingCounterCode) + "," + $utils.superTrim(templateData.intlCheckingCounterCode);

    var ganttOpts = $(".gantt").data('options'), yAxis = ganttOpts.yAxis;
    $(".airplane-wrapper > div").filter(function () {
        return $(this).data('o').flightDynamicId === templateData.flightDynamicId;
    }).remove();

    ganttOpts.data = ganttOpts.data.filter(filterData => filterData.flightDynamicId !== templateData.flightDynamicId);
    var generateGanttData = newTempalteWrapper(templateData, templateData);
    if (resourceNum === ",") {
        //清除资源
        $.fn.gantt('append', $.extend(generateGanttData, {
            extra: { codeNature: templateData.flightDynamicNature }, gate: [], inteCode: [], intlCode: []
        }), ganttOpts);
    } else {
        // 根据resourceNum来重新绘制Gantt Element
        for (var resourceCode of resourceNum.split(",")) {
            if (_.isEmpty(resourceCode)) continue;
            generateGanttData.extra = { codeNature: yAxis.filter(axis => axis.text === resourceCode)[0].nature };
            generateGanttData.gate = [resourceCode];
            $.fn.gantt('append', generateGanttData, ganttOpts);
        }
    }
    removeEmptySubRows(ganttOpts); // 清空empty sub rows
    clearManuallyGanttRefs();
    immediateSortDate(datagrid, inoutTypeCode);
    layoutAutomaticallyAdapter();
}